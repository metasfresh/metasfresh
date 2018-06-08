package de.metas.edi.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.model.I_AD_InputDataSource;

/*
 * #%L
 * de.metas.edi
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

public class EDIInputDataSourceBL implements IEDIInputDataSourceBL
{
	@Override
	public boolean isEDIInputDataSource(final int inputDataSourceId)
	{
		if (inputDataSourceId <= 0)
		{
			// shall not happen
			return false;
		}
		
		final I_AD_InputDataSource ediInputDataSource = loadOutOfTrx(inputDataSourceId, I_AD_InputDataSource.class);
		return ediInputDataSource.isEdiEnabled();
	}
}
