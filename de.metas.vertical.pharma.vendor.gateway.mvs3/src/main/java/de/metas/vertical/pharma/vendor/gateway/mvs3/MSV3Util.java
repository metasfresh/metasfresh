package de.metas.vertical.pharma.vendor.gateway.mvs3;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;

import de.metas.vendor.gateway.api.ProductAndQuantity;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MSV3Util
{

	public static final int MSV3_MAX_QUANTITY_99999 = 99999;

	public static final ExtendedMemorizingSupplier<String> CLIENT_SOFTWARE_IDENTIFIER = ExtendedMemorizingSupplier
			.of(() -> retrieveSoftwareIndentifier());

	private static String retrieveSoftwareIndentifier()
	{
		try
		{
			final I_AD_System adSystem = Services.get(ISystemBL.class).get(Env.getCtx());
			return "metasfresh-" + adSystem.getDBVersion();
		}
		catch (final RuntimeException e)
		{
			return "metasfresh-<unable to retrieve version!>";
		}
	}

	public static String createUniqueId()
	{
		return UUID.randomUUID().toString();
	}

	public static long extractPZN(final ProductAndQuantity requestItem)
	{
		final String productIdentifier = requestItem.getProductIdentifier();
		try
		{
			return Long.parseLong(productIdentifier);
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("Unable to parse a long value from productIdentifier=" + productIdentifier, e)
					.appendParametersToMessage().setParameter("requestItem", requestItem);
		}
	}

	public static int extractMenge(final ProductAndQuantity requestItem)
	{
		final int intValue = requestItem.getQuantity().setScale(0, RoundingMode.UP).intValue();
		Check.errorIf(intValue > MSV3_MAX_QUANTITY_99999,
				"The MSV3 standard allows a maximum quantity of {}; productAndQuantity={}",
				MSV3_MAX_QUANTITY_99999, requestItem);
		return intValue;
	}

	public static Timestamp toTimestampOrNull(@Nullable final XMLGregorianCalendar xmlGregorianCalendar)
	{
		final Date datePromised = toDateOrNull(xmlGregorianCalendar);
		return new Timestamp(datePromised.getTime());
	}

	public static Date toDateOrNull(@Nullable final XMLGregorianCalendar xmlGregorianCalendar)
	{
		final Date datePromised = xmlGregorianCalendar == null ? null : xmlGregorianCalendar.toGregorianCalendar().getTime();
		return datePromised;
	}
}
