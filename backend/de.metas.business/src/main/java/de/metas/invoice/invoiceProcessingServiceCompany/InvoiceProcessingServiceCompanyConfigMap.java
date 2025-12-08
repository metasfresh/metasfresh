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

package de.metas.invoice.invoiceProcessingServiceCompany;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* package */ class InvoiceProcessingServiceCompanyConfigMap
{
	/**
	 * Maps the payment service company (the ones that get the service-fee from us) to configs.
	 */
	private final ImmutableListMultimap<BPartnerId, InvoiceProcessingServiceCompanyConfig> companyBPartnersToConfigsSorted;

	public InvoiceProcessingServiceCompanyConfigMap(@NonNull final List<InvoiceProcessingServiceCompanyConfig> configs)
	{
		final ImmutableListMultimap.Builder<BPartnerId, InvoiceProcessingServiceCompanyConfig> map = ImmutableListMultimap.builder();
		for (final InvoiceProcessingServiceCompanyConfig config : configs)
		{
			map.put(config.getServiceCompanyBPartnerId(), config);
		}
		// for ease of access and calculation: sort by ValidFrom
		map.orderValuesBy(Comparator.comparing(InvoiceProcessingServiceCompanyConfig::getValidFrom));

		companyBPartnersToConfigsSorted = map.build();
	}

	/**
	 * @return if there is an {@link InvoiceProcessingServiceCompanyConfig} with the given {@code customerId} (the one for which a service-company handles payments) at the given {@code validFrom}, then it is returned.<br>
	 * If there is an older config for the given {@code customerId}, but a more recent config which does not have the given {@code customerId},
	 * then {@link Optional#empty()} is returned.
	 */
	@NonNull
	public Optional<InvoiceProcessingServiceCompanyConfig> getByCustomerIdAndDate(@NonNull final BPartnerId customerId, @NonNull final ZonedDateTime validFrom)
	{
		final ImmutableList<InvoiceProcessingServiceCompanyConfig> configsForCompanyBPartners = companyBPartnersToConfigsSorted.values().asList();
		for (int i = configsForCompanyBPartners.size() - 1; i >= 0; i--)
		{
			final InvoiceProcessingServiceCompanyConfig companyConfig = configsForCompanyBPartners.get(i);
			if (companyConfig.isValid(validFrom))
			{
				if (companyConfig.isBPartnerDetailsActive(customerId))
				{
					return Optional.of(companyConfig);
				}
				else
				{
					return Optional.empty();
				}
			}
		}

		return Optional.empty();
	}

	/**
	 * If ValidFrom is before any of the CompanyConfigs.ValidFrom, we will return the CompanyConfig with ValidFrom closest to the received parameter.
	 * <p>
	 * example:
	 * - ValidFrom: 2020-01-01
	 * - CompanyConfig1.ValidFrom: 2020-02-01
	 * - CompanyConfig2.ValidFrom: 2020-03-01
	 * => return CompanyConfig1.
	 */
	public Optional<InvoiceProcessingServiceCompanyConfig> getByServiceCompanyBPartnerIdAndDateIncludingInvalidDates(final BPartnerId serviceCompanyBPartnerId, final ZonedDateTime validFrom)
	{
		final ImmutableList<InvoiceProcessingServiceCompanyConfig> configs = companyBPartnersToConfigsSorted.get(serviceCompanyBPartnerId);

		if (configs.isEmpty())
		{
			return Optional.empty();
		}

		for (int i = configs.size() - 1; i >= 0; i--)
		{
			final InvoiceProcessingServiceCompanyConfig config = configs.get(i);
			if (config.getValidFrom().isBefore(validFrom) || config.getValidFrom().isEqual(validFrom))
			{
				return Optional.of(config);
			}
		}

		return Optional.of(configs.get(0));
	}
}

