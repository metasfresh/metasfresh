package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONDocumentReferencesList
{

	public static JSONDocumentReferencesList of(final List<ZoomInfo> zoomInfos, final JSONFilteringOptions jsonOpts)
	{
		return new JSONDocumentReferencesList(zoomInfos, jsonOpts);
	}

	@JsonProperty("references")
	private final List<JSONDocumentReference> references;

	private JSONDocumentReferencesList(final List<ZoomInfo> zoomInfos, final JSONFilteringOptions jsonOpts)
	{
		super();
		references = zoomInfos.stream()
				.map(zoomInfo -> JSONDocumentReference.of(zoomInfo, jsonOpts))
				.filter(jsonDocumentReference -> jsonDocumentReference != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(references)
				.toString();
	}

	public List<JSONDocumentReference> getReferences()
	{
		return references;
	}
}
