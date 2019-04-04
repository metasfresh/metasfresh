package de.metas.material.cockpit.availableforsales.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.Util.coalesce;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map.Entry;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;

import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult.Quantities;
import de.metas.material.cockpit.availableforsales.model.I_C_OrderLine;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.ColorId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Component
public class AvailableForSalesUtil
{
	private final AvailableForSalesRepository availableForSalesRepository;

	public AvailableForSalesUtil(@NonNull final AvailableForSalesRepository availableForSalesRepository)
	{
		this.availableForSalesRepository = availableForSalesRepository;
	}

	public boolean isOrderEligibleForFeature(@NonNull final I_C_Order orderRecord)
	{
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		if (orderBL.isQuotation(orderRecord))
		{
			return false;
		}
		if (!orderRecord.isSOTrx())
		{
			return false;
		}
		return true;
	}

	public boolean isOrderLineEligibleForFeature(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (orderLineRecord.getQtyOrdered().signum() <= 0)
		{
			return false;
		}
		if (orderLineRecord.getM_Product_ID() <= 0)
		{
			return false;
		}

		final I_M_Product productRecord = orderLineRecord.getM_Product();
		if (!productRecord.isStocked())
		{
			return false;
		}

		return true;
	}

	public List<CheckAvailableForSalesRequest> createRequests(
			@NonNull final I_C_Order orderRecord,
			@NonNull final AvailableForSalesConfig config)
	{
		final ImmutableList.Builder<CheckAvailableForSalesRequest> result = ImmutableList.builder();

		final List<I_C_OrderLine> orderLineRecords = Services.get(IOrderDAO.class).retrieveOrderLines(orderRecord, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLineRecord : orderLineRecords)
		{
			if (isOrderLineEligibleForFeature(orderLineRecord))
			{
				result.add(createRequest(orderLineRecord, config));
			}
		}
		return result.build();
	}

	public CheckAvailableForSalesRequest createRequest(
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final AvailableForSalesConfig config)
	{
		final I_C_Order orderRecord = orderLineRecord.getC_Order();
		final Timestamp preparationDate = coalesce(orderRecord.getPreparationDate(), orderRecord.getDatePromised());

		return CheckAvailableForSalesRequest
				.builder()
				.orderLineId(OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(orderLineRecord.getM_AttributeSetInstance_ID()))
				.preparationDate(preparationDate)
				.insufficientQtyAvailableForSalesColorId(config.getInsufficientQtyAvailableForSalesColorId())
				.shipmentDateLookAheadHours(config.getShipmentDateLookAheadHours())
				.salesOrderLookBehindHours(config.getSalesOrderLookBehindHours())
				.build();
	}

	@Value
	@Builder
	public static class CheckAvailableForSalesRequest
	{
		OrderLineId orderLineId;

		ProductId productId;

		AttributeSetInstanceId attributeSetInstanceId;

		Timestamp preparationDate;

		ColorId insufficientQtyAvailableForSalesColorId;

		int shipmentDateLookAheadHours;

		int salesOrderLookBehindHours;
	}

	public ImmutableMultimap<AvailableForSalesQuery, OrderLineId> createQueries(@NonNull final List<CheckAvailableForSalesRequest> requests)
	{
		final ImmutableMultimap.Builder<AvailableForSalesQuery, OrderLineId> query2OrderLineId = ImmutableMultimap.builder();

		for (final CheckAvailableForSalesRequest request : requests)
		{
			final Instant dateOfInterest = TimeUtil.asInstant(request.getPreparationDate());
			final int productId = request.getProductId().getRepoId();
			final AttributesKey storageAttributesKey = AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(request.getAttributeSetInstanceId())
					.orElse(AttributesKey.NONE);

			final AvailableForSalesQuery availableForSalesQuery = AvailableForSalesQuery
					.builder()
					.dateOfInterest(dateOfInterest)
					.productId(productId)
					.storageAttributesKey(storageAttributesKey)
					.shipmentDateLookAheadHours(request.getShipmentDateLookAheadHours())
					.salesOrderLookBehindHours(request.getSalesOrderLookBehindHours())
					.build();

			query2OrderLineId.put(availableForSalesQuery, request.getOrderLineId());
		}

		return query2OrderLineId.build();
	}

	public void checkAndUpdateOrderLineRecords(@NonNull final List<CheckAvailableForSalesRequest> requests)
	{

		// We cannot use the thread-inherited transaction that would otherwise be used by default.
		// Because when this method is called, it means that the thread-inherited transaction is already committed
		// Therefore, let's create our own trx to work in
		Services.get(ITrxManager.class).run(trx -> {

			retrieveDataAndUpdateOrderLines(requests);

		});
	}

	@VisibleForTesting
	void retrieveDataAndUpdateOrderLines(@NonNull final List<CheckAvailableForSalesRequest> requests)
	{
		final ImmutableMultimap<AvailableForSalesQuery, OrderLineId> //
		query2OrderLineIds = createQueries(requests);

		final ImmutableMap<OrderLineId, CheckAvailableForSalesRequest> //
		orderLineId2Request = Maps.uniqueIndex(requests, CheckAvailableForSalesRequest::getOrderLineId);

		final AvailableForSalesMultiQuery availableForSalesMultiQuery = AvailableForSalesMultiQuery
				.builder()
				.availableForSalesQueries(query2OrderLineIds.keySet())
				.build();

		// in here, the thread-inherited transaction is our *new* not-yet-committed/closed transaction
		final ImmutableMap<OrderLineId, Quantities> //
		qtyIncludingSalesOrderLine = retrieveAvailableQty(query2OrderLineIds, availableForSalesMultiQuery);

		for (final Entry<OrderLineId, Quantities> entry : qtyIncludingSalesOrderLine.entrySet())
		{
			final OrderLineId orderLineId = entry.getKey();
			final Quantities quantities = entry.getValue();

			final CheckAvailableForSalesRequest request = orderLineId2Request.get(orderLineId);
			final ColorId insufficientQtyAvailableForSalesColorId = request.getInsufficientQtyAvailableForSalesColorId();

			updateOrderLineRecord(orderLineId, quantities, insufficientQtyAvailableForSalesColorId);
		}
	}

	private ImmutableMap<OrderLineId, Quantities> retrieveAvailableQty(
			@NonNull final ImmutableMultimap<AvailableForSalesQuery, OrderLineId> query2OrderLineIds,
			@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery)
	{
		final ImmutableMap.Builder<OrderLineId, Quantities> result = ImmutableMap.builder();

		final AvailableForSalesMultiResult multiResult = availableForSalesRepository.getBy(availableForSalesMultiQuery);
		for (final AvailableForSalesResult availableForSalesResult : multiResult.getAvailableForSalesResults())
		{
			final AvailableForSalesQuery query = availableForSalesResult.getAvailableForSalesQuery();
			for (final OrderLineId orderLineId : query2OrderLineIds.get(query))
			{
				result.put(orderLineId, availableForSalesResult.getQuantities());
			}
		}
		return result.build();
	}

	private void updateOrderLineRecord(
			@NonNull final OrderLineId orderLineId,
			@NonNull final Quantities quantities,
			@NonNull final ColorId insufficientQtyAvailableForSalesColorId)
	{
		final I_C_OrderLine salesOrderLineRecord = load(orderLineId, I_C_OrderLine.class);

		// qtyToBeShipped includes the salesOrderLineRecord.getQtyOrdered(). We subtract it again to make it comparable with the orderLine's qtyOrdered.
		final BigDecimal qtyToBeShippedEff = quantities
				.getQtyToBeShipped()
				.subtract(salesOrderLineRecord.getQtyOrdered());

		final BigDecimal newValueInStockingUom = quantities
				.getQtyOnHandStock()
				.subtract(qtyToBeShippedEff);

		final BigDecimal newValue = Services.get(IUOMConversionBL.class)
				.convertFromProductUOM(
						ProductId.ofRepoId(salesOrderLineRecord.getM_Product_ID()),
						UomId.ofRepoId(salesOrderLineRecord.getC_UOM_ID()),
						newValueInStockingUom);
		salesOrderLineRecord.setQtyAvailableForSales(newValue);

		if (newValue.compareTo(salesOrderLineRecord.getQtyOrdered()) < 0)
		{
			salesOrderLineRecord.setInsufficientQtyAvailableForSalesColor_ID(insufficientQtyAvailableForSalesColorId.getRepoId());
		}
		else
		{
			salesOrderLineRecord.setInsufficientQtyAvailableForSalesColor(null);
		}
		saveRecord(salesOrderLineRecord);
	}
}
