package de.metas.process;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
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

	public static final ProcessPreconditionsResolution accept()
	{
		return ACCEPTED;
	}

	public static final ProcessPreconditionsResolution reject()
	{
		return REJECTED_UnknownReason;
	}

	/**
	 * Convenience method to flag a process as not available in a particular context, giving the user a short reason about why the process is unavailable.
	 * 
	 * @param reason
	 * @return
	 */
	public static final ProcessPreconditionsResolution reject(@NonNull final ITranslatableString reason)
	{
		final boolean accepted = false;
		final boolean internal = false;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
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
	public static final ProcessPreconditionsResolution reject(final String reasonStr)
	{
		if (Check.isEmpty(reasonStr, true))
		{
			return REJECTED_UnknownReason;
		}

		final ITranslatableString reason = ImmutableTranslatableString.constant(reasonStr);
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
	public static final ProcessPreconditionsResolution rejectWithInternalReason(final String reasonStr)
	{
		if (Check.isEmpty(reasonStr, true))
		{
			return REJECTED_UnknownReason;
		}

		final boolean accepted = false;
		final ITranslatableString reason = ImmutableTranslatableString.constant(reasonStr);
		final boolean internal = true;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	public static final ProcessPreconditionsResolution rejectBecauseNoSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_ROWS_SELECTED);
		final boolean internal = false;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	public static final ProcessPreconditionsResolution rejectBecauseNotSingleSelection()
	{
		final boolean accepted = false;
		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ONLY_ONE_SELECTED_ROW_ALLOWED);
		final boolean internal = false;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	/**
	 * NOTE: please avoid using this method. It was introduced just to migrate the old processes.
	 *
	 * @param accept
	 * @return if <code>accept</code> is true then returns {@link #accepted} else {@link #reject()}.
	 */
	public static final ProcessPreconditionsResolution acceptIf(final boolean accept)
	{
		return accept ? ACCEPTED : REJECTED_UnknownReason;
	}

	public static final ProcessPreconditionsResolution.Builder builder()
	{
		return new Builder();
	}

	private static final ProcessPreconditionsResolution ACCEPTED = new ProcessPreconditionsResolution(true, null, false);
	private static final ProcessPreconditionsResolution REJECTED_UnknownReason = new ProcessPreconditionsResolution(false, null, true);

	private final boolean accepted;
	private final ITranslatableString reason;
	private final boolean internal;
	private final ITranslatableString captionOverride;

	private ProcessPreconditionsResolution(final boolean accepted, final ITranslatableString reason, final boolean internal)
	{
		this.accepted = accepted;
		this.reason = reason;
		this.internal = internal;
		captionOverride = null;
	}

	private ProcessPreconditionsResolution(final ProcessPreconditionsResolution.Builder builder)
	{
		accepted = builder.isAccepted();
		reason = builder.getReason();
		internal = false;
		captionOverride = builder.getCaptionOverride();
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
			return ImmutableTranslatableString.empty();
		}

		return reason != null ? reason : ImmutableTranslatableString.empty();
	}

	public boolean isInternal()
	{
		return internal;
	}

	public String getCaptionOverrideOrNull(final String adLanguage)
	{
		return captionOverride == null ? null : captionOverride.translate(adLanguage);
	}

	private Builder toBuilder()
	{
		return new Builder(this);
	}

	public static final class Builder
	{
		private Boolean accepted;
		private ITranslatableString reason;
		private ITranslatableString captionOverride;

		private Builder()
		{
		}

		private Builder(@NonNull final ProcessPreconditionsResolution template)
		{
			accepted = template.accepted;
			reason = template.reason;
			captionOverride = template.captionOverride;
		}

		public ProcessPreconditionsResolution build()
		{
			return new ProcessPreconditionsResolution(this);
		}

		public ProcessPreconditionsResolution accept()
		{
			accepted = Boolean.TRUE;
			reason = null;
			return build();
		}

		public ProcessPreconditionsResolution reject(final String reason)
		{
			accepted = Boolean.FALSE;
			this.reason = ImmutableTranslatableString.constant(reason);
			return build();
		}

		private boolean isAccepted()
		{
			Check.assumeNotNull(accepted, "accepted/rejected shall be set");
			return accepted.booleanValue();
		}

		private ITranslatableString getReason()
		{
			return reason;
		}

		/**
		 * Set a translatable string to be shown as "process name", instead of the actual {@code AD_Process.Name}.
		 * 
		 * @param captionOverride optional, may be {@code null} to have no override.
		 * @return
		 */
		public ProcessPreconditionsResolution.Builder setCaptionOverride(final ITranslatableString captionOverride)
		{
			this.captionOverride = captionOverride;
			return this;
		}

		/**
		 * Set a constant string to be shown as "process name", instead of the actual {@code AD_Process.Name}.
		 * <p>
		 * Note: this method makes sense, because there are cases where we choose not to translate a string.<br>
		 * Known example: packing instruction names, such as "IFCO 6410 x 10"
		 * 
		 * @param captionOverride optional, may be {@code null} to have no override.
		 * @return
		 */
		public ProcessPreconditionsResolution.Builder setCaptionOverride(final String captionOverride)
		{
			this.captionOverride = captionOverride == null ? null : ImmutableTranslatableString.constant(captionOverride);
			return this;
		}

		private ITranslatableString getCaptionOverride()
		{
			return captionOverride;
		}
	}

	/**
	 * Derive this resolution, overriding the caption.
	 * 
	 * @param captionOverride caption override; null value will be considered as no override
	 * @return
	 */
	public ProcessPreconditionsResolution deriveWithCaptionOverride(@Nullable final String captionOverride)
	{
		final ITranslatableString captionOverrideNew = captionOverride == null ? null : ImmutableTranslatableString.constant(captionOverride);
		if (Objects.equal(this.captionOverride, captionOverrideNew))
		{
			return this;
		}

		return toBuilder()
				.setCaptionOverride(captionOverrideNew)
				.build();
	}

	public ProcessPreconditionsResolution and(Supplier<ProcessPreconditionsResolution> resolutionSupplier)
	{
		if (isRejected())
		{
			return this;
		}

		return resolutionSupplier.get();
	}

}
