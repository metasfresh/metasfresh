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

package de.metas.remittanceadvice.interceptor;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_RemittanceAdvice;
import org.springframework.stereotype.Component;

@Interceptor(I_C_RemittanceAdvice.class)
@Callout(I_C_RemittanceAdvice.class)
@Component
public class C_RemittanceAdvice
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_RemittanceAdvice.COLUMNNAME_C_DocType_ID)
	public void updateFromDocType(final I_C_RemittanceAdvice remittanceAdviceRecord, final ICalloutField field)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(remittanceAdviceRecord.getC_DocType_ID());
		if (docTypeId == null)
		{
			return;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docType)
				.setOldDocumentNo(remittanceAdviceRecord.getDocumentNo())
				.setDocumentModel(remittanceAdviceRecord)
				.buildOrNull();

		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			remittanceAdviceRecord.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}
}
