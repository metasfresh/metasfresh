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
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_C_UOM_Dimension_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Height;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Length;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Width;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName("M_HU_PackingMaterial_ID")
				.forEach(row -> {
					final ProductId productId = row.getAsOptionalIdentifier(COLUMNNAME_M_Product_ID)
							.map(productTable::getId)
							.orElse(null);

					final ValueAndName valueAndName = row.suggestValueAndName();
					final String name = valueAndName.getName();

					final I_M_HU_PackingMaterial huPackingMaterial = CoalesceUtil.coalesceSuppliersNotNull(
							() -> {
								final IQueryBuilder<I_M_HU_PackingMaterial> queryBuilder = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class)
										.addEqualsFilter(COLUMNNAME_Name, name);

								if (productId != null)
								{
									queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, productId);
								}

								return queryBuilder.create()
										.firstOnlyOrNull(I_M_HU_PackingMaterial.class);
							},
							() -> {
								final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
								packingMaterial.setName(name);
								if (productId != null)
								{
									packingMaterial.setM_Product_ID(productId.getRepoId());
								}

								saveRecord(packingMaterial);

								return packingMaterial;
							});

					row.getAsOptionalBigDecimal(COLUMNNAME_Length).ifPresent(huPackingMaterial::setLength);
					row.getAsOptionalBigDecimal(COLUMNNAME_Width).ifPresent(huPackingMaterial::setWidth);
					row.getAsOptionalBigDecimal(COLUMNNAME_Height).ifPresent(huPackingMaterial::setHeight);
					row.getAsOptionalIdentifier(COLUMNNAME_C_UOM_Dimension_ID)
							.map(uomTable::getId)
							.ifPresent(uomId -> huPackingMaterial.setC_UOM_Dimension_ID(uomId.getRepoId()));
					saveRecord(huPackingMaterial);
					huPackingMaterialTable.put(row.getAsIdentifier(), huPackingMaterial);
				});
	}
}
