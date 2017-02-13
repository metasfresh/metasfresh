package de.metas.process;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ProcessPreconditionsResolution
{
	public static final ProcessPreconditionsResolution accept()
	{
		return ACCEPTED;
	}

	public static final ProcessPreconditionsResolution reject()
	{
		return REJECTED_UnknownReason;
	}

	public static final ProcessPreconditionsResolution reject(final String reason)
	{
		if (Check.isEmpty(reason, true))
		{
			return REJECTED_UnknownReason;
		}

		final boolean accepted = false;
		final boolean internal = false;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	public static final ProcessPreconditionsResolution rejectWithInternalReason(final String reason)
	{
		if (Check.isEmpty(reason, true))
		{
			return REJECTED_UnknownReason;
		}

		final boolean accepted = false;
		final boolean internal = true;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	public static final ProcessPreconditionsResolution rejectBecauseNoSelection()
	{
		final boolean accepted = false;
		final String reason = "no rows selected"; // TODO: trl
		final boolean internal = false;
		return new ProcessPreconditionsResolution(accepted, reason, internal);
	}

	public static final ProcessPreconditionsResolution rejectBecauseNotSingleSelection()
	{
		final boolean accepted = false;
		final String reason = "only one row shall be selected"; // TODO: trl
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
	private final String reason;
	private final boolean internal;
	private final ITranslatableString captionOverride;

	private ProcessPreconditionsResolution(final boolean accepted, final String reason, final boolean internal)
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

	public String getReason()
	{
		return reason;
	}

	public String getRejectReason()
	{
		return !accepted ? reason : null;
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
		private String reason;
		private ITranslatableString captionOverride;

		private Builder()
		{
		}

		private Builder(final ProcessPreconditionsResolution template)
		{
			accepted = template.accepted;
			reason = template.reason;
			captionOverride = template.captionOverride;
		}

		public ProcessPreconditionsResolution build()
		{
			return new ProcessPreconditionsResolution(this);
		}

		public ProcessPreconditionsResolution.Builder accept()
		{
			accepted = Boolean.TRUE;
			reason = null;
			return this;
		}

		public ProcessPreconditionsResolution.Builder reject(final String reason)
		{
			accepted = Boolean.FALSE;
			this.reason = reason;
			return this;
		}

		private boolean isAccepted()
		{
			Check.assumeNotNull(accepted, "accepted/rejected shall be set");
			return accepted.booleanValue();
		}

		private String getReason()
		{
			return reason;
		}

		public ProcessPreconditionsResolution.Builder setCaptionOverride(final ITranslatableString captionOverride)
		{
			this.captionOverride = captionOverride;
			return this;
		}

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

	public ProcessPreconditionsResolution deriveWithCaptionOverride(final String captionOverride)
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

}