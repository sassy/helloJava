package sample;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Flowable;

import java.lang.Thread;

public class Main {
    public static void main(String[] args) throws Exception {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(Flowable.range(1, 3)
            .doOnCancel(() -> System.out.println("No.1 canceled"))
            .observeOn(Schedulers.computation())
            .subscribe(data -> {
                Thread.sleep(100L);
                System.out.println("No.1: " + data);
        }));

        compositeDisposable.add(Flowable.range(1, 3)
            .doOnCancel(() -> System.out.println("No.2 canceled"))
            .observeOn(Schedulers.computation())
            .subscribe(data -> {
                Thread.sleep(100L);
                System.out.println("No.2: " + data);
        }));

        Thread.sleep(150L);

        compositeDisposable.dispose();
    }
}