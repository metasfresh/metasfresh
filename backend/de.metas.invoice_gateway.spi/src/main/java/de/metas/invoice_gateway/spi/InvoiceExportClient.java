package de.metas.invoice_gateway.spi;

import java.util.List;

import de.metas.invoice_gateway.spi.model.InvoiceExportResult;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;

/*
 * #%L
 * metasfresh-invoice.gateway.commons
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** SPI to extend for different formats and protocols. */
public interface InvoiceExportClient
{
	/**
	 * @return {@code true} if the implementation applies to the given {@code invoice}.
	 * @throws InvoiceNotExportableException if the implementation "should" export the given {@code invoice}, but is not able to do so, e.g. because of missing properties.
	 */
	boolean applies(InvoiceToExport invoice);

	List<InvoiceExportResult> export(InvoiceToExport invoice);

	public static final class InvoiceNotExportableException extends RuntimeException
	{
		private static final long serialVersionUID = 748656736637027017L;

		public InvoiceNotExportableException(String reason)
		{
			super(reason);
		}
	}
}
