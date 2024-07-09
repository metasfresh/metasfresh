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
import de.metas.util.Check;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFState;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
class WFActivityState
{
	@Nullable
	WFActivityId id;

	@NonNull
	WFNodeId wfNodeId;

	int priority;

	@NonNull
	WFState wfState;
	boolean processed;

	@Nullable
	String textMsg;
	@Nullable
	AdIssueId issueId;

	@NonNull
	WFResponsibleId wfResponsibleId;
	@Nullable
	UserId userId;

	@Nullable
	private Instant endWaitTime;

	public WFActivityId getIdNotNull() {return Check.assumeNotNull(id, "WF Activity is saved: {}", this);}
}
