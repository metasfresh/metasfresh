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
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_OrderTax;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;

import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.quantity.Quantity;
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
public class Doc_Order extends Doc<DocLine_Order>
{
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

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_Order order = getModel(I_C_Order.class);
		setDateDoc(order.getDateOrdered());
		// Amounts
		setAmount(AMTTYPE_Gross, order.getGrandTotal());
		setAmount(AMTTYPE_Net, order.getTotalLines());
		setAmount(AMTTYPE_Charge, order.getChargeAmt());

		setDocLines(loadLines(order));
	}

	private List<DocLine_Order> loadLines(final I_C_Order order)
	{
		final List<DocLine_Order> docLines = new ArrayList<>();
		for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLines(order))
		{
			final DocLine_Order docLine = new DocLine_Order(orderLine, this);
			docLine.setIsTaxIncluded(orderLineBL.isTaxIncluded(orderLine));

			final BigDecimal qtyOrdered = orderLine.getQtyOrdered();
			docLine.setQty(Quantity.of(qtyOrdered, docLine.getProductStockingUOM()), order.isSOTrx());

			//
			BigDecimal priceCost = null;
			if (DOCTYPE_POrder.equals(getDocumentType()))  	// PO
			{
				priceCost = orderLine.getPriceCost();
			}

			BigDecimal lineNetAmt = null;
			if (priceCost != null && priceCost.signum() != 0)
			{
				lineNetAmt = qtyOrdered.multiply(priceCost);
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

			docLine.setAmount(lineNetAmt, priceList, qtyOrdered);
			
			docLines.add(docLine);
		}

		return docLines;
	}

	private List<DocTax> getDocTaxes()
	{
		if (_taxes == null)
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
		for (final DocLine_Order docLine : getDocLines())
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
			throw newPostingException().setC_AcctSchema(as).setDetailMessage("PO commitment posting not supported");
		}
		if (as.isCreateReservation())
		{
			throw newPostingException().setC_AcctSchema(as).setDetailMessage("PO reservation posting not supported");
		}

		return facts;
	}

	private List<Fact> createFacts_SalesOrder(final MAcctSchema as)
	{
		final List<Fact> facts = new ArrayList<>();

		// Commitment
		if (as.isCreateSOCommitment())
		{
			throw newPostingException().setC_AcctSchema(as).setDetailMessage("SO commitment posting not supported");
		}

		return facts;
	}

}
