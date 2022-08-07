package com.bjit.nusaiba.backend_project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
class IntregationTesting {
	@Autowired
    private MockMvc mockMvc;
	
	private String adminToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJudXNhaWJhLm5pemFtQGJqaXQuY29tIiwiZXhwIjoxNjU2NDQwMTM3LCJpYXQiOjE2NTY0MjIxMzd9.-a7yZaWsWNRctR36ww_AifB97_P5cGzg3dhjk56U51_KmGj2eH0MlzTNHy0ocVBoC8zNE7iJ05jVOf9dCz5jtg";

	

	@Test
	@Order(1)
    public void checkAuthAdmin() throws Exception{
		MvcResult result =this.mockMvc.perform(post("/authenticate")
    			.content("{\"username\":\"nusaiba.nizam@bjit.com\",\"password\":\"1234\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
    			.andReturn();
		
		
		String token= JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");
		
		this.mockMvc.perform(put("/api/students/?id=6")
    			.content("{\"name\":\"sadia\",\"address\":\"Comilla\",\"age\":\"23\"}")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
    	.andExpect(status().is2xxSuccessful());
    }
	
	
	@Test
	@Order(2)
    public void checkAuthStudent() throws Exception{
		MvcResult result =this.mockMvc.perform(post("/authenticate")
    			.content("{\"username\":\"nipa.howladar@gmail.com\",\"password\":\"1234\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
    			.andReturn();
		
		
		String token= JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");
		
		this.mockMvc.perform(get("/api/students/?id=1").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
   	 .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.address").value("Lalmatia Dhaka"));
    }
	
    @Test
    @Order(3)
    public void checkGetWithId() throws Exception {
    	

    	 this.mockMvc.perform(get("/api/students/?id=1").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
    	 .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.address").value("Lalmatia Dhaka"));
    	 
    	 this.mockMvc.perform(get("/api/students/?id=999").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
    	 .andDo(print()).andExpect(status().is4xxClientError());

    	 this.mockMvc.perform(get("/api/students/?id=1").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
         .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").exists());
    	 


    }

    @Test
    @Order(4)
    public void checkPost() throws Exception{
    	this.mockMvc.perform(post("/api/students")
    			.content("{\"name\":\"nusaiba\",\"address\":\"Lalmatia\",\"age\":\"23\",\"role\":\"student\",\"pass\":\"$2a$12$GMssNL49WfZP9DDvksLRsugNFgoZ4tsRc073GopwbMcebBwHsWvcm\",\"email\":\"k@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
    	.andExpect(status().is2xxSuccessful());
    	
    }
    
    
    @Test
    @Order(5)
    public void checkPut() throws Exception{
    	this.mockMvc.perform(put("/api/students/?id=6")
    			.content("{\"name\":\"sadia\",\"address\":\"Comilla\",\"age\":\"23\"}")
                .contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
    	.andExpect(status().is2xxSuccessful());
    	
    }
    
    @Test
    @Order(6)
    public void checkGetWithEmail() throws Exception {
    	this.mockMvc.perform(get("/api/students/?email=k@gmail.com").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
   	 	.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("nusaiba"));
    }
    
    
    @Test
    @Order(7)
    public void checkDelete() throws Exception {
    	MvcResult result =this.mockMvc.perform(get("/api/students/?email=k@gmail.com").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
   	 	.andDo(print()).andExpect(status().isOk()).andReturn();
    	Integer id= JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    	
    	this.mockMvc.perform(delete("/api/students/?id="+id).header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
    	   	 	.andDo(print()).andExpect(status().is2xxSuccessful());
    	
    }
}
