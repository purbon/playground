import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Directory2FileIterator;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileListIterator;
import cc.mallet.pipe.iterator.SimpleFileLineIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import cc.mallet.util.CharSequenceLexer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class TopicModeling {


    private static String baseDir = "/Users/purbon/work/relevance-search/tools/Mallet/";

    public static void main(String[] args) throws Exception {

        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new Input2CharSequence());
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(CharSequenceLexer.LEX_WORD_CLASSES));
        pipeList.add(new TokenSequenceRemoveStopwords(new File(baseDir + "stoplists/en.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());

        InstanceList instances = new InstanceList(new SerialPipes(pipeList));


        File dir = new File(baseDir + "sample-data/web/en");

        /*for(File file : dir.listFiles()) {
            SimpleFileLineIterator iterator = new SimpleFileLineIterator(file.getAbsoluteFile().getPath());
            instances.addThruPipe(iterator);
        }*/


        File[] files = dir.listFiles();
        FileListIterator it = new FileListIterator(files, new FileFilter() {
            public boolean accept(File pathname) {
                return true;
            }
        }, Pattern.compile("(\\.txt)"), true);

        instances.addThruPipe(it);

        int numTopics = 10;
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.001);

        model.addInstances(instances);

        model.setNumThreads(4);
        model.setNumIterations(50);
        model.estimate();

        Alphabet dataAlphabet = instances.getDataAlphabet();

        System.out.println(model.getData().size());


        for (int i = 0; i < model.getData().size() - 11; i++) {
            System.out.println("Instance: " + i);

            FeatureSequence tokens = (FeatureSequence) model.getData().get(i).instance.getData();
            LabelSequence topics = model.getData().get(i).topicSequence;
            Formatter out = new Formatter(new StringBuilder(), Locale.GERMANY);

            double[] topicDistribution = model.getTopicProbabilities(i);

            // Get an array of sorted sets of word ID/count pairs
            ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

            // Show top 5 words in topics with proportions for the first document
            for (int topic = 0; topic < numTopics; topic++) {
                Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

                out = new Formatter(new StringBuilder(), Locale.GERMANY);
                out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
                int rank = 0;
                while (iterator.hasNext() && rank < 5) {
                    IDSorter idCountPair = iterator.next();
                    out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                    rank++;
                }
                System.out.println(out);
            }


            // Create a new instance with high probability of topic 0
            StringBuilder topicZeroText = new StringBuilder();
            Iterator<IDSorter> iterator = topicSortedWords.get(i).iterator();

            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
                rank++;
            }

            // Create a new instance named "test instance" with empty target and source fields.
            InstanceList testing = new InstanceList(instances.getPipe());
            testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

            TopicInferencer inferencer = model.getInferencer();
            double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
            System.out.println("0\t" + testProbabilities[0]);

        }

    }
}
