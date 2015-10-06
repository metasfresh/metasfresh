package de.metas.materialtracking.qualityBasedInvoicing.invoicing.impl;

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

import org.adempiere.util.Check;

import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;

public class QualityInvoiceLineGroup implements IQualityInvoiceLineGroup
{
	private IQualityInvoiceLine invoiceableLine;
	private IQualityInvoiceLine invoiceableLineOverride;
	private QualityInvoiceLineGroupType qualityInvoiceLineGroupType;

	private final List<IQualityInvoiceLine> detailsBefore = new ArrayList<>();
	private final List<IQualityInvoiceLine> detailsAfter = new ArrayList<>();

	public QualityInvoiceLineGroup()
	{
		super();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("QualityInvoiceLineGroup[");
		sb.append("\n\tType: ").append(qualityInvoiceLineGroupType);

		sb.append("\n\tinvoiceableLine: ").append(invoiceableLine);

		if (!detailsBefore.isEmpty())
		{
			sb.append("\n\tDetails(before)");
			for (final IQualityInvoiceLine detail : detailsBefore)
			{
				sb.append("\n\t\t").append(detail);
			}
		}

		if (!detailsAfter.isEmpty())
		{
			sb.append("\n\tDetails(after)");
			for (final IQualityInvoiceLine detail : detailsAfter)
			{
				sb.append("\n\t\t").append(detail);
			}
		}

		sb.append("\n]");
		return sb.toString();
	}

	@Override
	public void setInvoiceableLine(final IQualityInvoiceLine invoiceableLine)
	{
		this.invoiceableLine = invoiceableLine;
	}

	@Override
	public IQualityInvoiceLine getInvoiceableLine()
	{
		Check.assumeNotNull(invoiceableLine, "invoiceableLine not null");
		return invoiceableLine;
	}

	@Override
	public void setInvoiceableLineOverride(final IQualityInvoiceLine invoiceableLineOverride)
	{
		this.invoiceableLineOverride = invoiceableLineOverride;
	}

	@Override
	public IQualityInvoiceLine getInvoiceableLineOverride()
	{
		return invoiceableLineOverride;
	}

	@Override
	public QualityInvoiceLineGroupType getQualityInvoiceLineGroupType()
	{
		return qualityInvoiceLineGroupType;
	}

	public void setQualityInvoiceLineGroupType(final QualityInvoiceLineGroupType qualityInvoiceLineGroupType)
	{
		this.qualityInvoiceLineGroupType = qualityInvoiceLineGroupType;
	}

	@Override
	public void addDetailBefore(final IQualityInvoiceLine line)
	{
		Check.assumeNotNull(line, "line not null");
		detailsBefore.add(line);
	}

	@Override
	public List<IQualityInvoiceLine> getDetailsBefore()
	{
		return detailsBefore;
	}

	@Override
	public void addDetailAfter(final IQualityInvoiceLine line)
	{
		Check.assumeNotNull(line, "line not null");
		detailsAfter.add(line);
	}

	@Override
	public List<IQualityInvoiceLine> getDetailsAfter()
	{
		return detailsAfter;
	}

}
