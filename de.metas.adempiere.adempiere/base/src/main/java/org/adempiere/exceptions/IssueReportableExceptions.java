package org.adempiere.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import javax.annotation.Nullable;

import de.metas.error.AdIssueId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Utility methods for working with {@link IIssueReportableAware} exceptions.
 * 
 * @author tsa
 *
 */
@UtilityClass
public final class IssueReportableExceptions
{
	/**
	 * Marks given exception and all of it's causes chain as issue reported.
	 * 
	 * @param exception
	 * @param adIssueId
	 */
	public static void markReportedIfPossible(@NonNull final Throwable exception, @NonNull final AdIssueId adIssueId)
	{
		// NOTE: we are marking as reported all the causes downline because mainly all of those exceptions were reported in one shot.
		Throwable currentEx = exception;
		while (currentEx != null)
		{
			if (currentEx instanceof IIssueReportableAware)
			{
				final IIssueReportableAware issueReportable = (IIssueReportableAware)currentEx;
				issueReportable.markIssueReported(adIssueId);
			}

			currentEx = currentEx.getCause();
		}
	}

	/**
	 * Checks if given exception or any of it's causes was marked as reported.
	 * 
	 * NOTE: We are also checking the causes downline because ADempiere's excesive exception wrapping.
	 * 
	 * @param exception exceptions chain to be checked
	 * @return <code>true</code> if given exception was marked as reported.
	 */
	public static boolean isReported(@Nullable final Throwable exception)
	{
		Throwable currentEx = exception;

		while (currentEx != null)
		{
			if (currentEx instanceof IIssueReportableAware)
			{
				final IIssueReportableAware issueReportable = (IIssueReportableAware)currentEx;
				if (issueReportable.isIssueReported())
				{
					return true;
				}
			}

			currentEx = currentEx.getCause();
		}

		return false;
	}

	public static AdIssueId getAdIssueIdOrNull(@NonNull final Throwable exception)
	{
		if (exception instanceof IIssueReportableAware)
		{
			final IIssueReportableAware issueReportable = (IIssueReportableAware)exception;
			if (issueReportable.isIssueReported())
			{
				return issueReportable.getAdIssueId();
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
}
