package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.handlingunits.reservation.RetrieveHUsQtyRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{
	@Autowired private HUReservationService huReservationService;
	@Autowired private SalesOrderLineRepository salesOrderLineRepository;

	private final IHUOrderBL huOrderBL = Services.get(IHUOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final static AdMessageKey MSG_HU_CONTAINS_MORE_QUANTITY_THAN_NEEDED = AdMessageKey.of("HUContainsMoreQuantityThanNeeded");
	private final static AdMessageKey MSG_SELECTED_HUS_CONTAIN_OTHER_PRODUCTS = AdMessageKey.of("SelectedHUsContainOtherProducts");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository);
		final ProductId productId = salesOrderLine.getProductId();
		final Quantity reservableQty = retrieveReservableQuantity(productId);
		if (reservableQty.signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No reservable quantity for productId=" + productId);
		}
		//Cannot reserve HU if qty in it is greater than what's needed by the order.
		if (salesOrderLine.getOrderedQty().compareTo(reservableQty) < 0)
		{
			return ProcessPreconditionsResolution.reject(MSG_HU_CONTAINS_MORE_QUANTITY_THAN_NEEDED, reservableQty, salesOrderLine.getOrderedQty());
		}
		//Cannot reserve HU if qty in it is greater than the unreservedQty (orderedQty - sum(reservedQty))
		final Quantity unreservedQty = getUnreservedQty(salesOrderLine, reservableQty);
		if (unreservedQty.isLessThan(reservableQty))
		{
			return ProcessPreconditionsResolution.reject(MSG_HU_CONTAINS_MORE_QUANTITY_THAN_NEEDED, reservableQty, unreservedQty);
		}
		final Set<ProductId> storedProducts = handlingUnitsBL.getStoredProducts(streamSelectedHUIds(Select.ALL).collect(Collectors.toList()));
		if (storedProducts.stream().anyMatch(product -> !product.equals(productId)))
		{
			return ProcessPreconditionsResolution.reject(MSG_SELECTED_HUS_CONTAIN_OTHER_PRODUCTS);
		}

		return ProcessPreconditionsResolution.accept();
	}

	private Quantity getUnreservedQty(final SalesOrderLine salesOrderLine, final Quantity reservableQty)
	{
		final Quantity sumReservedQty = huReservationService.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(salesOrderLine.getId().getOrderLineId()))
				.map(HUReservation::getReservedQtySum)
				.orElse(Quantity.zero(reservableQty.getUOM()));
		return salesOrderLine.getOrderedQty().subtract(sumReservedQty);
	}

	protected Quantity retrieveReservableQuantity(@NonNull final ProductId productId)
	{
		final RetrieveHUsQtyRequest request = WEBUI_C_OrderLineSO_Util.createHuQuantityRequest(
				streamSelectedHUIds(Select.ALL), productId);

		return huReservationService.retrieveReservableQty(request);
	}

	@Override
	protected String doIt()
	{
		final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository);
		final ImmutableList<HuId> selectedHuIds = streamSelectedHUIds(Select.ALL)
				.collect(ImmutableList.toImmutableList());
		if (selectedHuIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}
		final ProductId productId = salesOrderLine.getProductId();
		final Quantity reservableQty = retrieveReservableQuantity(productId);

		final ReserveHUsRequest reservationRequest = ReserveHUsRequest
				.builder()
				.huIds(selectedHuIds)
				.productId(productId)
				.documentRef(HUReservationDocRef.ofSalesOrderLineId(salesOrderLine.getId().getOrderLineId()))
				.qtyToReserve(getUnreservedQty(salesOrderLine, reservableQty))
				.reserveActualHUs(true)
				.build();
		huReservationService.makeReservation(reservationRequest);
		huOrderBL.updateOrderLineFromReservations(salesOrderLine.getId());

		return MSG_OK;
	}

}
