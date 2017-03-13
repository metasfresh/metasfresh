package de.metas.handlingunits.client.terminal.receiptschedule.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;

public class ReceiptScheduleTableRow implements IReceiptScheduleTableRow
{
	//
	// Services
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IHUPIItemProductBL piItemProductBL = Services.get(IHUPIItemProductBL.class);

	private final I_M_ReceiptSchedule rs;

	private String productNameAndASI = null;
	private String piItemProductName = null;

	public ReceiptScheduleTableRow(final I_M_ReceiptSchedule rs)
	{
		super();
		Check.assumeNotNull(rs, "rs not null");
		this.rs = rs;

		// We need to make sure receipt schedule is out-of-transaction
		// because we will save it after each setter
		final String trxName = InterfaceWrapperHelper.getTrxName(rs);
		Check.assume(Services.get(ITrxManager.class).isNull(trxName), "receipt schedule's transaction shall be null");
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(rs.getM_ReceiptSchedule_ID())
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final ReceiptScheduleTableRow other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(rs.getM_ReceiptSchedule_ID(), other.rs.getM_ReceiptSchedule_ID())
				.isEqual();
	}

	@Override
	public I_C_Order getC_Order()
	{
		return rs.getC_Order();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return rs.getC_Order().getC_BPartner();
	}

	public int getC_BPartner_ID()
	{
		final I_C_BPartner bpartner = getC_BPartner();
		final int bpartnerId = bpartner == null ? -1 : bpartner.getC_BPartner_ID();
		return bpartnerId;
	}

	@Override
	public String getBPName()
	{
		return getC_BPartner().getName();
	}

	@Override
	public String getProductNameAndASI()
	{
		if (productNameAndASI == null)
		{
			final StringBuilder productNameAndASI = new StringBuilder();
			productNameAndASI.append("<html>");

			final String productName = getM_Product().getName();
			productNameAndASI.append(Util.maskHTML(productName));

			final String asiDescription = getASIDescription();
			if (!Check.isEmpty(asiDescription, true))
			{
				productNameAndASI.append("<br><i>").append(Util.maskHTML(asiDescription)).append("</i>");
			}

			productNameAndASI.append("</html>");

			this.productNameAndASI = productNameAndASI.toString();
		}

		return productNameAndASI;
	}

	private final String getASIDescription()
	{
		if (rs.getM_AttributeSetInstance_ID() <= 0)
		{
			return null;
		}

		final I_M_AttributeSetInstance asi = rs.getM_AttributeSetInstance();
		if (asi == null)
		{
			return null;
		}

		final String asiDescription = asi.getDescription();
		return asiDescription;
	}

	@Override
	public BigDecimal getQtyOrdered()
	{
		// NOTE: always show the QtyOrdered from receipt schedule (confirmed with Mark)
		final BigDecimal qtyOrdered = rs.getQtyOrdered();
		return qtyOrdered;
	}

	@Override
	public BigDecimal getQtyMoved()
	{
		final BigDecimal qtyMoved = receiptScheduleBL.getQtyMoved(rs);
		return qtyMoved;
	}

	@Override
	public String getUOMSymbol()
	{
		return rs.getC_UOM().getUOMSymbol();
	}

	@Override
	public String getM_HU_PI_Item_Product_Name()
	{
		if (piItemProductName != null)
		{
			return piItemProductName;
		}

		final I_M_HU_PI_Item_Product pip = huReceiptScheduleBL
				.createLUTUConfigurationManager(rs)
				.getM_HU_PI_Item_Product();
		if (pip == null)
		{
			return null;
		}

		final BigDecimal qtyOrderedTU = getQtyOrderedTUOrNull();
		final BigDecimal qtyMovedTU = rs.getQtyMovedTU();

		piItemProductName = piItemProductBL.buildDisplayName()
				.setM_HU_PI_Item_Product(pip)
				.setQtyTUPlanned(qtyOrderedTU)
				.setQtyTUMoved(qtyMovedTU)
				.build();
		return piItemProductName;
	}

	/**
	 *
	 * @return amount of TUs which were planned to be received (i.e. amount of TUs ordered)
	 */
	private BigDecimal getQtyOrderedTUOrNull()
	{
		return huReceiptScheduleBL.getQtyOrderedTUOrNull(rs);
	}

	@Override
	public Date getMovementDate()
	{
		return rs.getMovementDate();
	}

	@Override
	public java.sql.Time getPreparationTime()
	{
		final Timestamp ts = rs.getPreparationTime();
		if (ts == null)
		{
			// PreparationTime was not registered
			return null;
		}
		return new java.sql.Time(ts.getTime());
	}

	@Override
	public BigDecimal getQualityDiscountPercent()
	{
		return rs.getQualityDiscountPercent();
	}

	@Override
	public String getQualityNote()
	{
		return rs.getQualityNote();
	}

	public I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return rs;
	}

	@Override
	public KeyNamePair getM_Warehouse_Dest()
	{
		final I_M_Warehouse warehouseDest = rs.getM_Warehouse_Dest();
		if (warehouseDest == null)
		{
			return null;
		}

		final KeyNamePair warehouseDestKNP = new KeyNamePair(warehouseDest.getM_Warehouse_ID(), warehouseDest.getName());
		return warehouseDestKNP;
	}

	private final int getM_Product_ID()
	{
		return rs.getM_Product_ID();
	}

	public I_M_Product getM_Product()
	{
		return rs.getM_Product();
	}

	@Override
	public Set<Integer> getM_Product_IDs()
	{
		return Collections.singleton(getM_Product_ID());
	}

	@Override
	public void setM_Warehouse_Dest(final KeyNamePair warehouseDestKNP)
	{
		if (warehouseDestKNP == null || warehouseDestKNP.getKey() <= 0)
		{
			rs.setM_Warehouse_Dest(null);
		}
		else
		{
			rs.setM_Warehouse_Dest_ID(warehouseDestKNP.getKey());
		}

		InterfaceWrapperHelper.save(rs);
	}
}
