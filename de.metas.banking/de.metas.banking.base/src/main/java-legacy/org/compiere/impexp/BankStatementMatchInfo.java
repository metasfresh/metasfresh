/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.impexp;

/**
 *	Bank Statement Match Information.
 *	Returned by Bank Statement Matcher	
 *	
 *  @author Jorg Janke
 *  @version $Id: BankStatementMatchInfo.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class BankStatementMatchInfo
{
	/**
	 * 	Standard Constructor
	 */
	public BankStatementMatchInfo()
	{
		super();
	}	//	BankStatementMatchInfo


	private int m_C_BPartner_ID = 0;
	private int m_C_Payment_ID = 0;
	private int m_C_Invoice_ID = 0;


	/**
	 * 	Do we have a match?
	 *	@return true if something could be matched
	 */
	public boolean isMatched()
	{
		return m_C_BPartner_ID > 0 || m_C_Payment_ID > 0 || m_C_Invoice_ID > 0;
	}	//	isValid


	/**
	 *	Get matched BPartner
	 * 	@return BPartner
	 */
	public int getC_BPartner_ID() 
	{
		return m_C_BPartner_ID;
	}
	/**
	 * 	Set matched BPartner
	 * 	@param C_BPartner_ID BPartner
	 */
	public void setC_BPartner_ID (int C_BPartner_ID) 
	{
		m_C_BPartner_ID = C_BPartner_ID;
	}
	
	/**
	 *	Get matched Payment
	 *	@return Payment
	 */
	public int getC_Payment_ID() 
	{
		return m_C_Payment_ID;
	}
	/**
	 *	Set matched Payment
	 *	@param C_Payment_ID payment
	 */
	public void setC_Payment_ID (int C_Payment_ID) 
	{
		m_C_Payment_ID = C_Payment_ID;
	}
	
	/**
	 *	Get matched Invoice
	 *	@return invoice
	 */
	public int getC_Invoice_ID() 
	{
		return m_C_Invoice_ID;
	}
	/**
	 * 	Set matched Invoice
	 *	@param C_Invoice_ID invoice
	 */
	public void setC_Invoice_ID (int C_Invoice_ID) 
	{
		m_C_Invoice_ID = C_Invoice_ID;
	}
	
}	//	BankStatementMatchInfo
