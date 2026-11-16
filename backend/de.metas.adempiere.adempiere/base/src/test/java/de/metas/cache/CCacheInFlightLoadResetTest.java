package de.metas.cache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
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
	 * Guava's invalidateAll() leaves an entry which is still loading in place, so a reader which starts after the reset
	 * also waits for the (stale) in-flight load. When that load is discarded, the reader must load again.
	 */
	@Test
	void readerStartingAfterReset_getsFreshValue() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_afterReset", 1, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		db.set("new");
		cache.reset();
		final CompletableFuture<String> reader = CompletableFuture.supplyAsync(() -> getOrLoadFromDb(cache), executor);
		release.countDown(); // the reader's outcome is the same whether it reached the in-flight load before this or not

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(reader.get(10, TimeUnit.SECONDS)).as("reader that started after the reset").isEqualTo("new");
		assertThat(cache.get(KEY)).isEqualTo("new");
	}

	/**
	 * Guava de-duplicates concurrent loads of one key: the reader waits for the in-flight load instead of loading itself.
	 * When that load is discarded because of a reset, the reader must load again instead of getting the stale value.
	 */
	@Test
	void readerWaitingForInFlightLoad_loadsAgainWhenThatLoadIsDiscarded() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_joiner", 1, CCache.EXPIREMINUTES_Never);

		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<String> inFlightLoad = startBlockedLoad(cache, release);

		final CompletableFuture<String> reader = new CompletableFuture<>();
		final Thread readerThread = new Thread(() -> reader.complete(getOrLoadFromDb(cache)), "reader");
		readerThread.start();
		awaitWaiting(readerThread);

		db.set("new");
		cache.reset();
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).isEqualTo("old");
		assertThat(reader.get(10, TimeUnit.SECONDS)).as("reader which waited for the discarded load").isEqualTo("new");
		assertThat(cache.get(KEY)).isEqualTo("new");
		assertThat(loadCount.get()).as("the blocked load + the reader's own load").isEqualTo(2);
	}

	private static void awaitWaiting(final Thread thread) throws InterruptedException
	{
		final long deadline = System.currentTimeMillis() + 10_000;
		while (thread.getState() != Thread.State.WAITING && thread.getState() != Thread.State.TIMED_WAITING)
		{
			assertThat(System.currentTimeMillis()).as("thread %s is waiting", thread.getName()).isLessThan(deadline);
			Thread.sleep(5);
		}
	}

	/**
	 * A loader which itself causes a reset (e.g. it creates a missing record, and the save resets the cache) can't have read stale data
	 * because of that reset, so its value is cached as before.
	 */
	@Test
	void loaderResettingTheCacheItself_valueIsCached()
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_selfReset", 1, CCache.EXPIREMINUTES_Never);

		assertThat(cache.getOrLoad(KEY, () -> {
			cache.reset();
			cache.remove(KEY);
			return "created";
		})).isEqualTo("created");
		assertThat(cache.get(KEY)).isEqualTo("created");
	}

	@Test
	void getAllOrLoad_loadInFlightDuringReset_isReturnedButNotCached() throws Exception
	{
		final CCache<String, String> cache = CCache.newCache("CCacheInFlightLoadResetTest_getAll", 10, CCache.EXPIREMINUTES_Never);

		final CountDownLatch dbRead = new CountDownLatch(1);
		final CountDownLatch release = new CountDownLatch(1);
		final CompletableFuture<Collection<String>> inFlightLoad = CompletableFuture.supplyAsync(
				() -> cache.getAllOrLoad(ImmutableList.of("k1", "k2"), keys -> {
					final String value = db.get();
					dbRead.countDown();
					try
					{
						assertThat(release.await(10, TimeUnit.SECONDS)).isTrue();
					}
					catch (final InterruptedException e)
					{
						throw new RuntimeException(e);
					}
					return keys.stream().collect(ImmutableMap.toImmutableMap(key -> key, key -> value));
				}),
				executor);
		assertThat(dbRead.await(10, TimeUnit.SECONDS)).isTrue();

		db.set("new");
		cache.remove("k1");
		release.countDown();

		assertThat(inFlightLoad.get(10, TimeUnit.SECONDS)).containsExactlyInAnyOrder("old", "old");
		assertThat(cache.get("k1")).as("k1 was removed while loading").isNull();
		assertThat(cache.get("k2")).as("k2 was not reset").isEqualTo("old");
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
