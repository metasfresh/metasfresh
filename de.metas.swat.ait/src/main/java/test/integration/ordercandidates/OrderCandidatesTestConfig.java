package test.integration.ordercandidates;

/*
 * #%L
 * de.metas.swat.ait
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


import java.util.Properties;

import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.TestConfig;

public class OrderCandidatesTestConfig extends TestConfig
{
	private static final String AD_INPUT_DATA_SOURCE_INTERNAL_NAME = "AD_InputDataSource_InternalName";

	public OrderCandidatesTestConfig(final TestConfig parent)
	{
		super(parent);

		final Properties props = parent.getTestProperties();

		// make sure the parameters are set, but don't override them in case they were already set in 'parent'
		setCustomParamIfEmpty(AD_INPUT_DATA_SOURCE_INTERNAL_NAME,
				Helper.parseName(props.getProperty(AD_INPUT_DATA_SOURCE_INTERNAL_NAME)));
	}

	public String getAD_InputDataSource_InternalName()
	{
		return getCustomParamStr(AD_INPUT_DATA_SOURCE_INTERNAL_NAME);
	}

	@Override
	public String toString()
	{
		return super.toString()
				+ "\nOrderCandidatesTestConfig ["
				+ ", " + AD_INPUT_DATA_SOURCE_INTERNAL_NAME + "=" + getAD_InputDataSource_InternalName()
				+ "]";
	}
}
