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
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.IntFunction;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.MCharge;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.acct.tax.TaxAcctType;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import de.metas.util.Optionals;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

/**
 * Standard Document Line
 *
 * @author Jorg Janke
 * @author Armen Rizal, Goodwill Consulting
 * <li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 * @version $Id: DocLine.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class DocLine<DT extends Doc<? extends DocLine<?>>>
{
	// services
	private final Logger logger = LogManager.getLogger(getClass());
	protected final AcctDocRequiredServicesFacade services;

	/**
	 * Persistent Object
	 */
	private final PO p_po;
	/**
	 * Parent
	 */
	private final DT m_doc;

	/**
	 * Qty
	 */
	private Quantity qty = null;

	// -- GL Amounts
	/**
	 * Debit Journal Amt
	 */
	private BigDecimal m_AmtSourceDr = BigDecimal.ZERO;
	/**
	 * Credit Journal Amt
	 */
	private BigDecimal m_AmtSourceCr = BigDecimal.ZERO;
	/**
	 * Net Line Amt
	 */
	private BigDecimal m_LineNetAmt = null;
	/**
	 * List Amount
	 */
	private BigDecimal m_ListAmt = BigDecimal.ZERO;
	/**
	 * Discount Amount
	 */
	private BigDecimal m_DiscountAmt = BigDecimal.ZERO;

	/**
	 * Converted Amounts
	 */
	private BigDecimal m_AmtAcctDr = null;
	private BigDecimal m_AmtAcctCr = null;

	private I_M_Product _product; // lazy
	private Boolean _productIsItem = null; // lazy

	private LocalDate m_DateAcct = null;
	private LocalDate m_DateDoc = null;
	private int m_C_SalesRegion_ID = -1;
	private Optional<BPartnerId> _bpartnerId;
	private final LocationId locationFromId = null;
	private final LocationId locationToId = null;
	private Optional<CurrencyId> _currencyId;
	private Optional<CurrencyConversionTypeId> currencyConversionTypeIdHolder;
	private int m_C_Period_ID = -1;
	private Optional<TaxId> _taxId = null;
	private boolean _taxIncluded = false;

	private int m_ReversalLine_ID = 0;

	public DocLine(
			@NonNull final PO linePO,
			@NonNull final DT doc)
	{
		this.services = doc.getServices();
		p_po = linePO;
		m_doc = doc;

		//
		// Document Consistency
		if (p_po.getAD_Org_ID() <= 0)
		{
			p_po.setAD_Org_ID(m_doc.getOrgId().getRepoId());
		}
	}    // DocLine

	protected final ClientId getClientId()
	{
		return m_doc.getClientId();
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

	public final CurrencyId getCurrencyId()
	{
		if (_currencyId == null)
		{
			final CurrencyId lineCurrencyId = CurrencyId.ofRepoIdOrNull(getValue("C_Currency_ID"));
			if (lineCurrencyId != null)
			{
				_currencyId = Optional.of(lineCurrencyId);
			}
			else
			{
				_currencyId = Optional.ofNullable(m_doc.getCurrencyId());
			}
		}

		return _currencyId.orElse(null);
	}

	public final CurrencyConversionTypeId getCurrencyConversionTypeId()
	{
		Optional<CurrencyConversionTypeId> currencyConversionTypeIdHolder = this.currencyConversionTypeIdHolder;
		if (currencyConversionTypeIdHolder == null)
		{
			CurrencyConversionTypeId currencyConversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(getValue("C_ConversionType_ID"));
			if (currencyConversionTypeId == null)
			{
				currencyConversionTypeId = m_doc.getCurrencyConversionTypeId();
			}

			currencyConversionTypeIdHolder = this.currencyConversionTypeIdHolder = Optional.ofNullable(currencyConversionTypeId);
		}
		return currencyConversionTypeIdHolder.orElse(null);
	}

	protected final void setC_ConversionType_ID(final int C_ConversionType_ID)
	{
		currencyConversionTypeIdHolder = CurrencyConversionTypeId.optionalOfRepoId(C_ConversionType_ID);
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

	/**
	 * Set Product Amounts
	 *
	 * @param LineNetAmt Line Net Amt
	 * @param PriceList  Price List
	 * @param Qty        Qty for discount calc
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

	/**
	 * Line Account from Product (or Charge).
	 *
	 * @param acctType see ProductCost.ACCTTYPE_* (0..3)
	 * @param as       Accounting schema
	 * @return Requested Product Account
	 */
	@NonNull
	@OverridingMethodsMustInvokeSuper
	public MAccount getAccount(@NonNull final ProductAcctType acctType, @NonNull final AcctSchema as)
	{
		//
		// Charge account
		if (getProductId() == null && getC_Charge_ID() > 0)
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
				throw newPostingException().setAcctSchema(as).setDetailMessage("No Charge Account for account type: " + acctType);
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

	public final LocalDate getDateAcct()
	{
		if (m_DateAcct == null)
		{
			m_DateAcct = CoalesceUtil.coalesceSuppliers(
					() -> getValueAsLocalDateOrNull("DateAcct"),
					() -> getDoc().getDateAcct());
		}
		return m_DateAcct;
	}

	protected final void setDateDoc(final LocalDate dateDoc)
	{
		m_DateDoc = dateDoc;
	}   // setDateDoc

	public final LocalDate getDateDoc()
	{
		if (m_DateDoc == null)
		{
			m_DateDoc = CoalesceUtil.coalesceSuppliers(
					() -> getValueAsLocalDateOrNull("DateDoc"),
					() -> getValueAsLocalDateOrNull("DateTrx"),
					() -> getDoc().getDateAcct());
		}
		return m_DateDoc;
	}

	@NonNull
	private MAccount getProductAccount(final ProductAcctType acctType, final AcctSchema as)
	{
		//
		// Product Revenue: check/use the override defined on tax level
		if (acctType == ProductAcctType.Revenue)
		{
			final MAccount productRevenueAcct = getTaxAccount(TaxAcctType.ProductRevenue_Override, as.getId()).orElse(null);
			if (productRevenueAcct != null)
			{
				return productRevenueAcct;
			}
		}

		// No Product - get Default from Product Category
		final ProductId productId = getProductId();
		if (productId == null)
		{
			return getAccountDefault(acctType, as);
		}

		final AccountId accountId = services.getProductAcct(as.getId(), productId, acctType).orElse(null);
		if (accountId == null)
		{
			final String productName = services.getProductName(productId);
			throw newPostingException().setAcctSchema(as).setDetailMessage("No Product Account for account type " + acctType + " and product " + productName);
		}

		return services.getAccountById(accountId);
	}

	/**
	 * Account from Default Product Category
	 * @param as       accounting schema
	 * @return Requested Product Account
	 */
	private final MAccount getAccountDefault(final ProductAcctType acctType, final AcctSchema as)
	{
		final AccountId accountId = services.getProductDefaultAcct(as.getId(), acctType).orElse(null);
		if (accountId == null)
		{
			throw newPostingException().setAcctSchema(as).setDetailMessage("No Default Account for account type: " + acctType);
		}

		return services.getAccountById(accountId);
	}

	private final Optional<MAccount> getTaxAccount(@NonNull final TaxAcctType taxAcctType, final AcctSchemaId acctSchemaId)
	{
		final TaxId taxId = getTaxId().orElse(null);
		return services.getTaxAccount(acctSchemaId, taxId, taxAcctType);
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
	 * @param as     account schema
	 * @param amount amount for expense(+)/revenue(-)
	 * @return Charge Account or null
	 */
	protected final MAccount getChargeAccount(final AcctSchema as, final BigDecimal amount)
	{
		final int C_Charge_ID = getC_Charge_ID();
		if (C_Charge_ID <= 0)
		{
			return null;
		}
		return MCharge.getAccount(C_Charge_ID, as.getId(), amount);
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
	}    // getC_Period_ID

	protected final void setC_Period_ID(final int C_Period_ID)
	{
		m_C_Period_ID = C_Period_ID;
	}

	public final int get_ID()
	{
		return getPO().get_ID();
	}

	public final OrgId getOrgId()
	{
		return OrgId.ofRepoId(getPO().getAD_Org_ID());
	}

	public final ProductId getProductId()
	{
		return ProductId.ofRepoIdOrNull(getValue("M_Product_ID"));
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
		return services.isProductStocked(product);
	}

	public final boolean isService()
	{
		return !isItem();
	}

	public final CostingMethod getProductCostingMethod(final AcctSchema as)
	{
		return services.getCostingMethod(getProductId(), as);
	}

	public final CostingLevel getProductCostingLevel(final AcctSchema as)
	{
		return services.getCostingLevel(getProductId(), as);
	}

	public final AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.ofRepoIdOrNone(getValue("M_AttributeSetInstance_ID"));
	}

	public final int getM_Locator_ID()
	{
		return getValue("M_Locator_ID");
	}

	public final OrderLineId getOrderLineId()
	{
		return OrderLineId.ofRepoIdOrNull(getValue("C_OrderLine_ID"));
	}

	public final LocationId getLocationFromId()
	{
		return locationFromId;
	}

	public final LocationId getLocationToId()
	{
		return locationToId;
	}

	/**
	 * @return product or null if no product
	 */
	private final I_M_Product getProduct()
	{
		if (_product == null)
		{
			final ProductId productId = getProductId();
			if (productId != null)
			{
				_product = services.getProductById(productId);
			}
		}
		return _product;
	}

	protected final I_C_UOM getProductStockingUOM()
	{
		return services.getProductStockingUOM(getProductId());
	}

	protected final UomId getProductStockingUOMId()
	{
		return services.getProductStockingUOMId(getProductId());
	}

	/**
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
		if (getProductId() != null)
		{
			final I_C_UOM uom = getProductStockingUOM();
			return uom.getC_UOM_ID();
		}

		return -1;
	}

	/**
	 * Quantity
	 *
	 * @param quantity transaction Qty
	 * @param isSOTrx  SL order trx (i.e. negative qty)
	 */
	protected final void setQty(@NonNull final Quantity quantity, final boolean isSOTrx)
	{
		if (isSOTrx)
		{
			setQty(quantity.negate());
		}
		else
		{
			setQty(quantity);
		}
	}

	protected final void setQty(@NonNull final Quantity quantity)
	{
		this.qty = quantity;
	}

	/**
	 * Quantity
	 *
	 * @return transaction Qty
	 */
	public final Quantity getQty()
	{
		return qty;
	}   // getQty

	public final String getDescription()
	{
		return getValueAsString("Description");
	}

	public final Optional<TaxId> getTaxId()
	{
		Optional<TaxId> taxId = this._taxId;
		if (taxId == null)
		{
			taxId = this._taxId = TaxId.optionalOfRepoId(getValue("C_Tax_ID"));
		}
		return taxId;
	}

	public final void setTaxId(@Nullable final TaxId taxId)
	{
		_taxId = Optional.ofNullable(taxId);
	}

	public final int getLine()
	{
		return getValue("Line");
	}

	public BPartnerId getBPartnerId()
	{
		if (_bpartnerId == null)
		{
			_bpartnerId = Optionals.firstPresentOfSuppliers(
					() -> getValueAsOptionalId("C_BPartner_ID", BPartnerId::ofRepoIdOrNull),
					() -> Optional.ofNullable(m_doc.getBPartnerId()));
		}
		return _bpartnerId.orElse(null);
	}

	protected final void setBPartnerId(final BPartnerId bpartnerId)
	{
		_bpartnerId = Optional.ofNullable(bpartnerId);
	}

	private final int getC_BPartner_Location_ID()
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> getValue("C_BPartner_Location_ID"),
				() -> m_doc.getC_BPartner_Location_ID());
	}

	public final OrgId getOrgTrxId()
	{
		return getValueAsIdOrNull("AD_OrgTrx_ID", OrgId::ofRepoIdOrNull);
	}

	/**
	 * Get SalesRegion.
	 * - get Sales Region from BPartner
	 *
	 * @return C_SalesRegion_ID
	 */
	public final int getC_SalesRegion_ID()
	{
		if (m_C_SalesRegion_ID == -1)    // never tried
		{
			final int bpartnerLocationId = getC_BPartner_Location_ID();
			if (bpartnerLocationId > 0)
			// && m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				final String sql = "SELECT COALESCE(C_SalesRegion_ID,0) FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
				m_C_SalesRegion_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, bpartnerLocationId);
				if (m_C_SalesRegion_ID == 0)
				{
					m_C_SalesRegion_ID = -2;    // don't try again
				}
			}
			else
			{
				m_C_SalesRegion_ID = -2;        // don't try again
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

	public final ActivityId getActivityFromId()
	{
		return ActivityId.ofRepoIdOrNull(getValue("C_ActivityFrom_ID"));
	}

	public ActivityId getActivityId()
	{
		return ActivityId.ofRepoIdOrNull(getValue("C_Activity_ID"));
	}

	public final int getUser1_ID()
	{
		return getValue("User1_ID");
	}

	public final int getUser2_ID()
	{
		return getValue("User2_ID");
	}

	private final <T extends RepoIdAware> T getValueAsIdOrNull(final String columnName, final IntFunction<T> idOrNullMapper)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index < 0)
		{
			return null;
		}

		final Object valueObj = po.get_Value(index);
		final Integer valueInt = NumberUtils.asInteger(valueObj, null);
		if (valueInt == null)
		{
			return null;
		}

		final T id = idOrNullMapper.apply(valueInt);
		return id;
	}

	private final <T extends RepoIdAware> Optional<T> getValueAsOptionalId(final String columnName, final IntFunction<T> idOrNullMapper)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index < 0)
		{
			return Optional.empty();
		}

		final Object valueObj = po.get_Value(index);
		final Integer valueInt = NumberUtils.asInteger(valueObj, null);
		if (valueInt == null)
		{
			return Optional.empty();
		}

		final T id = idOrNullMapper.apply(valueInt);
		if (id == null)
		{
			return Optional.empty();
		}

		return Optional.of(id);
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

	@Nullable final BigDecimal getValueAsBD(final String columnName, @Nullable final BigDecimal defaultValue)
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

	private final LocalDate getValueAsLocalDateOrNull(final String columnName)
	{
		final PO po = getPO();
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			return TimeUtil.asLocalDate(po.get_Value(index));
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
	 * @return get original (voided/reversed) document line
	 */
	public final int getReversalLine_ID()
	{
		return m_ReversalLine_ID;
	}

	/**
	 * @return is the reversal document line (not the original)
	 */
	public final boolean isReversalLine()
	{
		return m_ReversalLine_ID > 0 // has a reversal counterpart
				&& get_ID() > m_ReversalLine_ID; // this document line was created after it's reversal counterpart
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", get_ID())
				.add("description", getDescription())
				.add("productId", getProductId())
				.add("qty", getQty())
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
	}    // isTaxIncluded

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

	/**
	 * @return document (header)
	 */
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
