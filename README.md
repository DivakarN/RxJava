# RxJava

Java Observables:
-----------------
Observer pattern , but it has few disadvantages
1) Complex
2) Little to no examples
3) use with layered architecture?
4) Not kotlinish

RxJava:
-------
RxJava is ReactiveX family for reactive programming.
Observer pattern process to be notified when data changes, task completes, errors happen.

Advantages:
-----------
Common pattern for events and data changes.
Filter, map values to new results.
Chain actions.
Async and threading APIs.

Observables - What you watch:
-----------------------------
Unbounded list of events - UI events
Single variable - A title change after data update
Tasks of code - network taks 
Bounded array - a list of user ids to process

Observer/Subscribers - Watchers:
--------------------------------

OnNext - next item to be subscribed
OnSubscribe - called if subscription started
OnComplete - called if subscription completed
OnError - called if subscription errored

private fun sampleObservables(){
    val observable = Observable.fromArray(1,2,3,4,5,6,7,8)

    val disposable = observable.subscribe({ number: Int -> //onNext
        println(number)
    }, { error ->
        println("Error $error")
    }, { //onCompleted
        println("Subscribed")
    })

}

Types of Observables:
---------------------

Relay
Subjects
Observables

Relay:
------
Will fit most of your needs
Get and set any time
Can subscribe to when it changes
Hot Observable
Never error out or complete

fun simpleBehaviorRelay() {

    val someInfo = BehaviorRelay.createDefault("1")
    println("🙈 someInfo.value ${ someInfo.value }")

    val plainString = someInfo.value
    println("🙈 plainString: $plainString")

    someInfo.accept("2")
    println("🙈 someInfo.value ${ someInfo.value }")

    someInfo.subscribe { newValue ->
        println("🦄 value has changed: $newValue")
    }

    someInfo.accept("3")

    //NOTE: Relays will never receive onError, and onComplete events
}

Subjects:
---------
Can receive onError/onCompleted events
"Die" after onError/onCompleted
Can observe and be observed
Hot observable

fun simpleBehaviorSubjects() {
    val behaviorSubject = BehaviorSubject.createDefault(24)

    val disposable = behaviorSubject.subscribe({ newValue -> //onNext
        println("🕺 behaviorSubject subscription: $newValue")
    }, { error -> //onError
        println("🕺 error: ${ error.localizedMessage }")
    }, { //onCompleted
        println("🕺 completed")
    }).dispose()

    behaviorSubject.onNext(34)
    behaviorSubject.onNext(48)
    behaviorSubject.onNext(48) //duplicates show as new events by default

    //1 onError
    val someException = IllegalArgumentException("some fake error")
    behaviorSubject.onError(someException)
    behaviorSubject.onNext(109) //will never show

    //2 onComplete
    behaviorSubject.onComplete()
    behaviorSubject.onNext(10983) //will never show

}

Subjects types:
---------------

Behaviour - Behaviour gives most recent values(last event or default)
Publish - Its only get new values(New events)
Replay - Depends on the bufferSize its gives previous events(n number of previous events)

val subject = ReplaySubject<String>.create(bufferSize: 3)

Traits:
-------

Traits are observable with only one event.

Single(onNext, onError):
------------------------

fun traitsSingle() {
    val single = Single.create<String> { single ->
        //do some logic here
        val success = true


        if (success) { //return a value
            single.onSuccess("nice work!")
        } else {
            val someException = IllegalArgumentException("some fake error")
            single.onError(someException)
        }
    }

    single.subscribe({ result ->
        //do something with result
        println("👻 single: ${ result }")
    }, { error ->
        //do something for error
    })
}
	
	
Completable(onComplete, onError):
---------------------------------

fun traitsCompletable() {

    val completable = Completable.create { completable ->
        //do logic here
        val success = true

        if (success) {
            completable.onComplete()
        } else {
            val someException = IllegalArgumentException("some fake error")
            completable.onError(someException)
        }
    }

    completable.subscribe({
        //handle on complete
        println("👻 Completable completed")
    }, { error ->
        //do something for error
    })

}

Maybe(onNext/onCompleted, onError):
-----------------------------------

fun traitsMaybe() {
    val maybe = Maybe.create<String> { maybe ->
        //do something
        val success = true
        val hasResult = true


        if (success) {
            if (hasResult) {
                maybe.onSuccess("some result")
            } else {
                maybe.onComplete()
            }
        } else {
            val someException = IllegalArgumentException("some fake error")
            maybe.onError(someException)
        }
    }

    maybe.subscribe({ result ->
        //do something with result
        println("👻 Maybe - result: ${ result }")
    }, { error ->
        //do something with the error
    }, {
        //do something about completing
        println("👻 Maybe - completed")
    })
}

Observables:
------------

fun basicObservable() {
    //The observable
    val observable = Observable.create<String> { observer ->
        //The lambda is called for every subscriber - by default
        println("🍄 ~~ Observable logic being triggered ~~")

        //Do work on a background thread
         launch (Dispatchers.IO){
             delay(1000) //artificial delay 1 second

             observer.onNext("some value 23")
             observer.onComplete()
         }
    }

    observable.subscribe { someString ->
        println("🍄 new value: $someString")
    }

    observable.subscribe { someString ->
        println("🍄 Another subscriber: $someString")
    }
}

Zip:
----

Zip combine the emissions of multiple Observables together via a specified function and emit single items for each combination based on the results of this function

Advantages of Zip Operator:

Run all the tasks in parallel when Schedulers are correctly provided to each observable.
Return the results of all the tasks in a single callback when all the tasks are completed.

CompositeDisposable:
--------------------

Its container of disposable(A Disposable is a stream or a link between an Observable and an Observer).
Subscriptions to streams need to be disposed at the right time in order to avoid memory leaks. 

Threading:
----------

observeOn - It determines what thread the subscription block runs on.
subscripeOn(Thread) - It determines what thread the observables runs on.
