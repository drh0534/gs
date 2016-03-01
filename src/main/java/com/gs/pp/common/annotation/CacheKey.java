package com.gs.pp.common.annotation;

import com.gs.pp.common.utils.RedisUtils;

public enum CacheKey {
  
	LIST_ALBUM_ALL_VISITED(RedisUtils.LIST_ALBUM_ALL_VISITED),
	
	LIST_ALBUM_1_VISITED(RedisUtils.LIST_ALBUM_ALL_VISITED);

	
	
	
	
	
	private String key;
	
	private CacheKey(String key){
		this.key = key;
	}
	
	@Override
    public String toString() {
		
        return String.valueOf ( this . key );
    }
	
}
