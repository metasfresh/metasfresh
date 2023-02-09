/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.eevolution.document;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.X_C_RemittanceAdvice;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOM;

import javax.annotation.Nullable;

public class PP_Product_BOM_DocHandler implements DocumentHandler
{
	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractProductBom(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractProductBom(docFields).getCreatedBy();
	}

	@Nullable
	@Override
	public InstantAndOrgId getDocumentDate(final DocumentTableFields docFields)
	{
		final I_PP_Product_BOM productBom = extractProductBom(docFields);
		final OrgId orgId = OrgId.ofRepoId(productBom.getAD_Org_ID());
		return InstantAndOrgId.ofTimestamp(productBom.getDateDoc(), orgId);
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_PP_Product_BOM productBomRecord = extractProductBom(docFields);

		productBomRecord.setDocAction(X_C_RemittanceAdvice.DOCACTION_Re_Activate);
		productBomRecord.setProcessed(true);
		return X_PP_Product_BOM.DOCSTATUS_Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_PP_Product_BOM productBom = extractProductBom(docFields);

		productBom.setProcessed(false);
		productBom.setDocAction(X_PP_Product_BOM.DOCACTION_Complete);
	}

	private static I_PP_Product_BOM extractProductBom(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_PP_Product_BOM.class);
	}
}