import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Student_curd {
	
	public static void main(String[] args) {
		
		PreparedStatement st;
		ResultSet rs;
		Statement s;
		Connection con;
		int option,stud_id;
		String stud_name,stud_dob,stud_doj;
		Date s_dob,s_doj;
		Scanner sc= new Scanner(System.in);
		
		//Student_curd obj=new Student_curd();
		
		System.out.println("1. Insert student data into Student table\r\n"
				+ "2. Update student data into Student table\r\n"
				+ "3. Delete student data from Student table\r\n"
				+ "4. Get a list of all students\r\n"
				+ "5. Get one student information depending on the student id filter.");
		
		System.out.println("Enter your option: ");
		option=sc.nextInt();
		
		String jdbcurl="jdbc:mysql://localhost:3306/student_db";
		String username="root";
		String password="System";
		
		
		try {
			con= DriverManager.getConnection(jdbcurl, username, password);
			if(con != null) {
				System.out.println("Connected");
				switch(option) {
				case 1:  
					System.out.println("enter Student_no");
					stud_id=sc.nextInt();
					System.out.println("enter Student_name");
					stud_name=sc.next();
					System.out.println("enter Student_dob");
					stud_dob=sc.next();
					s_dob= string_to_date(stud_dob);
					System.out.println("enter Student_doj");
					stud_doj=sc.next();
					s_doj= string_to_date(stud_doj);
					
					String INSERT_STU_INFO= "INSERT INTO student (student_no,student_name,student_dob,student_doj)"+ "VALUES(?,?,?,?);";
					st=con.prepareStatement(INSERT_STU_INFO);
					st.setInt(1,stud_id);
					st.setString(2,stud_name);
					st.setDate(3,(java.sql.Date) s_dob);
					st.setDate(4,(java.sql.Date) s_doj);
					
					int rows=st.executeUpdate();
					if(rows>0) {
						System.out.println("Inserted new row");		
					}
				
				break;
				case 2: 
					System.out.println("enter Student_no for which you want to update Student information: ");
					stud_id=sc.nextInt();
					System.out.println("enter name to update:");
					stud_name=sc.next();
					System.out.println("enter dob to update: ");
					stud_dob=sc.next();
					s_dob= string_to_date(stud_dob);
					System.out.println("enter doj to update: ");
					stud_doj=sc.next();
					s_doj= string_to_date(stud_doj);
					
					String UPDATE_STU_INFO = "update student SET student_name = ?,student_dob=?,student_doj=? WHERE student_no = ?";
					
					st=con.prepareStatement(UPDATE_STU_INFO);
					st.setString(1, stud_name);
					st.setDate(2,(java.sql.Date) s_dob);
					st.setDate(3, (java.sql.Date)s_doj);
					st.setInt(4,stud_id);
					
					
					int row=st.executeUpdate();
					if(row>0) {
						System.out.println("An existing user was updated  successfully!");		
					}
					
				break;
				case 3: 
							System.out.println("Enter the Id of the student whose information has to be deleted");
							stud_id=sc.nextInt();
							
							String DELETE_STU_INFO = "delete from student where student_no = ?";
							st = con.prepareStatement(DELETE_STU_INFO);
							st.setInt(1, stud_id);
							int del_row=st.executeUpdate();
							
							if(del_row>0) {
								System.out.println("Deleted");
							}
							
				break;
				case 4: System.out.println("Getting the data all the students ");
				String SELECT_ALL_STU_INFO = "select * from student";
					s=con.createStatement();
					rs=s.executeQuery(SELECT_ALL_STU_INFO);
				
				while(rs.next()) {
					stud_id=rs.getInt(1);
					stud_name=rs.getString(2);
					s_dob=rs.getDate(3);
					s_doj=rs.getDate(4);
					
					
					System.out.println("Student_No: "+stud_id+" Student_name: "+stud_name+" Student_Dob: "+s_dob+" Student_Doj: "+s_doj+"\n");
				}
				break;
				case 5: 
					System.out.println("Enter the Student_id for which you want details: ");
					stud_id=sc.nextInt();
					
					String SELECT_STU_INFO = "Select * from student WHERE student_no = ?";
					st=con.prepareStatement(SELECT_STU_INFO);
					st.setInt(1,stud_id);
					rs=st.executeQuery();
					 while(rs.next()) {
							
							stud_name=rs.getString(2);
							s_dob=rs.getDate(3);
							s_doj=rs.getDate(4);
							
							System.out.println("Student Details for Student _id :"+stud_id);
							System.out.println(" Student_name: "+stud_name+" Student_Dob: "+s_dob+" Student_Doj: "+s_doj+"\n");
						}
				
				
				break;
				default: System.out.println("Incorrect option ");
				
				}
				con.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}


	public static Date string_to_date(String _date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date date = null;
		try {
			date = formatter.parse(_date);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

}
