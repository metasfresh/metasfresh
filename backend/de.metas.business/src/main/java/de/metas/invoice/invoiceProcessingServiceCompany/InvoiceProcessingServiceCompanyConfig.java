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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.common.util.Check.isEmpty;

@Value
@Builder
public class InvoiceProcessingServiceCompanyConfig
{
	@NonNull
	BPartnerId serviceCompanyBPartnerId;

	@NonNull
	DocTypeId serviceInvoiceDocTypeId;

	@NonNull
	ProductId serviceFeeProductId;

	@NonNull
	ZonedDateTime validFrom;

	@Getter(AccessLevel.NONE)
	@NonNull ImmutableMultimap<BPartnerId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> bpartnerDetails;

	@NonNull
	public Optional<Percent> getFeePercentageOfGrandTotalByBpartner(@NonNull final BPartnerId bpartnerId, @Nullable final DocTypeId invoiceDocTypeId)
	{

		final ImmutableCollection<InvoiceProcessingServiceCompanyConfigBPartnerDetails> detailsList = bpartnerDetails.get(bpartnerId);

		if (isEmpty(detailsList))
		{
			return Optional.empty();
		}

		if (invoiceDocTypeId != null)
		{
			final Optional<Percent> matchingDocTypePercent = detailsList.stream()
					.filter(InvoiceProcessingServiceCompanyConfigBPartnerDetails::isActive)
					.filter(details -> invoiceDocTypeId.equals(details.getDocTypeId()))
					.map(InvoiceProcessingServiceCompanyConfigBPartnerDetails::getPercent)
					.findFirst();

			if (matchingDocTypePercent.isPresent())
			{
				return matchingDocTypePercent;
			}
		}

		return detailsList.stream()
				.filter(InvoiceProcessingServiceCompanyConfigBPartnerDetails::isActive)
				.filter(details -> details.getDocTypeId() == null)
				.map(InvoiceProcessingServiceCompanyConfigBPartnerDetails::getPercent)
				.findFirst();
	}

	public boolean isBPartnerDetailsActive(@NonNull final BPartnerId bpartnerId)
	{
		final ImmutableCollection<InvoiceProcessingServiceCompanyConfigBPartnerDetails> detailsList = bpartnerDetails.get(bpartnerId);

		if (detailsList.isEmpty())
		{
			return false;
		}

		return detailsList.stream()
				.anyMatch(InvoiceProcessingServiceCompanyConfigBPartnerDetails::isActive);
	}

	public boolean isValid(@NonNull final ZonedDateTime validFrom)
	{
		return this.validFrom.isBefore(validFrom) || this.validFrom.isEqual(validFrom);
	}
}

@Builder
@Value
		/* package */ class InvoiceProcessingServiceCompanyConfigBPartnerDetails
{
	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	Percent percent;

	@Nullable
	DocTypeId docTypeId;

	@Default
	boolean isActive = true;
}
