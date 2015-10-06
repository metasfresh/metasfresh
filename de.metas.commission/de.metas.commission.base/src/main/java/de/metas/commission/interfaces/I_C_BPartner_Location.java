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


public interface I_C_BPartner_Location extends de.metas.adempiere.model.I_C_BPartner_Location
{
	public static String COLUMNNAME_IsCommissionTo = "IsCommissionTo";

	public boolean isCommissionTo();

	public void setIsCommissionTo(boolean IsCommissionTo);

	public static String COLUMNNAME_IsCommissionToDefault = "IsCommissionToDefault";

	public boolean isCommissionToDefault();

	public void setIsCommissionToDefault(boolean IsCommissionToDefault);
}
