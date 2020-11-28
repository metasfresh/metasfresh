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

public interface I_C_Invoice_Detail extends org.compiere.model.I_C_Invoice_Detail
{
	//@formatter:off
	 org.adempiere.model.ModelColumn<I_C_Invoice_Detail, I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID =
			 new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, I_C_Invoice_Candidate>(I_C_Invoice_Detail.class, "C_Invoice_Candidate_ID", I_C_Invoice_Candidate.class);

	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";
	void setC_Invoice_Candidate_ID(int C_Invoice_Candidate_ID);
	I_C_Invoice_Candidate getC_Invoice_Candidate();
	int getC_Invoice_Candidate_ID();
	void setC_Invoice_Candidate(final I_C_Invoice_Candidate C_Invoice_Candidate);
	//@formatter:on

}
