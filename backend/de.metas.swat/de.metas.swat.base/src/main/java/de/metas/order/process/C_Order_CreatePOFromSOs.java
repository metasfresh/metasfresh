package de.metas.order.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsDAO;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregationKeyBuilder;
import de.metas.order.createFrom.po_from_so.impl.CreatePOFromSOsAggregator;
import de.metas.order.model.I_C_Order;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.order.BOMExploderCommand;
import de.metas.ui.web.order.OrderLineCandidate;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMDAO;

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

	@Param(parameterName = "IsPurchaseBOMComponents")
	private boolean p_isPurchaseBOMComponents;

	@Param(parameterName="C_Tax_ID")
	private int p_C_Tax_ID;

	private final IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO = Services.get(IC_Order_CreatePOFromSOsDAO.class);

	private final IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL = Services.get(IC_Order_CreatePOFromSOsBL.class);

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final OrderGroupRepository orderGroupsRepo = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);

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
				p_TypeOfPurchase,
				TaxId.ofRepoIdOrNull(p_C_Tax_ID));

		workpackageAggregator.setItemAggregationKeyBuilder(new CreatePOFromSOsAggregationKeyBuilder(p_Vendor_ID, this, p_IsVendorInOrderLinesRequired));
		workpackageAggregator.setGroupsBufferSize(100);

		for (final I_C_Order salesOrder : IteratorUtils.asIterable(it))
		{
			final List<I_C_OrderLine> salesOrderLines = orderCreatePOFromSOsDAO.retrieveOrderLines(salesOrder,
					p_allowMultiplePOOrders,
					purchaseQtySource);
			for (final I_C_OrderLine salesOrderLine : salesOrderLines)
			{
				if (p_isPurchaseBOMComponents)
				{
					final ProductId productId = ProductId.ofRepoId(salesOrderLine.getM_Product_ID());
					if (bomDAO.hasBOMs(productId))
					{
						final List<OrderLineCandidate> orderLines = BOMExploderCommand.builder()
								.initialCandidate(toOrderLineCandidate(salesOrderLine))
								.explodeOnlyComponentType(BOMComponentType.Component)
								.build()
								.execute();
						orderLines.stream()
								.map(orderLine -> this.fromOrderLineCandidate(orderLine, salesOrderLine))
								.forEach(orderLine -> addLineToAggregator(purchaseOrderLineCount, workpackageAggregator, orderLine));
					}
					else
					{
						// not a BOM, don't add it to the aggregator
					}
				}
				else
				{
					addLineToAggregator(purchaseOrderLineCount, workpackageAggregator, salesOrderLine);
				}
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

	private void addLineToAggregator(final Mutable<Integer> purchaseOrderLineCount, final CreatePOFromSOsAggregator workpackageAggregator, final I_C_OrderLine salesOrderLine)
	{
		workpackageAggregator.add(salesOrderLine);
		purchaseOrderLineCount.setValue(purchaseOrderLineCount.getValue() + 1);
	}

	private OrderLineCandidate toOrderLineCandidate(final I_C_OrderLine ol)
	{
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(ol.getM_AttributeSetInstance_ID()));

		return OrderLineCandidate.builder()
				.orderId(OrderId.ofRepoId(ol.getC_Order_ID()))
				.productId(ProductId.ofRepoId(ol.getM_Product_ID()))
				.attributes(attributeSet)
				.qty(Quantitys.of(ol.getQtyEntered(), UomId.ofRepoId(ol.getC_UOM_ID())))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.ofNullableCode(ol.getShipmentAllocation_BestBefore_Policy()))
				.bpartnerId(BPartnerId.ofRepoId(ol.getC_BPartner_ID()))
				.soTrx(SOTrx.SALES)
				.build();
	}

	/**
	 * Returns a "virtual" I_C_OrderLine which represents a single BOM component from:
	 * <ul>
	 *     <li>the given sales order line containing a Product that has a BOM</li>
	 *     <li>the given BOM component</li>
	 * </ul>
	 *
	 * @param candidate         a BOM component
	 * @param bomSalesOrderLine the original BOM product C_OrderLine
	 * @return a NOT SAFE FOR PERSISTING instance of I_C_OrderLine which should only be used for creating a PO OrderLine
	 */
	private @NonNull I_C_OrderLine fromOrderLineCandidate(final @NonNull OrderLineCandidate candidate, @NonNull final I_C_OrderLine bomSalesOrderLine)
	{
		final I_C_OrderLine newOrderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, bomSalesOrderLine);
		PO.copyValues((X_C_OrderLine)bomSalesOrderLine, (X_C_OrderLine)newOrderLine);

		newOrderLine.setM_Product_ID(candidate.getProductId().getRepoId());
		newOrderLine.setC_UOM_ID(candidate.getQty().getUomId().getRepoId());
		newOrderLine.setQtyOrdered(candidate.getQty().toBigDecimal());
		newOrderLine.setQtyReserved(candidate.getQty().toBigDecimal());
		if (candidate.getBestBeforePolicy() != null)
		{
			newOrderLine.setShipmentAllocation_BestBefore_Policy(candidate.getBestBeforePolicy().getCode());
		}
		newOrderLine.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());

		newOrderLine.setExplodedFrom_BOMLine_ID(candidate.getExplodedFromBOMLineId().getRepoId());

		final GroupId compensationGroupId = candidate.getCompensationGroupId();

		if (compensationGroupId != null)
		{
			final Group group = orderGroupsRepo.retrieveGroupIfExists(compensationGroupId);

			final ActivityId groupActivityId = group == null ? null : group.getActivityId();

			newOrderLine.setC_Order_CompensationGroup_ID(compensationGroupId.getOrderCompensationGroupId());
			newOrderLine.setC_Activity_ID(ActivityId.toRepoId(groupActivityId));
		}

		//needed to know to what SO line the resulting PO Lines should be allocated to
		newOrderLine.setC_OrderLine_ID(bomSalesOrderLine.getC_OrderLine_ID());
		return newOrderLine;
	}
}
