package de.metas.cache;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A model save inside a transaction resets the local cache BEFORE commit ({@link CacheMgt#resetLocalNowAndBroadcastOnTrxCommit}).
 * A concurrent loader on another DB connection that runs between that reset and the commit can only see the
 * pre-commit state (READ COMMITTED) - simulated here by a loader returning a "pre-commit" marker.
 * The local cache must therefore be reset again after the commit, like the remote caches are (via the broadcast).
 * <p>
 * Real case: a payment term with two breaks; while break 2 is saved, another thread loads the term with only break 1,
 * and order completion later fails with "Total percent must be exactly 100%".
 */
class CacheMgtResetOnTrxCommitRaceTest
{
	private static final String TABLE_PaymentTerm = "C_PaymentTerm";
	private static final String TABLE_PaymentTerm_Break = "C_PaymentTerm_Break";

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void valueLoadedByAnotherThreadBetweenLocalResetAndCommit_mustNotSurviveTheCommit()
	{
		final CCache<Integer, String> cache = CCache.<Integer, String>builder()
				.tableName(TABLE_PaymentTerm)
				.additionalTableNameToResetFor(TABLE_PaymentTerm_Break)
				.initialCapacity(1)
				.build();

		cache.getOrLoad(0, () -> "before break 2 was saved");

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx(() -> {
			assertThat(trxManager.isActive(trxManager.get(ITrx.TRXNAME_ThreadInherited, OnTrxMissingPolicy.ReturnTrxNone))).as("trx active").isTrue();

			// what ModelCacheInvalidationService does when break 2 is saved in this trx
			CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(
					ITrx.TRXNAME_ThreadInherited,
					CacheInvalidateMultiRequest.fromTableNameAndRecordId(TABLE_PaymentTerm_Break, 2));

			// e.g. an async invoice-candidate worker calling PaymentTermRepository.getById for ANY term
			CompletableFuture.runAsync(() -> cache.getOrLoad(0, () -> "pre-commit view: break 2 not visible")).join();
		});

		// after the commit, the next reader must get a fresh load (which would see break 2)
		final String valueAfterCommit = cache.getOrLoad(0, () -> "post-commit view: both breaks");
		assertThat(valueAfterCommit).isEqualTo("post-commit view: both breaks");
	}
}
