package de.metas.invoicecandidate.internalbusinesslogic;

import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
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
public class ToInvoiceData
{
	/** Excluding possible receipt quantities with quality issues */
	StockQtyAndUOMQty qtysRaw;

	/** Computed including possible receipt quality issues, not overridden by a possible qtyToInvoice override. */
	StockQtyAndUOMQty qtysCalc;

	StockQtyAndUOMQty qtysEffective;

	Quantity qtyInPriceUom;

	@Builder
	private ToInvoiceData(
			@NonNull final StockQtyAndUOMQty qtysRaw,
			@NonNull final StockQtyAndUOMQty qtysCalc,
			@NonNull final StockQtyAndUOMQty qtysEffective,
			@NonNull final Quantity qtyInPriceUom)
	{
		this.qtysRaw = qtysRaw;
		this.qtysCalc = qtysCalc;
		this.qtysEffective = qtysEffective;
		this.qtyInPriceUom = qtyInPriceUom;
	}
}
