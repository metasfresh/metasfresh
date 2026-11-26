package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.RetrieveHUsQtyRequest;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Objects;

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

public class WEBUI_C_OrderLineSO_Delete_HUReservation
		extends HUEditorProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IHUOrderBL huOrderBL = Services.get(IHUOrderBL.class);
	private final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);
	private final SalesOrderLineRepository salesOrderLineRepository = SpringContextHolder.instance.getBean(SalesOrderLineRepository.class);

	private static final String PARAM_QtyCU_Reserved = "QtyCU_Reserved";
	@Param(parameterName = PARAM_QtyCU_Reserved)
	private BigDecimal p_QtyCU_Reserved;

	private static final String PARAM_QtyAvailableCU = "QtyAvailableCU";
	@Param(parameterName = PARAM_QtyAvailableCU)
	private BigDecimal p_QtyAvailableCU;

	private static final String PARAM_QtyToUnreserve = "QtyToUnreserve";
	@Param(parameterName = PARAM_QtyToUnreserve, mandatory = true)
	private BigDecimal p_QtyToUnreserve;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository);
		final ProductId productId = salesOrderLine.getProductId();
		final Quantity unreservableQty = retrieveUnreservableQuantity(productId);
		if (unreservableQty.signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No unreservableQty quantity for productId=" + productId);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();

		if (PARAM_QtyToUnreserve.equals(parameterName) || PARAM_QtyCU_Reserved.equals(parameterName))
		{
			final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository);
			return retrieveUnreservableQuantity(salesOrderLine.getProductId()).toBigDecimal();
		}
		else if (PARAM_QtyAvailableCU.equals(parameterName))
		{
			return streamSelectedRows(HUEditorRowFilter.select(Select.ALL))
					.map(HUEditorRow::getQtyCU)
					.filter(Objects::nonNull)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private Quantity retrieveUnreservableQuantity(final ProductId productId)
	{
		final RetrieveHUsQtyRequest request = WEBUI_C_OrderLineSO_Util.createHuQuantityRequest(
				streamSelectedHUIds(Select.ALL), productId);
		return huReservationService.retrieveActualReservedQty(request);
	}

	@Override
	@RunOutOfTrx // the service we invoke creates its own transaction
	protected String doIt()
	{
		final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository);

		final ImmutableSet<HuId> selectedReservedHUs = streamSelectedHUIds(HUEditorRowFilter.ALL)
				.collect(ImmutableSet.toImmutableSet());

		// reversible = total currently reserved CU of the selected HUs (an HU reservation has no partial-delivery concept)
		final Quantity reversibleQty = retrieveUnreservableQuantity(salesOrderLine.getProductId());
		if (p_QtyToUnreserve == null || p_QtyToUnreserve.signum() <= 0)
		{
			throw new AdempiereException("@QtyToUnreserve@ <= 0");
		}
		final Quantity qtyToUnreserve = Quantity.of(p_QtyToUnreserve, reversibleQty.getUOM());
		if (qtyToUnreserve.compareTo(reversibleQty) > 0)
		{
			throw new AdempiereException("@QtyToUnreserve@ > " + reversibleQty);
		}

		huReservationService.reduceReservation(selectedReservedHUs, qtyToUnreserve);

		huOrderBL.updateOrderLineFromReservations(salesOrderLine.getId());

		return MSG_OK;
	}

}
