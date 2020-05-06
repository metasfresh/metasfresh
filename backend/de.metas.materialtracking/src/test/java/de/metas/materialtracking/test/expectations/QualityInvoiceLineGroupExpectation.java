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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.junit.Assert;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Validates {@link IQualityInvoiceLineGroup}, {@link I_C_Invoice_Candidate}.
 *
 * @author tsa
 *
 */
public class QualityInvoiceLineGroupExpectation extends AbstractExpectation
{
	private final QualityInvoiceLineGroupExpectations parent;
	private String expectationName = null;

	private QualityInvoiceLineGroupType type = null;
	private QualityInvoiceLineExpectation overridingDetailException = null;
	private QualityInvoiceLineExpectation invoiceableLineExpectation = null;
	private List<QualityInvoiceLineExpectation> detailsBeforeExpectations = new ArrayList<>();
	private List<QualityInvoiceLineExpectation> detailsAfterExpectations = new ArrayList<>();

	/* package */QualityInvoiceLineGroupExpectation(QualityInvoiceLineGroupExpectations parent)
	{
		super();
		this.parent = parent;
	}

	private <T extends QualityInvoiceLineExpectation> T createQualityInvoiceLineExpectation(Class<T> clazz)
	{
		Constructor<T> constructor;
		try
		{
			constructor = clazz.getConstructor(QualityInvoiceLineGroupExpectation.class);
			return constructor.newInstance(this);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	public QualityInvoiceLineGroupExpectations endExpectation()
	{
		Check.assumeNotNull(parent, "parent not null");
		return parent;
	}

	public QualityInvoiceLineGroupExpectation expectationName(final String name)
	{
		this.expectationName = name;
		return this;
	}

	public QualityInvoiceLineGroupExpectation type(final QualityInvoiceLineGroupType type)
	{
		this.type = type;
		return this;
	}

	public QualityInvoiceLineExpectation invoiceableLineExpectation()
	{
		return invoiceableLineExpectation(QualityInvoiceLineExpectation.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends QualityInvoiceLineExpectation> T invoiceableLineExpectation(Class<T> clazz)
	{
		if (invoiceableLineExpectation == null)
		{
			invoiceableLineExpectation = createQualityInvoiceLineExpectation(clazz);
		}
		return (T)invoiceableLineExpectation;
	}

	public QualityInvoiceLineExpectation newDetailOverrideExpectation()
	{
		return newDetailOverrideExpectation(QualityInvoiceLineExpectation.class);
	}

	public <T extends QualityInvoiceLineExpectation> T newDetailOverrideExpectation(Class<T> clazz)
	{
		final T expectation = createQualityInvoiceLineExpectation(clazz);
		expectation.printBefore(true);
		overridingDetailException = expectation;
		return expectation;
	}

	public QualityInvoiceLineExpectation newBeforeDetailExpectation()
	{
		return newBeforeDetailExpectation(QualityInvoiceLineExpectation.class);
	}

	public <T extends QualityInvoiceLineExpectation> T newBeforeDetailExpectation(Class<T> clazz)
	{
		final T expectation = createQualityInvoiceLineExpectation(clazz);
		expectation.printBefore(true);
		detailsBeforeExpectations.add(expectation);
		return expectation;
	}

	public QualityInvoiceLineExpectation newAfterDetailExpectation()
	{
		return newAfterDetailExpectation(QualityInvoiceLineExpectation.class);
	}

	public <T extends QualityInvoiceLineExpectation> T newAfterDetailExpectation(Class<T> clazz)
	{
		final T expectation = createQualityInvoiceLineExpectation(clazz);
		expectation.printBefore(false);
		detailsAfterExpectations.add(expectation);
		return expectation;
	}

	public void assertExpected(final IQualityInvoiceLineGroup group)
	{
		final String prefix = "Group: " + group
				+ "\nExpectation name: " + expectationName
				+ "\n\nInvalid ";
		Assert.assertEquals(prefix + "Type", this.type, group.getQualityInvoiceLineGroupType());

		if (invoiceableLineExpectation != null)
		{
			final IQualityInvoiceLine invoiceableLine = group.getInvoiceableLine();
			invoiceableLineExpectation.assertExpected("invoiceable line", invoiceableLine);
		}

		assertExpected(prefix + "before details\n\nInvalid: ", detailsBeforeExpectations, group.getDetailsBefore());
		assertExpected(prefix + "after details\n\nInvalid: ", detailsAfterExpectations, group.getDetailsAfter());
	}

	private void assertExpected(final String message, final List<QualityInvoiceLineExpectation> expectations, final List<IQualityInvoiceLine> lines)
	{
		final int count = lines.size();
		final int expectedCount = expectations.size();

		Assert.assertEquals(message + " lines count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final IQualityInvoiceLine line = lines.get(i);
			final QualityInvoiceLineExpectation expectation = expectations.get(i);

			expectation.assertExpected(message, line);
		}
	}

	public void assertExpected(final I_C_Invoice_Candidate ic)
	{
		if (invoiceableLineExpectation != null)
		{
			invoiceableLineExpectation.assertExpected("invoiceable line", ic);
		}

		if (overridingDetailException != null)
		{
			final List<I_C_Invoice_Detail> overridingDetail = retriveInvoiceOverridingDetail(ic);

			Assert.assertEquals("overriding details: there shall be exactly one", overridingDetail.size(), 1);
			overridingDetailException.assertExpected("overridingDetailException", overridingDetail.get(0));
		}

		final List<I_C_Invoice_Detail> detailsBefore = retriveInvoiceDetails(ic, true);
		assertExpectedInvoiceDetails("before details", detailsBeforeExpectations, detailsBefore);

		final List<I_C_Invoice_Detail> detailsAfter = retriveInvoiceDetails(ic, false);
		assertExpectedInvoiceDetails("after details", detailsAfterExpectations, detailsAfter);
	}

	private void assertExpectedInvoiceDetails(final String message,
			final List<QualityInvoiceLineExpectation> expectations,
			final List<I_C_Invoice_Detail> details)
	{
		final int count = details.size();
		final int expectedCount = expectations.size();

		Assert.assertEquals("Invalid expected lines count: " + message, expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_C_Invoice_Detail detail = details.get(i);
			final QualityInvoiceLineExpectation expectation = expectations.get(i);

			expectation.assertExpected(message, detail);
		}
	}

	private List<I_C_Invoice_Detail> retriveInvoiceOverridingDetail(final I_C_Invoice_Candidate ic)
	{
		final IQueryBuilder<I_C_Invoice_Detail> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Detail.class, ic)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_IsDetailOverridesLine, true);

		queryBuilder.orderBy()
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_IsDetailOverridesLine)
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_SeqNo);

		return queryBuilder.create().list();
	}

	private List<I_C_Invoice_Detail> retriveInvoiceDetails(final I_C_Invoice_Candidate ic, final boolean isPrintBefore)
	{
		final IQueryBuilder<I_C_Invoice_Detail> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Detail.class, ic)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, ic.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_IsPrintBefore, isPrintBefore)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_IsDetailOverridesLine, false);

		queryBuilder.orderBy()
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_IsPrintBefore)
				.addColumn(I_C_Invoice_Detail.COLUMNNAME_SeqNo);

		return queryBuilder.create().list();
	}
}
