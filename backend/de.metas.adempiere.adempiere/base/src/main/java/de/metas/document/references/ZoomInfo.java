package de.metas.document.references;

import java.time.Duration;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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

public final class ZoomInfo
{
	@Getter
	private final ZoomInfoId id;
	@Getter
	private final String internalName;
	@Getter
	private final ITranslatableString caption;
	@Getter
	private final MQuery query;
	@Getter
	private final ZoomTargetWindow targetWindow;
	@Getter
	private final Priority priority;

	@Builder
	private ZoomInfo(
			@NonNull final ZoomInfoId id,
			@NonNull final String internalName,
			@NonNull final ZoomTargetWindow targetWindow,
			@NonNull final Priority priority,
			@NonNull final ITranslatableString caption,
			@NonNull final MQuery query)
	{
		this.id = id;
		this.internalName = Check.assumeNotEmpty(internalName, "internalName is not empty");

		this.targetWindow = targetWindow;
		this.priority = priority;

		this.caption = caption;

		this.query = query;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("internalName", internalName)
				.add("caption", caption.getDefaultValue())
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
