package test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class phuong2 extends JFrame {
	private static final long serialVersionUID = 1L;
    // Các thành phần giao diện
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextArea txtAddress;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;
    private JProgressBar progressBar;
    
    // Dữ liệu
    private List<Student> studentList;
    private int currentId = 1;
    
    // Constructor
    public phuong2() {
        setTitle("Quản Lý Sinh Viên - Phiên bản nâng cao");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setLocationRelativeTo(null);
        
        // Khởi tạo dữ liệu
        studentList = new ArrayList<>();
        
        // Tạo giao diện
        createComponents();
        setupLayout();
        setupEventHandlers();
        loadSampleData();
        
        // Icon cho ứng dụng (nếu có file icon)
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Bỏ qua nếu không có icon
        }
    }
    
    private void createComponents() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);
        
        // Panel tiêu đề
        JPanel titlePanel = createTitlePanel();
        
        // Panel nhập liệu
        JPanel inputPanel = createInputPanel();
        
        // Panel bảng dữ liệu
        JPanel tablePanel = createTablePanel();
        
        // Panel nút điều khiển
        JPanel buttonPanel = createButtonPanel();
        
        // Panel trạng thái
        JPanel statusPanel = createStatusPanel();
        
        // Thêm vào content pane
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(inputPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.EAST);
        contentPane.add(statusPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ SINH VIÊN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(titleLabel);
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Thông tin sinh viên"));
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(300, 400));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtName = new JTextField(20);
        txtName.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtName, gbc);
        
        // Tuổi
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Tuổi:"), gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtAge = new JTextField(20);
        txtAge.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtAge, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPhone = new JTextField(20);
        txtPhone.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtPhone, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEmail = new JTextField(20);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtEmail, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.BOTH;
        txtAddress = new JTextArea(3, 20);
        txtAddress.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        panel.add(scrollAddress, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Danh sách sinh viên"));
        
        // Tạo bảng
        String[] columnNames = {"ID", "Họ tên", "Tuổi", "Số ĐT", "Email", "Địa chỉ", "Ngày tạo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
        	private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Thiết lập độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Họ tên
        table.getColumnModel().getColumn(2).setPreferredWidth(50);  // Tuổi
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // SĐT
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // Email
        table.getColumnModel().getColumn(5).setPreferredWidth(200); // Địa chỉ
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Ngày tạo
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Chức năng"));
        panel.setLayout(new GridLayout(8, 1, 5, 5));
        panel.setPreferredSize(new Dimension(120, 400));
        
        // Tạo các nút
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnExport = new JButton("Xuất file");
        JButton btnImport = new JButton("Nhập file");
        JButton btnExit = new JButton("Thoát");
        
        // Thiết lập màu sắc và font
        btnAdd.setBackground(new Color(46, 125, 50));
        btnAdd.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(255, 193, 7));
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        btnExit.setBackground(new Color(158, 158, 158));
        
        Font buttonFont = new Font("Arial", Font.BOLD, 11);
        btnAdd.setFont(buttonFont);
        btnUpdate.setFont(buttonFont);
        btnDelete.setFont(buttonFont);
        btnClear.setFont(buttonFont);
        btnSearch.setFont(buttonFont);
        btnExport.setFont(buttonFont);
        btnImport.setFont(buttonFont);
        btnExit.setFont(buttonFont);
        
        // Thêm vào panel
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnSearch);
        panel.add(btnExport);
        panel.add(btnImport);
        panel.add(btnExit);
        
        return panel;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        lblStatus = new JLabel("Sẵn sàng");
        lblStatus.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setPreferredSize(new Dimension(200, 20));
        
        JLabel lblTime = new JLabel();
        lblTime.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        // Timer để cập nhật thời gian
        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            lblTime.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        });
        timer.start();
        
        panel.add(lblStatus, BorderLayout.WEST);
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(lblTime, BorderLayout.EAST);
        
        return panel;
    }
    
    private void setupLayout() {
        // Layout đã được thiết lập trong createComponents()
    }
    
    private void setupEventHandlers() {
        // Event cho bảng - khi chọn một dòng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    loadDataToForm(selectedRow);
                }
            }
        });
        
        // Event cho các nút
        Component[] buttons = ((JPanel)contentPane.getComponent(3)).getComponents();
        
        // Nút Thêm
        ((JButton)buttons[0]).addActionListener(e -> addStudent());
        
        // Nút Sửa
        ((JButton)buttons[1]).addActionListener(e -> updateStudent());
        
        // Nút Xóa
        ((JButton)buttons[2]).addActionListener(e -> deleteStudent());
        
        // Nút Làm mới
        ((JButton)buttons[3]).addActionListener(e -> clearForm());
        
        // Nút Tìm kiếm
        ((JButton)buttons[4]).addActionListener(e -> searchStudent());
        
        // Nút Xuất file
        ((JButton)buttons[5]).addActionListener(e -> exportToFile());
        
        // Nút Nhập file
        ((JButton)buttons[6]).addActionListener(e -> importFromFile());
        
        // Nút Thoát
        ((JButton)buttons[7]).addActionListener(e -> exitApplication());
        
        // Event cho window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
        
        // Phím tắt
        setupKeyBindings();
    }
    
    private void setupKeyBindings() {
        // Ctrl+N: Thêm mới
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ctrl N"), "addNew");
        getRootPane().getActionMap().put("addNew", new AbstractAction() {
        	private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        
        // Ctrl+S: Lưu/Cập nhật
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ctrl S"), "save");
        getRootPane().getActionMap().put("save", new AbstractAction() {
        	private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    updateStudent();
                } else {
                    addStudent();
                }
            }
        });
        
        // Delete: Xóa
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("DELETE"), "delete");
        getRootPane().getActionMap().put("delete", new AbstractAction() {
        	private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        
        // F5: Làm mới
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("F5"), "refresh");
        getRootPane().getActionMap().put("refresh", new AbstractAction() {
        	private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                refreshTable();
            }
        });
    }
    
    private void loadSampleData() {
        studentList.add(new Student(currentId++, "Nguyễn Văn An", 20, "0123456789", "an@email.com", "Hà Nội"));
        studentList.add(new Student(currentId++, "Trần Thị Bình", 21, "0987654321", "binh@email.com", "TP.HCM"));
        studentList.add(new Student(currentId++, "Lê Văn Cường", 22, "0369258147", "cuong@email.com", "Đà Nẵng"));
        refreshTable();
        updateStatus("Đã tải " + studentList.size() + " sinh viên mẫu");
    }
    
    private void addStudent() {
        if (validateInput()) {
            Student student = new Student(
                currentId++,
                txtName.getText().trim(),
                Integer.parseInt(txtAge.getText().trim()),
                txtPhone.getText().trim(),
                txtEmail.getText().trim(),
                txtAddress.getText().trim()
            );
            
            studentList.add(student);
            refreshTable();
            clearForm();
            updateStatus("Đã thêm sinh viên: " + student.getName());
            
            // Animation cho progress bar
            animateProgressBar("Đang thêm...", 100);
        }
    }
    
    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0 && validateInput()) {
            Student student = studentList.get(selectedRow);
            student.setName(txtName.getText().trim());
            student.setAge(Integer.parseInt(txtAge.getText().trim()));
            student.setPhone(txtPhone.getText().trim());
            student.setEmail(txtEmail.getText().trim());
            student.setAddress(txtAddress.getText().trim());
            
            refreshTable();
            updateStatus("Đã cập nhật sinh viên: " + student.getName());
            
            animateProgressBar("Đang cập nhật...", 100);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn sinh viên cần cập nhật!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Student student = studentList.get(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sinh viên: " + student.getName() + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                studentList.remove(selectedRow);
                refreshTable();
                clearForm();
                updateStatus("Đã xóa sinh viên: " + student.getName());
                
                animateProgressBar("Đang xóa...", 100);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn sinh viên cần xóa!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        table.clearSelection();
        updateStatus("Đã làm mới form");
    }
    
    private void searchStudent() {
        String keyword = JOptionPane.showInputDialog(this, 
            "Nhập từ khóa tìm kiếm (tên, email, số điện thoại):", 
            "Tìm kiếm", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.toLowerCase().trim();
            List<Student> searchResults = new ArrayList<>();
            
            for (Student student : studentList) {
                if (student.getName().toLowerCase().contains(keyword) ||
                    student.getEmail().toLowerCase().contains(keyword) ||
                    student.getPhone().contains(keyword)) {
                    searchResults.add(student);
                }
            }
            
            // Hiển thị kết quả tìm kiếm
            showSearchResults(searchResults, keyword);
        }
    }
    
    private void showSearchResults(List<Student> results, String keyword) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy sinh viên nào với từ khóa: " + keyword, 
                "Kết quả tìm kiếm", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Tìm thấy ").append(results.size()).append(" sinh viên:\n\n");
            
            for (Student student : results) {
                sb.append("- ").append(student.getName())
                  .append(" (").append(student.getEmail()).append(")\n");
            }
            
            JOptionPane.showMessageDialog(this, sb.toString(), 
                "Kết quả tìm kiếm", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        updateStatus("Tìm thấy " + results.size() + " kết quả cho: " + keyword);
    }
    
    private void exportToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Xuất dữ liệu ra file");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("DANH SÁCH SINH VIÊN");
                writer.println("===================");
                writer.println();
                
                for (Student student : studentList) {
                    writer.println("ID: " + student.getId());
                    writer.println("Họ tên: " + student.getName());
                    writer.println("Tuổi: " + student.getAge());
                    writer.println("Số điện thoại: " + student.getPhone());
                    writer.println("Email: " + student.getEmail());
                    writer.println("Địa chỉ: " + student.getAddress());
                    writer.println("Ngày tạo: " + student.getCreatedDate().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    writer.println("------------------------");
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Xuất file thành công!\nĐường dẫn: " + file.getAbsolutePath(), 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                updateStatus("Đã xuất " + studentList.size() + " sinh viên ra file");
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xuất file: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void importFromFile() {
        JOptionPane.showMessageDialog(this, 
            "Tính năng nhập file đang được phát triển!", 
            "Thông báo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn thoát ứng dụng?",
            "Xác nhận thoát",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            updateStatus("Đang thoát ứng dụng...");
            System.exit(0);
        }
    }
    
    private boolean validateInput() {
        // Validate họ tên
        if (txtName.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập họ tên!");
            txtName.requestFocus();
            return false;
        }
        
        // Validate tuổi
        try {
            int age = Integer.parseInt(txtAge.getText().trim());
            if (age < 16 || age > 100) {
                showValidationError("Tuổi phải từ 16 đến 100!");
                txtAge.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showValidationError("Tuổi phải là số nguyên!");
            txtAge.requestFocus();
            return false;
        }
        
        // Validate số điện thoại
        String phone = txtPhone.getText().trim();
        if (phone.isEmpty() || !phone.matches("\\d{10,11}")) {
            showValidationError("Số điện thoại phải có 10-11 chữ số!");
            txtPhone.requestFocus();
            return false;
        }
        
        // Validate email
        String email = txtEmail.getText().trim();
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            showValidationError("Email không hợp lệ!");
            txtEmail.requestFocus();
            return false;
        }
        
        // Validate địa chỉ
        if (txtAddress.getText().trim().isEmpty()) {
            showValidationError("Vui lòng nhập địa chỉ!");
            txtAddress.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
    }
    
    private void loadDataToForm(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < studentList.size()) {
            Student student = studentList.get(rowIndex);
            txtName.setText(student.getName());
            txtAge.setText(String.valueOf(student.getAge()));
            txtPhone.setText(student.getPhone());
            txtEmail.setText(student.getEmail());
            txtAddress.setText(student.getAddress());
            
            updateStatus("Đã chọn: " + student.getName());
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student student : studentList) {
            Object[] row = {
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                student.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateStatus(String message) {
        lblStatus.setText(message);
    }
    
    private void animateProgressBar(String message, int maxValue) {
        progressBar.setString(message);
        progressBar.setValue(0);
        
        Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener() {
            int value = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                value += 10;
                progressBar.setValue(value);
                
                if (value >= maxValue) {
                    timer.stop();
                    progressBar.setValue(0);
                    progressBar.setString("");
                }
            }
        });
        timer.start();
    }
    
    // Main method
    public static void main(String[] args) {
        // Thiết lập Look and Feel
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Chạy ứng dụng trên EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                phuong2 frame = new phuong2();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

// Class Student để quản lý dữ liệu sinh viên
class Student {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdDate;
    
    public Student(int id, String name, int age, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", createdDate=" + createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                '}';
    }
}

