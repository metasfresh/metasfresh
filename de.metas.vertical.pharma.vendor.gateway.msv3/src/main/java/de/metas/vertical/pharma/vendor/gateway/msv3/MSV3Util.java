package de.metas.vertical.pharma.vendor.gateway.msv3;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;

import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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
	private static final ExtendedMemorizingSupplier<ClientSoftwareId> CLIENT_SOFTWARE_IDENTIFIER = ExtendedMemorizingSupplier
			.of(() -> retrieveSoftwareIndentifier());

	public static ClientSoftwareId getClientSoftwareId()
	{
		return CLIENT_SOFTWARE_IDENTIFIER.get();
	}

	private static ClientSoftwareId retrieveSoftwareIndentifier()
	{
		try
		{
			final I_AD_System adSystem = Services.get(ISystemBL.class).get(Env.getCtx());
			return ClientSoftwareId.of("metasfresh-" + adSystem.getDBVersion());
		}
		catch (final RuntimeException e)
		{
			return ClientSoftwareId.of("metasfresh-<unable to retrieve version!>");
		}
	}
}
