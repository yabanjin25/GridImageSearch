package com.example.ayamanaka.gridimagesearch.models;

import java.io.Serializable;

public class ImageResult implements Serializable {
    private String id;
    private String height;
    private String width;
    private String fullUrl;
    private String thumbUrl;
    private String siteOrigin;
    private String title;
    private String titleNoFormatting;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public void setFullUrl(String fullUrl)
    {
        this.fullUrl = fullUrl;
    }

    public void setThumbUrl(String thumbUrl)
    {
        this.thumbUrl = thumbUrl;
    }

    public void setSiteOrigin(String siteOrigin)
    {
        this.siteOrigin = siteOrigin;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setTitleNoFormatting(String title)
    {
        this.titleNoFormatting = title;
    }

    public String getId()
    {
        return id;
    }

    public String getHeight()
    {
        return height;
    }

    public String getWidth()
    {
        return width;
    }

    public String getFullUrl()
    {
        return fullUrl;
    }

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public String getSiteOrigin()
    {
        return siteOrigin;
    }

    public String getTitle()
    {
        return title;
    }

    public String getTitleNoFormatting()
    {
        return titleNoFormatting;
    }

}
