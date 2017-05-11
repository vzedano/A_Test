package com.test;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

public class JsonTest {

	public static void main(String[] args) throws IOException {
		new JsonTest();
	}

	public JsonTest() throws IOException {
		JsonObject object = new JsonObject();
		
		object.properties.put("com.test",new MySimpleObject(null, 1));
		object.properties.put("com.main",new MySimpleObject("com.main", 1));
		object.properties.put("com.res",new MySimpleObject("com.res", 1));
		object.properties.put("com.test.hi",new MySimpleObject("com.test.hi", 1));
		object.properties.put("com.test.there",new MySimpleObject("com.test.there", 1));
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
		System.out.println(writer.writeValueAsString(object));;
		
	}
	
	public class JsonObject {
		@JsonProperty(value="properties")
		HashMap<String, MySimpleObject> properties = new HashMap<String,MySimpleObject>();
	}
}