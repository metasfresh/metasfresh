/**
 * 
 */
package org.adempiere.webui.component;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import org.zkoss.zk.ui.Component;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.East;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;

/**
 * @author teo_sarca
 *
 */
public class Borderlayout extends org.zkoss.zkex.zul.Borderlayout
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6976820221945897268L;
	
	public Borderlayout()
	{
		super();
	}
	
	public Borderlayout appendNorth(Component c)
	{
		North north = getNorth();
		if (north == null)
		{
			north = new North();
			this.appendChild(north);
		}
		north.appendChild(c);
		return this;
	}
	public Borderlayout appendWest(Component c)
	{
		West west = getWest();
		if (west == null)
		{
			west = new West();
			this.appendChild(west);
		}
		west.appendChild(c);
		return this;
	}
	public Borderlayout appendSouth(Component c)
	{
		South south = getSouth();
		if (south == null)
		{
			south = new South();
			this.appendChild(south);
		}
		south.appendChild(c);
		return this;
	}
	public Borderlayout appendEast(Component c)
	{
		East east = getEast();
		if (east == null)
		{
			east = new East();
			this.appendChild(east);
		}
		east.appendChild(c);
		return this;
	}
	public Borderlayout appendCenter(Component c)
	{
		Center center = getCenter();
		if (center == null)
		{
			center = new Center();
			this.appendChild(center);
		}
		center.appendChild(c);
		return this;
	}
}
