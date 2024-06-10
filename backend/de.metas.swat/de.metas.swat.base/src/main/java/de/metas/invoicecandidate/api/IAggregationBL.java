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

import de.metas.aggregation.api.AggregationKey;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import de.metas.util.IProcessor;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.util.List;

public interface IAggregationBL extends ISingletonService
{
	/**
	 * If <code>ic</code> has a <code>C_Invoice_Candidate_Agg</code> and that record has its' <code>Classname</code> column set, the method returns a new instance of that class.<br>
	 * Otherwise, it returns a new instance of {@link DefaultAggregator}.
	 *
	 * @param ic invoice candidate
	 * @return invoice line aggregator to be used for given invoice candidate; never returns <code>null</code>
	 */
	IAggregator createInvoiceLineAggregatorInstance(I_C_Invoice_Candidate ic);

	/**
	 * Make sure the {@link I_C_Invoice_Candidate_Agg#getClassname()} exists and is implementing {@link IAggregator} interface.
	 */
	void evalClassName(I_C_Invoice_Candidate_Agg agg);

	/**
	 * Creates a plain {@link IInvoiceLineRW} instance.
	 */
	IInvoiceLineRW mkInvoiceLine();

	/**
	 * Creates a new {@link IInvoiceLineRW}, and set every properties of that line from the given <code>template</code>.
	 */
	IInvoiceLineRW mkInvoiceLine(IInvoiceLineRW template);

	/**
	 * Creates a plain {@link IInvoiceCandAggregate} instance.
	 */
	IInvoiceCandAggregate mkInvoiceCandAggregate();

	IAggregationKeyBuilder<I_C_Invoice_Candidate> getHeaderAggregationKeyBuilder();

	/**
	 * Build an invoice header aggregation key. In other words, all {@link I_C_Invoice_Candidate}s which will go to same invoice will have the same header aggregation key.
	 *
	 * @return header aggregation key
	 */
	AggregationKey mkHeaderAggregationKey(I_C_Invoice_Candidate ic);

	AggregationKey mkLineAggregationKey(I_C_Invoice_Candidate ic);

	/**
	 * Gets the {@link IProcessor} used to update aggregation related informations of an {@link I_C_Invoice_Candidate}.
	 * <p>
	 * i.e.
	 * <ul>
	 * <li> {@link I_C_Invoice_Candidate#setC_Invoice_Candidate_Agg(I_C_Invoice_Candidate_Agg)}
	 * <li> {@link I_C_Invoice_Candidate#setHeaderAggregationKey(String)}
	 * <li> {@link I_C_Invoice_Candidate#setLineAggregationKey(String)}
	 * </ul>
	 */
	IProcessor<I_C_Invoice_Candidate> getUpdateProcessor();

	/**
	 * Convenience method that returns <code>true</code> if the given <code>iciol</code>'s inOutLine has it's <code>InDispute</code> flag set. <br>
	 * If iciol is <code>null</code> or if the iciol's M_InOutLine is not set, then the method return <code>false</code>.
	 */
	boolean isIolInDispute(I_C_InvoiceCandidate_InOutLine iciol);

	/**
	 * Extract invoice line relevant product attributes from {@link I_M_InOutLine}.
	 * <p>
	 * Relevant attributes from inout line are those that:
	 * <ul>
	 * <li>are inside the inout line's ASI
	 * <li>have {@link I_M_Attribute#isAttrDocumentRelevant()} set.
	 * </ul>
	 *
	 * @return invoice line product attributes
	 *
	 * task 08451- this task was the one that initially demanded to aggregate invoice lines per ASI relevant attributes
	 */
	@NonNull
	List<IInvoiceLineAttribute> extractInvoiceLineAttributes(@Nullable final I_M_InOutLine inOutLine);

	@NonNull
	List<IInvoiceLineAttribute> extractInvoiceLineAttributes(@NonNull I_C_Invoice_Candidate icRecord);

	/**
	 * Builds and set Header Aggregation Keys (calculated and the actual one).
	 * <p>
	 * Also set the invoicing group.
	 */
	void setHeaderAggregationKey(I_C_Invoice_Candidate ic);

	/**
	 * Reset Header Aggregation Keys (calculated and actual).
	 * <p>
	 * Also resets the invoicing group.
	 */
	void resetHeaderAggregationKey(I_C_Invoice_Candidate ic);
}
