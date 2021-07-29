/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.util.lang.UIDStringUtil;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class JsonServiceFactory
{
	private final JsonRequestConsolidateService jsonRequestConsolidateService;
	private final BPartnerQueryService bpartnerQueryService;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final BPGroupRepository bpGroupRepository;
	private final GreetingRepository greetingRepository;
	private final CurrencyRepository currencyRepository;
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final AlbertaBPartnerCompositeService albertaBPartnerCompositeService;

	public JsonServiceFactory(
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService,
			@NonNull final BPartnerQueryService bpartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final GreetingRepository greetingRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ExternalReferenceRestControllerService externalReferenceService, final AlbertaBPartnerCompositeService albertaBPartnerCompositeService)
	{
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.bpartnerQueryService = bpartnerQueryService;
		this.greetingRepository = greetingRepository;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.currencyRepository = currencyRepository;
		this.externalReferenceService = externalReferenceService;
		this.albertaBPartnerCompositeService = albertaBPartnerCompositeService;
	}

	public JsonPersisterService createPersister()
	{
		final String identifier = "persister_" + UIDStringUtil.createNext();
		final JsonRetrieverService jsonRetrieverService = createRetrieverService(identifier);

		return new JsonPersisterService(
				jsonRetrieverService,
				jsonRequestConsolidateService,
				bpartnerCompositeRepository,
				bpGroupRepository,
				currencyRepository,
				externalReferenceService,
				albertaBPartnerCompositeService, identifier);
	}

	public JsonRetrieverService createRetriever()
	{
		final String identifier = "retriever_" + UIDStringUtil.createNext();
		return createRetrieverService(identifier);
	}

	private JsonRetrieverService createRetrieverService(@NonNull final String identifier)
	{
		return new JsonRetrieverService(
				bpartnerQueryService,
				bpartnerCompositeRepository,
				bpGroupRepository,
				greetingRepository,
				externalReferenceService,
				identifier);
	}
}
