package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.document.ICopyHandlerBL;
import de.metas.edi.spi.impl.EdiInvoiceCandidateListener;
import de.metas.edi.spi.impl.EdiInvoiceCopyHandler;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;

public class Main implements ModelValidator
{
	private int adClientId = -1;

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		adClientId = client == null ? -1 : client.getAD_Client_ID();

		engine.addModelValidator(new C_Invoice(), client);
		engine.addModelValidator(new C_BPartner(), client);
		engine.addModelValidator(C_Order.INSTANCE, client);
		engine.addModelValidator(C_OrderLine.INSTANCE, client);
		engine.addModelValidator(new C_OLCand(), client);

		engine.addModelValidator(EDI_Desadv.INSTANCE, client);
		engine.addModelValidator(EDI_DesadvLine.INSTANCE, client);
		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(M_InOutLine.INSTANCE, client);

		engine.addModelValidator(new C_Invoice_Candidate(), client);

		// task 08926
		// invoice candidate listener

		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(EdiInvoiceCandidateListener.instance);

		// task 08926
		// invoice copy handler

		Services.get(ICopyHandlerBL.class).registerCopyHandler(
				org.compiere.model.I_C_Invoice.class,
				new IQueryFilter<ImmutablePair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice>>()
				{
					@Override
					public boolean accept(ImmutablePair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice> model)
					{
						return true;
					}
				},
				new EdiInvoiceCopyHandler());
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}
}
