package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.RetrieveHUsQtyRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import org.compiere.SpringContextHolder;

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
		implements IProcessPrecondition
{
	private final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);
	private final SalesOrderLineRepository salesOrderLineRepository = SpringContextHolder.instance.getBean(SalesOrderLineRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final SalesOrderLine salesOrderLine = WEBUI_C_OrderLineSO_Util.retrieveSalesOrderLine(getView(), salesOrderLineRepository)
				.orElse(null);
		if (salesOrderLine == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order was set");
		}

		final ProductId productId = salesOrderLine.getProductId();
		final Quantity unreservableQty = retrieveUnreservableQuantity(productId);
		if (unreservableQty.signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No unreservableQty quantity for productId=" + productId);
		}

		return ProcessPreconditionsResolution.accept();
	}

	private Quantity retrieveUnreservableQuantity(final ProductId productId)
	{
		final RetrieveHUsQtyRequest request = WEBUI_C_OrderLineSO_Util.createHuQuantityRequest(
				streamSelectedHUIds(Select.ALL), productId);
		return huReservationService.retrieveUnreservableQty(request);
	}

	@Override
	@RunOutOfTrx // the service we invoke creates its own transaction
	protected String doIt()
	{
		final ImmutableList<HuId> selectedReservedHUs = streamSelectedHUIds(HUEditorRowFilter.ALL)
				.collect(ImmutableList.toImmutableList());

		huReservationService.deleteReservations(selectedReservedHUs);

		return MSG_OK;
	}

}
