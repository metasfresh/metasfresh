/*
 * #%L
 * de.metas.business
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

package de.metas.order.impl;

import de.metas.common.ordercandidates.v2.request.JsonOrderDocType;
<<<<<<< HEAD
=======
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
<<<<<<< HEAD
import de.metas.util.Check;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
<<<<<<< HEAD
import org.compiere.model.X_C_DocType;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

<<<<<<< HEAD
import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
@Service
public class DocTypeService
{
	private final IOrgDAO orgsDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

<<<<<<< HEAD
	@Nullable
	public DocTypeId getInvoiceDocTypeId(
			@Nullable final String docBaseType,
			@Nullable final String docSubType,
			@NonNull final OrgId orgId)
	{
		if (Check.isBlank(docBaseType))
		{
			return null;
		}

=======
	@NonNull
	public DocTypeId getDocTypeId(
			@NonNull final DocBaseType docBaseType,
			@NonNull final OrgId orgId)
	{
		return getDocTypeId(docBaseType, DocSubType.NONE, orgId);
	}

	@NonNull
	public DocTypeId getDocTypeId(
			@NonNull final DocBaseType docBaseType,
			@NonNull final DocSubType docSubType,
			@NonNull final OrgId orgId)
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final I_AD_Org orgRecord = orgsDAO.getById(orgId);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(docBaseType)
<<<<<<< HEAD
				.docSubType(firstNotEmptyTrimmed(docSubType, DocTypeQuery.DOCSUBTYPE_NONE))
=======
				.docSubType(docSubType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

	@Nullable
	public DocTypeId getOrderDocTypeId(@Nullable final JsonOrderDocType orderDocType, final OrgId orgId)
	{
		if (orderDocType == null)
		{
			return null;
		}

<<<<<<< HEAD
		final String docBaseType = X_C_DocType.DOCBASETYPE_SalesOrder;
		final String docSubType;

		if (JsonOrderDocType.PrepayOrder.equals(orderDocType))
		{
			docSubType = X_C_DocType.DOCSUBTYPE_PrepayOrder;
		}
		else
		{
			docSubType = X_C_DocType.DOCSUBTYPE_StandardOrder;
=======
		final DocBaseType docBaseType = DocBaseType.SalesOrder;
		final DocSubType docSubType;

		if (JsonOrderDocType.PrepayOrder.equals(orderDocType))
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

	@NonNull
	public Optional<JsonOrderDocType> getOrderDocType(@Nullable final DocTypeId docTypeId)
	{
		if (docTypeId == null)
		{
			return Optional.empty();
		}
		
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);
<<<<<<< HEAD
		if (!X_C_DocType.DOCBASETYPE_SalesOrder.equals(docType.getDocBaseType()))
=======
		final DocBaseType docBaseType = DocBaseType.ofCode(docType.getDocBaseType());
		if (!docBaseType.isSalesOrder())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			throw new AdempiereException("Invalid base doc type!");
		}

<<<<<<< HEAD
		return Optional.of(JsonOrderDocType.ofCode(docType.getDocSubType()));
=======
		return Optional.ofNullable(JsonOrderDocType.ofCodeOrNull(docType.getDocSubType()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
