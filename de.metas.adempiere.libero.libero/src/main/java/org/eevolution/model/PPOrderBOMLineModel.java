/**
 * 
 */
package org.eevolution.model;

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

import org.compiere.util.KeyNamePair;

/**
 * Holds the needed data from PP Order BOM Line
 * @author cg
 *
 * @deprecated Planning to remove this model and move the functionality to some other models which could be used in more places, because this one has very limited use-cases where we can use it.
 */
@Deprecated
public class PPOrderBOMLineModel
{
	final KeyNamePair knp;
	final boolean isCritical;
	final String value;
	final int M_Product_ID;
	final BigDecimal qtyToDeliver;	
	final BigDecimal qtyScrapComponent;
	
	public PPOrderBOMLineModel(KeyNamePair knp, boolean isCritical, String value, int m_Product_ID, BigDecimal qtyToDeliver, BigDecimal qtyScrapComponent)
	{
		this.knp = knp;
		this.isCritical = isCritical;
		this.value = value;
		this.M_Product_ID = m_Product_ID;
		this.qtyToDeliver = qtyToDeliver;
		this.qtyScrapComponent = qtyScrapComponent;
	}
	
	
	public KeyNamePair getKnp()
	{
		return knp;
	}

	public boolean isCritical()
	{
		return isCritical;
	}

	public String getValue()
	{
		return value;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public BigDecimal getQtyToDeliver()
	{
		return qtyToDeliver;
	}

	public BigDecimal getQtyScrapComponent()
	{
		return qtyScrapComponent;
	}
	
}
