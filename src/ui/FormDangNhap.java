package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FormDangNhap extends JFrame implements MouseListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JPanel pnCneter;
    JMenu mnTrangChu, mnThoat, mnNhanXe, mnTraXe, mnDangKy, mnKhuVuc, mnThongKe;
    JMenuItem mnThongKeLuotGui,mnThongKeLuotDangKy;
        JPanel pnLogin;
        JPasswordField txtPassword;
JTextField txtusername;
    public FormDangNhap(){
        doShow();
        createLoginForm();
    }
    public void doShow(){
        setSize(1400,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quản Lý Bãi Giữ Xe");

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        //Menu chương trình
        Font ftmn = new Font("arial",Font.BOLD,16);
        

     

       


        //PnCneter
        pnCneter = new JPanel();
        JPanel pnCenterN = new JPanel();
        JPanel pnCenterC = new JPanel();
        pnCneter.setLayout(new BorderLayout());
        JLabel lblTieuDe = new JLabel("Đăng nhập");
        lblTieuDe.setFont(new Font("arial",Font.BOLD,24));
        lblTieuDe.setForeground(Color.RED);
        pnCenterN.setPreferredSize(new Dimension(1000,80));

        JLabel lbImage = new JLabel();
        lbImage.setIcon(new ImageIcon(getClass().getResource("/icons/background.png")));

        pnCenterN.add(lblTieuDe);
        pnCenterC.add(lbImage);
        pnCneter.add(pnCenterN,BorderLayout.NORTH);
        pnCneter.add(pnCenterC,BorderLayout.CENTER);

        //pnSouth
        JPanel pnSouth = new JPanel();

        cp.add(pnCneter,BorderLayout.CENTER);
        cp.add(pnSouth,BorderLayout.SOUTH);
        pnCneter.setForeground(Color.decode("#CCCCCC"));

        //Event
      

    }

   private void createLoginForm() {
    pnLogin = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    pnLogin.setBorder(BorderFactory.createEmptyBorder(50, 300, 200, 300));
    txtusername = new JTextField();
    txtPassword = new JPasswordField();
    JLabel lblUsername = new JLabel("Username:");
    JLabel lblPassword = new JLabel("Password:");
    JButton btnLogin = new JButton("Login");

    // Set preferred size for text fields
    txtusername.setPreferredSize(new Dimension(300, 30));
    txtPassword.setPreferredSize(new Dimension(300, 30));
    pnLogin.add(Box.createRigidArea(new Dimension(0, 30))); 

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.EAST;
    pnLogin.add(lblUsername, gbc);

    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.WEST;
    pnLogin.add(txtusername, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    pnLogin.add(lblPassword, gbc);

    gbc.gridx = 1;
    pnLogin.add(txtPassword, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    pnLogin.add(btnLogin, gbc);

    btnLogin.addActionListener(this);

    // Center the login form on the screen
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(pnLogin, BorderLayout.CENTER);

    pnCneter.add(centerPanel, BorderLayout.SOUTH);
}




    

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(pnLogin.getComponent(5))) {
             String username = txtusername.getText().trim();
             char[] password = txtPassword.getPassword();
            // Implement your login logic here
            // For example, compare with hardcoded values or check against a database
            if (username.equals("admin") && Arrays.equals(password, "admin".toCharArray())) {
                // Successful login, show main program
                // For example:
               new Form_GiaoDienChinh().setVisible(true);
                dispose();
            } else {
                // Failed login, show an error message
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
