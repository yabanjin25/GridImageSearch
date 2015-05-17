package com.example.ayamanaka.gridimagesearch.models;

import java.io.Serializable;
import java.util.regex.Pattern;

public class QueryParams implements Serializable {

    private String expression;
    private String color;
    private String size;
    private String siteFilter;
    private String imageType;
    private String fileType;
    private String resultsPerPage;
    private String indexOfFirstResult;
    private String safe;

    public QueryParams(String expression)
    {
        this.expression = expression;
        this.color = "";
        this.size = "";
        this.siteFilter = "";
        this.imageType = "";
        this.fileType = "";
        this.resultsPerPage = "";
        this.indexOfFirstResult = "";
        this.safe = "";
    }

    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public void setSiteFilter(String siteFilter)
    {
        this.siteFilter = siteFilter;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public void setResultsPerPage(String resultsPerPage)
    {
        this.resultsPerPage = resultsPerPage;
    }

    public void setIndexOfFirstResult(String indexOfFirstResult)
    {
        this.indexOfFirstResult = indexOfFirstResult;
    }

    public void setSafe(String safe)
    {
        this.safe = safe;
    }

    public String getExpression()
    {
        return expression;
    }

    public String getColor()
    {
        return color;
    }

    public String getSize()
    {
        return size;
    }

    public String getSiteFilter()
    {
        return siteFilter;
    }

    public String getImageType()
    {
        return imageType;
    }

    public String getFileType()
    {
        return fileType;
    }

    public String getResultsPerPage()
    {
        return resultsPerPage;
    }

    public String getIndexOfFirstResult()
    {
        return indexOfFirstResult;
    }

    public String getSafe()
    {
        return safe;
    }

    @Override
    public String toString()
    {
        return constructFullQueryString();
    }

    public boolean isValidQueryParam()
    {
        return !Pattern.matches("\\^\\s*$", expression);
    }

    private String constructFullQueryString()
    {
        String result = expression;

        if (!color.equals("")) {
            result += "&imgcolor=" + color;
        }

        if (!size.equals("")) {
            result += "&imgsz=" + size;
        }

        if (!siteFilter.equals("")) {
            result += "&as_sitesearch=" + siteFilter;
        }

        if (!imageType.equals("")) {
            result += "&imgtype=" + imageType;
        }

        if (!fileType.equals("")) {
            result += "&as_filetype=" + fileType;
        }

        if (!resultsPerPage.equals("")) {
            result += "&rsz=" + resultsPerPage;
        }

        if (!indexOfFirstResult.equals("")) {
            result += "&start=" + indexOfFirstResult;
        }

        if (!safe.equals("")) {
            result += "&safe=" + safe;
        }

        return result;
    }
}
