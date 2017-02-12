package de.metas.adempiere.report.jasper;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import com.google.common.base.Optional;

/**
 * Builds and returns the local organization logo {@link File}.
 * 
 * @author tsa
 *
 */
class OrgLogoLocalFileLoader implements Callable<Optional<File>>
{
	public static final OrgLogoLocalFileLoader forAD_Org_ID(final int adOrgId)
	{
		return new OrgLogoLocalFileLoader(adOrgId);
	}

	private static final transient Logger logger = LogManager.getLogger(OrgLogoLocalFileLoader.class);

	private final int _adOrgId;

	private OrgLogoLocalFileLoader(final int adOrgId)
	{
		super();
		this._adOrgId = adOrgId;
	}

	@Override
	public Optional<File> call() throws Exception
	{
		final File logoFile = createAndGetLocalLogoFile();
		return Optional.fromNullable(logoFile);
	}

	private final int getAD_Org_ID()
	{
		return _adOrgId;
	}

	private File createAndGetLocalLogoFile()
	{
		//
		// Retrieve the logo image
		final I_AD_Image logo = retrieveLogoImage();
		if (logo == null || logo.getAD_Image_ID() <= 0)
		{
			return null;
		}

		//
		// Save the logo in a temporary file, to be locally available
		File logoFile = createTempLogoFile(logo);
		return logoFile;
	}

	private final I_AD_Image retrieveLogoImage()
	{
		final int adOrgId = getAD_Org_ID();
		if (adOrgId <= 0)
		{
			return null;
		}

		final Properties ctx = Env.getCtx();

		//
		// Get Logo from Org's BPartner
		// task FRESH-356: get logo also from org's bpartner if is set
		{
			final I_AD_Org org = Services.get(IOrgDAO.class).retrieveOrg(ctx, adOrgId);
			if (org != null)
			{
				final I_C_BPartner orgBPartner = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(org);
				if (orgBPartner != null)
				{
					final I_AD_Image orgBPartnerLogo = orgBPartner.getLogo();
					if (orgBPartnerLogo != null && orgBPartnerLogo.getAD_Image_ID() > 0)
					{
						return orgBPartnerLogo;
					}
				}
			}
		}

		//
		// Get Org Logo
		final I_AD_OrgInfo orgInfo = Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, adOrgId, ITrx.TRXNAME_None);
		if (orgInfo == null)
		{
			return null;
		}
		//
		final I_AD_Image orgLogo = orgInfo.getLogo();
		if (orgLogo != null && orgLogo.getAD_Image_ID() > 0)
		{
			return orgLogo;
		}

		//
		// Get Tenant level Logo
		final int adClientId = orgInfo.getAD_Client_ID();
		final I_AD_ClientInfo clientInfo = Services.get(IClientDAO.class).retrieveClientInfo(ctx, adClientId);
		I_AD_Image clientLogo = clientInfo.getLogoReport();
		if (clientLogo == null || clientLogo.getAD_Image_ID() <= 0)
		{
			clientLogo = clientInfo.getLogo();
		}
		if (clientLogo == null || clientLogo.getAD_Image_ID() <= 0)
		{
			return null;
		}
		return clientLogo;
	}

	private final File createTempLogoFile(final I_AD_Image logo)
	{
		if (logo == null)
		{
			return null;
		}

		final byte[] logoData = logo.getBinaryData();
		if (logoData == null || logoData.length <= 0)
		{
			return null;
		}

		try
		{
			final File tempFile = File.createTempFile("logo", ".png");
			try (final FileOutputStream out = new FileOutputStream(tempFile))
			{
				out.write(logoData);
				out.close();
			}

			return tempFile;
		}
		catch (IOException e)
		{
			logger.warn("Failed creating the logo temporary file", e);
		}

		return null;
	}

}
