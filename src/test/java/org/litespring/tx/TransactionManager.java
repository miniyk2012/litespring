package org.litespring.tx;

import org.junit.Test;
import org.litespring.util.MessageTracker;

public class TransactionManager {
	
	public void start(){
		System.out.println("start tx");	
		MessageTracker.addMsg("start tx");
	}
	public void commit(){
		System.out.println("commit tx");
		MessageTracker.addMsg("commit tx");
	}
	public void rollback(){
		System.out.println("rollback tx");
		MessageTracker.addMsg("rollback tx");
	}

	private void append(StringBuffer a) {
		a.append("x");
	}

	@Test
	public void testStringBUffer() {
		StringBuffer a = new StringBuffer("abc");
		append(a);
		System.out.println(a.toString());
	}
}
