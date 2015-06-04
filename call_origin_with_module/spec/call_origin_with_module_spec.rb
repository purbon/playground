require 'spec_helper'

describe "CallOriginWithModule" do

  it "calls the generate once" do
    expect(JrJackson::Raw).to receive(:generate).with({:source=>3, :target=>-3}).and_call_original
    expect(AClass.bar(-3)).to eq("{\"source\":3,\"target\":-3}")
  end

  it "calls the generate twice" do
    expect(JrJackson::Raw).to receive(:generate).with({:source=>3, :target=>3}).and_call_original
    expect(AClass.bar(3)).to eq("{\"source\":3,\"target\":3}")
  end

   it "calls the dump once" do
    expect(JrJackson::Raw).to receive(:dump).with([3,-3]).and_call_original
    expect(AClass.nap(-3)).to eq("[3,-3]")
  end

  it "calls the dump twice" do
    expect(JrJackson::Raw).to receive(:dump).with([3,3]).and_call_original
    expect(AClass.nap(3)).to eq("[3,3]")
  end

  describe "without using call original" do
  
    it "calls the generate once " do
      expect(JrJackson::Raw).to receive(:generate).and_return("foo")
      expect(AClass.bar(-3)).to eq("foo")
    end

    it "calls the generate twice" do
      expect(JrJackson::Raw).to receive(:generate).and_return("oof")
      expect(AClass.bar(3)).to eq("oof")
    end

  end



end
