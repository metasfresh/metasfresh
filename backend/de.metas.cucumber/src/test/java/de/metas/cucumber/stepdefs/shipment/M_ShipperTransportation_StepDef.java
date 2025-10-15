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

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Package_StepDefData;
import de.metas.cucumber.stepdefs.shipment.pickingterminal.M_ShippingPackage_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AllArgsConstructor
public class M_ShipperTransportation_StepDef
{
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Shipper_StepDefData shipperTable;
	private final M_ShippingPackage_StepDefData shippingPackageTable;
	private final M_Package_StepDefData packageTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;

	private final M_InOut_StepDefData shipmentTable;
	private final C_Order_StepDefData orderTable;

	public final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

	@And("validate M_ShipperTransportation:")
	public void validateM_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
			assertThat(deliveryInstruction).isNotNull();

			final SoftAssertions softly = new SoftAssertions();

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipper).isNotNull();
			softly.assertThat(deliveryInstruction.getM_Shipper_ID()).as(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());

			final String shipperBPIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner shipperBP = bPartnerTable.get(shipperBPIdentifier);
			softly.assertThat(shipperBP).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_BPartner_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID).isEqualTo(shipperBP.getC_BPartner_ID());

			final String shipperLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location shipperLocation = bPartnerLocationTable.get(shipperLocationIdentifier);
			softly.assertThat(shipperLocation).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_Location_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID).isEqualTo(shipperLocation.getC_BPartner_Location_ID());

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_DocStatus);
			if (Check.isNotBlank(docStatus))
			{
				softly.assertThat(deliveryInstruction.getDocStatus()).as(I_M_ShipperTransportation.COLUMNNAME_DocStatus).isEqualTo(docStatus);
			}

			softly.assertAll();
		}
	}

	@And("load Transportation Order from Shipment")
	public void load_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut shipment = shipmentTable.get(shipmentIdentifier);

			final I_M_ShipperTransportation shipperTransportation = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, shipment.getM_InOut_ID())
					.andCollect(I_M_ShippingPackage.COLUMN_M_ShipperTransportation_ID)
					.orderBy(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
					.first();
			assertThat(shipperTransportation).isNotNull();
			final String shipperTransportationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(shipperTransportationIdentifier, shipperTransportation);
		}
	}

	@And("metasfresh contains M_ShipperTransportation")
	public void add_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.forEach(this::createM_ShipperTransportation);
	}

	public void createM_ShipperTransportation(@NonNull final DataTableRow row)
	{
		final I_M_ShipperTransportation shipperTransportationRecord = newInstance(I_M_ShipperTransportation.class);

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID)
				.map(shipperTable::getId)
				.ifPresent(id -> shipperTransportationRecord.setM_Shipper_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID)
				.map(bPartnerTable::getId)
				.ifPresent(id -> shipperTransportationRecord.setShipper_BPartner_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID)
				.map(bPartnerLocationTable::getId)
				.ifPresent(id -> shipperTransportationRecord.setShipper_Location_ID(id.getRepoId()));

		saveRecord(shipperTransportationRecord);

		deliveryInstructionTable.putOrReplace(row.getAsIdentifier(), shipperTransportationRecord);
	}

	@And("metasfresh contains M_ShippingPackage")
	public void add_M_ShippingPackage(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID)
				.forEach(this::createM_ShippingPackage);
	}

	public void createM_ShippingPackage(@NonNull final DataTableRow row)
	{
		final I_M_ShippingPackage shippingPackageRecord = newInstance(I_M_ShippingPackage.class);

		row.getAsOptionalIdentifier(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.map(deliveryInstructionTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_ShipperTransportation_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_Order_ID)
				.map(orderTable::getId)
				.ifPresent(id -> shippingPackageRecord.setC_Order_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID)
				.map(shipmentTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_InOut_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_Package_ID)
				.map(packageTable::getId)
				.ifPresent(id -> shippingPackageRecord.setM_Package_ID(id.getRepoId()));

		row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID)
				.map(bPartnerLocationTable::getId)
				.ifPresent(id -> shippingPackageRecord.setC_BPartner_Location_ID(id.getRepoId()));

		saveRecord(shippingPackageRecord);

		shippingPackageTable.putOrReplace(row.getAsIdentifier(), shippingPackageRecord);
	}

	@And("metasfresh contains M_Package")
	public void add_M_Package(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Package.COLUMNNAME_M_Package_ID)
				.forEach(this::creat_M_Package);
	}

	private void creat_M_Package(@NonNull final DataTableRow row)
	{
		final I_M_Package packageRecord = newInstance(I_M_Package.class);

		row.getAsOptionalIdentifier(I_M_Package.COLUMNNAME_M_Shipper_ID)
				.map(shipperTable::getId)
				.ifPresent(id -> packageRecord.setM_Shipper_ID(id.getRepoId()));
		saveRecord(packageRecord);

		packageTable.putOrReplace(row.getAsIdentifier(), packageRecord);
	}

	@And("update transport order")
	public void update_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
				.forEach(this::updateTransportOrder);
	}

	private void updateTransportOrder(@NonNull final DataTableRow tableRow)
	{
		final ShipperTransportationId shipperTransportationId = tableRow.getAsIdentifier().lookupNotNullIdIn(deliveryInstructionTable);
		final I_M_ShipperTransportation record = shipperTransportationDAO.getById(shipperTransportationId);

		tableRow.getAsOptionalInstant(I_M_ShipperTransportation.COLUMNNAME_ETA)
				.ifPresent(expected -> record.setETA(Timestamp.from(expected)));

		tableRow.getAsOptionalInstant(I_M_ShipperTransportation.COLUMNNAME_BLDate)
				.ifPresent(expected -> record.setBLDate(Timestamp.from(expected)));
		saveRecord(record);

		deliveryInstructionTable.putOrReplace(tableRow.getAsIdentifier(), record);
	}
}
