package de.metas.material.dispo.commons.repository;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery.AvailableToPromiseQueryBuilder;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public final class AvailableToPromiseQueryBL
{

	private static final String SYSCONFIG_ATP_ATTRIBUTES_KEYS = //
			"de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.AttributesKeys";

	public static void addStorageAttributeKeysToQueryBuilder(@NonNull final AvailableToPromiseQueryBuilder stockQueryBuilder)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final String storageAttributesKeys = sysConfigBL.getValue(
				SYSCONFIG_ATP_ATTRIBUTES_KEYS,
				AttributesKey.ALL.getAsString(),
				clientId, orgId);

		final Splitter splitter = Splitter
				.on(",")
				.trimResults(CharMatcher.whitespace())
				.omitEmptyStrings();
		for (final String storageAttributesKey : splitter.splitToList(storageAttributesKeys))
		{
			if ("<ALL_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
			{
				stockQueryBuilder.storageAttributesKey(AttributesKey.ALL);
			}
			else if ("<OTHER_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
			{
				stockQueryBuilder.storageAttributesKey(AttributesKey.OTHER);
			}
			else
			{
				stockQueryBuilder.storageAttributesKey(AttributesKey.ofString(storageAttributesKey));
			}
		}
	}

}
