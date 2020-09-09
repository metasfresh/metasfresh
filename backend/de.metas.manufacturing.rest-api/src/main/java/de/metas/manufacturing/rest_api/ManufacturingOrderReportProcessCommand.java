package de.metas.manufacturing.rest_api;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.slf4j.MDC;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.manufacturing.order.exportaudit.ExportTransactionId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ManufacturingOrderReportProcessCommand
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final JsonRequestManufacturingOrdersReport request;

	private ImmutableMap<PPOrderId, I_PP_Order> _orders; // lazy

	@Builder
	private ManufacturingOrderReportProcessCommand(
			@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		this.request = request;
	}

	private static PPOrderId extractPPOrderId(final I_PP_Order record)
	{
		return PPOrderId.ofRepoId(record.getPP_Order_ID());
	}

	public void execute()
	{
		final ExportTransactionId transactionKey = ExportTransactionId.random();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			for (final JsonRequestIssueToManufacturingOrder issue : request.getIssues())
			{
				processIssue(issue);
			}

			for (final JsonRequestReceiveFromManufacturingOrder receipt : request.getReceipts())
			{
				processReceipt(receipt);
			}
		}

	}

	private void processIssue(final JsonRequestIssueToManufacturingOrder issue)
	{
		final PPOrderId orderId = extractPPOrderId(issue);
		final Quantity qtyToIssue = toQuantity(issue.getQtyToIssue());
		if (issue.getHandlingUnits().isEmpty())
		{
			throw new AdempiereException("No HUs specified in " + issue);
		}

		final List<I_M_HU> hus = resolveHUs(issue.getHandlingUnits());

		huPPOrderBL.createIssueProducer(orderId)
				.fixedQtyToIssue(qtyToIssue)
				.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
				.createIssues(hus);
	}

	private void processReceipt(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		final PPOrderId orderId = extractPPOrderId(receipt);
		final Quantity qtyToReceive = toQuantity(receipt.getQtyToReceive());
		final LocatorId locatorId = getReceiveToLocatorByOrderId(orderId);

		// TODO: reserve the HU for the shipment schedule!
		huPPOrderBL.receivingMainProduct(orderId)
				.movementDate(receipt.getDate())
				.locatorId(locatorId)
				.receiveVHU(qtyToReceive);
	}

	private Quantity toQuantity(final JsonQuantity json)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(json.getUomCode());
		final I_C_UOM uom = uomDAO.getByX12DE355(x12de355);

		return Quantity.of(json.getQty(), uom);
	}

	private LocatorId getReceiveToLocatorByOrderId(final PPOrderId orderId)
	{
		final I_PP_Order order = getOrderById(orderId);
		return LocatorId.ofRepoId(order.getM_Warehouse_ID(), order.getM_Locator_ID());
	}

	private I_PP_Order getOrderById(final PPOrderId orderId)
	{
		if (_orders == null)
		{
			_orders = Maps.uniqueIndex(
					huPPOrderBL.getByIds(getOrderIds()),
					order -> extractPPOrderId(order));
		}

		final I_PP_Order order = _orders.get(orderId);
		Check.assumeNotNull(order, "{} exists in {}", orderId, _orders);

		return order;
	}

	private Set<PPOrderId> getOrderIds()
	{
		return Stream.concat(
				request.getReceipts().stream().map(receipt -> extractPPOrderId(receipt)),
				request.getIssues().stream().map(issue -> extractPPOrderId(issue)))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		return PPOrderId.ofRepoId(receipt.getOrderId().getValue());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestIssueToManufacturingOrder issue)
	{
		return PPOrderId.ofRepoId(issue.getOrderId().getValue());
	}

	private List<I_M_HU> resolveHUs(@NonNull final Collection<JsonRequestHULookup> requests)
	{
		final ImmutableSet<HuId> huIds = requests.stream()
				.flatMap(request -> resolveHUId(request).stream())
				.collect(ImmutableSet.toImmutableSet());

		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);
		if (hus.isEmpty())
		{
			throw new AdempiereException("No HUs found for " + requests);
		}

		return hus;
	}

	private Set<HuId> resolveHUId(@NonNull final JsonRequestHULookup request)
	{
		final Set<HuId> huIds = handlingUnitsBL.createHUQueryBuilder()
				.addOnlyWithAttribute(AttributeConstants.ATTR_LotNumber, request.getLotNumber())
				.addOnlyWithAttribute(AttributeConstants.ATTR_BestBeforeDate, request.getBestBeforeDate())
				.listIds();
		if (huIds.isEmpty())
		{
			throw new AdempiereException("No HU found for " + request);
		}

		return huIds;
	}
}
