package de.metas.report.jasper;

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
import java.util.Optional;
import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MImage;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.io.BaseEncoding;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Builds and returns the local organization logo {@link File}.
 * 
 * @author tsa
 *
 */
final class OrgLogoLocalFileLoader
{
	public static OrgLogoLocalFileLoader newInstance()
	{
		return new OrgLogoLocalFileLoader();
	}

	private static final transient Logger logger = LogManager.getLogger(OrgLogoLocalFileLoader.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);

	private static final String emptyPNGBase64Encoded = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII="; // thanks to http://png-pixel.com/
	private transient File emptyPNGFile; // lazy

	private OrgLogoLocalFileLoader()
	{
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("emptyPNGFile", emptyPNGFile)
				.toString();
	}

	// @Override
	public Optional<File> loadLogoForOrg(@NonNull final OrgId adOrgId)
	{
		final File logoFile = createAndGetLocalLogoFile(adOrgId);
		if (logoFile != null)
		{
			return Optional.of(logoFile);
		}
		else
		{
			logger.warn("Cannot find logo for {}, please add a logo file to the organization. Returning empty PNG file", this);
			return Optional.of(getEmptyPNGFile());
		}
	}

	private File createAndGetLocalLogoFile(@NonNull final OrgId adOrgId)
	{
		//
		// Retrieve the logo image
		final I_AD_Image logo = retrieveLogoImage(adOrgId);
		if (logo == null || logo.getAD_Image_ID() <= 0)
		{
			return null;
		}

		//
		// Save the logo in a temporary file, to be locally available
		File logoFile = createTempLogoFile(logo);
		return logoFile;
	}

	private I_AD_Image retrieveLogoImage(@NonNull final OrgId adOrgId)
	{
		if (adOrgId.isAny())
		{
			return null;
		}

		final Properties ctx = Env.getCtx();

		//
		// Get Logo from Org's BPartner
		// task FRESH-356: get logo also from org's bpartner if is set
		{
			final I_AD_Org org = orgsRepo.getById(adOrgId);
			if (org != null)
			{
				final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);
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
		final OrgInfo orgInfo = orgsRepo.getOrgInfoById(adOrgId);
		final int logoImageId = orgInfo.getLogoImageId();
		if (logoImageId > 0)
		{
			return MImage.get(Env.getCtx(), logoImageId);
		}

		//
		// Get Tenant level Logo
		final ClientId adClientId = orgInfo.getClientId();
		final I_AD_ClientInfo clientInfo = clientsRepo.retrieveClientInfo(ctx, adClientId.getRepoId());
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

	private static File createTempLogoFile(final I_AD_Image logo)
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

		return createTempPNGFile("logo", logoData);
	}

	private static File createTempPNGFile(
			@NonNull final String filenamePrefix,
			@NonNull byte[] content)
	{
		try
		{
			final File tempFile = File.createTempFile(filenamePrefix, ".png");
			try (final FileOutputStream out = new FileOutputStream(tempFile))
			{
				out.write(content);
				out.close();
			}

			return tempFile;
		}
		catch (IOException e)
		{
			logger.warn("Failed creating the logo temporary file", e);
			return null;
		}
	}

	private File getEmptyPNGFile()
	{
		File file = this.emptyPNGFile;
		if (file != null && !file.exists())
		{
			file = null;
		}

		if (file == null)
		{
			file = this.emptyPNGFile = createTempPNGFile(
					"empty",
					BaseEncoding.base64().decode(emptyPNGBase64Encoded));
		}
		return file;
	}
}
