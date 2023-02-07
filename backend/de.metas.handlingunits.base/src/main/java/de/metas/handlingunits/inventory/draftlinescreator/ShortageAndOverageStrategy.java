package de.metas.handlingunits.inventory.draftlinescreator;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Stream;

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

/**
 * Builds up a list of HUs for certain product, locator and warehouse, which have stock
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class ShortageAndOverageStrategy implements HUsForInventoryStrategy
{
	// services
	IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull
	HuForInventoryLineFactory huForInventoryLineFactory;

	@NonNull
	WarehouseId warehouseId;
	@NonNull
	LocatorId locatorId;
	@NonNull
	ProductId productId;
	@NonNull
	Collection<HuId> huIds;
	@NonNull
	AttributeSetInstanceId asiId;

	@Builder
	private ShortageAndOverageStrategy(
			@NonNull final HuForInventoryLineFactory huForInventoryLineFactory,
			@NonNull final WarehouseId warehouseId,
			@NonNull final LocatorId locatorId,
			@NonNull final ProductId productId,
			@NonNull final Collection<HuId> huIds,
			@Nullable final AttributeSetInstanceId asiId)
	{
		this.huForInventoryLineFactory = huForInventoryLineFactory;

		this.locatorId = locatorId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.huIds = huIds;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;

		Check.errorUnless(warehouseId.equals(locatorId.getWarehouseId()),
							  "The locator's WH needs to be the given WH; warehouseId={}; locatorId={}",
							  warehouseId, locatorId);

	}

	@Override
	public Stream<HuForInventoryLine> streamHus()
	{
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setOnlyTopLevelHUs()
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setOnlyStockedProducts(true)
				.addOnlyInWarehouseId(warehouseId)
				.addOnlyInLocatorId(locatorId)
				.addOnlyWithProductId(productId)
				.addOnlyHUIds(huIds);

		if (asiId.isRegular())
		{
			appendAttributeFilters(huQueryBuilder);
		}

		return huQueryBuilder
				.createQueryBuilder()
				.clearOrderBys().orderBy(I_M_HU.COLUMNNAME_M_HU_ID)
				.create()
				.iterateAndStream()
				.flatMap(huForInventoryLineFactory::ofHURecord);
	}

	private void appendAttributeFilters(final IHUQueryBuilder huQueryBuilder)
	{
		final ImmutableAttributeSet storageRelevantAttributes = attributeDAO.getImmutableAttributeSetById(asiId)
				.filterOnlyStorageRelevantAttributes();
		if (storageRelevantAttributes.isEmpty())
		{
			return;
		}

		for (final AttributeId attributeId : storageRelevantAttributes.getAttributeIds())
		{
			final Object value = storageRelevantAttributes.getValue(attributeId);
			huQueryBuilder.addOnlyWithAttribute(attributeId, value);
		}
	}
}
