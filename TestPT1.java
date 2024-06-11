package testpt1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class TestPT1 extends JFrame implements ItemListener {
    
    // Database connection 
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/finalipt";//<<--ni nama database  
    String username = "root";
    String password = "";
    Connection connection;
    
    JLabel lblTitle, lblname, lblID, lblEmail, lblProglvl, lblProgName, lblProgSession, lblOutput;
    JTextField txtname, txtID, txtEmail;
    JTextArea txtOutput;
    JComboBox<String> cboProgramLevel, cboProgramName, cboSession;
    JButton btnSubmit, btnView, btnReset;
    
    String program[] = {"--Please Choose--", "Degree", "Diploma"};
    String diploma[] = {"--Please Choose--", "Diploma in Business Administration",
            "Diploma in Information Technology",
            "Diploma in Electrical Engineering", "Diploma in Mechanical Engineering",
            "Diploma in Manufacturing Technology"};
    String degree[] = {"--Please Choose--", "Degree in Manufacturing Technology", "Degree in Business Administration"};
    String sessions[] = {"--Please Choose--", "Session 1", "Session 2", "Session 3"};
    
    TestPT1() {
        setTitle("STUDENT ENROLLMENT SYSTEM SUNWAY UNIVERSITY");
        setLayout(null);
        
        // TITLE
        lblTitle = new JLabel("ENROLLMENT DATA");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 25));
        lblTitle.setForeground(Color.BLUE);
        add(lblTitle);
        lblTitle.setBounds(100, 20, 500, 40);
        
        // Content
        lblname = new JLabel("Name ");
        add(lblname);
        lblname.setBounds(30, 60, 120, 25);
        
        txtname = new JTextField(10);
        add(txtname);
        txtname.setBounds(150, 60, 250, 25);
        
        lblID = new JLabel("StudentID ");
        add(lblID);
        lblID.setBounds(30, 90, 120, 25);
        
        txtID = new JTextField(10);
        add(txtID);
        txtID.setBounds(150, 90, 250, 25);
        
        lblEmail = new JLabel("Email ");
        add(lblEmail);
        lblEmail.setBounds(30, 120, 120, 25);
        
        txtEmail = new JTextField(10);
        add(txtEmail);
        txtEmail.setBounds(150, 120, 250, 25);
        
        lblProglvl = new JLabel("Program Level ");
        add(lblProglvl);
        lblProglvl.setBounds(30, 150, 120, 25);
        
        cboProgramLevel = new JComboBox<>(program);
        cboProgramLevel.addItemListener(this);
        add(cboProgramLevel);
        cboProgramLevel.setBounds(150, 150, 250, 25);
        
        lblProgName = new JLabel("Program Name ");
        add(lblProgName);
        lblProgName.setBounds(30, 180, 120, 25);
        
        cboProgramName = new JComboBox<>(new String[]{"--Please Choose--"});
        add(cboProgramName);
        cboProgramName.setBounds(150, 180, 250, 25);
        
        lblProgSession = new JLabel("Program Session ");
        add(lblProgSession);
        lblProgSession.setBounds(30, 210, 120, 25);
        
        cboSession = new JComboBox<>(sessions);
        add(cboSession);
        cboSession.setBounds(150, 210, 250, 25);
        
        btnSubmit = new JButton("Submit");
        add(btnSubmit);
        btnSubmit.setBounds(50, 250, 100, 25);
        
        btnView = new JButton("View");
        add(btnView);
        btnView.setBounds(180, 250, 100, 25);
        
        btnReset = new JButton("Reset");
        add(btnReset);
        btnReset.setBounds(300, 250, 100, 25);
        
        lblOutput = new JLabel("Output ");
        add(lblOutput);
        lblOutput.setBounds(30, 290, 120, 25);
        
        txtOutput = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(30, 320, 400, 200);
        add(scrollPane);
        
         // Submit use button action listener
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtname.getText();
                String studentID = txtID.getText();
                String email = txtEmail.getText();
                String programLevel = cboProgramLevel.getSelectedItem().toString();
                String programName = cboProgramName.getSelectedItem().toString();
                String session = cboSession.getSelectedItem().toString();

                if (!name.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Letter only!");
                    return;
                }

                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Email must contain @.");
                    return;
                }
            
                try {
                    // ni database connection
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, username, password);

                    // ni untuk insert data to database
                    String query = "INSERT INTO test1 (name, student_id, email, program_level, program_name, session) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setString(2, studentID);
                    stmt.setString(3, email);
                    stmt.setString(4, programLevel);
                    stmt.setString(5, programName);
                    stmt.setString(6, session);
                    int rowsInserted = stmt.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data insertion failed!");
                    }

                    connection.close();

                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Database driver not found: " + ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }

            
            }
        });

        // View button guna action listener
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText(""); // Clear the output area
                try {
                    // Establish database connection
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, username, password);

                    // Retrieve data from database
                    String query = "SELECT * FROM test1";
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    // Display data kat output area
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String studentID = rs.getString("student_id");
                        String email = rs.getString("email");
                        String programLevel = rs.getString("program_level");
                        String programName = rs.getString("program_name");
                        String session = rs.getString("session");

                        txtOutput.append("***************************************************" +
                                         "\nName: " + name + 
                                         "\nEmail: " + email + 
                                         "\nStudent ID: " + studentID +
                                         "\nProgram Level: " + programLevel +
                                         "\nProgram Name: " + programName +
                                         "\nProgram Session: " + session + 
                                         "\n*************************************************\n\n");
                    }

                    connection.close();

                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Database driver not found: " + ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtname.setText("");
                txtID.setText("");
                txtEmail.setText("");
                cboProgramLevel.setSelectedIndex(0);
                cboProgramName.setSelectedIndex(0);
                cboSession.setSelectedIndex(0);
                txtOutput.setText("");
            }
        });
        
        setSize(500, 600);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        TestPT1 Testpt1 = new TestPT1();
    }

    // --- unutuk select program  Degree/Diploma --- //
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (cboProgramLevel.getSelectedItem().equals("Diploma")) {
                cboProgramName.removeAllItems();
                for (String item : diploma) {
                    cboProgramName.addItem(item);
                }
            } else if (cboProgramLevel.getSelectedItem().equals("Degree")) {
                cboProgramName.removeAllItems();
                for (String item : degree) {
                    cboProgramName.addItem(item);
                }
            } else {
                cboProgramName.removeAllItems();
                cboProgramName.addItem("--Please Choose--");
            }
        }
    }
}
