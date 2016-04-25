package org.eevolution.mrp.api.impl;

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


import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_AD_Note;

public class MRPNoteMatcher implements Predicate<I_AD_Note>
{
	// used in toString()
	@SuppressWarnings("unused")
	private String _mrpCode;
	
	private int _mrpCodeMessageId = -1;
	private I_M_Product _product = null;
	private I_M_Warehouse _warehouse = null;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean evaluate(I_AD_Note note)
	{
		if (note == null)
		{
			return false;
		}

		final int mrpCodeMessageId = getMRPCode_Message_ID();
		if (mrpCodeMessageId > 0 && mrpCodeMessageId != note.getAD_Message_ID())
		{
			return false;
		}

		final int productId = getM_Product_ID();
		if (productId > 0 && productId != note.getM_Product_ID())
		{
			return false;
		}

		final int warehouseId = getM_Warehouse_ID();
		if (warehouseId > 0 && warehouseId != note.getM_Warehouse_ID())
		{
			return false;
		}

		return true;
	}

	public void setM_Product(final I_M_Product product)
	{
		this._product = product;
	}

	private int getM_Product_ID()
	{
		if (_product == null)
		{
			return -1;
		}
		else
		{
			return _product.getM_Product_ID();
		}
	}

	public void setMRPCode(final String mrpCode)
	{
		this._mrpCode = mrpCode;
		if (Check.isEmpty(mrpCode, true))
		{
			this._mrpCodeMessageId = -1;
		}
		else
		{
			this._mrpCodeMessageId = Services.get(IADMessageDAO.class).retrieveIdByValue(Env.getCtx(), mrpCode);
			Check.assume(_mrpCodeMessageId > 0, LiberoException.class, "AD_Message_ID > 0 for Value={}", mrpCode);
		}
	}

	private int getMRPCode_Message_ID()
	{
		return this._mrpCodeMessageId;
	}

	public void setM_Warehouse(I_M_Warehouse warehouse)
	{
		this._warehouse = warehouse;
	}

	private int getM_Warehouse_ID()
	{
		if (_warehouse == null)
		{
			return -1;
		}
		return _warehouse.getM_Warehouse_ID();
	}
}
