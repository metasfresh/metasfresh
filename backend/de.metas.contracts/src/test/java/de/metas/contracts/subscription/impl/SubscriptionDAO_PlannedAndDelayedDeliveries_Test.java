package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.util.Services;
import de.metas.common.util.time.SystemTime;

public class SubscriptionDAO_PlannedAndDelayedDeliveries_Test
{
	private final Timestamp now = SystemTime.asTimestamp();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private I_C_Flatrate_Term term(final String productType, final boolean termActive)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setProductType(productType);
		saveRecord(product);
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setM_Product_ID(product.getM_Product_ID());
		term.setIsActive(termActive);
		saveRecord(term);
		return term;
	}

	private I_C_SubscriptionProgress entry(final I_C_Flatrate_Term term, final String status)
	{
		final I_C_SubscriptionProgress sp = newInstance(I_C_SubscriptionProgress.class);
		sp.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		sp.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		sp.setStatus(status);
		sp.setEventDate(TimeUtil.addDays(now, -1));
		saveRecord(sp);
		return sp;
	}

	private List<Integer> streamIds()
	{
		return Services.get(ISubscriptionDAO.class)
				.streamPlannedAndDelayedDeliveries(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, 2)
				.map(I_C_SubscriptionProgress::getC_SubscriptionProgress_ID)
				.collect(ImmutableList.toImmutableList());
	}

	@Test // TC1 read side: service-product entries are not read
	public void serviceProductEntries_notRead()
	{
		final I_C_Flatrate_Term serviceTerm = term(X_M_Product.PRODUCTTYPE_Service, true);
		entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Planned);
		entry(serviceTerm, X_C_SubscriptionProgress.STATUS_Delayed);
		final I_C_SubscriptionProgress itemEntry = entry(term(X_M_Product.PRODUCTTYPE_Item, true), X_C_SubscriptionProgress.STATUS_Planned);

		assertThat(streamIds()).containsExactly(itemEntry.getC_SubscriptionProgress_ID());
	}

	@Test // REQUIREMENTS 3.1: product-type filter only, no term-active filter
	public void inactiveTermWithItemProduct_stillRead()
	{
		final I_C_SubscriptionProgress itemEntry = entry(term(X_M_Product.PRODUCTTYPE_Item, false), X_C_SubscriptionProgress.STATUS_Delayed);

		assertThat(streamIds()).containsExactly(itemEntry.getC_SubscriptionProgress_ID());
	}

	@Test // TC7(b): paged guaranteed iterator, page size as given
	public void query_isPagedGuaranteedIterator()
	{
		final IQuery<I_C_SubscriptionProgress> query = Services.get(ISubscriptionDAO.class)
				.createPlannedAndDelayedDeliveriesQuery(Env.getCtx(), now, ITrx.TRXNAME_ThreadInherited, 7);

		assertThat(query.<Boolean> getOption(IQuery.OPTION_GuaranteedIteratorRequired)).isTrue();
		assertThat(query.<Integer> getOption(IQuery.OPTION_IteratorBufferSize)).isEqualTo(7);
	}
}
