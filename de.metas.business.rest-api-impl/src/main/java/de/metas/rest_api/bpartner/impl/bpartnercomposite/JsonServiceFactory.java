package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import org.adempiere.ad.table.RecordChangeLogRepository;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.greeting.GreetingRepository;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.util.lang.UIDStringUtil;
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
public class JsonServiceFactory
{
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final BPGroupRepository bpGroupRepository;
	private final RecordChangeLogRepository recordChangeLogRepository;
	private final GreetingRepository greetingRepository;

	public JsonServiceFactory(
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final GreetingRepository greetingRepository,
			@NonNull final RecordChangeLogRepository recordChangeLogRepository)
	{
		this.greetingRepository = greetingRepository;
		this.recordChangeLogRepository = recordChangeLogRepository;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
	}

	public JsonPersisterService createPersister()
	{
		final String identifier = "persister_" + UIDStringUtil.createNext();
		final JsonRetrieverService jsonRetrieverService = createRetrieverService(identifier);

		return new JsonPersisterService(bpartnerCompositeRepository, bpGroupRepository, jsonRetrieverService, identifier);
	}

	public JsonRetrieverService createRetriever()
	{
		final String identifier = "retriever_" + UIDStringUtil.createNext();
		return createRetrieverService(identifier);
	}

	private JsonRetrieverService createRetrieverService(@NonNull final String identifier)
	{
		return new JsonRetrieverService(bpartnerCompositeRepository, bpGroupRepository, greetingRepository, recordChangeLogRepository, identifier);
	}
}
