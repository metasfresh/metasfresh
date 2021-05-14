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

import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;

import javax.annotation.Nullable;
import java.time.Duration;

@Value
public class RelatedDocuments
{
	@NonNull RelatedDocumentsId id;
	@NonNull String internalName;
	@NonNull ITranslatableString caption;
	@Nullable
	ITranslatableString filterByFieldCaption;
	@NonNull MQuery query;
	@NonNull RelatedDocumentsTargetWindow targetWindow;
	@NonNull Priority priority;

	@Builder
	private RelatedDocuments(
			@NonNull final RelatedDocumentsId id,
			@NonNull final String internalName,
			@NonNull final RelatedDocumentsTargetWindow targetWindow,
			@NonNull final Priority priority,
			@NonNull final ITranslatableString caption,
			@Nullable final ITranslatableString filterByFieldCaption,
			@NonNull final MQuery query)
	{
		this.id = id;
		this.internalName = Check.assumeNotEmpty(internalName, "internalName is not empty");

		this.targetWindow = targetWindow;
		this.priority = priority;

		this.caption = caption;
		this.filterByFieldCaption = filterByFieldCaption;

		this.query = query;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("internalName", internalName)
				.add("caption", caption.getDefaultValue())
				.add("filterByFieldCaption", filterByFieldCaption != null ? filterByFieldCaption.getDefaultValue() : null)
				.add("targetWindow", targetWindow)
				.add("RecordCount", query.getRecordCount())
				.toString();
	}

	public AdWindowId getAdWindowId()
	{
		return getTargetWindow().getAdWindowId();
	}

	public int getRecordCount()
	{
		return query.getRecordCount();
	}

	@Nullable
	public Duration getRecordCountDuration()
	{
		return query.getRecordCountDuration();
	}
}
