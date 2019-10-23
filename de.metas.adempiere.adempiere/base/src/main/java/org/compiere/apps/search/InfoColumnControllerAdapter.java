package org.compiere.apps.search;

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


public abstract class InfoColumnControllerAdapter implements IInfoColumnController
{
	
	/**
	 *  Signals the other controllers to return null.
	 *  We use this constant because if a controller returns null, it may have not applied and we need a fallback.
	 */
	public static final String RETURN_NULL = "Return_null";
	
	@Override
	public void afterInfoWindowInit(IInfoSimple infoWindow)
	{
		// nothing
	}

	/**
	 * @return <code>data</code>
	 */
	@Override
	public Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, int rowRecordId, Object data)
	{
		return data;
	}

}
