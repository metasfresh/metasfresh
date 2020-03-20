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
 * Contributor(s): Teo Sarca *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;

/**
 * Bank Statement Line Model
 *
 * @author Eldir Tomassen/Jorg Janke
 * @version $Id: MBankStatementLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 
 *          Carlos Ruiz - globalqss - integrate bug fixing from Teo Sarca
 *          [ 1619076 ] Bank statement's StatementDifference becames NULL
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1896880 ] Unlink Payment if TrxAmt is zero
 *         <li>BF [ 1896885 ] BS Line: don't update header if after save/delete fails
 */
@SuppressWarnings("serial")
public class MBankStatementLine extends X_C_BankStatementLine
{
	public MBankStatementLine(Properties ctx, int C_BankStatementLine_ID, String trxName)
	{
		super(ctx, C_BankStatementLine_ID, trxName);
		if (is_new())
		{
			// setC_BankStatement_ID (0); // Parent
			// setC_Charge_ID (0);
			// setC_Currency_ID (0); // Bank Acct Currency
			// setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_BankStatementLine WHERE C_BankStatement_ID=@C_BankStatement_ID@
			setStmtAmt(BigDecimal.ZERO);
			setTrxAmt(BigDecimal.ZERO);
			setInterestAmt(BigDecimal.ZERO);
			setChargeAmt(BigDecimal.ZERO);
			setIsReversal(false);
			// setValutaDate (new Timestamp(System.currentTimeMillis())); // @StatementDate@
			// setDateAcct (new Timestamp(System.currentTimeMillis())); // @StatementDate@
		}
	}	// MBankStatementLine

	public MBankStatementLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Set Statement Line Date and all other dates (Valuta, Acct)
	 * 
	 * @param StatementLineDate date
	 */
	@Override
	public void setStatementLineDate(final Timestamp StatementLineDate)
	{
		super.setStatementLineDate(StatementLineDate);
		setValutaDate(StatementLineDate);
		setDateAcct(StatementLineDate);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord)
		{
			final BankStatementId bankStatementId = BankStatementId.ofRepoId(getC_BankStatement_ID());
			final I_C_BankStatement bankStatement = Services.get(IBankStatementDAO.class).getById(bankStatementId);
			final DocStatus docStatus = DocStatus.ofNullableCode(bankStatement.getDocStatus());
			if (docStatus.isCompletedOrClosedOrReversed())
			{
				throw new AdempiereException("@ParentComplete@ @C_BankStatementLine_ID@");
			}
		}
		if (getChargeAmt().signum() != 0 && getC_Charge_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_C_Charge_ID);
		}
		// Un-link Payment if TrxAmt is zero - teo_sarca BF [ 1896880 ]
		if (getTrxAmt().signum() == 0 && getC_Payment_ID() > 0)
		{
			setC_Payment_ID(I_ZERO);
			setC_Invoice_ID(I_ZERO);
		}
		// Set Line No
		if (getLine() == 0)
		{
			final String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_BankStatementLine WHERE C_BankStatement_ID=?";
			int ii = DB.getSQLValue(get_TrxName(), sql, getC_BankStatement_ID());
			setLine(ii);
		}

		// Set References
		if (getC_Payment_ID() != 0 && getC_BPartner_ID() == 0)
		{
			MPayment payment = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			setC_BPartner_ID(payment.getC_BPartner_ID());
			if (payment.getC_Invoice_ID() != 0)
			{
				setC_Invoice_ID(payment.getC_Invoice_ID());
			}
		}
		if (getC_Invoice_ID() != 0 && getC_BPartner_ID() == 0)
		{
			MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			setC_BPartner_ID(invoice.getC_BPartner_ID());
		}

		setChargeAmt(computeChargetAmt(this));

		return true;
	}

	private static BigDecimal computeChargetAmt(final I_C_BankStatementLine line)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

		return line.getStmtAmt()
				.subtract(bankStatementBL.computeStmtAmtExcludingChargeAmt(line));
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return false;
		}

		updateHeader();
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
		{
			return false;
		}

		updateHeader();
		return true;
	}

	private void updateHeader()
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(getC_BankStatement_ID());

		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET StatementDifference=(SELECT COALESCE(SUM(StmtAmt),0) FROM C_BankStatementLine bsl "
					+ "WHERE bsl.C_BankStatement_ID=bs.C_BankStatement_ID AND bsl.IsActive='Y') "
					+ "WHERE C_BankStatement_ID=?";
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
		}

		{
			final String sql = "UPDATE C_BankStatement bs"
					+ " SET EndingBalance=BeginningBalance+StatementDifference "
					+ "WHERE C_BankStatement_ID=?";
			DB.executeUpdateEx(sql, new Object[] { bankStatementId }, ITrx.TRXNAME_ThreadInherited);
		}
	}
}
