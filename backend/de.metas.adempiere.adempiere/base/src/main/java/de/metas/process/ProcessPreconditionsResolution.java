package de.metas.process;

import com.google.common.base.MoreObjects;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.function.Supplier;

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
	public static final AdMessageKey MSG_ONLY_ONE_SELECTED_ROW_ALLOWED = AdMessageKey.of("ProcessPreconditionsResolution_OnlyOneSelectedRowAllowed");

	/**
	 * {@code AD_Message} value that is used by {@link #rejectBecauseNoSelection()}. This constant can also be used with {@link #rejectWithInternalReason(String)}.
	 */
	public static final AdMessageKey MSG_NO_ROWS_SELECTED = AdMessageKey.of("ProcessPreconditionsResolution_NoRowsSelected");

	public static ProcessPreconditionsResolution accept()
	{
		return ACCEPTED;
	}

	public static ProcessPreconditionsResolution reject()
	{
		return REJECTED_UnknownReason;
	}

	public static ProcessPreconditionsResolution reject(@NonNull final AdMessageKey adMessageKey)
	{
		return reject(TranslatableStrings.adMessage(adMessageKey));
	}

	/**
	 * Convenience method to flag a process as not available in a particular context, giving the user a short reason about why the process is unavailable.
	 */
	public static ProcessPreconditionsResolution reject(@NonNull final ITranslatableString reason)
	{
		final boolean accepted = false;
		final boolean internal = false;
		final ProcessCaptionMapper captionMapper = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionMapper);
	}

	/**
	 * Like {@link #reject(ITranslatableString)}, but with a constant string.
	 *
	 * @param reasonStr this string will be used as-is (not translated)
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
	 */
	public static ProcessPreconditionsResolution rejectWithInternalReason(final String reasonStr)
	{
		if (Check.isEmpty(reasonStr, true))
		{
			return REJECTED_UnknownReason;
		}

		final ITranslatableString reason = TranslatableStrings.constant(reasonStr);
		return rejectWithInternalReason(reason);
	}

	public static ProcessPreconditionsResolution rejectWithInternalReason(@NonNull final ITranslatableString reason)
	{
		final boolean accepted = false;
		final boolean internal = true;
		final ProcessCaptionMapper captionMapper = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionMapper);
	}

	public static ProcessPreconditionsResolution rejectBecauseNoSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_ROWS_SELECTED);
		final boolean internal = false;
		final ProcessCaptionMapper captionMapper = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionMapper);
	}

	public static ProcessPreconditionsResolution rejectBecauseNotSingleSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ONLY_ONE_SELECTED_ROW_ALLOWED);
		final boolean internal = false;
		final ProcessCaptionMapper captionMapper = null;
		return new ProcessPreconditionsResolution(accepted, reason, internal, captionMapper);
	}

	/**
	 * NOTE: please avoid using this method. It was introduced just to migrate the old processes.
	 *
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

	public interface ProcessCaptionMapper
	{
		ITranslatableString computeCaption(ITranslatableString originalProcessCaption);
	}

	private final ProcessCaptionMapper captionMapper;

	@Builder(toBuilder = true)
	private ProcessPreconditionsResolution(
			@NonNull final Boolean accepted,
			@Nullable final ITranslatableString reason,
			@NonNull final Boolean internal,
			@Nullable final ProcessCaptionMapper captionMapper)
	{
		this.accepted = accepted;
		this.reason = reason;
		this.internal = internal;
		this.captionMapper = captionMapper;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(accepted ? "accept" : "reject")
				.omitNullValues()
				.addValue(reason)
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

	public String computeCaption(@NonNull final ITranslatableString originalProcessCaption, @NonNull final String adLanguage)
	{
		final ITranslatableString caption = captionMapper != null
				? captionMapper.computeCaption(originalProcessCaption)
				: originalProcessCaption;

		return caption.translate(adLanguage);
	}

	/**
	 * Derive this resolution, overriding the caption.
	 *
	 * @param captionOverride caption override; null value will be considered as no override
	 */
	public ProcessPreconditionsResolution deriveWithCaptionOverride(@NonNull final String captionOverride)
	{
		return withCaptionMapper(new ProcessCaptionOverrideMapper(captionOverride));
	}

	public ProcessPreconditionsResolution deriveWithCaptionOverride(@NonNull final ITranslatableString captionOverride)
	{
		return withCaptionMapper(new ProcessCaptionOverrideMapper(captionOverride));
	}

	@Value
	private static class ProcessCaptionOverrideMapper implements ProcessCaptionMapper
	{
		ITranslatableString captionOverride;

		public ProcessCaptionOverrideMapper(@NonNull final ITranslatableString captionOverride)
		{
			this.captionOverride = captionOverride;
		}

		public ProcessCaptionOverrideMapper(@NonNull final String captionOverride)
		{
			this.captionOverride = TranslatableStrings.anyLanguage(captionOverride);
		}

		@Override
		public ITranslatableString computeCaption(final ITranslatableString originalProcessCaption)
		{
			return captionOverride;
		}
	}

	public ProcessPreconditionsResolution withCaptionMapper(@Nullable final ProcessCaptionMapper captionMapper)
	{
		return toBuilder().captionMapper(captionMapper).build();
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
