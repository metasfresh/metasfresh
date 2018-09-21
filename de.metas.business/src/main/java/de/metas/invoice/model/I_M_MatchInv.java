package de.metas.invoice.model;

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


import org.compiere.model.I_C_Invoice;



public interface I_M_MatchInv extends org.compiere.model.I_M_MatchInv
{
	//@formatter:off
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
	void setC_Invoice_ID(int C_Invoice_ID);
	I_C_Invoice getC_Invoice();
	int getC_Invoice_ID();
	void setC_Invoice(final I_C_Invoice C_Invoice);
	//@formatter:on
}
