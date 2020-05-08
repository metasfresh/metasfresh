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

package de.metas.adempiere.service.impl;

/**
 * This controls what Tooltip value is used for Lists  // TODO tbp: is "Lists correct" ? it should include Table, table direct, search and list, but tabledirect returns null right now (see other TODOs)
 */
 // TODO tbp: introduce this reflist to ad_table
public enum ReferenceTooltipType
{
	/**
	 * Tooltip shows nothing
	 */
	None,

	/**
	 * Tooltip shows Description field if it exists. Else nothing.
	 */
	Description,

	/**
	 * Tooltip shows the table identifier.
	 */
	TableIdentifier,

	/**
	 * Tooltip shows the Description field if it is not null, else the table identifier.
	 */
	DescriptionFallbackToTableIdentifier
}
