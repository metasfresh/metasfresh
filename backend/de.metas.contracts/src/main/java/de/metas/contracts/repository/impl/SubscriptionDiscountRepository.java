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

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.SubscriptionDiscountLine;
import de.metas.contracts.SubscriptionDiscountLineId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_SubscrDiscount_Line;
import de.metas.contracts.model.X_C_SubscrDiscount_Line;
import de.metas.contracts.repository.ISubscriptionDiscountRepository;
import de.metas.contracts.repository.SubscriptionDiscountQuery;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class SubscriptionDiscountRepository implements ISubscriptionDiscountRepository
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);
	final private IProductDAO productDAO = Services.get(IProductDAO.class);
	final private IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	@Nullable
	public Optional<SubscriptionDiscountLine> getDiscount(final SubscriptionDiscountQuery query)
	{
		final I_C_Flatrate_Conditions flatrateConditions = flatrateDAO.getConditionsById(query.getFlatrateConditionId());
		if (flatrateConditions.getC_SubscrDiscount_ID() <= 0)
		{
			return Optional.empty();
		}
		final ZonedDateTime onDate = query.getOnDate();
		final int month = onDate.getMonthValue();
		final int dayOfMonth = onDate.getDayOfMonth();

		final ProductId productId = query.getProductId();
		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);

		final I_C_SubscrDiscount_Line lowestSeqNoLine = queryBL.createQueryBuilder(I_C_SubscrDiscount_Line.class)
				.addEqualsFilter(I_C_SubscrDiscount_Line.COLUMNNAME_C_SubscrDiscount_ID, flatrateConditions.getC_SubscrDiscount_ID())
				.addOnlyActiveRecordsFilter()
				.filter(queryBL.createCompositeQueryFilter(I_C_SubscrDiscount_Line.class)
						.addInArrayFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_ID, productId, null)
						.addInArrayFilter(I_C_SubscrDiscount_Line.COLUMNNAME_M_Product_Category_ID, productCategoryId, null)
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
		return Optional.ofNullable(from(lowestSeqNoLine));
	}

	@Nullable
	private SubscriptionDiscountLine from(@Nullable final I_C_SubscrDiscount_Line record)
	{
		return record == null ?
				null :
				SubscriptionDiscountLine.builder()
						.discount(Percent.of(record.getDiscount()))
						.id(SubscriptionDiscountLineId.ofRepoId(record.getC_SubscrDiscount_Line_ID()))
						.prioritiseOwnDiscount(Objects.equals(X_C_SubscrDiscount_Line.IFFOREIGNDISCOUNTSEXIST_Use_Our_Discount, record.getIfForeignDiscountsExist()))
						.matchIfTermEndsWithCalendarYear(record.isMatchIfTermEndsWithCalendarYear())
						.build();
	}

}
