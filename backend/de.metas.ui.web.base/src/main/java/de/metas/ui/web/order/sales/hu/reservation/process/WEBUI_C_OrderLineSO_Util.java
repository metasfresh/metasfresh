package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.RetrieveHUsQtyRequest;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.ui.web.handlingunits.HUEditorView;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.stream.Stream;

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

@UtilityClass
final class WEBUI_C_OrderLineSO_Util
{
	public Optional<SalesOrderLine> retrieveSalesOrderLine(
			@NonNull final HUEditorView huEditorView,
			@NonNull final SalesOrderLineRepository salesOrderLineRepository)
	{
		final Optional<OrderLineId> orderLineId = huEditorView
				.getParameterAsId(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);

		return orderLineId.map(salesOrderLineRepository::getById);
	}

	public RetrieveHUsQtyRequest createHuQuantityRequest(
			@NonNull final Stream<HuId> selectedHUIdStream,
			@NonNull final ProductId productId)
	{
		final ImmutableList<HuId> selectedHuIds = selectedHUIdStream.collect(ImmutableList.toImmutableList());

		return RetrieveHUsQtyRequest.builder()
				.huIds(selectedHuIds)
				.productId(productId)
				.build();
	}
}
