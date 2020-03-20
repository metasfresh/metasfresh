/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import org.compiere.impexp.BankStatementMatchInfo;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.MBankStatementMatcher;
import org.compiere.model.X_I_BankStatement;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

/**
 * Bank Statement Matching
 * 
 * @author Jorg Janke
 * @version $Id: BankStatementMatcher.java,v 1.3 2006/09/25 00:59:41 jjanke Exp $
 */
public class BankStatementMatcher extends JavaProcess
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	/** Matchers */
	MBankStatementMatcher[] m_matchers = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{

			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
		m_matchers = MBankStatementMatcher.getMatchers(getCtx(), get_TrxName());
	}	// prepare

	/**
	 * Perform process.
	 * 
	 * @return Message
	 * @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		final int Record_ID = getRecord_ID();
		if (m_matchers == null || m_matchers.length == 0)
		{
			throw new IllegalStateException("No Matchers found");
		}
		//
		log.info("doIt - Table_Name=" + tableName + ", Record_ID=" + Record_ID
				+ ", Matchers=" + m_matchers.length);

		if (I_I_BankStatement.Table_Name.equals(tableName))
		{
			return match(new X_I_BankStatement(getCtx(), Record_ID, get_TrxName()));
		}
		else if (I_C_BankStatement.Table_Name.equals(tableName))
		{
			final BankStatementId bankStatementId = BankStatementId.ofRepoId(Record_ID);
			final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
			return match(bankStatement);
		}
		else if (I_C_BankStatementLine.Table_Name.equals(tableName))
		{
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(Record_ID);
			final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);
			return match(bankStatementLine);
		}

		return "??";
	}	// doIt

	/**
	 * Perform Match
	 * 
	 * @param ibs import bank statement line
	 * @return Message
	 */
	private String match(X_I_BankStatement ibs)
	{
		if (m_matchers == null || ibs == null)
		{
			return "--";
		}

		log.debug("" + ibs);
		BankStatementMatchInfo info = null;
		for (MBankStatementMatcher m_matcher : m_matchers)
		{
			if (m_matcher.isMatcherValid())
			{
				info = m_matcher.getMatcher().findMatch(ibs);
				if (info != null && info.isMatched())
				{
					if (info.getC_Invoice_ID() > 0)
					{
						ibs.setC_Invoice_ID(info.getC_Invoice_ID());
					}
					if (info.getC_BPartner_ID() > 0)
					{
						ibs.setC_BPartner_ID(info.getC_BPartner_ID());
					}
					ibs.save();
					return "OK";
				}
			}
		}	// for all matchers
		return "--";
	}	// match

	/**
	 * Perform Match
	 * 
	 * @param bsl bank statement line
	 * @return Message
	 */
	private String match(I_C_BankStatementLine bsl)
	{
		if (m_matchers == null || bsl == null || bsl.getC_Payment_ID() != 0)
		{
			return "--";
		}

		log.debug("match - {}", bsl);
		BankStatementMatchInfo info = null;
		for (MBankStatementMatcher m_matcher : m_matchers)
		{
			if (m_matcher.isMatcherValid())
			{
				info = m_matcher.getMatcher().findMatch(bsl);
				if (info != null && info.isMatched())
				{
					if (info.getC_Payment_ID() > 0)
					{
						bsl.setC_Payment_ID(info.getC_Payment_ID());
					}
					if (info.getC_Invoice_ID() > 0)
					{
						bsl.setC_Invoice_ID(info.getC_Invoice_ID());
					}
					if (info.getC_BPartner_ID() > 0)
					{
						bsl.setC_BPartner_ID(info.getC_BPartner_ID());
					}

					bankStatementDAO.save(bsl);

					return "OK";
				}
			}
		}	// for all matchers
		return "--";
	}	// match

	/**
	 * Perform Match
	 * 
	 * @param bs bank statement
	 * @return Message
	 */
	private String match(I_C_BankStatement bs)
	{
		if (m_matchers == null || bs == null)
		{
			return "--";
		}

		int count = 0;
		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bs))
		{
			if (line.getC_Payment_ID() <= 0)
			{
				match(line);
				count++;
			}
		}
		return String.valueOf(count);
	}	// match

}	// BankStatementMatcher
