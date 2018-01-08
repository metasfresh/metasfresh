package de.metas.dpd.exception;

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
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Package;
import org.compiere.util.Env;

import de.metas.dpd.service.RoutingQuery;
import de.metas.i18n.Msg;
import de.metas.inout.model.I_M_InOut;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class DPDException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5082834213214079451L;

	private final RoutingQuery query;
	private final Map<String, String> invalidFields;

	public RoutingQuery getQuery()
	{
		return query;
	}

	public Map<String, String> getInvalidFields()
	{
		return invalidFields;
	}

	private DPDException(final RoutingQuery query, final Map<String, String> invalidFields)
	{
		super("Invalid: " + invalidFields);

		this.query = query;
		this.invalidFields = invalidFields;
	}

	private DPDException(final String msg)
	{
		super(msg);

		this.query = null;
		this.invalidFields = null;
	}

	private DPDException(final Properties ctx, final I_M_InOut invalidInOut, final String msg, final Object... params)
	{
		super(Msg.getMsg(ctx, msg, params));

		this.query = null;
		this.invalidFields = null;
	}

	private DPDException(final I_M_Package invalidPack, final String msg, final Object... params)
	{
		super(Msg.getMsg(Env.getCtx(), msg, params));

		this.query = null;
		this.invalidFields = null;
	}

	public static DPDException invalidQuery(final RoutingQuery query, final Map<String, String> invalidFields)
	{
		return new DPDException(query, invalidFields);
	};

	public static DPDException invalidPackage(final I_M_Package pack, final String msg)
	{
		return new DPDException(pack, msg);
	}

	public static DPDException invalidInOut(final Properties ctx, final I_M_InOut invalidInOut, final String msg)
	{
		return new DPDException(ctx, invalidInOut, msg);
	}

	public static DPDException missingRoute(final String countryCode,
			final String postal, final String serviceCode,
			final String routingDepot, final Timestamp date)
	{
		// TODO -> AD_Message
		final StringBuilder sb = new StringBuilder(
				"Fehlende Rounting-Daten fuer Addresse und Service\n");
		sb.append("Datum: '").append(date).append("'\n");
		sb.append("Land: '").append(countryCode).append("'\n");
		sb.append("PLZ: '").append(postal).append("'\n");
		sb.append("Service-Code: '").append(serviceCode).append("'\n");
		sb.append("Routing-Depot: '").append(routingDepot).append("'\n");

		return new DPDException(sb.toString());
	}
}
