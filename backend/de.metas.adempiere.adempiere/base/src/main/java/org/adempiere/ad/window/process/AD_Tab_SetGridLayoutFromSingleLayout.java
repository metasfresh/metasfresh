package org.adempiere.ad.window.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;

import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;

import de.metas.process.JavaProcess;

/**
 * Copy Single Layout settings (one row layout) to grid layout.
 * 
 * After running this process, same fields that are displayed on single layout will be displayed in grid layout, in the same order.
 * 
 * @author tsa
 *
 */
public class AD_Tab_SetGridLayoutFromSingleLayout extends JavaProcess
{

	private int p_AD_Tab_ID = -1;

	@Override
	protected void prepare()
	{
		if (I_AD_Tab.Table_Name.equals(getTableName()))
		{
			p_AD_Tab_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Tab_ID <= 0)
		{
			throw new FillMandatoryException(I_AD_Tab.COLUMNNAME_AD_Tab_ID);
		}

		final I_AD_Tab adTab = InterfaceWrapperHelper.create(getCtx(), p_AD_Tab_ID, I_AD_Tab.class, getTrxName());

		copySingleLayoutToGridLayout(adTab);

		return MSG_OK;
	}

	private void copySingleLayoutToGridLayout(final I_AD_Tab adTab)
	{
		final List<I_AD_Field> adFields = Services.get(IADFieldDAO.class).retrieveFields(adTab);
		for (final I_AD_Field adField : adFields)
		{
			copySingleLayoutToGridLayout(adField);
			InterfaceWrapperHelper.save(adField);
		}
	}

	private void copySingleLayoutToGridLayout(final I_AD_Field adField)
	{
		adField.setIsDisplayedGrid(adField.isDisplayed());
		adField.setSeqNoGrid(adField.getSeqNo());
	}

}
