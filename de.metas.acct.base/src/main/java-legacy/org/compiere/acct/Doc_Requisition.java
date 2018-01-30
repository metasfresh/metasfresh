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
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MRequisition;

import de.metas.acct.api.ProductAcctType;

/**
 * Post Order Documents.
 * 
 * <pre>
 *   Table:              M_Requisition
 *   Document Types:     POR (Requisition)
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Requisition.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Requisition extends Doc<DocLine_Requisition>
{
	public Doc_Requisition(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_PurchaseRequisition);
	}	// Doc_Requisition

	@Override
	protected void loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		final I_M_Requisition req = getModel(I_M_Requisition.class);
		setDateDoc(req.getDateDoc());
		setDateAcct(req.getDateDoc());
		// Amounts
		setAmount(AMTTYPE_Gross, req.getTotalLines());
		setAmount(AMTTYPE_Net, req.getTotalLines());
		setDocLines(loadLines(req));
	}

	private List<DocLine_Requisition> loadLines(I_M_Requisition req)
	{
		final List<DocLine_Requisition> list = new ArrayList<>();
		final MRequisition reqPO = LegacyAdapters.convertToPO(req);
		for (final I_M_RequisitionLine line : reqPO.getLines())
		{
			DocLine_Requisition docLine = new DocLine_Requisition(line, this);
			list.add(docLine);
		}
		
		return list;
	}

	/***************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total -
	 * no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}	// getBalance

	/***************************************************************************
	 * Create Facts (the accounting logic) for POR.
	 * 
	 * <pre>
	 * Reservation
	 * 	Expense		CR
	 * 	Offset			DR
	 * </pre>
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<>();
		Fact fact = new Fact(this, as, Fact.POST_Reservation);
		setC_Currency_ID(as.getC_Currency_ID());
		
		//
		// Commitment
		if (as.isCreateReservation())
		{
			BigDecimal total = BigDecimal.ZERO;
			for (final DocLine_Requisition line : getDocLines())
			{
				BigDecimal cost = line.getAmtSource();
				total = total.add(cost);
				// Account
				MAccount expense = line.getAccount(ProductAcctType.Expense, as);
				//
				fact.createLine(line, expense, as.getC_Currency_ID(), cost, null);
			}
			// Offset
			MAccount offset = getAccount(ACCTTYPE_CommitmentOffset, as);
			if (offset == null)
			{
				throw newPostingException().setDetailMessage("@NotFound@ @CommitmentOffset_Acct@");
			}
			fact.createLine(null, offset, getC_Currency_ID(), null, total);
			facts.add(fact);
		}

		return facts;
	} // createFact
} // Doc_Requisition
