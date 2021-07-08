package de.metas.ui.web.document.references;

import javax.annotation.Nullable;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class DocumentReferenceTargetWindow
{
	public static DocumentReferenceTargetWindow ofWindowId(@NonNull final WindowId windowId)
	{
		final String category = null;
		return new DocumentReferenceTargetWindow(windowId, category);
	}

	public static DocumentReferenceTargetWindow ofWindowIdAndCategory(
			@NonNull final WindowId windowId,
			@NonNull final String category)
	{
		Check.assumeNotEmpty(category, "category is not empty");
		return new DocumentReferenceTargetWindow(windowId, category);
	}

	WindowId windowId;
	String category;

	private DocumentReferenceTargetWindow(
			@NonNull final WindowId windowId,
			@Nullable final String category)
	{
		this.windowId = windowId;
		this.category = StringUtils.trimBlankToNull(category);
	}
}
