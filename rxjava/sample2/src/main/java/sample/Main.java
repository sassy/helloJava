package sample;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.ObservableEmitter;

public class Main {
    public static void main(String[] args) throws Exception {
        Observable<String> observable = 
            Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String[] datas = {"Hello, World!", "こんにちは、世界!"};

                for (String data: datas) {
                    if (emitter.isDisposed()) {
                        return;
                    }

                    emitter.onNext(data);
                }

                emitter.onComplete();
            }
        });

        observable
            .observeOn(Schedulers.computation())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    //do nothing
                }

                @Override
                public void onNext(String item) {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + ": " + item);
                }

                @Override
                public void onComplete() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + ": 完了しました");
                }

                @Override
                public void onError(Throwable error) {
                    error.printStackTrace();
                }
            });

        Thread.sleep(500L);
    }
}