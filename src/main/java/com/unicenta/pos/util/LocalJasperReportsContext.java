package com.unicenta.pos.util;

import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;

public class LocalJasperReportsContext extends SimpleJasperReportsContext {
    private final JasperReportsContext parentContext;

    public LocalJasperReportsContext(JasperReportsContext parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    public JasperReportsContext getParent() {
        return parentContext;
    }
}
