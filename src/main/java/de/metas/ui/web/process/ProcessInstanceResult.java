package de.metas.ui.web.process;

import java.io.File;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import org.compiere.util.Util;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

@Immutable
@SuppressWarnings("serial")
public final class ProcessInstanceResult implements Serializable
{
	public static final Builder builder(final DocumentId instanceId)
	{
		return new Builder(instanceId);
	}

	public static final ProcessInstanceResult ok(final DocumentId instanceId)
	{
		return builder(instanceId).build();
	}

	public static final ProcessInstanceResult error(final DocumentId instanceId, final Throwable error)
	{
		return builder(instanceId)
				.setError(true)
				.setSummary(error.getLocalizedMessage())
				.build();
	}

	private final DocumentId instanceId;
	private final String summary;
	private final boolean error;
	private final ResultAction action;

	private ProcessInstanceResult(final Builder builder)
	{
		super();
		instanceId = builder.instanceId;
		summary = builder.summary;
		error = builder.error;

		action = builder.action;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("instanceId", instanceId)
				.add("summary", summary)
				.add("error", error)
				.add("action", action)
				.toString();
	}

	public DocumentId getInstanceId()
	{
		return instanceId;
	}

	public String getSummary()
	{
		return summary;
	}

	public boolean isSuccess()
	{
		return !isError();
	}

	public boolean isError()
	{
		return error;
	}

	/** @return action or null */
	public ResultAction getAction()
	{
		return action;
	}

	/** @return action of given type; never returns null */
	public <T extends ResultAction> T getAction(final Class<T> actionType)
	{
		final ResultAction action = getAction();
		if (action == null)
		{
			throw new IllegalStateException("No action defined");
		}
		if (!actionType.isAssignableFrom(action.getClass()))
		{
			throw new IllegalStateException("Action is not of type " + actionType + " but " + action.getClass());
		}
		@SuppressWarnings("unchecked")
		final T actionCasted = (T)action;
		return actionCasted;
	}

	//
	//
	//
	//
	//

	/** Base interface for all post-process actions */
	public static interface ResultAction
	{
	}

	@lombok.Value
	@lombok.Builder
	public static final class OpenReportAction implements ResultAction
	{
		@NonNull
		private final String filename;
		@NonNull
		private final String contentType;
		@NonNull
		@Setter(AccessLevel.NONE)
		@Getter(AccessLevel.NONE)
		private final File tempFile;

		public byte[] getReportData()
		{
			return Util.readBytes(tempFile);
		}

	}

	@lombok.Value
	@lombok.Builder
	public static final class OpenViewAction implements ResultAction
	{
		@NonNull
		private final ViewId viewId;
	}

	@lombok.Value
	@lombok.Builder
	public static class OpenIncludedViewAction implements ResultAction
	{
		@NonNull
		private final ViewId viewId;
		private final ViewProfileId profileId;
	}

	@lombok.Value
	@AllArgsConstructor(staticName = "of")
	public static class CreateAndOpenIncludedViewAction implements ResultAction
	{
		@NonNull
		private final CreateViewRequest createViewRequest;
	}

	@lombok.Value
	@lombok.Builder
	public static final class OpenSingleDocument implements ResultAction
	{
		@NonNull
		private final DocumentPath documentPath;
		private final boolean modal;
	}

	@lombok.Value
	@lombok.Builder
	public static final class SelectViewRowsAction implements ResultAction
	{
		private @NonNull final ViewId viewId;
		private @NonNull final DocumentIdsSelection rowIds;
	}

	public static final class Builder
	{
		private DocumentId instanceId;
		private String summary;
		private boolean error;

		private ResultAction action;

		private Builder(@NonNull DocumentId instanceId)
		{
			super();
			this.instanceId = instanceId;
		}

		public ProcessInstanceResult build()
		{
			return new ProcessInstanceResult(this);
		}

		public Builder setSummary(final String summary)
		{
			this.summary = summary;
			return this;
		}

		public Builder setError(final boolean error)
		{
			this.error = error;
			return this;
		}

		public Builder setAction(final ResultAction action)
		{
			this.action = action;
			return this;
		}
	}
}
