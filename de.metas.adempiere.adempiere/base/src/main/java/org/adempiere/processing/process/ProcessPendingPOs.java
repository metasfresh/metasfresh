package  org.adempiere.processing.process;

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


import java.math.BigDecimal;

import org.adempiere.processing.model.MADProcessablePO;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class ProcessPendingPOs extends SvrProcess
{

	private int adTableId;

	@Override
	protected String doIt() throws Exception
	{
		final IProcessingService processingService = Services.get(IProcessingService.class);
		
		int count = 0;
		MADProcessablePO processablePOPointer = MADProcessablePO.retrieveOldest(getCtx(), adTableId, -1, get_TrxName());

		while (processablePOPointer != null)
		{
			final int nextAdProcessablePoId = processablePOPointer.getAD_ProcessablePO_ID() + 1;
			processingService.process(processablePOPointer, this);
			count++;
			
			processablePOPointer = MADProcessablePO.retrieveOldest(getCtx(), adTableId, nextAdProcessablePoId, get_TrxName());
		}
		return "@Success@: " + count + " @Processed@ ";
	}

	

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;

			else if (name.equals(MADProcessablePO.COLUMNNAME_AD_Table_ID))
			{
				adTableId = ((BigDecimal)para[i].getParameter()).intValue();
			}
		}

	}

}
