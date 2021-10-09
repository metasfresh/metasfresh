/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.impl;

import de.metas.elasticsearch.IESSystem;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESSystem implements IESSystem
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	// IMPORTANT: fetch it only when needed and when this feature is enabled!!!
	// else it might start the elasticsearch client when the elasticsearch server does not even exists,
	// which will flood the console with errors
	private RestHighLevelClient elasticsearchClient = null;

	public static final String SYSCONFIG_elastic_enable = "elastic_enable";
	private static final BooleanWithReason DISABLED_BECAUSE_JUNIT_MODE = BooleanWithReason.falseBecause("Elasticsearch disabled when running in JUnit test mode");
	private static final BooleanWithReason DISABLED_BECAUSE_SYSCONFIG = BooleanWithReason.falseBecause("Elasticsearch disabled by sysconfig `" + SYSCONFIG_elastic_enable + "`");

	@Override
	public BooleanWithReason getEnabled()
	{
		if (Adempiere.isUnitTestMode())
		{
			return DISABLED_BECAUSE_JUNIT_MODE;
		}

		// Check if it was disabled by sysconfig
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_elastic_enable, true))
		{
			return DISABLED_BECAUSE_SYSCONFIG;
		}

		return BooleanWithReason.TRUE;
	}

	private void assertEnabled()
	{
		final BooleanWithReason enabled = getEnabled();
		if (enabled.isFalse())
		{
			throw new AdempiereException("Expected elasticsearch feature to be enabled but is disabled because `" + enabled.getReasonAsString() + "`");
		}
	}

	@Override
	public RestHighLevelClient elasticsearchClient()
	{
		assertEnabled();

		RestHighLevelClient elasticsearchClient = this.elasticsearchClient;
		if (elasticsearchClient == null)
		{
			elasticsearchClient = this.elasticsearchClient = SpringContextHolder.instance.getBean(RestHighLevelClient.class);
		}

		return elasticsearchClient;
	}
}
