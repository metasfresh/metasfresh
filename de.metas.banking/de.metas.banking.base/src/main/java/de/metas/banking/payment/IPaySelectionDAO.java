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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;

/**
 * @author al
 */
public interface IPaySelectionDAO extends ISingletonService
{
	<T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(I_C_PaySelection paySelection, Class<T> clazz);

	int retrievePaySelectionLinesCount(I_C_PaySelection paySelection);

	int retrieveLastPaySelectionLineNo(Properties ctx, int paySelectionId, String trxName);

	List<I_C_PaySelectionLine> retrievePaySelectionLinesMatchingInvoice(I_C_PaySelection paySelection, I_C_Invoice invoice);

	boolean isPaySelectionLineMatchInvoice(I_C_PaySelection paySelection, I_C_Invoice invoice);

	Collection<MPaySelectionCheck> retrievePaySelectionChecks(MPaySelection ps);

	Collection<MPaySelectionCheck> retrievePaySelectionChecks(MPaySelection ps, String paymentRule);

	Collection<MPaySelectionLine> retrievePaySelectionLines(MPaySelectionCheck psc);

	List<de.metas.banking.model.I_C_PaySelectionLine> retrievePaySelectionLines(I_C_BankStatementLine bankStatementLine);
	de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLine(I_C_BankStatementLine_Ref bankStatementLineRef);

	/**
	 * Returns the given invoice's <code>C_PaySelectionLine</code>s
	 * 
	 * @param invoice
	 * @return
	 */
	List<I_C_PaySelectionLine> retrievePaySelectionLines(org.compiere.model.I_C_Invoice invoice);
}
