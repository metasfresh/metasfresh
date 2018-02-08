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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;

/**
 * Match Invoice (Receipt<>Invoice) Model.
 * Accounting:
 * - Not Invoiced Receipts (relief)
 * - IPV
 *
 * @author Jorg Janke
 * @version $Id: MMatchInv.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1926113 ] MMatchInv.getNewerDateAcct() should work in trx
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 * @author Bayu Cahya, Sistematika
 *         <li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 *
 */
public class MMatchInv extends X_M_MatchInv
{
	private static final long serialVersionUID = 3668871839074170205L;

	public MMatchInv(final Properties ctx, final int M_MatchInv_ID, final String trxName)
	{
		super(ctx, M_MatchInv_ID, trxName);
		if (is_new())
		{
			// setDateTrx (new Timestamp(System.currentTimeMillis()));
			// setC_InvoiceLine_ID (0);
			// setM_InOutLine_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
			// setQty (Env.ZERO);
			setPosted(false);
			setProcessed(false);
			setProcessing(false);
		}
	}

	public MMatchInv(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Set Trx Date
		if (getDateTrx() == null)
		{
			setDateTrx(SystemTime.asDayTimestamp());
		}

		// Set Acct Date
		if (getDateAcct() == null)
		{
			Timestamp dateAcct = getNewerDateAcct();
			if (dateAcct == null)
			{
				dateAcct = getDateTrx();
			}
			setDateAcct(dateAcct);
		}

		if (getM_AttributeSetInstance_ID() <= 0 && getM_InOutLine_ID() > 0)
		{
			final I_M_InOutLine iol = getM_InOutLine();
			setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
		}

		return true;
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		// shall not happen (legacy)
		if (!success)
		{
			return false;
		}

		return success;
	}	// afterSave

	/**
	 * Get the later Date Acct from invoice or shipment
	 *
	 * @return date or null
	 */
	private Timestamp getNewerDateAcct()
	{
		String sql = "SELECT i.DateAcct "
				+ "FROM C_InvoiceLine il"
				+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
				+ "WHERE C_InvoiceLine_ID=?";
		final Timestamp invoiceDate = DB.getSQLValueTSEx(get_TrxName(), sql, getC_InvoiceLine_ID());
		//
		sql = "SELECT io.DateAcct "
				+ "FROM M_InOutLine iol"
				+ " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
				+ "WHERE iol.M_InOutLine_ID=?";
		final Timestamp shipDate = DB.getSQLValueTSEx(get_TrxName(), sql, getM_InOutLine_ID());
		//
		if (invoiceDate == null)
		{
			return shipDate;
		}
		if (shipDate == null)
		{
			return invoiceDate;
		}
		if (invoiceDate.after(shipDate))
		{
			return invoiceDate;
		}
		return shipDate;
	}	// getNewerDateAcct

	@Override
	protected boolean beforeDelete()
	{
		if (isPosted())
		{
			MPeriod.testPeriodOpen(getCtx(), getDateAcct(), X_C_DocType.DOCBASETYPE_MatchInvoice, getAD_Org_ID());

			setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocumentModel(this);
		}

		deleteMatchInvCostDetail();

		return true;
	}

	@Override
	protected boolean afterDelete(final boolean success)
	{
		if (!success)
		{
			return success;
		}

		clearInvoiceLineFromMatchPOs();
		return true;
	}

	private void clearInvoiceLineFromMatchPOs()
	{
		// Get Order and decrease invoices
		final I_C_InvoiceLine iLine = getC_InvoiceLine();
		int C_OrderLine_ID = iLine.getC_OrderLine_ID();
		if (C_OrderLine_ID <= 0)
		{
			final I_M_InOutLine ioLine = getM_InOutLine();
			C_OrderLine_ID = ioLine.getC_OrderLine_ID();
		}

		// No Order Found
		if (C_OrderLine_ID <= 0)
		{
			return;
		}

		// Find MatchPO
		for (final I_M_MatchPO matchPO : MMatchPO.get(getCtx(), C_OrderLine_ID, getC_InvoiceLine_ID(), get_TrxName()))
		{
			if (matchPO.getM_InOutLine_ID() <= 0)
			{
				matchPO.setProcessed(false);
				InterfaceWrapperHelper.delete(matchPO);
			}
			else
			{
				matchPO.setC_InvoiceLine(null);
				InterfaceWrapperHelper.save(matchPO);
			}
		}
	}

	private void deleteMatchInvCostDetail()
	{
		if (isSOTrx())
		{
			return; // task 08529: we extend the use of matchInv to also keep track of the SoTrx side. However, currently we don't need the accounting of that side to work
		}

		final ICostDetailService costDetailService = Adempiere.getBean(ICostDetailService.class);
		costDetailService.voidAndDeleteForDocument(CostingDocumentRef.ofMatchInvoiceId(getM_MatchInv_ID()));
	}
}	// MMatchInv
