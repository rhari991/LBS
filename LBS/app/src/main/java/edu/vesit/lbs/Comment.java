package edu.vesit.lbs;

public class Comment
{

    private String posterName, postBody, postedTime;

    public Comment(String posterName, String postBody, String postedTime)
    {
        this.posterName = posterName;
        this.postBody = postBody;
        this.postedTime = postedTime;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

}
