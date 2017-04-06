package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class TestMain {

	private static TreeHolder treeHolder = null;


	/*
	 * public static void main(String[] args) {
	 * 
	 * new TestMain();
	 * 
	 * }
	 */

	public TestMain() {

	}

	@Test
	public void testConcurrentModification() {
		treeHolder = new TreeHolder();

		IntStream.range(0, 1000).forEach(integer -> {
			System.out.println("Running attempt : " + integer);
			try {
				doTestProcessTree();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private void doTestProcessTree() throws InterruptedException {
		List<Runnable> treeRunnables = new ArrayList<Runnable>();


		IntStream.range(0, 2).forEach(integer -> treeRunnables.add(() -> {
			int numberOfElements = 10000;

			List<MySimpleObject> objectList = new ArrayList<MySimpleObject>(numberOfElements);

			IntStream.range(0, numberOfElements).forEach(anInteger -> {
				MySimpleObject anObject = new MySimpleObject(String.valueOf(anInteger), anInteger);
				objectList.add(anObject);
			});


			objectList.forEach(mySimpleObject -> treeHolder.ourTree.put(mySimpleObject.name, mySimpleObject));
		}));

		IntStream.range(0, 1000).forEach(integer -> treeRunnables.add(() -> getObjects()));

		assertConcurrent("Test testTreeAccess()", treeRunnables, 25);
	}

	private static String generateThreadDump() {
		final StringBuilder dump = new StringBuilder();
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
		for (ThreadInfo threadInfo : threadInfos) {
			if (threadInfo == null)
				continue;
			dump.append('"');
			dump.append(threadInfo.getThreadName());
			dump.append("\" ");
			final Thread.State state = threadInfo.getThreadState();
			dump.append("\n   java.lang.Thread.State: ");
			dump.append(state);
			final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
			for (final StackTraceElement stackTraceElement : stackTraceElements) {
				dump.append("\n        at ");
				dump.append(stackTraceElement);
			}
			dump.append("\n\n");
		}
		return dump.toString();
	}

	public void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds) throws InterruptedException {
		final int numThreads = runnables.size();
		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
		try {
			final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
			final CountDownLatch afterInitBlocker = new CountDownLatch(1);
			final CountDownLatch allDone = new CountDownLatch(numThreads);
			for (final Runnable submittedTestRunnable : runnables) {
				threadPool.submit(new Runnable() {
					public void run() {
						allExecutorThreadsReady.countDown();
						try {
							afterInitBlocker.await();
							submittedTestRunnable.run();
						} catch (final Throwable e) {
							exceptions.add(e);
							e.printStackTrace();
						} finally {
							allDone.countDown();
						}
					}
				});
			}
			// wait until all threads are ready
			Assert.assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent", allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
			// start all test runners
			afterInitBlocker.countDown();
			// Assert.assertTrue(message +" timeout! More than " +
			// maxTimeoutSeconds + " seconds", allDone.await(maxTimeoutSeconds,
			// TimeUnit.SECONDS));
			if (!allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS)) {
				// we are stuck in the infinite loop
				try (BufferedWriter os = new BufferedWriter(new FileWriter(new File("thread-dump.txt")))) {
					os.write(generateThreadDump());
				} catch (IOException ex) {
					/**/
				}
				// System.out.println(generateThreadDump());
				Assert.fail(message + " timeout! More than " + maxTimeoutSeconds + " seconds");
				System.exit(1);
			}

		} finally {
			threadPool.shutdownNow();
		}
		Assert.assertTrue(message + " failed with exception(s) " + exceptions, exceptions.isEmpty());
	}

	public Set<MySimpleObject> getObjects() {
		Set<MySimpleObject> set = new HashSet<MySimpleObject>();
		treeHolder.ourTree.put("Key1", new MySimpleObject("Key1", 1));
		treeHolder.ourTree.put("Key2", new MySimpleObject("Key1", 2));
		treeHolder.ourTree.put("Key3", new MySimpleObject("Key1", 3));
		treeHolder.ourTree.put("Key4", new MySimpleObject("Key1", 4));
		treeHolder.ourTree.put("Key5", new MySimpleObject("Key1", 5));
		treeHolder.ourTree.put("Key6", new MySimpleObject("Key1", 6));
		treeHolder.ourTree.put("Key7", new MySimpleObject("Key1", 7));
		treeHolder.ourTree.put("Key8", new MySimpleObject("Key1", 8));
		treeHolder.ourTree.put("Key9", new MySimpleObject("Key1", 9));
		treeHolder.ourTree.put("Key10", new MySimpleObject("Key1", 10));
		try {

			synchronized(treeHolder.ourTree){
				Iterator<java.util.Map.Entry<String, MySimpleObject>> it = treeHolder.ourTree.entrySet().iterator();
				while (it.hasNext()) {
					set.add(it.next().getValue());
				}
			}
			

		} catch (Exception e) {/* Munch */System.out.println("Ooops!");}

		return set;
	}

	/*
	 * -------------------------------------------------------------------------
	 * ---------------------------------------------------
	 */

}
