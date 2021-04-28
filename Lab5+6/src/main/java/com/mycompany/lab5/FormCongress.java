package com.mycompany.lab5;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import src.DAOs.DAOCongresses;
import src.DAOs.DAOStagedParticipants;
import src.Participant;
import src.DAOs.DAOCongDateParticipant;
import src.DAOs.DAOElections;
import src.DAOs.DAOParticipants;
import src.DateParticipant;
import src.DAOs.DAOPositions;
import src.DateTimeUtilities;
import src.ParticipantVote;
import src.PositionParticipant;

public class FormCongress extends javax.swing.JPanel {

    public FormCongress() {
        initComponents();
        connectToTheDatabase();
        checkFindFormInput(); //calling to add all participants to the table

        positionChooser.setVisible(false);

        checkboxElection.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    positionChooser.setVisible(true);
                } else {
                    positionChooser.setVisible(false);
                }
            }
        });

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

        congressTable.setAutoCreateRowSorter(true);
    }

    private void connectToTheDatabase() {
        try {
            daoStagedParticipants.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoCongresses.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoCongDateParticipant.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoParticipants.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoPositions.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");
            daoElections.setConnection("jdbc:sqlite:E:\\NetBeans Java Projects\\Lab5\\database\\politicalPower.db");

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
        DefaultTableModel tableModel = (DefaultTableModel) congressTable.getModel();

        int numOfRows = tableModel.getRowCount();
        for (int i = numOfRows - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }

        String input = fieldCriteria.getText();
        String field = (String) criteriaFieldChooser.getSelectedItem();

        List<Participant> participants = new ArrayList<>();
        try {
            participants = daoStagedParticipants.getByCriteria(input, field);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while processing input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        addDataToTable(participants, tableModel);
    }

    public void addToStaged(List<Participant> participants) {
        try {
            for (Participant participant : participants) {
                daoStagedParticipants.create(participant);
            }
            addDataToTable(participants, (DefaultTableModel) congressTable.getModel());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while staging the participant!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDataToTable(List<Participant> participants, DefaultTableModel tableModel) {
        if (!participants.isEmpty()) {
            int columnsQty = congressTable.getColumnCount();
            String[] stringData = new String[columnsQty];

            for (int i = 0; i < participants.size(); i++) {
                stringData[0] = participants.get(i).getId();
                stringData[1] = participants.get(i).firstName;
                stringData[2] = participants.get(i).lastName;
                stringData[3] = participants.get(i).patronymic;
                stringData[4] = participants.get(i).region.name();

                tableModel.addRow(stringData);
            }
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        congressTable = new javax.swing.JTable();
        btnClearList = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        fieldCriteria = new javax.swing.JTextField();
        criteriaFieldChooser = new javax.swing.JComboBox<>();
        btnConduct = new javax.swing.JButton();
        checkboxElection = new javax.swing.JCheckBox();
        positionChooser = new javax.swing.JComboBox<>();

        jToggleButton1.setText("jToggleButton1");

        setBackground(new java.awt.Color(102, 204, 255));
        setPreferredSize(new java.awt.Dimension(640, 640));

        congressTable.setBackground(java.awt.Color.cyan);
        congressTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        congressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "firstName", "lastName", "patronymic", "region"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        congressTable.setGridColor(new java.awt.Color(51, 204, 255));
        jScrollPane2.setViewportView(congressTable);

        btnClearList.setBackground(java.awt.Color.cyan);
        btnClearList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClearList.setText("Remove from the list");
        btnClearList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClearListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClearListMouseExited(evt);
            }
        });
        btnClearList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearListActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Find in congress list");

        fieldCriteria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        criteriaFieldChooser.setBackground(java.awt.Color.cyan);
        criteriaFieldChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        criteriaFieldChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "id", "firstName", "lastName", "patronymic", "region", "netWorth" }));

        btnConduct.setBackground(java.awt.Color.cyan);
        btnConduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConduct.setText("Conduct");
        btnConduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConductMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConductMouseExited(evt);
            }
        });
        btnConduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConductActionPerformed(evt);
            }
        });

        checkboxElection.setBackground(java.awt.Color.cyan);
        checkboxElection.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkboxElection.setText("Election");

        positionChooser.setBackground(java.awt.Color.cyan);
        positionChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        positionChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "leader", "secretary", "treasurer", "presiding" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(criteriaFieldChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClearList, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConduct, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(checkboxElection)
                        .addGap(18, 18, 18)
                        .addComponent(positionChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearList)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(criteriaFieldChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConduct)
                    .addComponent(checkboxElection)
                    .addComponent(positionChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearListActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) congressTable.getModel();

        int[] selectedIndices = reverseSelectedIndices(congressTable.getSelectedRows());

        for (int index : selectedIndices) {
            String participantId = (String) tableModel.getValueAt(index, 0);

            try {
                daoStagedParticipants.delete(participantId);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        String.format("Error while unstaging participant with id %s!", participantId),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            tableModel.removeRow(index);
        }
    }//GEN-LAST:event_btnClearListActionPerformed

    private int[] reverseSelectedIndices(int[] selectedIndices) {
        for (int i = 0; i < selectedIndices.length / 2; i++) {
            int temp = selectedIndices[i];
            selectedIndices[i] = selectedIndices[selectedIndices.length - i - 1];
            selectedIndices[selectedIndices.length - i - 1] = temp;
        }
        return selectedIndices;
    }

    private void btnConductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConductActionPerformed
        Calendar dateAndTime = DateTimeUtilities.getCurrentDateAndTime();
        String dateAndTimeString = DateTimeUtilities.getDateAndTimeString(dateAndTime);

        List<Participant> stagedParticipants = fetchStagedParticipantsList();
        if (!stagedParticipantsListIsNotEmpty(stagedParticipants)) {
            return;
        }

        if (checkIfElection()) {
            String position = getElectionPosition();

            List<Participant> allParticipants = fetchAllParticipants();

            List<ParticipantVote> participantVotes = makeParticipantsVote(allParticipants, stagedParticipants, dateAndTime);

            Map<String, Integer> votesPerParticipant = new HashMap<>();
            getVotesPerParticipant(votesPerParticipant, participantVotes);

            if (!createVotesDatabaseRecords(participantVotes)) {
                revertVotes(dateAndTimeString);
                return;
            }

            String winnerId = getElectionWinner(votesPerParticipant);
            updatePositionInDatabase(position, winnerId);
        }

        createCongressDatabaseRecord(dateAndTimeString);
        createCongDateParticipantDatabaseRecords(stagedParticipants, dateAndTime);

        deleteFromStagedParticipantsDatabaseTable(stagedParticipants);
        deleteAllFromParticipantsApplicationTable();
    }//GEN-LAST:event_btnConductActionPerformed

    private void btnClearListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearListMouseEntered
        evt.getComponent().setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnClearListMouseEntered

    private void btnClearListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearListMouseExited
        evt.getComponent().setBackground(Color.CYAN);
    }//GEN-LAST:event_btnClearListMouseExited

    private void btnConductMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConductMouseEntered
        evt.getComponent().setBackground(Color.YELLOW);
    }//GEN-LAST:event_btnConductMouseEntered

    private void btnConductMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConductMouseExited
        evt.getComponent().setBackground(Color.CYAN);
    }//GEN-LAST:event_btnConductMouseExited

    private List<Participant> fetchStagedParticipantsList() {
        List<Participant> stagedParticipants = new ArrayList<>();

        try {
            stagedParticipants = daoStagedParticipants.getAll();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error while fetching staged participants!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return stagedParticipants;
    }

    private boolean stagedParticipantsListIsNotEmpty(List<Participant> stagedParticipants) {
        if (stagedParticipants.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot conduct congress with no participants!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean createCongressDatabaseRecord(String dateAndTime) {
        try {
            daoCongresses.create(dateAndTime);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error while creating congress record!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private List<Participant> createCongDateParticipantDatabaseRecords(List<Participant> stagedParticipants, Calendar dateAndTime) {
        List<Participant> addedParticipants = new ArrayList<>();
        for (Participant participant : stagedParticipants) {
            try {
                daoCongDateParticipant.create(new DateParticipant(dateAndTime, participant));
                addedParticipants.add(participant);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        String.format("Error while adding %s to the table!", participant.getId()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return addedParticipants;
    }

    private boolean checkIfElection() {
        if (checkboxElection.isSelected()) {
            return true;
        }
        return false;
    }

    private String getElectionPosition() {
        return (String) positionChooser.getSelectedItem();
    }

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

    private List<ParticipantVote> makeParticipantsVote(List<Participant> participants, List<Participant> candidates, Calendar dateAndTime) {
        List<ParticipantVote> participantVotes = new ArrayList<>();
        Random random = new Random();
        for (Participant participant : participants) {
            int votedForIndex = 0;
            Participant votedFor = null;
            do {
                votedForIndex = random.nextInt(candidates.size() - 1);
                votedFor = candidates.get(votedForIndex);
            } while (candidates.get(votedForIndex).getId().equals(participant.getId()));
                
            participantVotes.add(new ParticipantVote(
                    dateAndTime,
                    participant.getId(),
                    votedFor.getId()));
        }
        return participantVotes;
    }

    private void getVotesPerParticipant(Map<String, Integer> votesPerParticipant, List<ParticipantVote> participantVotes) {
        for (ParticipantVote participantVote : participantVotes) {
            String candidateId = participantVote.getVotedForId();

            int votesAmount = votesPerParticipant.containsKey(candidateId) ? votesPerParticipant.get(candidateId) : 0;
            votesPerParticipant.put(candidateId, votesAmount + 1);
        }
    }

    private boolean createVotesDatabaseRecords(List<ParticipantVote> participantVotes) {
        for (ParticipantVote participantVote : participantVotes) {
            try {
                daoElections.create(participantVote);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        String.format("Error while adding %s vote to the table!", participantVote.getVoterId()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean revertVotes(String dateAndTime) {
        try {
            daoElections.delete(dateAndTime);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,
                    String.format("Error while deleting votes from the table!"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String getElectionWinner(Map<String, Integer> votesPerParticipant) {
        int maxVotes = 0;
        String winnerId = "";
        for (String candidateId : votesPerParticipant.keySet()) {
            int votesForCurrentCandidate = votesPerParticipant.get(candidateId);
            if (votesForCurrentCandidate > maxVotes) {
                winnerId = candidateId;
                maxVotes = votesForCurrentCandidate;
            }
        }
        return winnerId;
    }

    private boolean updatePositionInDatabase(String position, String winnerId) {
        try {
            daoPositions.update(new PositionParticipant(position, winnerId));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    String.format("Error while updating positions table!"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean deleteFromStagedParticipantsDatabaseTable(List<Participant> stagedParticipants) {
        for (Participant participant : stagedParticipants) {
            try {
                daoStagedParticipants.delete(participant.getId());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        String.format("Error while deleting %s from database table!", participant.getId()),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void deleteAllFromParticipantsApplicationTable() {
        DefaultTableModel tableModel = (DefaultTableModel) congressTable.getModel();

        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private DAOParticipants daoParticipants = new DAOParticipants();
    private DAOStagedParticipants daoStagedParticipants = new DAOStagedParticipants();
    private DAOCongresses daoCongresses = new DAOCongresses();
    private DAOCongDateParticipant daoCongDateParticipant = new DAOCongDateParticipant();
    private DAOPositions daoPositions = new DAOPositions();
    private DAOElections daoElections = new DAOElections();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearList;
    private javax.swing.JButton btnConduct;
    private javax.swing.JCheckBox checkboxElection;
    public javax.swing.JTable congressTable;
    private javax.swing.JComboBox<String> criteriaFieldChooser;
    private javax.swing.JTextField fieldCriteria;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox<String> positionChooser;
    // End of variables declaration//GEN-END:variables
}
