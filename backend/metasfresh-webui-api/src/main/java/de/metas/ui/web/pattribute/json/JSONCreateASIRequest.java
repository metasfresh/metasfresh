package de.metas.ui.web.pattribute.json;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import lombok.NonNull;
import lombok.Value;

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
public final class JSONCreateASIRequest
{
	@JsonProperty("templateId")
	private final AttributeSetInstanceId templateId;

	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private final JSONDocumentPath source;

	@JsonCreator
	private JSONCreateASIRequest(
			@JsonProperty("templateId") @Nullable final AttributeSetInstanceId templateId,
			@JsonProperty("source") @NonNull final JSONDocumentPath source)
	{
		this.templateId = templateId != null ? templateId : AttributeSetInstanceId.NONE;
		this.source = source;
	}
}
