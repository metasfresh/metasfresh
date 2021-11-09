/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.pricing.trade_margin;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin;
import de.metas.contracts.commission.model.I_C_Customer_Trade_Margin_Line;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerTradeMarginRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public CustomerTradeMargin getById(@NonNull final CustomerTradeMarginId customerTradeMarginId)
	{
		final I_C_Customer_Trade_Margin record = queryBL.createQueryBuilder(I_C_Customer_Trade_Margin.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Customer_Trade_Margin.COLUMNNAME_C_Customer_Trade_Margin_ID, customerTradeMarginId)
				.create()
				.firstOnlyNotNull(I_C_Customer_Trade_Margin.class);

		return toCustomerTradeMargin(record);
	}

	@NonNull
	private CustomerTradeMargin toCustomerTradeMargin(@NonNull final I_C_Customer_Trade_Margin record)
	{
		final CustomerTradeMarginId customerTradeMarginId = CustomerTradeMarginId.ofRepoId(record.getC_Customer_Trade_Margin_ID());

		return CustomerTradeMargin.builder()
				.customerTradeMarginId(customerTradeMarginId)
				.commissionProductId(ProductId.ofRepoId(record.getCommission_Product_ID()))
				.pointsPrecision(record.getPointsPrecision())
				.name(record.getName())
				.description(record.getDescription())
				.lines(retrieveLinesForTradeMargin(customerTradeMarginId))
				.build();
	}

	@NonNull
	private ImmutableList<CustomerTradeMarginLine> retrieveLinesForTradeMargin(@NonNull final CustomerTradeMarginId customerTradeMarginId)
	{
		return queryBL.createQueryBuilder(I_C_Customer_Trade_Margin_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Customer_Trade_Margin_Line.COLUMNNAME_C_Customer_Trade_Margin_ID, customerTradeMarginId)
				.create()
				.stream()
				.map(this::toCustomerTradeMarginLine)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private CustomerTradeMarginLine toCustomerTradeMarginLine(@NonNull final I_C_Customer_Trade_Margin_Line record)
	{
		final CustomerTradeMarginId customerTradeMarginId = CustomerTradeMarginId.ofRepoId(record.getC_Customer_Trade_Margin_ID());

		return CustomerTradeMarginLine.builder()
				.customerTradeMarginLineId(CustomerTradeMarginLineId.ofRepoId(customerTradeMarginId, record.getC_Customer_Trade_Margin_Line_ID()))
				.customerTradeMarginId(customerTradeMarginId)
				.seqNo(record.getSeqNo())
				.marginPercent(record.getMargin())
				.customerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_Customer_ID()))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(record.getM_Product_Category_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.active(record.isActive())
				.build();
	}
}
