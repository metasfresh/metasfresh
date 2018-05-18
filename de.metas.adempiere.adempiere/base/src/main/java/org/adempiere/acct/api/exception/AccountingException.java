package org.adempiere.acct.api.exception;

import org.adempiere.exceptions.AdempiereException;

public class AccountingException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1774967178922781694L;

	public AccountingException(String message)
	{
		super(message);
	}

	public AccountingException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
