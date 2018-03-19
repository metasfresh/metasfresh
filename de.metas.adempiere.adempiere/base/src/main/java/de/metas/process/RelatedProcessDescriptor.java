package de.metas.process;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.security.IUserRolePermissions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.Value;

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

/**
 * A small immutable object which contains the AD_Process_ID and it's attached flags (is quick action, is the default quick action etc).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@Value
public final class RelatedProcessDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int processId;

	private final int tableId;
	private final int windowId;

	private final boolean webuiQuickAction;
	private final boolean webuiDefaultQuickAction;

	private final ImmutableList<IProcessPrecondition> processPreconditionsCheckers;

	private RelatedProcessDescriptor(final Builder builder)
	{
		processId = builder.processId;
		Preconditions.checkArgument(processId > 0, "adProcessId not set");

		tableId = builder.tableId > 0 ? builder.tableId : 0;
		windowId = builder.windowId > 0 ? builder.windowId : 0;

		webuiQuickAction = builder.webuiQuickAction;
		webuiDefaultQuickAction = builder.webuiDefaultQuickAction;

		processPreconditionsCheckers = builder.getProcessPreconditionsCheckers();
	}

	public boolean isExecutionGranted(final IUserRolePermissions permissions)
	{
		return permissions.checkProcessAccessRW(processId);
	}

	public static final class Builder
	{
		private int processId;
		private int tableId;
		private int windowId;
		private boolean webuiQuickAction;
		private boolean webuiDefaultQuickAction;
		private List<IProcessPrecondition> processPreconditionsCheckers;

		private Builder()
		{
			super();
		}

		public RelatedProcessDescriptor build()
		{
			return new RelatedProcessDescriptor(this);
		}

		public Builder processId(final int adProcessId)
		{
			processId = adProcessId;
			return this;
		}

		public Builder tableId(final int adTableId)
		{
			tableId = adTableId;
			return this;
		}

		public Builder anyTable()
		{
			tableId = 0;
			return this;
		}

		public Builder windowId(final int adWindowId)
		{
			windowId = adWindowId > 0 ? adWindowId : 0;
			return this;
		}

		public Builder anyWindow()
		{
			windowId = 0;
			return this;
		}

		public Builder webuiQuickAction(final boolean webuiQuickAction)
		{
			this.webuiQuickAction = webuiQuickAction;
			return this;
		}

		public Builder webuiDefaultQuickAction(final boolean webuiDefaultQuickAction)
		{
			this.webuiDefaultQuickAction = webuiDefaultQuickAction;
			return this;
		}

		public Builder webuiDefaultQuickAction()
		{
			webuiDefaultQuickAction(true);
			return this;
		}

		private ImmutableList<IProcessPrecondition> getProcessPreconditionsCheckers()
		{
			return processPreconditionsCheckers != null ? ImmutableList.copyOf(processPreconditionsCheckers) : ImmutableList.of();
		}

		public Builder processPreconditionsCheckers(final List<IProcessPrecondition> processPreconditionsCheckers)
		{
			if (this.processPreconditionsCheckers == null)
			{
				this.processPreconditionsCheckers = new ArrayList<>();
			}
			this.processPreconditionsCheckers.addAll(processPreconditionsCheckers);
			return this;
		}

		public Builder processPreconditionsChecker(@NonNull final IProcessPrecondition processPreconditionsChecker)
		{
			if (this.processPreconditionsCheckers == null)
			{
				this.processPreconditionsCheckers = new ArrayList<>();
			}
			this.processPreconditionsCheckers.add(processPreconditionsChecker);
			return this;
		}
	}
}
