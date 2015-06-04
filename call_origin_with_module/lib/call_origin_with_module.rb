require "call_origin_with_module/version"
require "jrjackson"

module AClass

  extend self
  def bar(n)
    JrJackson::Raw.generate({:source => 3, :target => n})
  end

  def nap(n)
    JrJackson::Json.dump({:source => 3, :target => n})
  end
end
