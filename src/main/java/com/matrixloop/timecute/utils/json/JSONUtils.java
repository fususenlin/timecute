package com.matrixloop.timecute.utils.json;



import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
	
	public static JSONObject merge(JSONObject target, JSONObject source) throws JSONException{
		@SuppressWarnings("unchecked")
		Iterator<String> it = source.keys();
		while(it.hasNext()){
			String key = it.next();
			if(!target.has(key)){
				target.put(key, source.get(key));
			}
		}
		return target;
	}
	
	public static JSONObject merge(JSONObject[] jsonObjects) throws JSONException{
		JSONObject merged = new JSONObject();
		for(JSONObject obj : jsonObjects){
			@SuppressWarnings("unchecked")
			Iterator<String> it = obj.keys();
			while(it.hasNext()){
				String key = it.next();
				if(!merged.has(key)){
					merged.put(key, obj.get(key));
				}
			}
		}
		return merged;
	}
	
	public static JSONObject merge(List<JSONObject> jsonList) throws JSONException{
		Object[] oArray = jsonList.toArray();
		JSONObject[] objs = new JSONObject[]{new JSONObject()};
		for(int i=0;i<oArray.length;i++){
			JSONObject o = (JSONObject)oArray[i];
			objs[i] = (JSONObject)o;
		}
		return merge(objs);
	}
}
