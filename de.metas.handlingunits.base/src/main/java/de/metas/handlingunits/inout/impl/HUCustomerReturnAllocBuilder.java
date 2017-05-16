package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.inout.IHUCustomerReturnAllocBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleAllocBuilder;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_InOutLine_HU_Alloc;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUCustomerReturnAllocBuilder implements IHUCustomerReturnAllocBuilder
{
	// services
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private Quantity _huQtyAllocated;
	private I_M_HU _luHU;
	private I_M_HU _tuHU;
	private I_M_HU _vhu;
	

	private I_M_InOutLine _inOutLine;
	private BigDecimal _qtyToAllocate;
	private BigDecimal _qtyWithIssues;
	private IContextAware _context;
	
	
	/*
	 * 		builder.setContext(contextProvider)

				.setM_InOutLine(inOutLine)
				.setQtyToAllocate(BigDecimal.ZERO)
				.setQtyWithIssues(BigDecimal.ZERO) // to be sure...
		;
		builder.setHU_QtyAllocated(qtyToAllocate, uom)
				.setM_LU_HU(luHU)
				.setM_TU_HU(tuHUActual)
				.setVHU(vhu);

		// Create RSA and save it
		builder.buildAndSave();
		*/
	
	@Override
	public I_M_InOutLine_HU_Alloc build()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_M_InOutLine_HU_Alloc buildAndSave()
	{
		final I_M_InOutLine_HU_Alloc rsa = build();
		InterfaceWrapperHelper.save(rsa);
		return rsa;
	}

	@Override
	public IHUCustomerReturnAllocBuilder setContext(IContextAware context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private BigDecimal getQtyToAllocate()
	{
		Check.assumeNotNull(_qtyToAllocate, "qtyToAllocate not null");
		return _qtyToAllocate;
	}

	@Override
	public IHUCustomerReturnAllocBuilder setQtyToAllocate(BigDecimal qtyToAllocate)
	{
		this._qtyToAllocate = qtyToAllocate;
		return this;
	}


	@Override
	public IHUCustomerReturnAllocBuilder setM_InOutLine(I_M_InOutLine receiptLine)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_M_InOutLine_HU_Alloc build(I_M_InOutLine_HU_Alloc huAlloc)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IHUCustomerReturnAllocBuilder setHU_QtyAllocated(BigDecimal qtyAllocated)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IHUCustomerReturnAllocBuilder setHU_QtyAllocated(BigDecimal qtyAllocated, I_C_UOM uom)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQtyWithIssues(BigDecimal zero)
	{
		// TODO Auto-generated method stub
		
	}

	

	public I_M_InOutLine get_inOutLine()
	{
		return _inOutLine;
	}

	public void set_inOutLine(I_M_InOutLine _inOutLine)
	{
		this._inOutLine = _inOutLine;
	}
	
	private I_M_HU getM_LU_HU()
	{
		return _luHU;
	}

	public IHUCustomerReturnAllocBuilder setM_LU_HU(final I_M_HU luHU)
	{
		_luHU = luHU;
		return this;
	}

	private I_M_HU getM_TU_HU()
	{
		return _tuHU;
	}

	@Override
	public IHUCustomerReturnAllocBuilder setM_TU_HU(final I_M_HU tuHU)
	{
		_tuHU = tuHU;
		return this;
	}

	private I_M_HU getVHU()
	{
		return _vhu;
	}

	public IHUCustomerReturnAllocBuilder setVHU(final I_M_HU vhu)
	{
		_vhu = vhu;
		return this;
	}


}
