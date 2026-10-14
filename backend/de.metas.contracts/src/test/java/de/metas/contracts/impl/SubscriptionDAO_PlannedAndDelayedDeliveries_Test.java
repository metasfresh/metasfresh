package de.metas.contracts.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.SubscriptionProgressTestHelper;

public class SubscriptionDAO_PlannedAndDelayedDeliveries_Test
{
	private final Timestamp now = SystemTime.asTimestamp();
	private AbstractSubscriptionDAO subscriptionDAO;
	private SubscriptionProgressTestHelper helper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		subscriptionDAO = new PlainSubscriptionDAO();
		helper = new SubscriptionProgressTestHelper(now);
	}

	private I_C_SubscriptionProgress entry(final I_C_Flatrate_Term term, final String status)
	{
		return helper.entry(term, status, -1, 0);
	}

	private List<Integer> retrieveIds()
	{
		return Streams.stream(subscriptionDAO.retrievePlannedAndDelayedDeliveries(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, 2))
				.map(I_C_SubscriptionProgress::getC_SubscriptionProgress_ID)
				.collect(ImmutableList.toImmutableList());
	}

	@Test
	public void serviceProductEntries_notRead()
	{
		final I_C_Flatrate_Term serviceTerm = helper.term(X_M_Product.PRODUCTTYPE_Service, true);
		entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Planned);
		entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Delayed);
		final I_C_SubscriptionProgress itemEntry = entry(helper.term(X_M_Product.PRODUCTTYPE_Item, true), X_C_SubscriptionProgress.STATUS_Planned);

		assertThat(retrieveIds()).containsExactly(itemEntry.getC_SubscriptionProgress_ID());
	}

	@Test
	public void inactiveTermWithItemProduct_stillRead()
	{
		final I_C_SubscriptionProgress itemEntry = entry(helper.term(X_M_Product.PRODUCTTYPE_Item, false), X_C_SubscriptionProgress.STATUS_Delayed);

		assertThat(retrieveIds()).containsExactly(itemEntry.getC_SubscriptionProgress_ID());
	}

	@Test
	public void query_isPagedGuaranteedIterator()
	{
		final IQuery<I_C_SubscriptionProgress> query = subscriptionDAO.createPlannedAndDelayedDeliveriesQuery(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, 7);

		assertThat(query.<Boolean> getOption(IQuery.OPTION_GuaranteedIteratorRequired)).isTrue();
		assertThat(query.<Integer> getOption(IQuery.OPTION_IteratorBufferSize)).isEqualTo(7);
	}

	@Test
	public void nonPositivePageSize_isRejected()
	{
		assertThatThrownBy(() -> subscriptionDAO.retrievePlannedAndDelayedDeliveries(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, 0))
				.hasMessageContaining("pageSize");
	}

	@Test
	public void retrieve_returnsAllRowsOrderedByEventDateTermSeqNo()
	{
		final I_C_Flatrate_Term termA = helper.term(X_M_Product.PRODUCTTYPE_Item, true);
		final I_C_Flatrate_Term termB = helper.term(X_M_Product.PRODUCTTYPE_Item, true);

		// inserted out of order
		final I_C_SubscriptionProgress latestB = helper.entry(termB, X_C_SubscriptionProgress.STATUS_Planned, -1, 1);
		final I_C_SubscriptionProgress midASeq2 = helper.entry(termA, X_C_SubscriptionProgress.STATUS_Delayed, -2, 2);
		final I_C_SubscriptionProgress oldest = helper.entry(termB, X_C_SubscriptionProgress.STATUS_Planned, -3, 1);
		final I_C_SubscriptionProgress midASeq1 = helper.entry(termA, X_C_SubscriptionProgress.STATUS_Planned, -2, 1);

		assertThat(retrieveIds()).containsExactly(
				oldest.getC_SubscriptionProgress_ID(),
				midASeq1.getC_SubscriptionProgress_ID(),
				midASeq2.getC_SubscriptionProgress_ID(),
				latestB.getC_SubscriptionProgress_ID());
	}
}
