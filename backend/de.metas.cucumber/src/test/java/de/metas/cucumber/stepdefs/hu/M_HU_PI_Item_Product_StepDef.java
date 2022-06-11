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
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.PCE_UOM_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_IsActive;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_Item_Product_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_M_HU_PI_Item> huPiItemTable;
	private final StepDefData<I_M_HU_PI_Item_Product> huPiItemProductTable;

	public M_HU_PI_Item_Product_StepDef(
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_M_HU_PI_Item> huPiItemTable,
			@NonNull final StepDefData<I_M_HU_PI_Item_Product> huPiItemProductTable)
	{
		this.productTable = productTable;
		this.huPiItemTable = huPiItemTable;
		this.huPiItemProductTable = huPiItemProductTable;
	}

	@And("metasfresh contains M_HU_PI_Item_Product:")
	public void add_M_HU_PI_Item_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String huPiItemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI_Item huPiItem = huPiItemTable.get(huPiItemIdentifier);

			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Qty);
			final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, COLUMNNAME_ValidFrom);
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, COLUMNNAME_IsActive, true);

			final I_M_HU_PI_Item_Product existingHuPiItemProduct = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
					.addEqualsFilter(COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.addEqualsFilter(COLUMNNAME_M_HU_PI_Item_ID, huPiItem.getM_HU_PI_Item_ID())
					.addEqualsFilter(COLUMNNAME_Qty, qty)
					.addEqualsFilter(COLUMNNAME_IsActive, active)
					.create()
					.firstOnlyOrNull(I_M_HU_PI_Item_Product.class);

			final I_M_HU_PI_Item_Product huPiItemProductRecord = CoalesceUtil.coalesceSuppliers(() -> existingHuPiItemProduct,
																								() -> InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class));
			assertThat(huPiItemProductRecord).isNotNull();

			huPiItemProductRecord.setM_Product_ID(product.getM_Product_ID());
			huPiItemProductRecord.setM_HU_PI_Item_ID(huPiItem.getM_HU_PI_Item_ID());
			huPiItemProductRecord.setQty(qty);
			huPiItemProductRecord.setValidFrom(validFrom);
			huPiItemProductRecord.setC_UOM_ID(PCE_UOM_ID.getRepoId());
			huPiItemProductRecord.setIsActive(active);

			saveRecord(huPiItemProductRecord);

			final String huPiItemProductIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPiItemProductTable.put(huPiItemProductIdentifier, huPiItemProductRecord);
		}
	}
}
