package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

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

@EqualsAndHashCode
public final class DocumentSaveStatus
{
	public static DocumentSaveStatus unknown()
	{
		return STATUS_Unknown;
	}

	public static DocumentSaveStatus saved()
	{
		return STATUS_Saved;
	}

	public static DocumentSaveStatus deleted()
	{
		return STATUS_Deleted;
	}

	public static DocumentSaveStatus notSaved(final DocumentValidStatus invalidState)
	{
		return builder().hasChangesToBeSaved(true).error(true).reason(invalidState.getReason()).exception(invalidState.getException()).build();
	}

	public static DocumentSaveStatus error(@NonNull final Exception exception)
	{
		return builder().hasChangesToBeSaved(true).error(true).reason(AdempiereException.extractMessageTrl(exception)).exception(exception).build();
	}

	public static DocumentSaveStatus notSavedJustCreated()
	{
		return STATUS_NotSavedJustCreated;
	}

	public static DocumentSaveStatus savedJustLoaded()
	{
		return STATUS_SavedJustLoaded;
	}

	private static final DocumentSaveStatus STATUS_Unknown = builder().hasChangesToBeSaved(true).error(false).reason(TranslatableStrings.anyLanguage("not yet checked")).build();
	private static final DocumentSaveStatus STATUS_Saved = builder().hasChangesToBeSaved(false).error(false).build();
	private static final DocumentSaveStatus STATUS_Deleted = builder().hasChangesToBeSaved(false).deleted(true).error(false).build();
	private static final DocumentSaveStatus STATUS_NotSavedJustCreated = builder().hasChangesToBeSaved(true).error(false).reason(TranslatableStrings.anyLanguage("new")).build();
	private static final DocumentSaveStatus STATUS_SavedJustLoaded = builder().hasChangesToBeSaved(false).error(false).reason(TranslatableStrings.anyLanguage("just loaded")).build();

	@Getter private final boolean hasChangesToBeSaved;
	@Getter private final boolean deleted;
	@Getter private final boolean error;
	@Nullable @Getter private final ITranslatableString reason;
	@Nullable @Getter private final transient Exception exception;

	@Getter private final boolean saved; // computed

	@Builder
	private DocumentSaveStatus(
			final boolean hasChangesToBeSaved,
			final boolean deleted,
			final boolean error,
			@Nullable final ITranslatableString reason,
			@Nullable final Exception exception)
	{
		this.hasChangesToBeSaved = hasChangesToBeSaved;
		this.deleted = deleted;
		this.error = error;
		this.reason = reason;
		this.exception = exception;

		this.saved = !this.hasChangesToBeSaved && !this.error && !this.deleted;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("saved", saved)
				.add("deleted", deleted)
				.add("hasChangesToBeSaved", hasChangesToBeSaved)
				.add("error", error)
				.add("reason", reason)
				.toString();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isSavedOrDeleted()
	{
		return isSaved() || isDeleted();
	}

	public DocumentSaveStatus throwIfError()
	{
		if (!error)
		{
			return this;
		}

		if (exception != null)
		{
			throw AdempiereException.wrapIfNeeded(exception);
		}
		else
		{
			throw new AdempiereException(reason != null ? reason : TranslatableStrings.anyLanguage("Error"));
		}
	}

	public void throwIfNotSavedNorDelete()
	{
		if (isSavedOrDeleted())
		{
			return;
		}

		if (exception != null)
		{
			throw AdempiereException.wrapIfNeeded(exception);
		}
		else
		{
			throw new AdempiereException(reason != null ? reason : TranslatableStrings.anyLanguage("Not saved"));
		}
	}
}
