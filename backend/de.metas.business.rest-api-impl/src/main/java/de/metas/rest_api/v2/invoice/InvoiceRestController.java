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

package de.metas.rest_api.v2.invoice;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.invoice.JsonInvoicePaymentCreateRequest;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/invoices")
@Profile(Profiles.PROFILE_App)
public class InvoiceRestController
{
	private static final Logger logger = LogManager.getLogger(InvoiceRestController.class);

	private final InvoiceService invoiceService;

	public InvoiceRestController(final InvoiceService invoiceService)
	{
		this.invoiceService = invoiceService;
	}

	@ApiOperation("Create new invoice payment")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new invoice payment"),
			@ApiResponse(code = 401, message = "You are not authorized to create a new invoice payment"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping("/payment")
	public ResponseEntity<?> createInvoicePayment(@NonNull @RequestBody final JsonInvoicePaymentCreateRequest request)
	{
		try
		{
			invoiceService.createInboundPaymentFromJson(request);

			return ResponseEntity.ok().build();
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();

			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}

	}
}
