package de.metas.invoicecandidate.api;

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


import java.util.Date;

public interface IInvoiceCandidateQuery
{
	IInvoiceCandidateQuery copy();

	int getBill_BPartner_ID();

	void setBill_BPartner_ID(int bill_BPartner_ID);

	Date getDateToInvoice();

	void setDateToInvoice(Date dateToInvoice);

	String getHeaderAggregationKey();

	void setHeaderAggregationKey(String headerAggregationKey);

	int getExcludeC_Invoice_Candidate_ID();

	void setExcludeC_Invoice_Candidate_ID(int excludeC_Invoice_Candidate_ID);

	int getMaxManualC_Invoice_Candidate_ID();


	void setMaxManualC_Invoice_Candidate_ID(int maxManualC_Invoice_Candidate_ID);

	Boolean getProcessed();

	void setProcessed(Boolean processed);

}
