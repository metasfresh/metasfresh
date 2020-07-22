package de.metas.inventory;

import java.util.stream.Stream;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum AggregationType
{
	// NOTE to developer: Please keep in sync doc sub types with list reference "C_DocType SubType" {@code AD_Reference_ID=148}

	SINGLE_HU(
			HUAggregationType.SINGLE_HU,
			DocBaseAndSubType.of(
					X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory,
					InventoryDocSubType.SingleHUInventory.getCode())),

	MULTIPLE_HUS(
			HUAggregationType.MULTI_HU,
			DocBaseAndSubType.of(
					X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory,
					InventoryDocSubType.AggregatedHUInventory.getCode()));

	@Getter
	private final HUAggregationType huAggregationType;

	@Getter
	private final DocBaseAndSubType docBaseAndSubType;

	private static ImmutableMap<DocBaseAndSubType, AggregationType> docType2aggregationMode = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(AggregationType::getDocBaseAndSubType, t -> t));

	private AggregationType(
			@NonNull final HUAggregationType huAggregationType,
			@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		this.huAggregationType = huAggregationType;
		this.docBaseAndSubType = docBaseAndSubType;
	}

	public String getHuAggregationTypeCode()
	{
		return getHuAggregationType().getCode();
	}

	public static AggregationType getByDocTypeOrNull(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		return docType2aggregationMode.get(docBaseAndSubType);
	}

}
