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
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_C_UOM_Dimension_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Height;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_IsInvoiceable;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Length;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Name;
import static de.metas.handlingunits.model.I_M_HU_PackingMaterial.COLUMNNAME_Width;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class M_HU_PackingMaterial_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_HU_PackingMaterial_StepDefData huPackingMaterialTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_UOM_StepDefData uomTable;

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference (also accepted: <code>M_HU_PackingMaterial_ID</code> or <code>Name</code>)<br>
	 *   <b>Name</b> — (required) packing-material display name; also used to look up an existing record before creating one<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) the product representing this packing material (e.g. a Karton or Pallet product)<br>
	 *   <b>Length</b>, <b>Width</b>, <b>Height</b> — (optional) physical dimensions; if any is set, <code>C_UOM_Dimension_ID</code> becomes required<br>
	 *   <b>C_UOM_Dimension_ID</b> — (optional, identifier-ref) UOM for length/width/height (e.g. <code>cm</code>)<br>
	 *   <b>IsInvoiceable</b> — (optional) when <code>N</code>, the packing material does NOT generate an invoice candidate
	 *   (<code>HuInOutInvoiceCandidateVetoer</code> blocks it); defaults to <code>Y</code> matching the column DB default
	 * @cucumber.depends StepDefData: M_HU_PackingMaterial_StepDefData, M_Product_StepDefData, C_UOM_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_HU_PackingMaterial:
	 *   | M_HU_PackingMaterial_ID | M_Product_ID      | Name   | Length | Width | Height | C_UOM_Dimension_ID | IsInvoiceable |
	 *   | dhl_pm                  | packing_product_1 | Karton | 30     | 20    | 10     | cm                 | N             |
	 * </pre>
	 */
	@And("metasfresh contains M_HU_PackingMaterial:")
	public void add_M_HU_PackingMaterial(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PackingMaterial_ID)
				.forEach(row -> {
					
					final ProductId productId = row.getAsOptionalIdentifier(COLUMNNAME_M_Product_ID)
							.map(productTable::getId)
							.orElse(null);

					final String name = row.suggestValueAndName().getName();

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

					final Optional<BigDecimal> length = row.getAsOptionalBigDecimal(COLUMNNAME_Length);
					length.ifPresent(huPackingMaterial::setLength);
					final Optional<BigDecimal> width = row.getAsOptionalBigDecimal(COLUMNNAME_Width);
					width.ifPresent(huPackingMaterial::setWidth);

					final Optional<BigDecimal> height = row.getAsOptionalBigDecimal(COLUMNNAME_Height);
					height.ifPresent(huPackingMaterial::setHeight);

					final Optional<StepDefDataIdentifier> uomDimensionIdentifier = row.getAsOptionalIdentifier(COLUMNNAME_C_UOM_Dimension_ID);
					if (length.isPresent() || width.isPresent() || height.isPresent())
					{
						assertThat(uomDimensionIdentifier).as("uomDimensionIdentifier").isPresent();
					}
					if (uomDimensionIdentifier.isPresent())
					{
						final UomId uomId = uomDimensionIdentifier.get().lookupIdIn(uomTable);
						huPackingMaterial.setC_UOM_Dimension_ID(uomId.getRepoId());
					}

					huPackingMaterial.setIsInvoiceable(row.getAsOptionalBoolean(COLUMNNAME_IsInvoiceable).orElseTrue());

					saveRecord(huPackingMaterial);

					row.getAsOptionalIdentifier().ifPresent(identifier -> huPackingMaterialTable.put(identifier, huPackingMaterial));
				});
	}
}
