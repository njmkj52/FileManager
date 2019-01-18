package c.sra.filemanager;

import android.graphics.Bitmap;

public class ListItem {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private String mPath = null;

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
}