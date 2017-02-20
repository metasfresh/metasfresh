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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;

/**
 * Order Line Model. <code>
 * 			MOrderLine ol = new MOrderLine(m_order);
			ol.setM_Product_ID(wbl.getM_Product_ID());
			ol.setQtyOrdered(wbl.getQuantity());
			ol.setPrice();
			ol.setPriceActual(wbl.getPrice());
			ol.setTax();
			ol.save();

 *	</code>
 *
 * @author Jorg Janke
 * @version $Id: MOrderLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <ul>
 *         <li>BF [ 2588043 ] Insufficient message ProductNotOnPriceList
 * @author Michael Judd, www.akunagroup.com
 *         <ul>
 *         <li>BF [ 1733602 ] Price List including Tax Error - when a user changes the orderline or invoice line for a product on a price list that includes tax, the net amount is incorrectly
 *         calculated.
 */
public class MOrderLine extends X_C_OrderLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7305265800857547603L;

	public static final String MSG_PriceListVersionInvalid = "PriceListVersionInvalid";

	/**
	 * Get Order Unreserved Qty
	 *
	 * @param ctx context
	 * @param M_Warehouse_ID wh
	 * @param M_Product_ID product
	 * @param M_AttributeSetInstance_ID asi
	 * @param excludeC_OrderLine_ID exclude C_OrderLine_ID
	 * @return Unreserved Qty
	 */
	public static BigDecimal getNotReserved(Properties ctx, int M_Warehouse_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID, int excludeC_OrderLine_ID)
	{
		BigDecimal retValue = BigDecimal.ZERO;
		String sql = "SELECT SUM(ol.QtyOrdered-ol.QtyDelivered-ol.QtyReserved) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE ol.M_Warehouse_ID=?"	// #1
				// metas: adding table alias "ol" to M_Product_ID to distinguish it from C_Order's M_Product_ID
				+ " AND ol.M_Product_ID=?" // #2
				+ " AND o.IsSOTrx='Y' AND o.DocStatus='DR'"
				+ " AND ol.QtyOrdered-ol.QtyDelivered-ol.QtyReserved<>0"
				+ " AND ol.C_OrderLine_ID<>?";
		if (M_AttributeSetInstance_ID != 0)
			sql += " AND ol.M_AttributeSetInstance_ID=?";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			pstmt.setInt(3, excludeC_OrderLine_ID);
			if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt(4, M_AttributeSetInstance_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getBigDecimal(1);
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (retValue == null)
			s_log.debug("-");
		else
			s_log.debug(retValue.toString());
		return retValue;
	}	// getNotReserved

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MOrderLine.class);

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_OrderLine_ID order line to load
	 * @param trxName trx name
	 */
	public MOrderLine(Properties ctx, int C_OrderLine_ID, String trxName)
	{
		super(ctx, C_OrderLine_ID, trxName);
		if (C_OrderLine_ID == 0)
		{
			// setC_Order_ID (0);
			// setLine (0);
			// setM_Warehouse_ID (0); // @M_Warehouse_ID@
			// setC_BPartner_ID(0);
			// setC_BPartner_Location_ID (0); // @C_BPartner_Location_ID@
			// setC_Currency_ID (0); // @C_Currency_ID@
			// setDateOrdered (new Timestamp(System.currentTimeMillis())); // @DateOrdered@
			//
			// setC_Tax_ID (0);
			// setC_UOM_ID (0);
			//
			setFreightAmt(BigDecimal.ZERO);
			setLineNetAmt(BigDecimal.ZERO);
			//
			setPriceEntered(BigDecimal.ZERO);
			setPriceActual(BigDecimal.ZERO);
			setPriceLimit(BigDecimal.ZERO);
			setPriceList(BigDecimal.ZERO);
			//
			setM_AttributeSetInstance_ID(0);
			//
			setQtyEntered(BigDecimal.ZERO);
			setQtyOrdered(BigDecimal.ZERO);	// 1
			setQtyDelivered(BigDecimal.ZERO);
			setQtyInvoiced(BigDecimal.ZERO);
			// task 09358: get rid of this; instead, update qtyReserved at one central place
			// setQtyReserved(BigDecimal.ZERO);
			//
			setIsDescription(false);	// N
			setProcessed(false);
			setLine(0);
		}
	}	// MOrderLine

	/**
	 * Parent Constructor.
	 * <ul>
	 * <li>ol.setM_Product_ID(wbl.getM_Product_ID());
	 * <li>ol.setQtyOrdered(wbl.getQuantity());
	 * <li>ol.setPrice();
	 * <li>ol.setPriceActual(wbl.getPrice());
	 * <li>ol.setTax();
	 * <li>ol.save();
	 *
	 * @param order parent order
	 */
	public MOrderLine(MOrder order)
	{
		this(order.getCtx(), 0, order.get_TrxName());
		if (order.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setC_Order_ID(order.getC_Order_ID());	// parent
		setOrder(order);
	}	// MOrderLine

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set record
	 * @param trxName transaction
	 */
	public MOrderLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MOrderLine

	private int m_M_PriceList_ID = 0;
	//
	private boolean m_IsSOTrx = true;
	// Product Pricing
	private MProductPricing m_productPrice = null;

	/** Tax */
	private MTax m_tax = null;

	/** Cached Currency Precision */
	private Integer m_precision = null;
	/** Product */
	private MProduct m_product = null;
	/** Charge */
	private MCharge m_charge = null;

	/**
	 * Set Defaults from Order. Does not set Parent !!
	 *
	 * @param order order
	 */
	public void setOrder(MOrder order)
	{
		setClientOrg(order);
		final boolean isDropShip = order.isDropShip();
		final int C_BPartner_ID = isDropShip && order.getDropShip_BPartner_ID() > 0 ? order.getDropShip_BPartner_ID() : order.getC_BPartner_ID();
		setC_BPartner_ID(C_BPartner_ID);

		final int C_BPartner_Location_ID = isDropShip && order.getDropShip_Location_ID() > 0 ? order.getDropShip_Location_ID() : order.getC_BPartner_Location_ID();
		setC_BPartner_Location_ID(C_BPartner_Location_ID);

		// metas: begin: copy AD_User_ID
		final de.metas.interfaces.I_C_OrderLine oline = InterfaceWrapperHelper.create(this, de.metas.interfaces.I_C_OrderLine.class);
		final int AD_User_ID = isDropShip && order.getDropShip_User_ID() > 0 ? order.getDropShip_User_ID() : order.getAD_User_ID();
		oline.setAD_User_ID(AD_User_ID);
		// metas: end

		oline.setM_PriceList_Version_ID(0); // the current PLV might be add or'd with the new order's PL.

		setM_Warehouse_ID(order.getM_Warehouse_ID());
		setDateOrdered(order.getDateOrdered());
		setDatePromised(order.getDatePromised());
		setC_Currency_ID(order.getC_Currency_ID());
		//
		setHeaderInfo(order);	// sets m_order
		// Don't set Activity, etc as they are overwrites
	}	// setOrder

	/**
	 * Set Header Info
	 *
	 * @param order order
	 */
	public void setHeaderInfo(final MOrder order)
	{
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		m_precision = orderBL.getPrecision(order);
		m_M_PriceList_ID = orderBL.retrievePriceListId(order);
		m_IsSOTrx = order.isSOTrx();
	}	// setHeaderInfo

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public MOrder getParent()
	{
		return LegacyAdapters.convertToPO(getC_Order());
	}	// getParent

	/**
	 * Set Price Entered/Actual. Use this Method if the Line UOM is the Product UOM
	 *
	 * @param PriceActual price
	 */
	public void setPrice(BigDecimal PriceActual)
	{
		setPriceEntered(PriceActual);
		setPriceActual(PriceActual);
	}	// setPrice

	/**
	 * Set Price Actual. (actual price is not updateable)
	 *
	 * @param PriceActual actual price
	 */
	@Override
	public void setPriceActual(BigDecimal PriceActual)
	{
		if (PriceActual == null)
			throw new IllegalArgumentException("PriceActual is mandatory");
		set_ValueNoCheck("PriceActual", PriceActual);
	}	// setPriceActual

	/**
	 * Set Price for Product and PriceList. Use only if newly created.
	 */
	public void setPrice()
	{
		if (getM_Product_ID() <= 0)
		{
			return;
		}
		if (m_M_PriceList_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @M_Pricelist_ID@ @C_BPartner_ID@ " + getC_BPartner().getName());
		}
		setPrice(m_M_PriceList_ID);
	}	// setPrice

	/**
	 * Set Price for Product and PriceList
	 *
	 * @param M_PriceList_ID price list
	 */
	public void setPrice(int INGORED)
	{
		if (getM_Product_ID() <= 0)
		{
			return;
		}
		//
		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(this, de.metas.interfaces.I_C_OrderLine.class);
		Services.get(IOrderLineBL.class).updatePrices(ol);
	}	// setPrice

	/**
	 * Get and calculate Product Pricing
	 *
	 * @param M_PriceList_ID id
	 * @param M_PriceList_Version_ID
	 * @return product pricing
	 */
	private MProductPricing getProductPricing(int M_PriceList_ID, int M_PriceList_Version_ID)
	{
		final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(getCtx(), M_PriceList_Version_ID, I_M_PriceList_Version.class, get_TrxName());
		if (M_PriceList_Version_ID > 0)
		{
			// If we have a pricelist version, make sure it belongs to the pricelist
			Check.assume(M_PriceList_ID == plv.getM_PriceList_ID(), Msg.getMsg(getCtx(), MSG_PriceListVersionInvalid));
		}

		m_productPrice = new MProductPricing(getM_Product_ID(), getC_BPartner_ID(), getQtyOrdered(), m_IsSOTrx);

		m_productPrice.setReferencedObject(this); // 03152: setting the 'ol' to allow the subscription system to compute the right price
		m_productPrice.setPriceDate(getDatePromised()); // important: need to use the data when the service will be provided, so we make sure that we get the right PLV
		m_productPrice.setM_PriceList_ID(M_PriceList_ID);
		m_productPrice.setPriceDate(getDateOrdered());
		m_productPrice.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		//
		m_productPrice.calculatePrice();
		return m_productPrice;
	}	// getProductPrice

	/**
	 * Set Tax
	 *
	 * @return true if tax is set
	 */
	public boolean setTax()
	{
		// metas: we need to fetch the Tax based on pricing tax category and not directly

		// int ii = Tax.get(getCtx(), getM_Product_ID(), getC_Charge_ID(), getDateOrdered(), getDateOrdered(),
		// getAD_Org_ID(), Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this).getM_Warehouse_ID(),
		// getC_BPartner_Location_ID(), // should be bill to
		// getC_BPartner_Location_ID(), m_IsSOTrx);

		final int taxCategoryId = Services.get(IOrderLineBL.class).getC_TaxCategory_ID(this);
		if (taxCategoryId <= 0)
		{
			log.error("No Tax Category found");
			return false;
		}

		final I_M_Warehouse warehouse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this);
		final I_C_Location locationFrom = Services.get(IWarehouseBL.class).getC_Location(warehouse);
		final int countryFromId = locationFrom.getC_Country_ID();
		final int taxId = Services.get(ITaxBL.class).retrieveTaxIdForCategory(
				getCtx(),
				countryFromId,
				getAD_Org_ID(),
				getC_BPartner_Location(),		// should be bill to
				getDateOrdered(),
				taxCategoryId,
				m_IsSOTrx,
				get_TrxName(),
				true); // throwEx

		if (taxId <= 0)
		{
			log.error("No Tax found");
			return false;
		}
		setC_Tax_ID(taxId);

		final I_C_Tax tax = InterfaceWrapperHelper.create(getCtx(), taxId, I_C_Tax.class, ITrx.TRXNAME_None);

		final I_C_TaxCategory taxCategory = tax.getC_TaxCategory();
		setC_TaxCategory(taxCategory);

		return true;
	}	// setTax

	/**
	 * Calculate Extended Amt. May or may not include tax
	 */
	public void setLineNetAmt()
	{
		BigDecimal bd = getPriceActual().multiply(getQtyOrdered());
		// metas: tsa: begin: 01459
		// Line Net Amt shall be zero if the line is not active
		if (!isActive())
		{
			bd = BigDecimal.ZERO;
		}
		// metas: tsa: end: 01459

		final boolean documentLevel = getTax().isDocumentLevel();
		final boolean isTaxIncluded = Services.get(IOrderLineBL.class).isTaxIncluded(this);
		final int taxPrecision = Services.get(IOrderLineBL.class).getPrecision(this);

		// juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		// http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded && !documentLevel)
		{
			BigDecimal taxStdAmt = BigDecimal.ZERO, taxThisAmt = BigDecimal.ZERO;

			MTax orderTax = getTax();
			MTax stdTax = null;

			// get the standard tax
			if (getProduct() == null)
			{
				if (getCharge() != null)	// Charge
				{
					stdTax = new MTax(getCtx(),
							((MTaxCategory)getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
							get_TrxName());
				}
			}
			else
			// Product
			{
				// FIXME metas 05129 need proper concept (link between M_Product and C_TaxCategory_ID was removed!!!!!)
				throw new AdempiereException("Unsupported tax calculation when tax is included, but it's not on document level");
				// stdTax = new MTax (getCtx(),
				// ((MTaxCategory) getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
				// get_TrxName());
			}

			if (stdTax != null)
			{
				log.debug("stdTax rate is " + stdTax.getRate());
				log.debug("orderTax rate is " + orderTax.getRate());

				final ITaxBL taxBL = Services.get(ITaxBL.class);
				taxThisAmt = taxThisAmt.add(taxBL.calculateTax(orderTax, bd, isTaxIncluded, taxPrecision));
				taxStdAmt = taxStdAmt.add(taxBL.calculateTax(stdTax, bd, isTaxIncluded, taxPrecision));

				bd = bd.subtract(taxStdAmt).add(taxThisAmt);

				log.debug("Price List includes Tax and Tax Changed on Order Line: New Tax Amt: "
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);
			}

		}

		if (bd.scale() > taxPrecision)
			bd = bd.setScale(taxPrecision, BigDecimal.ROUND_HALF_UP);
		super.setLineNetAmt(bd);
	}	// setLineNetAmt

	/**
	 * Get Charge
	 *
	 * @return product or null
	 */
	public MCharge getCharge()
	{
		if (m_charge == null && getC_Charge_ID() != 0)
			m_charge = MCharge.get(getCtx(), getC_Charge_ID());
		return m_charge;
	}

	/**
	 * Get Tax
	 *
	 * @return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	// getTax

	/**
	 * Get Currency Precision from Currency
	 *
	 * @return precision
	 */
	public int getPrecision()
	{
		if (m_precision != null)
		{
			return m_precision;
		}

		//
		if (getC_Currency_ID() == 0)
		{
			setOrder(getParent());
			if (m_precision != null)
				return m_precision;
		}
		if (getC_Currency_ID() > 0)
		{
			final I_C_Currency cur = Services.get(ICurrencyDAO.class).retrieveCurrency(getCtx(), getC_Currency_ID());
			if (cur.getC_Currency_ID() != 0)
			{
				m_precision = cur.getStdPrecision();
				return m_precision;
			}
		}

		//
		// Fallback
		// FIXME: drop this, i guess is not used AT ALL
		final String sql = "SELECT c.StdPrecision "
				+ "FROM C_Currency c INNER JOIN C_Order x ON (x.C_Currency_ID=c.C_Currency_ID) "
				+ "WHERE x.C_Order_ID=?";
		m_precision = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		return m_precision;
	}	// getPrecision

	/**
	 * Set Product
	 *
	 * @param product product
	 */
	public void setProduct(MProduct product)
	{
		Services.get(IOrderLineBL.class).setM_Product_ID(
				InterfaceWrapperHelper.create(this, de.metas.interfaces.I_C_OrderLine.class),
				product.getM_Product_ID(),
				true);
	}	// setProduct

	/**
	 * Set M_Product_ID
	 *
	 * @param M_Product_ID product
	 * @param setUOM set also UOM
	 */
	public void setM_Product_ID(int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID(M_Product_ID);
		setM_AttributeSetInstance_ID(0);
	}	// setM_Product_ID

	/**
	 * Set Product and UOM
	 *
	 * @param M_Product_ID product
	 * @param C_UOM_ID uom
	 */
	public void setM_Product_ID(int M_Product_ID, int C_UOM_ID)
	{
		super.setM_Product_ID(M_Product_ID);
		if (C_UOM_ID != 0)
			super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
	}	// setM_Product_ID

	/**
	 * Get Product
	 *
	 * @return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product = MProduct.get(getCtx(), getM_Product_ID());
		return m_product;
	}	// getProduct

	/**
	 * Set M_AttributeSetInstance_ID
	 *
	 * @param M_AttributeSetInstance_ID id
	 */
	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)		// 0 is valid ID
			set_Value("M_AttributeSetInstance_ID", new Integer(0));
		else
			super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}	// setM_AttributeSetInstance_ID

	/**
	 * Set Warehouse
	 *
	 * @param M_Warehouse_ID warehouse
	 */
	@Override
	public void setM_Warehouse_ID(int M_Warehouse_ID)
	{
		if (getM_Warehouse_ID() > 0
				&& getM_Warehouse_ID() != M_Warehouse_ID
				&& !canChangeWarehouse(false) // trowEx=false for legacy purposes. We need to evaluate and consider to throw exception
		)
		{
			log.error("Ignored - Already Delivered/Invoiced/Reserved");
		}
		else
		{
			super.setM_Warehouse_ID(M_Warehouse_ID);
		}
	}	// setM_Warehouse_ID

	/**
	 * Can Change Warehouse
	 *
	 * @param throwEx if <code>true</code> an exception will be thrown in case something is not valid
	 * @return true if warehouse can be changed
	 */
	public final boolean canChangeWarehouse(final boolean throwEx)
	{
		if (getQtyDelivered().signum() != 0)
		{
			return new AdempiereException("@CannotChangeWarehouse@ @QtyDelivered@=" + getQtyDelivered())
					.throwOrLogSevere(throwEx, log);
		}
		if (getQtyInvoiced().signum() != 0)
		{
			return new AdempiereException("@CannotChangeWarehouse@ @QtyInvoiced@=" + getQtyInvoiced())
					.throwOrLogSevere(throwEx, log);
		}
		if (getQtyReserved().signum() != 0)
		{
			return new AdempiereException("@CannotChangeWarehouse@ @QtyReserved@=" + getQtyReserved())
					.throwOrLogSevere(throwEx, log);
		}
		// We can change
		return true;
	}	// canChangeWarehouse

	/**
	 * Get C_Project_ID
	 *
	 * @return project
	 */
	@Override
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID();
		if (ii == 0)
			ii = getParent().getC_Project_ID();
		return ii;
	}	// getC_Project_ID

	/**
	 * Get C_Activity_ID
	 *
	 * @return Activity
	 */
	@Override
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID();
		if (ii == 0)
			ii = getParent().getC_Activity_ID();
		return ii;
	}	// getC_Activity_ID

	/**
	 * Get C_Campaign_ID
	 *
	 * @return Campaign
	 */
	@Override
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID();
		if (ii == 0)
			ii = getParent().getC_Campaign_ID();
		return ii;
	}	// getC_Campaign_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser1_ID()
	{
		int ii = super.getUser1_ID();
		if (ii == 0)
			ii = getParent().getUser1_ID();
		return ii;
	}	// getUser1_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser2_ID()
	{
		int ii = super.getUser2_ID();
		if (ii == 0)
			ii = getParent().getUser2_ID();
		return ii;
	}	// getUser2_ID

	/**
	 * Get AD_OrgTrx_ID
	 *
	 * @return trx org
	 */
	@Override
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
			ii = getParent().getAD_OrgTrx_ID();
		return ii;
	}	// getAD_OrgTrx_ID

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MOrderLine[")
				.append(get_ID())
				.append(", Line=").append(getLine())
				.append(", Ordered=").append(getQtyOrdered())
				.append(", Delivered=").append(getQtyDelivered())
				.append(", Invoiced=").append(getQtyInvoiced())
				.append(", Reserved=").append(getQtyReserved())
				.append(", LineNet=").append(getLineNetAmt())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	/**
	 * Get Description Text. For jsp access (vs. isDescription)
	 *
	 * @return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}	// getDescriptionText

	/**
	 * Get Name
	 *
	 * @return get the name of the line (from Product)
	 */
	public String getName()
	{
		getProduct();
		if (m_product != null)
			return m_product.getName();
		if (getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(getCtx(), getC_Charge_ID());
			return charge.getName();
		}
		return "";
	}	// getName

	/**
	 * Set C_Charge_ID
	 *
	 * @param C_Charge_ID charge
	 */
	@Override
	public void setC_Charge_ID(int C_Charge_ID)
	{
		super.setC_Charge_ID(C_Charge_ID);
		if (C_Charge_ID > 0)
			set_ValueNoCheck("C_UOM_ID", null);
	}	// setC_Charge_ID

	/**
	 * Set Discount
	 */
	public void setDiscount()
	{
		BigDecimal list = getPriceList();
		// No List Price
		if (BigDecimal.ZERO.compareTo(list) == 0)
			return;
		// final int precision = getPrecision();
		final int precision = 1; // metas
		// TODO: metas: why we are using precision=1 instead of getPrecision()?
		BigDecimal discount = list.subtract(getPriceActual())
				.multiply(new BigDecimal(100))
				.divide(list, precision, BigDecimal.ROUND_HALF_UP);
		setDiscount(discount);
	}	// setDiscount

	/**
	 * Set Qty Entered/Ordered. Use this Method if the Line UOM is the Product UOM
	 *
	 * @param Qty QtyOrdered/Entered
	 */
	public void setQty(BigDecimal Qty)
	{
		super.setQtyEntered(Qty);
		super.setQtyOrdered(getQtyEntered());
	}	// setQty

	/**
	 * Set Qty Entered - enforce entered UOM
	 *
	 * @param QtyEntered
	 */
	@Override
	public void setQtyEntered(BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered(QtyEntered);
	}	// setQtyEntered

	/**
	 * Set Qty Ordered - enforce Product UOM
	 *
	 * @param QtyOrdered
	 */
	@Override
	public void setQtyOrdered(BigDecimal QtyOrdered)
	{
		MProduct product = getProduct();
		if (QtyOrdered != null && product != null)
		{
			int precision = product.getUOMPrecision();
			QtyOrdered = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyOrdered(QtyOrdered);
	}	// setQtyOrdered

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return true if it can be saved
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{

		final boolean complete = getParent().isComplete();
		if (newRecord && complete)
		{
			throw new AdempiereException("@ParentComplete@ @C_OrderLine_ID@");
		}

		// In case our order is complete do nothing, don't update any field
		if (complete)
		{
			// TODO: make sure that only QtyDelivered, QtyInvoiced fields are updated.
			// The rest shall be forbidden.
			// NOTE: also please check if those are the only fields which are updated after an order is completed
			return true;
		}

		// Get Defaults from Parent
		final I_M_Warehouse warehouse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this);
		if (getC_BPartner_ID() <= 0 || getC_BPartner_Location_ID() <= 0
				|| warehouse == null || warehouse.getM_Warehouse_ID() <= 0
				|| getC_Currency_ID() <= 0)
		{
			setOrder(getParent());
		}
		
		// metas: try to get the pl-id from our plv
		if (m_M_PriceList_ID <= 0)
		{
			final int plvId = get_ValueAsInt(de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_PriceList_Version_ID);
			if (plvId > 0)
			{
				m_M_PriceList_ID = DB.getSQLValueEx(get_TrxName(), "SELECT M_PriceList_ID FROM M_PriceList_Version WHERE M_PriceList_Version_ID=" + plvId);
			}
		}
		// metas: end
		if (m_M_PriceList_ID <= 0)
		{
			setHeaderInfo(getParent());
		}
		// R/O Check - Product/Warehouse Change
		if (!newRecord
				&& (is_ValueChanged("M_Product_ID") || is_ValueChanged("M_Warehouse_ID")))
		{
			if (!canChangeWarehouse(true))
				return false;
		}	// Product Changed

		// Charge
		if (getC_Charge_ID() != 0 && getM_Product_ID() != 0)
			setM_Product_ID(0);
		// No Product
		if (getM_Product_ID() == 0)
			setM_AttributeSetInstance_ID(0);
		// Product
		else
		// Set/check Product Price
		{
			// Set Price if Actual = 0
			if (m_productPrice == null
					&& getPriceActual().signum() == 0
					&& getPriceList().signum() == 0)
			{
				setPrice();
			}

			// Check if on Price list
			if (m_productPrice == null)
				getProductPricing(m_M_PriceList_ID, get_ValueAsInt(de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_PriceList_Version_ID));
			if (!m_productPrice.isCalculated())
			{
				throw new ProductNotOnPriceListException(m_productPrice, getLine());
			}
		}

		// metas: Not allowed to save without (Product or Charge) and qty > 0
		if (getM_Product_ID() == 0 && getC_Charge_ID() == 0 && getQtyEntered().intValue() > 0)
			throw new AdempiereException("@NotFound@ @M_Product_ID@/@C_Charge_ID@ (@QtyEntered@>0)");

		// UOM
		if (getC_UOM_ID() == 0
				&& (getM_Product_ID() != 0
						|| getPriceEntered().compareTo(BigDecimal.ZERO) != 0
						|| getC_Charge_ID() != 0))
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID(C_UOM_ID);
		}

		// Price_UOM task 06942
		// note: we do not set the price-UOM, because that would only make sense if we also set the prices themselves.

		// Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("QtyOrdered"))
			setQtyOrdered(getQtyOrdered());

		// task 05295: commenting this out because also for ASI-Order-Lines it shall be allowed to order qty that is not yet fully avalable on stock
		// // Qty on instance ASI for SO
		// if (m_IsSOTrx
		// && getM_AttributeSetInstance_ID() != 0
		// && (newRecord || is_ValueChanged("M_Product_ID")
		// || is_ValueChanged("M_AttributeSetInstance_ID")
		// || is_ValueChanged("M_Warehouse_ID")))
		// {
		// MProduct product = getProduct();
		// if (product.isStocked())
		// {
		// int M_AttributeSet_ID = product.getM_AttributeSet_ID();
		// boolean isInstance = M_AttributeSet_ID != 0;
		// if (isInstance)
		// {
		// MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
		// isInstance = mas.isInstanceAttribute();
		// }
		// // Max
		// if (isInstance)
		// {
		// MStorage[] storages = MStorage.getWarehouse(getCtx(),
		// Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this).getM_Warehouse_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(),
		// M_AttributeSet_ID, false, null, true, get_TrxName());
		// BigDecimal qty = BigDecimal.ZERO;
		// for (int i = 0; i < storages.length; i++)
		// {
		// if (storages[i].getM_AttributeSetInstance_ID() == getM_AttributeSetInstance_ID())
		// qty = qty.add(storages[i].getQtyOnHand());
		// }
		//
		// if (getQtyOrdered().compareTo(qty) > 0)
		// {
		// log.warn("Qty - Stock=" + qty + ", Ordered=" + getQtyOrdered());
		// log.error("QtyInsufficient", "=" + qty);
		// return false;
		// }
		// }
		// } // stocked
		// } // SO instance

		// FreightAmt Not used
		if (BigDecimal.ZERO.compareTo(getFreightAmt()) != 0)
			setFreightAmt(BigDecimal.ZERO);

		// Set Tax
		// metas: Steuer muss immer ermittelt werden, da durch eine Anschriftenaenderung im Kopf auch Steueraenderungen in Positionen auftreten.
		// if (getC_Tax_ID() == 0)
		if (!setTax())
		{
			return false;
		}
		// metas ende

		// Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
			int ii = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
			setLine(ii);
		}

		// Calculations & Rounding

		// FIXME: commented out because actually was doing nothing (see, it was updating another instance of this order line, which is not saved), and more, setLineNetAmt is no longer called from here
		// final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(getCtx(), getC_OrderLine_ID(), I_C_OrderLine.class, get_TrxName());
		// Services.get(IOrderLineBL.class).setPrices(orderLine);

		// 07264
		// commented out because we are not using this anymore
		// setLineNetAmt(); // extended Amount with or without tax

		// metas
		// setDiscount();
		// metas ende

		return true;
	}	// beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		// R/O Check - Something delivered. etc.
		if (BigDecimal.ZERO.compareTo(getQtyDelivered()) != 0)
		{
			throw new AdempiereException("@DeleteError@ @QtyDelivered@=" + getQtyDelivered());
		}
		if (BigDecimal.ZERO.compareTo(getQtyInvoiced()) != 0)
		{
			throw new AdempiereException("@DeleteError@ @QtyInvoiced@=" + getQtyInvoiced());
		}
		if (BigDecimal.ZERO.compareTo(getQtyReserved()) != 0)
		{
			// metas: attempt to unreserve stock
			BigDecimal oldVal = getQtyOrdered();
			if (oldVal.signum() != 0)
			{
				setQty(BigDecimal.ZERO);
				setLineNetAmt(BigDecimal.ZERO);
				saveEx(get_TrxName());
			}

			if (!getParent().reserveStock(null, new MOrderLine[] { this }))
			{
				throw new AdempiereException("@DeleteError@ @QtyReserved@=" + getQtyReserved());
			}
			// metas end
		}

		// UnLink All Requisitions
		MRequisitionLine.unlinkC_OrderLine_ID(getCtx(), get_ID(), get_TrxName());

		return true;
	}	// beforeDelete

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return saved
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (!newRecord && is_ValueChanged("C_Tax_ID"))
		{
			// Recalculate Tax for old Tax
			if (!getParent().isProcessed())
				if (!updateOrderTax(true))
					return false;
		}
		return updateHeaderTax();
	}	// afterSave

	/**
	 * After Delete
	 *
	 * @param success success
	 * @return deleted
	 */
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return success;
		if (getS_ResourceAssignment_ID() != 0)
		{
			MResourceAssignment ra = new MResourceAssignment(getCtx(), getS_ResourceAssignment_ID(), get_TrxName());
			ra.delete(true);
		}

		return updateHeaderTax();
	}	// afterDelete

	/**
	 * Recalculate order tax
	 *
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 *
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateOrderTax(final boolean oldTax)
	{
		// NOTE: keep in sync with org.compiere.model.MInvoiceLine.updateInvoiceTax(boolean)

		final String trxName = get_TrxName();
		final int taxPrecision = getPrecision();
		final MOrderTax tax = MOrderTax.get(this, taxPrecision, oldTax, trxName);
		if (tax == null)
		{
			return true;
		}

		if (!tax.calculateTaxFromLines())
		{
			return false;
		}

		//
		// If tax has invoice lines behind => fine, save it
		if (tax.isActive())
		{
			InterfaceWrapperHelper.save(tax, trxName);
		}
		// Tax has no longer any invoice lines behind => deleted it if it's not new
		else
		{
			if (!InterfaceWrapperHelper.isNew(tax))
			{
				InterfaceWrapperHelper.delete(tax);
			}
		}

		return true;
	}

	/**
	 * Update Tax & Header
	 *
	 * @return true if header updated
	 */
	private boolean updateHeaderTax()
	{
		// Recalculate Tax for this Tax
		if (!getParent().isProcessed())
		{
			if (!updateOrderTax(false))
			{
				return false;
			}
		}

		// task 08999:
		// Avoid a possible deadlock by updating the C_Order *after* the current transaction, because at this point we might already hold a lot of locks to different objects.
		// The updates in updateHeader0 will try aggregate and obtain any number of additional shared locks.
		// Concrete, we observed a deadlock between this code and M_ReceiptSchedule.propagateQtysToOrderLine()
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager
				.getTrxListenerManager(get_TrxName())
				.registerListener(new TrxListenerAdapter()
				{
					@Override
					public void afterCommit(final ITrx trx)
					{
						trxManager.run(new TrxRunnableAdapter()
						{
							@Override
							public void run(final String localTrxName) throws Exception
							{
								updateHeader0(getC_Order_ID());
							}
						});
					}
				});
		return true;
	}	// updateHeaderTax

	/**
	 * See the comment in {@link #updateHeaderTax()}.
	 *
	 * @param orderId
	 */
	private static void updateHeader0(final int orderId)
	{

		// Update Order Header: TotalLines
		{
			final String sql = "UPDATE C_Order i"
					+ " SET TotalLines="
					+ "(SELECT COALESCE(SUM(LineNetAmt),0) FROM C_OrderLine il WHERE i.C_Order_ID=il.C_Order_ID) "
					+ "WHERE C_Order_ID=" + orderId;
			final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			if (no != 1)
			{
				new AdempiereException("Updating TotalLines failed for C_Order_ID=" + orderId);
			}
		}
		// Update Order Header: GrandTotal
		{
			final String sql = "UPDATE C_Order i "
					+ " SET GrandTotal=TotalLines+"
					// SUM up C_OrderTax.TaxAmt only for those lines which does not have Tax Included
					+ "(SELECT COALESCE(SUM(TaxAmt),0) FROM C_OrderTax it WHERE i.C_Order_ID=it.C_Order_ID AND it.IsActive='Y' AND it.IsTaxIncluded='N') "
					+ "WHERE C_Order_ID=" + orderId;
			final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
			if (no != 1)
			{
				new AdempiereException("Updating GrandTotal failed for C_Order_ID=" + orderId);
			}
		}
	}
}	// MOrderLine
