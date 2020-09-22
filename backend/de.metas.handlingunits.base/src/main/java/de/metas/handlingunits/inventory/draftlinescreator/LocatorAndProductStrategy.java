package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
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

/**
 * Builds up a list of HUs for certain product, locator and warehouse, which have stock
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class LocatorAndProductStrategy implements HUsForInventoryStrategy
{
	// services
	IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	IHUPIAttributesDAO piAttributesDAO = Services.get(IHUPIAttributesDAO.class);
	HuForInventoryLineFactory huForInventoryLineFactory;

	WarehouseId warehouseId;
	LocatorId locatorId;
	ProductId productId;
	AttributeSetInstanceId asiId;

	@Builder
	private LocatorAndProductStrategy(
			@NonNull final HuForInventoryLineFactory huForInventoryLineFactory,
			//
			@Nullable final WarehouseId warehouseId,
			@Nullable final LocatorId locatorId,
			@Nullable final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId)
	{
		this.huForInventoryLineFactory = huForInventoryLineFactory;

		this.locatorId = locatorId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;

		if (warehouseId != null && locatorId != null)
		{
			Check.errorUnless(warehouseId.equals(locatorId.getWarehouseId()),
					"If both a warehouse and locator are specified, then the locator's WH needs to be the given WH; warehouseId={}; locatorId={}",
					warehouseId, locatorId);
		}
	}

	@Override
	public Stream<HuForInventoryLine> streamHus()
	{
		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder()
				.setOnlyTopLevelHUs()
				.addHUStatusesToInclude(huStatusBL.getQtyOnHandStatuses());

		if (warehouseId != null)
		{
			huQueryBuilder.addOnlyInWarehouseId(warehouseId);
		}
		if (locatorId != null)
		{
			huQueryBuilder.addOnlyInLocatorId(locatorId.getRepoId());
		}
		if (productId != null)
		{
			huQueryBuilder.addOnlyWithProductId(productId);
		}

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
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(asiId);
		if (attributeSet.isEmpty())
		{
			return;
		}

		final PIAttributes piAttributes = piAttributesDAO.retrievePIAttributes(HuPackingInstructionsVersionId.VIRTUAL);

		for (final AttributeId attributeId : attributeSet.getAttributeIds())
		{
			if (piAttributes.hasActiveAttribute(attributeId)
					&& piAttributes.isUseInASI(attributeId))
			{
				final Object value = attributeSet.getValue(attributeId);
				huQueryBuilder.addOnlyWithAttribute(attributeId, value);
			}
		}
	}
}
