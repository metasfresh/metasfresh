package de.metas.handlingunits.inventory;

import org.compiere.model.X_C_DocType;

import com.google.common.collect.ImmutableMap;

import de.metas.document.DocBaseAndSubType;
import lombok.Getter;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface InventoryLineAggregator
{
	/**
	 * Please keep in sync with
	 * <li>list reference "HUAggregationType" {@code AD_Reference_ID=540976}
	 * <li>list reference "C_DocType SubType" {@code AD_Reference_ID=148}
	 */
	public enum AggregationMode
	{
		SINGLE_HU("S", DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, "SingleHUMaterialInventory")),

		MULTIPLE_HUS("M", DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory, "AggregatedHUMaterialInventory"));

		@Getter
		private final String value;

		@Getter
		private final DocBaseAndSubType docBaseAndSubType;

		private static ImmutableMap<DocBaseAndSubType, AggregationMode> docType2aggregationMode = ImmutableMap
				.<DocBaseAndSubType, AggregationMode> builder()
				.put(SINGLE_HU.getDocBaseAndSubType(), SINGLE_HU)
				.put(MULTIPLE_HUS.getDocBaseAndSubType(), MULTIPLE_HUS)
				.build();

		private AggregationMode(
				String value,
				DocBaseAndSubType docBaseAndSubType)
		{
			this.value = value;
			this.docBaseAndSubType = docBaseAndSubType;
		}

		public static AggregationMode getByDocType(DocBaseAndSubType docBaseAndSubType)
		{
			return docType2aggregationMode.get(docBaseAndSubType);
		}

	}

	InventoryLineAggregationKey createAggregationKey(HuForInventoryLine huForInventoryLine);

	InventoryLineAggregationKey createAggregationKey(InventoryLine inventoryLine);

	AggregationMode getAggregationMode();
}
