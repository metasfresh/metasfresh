package de.metas.ui.web.process;

import de.metas.process.ProcessExecutionResult;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.io.File;

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

@Value
public class ProcessInstanceResult
{
	public static ProcessInstanceResultBuilder builder(@NonNull final DocumentId instanceId)
	{
		return new ProcessInstanceResultBuilder()
				.instanceId(instanceId);
	}

	DocumentId instanceId;
	String summary;
	boolean error;
	@Nullable ResultAction action;

	@Builder
	private ProcessInstanceResult(
			@NonNull final DocumentId instanceId,
			final String summary,
			final boolean error,
			@Nullable final ResultAction action)
	{
		this.instanceId = instanceId;
		this.summary = summary;
		this.error = error;
		this.action = action;
	}

	public boolean isSuccess()
	{
		return !isError();
	}

	/**
	 * @return action of given type; never returns null
	 */
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
		@SuppressWarnings("unchecked") final T actionCasted = (T)action;
		return actionCasted;
	}

	//
	//
	//
	//
	//

	/**
	 * Base interface for all post-process actions
	 */
	public interface ResultAction
	{
	}

	@lombok.Value
	@lombok.Builder
	public static class OpenReportAction implements ResultAction
	{
		@NonNull
		String filename;

		@NonNull
		String contentType;

		@NonNull
		@Setter(AccessLevel.NONE)
		@Getter(AccessLevel.NONE)
		File tempFile;

		public byte[] getReportData()
		{
			return Util.readBytes(tempFile);
		}
	}

	@lombok.Value
	@lombok.Builder
	public static class OpenViewAction implements ResultAction
	{
		@NonNull
		ViewId viewId;

		@Nullable
		ViewProfileId profileId;

		@Builder.Default
		ProcessExecutionResult.RecordsToOpen.TargetTab targetTab = ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB_OVERLAY;
	}

	@lombok.Value
	@lombok.Builder
	public static class OpenIncludedViewAction implements ResultAction
	{
		@NonNull
		ViewId viewId;
		ViewProfileId profileId;
	}

	@lombok.Value(staticConstructor = "of")
	public static class CreateAndOpenIncludedViewAction implements ResultAction
	{
		@NonNull
		CreateViewRequest createViewRequest;
	}

	public static final class CloseViewAction implements ResultAction
	{
		public static final CloseViewAction instance = new CloseViewAction();

		private CloseViewAction() {}
	}

	@lombok.Value
	@lombok.Builder
	public static class OpenSingleDocument implements ResultAction
	{
		@NonNull
		DocumentPath documentPath;
		@Builder.Default
		ProcessExecutionResult.RecordsToOpen.TargetTab targetTab = ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB;

	}

	@lombok.Value
	@lombok.Builder
	public static class SelectViewRowsAction implements ResultAction
	{
		@NonNull ViewId viewId;
		@NonNull DocumentIdsSelection rowIds;
	}

	@lombok.Value
	@lombok.Builder
	public static class DisplayQRCodeAction implements ResultAction
	{
		@NonNull String code;
	}
}
