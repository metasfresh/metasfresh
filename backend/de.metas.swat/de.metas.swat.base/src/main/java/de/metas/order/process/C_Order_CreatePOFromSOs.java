package de.metas.order.process;

import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsDAO;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregator;
import de.metas.order.model.I_C_Order;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * Creates pruchase order(s) from sales order(s).
 * This process is to replace the old org.compiere.process.OrderPOCreate.
 *
 * @author metas-dev <dev@metasfresh.com>
 * Task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
 */
public class C_Order_CreatePOFromSOs
		extends JavaProcess
{
	@Param(parameterName = "DatePromised_From")
	private Timestamp p_DatePromised_From;

	@Param(parameterName = "DatePromised_To")
	private Timestamp p_DatePromised_To;

	@Param(parameterName = "C_BPartner_ID")
	private int p_C_BPartner_ID;

	@Param(parameterName = "Vendor_ID")
	private int p_Vendor_ID;

	@Param(parameterName = "C_Order_ID")
	private int p_C_Order_ID;

	@Param(parameterName = "TypeOfPurchase", mandatory = true)
	private PurchaseTypeEnum p_TypeOfPurchase;

	@Param(parameterName = "poReference")
	private String p_poReference;

	@Param(parameterName = "IsVendorInOrderLinesRequired")
	private boolean p_IsVendorInOrderLinesRequired;

	private final IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO = Services.get(IC_Order_CreatePOFromSOsDAO.class);

	private final IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL = Services.get(IC_Order_CreatePOFromSOsBL.class);

	/**
	 * Task http://dewiki908/mediawiki/index.php/07228_Create_bestellung_from_auftrag_more_than_once_%28100300573628%29
	 */
	private final boolean p_allowMultiplePOOrders = true;

	private final INotificationBL userNotifications = Services.get(INotificationBL.class);

	private static final AdMessageKey MSG_SKIPPED_C_ORDERLINE_IDS = AdMessageKey.of("SkippedOrderLines");

	@Override
	protected String doIt() throws Exception
	{
		final Mutable<Integer> purchaseOrderLineCount = new Mutable<>(0);

		final Iterator<I_C_Order> it = orderCreatePOFromSOsDAO.createSalesOrderIterator(
				this,
				p_allowMultiplePOOrders,
				p_C_Order_ID,
				p_C_BPartner_ID,
				p_Vendor_ID,
				p_poReference,
				p_DatePromised_From,
				p_DatePromised_To,
				p_IsVendorInOrderLinesRequired);

		final String purchaseQtySource = orderCreatePOFromSOsBL.getConfigPurchaseQtySource();
		final CreatePOFromSOsAggregator workpackageAggregator = new CreatePOFromSOsAggregator(this,
																							  purchaseQtySource,
																							  p_TypeOfPurchase);

		workpackageAggregator.setItemAggregationKeyBuilder(new CreatePOFromSOsAggregationKeyBuilder(p_Vendor_ID, this, p_IsVendorInOrderLinesRequired));
		workpackageAggregator.setGroupsBufferSize(100);

		for (final I_C_Order salesOrder : IteratorUtils.asIterable(it))
		{
			final List<I_C_OrderLine> salesOrderLines = orderCreatePOFromSOsDAO.retrieveOrderLines(salesOrder,
																								   p_allowMultiplePOOrders,
																								   purchaseQtySource);
			for (final I_C_OrderLine salesOrderLine : salesOrderLines)
			{
				workpackageAggregator.add(salesOrderLine);
				purchaseOrderLineCount.setValue(purchaseOrderLineCount.getValue() + 1);
			}
		}
		workpackageAggregator.closeAllGroups();

		workpackageAggregator.getSkippedLinesMessage()
				.ifPresent(skippedLinesMessage -> {
					final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
							.recipientUserId(Env.getLoggedUserId())
							.contentADMessage(MSG_SKIPPED_C_ORDERLINE_IDS)
							.contentADMessageParam(skippedLinesMessage)
							.build();
					userNotifications.send(userNotificationRequest);
				});

		return MSG_OK;
	}
}
