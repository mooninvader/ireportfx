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
import net.sf.jasperreports.engine.export.JRHtmlExporter;

/**
 *
 * @author hakim
 */
public class ReportPrinter_old {
   Connection conn=null;

    public ReportPrinter_old(Connection conn) {
        this.conn=conn;
    }

    
    
    private String jasperReport(InputStream stream, String type,  Map params)  {
        
        JasperPrint jasperPrint = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(ReportPrinter_old.class.getResource("scott.jrxml").getPath());
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReportPrinter_old.class.getName()).log(Level.SEVERE, null, ex);
        } 

        JRExporter exporter = null;
        StringWriter out = new StringWriter();
        PrintWriter printWriter=new PrintWriter(out);        
        
        try {            
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, printWriter);
        } catch (RuntimeException e) {           
            throw e;
        }
        
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(ReportPrinter_old.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
          if (conn!=null) conn.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ReportPrinter_old.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return out.toString();
    }

//    private String generateReport(InputStream stream, Parameter... params)  {
//
//        //fill the parameters
//       Map fillParams = new HashMap();
//       for (Parameter param : params) {
//           fillParams.put(params[0].getName(), param.getValue());
//       }
//
//        return jasperReport(stream, "text/html", null, fillParams);
//    }

    public String generateReport(InputStream stream, Map fillParams)  {
        return jasperReport(stream, "text/html", fillParams);
    }

    
    
}
