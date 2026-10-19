package de.metas.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A {@link CCache#getOrLoad} whose load started before a reset/remove must not leave its (stale) result in the cache.
 * <p>
 * Real case: a load reads the DB before a commit, a reset runs after the commit while the load is still running,
 * and the load then stores the old value. For a single-entry, never-expiring cache like {@code CustomColumnRepository}
 * that old value stays until the next change of the underlying table.
 */
class CCacheInFlightLoadResetTest
{
	private static final String KEY = "key";

	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final AtomicReference<String> db = new AtomicReference<>("old");
	private final AtomicInteger loadCount = new AtomicInteger();

	@AfterEach
	void shutdownExecutor()
	{
		executor.shutdownNow();
	}

	/**
	 * Starts a load on another thread which reads the "DB" right away, and then blocks until {@code release} is counted down.
	 */
	private CompletableFuture<String> startBlockedLoad(
			final CCache<String, String> cache,
			final CountDownLatch release) throws InterruptedException
	{
		final CountDownLatch dbRead = new CountDownLatch(1);
		final CompletableFuture<String> result = CompletableFuture.supplyAsync(
				() -> cache.getOrLoad(KEY, () -> {
					loadCount.incrementAndGet();
					final String value = db.get(); // the SELECT's snapshot
					dbRead.countDown();
					assertThat(release.await(10, TimeUnit.SECONDS)).isTrue();
					return value;
				}),
				executor);
		assertThat(dbRead.await(10, TimeUnit.SECONDS)).as("load started").isTrue();
		return result;
	}

	private String getOrLoadFromDb(final CCache<String, String> cache)
	{
		return cache.getOrLoad(KEY, () -> {
			loadCount.incrementAndGet();
			return db.get();
		});
	}

	@Test
	void loadInFlightDuringReset_isReturnedButNotCached() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_reset", 1, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		db.set("new"); // the commit
		cache.reset(); // the after-commit reset, while the load is still running
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).as("the in-flight load still returns what it read").isEqualTo("old");
		assertThat(cache.get(KEY)).as("stale value must not survive the reset").isNull();
		assertThat(getOrLoadFromDb(cache)).isEqualTo("new");
	}

	@Test
	void loadInFlightDuringCacheMgtResetOfTable_isNotCached() throws Exception
	{
		final CCache<String, String> cache = CCache.<String, String>builder()
				.cacheName("CCacheInFlightLoadResetTest_table")
				.tableName("CCacheInFlightLoadResetTest_Table")
				.initialCapacity(1)
				.expireMinutes(CCache.EXPIREMINUTES_Never)
				.build();

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		db.set("new");
		CacheMgt.get().reset("CCacheInFlightLoadResetTest_Table");
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(getOrLoadFromDb(cache)).as("value after the reset").isEqualTo("new");
	}

	@Test
	void loadInFlightDuringRemoveOfSameKey_isNotCached() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_remove", 10, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		db.set("new");
		cache.remove(KEY);
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(cache.get(KEY)).as("stale value must not survive the remove").isNull();
	}

	@Test
	void loadInFlightDuringRemoveOfOtherKey_isCached() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_otherKey", 10, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		cache.remove("otherKey");
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(cache.get(KEY)).as("a remove of another key does not discard this load").isEqualTo("old");
	}

	/**
	 * A reader which waits for the same in-flight load (Guava de-duplicates concurrent loads of one key)
	 * and started after the reset must not get the stale value either.
	 */
	@Test
	void readerJoiningInFlightLoadAfterReset_getsFreshValue() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_joiner", 1, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		db.set("new");
		cache.reset();

		final CompletableFuture<String> reader = CompletableFuture.supplyAsync(() -> getOrLoadFromDb(cache), executor);
		Thread.sleep(300); // let the reader reach the in-flight load
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(reader.get(10, TimeUnit.SECONDS)).as("reader that started after the reset").isEqualTo("new");
		assertThat(cache.get(KEY)).isEqualTo("new");
	}

	@Test
	void noReset_loadIsCachedOnce()
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_noReset", 1, CCache.EXPIREMINUTES_Never);

		assertThat(getOrLoadFromDb(cache)).isEqualTo("old");
		db.set("new");
		assertThat(getOrLoadFromDb(cache)).as("cached").isEqualTo("old");
		assertThat(loadCount.get()).isEqualTo(1);
	}

	@Test
	void noReset_loaderReturningNull_isNotCachedAndReturnsNull()
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_null", 1, CCache.EXPIREMINUTES_Never);

		assertThat(cache.getOrLoad(KEY, () -> null)).isNull();
		assertThat(cache.containsKey(KEY)).isFalse();
	}
}
