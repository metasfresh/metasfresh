package org.adempiere.mm.attributes.listeners.adr;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Arrays;
import java.util.List;

import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.inout.IInOutDAO;
import de.metas.util.Services;

public class InOutADRModelAttributeSetInstanceListener implements IModelAttributeSetInstanceListener
{
	private final InOutLineADRModelAttributeSetInstanceListener inoutLineListener = new InOutLineADRModelAttributeSetInstanceListener();

	private static final List<String> sourceColumnNames = Arrays.asList(I_M_InOut.COLUMNNAME_C_BPartner_ID);

	@Override
	public String getSourceTableName()
	{
		return I_M_InOut.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return sourceColumnNames;
	}

	@Override
	public void modelChanged(final Object model)
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(model, I_M_InOut.class);
		for (final I_M_InOutLine line : Services.get(IInOutDAO.class).retrieveLines(inout))
		{
			inoutLineListener.modelChanged(line);
		}
	}
}
