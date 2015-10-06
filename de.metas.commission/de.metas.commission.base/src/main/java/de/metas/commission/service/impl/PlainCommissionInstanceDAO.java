package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;

public class PlainCommissionInstanceDAO extends AbstractCommissionInstanceDAO
{
	@Override
	protected <T> T retrievePOCached(
			final Properties ctx,
			final int ad_Table_ID,
			final int record_ID,
			final Class<T> clazz,
			final String trxName)
	{
		return InterfaceWrapperHelper.create(POJOLookupMap.get().lookup(ad_Table_ID, record_ID), clazz);
	}

}
