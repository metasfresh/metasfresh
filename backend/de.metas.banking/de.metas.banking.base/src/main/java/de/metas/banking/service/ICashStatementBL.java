package de.metas.banking.service;

import de.metas.util.ISingletonService;
import org.compiere.model.I_C_Payment;

/**
 * Handles cash journals (as bank statements).
 * 
 * @author tsa
 *
 */
public interface ICashStatementBL extends ISingletonService
{
	void createCashStatementLine(I_C_Payment payment);
}
