package de.metas.handlingunits.inventory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.I_M_Warehouse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.attachments.AttachmentEntry;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.product.ProductId;
import lombok.Builder;
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
public class OldInvetoriesStrategy implements HUsForInventoryStrategy
{
	int maxLocators;

	BigDecimal minimumPrice;

	private OldInvetoriesStrategy(final int maxLocators, final BigDecimal minimumPrice)
	{
		this.maxLocators = maxLocators;
		this.minimumPrice = minimumPrice;
	}

	@Override
	public Stream<I_M_HU> streamHus()
	{
		final ImmutableSetMultimap<Integer, ProductId> productIdsByLocatorId = retrieveInventoryProductIdsByLocatorId();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder().setOnlyTopLevelHUs();

		if (!productIdsByLocatorId.isEmpty())
		{
			final ImmutableSet<Integer> locators = productIdsByLocatorId.keySet();
			final List<WarehouseId> warehouseIds = locators.stream().map(this::mapToWarehouseId)
					.collect(ImmutableList.toImmutableList());

			huQueryBuilder.addOnlyInWarehouseIds(warehouseIds);
			huQueryBuilder.addOnlyInLocatorIds(locators);
			huQueryBuilder.addOnlyWithProductIds(ProductId.toRepoIds(productIdsByLocatorId.values()));

		}

		huQueryBuilder.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses());

		return huQueryBuilder.createQuery().iterateAndStream();
	}

	private ImmutableSetMultimap<Integer, ProductId> retrieveInventoryProductIdsByLocatorId()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_M_Transaction.class).addOnlyActiveRecordsFilter()
				.addNotNull(I_M_Transaction.COLUMN_M_InventoryLine_ID).orderBy()
				.addColumn(I_M_Transaction.COLUMNNAME_M_Locator_ID)
				.addColumnDescending(I_M_Transaction.COLUMNNAME_MovementDate).endOrderBy().create()
				.setLimit(maxLocators)
				.listDistinct(I_M_Transaction.COLUMNNAME_M_Locator_ID, I_M_Transaction.COLUMNNAME_M_Product_ID).stream()
				.map(record -> {
					final ProductId productId = ProductId
							.ofRepoId((int)record.get(I_M_Product.COLUMNNAME_M_Product_ID));
					final int locatorId = (int)record.get(I_M_Locator.COLUMNNAME_M_Locator_ID);
					return GuavaCollectors.entry(locatorId, productId);
				}).collect(GuavaCollectors.toImmutableSetMultimap());
	}

	private WarehouseId mapToWarehouseId(final int locatorId)
	{
		final I_M_Locator locator = InterfaceWrapperHelper.load(locatorId, I_M_Locator.class);
		return WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
	}
}
