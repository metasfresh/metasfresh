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
import java.sql.Timestamp;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.acct.api.IAccountDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MAccount;
import org.compiere.model.MCharge;
import org.compiere.model.PO;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.acct.api.ProductAcctType;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostDetailRepository;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * Standard Document Line
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 *         <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: DocLine.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class DocLine<DT extends Doc<? extends DocLine<?>>>
{
	// services
	private final transient Logger logger = LogManager.getLogger(getClass());
	protected final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);
	private final transient IAccountDAO accountDAO = Services.get(IAccountDAO.class);

	/** Persistent Object */
	private final PO p_po;
	/** Parent */
	private final DT m_doc;

	/** Qty */
	private Quantity m_qty = null;

	// -- GL Amounts
	/** Debit Journal Amt */
	private BigDecimal m_AmtSourceDr = BigDecimal.ZERO;
	/** Credit Journal Amt */
	private BigDecimal m_AmtSourceCr = BigDecimal.ZERO;
	/** Net Line Amt */
	private BigDecimal m_LineNetAmt = null;
	/** List Amount */
	private BigDecimal m_ListAmt = BigDecimal.ZERO;
	/** Discount Amount */
	private BigDecimal m_DiscountAmt = BigDecimal.ZERO;

	/** Converted Amounts */
	private BigDecimal m_AmtAcctDr = null;
	private BigDecimal m_AmtAcctCr = null;

	private I_M_Product _product; // lazy
	private Boolean _productIsItem = null; // lazy
	/** Product Costs */
	private ProductCost m_productCost = null;

	/** Accounting Date */
	private Timestamp m_DateAcct = null;
	/** Document Date */
	private Timestamp m_DateDoc = null;
	/** Sales Region */
	private int m_C_SalesRegion_ID = -1;
	/** Sales Region */
	private int m_C_BPartner_ID = -1;
	/** Location From */
	private final int m_C_LocFrom_ID = 0;
	/** Location To */
	private int m_C_LocTo_ID = 0;
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

	private int m_ReversalLine_ID = 0;

	public DocLine(@NonNull final PO linePO, @NonNull final DT doc)
	{
		p_po = linePO;
		m_doc = doc;

		//
		// Document Consistency
		if (p_po.getAD_Org_ID() <= 0)
		{
			p_po.setAD_Org_ID(m_doc.getAD_Org_ID());
		}
	}	// DocLine

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

	private final PO getPO()
	{
		return p_po;
	}

	/**
	 * @param modelType
	 * @return underlying model
	 */
	protected final <T> T getModel(final Class<T> modelType)
	{
		return InterfaceWrapperHelper.create(getPO(), modelType);
	}

	public final int getC_Currency_ID()
	{
		if (m_C_Currency_ID == null)
		{
			// Get it from underlying document line model
			m_C_Currency_ID = getValue("C_Currency_ID");

			// Get it from document header
			if (m_C_Currency_ID == null || m_C_Currency_ID <= 0)
			{
				m_C_Currency_ID = m_doc.getC_Currency_ID();
			}
		}

		return m_C_Currency_ID;
	}

	public final int getC_ConversionType_ID()
	{
		if (m_C_ConversionType_ID == -1)
		{
			m_C_ConversionType_ID = getValue("C_ConversionType_ID");
			if (m_C_ConversionType_ID <= 0)
			{
				m_C_ConversionType_ID = m_doc.getC_ConversionType_ID();
			}
		}
		return m_C_ConversionType_ID;
	}

	protected final void setC_ConversionType_ID(final int C_ConversionType_ID)
	{
		m_C_ConversionType_ID = C_ConversionType_ID;
	}

	/**
	 * Set Amount (DR)
	 *
	 * @param sourceAmt source amt
	 */
	protected final void setAmount(final BigDecimal sourceAmt)
	{
		m_AmtSourceDr = sourceAmt == null ? BigDecimal.ZERO : sourceAmt;
		m_AmtSourceCr = BigDecimal.ZERO;
	}   // setAmounts

	/**
	 * Set Amounts
	 *
	 * @param amtSourceDr source amount dr
	 * @param amtSourceCr source amount cr
	 */
	protected final void setAmount(final BigDecimal amtSourceDr, final BigDecimal amtSourceCr)
	{
		m_AmtSourceDr = amtSourceDr == null ? BigDecimal.ZERO : amtSourceDr;
		m_AmtSourceCr = amtSourceCr == null ? BigDecimal.ZERO : amtSourceCr;
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

	public final BigDecimal getChargeAmt()
	{
		return getValueAsBD("ChargeAmt", BigDecimal.ZERO);
	}

	/**
	 * Set Product Amounts
	 *
	 * @param LineNetAmt Line Net Amt
	 * @param PriceList Price List
	 * @param Qty Qty for discount calc
	 */
	public final void setAmount(final BigDecimal LineNetAmt, final BigDecimal PriceList, final BigDecimal Qty)
	{
		m_LineNetAmt = LineNetAmt == null ? BigDecimal.ZERO : LineNetAmt;

		if (PriceList != null && Qty != null)
		{
			m_ListAmt = PriceList.multiply(Qty);
		}
		if (m_ListAmt.compareTo(BigDecimal.ZERO) == 0)
		{
			m_ListAmt = m_LineNetAmt;
		}
		m_DiscountAmt = m_ListAmt.subtract(m_LineNetAmt);
		//
		setAmount(m_ListAmt, m_DiscountAmt);
	}

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

		logger.warn("Diff={} - LineNetAmt={} -> {} - {}", diff, lineNetAmtOld, m_LineNetAmt, this);
	}

	public final Timestamp getDateAcct()
	{
		if (m_DateAcct == null)
		{
			m_DateAcct = Util.coalesceSuppliers(
					() -> getValueAsTSOrNull("DateAcct"),
					() -> getDoc().getDateAcct());
		}
		return m_DateAcct;
	}

	protected final void setDateDoc(final Timestamp dateDoc)
	{
		m_DateDoc = dateDoc;
	}   // setDateDoc

	public final Timestamp getDateDoc()
	{
		if (m_DateDoc == null)
		{
			m_DateDoc = Util.coalesceSuppliers(
					() -> getValueAsTSOrNull("DateDoc"),
					() -> getValueAsTSOrNull("DateTrx"),
					() -> getDoc().getDateAcct());
		}
		return m_DateDoc;
	}

	/**
	 * Line Account from Product (or Charge).
	 *
	 * @param acctType see ProductCost.ACCTTYPE_* (0..3)
	 * @param as Accounting schema
	 * @return Requested Product Account
	 */
	@OverridingMethodsMustInvokeSuper
	public MAccount getAccount(final ProductAcctType acctType, final I_C_AcctSchema as)
	{
		//
		// Charge account
		if (getM_Product_ID() <= 0 && getC_Charge_ID() > 0)
		{
			final MAccount acct;
			if (!m_doc.isSOTrx())
			{
				acct = getChargeAccount(as, BigDecimal.ONE); // Expense (+)
			}
			else
			{
				acct = getChargeAccount(as, BigDecimal.ONE.negate()); // Revenue (-)
			}
			if (acct == null)
			{
				throw newPostingException().setC_AcctSchema(as).setDetailMessage("No Charge Account for account type: " + acctType);
			}
			return acct;
		}
		//
		// Product Account
		else
		{
			return getProductAccount(acctType, as);
		}
	}

	private MAccount getProductAccount(final ProductAcctType acctType, final I_C_AcctSchema as)
	{
		// No Product - get Default from Product Category
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return getAccountDefault(acctType, as);
		}

		final I_M_Product_Acct productAcct = productAcctDAO.retrieveProductAcctOrNull(as, productId);
		Check.assumeNotNull(productAcct, "Product {} has accounting definition records", productId);
		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(productAcct, acctType.getColumnName());
		if (validCombinationId == null || validCombinationId <= 0)
		{
			throw newPostingException().setC_AcctSchema(as).setDetailMessage("No Product Account for account type: " + acctType);
		}

		return accountDAO.retrieveAccountById(getCtx(), validCombinationId);
	}

	/**
	 * Account from Default Product Category
	 *
	 * @param AcctType see ACCTTYPE_* (1..8)
	 * @param as accounting schema
	 * @return Requested Product Account
	 */
	private final MAccount getAccountDefault(final ProductAcctType acctType, final I_C_AcctSchema as)
	{
		final I_M_Product_Category_Acct pcAcct = productAcctDAO.retrieveDefaultProductCategoryAcct(as);
		final Integer validCombinationId = InterfaceWrapperHelper.getValueOrNull(pcAcct, acctType.getColumnName());
		if (validCombinationId == null || validCombinationId <= 0)
		{
			throw newPostingException().setC_AcctSchema(as).setDetailMessage("No Default Account for account type: " + acctType);
		}

		return accountDAO.retrieveAccountById(getCtx(), validCombinationId);
	}

	/**
	 * Get Charge
	 *
	 * @return C_Charge_ID
	 */
	protected final int getC_Charge_ID()
	{
		return getValue("C_Charge_ID");
	}

	/**
	 * Get Charge Account
	 *
	 * @param as account schema
	 * @param amount amount for expense(+)/revenue(-)
	 * @return Charge Account or null
	 */
	protected final MAccount getChargeAccount(final I_C_AcctSchema as, final BigDecimal amount)
	{
		final int C_Charge_ID = getC_Charge_ID();
		if (C_Charge_ID == 0)
		{
			return null;
		}
		return MCharge.getAccount(C_Charge_ID, as, amount);
	}

	protected final int getC_Period_ID()
	{
		if (m_C_Period_ID == -1)
		{
			m_C_Period_ID = getValue("C_Period_ID");
			if (m_C_Period_ID <= 0)
			{
				m_C_Period_ID = 0;
			}
		}
		return m_C_Period_ID;
	}	// getC_Period_ID

	protected final void setC_Period_ID(final int C_Period_ID)
	{
		m_C_Period_ID = C_Period_ID;
	}

	public final int get_ID()
	{
		return getPO().get_ID();
	}

	public final int getAD_Org_ID()
	{
		return getPO().getAD_Org_ID();
	}

	public final int getM_Product_ID()
	{
		return getValue("M_Product_ID");
	}

	/**
	 * Is this an stockable item Product (vs. not a Service, a charge)
	 *
	 * @return true if we have a stockable product
	 */
	public final boolean isItem()
	{
		if (_productIsItem == null)
		{
			_productIsItem = checkIsItem();
		}
		return _productIsItem;
	}

	private final boolean checkIsItem()
	{
		final I_M_Product product = getProduct();
		if (product == null)
		{
			return false;
		}

		// NOTE: we are considering the product as Item only if it's stockable.
		// Before changing this logic, pls evaluate the Doc_Invoice which is booking on P_InventoryClearing account when the product is stockable
		return productBL.isStocked(product);
	}

	public final boolean isService()
	{
		return !isItem();
	}

	public final CostingMethod getProductCostingMethod(final I_C_AcctSchema as)
	{
		return productBL.getCostingMethod(getM_Product_ID(), as);
	}

	public final CostingLevel getProductCostingLevel(final I_C_AcctSchema as)
	{
		return productBL.getCostingLevel(getM_Product_ID(), as);
	}

	public final int getM_AttributeSetInstance_ID()
	{
		return getValue("M_AttributeSetInstance_ID");
	}

	public final int getM_Locator_ID()
	{
		return getValue("M_Locator_ID");
	}

	public final int getC_OrderLine_ID()
	{
		return getValue("C_OrderLine_ID");
	}

	public final int getC_LocFrom_ID()
	{
		return m_C_LocFrom_ID;
	}

	public final int getC_LocTo_ID()
	{
		return m_C_LocTo_ID;
	}

	public final void setC_LocTo_ID(final int C_LocTo_ID)
	{
		m_C_LocTo_ID = C_LocTo_ID;
	}	// setC_LocTo_ID

	protected final ProductCost getProductCost()
	{
		if (m_productCost == null)
		{
			m_productCost = new ProductCost(getCtx(), getM_Product_ID(), getM_AttributeSetInstance_ID(), ITrx.TRXNAME_ThreadInherited);
		}
		return m_productCost;
	}

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
	public final BigDecimal getProductCosts(final I_C_AcctSchema as, final int AD_Org_ID, final boolean zeroCostsOK, final CostingDocumentRef documentRef)
	{
		if (documentRef != null)
		{
			final CostDetailQuery query = CostDetailQuery.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.documentRef(documentRef)
					.attributeSetInstanceId(getM_AttributeSetInstance_ID())
					.build();
			final ICostDetailRepository costDetailsRepo = Adempiere.getBean(ICostDetailRepository.class);
			final BigDecimal costDetailAmt = costDetailsRepo.getCostDetailAmtOrNull(query);
			if (costDetailAmt != null)
			{
				return costDetailAmt;
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
	public final BigDecimal getProductCosts(final I_C_AcctSchema as, final int AD_Org_ID, final boolean zeroCostsOK)
	{
		final ProductCost pc = getProductCost();
		final int C_OrderLine_ID = getC_OrderLine_ID();
		final CostingMethod costingMethod = null;
		final BigDecimal costs = pc.getProductCosts(as, AD_Org_ID, costingMethod, C_OrderLine_ID, zeroCostsOK);
		if (costs != null)
		{
			return costs;
		}
		return BigDecimal.ZERO;
	}   // getProductCosts

	/**
	 * @return product or null if no product
	 */
	public final I_M_Product getProduct()
	{
		if (_product == null)
		{
			final int productId = getM_Product_ID();
			if (productId > 0)
			{
				_product = InterfaceWrapperHelper.loadOutOfTrx(productId, I_M_Product.class);
			}
		}
		return _product;
	}

	protected final I_C_UOM getProductStockingUOM()
	{
		return productBL.getStockingUOM(getM_Product_ID());
	}

	/**
	 * Get Revenue Recognition
	 *
	 * @return C_RevenueRecognition_ID or 0
	 */
	public final int getC_RevenueRecognition_ID()
	{
		final I_M_Product product = getProduct();
		if (product != null)
		{
			return product.getC_RevenueRecognition_ID();
		}
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
		final int uomId = getValue("C_UOM_ID");
		if (uomId > 0)
		{
			return uomId;
		}

		// Storage UOM
		final I_M_Product product = getProduct();
		if (product != null)
		{
			return product.getC_UOM_ID();
		}

		//
		return 0;
	}

	/**
	 * Quantity
	 *
	 * @param quantity transaction Qty
	 * @param isSOTrx SL order trx (i.e. negative qty)
	 */
	protected final void setQty(@NonNull final Quantity quantity, final boolean isSOTrx)
	{
		if (isSOTrx)
		{
			m_qty = quantity.negate();
		}
		else
		{
			m_qty = quantity;
		}
		getProductCost().setQty(quantity.getQty());
	}

	/**
	 * Quantity
	 *
	 * @return transaction Qty
	 */
	public final Quantity getQty()
	{
		return m_qty;
	}   // getQty

	public final String getDescription()
	{
		return getValueAsString("Description");
	}

	public final int getC_Tax_ID()
	{
		if (m_C_Tax_ID == null)
		{
			m_C_Tax_ID = getValue("C_Tax_ID");
		}
		return m_C_Tax_ID;
	}

	public final void setC_Tax_ID(final int taxId)
	{
		m_C_Tax_ID = taxId;
	}

	public final int getLine()
	{
		return getValue("Line");
	}

	public int getC_BPartner_ID()
	{
		if (m_C_BPartner_ID == -1)
		{
			m_C_BPartner_ID = Util.coalesceSuppliers(
					() -> getValue("C_BPartner_ID"),
					() -> m_doc.getC_BPartner_ID());
			if (m_C_BPartner_ID <= 0)
			{
				m_C_BPartner_ID = 0;
			}
		}
		return m_C_BPartner_ID;
	}

	protected final void setC_BPartner_ID(final int C_BPartner_ID)
	{
		m_C_BPartner_ID = C_BPartner_ID;
	}	// setC_BPartner_ID

	private final int getC_BPartner_Location_ID()
	{
		return Util.coalesceSuppliers(
				() -> getValue("C_BPartner_Location_ID"),
				() -> m_doc.getC_BPartner_Location_ID());
	}

	public final int getAD_OrgTrx_ID()
	{
		return getValue("AD_OrgTrx_ID");
	}

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
			final int bpartnerLocationId = getC_BPartner_Location_ID();
			if (bpartnerLocationId > 0)
			// && m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				final String sql = "SELECT COALESCE(C_SalesRegion_ID,0) FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
				m_C_SalesRegion_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, bpartnerLocationId);
				if (m_C_SalesRegion_ID == 0)
				{
					m_C_SalesRegion_ID = -2;	// don't try again
				}
			}
			else
			{
				m_C_SalesRegion_ID = -2;		// don't try again
			}
		}
		if (m_C_SalesRegion_ID < 0)
		{
			return 0;
		}
		return m_C_SalesRegion_ID;
	}   // getC_SalesRegion_ID

	public final int getC_Project_ID()
	{
		return getValue("C_Project_ID");
	}

	public final int getC_Campaign_ID()
	{
		return getValue("C_Campaign_ID");
	}

	public final int getC_ActivityFrom_ID()
	{
		return getValue("C_ActivityFrom_ID");
	}

	public int getC_Activity_ID()
	{
		return getValue("C_Activity_ID");
	}

	public final int getUser1_ID()
	{
		return getValue("User1_ID");
	}

	public final int getUser2_ID()
	{
		return getValue("User2_ID");
	}

	/**
	 * Get User Defined Column
	 *
	 * @param columnName column name
	 * @return user defined column value
	 */
	public final int getValue(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Integer valueInt = (Integer)po.get_Value(index);
			if (valueInt != null)
			{
				return valueInt.intValue();
			}
		}
		return 0;
	}

	private final BigDecimal getValueAsBD(final String columnName, final BigDecimal defaultValue)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return NumberUtils.asBigDecimal(valueObj, defaultValue);
		}

		return defaultValue;
	}

	private final Timestamp getValueAsTSOrNull(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Timestamp valueDate = (Timestamp)po.get_Value(index);
			return valueDate;
		}

		return null;
	}

	private final String getValueAsString(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return valueObj != null ? valueObj.toString() : null;
		}

		return null;
	}

	/**
	 * Set ReversalLine_ID
	 * store original (voided/reversed) document line
	 *
	 * @param ReversalLine_ID
	 */
	public final void setReversalLine_ID(final int ReversalLine_ID)
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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", get_ID())
				.add("description", getDescription())
				.add("productId", getM_Product_ID())
				.add("qty", m_qty)
				.add("amtSource", getAmtSource())
				.toString();
	}

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
		_taxIncluded = taxIncluded;
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
	protected final DT getDoc()
	{
		return m_doc;
	}

	public final PostingException newPostingException()
	{
		return m_doc.newPostingException()
				.setDocument(getDoc())
				.setDocLine(this);
	}

	public final PostingException newPostingException(final Throwable ex)
	{
		return m_doc.newPostingException(ex)
				.setDocument(getDoc())
				.setDocLine(this);
	}
}
