package de.metas.banking.process;

/*
 * #%L
 * de.metas.banking.base
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


import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionUpdater;

/**
 * Create Payment Selection Lines from AP Invoices
 * 
 * @author tsa
 * @task 08972
 */
public class C_PaySelection_CreateFrom extends SvrProcess
{
	// services
	private final transient IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	private IPaySelectionUpdater paySelectionUpdater;

	@Override
	protected void prepare()
	{
		paySelectionUpdater = paySelectionBL.newPaySelectionUpdater();

		final I_C_PaySelection paySelection = getRecord(I_C_PaySelection.class);
		paySelectionUpdater.setC_PaySelection(paySelection);

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("OnlyDiscount"))
			{
				final boolean p_OnlyDiscount = para.getParameterAsBoolean();
				paySelectionUpdater.setOnlyDiscount(p_OnlyDiscount);
			}
			else if (name.equals("OnlyDue"))
			{
				final boolean p_OnlyDue = para.getParameterAsBoolean();
				paySelectionUpdater.setOnlyDue(p_OnlyDue);
			}
			else if (name.equals("IncludeInDispute"))
			{
				final boolean p_IncludeInDispute = para.getParameterAsBoolean();
				paySelectionUpdater.setIncludeInDispute(p_IncludeInDispute);
			}
			else if (name.equals("MatchRequirement"))
			{
				final String p_MatchRequirement = para.getParameterAsString();
				paySelectionUpdater.setMatchRequirement(p_MatchRequirement);
			}
			else if (name.equals("PayDate"))
			{
				final Timestamp p_PayDate = para.getParameterAsTimestamp();
				paySelectionUpdater.setPayDate(p_PayDate);
			}
			else if (name.equals("PaymentRule"))
			{
				final String p_PaymentRule = para.getParameterAsString();
				paySelectionUpdater.setPaymentRule(p_PaymentRule);
			}
			else if (name.equals("C_BPartner_ID"))
			{
				final int p_C_BPartner_ID = para.getParameterAsInt();
				paySelectionUpdater.setC_BPartner_ID(p_C_BPartner_ID);
			}
			else if (name.equals("C_BP_Group_ID"))
			{
				final int p_C_BP_Group_ID = para.getParameterAsInt();
				paySelectionUpdater.setC_BP_Group_ID(p_C_BP_Group_ID);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		paySelectionUpdater.setContext(this);
		paySelectionUpdater.update();
		return paySelectionUpdater.getSummary();
	}

}
