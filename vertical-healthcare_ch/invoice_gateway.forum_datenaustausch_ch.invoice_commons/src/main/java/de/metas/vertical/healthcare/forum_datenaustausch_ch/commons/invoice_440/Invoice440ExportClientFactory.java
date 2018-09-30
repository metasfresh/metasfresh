package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.invoice_440;

import org.springframework.stereotype.Service;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.BPartnerId;

/*
 * #%L
 * metasfresh-healthcare.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

@Service
public class Invoice440ExportClientFactory implements InvoiceExportClientFactory
{

	@Override
	public String getInvoiceExportGatewayId()
	{
		return "forum_datenaustausch_ch.invoice_440";
	}

	@Override
	public InvoiceExportClient newClientForRecipient(BPartnerId invoiceRecipientId)
	{
		return new Invoice440ExportClient();
	}

}
