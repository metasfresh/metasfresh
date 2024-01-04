package de.metas.fresh.api.invoicecandidate.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.fresh.api.invoicecandidate.IFreshInvoiceCandBL;
import de.metas.fresh.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

public class FreshInvoiceCandBL implements IFreshInvoiceCandBL
{
	@Override
	public void updateC_DocTypeInvoice(I_C_Invoice_Candidate candidate)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		if (candidate.isSOTrx())
		{
			// nothing to do, because the produzendenabrechnung doctype is only for purchase transactions
			return;
		}

		final DocTypeId freshProduzentenabrechnung = Services.get(IDocTypeDAO.class).getDocTypeId(
				DocTypeQuery.builder()
				.docBaseType(DocBaseType.APInvoice)
				.docSubType(X_C_DocType.DOCSUBTYPE_VendorInvoice)
				.adClientId(candidate.getAD_Client_ID())
				.adOrgId(candidate.getAD_Org_ID())
				.build());

		if (freshProduzentenabrechnung == null)
		{
			// This means that no API - VI doc type was defined in db
			// do nothing
			return;
		}

		final int candidateDocTypeID = candidate.getC_DocTypeInvoice_ID();

		final I_C_BPartner partner = bpartnerDAO.getById(BPartnerId.ofRepoId(candidate.getBill_BPartner_ID()), I_C_BPartner.class);

		final boolean isFresh_Produzentenabrechnung = partner.isFresh_Produzentenabrechnung();

		if (!isFresh_Produzentenabrechnung)
		{
			if (freshProduzentenabrechnung.getRepoId() == candidateDocTypeID)
			{
				// the candidate was already freshProduzentenabrechnung but the partner was changed
				// and the new partner is not freshProduzentenabrechnung
				// In this case, the doctype of the candidate will be set to null
				candidate.setC_DocTypeInvoice_ID(-1);
			}

			// no other validations needed. Do nothing any more
			return;
		}

		if (candidate.getC_DocTypeInvoice_ID() > 0)
		{

			// check if we already have another special docType from material tracking
			final I_C_DocType docTypeInvoice = docTypeDAO.getRecordById(DocTypeId.ofRepoId(candidate.getC_DocTypeInvoice_ID()));
			if (X_C_DocType.DOCBASETYPE_APInvoice.equals(docTypeInvoice.getDocBaseType()) &&
					(IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment.equals(docTypeInvoice.getDocSubType()) ||
							IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement.equals(docTypeInvoice.getDocSubType())))
			{
				return; // task 07845: these two doctypes are even more specific; don't override them
			}
		}

		candidate.setC_DocTypeInvoice_ID(freshProduzentenabrechnung.getRepoId());
	}
}
