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
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

public class M_CostRevaluation_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_CostRevaluation)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(Env.getClientId().getRepoId())
				.adOrgId(Env.getOrgId().getRepoId())
				.build();

		final DocTypeId docTypeId = Services.get(IDocTypeDAO.class).getDocTypeIdOrNull(docTypeQuery);
		final I_M_CostRevaluation costRevaluation = calloutRecord.getModel(I_M_CostRevaluation.class);

		if (docTypeId == null || costRevaluation == null)
		{
			return;
		}

		final I_C_DocType costRevaluationDocType = Services.get(IDocTypeDAO.class).getById(docTypeId);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(costRevaluationDocType)
				.setOldDocumentNo(costRevaluation.getDocumentNo())
				.setDocumentModel(costRevaluation)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			costRevaluation.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

}
