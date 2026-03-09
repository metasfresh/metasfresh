package de.metas.util.async;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DebouncerTest
{
	@Test
	void threadTerminatesAfterIdleTimeout() throws Exception
	{
		final String debouncerName = "test-threadTerminates";
		final CountDownLatch consumed = new CountDownLatch(1);
		final List<String> receivedItems = Collections.synchronizedList(new ArrayList<>());

		final Debouncer<String> debouncer = Debouncer.<String>builder()
				.name(debouncerName)
				.delayInMillis(50)
				.bufferMaxSize(100)
				.consumer(items -> {
					receivedItems.addAll(items);
					consumed.countDown();
				})
				.build();

		// Act: add an item so the executor thread starts
		debouncer.add("item1");

		// Wait for the consumer to fire
		assertThat(consumed.await(5, TimeUnit.SECONDS))
				.as("consumer should have been called")
				.isTrue();
		assertThat(receivedItems).containsExactly("item1");

		// Verify: the debouncer thread should exist right after consumption
		final long threadCountBefore = countThreadsByName(debouncerName);
		assertThat(threadCountBefore).isGreaterThanOrEqualTo(1);

		// Shutdown the debouncer and wait for the core thread to time out (allowCoreThreadTimeOut=true, keepAlive=60s)
		// We can't wait 60s in a test, so we verify the executor honors shutdown + allowCoreThreadTimeOut
		debouncer.shutdown();

		// After shutdown with allowCoreThreadTimeOut(true), the idle thread should terminate.
		// The thread should die within a few seconds since shutdown() + allowCoreThreadTimeOut are both set.
		Thread.sleep(2000);
		final long threadCountAfter = countThreadsByName(debouncerName);
		assertThat(threadCountAfter)
				.as("debouncer thread should have terminated after shutdown + idle timeout")
				.isEqualTo(0);
	}

	@Test
	void multipleInstancesDoNotLeakThreads() throws Exception
	{
		final String debouncerName = "test-multiInstance";
		final int instanceCount = 20;
		final List<Debouncer<String>> debouncers = new ArrayList<>();
		final CountDownLatch allConsumed = new CountDownLatch(instanceCount);

		// Create many debouncers (simulating many ProcessExecutionResult instances)
		for (int i = 0; i < instanceCount; i++)
		{
			final Debouncer<String> debouncer = Debouncer.<String>builder()
					.name(debouncerName)
					.delayInMillis(50)
					.bufferMaxSize(100)
					.consumer(items -> allConsumed.countDown())
					.build();
			debouncer.add("item-" + i);
			debouncers.add(debouncer);
		}

		// Wait for all consumers to fire
		assertThat(allConsumed.await(10, TimeUnit.SECONDS))
				.as("all consumers should have been called")
				.isTrue();

		// Shutdown all
		debouncers.forEach(Debouncer::shutdown);

		// Wait for threads to terminate
		Thread.sleep(2000);
		final long remainingThreads = countThreadsByName(debouncerName);
		assertThat(remainingThreads)
				.as("all debouncer threads should have terminated after shutdown")
				.isEqualTo(0);
	}

	private static long countThreadsByName(final String nameSubstring)
	{
		return Thread.getAllStackTraces().keySet().stream()
				.filter(t -> t.getName().contains(nameSubstring))
				.filter(Thread::isAlive)
				.count();
	}
}
