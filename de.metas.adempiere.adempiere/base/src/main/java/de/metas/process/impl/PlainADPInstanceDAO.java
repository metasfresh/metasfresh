package de.metas.process.impl;

import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_AD_PInstance;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PlainADPInstanceDAO extends ADPInstanceDAO
{
	@Override
	public int createAD_PInstance_ID(final Properties ctx)
	{
		return POJOLookupMap.get().nextId(I_AD_PInstance.Table_Name);
	}
}
