package keel.GraphInterKeel.datacf.visualizeData;

/**
 * <p>
 * @author Written by Juan Carlos Fernández and Pedro Antonio Gutiérrez(University of Córdoba) 23/10/2008
 * @version 1.0
 * @since JDK1.5
 * </p>
 */
public class VisualizePanelDataset extends javax.swing.JPanel {

    /**
     * <p>
     * Class that implements a Panel for datasets
     * </p>
     */

    /**
     * <p>
     * Constructor that initializes the panel
     * </p>
     */
    public VisualizePanelDataset() {
        initComponents();
    }

    /**
     * <p>
     * This method is called from within the constructor to
     * initialize the form.
     * </p>
     * <p>
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * </p>
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataSetScrollPane = new javax.swing.JScrollPane();
        dataSetTextArea = new javax.swing.JTextArea();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Content"));
        setName("Form"); // NOI18N

        dataSetScrollPane.setName("dataSetScrollPane"); // NOI18N

        dataSetTextArea.setColumns(20);
        dataSetTextArea.setRows(5);
        dataSetTextArea.setName("dataSetTextArea"); // NOI18N
        dataSetScrollPane.setViewportView(dataSetTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataSetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataSetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane dataSetScrollPane;
    private javax.swing.JTextArea dataSetTextArea;
    // End of variables declaration//GEN-END:variables

    /**
     * <p>
     * Sets text for dataset area
     * </p>
     * @param text Test for dataset area
     */
    public void setTextData(String text) {
        this.dataSetTextArea.setText(text);
    }

    /**
     * <p>
     * Sets care postion for dataset area
     * </p>
     * @param care Care postion for dataset area
     */
    public void setCaret(int care) {
        this.dataSetTextArea.setCaretPosition(care);
    }
}
