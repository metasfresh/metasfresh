package de.metas.fresh.api.invoicecandidate.impl;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;

import de.metas.document.IDocTypeDAO;
import de.metas.fresh.api.invoicecandidate.IFreshInvoiceCandBL;
import de.metas.fresh.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingBL;

public class FreshInvoiceCandBL implements IFreshInvoiceCandBL
{
	@Override
	public void updateC_DocTypeInvoice(I_C_Invoice_Candidate candidate)
	{
		if(candidate.isSOTrx())
		{
			// nothing to do, because the produzendenabrechnung doctype is only for purchase transactions
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);

		final int adClientId = candidate.getAD_Client_ID();
		final int adOrgId = candidate.getAD_Org_ID();

		final String docBaseType = X_C_DocType.DOCBASETYPE_APInvoice;
		final String docSubType = X_C_DocType.DOCSUBTYPE_VendorInvoice;

		final int freshProduzentenabrechnung =
			Services.get(IDocTypeDAO.class).getDocTypeId(
						ctx,
						docBaseType,
						docSubType,
						adClientId,
						adOrgId,
						ITrx.TRXNAME_None);

		if (freshProduzentenabrechnung <= 0)
		{
			// This means that no API - VI doc type was defined in db
			// do nothing
			return;
		}

		final int candidateDocTypeID = candidate.getC_DocTypeInvoice_ID();

		final I_C_BPartner partner = InterfaceWrapperHelper.create(candidate.getBill_BPartner(), I_C_BPartner.class);

		final boolean isFresh_Produzentenabrechnung = partner.isFresh_Produzentenabrechnung();

		if (!isFresh_Produzentenabrechnung)
		{
			if (freshProduzentenabrechnung == candidateDocTypeID)
			{
				// the candidate was already freshProduzentenabrechnung but the partner was changed
				// and the new partner is not freshProduzentenabrechnung
				// In this case, the doctype of the candidate will be set to null
				candidate.setC_DocTypeInvoice(null);
			}

			// no other validations needed. Do nothing any more
			return;
		}

		if (candidate.getC_DocTypeInvoice_ID() > 0)
		{
			// check if we already have another special docType from material tracking
			final I_C_DocType docTypeInvoice = candidate.getC_DocTypeInvoice();
			if (X_C_DocType.DOCBASETYPE_APInvoice.equals(docTypeInvoice.getDocBaseType()) &&
					(IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment.equals(docTypeInvoice.getDocSubType()) ||
					IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement.equals(docTypeInvoice.getDocSubType())))
			{
				return; // task 07845: these two doctypes are even more specific; don't override them
			}
		}

		candidate.setC_DocTypeInvoice_ID(freshProduzentenabrechnung);
	}
}
