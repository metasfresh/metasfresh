package de.metas.edi.model;

/*
 * #%L
 * de.metas.edi
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



public interface I_C_Invoice_Candidate extends de.metas.invoicecandidate.model.I_C_Invoice_Candidate
{

	/** Column definition for IsEdiRecipient */
	public static final org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsEdiRecipient = new org.adempiere.model.ModelColumn<I_C_Invoice_Candidate, Object>(
			I_C_Invoice_Candidate.class, "IsEdiRecipient", null);
	/** Column name IsEdiRecipient */
	public static final String COLUMNNAME_IsEdiRecipient = "IsEdiRecipient";

	public void setIsEdiRecipient(boolean IsEdiRecipient);

	public boolean isEdiRecipient();
	
	
	// @formatter:off
	String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";
	boolean isEdiEnabled();
	void setIsEdiEnabled(boolean IsEdiEnabled);
	// @formatter:on

}
