package de.metas.invoicecandidate.externallyreferenced;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class InvoiceCandidateLookupKey
{
	InvoiceCandidateId invoiceCandidateId;
	ExternalId externalHeaderId;
	ExternalId externalLineId;

	@Builder(toBuilder = true)
	private InvoiceCandidateLookupKey(
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@Nullable final ExternalId externalHeaderId,
			@Nullable final ExternalId externalLineId)
	{
		this.invoiceCandidateId = invoiceCandidateId;
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;

		if (invoiceCandidateId == null)
		{
			if (externalHeaderId == null || externalLineId == null)
			{
				throw new AdempiereException(TranslatableStrings.constant("If invoiceCandidateId is unspecified, then both externalHeaderId and externalLineId need to be specified"))
						.appendParametersToMessage()
						.setParameter("invoiceCandidateLookupKey", this);
			}
		}
	}
}
