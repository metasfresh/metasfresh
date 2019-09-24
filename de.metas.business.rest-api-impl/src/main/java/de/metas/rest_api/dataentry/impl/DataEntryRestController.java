package de.metas.rest_api.dataentry.impl;

import org.adempiere.ad.element.api.AdWindowId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;

import de.metas.Profiles;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.logging.LogManager;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntry;
import de.metas.rest_api.dataentry.impl.dto.JsonDataEntryResponse;
import de.metas.rest_api.utils.RestApiUtils;
import de.metas.util.rest.MetasfreshRestAPIConstants;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
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

@RestController
@RequestMapping(DataEntryRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class DataEntryRestController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/dataentry";

	private static final transient Logger log = LogManager.getLogger(DataEntryRestController.class);

	private final DataEntryLayoutRepository layoutRepo;
	private final DataEntryRecordCache dataRecords;

	DataEntryRestController(
			@NonNull final DataEntryLayoutRepository layoutRepo,
			@NonNull final DataEntryRecordRepository recordsRepo,
			@Value("${de.metas.dataentry.rest_api.DataEntryRestController.cacheCapacity:200}") final int cacheCapacity)
	{
		this.layoutRepo = layoutRepo;
		this.dataRecords = new DataEntryRecordCache(recordsRepo, cacheCapacity);
	}

	@GetMapping("/byId/{windowId}/{recordId}")
	public ResponseEntity<JsonDataEntryResponse> getByRecordId(
			// with swagger 2.9.2, parameters are always ordered alphabetically, see https://github.com/springfox/springfox/issues/2418
			@PathVariable("windowId") final int windowId,
			@PathVariable("recordId") final int recordId)
	{
		final Stopwatch w = Stopwatch.createStarted();
		final String adLanguage = RestApiUtils.getAdLanguage();
		final ResponseEntity<JsonDataEntryResponse> jsonDataEntry = getByRecordId0(AdWindowId.ofRepoId(windowId), recordId, adLanguage);
		w.stop();

		log.trace("getJsonDataEntry by {windowId '{}' and recordId '{}'} duration: {}", windowId, recordId, w);
		return jsonDataEntry;
	}

	@VisibleForTesting
	ResponseEntity<JsonDataEntryResponse> getByRecordId0(final AdWindowId windowId, final int recordId, final String adLanguage)
	{
		final DataEntryLayout layout = layoutRepo.getByWindowId(windowId);
		if (layout.isEmpty())
		{
			return JsonDataEntryResponse.notFound(String.format("No dataentry for windowId '%d'.", windowId.getRepoId()));
		}

		final DataEntryRecordsMap records = dataRecords.get(recordId, layout.getSubTabIds());
		if (records.getSubTabIds().isEmpty())
		{
			return JsonDataEntryResponse.notFound(String.format("No dataentry for windowId '%d' and recordId '%s'.", windowId.getRepoId(), recordId));
		}

		final JsonDataEntry jsonDataEntry = JsonDataEntryFactory.builder()
				.layout(layout)
				.records(records)
				.adLanguage(adLanguage)
				.build();

		return JsonDataEntryResponse.ok(jsonDataEntry);
	}
}
