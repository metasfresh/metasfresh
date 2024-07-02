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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import de.metas.banking.service.IBankStatementDAO;
import de.metas.util.Services;

@SuppressWarnings("serial")
public class MBankStatement extends X_C_BankStatement
{
	public MBankStatement(final Properties ctx, final int C_BankStatement_ID, final String trxName)
	{
		super(ctx, C_BankStatement_ID, trxName);
		if (is_new())
		{
			// setC_BP_BankAccount_ID (0); // parent
			setStatementDate(new Timestamp(System.currentTimeMillis()));	// @Date@
			setDocAction(DOCACTION_Complete);	// CO
			setDocStatus(DOCSTATUS_Drafted);	// DR
			setBeginningBalance(BigDecimal.ZERO);
			setStatementDifference(BigDecimal.ZERO);
			setEndingBalance(BigDecimal.ZERO);
			setIsApproved(false);	// N
			setIsManual(true);	// Y
			setPosted(false);	// N
			super.setProcessed(false);
		}
	}

	public MBankStatement(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);

		final BankStatementId bankStatementId = BankStatementId.ofRepoIdOrNull(getC_BankStatement_ID());
		if (bankStatementId != null)
		{
			final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
			bankStatementDAO.updateBankStatementLinesProcessedFlag(bankStatementId, processed);
		}
	}
}
