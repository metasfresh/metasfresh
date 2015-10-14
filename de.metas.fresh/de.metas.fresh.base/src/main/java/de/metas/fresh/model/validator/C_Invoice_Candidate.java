package de.metas.fresh.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.fresh.api.invoicecandidate.IFreshInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

@Validator(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setFresh_Produzentenabrechnung(final I_C_Invoice_Candidate candidate)
	{
		// set DocType invoice fresh_Produzentenabrechnung based on the flag fresh_Produzentenabrechnung from c_BPartner
		Services.get(IFreshInvoiceCandBL.class).updateC_DocTypeInvoice(candidate);
	}
}
