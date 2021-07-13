package de.metas.ui.web.window.descriptor;

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

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Decides if and how a given field may be edited by a user.
 */
public enum ViewEditorRenderMode implements ReferenceListAwareEnum
{
	/**
	 * Read-only
	 */
	NEVER("N", "never"),

	/**
	 * after a right-click
	 */
	ON_DEMAND("D", "on-demand"),

	ALWAYS("Y", "always");

	private static final ReferenceListAwareEnums.ValuesIndex<ViewEditorRenderMode> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;
	private final String json;

	ViewEditorRenderMode(@NonNull final String code, @NonNull final String json)
	{
		this.code = code;
		this.json = json;
	}

	public static ViewEditorRenderMode ofCode(@NonNull final String code) { return index.ofCode(code); }

	public static ViewEditorRenderMode ofNullableCode(@Nullable final String code) { return index.ofNullableCode(code); }

	public String toJson()
	{
		return json;
	}

	public boolean isEditable()
	{
		return this == ON_DEMAND || this == ALWAYS;
	}
}
