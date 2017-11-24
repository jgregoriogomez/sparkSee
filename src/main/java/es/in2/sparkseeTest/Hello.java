package es.in2.sparkseeTest;

import java.io.File;

import com.sparsity.sparksee.gdb.Sparksee;
import com.sparsity.sparksee.gdb.SparkseeConfig;
import com.sparsity.sparksee.gdb.SparkseeProperties;

public class Hello {

	public static void main(String[] args) {
		
		
		SparkseeConfig cfg = new SparkseeConfig();
		cfg.setCacheMaxSize(2048); // 2 GB
		cfg.setLogFile("HelloSparksee.log");
		Sparksee sparksee = new Sparksee(cfg);
		/*SparkseeProperties.load("sparksee/config/dir/mysparksee.cfg");
		SparkseeConfig cfg = new SparkseeConfig();
		Sparksee sparksee = new Sparksee(cfg);
		//Database db = sparksee.create("HelloSparksee.gdb", "HelloSparksee");*/
	}

}
