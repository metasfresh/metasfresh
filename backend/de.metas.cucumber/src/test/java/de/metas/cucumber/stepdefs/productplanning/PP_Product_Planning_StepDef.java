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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMVersions_StepDefData;
import de.metas.cucumber.stepdefs.distribution.DD_NetworkDistribution_StepDefData;
import de.metas.cucumber.stepdefs.pporder.maturing.M_Maturing_Configuration_Line_StepDefData;
import de.metas.cucumber.stepdefs.pporder.maturing.M_Maturing_Configuration_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.cucumber.stepdefs.workflow.AD_Workflow_StepDefData;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanning.ProductPlanningBuilder;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Maturing_Configuration;
import org.compiere.model.I_M_Maturing_Configuration_Line;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TEST_PLANT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.WORKFLOW_ID;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_AD_Workflow_ID;

public class PP_Product_Planning_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOMVersions_StepDefData productBomVersionsTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final PP_Product_BOMVersions_StepDefData bomVersionsTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final DD_NetworkDistribution_StepDefData ddNetworkTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final AD_Workflow_StepDefData workflowTable;
	private final M_Maturing_Configuration_StepDefData maturingConfigurationTable;
	private final M_Maturing_Configuration_Line_StepDefData maturingConfigurationLineTable;

	public PP_Product_Planning_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOMVersions_StepDefData productBomVersionsTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final PP_Product_BOMVersions_StepDefData bomVersionsTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final DD_NetworkDistribution_StepDefData ddNetworkTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final AD_Workflow_StepDefData workflowTable,
			@NonNull final M_Maturing_Configuration_StepDefData maturingConfigurationTable,
			@NonNull final M_Maturing_Configuration_Line_StepDefData maturingConfigurationLineTable)
	{
		this.productTable = productTable;
		this.productBomVersionsTable = productBomVersionsTable;
		this.productPlanningTable = productPlanningTable;
		this.bomVersionsTable = bomVersionsTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.ddNetworkTable = ddNetworkTable;
		this.warehouseTable = warehouseTable;
		this.workflowTable = workflowTable;
		this.maturingConfigurationTable = maturingConfigurationTable;
		this.maturingConfigurationLineTable = maturingConfigurationLineTable;
	}

	@Given("metasfresh contains PP_Product_Plannings")
	public void add_PP_Product_Planning(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPP_Product_Planning);
	}

	private void createPP_Product_Planning(@NonNull final DataTableRow tableRow)
	{
		final I_M_Product productRecord = tableRow.getAsIdentifier(I_PP_Product_Planning.COLUMNNAME_M_Product_ID).lookupIn(productTable);
		final OrgId orgId = OrgId.ofRepoIdOrAny(productRecord.getAD_Org_ID());
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final WarehouseId warehouseId = tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID).map(warehouseTable::getId).orElse(null);

		final boolean isLotForLot = tableRow.getAsOptionalBoolean(I_PP_Product_Planning.COLUMNNAME_IsLotForLot).orElse(false);

		final ProductPlanning existingProductPlanning = getExistingProductPlanning(orgId, warehouseId, productId).orElse(null);

		final ProductPlanningBuilder builder = existingProductPlanning != null ? existingProductPlanning.toBuilder() : ProductPlanning.builder();

		builder.productId(productId);
		builder.orgId(orgId);
		builder.plantId(TEST_PLANT_ID);
		builder.workflowId(tableRow.getAsOptionalIdentifier(COLUMNNAME_AD_Workflow_ID).map(workflowTable::getId).orElse(WORKFLOW_ID));
		tableRow.getAsOptionalBoolean(I_PP_Product_Planning.COLUMNNAME_IsCreatePlan).ifPresent(builder::isCreatePlan);
		builder.isAttributeDependant(tableRow.getAsOptionalBoolean(I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant).orElse(false));
		builder.isManufactured(true);
		builder.isLotForLot(isLotForLot);

		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID)
				.map(productBomVersionsTable::getId)
				.ifPresent(builder::bomVersionsId);

		final boolean isPurchased = tableRow.getAsOptionalBoolean(I_PP_Product_Planning.COLUMNNAME_IsPurchased).orElse(false);
		if (isPurchased)
		{
			builder.isPurchased(true);
			builder.isManufactured(false);
		}

		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> {
					final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
					builder.attributeSetInstanceId(asiId);
					builder.storageAttributesKey(storageAttributesKey.getAsString());
				});

		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_DD_NetworkDistribution_ID)
				.map(ddNetworkTable::getId)
				.ifPresent(distributionNetworkId -> {
					builder.distributionNetworkId(distributionNetworkId);
					builder.isManufactured(false);
				});

		if (warehouseId != null)
		{
			builder.warehouseId(warehouseId);
		}

		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID)
				.map(bomVersionsTable::getId)
				.ifPresent(builder::bomVersionsId);
		tableRow.getAsOptionalQuantity(
						I_PP_Product_Planning.COLUMNNAME_MaxManufacturedQtyPerOrderDispo,
						I_PP_Product_Planning.COLUMNNAME_MaxManufacturedQtyPerOrderDispo_UOM_ID,
						uomString -> uomDAO.getByX12DE355(X12DE355.ofCode(uomString))
				)
				.ifPresent(builder::maxManufacturedQtyPerOrderDispo);

		tableRow.getAsOptionalInt(I_PP_Product_Planning.COLUMNNAME_SeqNo)
				.ifPresent(builder::seqNo);
		tableRow.getAsOptionalBoolean(I_PP_Product_Planning.COLUMNNAME_IsMatured)
				.ifPresent(builder::isMatured);
		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_M_Maturing_Configuration_ID)
				.map(maturingConfigurationTable::get)
				.map(I_M_Maturing_Configuration::getM_Maturing_Configuration_ID)
				.map(MaturingConfigId::ofRepoId)
				.ifPresent(builder::maturingConfigId);
		tableRow.getAsOptionalIdentifier(I_PP_Product_Planning.COLUMNNAME_M_Maturing_Configuration_Line_ID)
				.map(maturingConfigurationLineTable::get)
				.map(I_M_Maturing_Configuration_Line::getM_Maturing_Configuration_Line_ID)
				.map(MaturingConfigLineId::ofRepoId)
				.ifPresent(builder::maturingConfigLineId);

		final ProductPlanning productPlanning = productPlanningDAO.save(builder.build());

		tableRow.getAsIdentifier().put(productPlanningTable, productPlanning);
	}

	@NonNull
	private Optional<ProductPlanning> getExistingProductPlanning(
			@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final ProductId productId)
	{
		final ResourceId plantId = TEST_PLANT_ID;

		return productPlanningDAO.find(ProductPlanningQuery.builder()
						.orgId(orgId)
						.warehouseId(warehouseId)
						.plantId(plantId)
						.productId(productId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build())
				// Because find method could fall back to more generic product planning,
				// let's make sure we have one product planning exactly for our org/warehouse/product/plant
				.filter(productPlanning -> OrgId.equals(productPlanning.getOrgId(), orgId)
						&& WarehouseId.equals(productPlanning.getWarehouseId(), warehouseId)
						&& ResourceId.equals(productPlanning.getPlantId(), plantId)
						&& ProductId.equals(productPlanning.getProductId(), productId));
	}
}