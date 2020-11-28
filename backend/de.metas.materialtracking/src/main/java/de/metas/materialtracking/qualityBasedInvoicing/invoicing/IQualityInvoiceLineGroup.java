package de.metas.materialtracking.qualityBasedInvoicing.invoicing;

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


import java.util.List;

/**
 * A group consists of one "main" line (see {@link #getInvoiceableLine()}) and a number (maybe zero) of preceding or following "detail" lines.
 *
 * @author tsa
 *
 */
public interface IQualityInvoiceLineGroup
{
	/**
	 *
	 * @return never return <code>null</code>.
	 */
	QualityInvoiceLineGroupType getQualityInvoiceLineGroupType();

	IQualityInvoiceLine getInvoiceableLine();

	void setInvoiceableLine(final IQualityInvoiceLine invoiceableLine);

	/**
	 * Each invoice line group can have one detail line which contains informations to be displayed (on the invoice jasper) <b>instead</b> of the actual invoice line's product, qty etc.
	 *
	 * @return
	 */
	IQualityInvoiceLine getInvoiceableLineOverride();

	/**
	 * See {@link #getInvoiceableLineOverride()}.
	 *
	 * @param invoiceableLineOverride
	 */
	void setInvoiceableLineOverride(IQualityInvoiceLine invoiceableLineOverride);

	List<IQualityInvoiceLine> getDetailsBefore();

	void addDetailBefore(final IQualityInvoiceLine line);

	List<IQualityInvoiceLine> getDetailsAfter();

	void addDetailAfter(final IQualityInvoiceLine line);
}
