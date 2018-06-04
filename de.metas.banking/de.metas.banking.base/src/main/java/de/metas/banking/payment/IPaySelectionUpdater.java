package de.metas.banking.payment;

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

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_PaySelection;

import de.metas.adempiere.model.I_C_PaySelectionLine;

/**
 * Used to create or update {@link I_C_PaySelectionLine}s for a given {@link I_C_PaySelection}.<br>
 * Use {@link IPaySelectionBL#newPaySelectionUpdater()} to get your own.
 *
 * @author tsa
 * @task 08972
 */
public interface IPaySelectionUpdater
{
	/**
	 * Perform {@link I_C_PaySelectionLine} create/update.
	 * <p>
	 * Note that if there is more than one <code>C_BP_BankAccount</code> record which fits to the respective invoice,
	 * then the one with the biggest <code>C_BP_BankAccount_ID</code> is chosen.<br>
	 * In the banking module, there is <code>IPaymentRequestBL</code> which shall select the correct bank account based on the <code>C_Payment_Request</code>.
	 */
	void update();

	/**
	 * Gets update summary (ready to be translated)
	 *
	 * @return update summary
	 */
	String getSummary();

	IPaySelectionUpdater setContext(final IContextAware context);

	IPaySelectionUpdater setContext(final Properties ctx, final String trxName);

	/**
	 * Sets {@link I_C_PaySelection} in scope (mandatory).
	 *
	 * @param paySelection
	 */
	IPaySelectionUpdater setC_PaySelection(final I_C_PaySelection paySelection);

	IPaySelectionUpdater setC_BP_Group_ID(int bpGroupId);

	IPaySelectionUpdater setC_BPartner_ID(int bpartnerId);

	IPaySelectionUpdater setPaymentRule(String paymentRule);

	IPaySelectionUpdater setPayDate(Timestamp payDate);

	IPaySelectionUpdater setMatchRequirement(String matchRequirement);

	IPaySelectionUpdater setIncludeInDispute(boolean includeInDispute);

	IPaySelectionUpdater setOnlyDue(boolean onlyDue);

	IPaySelectionUpdater setOnlyDiscount(boolean onlyDiscount);

	IPaySelectionUpdater addPaySelectionLineToUpdate(int paySelectionLineId);

	/**
	 * Adds {@link I_C_PaySelectionLine}s to be used.
	 *
	 * NOTE: given {@link I_C_PaySelectionLine} will be used in another instance, so after running this update, if you want to use these {@link I_C_PaySelectionLine}s please make sure you are
	 * refreshing them.
	 *
	 * @param paySelectionLines
	 */
	IPaySelectionUpdater addPaySelectionLinesToUpdate(Iterable<? extends org.compiere.model.I_C_PaySelectionLine> paySelectionLines);
}
