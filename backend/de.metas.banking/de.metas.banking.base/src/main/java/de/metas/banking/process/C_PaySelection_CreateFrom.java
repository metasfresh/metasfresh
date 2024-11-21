package de.metas.banking.process;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.InvoiceMatchingMode;
import de.metas.document.engine.DocStatus;
import de.metas.payment.PaymentRule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySelection;

import java.sql.Timestamp;

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

	@Param(parameterName = "OnlyDiscount", mandatory = true)
	private boolean p_OnlyDiscount;

	@Param(parameterName = "OnlyDue", mandatory = true)
	private boolean p_OnlyDue;

	@Param(parameterName = "IncludeInDispute", mandatory = false)
	private boolean p_IncludeInDispute;

	@Param(parameterName = "MatchRequirement", mandatory = true)
	private String p_MatchRequirement;

	@Param(parameterName = "PayDate", mandatory = false)
	private Timestamp p_PayDate;

	@Param(parameterName = "PaymentRule", mandatory = false)
	private String p_PaymentRule;

	@Param(parameterName = I_C_BPartner.COLUMNNAME_C_BPartner_ID, mandatory = false)
	private int p_C_BPartner_ID;

	@Param(parameterName = I_C_BP_Group.COLUMNNAME_C_BP_Group_ID, mandatory = false)
	private int p_C_BP_Group_ID;

	@Param(parameterName = "DateTrx", mandatory = false)
	private Timestamp p_DateTrx;

	@Param(parameterName = "POReference", mandatory = false)
	private String p_POReference;

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

		// set params
		paySelectionUpdater.setOnlyDiscount(p_OnlyDiscount);
		paySelectionUpdater.setOnlyDue(p_OnlyDue);
		paySelectionUpdater.setIncludeInDispute(p_IncludeInDispute);
		paySelectionUpdater.setMatchRequirement(InvoiceMatchingMode.ofCode(p_MatchRequirement));
		paySelectionUpdater.setPayDate(p_PayDate);
		paySelectionUpdater.setPaymentRule(PaymentRule.ofNullableCode(p_PaymentRule));
		paySelectionUpdater.setC_BPartner_ID(p_C_BPartner_ID);
		paySelectionUpdater.setC_BP_Group_ID(p_C_BP_Group_ID);
		paySelectionUpdater.setPOReference(p_POReference);
		paySelectionUpdater.setDateTrx(p_DateTrx);
	}

	@Override
	protected String doIt()
	{
		paySelectionUpdater.update();
		return paySelectionUpdater.getSummary();
	}

}
