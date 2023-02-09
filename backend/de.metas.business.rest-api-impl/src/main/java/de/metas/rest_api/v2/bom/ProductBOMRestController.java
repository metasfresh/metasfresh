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

package de.metas.rest_api.v2.bom;

import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateRequest;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateResponse;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/bom/version" })
@RestController
public class ProductBOMRestController
{
	private final BOMRestService bomRestService;

	public ProductBOMRestController(final BOMRestService bomRestService)
	{
		this.bomRestService = bomRestService;
	}

	@ApiOperation("Create a new bill of material version.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created bill of material"),
			@ApiResponse(code = 401, message = "You are not authorized to perform this action"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}")
	public ResponseEntity<?> createBOMs(
			@ApiParam(required = true, value = "Organisation within to create the bom formula.")
			@PathVariable("orgCode") @Nullable final String orgCode,
			@RequestBody @NonNull final JsonBOMCreateRequest request)
	{
		try
		{
			final JsonBOMCreateResponse bomId = bomRestService.createBOMs(orgCode, request);

			return ResponseEntity.ok().body(bomId);
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}

	@ApiOperation("Verifies the default bill of material version for given product identifier.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully verified bill of material"),
			@ApiResponse(code = 401, message = "You are not authorized to perform this action"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}/verify/{productIdentifier}")
	public ResponseEntity<?> verifyDefaultBOM(
			@PathVariable("orgCode") @NonNull final String orgCode,
			@ApiParam(required = true, value = PRODUCT_IDENTIFIER_DOC)
			@PathVariable("productIdentifier") @NonNull final String productIdentifier)
	{
		try
		{
			bomRestService.verifyDefaultBOM(productIdentifier, orgCode);

			return ResponseEntity.ok().build();
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}
}
