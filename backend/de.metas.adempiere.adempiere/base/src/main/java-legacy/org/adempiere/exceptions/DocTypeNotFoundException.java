/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.exceptions;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;

/**
 * Throwed when desired document type was not found
 *
 * @author Teo Sarca, www.arhipac.ro
 */
public class DocTypeNotFoundException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1269337075319916877L;

	public DocTypeNotFoundException(final DocBaseType docBaseType, final String additionalInfo)
	{
		super(buildMsg(docBaseType, additionalInfo));
	}

	public DocTypeNotFoundException(@NonNull final DocTypeQuery query)
	{
		super(buildMsg(query));
	}

	private static ITranslatableString buildMsg(final DocBaseType docBaseType, final String additionalInfo)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		builder.appendADMessage(AdMessageKey.of("NotFound")).append(" ").appendADElement("C_DocType_ID");

		if (docBaseType != null)
		{
			final IADReferenceDAO adReferenceService = Services.get(IADReferenceDAO.class);

			builder.append(" - ")
					.appendADElement("DocBaseType")
					.append(": ")
					.append(adReferenceService.retrieveListNameTranslatableString(DocBaseType.AD_REFERENCE_ID, docBaseType.getCode()));
		}

		if (!Check.isBlank(additionalInfo))
		{
			builder.append(" (").append(additionalInfo).append(")");
		}

		return builder.build();
	}

	private static ITranslatableString buildMsg(final DocTypeQuery query)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		builder.appendADMessage(AdMessageKey.of("NotFound")).append(" ").appendADElement("C_DocType_ID");

		final IADReferenceDAO adReferenceService = Services.get(IADReferenceDAO.class);
		builder.append(" - ").appendADElement("DocBaseType").append(": ").append(adReferenceService.retrieveListNameTranslatableString(DocBaseType.AD_REFERENCE_ID, query.getDocBaseType().getCode()));
		builder.append(", ").appendADElement("DocSubType").append(": ").append(query.getDocSubType());
		builder.append(", ").appendADElement("AD_Client_ID").append(": ").append(query.getAdClientId());
		builder.append(", ").appendADElement("AD_Org_ID").append(": ").append(query.getAdOrgId());

		if (query.getIsSOTrx() != null)
		{
			builder.append(", ").appendADElement("IsSOTrx").append(": ").append(query.getIsSOTrx());
		}
		if (!Check.isBlank(query.getName()))
		{
			builder.append(", ").appendADElement("Name").append(": ").append(query.getName());
		}

		return builder.build();
	}
}
