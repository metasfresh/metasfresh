package de.metas.banking.service;

import org.compiere.model.I_C_Payment;

import de.metas.util.ISingletonService;

/**
 * Handles cash journals (as bank statements) when CASH_AS_PAYMENT functionality is activated (default).
 * 
 * @author tsa
 *
 */
public interface ICashStatementBL extends ISingletonService
{
	void createCashStatementLine(I_C_Payment payment);
}
