package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum Flag
{
	/** Means that at least originally there was no corresponding document (for example, production order), but that the system had proposed a receipt. */
	TRUE,

	FALSE,

	/**
	 * Don't update existing records, but initialize new ones to {@code false}.
	 * <p>
	 * Only used when storing an instance with the {@link CandidateRepositoryWriteService}.<br>
	 * If you load an instance from DB, it shall never have flags with this value.
	 */
	FALSE_DONT_UPDATE;

	public static Flag of(final boolean value)
	{
		return value ? TRUE : FALSE;
	}

	public boolean isTrue()
	{
		return this.equals(TRUE);
	}

	public boolean isUpdateExistingRecord()
	{
		return !this.equals(FALSE_DONT_UPDATE);
	}
}
