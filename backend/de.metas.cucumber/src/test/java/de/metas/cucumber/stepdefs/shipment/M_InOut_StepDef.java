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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
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
import org.assertj.core.api.SoftAssertions;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
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
	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final AD_Message_StepDefData messageTable;

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

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
		final LocalDate dateOrdered = row.getAsLocalDate("dateordered");
		final String poReference = row.getAsOptionalString(I_M_InOut.COLUMNNAME_POReference).orElse(null);
		final boolean processed = row.getAsBoolean("processed");
		final String docStatus = row.getAsString("docStatus");

		final @NonNull StepDefDataIdentifier bpartnerIdentifier = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
		final int expectedBPartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(bpartnerIdentifier::getAsInt);

		final @NonNull StepDefDataIdentifier bpartnerLocationIdentifier = row.getAsIdentifier(COLUMNNAME_C_BPartner_Location_ID);
		final int expectedBPartnerLocationId = bpartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(bpartnerLocationIdentifier::getAsInt);

		final I_M_InOut shipment = shipmentTable.get(identifier);

		softly.assertThat(shipment.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		softly.assertThat(shipment.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
		softly.assertThat(TimeUtil.asLocalDate(shipment.getDateOrdered())).isEqualTo(dateOrdered);

		if (Check.isNotBlank(poReference))
		{
			softly.assertThat(shipment.getPOReference()).isEqualTo(poReference);
		}

		softly.assertThat(shipment.isProcessed()).isEqualTo(processed);
		softly.assertThat(shipment.getDocStatus()).isEqualTo(docStatus);

		final String internalName = row.getAsOptionalString(I_M_InOut.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName).orElse(null);
		if (Check.isNotBlank(internalName))
		{
			final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
			softly.assertThat(shipment.getAD_InputDataSource_ID()).isEqualTo(dataSource.getAD_InputDataSource_ID());
		}

		final String docBaseType = row.getAsOptionalString(I_C_DocType.Table_Name + "." + COLUMNNAME_DocBaseType).orElse(null);
		if (Check.isNotBlank(docBaseType))
		{
			final String name = row.getAsString(I_C_DocType.Table_Name + "." + COLUMNNAME_Name);

			final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
					.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
					.addEqualsFilter(COLUMNNAME_Name, name)
					.create()
					.firstOnlyNotNull(I_C_DocType.class);

			softly.assertThat(shipment.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
		}

		softly.assertAll();
	}

	@And("'generate shipments' process is invoked")
	public void invokeGenerateShipmentsProcess(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> tableRow : dataTable)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);

			final String quantityType = DataTableUtil.extractStringForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_QuantityType);
			final boolean isCompleteShipments = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
			final boolean isShipToday = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);
			final BigDecimal qtyToDeliverOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_QtyToDeliver_Override);

			final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);

			final IQueryFilter<de.metas.handlingunits.model.I_M_ShipmentSchedule> queryFilter = queryBL.createCompositeQueryFilter(de.metas.handlingunits.model.I_M_ShipmentSchedule.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID());

			final ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.ShipmentScheduleWorkPackageParametersBuilder workPackageParametersBuilder = ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.builder()
					.adPInstanceId(pinstanceDAO.createSelectionId())
					.queryFilters(queryFilter)
					.quantityType(M_ShipmentSchedule_QuantityTypeToUse.ofCode(quantityType))
					.completeShipments(isCompleteShipments)
					.isShipmentDateToday(isShipToday);

			if (qtyToDeliverOverride != null)
			{
				final ImmutableMap<ShipmentScheduleId, BigDecimal> qtysToDeliverOverride = ImmutableMap.of(
						ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()), qtyToDeliverOverride);
				workPackageParametersBuilder.qtysToDeliverOverride(qtysToDeliverOverride);
			}

			final ShipmentScheduleEnqueuer.Result result = new ShipmentScheduleEnqueuer()
					.setContext(Env.getCtx(), Trx.TRXNAME_None)
					.createWorkpackages(workPackageParametersBuilder.build());

			assertThat(result.getEnqueuedPackagesCount()).isEqualTo(1);
		}
	}

	@And("^after not more than (.*)s, M_InOut is found:$")
	public void shipmentIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Optional<String> docStatus = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_InOut.COLUMNNAME_DocStatus));

		final String alreadyCreatedShipmentIdentifiers = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.IgnoreCreated" + "." + I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Set<InOutLineId> alreadyCreatedShipmentLines = Optional.ofNullable(alreadyCreatedShipmentIdentifiers)
				.map(StepDefUtil::extractIdentifiers)
				.map(this::getShipmentLinesForShipmentIdentifiers)
				.orElseGet(ImmutableSet::of);

		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);

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
				final I_M_InOut prevShipment = shipmentTable.getOptional(shipmentIdentifier).orElse(null);
				if (prevShipment == null)
				{
					shipmentTable.put(shipmentIdentifier, shipment);
				}
				else
				{
					assertThat(prevShipment.getM_InOut_ID()).isEqualTo(shipment.getM_InOut_ID());
					shipmentTable.putOrReplace(shipmentIdentifier, shipment);
				}

				return true;
			}

			return false;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentCreated);
	}

	@And("^the (shipment|material receipt|return inOut) identified by (.*) is (completed|reactivated|reversed|voided|closed)$")
	public void shipment_action(@NonNull final String model_UNUSED, @NonNull final String shipmentIdentifier, @NonNull final String action)
	{
		final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);
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

	@And("load M_InOut:")
	public void loadM_InOut(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_M_InOutLine.COLUMNNAME_QtyEntered);

			final IQueryBuilder<I_M_InOutLine> shipmentLineBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_QtyEntered, qtyEntered);

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				shipmentLineBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
			}

			final I_M_InOutLine shipmentLine = shipmentLineBuilder.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(shipmentLineIdentifier, shipmentLine);

			final I_M_InOut shipment = InterfaceWrapperHelper.load(shipmentLine.getM_InOut_ID(), I_M_InOut.class);
			assertThat(shipment).isNotNull();

			final String docStatus = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_DocStatus);
			assertThat(shipment.getDocStatus()).isEqualTo(docStatus);

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(shipment.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentTable.putOrReplace(shipmentIdentifier, shipment);
		}
	}

	@And("perform shipment document action")
	public void reverseShipment(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String docAction = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_DocAction);

			final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);

			documentBL.processEx(shipment, docAction);
		}
	}

	@Then("locate M_InOut by shipment schedule Id")
	public void locate_shipment_by_scheduleId(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			locateShipmentByScheduleId(row);
		}
	}

	@And("validate M_In_Out status")
	public void validate_M_In_Out_status(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);
			InterfaceWrapperHelper.refresh(shipment);

			final String docStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DocStatus);
			assertThat(shipment.getDocStatus()).isEqualTo(docStatus);
		}
	}

	@And("^reset M_InOut packing lines for shipment (.*)$")
	public void reset_M_InOut_PackingLines(@NonNull final String shipmentIdentifier)
	{
		final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);
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

	@And("metasfresh contains M_InOut:")
	public void create_M_InOut(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			assertThat(bPartner).isNotNull();
			inOut.setC_BPartner_ID(bPartner.getC_BPartner_ID());

			final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bpartnerLocationIdentifier);
			assertThat(bpartnerIdentifier).isNotNull();
			inOut.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());

			final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsSOTrx);
			inOut.setIsSOTrx(isSOTrx);

			final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_DocBaseType);
			if (EmptyUtil.isNotBlank(docBaseType))
			{
				final String docSubType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_DocSubType);

				final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
						.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
						.addEqualsFilter(COLUMNNAME_DocSubType, docSubType)
						.create()
						.firstOnlyNotNull(I_C_DocType.class);

				assertThat(docType).isNotNull();

				inOut.setC_DocType_ID(docType.getC_DocType_ID());
			}

			final String deliveryRule = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_DeliveryRule);
			inOut.setDeliveryRule(deliveryRule);

			final String deliveryViaRule = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_DeliveryViaRule);
			inOut.setDeliveryViaRule(deliveryViaRule);

			final String freightCostRule = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_FreightCostRule);
			inOut.setFreightCostRule(freightCostRule);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(warehouseIdentifier))
			{
				final int warehouseId = warehouseTable.get(warehouseIdentifier).getM_Warehouse_ID();
				inOut.setM_Warehouse_ID(warehouseId);
			}

			final Timestamp movementDate = DataTableUtil.extractDateTimestampForColumnName(row, I_M_InOut.COLUMNNAME_MovementDate);
			inOut.setMovementDate(movementDate);

			final String movementType = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_MovementType);
			inOut.setMovementType(movementType);

			final String priorityRule = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_PriorityRule);
			inOut.setPriorityRule(priorityRule);

			InterfaceWrapperHelper.saveRecord(inOut);

			final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentTable.putOrReplace(inOutIdentifier, inOut);
		}
	}

	private void locateShipmentByScheduleId(@NonNull final Map<String, String> row)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleTable.get(shipmentScheduleIdentifier), I_M_ShipmentSchedule.class);

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);
		final InOutLineId lineId = InOutLineId.ofRepoId(shipmentScheduleQtyPickedRecords.get(0).getM_InOutLine_ID());

		final I_M_InOut shipmentRecord = inOutDAO.retrieveInOutByLineIds(ImmutableSet.of(lineId)).get(lineId);

		final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + ".Identifier");
		shipmentTable.put(shipmentIdentifier, shipmentRecord);
	}

	@And("^the (shipment|material receipt|return inOut) identified by (.*) is (completed|reactivated|reversed|voided|closed) expecting error$")
	public void shipment_action_expecting_error(@NonNull final String model_UNUSED, @NonNull final String shipmentIdentifier, @NonNull final String action, @NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		boolean errorThrown = false;

		try
		{
			shipment_action(model_UNUSED, shipmentIdentifier, action);
		}
		catch (final Exception e)
		{
			errorThrown = true;

			final String errorMessageIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Message_ID + "." + TABLECOLUMN_IDENTIFIER);

			if (errorMessageIdentifier != null)
			{
				final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);
				assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
			}
		}

		assertThat(errorThrown).isTrue();
	}

	@And("^after not more than (.*)s, locate reversal M_InOut$")
	public void find_reversal_M_InOut(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_M_InOut reversalInOut = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_reversal_InOut(row));

			softly.assertThat(reversalInOut).isNotNull();

			final String reversalInOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentTable.putOrReplace(reversalInOutIdentifier, reversalInOut);
		}
		softly.assertAll();
	}

	@NonNull
	private Set<InOutLineId> getShipmentLinesForShipmentIdentifiers(@NonNull final List<String> shipmentIdentifiers)
	{
		final Set<Integer> shipmentIds = shipmentIdentifiers.stream()
				.map(shipmentTable::get)
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
	private ItemProvider.ProviderResult<I_M_InOut> load_reversal_InOut(@NonNull final Map<String, String> row)
	{
		final String inOutToReverseIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_Reversal_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_InOut inOutToReverse = shipmentTable.get(inOutToReverseIdentifier);

		final I_M_InOut reversalInOutRecord = queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_Reversal_ID, inOutToReverse.getM_InOut_ID())
				.create()
				.firstOnlyOrNull(I_M_InOut.class);

		if (reversalInOutRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No reversal I_M_InOut found for row=" + row);
		}

		return ItemProvider.ProviderResult.resultWasFound(reversalInOutRecord);
	}
}
