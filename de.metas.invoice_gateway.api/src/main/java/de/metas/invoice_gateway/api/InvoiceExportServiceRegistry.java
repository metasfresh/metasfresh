package de.metas.invoice_gateway.api;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.Objects;
import com.google.common.collect.ImmutableList;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class InvoiceExportServiceRegistry
{
	private final Map<String, InvoiceExportClientFactory> invoiceExportClientFactoriesByGatewayId;

	public InvoiceExportServiceRegistry(Optional<List<InvoiceExportClientFactory>> invoiceExportClientFactories)
	{
		invoiceExportClientFactoriesByGatewayId = invoiceExportClientFactories.orElse(ImmutableList.of())
				.stream()
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMapByKey(InvoiceExportClientFactory::getInvoiceExportProviderId));
	}

	public boolean hasServiceSupport(@Nullable final String invoiceExportGatewayId)
	{
		if (Check.isEmpty(invoiceExportGatewayId, true))
		{
			return false;
		}
		return invoiceExportClientFactoriesByGatewayId.containsKey(invoiceExportGatewayId);
	}

	public List<InvoiceExportClient> createExportClients(@NonNull final InvoiceToExport invoice)
	{
		final Collection<InvoiceExportClientFactory> factories = invoiceExportClientFactoriesByGatewayId.values();

		final ImmutableList.Builder<InvoiceExportClient> result = ImmutableList.builder();
		for (final InvoiceExportClientFactory factory : factories)
		{
			final Optional<InvoiceExportClient> newClientForInvoice = factory.newClientForInvoice(invoice);
			newClientForInvoice.ifPresent(result::add);
		}
		return result.build();
	}
}
