package de.metas.rest_api.utils;

import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest.OrderDocType;
<<<<<<< HEAD
import de.metas.common.rest_api.v1.JsonDocTypeInfo;
=======
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_AD_Org;
<<<<<<< HEAD
import org.compiere.model.X_C_DocType;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

<<<<<<< HEAD
import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Service
public class DocTypeService
{
	private final IOrgDAO orgsDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public DocTypeId getInvoiceDocTypeId(
<<<<<<< HEAD
			@Nullable final JsonDocTypeInfo invoiceDocType,
			@NonNull final OrgId orgId)
	{
		if (invoiceDocType == null)
=======
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@NonNull final OrgId orgId)
	{
		if (docBaseAndSubType == null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return null;
		}

<<<<<<< HEAD
		final String docSubType = firstNotEmptyTrimmed(
				invoiceDocType.getDocSubType(),
				DocTypeQuery.DOCSUBTYPE_NONE);

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(invoiceDocType.getDocBaseType())
=======
		final DocBaseType docBaseType = docBaseAndSubType.getDocBaseType();
		final DocSubType docSubType = docBaseAndSubType.getDocSubType();

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);
		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(docBaseType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

<<<<<<< HEAD
	public DocTypeId getOrderDocTypeId(final OrderDocType orderDocType, OrgId orgId)
=======
	@Nullable
	public DocTypeId getOrderDocTypeIdOrNull(@Nullable final OrderDocType orderDocType, final OrgId orgId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (orderDocType == null)
		{
			return null;
		}

<<<<<<< HEAD
		final String docBaseType = X_C_DocType.DOCBASETYPE_SalesOrder;
		final String docSubType;

		if (OrderDocType.PrepayOrder.equals(orderDocType))
		{
			docSubType = X_C_DocType.DOCSUBTYPE_PrepayOrder;
		}
		else
		{
			docSubType = X_C_DocType.DOCSUBTYPE_StandardOrder;
=======
		final DocBaseType docBaseType = DocBaseType.SalesOrder;
		final DocSubType docSubType;

		if (OrderDocType.PrepayOrder.equals(orderDocType))
		{
			docSubType = DocSubType.PrepayOrder;
		}
		else
		{
			docSubType = DocSubType.StandardOrder;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}
}
