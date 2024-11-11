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

package de.metas.rest_api.v2.product;

import de.metas.Profiles;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.rest_api.v2.JsonResponseUpsert;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.audit.CreateExportAuditRequest;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.externlasystem.ExternalSystemService;
import de.metas.rest_api.v2.product.command.GetProductsCommand;
import de.metas.util.Check;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.vertical.healthcare.alberta.service.AlbertaProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.ORG_CODE_PARAMETER_DOC;
import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.product.v2.response.ProductsQueryParams.AD_PINSTANCE_ID;
import static de.metas.common.product.v2.response.ProductsQueryParams.EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE;
import static de.metas.common.product.v2.response.ProductsQueryParams.EXTERNAL_SYSTEM_CONFIG_TYPE;
import static de.metas.common.product.v2.response.ProductsQueryParams.SINCE;
import static de.metas.common.rest_api.v2.APIConstants.ENDPOINT_MATERIAL;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + ENDPOINT_MATERIAL + "/products" })
@RestController
@Profile(Profiles.PROFILE_App)
public class ProductsRestController
{
	private static final Logger logger = LogManager.getLogger(ProductsRestController.class);
	private final ProductsServicesFacade productsServicesFacade;
	private final AlbertaProductService albertaProductService;
	private final ExternalSystemService externalSystemService;
	private final ProductRestService productRestService;

	public ProductsRestController(
			@NonNull final ProductsServicesFacade productsServicesFacade,
			@NonNull final AlbertaProductService albertaProductService,
			@NonNull final ExternalSystemService externalSystemService,
			@NonNull final ProductRestService productRestService)
	{
		this.productsServicesFacade = productsServicesFacade;
		this.albertaProductService = albertaProductService;
		this.externalSystemService = externalSystemService;
		this.productRestService = productRestService;
	}

	@GetMapping
	public ResponseEntity<?> getProducts(
			@RequestParam(value = SINCE, required = false) @Nullable final Instant since,
			@RequestParam(value = AD_PINSTANCE_ID, required = false) @Nullable final Integer pInstanceId,
			@RequestParam(value = EXTERNAL_SYSTEM_CONFIG_TYPE, required = false) @Nullable final String externalSystemConfigType,
			@RequestParam(value = EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE, required = false) @Nullable final String externalSystemChildConfigValue)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		try
		{
			final ExternalSystemType externalSystemType = externalSystemConfigType != null
					? ExternalSystemType.ofCodeOrNameOrNull(externalSystemConfigType)
					: null;

			final JsonGetProductsResponse response = GetProductsCommand.builder()
					.servicesFacade(productsServicesFacade)
					.albertaProductService(albertaProductService)
					.externalSystemService(externalSystemService)
					.productRestService(productRestService)
					.externalSystemType(externalSystemType)
					.externalSystemConfigValue(externalSystemChildConfigValue)
					.adLanguage(adLanguage)
					.since(since)
					.execute();

			final HashMap<String, String> queryParameters = new HashMap<>();
			queryParameters.put(SINCE, String.valueOf(since));
			queryParameters.put(AD_PINSTANCE_ID, String.valueOf(pInstanceId));
			queryParameters.put(EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE, String.valueOf(externalSystemChildConfigValue));
			queryParameters.put(EXTERNAL_SYSTEM_CONFIG_TYPE, String.valueOf(externalSystemConfigType));
			logExportedProducts(queryParameters, externalSystemType, pInstanceId, response);

			return ResponseEntity.ok(response);
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);

			return ResponseEntity.badRequest()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@ApiOperation("Create or update products, corresponding Bpartner-products and Product Tax Categories.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created or updated product(s)"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@PutMapping("{orgCode}")
	public ResponseEntity<JsonResponseUpsert> upsertProducts(
			@ApiParam(required = true, value = ORG_CODE_PARAMETER_DOC)
			@PathVariable("orgCode") @Nullable final String orgCode,
			@RequestBody @NonNull final JsonRequestProductUpsert request)

	{
		final JsonResponseUpsert responseUpsert = productRestService.upsertProducts(orgCode, request);

		return ResponseEntity.ok().body(responseUpsert);
	}

	@ApiOperation("Retrieve product by product identifier.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Product successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to create or update the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 422, message = "The request entity could not be processed")
	})
	@GetMapping("{orgCode}/{externalIdentifier}")
	public ResponseEntity<?> getByExternalIdentifier(
			@PathVariable("orgCode") @Nullable final String orgCode,
			@ApiParam(PRODUCT_IDENTIFIER_DOC) @PathVariable(value = "externalIdentifier") @NonNull final String externalIdentifier)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		try
		{
			final ExternalIdentifier productIdentifier = ExternalIdentifier.of(externalIdentifier);

			final JsonGetProductsResponse response = GetProductsCommand.builder()
					.servicesFacade(productsServicesFacade)
					.albertaProductService(albertaProductService)
					.externalSystemService(externalSystemService)
					.productRestService(productRestService)
					.adLanguage(adLanguage)
					.orgCode(orgCode)
					.productIdentifier(productIdentifier)
					.execute();

			Check.assumeEquals(response.getProducts().size(), 1, "JsonGetProductsResponse should have only one JsonProduct!");

			return ResponseEntity.ok().body(response.getProducts().get(0));
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);

			return ResponseEntity
					.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(ex);
		}
	}

	private void logExportedProducts(
			@Nullable final Map<String, String> queryParameters,
			@Nullable final ExternalSystemType externalSystemType,
			@Nullable final Integer pInstanceId,
			@NonNull final JsonGetProductsResponse products)
	{
		final String exportParameters = queryParameters == null
				? null
				: queryParameters.entrySet()
				.stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(", "));

		products.getProducts().stream()
				.map(product -> TableRecordReference.of(I_M_Product.Table_Name, product.getId().getValue()))
				.map(
						productTableRecordReference -> CreateExportAuditRequest.builder()
								.exportParameters(exportParameters)
								.exportRoleId(Env.getLoggedRoleId())
								.exportUserId(Env.getLoggedUserId())
								.externalSystemType(externalSystemType)
								.exportTime(Instant.now())
								.pInstanceId(pInstanceId != null ? PInstanceId.ofRepoIdOrNull(pInstanceId) : null)
								.tableRecordReference(productTableRecordReference)
								.build())
				.forEach(externalSystemService::createESExportAudit);
	}
}
