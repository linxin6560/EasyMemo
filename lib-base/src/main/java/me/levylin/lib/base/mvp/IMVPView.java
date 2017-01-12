package me.levylin.lib.base.mvp;

/**
 * MVP View的基础类
 * Created by LinXin on 2016/8/25 19:27.
 */
public interface IMVPView {

    /**
     * 显示进度框
     *
     * @param isShow 是否显示
     */
    void showLoadingDialog(boolean isShow);

    /**
     * 为Activity,Fragment等基类设置Presenter
     *
     * @param presenter the presenter
     */
    void setPresenter(BasePresenter presenter);

    /**
     * 在基类中销毁Presenter
     */
    void destroyPresenter();
}
