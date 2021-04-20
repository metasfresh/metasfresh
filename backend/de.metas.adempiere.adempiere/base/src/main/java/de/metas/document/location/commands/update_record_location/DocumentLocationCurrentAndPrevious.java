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

package de.metas.document.location.commands.update_record_location;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.location.DocumentLocation;
import de.metas.location.LocationId;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

class DocumentLocationCurrentAndPrevious
{
	@Getter
	private final DocumentLocation current;
	private final DocumentLocation previous;
	private final boolean isNewDocument;

	public static DocumentLocationCurrentAndPrevious ofNewDocument(@Nullable final DocumentLocation current)
	{
		return new DocumentLocationCurrentAndPrevious(current, null, true);
	}

	public static DocumentLocationCurrentAndPrevious ofChangedDocument(
			@Nullable final DocumentLocation current,
			@Nullable final DocumentLocation previous)
	{
		return new DocumentLocationCurrentAndPrevious(current, previous, false);
	}

	private DocumentLocationCurrentAndPrevious(
			@Nullable final DocumentLocation current,
			@Nullable final DocumentLocation previous,
			final boolean isNewDocument)
	{
		this.current = current;
		this.previous = previous;
		this.isNewDocument = isNewDocument;
	}

	public boolean isBPartnerLocationIdChanged()
	{
		return !Objects.equals(getCurrentBPartnerLocationId(), extractBPartnerLocationId(previous));
	}

	public Optional<BPartnerLocationId> getCurrentBPartnerLocationId()
	{
		return extractBPartnerLocationId(current);
	}

	private static Optional<BPartnerLocationId> extractBPartnerLocationId(@Nullable final DocumentLocation location)
	{
		return Optional.ofNullable(location != null ? location.getBpartnerLocationId() : null);
	}

	public Optional<LocationId> getCurrentLocationId()
	{
		return extractLocationId(current);
	}

	public Optional<LocationId> getPreviousLocationId()
	{
		return extractLocationId(previous);
	}

	public boolean isLocationIdChanged()
	{
		return !Objects.equals(extractLocationId(current), extractLocationId(previous));
	}

	private static Optional<LocationId> extractLocationId(@Nullable final DocumentLocation location)
	{
		return Optional.ofNullable(location != null ? location.getLocationId() : null);
	}

	public boolean isUpdateLocationRequiredBecauseRelatedChanges()
	{
		// In case of new documents: update the location only if it wasn't set explicitly
		if (isNewDocument)
		{
			return !getCurrentLocationId().isPresent();
		}
		// In case of changed documents: update the location only if it wasn't changed explicitly
		else
		{
			return isBPartnerLocationIdChanged() && !isLocationIdChanged();
		}
	}
}
