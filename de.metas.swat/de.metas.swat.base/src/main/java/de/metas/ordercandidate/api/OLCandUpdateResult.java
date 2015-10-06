package de.metas.ordercandidate.api;

/*
 * #%L
 * de.metas.swat.base
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
 * Instances of this class are returned by {@link IOLCandUpdateBL#updateOLCands(java.util.Properties, java.util.Iterator, org.adempiere.util.api.IParams)}.
 * 
 * @author ts
 * 
 */
public class OLCandUpdateResult
{
	private int skipped;

	private int updated;

	public void incSkipped()
	{
		skipped++;
	}

	public void incUpdated()
	{
		updated++;
	}

	public int getSkipped()
	{
		return skipped;
	}

	public int getUpdated()
	{
		return updated;
	}
}
