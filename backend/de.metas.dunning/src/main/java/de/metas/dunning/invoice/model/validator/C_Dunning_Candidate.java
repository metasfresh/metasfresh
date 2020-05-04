package de.metas.dunning.invoice.model.validator;

/*
 * #%L
 * de.metas.dunning
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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.model.I_C_Dunning_Candidate;

@Validator(I_C_Dunning_Candidate.class)
public class C_Dunning_Candidate
{
	public static final String POATTR_CallerPO = C_Dunning_Candidate.class + "#CallerPO";

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = I_C_Dunning_Candidate.COLUMNNAME_DunningGrace)
	public void setDunningGraceOnInvoice(final I_C_Dunning_Candidate candidate)
	{
		if (candidate.getAD_Table_ID() != InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_Invoice.class, trxName);
		invoice.setDunningGrace(candidate.getDunningGrace());
		InterfaceWrapperHelper.setDynAttribute(invoice, POATTR_CallerPO, candidate);
		InterfaceWrapperHelper.save(invoice);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = I_C_Dunning_Candidate.COLUMNNAME_IsDunningDocProcessed)
	public void setDunningLevelOnInvoice(final I_C_Dunning_Candidate candidate)
	{
		if (candidate.getAD_Table_ID() != InterfaceWrapperHelper.getTableId(I_C_Invoice.class)
				|| !candidate.isDunningDocProcessed())
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_Invoice.class, trxName);
		invoice.setC_DunningLevel(candidate.getC_DunningLevel());
		InterfaceWrapperHelper.save(invoice);
	}
}
