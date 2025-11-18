package de.metas.shipping.callout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Inventory;

import javax.annotation.Nullable;

@Callout(I_M_ShipperTransportation.class)
public class M_ShipperTransportation
{

	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IShipperTransportationBL shipperTransportationBL = Services.get(IShipperTransportationBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	/**
	 * Sets the {@link I_M_ShipperTransportation}'s M_Shipper_ID accordingly to {@link I_M_ShipperTransportation}'s Shipper_BPartner_ID
	 *
	 * @param shipperTransportation
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID })
	public void onShipperBPartner(final I_M_ShipperTransportation shipperTransportation, final ICalloutField field)
	{
		// fix to avoid NPE when new entry
		if (shipperTransportation == null)
		{
			// new entry
			return;
		}

		final BPartnerId shipperPartnerId = BPartnerId.ofRepoIdOrNull(shipperTransportation.getShipper_BPartner_ID());
		if (shipperPartnerId == null)
		{
			return;
		}

		shipperDAO.getShipperIdByShipperPartnerId(shipperPartnerId)
				.ifPresent(shipperId -> shipperTransportationBL.setShipper(shipperTransportation, shipperId));
	}

	@CalloutMethod(columnNames = { I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID })
	public void onShipper(final I_M_ShipperTransportation shipperTransportation, final ICalloutField field)
	{
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(shipperTransportation.getM_Shipper_ID());
		if (shipperId != null)
		{
			shipperTransportationBL.setShipper(shipperTransportation, shipperId);
		}
	}

	@CalloutMethod(columnNames = I_M_Inventory.COLUMNNAME_C_DocType_ID)
	public void updateFromDocType(final I_M_ShipperTransportation shipperTransportationRecord, final ICalloutField field)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(getDocTypeOrNull(shipperTransportationRecord))
				.setOldDocumentNo(shipperTransportationRecord.getDocumentNo())
				.setDocumentModel(shipperTransportationRecord)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			shipperTransportationRecord.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	@Nullable
	private I_C_DocType getDocTypeOrNull(final I_M_ShipperTransportation shipperTransportationRecord)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(shipperTransportationRecord.getC_DocType_ID());
		return docTypeId != null
				? docTypeDAO.getById(docTypeId)
				: null;
	}
}
