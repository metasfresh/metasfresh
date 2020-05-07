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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.Callable;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.CCache;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.logging.LogManager;

/**
 * Logo hook: called by {@link JasperClassLoader} in order to intercept logo picture resources and provide the actual logo for current organization.
 * 
 * @author tsa
 *
 */
class OrgLogoClassLoaderHook
{
	public static final OrgLogoClassLoaderHook forAD_Org_ID(final int adOrgId)
	{
		return new OrgLogoClassLoaderHook(adOrgId);
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(OrgLogoClassLoaderHook.class);

	// Parameters
	private final int adOrgId;

	//
	// Org Logo resource matchers
	private static final String SYSCONFIG_ResourceNameEndsWith = "de.metas.adempiere.report.jasper.OrgLogoClassLoaderHook.ResourceNameEndsWith";
	private static final String DEFAULT_ResourceNameEndsWith = "de/metas/generics/logo.png";
	private final Set<String> resourceNameEndsWithMatchers;

	//
	// Caching (static)
	private static final CCache<Integer, Optional<File>> adOrgId2logoLocalFile = new CCache<Integer, Optional<File>>(I_AD_Image.Table_Name + "#LogoBy_AD_Org_ID", 10, 0)
			.addResetForTableName(I_AD_ClientInfo.Table_Name) // FRESH-327
			.addResetForTableName(I_AD_OrgInfo.Table_Name) // FRESH-327
			.addResetForTableName(I_C_BPartner.Table_Name) // for C_BPartner.Logo_ID FRESH-356
			;
	private final Callable<Optional<File>> orgLogoLocalFileLoader;

	private OrgLogoClassLoaderHook(final int adOrgId)
	{
		super();
		this.adOrgId = adOrgId;
		this.resourceNameEndsWithMatchers = buildResourceNameEndsWithMatchers();
		this.orgLogoLocalFileLoader = OrgLogoLocalFileLoader.forAD_Org_ID(adOrgId);
	}

	private static final Set<String> buildResourceNameEndsWithMatchers()
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

	public URL getResourceURLOrNull(final String resourceName)
	{
		// Skip if it's not about our hooked logo resources
		if (!isLogoResourceName(resourceName))
		{
			return null;
		}

		//
		// Get the local logo file
		final File logoFile = getLogoFile();
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
			logger.warn("Failed converting the local logo file to URL: " + logoFile, e);
		}
		return null;
	}

	private final File getLogoFile()
	{
		File logoFile = adOrgId2logoLocalFile.get(adOrgId, orgLogoLocalFileLoader).orNull();

		// If logo file does not exist or it's not readable, try recreating it
		if (logoFile != null && !logoFile.canRead())
		{
			adOrgId2logoLocalFile.put(adOrgId, null); // invalidate current cached record
			logoFile = adOrgId2logoLocalFile.get(adOrgId, orgLogoLocalFileLoader).orNull(); // get it again
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
