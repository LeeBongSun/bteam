package com.member.study;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.member.study.MemberDTO;
import com.member.study.SearchDTO;

public class MemberDAO {

	//기존 JDBC 모델에서는 Connection(연결객체)을 먼저 만들었지만,
	//myBatis 경우 SqlSessionFactory 객체를 먼저 생성
	private static SqlSessionFactory sqlMapper;
	static {
		String resource = "com/hanul/mybatis/SqlMapConfig.xml";

		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);	//SqlMapConfig.xml 파일 입력객체 생성

			sqlMapper = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("memberDAO.SqlSessionFactory Exception!!!");

		}
	}//static(초기화블럭)

	//전체 목록검색
	public List<MemberDTO> getAllMember() {
		SqlSession session = sqlMapper.openSession();
		List<MemberDTO> list = null;

		list = session.selectList("getAllMember");

		session.close();
		return list;
	}

	public int isMember(MemberDTO dto) {
		SqlSession session = sqlMapper.openSession();
		MemberDTO isDto = null;
		isDto = session.selectOne("isMember", dto);
		session.close();
		
		int result = -1;
		if(isDto != null) {
			if( isDto.getMember_pw().equals(dto.getMember_pw()) ) {
				result = 1;	//비밀번호가 일치
			} else {
				result = 0;	//비밀번호가 불일치
			}
		} else {	//아이디가 존재하지 않는다.
			result = -1;
		}
		
		return result;	//결과 리턴		
	}//isMember()

	//레코드 추가
	public int joinMember(MemberDTO dto) {
		//SqlSessionFactory(sqlMapper)에서 session 활성화 : DB접속
		SqlSession session = sqlMapper.openSession();
		//alert() 창을 사용할 경우에는 succ를 return 해야 한다.
		//▶ PrintWriter를 이용한 script 코드 구현
		int succ = 0;	//성공여부 판단
		
		//insert 작업(SQL문장작성) ▶ memberMapper.xml
		succ = session.insert("joinMember", dto);
		
		session.commit();	//커밋
		session.close();	//session 종료
		
		return succ;	//결과 리턴		
	}//joinMember()
	
	//레코드 삭제
	public int deleteMember(String member_id) {
		SqlSession session = sqlMapper.openSession();
		//alert 창을 사용하지 않을 경우에는 succ가 필요없다.
		//▶ ActionForward를 이용한 페이지 전환
		int succ = 0;	//성공여부 판단
		
		session.delete("deleteMember", member_id);

		session.commit();
		session.close();
		return succ;
	}

	//레코드 보기
	public MemberDTO memberDetail(int b_num) {
		SqlSession session = sqlMapper.openSession();
		MemberDTO dto = null;

		dto = session.selectOne("memberDetail", b_num);

		session.close();
		return dto;
	}//memberDetail()
	
	//레코드 수정
	public void memberUpdate(MemberDTO dto) {
		SqlSession session = sqlMapper.openSession();

		session.update("memberUpdate", dto);
		
		session.commit();
		session.close();
		//return succ;
	}//memberUpdate()
	
	//레코드 조건검색
	public List<MemberDTO> memberSearch(SearchDTO dto) {
		SqlSession session = sqlMapper.openSession();
		List<MemberDTO> list = null;

		list = session.selectList("memberSearch", dto);

		session.close();
		return list;
	}//memberSearch()

	//레코드 b_num 검색
	public MemberDTO getById(int b_num) {
		SqlSession session = sqlMapper.openSession();
		MemberDTO dto = null;

		dto = session.selectOne("getById", b_num);

		session.close();
		return dto;
	}//getById()
	
	public void memberCount(int b_num) {
		SqlSession session = sqlMapper.openSession();
		session.update("memberCount", b_num);
		
		session.commit();
		session.close();
	}

	
}//class
