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
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_RemittanceAdvice.class)
@Callout(I_C_RemittanceAdvice.class)
@Component
public class C_RemittanceAdvice
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final RemittanceAdviceRepository remittanceAdviceRepository;

	public C_RemittanceAdvice(final RemittanceAdviceRepository remittanceAdviceRepository)
	{
		this.remittanceAdviceRepository = remittanceAdviceRepository;
	}

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

		final I_C_DocType docType = docTypeDAO.getRecordById(docTypeId);
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = I_C_RemittanceAdvice.COLUMNNAME_C_Payment_Doctype_Target_ID)
	public void setIsSOTrxFromPaymentDocType(@NonNull final I_C_RemittanceAdvice record)
	{
		final I_C_DocType targetPaymentDocType = docTypeDAO.getRecordById(record.getC_Payment_Doctype_Target_ID());

		record.setIsSOTrx(targetPaymentDocType.isSOTrx());
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = I_C_RemittanceAdvice.COLUMNNAME_Processed)
	public void setProcessedFlag(@NonNull final I_C_RemittanceAdvice record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.getRemittanceAdvice(remittanceAdviceId);

		remittanceAdvice.setProcessedFlag(record.isProcessed());

		remittanceAdviceRepository.updateRemittanceAdvice(remittanceAdvice);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteLines(@NonNull final I_C_RemittanceAdvice record)
	{
		queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, record.getC_RemittanceAdvice_ID())
				.create()
				.delete();
	}
}
