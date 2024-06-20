package de.metas.rest_api.bpartner_pricelist.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.rest_api.utils.JsonErrors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

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

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonResponsePriceList
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Singular
	List<JsonResponsePrice> prices;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Singular
	List<JsonErrorItem> errors;

	public static JsonResponsePriceList error(@NonNull final Throwable throwable, @NonNull final String adLanguage)
	{
		return builder()
				.error(JsonErrors.ofThrowable(throwable, adLanguage))
				.build();
	}
}
