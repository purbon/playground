Gem::Specification.new do |spec|
  files = %x{git ls-files}.split("\n")

  spec.name = "purbon_playground"
  spec.version = "0.0.1"
  spec.summary = "purbon_playground"
  spec.description = "purbon_playground"
  spec.license = "Apache 2.0"

  spec.files = files
  spec.require_paths << "lib"

  spec.authors = ["Pere Urbon"]
  spec.email = ["pere.urbon@gmail.com"]
  spec.homepage = "https://github.com/purbon/purbon_playground"
  spec.platform = 'java'

  spec.add_development_dependency "clamp"      

end

