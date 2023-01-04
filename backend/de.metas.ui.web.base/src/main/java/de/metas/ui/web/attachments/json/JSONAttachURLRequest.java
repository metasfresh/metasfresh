package de.metas.ui.web.attachments.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.printing.esb.base.util.Check;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.net.URI;
import java.net.URISyntaxException;

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
@Value
public class JSONAttachURLRequest
{
	@JsonProperty("name")
	String name;
	@JsonProperty("url")
	String url;
	@JsonIgnore
	URI uri;

	@JsonCreator
	private JSONAttachURLRequest(
			@JsonProperty("name") final String name,
			@JsonProperty("url") final String url)
	{
		this.name = name;
		if (Check.isEmpty(name, true))
		{
			throw new AdempiereException("name cannot be empty");
		}
		
		this.url = url;
		try
		{
			this.uri = new URI(url);
		}
		catch (final URISyntaxException ex)
		{
			throw new AdempiereException("Invalid URL: " + url, ex);
		}
	}
}
