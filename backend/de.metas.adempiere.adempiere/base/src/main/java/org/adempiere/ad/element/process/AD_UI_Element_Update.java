package org.adempiere.ad.element.process;

import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Element;

import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AD_UI_Element_Update extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Element element = getRecord(I_AD_Element.class);

		Services.get(IElementBL.class).updateUIElement(element);

		return MSG_OK;
	}

}
