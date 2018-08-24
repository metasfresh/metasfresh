package de.metas.vertical.pharma.vendor.gateway.msv3;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Properties;
import java.util.UUID;

import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_System;
import org.compiere.model.MSequence;
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
	private static final int MSV3_MAX_SUPPORT_ID_999999 = 999999;
	private static final String MSV3_SUPPORT_ID_SEQUENCE = "MSV3_SupportId";

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

	public static String createUniqueId()
	{
		return UUID.randomUUID().toString();
	}

	private static int staticIdForUnitTests = 0;

	// TODO: convert it to some service!!!
	public static int retrieveNextSupportId()
	{
		if (Adempiere.isUnitTestMode())
		{
			return ++staticIdForUnitTests;
		}

		final int supportId = MSequence.getNextID(Env.CTXVALUE_AD_Client_ID_System, MSV3_SUPPORT_ID_SEQUENCE);
		if (supportId <= MSV3_MAX_SUPPORT_ID_999999)
		{
			return supportId;
		}

		final Properties sysContext = Env.createSysContext(Env.getCtx());
		final I_AD_Sequence tableSequence = Services.get(ISequenceDAO.class).retrieveTableSequenceOrNull(
				sysContext, MSV3_SUPPORT_ID_SEQUENCE);

		tableSequence.setCurrentNext(2);
		tableSequence.setCurrentNextSys(2);
		save(tableSequence);

		return 1;
	}
}
