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

package de.metas.rest_api.productV2;

import de.metas.Profiles;
import de.metas.common.bpartner.response.JsonResponseUpsert;
import de.metas.common.bpartner.response.JsonResponseUpsertItem;
import de.metas.common.product.JsonRequestProductUpsert;
import de.metas.common.rest_api.SyncAdvise;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/product")
@RestController
@Profile(Profiles.PROFILE_App)
public class ProductRestController
{
	private final ProductRestService productRestService;

	public ProductRestController(final ProductRestService productRestService)
	{
		this.productRestService = productRestService;
	}

	@PutMapping("{orgCode}")
	public ResponseEntity<JsonResponseUpsert> upsertProducts(@RequestBody @NonNull final JsonRequestProductUpsert request,
			@PathVariable("orgCode") @Nullable final String orgCode)
	{
		final SyncAdvise syncAdvise = request.getSyncAdvise();

		final List<JsonResponseUpsertItem> responseList =
				request.getRequestItems()
						.stream()
						.map(reqItem -> productRestService.createOrUpdateProduct(reqItem, syncAdvise, orgCode))
						.collect(Collectors.toList());

		return ResponseEntity.ok().body(JsonResponseUpsert.builder().responseItems(responseList).build());
	}

}
