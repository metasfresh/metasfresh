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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.datasource.AD_InputDataSource_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.paymentterm.C_PaymentTerm_StepDef;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.impexp.InputDataSourceId;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.process.C_Order_CreatePOFromSOs;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_Order.*;

@RequiredArgsConstructor
public class C_Order_StepDef
{
	private final Logger logger = LogManager.getLogger(C_Order_StepDef.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final CopyRecordService copyRecordService = SpringContextHolder.instance.getBean(CopyRecordService.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	private final @NonNull C_BPartner_StepDefData bpartnerTable;
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final @NonNull AD_User_StepDefData userTable;
	private final @NonNull M_PricingSystem_StepDefData pricingSystemDataTable;
	private final @NonNull M_Warehouse_StepDefData warehouseTable;
	private final @NonNull AD_Org_StepDefData orgTable;
	private final @NonNull AD_InputDataSource_StepDefData dataSourceTable;
	private final @NonNull TestContext restTestContext;
	private final @NonNull C_PaymentTerm_StepDef paymentTermStepDef;


	@Given("metasfresh contains C_Orders:")
	public void metasfresh_contains_c_orders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_Order_ID)
				.forEach(this::createOrder);
	}

	public I_C_Order createOrder(final DataTableRow tableRow)
	{
		final String poReference = tableRow.getAsOptionalName(I_C_Order.COLUMNNAME_POReference).orElse(null);
		final StepDefDataIdentifier pricingSystemIdentifier = tableRow.getAsOptionalIdentifier(COLUMNNAME_M_PricingSystem_ID).orElse(null);
		final SOTrx soTrx = tableRow.getAsOptionalBoolean(I_C_Order.COLUMNNAME_IsSOTrx).map(SOTrx::ofBoolean).orElse(null);
		final DocBaseType docBaseType = Optionals.firstPresentOfSuppliers(
				() -> tableRow.getAsOptionalEnum(COLUMNNAME_DocBaseType, DocBaseType.class),
				() -> soTrx != null && soTrx.isPurchase() ? Optional.of(DocBaseType.PurchaseOrder) : Optional.empty() // if we don't do this, MOrder.beforeSave will automatically set IsSOTrx=true because C_DocTypeTarget_ID is not set
		).orElse(null);

		final boolean isSOTrx;
		if (soTrx != null)
		{
			isSOTrx = soTrx.toBoolean();
		}
		else if (docBaseType != null)
		{
			isSOTrx = docBaseType.isSalesOrder();
		}
		else
		{
			throw new AdempiereException("Either IsSOTrx or DocBaseType needs to be set");
		}

		final StepDefDataIdentifier bpartnerIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_ID);
		final BPartnerId bpartnerId = bpartnerTable.getIdOptional(bpartnerIdentifier)
				.orElseGet(() -> bpartnerIdentifier.getAsId(BPartnerId.class));

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartnerId.getRepoId());
		order.setIsSOTrx(isSOTrx);
		order.setDateOrdered(tableRow.getAsLocalDateTimestamp(I_C_Order.COLUMNNAME_DateOrdered));

		// dropship
		order.setIsDropShip(tableRow.getAsOptionalBoolean(I_C_Order.COLUMNNAME_IsDropShip).orElse(false));
		tableRow.getAsOptionalIdentifier(COLUMNNAME_DropShip_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(id -> order.setDropShip_BPartner_ID(id.getRepoId()));
		tableRow.getAsOptionalIdentifier(COLUMNNAME_DropShip_Location_ID)
				.map(bpartnerLocationTable::getId)
				.ifPresent(id -> order.setDropShip_Location_ID(id.getRepoId()));

		final OrgId orgId = tableRow.getAsOptionalIdentifier(COLUMNNAME_AD_Org_ID)
				.map(orgTable::getId)
				.orElse(StepDefConstants.ORG_ID);
		order.setAD_Org_ID(orgId.getRepoId());

		paymentTermStepDef.extractPaymentTermId(tableRow).ifPresent(paymentTermId -> order.setC_PaymentTerm_ID(paymentTermId.getRepoId()));

		tableRow.getAsOptionalIdentifier(I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
				.map(bpartnerLocationTable::getId)
				.ifPresent(bpLocationId -> order.setC_BPartner_Location_ID(bpLocationId.getRepoId()));

		final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(userIdentifier))
		{
			final I_AD_User user = userTable.get(userIdentifier);
			order.setAD_User_ID(user.getAD_User_ID());
		}

		final String billBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billBPartnerIdentifier))
		{
			final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
			order.setC_BPartner_ID(billBPartner.getC_BPartner_ID());
		}

		final String bpBillLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpBillLocationIdentifier))
		{
			final I_C_BPartner_Location billBPartnerLocation = bpartnerLocationTable.get(bpBillLocationIdentifier);
			order.setBill_BPartner_ID(billBPartnerLocation.getC_BPartner_ID());
			order.setBill_Location_ID(billBPartnerLocation.getC_BPartner_Location_ID());
		}

		final String deliveryRule = tableRow.getAsOptionalString(I_C_Order.COLUMNNAME_DeliveryRule).orElse(null);
		if (Check.isNotBlank(deliveryRule))
		{
			// note that IF the C_BPartner has a deliveryRule set (not-mandatory there), this values will be overwritten by it
			order.setDeliveryRule(deliveryRule);
		}

		final String deliveryViaRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_DeliveryViaRule);
		if (Check.isNotBlank(deliveryViaRule))
		{
			order.setDeliveryViaRule(deliveryViaRule);
		}

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_InvoiceRule);
		if (Check.isNotBlank(invoiceRule))
		{
			order.setInvoiceRule(invoiceRule);
		}

		final String paymentTermValue = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_C_PaymentTerm_ID + ".Value");
		if (de.metas.util.Check.isNotBlank(paymentTermValue))
		{
			final I_C_PaymentTerm paymentTerm = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
					.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, paymentTermValue)
					.create()
					.firstOnlyNotNull(I_C_PaymentTerm.class);

			order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
		}

		final String email = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_EMail);
		if (Check.isNotBlank(email))
		{
			order.setEMail(email);
		}

		final Instant preparationDate = tableRow.getAsOptionalInstant(I_C_Order.COLUMNNAME_PreparationDate).orElse(null);
		final Instant datePromised = tableRow.getAsOptionalInstant(I_C_Order.COLUMNNAME_DatePromised).orElse(null);

		final Instant preparationDateToBeSet = CoalesceUtil.coalesce(preparationDate, datePromised);
		if (preparationDateToBeSet != null)
		{
			order.setPreparationDate(Timestamp.from(preparationDateToBeSet));
		}

		final Instant datePromisedToBeSet = CoalesceUtil.coalesce(datePromised, preparationDate);
		if (datePromisedToBeSet != null)
		{
			order.setDatePromised(Timestamp.from(datePromisedToBeSet));
		}

		if (EmptyUtil.isNotBlank(poReference))
		{
			order.setPOReference(poReference);
		}

		if (pricingSystemIdentifier != null)
		{
			final I_M_PricingSystem pricingSystem = pricingSystemDataTable.get(pricingSystemIdentifier);
			assertThat(pricingSystem).isNotNull();
			order.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		}

		if (docBaseType != null)
		{
			final DocSubType docSubType = tableRow.getAsOptionalEnum(COLUMNNAME_DocSubType, DocSubType.class)
					.orElseGet(() -> docBaseType.isSalesOrder() ? DocSubType.StandardOrder : DocSubType.ANY);

			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(docBaseType)
					.docSubType(docSubType)
					.clientAndOrgId(StepDefConstants.CLIENT_ID, orgId)
					.build());

			order.setC_DocType_ID(docTypeId.getRepoId());
			order.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRule);
		if (Check.isNotBlank(paymentRule))
		{
			order.setPaymentRule(paymentRule);
		}

		tableRow.getAsOptionalIdentifier(COLUMNNAME_M_Warehouse_ID)
				.map(warehouseIdentifier -> warehouseTable.getIdOptional(warehouseIdentifier).orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class)))
				.ifPresent(warehouseId -> order.setM_Warehouse_ID(warehouseId.getRepoId()));

		final String billUserIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billUserIdentifier))
		{
			final I_AD_User billUser = userTable.get(billUserIdentifier);
			order.setBill_User_ID(billUser.getAD_User_ID());
		}

		final String dropShipLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DropShip_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(dropShipLocationIdentifier))
		{
			final I_C_BPartner_Location dropShipLocation = bpartnerLocationTable.get(dropShipLocationIdentifier);
			order.setDropShip_Location_ID(dropShipLocation.getC_BPartner_Location_ID());
			order.setDropShip_BPartner_ID(dropShipLocation.getC_BPartner_ID());
		}

		tableRow.getAsOptionalString(COLUMNNAME_DocumentNo)
				.ifPresent(order::setDocumentNo);

		tableRow.getAsOptionalString(COLUMNNAME_ExternalId)
				.ifPresent(order::setExternalId);

		tableRow.getAsOptionalIdentifier(COLUMNNAME_AD_InputDataSource_ID)
				.map(dataSourceIdentifier -> dataSourceTable.getIdOptional(dataSourceIdentifier)
						.orElseGet(() -> dataSourceIdentifier.getAsId(InputDataSourceId.class)))
				.ifPresent(inputDataSourceId -> order.setAD_InputDataSource_ID(inputDataSourceId.getRepoId()));

		saveRecord(order);

		orderTable.putOrReplace(tableRow.getAsIdentifier(), order);
		restTestContext.setIntVariableFromRow(tableRow, order::getC_Order_ID);

		tableRow.getAsOptionalIdentifier("REST.Context.DocumentNo")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), order.getDocumentNo()));

		return order;
	}

	@And("^the order identified by (.*) is (reactivated|completed|closed|voided|reversed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String action)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reactivated:
				order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
				documentBL.processEx(order, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				logger.info("Order {} was reactivated", order);
				break;
			case completed:
				completeOrder(order);
				break;
			case closed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			case voided:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case reversed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			default:
				throw new AdempiereException("Unhandled C_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	public void completeOrder(final I_C_Order order)
	{
		order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
		documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		logger.info("Order {} was completed", order);
	}

	@Given("generate PO from SO is invoked with parameters:")
	public void generate_PO_from_SO_invoked(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String purchaseType = DataTableUtil.extractStringForColumnName(tableRow, "PurchaseType");
			final boolean purchaseBomComponents = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "IsPurchaseBOMComponents", false);

			final I_C_Order order = orderTable.get(orderIdentifier);
			final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);

			final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(C_Order_CreatePOFromSOs.class);

			final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder();
			processInfoBuilder.setAD_Process_ID(processId.getRepoId());
			processInfoBuilder.addParameter("DatePromised_From", SystemTime.asTimestamp());
			processInfoBuilder.addParameter("DatePromised_To", SystemTime.asTimestamp());
			processInfoBuilder.addParameter("C_BPartner_ID", bpartner.getC_BPartner_ID());
			processInfoBuilder.addParameter("C_Order_ID", order.getC_Order_ID());
			processInfoBuilder.addParameter("TypeOfPurchase", purchaseType);
			processInfoBuilder.addParameter("IsPurchaseBOMComponents", purchaseBomComponents);

			processInfoBuilder
					.buildAndPrepareExecution()
					.executeSync()
					.getResult();
		}

	}

	@Then("the order is created:")
	public void thePurchaseOrderIsCreated(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String linkedOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Link_Order_ID + ".Identifier");
			final int linkedOrderId = orderTable.get(linkedOrderIdentifier).getC_Order_ID();
			final I_C_Order purchaseOrder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Order.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Order.COLUMNNAME_Link_Order_ID, linkedOrderId)
					.create()
					.firstOnly(I_C_Order.class);

			final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_Order.COLUMNNAME_IsSOTrx);
			assertThat(purchaseOrder).as("purchaseOrder for Link_Order_ID=%s; Identifier=%s", linkedOrderId, linkedOrderIdentifier).isNotNull();
			assertThat(purchaseOrder.isSOTrx()).isEqualTo(isSOTrx);

			final I_C_DocType docType = load(purchaseOrder.getC_DocTypeTarget_ID(), I_C_DocType.class);

			final String docBaseType = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocBaseType);
			assertThat(docType.getDocBaseType()).isEqualTo(docBaseType);

			final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_DocSubType);
			assertThat(docType.getDocSubType()).isEqualTo(docSubType);

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocStatus);
			if (docStatus != null)
			{
				assertThat(purchaseOrder.getDocStatus()).isEqualTo(docStatus);
			}

			final boolean isDropShip = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_Order.COLUMNNAME_IsDropShip, false);
			assertThat(purchaseOrder.isDropShip()).isEqualTo(isDropShip);
			// TODO: introduce DataTableRows for this whole stepdef
			DataTableRow.singleRow(tableRow)
					.getAsOptionalIdentifier(COLUMNNAME_DropShip_BPartner_ID)
					.map(bpartnerTable::getId)
					.ifPresent(dropShipId -> assertThat(purchaseOrder.getDropShip_BPartner_ID())
							.as("DropShip_BPartner_ID")
							.isEqualTo(dropShipId.getRepoId()));
		}
	}

	@Then("the sales order identified by {string} is closed")
	public void salesOrderIsClosed(
			@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()));

		assertThat(salesOrder.getDocStatus()).isEqualTo(IDocument.STATUS_Closed);
	}

	@Then("the sales order identified by {string} is reversed")
	public void salesOrderIsReversed(
			@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()));

		assertThat(salesOrder.getDocStatus()).isEqualTo(IDocument.STATUS_Reversed);
	}

	@Then("the sales order identified by {string} is not closed")
	public void salesOrderIsNotClosed(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()));

		assertThat(salesOrder.getDocStatus()).isNotEqualTo(IDocument.STATUS_Closed);
	}

	@Then("a PurchaseOrder with externalId {string} is created after not more than {int} seconds and has values")
	public void verifyOrder(final String externalId, final int timeoutSec,
							@NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> dataTableRow = dataTable.asMaps().get(0);

		final Supplier<Boolean> purchaseOrderQueryExecutor = () -> {

			final I_C_Order purchaseOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
					.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalId)
					.create()
					.firstOnly(I_C_Order.class);

			return purchaseOrderRecord != null;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, purchaseOrderQueryExecutor);

		final I_C_Order purchaseOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalId)
				.create()
				.firstOnlyNotNull(I_C_Order.class);

		final String externalPurchaseOrderUrl = DataTableUtil.extractStringForColumnName(dataTableRow, I_C_Order.COLUMNNAME_ExternalPurchaseOrderURL);
		assertThat(purchaseOrderRecord.getExternalPurchaseOrderURL()).isEqualTo(externalPurchaseOrderUrl);

		final String poReference = DataTableUtil.extractStringForColumnName(dataTableRow, I_C_Order.COLUMNNAME_POReference);
		assertThat(purchaseOrderRecord.getPOReference()).isEqualTo(poReference);

		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (EmptyUtil.isNotBlank(orderIdentifier))
		{
			orderTable.putOrReplace(orderIdentifier, purchaseOrderRecord);
		}
	}

	@And("validate the created orders")
	public void validate_created_order(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_Order_ID)
				.forEach(this::validateOrder);
	}

	@And("update order")
	public void update_order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocBaseType);
			final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocSubType);

			if (Check.isNotBlank(docBaseType) && Check.isNotBlank(docSubType))
			{
				final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
						.docBaseType(docBaseType)
						.docSubType(DocSubType.ofNullableCode(docSubType))
						.adClientId(order.getAD_Client_ID())
						.adOrgId(order.getAD_Org_ID())
						.build();

				final DocTypeId docTypeId = docTypeDAO.getDocTypeId(docTypeQuery);

				order.setC_DocType_ID(docTypeId.getRepoId());
				order.setC_DocTypeTarget_ID(docTypeId.getRepoId());
			}

			final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRule);
			if (Check.isNotBlank(paymentRule))
			{
				order.setPaymentRule(paymentRule);
			}

			final Timestamp preparationDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(tableRow, "OPT." + COLUMNNAME_PreparationDate);
			if (preparationDate != null)
			{
				order.setPreparationDate(preparationDate);
			}

			InterfaceWrapperHelper.saveRecord(order);

			orderTable.putOrReplace(orderIdentifier, order);
		}
	}

	@And("^after not more than (.*)s the order is found$")
	public void lookup_order(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> retrieveOrder(tableRow));
		}
	}

	private void validateOrder(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final String identifierStr = identifier.getAsString();
		final I_C_Order order = identifier.lookupNotNullIn(orderTable);
		InterfaceWrapperHelper.refresh(order);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_ID)
				.ifPresent(bpartnerIdentifier -> {
					final BPartnerId expectedBPartnerId = bpartnerTable.getIdOptional(bpartnerIdentifier)
							.orElseGet(() -> bpartnerIdentifier.getAsId(BPartnerId.class));
					softly.assertThat(order.getC_BPartner_ID()).as("C_BPartner_ID for Identifier=%s", identifierStr).isEqualTo(expectedBPartnerId.getRepoId());
				});

		row.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_Location_ID)
				.ifPresent(bpartnerLocationIdentifier -> {
					final Integer expectedBPartnerLocationId = bpartnerLocationTable.getOptional(bpartnerLocationIdentifier)
							.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
							.orElseGet(bpartnerLocationIdentifier::getAsInt);
					softly.assertThat(order.getC_BPartner_Location_ID()).as("C_BPartner_Location_ID for Identifier=%s", identifierStr).isEqualTo(expectedBPartnerLocationId);
				});

		row.getAsOptionalLocalDate(COLUMNNAME_DateOrdered)
				.ifPresent(dateOrdered -> {
					final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
					final ZoneId zoneId = orgDAO.getTimeZone(orgId);
					softly.assertThat(TimeUtil.asLocalDate(order.getDateOrdered(), zoneId)).as("DateOrdered for Identifier=%s", identifierStr).isEqualTo(dateOrdered);
				});

		row.getAsOptionalString(COLUMNNAME_DocBaseType)
				.ifPresent(docBaseType -> {
					final I_C_DocType docType = docTypeDAO.getById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
					softly.assertThat(docType.getDocBaseType()).as("DocBaseType for Identifier=%s", identifierStr).isEqualTo(docBaseType);
				});

		row.getAsOptionalCurrencyCode()
				.ifPresent(currencyCode -> {
					final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
					softly.assertThat(order.getC_Currency_ID()).as("C_Currency_ID for Identifier=%s", identifierStr).isEqualTo(currencyId.getRepoId());
				});

		row.getAsOptionalString(COLUMNNAME_DeliveryRule)
				.ifPresent(deliveryRule -> softly.assertThat(order.getDeliveryRule()).as("DeliveryRule for Identifier=%s", identifierStr).isEqualTo(deliveryRule));

		row.getAsOptionalString(COLUMNNAME_DeliveryViaRule)
				.ifPresent(deliveryViaRule -> softly.assertThat(order.getDeliveryViaRule()).as("DeliveryViaRule for Identifier=%s", identifierStr).isEqualTo(deliveryViaRule));

		row.getAsOptionalBoolean("processed")
				.ifPresent(processed -> softly.assertThat(order.isProcessed()).as("Processed for Identifier=%s", identifierStr).isEqualTo(processed));

		row.getAsOptionalString(COLUMNNAME_ExternalId)
				.ifPresent(externalId -> softly.assertThat(order.getExternalId()).as("ExternalId for Identifier=%s", identifierStr).isEqualTo(externalId));

		row.getAsOptionalString(COLUMNNAME_DocStatus)
				.ifPresent(docStatus -> softly.assertThat(order.getDocStatus()).as("DocStatus for Identifier=%s", identifierStr).isEqualTo(docStatus));

		row.getAsOptionalString(COLUMNNAME_BPartnerName)
				.ifPresent(bpartnerName -> softly.assertThat(order.getBPartnerName()).as("BPartnerName for Identifier=%s", identifierStr).isEqualTo(bpartnerName));

		row.getAsOptionalIdentifier(COLUMNNAME_AD_User_ID)
				.map(userTable::get)
				.ifPresent(user -> softly.assertThat(order.getAD_User_ID()).as("AD_User_ID for Identifier=%s", identifierStr).isEqualTo(user.getAD_User_ID()));

		row.getAsOptionalIdentifier(COLUMNNAME_Bill_BPartner_ID)
				.map(bpartnerTable::get)
				.ifPresent(billBP -> softly.assertThat(order.getBill_BPartner_ID()).as("Bill_BPartner_ID for Identifier=%s", identifierStr).isEqualTo(billBP.getC_BPartner_ID()));

		row.getAsOptionalIdentifier(COLUMNNAME_Bill_Location_ID)
				.map(bpartnerLocationTable::get)
				.ifPresent(billLocation -> softly.assertThat(order.getBill_Location_ID()).as("Bill_Location_ID for Identifier=%s", identifierStr).isEqualTo(billLocation.getC_BPartner_Location_ID()));

		row.getAsOptionalIdentifier(COLUMNNAME_Bill_User_ID)
				.map(userTable::get)
				.ifPresent(billUser -> softly.assertThat(order.getBill_User_ID()).as("Bill_User_ID for Identifier=%s", identifierStr).isEqualTo(billUser.getAD_User_ID()));

		row.getAsOptionalString(COLUMNNAME_EMail)
				.ifPresent(email -> {
					if(DataTableUtil.NULL_STRING.equals(email))
					{
						softly.assertThat(order.getEMail()).as("EMail for Identifier=%s", identifierStr).isNull();
					}
					else
					{
						softly.assertThat(order.getEMail()).as("EMail for Identifier=%s", identifierStr).isEqualTo(email);
					}
				}
				);


		row.getAsOptionalString(COLUMNNAME_InvoiceRule)
				.ifPresent(invoiceRule -> softly.assertThat(order.getInvoiceRule()).as("InvoiceRule for Identifier=%s", identifierStr).isEqualTo(invoiceRule));

		paymentTermStepDef.extractPaymentTermId(row)
				.ifPresent(paymentTermId -> softly.assertThat(PaymentTermId.ofRepoIdOrNull(order.getC_PaymentTerm_ID())).as("C_PaymentTerm_ID for Identifier=%s", identifierStr).isEqualTo(paymentTermId));

		row.getAsOptionalString(COLUMNNAME_PaymentRule)
				.ifPresent(paymentRule -> softly.assertThat(order.getPaymentRule()).as("PaymentRule for Identifier=%s", identifierStr).isEqualTo(paymentRule));

		row.getAsOptionalString(COLUMNNAME_POReference)
				.ifPresent(poReference -> softly.assertThat(order.getPOReference()).as("POReference for Identifier=%s", identifierStr).isEqualTo(poReference));

		row.getAsOptionalString(I_C_Order.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName)
				.ifPresent(internalName -> {
					final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
					softly.assertThat(order.getAD_InputDataSource_ID()).as("AD_InputDataSource_ID for Identifier=%s", identifierStr).isEqualTo(dataSource.getAD_InputDataSource_ID());
				});

		row.getAsOptionalBoolean(COLUMNNAME_IsDropShip)
				.ifPresent(isDropShip -> softly.assertThat(order.isDropShip()).as("IsDropShip for Identifier=%s", identifierStr).isEqualTo(isDropShip));

		row.getAsOptionalIdentifier(COLUMNNAME_DropShip_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(bpartnerId -> softly.assertThat(order.getDropShip_BPartner_ID()).as("DropShip_BPartner_ID for Identifier=%s", identifierStr).isEqualTo(bpartnerId.getRepoId()));

		row.getAsOptionalIdentifier(COLUMNNAME_DropShip_Location_ID)
				.map(bpartnerLocationTable::getId)
				.ifPresent(locationId -> softly.assertThat(order.getDropShip_Location_ID()).as("DropShip_Location_ID for Identifier=%s", identifierStr).isEqualTo(locationId.getRepoId()));

		row.getAsOptionalIdentifier(COLUMNNAME_DropShip_User_ID)
				.map(userTable::get)
				.ifPresent(dropShipUser -> softly.assertThat(order.getDropShip_User_ID()).as("DropShip_User_ID for Identifier=%s", identifierStr).isEqualTo(dropShipUser.getAD_User_ID()));

		row.getAsOptionalBoolean(COLUMNNAME_IsUseHandOver_Location)
				.ifPresent(isHandover -> softly.assertThat(order.isUseHandOver_Location()).as("IsUseHandOver_Location for Identifier=%s", identifierStr).isEqualTo(isHandover));

		row.getAsOptionalIdentifier(COLUMNNAME_HandOver_Partner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(partnerId -> softly.assertThat(order.getHandOver_Partner_ID()).as("HandOver_Partner_ID for Identifier=%s", identifierStr).isEqualTo(partnerId.getRepoId()));

		row.getAsOptionalIdentifier(COLUMNNAME_HandOver_Location_ID)
				.map(bpartnerLocationTable::getId)
				.ifPresent(locationId -> softly.assertThat(order.getHandOver_Location_ID()).as("HandOver_Location_ID for Identifier=%s", identifierStr).isEqualTo(locationId.getRepoId()));

		row.getAsOptionalIdentifier(COLUMNNAME_HandOver_User_ID)
				.map(userTable::get)
				.ifPresent(handoverUser -> softly.assertThat(order.getHandOver_User_ID()).as("HandOver_User_ID for Identifier=%s", identifierStr).isEqualTo(handoverUser.getAD_User_ID()));


		softly.assertAll();
	}

	@Then("the following group compensation order lines were created for externalHeaderId: {string}")
	public void verifyOrderLines(final String externalHeaderId,
								 @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final int line = DataTableUtil.extractIntForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_Line);
			final Boolean isGroupCompensationLine = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine);
			final BigDecimal groupCompensationPercentage = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage);
			final String groupCompensationType = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationType);
			final String groupCompensationAmtType = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType);

			final I_C_Order orderRecord = queryBL.createQueryBuilder(I_C_Order.class)
					.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalHeaderId)
					.create()
					.firstOnlyNotNull(I_C_Order.class);

			final Optional<I_C_OrderLine> orderLine = queryBL.createQueryBuilder(I_C_OrderLine.class)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_Line, line)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, isGroupCompensationLine)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage, groupCompensationPercentage)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationType, groupCompensationType)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType, groupCompensationAmtType)
					.create()
					.firstOnlyOptional(I_C_OrderLine.class);

			assertThat(orderLine).isPresent();
		}
	}

	@When("C_Order is cloned")
	public void clone_order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String clonedOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "ClonedOrder." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Order order = orderTable.get(orderIdentifier);

			assertThat(order).isNotNull();

			final TableRecordReference recordReference = TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID());
			final CopyRecordRequest copyRecordRequest = CopyRecordRequest.builder()
					.tableRecordReference(recordReference)
					.build();

			final PO po = copyRecordService.copyRecord(copyRecordRequest);

			final I_C_Order clonedOrder = load(po.get_ID(), I_C_Order.class);
			orderTable.putOrReplace(clonedOrderIdentifier, clonedOrder);
		}
	}

	@NonNull
	private Boolean retrieveOrder(@NonNull final Map<String, String> row)
	{
		final String docStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DocStatus);

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order order = orderTable.get(orderIdentifier);

		final I_C_Order orderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_DocStatus, docStatus)
				.addEqualsFilter(COLUMNNAME_Processing, false)
				.create()
				.firstOnlyOrNull(I_C_Order.class);

		if (orderRecord == null)
		{
			return false;
		}

		orderTable.putOrReplace(orderIdentifier, orderRecord);
		return true;
	}

	@And("store order-values in TestContext")
	public void storeValuesFromOrderInTestContext(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> {
							final I_C_Order order = row.getAsIdentifier(COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);

							final String column = row.getAsString("Column");
							final Object value = InterfaceWrapperHelper.getValueOrNull(order, column);

							restTestContext.setStringVariableFromRow(row, () -> value == null ? null : value.toString());
						}
				);
	}

	@And("^store sales order PDF endpointPath (.*) in context$")
	public void store_salesOrder_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = "@[a-zA-Z\\d_-]+@";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String orderIdentifierGroup = matcher.group();
			final String orderIdentifier = orderIdentifierGroup.replace("@", "");

			final I_C_Order order = orderTable.get(orderIdentifier);
			assertThat(order).isNotNull();

			endpointPath = endpointPath.replace(orderIdentifierGroup, String.valueOf(order.getC_Order_ID()));

			restTestContext.setEndpointPath(endpointPath);
		}
	}
}
