/**
 * 
 */
package org.compiere.model;

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


import java.util.Properties;

/**
 * @author teo_sarca
 *
 */
public class CalloutDummyClientLevel extends CalloutEngine
{
	public String test (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		System.out.println("WindowNo="+WindowNo
				+", ColumnName="+mTab.getTableName()+"."+mField.getColumnName()
				+", Value="+value
		);
		return "";
	}

}
