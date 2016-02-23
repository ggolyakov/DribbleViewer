package com.woolf.dribbleviewer.rest.models;

public class SignInModel  {
//
//    private Observable<Pair<AuthenticatedUser>> mObservable;
//
//    public SignInModel() {
//        super();
//    }
//
//
//    private Pair<AuthenticatedUser> saveUserModel(RequestResult<AuthenticatedUserResult> result) {
//        AuthenticatedUser user = result.getData().getUser();
//        String message = result.getMessage();
//        DataManager.getInstance().getAuthenticatedUser().updateUser(user);
//        return new Pair<>(user, message);
//    }
//
//    private void createObservable(HashMap<String, String> params) {
//        SignInService service = ApiFactory.login();
//        if (mObservable == null) {
//            mObservable = service.login(params)
//                    .map(this::saveUserModel)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .cache();
//        }
//    }
//
//    public void login(HashMap<String, String> params) {
//        onStart();
//        createObservable(params);
//        unsubscribe();
//        subscribe();
//    }
//
//    @Override
//    protected void subscribe() {
//        mObservable.subscribe(this::success, this::error);
//    }
//
//    @Override
//    protected void clearRequestParams() {
//        super.clearRequestParams();
//        mObservable = null;
//    }
//
//    private void success(Pair<AuthenticatedUser> user) {
//        Log.e("REST","success");
//        onCompleted(user);
//        clearRequestParams();
//    }
//
//    private void error(Throwable throwable) {
//        Log.e("REST","error");
//        onError(throwable);
//        clearRequestParams();
//    }
}
