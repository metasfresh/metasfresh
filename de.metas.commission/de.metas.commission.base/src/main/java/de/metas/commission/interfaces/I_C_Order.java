package de.metas.commission.interfaces;

/*
 * #%L
 * de.metas.commission.base
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


import de.metas.commission.model.I_C_Sponsor;

public interface I_C_Order extends de.metas.adempiere.model.I_C_Order
{

	public static final String C_Sponsor_ID = "C_Sponsor_ID";

	@Override
	int getC_Sponsor_ID();

	I_C_Sponsor getC_Sponsor();

	@Override
	void setC_Sponsor_ID(int sponsorId);

	public static final String IS_COMMISSION_LOCK = "IsComissionLock";

	boolean isComissionLock();

	void setComissionLock(boolean isComissionLock);

	public static final String COLUMNNAME_IsB2BOrder = "IsB2BOrder";

	public boolean isIsB2BOrder();

	public void setIsB2BOrder(boolean IsB2BOrder);
}
