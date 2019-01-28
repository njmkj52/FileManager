package c.sra.filemanager;

import android.graphics.Bitmap;

public class ListItem {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private String mPath = null;
    private Boolean isPushed = false;

    /**
     * 空のコンストラクタ
     */
    public ListItem() {};

    /**
     * コンストラクタ
     * @param thumbnail サムネイル画像
     * @param title タイトル
     */
    public ListItem(Bitmap thumbnail, String title, String path) {
        mThumbnail = thumbnail;
        mTitle = title;
        mPath = path;
        isPushed = false;
    }

    /**
     * サムネイル画像を設定
     * @param thumbnail サムネイル画像
     */
    public void setThumbnail(Bitmap thumbnail) {
        mThumbnail = thumbnail;
    }

    /**
     * タイトルを設定
     * @param title タイトル
     */
    public void setmTitle(String title) {
        mTitle = title;
    }

    /**
     * パスを設定
     * @param path タイトル
     */
    public void setmPath(String path) {
        mPath = path;
    }

    /**
     * 履歴の設定
     */
    public void setHistory() {
        isPushed = true;
    }

    /**
     * サムネイル画像を取得
     * @return サムネイル画像
     */
    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    /**
     * タイトルを取得
     * @return タイトル
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * パスを取得
     * @return タイトル
     */
    public String getPath() {
        return mPath;
    }

    /**
     * 履歴
     * @return 履歴
     */
    public Boolean isHistory() {
        return isPushed;
    }


}