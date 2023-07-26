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

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 * Org Image hook: called by {@link JasperClassLoader} in order to intercept org image resources and provide the actual image for current organization.
 *
 * @author tsa
 */
final class OrgImageClassLoaderHook
{
	public static OrgImageClassLoaderHook newInstance()
	{
		return new OrgImageClassLoaderHook();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(OrgImageClassLoaderHook.class);

	private static final CCache<OrgResourceNameContext, Optional<File>> imageLocalFileByOrgId = CCache.<OrgResourceNameContext, Optional<File>>builder()
			.initialCapacity(10)
			.tableName(I_AD_Image.Table_Name)
			.additionalTableNameToResetFor(I_AD_ClientInfo.Table_Name) // FRESH-327
			.additionalTableNameToResetFor(I_AD_OrgInfo.Table_Name) // FRESH-327
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name) // for C_BPartner.Logo_ID FRESH-356
			.build();

	private final OrgImageLocalFileLoader orgImageLocalFileLoader = new OrgImageLocalFileLoader();

	private OrgImageClassLoaderHook()
	{
	}

	@Nullable
	public URL getResourceURLOrNull(
			@NonNull final OrgId adOrgId,
			@Nullable final String resourceName)
	{
		if (resourceName == null)
		{
			return null;
		}
		
		final OrgResourceNameContext context = OrgResourceNameContext.builder()
				.orgId(adOrgId)
				.resourceName(resourceName)
				.build();

		if (!orgImageLocalFileLoader.isLogoOrImageResourceName(context))
		{
			return null;
		}
		
		//
		// Get the local image file
		final File imageFile = getLocalImageFile(context);
		if (imageFile == null)
		{
			return null;
		}

		//
		// Convert the image file to URL
		try
		{
			return imageFile.toURI().toURL();
		}
		catch (final MalformedURLException ex)
		{
			logger.warn("Failed converting the local image file to URL: {}", imageFile, ex);
			return null;
		}
	}

	
	@Nullable
	private File getLocalImageFile(@NonNull final OrgResourceNameContext context)
	{
		File imageFile = imageLocalFileByOrgId
				.getOrLoad(context, orgImageLocalFileLoader::getImageLocalFile)
				.orElse(null);

		// If image file does not exist or it's not readable, try recreating it
		if (imageFile != null && !imageFile.canRead())
		{
			imageLocalFileByOrgId.remove(context); // invalidate current cached record
			imageFile = imageLocalFileByOrgId
					.getOrLoad(context, orgImageLocalFileLoader::getImageLocalFile)
					.orElse(null);
		}

		return imageFile;
	}
}
