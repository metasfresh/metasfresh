package de.metas.dpd.service;

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


import java.sql.Timestamp;

/**
 * 
 * @author ts
 * @see RoutingResult
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class RoutingQuery
{

	/**
	 * R-Depot
	 */
	public final String rDepot;

	/**
	 * Date and Time
	 */
	public final Timestamp date;

	/**
	 * Service
	 */
	public final String service;

	/**
	 * D-Country
	 */
	public final String dCountry;

	/**
	 * D-Postcode
	 */
	public final String dPostCode;

	/**
	 * D-Area
	 */
	public final String dArea;

	/**
	 * D-City
	 */
	public final String dCity;

	public RoutingQuery(String rDepot, Timestamp date, String service,
			String dCountry, String dPostCode, String dArea, String dCity)
	{

		this.rDepot = rDepot;
		this.date = date;
		this.service = service;
		this.dCountry = dCountry;
		this.dPostCode = dPostCode;
		this.dArea = dArea;
		this.dCity = dCity;
	}

}
