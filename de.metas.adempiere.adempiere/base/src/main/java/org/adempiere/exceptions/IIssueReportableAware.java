package org.adempiere.exceptions;

import de.metas.error.AdIssueId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * Interface implemented by exceptions which are aware of AD_Issue reporting.
 * 
 * To work with those kind of exceptions, please use {@link IssueReportableExceptions}.
 * 
 * @author tsa
 *
 */
public interface IIssueReportableAware
{
	/**
	 * Mark this exception as reported.
	 * 
	 * @param adIssueId
	 */
	void markIssueReported(final AdIssueId adIssueId);

	/**
	 * @return <code>true</code> if this exception was already reported
	 */
	boolean isIssueReported();
	
	AdIssueId getAdIssueId();
}
