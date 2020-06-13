package com.google.sps.data;

import java.util.Date;

/**
 * Class representing a Comment object, which consists of the text and the date 
 * 
 */
public class Comment {
  private final long id;
  private final String text;
  private final Date dateCreated;

  public Comment(Long id, String text, Date dateCreated) {
    this.id = id;
    this.text = text;
    this.dateCreated = dateCreated;
  }
}
