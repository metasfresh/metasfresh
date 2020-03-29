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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Logo hook: called by {@link JasperClassLoader} in order to intercept logo picture resources and provide the actual logo for current organization.
 * 
 * @author tsa
 *
 */
final class OrgLogoClassLoaderHook
{
	public static OrgLogoClassLoaderHook newInstance()
	{
		return new OrgLogoClassLoaderHook();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(OrgLogoClassLoaderHook.class);

	//
	// Org Logo resource matchers
	private static final String SYSCONFIG_ResourceNameEndsWith = "de.metas.adempiere.report.jasper.OrgLogoClassLoaderHook.ResourceNameEndsWith";
	private static final String DEFAULT_ResourceNameEndsWith = "de/metas/generics/logo.png";
	private final ImmutableSet<String> resourceNameEndsWithMatchers;

	//
	// Caching (static)
	private static final CCache<OrgId, Optional<File>> adOrgId2logoLocalFile = CCache.<OrgId, Optional<File>> builder()
			.cacheName(I_AD_Image.Table_Name + "#LogoBy_AD_Org_ID")
			.tableName(I_AD_Image.Table_Name)
			.initialCapacity(10)
			.additionalTableNameToResetFor(I_AD_ClientInfo.Table_Name) // FRESH-327
			.additionalTableNameToResetFor(I_AD_OrgInfo.Table_Name) // FRESH-327
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name) // for C_BPartner.Logo_ID FRESH-356
			.build();

	private final OrgLogoLocalFileLoader orgLogoLocalFileLoader;

	private OrgLogoClassLoaderHook()
	{
		this.resourceNameEndsWithMatchers = buildResourceNameEndsWithMatchers();
		this.orgLogoLocalFileLoader = OrgLogoLocalFileLoader.newInstance();
	}

	private static ImmutableSet<String> buildResourceNameEndsWithMatchers()
	{
		final String resourceNameEndsWithStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ResourceNameEndsWith, DEFAULT_ResourceNameEndsWith);
		if (Check.isEmpty(resourceNameEndsWithStr, true))
		{
			return ImmutableSet.of();
		}

		return ImmutableSet.copyOf(Splitter.on(';')
				.trimResults()
				.omitEmptyStrings()
				.splitToList(resourceNameEndsWithStr));
	}

	public URL getResourceURLOrNull(
			@NonNull final OrgId adOrgId,
			@Nullable final String resourceName)
	{
		// Skip if it's not about our hooked logo resources
		if (!isLogoResourceName(resourceName))
		{
			return null;
		}

		//
		// Get the local logo file
		final File logoFile = getLogoFile(adOrgId);
		if (logoFile == null)
		{
			return null;
		}

		//
		// Convert the logo file to URL
		try
		{
			return logoFile.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			logger.warn("Failed converting the local logo file to URL: {}", logoFile, e);
		}
		return null;
	}

	private File getLogoFile(@NonNull final OrgId adOrgId)
	{
		File logoFile = adOrgId2logoLocalFile
				.getOrLoad(adOrgId, orgLogoLocalFileLoader::loadLogoForOrg)
				.orElse(null);

		// If logo file does not exist or it's not readable, try recreating it
		if (logoFile != null && !logoFile.canRead())
		{
			adOrgId2logoLocalFile.put(adOrgId, null); // invalidate current cached record
			logoFile = adOrgId2logoLocalFile
					.getOrLoad(adOrgId, orgLogoLocalFileLoader::loadLogoForOrg)
					.orElse(null);
		}

		return logoFile;
	}

	/**
	 * @return true if given resourceName is a logo image
	 */
	private boolean isLogoResourceName(final String resourceName)
	{
		// Skip if no resourceName
		if (resourceName == null || resourceName.isEmpty())
		{
			return false;
		}

		// Check if our resource name ends with one of our predefined matchers
		for (final String resourceNameEndsWithMatcher : resourceNameEndsWithMatchers)
		{
			if (resourceName.endsWith(resourceNameEndsWithMatcher))
			{
				return true;
			}
		}

		// Fallback: not a logo resource
		return false;
	}
}
