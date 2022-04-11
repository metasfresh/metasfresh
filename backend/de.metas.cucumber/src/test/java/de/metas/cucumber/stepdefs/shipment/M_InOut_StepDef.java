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
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOutLine;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_Location_ID;

public class M_InOut_StepDef
{
	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO pinstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public M_InOut_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
	}

	@And("validate the created shipments")
	public void validate_created_shipments(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");
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

	@And("generate shipments process is invoked")
	public void invokeGenerateShipmentsProcess(@NonNull final DataTable table)
	{
		final Map<String, String> tableRow = table.asMaps().get(0);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String quantityType = DataTableUtil.extractStringForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_QuantityType);
		final boolean isCompleteShipments = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
		final boolean isShipToday = DataTableUtil.extractBooleanForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);
		final BigDecimal qtyToDeliverOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_QtyToDeliver_Override);

		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final IQueryFilter<I_M_ShipmentSchedule> queryFilter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
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

		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

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

			final I_M_InOut shipment = queryBL
					.createQueryBuilder(I_M_InOut.class)
					.addOnlyActiveRecordsFilter()
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

	@And("^the (shipment|material receipt) identified by (.*) is (completed|reactivated|reversed)$")
	public void shipment_action(@NonNull final String model_UNUSED, @NonNull final String shipmentIdentifier, @NonNull final String action)
	{
		final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);

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

			final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOut.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderIdentifier))
			{
				final I_C_Order order = orderTable.get(orderIdentifier);
				assertThat(shipment.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			}

			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentTable.putOrReplace(shipmentIdentifier, shipment);
		}
	}
}
