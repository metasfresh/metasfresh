package de.metas.dataentry.rest_api;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Stopwatch;

import de.metas.Profiles;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import de.metas.dataentry.rest_api.dto.JsonDataEntryResponse;
import de.metas.logging.LogManager;
import de.metas.util.web.MetasfreshRestAPIConstants;
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

@Profile(Profiles.PROFILE_App)
@RestController
@RequestMapping(DataEntryRestController.ENDPOINT)
public class DataEntryRestController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/dataentry";

	protected final transient Logger log = LogManager.getLogger(getClass());

	private final DataEntryLayoutRepository layoutRepo;
	private final DataEntryRecordCache dataRecords;

	DataEntryRestController(
			@NonNull final DataEntryLayoutRepository layoutRepo,
			@NonNull final DataEntryRecordRepository recordsRepo,
			@Value("${de.metas.dataentry.rest_api.DataEntryRestController.cacheCapacity:200}") final int cacheCapacity)
	{
		this.layoutRepo = layoutRepo;
		this.dataRecords = new DataEntryRecordCache(recordsRepo);
	}

	@GetMapping("/byId/{windowId}/{recordId}")
	public ResponseEntity<JsonDataEntryResponse> getByRecordId(
			@PathVariable("windowId") final int windowId,
			@PathVariable("recordId") final int recordId)
	{
		final Stopwatch w = Stopwatch.createStarted();
		final ResponseEntity<JsonDataEntryResponse> jsonDataEntry = getJsonDataEntry(AdWindowId.ofRepoId(windowId), recordId);
		w.stop();
		log.debug("getJsonDataEntry by {windowId '{}' and recordId '{}'} duration: {}", windowId, recordId, w);
		return jsonDataEntry;
	}

	private ResponseEntity<JsonDataEntryResponse> getJsonDataEntry(final AdWindowId windowId, final int recordId)
	{
		final String adLanguage = Env.getAD_Language();
		final JsonDataEntry jsonDataEntry = getJsonDataEntry(windowId, recordId, adLanguage);

		if (jsonDataEntry.isEmpty())
		{
			final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
					.error("No dataentry for windowId: " + windowId)
					.status(HttpStatus.NOT_FOUND.value())
					.build();
			return new ResponseEntity<>(dataEntryResponse, HttpStatus.NOT_FOUND);
		}

		final JsonDataEntryResponse dataEntryResponse = JsonDataEntryResponse.builder()
				.result(jsonDataEntry)
				.status(HttpStatus.OK.value())
				.build();
		return new ResponseEntity<>(dataEntryResponse, HttpStatus.OK);
	}

	@NonNull
	private JsonDataEntry getJsonDataEntry(
			@NonNull final AdWindowId windowId,
			final int recordId,
			@NonNull final String adLanguage)
	{
		final DataEntryLayout layout = layoutRepo.getByWindowId(windowId);
		final DataEntryRecordsMap records = dataRecords.get(recordId, layout.getSubTabIds());

		return JsonDataEntryFactory.builder()
				.layout(layout)
				.records(records)
				.adLanguage(adLanguage)
				.build();
	}

}
