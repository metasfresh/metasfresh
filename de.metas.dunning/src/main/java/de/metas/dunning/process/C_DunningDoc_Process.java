/**
 * 
 */
package de.metas.dunning.process;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.JavaProcess;

/**
 * Process to invoke {@link IDunningBL#processDunningDoc(IDunningContext, I_C_DunningDoc)} on a given {@link I_C_DunningDoc}.
 * 
 * This will probably replaced with actual docAction at a later stage.
 * 
 * @author ad
 * 
 */
public class C_DunningDoc_Process extends JavaProcess
{
	private int p_C_DunningDoc_ID = -1;
	@Override
	protected void prepare()
	{
		if (I_C_DunningDoc.Table_Name.equals(getTableName()))
		{
			p_C_DunningDoc_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt()
	{
		if (p_C_DunningDoc_ID <= 0)
		{
			throw new FillMandatoryException(I_C_DunningDoc.COLUMNNAME_C_DunningDoc_ID);
		}
		
		final IDunningBL dunningBL = Services.get(IDunningBL.class);
		final IDunningContext context = dunningBL.createDunningContext(getCtx(),
				null, // DunningDate, not needed
				null, // C_DunningLevel, not needed
				get_TrxName());
		
		
		final I_C_DunningDoc dunningDoc = InterfaceWrapperHelper.create(getCtx(), p_C_DunningDoc_ID, I_C_DunningDoc.class, get_TrxName());
		Services.get(IDunningBL.class).processDunningDoc(context, dunningDoc);
		
		return "OK";
	}

}
