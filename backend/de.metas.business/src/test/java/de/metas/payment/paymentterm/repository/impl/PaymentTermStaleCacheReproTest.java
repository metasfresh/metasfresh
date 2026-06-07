/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.payment.paymentterm.repository.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.X_C_PaymentTerm_Break;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deterministic reproduction of the stale payment-term cache defect (de-flake split-payment).
 *
 * <p>The split-payment cucumber family flakes because pay-schedule creation reads the payment term
 * through the process-wide {@link PaymentTermRepository} cache (a single all-or-nothing snapshot of every
 * term + its breaks). Under concurrency a reader can populate that snapshot during the window between the
 * two break INSERTs, caching a term with only the LC break; the snapshot then survives the cache reset.
 * {@code PaymentTerm.spreadByBreaks} gives the sole/last break the full remainder, so the LC line gets the
 * whole order total (10000) instead of its 30% share (3000).
 *
 * <p>This test reproduces that stale-cache outcome <b>deterministically</b>, without the timing race:
 * it poisons the shared cache to an {@code [LC]}-only snapshot while the DB holds <b>both</b> breaks, then
 * shows the two read paths diverge:
 * <ul>
 *     <li>the cached {@code getById} keeps returning the stale {@code [LC]}-only term (LC DueAmt = 10000),</li>
 *     <li>the fix's {@code getByIdInTrx} (a fresh in-trx load that {@code OrderPayScheduleService.extractContext}
 *         now uses) reads the committed DB and returns both breaks (LC DueAmt = 3000).</li>
 * </ul>
 * Asserting LC DueAmt = 3000 on the {@code getByIdInTrx} path is the RED→GREEN gate: before the fix
 * {@code extractContext} used {@code getById} and would see 10000; after the fix it uses {@code getByIdInTrx}
 * and sees 3000.
 */
public class PaymentTermStaleCacheReproTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final Money GRAND_TOTAL = Money.of("10000.00", EUR);
	private static final CurrencyPrecision PRECISION = CurrencyPrecision.TWO;

	private PaymentTermRepository repo;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repo = new PaymentTermRepository();
	}

	@Test
	public void staleCachedTerm_spreadsWrong_freshInTrxLoad_spreadsRight()
	{
		// 1. Create the complex term pt_lc + its LC 30% break (active). DB has only the LC break so far.
		final PaymentTermId ptId = createComplexTerm("pt_lc");
		createBreak(ptId, X_C_PaymentTerm_Break.REFERENCEDATETYPE_LCDate, 30, 10, true);

		// 1b. Create the OD 70% break but INACTIVE — so the loader's active-records filter ignores it for now.
		//     This lets us populate the shared cache with an [LC]-only snapshot deterministically, simulating
		//     the inter-INSERT window where only the LC break is visible.
		final I_C_PaymentTerm_Break odBreak =
				createBreak(ptId, X_C_PaymentTerm_Break.REFERENCEDATETYPE_OrderDate, 70, 20, false);

		// 2. Populate the process-wide cache with the [LC]-only snapshot.
		final PaymentTerm cachedSnapshot = repo.getById(ptId);
		assertThat(cachedSnapshot.getSortedBreaks()).as("cache poisoned to [LC]-only").hasSize(1);

		// 3. Make the OD break visible in the DB WITHOUT going through saveRecord() — a saveRecord would reset
		//    the cache and re-load both breaks, masking the staleness. We flip IsActive on the live stored
		//    record, leaving the cache untouched. This reproduces the real-world state: the DB now holds BOTH
		//    breaks, but the shared cache still holds the partial [LC]-only snapshot.
		activateInStoreWithoutCacheReset(odBreak);

		// 4a. STALE PATH (what extractContext used BEFORE the fix): cached getById still returns [LC]-only,
		//     and spreadByBreaks gives the sole LC break the FULL amount → LC DueAmt = 10000 (the flake).
		final PaymentTerm stale = repo.getById(ptId);
		assertThat(stale.getSortedBreaks()).as("stale cached read still [LC]-only").hasSize(1);
		final Money staleLcDueAmt = lcDueAmt(stale);
		assertThat(staleLcDueAmt).as("stale cached term mis-spreads LC to the full total").isEqualTo(Money.of("10000.00", EUR));

		// 4b. FIXED PATH (what extractContext uses AFTER the fix): a fresh in-trx load reads the committed DB,
		//     sees BOTH breaks, and spreadByBreaks gives LC its 30% share → LC DueAmt = 3000.
		final PaymentTerm fresh = repo.getByIdInTrx(ptId);
		assertThat(fresh.getSortedBreaks()).as("fresh in-trx load sees both breaks").hasSize(2);
		final Money freshLcDueAmt = lcDueAmt(fresh);
		assertThat(freshLcDueAmt).as("fresh in-trx term spreads LC to its 30% share").isEqualTo(Money.of("3000.00", EUR));
	}

	/** LC line's spread amount = the break whose reference-date-type is LetterOfCreditDate. */
	private Money lcDueAmt(final PaymentTerm term)
	{
		final ImmutableMap<PaymentTermBreakId, Money> byBreakId = term.spreadByBreaks(GRAND_TOTAL, PRECISION);
		return term.getSortedBreaks().stream()
				.filter(b -> b.getReferenceDateType().isLetterOfCreditDate())
				.map(b -> byBreakId.get(b.getId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no LC break found in " + term));
	}

	private PaymentTermId createComplexTerm(final String value)
	{
		final I_C_PaymentTerm pt = newInstance(I_C_PaymentTerm.class);
		pt.setValue(value);
		pt.setName(value);
		pt.setIsComplex(true);
		pt.setDiscount(BigDecimal.ZERO);
		pt.setDiscount2(BigDecimal.ZERO);
		saveRecord(pt);
		return PaymentTermId.ofRepoId(pt.getC_PaymentTerm_ID());
	}

	private I_C_PaymentTerm_Break createBreak(
			final PaymentTermId ptId,
			final String referenceDateType,
			final int percent,
			final int seqNo,
			final boolean active)
	{
		final I_C_PaymentTerm_Break b = newInstance(I_C_PaymentTerm_Break.class);
		b.setC_PaymentTerm_ID(ptId.getRepoId());
		b.setReferenceDateType(referenceDateType);
		b.setPercent(percent);
		b.setSeqNo(seqNo);
		b.setIsActive(active);
		saveRecord(b);
		return b;
	}

	/**
	 * Flips IsActive=Y on the LIVE stored record in {@link POJOLookupMap}, bypassing
	 * {@code saveRecord()} (which would trigger a {@code CacheMgt.reset} and evict the poisoned snapshot).
	 * This is the test-only seam that reproduces "DB has both breaks, but the shared cache is stale".
	 */
	private void activateInStoreWithoutCacheReset(final I_C_PaymentTerm_Break breakRecord)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		try
		{
			final Field cachedObjectsField = POJOLookupMap.class.getDeclaredField("cachedObjects");
			cachedObjectsField.setAccessible(true);
			@SuppressWarnings("unchecked")
			final Map<String, Map<Integer, Object>> cachedObjects =
					(Map<String, Map<Integer, Object>>)cachedObjectsField.get(db);

			final Map<Integer, Object> breakRecords = cachedObjects.get(I_C_PaymentTerm_Break.Table_Name);
			final Object liveRecord = breakRecords.get(breakRecord.getC_PaymentTerm_Break_ID());
			InterfaceWrapperHelper.setValue(liveRecord, I_C_PaymentTerm_Break.COLUMNNAME_IsActive, true);
		}
		catch (final ReflectiveOperationException e)
		{
			throw new RuntimeException("Failed to activate break record in-store without cache reset", e);
		}
	}
}
