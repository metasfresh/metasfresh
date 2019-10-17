package de.metas.shipment.repo;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.HashMap;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Shipment_Declaration;
import org.compiere.model.I_M_Shipment_Declaration_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipment.ShipmentDeclaration;
import de.metas.shipment.ShipmentDeclarationId;
import de.metas.shipment.ShipmentDeclarationLine;
import de.metas.shipment.ShipmentDeclarationLineId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Repository
public class ShipmentDeclarationRepository
{

	public I_M_Shipment_Declaration save(final ShipmentDeclaration shipmentDeclaration)
	{
		final I_M_Shipment_Declaration shipmentDeclarationRecord;
		final HashMap<ShipmentDeclarationLineId, I_M_Shipment_Declaration_Line> existingLineRecords;
		if (shipmentDeclaration.getId() == null)
		{
			shipmentDeclarationRecord = newInstance(I_M_Shipment_Declaration.class);
			existingLineRecords = new HashMap<>();
		}
		else
		{
			shipmentDeclarationRecord = load(shipmentDeclaration.getId(), I_M_Shipment_Declaration.class);
			existingLineRecords = retrieveLineRecords(shipmentDeclaration.getId())
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(lineRecord -> ShipmentDeclarationLineId.ofRepoId(lineRecord.getM_Shipment_Declaration_ID(), lineRecord.getM_Shipment_Declaration_Line_ID())));
		}

		shipmentDeclarationRecord.setDocumentNo(shipmentDeclaration.getDocumentNo());

		final OrgId orgId = shipmentDeclaration.getOrgId();
		shipmentDeclarationRecord.setAD_Org_ID(orgId.getRepoId());

		final BPartnerLocationId bpartnerAndLocation = shipmentDeclaration.getBpartnerAndLocationId();
		final BPartnerId bpartnerId = bpartnerAndLocation.getBpartnerId();

		shipmentDeclarationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		shipmentDeclarationRecord.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());
		shipmentDeclarationRecord.setAD_User_ID(UserId.toRepoId(shipmentDeclaration.getUserId()));

		final DocTypeId docTypeId = shipmentDeclaration.getDocTypeId();
		shipmentDeclarationRecord.setC_DocType_ID(docTypeId.getRepoId());

		shipmentDeclarationRecord.setDeliveryDate(TimeUtil.asTimestamp(shipmentDeclaration.getShipmentDate()));

		final InOutId shipmentId = shipmentDeclaration.getShipmentId();
		shipmentDeclarationRecord.setM_InOut_ID(shipmentId.getRepoId());

		shipmentDeclarationRecord.setDocAction(shipmentDeclaration.getDocAction());
		shipmentDeclarationRecord.setDocStatus(shipmentDeclaration.getDocStatus());

		shipmentDeclarationRecord.setM_Shipment_Declaration_Base_ID(ShipmentDeclarationId.toRepoId(shipmentDeclaration.getBaseShipmentDeclarationId()));

		shipmentDeclarationRecord.setM_Shipment_Declaration_Correction_ID(ShipmentDeclarationId.toRepoId(shipmentDeclaration.getCorrectionShipmentDeclarationId()));

		saveRecord(shipmentDeclarationRecord);

		final ShipmentDeclarationId shipmentDeclarationId = ShipmentDeclarationId.ofRepoId(shipmentDeclarationRecord.getM_Shipment_Declaration_ID());
		shipmentDeclaration.setId(shipmentDeclarationId);

		for (final ShipmentDeclarationLine line : shipmentDeclaration.getLines())
		{
			final I_M_Shipment_Declaration_Line existingLineRecord = existingLineRecords.remove(line.getId());
			saveLine(line, shipmentDeclarationId, existingLineRecord);
		}

		//
		// Delete remaining lines
		deleteAll(existingLineRecords.values());

		return shipmentDeclarationRecord;
	}

	private List<I_M_Shipment_Declaration_Line> retrieveLineRecords(@NonNull final ShipmentDeclarationId shipmentDeclarationId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Shipment_Declaration_Line.class)
				.addEqualsFilter(I_M_Shipment_Declaration_Line.COLUMN_M_Shipment_Declaration_ID, shipmentDeclarationId)
				.create()
				.list();
	}

	private void saveLine(
			@NonNull final ShipmentDeclarationLine line,
			@NonNull final ShipmentDeclarationId shipmentDeclarationId,
			final I_M_Shipment_Declaration_Line existingRecord)
	{
		final I_M_Shipment_Declaration_Line record;
		if (existingRecord == null)
		{
			record = newInstance(I_M_Shipment_Declaration_Line.class);
		}
		else
		{
			record = existingRecord;
		}

		record.setM_Shipment_Declaration_ID(shipmentDeclarationId.getRepoId());

		record.setLineNo(line.getLineNo());

		final InOutLineId shipmentLineId = line.getShipmentLineId();
		record.setM_InOutLine_ID(shipmentLineId.getRepoId());

		final OrgId orgId = line.getOrgId();
		record.setAD_Org_ID(orgId.getRepoId());

		final Quantity quantity = line.getQuantity();
		record.setQty(quantity.toBigDecimal());
		record.setC_UOM_ID(quantity.getUOMId());

		final ProductId productId = line.getProductId();
		record.setM_Product_ID(productId.getRepoId());

		record.setPackageSize(line.getPackageSize());

		saveRecord(record);

		line.setId(ShipmentDeclarationLineId.ofRepoId(record.getM_Shipment_Declaration_ID(), record.getM_Shipment_Declaration_Line_ID()));
	}

	public boolean existCompletedShipmentDeclarationsForShipmentId(final InOutId shipmentId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Shipment_Declaration.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Shipment_Declaration.COLUMN_M_InOut_ID, shipmentId)
				.create()
				.match();
	}

}
