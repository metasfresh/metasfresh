package de.metas.material.dispo.commons.candidate;

import javax.annotation.Nullable;

import de.metas.document.engine.DocStatus;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public enum CandidateStatus
{
	doc_planned, doc_created, doc_completed, doc_closed, unexpected;

	public static CandidateStatus ofDocStatus(@Nullable final DocStatus docStatus)
	{
		if (docStatus == null)
		{
			return unexpected;
		}
		if (docStatus.isDraftedOrInProgress())
		{
			return doc_created;
		}
		else if (docStatus.isCompleted())
		{
			return doc_completed;
		}
		else if (docStatus.isClosed())
		{
			return doc_closed;
		}
		else
		{
			return unexpected;
		}
	}
}
