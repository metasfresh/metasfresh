package de.metas.document.references;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;

import de.metas.i18n.ITranslatableString;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
public class ZoomTargetWindow
{
	public static ZoomTargetWindow ofAdWindowId(@NonNull final AdWindowId adWindowId)
	{
		final String category = null;
		final ITranslatableString categoryDisplayName = null;
		return new ZoomTargetWindow(adWindowId, category, categoryDisplayName);
	}

	public static ZoomTargetWindow ofAdWindowIdAndCategory(
			@NonNull final AdWindowId adWindowId,
			@NonNull final ReferenceListAwareEnum category,
			@NonNull final ITranslatableString categoryDisplayName)
	{
		return new ZoomTargetWindow(adWindowId, category.getCode(), categoryDisplayName);
	}

	AdWindowId adWindowId;
	String category;
	ITranslatableString categoryDisplayName;

	private ZoomTargetWindow(
			@NonNull final AdWindowId adWindowId,
			@Nullable final String category,
			@Nullable final ITranslatableString categoryDisplayName)
	{
		this.adWindowId = adWindowId;
		this.category = StringUtils.trimBlankToNull(category);
		this.categoryDisplayName = categoryDisplayName;
	}
}
