/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.exceptions;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

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
	/** Doc Base Type */
	private String docBaseType = null;

	/**
	 * @param docBaseType Document Base Type (see MDocType.DOCBASETYPE_*)
	 * @param additionalInfo optional if there is some additional info
	 */
	public DocTypeNotFoundException(final String docBaseType, final String additionalInfo)
	{
		super(buildMsg(docBaseType, additionalInfo));
		this.docBaseType = docBaseType;
	}

	private static final String buildMsg(final String docBaseType, final String additionalInfo)
	{
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

		final String docBaseTypeName = adReferenceDAO.retrieveListNameTrl(Env.getCtx(), X_C_DocType.DOCBASETYPE_AD_Reference_ID, docBaseType);

		final StringBuilder sb = new StringBuilder("@NotFound@ @C_DocType_ID@");
		sb.append(" - @DocBaseType@ : " + docBaseTypeName);
		if (!Check.isEmpty(additionalInfo, true))
		{
			sb.append(" (").append(additionalInfo).append(")");
		}
		return sb.toString();
	}

	public String getDocBaseType()
	{
		return docBaseType;
	}
}
