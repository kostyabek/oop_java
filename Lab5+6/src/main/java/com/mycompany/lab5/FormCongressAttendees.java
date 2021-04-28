package com.mycompany.lab5;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import src.DAOs.DAOCongDateParticipant;
import src.DAOs.DAOParticipants;
import src.DateParticipant;
import src.DateTimeUtilities;
import src.Participant;
import src.Regions;

public class FormCongressAttendees extends javax.swing.JPanel {

    public FormCongressAttendees() {
        initComponents();

        connectToTheDatabase();

        checkFindFormInput(); //calling to add all participants to the table

        DocumentListener searchFormListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                checkFindFormInput();
            }

            public void removeUpdate(DocumentEvent e) {
                checkFindFormInput();
            }

            public void changedUpdate(DocumentEvent e) {
                //Plain text components do not fire these events
            }
        };

        fieldCriteria.getDocument().addDocumentListener(searchFormListener);

        Comparator<Calendar> comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Calendar c1 = DateTimeUtilities.convertStringDateToCalendar((String) o1);
                Calendar c2 = DateTimeUtilities.convertStringDateToCalendar((String) o2);
                return c1.compareTo(c2);
            }
        };
        TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(attendeesTable.getModel());
        tableRowSorter.setComparator(0, comparator);
        attendeesTable.setRowSorter(tableRowSorter);
    }

    private void connectToTheDatabase() {
        try {
            daoCongDateParticipant.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoParticipants.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while connecting to the database!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void checkFindFormInput() {
        DefaultTableModel tableModel = (DefaultTableModel) attendeesTable.getModel();

        int numOfRows = tableModel.getRowCount();
        for (int i = numOfRows - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        String input = fieldCriteria.getText();
        String field = (String) criteriaFieldChooser.getSelectedItem();

        List<DateParticipant> participants = new ArrayList<>();
        try {
            participants = daoCongDateParticipant.getByCriteria(input, field);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while processing input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        addDataToTable(participants);
    }

    private void addDataToTable(List<DateParticipant> entities) {
        DefaultTableModel tableModel = (DefaultTableModel) attendeesTable.getModel();

        if (!entities.isEmpty()) {
            int columnsQty = attendeesTable.getColumnCount();
            String[] stringData = new String[columnsQty];

            for (int i = 0; i < entities.size(); i++) {
                Participant participant = entities.get(i).getParticipant();
                stringData[0] = entities.get(i).getDateString();
                stringData[1] = participant.getId();
                stringData[2] = participant.firstName;
                stringData[3] = participant.lastName;
                stringData[4] = participant.patronymic;
                stringData[5] = participant.region.name();

                tableModel.addRow(stringData);
            }
        }
    }

//    private Map<String, List<Participant>> formAttendeesMap() {
//        Map<String, List<Participant>> attendees = new HashMap<>();
//        List<DateParticipant> attendeesDateAndId = new ArrayList<>();
//        
//        try {
//            attendeesDateAndId = daoCongDateParticipant.getAll();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            JOptionPane.showMessageDialog(this,
//                    "Error while fetching congress attendees list!",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//        
//        List<Participant> participants = fetchAllParticipants();
//        for (DateParticipant item : attendeesDateAndId) {
//            for (Participant participant : participants) {
//                if (item.getParticipant().getId().equals(participant.getId())) {
//                    attendees.putIfAbsent(item.getDate(), new ArrayList<>()).add(participant);
//                }
//            }
//        }
//        
//        return attendees;
//    }
    private List<Participant> fetchAllParticipants() {
        List<Participant> participants = new ArrayList<>();
        try {
            participants = daoParticipants.getAll();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error while fetching all participants!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return participants;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        fieldCriteria = new javax.swing.JTextField();
        criteriaFieldChooser = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendeesTable = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(102, 204, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Find in attendees list");

        fieldCriteria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        criteriaFieldChooser.setBackground(java.awt.Color.cyan);
        criteriaFieldChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        criteriaFieldChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "dateTime", "participantId", "firstName", "lastName", "patronymic", "region", "netWorth" }));

        attendeesTable.setBackground(java.awt.Color.cyan);
        attendeesTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        attendeesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "dateTime", "participantId", "firstName", "lastName", "patronymic", "region"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendeesTable.setGridColor(new java.awt.Color(51, 204, 255));
        jScrollPane2.setViewportView(attendeesTable);

        btnRefresh.setBackground(java.awt.Color.cyan);
        btnRefresh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRefreshMouseExited(evt);
            }
        });
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(criteriaFieldChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(199, 199, 199)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(criteriaFieldChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        List<DateParticipant> records = fetchAllCongressRecords();

        List<DateParticipant> newRecords = getNewRecordsFromFetchedList(records);

        clearTable();
        addDataToTable(newRecords);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseEntered
        evt.getComponent().setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnRefreshMouseEntered

    private void btnRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseExited
        evt.getComponent().setBackground(Color.CYAN);
    }//GEN-LAST:event_btnRefreshMouseExited

    private List<DateParticipant> fetchAllCongressRecords() {
        List<DateParticipant> records = new ArrayList<>();
        try {
            records = daoCongDateParticipant.getAll();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while fetching records list from database!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }
        return records;
    }

    private List<DateParticipant> getNewRecordsFromFetchedList(List<DateParticipant> listDatabase) {
        List<DateParticipant> listTable = getRecordsFromTable();
        listTable.addAll(listDatabase);
        return new HashSet(listTable).stream().toList();
//        return listTable.stream().filter(distinctByKey(e -> Arrays.asList(e.getDate(), e.getParticipant().getId()))).toList();
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new HashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private List<DateParticipant> getRecordsFromTable() {
        DefaultTableModel tableModel = (DefaultTableModel) attendeesTable.getModel();
        List<DateParticipant> records = new ArrayList<>();

        int rowsQty = tableModel.getRowCount();
        int colsQty = tableModel.getColumnCount();

        String[] data = new String[colsQty];

        for (int i = 0; i < rowsQty; i++) {
            for (int j = 0; j < colsQty; j++) {
                data[j] = (String) tableModel.getValueAt(i, j);
            }
            String dateTimeString = data[0];
            String id = data[1];
            String firstName = data[2];
            String lastName = data[3];
            String patronymic = data[4];
            Regions region = Regions.valueOf(data[5]);

            Calendar dateTime = DateTimeUtilities.convertStringDateToCalendar(dateTimeString);

            records.add(new DateParticipant(dateTime,
                    new Participant(firstName, lastName, patronymic, region, id)));
        }

        return records;
    }

    private void clearTable() {
        DefaultTableModel tableModel = (DefaultTableModel) attendeesTable.getModel();

        int rowsQty = tableModel.getRowCount();
        for (int i = rowsQty - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private DAOCongDateParticipant daoCongDateParticipant = new DAOCongDateParticipant();
    private DAOParticipants daoParticipants = new DAOParticipants();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attendeesTable;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> criteriaFieldChooser;
    private javax.swing.JTextField fieldCriteria;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
