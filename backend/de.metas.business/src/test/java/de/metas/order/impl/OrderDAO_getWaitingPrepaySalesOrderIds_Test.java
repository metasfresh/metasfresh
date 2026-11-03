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

package de.metas.order.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.WaitingPrepaySalesOrderQuery;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The partner filter is the only criterion of the query that is tested here; the other criteria are covered by cucumber scenarios.
 */
public class OrderDAO_getWaitingPrepaySalesOrderIds_Test
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final BPartnerId PARTNER_A = BPartnerId.ofRepoId(1);
	private static final BPartnerId PARTNER_B = BPartnerId.ofRepoId(2);

	private IOrderDAO orderDAO;
	private I_C_DocType prepayDocType;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orderDAO = Services.get(IOrderDAO.class);

		prepayDocType = newInstance(I_C_DocType.class);
		prepayDocType.setDocSubType(X_C_DocType.DOCSUBTYPE_PrepayOrder);
		saveRecord(prepayDocType);
	}

	private OrderId createWaitingPrepayOrder(final BPartnerId billPartnerId, final BPartnerId orderingPartnerId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		order.setDocStatus(X_C_Order.DOCSTATUS_WaitingPayment);
		order.setC_DocType_ID(prepayDocType.getC_DocType_ID());
		order.setDocumentNo("0021");
		order.setC_Currency_ID(EUR.getRepoId());
		order.setGrandTotal(new BigDecimal("123.52"));
		order.setBill_BPartner_ID(billPartnerId.getRepoId());
		order.setC_BPartner_ID(orderingPartnerId.getRepoId());
		saveRecord(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private WaitingPrepaySalesOrderQuery.WaitingPrepaySalesOrderQueryBuilder query()
	{
		return WaitingPrepaySalesOrderQuery.builder()
				.docTypeIds(ImmutableSet.of(DocTypeId.ofRepoId(prepayDocType.getC_DocType_ID())))
				.documentNos(ImmutableSet.of("0021"))
				.currencyId(EUR)
				.amount(new BigDecimal("123.52"));
	}

	/** A given partner matches the orders it has as bill partner or ordering partner; without a partner all waiting prepay orders match. */
	@Test
	void partnerGiven_matchesBillPartnerOrOrderingPartner_noPartner_matchesAll()
	{
		final OrderId orderOfAAsBillPartner = createWaitingPrepayOrder(PARTNER_A, PARTNER_B);
		final OrderId orderOfAAsOrderingPartnerOnly = createWaitingPrepayOrder(PARTNER_B, PARTNER_A);
		final OrderId orderOfB = createWaitingPrepayOrder(PARTNER_B, PARTNER_B);

		assertThat(orderDAO.getWaitingPrepaySalesOrderIds(query().bpartnerId(PARTNER_A).build()))
				.containsExactlyInAnyOrder(orderOfAAsBillPartner, orderOfAAsOrderingPartnerOnly);

		assertThat(orderDAO.getWaitingPrepaySalesOrderIds(query().build()))
				.containsExactlyInAnyOrder(orderOfAAsBillPartner, orderOfAAsOrderingPartnerOnly, orderOfB);
	}
}
