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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
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
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Order_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOM_StepDefData productBOMTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final S_Resource_StepDefData resourceTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final PP_Order_BOMLine_StepDefData ppOrderBomLineTable;

	private final TestContext testContext;

	public PP_Order_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOM_StepDefData productBOMTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final S_Resource_StepDefData resourceTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final PP_Order_BOMLine_StepDefData ppOrderBomLineTable,
			@NonNull final TestContext testContext)
	{
		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productPlanningTable = productPlanningTable;
		this.bPartnerTable = bPartnerTable;
		this.ppOrderTable = ppOrderTable;
		this.resourceTable = resourceTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.ppOrderBomLineTable = ppOrderBomLineTable;
		this.testContext = testContext;
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
	public void process_pp_order(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

			trxManager.runInThreadInheritedTrx(() -> huPPOrderBL.processPlanning(PPOrderPlanningStatus.COMPLETE, PPOrderId.ofRepoId(ppOrder.getPP_Order_ID())));
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

		final boolean bomLineFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderBOMLineQueryExecutor);
		assertThat(bomLineFound).isTrue();

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

		final boolean orderFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderQueryExecutor);
		assertThat(orderFound).isTrue();

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
	}

	@And("^store PP_Order endpointPath (.*) in context$")
	public void store_pp_order_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = ".*(:[a-zA-Z]+)/?.*";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String ppOrderIdentifierGroup = matcher.group(1);
			final String ppOrderIdentifier = ppOrderIdentifierGroup.replace(":", "");

			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
			assertThat(ppOrder).isNotNull();

			endpointPath = endpointPath.replace(ppOrderIdentifierGroup, String.valueOf(ppOrder.getPP_Order_ID()));

			testContext.setEndpointPath(endpointPath);
		}
	}

	@And("validate retrieve pp_order response:")
	public void validate_retrieve_pp_order_response(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonResponseManufacturingOrder ppOrderResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseManufacturingOrder.class);
		assertThat(ppOrderResponse).isNotNull();

		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> row : tableRows)
		{
			validatePPOrder(row, ppOrderResponse);
		}
	}

	private void validatePPOrder(@NonNull final Map<String, String> row, @NonNull final JsonResponseManufacturingOrder ppOrderResponse)
	{
		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String orgCode = DataTableUtil.extractStringForColumnName(row, "OrgCode");
		final ZonedDateTime dateOrdered = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DateOrdered);
		final ZonedDateTime datePromised = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DatePromised);
		final ZonedDateTime dateStartSchedule = DataTableUtil.extractZonedDateTimeForColumnName(row, I_PP_Order.COLUMNNAME_DateStartSchedule);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, I_PP_Order.COLUMNNAME_QtyEntered);
		final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String componentIdentifier = DataTableUtil.extractStringForColumnName(row, "Component" + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal componentQty = DataTableUtil.extractBigDecimalForColumnName(row, "ComponentQty");
		final String componentType = DataTableUtil.extractStringForColumnName(row, I_PP_Product_BOMLine.COLUMNNAME_ComponentType);

		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		assertThat(ppOrder).isNotNull();

		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		assertThat(ppOrderResponse.getOrderId().getValue()).isEqualTo(ppOrder.getPP_Order_ID());
		assertThat(ppOrderResponse.getOrgCode()).isEqualTo(orgCode);
		assertThat(ppOrderResponse.getDateOrdered()).isEqualTo(dateOrdered);
		assertThat(ppOrderResponse.getDatePromised()).isEqualTo(datePromised);
		assertThat(ppOrderResponse.getDateStartSchedule()).isEqualTo(dateStartSchedule);
		assertThat(ppOrderResponse.getQtyToProduce().getQty()).isEqualTo(qty);
		assertThat(ppOrderResponse.getQtyToProduce().getUomCode()).isEqualTo(uomCode);
		assertThat(ppOrderResponse.getProductId().getValue()).isEqualTo(product.getM_Product_ID());

		final I_M_Product component = productTable.get(componentIdentifier);
		assertThat(component).isNotNull();
		assertThat(ppOrderResponse.getComponents().get(0).getProduct().getProductNo()).isEqualTo(component.getValue());
		assertThat(ppOrderResponse.getComponents().get(0).getQty().getQty()).isEqualTo(componentQty);
		assertThat(ppOrderResponse.getComponents().get(0).getComponentType()).isEqualTo(componentType);
	}
}