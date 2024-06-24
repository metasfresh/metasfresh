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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;

/**
 * Order Line Model. <code>
 * MOrderLine ol = new MOrderLine(m_order);
 * ol.setM_Product_ID(wbl.getM_Product_ID());
 * ol.setQtyOrdered(wbl.getQuantity());
 * ol.setPrice();
 * ol.setPriceActual(wbl.getPrice());
 * ol.setTax();
 * ol.save();
 *
 * </code>
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <ul>
 * <li>BF [ 2588043 ] Insufficient message ProductNotOnPriceList
 * @author Michael Judd, www.akunagroup.com
 * <ul>
 * <li>BF [ 1733602 ] Price List including Tax Error - when a user changes the orderline or invoice line for a product on a price list that includes tax, the net amount is incorrectly
 * calculated.
 * @version $Id: MOrderLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
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
	 * @param ctx                       context
	 * @param M_Warehouse_ID            wh
	 * @param M_Product_ID              product
	 * @param M_AttributeSetInstance_ID asi
	 * @param excludeC_OrderLine_ID     exclude C_OrderLine_ID
	 * @return Unreserved Qty
	 */
	public static BigDecimal getNotReserved(Properties ctx, int M_Warehouse_ID,
											int M_Product_ID, int M_AttributeSetInstance_ID, int excludeC_OrderLine_ID)
	{
		BigDecimal retValue = BigDecimal.ZERO;
		String sql = "SELECT SUM(ol.QtyOrdered-ol.QtyDelivered-ol.QtyReserved) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE ol.M_Warehouse_ID=?"    // #1
				// metas: adding table alias "ol" to M_Product_ID to distinguish it from C_Order's M_Product_ID
				+ " AND ol.M_Product_ID=?" // #2
				+ " AND o.IsSOTrx='Y' AND o.DocStatus='DR'"
				+ " AND ol.QtyOrdered-ol.QtyDelivered-ol.QtyReserved<>0"
				+ " AND ol.C_OrderLine_ID<>?";
		if (M_AttributeSetInstance_ID != 0)
		{
			sql += " AND ol.M_AttributeSetInstance_ID=?";
		}

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			pstmt.setInt(3, excludeC_OrderLine_ID);
			if (M_AttributeSetInstance_ID != 0)
			{
				pstmt.setInt(4, M_AttributeSetInstance_ID);
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
			}
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
			{
				pstmt.close();
			}
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (retValue == null)
		{
			s_log.debug("-");
		}
		else
		{
			s_log.debug(retValue.toString());
		}
		return retValue;
	}    // getNotReserved

	/**
	 * Logger
	 */
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
			setQtyOrdered(BigDecimal.ZERO);    // 1
			setQtyDelivered(BigDecimal.ZERO);
			setQtyInvoiced(BigDecimal.ZERO);
			// task 09358: get rid of this; instead, update qtyReserved at one central place
			// setQtyReserved(BigDecimal.ZERO);
			//
			setIsDescription(false);    // N
			setProcessed(false);
			setLine(0);
		}
	}    // MOrderLine

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
	 * @deprecated please use {@link IOrderLineBL#createOrderLine(I_C_Order)} instead.
	 */
	@Deprecated
	public MOrderLine(@NonNull final I_C_Order order)
	{
		this(InterfaceWrapperHelper.getCtx(order), 0, getTrxName(order));
		if (order.getC_Order_ID() == 0)
		{
			throw new IllegalArgumentException("Header not saved");
		}
		setC_Order_ID(order.getC_Order_ID());    // parent
		Services.get(IOrderLineBL.class).setOrder(this, order);
	}    // MOrderLine

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set record
	 * @param trxName transaction
	 */
	public MOrderLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    // MOrderLine

	/**
	 * Tax
	 */
	private MTax m_tax = null;

	/**
	 * Product
	 */
	private MProduct m_product = null;
	/**
	 * Charge
	 */
	private MCharge m_charge = null;

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public MOrder getParent()
	{
		return LegacyAdapters.convertToPO(getC_Order());
	}    // getParent

	/**
	 * Set Price Entered/Actual. Use this Method if the Line UOM is the Product UOM
	 *
	 * @param PriceActual price
	 */
	public void setPrice(BigDecimal PriceActual)
	{
		setPriceEntered(PriceActual);
		setPriceActual(PriceActual);
	}    // setPrice

	/**
	 * Set Price Actual. (actual price is not updateable)
	 *
	 * @param PriceActual actual price
	 */
	@Override
	public void setPriceActual(BigDecimal PriceActual)
	{
		if (PriceActual == null)
		{
			throw new IllegalArgumentException("PriceActual is mandatory");
		}
		set_ValueNoCheck("PriceActual", PriceActual);
	}    // setPriceActual

	/**
	 * Set Price for Product and PriceList. Use only if newly created.
	 */
	public void setPrice()
	{
		if (getM_Product_ID() <= 0)
		{
			return;
		}

		Services.get(IOrderLineBL.class).updatePrices(this);
	}    // setPrice

	/**
	 * Set Tax or throw an exception if that was not possible
	 */
	public void setTax()
	{
		final TaxCategoryId taxCategoryId = Services.get(IOrderLineBL.class).getTaxCategoryId(this);

		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this);
		final CountryId countryFromId = Services.get(IWarehouseBL.class).getCountryId(warehouseId);

		final BPartnerLocationAndCaptureId bpLocationId = OrderLineDocumentLocationAdapterFactory.locationAdapter(this).getBPartnerLocationAndCaptureId();

		final boolean isSOTrx = getParent().isSOTrx();
		final Timestamp taxDate = getDatePromised();
		final Tax tax = Services.get(ITaxDAO.class).getBy(TaxQuery.builder()
				.fromCountryId(countryFromId)
				.orgId(OrgId.ofRepoId(getAD_Org_ID()))
				.bPartnerLocationId(bpLocationId)
				.warehouseId(warehouseId)
				.dateOfInterest(taxDate)
				.taxCategoryId(taxCategoryId)
				.soTrx(SOTrx.ofBoolean(isSOTrx))
				.build());

		if (tax == null)
		{
			TaxNotFoundException.builder()
					.taxCategoryId(taxCategoryId)
					.isSOTrx(isSOTrx)
					.billDate(taxDate)
					.billFromCountryId(countryFromId)
					.billToC_Location_ID(bpLocationId.getLocationCaptureId())
					.build()
					.throwOrLogWarning(true, log);
		}

		setC_Tax_ID(tax.getTaxId().getRepoId());
		setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
	}    // setTax

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
		final CurrencyPrecision taxPrecision = Services.get(IOrderLineBL.class).getTaxPrecision(this);

		// juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		// http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded && !documentLevel)
		{
			BigDecimal taxStdAmt = BigDecimal.ZERO;
			BigDecimal taxThisAmt = BigDecimal.ZERO;

			MTax orderTax = getTax();
			MTax stdTax = null;

			// get the standard tax
			if (getProduct() == null)
			{
				if (getCharge() != null)    // Charge
				{
					stdTax = new MTax(getCtx(),
							((MTaxCategory)getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
							get_TrxName());
				}
			}
			else
			// Product
			{
				// FIXME metas 05129 need proper concept (link between M_Product and C_TaxCategory_ID was removed)
				throw new AdempiereException("Unsupported tax calculation when tax is included, but it's not on document level");
			}

			if (stdTax != null)
			{
				log.debug("stdTax rate is " + stdTax.getRate());
				log.debug("orderTax rate is " + orderTax.getRate());

				final ITaxBL taxBL = Services.get(ITaxBL.class);
				taxThisAmt = taxThisAmt.add(taxBL.calculateTax(orderTax, bd, isTaxIncluded, taxPrecision.toInt()));
				taxStdAmt = taxStdAmt.add(taxBL.calculateTax(stdTax, bd, isTaxIncluded, taxPrecision.toInt()));

				bd = bd.subtract(taxStdAmt).add(taxThisAmt);

				log.debug("Price List includes Tax and Tax Changed on Order Line: New Tax Amt: "
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);
			}

		}

		if (bd.scale() > taxPrecision.toInt())
		{
			bd = bd.setScale(taxPrecision.toInt(), BigDecimal.ROUND_HALF_UP);
		}
		super.setLineNetAmt(bd);
	}    // setLineNetAmt

	/**
	 * Get Charge
	 *
	 * @return product or null
	 */
	public MCharge getCharge()
	{
		if (m_charge == null && getC_Charge_ID() != 0)
		{
			m_charge = MCharge.get(getCtx(), getC_Charge_ID());
		}
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
		{
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		}
		return m_tax;
	}    // getTax

	public void setM_Product_ID(final int productRepoId, final boolean setUOM)
	{
		final ProductId productId = ProductId.ofRepoId(productRepoId);
		Services.get(IOrderLineBL.class).setProductId(this, productId, setUOM);
	}

	/**
	 * Set Product and UOM
	 *
	 * @param M_Product_ID product
	 * @param C_UOM_ID     uom
	 */
	public void setM_Product_ID(int M_Product_ID, int C_UOM_ID)
	{
		super.setM_Product_ID(M_Product_ID);
		if (C_UOM_ID != 0)
		{
			super.setC_UOM_ID(C_UOM_ID);
		}
		setM_AttributeSetInstance_ID(0);
	}    // setM_Product_ID

	/**
	 * Get Product
	 *
	 * @return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
		{
			m_product = MProduct.get(getCtx(), getM_Product_ID());
		}
		return m_product;
	}    // getProduct

	/**
	 * Set M_AttributeSetInstance_ID
	 *
	 * @param M_AttributeSetInstance_ID id
	 */
	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)
		{
			set_Value("M_AttributeSetInstance_ID", new Integer(0));
		}
		else
		{
			super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		}
	}    // setM_AttributeSetInstance_ID

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
	}    // setM_Warehouse_ID

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
		// We can change
		return true;
	}    // canChangeWarehouse

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
		{
			ii = getParent().getC_Project_ID();
		}
		return ii;
	}    // getC_Project_ID

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
		{
			ii = getParent().getC_Activity_ID();
		}
		return ii;
	}    // getC_Activity_ID

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
		{
			ii = getParent().getC_Campaign_ID();
		}
		return ii;
	}    // getC_Campaign_ID

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
		{
			ii = getParent().getUser1_ID();
		}
		return ii;
	}    // getUser1_ID

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
		{
			ii = getParent().getUser2_ID();
		}
		return ii;
	}    // getUser2_ID

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
		{
			ii = getParent().getAD_OrgTrx_ID();
		}
		return ii;
	}    // getAD_OrgTrx_ID

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
	}    // toString

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}    // addDescription

	/**
	 * Get Description Text. For jsp access (vs. isDescription)
	 *
	 * @return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}    // getDescriptionText

	/**
	 * Get Name
	 *
	 * @return get the name of the line (from Product)
	 */
	public String getName()
	{
		getProduct();
		if (m_product != null)
		{
			return m_product.getName();
		}
		if (getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(getCtx(), getC_Charge_ID());
			return charge.getName();
		}
		return "";
	}    // getName

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
		{
			set_ValueNoCheck("C_UOM_ID", null);
		}
	}    // setC_Charge_ID

	/**
	 * Set Qty Entered/Ordered. Use this Method if the Line UOM is the Product UOM
	 *
	 * @param Qty QtyOrdered/Entered
	 */
	public void setQty(BigDecimal Qty)
	{
		super.setQtyEntered(Qty);
		super.setQtyOrdered(getQtyEntered());
	}    // setQty

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
	}    // setQtyEntered

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
	}    // setQtyOrdered

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return true if it can be saved
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
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
		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(this);
		if (getC_BPartner_ID() <= 0 || getC_BPartner_Location_ID() <= 0
				|| warehouseId == null
				|| getC_Currency_ID() <= 0)
		{
			Services.get(IOrderLineBL.class).setOrder(this, getC_Order());
		}

		// R/O Check - Product/Warehouse Change
		if (!newRecord
				&& (is_ValueChanged(COLUMNNAME_M_Product_ID) || is_ValueChanged(COLUMNNAME_M_Warehouse_ID)))
		{
			canChangeWarehouse(true);
		}    // Product Changed

		// Charge
		if (getC_Charge_ID() > 0 && getM_Product_ID() > 0)
		{
			setM_Product_ID(0);
		}
		// No Product
		if (getM_Product_ID() <= 0)
		{
			setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		}
		// Product
		else
		{
			// Set Price if Actual = 0
			if (getPriceList().signum() == 0)
			{
				setPrice();
			}
		}

		// metas: Not allowed to save without (Product or Charge) and qty > 0
		if (getM_Product_ID() <= 0 && getC_Charge_ID() <= 0 && getQtyEntered().intValue() > 0)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@/@C_Charge_ID@ (@QtyEntered@>0)");
		}
		// UOM
		if (getC_UOM_ID() <= 0)
		{
			// attempt to provide a UOM
			if (getM_Product_ID() > 0)
			{
				final ProductId productId = ProductId.ofRepoId(getM_Product_ID());
				final IProductBL productBL = Services.get(IProductBL.class);
				setC_UOM_ID(productBL.getStockUOMId(productId).getRepoId());
			}
			else if (getC_Charge_ID() > 0
					|| getPriceEntered().signum() != 0)
			{
				int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
				if (C_UOM_ID > 0)
				{
					setC_UOM_ID(C_UOM_ID);
				}
			}
		}

		// Price_UOM task 06942
		// note: we do not set the price-UOM, because that would only make sense if we also set the prices themselves.

		// Qty Precision
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyEntered))
		{
			setQtyEntered(getQtyEntered());
		}
		if (newRecord || is_ValueChanged(COLUMNNAME_QtyOrdered))
		{
			setQtyOrdered(getQtyOrdered());
		}

		// FreightAmt Not used
		if (getFreightAmt().signum() != 0)
		{
			setFreightAmt(BigDecimal.ZERO);
		}

		// metas: Steuer muss immer ermittelt werden, da durch eine Anschriftenaenderung im Kopf auch Steueraenderungen in Positionen auftreten.
		setTax();

		// metas ende

		// Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
			int ii = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
			setLine(ii);
		}

		return true;
	}    // beforeSave

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

			if (!getParent().reserveStock(null, ImmutableList.of(this)))
			{
				throw new AdempiereException("@DeleteError@ @QtyReserved@=" + getQtyReserved());
			}
			// metas end
		}

		// UnLink All Requisitions
		MRequisitionLine.unlinkC_OrderLine_ID(getCtx(), get_ID(), get_TrxName());

		return true;
	}    // beforeDelete

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success   success
	 * @return saved
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}
		if (!newRecord && is_ValueChanged("C_Tax_ID"))
		{
			// Recalculate Tax for old Tax
			if (!getParent().isProcessed())
			{
				if (!updateOrderTax(true))
				{
					return false;
				}
			}
		}
		return updateHeaderTax();
	}    // afterSave

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
		{
			return success;
		}
		if (getS_ResourceAssignment_ID() != 0)
		{
			MResourceAssignment ra = new MResourceAssignment(getCtx(), getS_ResourceAssignment_ID(), get_TrxName());
			ra.delete(true);
		}

		return updateHeaderTax();
	}    // afterDelete

	/**
	 * Recalculate order tax
	 *
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateOrderTax(final boolean oldTax)
	{
		// NOTE: keep in sync with org.compiere.model.MInvoiceLine.updateInvoiceTax(boolean)

		final String trxName = get_TrxName();
		final CurrencyPrecision taxPrecision = Services.get(IOrderBL.class).getTaxPrecision(getC_Order());
		final MOrderTax tax = MOrderTax.get(this, taxPrecision.toInt(), oldTax, trxName);
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
		final ITrxManager trxManager = get_TrxManager();
		trxManager.accumulateAndProcessAfterCommit(
				"ordersToUpdateHeader",
				ImmutableSet.of(OrderId.ofRepoId(getC_Order_ID())),
				this::updateHeaderInNewTrx
		);

		return true;
	}    // updateHeaderTax

	private void updateHeaderInNewTrx(final Collection<OrderId> orderIds)
	{
		final ITrxManager trxManager = get_TrxManager();
		trxManager.runInNewTrx(() -> updateHeaderNow(ImmutableSet.copyOf(orderIds)));
	}

	private static void updateHeaderNow(final Set<OrderId> orderIds)
	{
		// shall not happen
		if (orderIds.isEmpty())
		{
			return;
		}

		// Update Order Header: TotalLines
		{
			final ArrayList<Object> sqlParams = new ArrayList<>();
			final String sql = "UPDATE C_Order o"
					+ " SET TotalLines="
					+ "(SELECT COALESCE(SUM(ol.LineNetAmt),0) FROM C_OrderLine ol WHERE ol.C_Order_ID=o.C_Order_ID) "
					+ "WHERE " + DB.buildSqlList("C_Order_ID", orderIds, sqlParams);
			DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		}
		// Update Order Header: GrandTotal
		{
			final ArrayList<Object> sqlParams = new ArrayList<>();
			final String sql = "UPDATE C_Order o "
					+ " SET GrandTotal=TotalLines+"
					// SUM up C_OrderTax.TaxAmt only for those lines which does not have Tax Included
					+ "(SELECT COALESCE(SUM(TaxAmt),0) FROM C_OrderTax ot WHERE o.C_Order_ID=ot.C_Order_ID AND ot.IsActive='Y' AND ot.IsTaxIncluded='N') "
					+ "WHERE " + DB.buildSqlList("C_Order_ID", orderIds, sqlParams);
			DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		}
	}
}    // MOrderLine
