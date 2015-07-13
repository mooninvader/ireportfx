package ireportfx;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javax.swing.SwingUtilities;

/**
 *
 * @author hakim
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webView.getEngine().loadContent(generateReport());
    }


    Connection getConnection()  {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        Connection con=null;
        try {
            con = DriverManager.getConnection(url,"scott","tiger");
        } catch (Exception except) {
            except.printStackTrace();

        }
        return con;
    }

    private String generateReport() {
          ReportPrinter rp=new ReportPrinter(getConnection())  ;
          String sp=rp.generateReport("scott.jrxml",  null);
          System.out.println(sp);
          return sp;
    }

}
