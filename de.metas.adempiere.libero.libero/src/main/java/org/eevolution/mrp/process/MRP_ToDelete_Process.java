package org.eevolution.mrp.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.eevolution.mrp.api.IMRPDocumentDeleteService;

/**
 * Delete all documents which were flagged to be deleted
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08470_speed_up_MRP_cleanup_%28100715712605%29
 */
public class MRP_ToDelete_Process extends SvrProcess
{
	private int p_DeleteLimit = -1;
	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			
			final String parameterName = para.getParameterName();
			if ("DeleteLimit".equals(parameterName))
			{
				p_DeleteLimit = para.getParameterAsInt();
			}
		}
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final IMRPDocumentDeleteService mrpDocumentsDeleteService = Services.newMultiton(IMRPDocumentDeleteService.class)
				.setInterruptible(true)
				.setMaxPendingDocumentsToDelete(p_DeleteLimit);

		final int countDeleted = mrpDocumentsDeleteService.deletePending();
		return "@Deleted@ #" + countDeleted;
	}

}
