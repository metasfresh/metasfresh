package de.metas.document.interceptor;

import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolService;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_C_DocType.class)
@Component
public class C_DocType
{
	private static final AdMessageKey MSG_INACTIVE_INVOICING_POOL = AdMessageKey.of("InvoicingPoolNotActive");
	private static final AdMessageKey MSG_DIFFERENT_SO_TRX_INVOICING_POOL_DOCUMENT_TYPE = AdMessageKey.of("DifferentSOTrxInvoicingPoolDocumentType");
	
	@NonNull
	private final DocTypeInvoicingPoolService docTypeInvoicingPoolService;

	public C_DocType(@NonNull final DocTypeInvoicingPoolService docTypeInvoicingPoolService)
	{
		this.docTypeInvoicingPoolService = docTypeInvoicingPoolService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteDocTypeAccess(@NonNull final I_C_DocType docTypeRecord)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Document_Action_Access.class)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMN_C_DocType_ID, docTypeRecord.getC_DocType_ID())
				.create()
				.delete();
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_DocType.COLUMNNAME_C_DocType_Invoicing_Pool_ID, I_C_DocType.COLUMNNAME_IsSOTrx })
	public void validateInvoicingPoolAssignment(@NonNull final I_C_DocType docType)
	{
		Optional.ofNullable(DocTypeInvoicingPoolId.ofRepoIdOrNull(docType.getC_DocType_Invoicing_Pool_ID()))
				.map(docTypeInvoicingPoolService::getById)
				.ifPresent(docTypeInvoicingPool -> {
					if (!docTypeInvoicingPool.isActive())
					{
						throw new AdempiereException(MSG_INACTIVE_INVOICING_POOL)
								.markAsUserValidationError()
								.appendParametersToMessage()
								.setParameter("DocTypeInvoicingPool.Name", docTypeInvoicingPool.getName())
								.setParameter("DocTypeId", docType.getC_DocType_ID());
					}
					
					if (docTypeInvoicingPool.getIsSoTrx().toBoolean() != docType.isSOTrx())
					{
						throw new AdempiereException(MSG_DIFFERENT_SO_TRX_INVOICING_POOL_DOCUMENT_TYPE)
								.markAsUserValidationError()
								.appendParametersToMessage()
								.setParameter("DocTypeInvoicingPool.Name", docTypeInvoicingPool.getName())
								.setParameter("DocTypeId", docType.getC_DocType_ID());
					}
				});
	}
}
