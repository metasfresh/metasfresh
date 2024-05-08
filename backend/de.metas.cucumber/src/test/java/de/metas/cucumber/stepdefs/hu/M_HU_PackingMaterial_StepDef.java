/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_HU_PackingMaterial_StepDef
{
	private final M_HU_PackingMaterial_StepDefData huPackingMaterialTable;
	private final M_Product_StepDefData productTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_HU_PackingMaterial_StepDef(
			@NonNull final M_HU_PackingMaterial_StepDefData huPackingMaterialTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.huPackingMaterialTable = huPackingMaterialTable;
		this.productTable = productTable;
	}

	@And("metasfresh contains M_HU_PackingMaterial:")
	public void add_M_HU_PackingMaterial(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = Optional.ofNullable(productIdentifier)
					.map(productTable::get)
					.orElse(null);

			final String name = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);

			final I_M_HU_PackingMaterial huPackingMaterial = CoalesceUtil.coalesceSuppliersNotNull(
					() -> {
						final IQueryBuilder<I_M_HU_PackingMaterial> queryBuilder = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class)
								.addEqualsFilter(COLUMNNAME_Name, name);

						Optional.ofNullable(product)
								.ifPresent(prod -> queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, prod.getM_Product_ID()));

						return queryBuilder.create()
								.firstOnlyOrNull(I_M_HU_PackingMaterial.class);
					},
					() -> {
						final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
						packingMaterial.setName(name);
						Optional.ofNullable(product)
								.ifPresent(prod -> packingMaterial.setM_Product_ID(product.getM_Product_ID()));

						saveRecord(packingMaterial);

						return packingMaterial;
					});

			final String huPackingMaterialIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPackingMaterialTable.put(huPackingMaterialIdentifier, huPackingMaterial);
		}
	}
}
