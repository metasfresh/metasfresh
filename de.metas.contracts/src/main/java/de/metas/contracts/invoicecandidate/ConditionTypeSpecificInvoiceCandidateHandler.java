package de.metas.contracts.invoicecandidate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.function.Consumer;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface ConditionTypeSpecificInvoiceCandidateHandler
{
	String getConditionsType();

	Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(int limit);

	void setSpecificInvoiceCandidateValues(I_C_Invoice_Candidate ic, I_C_Flatrate_Term term);

	BigDecimal calculateQtyOrdered(I_C_Invoice_Candidate invoiceCandidateRecord);

	Timestamp calculateDateOrdered(I_C_Invoice_Candidate invoiceCandidateRecord);

	PriceAndTax calculatePriceAndTax(I_C_Invoice_Candidate invoiceCandidateRecord);

	Consumer<I_C_Invoice_Candidate> getSetInvoiceScheduleImplementation(Consumer<I_C_Invoice_Candidate> defaultImplementation);
}
