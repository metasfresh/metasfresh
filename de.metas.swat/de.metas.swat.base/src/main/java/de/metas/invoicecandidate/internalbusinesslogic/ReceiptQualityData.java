package de.metas.invoicecandidate.internalbusinesslogic;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.lang.Percent;
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
public class ReceiptQualityData
{
	@NonNull
	StockQtyAndUOMQty qtysWithIssues;

	@NonNull
	StockQtyAndUOMQty qtysTotal;


	public StockQtyAndUOMQty computeQtysWithIssuesEffective(@Nullable final Percent qualityDiscountOverride)
	{
		if (qualityDiscountOverride == null)
		{
			return qtysWithIssues;
		}

		final Quantity qtyWithIssues = qtysWithIssues.getUOMQtyOpt().get();
		final Quantity qtyWithcIssuesInStockUom = qtysWithIssues.getStockQty();

		final BigDecimal qtyWithIssuesEffective = qualityDiscountOverride.multiply(
				qtyWithIssues.toBigDecimal(),
				qtyWithIssues.getUOM().getStdPrecision());

		final BigDecimal qtyWithIssuesInStockUomEffective = qualityDiscountOverride.multiply(
				qtyWithcIssuesInStockUom.toBigDecimal(),
				qtyWithcIssuesInStockUom.getUOM().getStdPrecision());

		return StockQtyAndUOMQtys.create(
				qtysWithIssues.getProductId(), qtyWithIssuesInStockUomEffective,
				qtyWithIssues.getUomId(), qtyWithIssuesEffective);
	}



	public StockQtyAndUOMQty computeInvoicableQtyDelivered(@Nullable final Percent qualityDiscountOverride)
	{
		final StockQtyAndUOMQty qtysWithIssuesEffective = computeQtysWithIssuesEffective(qualityDiscountOverride);
		return qtysTotal.subtract(qtysWithIssuesEffective);
	}



	private ReceiptQualityData(
			@NonNull final StockQtyAndUOMQty qtysWithIssues,
			@NonNull final StockQtyAndUOMQty qtysTotal)
	{
		this.qtysWithIssues = qtysWithIssues;
		this.qtysTotal = qtysTotal;

		StockQtyAndUOMQtys.assumeCommonProductAndUom(qtysWithIssues, qtysTotal);
	}


}
