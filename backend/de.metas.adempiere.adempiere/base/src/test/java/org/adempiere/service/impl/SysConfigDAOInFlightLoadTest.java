package org.adempiere.service.impl;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An org-level AD_SysConfig is created while another thread is (re)loading the sysconfig map.
 * <p>
 * Saving an AD_SysConfig in a trx resets the local cache before commit, and the AD_SysConfig interceptors reset it again after commit.
 * The other thread's load started before the commit and is still running when the after-commit reset fires.
 * Its (old) result must not be cached, or the new value would stay invisible until the next AD_SysConfig change.
 */
class SysConfigDAOInFlightLoadTest
{
	private static final String NAME = "SysConfigDAOInFlightLoadTest_Name";
	private static final ClientAndOrgId ORG = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ofRepoId(1000000));
	private static final int SYSCONFIG_ID = 4711;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * The "DB": a load sees the new org-level value only once this is true (READ COMMITTED, snapshot taken when the SELECT starts).
	 */
	private final AtomicBoolean committed = new AtomicBoolean(false);
	@Nullable private volatile CountDownLatch loadStarted = null;
	@Nullable private volatile CountDownLatch releaseLoad = null;

	private SysConfigDAO dao;
	private ITrxManager trxManager;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		dao = new SysConfigDAO()
		{
			@Override
			protected ImmutableListMultimap<String, SysConfigEntryValue> retrieveSysConfigEntryValues()
			{
				final boolean visible = committed.get();
				final CountDownLatch started = loadStarted;
				final CountDownLatch release = releaseLoad;
				if (started != null && release != null)
				{
					started.countDown();
					try
					{
						assertThat(release.await(10, TimeUnit.SECONDS)).isTrue();
					}
					catch (final InterruptedException e)
					{
						throw new RuntimeException(e);
					}
				}

				final ImmutableListMultimap.Builder<String, SysConfigEntryValue> result = ImmutableListMultimap.builder();
				result.put(NAME, SysConfigEntryValue.of("N", ClientAndOrgId.SYSTEM));
				if (visible)
				{
					result.put(NAME, SysConfigEntryValue.of("Y", ORG));
				}
				return result.build();
			}
		};
		assertThat(dao.getValue(NAME, ORG)).contains("N"); // warm the cache
	}

	@AfterEach
	void shutdownExecutor()
	{
		executor.shutdownNow();
	}

	@Test
	void loadInFlightAcrossCommitAndAfterCommitReset_isNotCached() throws Exception
	{
		loadStarted = new CountDownLatch(1);
		releaseLoad = new CountDownLatch(1);

		final CompletableFuture<Optional<String>>[] concurrentRead = new CompletableFuture[1];
		trxManager.runInNewTrx(() -> {
			// what ModelCacheInvalidationService does when the AD_SysConfig is saved in this trx
			CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited,
					CacheInvalidateMultiRequest.fromTableNameAndRecordId(I_AD_SysConfig.Table_Name, SYSCONFIG_ID));

			// another thread starts reloading before the commit
			concurrentRead[0] = CompletableFuture.supplyAsync(() -> dao.getValue(NAME, ORG), executor);
			try
			{
				assertThat(loadStarted.await(10, TimeUnit.SECONDS)).isTrue();
			}
			catch (final InterruptedException e)
			{
				throw new RuntimeException(e);
			}

			trxManager.runAfterCommit(() -> {
				committed.set(true); // the commit itself
				CacheMgt.get().reset(I_AD_SysConfig.Table_Name, SYSCONFIG_ID); // the AD_SysConfig interceptors' after-commit reset
			});
		});
		loadStarted = null;

		// a reader which starts after the commit, and probably waits for the same in-flight load
		final CompletableFuture<Optional<String>> readerAfterCommit = CompletableFuture.supplyAsync(() -> dao.getValue(NAME, ORG), executor);
		Thread.sleep(300);
		releaseLoad.countDown();

		assertThat(concurrentRead[0].get(10, TimeUnit.SECONDS)).as("the in-flight load returns what it read").contains("N");
		assertThat(readerAfterCommit.get(10, TimeUnit.SECONDS)).as("reader which started after the commit").contains("Y");
		assertThat(dao.getValue(NAME, ORG)).as("after the commit").contains("Y");
	}
}
