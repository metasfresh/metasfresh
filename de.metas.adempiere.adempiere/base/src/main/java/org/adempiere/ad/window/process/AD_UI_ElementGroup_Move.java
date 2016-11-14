package org.adempiere.ad.window.process;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.process.SvrProcess;

import de.metas.process.Param;

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

/**
 * Helper process to move an {@link I_AD_UI_ElementGroup} to another {@link I_AD_UI_Column}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_UI_ElementGroup_Move extends SvrProcess
{
	@Param(parameterName = I_AD_UI_Column.COLUMNNAME_AD_UI_Column_ID, mandatory = true)
	private int p_AD_UI_Column_ID;

	@Override
	protected String doIt() throws Exception
	{
		getResult().setRefreshAllAfterExecution(true);

		final I_AD_UI_ElementGroup uiElementGroup = getRecord(I_AD_UI_ElementGroup.class);
		final I_AD_UI_Column toUIColumn = InterfaceWrapperHelper.create(getCtx(), p_AD_UI_Column_ID, I_AD_UI_Column.class, ITrx.TRXNAME_ThreadInherited);

		Services.get(IADWindowDAO.class).moveElementGroup(uiElementGroup, toUIColumn);

		return MSG_OK;
	}
}
