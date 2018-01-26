package de.metas.vertical.pharma.vendor.gateway.mvs3;

import java.util.UUID;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MSV3Util
{
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

}
