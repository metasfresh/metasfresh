package de.metas.report.jasper.class_loader.images.org;

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

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.image.AdImageId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.report.jasper.class_loader.images.ImageUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

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
	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);

	private final OrgLogoResourceNameMatcher orgLogoResourceNameMatcher;

	public OrgImageLocalFileLoader()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		this.orgLogoResourceNameMatcher = new OrgLogoResourceNameMatcher(sysConfigBL);
	}

	public Optional<File> getImageLocalFile(@NonNull final OrgResourceNameContext context)
	{
		return retrieveImageId(context).flatMap(ImageUtils::createTempImageFile);
	}

	public boolean isLogoOrImageResourceName(final OrgResourceNameContext context)
	{
		return orgLogoResourceNameMatcher.matches(context.getResourceName())
				|| OrgImageResourceNameMatcher.instance.matches(context.getResourceName());
	}

	private Optional<AdImageId> retrieveImageId(@NonNull final OrgResourceNameContext context)
	{
		final OrgId adOrgId = context.getOrgId();
		if (adOrgId.isAny())
		{
			return Optional.empty();
		}

		if (orgLogoResourceNameMatcher.matches(context.getResourceName()))
		{
			return Optional.ofNullable(retrieveLogoImageId(adOrgId));
		}

		final String imageColumnName = OrgImageResourceNameMatcher.instance.getImageColumnName(context.getResourceName()).orElse(null);
		if (imageColumnName == null)
		{
			return Optional.empty();
		}

		return orgsRepo.getOrgInfoById(adOrgId)
				.getImagesMap()
				.getImageId(imageColumnName);
	}

	@Nullable
	private AdImageId retrieveLogoImageId(@NonNull final OrgId adOrgId)
	{
		//
		// Get Logo from Organization's BPartner
		// task FRESH-356: get logo also from organization's bpartner if is set
		final Properties ctx = Env.getCtx();
		final I_AD_Org org = orgsRepo.getById(adOrgId);
		if (org != null)
		{
			final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);
			if (orgBPartner != null)
			{
				final AdImageId orgBPartnerLogoId = AdImageId.ofRepoIdOrNull(orgBPartner.getLogo_ID());
				if (orgBPartnerLogoId != null)
				{
					return orgBPartnerLogoId;
				}
			}
		}

		//
		// Get Org Logo
		final OrgInfo orgInfo = orgsRepo.getOrgInfoById(adOrgId);
		final AdImageId logoImageId = orgInfo.getImagesMap().getLogoId().orElse(null);
		if (logoImageId != null)
		{
			return logoImageId;
		}

		//
		// Get Tenant level Logo
		final ClientId adClientId = orgInfo.getClientId();
		final I_AD_ClientInfo clientInfo = clientsRepo.retrieveClientInfo(ctx, adClientId.getRepoId());
		AdImageId clientLogoId = AdImageId.ofRepoIdOrNull(clientInfo.getLogoReport_ID());
		if (clientLogoId == null)
		{
			clientLogoId = AdImageId.ofRepoIdOrNull(clientInfo.getLogo_ID());
		}
		return clientLogoId;

	}
}
