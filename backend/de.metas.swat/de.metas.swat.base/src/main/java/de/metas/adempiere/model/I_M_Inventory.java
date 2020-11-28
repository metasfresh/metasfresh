package de.metas.adempiere.model;

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


import java.math.BigDecimal;

import org.compiere.model.I_M_Product;

public interface I_M_Inventory extends org.compiere.model.I_M_Inventory
{
	public static final String COLUMNNAME_QuickInput_Product_ID = "QuickInput_Product_ID";

	public void setQuickInput_Product_ID(int QuickInput_Product_ID);

	public int getQuickInput_Product_ID();

	public I_M_Product getQuickInput_Product() throws RuntimeException;

	public I_M_Product setQuickInput_Product(I_M_Product product);

	public static final String COLUMNNAME_QuickInput_QtyInternalGain = "QuickInput_QtyInternalGain";

	public void setQuickInput_QtyInternalGain(BigDecimal QuickInput_QtyInternalGain);

	public BigDecimal getQuickInput_QtyInternalGain();

}
