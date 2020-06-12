package com.google.sps.data;

import java.util.Date;

/**
 * Class representing a Comment object, which consists of the text and the date 
 * 
 */
public class Comment {

  private final String text;
  private final Date dateCreated;

  public Comment(String text, Date dateCreated) {
    this.text = text;
    this.dateCreated = dateCreated;
  }

  public String getText() {
    return text;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

}
