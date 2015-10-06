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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MDistribution;
import org.compiere.model.MDistributionLine;
import org.compiere.model.MElementValue;
import org.compiere.model.MFactAcct;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 *  Accounting Fact
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 *  
 *  BF [ 2789949 ] Multicurrency in matching posting
 */
public final class Fact
{
	/**
	 *	Constructor
	 *  @param  document    pointer to document
	 *  @param  acctSchema  Account Schema to create accounts
	 *  @param  defaultPostingType  the default Posting type (actual,..) for this posting
	 */
	public Fact (final Doc document, final MAcctSchema acctSchema, final String defaultPostingType)
	{
		m_doc = document;
		m_acctSchema = acctSchema;
		m_postingType = defaultPostingType;
		// Fix [ 1884676 ] Fact not setting transaction
		m_trxName = document.getTrxName();
		//
		log.config(toString());
	}	//	Fact


	/**	Log					*/
	private CLogger			log = CLogger.getCLogger(getClass());

	/** Document            */
	private Doc             m_doc = null;
	/** Accounting Schema   */
	private MAcctSchema	    m_acctSchema = null;
	/** Transaction			*/
	private String m_trxName = ITrx.TRXNAME_None;

	/** Posting Type        */
	private String		    m_postingType = null;

	/** Actual Balance Type */
	public static final String	POST_Actual = MFactAcct.POSTINGTYPE_Actual;
	/** Budget Balance Type */
	public static final String	POST_Budget = MFactAcct.POSTINGTYPE_Budget;
	/** Encumbrance Posting */
	public static final String	POST_Commitment = MFactAcct.POSTINGTYPE_Commitment;
	/** Encumbrance Posting */
	public static final String	POST_Reservation = MFactAcct.POSTINGTYPE_Reservation;


	/** Is Converted        */
	private boolean		    m_converted = false;

	/** Lines               */
	private List<FactLine>	m_lines = new ArrayList<>();


	/**
	 *  Dispose
	 */
	public final void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}   //  dispose

	/**
	 *	Create and convert Fact Line.
	 *  Used to create a DR and/or CR entry
	 *
	 *	@param  docLine     the document line or null
	 *  @param  account     if null, line is not created
	 *  @param  C_Currency_ID   the currency
	 *  @param  debitAmt    debit amount, can be null
	 *  @param  creditAmt  credit amount, can be null
	 *  @return Fact Line
	 */
	public FactLine createLine (final DocLine docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt)
	{
		final BigDecimal qty = null; // N/A
		return createLine(docLine, account, C_Currency_ID, debitAmt, creditAmt, qty);
	}
	
	/**
	 * Create and convert Fact Line. Used to create a DR and/or CR entry.
	 *
	 * @param docLine the document line or null
	 * @param account if null, line is not created
	 * @param C_Currency_ID the currency
	 * @param debitAmt debit amount, can be null
	 * @param creditAmt credit amount, can be null
	 * @param qty quantity, can be null and in that case the standard qty from DocLine/Doc will be used.
	 * @return Fact Line or null
	 */
	public FactLine createLine (final DocLine docLine,
			final MAccount account,
			final int C_Currency_ID,
			final BigDecimal debitAmt, final BigDecimal creditAmt,
			final BigDecimal qty)
	{
	//	log.fine("createLine - " + account	+ " - Dr=" + debitAmt + ", Cr=" + creditAmt);

		//  Data Check
		if (account == null)
		{
			log.info("No account for " + docLine 
				+ ": Amt=" + debitAmt + "/" + creditAmt 
				+ " - " + toString());			
			return null;
		}
		
		//
		final FactLine line = new FactLine(m_doc.getCtx(),
				m_doc.get_Table_ID(), // AD_Table_ID
				m_doc.get_ID(), // Record_ID
				docLine == null ? 0 : docLine.get_ID(), // Line_ID
				get_TrxName());
		//  Set Info & Account
		line.setDocumentInfo(m_doc, docLine);
		line.setPostingType(m_postingType);
		line.setAccount(m_acctSchema, account);
		
		//
		// Qty
		if (qty != null)
		{
			line.setQty(qty);
		}

		//
		//  Amounts - one needs to not zero
		line.setAmtSource(C_Currency_ID, debitAmt, creditAmt);
		if (line.isZeroAmtSource())
		{
			if (line.getQty().signum() == 0)
			{
				log.fine("Both amounts & qty = 0/Null - " + docLine + " - " + toString());			
				return null;
			}
			log.fine("Both amounts = 0/Null, Qty=" + (docLine == null ? "<NULL>" : docLine.getQty())
					+ " - docLine=" + (docLine == null ? "<NULL>" : docLine) + " - " + toString());
		}
		//  Convert
		line.convert();
		
		//  Optionally overwrite Acct Amount
		if (docLine != null && (docLine.getAmtAcctDr() != null || docLine.getAmtAcctCr() != null))
		{
			line.setAmtAcct(docLine.getAmtAcctDr(), docLine.getAmtAcctCr());
		}
		
		//
		log.log(Level.FINE, "{0}", line);
		add(line);
		return line;
	}	//	createLine

	/**
	 *  Add Fact Line
	 *  @param line fact line
	 */
	public void add (FactLine line)
	{
		m_lines.add(line);
	}   //  add

	/**
	 *  Remove Fact Line
	 *  @param line fact line
	 */
	public void remove (FactLine line)
	{
		m_lines.remove(line);
	}   //  remove

	/**
	 *	Create and convert Fact Line.
	 *  Used to create either a DR or CR entry
	 *
	 *	@param  docLine     Document Line or null
	 *  @param  accountDr   Account to be used if Amt is DR balance
	 *  @param  accountCr   Account to be used if Amt is CR balance
	 *  @param  C_Currency_ID Currency
	 *  @param  Amt if negative Cr else Dr
	 *  @return FactLine
	 */
	public FactLine createLine (DocLine docLine, MAccount accountDr, MAccount accountCr,
		int C_Currency_ID, BigDecimal Amt)
	{
		if (Amt.signum() < 0)
			return createLine (docLine, accountCr, C_Currency_ID, null, Amt.abs());
		else
			return createLine (docLine, accountDr, C_Currency_ID, Amt, null);
	}   //  createLine

	/**
	 *	Create and convert Fact Line.
	 *  Used to create either a DR or CR entry
	 *
	 *	@param  docLine Document line or null
	 *  @param  account   Account to be used
	 *  @param  C_Currency_ID Currency
	 *  @param  Amt if negative Cr else Dr
	 *  @return FactLine
	 */
	public FactLine createLine (DocLine docLine, MAccount account, int C_Currency_ID, BigDecimal Amt)
	{
		if (Amt.signum() < 0)
			return createLine (docLine, account, C_Currency_ID, null, Amt.abs());
		else
			return createLine (docLine, account, C_Currency_ID, Amt, null);
	}   //  createLine

	/**
	 *  Is Posting Type
	 *  @param  PostingType - see POST_*
	 *  @return true if document is posting type
	 */
	public boolean isPostingType (String PostingType)
	{
		return m_postingType.equals(PostingType);
	}   //  isPostingType

	/**
	 *	Is converted
	 *  @return true if converted
	 */
	public boolean isConverted()
	{
		return m_converted;
	}	//	isConverted

	/**
	 *	Get AcctSchema
	 *  @return AcctSchema
	 */
	public MAcctSchema getAcctSchema()
	{
		return m_acctSchema;
	}	//	getAcctSchema

	
	/**************************************************************************
	 *	Are the lines Source Balanced
	 *  @return true if source lines balanced
	 */
	public boolean isSourceBalanced()
	{
		//AZ Goodwill
		//  Multi-Currency documents are source balanced by definition
		//  No lines -> balanced
		if (m_lines.size() == 0 || m_doc.isMultiCurrency())
			return true;
		BigDecimal balance = getSourceBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
			log.finer(toString());
		else
			log.warning ("NO - Diff=" + balance + " - " + toString());
		return retValue;
	}	//	isSourceBalanced

	/**
	 *	Return Source Balance
	 *  @return source balance
	 */
	protected BigDecimal getSourceBalance()
	{
		BigDecimal result = Env.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			result = result.add (line.getSourceBalance());
		}
	//	log.fine("getSourceBalance - " + result.toString());
		return result;
	}	//	getSourceBalance

	/**
	 *	Create Source Line for Suspense Balancing.
	 *  Only if Suspense Balancing is enabled and not a multi-currency document
	 *  (double check as otherwise the rule should not have fired)
	 *  If not balanced create balancing entry in currency of the document
	 *  @return FactLine
	 */
	public FactLine balanceSource()
	{
		if (!m_acctSchema.isSuspenseBalancing() || m_doc.isMultiCurrency())
			return null;
		BigDecimal diff = getSourceBalance();
		log.finer("Diff=" + diff);

		//  new line
		FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
		line.setDocumentInfo(m_doc, null);
		line.setPostingType(m_postingType);

		//	Account
		line.setAccount(m_acctSchema, m_acctSchema.getSuspenseBalancing_Acct());

		//  Amount
		if (diff.signum() < 0)   //  negative balance => DR
			line.setAmtSource(m_doc.getC_Currency_ID(), diff.abs(), Env.ZERO);
		else                                //  positive balance => CR
			line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, diff);
			
		//  Convert
		line.convert();
		//
		log.fine(line.toString());
		m_lines.add(line);
		return line;
	}   //  balancingSource

	
	/**************************************************************************
	 *  Are all segments balanced
	 *  @return true if segments are balanced
	 */
	public boolean isSegmentBalanced()
	{
		//AZ Goodwill
		//  Multi-Currency documents are source balanced by definition
		//  No lines -> balanced
		if (m_lines.size() == 0 || m_doc.isMultiCurrency())
			return true;

		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		//  check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced() && !isSegmentBalanced (ase.getElementType()))
				return false;
		}
		return true;
	}   //  isSegmentBalanced

	/**
	 *  Is Source Segment balanced.
	 *  @param  segmentType - see AcctSchemaElement.SEGMENT_*
	 *  Implemented only for Org
	 *  Other sensible candidates are Project, User1/2
	 *  @return true if segments are balanced
	 */
	public boolean isSegmentBalanced (String segmentType)
	{
		if (segmentType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer,BigDecimal> map = new HashMap<Integer,BigDecimal>();
			//  Add up values by key
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = (FactLine)m_lines.get(i);
				Integer key = new Integer(line.getAD_Org_ID());
				BigDecimal bal = line.getSourceBalance();
				BigDecimal oldBal = (BigDecimal)map.get(key);
				if (oldBal != null)
					bal = bal.add(oldBal);
				map.put(key, bal);
			//	System.out.println("Add Key=" + key + ", Bal=" + bal + " <- " + line);
			}
			//  check if all keys are zero
			Iterator<BigDecimal> values = map.values().iterator();
			while (values.hasNext())
			{
				BigDecimal bal = values.next();
				if (bal.signum() != 0)
				{
					map.clear();
					log.warning ("(" + segmentType + ") NO - " + toString() + ", Balance=" + bal);
					return false;
				}
			}
			map.clear();
			log.finer("(" + segmentType + ") - " + toString());
			return true;
		}
		log.finer("(" + segmentType + ") (not checked) - " + toString());
		return true;
	}   //  isSegmentBalanced

	/**
	 *  Balance all segments.
	 *  - For all balancing segments
	 *      - For all segment values
	 *          - If balance <> 0 create dueTo/dueFrom line
	 *              overwriting the segment value
	 */
	public void balanceSegments()
	{
		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		//  check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced())
				balanceSegment (ase.getElementType());
		}
	}   //  balanceSegments

	/**
	 *  Balance Source Segment
	 *  @param elementType segment element type
	 */
	private void balanceSegment (String elementType)
	{
		//  no lines -> balanced
		if (m_lines.size() == 0)
			return;

		log.fine ("(" + elementType + ") - " + toString());

		//  Org
		if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer,Balance> map = new HashMap<Integer,Balance>();
			//  Add up values by key
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = (FactLine)m_lines.get(i);
				Integer key = new Integer(line.getAD_Org_ID());
			//	BigDecimal balance = line.getSourceBalance();
				Balance oldBalance = (Balance)map.get(key);
				if (oldBalance == null)
				{
					oldBalance = new Balance (line.getAmtSourceDr(), line.getAmtSourceCr());
					map.put(key, oldBalance);
				}
				else
					oldBalance.add(line.getAmtSourceDr(), line.getAmtSourceCr());
			//	log.info ("Key=" + key + ", Balance=" + balance + " - " + line);
			}

			//  Create entry for non-zero element
			Iterator<Integer> keys = map.keySet().iterator();
			while (keys.hasNext())
			{
				Integer key = keys.next();
				Balance difference = map.get(key);
				log.info (elementType + "=" + key + ", " + difference);
				//
				if (!difference.isZeroBalance())
				{
					//  Create Balancing Entry
					final FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
					line.setDocumentInfo(m_doc, null);
					line.setPostingType(m_postingType);
					//  Amount & Account
					if (difference.getBalance().signum() < 0)
					{
						if (difference.isReversal())
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, difference.getPostBalance());
						}
						else
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), Env.ZERO);
						}
					}
					else
					{
						if (difference.isReversal())
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), Env.ZERO);
						}
						else
						{
							line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
							line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, difference.getPostBalance());
						}
					}
					line.convert();
					line.setAD_Org_ID(key.intValue());
					//
					m_lines.add(line);
					log.fine("(" + elementType + ") - " + line);
				}
			}
			map.clear();
		}
	}   //  balanceSegment

	
	/**************************************************************************
	 *	Are the lines Accounting Balanced
	 *  @return true if accounting lines are balanced
	 */
	public boolean isAcctBalanced()
	{
		//  no lines -> balanced
		if (m_lines.size() == 0)
			return true;
		BigDecimal balance = getAcctBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue)
			log.finer(toString());
		else
			log.warning("NO - Diff=" + balance + " - " + toString());
		return retValue;
	}	//	isAcctBalanced

	/**
	 *	Return Accounting Balance
	 *  @return true if accounting lines are balanced
	 */
	protected BigDecimal getAcctBalance()
	{
		BigDecimal result = Env.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			result = result.add(line.getAcctBalance());
		}
	//	log.fine(result.toString());
		return result;
	}	//	getAcctBalance

	/**
	 *  Balance Accounting Currency.
	 *  If the accounting currency is not balanced,
	 *      if Currency balancing is enabled
	 *          create a new line using the currency balancing account with zero source balance
	 *      or
	 *          adjust the line with the largest balance sheet account
	 *          or if no balance sheet account exist, the line with the largest amount
	 *  @return FactLine
	 */
	public FactLine balanceAccounting()
	{
		BigDecimal diff = getAcctBalance();		//	DR-CR
		log.fine("Balance=" + diff 
			+ ", CurrBal=" + m_acctSchema.isCurrencyBalancing() 
			+ " - " + toString());
		FactLine line = null;

		BigDecimal BSamount = Env.ZERO;
		FactLine BSline = null;
		BigDecimal PLamount = Env.ZERO;
		FactLine PLline = null;

		//  Find line biggest BalanceSheet or P&L line
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine l = (FactLine)m_lines.get(i);
			BigDecimal amt = l.getAcctBalance().abs();
			if (l.isBalanceSheet() && amt.compareTo(BSamount) > 0)
			{
				BSamount = amt;
				BSline = l;
			}
			else if (!l.isBalanceSheet() && amt.compareTo(PLamount) > 0)
			{
				PLamount = amt;
				PLline = l;
			}
		}
		
		//  Create Currency Balancing Entry
		if (m_acctSchema.isCurrencyBalancing())
		{
			line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
			line.setDocumentInfo (m_doc, null);
			line.setPostingType (m_postingType);
			line.setAccount (m_acctSchema, m_acctSchema.getCurrencyBalancing_Acct());
			
			//  Amount
			line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, Env.ZERO);
			line.convert();
			//	Accounted
			BigDecimal drAmt = Env.ZERO;
			BigDecimal crAmt = Env.ZERO;
			boolean isDR = diff.signum() < 0;
			BigDecimal difference = diff.abs();
			if (isDR)
				drAmt = difference;
			else
				crAmt = difference;
			//	Switch sides
			boolean switchIt = BSline != null 
				&& ((BSline.isDrSourceBalance() && isDR)
					|| (!BSline.isDrSourceBalance() && !isDR));
			if (switchIt)
			{
				drAmt = Env.ZERO;
				crAmt = Env.ZERO;
				if (isDR)
					crAmt = difference.negate();
				else
					drAmt = difference.negate();
			}
			line.setAmtAcct(drAmt, crAmt);
			log.fine(line.toString());
			m_lines.add(line);
		}
		else	//  Adjust biggest (Balance Sheet) line amount
		{
			if (BSline != null)
				line = BSline;
			else
				line = PLline;
			if (line == null)
				log.severe ("No Line found");
			else
			{
				log.fine("Adjusting Amt=" + diff + "; Line=" + line);
				line.currencyCorrect(diff);
				log.fine(line.toString());
			}
		}   //  correct biggest amount

		return line;
	}   //  balanceAccounting

	/**
	 * 	Check Accounts of Fact Lines
	 *	@return true if success
	 */
	public boolean checkAccounts()
	{
		//  no lines -> nothing to distribute
		if (m_lines.size() == 0)
			return true;
		
		//	For all fact lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			MAccount account = line.getAccount();
			if (account == null)
			{
				log.warning("No Account for " + line);
				return false;
			}
			MElementValue ev = account.getAccount();
			if (ev == null)
			{
				log.warning("No Element Value for " + account 
					+ ": " + line);
				return false;
			}
			if (ev.isSummary())
			{
				log.warning("Cannot post to Summary Account " + ev 
					+ ": " + line);
				return false;
			}
			if (!ev.isActive())
			{
				log.warning("Cannot post to Inactive Account " + ev 
					+ ": " + line);
				return false;
			}

		}	//	for all lines
		
		return true;
	}	//	checkAccounts
	
	/**
	 * 	GL Distribution of Fact Lines
	 *	@return true if success
	 */
	public boolean distribute()
	{
		//  no lines -> nothing to distribute
		if (m_lines.size() == 0)
			return true;
		
		ArrayList<FactLine> newLines = new ArrayList<FactLine>();
		//	For all fact lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine dLine = (FactLine)m_lines.get(i);
			MDistribution[] distributions = MDistribution.get (dLine.getAccount(), 
				m_postingType, m_doc.getC_DocType_ID());
			//	No Distribution for this line
			//AZ Goodwill
			//The above "get" only work in GL Journal because it's using ValidCombination Account
			//Old:
			//if (distributions == null || distributions.length == 0)
			//	continue;
			//For other document, we try the followings (from FactLine):
			//New:	
			if (distributions == null || distributions.length == 0)
			{
				distributions = MDistribution.get (dLine.getCtx(), dLine.getC_AcctSchema_ID(),
					m_postingType, m_doc.getC_DocType_ID(),
					dLine.getAD_Org_ID(), dLine.getAccount_ID(),
					dLine.getM_Product_ID(), dLine.getC_BPartner_ID(), dLine.getC_Project_ID(),
					dLine.getC_Campaign_ID(), dLine.getC_Activity_ID(), dLine.getAD_OrgTrx_ID(),
					dLine.getC_SalesRegion_ID(), dLine.getC_LocTo_ID(), dLine.getC_LocFrom_ID(),
					dLine.getUser1_ID(), dLine.getUser2_ID());
				if (distributions == null || distributions.length == 0)
					continue;
			}
			//end AZ
			//	Just the first
			if (distributions.length > 1)
				log.warning("More then one Distributiion for " + dLine.getAccount());
			MDistribution distribution = distributions[0]; 

			// FR 2685367 - GL Distribution delete line instead reverse
			if (distribution.isCreateReversal()) {
				//	Add Reversal
				FactLine reversal = dLine.reverse(distribution.getName());
				log.info("Reversal=" + reversal);
				newLines.add(reversal);		//	saved in postCommit
			} else {
				// delete the line being distributed
				m_lines.remove(i);    // or it could be m_lines.remove(dLine);
				i--;
			}

			//	Prepare
			distribution.distribute(dLine.getAccount(), dLine.getSourceBalance(), dLine.getQty(), dLine.getC_Currency_ID());
			MDistributionLine[] lines = distribution.getLines(false);
			for (int j = 0; j < lines.length; j++)
			{
				MDistributionLine dl = lines[j];
				if (!dl.isActive() || dl.getAmt().signum() == 0)
					continue;
				final FactLine factLine = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), m_doc.get_ID(), 0, get_TrxName());
				//  Set Info & Account
				factLine.setDocumentInfo(m_doc, dLine.getDocLine());
				factLine.setAccount(m_acctSchema, dl.getAccount());
				factLine.setPostingType(m_postingType);
				if (dl.isOverwriteOrg())	//	set Org explicitly
					factLine.setAD_Org_ID(dl.getOrg_ID());
				//
				if (dl.getAmt().signum() < 0)
					factLine.setAmtSource(dLine.getC_Currency_ID(), null, dl.getAmt().abs()); 
				else
					factLine.setAmtSource(dLine.getC_Currency_ID(), dl.getAmt(), null);
				factLine.setQty(dl.getQty());
				//  Convert
				factLine.convert();
				//
				String description = distribution.getName() + " #" + dl.getLine();
				if (dl.getDescription() != null)
					description += " - " + dl.getDescription();
				factLine.addDescription(description);
				//
				log.info(factLine.toString());
				newLines.add(factLine);
			}
		}	//	for all lines
		
		//	Add Lines
		for (int i = 0; i < newLines.size(); i++)
			m_lines.add(newLines.get(i));
		
		return true;
	}	//	distribute	
	
	/**************************************************************************
	 * String representation
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Fact[");
		sb.append(m_doc.toString());
		sb.append(",").append(m_acctSchema.toString());
		sb.append(",PostType=").append(m_postingType);
		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 *	Get Lines
	 *  @return FactLine Array
	 */
	public FactLine[] getLines()
	{
		FactLine[] temp = new FactLine[m_lines.size()];
		m_lines.toArray(temp);
		return temp;
	}	//	getLines

	/**
	 *  Save Fact
	 *  @param trxName transaction
	 *  @return true if all lines were saved
	 */
	public final void save (final String trxName)
	{
		m_trxName = trxName;
		//  save Lines
		for (FactLine fl : m_lines)
		{
			InterfaceWrapperHelper.save(fl, trxName);
		}
	}   //  commit

	/**
	 * 	Get Transaction
	 *	@return trx
	 */
	public final String get_TrxName() 
	{
		return m_trxName;
	}	//	getTrxName

	/**
	 * 	Fact Balance Utility
	 *	
	 *  @author Jorg Janke
	 *  @version $Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
	 */
	private static final class Balance
	{
		/**
		 * 	New Balance
		 *	@param dr DR
		 *	@param cr CR
		 */
		public Balance (BigDecimal dr, BigDecimal cr)
		{
			DR = dr;
			CR = cr;
		}
		
		/** DR Amount	*/
		public BigDecimal DR = Env.ZERO;
		/** CR Amount	*/
		public BigDecimal CR = Env.ZERO;
		
		/**
		 * 	Add 
		 *	@param dr DR
		 *	@param cr CR
		 */
		public void add (BigDecimal dr, BigDecimal cr)
		{
			DR = DR.add(dr);
			CR = CR.add(cr);
		}
		
		/**
		 * 	Get Balance
		 *	@return balance
		 */
		public BigDecimal getBalance()
		{
			return DR.subtract(CR);
		}	//	getBalance
		
		/**
		 * 	Get Post Balance
		 *	@return absolute balance - negative if reversal
		 */
		public BigDecimal getPostBalance()
		{
			BigDecimal bd = getBalance().abs();
			if (isReversal())
				return bd.negate();
			return bd;
		}	//	getPostBalance

		/**
		 * 	Zero Balance
		 *	@return true if 0
		 */
		public boolean isZeroBalance()
		{
			return getBalance().signum() == 0;
		}	//	isZeroBalance
		
		/**
		 * 	Reversal
		 *	@return true if both DR/CR are negative or zero
		 */
		public boolean isReversal()
		{
			return DR.signum() <= 0 && CR.signum() <= 0;
		}	//	isReversal
		
		/**
		 * 	String Representation
		 *	@return info
		 */
		@Override
		public String toString ()
		{
			StringBuilder sb = new StringBuilder ("Balance[");
			sb.append ("DR=").append(DR)
				.append ("-CR=").append(CR)
				.append(" = ").append(getBalance())
				.append ("]");
			return sb.toString ();
		} //	toString
		
	}	//	Balance
	
}   //  Fact