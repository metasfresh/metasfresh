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

package de.metas.costrevaluation.callout;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_CostRevaluation;

@Callout(I_M_CostRevaluation.class)
public class M_CostRevaluation
{

	@CalloutMethod(columnNames = I_M_CostRevaluation.COLUMNNAME_C_DocType_ID)
	public void onDocTypeChanged(final I_M_CostRevaluation costRevaluation)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(costRevaluation.getC_DocType_ID());
		if (docTypeId == null)
		{
			return;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docType)
				.setOldDocumentNo(costRevaluation.getDocumentNo())
				.setDocumentModel(costRevaluation)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			costRevaluation.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

}
