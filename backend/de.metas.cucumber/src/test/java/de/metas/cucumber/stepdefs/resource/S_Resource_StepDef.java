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

package de.metas.cucumber.stepdefs.resource;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.I_S_Resource_Group;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_S_Resource.COLUMNNAME_S_Resource_ID;

public class S_Resource_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final S_Resource_StepDefData resourceTable;
	private final S_ResourceType_StepDefData resourceTypeTable;
	private final S_Resource_Group_StepDefData resourceGroupTable;

	public S_Resource_StepDef(
			@NonNull final S_Resource_StepDefData resourceTable,
			@NonNull final S_ResourceType_StepDefData resourceTypeTable,
			@NonNull final S_Resource_Group_StepDefData resourceGroupTable)
	{
		this.resourceTable = resourceTable;
		this.resourceTypeTable = resourceTypeTable;
		this.resourceGroupTable = resourceGroupTable;
	}

	@And("load S_Resource:")
	public void load_S_Resource(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_S_Resource_ID);

			final I_S_Resource testResource = InterfaceWrapperHelper.load(resourceId, I_S_Resource.class);
			assertThat(testResource).isNotNull();

			resourceTable.put(resourceIdentifier, testResource);
		}
	}

	@And("metasfresh contains S_Resource:")
	public void create_S_Resource(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String resourceValue = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_S_Resource.COLUMNNAME_Value);

			final String resourceInternalName = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_S_Resource.COLUMNNAME_InternalName);

			final String resourceTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer resourceTypeId = resourceTypeTable.getOptional(resourceTypeIdentifier).map(I_S_ResourceType::getS_ResourceType_ID).orElseGet(() -> Integer.parseInt(resourceTypeIdentifier));

			final I_S_Resource resourceRecord = CoalesceUtil.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_S_Resource.class).addEqualsFilter(I_S_Resource.COLUMNNAME_Value, resourceValue).create().firstOnlyOrNull(I_S_Resource.class), () -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_S_Resource.class));
			assertThat(resourceRecord).isNotNull();

			resourceRecord.setValue(resourceValue);
			resourceRecord.setS_ResourceType_ID(resourceTypeId);
			resourceRecord.setInternalName(resourceInternalName);
			resourceRecord.setName(resourceValue);

			InterfaceWrapperHelper.saveRecord(resourceRecord);

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);

			resourceTable.put(resourceIdentifier, resourceRecord);
		}
	}

	@And("metasfresh contains S_ResourceType:")
	public void create_S_ResourceType(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String resourceTypeValue = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_S_ResourceType.COLUMNNAME_Value);

			final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final int uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code)).getRepoId();

			final I_S_ResourceType resourceTypeRecord = CoalesceUtil.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_S_ResourceType.class)
																							   .addEqualsFilter(I_S_ResourceType.COLUMNNAME_Value, resourceTypeValue)
																							   .create()
																							   .firstOnlyOrNull(I_S_ResourceType.class),
																					   () -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_S_ResourceType.class));
			assertThat(resourceTypeRecord).isNotNull();

			resourceTypeRecord.setValue(resourceTypeValue);
			resourceTypeRecord.setName(resourceTypeValue);
			resourceTypeRecord.setC_UOM_ID(uomId);
			resourceTypeRecord.setM_Product_Category_ID(StepDefConstants.PRODUCT_CATEGORY_STANDARD_ID.getRepoId());

			InterfaceWrapperHelper.saveRecord(resourceTypeRecord);

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_ResourceType.COLUMNNAME_S_ResourceType_ID + "." + TABLECOLUMN_IDENTIFIER);

			resourceTypeTable.put(resourceIdentifier, resourceTypeRecord);
		}
	}

	@And("metasfresh contains S_Resource_Group:")
	public void create_S_Resource_Group(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource_Group.COLUMNNAME_Name);

			final String durationUnit = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource_Group.COLUMNNAME_DurationUnit);

			final I_S_Resource_Group resourceGroupRecord = CoalesceUtil.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_S_Resource_Group.class)
																								  .addEqualsFilter(I_S_Resource_Group.COLUMNNAME_Name, name)
																								  .create()
																								  .firstOnlyOrNull(I_S_Resource_Group.class),
																						  () -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_S_Resource_Group.class));
			assertThat(resourceGroupRecord).isNotNull();

			resourceGroupRecord.setName(name);
			resourceGroupRecord.setDurationUnit(durationUnit);
			resourceGroupRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			resourceGroupRecord.setM_Product_Category_ID(StepDefConstants.PRODUCT_CATEGORY_STANDARD_ID.getRepoId());

			InterfaceWrapperHelper.saveRecord(resourceGroupRecord);

			final String resourceGroupIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource_Group.COLUMNNAME_S_Resource_Group_ID + "." + TABLECOLUMN_IDENTIFIER);

			resourceGroupTable.put(resourceGroupIdentifier, resourceGroupRecord);
		}
	}
}
