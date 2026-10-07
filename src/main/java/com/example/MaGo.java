package com.example;

public abstract class MaGo implements MaGoSubject {

    private static final int MAX_STATUS = 100;
    private static final int MIN_STATUS = -100;
    private static final int MAGO_INITIAL_STATUS = 0;

    private MaGoObserver observer;
    private int status;

    public MaGo(MaGoObserver observer) {
        this.observer = observer;
        this.status = MAGO_INITIAL_STATUS;
    }

    @Override
    public int getStatusValue() {
        return status;
    }

    public static int getMaxStatus() {
        return MAX_STATUS;
    }
    public static int getMinStatus() {
        return MIN_STATUS;
    }

    public void reactionToMessage(String message) {
        int change = computeStatusChange(message);
        setStatus(status + change);
    }

    public void setStatus(int newStatus) {
        if (newStatus > MAX_STATUS) {
            status = MAX_STATUS;
        } else if (newStatus < MIN_STATUS) {
            status = MIN_STATUS;
        } else {
            status = newStatus;
        }
        notifyObserver();
    }
    
    private void notifyObserver() {
        observer.update(this);
    }

    public abstract boolean isCircle();

    protected abstract int computeStatusChange(String message);

}