package de.metas.banking.service;

import java.math.BigDecimal;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

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

import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.util.ISingletonService;

public interface IBankStatementBL extends ISingletonService
{
	/**
	 * Dynamic attribute which if set on a {@link I_C_BankStatementLine_Ref} will disable the model interceptors. which are automatically recalculating the {@link I_C_BankStatementLine} amounts from
	 * {@link I_C_BankStatementLine_Ref} when they change.
	 * <p>
	 * It's main purpose is to be used when mass generating {@link I_C_BankStatementLine_Ref}s.
	 */
	ModelDynAttributeAccessor<I_C_BankStatementLine_Ref, Boolean> DYNATTR_DisableBankStatementLineRecalculateFromReferences = new ModelDynAttributeAccessor<>(
			"DisableBankStatementLineRecalculateFromReferences", Boolean.class);

	/**
	 * Handles:
	 * <ul>
	 * <li>{@link I_C_BankStatementLine_Ref#getC_Payment()}s
	 * </ul>
	 */
	void handleAfterPrepare(I_C_BankStatement bankStatement);

	/**
	 * Handles:
	 * <ul>
	 * <li>{@link I_C_BankStatementLine_Ref#getC_Payment()}s
	 * </ul>
	 */
	void handleAfterComplete(I_C_BankStatement bankStatement);

	/**
	 * Handles:
	 * <ul>
	 * <li>{@link I_C_BankStatementLine_Ref#getC_Payment()}s
	 * </ul>
	 */
	void handleBeforeVoid(I_C_BankStatement bankStatement);

	/**
	 * Updates {@link I_C_BankStatementLine}'s amounts from {@link I_C_BankStatementLine_Ref} lines.
	 * <p>
	 * NOTE: this method it is also saving the given bank statement line.
	 */
	void recalculateStatementLineAmounts(I_C_BankStatementLine bankStatementLine);

	/**
	 * Updates bank statement ending balance as "beginning balance" + "statement difference".
	 */
	void updateEndingBalance(I_C_BankStatement bankStatement);

	/**
	 * Un-post given bank statement.
	 */
	void unpost(I_C_BankStatement bankStatement);

	boolean isReconciled(I_C_BankStatementLine line);

	BigDecimal computeStmtAmtExcludingChargeAmt(I_C_BankStatementLine line);
}
