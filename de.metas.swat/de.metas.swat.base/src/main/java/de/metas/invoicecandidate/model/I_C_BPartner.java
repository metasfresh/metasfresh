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


import de.metas.aggregation.model.I_C_Aggregation;

public interface I_C_BPartner extends de.metas.interfaces.I_C_BPartner
{
	//@formatter:off
    public static final String COLUMNNAME_PO_Invoice_Aggregation_ID = "PO_Invoice_Aggregation_ID";
	public void setPO_Invoice_Aggregation_ID (int PO_Invoice_Aggregation_ID);
	public int getPO_Invoice_Aggregation_ID();
	public I_C_Aggregation getPO_Invoice_Aggregation();
	public void setPO_Invoice_Aggregation(I_C_Aggregation aggregation);
	//@formatter:on

	//@formatter:off
    public static final String COLUMNNAME_PO_InvoiceLine_Aggregation_ID = "PO_InvoiceLine_Aggregation_ID";
	public void setPO_InvoiceLine_Aggregation_ID (int PO_InvoiceLine_Aggregation_ID);
	public int getPO_InvoiceLine_Aggregation_ID();
	public I_C_Aggregation getPO_InvoiceLine_Aggregation();
	public void setPO_InvoiceLine_Aggregation(I_C_Aggregation aggregation);
	//@formatter:on

	//@formatter:off
    public static final String COLUMNNAME_SO_Invoice_Aggregation_ID = "SO_Invoice_Aggregation_ID";
	public void setSO_Invoice_Aggregation_ID (int SO_Invoice_Aggregation_ID);
	public int getSO_Invoice_Aggregation_ID();
	public I_C_Aggregation getSO_Invoice_Aggregation();
	public void setSO_Invoice_Aggregation(I_C_Aggregation aggregation);
	//@formatter:on

	//@formatter:off
    public static final String COLUMNNAME_SO_InvoiceLine_Aggregation_ID = "SO_InvoiceLine_Aggregation_ID";
	public void setSO_InvoiceLine_Aggregation_ID (int SO_InvoiceLine_Aggregation_ID);
	public int getSO_InvoiceLine_Aggregation_ID();
	public I_C_Aggregation getSO_InvoiceLine_Aggregation();
	public void setSO_InvoiceLine_Aggregation(I_C_Aggregation aggregation);
	//@formatter:on
}
