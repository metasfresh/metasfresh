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

package de.metas.document.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

public class DocTypeBL implements IDocTypeBL
{
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	@Override
	@NonNull
	public I_C_DocType getById(@NonNull final DocTypeId docTypeId)
	{
		return docTypesRepo.getRecordById(docTypeId);
	}

	@Override
	@NonNull
	public I_C_DocType getByIdInTrx(@NonNull final DocTypeId docTypeId)
	{
		return docTypesRepo.getByIdInTrx(docTypeId);
	}

	@Override
	public DocTypeId getDocTypeIdOrNull(@NonNull final DocTypeQuery docTypeQuery)
	{
		return docTypesRepo.getDocTypeIdOrNull(docTypeQuery);
	}

	@Override
	@NonNull
	public DocTypeId getDocTypeId(@NonNull final DocTypeQuery docTypeQuery)
	{
		return docTypesRepo.getDocTypeId(docTypeQuery);
	}

	@Override
	@NonNull
	public ImmutableSet<DocTypeId> getDocTypeIdsByInvoicingPoolId(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return docTypesRepo.getDocTypeIdsByInvoicingPoolId(docTypeInvoicingPoolId);
	}

	@Override
	public ITranslatableString getNameById(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docType = docTypesRepo.getRecordById(docTypeId);
		return InterfaceWrapperHelper.getModelTranslationMap(docType)
				.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
	}

	@Override
	public boolean isSalesQuotation(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return isSalesQuotation(dt);
	}

	@Override
	public boolean isSalesQuotation(final I_C_DocType dt)
	{
		return DocBaseType.ofCode(dt.getDocBaseType()).isSalesOrder()
				&& X_C_DocType.DOCSUBTYPE_Quotation.equals(dt.getDocSubType());
	}

	@Override
	public boolean isSalesCostEstimate(final I_C_DocType dt)
	{
		return DocBaseType.ofCode(dt.getDocBaseType()).isSalesOrder()
				&& X_C_DocType.DOCSUBTYPE_CostEstimate.equals(dt.getDocSubType());
	}

	@Override
	public boolean isSalesProposal(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return isSalesProposal(dt);
	}

	@Override
	public boolean isSalesProposal(final I_C_DocType dt)
	{
		return DocBaseType.ofCode(dt.getDocBaseType()).isSalesOrder()
				&& X_C_DocType.DOCSUBTYPE_Proposal.equals(dt.getDocSubType());
	}

	@Override
	public boolean isSalesProposalOrQuotation(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return isSalesProposalOrQuotation(dt);
	}

	@Override
	public boolean isSalesProposalOrQuotation(final I_C_DocType dt)
	{
		return isSalesProposal(dt) || isSalesQuotation(dt) || isSalesCostEstimate(dt);
	}

	@Override
	public boolean isPrepay(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docType = docTypesRepo.getRecordById(docTypeId);
		return isPrepay(docType);
	}

	@Override
	public boolean isPrepay(final I_C_DocType dt)
	{
		return X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& DocBaseType.ofCode(dt.getDocBaseType()).isSalesOrder();
	}

	@Override
	public boolean hasRequestType(@NonNull final DocTypeId docTypeId)
	{
		return docTypesRepo.getRecordById(docTypeId).getR_RequestType_ID() > 0;
	}

	@Override
	public boolean isRequisition(final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return X_C_DocType.DOCSUBTYPE_Requisition.equals(dt.getDocSubType())
				&& DocBaseType.ofCode(dt.getDocBaseType()).isPurchaseOrder();
	}

	@Override
	public boolean isMediated(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return X_C_DocType.DOCSUBTYPE_Mediated.equals(dt.getDocSubType())
				&& DocBaseType.ofCode(dt.getDocBaseType()).isPurchaseOrder();
	}

	@Override
	public boolean isCallOrder(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return (X_C_DocType.DOCBASETYPE_SalesOrder.equals(dt.getDocBaseType()) || X_C_DocType.DOCBASETYPE_PurchaseOrder.equals(dt.getDocBaseType()))
				&& X_C_DocType.DOCSUBTYPE_CallOrder.equals(dt.getDocSubType());
	}

	@Override
	public boolean isInternalVendorInvoice(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCBASETYPE_APInvoice.equals(dt.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_InternalVendorInvoice.equals(dt.getDocSubType());
	}

	@Override
	public boolean isProFormaSO(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_ProFormaSO.equals(dt.getDocSubType())
				&& DocBaseType.ofCode(dt.getDocBaseType()).isSalesOrder();
	}

	@Override
	public boolean isDownPayment(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_DownPayment.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_APInvoice.equals(dt.getDocBaseType());
	}

	@Override
	public boolean isFinalInvoiceOrFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return isFinalInvoice(docTypeId) || isFinalCreditMemo(docTypeId);
	}

	private boolean isFinalInvoice(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_FinalInvoice.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_APInvoice.equals(dt.getDocBaseType());
	}

	private boolean isFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_FinalCreditMemo.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_APCreditMemo.equals(dt.getDocBaseType());
	}

	@Override
	public boolean isDefinitiveInvoiceOrDefinitiveCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return isDefinitiveInvoice(docTypeId) || isDefinitiveCreditMemo(docTypeId);
	}

	private boolean isDefinitiveInvoice(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_DefinitiveInvoice.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_APInvoice.equals(dt.getDocBaseType());
	}

	private boolean isDefinitiveCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);

		return X_C_DocType.DOCSUBTYPE_DefinitiveCreditMemo.equals(dt.getDocSubType())
				&& X_C_DocType.DOCBASETYPE_APCreditMemo.equals(dt.getDocBaseType());
	}

	@Override
	public void save(@NonNull final I_C_DocType dt)
	{
		docTypesRepo.save(dt);
	}

	@Override
	public boolean isModularManufacturingOrder(@NonNull final DocTypeId docTypeId)
	{
		final DocBaseType docBaseType = getDocBaseType(docTypeId);
		return DocBaseType.equals(docBaseType, DocBaseType.ModularOrder);
	}

	@NonNull
	private DocBaseType getDocBaseType(final @NonNull DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getRecordById(docTypeId);
		return DocBaseType.ofCode(dt.getDocBaseType());
	}
}
