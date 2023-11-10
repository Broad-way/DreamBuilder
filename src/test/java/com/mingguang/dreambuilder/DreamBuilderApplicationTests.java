package com.mingguang.dreambuilder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
class DreamBuilderApplicationTests {

	@Test
	void contextLoads() {
	}

	@RestController
	public class HelloController {
		@RequestMapping("hello")
		public String hello(){
			return "hello security!";
		}
	}

}
