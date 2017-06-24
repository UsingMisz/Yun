package zzy.com.Bean;

/**
 * Created by Administrator on 2017/3/25.
 */

public class GridViewBean {
    private String title;
    private int imageId;

    public GridViewBean()
    {
        super();
    }

    public GridViewBean(String title, int imageId)
    {
        super();
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }
}
