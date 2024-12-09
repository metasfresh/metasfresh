/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.document.invoicingpool;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Invoicing_Pool;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_DocType_Invoicing_Pool.class)
@Component
public class C_DocType_Invoicing_Pool
{
	private static final AdMessageKey MSG_DIFFERENT_SO_TRX_INVOICING_POOL_DOCUMENT_TYPE = AdMessageKey.of("DifferentSOTrxInvoicingPoolDocumentType");
	private static final AdMessageKey MSG_REFERENCED_INVOICING_POOL = AdMessageKey.of("ReferencedInvoicingPool");

	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { 
					I_C_DocType_Invoicing_Pool.COLUMNNAME_Positive_Amt_C_DocType_ID,
					I_C_DocType_Invoicing_Pool.COLUMNNAME_Negative_Amt_C_DocType_ID,
					I_C_DocType_Invoicing_Pool.COLUMNNAME_IsSOTrx })
	public void validateSOTrx(@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		validatePositiveAmountDocType(docTypeInvoicingPoolRecord);
		validateNegativeAmountDocType(docTypeInvoicingPoolRecord);
	}

	@ModelChange(
			timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = I_C_DocType_Invoicing_Pool.COLUMNNAME_IsActive)
	public void validateInactivation(@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		if (docTypeInvoicingPoolRecord.isActive())
		{
			return;
		}
		
		final ImmutableSet<DocTypeId> referencingDocTypeIds = docTypeBL
				.getDocTypeIdsByInvoicingPoolId(DocTypeInvoicingPoolId.ofRepoId(docTypeInvoicingPoolRecord.getC_DocType_Invoicing_Pool_ID()));

		if (!referencingDocTypeIds.isEmpty())
		{
			throw new AdempiereException(MSG_REFERENCED_INVOICING_POOL)
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("DocTypeIds", referencingDocTypeIds)
					.setParameter("DocTypeInvoicingPoolId", docTypeInvoicingPoolRecord.getC_DocType_Invoicing_Pool_ID());
		}
	}
	
	private void validatePositiveAmountDocType(@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		final DocTypeId positiveAmountDocTypeId = DocTypeId.ofRepoId(docTypeInvoicingPoolRecord.getPositive_Amt_C_DocType_ID());

		validateDocType(positiveAmountDocTypeId, docTypeInvoicingPoolRecord);
	}

	private void validateNegativeAmountDocType(@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		final DocTypeId negativeAmountDocTypeId = DocTypeId.ofRepoId(docTypeInvoicingPoolRecord.getNegative_Amt_C_DocType_ID());

		validateDocType(negativeAmountDocTypeId, docTypeInvoicingPoolRecord);
	}

	private void validateDocType(
			@NonNull final DocTypeId docTypeId,
			@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		final I_C_DocType docTypeRecord = docTypeBL.getById(docTypeId);
		if (docTypeRecord.isSOTrx() != docTypeInvoicingPoolRecord.isSOTrx())
		{
			throw new AdempiereException(MSG_DIFFERENT_SO_TRX_INVOICING_POOL_DOCUMENT_TYPE)
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("DocType.Name", docTypeRecord.getName())
					.setParameter("DocTypeInvoicingPoolId", docTypeInvoicingPoolRecord.getC_DocType_Invoicing_Pool_ID());
		}
	}
}
