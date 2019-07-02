package de.metas.process;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public final class ProcessPreconditionsResolution
{
	/**
	 * {@code AD_Message} value that is used by {@link #rejectBecauseNotSingleSelection()}. This constant can also be used with {@link #rejectWithInternalReason(String)}.
	 */
	public static final String MSG_ONLY_ONE_SELECTED_ROW_ALLOWED = "ProcessPreconditionsResolution_OnlyOneSelectedRowAllowed";

	/**
	 * {@code AD_Message} value that is used by {@link #rejectBecauseNoSelection()}. This constant can also be used with {@link #rejectWithInternalReason(String)}.
	 */
	public static final String MSG_NO_ROWS_SELECTED = "ProcessPreconditionsResolution_NoRowsSelected";

	public static ProcessPreconditionsResolution accept()
	{
		return ACCEPTED;
	}

	public static ProcessPreconditionsResolution reject()
	{
		return REJECTED_UnknownReason;
	}

	/**
	 * Convenience method to flag a process as not available in a particular context, giving the user a short reason about why the process is unavailable.
	 * 
	 * @param reason
	 * @return
	 */
	public static ProcessPreconditionsResolution reject(@NonNull final ITranslatableString reason)
	{
		final boolean accepted = false;
		final boolean internal = false;
		final ITranslatableString captionOverride = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionOverride);
	}

	/**
	 * Like {@link #reject(ITranslatableString)}, but with a constant string.
	 * 
	 * @param reasonStr this string will be used as-is (not translated)
	 * @return
	 * 
	 * @deprecated please use {@link #reject(ITranslatableString)} instead; see issue <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/510">metasfresh-webui-api#510</a>.
	 */
	@Deprecated
	public static ProcessPreconditionsResolution reject(final String reasonStr)
	{
		if (Check.isEmpty(reasonStr, true))
		{
			return REJECTED_UnknownReason;
		}

		final ITranslatableString reason = TranslatableStrings.constant(reasonStr);
		return reject(reason);
	}

	/**
	 * Convenience method to flag a process as not available in a particular context.<br>
	 * The process shall not be shown to the user.<br>
	 * The given {@code reasonStr} is intended only for logging, debugging etc.
	 * 
	 * @param reasonStr this string will be used as-is (not translated)
	 * @return
	 */
	public static ProcessPreconditionsResolution rejectWithInternalReason(final String reasonStr)
	{
		if (Check.isEmpty(reasonStr, true))
		{
			return REJECTED_UnknownReason;
		}

		final boolean accepted = false;
		final ITranslatableString reason = TranslatableStrings.constant(reasonStr);
		final boolean internal = true;
		final ITranslatableString captionOverride = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionOverride);
	}

	public static ProcessPreconditionsResolution rejectBecauseNoSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_ROWS_SELECTED);
		final boolean internal = false;
		final ITranslatableString captionOverride = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionOverride);
	}

	public static ProcessPreconditionsResolution rejectBecauseNotSingleSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ONLY_ONE_SELECTED_ROW_ALLOWED);
		final boolean internal = false;
		final ITranslatableString captionOverride = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionOverride);
	}

	/**
	 * NOTE: please avoid using this method. It was introduced just to migrate the old processes.
	 *
	 * @param accept
	 * @return if <code>accept</code> is true then returns {@link #accepted} else {@link #reject()}.
	 */
	public static ProcessPreconditionsResolution acceptIf(final boolean accept)
	{
		return accept ? ACCEPTED : REJECTED_UnknownReason;
	}

	@SafeVarargs
	public static ProcessPreconditionsResolution firstRejectOrElseAccept(@NonNull final Supplier<ProcessPreconditionsResolution>... suppliers)
	{
		for (final Supplier<ProcessPreconditionsResolution> supplier : suppliers)
		{
			final ProcessPreconditionsResolution resolution = supplier.get();
			if (resolution.isRejected())
			{
				return resolution;
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private static final ProcessPreconditionsResolution ACCEPTED = new ProcessPreconditionsResolution(true, null, false, null);
	private static final ProcessPreconditionsResolution REJECTED_UnknownReason = new ProcessPreconditionsResolution(false, null, true, null);

	private final boolean accepted;
	private final ITranslatableString reason;
	private final boolean internal;
	private final ITranslatableString captionOverride;

	@Builder(toBuilder = true)
	private ProcessPreconditionsResolution(
			@NonNull final Boolean accepted,
			final ITranslatableString reason,
			@NonNull final Boolean internal,
			final ITranslatableString captionOverride)
	{
		this.accepted = accepted;
		this.reason = reason;
		this.internal = internal;
		this.captionOverride = captionOverride;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(accepted ? "accept" : "reject")
				.omitNullValues()
				.addValue(reason)
				.add("captionOverride", captionOverride)
				.add("internal", internal ? Boolean.TRUE : null)
				.toString();
	}

	public boolean isAccepted()
	{
		return accepted;
	}

	public boolean isRejected()
	{
		return !accepted;
	}

	public ITranslatableString getRejectReason()
	{
		if (accepted)
		{
			return TranslatableStrings.empty();
		}

		return reason != null ? reason : TranslatableStrings.empty();
	}

	public boolean isInternal()
	{
		return internal;
	}

	public ProcessPreconditionsResolution toInternal()
	{
		if (internal)
		{
			return this;
		}

		return toBuilder().internal(true).build();
	}

	public String getCaptionOverrideOrNull(final String adLanguage)
	{
		return captionOverride == null ? null : captionOverride.translate(adLanguage);
	}

	/**
	 * Derive this resolution, overriding the caption.
	 * 
	 * @param captionOverride caption override; null value will be considered as no override
	 * @return
	 */
	public ProcessPreconditionsResolution deriveWithCaptionOverride(@Nullable final String captionOverride)
	{
		final ITranslatableString captionOverrideNew = captionOverride == null ? null : TranslatableStrings.constant(captionOverride);
		if (Objects.equal(this.captionOverride, captionOverrideNew))
		{
			return this;
		}

		return toBuilder().captionOverride(captionOverrideNew).build();
	}

	public ProcessPreconditionsResolution and(Supplier<ProcessPreconditionsResolution> resolutionSupplier)
	{
		if (isRejected())
		{
			return this;
		}

		return resolutionSupplier.get();
	}

	public void throwExceptionIfRejected()
	{
		if (isRejected())
		{
			throw new AdempiereException(getRejectReason());
		}
	}
}
