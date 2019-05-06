package de.metas.inventory;

import static de.metas.inventory.InventoryConstants.DocSubType_INVENTORY_WITH_AGGREGATED_HUS;
import static de.metas.inventory.InventoryConstants.DocSubType_INVENTORY_WITH_SINGLE_HU;
import static de.metas.inventory.InventoryConstants.HUAggregationType_MULTI_HU;
import static de.metas.inventory.InventoryConstants.HUAggregationType_SINGLE_HU;

import org.compiere.model.X_C_DocType;

import com.google.common.collect.ImmutableMap;

import de.metas.document.DocBaseAndSubType;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public enum AggregationType
{
	SINGLE_HU(
			HUAggregationType_SINGLE_HU,
			DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, DocSubType_INVENTORY_WITH_SINGLE_HU)),

	MULTIPLE_HUS(
			HUAggregationType_MULTI_HU,
			DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, DocSubType_INVENTORY_WITH_AGGREGATED_HUS));

	@Getter
	private final String refListValue;

	@Getter
	private final DocBaseAndSubType docBaseAndSubType;

	private static ImmutableMap<DocBaseAndSubType, AggregationType> docType2aggregationMode = ImmutableMap
			.<DocBaseAndSubType, AggregationType> builder()
			.put(SINGLE_HU.getDocBaseAndSubType(), SINGLE_HU)
			.put(MULTIPLE_HUS.getDocBaseAndSubType(), MULTIPLE_HUS)
			.build();

	private AggregationType(
			@NonNull final String refListValue,
			@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		this.refListValue = refListValue;
		this.docBaseAndSubType = docBaseAndSubType;
	}

	public static AggregationType getByDocType(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		return docType2aggregationMode.get(docBaseAndSubType);
	}

}