package de.metas.shipment.repo;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Set;

import org.adempiere.service.OrgId;
import org.compiere.model.I_M_Shipment_Declaration;
import org.compiere.model.I_M_Shipment_Declaration_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipment.ShipmentDeclaration;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationId;
import de.metas.shipment.ShipmentDeclarationLine;
import de.metas.util.Services;

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
	public void createAndCompleteShipmentDeclaration(final ShipmentDeclarationConfig config, final InOutId shipmentId, final Set<InOutAndLineId> inOutLinesForShipmentDeclaration)
	{
		final I_M_InOut shipment = load(shipmentId, I_M_InOut.class);

		final ShipmentDeclaration shipmentDeclaration = ShipmentDeclaration.builder()
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(shipment.getC_BPartner_ID(), shipment.getC_BPartner_Location_ID()))
				.docTypeId(config.getDocTypeId())
				.shipmentDate(TimeUtil.asLocalDate(shipment.getMovementDate()))
				.orgId(OrgId.ofRepoId(shipment.getAD_Org_ID()))
				.shipmentId(shipmentId)
				.docAction(IDocument.ACTION_Complete)
				.docStatus(IDocument.STATUS_Drafted)
				.build();

		final I_M_Shipment_Declaration shipmentDeclarationRecord = createAndSaveShipmentDeclaration(shipmentDeclaration);

		inOutLinesForShipmentDeclaration.stream().forEach(line -> generateShipmentDeclarationLine(
				ShipmentDeclarationId.ofRepoId(shipmentDeclarationRecord.getM_Shipment_Declaration_ID()),
				line));

		Services.get(IDocumentBL.class).processEx(shipmentDeclarationRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

	}

	private void generateShipmentDeclarationLine(final ShipmentDeclarationId shipmentDeclarationId, final InOutAndLineId inoutAndLineId)
	{
		final InOutLineId shipmentLineId = inoutAndLineId.getInOutLineId();

		final I_M_InOutLine shipmentLineRecord = load(shipmentLineId.getRepoId(), I_M_InOutLine.class);

		final int productRecordId = shipmentLineRecord.getM_Product_ID();
		final I_M_Product product = load(productRecordId, I_M_Product.class);

		final ShipmentDeclarationLine shipmentDeclarationLine = ShipmentDeclarationLine.builder()
				.orgId(OrgId.ofRepoId(shipmentLineRecord.getAD_Org_ID()))
				.packageSize(product.getPackageSize())
				.productId(ProductId.ofRepoId(productRecordId))
				.quantity(Quantity.of(shipmentLineRecord.getMovementQty(), shipmentLineRecord.getC_UOM()))
				.shipmentDeclarationId(shipmentDeclarationId)
				.shipmentLineId(shipmentLineId)
				.build();

		createAndSaveShipmentDeclarationLine(shipmentDeclarationLine);
	}

	private void createAndSaveShipmentDeclarationLine(final ShipmentDeclarationLine shipmentDeclarationLine)
	{
		final I_M_Shipment_Declaration_Line shipmentDeclarationLineRecord = newInstance(I_M_Shipment_Declaration_Line.class);

		final ShipmentDeclarationId shipmentDeclarationid = shipmentDeclarationLine.getShipmentDeclarationId();
		shipmentDeclarationLineRecord.setM_Shipment_Declaration_ID(shipmentDeclarationid.getRepoId());

		final InOutLineId shipmentLineId = shipmentDeclarationLine.getShipmentLineId();
		shipmentDeclarationLineRecord.setM_InOutLine_ID(shipmentLineId.getRepoId());

		final OrgId orgId = shipmentDeclarationLine.getOrgId();
		shipmentDeclarationLineRecord.setAD_Org_ID(orgId.getRepoId());

		final Quantity quantity = shipmentDeclarationLine.getQuantity();
		shipmentDeclarationLineRecord.setQty(quantity.getAsBigDecimal());
		shipmentDeclarationLineRecord.setC_UOM_ID(quantity.getUOMId());

		final ProductId productID = shipmentDeclarationLine.getProductId();
		shipmentDeclarationLineRecord.setM_Product_ID(productID.getRepoId());

		shipmentDeclarationLineRecord.setPackageSize(shipmentDeclarationLine.getPackageSize());

		saveRecord(shipmentDeclarationLineRecord);

	}

	private I_M_Shipment_Declaration createAndSaveShipmentDeclaration(final ShipmentDeclaration shipmentDeclaration)
	{
		final I_M_Shipment_Declaration shipmentDeclarationRecord = newInstance(I_M_Shipment_Declaration.class);

		final OrgId orgId = shipmentDeclaration.getOrgId();
		shipmentDeclarationRecord.setAD_Org_ID(orgId.getRepoId());

		final BPartnerLocationId bpartnerAndLocation = shipmentDeclaration.getBpartnerAndLocationId();
		final BPartnerId bpartnerId = bpartnerAndLocation.getBpartnerId();

		shipmentDeclarationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		shipmentDeclarationRecord.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());

		final DocTypeId docTypeId = shipmentDeclaration.getDocTypeId();
		shipmentDeclarationRecord.setC_DocType_ID(docTypeId.getRepoId());

		shipmentDeclarationRecord.setDeliveryDate(TimeUtil.asTimestamp(shipmentDeclaration.getShipmentDate()));

		final InOutId shipmentId = shipmentDeclaration.getShipmentId();
		shipmentDeclarationRecord.setM_InOut_ID(shipmentId.getRepoId());

		shipmentDeclarationRecord.setDocAction(shipmentDeclaration.getDocAction());
		shipmentDeclarationRecord.setDocStatus(shipmentDeclaration.getDocStatus());

		saveRecord(shipmentDeclarationRecord);

		return shipmentDeclarationRecord;

	}
}
