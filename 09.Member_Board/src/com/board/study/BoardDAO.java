package com.board.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.board.study.BoardDTO;
import com.board.study.SearchDTO;

public class BoardDAO {
	private Connection conn;		//연결 객체
	private PreparedStatement ps;	//DB 쿼리문 전송객체
	private ResultSet rs;			//SQL 결과객체 : select 문
	
	//DB접속 메소드 --------------------------------------------------------------------------------
	public Connection getConn() {

		//String url = "jdbc:oracle:thin:@192.168.0.22:1521:XE";
		//String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "hanul";
		String password = "0000";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	//동적로딩
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.getConn() Exception!!!");
		}//try//catch

		return conn;
	}//getConn()
	
	//DB종료 메소드 : conn -> ps -> rs 역순으로 종료 ----------------------------------------------
	public void dbClose() {

		try {
			if ( rs != null ) {rs.close();}
			if ( ps != null ) {ps.close();}
			if ( conn != null ) {conn.close();}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.dbClose() Exception!!!");
		}//try//catch

	}//dbClose()

	//번호 중복검사 메소드 ------------------------------------------------------------------------
	public ResultSet checkNum(int num) {

		conn = getConn();	//DB접속(연결)
		//String sql = "select * from memberBoard where num = " + num;	//Statement SQL Query 작성
		String sql = "select * from memberBoard where num = ?";	//Prepared Statement SQL Query 작성
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			ps.setInt(1, num); 	//매개변수 값을 전달(세팅)
			
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			//중요메소드: ps.executeQuery(), ps.executeUpdate()
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.checkNum() Exception!!!");
			
		} finally {
			//dbClose(); //DB접속해제	//연속작업을 위해 DB를 닫지 않는다.

		}//try//catch//finally

		return rs;
	}//checkNum()
	
	//마지막 도서정보 번호 출력 메소드 ----------------------------------------------------------
	public int returnLastNum() {
		int num = 0;

		conn = getConn();	//DB접속(연결)
		String sql = "select num from memberBoard order by num desc";	//Prepared Statement SQL Query 작성
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			//ps.setInt(1, num); 	//매개변수 값을 전달(세팅)
			
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			if (rs.next()) {	//도서정보가 있으면
				num = rs.getInt("num") + 1;	//마지막 도서번호 + 1
			} else {	//도서정보가 전혀 없으면(신규 테이블)
				num = 1;	//도서번호 = 1
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.returnLastNum() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제	//다음작업인 BoardDAO.checkNum()이 DB를 오픈하므로 DB를 닫는다.

		}//try//catch//finally

		return num;
	}//returnLastNum()

	//도서정보 최저단가 출력 메소드 --------------------------------------------------------------
	public int returnLowestCost() {
		int cost = 0;

		conn = getConn();	//DB접속(연결)
		String sql = "select cost from memberBoard order by cost asc";	//Prepared Statement SQL Query 작성
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			//ps.setInt(1, cost); 	//매개변수 값을 전달(세팅)
			
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			if (rs.next()) {	//도서정보가 있으면
				cost = rs.getInt("cost");	//order by cost asc : 첫 도서정보의 cost 는 최저가 임.
			} else {	//도서정보가 전혀 없으면(신규 테이블)
				cost = 0;	//도서 최저단가 = 0원
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.returnLowestCost() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return cost;
	}//returnLowestCost()
	
	//도서정보 최고단가 출력 메소드 ---------------------------------------------------------------
	public int returnHighestCost() {
		int cost = 0;

		conn = getConn();	//DB접속(연결)
		String sql = "select cost from memberBoard order by cost desc";	//Prepared Statement SQL Query 작성
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			//ps.setInt(1, cost); 	//매개변수 값을 전달(세팅)
			
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			if (rs.next()) {	//도서정보가 있으면
				cost = rs.getInt("cost");	//order by cost desc : 첫 도서정보의 cost 는 최고가 임.
			} else {	//도서정보가 전혀 없으면(신규 테이블)
				cost = 0;	//도서 최저단가 = 0원
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.returnHighestCost() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return cost;
	}//returnHighestCost()
	
	//도서등록 메소드 -------------------------------------------------------------------------------
	public int insertBoard(BoardDTO dto) {

		conn = getConn();	//DB접속(연결)
		String sql = "insert into memberBoard values(MB_NUM_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, 0, sysdate)";	//Prepared Statement SQL Query 작성
		int succ = 0;	//쿼리 성공 여부를 판단
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			ps.setString(1, dto.getBoard_id()); 	//매개변수 값을 전달(세팅)
			ps.setString(2, dto.getBoard_subject());
			ps.setString(3, dto.getBoard_content());
			ps.setString(4, dto.getBoard_file());
			ps.setInt(5, dto.getBoard_re_ref());
			ps.setInt(6, dto.getBoard_re_lev());
			ps.setInt(7, dto.getBoard_re_seq());
			
			succ = ps.executeUpdate();	//쿼리를 실행후 결과(int succ)값 저장시킴.
			//중요메소드: ps.executeQuery(), ps.executeUpdate()
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.insertBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return succ;
	}//insertBoard()
	
	//도서정보 삭제 메소드 ------------------------------------------------------------------------
	public int deleteBoard(int num) {

		conn = getConn();	//DB접속(연결)
		String sql = "delete from memberBoard where num = ?";	//Prepared Statement SQL Query 작성
		int succ = 0;	//쿼리 성공 여부를 판단
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			ps.setInt(1, num); 	//매개변수 값을 전달(세팅)
			
			succ = ps.executeUpdate();	//쿼리를 실행후 결과(int succ)값 저장시킴.
			//중요메소드: ps.executeQuery(), ps.executeUpdate()
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.deleteBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try/catch/finally

		return succ;
	}//deleteBoard()

	//도서정보 수정 메소드 ------------------------------------------------------------------------
	public int updateBoard(BoardDTO dto) {

		conn = getConn();	//DB접속(연결)
		String sql = "update memberBoard "
				+ "set title=?, company=?, name=?, cost=? "
				+ "where num=?";	//Prepared Statement SQL Query 작성
		int succ = 0;	//쿼리 성공 여부를 판단
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
//			ps.setString(1, dto.getTitle());
//			ps.setString(2, dto.getCompany());
//			ps.setString(3, dto.getName());
//			ps.setInt(4, dto.getCost());
//			ps.setInt(5, dto.getNum()); 	//매개변수 값을 전달(세팅)
			
			succ = ps.executeUpdate();	//쿼리를 실행후 결과(int succ)값 저장시킴.
			//중요메소드: ps.executeQuery(), ps.executeUpdate()
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.insertBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return succ;
	}//updateBoard()
	
	//전체 도서목록 보기 메소드 ---------------------------------------------------------------------
	public ArrayList<BoardDTO> searchAllBoard() {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard order by num";	//Prepared Statement SQL Query 작성
		BoardDTO dto = null;
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			while (rs.next()) {	//DB쿼리 결과가 있으면
				//System.out.println("BoardDAO.searchAllBoard() DB접속");
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String company = rs.getString("company");
				String name = rs.getString("name");
				int cost = rs.getInt("cost");
//				dto = new BoardDTO(num, title, company, name, cost);
				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchAllBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchAllBoard()
	
	//단일 도서번호의 도서정보 검색 메소드 --------------------------------------------------------
	public ArrayList<BoardDTO> searchNumBoard(int num) {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard "
				+ "where num = ?";	//Prepared Statement SQL Query 작성
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			ps.setInt(1, num);
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchNumBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchNumBoard()

	//도서제목 검색 메소드 ------------------------------------------------------------------------
	public ArrayList<BoardDTO> searchTitleBoard(String searchTitle) {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard "
				+ "where lower(title) like lower(?) "
				+ "order by title asc";	//Prepared Statement SQL Query 작성
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			ps.setString(1, "%" + searchTitle + "%");
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchTitleBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchTitleBoard()

	//출판사 검색 메소드 ------------------------------------------------------------------------
	public ArrayList<BoardDTO> searchCompanyBoard(String searchCompany) {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard "
				+ "where lower(company) like lower(?) "
				+ "order by company asc";	//Prepared Statement SQL Query 작성
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			ps.setString(1, "%" + searchCompany + "%");
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchCompanyBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchCompanyBoard()

	//저자 검색 메소드 ---------------------------------------------------------------------------
	public ArrayList<BoardDTO> searchNameBoard(String searchName) {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard "
				+ "where lower(name) like lower(?) "
				+ "order by name asc";	//Prepared Statement SQL Query 작성
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			ps.setString(1, "%" + searchName + "%");
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchNameBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchNameBoard()
	
	//도서단가 구간 검색 메소드 ---------------------------------------------------------------------
	public ArrayList<BoardDTO> searchCostBoard(int searchCost1, int searchCost2) {

		conn = getConn();	//DB접속(연결)
		String sql = null;
		
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			if (searchCost1 <= searchCost2) {
				sql = "select * from memberBoard "
						+ "where cost between ? and ? "
						+ "order by cost asc";	//Prepared Statement SQL Query 작성
				ps = conn.prepareStatement(sql);	//sql 문장 전송
				ps.setInt(1, searchCost1);
				ps.setInt(2, searchCost2);
			} else {
				sql = "select * from memberBoard "
						+ "where cost not between ? and ? "
						+ "order by cost asc";	//Prepared Statement SQL Query 작성
				ps = conn.prepareStatement(sql);	//sql 문장 전송
				ps.setInt(1, searchCost2);
				ps.setInt(2, searchCost1);
			}//if (searchCost1 < searchCost1)
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.searchCostBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//searchCostBoard()

	//도서 주문 메소드 ------------------------------------------------------------------------------
	public ArrayList<BoardDTO> orderBoard(int orderSu) {

		conn = getConn();	//DB접속(연결)
		String sql = "select * from memberBoard "
				+ "where lower(cost) like lower(?) "
				+ "order by num asc";	//Prepared Statement SQL Query 작성
		ArrayList<BoardDTO> list = new ArrayList<>();	//최종결과
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송
			ps.setString(1, "%" + orderSu + "%");
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.

			while (rs.next()) {	//DB쿼리 결과가 있으면
				BoardDTO dto = new BoardDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setTitle(rs.getString("title"));
//				dto.setCompany(rs.getString("company"));
//				dto.setName(rs.getString("name"));
//				dto.setCost(rs.getInt("cost"));

				list.add(dto);
			}//while

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.orderBoard() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return list;
	}//orderBoard()

	//출력 메소드 ----------------------------------------------------------------------------------
	public void display(ArrayList<BoardDTO> list) {

		DecimalFormat df = new DecimalFormat("￦#,##0");

		System.out.println("\t\t출력:[ "+ list.size() +" ]개");
		System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.println("\tNUM\tTITLE\t\t\tCOMPANY\t\tNAME\t\tCOST");
		System.out.println("\t───────────────────────────────────────────────────────────────────────────────");
		if (list.size() <= 0) {
			System.out.println("검색결과가 없습니다!");
		} else {
			for (BoardDTO dto : list) {

//				System.out.print("\t" + dto.getNum() + "\t");
//				
//				System.out.print(dto.getTitle() + "\t");
//				if		(dto.getTitle().length() < 4) {System.out.print("\t\t");
//				}else if(dto.getTitle().length() < 8) {System.out.print("\t");	} 
//
//				System.out.print(dto.getCompany() + "\t");
//				if		(dto.getCompany().length() < 4) {System.out.print("\t");}
//
//				System.out.print(dto.getName() + "\t");
//				if		(dto.getName().length() < 4)	{System.out.print("\t");}
//
//				System.out.print(df.format(dto.getCost()) + "\n");
				
				//System.out.println("\t" + dto.getTitle().length() + "\t" + dto.getCompany().length() + "\t" + dto.getName().length());
			}//for each

		}//if list.size()
		System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

	}//display()

	//마지막 주문정보 번호 출력 메소드 ----------------------------------------------------------
	public int returnLastOrderNum() {
		int num = 0;

		conn = getConn();	//DB접속(연결)
		String sql = "select num from tblOrder order by order_num desc";	//Prepared Statement SQL Query 작성
		
		try {
			ps = conn.prepareStatement(sql);	//sql 문장 전송객체값 대입
			//ps.setInt(1, num); 	//매개변수 값을 전달(세팅)
			
			rs = ps.executeQuery();	//문장을 실행 후 결과객체(ResultSet rs)값 저장시킴.
			if (rs.next()) {	//주문정보가 있으면
				num = rs.getInt("order_num") + 1;	//마지막 주문번호 + 1
			} else {	//주문정보가 전혀 없으면(신규 테이블)
				num = 1;	//주문번호 = 1
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BoardDAO.returnLastOrderNum() Exception!!!");
			
		} finally {
			dbClose(); //DB접속해제

		}//try//catch//finally

		return num;
	}//returnLastOrderNum()


}//class BoardDAO
