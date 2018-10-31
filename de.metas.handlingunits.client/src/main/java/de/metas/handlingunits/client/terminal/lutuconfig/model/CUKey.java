package de.metas.handlingunits.client.terminal.lutuconfig.model;

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

import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.util.Check;

public class CUKey extends AbstractLUTUCUKey
{
	private final I_M_Product product;
	private String id = null;

	private BigDecimal totalQtyCU = null;

	private I_M_HU_LUTU_Configuration lutuConfiguration = null;

	public CUKey(final ITerminalContext terminalContext, final I_M_Product product)
	{
		super(terminalContext);

		Check.assumeNotNull(product, "product not null");
		this.product = product;
	}

	@Override
	public final String getId()
	{
		if (id == null)
		{
			id = createId();
		}
		return id;
	}

	protected String createId()
	{
		final int productId = getM_Product().getM_Product_ID();
		final String id =
				"CUKey" // we use CUKey instead of "getClass().getName()" because we want to match between multiple instances
						+ "#M_Product_ID=" + productId;
		return id;
	}

	@Override
	public final KeyNamePair getValue()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public final I_M_HU_PI getM_HU_PI()
	{
		return null;
	}

	public final I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	protected String createName()
	{
		return product.getName();
	}

	/**
	 * Gets suggested Total Qty CU.
	 *
	 * If not null, this total Qty CU will be distributed tu TUs and LUs.
	 *
	 * @return total Qty CU or null
	 */
	public final BigDecimal getTotalQtyCU()
	{
		return totalQtyCU;
	}

	public final void setTotalQtyCU(final BigDecimal totalQtyCU)
	{
		this.totalQtyCU = totalQtyCU;
	}

	/**
	 * Sets suggested LU/TU Configuration.
	 *
	 * If not null, this configuration will be used to preselect the TU and LU keys when user presses on this CU key.
	 *
	 * @return suggested LU/TU Configuration or null
	 */
	public void setDefaultLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		this.lutuConfiguration = lutuConfiguration;
	}

	/**
	 * Gets suggested LU/TU Configuration.
	 *
	 * If not null, this configuration will be used to preselect the TU and LU keys when user presses on this CU key.
	 *
	 * @return suggested LU/TU Configuration or null
	 */
	public I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		return lutuConfiguration;
	}
}
