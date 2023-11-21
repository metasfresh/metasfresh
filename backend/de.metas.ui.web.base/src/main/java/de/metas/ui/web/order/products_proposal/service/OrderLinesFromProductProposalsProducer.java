package de.metas.ui.web.order.products_proposal.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.HUPackingAwareCopy.ASICopyMode;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.product.IProductBL;
import de.metas.ui.web.order.products_proposal.model.ProductProposalPrice;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRow;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public final class OrderLinesFromProductProposalsProducer
{
	private static final Logger logger = LogManager.getLogger(OrderLinesFromProductProposalsProducer.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final OrderId orderId;
	private final ImmutableList<ProductsProposalRow> rows;

	@Builder
	private OrderLinesFromProductProposalsProducer(
			@NonNull final OrderId orderId,
			@NonNull final List<ProductsProposalRow> rows)
	{
		this.orderId = orderId;
		this.rows = ImmutableList.copyOf(rows);
	}

	public void produce()
	{
		if (rows.isEmpty())
		{
			return;
		}

		trxManager.runInNewTrx(this::produceInTrx);
	}

	private void produceInTrx()
	{
		final Properties ctx = Env.getCtx();
		final I_C_Order order = ordersRepo.getById(orderId);

		final ImmutableMap<OrderLineId, I_C_OrderLine> existingOrderLines = Maps.uniqueIndex(
				ordersRepo.retrieveOrderLines(orderId, I_C_OrderLine.class),
				orderLineRecord -> OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID()));

		for (final ProductsProposalRow row : rows)
		{
			final I_C_OrderLine existingOrderLine = row.getExistingOrderLineId() != null
					? existingOrderLines.get(row.getExistingOrderLineId())
					: null;
			if (existingOrderLine == null)
			{
				if (row.isQtySet())
				{
					OrderFastInput.addOrderLine(ctx, order, orderLine -> updateOrderLine(order, orderLine, row));
				}
				else
				{
					// if qty is not set, don't create the row
				}
			}
			else
			{
				if (row.isQtySet())
				{
					updateOrderLine(order, existingOrderLine, row);
					ordersRepo.save(existingOrderLine);
				}
				else
				{
					ordersRepo.delete(existingOrderLine);
				}
			}
		}
	}

	private void updateOrderLine(
			final I_C_Order order,
			final I_C_OrderLine newOrderLineRecord,
			final ProductsProposalRow fromRow)
	{
		final IHUPackingAware rowPackingAware = createHUPackingAware(order, fromRow);
		final IHUPackingAware orderLinePackingAware = OrderLineHUPackingAware.of(newOrderLineRecord);

		huPackingAwareBL.prepareCopyFrom(rowPackingAware)
				.overridePartner(false)
				.asiCopyMode(ASICopyMode.Clone)
				.copyTo(orderLinePackingAware);

		final ProductProposalPrice price = fromRow.getPrice();
		// IMPORTANT: manual price is always true because we want to make sure the price the sales guy saw in proposals list is the price which gets into order line
		newOrderLineRecord.setIsManualPrice(true);
		newOrderLineRecord.setPriceEntered(price.getUserEnteredPriceValue());
		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
				.orderLine(newOrderLineRecord)
				.resultUOM(ResultUOM.PRICE_UOM)
				.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
				.updateLineNetAmt(true)
				.updateProfitPriceActual(true)
				.build());

		newOrderLineRecord.setDescription(fromRow.getDescription());
	}

	private IHUPackingAware createHUPackingAware(
			@NonNull final I_C_Order order,
			@NonNull final ProductsProposalRow fromRow)
	{
		final PlainHUPackingAware huPackingAware = createAndInitHUPackingAware(order, fromRow);

		final BigDecimal qty = fromRow.getQty();
		if (qty == null || qty.signum() <= 0)
		{
			throw new AdempiereException("Qty shall be greather than zero"); // TODO trl
		}

		huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(huPackingAware, qty);
		validateNewHUPackingAware(huPackingAware);

		return huPackingAware;
	}

	private PlainHUPackingAware createAndInitHUPackingAware(
			@NonNull final I_C_Order order,
			@NonNull final ProductsProposalRow fromRow)
	{

		final PlainHUPackingAware huPackingAware = new PlainHUPackingAware();
		huPackingAware.setBpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()));
		huPackingAware.setInDispute(false);

		final UomId uomId = productBL.getStockUOMId(fromRow.getProductId());
		huPackingAware.setProductId(fromRow.getProductId());
		huPackingAware.setUomId(uomId);
		huPackingAware.setM_AttributeSetInstance_ID(AttributeSetInstanceId.toRepoId(fromRow.getAsiId()));
		huPackingAware.setPiItemProductId(fromRow.getPackingMaterialId());

		return huPackingAware;
	}

	private void validateNewHUPackingAware(@NonNull final PlainHUPackingAware huPackingAware)
	{
		if (huPackingAware.getQty() == null || huPackingAware.getQty().signum() <= 0)
		{
			logger.warn("Invalid Qty={} for {}", huPackingAware.getQty(), huPackingAware);
			throw new AdempiereException("Qty shall be greather than zero"); // TODO trl
		}
		if (huPackingAware.getQtyTU() == null || huPackingAware.getQtyTU().signum() <= 0)
		{
			logger.warn("Invalid QtyTU={} for {}", huPackingAware.getQtyTU(), huPackingAware);
			throw new AdempiereException("QtyTU shall be greather than zero"); // TODO trl
		}
	}
}
