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
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

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
public class Doc_Requisition extends Doc
{
	/**
	 * Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Requisition (final IDocBuilder docBuilder)
	{
		super (docBuilder, DOCTYPE_PurchaseRequisition);
	}	//	Doc_Requisition

	/**
	 *	Load Specific Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails ()
	{
		setC_Currency_ID(NO_CURRENCY);
		MRequisition req = (MRequisition)getPO();
		setDateDoc (req.getDateDoc());
		setDateAcct (req.getDateDoc());
		// Amounts
		setAmount(AMTTYPE_Gross, req.getTotalLines());
		setAmount(AMTTYPE_Net, req.getTotalLines());
		// Contained Objects
		p_lines = loadLines (req);
		// log.debug( "Lines=" + p_lines.length + ", Taxes=" + m_taxes.length);
		return null;
	}	// loadDocumentDetails

	/**
	 *	Load Requisition Lines
	 *	@param req requisition
	 *	@return DocLine Array
	 */
	private DocLine[] loadLines (MRequisition req)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine> ();
		MRequisitionLine[] lines = req.getLines();
		for (int i = 0; i < lines.length; i++)
		{
			MRequisitionLine line = lines[i];
			DocLine docLine = new DocLine (line, this);
			BigDecimal Qty = line.getQty();
			docLine.setQty (Qty, false);
			BigDecimal PriceActual = line.getPriceActual();
			BigDecimal LineNetAmt = line.getLineNetAmt();
			docLine.setAmount (LineNetAmt);	 // DR
			list.add (docLine);
		}
		// Return Array
		DocLine[] dls = new DocLine[list.size ()];
		list.toArray (dls);
		return dls;
	}	// loadLines

	/***************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total -
	 * no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	public BigDecimal getBalance ()
	{
		BigDecimal retValue = new BigDecimal (0.0);
		return retValue;
	}	// getBalance

	/***************************************************************************
	 * Create Facts (the accounting logic) for POR.
	 * <pre>
	 * Reservation
	 * 	Expense		CR
	 * 	Offset			DR
	 * </pre>
	 * @param as accounting schema
	 * @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact (this, as, Fact.POST_Reservation);
		setC_Currency_ID(as.getC_Currency_ID());
		//
		BigDecimal grossAmt = getAmount (Doc.AMTTYPE_Gross);
		// Commitment
		if (as.isCreateReservation ())
		{
			BigDecimal total = Env.ZERO;
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLine line = p_lines[i];
				BigDecimal cost = line.getAmtSource();
				total = total.add (cost);
				// Account
				MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
				//
				fact.createLine (line, expense, as.getC_Currency_ID(), cost, null);
			}
			// Offset
			MAccount offset = getAccount (ACCTTYPE_CommitmentOffset, as);
			if (offset == null)
			{
				p_Error = "@NotFound@ @CommitmentOffset_Acct@";
				log.error(p_Error);
				return null;
			}
			fact.createLine (null, offset, getC_Currency_ID(), null, total);
			facts.add(fact);
		}
		
		return facts;
	} // createFact
} // Doc_Requisition
