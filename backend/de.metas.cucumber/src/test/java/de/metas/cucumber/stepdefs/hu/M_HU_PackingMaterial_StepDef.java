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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_C_UOM_Dimension_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Height;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Length;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Width;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AllArgsConstructor
public class M_HU_PackingMaterial_StepDef
{
	private final M_HU_PackingMaterial_StepDefData huPackingMaterialTable;
	private final M_Product_StepDefData productTable;
	private final C_UOM_StepDefData uomTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("metasfresh contains M_HU_PackingMaterial:")
	public void add_M_HU_PackingMaterial(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Optional<I_M_Product> product = Optional.ofNullable(productIdentifier)
					.map(productTable::get);

			final String name = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);

			final I_M_HU_PackingMaterial huPackingMaterial = CoalesceUtil.coalesceSuppliersNotNull(
					() -> {
						final IQueryBuilder<I_M_HU_PackingMaterial> queryBuilder = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class)
								.addEqualsFilter(COLUMNNAME_Name, name);

						product
								.ifPresent(prod -> queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, prod.getM_Product_ID()));

						return queryBuilder.create()
								.firstOnlyOrNull(I_M_HU_PackingMaterial.class);
					},
					() -> {
						final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
						packingMaterial.setName(name);
						product
								.ifPresent(prod -> packingMaterial.setM_Product_ID(prod.getM_Product_ID()));

						saveRecord(packingMaterial);

						return packingMaterial;
					});

			final BigDecimal length = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Length);
			if (length != null)
			{
				huPackingMaterial.setLength(length);
			}
			final BigDecimal width = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Width);
			if (width != null)
			{
				huPackingMaterial.setWidth(width);
			}
			final BigDecimal height = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Height);
			if (height != null)
			{
				huPackingMaterial.setHeight(height);
			}
			final String uomDimensionIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_UOM_Dimension_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (length != null || width != null || height != null)
			{
				assertThat(uomDimensionIdentifier).isNotNull();
			}
			if (uomDimensionIdentifier != null)
			{
				final I_C_UOM uom = uomTable.get(uomDimensionIdentifier);
				assertThat(uom).isNotNull();
				huPackingMaterial.setC_UOM_Dimension_ID(uom.getC_UOM_ID());
			}
			saveRecord(huPackingMaterial);
			final String huPackingMaterialIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PackingMaterial_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPackingMaterialTable.put(huPackingMaterialIdentifier, huPackingMaterial);
		});
	}
}
