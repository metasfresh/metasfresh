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

package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.externalsystem.ExternalSystem_Config_LeichMehl_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.ProductPlanning;
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
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class PP_Order_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

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
	private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	private final M_HU_StepDefData huTable;
	private final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final M_Warehouse_StepDefData warehouseTable;

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
			@NonNull final PP_Order_Candidate_StepDefData ppOrderCandidateTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final ExternalSystem_Config_LeichMehl_StepDefData leichMehlConfigTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable)
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
		this.ppOrderCandidateTable = ppOrderCandidateTable;
		this.huTable = huTable;
		this.leichMehlConfigTable = leichMehlConfigTable;
		this.contractTable = contractTable;
		this.warehouseTable = warehouseTable;
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

			final String productPlanningIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_PP_Product_Planning_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final WarehouseId warehouseId;
			if (Check.isNotBlank(warehouseIdentifier))
			{
				final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
				warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());
			}
			else
			{
				warehouseId = StepDefConstants.WAREHOUSE_ID;
			}

			final PPOrderCreateRequest.PPOrderCreateRequestBuilder ppOrderCreateRequest = PPOrderCreateRequest.builder()
					.docBaseType(docBaseType)
					.clientAndOrgId(clientAndOrgId)
					.plantId(resourceId)
					.warehouseId(warehouseId)
					.productId(productId)
					.qtyRequired(quantity)
					.dateOrdered(dateOrdered)
					.datePromised(datePromised)
					.dateStartSchedule(dateStartSchedule)
					.completeDocument(completeDocument);

			if (Check.isNotBlank(productPlanningIdentifier))
			{
				final ProductPlanning productPlanning = productPlanningTable.get(productPlanningIdentifier);
				ppOrderCreateRequest.productPlanningId(productPlanning.getIdNotNull());
			}

			final I_PP_Order ppOrder = ppOrderService.createOrder(ppOrderCreateRequest.build());
			assertThat(ppOrder).isNotNull();

			final String modularContractIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_Modular_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(modularContractIdentifier))
			{
				final I_C_Flatrate_Term modularContract = contractTable.get(modularContractIdentifier);
				ppOrder.setModular_Flatrate_Term_ID(modularContract.getC_Flatrate_Term_ID());
				saveRecord(ppOrder);
			}

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
				trxManager.runInThreadInheritedTrx(() -> huPPOrderBL.processPlanning(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()), PPOrderPlanningStatus.COMPLETE));

				assertThat(errorMessage).as("ErrorMessage should be null if huPPOrderBL.processPlanning() finished with no error!").isNull();
			}
			catch (final Exception e)
			{
				StepDefUtil.validateErrorMessage(e, errorMessage);
			}
		}
	}

	@And("update PP_Order:")
	public void update_PP_Order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			updatePPOrder(row);
		}
	}

	@And("^validate that after not more than (.*)s, PP_Orders are created for PP_Order_Candidate in the following order:$")
	public void validate_PP_Orders_created_in_the_following_order(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, ArrayList<String>> ppOrderCandidate2PPOrderIdsOrdered = getPPOrderCandIdentifier2PPOrderIdentifiers(dataTable);

		locateAndLoadPPOrders(timeoutSec, ppOrderCandidate2PPOrderIdsOrdered);

		final List<Integer> ppOrderIdsAsListedInFeatureFile = dataTable.asMaps()
				.stream()
				.map(row -> {
					final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
					return ppOrderTable.get(ppOrderIdentifier).getPP_Order_ID();
				})
				.collect(ImmutableList.toImmutableList());

		assertThat(Ordering.<Integer>natural().isOrdered(ppOrderIdsAsListedInFeatureFile)).isTrue();
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

	@And("validate I_PP_Order_Qty")
	public void validate_order_qty(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order order = ppOrderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Qty.COLUMNNAME_Qty);

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = bomLineIdentifier != null ? ppOrderBomLineTable.get(bomLineIdentifier) : null;

			final I_PP_Order_Qty orderQty = queryBL.createQueryBuilder(I_PP_Order_Qty.class)
					.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.create()
					.firstOnly(I_PP_Order_Qty.class);

			assertThat(orderQty).isNotNull();
			assertThat(orderQty.getQty()).isEqualTo(movementQty);
			if (bomLine != null)
			{
				assertThat(orderQty.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			}
		}
	}

	@And("^the manufacturing order identified by (.*) is (reactivated|completed)$")
	public void order_action(
			@NonNull final String orderIdentifier,
			@NonNull final String action)
	{
		final I_PP_Order orderRecord = ppOrderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reactivated:
				orderRecord.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(orderRecord, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				break;
			case completed:
				orderRecord.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(orderRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			default:
				throw new AdempiereException("Unhandled PP_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	private void validatePP_Order_BomLine(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrderRecord = ppOrderTable.get(ppOrderIdentifier);

		final BigDecimal qtyRequired = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered);
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
		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
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

		final String productPlanningIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "PP_Product_Planning_ID" + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final ProductPlanning productPlanning = productPlanningTable.get(productPlanningIdentifier);

		final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID);
		final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyEntered);
		final int qtyOrdered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyOrdered);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DatePromised);

		final String docStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_DocStatus);

		final String orderRecordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Order.Table_Name);
		final IQueryBuilder<I_PP_Order> queryBuilder = queryBL.createQueryBuilder(I_PP_Order.class)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_BOM_ID, productBOMRecord.getPP_Product_BOM_ID())
				.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_Planning_ID, productPlanning.getIdNotNull())
				.addEqualsFilter(I_PP_Order.COLUMNNAME_S_Resource_ID, resourceId)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyEntered, qtyEntered)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyOrdered, qtyOrdered)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_C_UOM_ID, uomId.getRepoId())

				.addEqualsFilter(I_PP_Order.COLUMNNAME_DatePromised, datePromised);

		Optional.ofNullable(bPartnerIdentifier)
				.map(bPartnerTable::get)
				.ifPresent(bPartnerRecord -> queryBuilder.addEqualsFilter(I_PP_Order.COLUMNNAME_C_BPartner_ID, bPartnerRecord.getC_BPartner_ID()));

		if (Check.isNotBlank(docStatus))
		{
			queryBuilder.addEqualsFilter(I_PP_Order.COLUMNNAME_DocStatus, docStatus);
		}

		ppOrderTable.getOptional(orderRecordIdentifier)
				.ifPresent(ppOrder -> queryBuilder.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID()));

		final Supplier<Boolean> ppOrderQueryExecutor = () -> {

			final I_PP_Order orderRecord = queryBuilder
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
		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
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

	private void locateAndLoadPPOrders(int timeoutSec, @NonNull final Map<String, ArrayList<String>> ppOrderCandidate2PPOrderIdsOrdered) throws InterruptedException
	{
		for (final String ppOrderCandIdentifier : ppOrderCandidate2PPOrderIdsOrdered.keySet())
		{
			final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidateTable.get(ppOrderCandIdentifier);
			assertThat(ppOrderCandidate).isNotNull();

			final Supplier<Boolean> ppOrderAllocationsFound = () -> {

				final ImmutableList<PPOrderId> ppOrderIdsForCandidateSorted = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
						.create()
						.stream()
						.map(I_PP_OrderCandidate_PP_Order::getPP_Order_ID)
						.sorted()
						.map(PPOrderId::ofRepoId)
						.collect(ImmutableList.toImmutableList());

				final List<String> ppOrderIdentifiersSorted = ppOrderCandidate2PPOrderIdsOrdered.get(ppOrderCandIdentifier);

				if (ppOrderIdsForCandidateSorted.size() != ppOrderIdentifiersSorted.size())
				{
					return false;
				}

				for (int i = 0; i < ppOrderIdsForCandidateSorted.size(); i++)
				{
					ppOrderTable.putOrReplace(ppOrderIdentifiersSorted.get(i), InterfaceWrapperHelper.load(ppOrderIdsForCandidateSorted.get(i), I_PP_Order.class));
				}

				return true;
			};

			StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderAllocationsFound);
		}
	}

	@NonNull
	private static Map<String, ArrayList<String>> getPPOrderCandIdentifier2PPOrderIdentifiers(@NonNull final DataTable dataTable)
	{
		final Map<String, ArrayList<String>> ppOrderCandidate2PPOrderIdsOrdered = new HashMap<>();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String ppOrderCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

			final ArrayList<String> ppOrderIdentifiers = new ArrayList<>();
			ppOrderIdentifiers.add(ppOrderIdentifier);
			ppOrderCandidate2PPOrderIdsOrdered.merge(ppOrderCandIdentifier, ppOrderIdentifiers, (old, newL) -> {
				old.addAll(newL);
				return old;
			});
		}

		return ppOrderCandidate2PPOrderIdsOrdered;
	}

	private void updatePPOrder(@NonNull final Map<String, String> row)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrderRecord = ppOrderTable.get(orderIdentifier);

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_PP_Order.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			ppOrderRecord.setQtyEntered(qtyEntered);
			ppOrderRecord.setQtyOrdered(qtyEntered);
		}

		final ZonedDateTime datePromised = DataTableUtil.extractZonedDateTimeOrNullForColumnName(row, "OPT." + I_PP_Order.COLUMNNAME_DatePromised);
		if (datePromised != null)
		{
			ppOrderRecord.setDatePromised(TimeUtil.asTimestampNotNull(datePromised));
		}

		saveRecord(ppOrderRecord);
	}

	@And("load manufactured HU for PP_Order:")
	public void loadManufacturedHUs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadManufacturedHu);
	}

	private void loadManufacturedHu(@NonNull final DataTableRow tableRow)
	{
		final IQueryBuilder<I_PP_Order_Qty> builder = queryBL.createQueryBuilder(I_PP_Order_Qty.class)
				.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_BOMLine_ID, null);
		final I_PP_Order ppOrder = tableRow.getAsOptionalIdentifier(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID)
				.map(ppOrderTable::get)
				.orElseThrow(() -> new AdempiereException("No PP_Order found for identifier " + tableRow.getAsString(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID)));
		builder.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_M_Product_ID, ppOrder.getM_Product_ID());

		final I_M_HU manufacturedHu = builder.andCollect(I_PP_Order_Qty.COLUMN_M_HU_ID)
				.create()
				.firstOnly(I_M_HU.class);
		assertThat(manufacturedHu).isNotNull();
		huTable.putOrReplace(tableRow.getAsIdentifier(I_M_HU.COLUMNNAME_M_HU_ID), manufacturedHu);
	}
}