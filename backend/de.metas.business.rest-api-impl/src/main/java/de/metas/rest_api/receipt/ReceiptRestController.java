/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.receipt;

import de.metas.Profiles;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.common.receipt.JsonCreateReceiptsResponse;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(ReceiptRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ReceiptRestController
{
	private static final Logger log = LogManager.getLogger(ReceiptRestController.class);

	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/receipt";

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ReceiptService receiptService;

	public ReceiptRestController(final ReceiptService receiptService)
	{
		this.receiptService = receiptService;
	}

	@PostMapping
	public ResponseEntity createReceipts(@RequestBody final JsonCreateReceiptsRequest jsonCreateReceiptsRequest)
	{
		try
		{
			final List<InOutId> createdReceiptIds = trxManager.callInNewTrx(() -> receiptService.updateReceiptCandidatesAndGenerateReceipts(jsonCreateReceiptsRequest));

			final JsonCreateReceiptsResponse receiptsResponse = toJsonCreateReceiptsResponse(createdReceiptIds);

			return ResponseEntity.ok(receiptsResponse);
		}
		catch (final Exception ex)
		{
			log.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@NonNull
	private JsonCreateReceiptsResponse toJsonCreateReceiptsResponse(@NonNull final List<InOutId> receiptIds)
	{
		final List<JsonMetasfreshId> jsonIds = receiptIds
				.stream()
				.map(InOutId::getRepoId)
				.map(JsonMetasfreshId::of)
				.collect(Collectors.toList());

		return JsonCreateReceiptsResponse.builder()
				.createdReceiptIdList(jsonIds)
				.build();
	}
}
