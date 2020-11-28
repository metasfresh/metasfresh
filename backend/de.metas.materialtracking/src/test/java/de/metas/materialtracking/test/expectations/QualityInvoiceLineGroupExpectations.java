package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;

public class QualityInvoiceLineGroupExpectations
{
	private List<QualityInvoiceLineGroupExpectation> expectations = new ArrayList<>();

	public QualityInvoiceLineGroupExpectations()
	{
		super();
	}

	public QualityInvoiceLineGroupExpectation newExpectation()
	{
		final QualityInvoiceLineGroupExpectation expectation = new QualityInvoiceLineGroupExpectation(this);
		expectations.add(expectation);

		return expectation;
	}

	public void assertExpected(final List<IQualityInvoiceLineGroup> invoiceLineGroups)
	{
		final int count = invoiceLineGroups.size();
		final int expectedCount = expectations.size();
		Assert.assertEquals("Invalid expected groups count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final IQualityInvoiceLineGroup group = invoiceLineGroups.get(i);
			final QualityInvoiceLineGroupExpectation expectation = expectations.get(i);

			expectation.assertExpected(group);
		}
	}

	public void assertExpectedInvoiceCandidates(final List<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		final int count = invoiceCandidates.size();
		final int expectedCount = expectations.size();
		Assert.assertEquals("Invalid expected groups count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_C_Invoice_Candidate ic = invoiceCandidates.get(i);
			final QualityInvoiceLineGroupExpectation expectation = expectations.get(i);

			expectation.assertExpected(ic);
		}
	}
}
