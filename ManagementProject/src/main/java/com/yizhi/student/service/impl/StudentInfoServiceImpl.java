package com.yizhi.student.service.impl;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentInfoServiceImpl implements StudentInfoService {



	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@Override
	public StudentInfoDO get(Integer id){
		System.out.println("======service层中传递过来的id参数是：" + id + "======");
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){
		if(map.get("currPage") != null && (String)map.get("pageSize") != null){
			Integer currPage = Integer.valueOf((String) map.get("currPage"));
			Integer pageSize = Integer.valueOf((String)map.get("pageSize"));
			map.put("p1",(currPage - 1) * pageSize);
			map.put("p2",pageSize);
		}
		return studentInfoDao.list(map);
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}
	
	@Override
	public int save(StudentInfoDO studentInfo){
		System.err.println(studentInfo);
		ArrayList<String> list = new ArrayList<>();
		list.add(studentInfo.getClassId());
		list.add(studentInfo.getTomajor().toString());
		list.add(studentInfo.getTocollege().toString());
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()){
			String next = iterator.next();
			if (!next.equals("1") && !next.equals("2")){
				return 0;
			}
		}
		String studentSex = studentInfo.getStudentSex();
		if(!studentSex.equals("2") && !studentSex.equals("1") && !studentSex.equals("0")){
			return 0;
		}
		list = new ArrayList<>();
		list.add(studentInfo.getStudentId());
		list.add(studentInfo.getExamId());
		list.add(studentInfo.getCertify());
		list.add(studentInfo.getCardId());
		list.add(studentInfo.getTelephone());
		list.add(studentInfo.getCardId());
		if (studentInfo.getBirthday() == null){
			return 0;
		}
		iterator = list.iterator();
		while (iterator.hasNext()){
			String s = iterator.next();
			if(s == null || !s.matches("[0-9]+")){
				return 0;
			}
		}
		return studentInfoDao.save(studentInfo);
	}
	
	@Override
	public int update(StudentInfoDO studentInfo){
		ArrayList<String> list = new ArrayList<>();
		list.add(studentInfo.getClassId());
		list.add(studentInfo.getTomajor().toString());
		list.add(studentInfo.getTocollege().toString());
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()){
			String next = iterator.next();
			if (!next.equals("1") && !next.equals("2")){
				return 0;
			}
		}
		if (studentInfo.getBirthday() == null){
			return 0;
		}
		String studentSex = studentInfo.getStudentSex();
		if(!studentSex.equals("2") && !studentSex.equals("1") && !studentSex.equals("0")){
			return 0;
		}
		list = new ArrayList<>();
		String cardId = studentInfo.getCardId();
		list.add(studentInfo.getStudentId());
		list.add(studentInfo.getExamId());
		list.add(studentInfo.getCertify());
		list.add(studentInfo.getCardId());
		list.add(studentInfo.getTelephone());
		iterator = list.iterator();
		while (iterator.hasNext()){
			String s = iterator.next();
			if(s == null || !s.matches("[0-9]+")){
				return 0;
			}
		}
		return studentInfoDao.update(studentInfo);
	}
	
	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}
	
	@Override
	@Transactional
	public int batchRemove(Integer[] ids){
		if (ids == null || ids.length == 0 ){
			return 1;
		}
		int i = studentInfoDao.batchRemove(ids);
		if (i != ids.length){
			throw new RuntimeException("删除失败");
		}
		return i >= 1 ? 1 : 0;
	}
	
}
