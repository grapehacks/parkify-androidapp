package com.grapeup.parkify.mvp;

/**
 * Base class for presenter
 *
 * @author Pavlo Tymchuk
 */
public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {
    private T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    public void checkViewIsAttached() {
        if (!isViewAttached()) {
            throw new IllegalStateException(mView.getClass().getSimpleName() +
                    " view is not attached. Please call Presenter.attachView(view) before" +
                    " requesting data to the Presenter");
        }
    }
}