package de.metas.rest_api.utils;

import static de.metas.util.lang.CoalesceUtil.firstNotEmptyTrimmed;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_Org;
import org.springframework.stereotype.Service;

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.util.Services;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DocTypeService
{
	private final IOrgDAO orgsDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public DocTypeId getDocTypeId(
			@Nullable final JsonDocTypeInfo invoiceDocType,
			@NonNull final OrgId orgId)
	{
		if (invoiceDocType == null)
		{
			return null;
		}

		final String docSubType = firstNotEmptyTrimmed(
				invoiceDocType.getDocSubType(),
				DocTypeQuery.DOCSUBTYPE_NONE);

		final I_AD_Org orgRecord = orgsDAO.getById(orgId);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(invoiceDocType.getDocBaseType())
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}
}
