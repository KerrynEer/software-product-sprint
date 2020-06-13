// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.sps.data.Comment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@WebServlet("/comments")
public class DataServlet extends HttpServlet {

    private List<Comment> commentsList = new ArrayList<Comment>(
        List.of(new Comment("I really like your webpage!", new Date()))
      );

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String commentsInJson = convertToJsonUsingGson(commentsList);

    response.setContentType("application/json;");
    response.getWriter().println(commentsInJson);
  }

  /**
   * Converts list of comments into a JSON string using the Gson library.
   */
  private String convertToJsonUsingGson(List<Comment> commentsList) {
    Gson gson = new Gson();
    String json = gson.toJson(commentsList);
    return json;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userComment = request.getParameter("user-comment");

    Date currentDate = new Date();
    Comment commentObj = new Comment(userComment, currentDate);
    commentsList.add(commentObj);

    response.sendRedirect("/index.html");
  }

}
