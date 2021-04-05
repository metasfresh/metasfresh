/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.exceptions;

import de.metas.error.AdIssueId;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.compiere.util.Trace;

import javax.annotation.Nullable;

@Value
@Builder
public class WebuiError
{
	@NonNull ITranslatableString errorMessage;
	@Nullable String stackTrace;
	@Nullable AdIssueId adIssueId;

	public static WebuiError of(@NonNull final Exception exception)
	{
		return of(exception, null);
	}

	public static WebuiError of(@NonNull final Exception exception, @Nullable final ITranslatableString errorMessage)
	{
		return builder()
				.errorMessage(errorMessage != null ? errorMessage : AdempiereException.extractMessageTrl(exception))
				.stackTrace(Trace.toOneLineStackTraceString(exception))
				.adIssueId(IssueReportableExceptions.getAdIssueIdOrNull(exception))
				.build();
	}
}
