package de.metas.security.permissions;

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

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.concurrent.Immutable;

/**
 * Application Dictionary element (e.g. Window, Process etc).
 *
 * @author tsa
 *
 */
@Immutable
@EqualsAndHashCode(of = {"elementTableNameUC", "elementId"})
public final class ElementResource implements Resource
{
	public static ElementResource of(final String tableName, final int elementId)
	{
		return new ElementResource(tableName, elementId);
	}

	@Getter
	private final String elementTableName;
	private final String elementTableNameUC;

	@Getter
	private final int elementId;

	private ElementResource(@NonNull final String elementTableName, final int elementId)
	{
		Check.assumeNotEmpty(elementTableName, "elementTableName not empty");
		Check.assume(elementId > 0, "elementId > 0");

		this.elementTableName = elementTableName.trim();
		this.elementTableNameUC = this.elementTableName.toUpperCase();
		this.elementId = elementId;
	}

	@Override
	public String toString()
	{
		return elementTableName + "-" + elementId;
	}
}
