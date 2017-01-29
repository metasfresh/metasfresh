package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONDocumentIdsSelection implements Serializable
{
	@JsonProperty("selection")
	private final Set<String> selection;

	private JSONDocumentIdsSelection(@JsonProperty("selection") final Set<String> selection)
	{
		this.selection = selection == null ? ImmutableSet.of() : ImmutableSet.copyOf(selection);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(selection).toString();
	}

	public Set<String> getSelection()
	{
		return selection;
	}

	public Set<DocumentId> getSelectionDocumentIds()
	{
		return DocumentId.ofStringSet(selection);
	}
}
