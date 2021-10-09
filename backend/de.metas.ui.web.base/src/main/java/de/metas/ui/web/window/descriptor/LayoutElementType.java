/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.window.descriptor;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_UI_Element;

import javax.annotation.Nullable;

public enum LayoutElementType implements ReferenceListAwareEnum
{
	Field(X_AD_UI_Element.AD_UI_ELEMENTTYPE_Field),
	Labels(X_AD_UI_Element.AD_UI_ELEMENTTYPE_Labels),
	InlineTab(X_AD_UI_Element.AD_UI_ELEMENTTYPE_InlineTab);

	private static final ReferenceListAwareEnums.ValuesIndex<LayoutElementType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	LayoutElementType(@NonNull final String code)
	{
		this.code = code;
	}

	public static LayoutElementType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static LayoutElementType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
