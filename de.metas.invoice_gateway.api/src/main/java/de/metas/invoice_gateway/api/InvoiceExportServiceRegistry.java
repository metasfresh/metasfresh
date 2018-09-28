package de.metas.invoice_gateway.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

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
				.filter(Predicates.notNull())
				.collect(GuavaCollectors.toImmutableMapByKey(InvoiceExportClientFactory::getInvoiceExportGatewayId));
	}

	public boolean hasServiceSupport(@Nullable final String shipperGatewayId)
	{
		if(Check.isEmpty(shipperGatewayId,true))
		{
			return false;
		}
		return invoiceExportClientFactoriesByGatewayId.containsKey(shipperGatewayId);
	}

	public InvoiceExportClientFactory getFactory(@NonNull final String invoiceExportGatewayId)
	{
		return invoiceExportClientFactoriesByGatewayId.get(invoiceExportGatewayId);
	}
}
