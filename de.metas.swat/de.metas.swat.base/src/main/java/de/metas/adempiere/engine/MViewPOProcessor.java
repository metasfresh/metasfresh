package de.metas.adempiere.engine;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.model.POWrapper;
import org.adempiere.util.Services;
import org.compiere.model.PO;

import de.metas.adempiere.model.I_AD_Table_MView;
import de.metas.adempiere.service.ITableMViewBL;
import de.metas.adempiere.service.ITableMViewBL.RefreshMode;
import de.metas.processor.spi.IPOProcessor;

public class MViewPOProcessor implements IPOProcessor
{

	@Override
	public Class<?> getTrxClass()
	{
		return I_AD_Table_MView.class;
	}

	@Override
	public boolean process(PO trx)
	{
		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);

		final I_AD_Table_MView mview = POWrapper.create(trx, I_AD_Table_MView.class);

		if (mview.isStaled())
		{
			mviewBL.refreshEx(mview, (PO)null, RefreshMode.Complete, trx.get_TrxName());
		}

		return true;
	}

}
