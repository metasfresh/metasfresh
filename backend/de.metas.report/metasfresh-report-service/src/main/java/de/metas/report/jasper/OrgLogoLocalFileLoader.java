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
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MImage;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.util.NumberUtils;
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
	final static ImageFileLoader imgFileLoader = ImageFileLoader.newInstance();
	
	public static OrgLogoLocalFileLoader newInstance()
	{
		return new OrgLogoLocalFileLoader();
	}

	private static final transient Logger logger = LogManager.getLogger(OrgLogoLocalFileLoader.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);

	private OrgLogoLocalFileLoader()
	{
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("emptyPNGFile", imgFileLoader.getEmptyPNGFile())
				.toString();
	}

	// @Override
	public Optional<File> loadLogoForOrg(@NonNull final ResourceNameContext rsc)
	{
		final File logoFile = createAndGetLocalLogoFile(rsc);
		if (logoFile != null)
		{
			return Optional.of(logoFile);
		}
		else
		{
			logger.warn("Cannot find logo for {}, please add a logo file to the organization. Returning empty PNG file", this);
			return Optional.of(imgFileLoader.getEmptyPNGFile());
		}
	}

	private File createAndGetLocalLogoFile(@NonNull final ResourceNameContext rsc)
	{
		//
		// Retrieve the logo image
		final I_AD_Image logo = retrieveImage(rsc);
		if (logo == null || logo.getAD_Image_ID() <= 0)
		{
			return null;
		}

		//
		// Save the logo in a temporary file, to be locally available
		File logoFile = createTempLogoFile(logo);
		return logoFile;
	}

	private I_AD_Image retrieveImage(@NonNull final ResourceNameContext rsc)
	{
		final OrgId adOrgId = rsc.getOrgId();
		if (adOrgId.isAny())
		{
			return null;
		}

		if (rsc.isLogo())
		{
			return retrieveLogoImage(adOrgId);
		}
		else
		{
			return retrieveOtherImage(rsc);
		}
	}

	private I_AD_Image retrieveOtherImage(@NonNull final ResourceNameContext rsc)
	{
		final OrgId adOrgId = rsc.getOrgId();
		final String resourceName = rsc.getResourceName();

		final List<String> listResourceName = Splitter.on('/')
				.trimResults()
				.omitEmptyStrings()
				.splitToList(resourceName);

		if (listResourceName.size() > 0)
		{
			final String resource = listResourceName.get(listResourceName.size() - 1);
			final int indexOf = resource.indexOf(".");
			if (indexOf < 0)
			{
				return null;
			}

			final String columnName = resource.substring(0, indexOf);

			//
			// Get image
			final I_AD_OrgInfo orgInfo = orgsRepo.retrieveOrgInfoRecordOrNull(adOrgId, ITrx.TRXNAME_None);

			final Object imageIdObj = InterfaceWrapperHelper.getValue(orgInfo, columnName).orElse(null);
			if (imageIdObj == null)
			{
				return null;
			}

			final int imageIdObjInt = NumberUtils.asInt(imageIdObj, -1);
			if (imageIdObjInt > 0)
			{
				return MImage.get(Env.getCtx(), imageIdObjInt);
			}
		}

		return null;
	}

	private I_AD_Image retrieveLogoImage(@NonNull final OrgId adOrgId)
	{
		//
		// Get Logo from Org's BPartner
		// task FRESH-356: get logo also from org's bpartner if is set
		final Properties ctx = Env.getCtx();
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

		//
		// Get Org Logo
		final OrgInfo orgInfo = orgsRepo.getOrgInfoById(adOrgId);
		final int logoImageId = orgInfo.getLogoImageId();
		if (logoImageId > 0)
		{
			return MImage.get(ctx, logoImageId);
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

		return imgFileLoader.createTempPNGFile("logo", logoData);
	}
}
