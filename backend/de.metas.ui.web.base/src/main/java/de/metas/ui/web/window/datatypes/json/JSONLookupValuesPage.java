/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JSONLookupValuesPage
{
	private static final JSONLookupValuesPage EMPTY = new JSONLookupValuesPage();

	List<JSONLookupValue> values;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean hasMoreResults;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isAlwaysDisplayNewBPartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable JsonErrorItem error;

	@Builder
	private JSONLookupValuesPage(
			@NonNull final ImmutableList<JSONLookupValue> values,
			@Nullable final Boolean hasMoreResults,
			@Nullable final Boolean isAlwaysDisplayNewBPartner,
			@Nullable JsonErrorItem error)
	{
		this.values = values;
		this.hasMoreResults = hasMoreResults;
		this.isAlwaysDisplayNewBPartner = isAlwaysDisplayNewBPartner;
		this.error = error;
	}

	private JSONLookupValuesPage()
	{
		values = ImmutableList.of();
		hasMoreResults = false;
		isAlwaysDisplayNewBPartner = false;
		error = null;
	}

	public static JSONLookupValuesPage of(
			@Nullable final LookupValuesPage page,
			@NonNull final String adLanguage)
	{
		return of(page, adLanguage, false);
	}

	public static JSONLookupValuesPage of(
			@Nullable final LookupValuesPage page,
			@NonNull final String adLanguage,
			@Nullable final Boolean isAlwaysDisplayNewBPartner)
	{
		if (page == null || page.isEmpty())
		{
			return EMPTY;
		}
		else
		{
			return builder()
					.values(JSONLookupValuesList.toListOfJSONLookupValues(page.getValues(), adLanguage, false))
					.hasMoreResults(page.getHasMoreResults().toBooleanOrNull())
					.isAlwaysDisplayNewBPartner(isAlwaysDisplayNewBPartner)
					.build();
		}
	}

	public static JSONLookupValuesPage error(@NonNull final JsonErrorItem error)
	{
		return builder().error(error).values(ImmutableList.of()).build();
	}
}
