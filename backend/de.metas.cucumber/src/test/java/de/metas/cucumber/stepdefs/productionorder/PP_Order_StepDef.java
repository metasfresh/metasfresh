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

package de.metas.cucumber.stepdefs.productionorder;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.externalsystem.ExternalSystem_Config_LeichMehl_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.IADPInstanceDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Order_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);

	private final ExportPPOrderToExternalSystem exportPPOrderToExternalSystem = SpringContextHolder.instance.getBean(ExportPPOrderToExternalSystem.class);

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOM_StepDefData productBOMTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final S_Resource_StepDefData resourceTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final PP_Order_BOMLine_StepDefData ppOrderBomLineTable;
	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable;

	public PP_Order_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOM_StepDefData productBOMTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final S_Resource_StepDefData resourceTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final PP_Order_BOMLine_StepDefData ppOrderBomLineTable,
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable)
	{
		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productPlanningTable = productPlanningTable;
		this.bPartnerTable = bPartnerTable;
		this.ppOrderTable = ppOrderTable;
		this.resourceTable = resourceTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.ppOrderBomLineTable = ppOrderBomLineTable;
		this.huPiItemProductTable = huPiItemProductTable;
		this.leichMehlConfigTable = leichMehlConfigTable;
	}

	@And("^after not more than (.*)s, PP_Orders are found$")
	public void validatePP_Order_Candidate(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_Order(timeoutSec, row);
		}
	}

	@And("^after not more than (.*)s, PP_Order_BomLines are found$")
	public void validatePP_Order_BomLine(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_Order_BomLine(timeoutSec, row);
		}
	}

	@And("create PP_Order:")
	public void compute_PPOrderCreateRequest_to_create_pp_order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String ppOrderDocBaseType = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_DocBaseType);
			final PPOrderDocBaseType docBaseType = PPOrderDocBaseType.ofCode(ppOrderDocBaseType);

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId());

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Resource testResource = resourceTable.get(resourceIdentifier);
			assertThat(testResource).isNotNull();
			final ResourceId resourceId = ResourceId.ofRepoId(testResource.getS_Resource_ID());

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

			final int enteredQuantity = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyEntered);
			final I_C_UOM uom = uomDAO.getEachUOM();
			final Quantity quantity = Quantity.of(enteredQuantity, uom);

			final Instant dateOrdered = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order.COLUMNNAME_DateOrdered);
			final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order.COLUMNNAME_DatePromised);
			final Instant dateStartSchedule = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order.COLUMNNAME_DateStartSchedule);

			final Boolean completeDocument = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "completeDocument", false);

			final PPOrderCreateRequest ppOrderCreateRequest = PPOrderCreateRequest.builder()
					.docBaseType(docBaseType)
					.clientAndOrgId(clientAndOrgId)
					.plantId(resourceId)
					.warehouseId(StepDefConstants.WAREHOUSE_ID)
					.productId(productId)
					.qtyRequired(quantity)
					.dateOrdered(dateOrdered)
					.datePromised(datePromised)
					.dateStartSchedule(dateStartSchedule)
					.completeDocument(completeDocument)
					.build();

			final I_PP_Order ppOrder = ppOrderService.createOrder(ppOrderCreateRequest);
			assertThat(ppOrder).isNotNull();

			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			ppOrderTable.put(ppOrderIdentifier, ppOrder);
		}
	}

	@And("complete planning for PP_Order:")
	public void process_pp_order(@NonNull final DataTable dataTable) throws Exception
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

			final String errorMessage = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.ErrorMessage");

			try
			{
				trxManager.runInThreadInheritedTrx(() -> huPPOrderBL.processPlanning(PPOrderPlanningStatus.COMPLETE, PPOrderId.ofRepoId(ppOrder.getPP_Order_ID())));

				assertThat(errorMessage).as("ErrorMessage should be null if huPPOrderBL.processPlanning() finished with no error!").isNull();
			}
			catch (final Exception e)
			{
				StepDefUtil.validateErrorMessage(e, errorMessage);
			}
		}
	}

	@And("export PP_Order to LeichMehl external system")
	public void export_PP_Order_LeichMehl(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		final TableRecordReference ppOrderRecordReference = TableRecordReference.of(ppOrder);

		final String leichMehlConfigIdentifier = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystem_Config_LeichMehl_ID
				+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_ExternalSystem_Config_LeichMehl leichMehlConfig = leichMehlConfigTable.get(leichMehlConfigIdentifier);
		final ExternalSystemLeichMehlConfigId leichMehlConfigId = ExternalSystemLeichMehlConfigId.ofRepoId(leichMehlConfig.getExternalSystem_Config_LeichMehl_ID());

		exportPPOrderToExternalSystem.exportToExternalSystem(leichMehlConfigId, ppOrderRecordReference, pinstanceDAO.createSelectionId());
	}

	private void validatePP_Order_BomLine(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrderRecord = ppOrderTable.get(ppOrderIdentifier);

		final int qtyRequired = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered);
		final boolean isQtyPercentage = DataTableUtil.extractBooleanForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_IsQtyPercentage);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String componentType = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_ComponentType);

		final String ppOrderBOMLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Supplier<Boolean> ppOrderBOMLineQueryExecutor = () -> {

			final I_PP_Order_BOMLine orderBOMLineRecord = queryBL.createQueryBuilder(I_PP_Order_BOMLine.class)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrderRecord.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered, qtyRequired)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_IsQtyPercentage, isQtyPercentage)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, componentType)
					.create()
					.firstOnly(I_PP_Order_BOMLine.class);

			if (orderBOMLineRecord == null)
			{
				return false;
			}

			ppOrderBomLineTable.putOrReplace(ppOrderBOMLineIdentifier, orderBOMLineRecord);
			return true;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderBOMLineQueryExecutor);

		final I_PP_Order_BOMLine ppOrderBOMLine = ppOrderBomLineTable.get(ppOrderBOMLineIdentifier);
		assertThat(ppOrderBOMLine).isNotNull();

		//validate asi
		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(expectedASI).isNotNull();

			final AttributesKey expectedAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);
			final AttributesKey ppOrderBOMLineAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(ppOrderBOMLine.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			assertThat(ppOrderBOMLineAttributesKeys).isEqualTo(expectedAttributesKeys);
		}
	}

	private void validatePP_Order(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String productBOMIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM productBOMRecord = productBOMTable.get(productBOMIdentifier);

		final String productPlanningIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_Planning productPlanningRecord = productPlanningTable.get(productPlanningIdentifier);

		final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID);
		final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyEntered);
		final int qtyOrdered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyOrdered);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartnerRecord = bPartnerTable.get(bPartnerIdentifier);

		final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DatePromised);

		final String orderRecordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Order.Table_Name);
		final Supplier<Boolean> ppOrderQueryExecutor = () -> {

			final I_PP_Order orderRecord = queryBL.createQueryBuilder(I_PP_Order.class)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_BOM_ID, productBOMRecord.getPP_Product_BOM_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_Planning_ID, productPlanningRecord.getPP_Product_Planning_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_S_Resource_ID, resourceId)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyEntered, qtyEntered)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyOrdered, qtyOrdered)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_C_BPartner_ID, bPartnerRecord.getC_BPartner_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_DatePromised, datePromised)
					.create()
					.firstOnly(I_PP_Order.class);

			if (orderRecord != null)
			{
				ppOrderTable.putOrReplace(orderRecordIdentifier, orderRecord);
				return true;
			}
			else
			{
				return false;
			}
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderQueryExecutor);

		final I_PP_Order ppOrder = ppOrderTable.get(orderRecordIdentifier);
		assertThat(ppOrder).isNotNull();

		//validate asi
		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(asiIdentifier);
			assertThat(expectedASI).isNotNull();

			final AttributesKey expectedAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);
			final AttributesKey ppOrderAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(ppOrder.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			assertThat(ppOrderAttributesKeys).isEqualTo(expectedAttributesKeys);
		}

		final String itemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(itemProductIdentifier))
		{
			final Integer huPiItemProductRecordID = huPiItemProductTable.getOptional(itemProductIdentifier)
					.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
					.orElseGet(() -> Integer.parseInt(itemProductIdentifier));

			assertThat(ppOrder.getM_HU_PI_Item_Product_ID()).isEqualTo(huPiItemProductRecordID);
		}
	}
}