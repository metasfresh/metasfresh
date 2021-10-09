/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.related_documents;

import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@Value
public class RelatedDocumentsTargetWindow
{
	public static RelatedDocumentsTargetWindow ofAdWindowId(@NonNull final AdWindowId adWindowId)
	{
		return new RelatedDocumentsTargetWindow(adWindowId, null);
	}

	public static RelatedDocumentsTargetWindow ofAdWindowIdAndCategory(
			@NonNull final AdWindowId adWindowId,
			@NonNull final ReferenceListAwareEnum category)
	{
		return new RelatedDocumentsTargetWindow(adWindowId, category.getCode());
	}

	public static RelatedDocumentsTargetWindow ofAdWindowIdAndCategory(
			@NonNull final AdWindowId adWindowId,
			@Nullable final String category)
	{
		return new RelatedDocumentsTargetWindow(adWindowId, category);
	}

	AdWindowId adWindowId;
	String category;

	private RelatedDocumentsTargetWindow(
			@NonNull final AdWindowId adWindowId,
			@Nullable final String category)
	{
		this.adWindowId = adWindowId;
		this.category = StringUtils.trimBlankToNull(category);
	}
}
