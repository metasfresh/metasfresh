/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.productplanning;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMVersions_StepDefData;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.StepDefConstants.TEST_PLANT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.WORKFLOW_ID;
import static org.assertj.core.api.Assertions.*;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Product_Planning_StepDef
{
	private final M_Product_StepDefData productTable;
	private final PP_Product_BOMVersions_StepDefData productBomVersionsTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public PP_Product_Planning_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOMVersions_StepDefData productBomVersionsTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.productTable = productTable;
		this.productBomVersionsTable = productBomVersionsTable;
		this.productPlanningTable = productPlanningTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@Given("metasfresh contains PP_Product_Plannings")
	public void add_PP_Product_Planning(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createPP_Product_Planning(tableRow);
		}
	}

	private void createPP_Product_Planning(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String bomVersionsIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Product_BOMVersions.COLUMNNAME_PP_Product_BOMVersions_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final boolean isCreatePlan = DataTableUtil.extractBooleanForColumnName(tableRow, I_PP_Product_Planning.COLUMNNAME_IsCreatePlan);

		final boolean isAttributeDependant = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant, false);

		final I_PP_Product_Planning productPlanningRecord = InterfaceWrapperHelper.newInstance(I_PP_Product_Planning.class);
		productPlanningRecord.setM_Product_ID(productRecord.getM_Product_ID());
		productPlanningRecord.setAD_Org_ID(productRecord.getAD_Org_ID());
		productPlanningRecord.setS_Resource_ID(TEST_PLANT_ID.getRepoId());
		productPlanningRecord.setAD_Workflow_ID(WORKFLOW_ID.getRepoId());
		productPlanningRecord.setIsCreatePlan(isCreatePlan);
		productPlanningRecord.setIsAttributeDependant(isAttributeDependant);

		if (bomVersionsIdentifier != null)
		{
			final I_PP_Product_BOMVersions bomVersions = productBomVersionsTable.get(bomVersionsIdentifier);

			assertThat(bomVersions).isNotNull();

			productPlanningRecord.setPP_Product_BOMVersions_ID(bomVersions.getPP_Product_BOMVersions_ID());
		}

		final boolean isPurchased = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_IsPurchased, false);

		if (isPurchased)
		{
			productPlanningRecord.setIsPurchased(X_PP_Product_Planning.ISPURCHASED_Yes);
		}
		else
		{
			productPlanningRecord.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes);
		}

		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(attributeSetInstance).isNotNull();

			final AttributesKey ppAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(attributeSetInstance.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			productPlanningRecord.setM_AttributeSetInstance_ID(attributeSetInstance.getM_AttributeSetInstance_ID());
			productPlanningRecord.setStorageAttributesKey(ppAttributesKeys.getAsString());
		}

		InterfaceWrapperHelper.save(productPlanningRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Product_Planning.Table_Name);
		productPlanningTable.putOrReplace(recordIdentifier, productPlanningRecord);
	}
}