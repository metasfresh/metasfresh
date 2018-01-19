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
import java.sql.Timestamp;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.acct.api.ProductAcctType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.PO;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailRepository;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

/**
 * Standard Document Line
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: DocLine.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class DocLine
{
	// services
	protected final transient Logger log = LogManager.getLogger(getClass());

	/**
	 * Create Document Line
	 *
	 * @param linePO line persistent object
	 * @param doc header
	 */
	public DocLine(final PO linePO, final Doc doc)
	{
		super();

		Check.assumeNotNull(linePO, "linePO not null");
		p_po = linePO;

		Check.assumeNotNull(doc, "doc not null");
		m_doc = doc;

		//
		// Document Consistency
		if (p_po.getAD_Org_ID() <= 0)
		{
			p_po.setAD_Org_ID(m_doc.getAD_Org_ID());
		}
	}	// DocLine

	/** Persistent Object */
	private final PO p_po;
	/** Parent */
	private final Doc m_doc;

	/** Qty */
	private BigDecimal m_qty = null;

	// -- GL Amounts
	/** Debit Journal Amt */
	private BigDecimal m_AmtSourceDr = Env.ZERO;
	/** Credit Journal Amt */
	private BigDecimal m_AmtSourceCr = Env.ZERO;
	/** Net Line Amt */
	private BigDecimal m_LineNetAmt = null;
	/** List Amount */
	private BigDecimal m_ListAmt = Env.ZERO;
	/** Discount Amount */
	private BigDecimal m_DiscountAmt = Env.ZERO;

	/** Converted Amounts */
	private BigDecimal m_AmtAcctDr = null;
	private BigDecimal m_AmtAcctCr = null;
	/** Acct Schema */
	private int m_C_AcctSchema_ID = 0;

	/** Product Costs */
	private ProductCost m_productCost = null;
	/** Production indicator */
	private boolean m_productionBOM = false;
	/** Outside Processing */
	private int m_PP_Cost_Collector_ID = 0;
	/** Account used only for GL Journal */
	private MAccount m_account = null;

	/** Accounting Date */
	private Timestamp m_DateAcct = null;
	/** Document Date */
	private Timestamp m_DateDoc = null;
	/** Sales Region */
	private int m_C_SalesRegion_ID = -1;
	/** Sales Region */
	private int m_C_BPartner_ID = -1;
	/** Location From */
	private int m_C_LocFrom_ID = 0;
	/** Location To */
	private int m_C_LocTo_ID = 0;
	/** Item */
	private Boolean m_isItem = null;
	/** Currency */
	private Integer m_C_Currency_ID = null;
	/** Conversion Type */
	private int m_C_ConversionType_ID = -1;
	/** Period */
	private int m_C_Period_ID = -1;
	/** C_Tax_ID (override) */
	private Integer m_C_Tax_ID = null;
	/** Is Tax Included ? */
	private boolean _taxIncluded = false;

	protected final Properties getCtx()
	{
		return m_doc.getCtx();
	}

	protected final String getTrxName()
	{
		return m_doc.getTrxName();
	}

	protected final int getAD_Client_ID()
	{
		return m_doc.getAD_Client_ID();
	}
	
	/**
	 * @param modelType
	 * @return underlying model
	 */
	protected final <T> T getModel(final Class<T> modelType)
	{
		return InterfaceWrapperHelper.create(p_po, modelType); 
	}

	/**
	 * Get Currency
	 * 
	 * @return c_Currency_ID
	 */
	public final int getC_Currency_ID()
	{
		if (m_C_Currency_ID == null)
		{
			// Get it from underlying document line model
			final int index = p_po.get_ColumnIndex("C_Currency_ID");
			if (index != -1)
			{
				final Integer currencyIdObj = (Integer)p_po.get_Value(index);
				if (currencyIdObj != null && currencyIdObj > 0)
				{
					m_C_Currency_ID = currencyIdObj;
				}
			}
			
			// Get it from document header
			if (m_C_Currency_ID == null || m_C_Currency_ID <= 0)
			{
				m_C_Currency_ID = m_doc.getC_Currency_ID();
			}
		}
		
		return m_C_Currency_ID;
	}   // getC_Currency_ID

	/**
	 * Get Conversion Type
	 * 
	 * @return C_ConversionType_ID
	 */
	public final int getC_ConversionType_ID()
	{
		if (m_C_ConversionType_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_ConversionType_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_ConversionType_ID = ii.intValue();
			}
			if (m_C_ConversionType_ID <= 0)
				m_C_ConversionType_ID = m_doc.getC_ConversionType_ID();
		}
		return m_C_ConversionType_ID;
	}   // getC_ConversionType_ID

	/**
	 * Set C_ConversionType_ID
	 *
	 * @param C_ConversionType_ID id
	 */
	protected final void setC_ConversionType_ID(int C_ConversionType_ID)
	{
		m_C_ConversionType_ID = C_ConversionType_ID;
	}	// setC_ConversionType_ID

	/**
	 * Set Amount (DR)
	 * 
	 * @param sourceAmt source amt
	 */
	protected final void setAmount(BigDecimal sourceAmt)
	{
		m_AmtSourceDr = sourceAmt == null ? Env.ZERO : sourceAmt;
		m_AmtSourceCr = Env.ZERO;
	}   // setAmounts

	/**
	 * Set Amounts
	 * 
	 * @param amtSourceDr source amount dr
	 * @param amtSourceCr source amount cr
	 */
	protected final void setAmount(BigDecimal amtSourceDr, BigDecimal amtSourceCr)
	{
		m_AmtSourceDr = amtSourceDr == null ? Env.ZERO : amtSourceDr;
		m_AmtSourceCr = amtSourceCr == null ? Env.ZERO : amtSourceCr;
	}   // setAmounts

	protected final void setAmountDrOrCr(final BigDecimal amtSource, final boolean isAmountDR)
	{
		final BigDecimal amtSourceDr = isAmountDR ? amtSource : null;
		final BigDecimal amtSourceCr = isAmountDR ? null : amtSource;
		setAmount(amtSourceDr, amtSourceCr);
	}   // setAmounts

	/**
	 * Set Converted Amounts. If converted amounts are set, they will be used as is, and no further convertion will be done.
	 * 
	 * @param amtAcctDr acct amount dr
	 * @param amtAcctCr acct amount cr
	 */
	public final void setConvertedAmt(final BigDecimal amtAcctDr, final BigDecimal amtAcctCr)
	{
		m_AmtAcctDr = amtAcctDr;
		m_AmtAcctCr = amtAcctCr;
	}   // setConvertedAmt

	/**
	 * Line Net Amount or Dr-Cr
	 * 
	 * @return balance
	 */
	public final BigDecimal getAmtSource()
	{
		return m_AmtSourceDr.subtract(m_AmtSourceCr);
	}   // getAmount

	/**
	 * Get (Journal) Line Source Dr Amount
	 * 
	 * @return DR source amount
	 */
	public final BigDecimal getAmtSourceDr()
	{
		return m_AmtSourceDr;
	}   // getAmtSourceDr

	/**
	 * Get (Journal) Line Source Cr Amount
	 * 
	 * @return CR source amount
	 */
	public final BigDecimal getAmtSourceCr()
	{
		return m_AmtSourceCr;
	}   // getAmtSourceCr

	/**
	 * Line Journal Accounted Dr Amount
	 * 
	 * @return DR accounted amount
	 */
	public final BigDecimal getAmtAcctDr()
	{
		return m_AmtAcctDr;
	}   // getAmtAcctDr

	/**
	 * Line Journal Accounted Cr Amount
	 * 
	 * @return CR accounted amount
	 */
	public final BigDecimal getAmtAcctCr()
	{
		return m_AmtAcctCr;
	}   // getAmtAccrCr

	/**
	 * Charge Amount
	 * 
	 * @return charge amount
	 */
	public final BigDecimal getChargeAmt()
	{
		int index = p_po.get_ColumnIndex("ChargeAmt");
		if (index != -1)
		{
			BigDecimal bd = (BigDecimal)p_po.get_Value(index);
			if (bd != null)
				return bd;
		}
		return Env.ZERO;
	}   // getChargeAmt

	/**
	 * Set Product Amounts
	 * 
	 * @param LineNetAmt Line Net Amt
	 * @param PriceList Price List
	 * @param Qty Qty for discount calc
	 */
	public final void setAmount(BigDecimal LineNetAmt, BigDecimal PriceList, BigDecimal Qty)
	{
		m_LineNetAmt = LineNetAmt == null ? Env.ZERO : LineNetAmt;

		if (PriceList != null && Qty != null)
			m_ListAmt = PriceList.multiply(Qty);
		if (m_ListAmt.compareTo(Env.ZERO) == 0)
			m_ListAmt = m_LineNetAmt;
		m_DiscountAmt = m_ListAmt.subtract(m_LineNetAmt);
		//
		setAmount(m_ListAmt, m_DiscountAmt);
		// Log.trace(this,Log.l6_Database, "DocLine_Invoice.setAmount",
		// "LineNet=" + m_LineNetAmt + ", List=" + m_ListAmt + ", Discount=" + m_DiscountAmt
		// + " => Amount=" + getAmount());
	}   // setAmounts

	/**
	 * Line Discount
	 * 
	 * @return discount amount
	 */
	public final BigDecimal getDiscount()
	{
		return m_DiscountAmt;
	}   // getDiscount

	/**
	 * Line List Amount
	 * 
	 * @return list amount
	 */
	public final BigDecimal getListAmount()
	{
		return m_ListAmt;
	}   // getListAmount

	/**
	 * Set Line Net Amt Difference
	 *
	 * @param diff difference (to be subtracted)
	 */
	protected final void setLineNetAmtDifference(final BigDecimal diff)
	{
		final BigDecimal lineNetAmtOld = m_LineNetAmt;
		m_LineNetAmt = m_LineNetAmt.subtract(diff);
		m_DiscountAmt = m_ListAmt.subtract(m_LineNetAmt);
		setAmount(m_ListAmt, m_DiscountAmt);
		
		log.warn("Diff=" + diff + " - LineNetAmt=" + lineNetAmtOld + " -> " + m_LineNetAmt + " - " + this);
	}	// setLineNetAmtDifference

	/**************************************************************************
	 * Set Accounting Date
	 * 
	 * @param dateAcct acct date
	 */
	public final void setDateAcct(Timestamp dateAcct)
	{
		m_DateAcct = dateAcct;
	}   // setDateAcct

	/**
	 * Get Accounting Date
	 * 
	 * @return accounting date
	 */
	public final Timestamp getDateAcct()
	{
		if (m_DateAcct != null)
			return m_DateAcct;
		int index = p_po.get_ColumnIndex("DateAcct");
		if (index != -1)
		{
			m_DateAcct = (Timestamp)p_po.get_Value(index);
			if (m_DateAcct != null)
				return m_DateAcct;
		}
		m_DateAcct = m_doc.getDateAcct();
		return m_DateAcct;
	}   // getDateAcct

	/**
	 * Set Document Date
	 * 
	 * @param dateDoc doc date
	 */
	protected final void setDateDoc(Timestamp dateDoc)
	{
		m_DateDoc = dateDoc;
	}   // setDateDoc

	/**
	 * Get Document Date
	 * 
	 * @return document date
	 */
	public final Timestamp getDateDoc()
	{
		if (m_DateDoc != null)
			return m_DateDoc;
		int index = p_po.get_ColumnIndex("DateDoc");
		if (index != -1)
		{
			m_DateDoc = (Timestamp)p_po.get_Value(index);
			if (m_DateDoc != null)
				return m_DateDoc;
		}
		m_DateDoc = m_doc.getDateDoc();
		return m_DateDoc;
	}   // getDateDoc

	/**************************************************************************
	 * Set GL Journal Account
	 * 
	 * @param acct account
	 */
	public final void setAccount(MAccount acct)
	{
		m_account = acct;
	}   // setAccount

	public final void setAccount(I_C_ValidCombination acct)
	{
		m_account = LegacyAdapters.convertToPO(acct);
	}   // setAccount

	/**
	 * Get GL Journal Account
	 * 
	 * @return account
	 */
	public final MAccount getAccount()
	{
		return m_account;
	}   // getAccount

	/**
	 * Line Account from Product (or Charge).
	 *
	 * @param AcctType see ProductCost.ACCTTYPE_* (0..3)
	 * @param as Accounting schema
	 * @return Requested Product Account
	 */
	@OverridingMethodsMustInvokeSuper
	public MAccount getAccount(ProductAcctType AcctType, MAcctSchema as)
	{
		if (getM_Product_ID() <= 0 && getC_Charge_ID() > 0)
		{
			final MAccount acct;
			if (!m_doc.isSOTrx())
			{
				acct = getChargeAccount(as, new BigDecimal(+1)); // Expense (+)
			}
			else
			{
				acct = getChargeAccount(as, new BigDecimal(-1)); // Revenue (-)
			}
			if (acct != null)
			{
				return acct;
			}
		}

		// Product Account
		return getProductCost().getAccount(AcctType, as);
	}

	/**
	 * This is {@link #getAccount(int, MAcctSchema)} variant for German/DATEV accounting.
	 * 
	 * We are not using it anymore (07089), but i am keeping it here for history and later use.
	 * 
	 * @param AcctType
	 * @param as
	 * @return account
	 * @deprecated Use it only if u know what are u doing. Else, use {@link #getAccount()}.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private final MAccount getAccount_DE(final ProductAcctType AcctType, MAcctSchema as)
	{
		// Charge Account
		// CHANGED: taxdependant receipts account
		// metas-mo: if charge, SOTrx and ACCTTYPE_P_Revenue, then use the tax revenue.
		// Otherwise, use getChargeAccount as before
		if (getM_Product_ID() <= 0 && getC_Charge_ID() != 0)
		{
			final MAccount acct;
			if (!m_doc.isSOTrx())
			{
				acct = getChargeAccount(as, new BigDecimal(+1)); // Expense (+)
			}
			else if (AcctType != ProductCost.ACCTTYPE_P_Revenue)
			{
				acct = getChargeAccount(as, new BigDecimal(-1)); // Revenue (-)
			}
			else
			{
				acct = getTaxAccount(as);
			}
			if (acct != null)
				return acct;
		}
		// CHANGED: taxdependant receipts account
		if (AcctType == ProductCost.ACCTTYPE_P_Revenue)
		{
			MAccount acct = getTaxAccount(as);
			if (acct != null)
			{
				return acct;
			}
		}
		// end changed
		// Product Account
		return getProductCost().getAccount(AcctType, as);
	} // getAccount

	// CHANGED metas - method getTaxAccount() added
	/**
	 * Returns the receipts account dependent on the tax (for ProductExpense/Revenue only).
	 * 
	 * @param taxId
	 * @return
	 */
	private final MAccount getTaxAccount(MAcctSchema as)
	{
		// ValidCombination mit
		// passender org/accountId
		int C_Tax_ID = getC_Tax_ID();
		if (C_Tax_ID <= 0)
		{
			return null;
		}
		return MTax.getRevenueAccount(C_Tax_ID, as);
	}

	// ende changed

	/**
	 * Get Charge
	 * 
	 * @return C_Charge_ID
	 */
	protected final int getC_Charge_ID()
	{
		int index = p_po.get_ColumnIndex("C_Charge_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	// getC_Charge_ID

	/**
	 * Get Charge Account
	 * 
	 * @param as account schema
	 * @param amount amount for expense(+)/revenue(-)
	 * @return Charge Account or null
	 */
	public final MAccount getChargeAccount(MAcctSchema as, BigDecimal amount)
	{
		int C_Charge_ID = getC_Charge_ID();
		if (C_Charge_ID == 0)
			return null;
		return MCharge.getAccount(C_Charge_ID, as, amount);
	}   // getChargeAccount

	/**
	 * Get Period
	 * 
	 * @return C_Period_ID
	 */
	protected final int getC_Period_ID()
	{
		if (m_C_Period_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_Period_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_Period_ID = ii.intValue();
			}
			if (m_C_Period_ID == -1)
				m_C_Period_ID = 0;
		}
		return m_C_Period_ID;
	}	// getC_Period_ID

	/**
	 * Set C_Period_ID
	 *
	 * @param C_Period_ID id
	 */
	protected final void setC_Period_ID(int C_Period_ID)
	{
		m_C_Period_ID = C_Period_ID;
	}	// setC_Period_ID

	/**************************************************************************
	 * Get (Journal) AcctSchema
	 * 
	 * @return C_AcctSchema_ID
	 */
	public final int getC_AcctSchema_ID()
	{
		return m_C_AcctSchema_ID;
	}   // getC_AcctSchema_ID

	public final void setC_AcctSchema_ID(final int acctSchemaId)
	{
		this.m_C_AcctSchema_ID = acctSchemaId;
	}

	/**
	 * Get Line ID
	 *
	 * @return id
	 */
	public final int get_ID()
	{
		return p_po.get_ID();
	}	// get_ID

	/**
	 * Get AD_Org_ID
	 *
	 * @return org
	 */
	public final int getAD_Org_ID()
	{
		return p_po.getAD_Org_ID();
	}	// getAD_Org_ID

	/**
	 * Get Order AD_Org_ID
	 *
	 * @return order org if defined
	 */
	public final int getOrder_Org_ID()
	{
		int C_OrderLine_ID = getC_OrderLine_ID();
		if (C_OrderLine_ID != 0)
		{
			String sql = "SELECT AD_Org_ID FROM C_OrderLine WHERE C_OrderLine_ID=?";
			int AD_Org_ID = DB.getSQLValue(null, sql, C_OrderLine_ID);
			if (AD_Org_ID > 0)
				return AD_Org_ID;
		}
		return getAD_Org_ID();
	}	// getOrder_Org_ID

	/**
	 * Product
	 * 
	 * @return M_Product_ID
	 */
	public final int getM_Product_ID()
	{
		int index = p_po.get_ColumnIndex("M_Product_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getM_Product_ID

	/**
	 * Is this an stockable item Product (vs. not a Service, a charge)
	 *
	 * @return true if we have a stockable product
	 */
	public final boolean isItem()
	{
		if (m_isItem != null)
			return m_isItem.booleanValue();

		m_isItem = Boolean.FALSE;
		if (getM_Product_ID() > 0)
		{
			final I_M_Product product = MProduct.get(getCtx(), getM_Product_ID());
			final IProductBL productBL = Services.get(IProductBL.class);

			// NOTE: we are considering the product as Item only if it's stockable.
			// Before changing this logic, pls evaluate the Doc_Invoice which is booking on P_InventoryClearing account when the product is stockable
			if (product.getM_Product_ID() == getM_Product_ID() && productBL.isStocked(product))
			{
				m_isItem = Boolean.TRUE;
			}
		}
		return m_isItem.booleanValue();
	}	// isItem
	
	public final boolean isService()
	{
		return !isItem();
	}

	/**
	 * ASI
	 * 
	 * @return M_AttributeSetInstance_ID
	 */
	public final int getM_AttributeSetInstance_ID()
	{
		int index = p_po.get_ColumnIndex("M_AttributeSetInstance_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getM_AttributeSetInstance_ID

	/**
	 * Get Warehouse Locator (from)
	 * 
	 * @return M_Locator_ID
	 */
	public final int getM_Locator_ID()
	{
		int index = p_po.get_ColumnIndex("M_Locator_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getM_Locator_ID

	/**
	 * Get Warehouse Locator To
	 * 
	 * @return M_Locator_ID
	 */
	public final int getM_LocatorTo_ID()
	{
		int index = p_po.get_ColumnIndex("M_LocatorTo_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getM_LocatorTo_ID

	/**
	 * Set Production BOM flag
	 *
	 * @param productionBOM flag
	 */
	public final void setProductionBOM(boolean productionBOM)
	{
		m_productionBOM = productionBOM;
	}	// setProductionBOM

	/**
	 * Is this the BOM to be produced
	 *
	 * @return true if BOM
	 */
	public final boolean isProductionBOM()
	{
		return m_productionBOM;
	}	// isProductionBOM

	/**
	 * Get Production Plan
	 * 
	 * @return M_ProductionPlan_ID
	 */
	public final int getM_ProductionPlan_ID()
	{
		int index = p_po.get_ColumnIndex("M_ProductionPlan_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getM_ProductionPlan_ID

	/**
	 * Get Order Line Reference
	 * 
	 * @return C_OrderLine_ID
	 */
	public final int getC_OrderLine_ID()
	{
		int index = p_po.get_ColumnIndex("C_OrderLine_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getC_OrderLine_ID

	/**
	 * Get C_LocFrom_ID
	 *
	 * @return loc from
	 */
	public final int getC_LocFrom_ID()
	{
		return m_C_LocFrom_ID;
	}	// getC_LocFrom_ID

	/**
	 * Set C_LocFrom_ID
	 *
	 * @param C_LocFrom_ID loc from
	 */
	public final void setC_LocFrom_ID(int C_LocFrom_ID)
	{
		m_C_LocFrom_ID = C_LocFrom_ID;
	}	// setC_LocFrom_ID

	/**
	 * Get PP_Cost_Collector_ID
	 *
	 * @return Cost Collector ID
	 */
	public final int getPP_Cost_Collector_ID()
	{
		return m_PP_Cost_Collector_ID;
	}	// getC_LocFrom_ID

	/**
	 * Get PP_Cost_Collector_ID
	 *
	 * @return Cost Collector ID
	 */
	public final int setPP_Cost_Collector_ID(int PP_Cost_Collector_ID)
	{
		return m_PP_Cost_Collector_ID;
	}	// getC_LocFrom_ID

	/**
	 * Get C_LocTo_ID
	 *
	 * @return loc to
	 */
	public final int getC_LocTo_ID()
	{
		return m_C_LocTo_ID;
	}	// getC_LocTo_ID

	/**
	 * Set C_LocTo_ID
	 *
	 * @param C_LocTo_ID loc to
	 */
	public final void setC_LocTo_ID(int C_LocTo_ID)
	{
		m_C_LocTo_ID = C_LocTo_ID;
	}	// setC_LocTo_ID

	/**
	 * Get Product Cost Info
	 *
	 * @return product cost
	 */
	public final ProductCost getProductCost()
	{
		if (m_productCost == null)
		{
			m_productCost = new ProductCost(getCtx(), getM_Product_ID(), getM_AttributeSetInstance_ID(), p_po.get_TrxName());
		}
		return m_productCost;
	}	// getProductCost

	// MZ Goodwill
	/**
	 * Get Total Product Costs from Cost Detail or from Current Cost
	 * 
	 * @param as accounting schema
	 * @param AD_Org_ID trx org
	 * @param zeroCostsOK zero/no costs are OK
	 * @param whereClause null are OK
	 * @return costs
	 */
	public final BigDecimal getProductCosts(MAcctSchema as, int AD_Org_ID, boolean zeroCostsOK, CostingDocumentRef documentRef)
	{
		if (documentRef != null)
		{
			final CostDetailQuery query = CostDetailQuery.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.documentRef(documentRef)
					.attributeSetInstanceId(getM_AttributeSetInstance_ID())
					.build();
			final I_M_CostDetail cd = Services.get(ICostDetailRepository.class).getCostDetailOrNull(query);
			if (cd != null)
			{
				return cd.getAmt();
			}
		}
		return getProductCosts(as, AD_Org_ID, zeroCostsOK);
	}   // getProductCosts

	// end MZ

	/**
	 * Get Total Product Costs
	 * 
	 * @param as accounting schema
	 * @param AD_Org_ID trx org
	 * @param zeroCostsOK zero/no costs are OK
	 * @return costs
	 */
	public final BigDecimal getProductCosts(MAcctSchema as, int AD_Org_ID, boolean zeroCostsOK)
	{
		ProductCost pc = getProductCost();
		int C_OrderLine_ID = getC_OrderLine_ID();
		String costingMethod = null;
		BigDecimal costs = pc.getProductCosts(as, AD_Org_ID, costingMethod, C_OrderLine_ID, zeroCostsOK);
		if (costs != null)
			return costs;
		return BigDecimal.ZERO;
	}   // getProductCosts

	/**
	 * Get Product
	 *
	 * @return product or null if no product
	 */
	public final MProduct getProduct()
	{
		return getProductCost().getProduct();
	}	// getProduct

	/**
	 * Get Revenue Recognition
	 * 
	 * @return C_RevenueRecognition_ID or 0
	 */
	public final int getC_RevenueRecognition_ID()
	{
		MProduct product = getProduct();
		if (product != null)
			return product.getC_RevenueRecognition_ID();
		return 0;
	}   // getC_RevenueRecognition_ID

	/**
	 * Quantity UOM
	 * 
	 * @return Transaction or Storage M_UOM_ID
	 */
	public final int getC_UOM_ID()
	{
		// Trx UOM
		int index = p_po.get_ColumnIndex("C_UOM_ID");
		if (index != -1)
		{
			final Integer uomId = (Integer)p_po.get_Value(index);
			if (uomId != null)
				return uomId.intValue();
		}
		// Storage UOM
		MProduct product = getProduct();
		if (product != null)
			return product.getC_UOM_ID();
		//
		return 0;
	}   // getC_UOM

	/**
	 * Quantity
	 * 
	 * @param qty transaction Qty
	 * @param isSOTrx SL order trx (i.e. negative qty)
	 */
	public final void setQty(final BigDecimal qty, final boolean isSOTrx)
	{
		if (qty == null)
			m_qty = Env.ZERO;
		else if (isSOTrx)
			m_qty = qty.negate();
		else
			m_qty = qty;
		getProductCost().setQty(qty);
	}   // setQty

	/**
	 * Quantity
	 * 
	 * @return transaction Qty
	 */
	public final BigDecimal getQty()
	{
		return m_qty;
	}   // getQty

	/**
	 * Description
	 * 
	 * @return doc line description
	 */
	public final String getDescription()
	{
		int index = p_po.get_ColumnIndex("Description");
		if (index != -1)
			return (String)p_po.get_Value(index);
		return null;
	}	// getDescription

	/**
	 * Line Tax
	 * 
	 * @return C_Tax_ID
	 */
	public final int getC_Tax_ID()
	{
		if (m_C_Tax_ID != null)
		{
			return m_C_Tax_ID;
		}

		final int index = p_po.get_ColumnIndex("C_Tax_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	// getC_Tax_ID

	public final void setC_Tax_ID(final Integer taxId)
	{
		this.m_C_Tax_ID = taxId;
	}

	/**
	 * Get Line Number
	 * 
	 * @return line no
	 */
	public final int getLine()
	{
		int index = p_po.get_ColumnIndex("Line");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getLine

	/**
	 * Get BPartner
	 * 
	 * @return C_BPartner_ID
	 */
	public int getC_BPartner_ID()
	{
		if (m_C_BPartner_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_BPartner_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_BPartner_ID = ii.intValue();
			}
			if (m_C_BPartner_ID <= 0)
				m_C_BPartner_ID = m_doc.getC_BPartner_ID();
		}
		return m_C_BPartner_ID;
	}   // getC_BPartner_ID

	/**
	 * Set C_BPartner_ID
	 *
	 * @param C_BPartner_ID id
	 */
	protected final void setC_BPartner_ID(int C_BPartner_ID)
	{
		m_C_BPartner_ID = C_BPartner_ID;
	}	// setC_BPartner_ID

	/**
	 * Get C_BPartner_Location_ID
	 *
	 * @return BPartner Location
	 */
	public final int getC_BPartner_Location_ID()
	{
		int index = p_po.get_ColumnIndex("C_BPartner_Location_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return m_doc.getC_BPartner_Location_ID();
	}	// getC_BPartner_Location_ID

	/**
	 * Get TrxOrg
	 * 
	 * @return AD_OrgTrx_ID
	 */
	public final int getAD_OrgTrx_ID()
	{
		int index = p_po.get_ColumnIndex("AD_OrgTrx_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getAD_OrgTrx_ID

	/**
	 * Get SalesRegion.
	 * - get Sales Region from BPartner
	 * 
	 * @return C_SalesRegion_ID
	 */
	public final int getC_SalesRegion_ID()
	{
		if (m_C_SalesRegion_ID == -1)	// never tried
		{
			if (getC_BPartner_Location_ID() != 0)
			// && m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				String sql = "SELECT COALESCE(C_SalesRegion_ID,0) FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
				m_C_SalesRegion_ID = DB.getSQLValue(null,
						sql, getC_BPartner_Location_ID());
				log.debug("C_SalesRegion_ID=" + m_C_SalesRegion_ID + " (from BPL)");
				if (m_C_SalesRegion_ID == 0)
					m_C_SalesRegion_ID = -2;	// don't try again
			}
			else
				m_C_SalesRegion_ID = -2;		// don't try again
		}
		if (m_C_SalesRegion_ID < 0)				// invalid
			return 0;
		return m_C_SalesRegion_ID;
	}   // getC_SalesRegion_ID

	/**
	 * Get Project
	 * 
	 * @return C_Project_ID
	 */
	public final int getC_Project_ID()
	{
		int index = p_po.get_ColumnIndex("C_Project_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getC_Project_ID

	/**
	 * Get Campaign
	 * 
	 * @return C_Campaign_ID
	 */
	public final int getC_Campaign_ID()
	{
		int index = p_po.get_ColumnIndex("C_Campaign_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getC_Campaign_ID

	// 07689
	// ActivityFrom
	/**
	 * Get Activity From
	 * 
	 * @return C_ActivityFrom_ID
	 */
	public final int getC_ActivityFrom_ID()
	{
		int index = p_po.get_ColumnIndex("C_ActivityFrom_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getC_ActivityFrom_ID

	/**
	 * Get Activity
	 * 
	 * @return C_Activity_ID
	 */
	public int getC_Activity_ID()
	{
		int index = p_po.get_ColumnIndex("C_Activity_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getC_Activity_ID

	/**
	 * Get User 1
	 * 
	 * @return user defined 1
	 */
	public final int getUser1_ID()
	{
		int index = p_po.get_ColumnIndex("User1_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getUser1_ID

	/**
	 * Get User 2
	 * 
	 * @return user defined 2
	 */
	public final int getUser2_ID()
	{
		int index = p_po.get_ColumnIndex("User2_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getUser2_ID

	/**
	 * Get User Defined Column
	 * 
	 * @param ColumnName column name
	 * @return user defined column value
	 */
	public final int getValue(String ColumnName)
	{
		int index = p_po.get_ColumnIndex(ColumnName);
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   // getValue

	// AZ Goodwill
	private int m_ReversalLine_ID = 0;

	/**
	 * Set ReversalLine_ID
	 * store original (voided/reversed) document line
	 * 
	 * @param ReversalLine_ID
	 */
	public final void setReversalLine_ID(int ReversalLine_ID)
	{
		m_ReversalLine_ID = ReversalLine_ID;
	}   // setReversalLine_ID

	/**
	 * Get ReversalLine_ID
	 * get original (voided/reversed) document line
	 * 
	 * @return ReversalLine_ID
	 */
	public final int getReversalLine_ID()
	{
		return m_ReversalLine_ID;
	}   // getReversalLine_ID

	// end AZ Goodwill

	/**
	 * String representation
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("DocLine=[");
		sb.append(p_po.get_ID());
		if (getDescription() != null)
			sb.append(",").append(getDescription());
		if (getM_Product_ID() != 0)
			sb.append(",M_Product_ID=").append(getM_Product_ID());
		sb.append(",Qty=").append(m_qty)
				.append(",Amt=").append(getAmtSource())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Is Tax Included
	 *
	 * @return true if tax is included in amounts
	 */
	public final boolean isTaxIncluded()
	{
		return _taxIncluded;
	}	// isTaxIncluded

	/**
	 * Set Tax Included
	 *
	 * @param taxIncluded Is Tax Included?
	 */
	protected final void setIsTaxIncluded(final boolean taxIncluded)
	{
		this._taxIncluded = taxIncluded;
	}
	
	/**
	 * @see Doc#isSOTrx()
	 */
	public boolean isSOTrx()
	{
		return m_doc.isSOTrx();
	}

	/**
	 * @return document currency precision
	 * @see Doc#getStdPrecision()
	 */
	protected final int getStdPrecision()
	{
		return m_doc.getStdPrecision();
	}

	/** @return document (header) */
	// NOTE: this method is not final because some DocLine implementions would like to cast the return of this method to specific Doc implementation.
	@OverridingMethodsMustInvokeSuper
	protected Doc getDoc()
	{
		return m_doc;
	}

	public PostingException newPostingException(final Throwable e)
	{
		return m_doc.newPostingException(e)
				.setDocument(m_doc)
				.setDocLine(this);
	}
}	// DocumentLine
