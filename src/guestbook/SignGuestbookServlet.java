package guestbook;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
//Импорт дата формата
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignGuestbookServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    String guestbookName = req.getParameter("guestbookName");
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    String content = req.getParameter("content");
    Date date = new Date();
    //Форматируем дату
    SimpleDateFormat df = new SimpleDateFormat("dd:MM:yy (HH:mm:ss)");
    String timeStamp = df.format(date);
    //отформатировали
    Entity greeting = new Entity("Greeting", guestbookKey);
    greeting.setProperty("user", user);
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);
    //добавляем свойство timeStamp
    greeting.setProperty("timeStamp", timeStamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);

    resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
  }
}
//[END all]