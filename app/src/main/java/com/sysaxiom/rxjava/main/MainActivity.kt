package com.sysaxiom.rxjava.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxrelay3.BehaviorRelay
import com.sysaxiom.rxjava.R
import com.sysaxiom.rxjava.complexui.ComplexUIActivity
import com.sysaxiom.rxjava.database.DatabaseActivity
import com.sysaxiom.rxjava.network.NetworkActivity
import com.sysaxiom.rxjava.simpleui.SimpleUIActivity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {

    var job : Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleObservables()

        simple_ui.setOnClickListener {
            Intent(this, SimpleUIActivity::class.java).also {
                this.startActivity(it)
            }
        }

        database_layer.setOnClickListener {
            Intent(this, DatabaseActivity::class.java).also {
                this.startActivity(it)
            }
        }

        network_layer.setOnClickListener {
            Intent(this, NetworkActivity::class.java).also {
                this.startActivity(it)
            }
        }

        complex_ui.setOnClickListener {
            Intent(this, ComplexUIActivity::class.java).also {
                this.startActivity(it)
            }
        }
    }

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

    fun simpleBehaviorSubjects() {
        val behaviorSubject = BehaviorSubject.createDefault(24)

        behaviorSubject.subscribe({ newValue -> //onNext
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

    fun creatingObservables() {
//        val observable = Observable.just(23)
//        val observable = Observable.interval(300, TimeUnit.MILLISECONDS).timeInterval(AndroidSchedulers.mainThread())
//          val observable = Observable.fromArray(1,2,3,4,5,6)
//        val userIds = arrayOf(1,2,3,4,5,6)
//        val observable = Observable.fromArray(*userIds)
//        val observable = userIds.toObservable()
    }

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

}
