import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Ex05 extends JFrame implements ActionListener , MouseListener{

	private JButton Save_Button = new JButton("등록");
	private JButton change_button = new JButton("수정");
	private JButton see_button = new JButton("조회");
	private JButton clear_button = new JButton("삭제");
	private JButton name_btn = new JButton("이름순 정렬");
	private JLabel name_label = new JLabel("이름");
	private JLabel kor_label = new JLabel("국어");
	private JLabel eng_label = new JLabel("영어");
	private JLabel math_label = new JLabel("수학");
	private JLabel gen_label = new JLabel("성별");
	private JLabel bigo_label = new JLabel("비고");
	private JLabel benner_label = new JLabel();
	private JTextArea remark_txt = new JTextArea();

	private JTextField name_txt = new JTextField();
	private JTextField kor_txt = new JTextField();
	private JTextField eng_txt = new JTextField();
	private JTextField math_txt = new JTextField();
	private JTextField gender_txt = new JTextField();

	private Image backgroundImage = null;
	private Image screenImage = null;
	private ImageIcon exitImage = null;
	private Image bennerImage = null;
	private Graphics screenGraphics = null;
	String[] col_names = new String[] { "순서", "년도", "학년", "이름", "국어", "영어", "수학", "성별", "비고란" };
	DefaultTableModel dtm = new DefaultTableModel(col_names, 0);
	private JTable jtable = new JTable(dtm);
	private String user = "hr";
	private String pw = "keoera1212";
	private String url = "jdbc:Oracle:thin:@localhost:1521:xe";
	
	private int mouseX, mouseY;
	private JTextField grade_txt;
	private JTextField year_txt;

	//학생관리 프로그램 디자인부분
	Ex05() {
		setTitle("학생관리");
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		getContentPane().setLayout(null);

		backgroundImage = new ImageIcon(Ex05.class.getResource("image.jpg")).getImage();
		exitImage = new ImageIcon(Ex05.class.getResource("exit.jpg"));
		bennerImage = new ImageIcon(Ex05.class.getResource("benner.jpg")).getImage();

		Save_Button.setBounds(12, 410, 132, 23);
		getContentPane().add(Save_Button);

		see_button.setBounds(308, 410, 132, 23);
		getContentPane().add(see_button);

		clear_button.setBounds(454, 408, 132, 23);
		getContentPane().add(clear_button);

		change_button.setBounds(162, 410, 132, 23);
		getContentPane().add(change_button);

		benner_label.setBounds(0, 0, 597, 43);
		getContentPane().add(benner_label);
		name_label.setForeground(Color.WHITE);

		name_label.setBounds(12, 138, 32, 19);
		getContentPane().add(name_label);
		kor_label.setForeground(Color.WHITE);

		kor_label.setBounds(12, 177, 32, 19);
		getContentPane().add(kor_label);
		eng_label.setForeground(Color.WHITE);

		eng_label.setBounds(12, 208, 32, 19);
		getContentPane().add(eng_label);
		math_label.setForeground(Color.WHITE);

		math_label.setBounds(12, 247, 32, 19);
		getContentPane().add(math_label);
		gen_label.setForeground(Color.WHITE);

		gen_label.setBounds(12, 278, 32, 19);
		getContentPane().add(gen_label);
		bigo_label.setForeground(Color.WHITE);

		bigo_label.setBounds(12, 334, 32, 19);
		getContentPane().add(bigo_label);

		name_txt.setBounds(52, 135, 92, 23);
		getContentPane().add(name_txt);
		name_txt.setColumns(10);

		kor_txt.setColumns(10);
		kor_txt.setBounds(52, 170, 92, 23);
		getContentPane().add(kor_txt);

		eng_txt.setColumns(10);
		eng_txt.setBounds(52, 205, 92, 23);
		getContentPane().add(eng_txt);

		math_txt.setColumns(10);
		math_txt.setBounds(52, 240, 92, 23);
		getContentPane().add(math_txt);

		gender_txt.setColumns(10);
		gender_txt.setBounds(52, 275, 92, 23);
		getContentPane().add(gender_txt);

		remark_txt.setBounds(52, 316, 92, 62);
		getContentPane().add(remark_txt);


		JButton Button_1 = new JButton();
		Button_1.setBounds(554, 0, 43, 43);
		benner_label.add(Button_1);
		Button_1.setIcon(exitImage);

		JScrollPane scrollPane = new JScrollPane(jtable);
		scrollPane.setBounds(162, 55, 421, 323);
		getContentPane().add(scrollPane);
		
		grade_txt = new JTextField();
		grade_txt.setColumns(10);
		grade_txt.setBounds(52, 102, 92, 24);
		getContentPane().add(grade_txt);
		
		year_txt = new JTextField();
		year_txt.setColumns(10);
		year_txt.setBounds(52, 66, 92, 24);
		getContentPane().add(year_txt);
		
		JLabel year_label = new JLabel("년도");
		year_label.setForeground(Color.WHITE);
		year_label.setBounds(14, 69, 30, 18);
		getContentPane().add(year_label);
		
		JLabel grade_label = new JLabel("학년");
		grade_label.setForeground(Color.WHITE);
		grade_label.setBounds(14, 108, 30, 18);
		getContentPane().add(grade_label);
		
		name_btn.setBounds(14, 462, 130, 27);
		getContentPane().add(name_btn);
		
		JButton num_jtm = new JButton("성적순 정렬");
		num_jtm.setBounds(162, 462, 130, 27);
		getContentPane().add(num_jtm);
		Button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		setSize(597, 545);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		Save_Button.addActionListener(this);
		see_button.addActionListener(this);
		clear_button.addActionListener(this);
		change_button.addActionListener(this);
		name_btn.addActionListener(this);
        jtable.addMouseListener(this);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 자동 생성된 메소드 스텁
				mouseX = e.getX();
				mouseY = e.getY();

			}

		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO 자동 생성된 메소드 스텁
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
	}

	public static void main(String[] args) {
		new Ex05();
	}

	@Override
	public void paint(Graphics g) {
		screenImage = createImage(597, 545);
		screenGraphics = screenImage.getGraphics();
		screenDraw(screenGraphics);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(bennerImage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

	/*
	 * textField 이름 textField_1 국어 textField_2 영어 textField_3 수학 textField_4 성별
	 * textArea 비고
	 */
	@Override
	//이벤트함수( ex : 버튼클릭 등...) 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(Save_Button)) {
			Student stu = new Student();
			stu.setYear(Integer.parseInt(year_txt.getText()));
			stu.setGrade(Integer.parseInt(grade_txt.getText()));
			stu.setName(name_txt.getText());
			stu.setKor(Integer.parseInt(kor_txt.getText()));
			stu.setEng(Integer.parseInt(eng_txt.getText()));
			stu.setMath(Integer.parseInt(math_txt.getText()));
			stu.setGender(gender_txt.getText());
			stu.setRemark(remark_txt.getText());

			System.out.println(stu.toString());

			// DB연결 객체
			Connection conn = null;
			// Sql 구문 나타내는 객체
			PreparedStatement pstmt = null;
			try {
				String insertSQL = "INSERT INTO STUDENT_INFO" +
				"(STU_INDEX,YEAR,GRADE,NAME,KOR,ENG,MATH,GENDER,REMARK)" +
				"VALUES" +
				"(STUDENT_INFO_KEY.NEXTVAL,? ,?, ?, ?, ?, ?, ?, ?)";
				// class 파일 추가 확인
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pw);
				pstmt = conn.prepareStatement(insertSQL);
				pstmt.setInt(1, stu.getYear());
				pstmt.setInt(2, stu.getGrade());
				pstmt.setString(3, stu.getName());
				pstmt.setInt(4, stu.getKor());
				pstmt.setInt(5, stu.getEng());
				pstmt.setInt(6, stu.getMath());
				pstmt.setString(7, stu.getGender());
				pstmt.setString(8, stu.getRemark());
				pstmt.executeUpdate();
				System.out.println("DB연결 성공");
				JOptionPane.showMessageDialog(this, "학생이 등록되었습니다.");
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}	
			doSelect();
		} 
		else if (e.getSource().equals(see_button)) {
			doSelect();
		 }
		else if(e.getSource().equals(change_button)) {
			doChange();
			doSelect();
		}
		else if(e.getSource().equals(clear_button)) {
			doClear();
			doSelect();
		}
		else if(e.getSource().equals(name_btn)) {
			
			dtm.setRowCount(0);
			// DB연결 객체
			Connection conn = null;
			// Sql 구문 나타내는 객체
			PreparedStatement pstmt = null;
			// 조회한 테이블값을 가지고 있는 객체
			ResultSet rs = null;

			// DB연결 시도
			try {
				conn = DriverManager.getConnection(url, user, pw);
				pstmt = conn.prepareStatement("SELECT * FROM STUDENT_INFO" +
						" ORDER BY NAME ASC");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String[] stu_ary = new String[9];
					stu_ary[0] = rs.getString("STU_INDEX");
					stu_ary[1] = String.valueOf(rs.getInt("YEAR"));
					stu_ary[2] = String.valueOf(rs.getInt("GRADE"));
					stu_ary[3] = rs.getString("NAME");
					stu_ary[4] = String.valueOf(rs.getInt("KOR"));
					stu_ary[5] = String.valueOf(rs.getInt("ENG"));
					stu_ary[6] = String.valueOf(rs.getInt("MATH"));
					stu_ary[7] = rs.getString("GENDER");
					stu_ary[8] = rs.getString("REMARK");
					dtm.addRow(stu_ary);
				}
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			} 
			finally {
				if (conn != null) {
					try {
						pstmt.close();
						conn.close();
					} 
					catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
		  }
		}
	//조회하게 해주는 함수
		public void doSelect() {
			
			dtm.setRowCount(0);
			// DB연결 객체
			Connection conn = null;
			// Sql 구문 나타내는 객체
			PreparedStatement pstmt = null;
			// 조회한 테이블값을 가지고 있는 객체
			ResultSet rs = null;

			// DB연결 시도
			try {
				conn = DriverManager.getConnection(url, user, pw);
				pstmt = conn.prepareStatement("SELECT * FROM STUDENT_INFO" +
						" ORDER BY STU_INDEX ASC");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String[] stu_ary = new String[9];
					stu_ary[0] = rs.getString("STU_INDEX");
					stu_ary[1] = String.valueOf(rs.getInt("YEAR"));
					stu_ary[2] = String.valueOf(rs.getInt("GRADE"));
					stu_ary[3] = rs.getString("NAME");
					stu_ary[4] = String.valueOf(rs.getInt("KOR"));
					stu_ary[5] = String.valueOf(rs.getInt("ENG"));
					stu_ary[6] = String.valueOf(rs.getInt("MATH"));
					stu_ary[7] = rs.getString("GENDER");
					stu_ary[8] = rs.getString("REMARK");
					dtm.addRow(stu_ary);
				}
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			} 
			finally {
				if (conn != null) {
					try {
						pstmt.close();
						conn.close();
					} 
					catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
			
		}
		//수정함수
		public void doChange() {
			//DB가서 수정해야함
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				String user = "hr";
				String pw = "1234";
				String url = "jdbc:Oracle:thin:@localhost:1521:xe";
				conn = DriverManager.getConnection(url, user, pw);
				pstmt = conn.prepareStatement(
						"UPDATE STUDENT_INFO" +
						" SET YEAR = ?, GRADE = ?, NAME = ?, KOR = ?, ENG = ?, MATH = ?, " +
						" GENDER = ?," + 
						" REMARK = ?" +
						" WHERE STU_INDEX = ?");
				pstmt.setInt(1, Integer.parseInt(year_txt.getText()));
				pstmt.setInt(2, Integer.parseInt(grade_txt.getText()));
				pstmt.setString(3, name_txt.getText());
				pstmt.setInt(4, Integer.parseInt(kor_txt.getText()));
				pstmt.setInt(5, Integer.parseInt(eng_txt.getText()));
				pstmt.setInt(6, Integer.parseInt(math_txt.getText()));
				pstmt.setString(7, gender_txt.getText());
				pstmt.setString(8, remark_txt.getText());
				pstmt.setInt(9, Integer.parseInt((String)jtable.getValueAt(jtable.getSelectedRow(), 0)));
				pstmt.executeUpdate();
				//insert update delete
				//select executeQuery();
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "숫자로 형변환");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					pstmt.close();
					conn.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		public void doClear() {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DriverManager.getConnection(url, user, pw);
				pstmt = conn.prepareStatement(
						"DELETE STUDENT_INFO" +
						" WHERE STU_INDEX = ? ");
				pstmt.setInt(1, 
						Integer.parseInt(
								(String)jtable.getValueAt(jtable.getSelectedRow(), 0)));
				pstmt.executeUpdate();
				//insert update delete
				//select executeQuery();
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "숫자로 형변환");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					pstmt.close();
					conn.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			jtable.getSelectedRow();
			jtable.getSelectedColumn();
			int select_row = jtable.getSelectedRow();
		    year_txt.setText((String)(jtable.getValueAt(select_row, 1)));
			grade_txt.setText((String)(jtable.getValueAt(select_row, 2)));
			name_txt.setText((String)(jtable.getValueAt(select_row, 3)));
			kor_txt.setText((String)(jtable.getValueAt(select_row, 4)));
			eng_txt.setText((String)(jtable.getValueAt(select_row, 5)));
			math_txt.setText((String)(jtable.getValueAt(select_row, 6)));
			gender_txt.setText((String)(jtable.getValueAt(select_row, 7)));
			remark_txt.setText((String)(jtable.getValueAt(select_row, 8)));
		}
		public void mouseReleased(MouseEvent e) {}
}
