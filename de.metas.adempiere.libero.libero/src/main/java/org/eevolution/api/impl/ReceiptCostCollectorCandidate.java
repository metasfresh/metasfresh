package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

/* package */ class ReceiptCostCollectorCandidate implements IReceiptCostCollectorCandidate
{
	private I_PP_Order order;
	private I_PP_Order_BOMLine orderBOMLine;
	private Timestamp movementDate = SystemTime.asTimestamp();
	private I_M_Product product;
	private I_C_UOM uom;
	private BigDecimal qtyToReceive = BigDecimal.ZERO;
	private BigDecimal qtyScrap = BigDecimal.ZERO;
	private BigDecimal qtyReject = BigDecimal.ZERO;
	private int locatorId = -1;
	private int attributeSetInstanceId = IAttributeDAO.M_AttributeSetInstance_ID_None;

	public ReceiptCostCollectorCandidate()
	{
		super();
	}

	@Override
	public I_PP_Order getPP_Order()
	{
		return order;
	}

	@Override
	public void setPP_Order(I_PP_Order order)
	{
		this.order = order;
	}

	@Override
	public I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return orderBOMLine;
	}

	@Override
	public void setPP_Order_BOMLine(I_PP_Order_BOMLine orderBOMLine)
	{
		this.orderBOMLine = orderBOMLine;
	}

	@Override
	public Timestamp getMovementDate()
	{
		return movementDate;
	}

	@Override
	public void setMovementDate(Timestamp movementDate)
	{
		this.movementDate = movementDate;
	}

	@Override
	public BigDecimal getQtyToReceive()
	{
		return qtyToReceive;
	}

	@Override
	public void setQtyToReceive(BigDecimal qtyToReceive)
	{
		Check.assumeNotNull(qtyToReceive, LiberoException.class, "qtyToReceive not null");
		this.qtyToReceive = qtyToReceive;
	}

	@Override
	public BigDecimal getQtyScrap()
	{
		return qtyScrap;
	}

	@Override
	public void setQtyScrap(BigDecimal qtyScrap)
	{
		Check.assumeNotNull(qtyScrap, LiberoException.class, "qtyScrap not null");
		this.qtyScrap = qtyScrap;
	}

	@Override
	public BigDecimal getQtyReject()
	{
		return qtyReject;
	}

	@Override
	public void setQtyReject(BigDecimal qtyReject)
	{
		Check.assumeNotNull(qtyReject, LiberoException.class, "qtyReject not null");
		this.qtyReject = qtyReject;
	}

	@Override
	public int getM_Locator_ID()
	{
		return locatorId;
	}

	@Override
	public void setM_Locator_ID(int m_Locator_ID)
	{
		this.locatorId = m_Locator_ID;
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return attributeSetInstanceId;
	}

	@Override
	public void setM_AttributeSetInstance_ID(int m_AttributeSetInstance_ID)
	{
		this.attributeSetInstanceId = m_AttributeSetInstance_ID;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public void setC_UOM(I_C_UOM uom)
	{
		Check.assumeNotNull(uom, LiberoException.class, "uom not null");
		this.uom = uom;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public void setM_Product(I_M_Product product)
	{
		this.product = product;
	}

}
