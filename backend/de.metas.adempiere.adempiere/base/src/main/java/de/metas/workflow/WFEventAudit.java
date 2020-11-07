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

package de.metas.workflow;

import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.workflow.execution.WFProcessId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

@Data
@Builder
public class WFEventAudit
{
	int id;

	@NonNull
	WFEventAuditType eventType;

	@Builder.Default
	@NonNull
	final OrgId orgId = OrgId.ANY;

	final WFProcessId wfProcessId;
	@Nullable
	final WFNodeId wfNodeId;

	@NonNull
	final TableRecordReference documentRef;

	@NonNull
	final WFResponsibleId wfResponsibleId;
	@Nullable
	UserId userId;

	@NonNull
	WFState wfState;
	@Nullable
	String textMsg;

	@NonNull
	@Builder.Default
	Instant created = SystemTime.asInstant();

	@NonNull
	@Builder.Default
	Duration elapsedTime = Duration.ZERO;

	@Nullable
	String attributeName;
	@Nullable
	String attributeValueOld;
	@Nullable
	String attributeValueNew;
}
