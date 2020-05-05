package de.metas.ui.web.view.json;

import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.ViewProfile;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONViewProfilesList
{
	public static final JSONViewProfilesList of(final List<ViewProfile> viewProfiles, final String adLanguage)
	{
		if (viewProfiles.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONLookupValue> jsonProfiles = viewProfiles.stream()
				.map(viewProfile -> toJSONLookupValue(viewProfile, adLanguage))
				.sorted(Comparator.comparing(JSONLookupValue::getCaption))
				.collect(ImmutableList.toImmutableList());

		return new JSONViewProfilesList(jsonProfiles);
	}

	private static final JSONLookupValue toJSONLookupValue(final ViewProfile viewProfile, final String adLanguage)
	{
		return JSONLookupValue.of(viewProfile.getProfileId().toJson(), viewProfile.getCaption().translate(adLanguage));
	}

	private static final JSONViewProfilesList EMPTY = new JSONViewProfilesList(ImmutableList.of());

	@JsonProperty("values")
	private final List<JSONLookupValue> values;

	private JSONViewProfilesList(@NonNull final ImmutableList<JSONLookupValue> values)
	{
		this.values = values;
	}

}
