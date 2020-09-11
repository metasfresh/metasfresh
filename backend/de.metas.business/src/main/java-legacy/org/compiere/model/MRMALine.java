package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderLineBL;
import de.metas.tax.api.ITaxBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

/**
 * RMA Line Model
 *
 * @author Jorg Janke
 * @version $Id: MRMALine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRMALine extends X_M_RMALine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926737621934215278L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_RMALine_ID id
	 * @param trxName transaction
	 */
	public MRMALine(Properties ctx, int M_RMALine_ID, String trxName)
	{
		super(ctx, M_RMALine_ID, trxName);
		if (M_RMALine_ID == 0)
		{
			setQty(Env.ONE);
			this.setQtyDelivered(Env.ONE);
		}

		init();
	}	// MRMALine

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MRMALine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		init();
	}	// MRMALine

	/** Shipment Line */
	private MInOutLine m_ioLine = null;
	/** Parent */
	private MRMA m_parent = null;

	private CurrencyPrecision taxPrecision = CurrencyPrecision.ZERO;
	private int taxId = -1;
	private BigDecimal unitAmount = Env.ZERO;
	private BigDecimal originalQty = Env.ZERO;

	/**
	 * Initialise parameters that are required
	 */
	private void init()
	{
		if (getC_Charge_ID() != 0)
		{
			// Retrieve tax Exempt
			String sql = "SELECT C_Tax_ID FROM C_Tax WHERE AD_Client_ID=? AND IsActive='Y' "
					+ "AND IsTaxExempt='Y' AND ValidFrom < now() ORDER BY IsDefault DESC";

			// Set tax for charge as exempt
			taxId = DB.getSQLValueEx(null, sql, Env.getAD_Client_ID(getCtx()));
			m_ioLine = null;
		}
		else
		{
			getShipLine();
		}

		if (m_ioLine != null)
		{
			// Get pricing details (Based on invoice if found, on order otherwise)
			// --> m_ioLine.isInvoiced just work for sales orders - so it doesn't work for purchases
			if (getInvoiceLineId() != 0)
			{
				MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), getInvoiceLineId(), get_TrxName());
				taxPrecision = Services.get(IInvoiceBL.class).getTaxPrecision(invoiceLine);
				unitAmount = invoiceLine.getPriceEntered();
				originalQty = invoiceLine.getQtyInvoiced();
				taxId = invoiceLine.getC_Tax_ID();
			}
			else if (m_ioLine.getC_OrderLine_ID() > 0)
			{
				MOrderLine orderLine = new MOrderLine(getCtx(), m_ioLine.getC_OrderLine_ID(), get_TrxName());
				taxPrecision = Services.get(IOrderLineBL.class).getTaxPrecision(orderLine);
				unitAmount = orderLine.getPriceEntered();
				originalQty = orderLine.getQtyDelivered();
				taxId = orderLine.getC_Tax_ID();
			}
			else
			{
				// NOTE: in this case the LineNetAmt and Original Qty informations will be missing
				// but we can live without those for now

				// throw new IllegalStateException("No Invoice/Order line found the Shipment/Receipt line associated");
			}
		}
		else if (getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(this.getCtx(), getC_Charge_ID());
			unitAmount = charge.getChargeAmt();
		}
	}

	/**
	 * Get Parent
	 * 
	 * @return parent
	 */
	private MRMA getParent()
	{
		if (m_parent == null)
		{
			m_parent = new MRMA(getCtx(), getM_RMA_ID(), get_TrxName());
		}
		return m_parent;
	}   // getParent

	/**
	 * Set M_InOutLine_ID
	 *
	 * @param M_InOutLine_ID
	 */
	@Override
	public void setM_InOutLine_ID(int M_InOutLine_ID)
	{
		super.setM_InOutLine_ID(M_InOutLine_ID);
		m_ioLine = null;
	}	// setM_InOutLine_ID

	/**
	 * Get Ship Line
	 *
	 * @return ship line
	 */
	public MInOutLine getShipLine()
	{
		if ((m_ioLine == null || is_ValueChanged("M_InOutLine_ID")) && getM_InOutLine_ID() != 0)
		{
			m_ioLine = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
		}
		return m_ioLine;
	}	// getShipLine

	/**
	 * Retrieves the invoiceLine Id associated with the Shipment/Receipt Line
	 * 
	 * @return Invoice Line ID
	 */
	private int getInvoiceLineId()
	{
		String whereClause = " M_InOutLine_ID = ? AND M_Product_ID "
				+ " IN (SELECT M_Product_ID FROM M_InOutLine ol "
				+ " WHERE ol.M_InOutLine_ID = ?) ";
		int invoiceLine_ID = new Query(getCtx(), MInvoiceLine.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getM_InOutLine_ID(), getM_InOutLine_ID() })
				.firstIdOnly();
		return invoiceLine_ID <= 0 ? 0 : invoiceLine_ID;
	}

	/**
	 * Calculates the unit amount for the product/charge
	 * 
	 * @return Unit Amount
	 */
	private BigDecimal getUnitAmt()
	{
		return unitAmount;
	}

	/** @return tax or <code>null</code> */
	private final I_C_Tax getC_Tax()
	{
		if (taxId <= 0)
		{
			return null;
		}
		return MTax.get(getCtx(), taxId);
	}

	/**
	 * Get Total Amt for the line including tax
	 * 
	 * @return amt
	 */
	private BigDecimal calculateTotalAmt()
	{
		BigDecimal totalAmt = Env.ZERO;
		BigDecimal taxAmt = Env.ZERO;

		if (Env.ZERO.compareTo(getQty()) != 0 && Env.ZERO.compareTo(getAmt()) != 0)
		{
			final I_C_Tax tax = getC_Tax();
			if (tax != null)
			{
				final boolean taxIncluded = getParent().isTaxIncluded(tax);
				totalAmt = getQty().multiply(getAmt());
				if (!taxIncluded)
				{
					final ITaxBL taxBL = Services.get(ITaxBL.class);
					taxAmt = taxBL.calculateTax(tax, getQty().multiply(unitAmount), taxIncluded, taxPrecision.toInt());
				}
			}
		}

		totalAmt = totalAmt.add(taxAmt);
		return totalAmt;
	}   // getAmt

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @M_RMA_ID@");
		}
		if (getM_InOutLine_ID() == 0 && getC_Charge_ID() == 0)
		{
			throw new AdempiereException("@FillShipLineOrCharge@");
		}

		if (getM_InOutLine_ID() != 0 && getC_Charge_ID() != 0)
		{
			throw new AdempiereException("@JustShipLineOrCharge@");
		}

		init();
		if (m_ioLine != null)
		{
			if (!checkQty())
			{
				throw new AdempiereException("AmtReturned>Shipped");
			}

			if (newRecord || is_ValueChanged(COLUMNNAME_M_InOutLine_ID))
			{
				String whereClause = "M_RMA_ID=" + getM_RMA_ID() + " AND M_InOutLine_ID=" + getM_InOutLine_ID() + " AND M_RMALine_ID!=" + getM_RMALine_ID();

				int lineIds[] = MRMALine.getAllIDs(MRMALine.Table_Name, whereClause, this.get_TrxName());

				if (lineIds.length > 0)
				{
					throw new AdempiereException("@InOutLineAlreadyEntered@");
				}
			}
		}

		// Set default amount for charge and qty
		if (this.getC_Charge_ID() != 0 && this.getQty().doubleValue() <= 0)
		{
			if (getQty().signum() == 0)
			{
				this.setQty(Env.ONE);
			}
			if (getAmt().signum() == 0)
			{
				this.setAmt(getUnitAmt());
			}
		}

		// Set amount for products
		if (this.getM_InOutLine_ID() != 0)
		{
			this.setAmt(getUnitAmt());

			if (newRecord && getQty().signum() == 0)
			{
				this.setQty(originalQty);
			}
		}

		this.setLineNetAmt(calculateTotalAmt());

		return true;
	}

	/* package */boolean checkQty()
	{
		if (m_ioLine.getMovementQty().compareTo(getQty()) < 0)
		{
			return false;
		}
		// metas: c.ghita@metas.ro : remove the current rma line
		BigDecimal totalQty = DB.getSQLValueBD(get_TrxName(),
				"SELECT SUM(Qty) FROM M_RMALine rl JOIN M_RMA r ON (r.M_RMA_ID = rl.M_RMA_ID) "
						+ "WHERE rl.M_InOutLine_ID = ? AND r.Processed = 'Y' AND r.DocStatus IN ('CO','CL')"
						+ " AND rl.M_RMALine_ID <> ?",
				getM_InOutLine_ID(), getM_RMALine_ID());
		if (totalQty == null)
		{
			totalQty = Env.ZERO;
		}
		totalQty = totalQty.add(getQty());
		if (m_ioLine.getMovementQty().compareTo(totalQty) < 0)
		{
			return false;
		}

		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}

		MRMA rma = new MRMA(getCtx(), getM_RMA_ID(), get_TrxName());
		rma.updateAmount();

		if (!rma.save())
		{
			throw new IllegalStateException("Could not update RMA grand total");
		}

		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description text
	 */
	/* package */void addDescription(String description)
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
	}   // addDescription

	/**
	 * Get UOM based on Shipment line if present.
	 * 
	 * @return UOM if based on shipment line or {@link IUOMDAO#C_UOM_ID_Each}
	 */
	public int getC_UOM_ID()
	{
		if (m_ioLine == null) // Charge
		{
			return UomId.EACH.getRepoId(); // Each
		}

		return m_ioLine.getC_UOM_ID();
	}

	/**
	 * Get Product
	 * 
	 * @return product if based on shipment line and 0 for charge based
	 */
	public int getM_Product_ID()
	{
		if (getC_Charge_ID() != 0)
		{
			return 0;
		}
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getM_Product_ID();
	}

	/**
	 * Get Project
	 * 
	 * @return project if based on shipment line and 0 for charge based
	 */
	public int getC_Project_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getC_Project_ID();
	}

	/**
	 * Get Project Phase
	 * 
	 * @return project phase if based on shipment line and 0 for charge based
	 */
	public int getC_ProjectPhase_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getC_ProjectPhase_ID();
	}

	/**
	 * Get Project Task
	 * 
	 * @return project task if based on shipment line and 0 for charge based
	 */
	public int getC_ProjectTask_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getC_ProjectTask_ID();
	}

	/**
	 * Get Activity
	 * 
	 * @return project phase if based on shipment line and 0 for charge based
	 */
	public int getC_Activity_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getC_Activity_ID();
	}

	/**
	 * Get Campaign
	 * 
	 * @return campaign if based on shipment line and 0 for charge based
	 */
	public int getC_Campaign_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getC_Campaign_ID();
	}

	/**
	 * Get Org Trx
	 * 
	 * @return Org Trx if based on shipment line and 0 for charge based
	 */
	public int getAD_OrgTrx_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getAD_OrgTrx_ID();
	}

	/**
	 * Get User1
	 * 
	 * @return user1 if based on shipment line and 0 for charge based
	 */
	public int getUser1_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getUser1_ID();
	}

	/**
	 * Get User2
	 * 
	 * @return user2 if based on shipment line and 0 for charge based
	 */
	public int getUser2_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getUser2_ID();
	}

	/**
	 * Get Attribute Set Instance
	 * 
	 * @return ASI if based on shipment line and 0 for charge based
	 */
	public int getM_AttributeSetInstance_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getM_AttributeSetInstance_ID();
	}

	/**
	 * Get Locator
	 * 
	 * @return locator if based on shipment line and 0 for charge based
	 */
	public int getM_Locator_ID()
	{
		if (m_ioLine == null)
		{
			return 0;
		}
		return m_ioLine.getM_Locator_ID();
	}

	/**
	 * Get Tax
	 * 
	 * @return Tax based on Invoice/Order line and Tax exempt for charge based
	 */
	public int getC_Tax_ID()
	{
		return taxId;
	}

}	// MRMALine
