package org.adempiere.ad.window.process;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementGroup;

import de.metas.process.Param;
import de.metas.process.JavaProcess;

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
 * Helper process to move an {@link I_AD_UI_Element} to another {@link I_AD_UI_ElementGroup}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AD_UI_Element_Move extends JavaProcess
{
	@Param(parameterName = I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_ElementGroup_ID, mandatory = true)
	private int p_AD_UI_ElementGroup_ID;

	@Override
	protected String doIt() throws Exception
	{
		getResult().setRefreshAllAfterExecution(true);

		final I_AD_UI_Element uiElement = getRecord(I_AD_UI_Element.class);
		final I_AD_UI_ElementGroup toUIElementGroup = InterfaceWrapperHelper.create(getCtx(), p_AD_UI_ElementGroup_ID, I_AD_UI_ElementGroup.class, ITrx.TRXNAME_ThreadInherited);

		Services.get(IADWindowDAO.class).moveElement(uiElement, toUIElementGroup);

		return MSG_OK;
	}
}
