package de.metas.invoicecandidate.internalbusinesslogic;

import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.invoicecandidate.internalbusinesslogic.OrderedData.OrderedDataBuilder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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

@Value
@Builder
public class OrderedDataLoader
{
	@NonNull
	I_C_Invoice_Candidate invoiceCandidateRecord;

	@NonNull
	UomId stockUomId;

	public OrderedData loadOrderedQtys()
	{
		final UomId icUomId = UomId.ofRepoId(invoiceCandidateRecord.getC_UOM_ID());
		final I_C_Order order = InterfaceWrapperHelper.create(invoiceCandidateRecord.getC_Order(), I_C_Order.class);

		final boolean hasInvalidOrder = null != order && !DocStatus.ofCode(order.getDocStatus()).isCompletedOrClosed();

		final OrderedDataBuilder result = OrderedData.builder();

		if (hasInvalidOrder)
		{
			result.qty(Quantitys.createZero(icUomId));
			result.qtyInStockUom(Quantitys.createZero(stockUomId));
		}
		else
		{
			result.qty(Quantitys.create(invoiceCandidateRecord.getQtyEntered(), icUomId));
			result.qtyInStockUom(Quantitys.create(invoiceCandidateRecord.getQtyOrdered(), stockUomId));
		}

		return result.build();
	}
}
