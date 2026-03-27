/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.StringUtils;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.QtyToDeliverMap;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.logging.LogManager;
import de.metas.material.MovementType;
import de.metas.order.OrderLineId;
import de.metas.process.IADPInstanceDAO;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocSubType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_Name;
import static org.compiere.model.I_M_InOut.COLUMNNAME_C_Order_ID;
import static org.compiere.model.I_M_InOut.COLUMNNAME_DocStatus;
import static org.compiere.model.I_M_InOut.COLUMNNAME_IsSOTrx;
import static org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID;

@RequiredArgsConstructor
public class M_InOut_StepDef
{
	private static final Logger logger = LogManager.getLogger(M_InOut_StepDef.class);
	private final M_InOut_StepDefData inoutTable;
	private final M_InOutLine_StepDefData inoutLineTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final AD_Message_StepDefData messageTable;
	private final C_DocType_StepDefData docTypeTable;
	private final M_HU_StepDefData huTable;
	private final TestContext restTestContext;

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final ShipmentService shipmentService = SpringContextHolder.instance.getBean(ShipmentService.class);
	private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IHUShipmentAssignmentBL huShipmentAssignmentBL = Services.get(IHUShipmentAssignmentBL.class);

	/**
	 * Validate M_InOut records (shipments or material receipts).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) alias from M_InOut_StepDefData<br>
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) expected business partner<br>
	 *   <b>C_BPartner_Location_ID</b> — (required, identifier-ref) expected BP location<br>
	 *   <b>DateOrdered</b> — (required) expected date, e.g., "2022-05-17"<br>
	 *   <b>processed</b> — (required) true/false<br>
	 *   <b>DocStatus</b> — (required) expected doc status: DR, IP, CO, VO, RE, CL<br>
	 *   <b>POReference</b> — (optional) expected PO reference<br>
	 *   <b>AD_InputDataSource_ID.InternalName</b> — (optional) expected input data source internal name<br>
	 *   <b>ExternalSystem.Value</b> — (optional) expected external system value<br>
	 *   <b>C_DocType.DocBaseType</b> — (optional) expected doc base type + C_DocType.Name<br>
	 *   <b>ExternalId</b> — (optional) expected external ID<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, C_BPartner_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate the created shipments
	 *   | M_InOut_ID | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | processed | DocStatus |
	 *   | shipment_1 | bpartner_1    | bpLocation_1           | 2022-05-17  | true      | CO        |
	 * </pre>
	 */
	@And("^validate the created (shipments|material receipt)$")
	public void validate_created_shipments(@NonNull final String ignoredInoutType, @NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::validate_created_shipment);
	}

	private void validate_created_shipment(final DataTableRow row)
	{
		logger.info("validate_created_shipment: {}", row);
		final SoftAssertions softly = new SoftAssertions();

		final StepDefDataIdentifier identifier = row.getAsIdentifier("M_InOut_ID");
		final LocalDate dateOrdered = row.getAsLocalDate(I_M_InOut.COLUMNNAME_DateOrdered);
		final String poReference = row.getAsOptionalString(I_M_InOut.COLUMNNAME_POReference).orElse(null);
		final boolean processed = row.getAsBoolean("processed");
		final String docStatus = row.getAsString(I_M_InOut.COLUMNNAME_DocStatus);

		final @NonNull StepDefDataIdentifier bpartnerIdentifier = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
		final int expectedBPartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(bpartnerIdentifier::getAsInt);

		final @NonNull StepDefDataIdentifier bpartnerLocationIdentifier = row.getAsIdentifier(COLUMNNAME_C_BPartner_Location_ID);
		final int expectedBPartnerLocationId = bpartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(bpartnerLocationIdentifier::getAsInt);

		final I_M_InOut inout = inoutTable.get(identifier);

		softly.assertThat(inout.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		softly.assertThat(inout.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
		softly.assertThat(TimeUtil.asLocalDate(inout.getDateOrdered())).isEqualTo(dateOrdered);

		if (Check.isNotBlank(poReference))
		{
			softly.assertThat(inout.getPOReference()).isEqualTo(poReference);
		}

		softly.assertThat(inout.isProcessed()).isEqualTo(processed);
		softly.assertThat(inout.getDocStatus()).isEqualTo(docStatus);

		final String internalName = row.getAsOptionalString(I_M_InOut.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName).orElse(null);
		if (Check.isNotBlank(internalName))
		{
			final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
			softly.assertThat(inout.getAD_InputDataSource_ID()).isEqualTo(dataSource.getAD_InputDataSource_ID());
		}

		row.getAsOptionalString(I_ExternalSystem.Table_Name + "." + I_ExternalSystem.COLUMNNAME_Value)
				.ifPresent(externalSystemValue -> {
					final ExternalSystemId externalSystemId = externalSystemRepository.getIdByType(ExternalSystemType.ofValue(externalSystemValue));
					softly.assertThat(inout.getExternalSystem_ID()).as("ExternalSystem_ID for value=%s", externalSystemValue).isEqualTo(externalSystemId.getRepoId());
				});

		row.getAsOptionalEnum(I_C_DocType.Table_Name + "." + COLUMNNAME_DocBaseType, DocBaseType.class)
				.ifPresent(docBaseType -> {
					final String docTypeName = row.getAsString(I_C_DocType.Table_Name + "." + COLUMNNAME_Name);

					final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
							.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
							.addEqualsFilter(COLUMNNAME_Name, docTypeName)
							.create()
							.firstOnlyNotNull(I_C_DocType.class);

					softly.assertThat(inout.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
				});

		row.getAsOptionalString(I_M_InOut.COLUMNNAME_ExternalId).
				ifPresent(externalId -> softly.assertThat(inout.getExternalId()).isEqualTo(externalId));

		softly.assertAll();
	}

	/**
	 * Invoke the "generate shipments" async workpackage for each M_ShipmentSchedule row individually.
	 * Each row specifies its own QuantityType, IsCompleteShipments, and IsShipmentDateToday.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) shipment schedule alias<br>
	 *   <b>QuantityType</b> — (required) "D" (delivery), "O" (ordered), etc.<br>
	 *   <b>IsCompleteShipments</b> — (required) true/false — auto-complete the generated shipment<br>
	 *   <b>IsShipmentDateToday</b> — (required) true/false — use today as shipment date<br>
	 *   <b>QtyToDeliver_Override</b> — (optional) override quantity to deliver<br>
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
	 *   | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipmentDateToday |
	 *   | shipmentSchedule_1    | D            | true                | false               |
	 * </pre>
	 */
	@And("'generate shipments' process is invoked individually for each M_ShipmentSchedule")
	public void invokeGenerateShipmentsProcessIndividually(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(tableRow ->
				{
					final String quantityType = DataTableUtil.extractStringForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_QuantityType);
					final boolean isCompleteShipments = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
					final boolean isShipToday = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);

					invokeGenerateShipmentsProcess0(quantityType, isCompleteShipments, isShipToday, DataTableRows.of(tableRow));
				}
		);
	}

	@And("'generate shipments' process is invoked individually for each M_ShipmentSchedule and expects error message")
	public void invokeGenerateShipmentsProcessIndividuallyAndAssertErrorMessage(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(tableRow ->
				{
					final String quantityType = DataTableUtil.extractStringForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_QuantityType);
					final boolean isCompleteShipments = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
					final boolean isShipToday = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);

					final String errorMessage = DataTableUtil.extractStringForColumnName(tableRow, "AD_Message.Value");

					try
					{
						invokeGenerateShipmentsProcess0(quantityType, isCompleteShipments, isShipToday, DataTableRows.ofRows(ImmutableList.of(tableRow)));

						assertThat(errorMessage).as("An error message should had been thrown!").isNull();
					}
					catch (final Exception e)
					{
						final AdMessageKey expectedErrorMessage = AdMessageKey.of("de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords");
						StepDefUtil.validateErrorMessage(e, msgBL.getTranslatableMsgText(expectedErrorMessage).translate(Optional.ofNullable(Env.getAD_Language())
								.orElse(Language.getBaseAD_Language())));
					}
				}
		);
	}

	/**
	 * Invoke the "generate shipments" async workpackage with parameters in the step text.
	 * All shipment schedules in the DataTable are enqueued together (single workpackage).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) shipment schedule alias<br>
	 *   <b>QtyToDeliver_Override</b> — (optional) override quantity to deliver<br>
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
	 *   | M_ShipmentSchedule_ID |
	 *   | shipmentSchedule_1    |
	 * </pre>
	 */
	@And("^'generate shipments' process is invoked with QuantityType=(.*), IsCompleteShipments=(true|false) and IsShipToday=(true|false)")
	public void invokeGenerateShipmentsProcess(
			@NonNull final String quantityType,
			final boolean isCompleteShipments,
			final boolean isShipToday,
			@NonNull final DataTable table)
	{
		invokeGenerateShipmentsProcess0(quantityType, isCompleteShipments, isShipToday, DataTableRows.of(table));
	}

	public void invokeGenerateShipmentsProcess0(
			@NonNull final String quantityType,
			final boolean isCompleteShipments,
			final boolean isShipToday,
			@NonNull final DataTableRows dataTable)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, StockQtyAndUOMQty> qtysToDeliverOverride = ImmutableMap.builder();
		final ArrayList<ShipmentScheduleId> schedIdsToEnqueue = new ArrayList<>();
		dataTable.forEach(tableRow ->
				{
					final StepDefDataIdentifier shipmentScheduleIdentifier = tableRow.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
					final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
					schedIdsToEnqueue.add(shipmentScheduleId);

					tableRow.getAsOptionalBigDecimal(ShipmentScheduleWorkPackageParameters.PARAM_QtyToDeliver_Override)
							.ifPresent(qtyToDeliverOverride ->
							{
								final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
								qtysToDeliverOverride.put(shipmentScheduleId, StockQtyAndUOMQtys.ofQtyInStockUOM(qtyToDeliverOverride, productId));
							});
				}
		);

		final IQueryFilter<de.metas.handlingunits.model.I_M_ShipmentSchedule> queryFilter = queryBL.createCompositeQueryFilter(de.metas.handlingunits.model.I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, schedIdsToEnqueue);

		final ShipmentScheduleWorkPackageParameters.ShipmentScheduleWorkPackageParametersBuilder workPackageParametersBuilder = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(pinstanceDAO.createSelectionId())
				// NOTE: keep in sync with de.metas.handlingunits.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.createShipmentSchedulesQueryFilters
				.queryFilters(shipmentService.createShipmentScheduleEnqueuerQueryFilters(queryFilter, Env.getZonedDateTime().toInstant()))
				.qtysToDeliverOverride(QtyToDeliverMap.ofMap(qtysToDeliverOverride.build()))
				.quantityType(M_ShipmentSchedule_QuantityTypeToUse.ofCode(quantityType))
				.completeShipments(StringUtils.toBoolean(isCompleteShipments))
				.isShipmentDateToday(StringUtils.toBoolean(isShipToday));

		final ShipmentScheduleEnqueuer.Result result = ShipmentScheduleEnqueuer.newInstance()
				.createWorkpackages(workPackageParametersBuilder.build());

		assertThat(result.getEnqueuedPackagesCount()).isGreaterThanOrEqualTo(1);
	}

	/**
	 * Poll for a shipment/receipt created via async workpackage processing. Waits up to N seconds.
	 * Finds the M_InOut via M_ShipmentSchedule_QtyPicked.M_InOutLine_ID, filters by DocStatus if given.
	 * Stores the found M_InOut in M_InOut_StepDefData under the given identifier.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) the schedule that triggered the shipment<br>
	 *   <b>M_InOut_ID</b> — (required) alias to store the found shipment under<br>
	 *   <b>DocStatus</b> — (optional) filter by doc status (CO, DR, etc.)<br>
	 *   <b>OPT.IgnoreCreated.M_InOut_ID.Identifier</b> — (optional) comma-separated shipment identifiers to skip<br>
	 *   <b>REST.Context.M_InOut_ID</b> — (optional) store M_InOut_ID in REST test context variable<br>
	 *   <b>REST.Context.DocumentNo</b> — (optional) store DocumentNo in REST test context variable<br>
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, M_InOut is found:
	 *   | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
	 *   | shipmentSchedule_1    | shipment_1 | CO        |
	 * </pre>
	 */
	@And("^after not more than (.*)s, M_InOut is found:$")
	public void shipmentIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRow firstRow = DataTableRows.of(dataTable).getFirstRow();
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(
				firstRow.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupIn(shipmentScheduleTable),
				I_M_ShipmentSchedule.class);

		final StepDefDataIdentifier shipmentIdentifier = firstRow.getAsIdentifier(COLUMNNAME_M_InOut_ID);
		final Optional<String> docStatus = firstRow.getAsOptionalString(I_M_InOut.COLUMNNAME_DocStatus);

		final Optional<String> alreadyCreatedShipmentIdentifiers = firstRow.getAsOptionalString("OPT.IgnoreCreated" + "." + COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Set<InOutLineId> alreadyCreatedShipmentLines = alreadyCreatedShipmentIdentifiers
				.map(StepDefUtil::extractIdentifiers)
				.map(this::getShipmentLinesForShipmentIdentifiers)
				.orElseGet(ImmutableSet::of);

		final Set<Integer> linesToIgnore = alreadyCreatedShipmentLines.isEmpty()
				? ImmutableSet.of(-1)
				: alreadyCreatedShipmentLines.stream().map(InOutLineId::getRepoId).collect(ImmutableSet.toImmutableSet());

		final Supplier<Boolean> isShipmentCreated = () -> {

			final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.addNotNull(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID)
					.addNotInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, linesToIgnore)
					.create()
					.list(I_M_ShipmentSchedule_QtyPicked.class);

			if (qtyPickedRecords.isEmpty())
			{
				return false;
			}

			final Set<InOutLineId> shipmentLineIds = qtyPickedRecords.stream()
					.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
					.map(InOutLineId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());

			final Set<InOutId> inOutIds = queryBL
					.createQueryBuilder(I_M_InOutLine.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLineIds)
					.create()
					.stream()
					.map(I_M_InOutLine::getM_InOut_ID)
					.map(InOutId::ofRepoId)
					.collect(Collectors.toSet());

			if (inOutIds.size() > 1)
			{
				throw new AdempiereException("More than one M_InOut found for shipmentSchedule=" + shipmentSchedule.getM_ShipmentSchedule_ID());
			}

			final IQueryBuilder<I_M_InOut> shipmentQueryBuilder = queryBL
					.createQueryBuilder(I_M_InOut.class)
					.addOnlyActiveRecordsFilter();

			docStatus.map(status -> shipmentQueryBuilder.addEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, status));

			final I_M_InOut shipment = shipmentQueryBuilder
					.addEqualsFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, inOutIds.iterator().next().getRepoId())
					.create()
					.firstOnly(I_M_InOut.class);

			if (shipment != null)
			{
				inoutTable.getOptional(shipmentIdentifier).ifPresent(prevShipment -> assertThat(prevShipment.getM_InOut_ID()).isEqualTo(shipment.getM_InOut_ID()));
				inoutTable.putOrReplace(shipmentIdentifier, shipment);
				restTestContext.setIntVariableFromRow(firstRow, shipment::getM_InOut_ID);

				firstRow.getAsOptionalIdentifier("REST.Context.M_InOut_ID")
						.ifPresent(id -> restTestContext.setVariable(id.getAsString(), shipment.getM_InOut_ID()));
				firstRow.getAsOptionalIdentifier("REST.Context.DocumentNo")
						.ifPresent(id -> restTestContext.setVariable(id.getAsString(), shipment.getDocumentNo()));

				return true;
			}

			return false;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentCreated);
	}

	/**
	 * Reverse a shipment/receipt and store the reversal under a new identifier.
	 * Performs Reverse_Correct doc action, then loads the reversal M_InOut record via Reversal_ID.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text, not a DataTable)
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the shipment identified by shipment_1 is reversed as shipment_1_reversal
	 * </pre>
	 */
	@And("^the (shipment|material receipt|return inOut) identified by (.*) is reversed as (.*)$")
	public void reverseInOut(
			@NonNull @SuppressWarnings("unused") final String model_UNUSED,
			@NonNull final String identifier,
			@NonNull final String reversalIdentifier)
	{
		final I_M_InOut inout = inoutTable.get(identifier);
		InterfaceWrapperHelper.refresh(inout);
		documentBL.processEx(inout, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		final InOutId reversalId = InOutId.ofRepoId(inout.getReversal_ID());
		final I_M_InOut reversal = inOutDAO.getById(reversalId);
		inoutTable.put(reversalIdentifier, reversal);
	}

	/**
	 * Process a shipment/receipt/return with a document action. No DataTable — parameters are in the step text.
	 * Actions: completed, reactivated, reversed, voided, closed.
	 * Note: "reversed" does NOT store the reversal; use "is reversed as &lt;reversalIdentifier&gt;" step instead if you need the reversal record.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text)
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the shipment identified by shipment_1 is completed
	 * And the shipment identified by shipment_1 is reversed
	 * And the material receipt identified by receipt_1 is completed
	 * </pre>
	 */
	@And("^the (shipment|material receipt|return inOut) identified by (.*) is (completed|reactivated|reversed|voided|closed)$")
	public void processInOut(
			@NonNull @SuppressWarnings("unused") final String model_UNUSED,
			@NonNull final String shipmentIdentifier,
			@NonNull final String action)
	{
		final I_M_InOut shipment = inoutTable.get(shipmentIdentifier);
		InterfaceWrapperHelper.refresh(shipment);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed:
				shipment.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MInOut.completeIt() won't complete it
				documentBL.processEx(shipment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			case reactivated:
				shipment.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MInOut.completeIt() won't complete it
				documentBL.processEx(shipment, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				break;
			case reversed:
				shipment.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MInOut.completeIt() won't complete it
				documentBL.processEx(shipment, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			case voided:
				shipment.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MInOut.completeIt() won't complete it
				documentBL.processEx(shipment, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case closed:
				shipment.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MInOut.completeIt() won't complete it
				documentBL.processEx(shipment, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			default:
				throw new AdempiereException("Unhandled M_InOut action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	/**
	 * Load an existing M_InOut by matching on M_InOutLine.QtyEntered (and optionally C_OrderLine_ID).
	 * Validates DocStatus and optionally C_Order_ID. Stores both the line and header in StepDefData.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>QtyEntered</b> — (required) quantity to match the M_InOutLine by<br>
	 *   <b>M_InOutLine_ID</b> — (required, identifier) alias to store the found shipment line<br>
	 *   <b>M_InOut_ID</b> — (required, identifier) alias to store the found shipment header<br>
	 *   <b>DocStatus</b> — (required) expected document status (CO, DR, etc.)<br>
	 *   <b>C_OrderLine_ID</b> — (optional, identifier-ref) narrow search to lines for a specific order line<br>
	 *   <b>C_Order_ID</b> — (optional, identifier-ref) assert the shipment belongs to a specific order<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_InOutLine_StepDefData, C_OrderLine_StepDefData, C_Order_StepDefData
	 */
	@And("load M_InOut:")
	public void loadM_InOut(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final BigDecimal qtyEntered = row.getAsBigDecimal(I_M_InOutLine.COLUMNNAME_QtyEntered);

			final IQueryBuilder<I_M_InOutLine> shipmentLineBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_QtyEntered, qtyEntered);

			row.getAsOptionalIdentifier(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID)
					.ifPresent(orderLineIdentifier -> {
						final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
						shipmentLineBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
					});

			final I_M_InOutLine shipmentLine = shipmentLineBuilder.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			final StepDefDataIdentifier shipmentLineIdentifier = row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
			inoutLineTable.putOrReplace(shipmentLineIdentifier, shipmentLine);

			final I_M_InOut shipment = InterfaceWrapperHelper.load(shipmentLine.getM_InOut_ID(), I_M_InOut.class);
			assertThat(shipment).isNotNull();

			final String docStatus = row.getAsString(I_M_InOut.COLUMNNAME_DocStatus);
			assertThat(shipment.getDocStatus()).isEqualTo(docStatus);

			row.getAsOptionalIdentifier(COLUMNNAME_C_Order_ID)
					.ifPresent(orderIdentifier -> {
						final I_C_Order order = orderTable.get(orderIdentifier);
						assertThat(shipment.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
					});

			final StepDefDataIdentifier shipmentIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
			inoutTable.putOrReplace(shipmentIdentifier, shipment);
		});
	}

	/**
	 * Perform an arbitrary document action on a shipment/receipt. Uses DocAction codes directly (CO, RC, RA, VO, CL).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment to process<br>
	 *   <b>DocAction</b> — (required) document action code: CO, RC, RA, VO, CL<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And perform shipment document action
	 *   | M_InOut_ID | DocAction |
	 *   | shipment_1 | RC        |
	 * </pre>
	 */
	@And("perform shipment document action")
	public void reverseShipment(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(row ->
		{
			final I_M_InOut shipment = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable);
			final String docAction = row.getAsString(I_M_InOut.COLUMNNAME_DocAction);
			documentBL.processEx(shipment, docAction);
		});
	}

	/**
	 * Locate M_InOut via M_ShipmentSchedule_QtyPicked and store it in StepDefData.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) the schedule that triggered the shipment<br>
	 *   <b>M_InOut_ID</b> — (required, identifier) alias to store the found shipment<br>
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData, M_InOut_StepDefData
	 */
	@Then("locate M_InOut by shipment schedule Id")
	public void locate_shipment_by_scheduleId(@NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::locateShipmentByScheduleId);
	}

	@And("validate M_In_Out status")
	public void validate_M_In_Out_status(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_InOut_ID)
				.forEach(row -> {
					final I_M_InOut shipment = row.getAsIdentifier().lookupNotNullIn(inoutTable);
					InterfaceWrapperHelper.refresh(shipment);

					final SoftAssertions softly = new SoftAssertions();

					row.getAsOptionalEnum(COLUMNNAME_DocStatus, DocStatus.class)
							.ifPresent(docStatus -> assertThat(shipment.getDocStatus()).as("DocStatus").isEqualTo(docStatus.getCode()));

					softly.assertAll();
				});
	}

	@And("^reset M_InOut packing lines for shipment (.*)$")
	public void reset_M_InOut_PackingLines(@NonNull final String shipmentIdentifier)
	{
		final I_M_InOut shipment = inoutTable.get(shipmentIdentifier);
		assertThat(shipment).isNotNull();

		huInOutBL.recreatePackingMaterialLines(shipment);
	}

	@And("^validate no M_InOut found for C_Order identified by (.*)$")
	public void no_M_InOut_found(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		assertThat(order).isNotNull();

		final I_M_InOut inOut = queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.firstOnly(I_M_InOut.class);

		assertThat(inOut).isNull();
	}

	/**
	 * Manually create M_InOut records (shipment/receipt). Primarily used for testing doc actions on manually created documents.
	 * Uses legacy DataTableUtil (not DataTableRow). Stores created record in M_InOut_StepDefData.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID.Identifier</b> — (required) alias to store the created record<br>
	 *   <b>C_BPartner_ID.Identifier</b> — (required) business partner<br>
	 *   <b>C_BPartner_Location_ID.Identifier</b> — (required) BP location<br>
	 *   <b>IsSOTrx</b> — (required) true=shipment, false=receipt<br>
	 *   <b>DeliveryRule</b> — (required) delivery rule code<br>
	 *   <b>DeliveryViaRule</b> — (required) delivery via rule code<br>
	 *   <b>FreightCostRule</b> — (required) freight cost rule code<br>
	 *   <b>M_Warehouse_ID.Identifier</b> — (required) warehouse<br>
	 *   <b>MovementDate</b> — (required) movement date<br>
	 *   <b>MovementType</b> — (required) movement type code<br>
	 *   <b>PriorityRule</b> — (required) priority rule code<br>
	 *   <b>OPT.DocBaseType</b> — (optional) doc base type + OPT.DocSubType for doc type lookup<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, C_BPartner_StepDefData, C_BPartner_Location_StepDefData, M_Warehouse_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_InOut:
	 *   | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule |
	 *   | shipment_1            | bpartner_1               | bpLocation_1                      | true    | F            | D               | I               | warehouse_1               | 2022-05-17   | C-           | 5            |
	 * </pre>
	 */
	@And("metasfresh contains M_InOut:")
	public void create_M_InOut(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);

			final I_C_BPartner bPartner = row.getAsIdentifier(I_M_InOut.COLUMNNAME_C_BPartner_ID).lookupNotNullIn(bpartnerTable);
			inOut.setC_BPartner_ID(bPartner.getC_BPartner_ID());

			final I_C_BPartner_Location bPartnerLocation = row.getAsIdentifier(I_M_InOut.COLUMNNAME_C_BPartner_Location_ID).lookupNotNullIn(bpartnerLocationTable);
			inOut.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());

			inOut.setIsSOTrx(row.getAsBoolean(COLUMNNAME_IsSOTrx));

			row.getAsOptionalString(COLUMNNAME_DocBaseType).ifPresent(docBaseType -> {
				final String docSubType = row.getAsOptionalString(COLUMNNAME_DocSubType).orElse(null);

				final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
						.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
						.addEqualsFilter(COLUMNNAME_DocSubType, docSubType)
						.create()
						.firstOnlyNotNull(I_C_DocType.class);

				inOut.setC_DocType_ID(docType.getC_DocType_ID());
			});

			inOut.setDeliveryRule(row.getAsString(I_M_InOut.COLUMNNAME_DeliveryRule));
			inOut.setDeliveryViaRule(row.getAsString(I_M_InOut.COLUMNNAME_DeliveryViaRule));
			inOut.setFreightCostRule(row.getAsString(I_M_InOut.COLUMNNAME_FreightCostRule));

			row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_Warehouse_ID)
					.ifPresent(warehouseIdentifier -> {
						final int warehouseId = warehouseIdentifier.lookupNotNullIn(warehouseTable).getM_Warehouse_ID();
						inOut.setM_Warehouse_ID(warehouseId);
					});

			inOut.setMovementDate(row.getAsLocalDateTimestamp(I_M_InOut.COLUMNNAME_MovementDate));
			inOut.setMovementType(row.getAsString(I_M_InOut.COLUMNNAME_MovementType));
			inOut.setPriorityRule(row.getAsString(I_M_InOut.COLUMNNAME_PriorityRule));

			InterfaceWrapperHelper.saveRecord(inOut);

			final StepDefDataIdentifier inOutIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
			inoutTable.putOrReplace(inOutIdentifier, inOut);
		});
	}

	private void locateShipmentByScheduleId(@NonNull final DataTableRow row)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(
				row.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupIn(shipmentScheduleTable),
				I_M_ShipmentSchedule.class);

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);
		final InOutLineId lineId = InOutLineId.ofRepoId(shipmentScheduleQtyPickedRecords.get(0).getM_InOutLine_ID());

		final I_M_InOut shipmentRecord = inOutDAO.retrieveInOutByLineIds(ImmutableSet.of(lineId)).get(lineId);

		final StepDefDataIdentifier shipmentIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
		inoutTable.put(shipmentIdentifier, shipmentRecord);
	}

	/**
	 * Poll for a customer return M_InOut matching a C_Order and C_DocType.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) the order the return is linked to<br>
	 *   <b>C_DocType_ID</b> — (required, identifier-ref) the expected doc type<br>
	 *   <b>M_InOut_ID</b> — (required, identifier) alias to store the found customer return<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_DocType_StepDefData, M_InOut_StepDefData
	 */
	@And("^after not more than (.*)s, Customer Return is found:$")
	public void customerReturnIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach(row -> findCustomerReturn(timeoutSec, row));
	}

	private void findCustomerReturn(
			final int timeoutSec,
			@NonNull final DataTableRow row) throws InterruptedException
	{
		final I_M_InOut customerReturnRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> isCustomerReturnFound(row));

		final StepDefDataIdentifier customerReturnIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
		inoutTable.put(customerReturnIdentifier, customerReturnRecord);
	}

	@And("^the (shipment|material receipt) identified by (.*) is (completed) and an exception with error-code (.*) is thrown")
	public void complete_inOut_expect_exception(
			@NonNull final String model_UNUSED,
			@NonNull final String shipmentIdentifier,
			@NonNull final String action,
			@NonNull final String errorCode)
	{
		try
		{
			processInOut(model_UNUSED, shipmentIdentifier, action);
			assertThat(1).as("An Exception should have been thrown !").isEqualTo(2);
		}
		catch (final AdempiereException exception)
		{
			assertThat(exception.getErrorCode()).as("ErrorCode of %s", exception).isEqualTo(errorCode);
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_InOut> isCustomerReturnFound(@NonNull final DataTableRow row)
	{
		final I_C_Order orderRecord = row.getAsIdentifier(I_M_InOut.COLUMNNAME_C_Order_ID).lookupNotNullIn(orderTable);
		final I_C_DocType docTypeRecord = row.getAsIdentifier(I_M_InOut.COLUMNNAME_C_DocType_ID).lookupNotNullIn(docTypeTable);

		final Optional<I_M_InOut> customerReturnRecord = queryBL
				.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeRecord.getC_DocType_ID())
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.firstOnlyOptional(I_M_InOut.class);

		return customerReturnRecord.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound(getCurrentCustomerReturnContext(orderRecord, docTypeRecord)));
	}

	@NonNull
	private String getCurrentCustomerReturnContext(@NonNull final I_C_Order orderRecord, @NonNull final I_C_DocType docTypeRecord)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for customer return instance with:").append("\n")
				.append(I_M_InOut.COLUMNNAME_C_Order_ID).append(" : ").append(orderRecord.getC_Order_ID()).append("\n")
				.append(I_M_InOut.COLUMNNAME_C_DocType_ID).append(" : ").append(docTypeRecord.getC_DocType_ID()).append("\n");

		message.append("See all M_InOut records for C_Order_ID = ").append(orderRecord.getC_Order_ID()).append(" and C_DocType_ID = ")
				.append(docTypeRecord.getC_DocType_ID()).append(":\n");

		queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeRecord.getC_DocType_ID())
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.stream()
				.forEach(inOut -> message
						.append("-->").append(I_M_InOut.COLUMNNAME_C_BPartner_ID).append(" : ").append(inOut.getC_BPartner_ID()).append(" ; ")
						.append("-->").append(I_M_InOut.COLUMNNAME_C_Invoice_ID).append(" : ").append(inOut.getC_Invoice_ID()).append(" ; ")
						.append("-->").append(I_M_InOut.COLUMNNAME_M_Warehouse_ID).append(" : ").append(inOut.getM_Warehouse_ID()).append(" ; "));

		return message.toString();
	}

	/**
	 * Process a document action on a shipment/receipt and expect it to throw an error.
	 * Optionally validates the error message against an AD_Message record.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Message_ID</b> — (optional, identifier-ref) expected error message from AD_Message<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, AD_Message_StepDefData
	 */
	@And("^the (shipment|material receipt|return inOut) identified by (.*) is (completed|reactivated|reversed|voided|closed) expecting error$")
	public void shipment_action_expecting_error(@NonNull final String model_UNUSED, @NonNull final String shipmentIdentifier, @NonNull final String action, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).getFirstRow();

		boolean errorThrown = false;

		try
		{
			processInOut(model_UNUSED, shipmentIdentifier, action);
		}
		catch (final Exception e)
		{
			errorThrown = true;

			row.getAsOptionalIdentifier(COLUMNNAME_AD_Message_ID)
					.ifPresent(errorMessageIdentifier -> {
						final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);
						assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
					});
		}

		assertThat(errorThrown).isTrue();
	}

	/**
	 * Poll for the reversal M_InOut of a previously reversed shipment/receipt.
	 * Looks up by Reversal_ID pointing back to the original, validates DocStatus.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID.Identifier</b> — (required) alias to store the reversal record<br>
	 *   <b>Reversal_ID.Identifier</b> — (required) alias of the original (reversed) M_InOut<br>
	 *   <b>DocStatus</b> — (optional) expected doc status of the reversal<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, locate reversal M_InOut
	 *   | M_InOut_ID.Identifier | Reversal_ID.Identifier | DocStatus |
	 *   | shipment_1_reversal   | shipment_1             | RE        |
	 * </pre>
	 */
	@And("^after not more than (.*)s, locate reversal M_InOut$")
	public void find_reversal_M_InOut(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_InOut reversalInOut = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_reversal_InOut(row));

			softly.assertThat(reversalInOut).isNotNull();

			final StepDefDataIdentifier reversalInOutIdentifier = row.getAsIdentifier(COLUMNNAME_M_InOut_ID);
			inoutTable.putOrReplace(reversalInOutIdentifier, reversalInOut);
		});
		softly.assertAll();
	}

	@NonNull
	private Set<InOutLineId> getShipmentLinesForShipmentIdentifiers(@NonNull final List<String> shipmentIdentifiers)
	{
		final Set<Integer> shipmentIds = shipmentIdentifiers.stream()
				.map(inoutTable::get)
				.map(I_M_InOut::getM_InOut_ID)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentIds)
				.create()
				.stream()
				.map(I_M_InOutLine::getM_InOutLine_ID)
				.map(InOutLineId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_InOut> load_reversal_InOut(@NonNull final DataTableRow row)
	{
		final I_M_InOut inOutToReverse = row.getAsIdentifier(I_M_InOut.COLUMNNAME_Reversal_ID).lookupNotNullIn(inoutTable);

		final I_M_InOut reversalInOutRecord = queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_Reversal_ID, inOutToReverse.getM_InOut_ID())
				.create()
				.firstOnlyOrNull(I_M_InOut.class);

		if (reversalInOutRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No reversal I_M_InOut found for Reversal_ID=" + inOutToReverse.getM_InOut_ID());
		}

		return ItemProvider.ProviderResult.resultWasFound(reversalInOutRecord);
	}

	@And("^Wait until (shipment|shipments|receipt|receipts) (.*) (is|are) posted$")
	public void waitUntilPosted(
			@SuppressWarnings("unused") final String type,
			@NonNull final String commaSeparatedIdentifiers,
			@SuppressWarnings("unused") final String isOrAre) throws InterruptedException
	{
		final ImmutableSet<TableRecordReference> inoutRefs = StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedIdentifiers)
				.stream()
				.map(inoutTable::getId)
				.map(inoutId -> TableRecordReference.of(I_M_InOut.Table_Name, inoutId))
				.collect(ImmutableSet.toImmutableSet());

		AccountingCucumberHelper.waitUtilPosted(inoutRefs);
	}

	@And("^store shipment PDF endpointPath (.*) in context$")
	public void store_shipment_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String regex = "@[a-zA-Z\\d_-]+@";

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String shipmentIdentifierGroup = matcher.group();
			final String shipmentIdentifier = shipmentIdentifierGroup.replace("@", "");

			final I_M_InOut shipment = inoutTable.get(shipmentIdentifier);
			assertThat(shipment).isNotNull();

			endpointPath = endpointPath.replace(shipmentIdentifierGroup, String.valueOf(shipment.getM_InOut_ID()));

			restTestContext.setEndpointPath(endpointPath);
		}
	}

	/**
	 * Create a customer return M_InOut by copying from an existing shipment.
	 * Copies lines with Return_Origin_InOutLine_ID and sets the return warehouse/locator.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) source shipment to create return from<br>
	 *   <b>CustomerReturn_ID</b> — (required, identifier) alias to store the created customer return<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And generate customer return from shipment
	 *   | M_InOut_ID | CustomerReturn_ID |
	 *   | shipment_1 | customerReturn_1  |
	 * </pre>
	 */
	@And("generate customer return from shipment")
	public void generateCustomerReturnFromShipment(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_InOut shipment = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable);

			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(DocBaseType.MaterialReceipt)
					.isSOTrx(true)
					.adClientId(shipment.getAD_Client_ID())
					.adOrgId(shipment.getAD_Org_ID())
					.build());

			final WarehouseId warehouseId = huWarehouseDAO.retrieveFirstQualityReturnWarehouseId();
			final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);

			final I_M_InOut customerReturn = InterfaceWrapperHelper.copy()
					.setSkipCalculatedColumns(true)
					.setFrom(shipment)
					.copyToNew(I_M_InOut.class);
			customerReturn.setC_DocType_ID(docTypeId.getRepoId());
			customerReturn.setIsSOTrx(true);
			customerReturn.setMovementType(MovementType.CustomerReturns.getCode());
			customerReturn.setReturn_Origin_InOut_ID(shipment.getM_InOut_ID());
			customerReturn.setMovementDate(SystemTime.asTimestamp());
			customerReturn.setDateAcct(SystemTime.asTimestamp());
			customerReturn.setM_Warehouse_ID(warehouseId.getRepoId());
			InterfaceWrapperHelper.save(customerReturn);

			for (final org.compiere.model.I_M_InOutLine shipmentLine : inOutBL.getLines(shipment))
			{
				final I_M_InOutLine returnLine = InterfaceWrapperHelper.copy()
						.setSkipCalculatedColumns(true)
						.setFrom(shipmentLine)
						.copyToNew(I_M_InOutLine.class);
				returnLine.setM_InOut_ID(customerReturn.getM_InOut_ID());
				returnLine.setReturn_Origin_InOutLine_ID(shipmentLine.getM_InOutLine_ID());
				returnLine.setM_Locator_ID(locatorId.getRepoId());
				returnLine.setC_OrderLine_ID(OrderLineId.toRepoId(null));
				InterfaceWrapperHelper.save(returnLine);
			}

			final StepDefDataIdentifier returnIdentifier = row.getAsIdentifier("CustomerReturn_ID");
			inoutTable.putOrReplace(returnIdentifier, customerReturn);
		});
	}

	/**
	 * Generate a vendor return document from a completed material receipt.
	 * <p>
	 * Mirrors the {@code M_InOut_GenerateVendorReturn} UI process: creates a new M_InOut
	 * with {@code MovementType=V-} (VendorReturns), copying all lines from the receipt.
	 * The returned document is in Draft status; use the complete step to finalise it.
	 * <p>
	 * On completion the {@code VendorReturnFromReceiptHUHandler} splits the receipt HUs
	 * and reassigns them to the vendor return lines.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>M_InOut_ID</b> — (required, identifier-ref) completed material receipt to return from<br>
	 * <b>VendorReturn_ID</b> — (required, identifier) alias to store the created vendor return<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * And generate vendor return from receipt
	 *   | M_InOut_ID      | VendorReturn_ID |
	 *   | receipt_1       | vendorReturn_1  |
	 * </pre>
	 */
	@And("generate vendor return from receipt")
	public void generateVendorReturnFromReceipt(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_InOut receipt = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inoutTable);

			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(DocBaseType.Shipment)
					.docSubType(DocSubType.NONE)
					.isSOTrx(false)
					.adClientId(receipt.getAD_Client_ID())
					.adOrgId(receipt.getAD_Org_ID())
					.build());

			final WarehouseId warehouseId = WarehouseId.ofRepoId(receipt.getM_Warehouse_ID());
			final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);

			final I_M_InOut vendorReturn = InterfaceWrapperHelper.copy()
					.setSkipCalculatedColumns(true)
					.setFrom(receipt)
					.copyToNew(I_M_InOut.class);
			vendorReturn.setC_DocType_ID(docTypeId.getRepoId());
			vendorReturn.setIsSOTrx(false);
			vendorReturn.setMovementType(MovementType.VendorReturns.getCode());
			vendorReturn.setReturn_Origin_InOut_ID(receipt.getM_InOut_ID());
			vendorReturn.setMovementDate(SystemTime.asTimestamp());
			vendorReturn.setDateAcct(SystemTime.asTimestamp());
			vendorReturn.setM_Warehouse_ID(warehouseId.getRepoId());
			InterfaceWrapperHelper.save(vendorReturn);

			for (final org.compiere.model.I_M_InOutLine receiptLine : inOutBL.getLines(receipt))
			{
				final I_M_InOutLine returnLine = InterfaceWrapperHelper.copy()
						.setSkipCalculatedColumns(true)
						.setFrom(receiptLine)
						.copyToNew(I_M_InOutLine.class);
				returnLine.setM_InOut_ID(vendorReturn.getM_InOut_ID());
				returnLine.setReturn_Origin_InOutLine_ID(receiptLine.getM_InOutLine_ID());
				returnLine.setM_Locator_ID(locatorId.getRepoId());
				returnLine.setC_OrderLine_ID(OrderLineId.toRepoId(null));
				InterfaceWrapperHelper.save(returnLine);
			}

			final StepDefDataIdentifier returnIdentifier = row.getAsIdentifier("VendorReturn_ID");
			inoutTable.putOrReplace(returnIdentifier, vendorReturn);
		});
	}

	/**
	 * Load the first HU assigned to an M_InOut and store it in M_HU_StepDefData.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment/receipt to look up HUs for<br>
	 *   <b>M_HU_ID</b> — (required, identifier) alias to store the first assigned HU<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_HU_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And load HUs assigned to M_InOut
	 *   | M_InOut_ID | M_HU_ID          |
	 *   | shipment_1 | shipment_1_hu    |
	 * </pre>
	 */
	@And("load HUs assigned to M_InOut")
	public void loadHUsAssignedToInOut(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final StepDefDataIdentifier inoutIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
			final I_M_InOut inout = inoutIdentifier.lookupNotNullIn(inoutTable);
			InterfaceWrapperHelper.refresh(inout);

			final List<I_M_HU> hus = huInOutBL.retrieveHandlingUnits(inout);
			assertThat(hus).as("HUs assigned to " + inoutIdentifier).isNotEmpty();

			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier(I_M_HU.COLUMNNAME_M_HU_ID);
			huTable.putOrReplace(huIdentifier, hus.get(0));
		});
	}

	/**
	 * Assert that no HUs are assigned to a given M_InOut (shipment, receipt, or return).
	 * <p>
	 * Useful after a vendor return is reactivated, to confirm that the HUs have been
	 * unassigned from the return document.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>M_InOut_ID</b> — (required, identifier-ref) document that should have no HU assignments<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * And assert no HUs assigned to M_InOut
	 *   | M_InOut_ID      |
	 *   | vendorReturn_1  |
	 * </pre>
	 */
	@And("assert no HUs assigned to M_InOut")
	public void assertNoHUsAssignedToInOut(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final StepDefDataIdentifier inoutIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);
			final I_M_InOut inout = inoutIdentifier.lookupNotNullIn(inoutTable);
			InterfaceWrapperHelper.refresh(inout);

			final boolean hasHUAssignments = huShipmentAssignmentBL.hasHUAssignments(inout);

			assertThat(hasHUAssignments).as("HUs assigned to " + inoutIdentifier + ", M_InOut_ID=" + inout.getM_InOut_ID()).isFalse();
		});
	}
}
