package de.metas.fresh.ordercheckup.model;


/*
 * #%L
 * de.metas.fresh.base
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

public interface I_PP_Product_Planning extends org.eevolution.model.I_PP_Product_Planning
{

	/**
	 * IsTraded AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISTRADED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISTRADED_Yes = "Y";
	/** No = N */
	public static final String ISTRADED_No = "N";

	/**
	 * Set Traded.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsTraded (String IsTraded);

	/**
	 * Get Traded.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public String getIsTraded();

    /** Column definition for IsTraded */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_Planning, Object> COLUMN_IsTraded = new org.adempiere.model.ModelColumn<I_PP_Product_Planning, Object>(I_PP_Product_Planning.class, "IsTraded", null);
    /** Column name IsTraded */
    public static final String COLUMNNAME_IsTraded = "IsTraded";
}
