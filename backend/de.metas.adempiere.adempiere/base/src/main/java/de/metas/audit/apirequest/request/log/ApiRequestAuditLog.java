/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.audit.apirequest.request.log;

import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.error.AdIssueId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ApiRequestAuditLog
{
	@NonNull
	Instant timestamp;

	@NonNull
	ApiRequestAuditId apiRequestAuditId;

	@NonNull
	ClientId adClientId;

	@NonNull
	UserId userId;

	@Nullable
	AdIssueId adIssueId;

	@Nullable
	String message;

	@Nullable
	ITableRecordReference recordReference;

	@Nullable
	StateType type;

	@Nullable
	String trxName;
}