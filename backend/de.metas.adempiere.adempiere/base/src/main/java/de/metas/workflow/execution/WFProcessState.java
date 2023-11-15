/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow.execution;

import de.metas.error.AdIssueId;
import de.metas.user.UserId;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import de.metas.workflow.WorkflowId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Data
@Builder
class WFProcessState
{
	@Nullable WFProcessId wfProcessId;
	@NonNull final WorkflowId workflowId;
	@NonNull final TableRecordReference documentRef;
	final int priority;

	@NonNull WFState wfState;

	boolean processed;

	@NonNull final WFResponsibleId wfResponsibleId;
	@NonNull final UserId initialUserId;
	@Nullable final UserId userId;

	@Nullable String textMsg;
	@Nullable AdIssueId issueId;
}
