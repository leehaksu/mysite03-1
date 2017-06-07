package com.jx372.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx372.mysite.repository.SiteDao;
import com.jx372.mysite.vo.SiteVo;

@Service
public class SiteService {
	
	@Autowired
	private SiteDao siteDao;
	
	public SiteVo getSiteInformation() {
		return siteDao.get();
	}
	
	public boolean updateSiteInformation( SiteVo siteVo ) {
		return siteDao.update( siteVo ) == 1;
	}
}
