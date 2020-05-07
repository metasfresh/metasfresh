package de.metas.dpd.model;

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


import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.adempiere.util.Check;
import org.compiere.model.Query;

import de.metas.dpd.exception.DPDException;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class MDPDRoute extends X_DPD_Route
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1476696360338453465L;

	private static final String DELIM = ",";

	public MDPDRoute(Properties ctx, int DPD_Route_ID, String trxName)
	{
		super(ctx, DPD_Route_ID, trxName);
	}

	public MDPDRoute(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static MDPDRoute retrieve(final Properties ctx,
			final String countryCode, final String postal,
			final String serviceCode, final String routingDepot,
			final int routeFileInfoId, final Timestamp date,
			final String trxName)
	{

		final Collection<MDPDRoute> routesPostal = retrieveForCountryAndPostal(
				ctx, countryCode, postal, routeFileInfoId, date, trxName);

		final Set<MDPDRoute> withGivenServiceCode = new HashSet<MDPDRoute>();
		final Set<MDPDRoute> withoutServiceCode = new HashSet<MDPDRoute>();

		for (final MDPDRoute route : routesPostal)
		{

			final String serviceCodes = route.getServiceCodes();
			if (Check.isEmpty(serviceCodes))
			{
				withoutServiceCode.add(route);
			}
			else if (containsCode(serviceCodes, serviceCode))
			{
				withGivenServiceCode.add(route);
			}
		}

		final Set<MDPDRoute> routesServiceCode;
		if (withGivenServiceCode.isEmpty())
		{
			routesServiceCode = withoutServiceCode;
		}
		else
		{
			routesServiceCode = withGivenServiceCode;
		}

		final Set<MDPDRoute> withGivenRoutingDepot = new HashSet<MDPDRoute>();
		final Set<MDPDRoute> withoutRoutingDepot = new HashSet<MDPDRoute>();

		final MDPDFileInfo depotFileInfo = MDPDFileInfo.retrieve(ctx, "DEPOTS",
				date, trxName);

		final MDPDDepot rDepot = MDPDDepot.retrieve(ctx, routingDepot,
				depotFileInfo.get_ID(), trxName);

		for (final MDPDRoute route : routesServiceCode)
		{

			final String routingPlaces = route.getRoutingPlaces();

			if (Check.isEmpty(routingPlaces))
			{
				withoutRoutingDepot.add(route);
			}
			else if (containsPlace(routingPlaces, rDepot))
			{
				withGivenRoutingDepot.add(route);
			}
		}

		final Set<MDPDRoute> result;
		if (withGivenRoutingDepot.isEmpty())
		{
			result = withoutRoutingDepot;
		}
		else
		{
			result = withGivenRoutingDepot;
		}

		if (result.isEmpty())
		{
			throw DPDException.missingRoute(countryCode, postal, serviceCode,
					routingDepot, date);
		}
		return result.iterator().next();
	}

	private static boolean containsPlace(final String routingPlaces,
			final MDPDDepot routingDepot)
	{

		final StringTokenizer t = new StringTokenizer(routingPlaces, DELIM);
		while (t.hasMoreTokens())
		{
			final String token = t.nextToken();
			if (token.matches("D...."))
			{
				// single depot code
				final String depotCode = token.substring(1);
				if (routingDepot.equals(depotCode))
				{
					return true;
				}
			}
			else if (token.matches("D........"))
			{
				// depot code range
				final int rangeStart = Integer.parseInt(token.substring(1, 5));
				final int rangeEnd = Integer.parseInt(token.substring(5));

				final int depotInt = Integer.parseInt(routingDepot
						.getGeoPostDepotNumber());

				if (rangeStart <= depotInt && rangeEnd >= depotInt)
				{
					return true;
				}

			}
			else if (token.matches("C.."))
			{
				// single ISO-2 country code

				final String country = token.substring(1);
				if (country.equals(routingDepot.getCountryCode()))
				{
					return true;
				}
			}
			else if (token.matches("G...."))
			{
				// single depot group

				final String group = token.substring(1);
				if (group.equals(routingDepot.getGroupID()))
				{
					return true;
				}
			}
		}

		// TODO Auto-generated method stub
		return false;
	}

	private static boolean containsCode(final String serviceCodes,
			final String serviceCode)
	{

		final StringTokenizer t = new StringTokenizer(serviceCodes, DELIM);
		while (t.hasMoreTokens())
		{
			final String token = t.nextToken();

			if (token.matches("S..."))
			{
				// single service code
				final String singleCode = token.substring(1);
				if (singleCode.equals(serviceCode))
				{
					return true;
				}

			}
			else if (token.matches("S......"))
			{
				// service code range
				final int rangeStart = Integer.parseInt(token.substring(1, 4));
				final int rangeEnd = Integer.parseInt(token.substring(4));

				final int serviceCodeInt = Integer.parseInt(serviceCode);

				if (rangeStart <= serviceCodeInt && rangeEnd >= serviceCodeInt)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static List<MDPDRoute> retrieveForCountryAndPostal(
			final Properties ctx, final String countryCode,
			final String postal, final int routeFileInfoId,
			final Timestamp date, final String trxName)
	{

		final MDPDFileInfo countryFileInfo = MDPDFileInfo.retrieve(ctx,
				"COUNTRY", date, trxName);

		final MDPDCountry country = MDPDCountry.retrieve(ctx, countryCode,
				countryFileInfo.get_ID(), trxName);

		final String whereClause;
		final Object[] parameters;

		if (country.isFlagPostCodeNo())
		{
			// country has no post code system
			whereClause = COLUMNNAME_DPD_FileInfo_ID + "=? AND "
					+ COLUMNNAME_CountryCode + "=?";
			parameters = new Object[] { routeFileInfoId, countryCode };
		}
		else
		{
			whereClause = //
			COLUMNNAME_DPD_FileInfo_ID
					+ "=? AND "
					+ COLUMNNAME_CountryCode
					+ "=? AND " //
					+ "(( " + COLUMNNAME_BeginPostCode + " <=? AND "
					+ COLUMNNAME_EndPostCode + ">=? ) OR "
					+ COLUMNNAME_BeginPostCode + "=? OR "
					+ COLUMNNAME_BeginPostCode + " is null)";
			parameters = new Object[] { routeFileInfoId, countryCode, postal,
					postal, postal };
		}

		return new Query(ctx, Table_Name, whereClause, trxName).setParameters(
				parameters).setOnlyActiveRecords(true).setOrderBy(
				COLUMNNAME_BeginPostCode).list();
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MDPDRoute[Id=");
		sb.append(get_ID());
		sb.append(", CountryCode=");
		sb.append(getCountryCode());
		sb.append(", BeginPostCode=");
		sb.append(getBeginPostCode());
		sb.append(", EndPostCode=");
		sb.append(getEndPostCode());
		sb.append(", ServiceCodes=");
		sb.append(getServiceCodes());
		sb.append(", RoutingPlaces=");
		sb.append(getRoutingPlaces());
		sb.append(", D_Depot=");
		sb.append(getD_Depot());
		sb.append("]");

		return sb.toString();
	}
}
