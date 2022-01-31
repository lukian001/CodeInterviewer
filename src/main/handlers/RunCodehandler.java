package main.handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class RunCodehandler {
	private String code;
	private String out;

	public RunCodehandler(String code) {
		this.code = code;
		this.code = "package test;" + System.lineSeparator() + "import java.util.*;" + this.code;
	}

	public void run() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		OutputStream output = new OutputStream() {
			private StringBuilder sb = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				this.sb.append((char) b);
			}

			@Override
			public String toString() {
				return this.sb.toString();
			}
		};

		File root = new File("/java");
		File sourceFile = new File(root, "test/Test.java");
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), code.getBytes(StandardCharsets.UTF_8));

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, output, sourceFile.getPath());

		if (output.toString().isBlank()) {
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
			Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
			Method met = cls.getMethod("main", new Class[] { String[].class });

			ByteArrayOutputStream baod = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baod);
			PrintStream old = System.out;
			System.setOut(ps);
			met.invoke(null, new Object[] { new String[0] });
			System.setOut(old);
			System.out.println(output.toString());
			this.out = baod.toString();
		} else {
			this.out = output.toString();
		}
	}

	public String getResult() {
		return out;
	}

}