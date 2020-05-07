package de.metas.picking.legacy.form;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Properties;

import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.product.IProductPA;

public class AvailableBins implements Comparable<AvailableBins>
{

	private I_M_Product product;

	private final I_M_PackagingContainer pc;

	private int qtyAvail;

	private final String trxName;
	
	private final Properties ctx;

	public AvailableBins(final AvailableBins orig)
	{

		this.pc = orig.pc;
		this.qtyAvail = orig.qtyAvail;
		this.ctx = orig.ctx;
		this.trxName = orig.trxName;
		this.product = orig.product;
	}

	public AvailableBins(final Properties ctx, final I_M_PackagingContainer pc, final int qtyAvail, final String trxName)
	{
		super();
		this.pc = pc;
		this.qtyAvail = qtyAvail;
		this.trxName = trxName;
		this.ctx = ctx;
	}

	public int compareTo(AvailableBins o)
	{

		return pc.getMaxVolume().compareTo(o.getPc().getMaxVolume());
	}

	public I_M_PackagingContainer getPc()
	{
		return pc;
	}

	public int getQtyAvail()
	{
		return qtyAvail;
	}

	public void setQtyAvail(final int qtyAvail)
	{
		this.qtyAvail = qtyAvail;
	}

	public I_M_Product retrieveProduct(final Properties ctx, final String trxName)
	{

		if (product == null)
		{
			final IProductPA productPA = Services.get(IProductPA.class);
			product = productPA.retrieveProduct(ctx, pc.getM_Product_ID(), true, trxName);
		}
		return product;
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append(getQtyAvail());
		sb.append(" x ");
		sb.append(retrieveProduct(ctx, trxName).getName());

		return sb.toString();
	}

	public String getTrxName()
	{
		return trxName;
	}

}
