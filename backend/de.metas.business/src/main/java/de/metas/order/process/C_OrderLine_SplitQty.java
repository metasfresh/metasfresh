/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.process;

import de.metas.order.OrderLineId;
import de.metas.order.split.OrderLineSplitCommand;
import de.metas.order.split.OrderLineSplitRequest;
import de.metas.order.split.OrderLineSplitResult;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;

import java.math.BigDecimal;

/**
 * AD_Process: C_OrderLine_SplitQty — Split sales order line into two.
 *
 * me03 #29261. Backed by {@link OrderLineSplitCommand}.
 */
public class C_OrderLine_SplitQty extends JavaProcess
{
	@Param(parameterName = "QtyToSplitOff", mandatory = true)
	private BigDecimal qtyToSplitOff;

	private final OrderLineSplitCommand splitCommand =
			SpringContextHolder.instance.getBean(OrderLineSplitCommand.class);

	@Override
	@NonNull
	protected String doIt()
	{
		final I_C_OrderLine orderLine = getRecord(I_C_OrderLine.class);

		final OrderLineSplitResult result = splitCommand.split(OrderLineSplitRequest.builder()
				.orderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.qtyToSplitOff(qtyToSplitOff)
				.build());

		return "@Success@ — new C_OrderLine_ID = " + result.getNewOrderLineId().getRepoId();
	}
}
