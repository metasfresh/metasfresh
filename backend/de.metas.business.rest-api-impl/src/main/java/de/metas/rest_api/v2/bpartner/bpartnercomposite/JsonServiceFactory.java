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
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.job.JobService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.sectionCode.SectionCodeService;
import de.metas.title.TitleRepository;
import de.metas.util.Services;
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
	private final TitleRepository titleRepository;
	private final CurrencyRepository currencyRepository;
	private final JobService jobService;
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final AlbertaBPartnerCompositeService albertaBPartnerCompositeService;
	private final SectionCodeService sectionCodeService;
	private final IncotermsRepository incotermsRepository;
	private final IPaymentTermRepository paymentTermRepository;
	private final BPartnerCreditLimitRepository bPartnerCreditLimitRepository;

	public JsonServiceFactory(
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService,
			@NonNull final BPartnerQueryService bpartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final GreetingRepository greetingRepository,
			@NonNull final TitleRepository titleRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final JobService jobService,
			@NonNull final ExternalReferenceRestControllerService externalReferenceService,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final IncotermsRepository incotermsRepository,
			@NonNull final AlbertaBPartnerCompositeService albertaBPartnerCompositeService,
			@NonNull final BPartnerCreditLimitRepository bPartnerCreditLimitRepository)
	{
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.bpartnerQueryService = bpartnerQueryService;
		this.greetingRepository = greetingRepository;
		this.titleRepository = titleRepository;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.currencyRepository = currencyRepository;
		this.jobService = jobService;
		this.externalReferenceService = externalReferenceService;
		this.sectionCodeService = sectionCodeService;
		this.albertaBPartnerCompositeService = albertaBPartnerCompositeService;
		this.incotermsRepository = incotermsRepository;
		this.paymentTermRepository = Services.get(IPaymentTermRepository.class);
		this.bPartnerCreditLimitRepository = bPartnerCreditLimitRepository;
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
				albertaBPartnerCompositeService,
				sectionCodeService,
				incotermsRepository,
				bPartnerCreditLimitRepository,
				identifier);
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
				titleRepository,
				jobService,
				externalReferenceService,
				paymentTermRepository,
				identifier);
	}
}
