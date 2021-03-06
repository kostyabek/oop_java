package com.mycompany.lab5;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import src.DAOs.DAOMonthlyIncome;
import src.DAOs.DAOParticipants;
import src.DateParticipant;
import src.Participant;

public class FormMonthlyIncome extends javax.swing.JPanel {

    public FormMonthlyIncome() {
        initComponents();
        connectToTheDatabase();

        DocumentListener incomeFieldListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                checkIncomeInput();
            }

            public void removeUpdate(DocumentEvent e) {
                checkIncomeInput();
            }

            public void changedUpdate(DocumentEvent e) {
                //Plain text components do not fire these events
            }
        };
        fieldIncomeToDeclare.getDocument().addDocumentListener(incomeFieldListener);
    }

    private void connectToTheDatabase() {
        try {
            daoMonthlyIncome.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoParticipants.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while connecting to the database!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkIncomeInput() {
        if (!fieldIncomeToDeclare.getText().matches("[0-9]*[.][0-9]{2}")) {
            fieldIncomeToDeclare.setBackground(Color.red);
            return false;
        } else {
            fieldIncomeToDeclare.setBackground(Color.white);
            return true;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDeclare = new javax.swing.JButton();
        fieldIncomeToDeclare = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        fieldLastName = new javax.swing.JTextField();
        fieldNetWorth = new javax.swing.JTextField();
        fieldId = new javax.swing.JTextField();
        fieldRegion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fieldPatronymic = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fieldFirstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnStartDeclaring = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 204, 255));

        btnDeclare.setBackground(java.awt.Color.cyan);
        btnDeclare.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeclare.setText("Declare");
        btnDeclare.setEnabled(false);
        btnDeclare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeclareMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeclareMouseExited(evt);
            }
        });
        btnDeclare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclareActionPerformed(evt);
            }
        });

        fieldIncomeToDeclare.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        fieldLastName.setEditable(false);
        fieldLastName.setBackground(new java.awt.Color(255, 255, 255));
        fieldLastName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        fieldNetWorth.setEditable(false);
        fieldNetWorth.setBackground(new java.awt.Color(255, 255, 255));
        fieldNetWorth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        fieldId.setEditable(false);
        fieldId.setBackground(new java.awt.Color(255, 255, 255));
        fieldId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        fieldRegion.setEditable(false);
        fieldRegion.setBackground(new java.awt.Color(255, 255, 255));
        fieldRegion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("First Name");

        fieldPatronymic.setEditable(false);
        fieldPatronymic.setBackground(new java.awt.Color(255, 255, 255));
        fieldPatronymic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Patronymic");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Region");

        fieldFirstName.setEditable(false);
        fieldFirstName.setBackground(new java.awt.Color(255, 255, 255));
        fieldFirstName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Net Worth");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(fieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(fieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(fieldPatronymic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(fieldNetWorth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(101, 101, 101))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fieldFirstName, fieldId, fieldLastName, fieldNetWorth, fieldPatronymic, fieldRegion});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(fieldRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldPatronymic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldNetWorth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Income");

        btnStartDeclaring.setBackground(java.awt.Color.cyan);
        btnStartDeclaring.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStartDeclaring.setText("Start declaring");
        btnStartDeclaring.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStartDeclaringMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnStartDeclaringMouseExited(evt);
            }
        });
        btnStartDeclaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDeclaringActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStartDeclaring)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDeclare)
                                .addGap(270, 270, 270))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7)
                                    .addComponent(fieldIncomeToDeclare, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addGap(185, 185, 185)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStartDeclaring)
                .addGap(175, 175, 175)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldIncomeToDeclare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(32, 32, 32)
                .addComponent(btnDeclare)
                .addContainerGap(217, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartDeclaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDeclaringActionPerformed
        try {
            allParticipants = daoParticipants.getAll();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while fetching participants list!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (!allParticipants.isEmpty()) {
            startIncomeDeclaration();
        } else {
            JOptionPane.showMessageDialog(this,
                    "There are no participants yet!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnStartDeclaringActionPerformed

    private void btnDeclareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclareActionPerformed
        if (!checkIncomeInput()) {
            JOptionPane.showMessageDialog(this,
                    "Fill in the fields first!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Participant currentParticipant = allParticipants.get(allParticipants.size() - 1);
        currentParticipant.currentMonthlyIncome = Double.valueOf(fieldIncomeToDeclare.getText());
        currentParticipant.netWorth += currentParticipant.currentMonthlyIncome;

        createDatabaseRecord(currentParticipant);

        allParticipants.remove(allParticipants.size() - 1);

        if (!updateParticipantNetWorth(currentParticipant)) {
            clearFields();
            return;
        }

        if (checkIfNoParticipantsLeft()) {
            return;
        }

        fillInTheFields(allParticipants.get(allParticipants.size() - 1));
    }//GEN-LAST:event_btnDeclareActionPerformed

    private void btnStartDeclaringMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartDeclaringMouseEntered
        evt.getComponent().setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnStartDeclaringMouseEntered

    private void btnStartDeclaringMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStartDeclaringMouseExited
        evt.getComponent().setBackground(Color.CYAN);
    }//GEN-LAST:event_btnStartDeclaringMouseExited

    private void btnDeclareMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeclareMouseEntered
        evt.getComponent().setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnDeclareMouseEntered

    private void btnDeclareMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeclareMouseExited
        evt.getComponent().setBackground(Color.CYAN);
    }//GEN-LAST:event_btnDeclareMouseExited

    private boolean createDatabaseRecord(Participant participant) {
        try {
            daoMonthlyIncome.create(new DateParticipant(calendar, participant));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    String.format("Error while creating %s database record!", participant.getId()),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean updateParticipantNetWorth(Participant participant) {
        try {
            daoParticipants.update(participant);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while updating Net Worth database record!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkIfNoParticipantsLeft() {
        if (allParticipants.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Done!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            btnStartDeclaring.setEnabled(true);
            btnDeclare.setEnabled(false);
            clearFields();

            return true;
        }
        return false;
    }

    private void clearFields() {
        fieldId.setText("");
        fieldFirstName.setText("");
        fieldLastName.setText("");
        fieldPatronymic.setText("");
        fieldRegion.setText("");
        fieldNetWorth.setText("");
        fieldIncomeToDeclare.setText("");
    }

    private void startIncomeDeclaration() {
        calendar = new GregorianCalendar();

        List<DateParticipant> incomeRecords = new ArrayList<>();
        try {
            incomeRecords = daoMonthlyIncome.getAll();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while fetching from the database!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        for (DateParticipant record : incomeRecords) {
            if (record.getDate().get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                JOptionPane.showMessageDialog(this,
                        "Income declaration has already been conducted this month!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        fillInTheFields(allParticipants.get(allParticipants.size() - 1));
        btnStartDeclaring.setEnabled(false);
        btnDeclare.setEnabled(true);
    }

    private void fillInTheFields(Participant participant) {
        fieldId.setText(participant.getId());
        fieldFirstName.setText(participant.firstName);
        fieldLastName.setText(participant.lastName);
        fieldPatronymic.setText(participant.patronymic);
        fieldRegion.setText(participant.region.toString());
        fieldNetWorth.setText(String.valueOf(participant.netWorth));
    }

    private DAOParticipants daoParticipants = new DAOParticipants();
    private DAOMonthlyIncome daoMonthlyIncome = new DAOMonthlyIncome();
    private List<Participant> allParticipants;
    private Calendar calendar;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeclare;
    private javax.swing.JButton btnStartDeclaring;
    private javax.swing.JTextField fieldFirstName;
    private javax.swing.JTextField fieldId;
    private javax.swing.JTextField fieldIncomeToDeclare;
    private javax.swing.JTextField fieldLastName;
    private javax.swing.JTextField fieldNetWorth;
    private javax.swing.JTextField fieldPatronymic;
    private javax.swing.JTextField fieldRegion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
