/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.api.impl;

import de.metas.common.util.Check;
import de.metas.document.DocTypeId;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;

import javax.annotation.Nullable;
import java.util.HashMap;

@Value
public class InvoiceDocTypeAggregator
{
	@NonNull
	HashMap<DocTypeId, I_C_DocType> docTypeRecords;

	@Nullable
	@Getter(AccessLevel.NONE)
	DocTypeInvoicingPoolId docTypeInvoicingPoolId;

	@NonNull
	public static InvoiceDocTypeAggregator ofDocType(@NonNull final I_C_DocType docType)
	{
		return new InvoiceDocTypeAggregator(
				new HashMap<DocTypeId, I_C_DocType>()
				{{
					put(DocTypeId.ofRepoId(docType.getC_DocType_ID()), docType);
				}},
				DocTypeInvoicingPoolId.ofRepoIdOrNull(docType.getC_DocType_Invoicing_Pool_ID()));
	}

	private InvoiceDocTypeAggregator(
			@NonNull final HashMap<DocTypeId, I_C_DocType> docTypeRecords,
			@Nullable final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		Check.assumeNotEmpty(docTypeRecords.keySet(), "docTypeRecords cannot be empty");
		
		this.docTypeRecords = docTypeRecords;
		this.docTypeInvoicingPoolId = docTypeInvoicingPoolId;
	}

	public void addDocType(@NonNull final I_C_DocType docType)
	{
		if (docTypeRecords.containsKey(DocTypeId.ofRepoId(docType.getC_DocType_ID())))
		{
			return; // nothing to do
		}

		Check.assumeNotNull(docTypeInvoicingPoolId, "Multiple C_DocType_ID cannot be aggregated if they don't share the same docTypeInvoicingPoolId!" 
				+ " DocTypeIds:" + docTypeRecords.keySet() + "; new docType to aggregate: " + docType.getC_DocType_ID());

		if (!docTypeInvoicingPoolId.equals(DocTypeInvoicingPoolId.ofRepoIdOrNull(docType.getC_DocType_Invoicing_Pool_ID())))
		{
			throw new AdempiereException("Multiple C_DocType_ID cannot be aggregated if they don't share the same docTypeInvoicingPoolId!")
					.appendParametersToMessage()
					.setParameter("this.DocTypeIds", docTypeRecords.keySet())
					.setParameter("this.docTypeInvoicingPoolId", docTypeInvoicingPoolId)
					.setParameter("docTypeToAdd.C_DocType_ID", docType.getC_DocType_ID())
					.setParameter("docTypeToAdd.docTypeInvoicingPoolId", docType.getC_DocType_Invoicing_Pool_ID());
		}

		docTypeRecords.put(DocTypeId.ofRepoId(docType.getC_DocType_ID()), docType);
	}

	@Nullable
	public I_C_DocType getSingleDocTypeOrNull()
	{
		if (isSingleDocType())
		{
			return docTypeRecords.values().iterator().next();
		}

		return null;
	}

	@Nullable
	public DocTypeInvoicingPoolId getAggregatedPoolIdOrNull()
	{
		if (areMultipleDocTypes())
		{
			Check.assumeNotNull(docTypeInvoicingPoolId, "docTypeInvoicingPoolId cannot be null if multiple doc types were aggregated!"
					+ " DocTypeIds=" + docTypeRecords.keySet());

			return docTypeInvoicingPoolId;
		}

		return null;
	}

	private boolean areMultipleDocTypes()
	{
		return docTypeRecords.values().size() > 1;
	}

	private boolean isSingleDocType()
	{
		return docTypeRecords.values().size() == 1;
	}
}
