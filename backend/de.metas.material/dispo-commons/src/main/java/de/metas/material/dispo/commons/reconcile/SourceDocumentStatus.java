package de.metas.material.dispo.commons.reconcile;

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

/**
 * Result of the liveness test that decides whether a candidate's source document
 * still contributes to the projected ATP.
 */
public enum SourceDocumentStatus
{
	/** The source document is open and contributes to ATP */
	STILL_OPEN,

	/** The source document is closed and no longer contributes to ATP */
	CLOSED,

	/** The candidate has no source document reference */
	NO_SOURCE_DOCUMENT;

	/**
	 * @return true if this status indicates the source document is still contributing to ATP
	 */
	public boolean isContributingToAtp()
	{
		return this == STILL_OPEN;
	}
}
