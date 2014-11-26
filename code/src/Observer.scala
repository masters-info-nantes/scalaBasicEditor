trait Observer[T] {
    def receiveUpdate(subject: T);
}

trait Subject[T] { 
    this: T =>
    private var observers: List[Observer[T]] = Nil
    def addObserver(observer: Observer[T]) = observers = observer :: observers
    def notifyObservers() = observers.foreach(_.receiveUpdate(this))
}