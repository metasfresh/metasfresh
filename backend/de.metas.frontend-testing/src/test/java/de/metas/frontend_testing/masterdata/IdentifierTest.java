package de.metas.frontend_testing.masterdata;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

class IdentifierTest
{
	@Test
	void toUniqueString_isUniqueWithinTheSameMillisecond()
	{
		final Identifier identifier = Identifier.ofString("login");

		final int count = 1000;
		final Set<String> values = new HashSet<>();
		for (int i = 0; i < count; i++)
		{
			values.add(identifier.toUniqueString());
		}

		assertThat(values).hasSize(count);
	}

	@Test
	void toUniqueString_isUniqueAcrossConcurrentRequests() throws Exception
	{
		final Identifier identifier = Identifier.ofString("login");
		final int threads = 8;
		final int perThread = 200;

		final ExecutorService executor = Executors.newFixedThreadPool(threads);
		try
		{
			final List<Callable<List<String>>> tasks = new ArrayList<>();
			for (int t = 0; t < threads; t++)
			{
				tasks.add(() -> {
					final List<String> result = new ArrayList<>();
					for (int i = 0; i < perThread; i++)
					{
						result.add(identifier.toUniqueString());
					}
					return result;
				});
			}

			final Set<String> values = new HashSet<>();
			for (final Future<List<String>> future : executor.invokeAll(tasks))
			{
				values.addAll(future.get());
			}

			assertThat(values).hasSize(threads * perThread);
		}
		finally
		{
			executor.shutdownNow();
		}
	}

	@Test
	void toUniqueString_keepsTheTimestampFormat()
	{
		assertThat(Identifier.ofString("login").toUniqueString())
				.matches("login_\\d{8}T\\d{9}");
	}
}
