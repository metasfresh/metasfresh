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

package de.metas.cucumber.stepdefs.shipment;

import com.google.common.collect.ImmutableMap;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.shipment.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.process.IADPInstanceDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

public class M_InOut_StepDef
{
	private final M_InOut_StepDefData shipmentTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final C_Order_StepDefData orderTable;
	private final C_DocType_StepDefData docTypeTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);

	public M_InOut_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.shipmentTable = shipmentTable;
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.orderTable = orderTable;
		this.docTypeTable = docTypeTable;
	}

	@And("validate created shipments")
	public void validate_created_shipments(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, "Shipment.Identifier");
			final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnName(row, "dateordered");
			final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOut.COLUMNNAME_POReference);
			final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");
			final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedBPartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
					.map(I_C_BPartner::getC_BPartner_ID)
					.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

			final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedBPartnerLocationId = bpartnerLocationTable.getOptional(bpartnerLocationIdentifier)
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));

			final I_M_InOut shipment = shipmentTable.get(identifier);

			assertThat(shipment.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
			assertThat(shipment.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocationId);
			assertThat(shipment.getDateOrdered()).isEqualTo(dateOrdered);

			if (Check.isNotBlank(poReference))
			{
				assertThat(shipment.getPOReference()).isEqualTo(poReference);
			}

			assertThat(shipment.isProcessed()).isEqualTo(processed);
			assertThat(shipment.getDocStatus()).isEqualTo(docStatus);
		}
	}

	@And("'generate shipments' process is invoked")
	public void invokeGenerateShipmentsProcess(@NonNull final DataTable table)
	{
		final Map<String, String> tableRow = table.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String quantityType = DataTableUtil.extractStringForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_QuantityType);
		final boolean isCompleteShipments = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
		final boolean isShipToday = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);
		final BigDecimal qtyToDeliverOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_QtyToDeliver_Override);

		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

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

	@And("^after not more than (.*)s, M_InOut is found:$")
	public void shipmentIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Optional<String> docStatus = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_InOut.COLUMNNAME_DocStatus));

		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final Supplier<Boolean> isShipmentCreated = () -> {

			final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnly(I_M_ShipmentSchedule_QtyPicked.class);

			if (qtyPickedRecord == null)
			{
				return false;
			}

			if (qtyPickedRecord.getM_InOutLine_ID() <= 0)
			{
				return false;
			}

			final I_M_InOutLine shipmentLine = queryBL
					.createQueryBuilder(I_M_InOutLine.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, qtyPickedRecord.getM_InOutLine_ID())
					.create()
					.firstOnly(I_M_InOutLine.class);

			if (shipmentLine == null)
			{
				return false;
			}

			final IQueryBuilder<I_M_InOut> shipmentQueryBuilder = queryBL
					.createQueryBuilder(I_M_InOut.class)
					.addOnlyActiveRecordsFilter();

			docStatus.map(status -> shipmentQueryBuilder.addEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, status));

			final I_M_InOut shipment = shipmentQueryBuilder
					.addEqualsFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, shipmentLine.getM_InOut_ID())
					.create()
					.firstOnly(I_M_InOut.class);

			if (shipment != null)
			{
				shipmentTable.put(shipmentIdentifier, shipment);
				return true;
			}

			return false;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentCreated);
	}

	@And("^after not more than (.*)s, Customer Return is found:$")
	public void customerReturnIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			findCustomerReturn(timeoutSec, row);
		}
	}

	private void findCustomerReturn(
			final int timeoutSec,
			@NonNull final Map<String, String> row) throws InterruptedException
	{
		final I_M_InOut customerReturnRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> isCustomerReturnFound(row));

		final String customerReturnIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
		shipmentTable.put(customerReturnIdentifier, customerReturnRecord);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_InOut> isCustomerReturnFound(@NonNull final Map<String, String> row)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String docTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		assertThat(orderRecord).isNotNull();
		final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
		assertThat(docTypeRecord).isNotNull();

		final Optional<I_M_InOut> customerReturnRecord = queryBL
				.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeRecord.getC_DocType_ID())
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.firstOnlyOptional(I_M_InOut.class);

		return customerReturnRecord.map(ItemProvider.ProviderResult::resultWasFound)
				.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound(getCurrentCustomerReturnContext(row)));
	}

	@NonNull
	private String getCurrentCustomerReturnContext(@NonNull final Map<String, String> row)
	{
		final StringBuilder message = new StringBuilder();

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String docTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		assertThat(orderRecord).isNotNull();
		final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
		assertThat(docTypeRecord).isNotNull();

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
}
