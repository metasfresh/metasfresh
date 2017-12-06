package de.metas.ui.web.material.cockpit;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Multimap;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.ui.web.document.filter.DocumentFilter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Repository
public class MaterialCockpitRowRepository
{
	public static final String DIM_SPEC_INTERNAL_NAME = "MRP_Product_Info_ASI_Values";

	private final MaterialCockpitFilters materialCockpitFilters;

	public MaterialCockpitRowRepository(@NonNull final MaterialCockpitFilters materialCockpitFilters)
	{
		this.materialCockpitFilters = materialCockpitFilters;
	}

	@Value
	@VisibleForTesting
	static class ProductIdAndDate
	{
		int productId;
		Timestamp date;
	}

	public List<MaterialCockpitRow> retrieveRows(@NonNull final List<DocumentFilter> filtersToUse)
	{
		final Stream<I_X_MRP_ProductInfo_Detail_MV> stream = materialCockpitFilters
				.createQuery(filtersToUse)
				.stream();

		return loadRowsFromStream(stream);
	}

	@VisibleForTesting
	List<MaterialCockpitRow> loadRowsFromStream(@NonNull final Stream<I_X_MRP_ProductInfo_Detail_MV> stream)
	{
		final Function<I_X_MRP_ProductInfo_Detail_MV, ProductIdAndDate> classifier = //
				record -> new ProductIdAndDate(record.getM_Product_ID(), record.getDateGeneral());

		final Map<ProductIdAndDate, List<I_X_MRP_ProductInfo_Detail_MV>> groups = stream
				.collect(Collectors.groupingBy(classifier));

		final Builder<MaterialCockpitRow> mainRows = ImmutableList.builder();

		for (final Entry<ProductIdAndDate, List<I_X_MRP_ProductInfo_Detail_MV>> group : groups.entrySet())
		{
			final Builder<MaterialCockpitRow> subRows = ImmutableList.builder();
			for (final I_X_MRP_ProductInfo_Detail_MV dbRow : group.getValue())
			{
				final MaterialCockpitRow subRow = MaterialCockpitRow.subRowBuilder()
						.date(dbRow.getDateGeneral())
						.productId(dbRow.getM_Product_ID())
						.dimensionGroupName(dbRow.getASIKey())
						.pmmQtyPromised(dbRow.getPMM_QtyPromised_OnDate())
						.qtyMaterialentnahme(dbRow.getQtyMaterialentnahme())
						.qtyMrp(dbRow.getFresh_QtyMRP())
						.qtyOnHand(dbRow.getQtyOnHand())
						.qtyOrdered(dbRow.getQtyOrdered_OnDate())
						.qtyReserved(dbRow.getQtyReserved_OnDate())
						.build();

				subRows.add(subRow);
			}

			final MaterialCockpitRow mainRow = MaterialCockpitRow.mainRowBuilder()
					.includedRows(subRows.build())
					.build();
			mainRows.add(mainRow);
		}
		return mainRows.build();
	}

	public Multimap<ProductIdAndDate, MaterialCockpitRow> createEmptySubRows(@NonNull final Timestamp asTimestamp)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
