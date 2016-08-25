/**
 * 
 */
package de.metas.adempiere.form;

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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_M_Product;
import de.metas.product.IProductPA;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class UsedBin
{

	private I_M_Product product;

	private I_M_PackagingContainer packagingContainer;
	
	private int M_Package_ID = 0;
	
	public int getM_Package_ID()
	{
		return M_Package_ID;
	}

	public void setM_Package_ID(int m_Package_ID)
	{
		M_Package_ID = m_Package_ID;
	}

	private boolean markedForPacking = false;

	/**
	 * @return the markedForPacking
	 */
	public boolean isMarkedForPacking()
	{
		return markedForPacking;
	}

	/**
	 * @param markedForPacking the markedForPacking to set
	 */
	public void setMarkedForPacking(boolean markedForPacking)
	{
		this.markedForPacking = markedForPacking;
	}


	private boolean ready = false;

	public boolean isReady()
	{
		return ready;
	}

	public void setReady(boolean ready)
	{
		this.ready = ready;
	}

	private final String trxName;

	private final Properties ctx;

	public String getTrxName()
	{
		return trxName;
	}

	public I_M_PackagingContainer getPackagingContainer()
	{
		return packagingContainer;
	}

	public UsedBin(final Properties ctx, final I_M_PackagingContainer packagingContainer, final String trxName)
	{
		this.packagingContainer = packagingContainer;
		this.trxName = trxName;
		this.ctx = ctx;
	}

	public I_M_Product retrieveProduct(final Properties ctx, final String trxName)
	{

		if (product == null)
		{
			final IProductPA productPA = Services.get(IProductPA.class);
			product = InterfaceWrapperHelper.create(productPA.retrieveProduct(ctx, packagingContainer.getM_Product_ID(), true, trxName), I_M_Product.class);
		}
		return product;
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append(retrieveProduct(ctx, trxName).getName());

		return sb.toString();
	}
}
