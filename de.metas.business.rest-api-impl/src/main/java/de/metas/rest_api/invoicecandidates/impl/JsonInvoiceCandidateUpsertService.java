package de.metas.rest_api.invoicecandidates.impl;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.BPartnerCompositeLookupKey;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsertItem;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert.JsonResponseInvoiceCandidateUpsertBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsertItem;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsertItem.JsonResponseInvoiceCandidateUpsertItemBuilder;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.IdentifierString;
import lombok.NonNull;

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

@Service
public class JsonInvoiceCandidateUpsertService
{

	// private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient BPartnerQueryService bPartnerQueryService;
	private BPartnerCompositeRepository bpartnerCompositeRepository;

	private JsonInvoiceCandidateUpsertService(
			@NonNull final BPartnerQueryService bPartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository)
	{
		this.bPartnerQueryService = bPartnerQueryService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
	}

	public JsonResponseInvoiceCandidateUpsert createOrUpdateInvoiceCandidates(@NonNull final JsonRequestInvoiceCandidateUpsert request)
	{
		final JsonResponseInvoiceCandidateUpsertBuilder result = JsonResponseInvoiceCandidateUpsert.builder();
		for (final JsonRequestInvoiceCandidateUpsertItem item : request.getRequestItems())
		{

			final IdentifierString bpartnerIdentifier = IdentifierString.of(item.getBillPartnerIdentifier());
			final BPartnerCompositeLookupKey bpartnerIdLookupKey = BPartnerCompositeLookupKey.ofIdentifierString(bpartnerIdentifier);
			final BPartnerQuery query = bPartnerQueryService.createQueryFailIfNotExists(bpartnerIdLookupKey);

			final JsonResponseInvoiceCandidateUpsertItemBuilder responseItem = JsonResponseInvoiceCandidateUpsertItem.builder();

			final Optional<BPartnerComposite> bpartnerComposite;
			try
			{
				bpartnerComposite = bpartnerCompositeRepository.getSingleByQuery(query);
			}
			catch (AdempiereException e)
			{
				throw new AdempiereException("The given lookup keys needs to yield max one BPartnerComposite; multiple items yielded instead", e)
						.appendParametersToMessage()
						.setParameter("BillPartnerIdentifier", item.getBillPartnerIdentifier());
			}

			if(!bpartnerComposite.isPresent())
			{
				// TODO we can still update existing ICs, but not create new ones
				// TODO if we were able to look up an existing IC and item.getBillLocationIdentifier() and/or item.getBillContactIdentifier() are set

			}

			result.responseItem(responseItem.build());
		}

		return result.build();
	}

}
