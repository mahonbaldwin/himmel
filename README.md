# Himmel

A basic wrapper for Google Cloud Datastore.

[![Clojars Project](https://img.shields.io/clojars/v/himmel.svg)](https://clojars.org/himmel)

## Why?

In addition to providing a convenient wrapper for developers writing Clojure, using the Java interop will not work for the Google Cloud Datastore library because of heavy use of the Builder Pattern which is not always compatible with Clojure's use of reflection. Some parts work, but I wasn't able to get very far before I started to get Exceptions with the message, "Can't call public method of non-public class." [More information on this problem.](http://stackoverflow.com/questions/38059977/cant-call-public-method-of-non-public-class-public-google-gcloud-library) 

## Notice

This is a basic wrapper. Not all functionality is present, but if you're looking for writing to and retrieving from the datastore this should be sufficient. For example, using this library outside of the Google Cloud ecosystem will require extra code that this library doesn't support.

Pull requests are welcome if you would like to add to this wrapper. If you're interested, see the [contributing](#contributing) section below.

## <a name="quick-start"></a>Quick Start

### <a name="add-entity"></a>Add Entity to Datastore

    (def ds himmel/datastore)
     
    (put-entity ds
        (make-entity ds "Task" "task-1" {:task-name "Eat a worm."
                                         :done false}))

This will create a new entity with type `Task` and key `task-1`.

### <a name="get-entity"></a>Get an entity

    (get-entity ds (entity-key ds "Task" "task-1"))
    

## <a name="version-numbers"></a>Version Numbers

Version numbers follow council from Rich Hickey's talk [Spec-ulation](https://www.youtube.com/watch?v=oyLBGkS5ICk). Basically, the major version of the library will never change (it will always be 1). If we ever need to make an incompatible change we will make a new version of the function, namespace, or class and the preserve the old one so that upgrades to newer versions of this library preserve old functionality and all changes will be backwards compatible. The one exception is when there is a post-fix to the library version. In es: version`1.0.1` will be backwards compatible with `1.0.0`, but `1.0.1` may not be backward compatible with `1.0.1-SNAPSHOT`, `1.0.1-ALPHA`, or `1.0.1-BETA`. If (beyond my ability to see now) we do need to change some functionality in an incompatible way, then a new, separate library will be created.

## <a name="contributing"></a>Contributing

When contributing please take care to follow to follow the practice explained in the [version numbers](#version-numbers) section above.

## License

Copyright © 2017 Mahon Baldiwn

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
