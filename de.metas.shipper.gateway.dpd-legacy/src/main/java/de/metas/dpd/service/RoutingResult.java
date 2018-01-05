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


/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */

public class RoutingResult
{

	/**
	 * Country
	 */
	public final String country;

	/**
	 * Service Text
	 */
	public final String serviceText;

	/**
	 * D-Depot
	 */
	public final String dDepot;

	/**
	 * Lot d'acheminement
	 */
	public final String lotDAcheminement;

	/**
	 * Service Mark
	 */
	public final String serviceMark;

	/**
	 * Service Info
	 */
	public final String serviceInfo;

	/**
	 * O-Sort
	 */
	public final String oSort;

	/**
	 * D-Sort
	 */
	public final String dSort;

	/**
	 * Grouping Priority
	 */
	public final String groupingPriority;

	/**
	 * Barcode ID
	 */
	public final String barcodeId;

	/**
	 * Routing Version
	 */
	public final String routingVersion;

	public final int numCountryCode;

	public RoutingResult(String country, String serviceText, String dDepot,
			String lotDAcheminement, String serviceMark, String serviceInfo,
			String oSort, String dSort, String groupingPriority,
			String barcodeId, String routingVersion, int numCountryCode)
	{

		this.country = country;
		this.serviceText = serviceText;
		this.dDepot = dDepot;
		this.lotDAcheminement = lotDAcheminement;
		this.serviceMark = serviceMark;
		this.serviceInfo = serviceInfo;
		this.oSort = oSort;
		this.dSort = dSort;
		this.groupingPriority = groupingPriority;
		this.barcodeId = barcodeId;
		this.routingVersion = routingVersion;
		this.numCountryCode = numCountryCode;
	}
}
