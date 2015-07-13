package ireportfx;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
        
/**
 *
 * @author hakim
 */
public class ReportPrinter {

    Connection conn = null;

    public ReportPrinter(Connection conn) {
        this.conn = conn;
    }


    private String jasperReport(String name, 
            ResultSet data, Map params) {

        JasperPrint jasperPrint = null;
        try {
            JasperReport jr=JasperCompileManager.compileReport(ReportPrinter.class.getResourceAsStream(name));
            InputStream stream = ReportPrinter.class.getResourceAsStream(name);
            jasperPrint = JasperFillManager.fillReport(
                    jr, params, conn);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReportPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

        JRExporter exporter = null;
        StringWriter out = new StringWriter();
        PrintWriter printWriter = new PrintWriter(out);

        try {
            exporter = new JRXhtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                    jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                    printWriter);

        } catch (RuntimeException e) {
            throw e;
        }
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(ReportPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ReportPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return out.toString();
    }

    public String generateReport(String ReportName, Map params) {
        return jasperReport(ReportName, null, params);
    }
    
}

