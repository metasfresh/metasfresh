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


public interface I_C_POSKey extends org.compiere.model.I_C_POSKey
{
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";
	public int getAD_Reference_ID();
	public void setAD_Reference_ID(int AD_Reference_ID);
	public org.compiere.model.I_AD_Reference getAD_Reference();
	
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";
	public int getAD_Val_Rule_ID();
	public void setAD_Val_Rule_ID(int AD_Val_Rule_ID);
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();
}
