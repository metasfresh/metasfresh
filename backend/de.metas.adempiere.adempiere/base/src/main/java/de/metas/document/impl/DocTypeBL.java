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
import de.metas.document.DocBaseAndSubType;
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
		return docTypesRepo.getById(docTypeId);
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
		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return InterfaceWrapperHelper.getModelTranslationMap(docType)
				.getColumnTrl(I_C_DocType.COLUMNNAME_Name, docType.getName());
	}

	@Override
	public boolean isSalesQuotation(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
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
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
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
		final I_C_DocType dt = docTypesRepo.getById(docTypeId);
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
		return getDocBaseAndSubType(docTypeId).isPrepaySO();
	}

	@Override
	public boolean hasRequestType(@NonNull final DocTypeId docTypeId)
	{
		return docTypesRepo.getById(docTypeId).getR_RequestType_ID() > 0;
	}

	@Override
	public boolean isRequisition(final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isRequisition();
	}

	@Override
	public boolean isMediated(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isMediated();
	}

	@Override
	public boolean isCallOrder(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isCallOrder();
	}

	@Override
	public boolean isInternalVendorInvoice(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isInternalVendorInvoice();
	}

	@Override
	public boolean isProformaSO(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isProformaSO();
	}

	@Override
	public boolean isProformaShipment(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isProformaShipment();
	}

	@Override
	public boolean isProformaShippingNotification(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isProformaShippingNotification();
	}

	@Override
	public boolean isInterimInvoice(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isInterimInvoice();
	}

	@Override
	public boolean isFinalInvoiceOrFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return isFinalInvoice(docTypeId) || isFinalCreditMemo(docTypeId);
	}

	private boolean isFinalInvoice(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isFinalInvoice();
	}

	private boolean isFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isFinalCreditMemo();
	}

	@Override
	public boolean isDefinitiveInvoiceOrDefinitiveCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return isDefinitiveInvoice(docTypeId) || isDefinitiveCreditMemo(docTypeId);
	}

	private boolean isDefinitiveInvoice(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isDefinitiveInvoice();
	}

	private boolean isDefinitiveCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isDefinitiveCreditMemo();
	}

	@Override
	public boolean isSalesFinalInvoiceOrFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return isSalesFinalInvoice(docTypeId) || isSalesFinalCreditMemo(docTypeId);
	}

	private boolean isSalesFinalInvoice(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isSalesFinalInvoice();
	}

	private boolean isSalesFinalCreditMemo(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isSalesFinalCreditMemo();
	}

	@Override
	public boolean isDeliveryInstruction(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isDeliveryInstruction();
	}

	@Override
	public void save(@NonNull final I_C_DocType dt)
	{
		docTypesRepo.save(dt);
	}

	@Override
	public boolean isModularManufacturingOrder(@NonNull final DocTypeId docTypeId)
	{
		return getDocBaseAndSubType(docTypeId).isModularManufacturingOrder();
	}

	@NonNull
	private DocBaseAndSubType getDocBaseAndSubType(final @NonNull DocTypeId docTypeId)
	{
		return docTypesRepo.getDocBaseAndSubTypeById(docTypeId);
	}
}
