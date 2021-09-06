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

package de.metas.rest_api.v2.shipping;

import javax.annotation.Nullable;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import org.adempiere.ad.dao.QueryLimit;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.common.shipping.v2.JsonRequestCandidateResults;
import de.metas.common.shipping.v2.receiptcandidate.JsonResponseReceiptCandidates;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;

@RequestMapping(value = {MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/receipts" })
@RestController
@Profile(Profiles.PROFILE_App)
public class ReceiptCandidatesRestController
{
	private final static transient Logger logger = LogManager.getLogger(ReceiptCandidatesRestController.class);

	private final ReceiptCandidateAPIService receiptCandidateAPIService;

	public ReceiptCandidatesRestController(@NonNull final ReceiptCandidateAPIService receiptCandidateAPIService)
	{
		this.receiptCandidateAPIService = receiptCandidateAPIService;
	}

	@GetMapping("receiptCandidates")
	public ResponseEntity<JsonResponseReceiptCandidates> getReceiptCandidates(
			@ApiParam("Max number of items to be returned in one request.") //
			@RequestParam(name = "limit", required = false, defaultValue = "500") //
			@Nullable final Integer limit)
	{
		final QueryLimit limitEff = QueryLimit.ofNullableOrNoLimit(limit).ifNoLimitUse(500);

		final JsonResponseReceiptCandidates result = receiptCandidateAPIService.exportReceiptCandidates(limitEff);
		Loggables.withLogger(logger, Level.DEBUG).addLog("Number of exported receiptCandidates: {}", result.getItems().size());
		return ResponseEntity.ok(result);
	}

	@PostMapping("receiptCandidatesResult")
	public ResponseEntity<String> postReceiptCandidatesStatus(@RequestBody @NonNull final JsonRequestCandidateResults status)
	{
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", status.getTransactionKey()))
		{
			receiptCandidateAPIService.updateStatus(status);
			Loggables.withLogger(logger, Level.DEBUG).addLog("ReceiptCandidates status updated");
			
			return ResponseEntity.accepted().body("ReceiptCandidates updated");
		}
	}
}
