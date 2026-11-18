import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

    public class ProductApp extends JFrame {
        private JTextField txtCustomerName, txtPrice;
        private JComboBox<String> cmbProduct;
        private JButton btnProcess;

        public ProductApp() {
            setTitle("Product App");
            setSize(500, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(null);
            setLocationRelativeTo(null);


            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenu toolsMenu = new JMenu("Tools");
            JMenuItem exitItem = new JMenuItem("Exit");
            JMenuItem processItem = new JMenuItem("Process");
            JMenuItem clearItem = new JMenuItem("Clear");

            fileMenu.add(exitItem);
            toolsMenu.add(processItem);
            toolsMenu.add(clearItem);
            menuBar.add(fileMenu);
            menuBar.add(toolsMenu);

            menuBar.add(new JMenu("Edit"));
            setJMenuBar(menuBar);

            JLabel lblCustomer = new JLabel("Customer Name:");
            lblCustomer.setBounds(30, 30, 120, 25);
            txtCustomerName = new JTextField();
            txtCustomerName.setBounds(160, 30, 280, 25);

            JLabel lblProduct = new JLabel("Select Product:");
            lblProduct.setBounds(30, 80, 120, 25);
            cmbProduct = new JComboBox<>(new String[]{"Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"});
            cmbProduct.setBounds(160, 80, 280, 25);

            JLabel lblPrice = new JLabel("Enter Price:");
            lblPrice.setBounds(30, 130, 120, 25);
            txtPrice = new JTextField();
            txtPrice.setBounds(160, 130, 280, 25);

            btnProcess = new JButton("PROCESS");
            btnProcess.setBounds(160, 180, 280, 35);

            add(lblCustomer); add(txtCustomerName);
            add(lblProduct); add(cmbProduct);
            add(lblPrice); add(txtPrice);
            add(btnProcess);


            btnProcess.addActionListener(e -> processSale());
            processItem.addActionListener(e -> processSale());
            clearItem.addActionListener(e -> clearForm());
            exitItem.addActionListener(e -> System.exit(0));
        }

        private void processSale() {
            try {
                String customerName = txtCustomerName.getText();
                String productName = (String) cmbProduct.getSelectedItem();
                double price = Double.parseDouble(txtPrice.getText());

                if (customerName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter customer name");
                    return;
                }

                ProductCalculations calc = new ProductCalculations(price);
                double vat = calc.GetVat();
                double commission = calc.GetCommission();
                double total = calc.GetTotal();


                String message = "Customer Name: " + customerName + "\n" +
                        "Product: " + productName + "\n" +
                        "VAT: R " + vat + "\n" +
                        "COMMISSION: R " + commission + "\n" +
                        "TOTAL: R " + total;
                JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);


                try (FileWriter w = new FileWriter("data.txt")) {
                    w.write("DATA LOG\n");
                    w.write("****************************************\n");
                    w.write("Customer Name: " + customerName + "\n");
                    w.write("Product: " + productName + "\n");
                    w.write("VAT: R " + vat + "\n");
                    w.write("COMMISSION: R " + commission + "\n");
                    w.write("TOTAL: R " + total + "\n");
                    w.write("****************************************\n");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid price");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        private void clearForm() {
            txtCustomerName.setText("");
            txtPrice.setText("");
            cmbProduct.setSelectedIndex(0);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
        }
    }

