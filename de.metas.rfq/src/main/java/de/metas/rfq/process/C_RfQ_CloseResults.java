package de.metas.rfq.process;

import org.adempiere.util.Services;

import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Close {@link I_C_RfQResponse}s of given {@link I_C_RfQ}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_RfQ_CloseResults extends JavaProcess implements IProcessPrecondition
{
	// services
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_C_RfQ rfq = context.getModel(I_C_RfQ.class);
		return rfqBL.isClosed(rfq);
	}

	@Override
	protected String doIt()
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			if (!rfqBL.isCompleted(rfqResponse))
			{
				addLog("@Warning@ @NotClosed@: {}", rfqBL.getSummary(rfqResponse));
			}

			rfqBL.close(rfqResponse);
		}

		return MSG_OK;
	}

}
