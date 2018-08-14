package de.metas.handlingunits.inventory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
@Builder
public class OldTransactionsStrategy implements HUsForInventoryStrategy
{
	int maxLocators;
	@NonNull
	BigDecimal minimumPrice;
	@NonNull
	LocalDate movementDate;

	final Map<String, Integer> MOVEMENT_TYPE_ORDERING = ImmutableMap.<String, Integer> builder()
			.put(X_M_Transaction.MOVEMENTTYPE_InventoryIn, 1)
			.put(X_M_Transaction.MOVEMENTTYPE_InventoryOut, 2)
			.put(X_M_Transaction.MOVEMENTTYPE_CustomerShipment, 3)
			.put(X_M_Transaction.MOVEMENTTYPE_CustomerReturns, 4)
			.put(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, 5)
			.put(X_M_Transaction.MOVEMENTTYPE_VendorReturns, 6)
			.put(X_M_Transaction.MOVEMENTTYPE_MovementFrom, 7)
			.put(X_M_Transaction.MOVEMENTTYPE_MovementTo, 8)
			.put(X_M_Transaction.MOVEMENTTYPE_ProductionPlus, 9)
			.put(X_M_Transaction.MOVEMENTTYPE_ProductionMinus, 10)
			.put(X_M_Transaction.MOVEMENTTYPE_WorkOrderPlus, 11)
			.put(X_M_Transaction.MOVEMENTTYPE_WorkOrderMinus, 12)
			.build();

	final Comparator<TransactionContext> TRANSACTIONS_BY_MOVEMENTTYPE_REVERSED_COMPARATOR = //
			Comparator.<TransactionContext, Integer> comparing(transaction -> MOVEMENT_TYPE_ORDERING.get(transaction.getMovementType())).reversed();

	final Comparator<TransactionContext> TRANSACTIONS_BY_MOVEMENDATE_COMPARATOR = //
			Comparator.<TransactionContext, LocalDate> comparing(transaction -> transaction.getMovementDate());

	final Comparator<TransactionContext> TRANSACTIONS_COMPARATOR = TRANSACTIONS_BY_MOVEMENTTYPE_REVERSED_COMPARATOR
			.thenComparing(TRANSACTIONS_BY_MOVEMENDATE_COMPARATOR);

	private OldTransactionsStrategy(final int maxLocators, final BigDecimal minimumPrice, final LocalDate movementDate)
	{
		this.maxLocators = maxLocators;
		this.minimumPrice = minimumPrice;
		this.movementDate = movementDate;
	}

	@Override
	public Stream<I_M_HU> streamHus()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder().setOnlyTopLevelHUs();

		final ImmutableSet<Integer> locatorIds = retrieveLocatorIds();
		if (locatorIds.isEmpty())
		{
			return Stream.empty();
		}

		final ImmutableSet<WarehouseId> warehouseIds = locatorIds.stream().map(this::mapToWarehouseId)
				.collect(ImmutableSet.toImmutableSet());

		huQueryBuilder.addOnlyInWarehouseIds(warehouseIds);
		huQueryBuilder.addOnlyInLocatorIds(locatorIds);

		huQueryBuilder.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses());

		// Order by
		final IQueryOrderBy queryOrderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_HU.class)
				.addColumn(I_M_HU.COLUMNNAME_M_Locator_ID)
				.createQueryOrderBy();

		return huQueryBuilder.createQuery()
				.setOrderBy(queryOrderBy)
				.iterateAndStream();
	}

	@Builder
	@Value
	static class TransactionContext
	{
		final int locatorId;
		final @NonNull ProductId productId;
		final @NonNull String movementType;
		final @NonNull LocalDate movementDate;
	}

	private ImmutableSet<Integer> retrieveLocatorIds()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Transaction> queryBuilder = queryBL.createQueryBuilder(I_M_Transaction.class)
				.addOnlyActiveRecordsFilter();

		if (BigDecimal.ZERO.compareTo(minimumPrice) < 0)
		{
			final Set<Integer> productIds = Services.get(IPriceListDAO.class).retrieveHighPriceProducts(getMinimumPrice(), getMovementDate());
			if (!productIds.isEmpty())
			{
				queryBuilder.addInArrayFilter(I_M_Transaction.COLUMNNAME_M_Product_ID, productIds);
			}
		}

		final ImmutableSetMultimap<Integer, ProductId> productsByLocatorIds = queryBuilder.create()
				.listDistinct(I_M_Transaction.COLUMNNAME_M_Locator_ID, I_M_Transaction.COLUMNNAME_M_Product_ID,
						I_M_Transaction.COLUMNNAME_MovementDate, I_M_Transaction.COLUMNNAME_MovementType)
				.stream()
				.map(record -> {
					return TransactionContext.builder()
							.locatorId((int)record.get(I_M_Locator.COLUMNNAME_M_Locator_ID))
							.productId(ProductId.ofRepoId((int)record.get(I_M_Product.COLUMNNAME_M_Product_ID)))
							.movementType((String)record.get(I_M_Transaction.COLUMNNAME_MovementType))
							.movementDate(TimeUtil.asLocalDate((Timestamp)record.get(I_M_Transaction.COLUMNNAME_MovementDate)))
							.build();
				})
				.sorted(TRANSACTIONS_COMPARATOR)
				.map(transaction -> {
					return GuavaCollectors.entry(transaction.getLocatorId(), transaction.getProductId());
				}).collect(GuavaCollectors.toImmutableSetMultimap());

		return productsByLocatorIds.keySet();
	}

	private WarehouseId mapToWarehouseId(final int locatorId)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.load(locatorId, I_M_Locator.class);
		return WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
	}

	@Override
	public boolean match(final int size)
	{
		return maxLocators == 0 ? true : maxLocators >= size;
	}
}
