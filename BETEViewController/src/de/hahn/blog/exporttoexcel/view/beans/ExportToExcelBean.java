package de.hahn.blog.exporttoexcel.view.beans;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.export.ExportContext;
import oracle.adf.view.rich.export.FormatHandler;


public class ExportToExcelBean {
    private static ADFLogger _logger = ADFLogger.createADFLogger(de.hahn.blog.exporttoexcel.view.beans.ExportToExcelBean.class);
    // private row counter
    int count = 0;

    public ExportToExcelBean() {
    }

    /**
     * This method gets called for each cell which is to be exported.
     * It can be used to filter data to be exported. In this case salary values > 6000 are not exported
     * @param uIComponent component of the cess which gets to be exported
     * @param exportContext context of the exported data (holds e.g. file name, character set...)
     * @param formatHandler format to be exported
     * @return true if cell value is exported, false if not
     */
    public Boolean exportCollectionFilter(UIComponent uIComponent, ExportContext exportContext, FormatHandler formatHandler) {
        if (exportContext.isFirstInRow()) {
            count++;
            _logger.info("Start a new Row " + count);
        }
        _logger.info("Export Collection UIComponent: " + uIComponent.getId());
        if (uIComponent instanceof RichOutputText) {
            RichOutputText rot = (RichOutputText) uIComponent;
            Object val = rot.getValue();
            String headerText = "";
            UIComponent component = rot.getParent();
            if (component instanceof RichColumn) {
                RichColumn col = (RichColumn) component;
                headerText = col.getHeaderText();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ");
            sb.append(headerText);
            sb.append(" Value: ");
            sb.append(val);
            _logger.info(sb.toString());
            // check if the salary is greater than 6000
            if ("Salary".equals(headerText)) {
                if (((BigDecimal) val).intValue() > 6000) {
                    // if yes return false so that the value isn't exported
                    _logger.info("Skip Vals > 6000");
                    return false;
                }
            }
        }

        return true;
    }
}
