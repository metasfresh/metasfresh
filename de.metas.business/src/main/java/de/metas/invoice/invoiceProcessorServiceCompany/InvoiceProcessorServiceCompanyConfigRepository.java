package de.metas.invoice.invoiceProcessorServiceCompany;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Repository
public class InvoiceProcessorServiceCompanyConfigRepository
{
	private final CCache<InvoiceProcessorServiceCompanyConfigId, InvoiceProcessorServiceCompanyConfig> //
	configsById = CCache.<InvoiceProcessorServiceCompanyConfigId, InvoiceProcessorServiceCompanyConfig> builder()
			// TODO .tableName(tableName)
			.build();

	private final CCache<Integer, CustomerToConfigAssignmentMap> //
	customerToConfigAssignmentsCache = CCache.<Integer, CustomerToConfigAssignmentMap> builder()
			// TODO .tableName(tableName)
			.build();

	public Optional<InvoiceProcessorServiceCompanyConfig> getByCustomerId(@NonNull final BPartnerId customerId)
	{
		return getConfigIdByCustomerId(customerId)
				.map(this::getById);
	}

	private Optional<InvoiceProcessorServiceCompanyConfigId> getConfigIdByCustomerId(@NonNull final BPartnerId customerId)
	{
		final CustomerToConfigAssignmentMap customerToConfigAssignmentMap = customerToConfigAssignmentsCache.getOrLoad(0, this::retrieveCustomerToConfigAssignmentMap);
		return customerToConfigAssignmentMap.getConfigIdByCustomerId(customerId);
	}

	private InvoiceProcessorServiceCompanyConfig getById(@NonNull final InvoiceProcessorServiceCompanyConfigId configId)
	{
		return configsById.getOrLoad(configId, this::retrieveById);
	}

	private InvoiceProcessorServiceCompanyConfig retrieveById(@NonNull final InvoiceProcessorServiceCompanyConfigId configId)
	{
		// TODO: impl
		throw new UnsupportedOperationException();
	}

	private CustomerToConfigAssignmentMap retrieveCustomerToConfigAssignmentMap()
	{
		// TODO: impl
		throw new UnsupportedOperationException();
	}

	//
	//
	//

	private static class CustomerToConfigAssignmentMap
	{
		private final ImmutableMap<BPartnerId, InvoiceProcessorServiceCompanyConfigId> configIdsByCustomerId;

		private CustomerToConfigAssignmentMap(final Map<BPartnerId, InvoiceProcessorServiceCompanyConfigId> configIdsByCustomerId)
		{
			this.configIdsByCustomerId = ImmutableMap.copyOf(configIdsByCustomerId);
		}

		public Optional<InvoiceProcessorServiceCompanyConfigId> getConfigIdByCustomerId(@NonNull final BPartnerId customerId)
		{
			return Optional.ofNullable(configIdsByCustomerId.get(customerId));
		}
	}
}
