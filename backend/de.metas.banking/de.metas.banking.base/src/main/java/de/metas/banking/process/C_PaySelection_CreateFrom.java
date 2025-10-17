package de.metas.banking.process;

import java.sql.Timestamp;

import org.compiere.model.I_C_PaySelection;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.PaySelectionMatchingMode;
import de.metas.document.engine.DocStatus;
import de.metas.payment.PaymentRule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Create Payment Selection Lines from AP or AR Invoices
 */
public class C_PaySelection_CreateFrom extends JavaProcess implements IProcessPrecondition
{
	// services
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private IPaySelectionUpdater paySelectionUpdater;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).orElse(null);
		if (paySelection == null)
		{
			// to avoid NPE in case the document is new in webui, not yet saved
			return ProcessPreconditionsResolution.reject();
		}

		final DocStatus paySelectionDocStatus = DocStatus.ofNullableCodeOrUnknown(paySelection.getDocStatus());
		if (!paySelectionDocStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
		paySelectionUpdater = paySelectionBL.newPaySelectionUpdater();

		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(getRecord_ID());
		final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
		paySelectionUpdater.setC_PaySelection(paySelection);

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{

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
				final PaySelectionMatchingMode matchRequirement = PaySelectionMatchingMode.ofCode(para.getParameterAsString());
				paySelectionUpdater.setMatchRequirement(matchRequirement);
			}
			else if (name.equals("PayDate"))
			{
				final Timestamp p_PayDate = para.getParameterAsTimestamp();
				paySelectionUpdater.setPayDate(p_PayDate);
			}
			else if (name.equals("PaymentRule"))
			{
				final PaymentRule paymentRule = PaymentRule.ofNullableCode(para.getParameterAsString());
				paySelectionUpdater.setPaymentRule(paymentRule);
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
	protected String doIt()
	{
		paySelectionUpdater.update();
		return paySelectionUpdater.getSummary();
	}

}
