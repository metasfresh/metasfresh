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

import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType_Invoicing_Pool;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class DocTypeInvoicingPoolRepository
{
	private CCache<DocTypeInvoicingPoolId, DocTypeInvoicingPool> docTypeInvoicingPoolById = CCache.<DocTypeInvoicingPoolId, DocTypeInvoicingPool>builder()
			.tableName(I_C_DocType_Invoicing_Pool.Table_Name)
			.build();

	@NonNull
	public DocTypeInvoicingPool getById(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return docTypeInvoicingPoolById.getOrLoad(docTypeInvoicingPoolId, this::retrieveDocTypeInvoicingPoolById);
	}

	@NonNull
	public DocTypeInvoicingPool create(@NonNull final DocTypeInvoicingPoolCreateRequest request)
	{
		final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord = newInstance(I_C_DocType_Invoicing_Pool.class);

		docTypeInvoicingPoolRecord.setName(request.getName());
		docTypeInvoicingPoolRecord.setPositive_Amt_C_DocType_ID(DocTypeId.toRepoId(request.getPositiveAmountDocTypeId()));
		docTypeInvoicingPoolRecord.setNegative_Amt_C_DocType_ID(DocTypeId.toRepoId(request.getNegativeAmountDocTypeId()));
		docTypeInvoicingPoolRecord.setIsSOTrx(request.getIsSoTrx().toBoolean());
		docTypeInvoicingPoolRecord.setIsActive(request.isActive());

		saveRecord(docTypeInvoicingPoolRecord);
		
		return ofRecord(docTypeInvoicingPoolRecord);
	}

	@NonNull
	private DocTypeInvoicingPool retrieveDocTypeInvoicingPoolById(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord = load(docTypeInvoicingPoolId, I_C_DocType_Invoicing_Pool.class);

		if (docTypeInvoicingPoolRecord == null)
		{
			throw new AdempiereException("No C_DocType_Invoicing_Pool record found for ID!")
					.appendParametersToMessage()
					.setParameter("DocTypeInvoicingPoolId", docTypeInvoicingPoolId);
		}

		return ofRecord(docTypeInvoicingPoolRecord);
	}

	@NonNull
	private static DocTypeInvoicingPool ofRecord(@NonNull final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord)
	{
		return DocTypeInvoicingPool.builder()
				.id(DocTypeInvoicingPoolId.ofRepoId(docTypeInvoicingPoolRecord.getC_DocType_Invoicing_Pool_ID()))
				.name(docTypeInvoicingPoolRecord.getName())
				.positiveAmountDocTypeId(DocTypeId.ofRepoId(docTypeInvoicingPoolRecord.getPositive_Amt_C_DocType_ID()))
				.negativeAmountDocTypeId(DocTypeId.ofRepoId(docTypeInvoicingPoolRecord.getNegative_Amt_C_DocType_ID()))
				.isSoTrx(SOTrx.ofBoolean(docTypeInvoicingPoolRecord.isSOTrx()))
				.isOnDistinctICTypes(docTypeInvoicingPoolRecord.isOnDistinctICTypes())
				.isActive(docTypeInvoicingPoolRecord.isActive())
				.build();
	}
}
