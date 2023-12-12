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
import de.metas.cucumber.stepdefs.distribution.DD_NetworkDistribution_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.cucumber.stepdefs.workflow.AD_Workflow_StepDefData;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanning.ProductPlanningBuilder;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.cucumber.stepdefs.StepDefConstants.TEST_PLANT_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.WORKFLOW_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_AD_Workflow_ID;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_DD_NetworkDistribution_ID;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Product_Planning_StepDef
{
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOMVersions_StepDefData productBomVersionsTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final DD_NetworkDistribution_StepDefData ddNetworkTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final AD_Workflow_StepDefData workflowTable;

	public PP_Product_Planning_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOMVersions_StepDefData productBomVersionsTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final DD_NetworkDistribution_StepDefData ddNetworkTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final AD_Workflow_StepDefData workflowTable)
	{
		this.productTable = productTable;
		this.productBomVersionsTable = productBomVersionsTable;
		this.productPlanningTable = productPlanningTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.ddNetworkTable = ddNetworkTable;
		this.warehouseTable = warehouseTable;
		this.workflowTable = workflowTable;
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

		final PPRoutingId workflowId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(workflowTable::get)
				.map(record -> PPRoutingId.ofRepoId(record.getAD_Workflow_ID()))
				.orElse(WORKFLOW_ID);

		final ProductPlanning existingProductPlanning = getExistingProductPlanning(ProductId.ofRepoId(productRecord.getM_Product_ID()), TEST_PLANT_ID).orElse(null);

		final ProductPlanningBuilder builder = existingProductPlanning != null ? existingProductPlanning.toBuilder() : ProductPlanning.builder();

		builder.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()));
		builder.orgId(OrgId.ofRepoIdOrAny(productRecord.getAD_Org_ID()));
		builder.plantId(TEST_PLANT_ID);
		builder.workflowId(workflowId);
		builder.isCreatePlan(isCreatePlan);
		builder.isAttributeDependant(isAttributeDependant);
		builder.isManufactured(true);

		if (bomVersionsIdentifier != null)
		{
			final ProductBOMVersionsId bomVersionsId = productBomVersionsTable.getId(bomVersionsIdentifier);
			builder.bomVersionsId(bomVersionsId);
		}

		final boolean isPurchased = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_IsPurchased, false);
		if (isPurchased)
		{
			builder.isPurchased(true);
			builder.isManufactured(false);
		}

		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(attributeSetInstance).isNotNull();

			final AttributesKey ppAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(attributeSetInstance.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			builder.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(attributeSetInstance.getM_AttributeSetInstance_ID()));
			builder.storageAttributesKey(ppAttributesKeys.getAsString());
		}

		final String ddNetworkIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DD_NetworkDistribution_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(ddNetworkIdentifier))
		{
			DistributionNetworkId distributionNetworkId = ddNetworkTable.getId(ddNetworkIdentifier);
			builder.distributionNetworkId(distributionNetworkId);
			builder.isManufactured(false);
		}

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final WarehouseId warehouseId = warehouseTable.getId(warehouseIdentifier);
			builder.warehouseId(warehouseId);
		}

		final BigDecimal maxManufacturedQtyPerOrderDispoBD = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_MaxManufacturedQtyPerOrderDispo);
		if (maxManufacturedQtyPerOrderDispoBD != null)
		{
			final String maxManufacturedQtyPerOrderDispoUOMCode = DataTableUtil.extractStringForColumnName(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_MaxManufacturedQtyPerOrderDispo + "UOMCode");
			final I_C_UOM uom = uomDao.getByX12DE355(X12DE355.ofCode(maxManufacturedQtyPerOrderDispoUOMCode));

			builder.maxManufacturedQtyPerOrderDispo(Quantity.of(maxManufacturedQtyPerOrderDispoBD, uom));
		}

		final Integer seqNo = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_PP_Product_Planning.COLUMNNAME_SeqNo);
		if (seqNo != null)
		{
			builder.seqNo(seqNo);
		}

		final ProductPlanning productPlanning = productPlanningDAO.save(builder.build());

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Product_Planning.Table_Name);
		productPlanningTable.putOrReplace(recordIdentifier, productPlanning);
	}

	@NonNull
	private Optional<ProductPlanning> getExistingProductPlanning(
			@NonNull final ProductId productId,
			@Nullable final ResourceId resourceId)
	{
		return productPlanningDAO.find(ProductPlanningQuery.builder()
				.productId(productId)
				.plantId(resourceId)
				.build());
	}
}