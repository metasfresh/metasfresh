package de.metas.shipment.document;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipment_Declaration;

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

public class ShipmentDeclarationDocumentHandler implements DocumentHandler
{

	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractShipmentDeclaration(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration record = extractShipmentDeclaration(docFields);
		return InstantAndOrgId.ofTimestamp(record.getDeliveryDate(), OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractShipmentDeclaration(docFields).getCreatedBy();
	}

	@Override
	public DocStatus completeIt(DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration shipmentDeclaration = extractShipmentDeclaration(docFields);
		shipmentDeclaration.setProcessed(true);
		shipmentDeclaration.setDocAction(IDocument.ACTION_ReActivate);

		return DocStatus.Completed;
	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_M_Shipment_Declaration shipmentDeclaration = extractShipmentDeclaration(docFields);

		shipmentDeclaration.setProcessed(false);
		shipmentDeclaration.setDocAction(IDocument.ACTION_Complete);

	}

	private static I_M_Shipment_Declaration extractShipmentDeclaration(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_Shipment_Declaration.class);
	}
}
