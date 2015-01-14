Gem::Specification.new do |spec|
  files = %x{git ls-files}.split("\n")

  spec.name = "dependant_playground"
  spec.version = "0.0.2"
  spec.summary = "dependant_playground"
  spec.description = "dependant_playground"
  spec.license = "Apache 2.0"

  spec.files = files
  spec.require_paths << "lib"

  spec.authors = ["Pere Urbon"]
  spec.email = ["pere.urbon@gmail.com"]
  spec.homepage = "https://github.com/purbon/dependant_playground"

  #spec.add_runtime_dependency 'purbon_playground', '>= 0', '< 2.0.0'
  spec.platform = 'java'

  spec.add_development_dependency 'logstash-devutils'
end
