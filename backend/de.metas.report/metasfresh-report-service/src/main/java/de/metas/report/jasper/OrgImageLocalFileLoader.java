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

import com.google.common.base.MoreObjects;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.image.AdImageId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MImage;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * Builds and returns the local organization logo {@link File}.
 *
 * @author tsa
 */
final class OrgImageLocalFileLoader
{
	private static final transient Logger logger = LogManager.getLogger(OrgImageLocalFileLoader.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);

	private final static ImageFileLoader imgFileLoader = ImageFileLoader.newInstance();
	private final OrgLogoResourceNameMatcher orgLogoResourceNameMatcher;

	public OrgImageLocalFileLoader()
	{
		this.orgLogoResourceNameMatcher = new OrgLogoResourceNameMatcher(sysConfigBL);
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
	public Optional<File> getImageLocalFile(@NonNull final OrgResourceNameContext context)
	{
		final File logoFile = createAndGetLocalImageFile(context);
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

	@Nullable
	private File createAndGetLocalImageFile(@NonNull final OrgResourceNameContext context)
	{
		//
		// Retrieve the image
		final I_AD_Image image = retrieveImage(context);
		if (image == null || image.getAD_Image_ID() <= 0)
		{
			return null;
		}

		//
		// Save the image in a temporary file, to be locally available
		return createTempImageFile(image);
	}
	
	public boolean isLogoOrImageResourceName(final OrgResourceNameContext context )
	{
		if (orgLogoResourceNameMatcher.matches(context.getResourceName()) 
				|| OrgImageResourceNameMatcher.instance.matches(context.getResourceName()))
		{
			return true;
		}

		// Fallback: not a logo/image resource
		return false;
	}
	

	@Nullable
	private I_AD_Image retrieveImage(@NonNull final OrgResourceNameContext context)
	{
		final OrgId adOrgId = context.getOrgId();
		if (adOrgId.isAny())
		{
			return null;
		}

		if (orgLogoResourceNameMatcher.matches(context.getResourceName()))
		{
			return retrieveLogoImage(adOrgId);
		}

		final String imageColumnName = OrgImageResourceNameMatcher.instance.getImageColumnName(context.getResourceName()).orElse(null);
		if (imageColumnName == null)
		{
			return null;
		}

		//
		// Get image
		final AdImageId imageId = orgsRepo.getOrgInfoById(adOrgId)
				.getImagesMap()
				.getImageId(imageColumnName)
				.orElse(null);
		if (imageId == null)
		{
			return null;
		}

		return MImage.get(Env.getCtx(), imageId.getRepoId());
	}

	@Nullable
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
		final AdImageId logoImageId = orgInfo.getImagesMap().getLogoId().orElse(null);
		if (logoImageId != null)
		{
			return MImage.get(ctx, logoImageId.getRepoId());
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

	@Nullable
	private static File createTempImageFile(final I_AD_Image image)
	{
		if (image == null)
		{
			return null;
		}

		final byte[] data = image.getBinaryData();
		if (data == null || data.length <= 0)
		{
			return null;
		}

		return imgFileLoader.createTempPNGFile("image", data);
	}
}
