package de.metas.invoicecandidate.model;

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


import de.metas.inout.model.I_M_InOut;

/**
 
 * 
 * @author ts
 * 
 */
public interface I_C_Invoice extends de.metas.adempiere.model.I_C_Invoice
{
	/**
	 * Allow our invoice which was created from invoice candidates to reference its respective inout.
	 * @task 06630 
	 */
	// @formatter:off
	public static final String COLUMNNAME_M_InOut = "M_InOut";
	public void setM_InOut_ID(int M_InOut_ID);
	public int getM_InOut_ID();
	public I_M_InOut getM_InOut();
	// @formatter:on
	
    /** Column name C_Async_Batch_ID */
    public static final String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/** Set Async Batch	  */
	public void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/** Get Async Batch	  */
	public int getC_Async_Batch_ID();
}
