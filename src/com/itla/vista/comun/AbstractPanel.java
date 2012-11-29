package com.itla.vista.comun;

import java.awt.Window;
import javax.swing.JPanel;
import javax.swing.JWindow;


/**
 *
 * @author Maria Elena
 */
public class AbstractPanel extends JPanel {

    protected Window padre;

    public void setPadre(JWindow padre) {
        this.padre = padre;
    }
}
