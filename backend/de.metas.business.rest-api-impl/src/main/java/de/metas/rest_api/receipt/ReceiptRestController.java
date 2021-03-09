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

import com.google.common.collect.ImmutableList;
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

@RequestMapping(ReceiptRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class ReceiptRestController
{
	private static final Logger log = LogManager.getLogger(ReceiptRestController.class);

	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/receipt";

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ReceiptService receiptService;
	private final CustomerReturnRestService customerReturnRestService;

	public ReceiptRestController(final ReceiptService receiptService, final CustomerReturnRestService customerReturnRestService)
	{
		this.receiptService = receiptService;
		this.customerReturnRestService = customerReturnRestService;
	}

	@PostMapping
	public ResponseEntity createReceipts(@RequestBody final JsonCreateReceiptsRequest jsonCreateReceiptsRequest)
	{
		try
		{
			final JsonCreateReceiptsResponse receiptsResponse = trxManager.callInNewTrx(() -> createReceipts_0(jsonCreateReceiptsRequest));

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

	private JsonCreateReceiptsResponse createReceipts_0(@NonNull final JsonCreateReceiptsRequest jsonCreateReceiptsRequest)
	{
		final List<InOutId> createdReceiptIds = jsonCreateReceiptsRequest.getJsonCreateReceiptInfoList().isEmpty()
				? ImmutableList.of()
				: receiptService.updateReceiptCandidatesAndGenerateReceipts(jsonCreateReceiptsRequest);

		final List<InOutId> createdReturnIds = jsonCreateReceiptsRequest.getJsonCreateCustomerReturnInfoList().isEmpty()
				? ImmutableList.of()
				: customerReturnRestService.handleReturns(jsonCreateReceiptsRequest.getJsonCreateCustomerReturnInfoList());

		return toJsonCreateReceiptsResponse(createdReceiptIds, createdReturnIds);
	}

	@NonNull
	private JsonCreateReceiptsResponse toJsonCreateReceiptsResponse(@NonNull final List<InOutId> receiptIds, @NonNull final List<InOutId> returnIds)
	{
		final List<JsonMetasfreshId> jsonReceiptIds = receiptIds
				.stream()
				.map(InOutId::getRepoId)
				.map(JsonMetasfreshId::of)
				.collect(ImmutableList.toImmutableList());

		final List<JsonMetasfreshId> jsonReturnIds = returnIds
				.stream()
				.map(InOutId::getRepoId)
				.map(JsonMetasfreshId::of)
				.collect(ImmutableList.toImmutableList());

		return JsonCreateReceiptsResponse.builder()
				.createdReceiptIdList(jsonReceiptIds)
				.createdReturnIdList(jsonReturnIds)
				.build();
	}
}
