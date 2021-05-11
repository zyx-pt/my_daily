package calculator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalStringTest {

	List<String> s=new ArrayList<String>();	
	@Before
	public void setUp() throws Exception {		
		s.add("1+2*3");
		s.add("2*3/2");
	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testCalculate() {
		System.out.println(CalString.calculate(s.get(0)));

		System.out.println(CalString.calculate(s.get(1)));

		assertEquals(CalString.calculate(s.get(0)), 7.0,1e-6);
		
		assertEquals(CalString.calculate(s.get(1)), 3.0,1e-6);
	}

}
