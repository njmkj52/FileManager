package c.sra.filemanager;

import android.graphics.Bitmap;

public class ListItem {
    private int mThumbnailId = 0;
    private String mTitle = null;
    private String mPath = null;
    private String mExt = null;
    private Boolean isPushed = false;

    /**
     * 空のコンストラクタ
     */
    public ListItem() {};

    /**
     * コンストラクタ
     * @param thumid サムネイル画像
     * @param title タイトル
     */
    public ListItem(int thumid, String title, String path, String ext) {
        mThumbnailId = thumid;
        mTitle = title;
        mPath = path;
        mExt = ext;
        isPushed = false;
    }

    /**
     * サムネイル画像のIDを設定
     * @param thumid
     * @return サムネイル画像のID
     */
    public void setThumbnailId(int thumid) {
        mThumbnailId = thumid;
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
     * 拡張子を設定
     * @param ext 拡張子
     */
    public void setmExt(String ext) {
        mExt = ext;
    }

    /**
     * 履歴の設定
     */
    public void setHistory() {
        isPushed = true;
    }

    /**
     * サムネイル画像のIDを取得
     * @return サムネイル画像のID
     */
    public int getThumbnailId() {
        return mThumbnailId;
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
     * 拡張子を取得
     * @return 拡張子
     */
    public String getExt() {
        return mExt;
    }

    /**
     * 履歴
     * @return 履歴
     */
    public Boolean isHistory() {
        return isPushed;
    }


}