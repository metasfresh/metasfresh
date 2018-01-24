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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_OrderTax;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MTax;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.tax.api.ITaxBL;

/**
 * Post Order Documents.
 * 
 * <pre>
 *  Table:              C_Order (259)
 *  Document Types:     SOO, POO
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Order.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Order extends Doc
{
	private static final Logger logger = LogManager.getLogger(Doc_Order.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);

	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_Order(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}	// Doc_Order

	/** Contained Optional Tax Lines */
	private List<DocTax> _taxes = null;
	/** Requisitions */
	private List<DocLine> _requisitionDocLines = null;

	/**
	 * Load Specific Document Details
	 * 
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		final I_C_Order order = getModel(I_C_Order.class);
		setDateDoc(order.getDateOrdered());
		// Amounts
		setAmount(AMTTYPE_Gross, order.getGrandTotal());
		setAmount(AMTTYPE_Net, order.getTotalLines());
		setAmount(AMTTYPE_Charge, order.getChargeAmt());

		// Contained Objects
		p_lines = loadLines(order);
		return null;
	}   // loadDocumentDetails

	private DocLine[] loadLines(final I_C_Order order)
	{
		final List<DocLine> docLines = new ArrayList<>();
		for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLines(order))
		{
			final DocLine docLine = new DocLine(InterfaceWrapperHelper.getPO(orderLine), this);
			docLine.setIsTaxIncluded(orderLineBL.isTaxIncluded(orderLine));
			final BigDecimal qty = orderLine.getQtyOrdered();
			docLine.setQty(qty, order.isSOTrx());

			//
			BigDecimal PriceCost = null;
			if (getDocumentType().equals(DOCTYPE_POrder))  	// PO
			{
				PriceCost = orderLine.getPriceCost();
			}

			BigDecimal lineNetAmt = null;
			if (PriceCost != null && PriceCost.signum() != 0)
			{
				lineNetAmt = qty.multiply(PriceCost);
			}
			else
			{
				lineNetAmt = orderLine.getLineNetAmt();
			}
			docLine.setAmount(lineNetAmt);	// DR

			BigDecimal priceList = orderLine.getPriceList();

			// Correct included Tax
			final int C_Tax_ID = docLine.getC_Tax_ID();
			if (docLine.isTaxIncluded() && C_Tax_ID > 0)
			{
				final MTax tax = MTax.get(getCtx(), C_Tax_ID);
				if (!tax.isZeroTax())
				{
					final BigDecimal lineNetAmtTax = taxBL.calculateTax(tax, lineNetAmt, true, getStdPrecision());
					lineNetAmt = lineNetAmt.subtract(lineNetAmtTax);
					for (final DocTax docTax : getDocTaxes())
					{
						if (docTax.getC_Tax_ID() == C_Tax_ID)
						{
							docTax.addIncludedTax(lineNetAmtTax);
							break;
						}
					}

					final BigDecimal priceListTax = taxBL.calculateTax(tax, priceList, true, getStdPrecision());
					priceList = priceList.subtract(priceListTax);
				}
			}  	// correct included Tax

			docLine.setAmount(lineNetAmt, priceList, qty);
			docLines.add(docLine);
		}

		// Return Array
		return docLines.toArray(new DocLine[docLines.size()]);
	}	// loadLines

	private List<DocLine> getRequisitionDocLines()
	{
		if (_requisitionDocLines == null)
		{
			_requisitionDocLines = loadRequisitions();
		}
		return _requisitionDocLines;
	}

	/**
	 * Load Requisitions
	 * 
	 * @return requisition lines of Order
	 */
	private List<DocLine> loadRequisitions()
	{
		I_C_Order order = getModel(I_C_Order.class);
		final Map<Integer, BigDecimal> qtys = new HashMap<>();
		for (I_C_OrderLine line : orderDAO.retrieveOrderLines(order))
		{
			qtys.put(line.getC_OrderLine_ID(), line.getQtyOrdered());
		}

		//
		final String sql = "SELECT * FROM M_RequisitionLine rl "
				+ "WHERE EXISTS (SELECT * FROM C_Order o "
				+ " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID) "
				+ "WHERE ol.C_OrderLine_ID=rl.C_OrderLine_ID"
				+ " AND o.C_Order_ID=?) "
				+ "ORDER BY rl.C_OrderLine_ID";
		final Object[] sqlParams = new Object[] { order.getC_Order_ID() };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final List<DocLine> list = new ArrayList<>();
			while (rs.next())
			{
				final MRequisitionLine line = new MRequisitionLine(getCtx(), rs, null);
				final DocLine docLine = new DocLine(line, this);
				// Quantity - not more then OrderLine
				// Issue: Split of Requisition to multiple POs & different price
				BigDecimal maxQty = qtys.get(line.getC_OrderLine_ID());
				BigDecimal Qty = line.getQty().max(maxQty);
				if (Qty.signum() == 0)
					continue;
				docLine.setQty(Qty, false);
				qtys.put(line.getC_OrderLine_ID(), maxQty.subtract(Qty));
				//
				BigDecimal PriceActual = line.getPriceActual();
				BigDecimal LineNetAmt = line.getLineNetAmt();
				if (line.getQty().compareTo(Qty) != 0)
					LineNetAmt = PriceActual.multiply(Qty);
				docLine.setAmount(LineNetAmt);	 // DR
				list.add(docLine);
			}

			return list;
		}
		catch (Exception e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	private List<DocTax> getDocTaxes()
	{
		if(_taxes == null)
		{
			_taxes = loadTaxes();
		}
		return _taxes;
	}

	/**
	 * Load Invoice Taxes
	 * 
	 * @return DocTax Array
	 */
	private List<DocTax> loadTaxes()
	{
		final String sql = "SELECT it.C_Tax_ID, t.Name, t.Rate, it.TaxBaseAmt, it.TaxAmt, t.IsSalesTax " // 1..6
				+ ", it." + I_C_OrderTax.COLUMNNAME_IsTaxIncluded
				+ " FROM C_Tax t, C_OrderTax it "
				+ " WHERE t.C_Tax_ID=it.C_Tax_ID AND it.C_Order_ID=?";
		final Object[] sqlParams = new Object[] { get_ID() };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<DocTax> list = ImmutableList.builder();
			while (rs.next())
			{
				int C_Tax_ID = rs.getInt(1);
				String name = rs.getString(2);
				BigDecimal rate = rs.getBigDecimal(3);
				BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				BigDecimal amount = rs.getBigDecimal(5);
				boolean salesTax = DisplayType.toBoolean(rs.getString(6));
				final boolean taxIncluded = DisplayType.toBoolean(rs.getString(7));
				//
				final DocTax taxLine = new DocTax(getCtx(),
						C_Tax_ID, name, rate,
						taxBaseAmt, amount, salesTax, taxIncluded);
				list.add(taxLine);
			}

			return list.build();
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}	// loadTaxes

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 * 
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		// In case of purchase orders, sum of Cost(vs. Price) in lines may not add up, so we are returning ZERO
		if (DOCTYPE_POrder.equals(getDocumentType()))
		{
			return BigDecimal.ZERO;
		}

		BigDecimal balance = BigDecimal.ZERO;

		// Total
		balance = balance.add(getAmount(Doc.AMTTYPE_Gross));

		// - Header Charge
		balance = balance.subtract(getAmount(Doc.AMTTYPE_Charge));

		// - Tax
		for (final DocTax docTax : getDocTaxes())
		{
			balance = balance.subtract(docTax.getTaxAmt());
		}

		// - Lines
		for (final DocLine docLine : p_lines)
		{
			balance = balance.subtract(docLine.getAmtSource());
		}

		return balance;
	}   // getBalance

	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		final String docBaseType = getDocumentType();
		if (DOCTYPE_POrder.equals(docBaseType))
		{
			return createFacts_PurchaseOrder(as);
		}
		else if (DOCTYPE_SOrder.equals(docBaseType))
		{
			return createFacts_SalesOrder(as);
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown @DocBaseType@: " + docBaseType);
		}
	}   // createFact

	private List<Fact> createFacts_PurchaseOrder(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		if (as.isCreatePOCommitment())
		{
			facts.addAll(createFacts_PurchaseOrder_Commitment(as));
		}
		if (as.isCreateReservation())
		{
			facts.addAll(createFacts_PurchaseOrder_Reservation(as));
		}

		return facts;
	}

	/**
	 * Purchase Order Commitment (to be released by Invoice Matching).
	 * PostingType: Commitment
	 * 
	 * <pre>
	 * 		Product Expense     DR
	 * 		CommitmentOffset            CR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseOrder_Commitment(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Commitment);
		facts.add(fact);

		BigDecimal total = BigDecimal.ZERO;
		for (final DocLine line : p_lines)
		{
			final BigDecimal cost = line.getAmtSource();
			total = total.add(cost);

			// DR Account
			final MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
			fact.createLine(line, expense, getC_Currency_ID(), cost, null);
		}

		// CR Offset
		final MAccount offset = getAccount(ACCTTYPE_CommitmentOffset, as);
		if (offset == null)
		{
			throw newPostingException().setDetailMessage("@NotFound@ @CommitmentOffset_Acct@");
		}
		fact.createLine(null, offset, getC_Currency_ID(), null, total);

		return facts;
	}

	/**
	 * Purchase Order Reservation (release).
	 * PostingType: Reservation
	 * 
	 * <pre>
	 * 		Product Expense          CR
	 * 		Commitment Offset   DR
	 * </pre>
	 */
	private List<Fact> createFacts_PurchaseOrder_Reservation(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Reservation);
		facts.add(fact);

		BigDecimal total = BigDecimal.ZERO;
		final List<DocLine> requisitionDocLines = getRequisitionDocLines();
		for (final DocLine line : requisitionDocLines)
		{
			BigDecimal cost = line.getAmtSource();
			total = total.add(cost);

			// Account
			MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
			fact.createLine(line, expense, getC_Currency_ID(), null, cost);
		}

		// Offset
		if (!requisitionDocLines.isEmpty())
		{
			MAccount offset = getAccount(ACCTTYPE_CommitmentOffset, as);
			if (offset == null)
			{
				throw newPostingException().setDetailMessage("@NotFound@ @CommitmentOffset_Acct@");
			}
			fact.createLine(null, offset, getC_Currency_ID(), total, null);
		}

		return facts;
	}

	private List<Fact> createFacts_SalesOrder(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();

		// Commitment
		if (as.isCreateSOCommitment())
		{
			facts.addAll(createFacts_SalesOrder_Commitment(as));
		}

		return facts;
	}

	/**
	 * Sales Order Commitment
	 * PostingType: Commitment
	 * 
	 * <pre>
	 * 		Product Revenue              CR
	 * 		CommitmentOffsetSales   DR
	 * </pre>
	 */
	private List<Fact> createFacts_SalesOrder_Commitment(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();
		final Fact fact = new Fact(this, as, Fact.POST_Commitment);
		facts.add(fact);

		BigDecimal total = BigDecimal.ZERO;
		for (DocLine line : p_lines)
		{
			final BigDecimal cost = line.getAmtSource();
			total = total.add(cost);

			// Account
			final MAccount revenue = line.getAccount(ProductCost.ACCTTYPE_P_Revenue, as);
			fact.createLine(line, revenue, getC_Currency_ID(), null, cost);
		}

		// Offset
		final MAccount offset = getAccount(ACCTTYPE_CommitmentOffsetSales, as);
		if (offset == null)
		{
			throw newPostingException().setDetailMessage("@NotFound@ @CommitmentOffsetSales_Acct@");
		}
		fact.createLine(null, offset, getC_Currency_ID(), total, null);

		return facts;
	}

	/**
	 * Get Commitments
	 * 
	 * @param doc document
	 * @param maxQty Qty invoiced/matched
	 * @param C_InvoiceLine_ID invoice line
	 * @return commitments (order lines)
	 */
	private static DocLine[] retrieveCommitmentPurchaseReleaseDocLines(final Doc doc, BigDecimal maxQty, final int C_InvoiceLine_ID)
	{
		int precision = -1;
		//
		ArrayList<DocLine> list = new ArrayList<>();
		final String sql = "SELECT * FROM C_OrderLine ol "
				+ "WHERE EXISTS (SELECT * FROM C_InvoiceLine il WHERE il.C_OrderLine_ID=ol.C_OrderLine_ID AND il.C_InvoiceLine_ID=?)"
				+ " OR EXISTS (SELECT * FROM M_MatchPO po WHERE po.C_OrderLine_ID=ol.C_OrderLine_ID AND po.C_InvoiceLine_ID=?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_InvoiceLine_ID);
			pstmt.setInt(2, C_InvoiceLine_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (maxQty.signum() == 0)
					continue;
				MOrderLine line = new MOrderLine(doc.getCtx(), rs, null);
				DocLine docLine = new DocLine(line, doc);
				// Currency
				if (precision == -1)
				{
					doc.setC_Currency_ID(docLine.getC_Currency_ID());
					precision = Services.get(ICurrencyDAO.class).getStdPrecision(doc.getCtx(), docLine.getC_Currency_ID());
				}
				// Qty
				BigDecimal Qty = line.getQtyOrdered().max(maxQty);
				docLine.setQty(Qty, false);
				//
				BigDecimal PriceActual = line.getPriceActual();
				BigDecimal PriceCost = line.getPriceCost();
				BigDecimal LineNetAmt = null;
				if (PriceCost != null && PriceCost.signum() != 0)
					LineNetAmt = Qty.multiply(PriceCost);
				else if (Qty.equals(maxQty))
					LineNetAmt = line.getLineNetAmt();
				else
					LineNetAmt = Qty.multiply(PriceActual);
				maxQty = maxQty.subtract(Qty);

				docLine.setAmount(LineNetAmt);	// DR
				BigDecimal PriceList = line.getPriceList();
				int C_Tax_ID = docLine.getC_Tax_ID();
				// Correct included Tax
				if (C_Tax_ID > 0 && Services.get(IOrderLineBL.class).isTaxIncluded(line))
				{
					MTax tax = MTax.get(doc.getCtx(), C_Tax_ID);
					if (!tax.isZeroTax())
					{
						BigDecimal LineNetAmtTax = tax.calculateTax(LineNetAmt, true, precision);
						logger.debug("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
						LineNetAmt = LineNetAmt.subtract(LineNetAmtTax);
						BigDecimal PriceListTax = tax.calculateTax(PriceList, true, precision);
						PriceList = PriceList.subtract(PriceListTax);
					}
				}  	// correct included Tax

				docLine.setAmount(LineNetAmt, PriceList, Qty);
				list.add(docLine);
			}
		}
		catch (Exception e)
		{
			logger.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Return Array
		DocLine[] dl = new DocLine[list.size()];
		list.toArray(dl);
		return dl;
	}	// getCommitments

	/**
	 * Get Commitment Release.
	 * Called from MatchInv for accrual and Allocation for Cash Based
	 * 
	 * @param as accounting schema
	 * @param doc doc
	 * @param qty qty invoiced/matched
	 * @param invoiceLineId line
	 * @param multiplier 1 for accrual
	 * @return Fact
	 */
	static Fact createFact_CommitmentPurchaseRelease(final MAcctSchema as, final Doc doc, final BigDecimal qty, final int invoiceLineId, final BigDecimal multiplier)
	{
		final Fact fact = new Fact(doc, as, Fact.POST_Commitment);

		BigDecimal total = BigDecimal.ZERO;
		int currencyId = -1;
		for (final DocLine line : retrieveCommitmentPurchaseReleaseDocLines(doc, qty, invoiceLineId))
		{
			if (currencyId == -1)
			{
				currencyId = line.getC_Currency_ID();
			}
			else if (currencyId != line.getC_Currency_ID())
			{
				throw doc.newPostingException().setDetailMessage("Different Currencies of Order Lines");
			}
			final BigDecimal cost = line.getAmtSource().multiply(multiplier);
			total = total.add(cost);

			// Account
			final MAccount expense = line.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
			fact.createLine(line, expense, currencyId, null, cost);
		}

		// Offset
		final MAccount offset = doc.getAccount(ACCTTYPE_CommitmentOffset, as);
		if (offset == null)
		{
			throw doc.newPostingException().setDetailMessage("@NotFound@ @CommitmentOffset_Acct@");
		}
		fact.createLine(null, offset, currencyId, total, null);

		return fact;
	}	// getCommitmentRelease

	/**
	 * Get Commitments Sales
	 * 
	 * @param doc document
	 * @param maxQty Qty invoiced/matched
	 * @param C_OrderLine_ID invoice line
	 * @return commitments (order lines)
	 */
	private static DocLine[] retrieveCommitmentsSalesReleaseDocLines(final Doc doc, BigDecimal maxQty, final int M_InOutLine_ID)
	{
		int precision = -1;
		//
		ArrayList<DocLine> list = new ArrayList<>();
		String sql = "SELECT * FROM C_OrderLine ol "
				+ "WHERE EXISTS (SELECT 1 FROM M_InOutLine il WHERE il.C_OrderLine_ID=ol.C_OrderLine_ID AND il.M_InOutLine_ID=?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_InOutLine_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (maxQty.signum() == 0)
					continue;
				MOrderLine line = new MOrderLine(doc.getCtx(), rs, null);
				DocLine docLine = new DocLine(line, doc);
				// Currency
				if (precision == -1)
				{
					doc.setC_Currency_ID(docLine.getC_Currency_ID());
					precision = Services.get(ICurrencyDAO.class).getStdPrecision(doc.getCtx(), docLine.getC_Currency_ID());
				}
				// Qty
				BigDecimal Qty = line.getQtyOrdered().max(maxQty);
				docLine.setQty(Qty, false);
				//
				BigDecimal PriceActual = line.getPriceActual();
				BigDecimal PriceCost = line.getPriceCost();
				BigDecimal LineNetAmt = null;
				if (PriceCost != null && PriceCost.signum() != 0)
					LineNetAmt = Qty.multiply(PriceCost);
				else if (Qty.equals(maxQty))
					LineNetAmt = line.getLineNetAmt();
				else
					LineNetAmt = Qty.multiply(PriceActual);
				maxQty = maxQty.subtract(Qty);

				docLine.setAmount(LineNetAmt);	// DR
				BigDecimal PriceList = line.getPriceList();
				int C_Tax_ID = docLine.getC_Tax_ID();
				// Correct included Tax
				if (C_Tax_ID > 0 && Services.get(IOrderLineBL.class).isTaxIncluded(line))
				{
					MTax tax = MTax.get(doc.getCtx(), C_Tax_ID);
					if (!tax.isZeroTax())
					{
						BigDecimal LineNetAmtTax = tax.calculateTax(LineNetAmt, true, precision);
						logger.debug("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
						LineNetAmt = LineNetAmt.subtract(LineNetAmtTax);
						BigDecimal PriceListTax = tax.calculateTax(PriceList, true, precision);
						PriceList = PriceList.subtract(PriceListTax);
					}
				}  	// correct included Tax

				docLine.setAmount(LineNetAmt, PriceList, Qty);
				list.add(docLine);
			}
		}
		catch (Exception e)
		{
			logger.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Return Array
		DocLine[] dl = new DocLine[list.size()];
		list.toArray(dl);
		return dl;
	}	// getCommitmentsSales

	/**
	 * Get Commitment Sales Release.
	 * Called from InOut
	 * 
	 * @param as accounting schema
	 * @param doc doc
	 * @param Qty qty invoiced/matched
	 * @param C_OrderLine_ID line
	 * @param multiplier 1 for accrual
	 * @return Fact
	 */
	static Fact createFact_CommitmentSalesRelease(final MAcctSchema as, final Doc doc, final BigDecimal Qty, final int M_InOutLine_ID, final BigDecimal multiplier)
	{
		final Fact fact = new Fact(doc, as, Fact.POST_Commitment);

		BigDecimal total = BigDecimal.ZERO;
		int C_Currency_ID = -1;
		for (DocLine line : retrieveCommitmentsSalesReleaseDocLines(doc, Qty, M_InOutLine_ID))
		{
			if (C_Currency_ID == -1)
				C_Currency_ID = line.getC_Currency_ID();
			else if (C_Currency_ID != line.getC_Currency_ID())
			{
				throw doc.newPostingException().setDetailMessage("Different Currencies of Order Lines");
			}
			final BigDecimal cost = line.getAmtSource().multiply(multiplier);
			total = total.add(cost);

			// Account
			final MAccount revenue = line.getAccount(ProductCost.ACCTTYPE_P_Revenue, as);
			fact.createLine(line, revenue, C_Currency_ID, cost, null);
		}
		// Offset
		final MAccount offset = doc.getAccount(ACCTTYPE_CommitmentOffsetSales, as);
		if (offset == null)
		{
			throw doc.newPostingException().setDetailMessage("@NotFound@ @CommitmentOffsetSales_Acct@");
		}
		fact.createLine(null, offset, C_Currency_ID, null, total);
		return fact;
	}	// getCommitmentSalesRelease
}   // Doc_Order
