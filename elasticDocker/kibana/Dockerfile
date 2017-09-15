FROM ubuntu:16.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update

RUN apt-get -y install python-software-properties
RUN apt-get -y install software-properties-common
RUN apt-get -y install apt-transport-https

RUN add-apt-repository ppa:webupd8team/java

RUN  apt-get update \
  && apt-get install -y wget \
  && apt-get install -y default-jdk \
  && rm -rf /var/lib/apt/lists/*

RUN ls /usr/lib/jvm

ENV JAVA_HOME  /usr/lib/jvm/java-8-openjdk-amd64


RUN wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | apt-key add -
RUN echo "deb https://artifacts.elastic.co/packages/5.x/apt stable main" | tee -a /etc/apt/sources.list.d/elastic-5.x.list

RUN apt-get update && apt-get -y install kibana

COPY kibana.yml /etc/kibana/

ENV PATH=$PATH:/usr/share/kibana/bin

CMD ["kibana"]

EXPOSE 5601
