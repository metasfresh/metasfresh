package de.metas.process;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;

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

/**
 * A small immutable object which contains the AD_Process_ID and it's attached flags (is quick action, is the default quick action etc).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class RelatedProcessDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final RelatedProcessDescriptor ofAD_Process_ID(final int adProcessId)
	{
		return builder()
				.setAD_Process_ID(adProcessId)
				.build();
	}

	private final int adProcessId;
	private final boolean webuiQuickAction;
	private final boolean webuiDefaultQuickAction;

	private RelatedProcessDescriptor(final Builder builder)
	{
		super();
		adProcessId = builder.adProcessId;
		if (adProcessId <= 0)
		{
			throw new IllegalArgumentException("adProcessId shall be >= 0");
		}

		webuiQuickAction = builder.webuiQuickAction;
		webuiDefaultQuickAction = builder.webuiDefaultQuickAction;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Process_ID", adProcessId)
				.add("webuiQuickAction", webuiQuickAction)
				.add("webuiDefaultQuickAction", webuiDefaultQuickAction)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(adProcessId, webuiQuickAction, webuiDefaultQuickAction);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof RelatedProcessDescriptor)
		{
			final RelatedProcessDescriptor other = (RelatedProcessDescriptor)obj;
			return adProcessId == other.adProcessId
					&& webuiQuickAction == other.webuiQuickAction
					&& webuiDefaultQuickAction == other.webuiDefaultQuickAction;
		}
		else
		{
			return false;
		}
	}

	public int getAD_Process_ID()
	{
		return adProcessId;
	}

	public boolean isWebuiQuickAction()
	{
		return webuiQuickAction;
	}

	public boolean isWebuiDefaultQuickAction()
	{
		return webuiDefaultQuickAction;
	}

	public static final class Builder
	{
		private int adProcessId;
		private boolean webuiQuickAction;
		private boolean webuiDefaultQuickAction;

		private Builder()
		{
			super();
		}

		public RelatedProcessDescriptor build()
		{
			return new RelatedProcessDescriptor(this);
		}

		public Builder setAD_Process_ID(final int adProcessId)
		{
			this.adProcessId = adProcessId;
			return this;
		}

		public Builder setWebuiQuickAction(final boolean webuiQuickAction)
		{
			this.webuiQuickAction = webuiQuickAction;
			return this;
		}

		public Builder setWebuiDefaultQuickAction(final boolean webuiDefaultQuickAction)
		{
			this.webuiDefaultQuickAction = webuiDefaultQuickAction;
			return this;
		}
	}
}
