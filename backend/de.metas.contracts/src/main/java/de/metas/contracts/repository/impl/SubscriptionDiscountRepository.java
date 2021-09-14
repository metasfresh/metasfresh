/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.repository.impl;

import de.metas.contracts.SubscriptionDiscount;
import de.metas.contracts.SubscriptionDiscountLineId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_SubscrDiscount;
import de.metas.contracts.model.I_C_SubscrDiscount_Line;
import de.metas.contracts.model.X_C_SubscrDiscount_Line;
import de.metas.contracts.repository.ISubscriptionDiscountRepository;
import de.metas.contracts.repository.SubscriptionDiscountQuery;
import de.metas.product.model.I_M_Product;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Objects;

@Repository
public class SubscriptionDiscountRepository implements ISubscriptionDiscountRepository
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public SubscriptionDiscount getDiscountOrNull(final SubscriptionDiscountQuery query)
	{

		final Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(query.getOnDate().getTime());
		final int month = 1 + instance.get(Calendar.MONTH);
		final int dayOfMonth = 1 + instance.get(Calendar.DAY_OF_MONTH);
		final IQueryBuilder<I_C_SubscrDiscount> subscrDiscountIdQuery = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID, query.getFlatrateConditionId())
				.andCollect(I_C_Flatrate_Conditions.COLUMN_C_SubscrDiscount_ID, I_C_SubscrDiscount.class);

		final IQuery<I_M_Product> productQuery = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, query.getProductId())
				.create();

		final I_C_SubscrDiscount_Line lowestSeqNoLine = queryBL.createQueryBuilder(I_C_SubscrDiscount_Line.class)
				.addInSubQueryFilter(I_C_SubscrDiscount_Line.COLUMNNAME_C_SubscrDiscount_ID, I_C_Flatrate_Conditions.COLUMNNAME_C_SubscrDiscount_ID, subscrDiscountIdQuery.create())
				.addOnlyActiveRecordsFilter()
				.filter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
						.addFilter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
								.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_ID, null)
								.addInSubQueryFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productQuery)
								.setJoinOr()
						)
						.addFilter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
								.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_Category_ID, null)
								.addInSubQueryFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_Category_ID, I_M_Product.COLUMNNAME_M_Product_Category_ID, productQuery)
								.setJoinOr()
						)
				)
				.filter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
						.setJoinOr()
						// startMonth null
						.addFilter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
								.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_StartMonth, null)
						)
						// OR startMonth < given month
						.addCompareFilter(I_C_SubscrDiscount_Line.COLUMNNAME_StartMonth, CompareQueryFilter.Operator.LESS_OR_EQUAL, month)
						// OR
						.addFilter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
								// startMonth = given month
								.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_StartMonth, month)
								// AND
								.addFilter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
										.setJoinOr()
										// startDay null
										.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_StartDay, null)
										// OR startDay <= given day
										.addCompareFilter(I_C_SubscrDiscount_Line.COLUMNNAME_StartDay, CompareQueryFilter.Operator.LESS_OR_EQUAL, dayOfMonth)))

				)
				.orderBy(I_C_SubscrDiscount_Line.COLUMNNAME_SeqNo)
				.create()
				.first();

		return lowestSeqNoLine == null ? null : from(lowestSeqNoLine);
	}

	private SubscriptionDiscount from(final I_C_SubscrDiscount_Line lineFromDB)
	{
		return SubscriptionDiscount.builder()
				.discount(Percent.of(lineFromDB.getDiscount()))
				.id(SubscriptionDiscountLineId.ofRepoId(lineFromDB.getC_SubscrDiscount_Line_ID()))
				.prioritiseOwnDiscount(Objects.equals(X_C_SubscrDiscount_Line.IFFOREIGNDISCOUNTSEXIST_Use_Our_Discount, lineFromDB.getIfForeignDiscountsExist()))
				.matchIfTermEndsWithCalendarYear(lineFromDB.isMatchIfTermEndsWithCalendarYear())
				.build();
	}

}
