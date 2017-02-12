package de.metas.rfq.process;

import java.util.List;

import org.adempiere.util.Services;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfQResponseProducer;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.business
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
 * Create {@link I_C_RfQResponse}s from {@link I_C_RfQ}'s topic.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_RfQ_CreateResponses extends JavaProcess implements IProcessPrecondition
{
	// services
	private final transient IRfQConfiguration rfqConfiguration = Services.get(IRfQConfiguration.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);

	/** Send RfQ */
	@Param(parameterName = "IsSendRfQ")
	private boolean p_IsSendRfQ;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final I_C_RfQ rfq = context.getSelectedModel(I_C_RfQ.class);
		return ProcessPreconditionsResolution.acceptIf(rfqBL.isCompleted(rfq));
	}

	@Override
	protected String doIt()
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		//
		// Generate RfQ responses
		final IRfQResponseProducer producer = rfqConfiguration.newRfQResponsesProducerFor(rfq);
		final List<I_C_RfQResponse> rfqResponses = producer
				.setC_RfQ(rfq)
				.setPublish(p_IsSendRfQ)
				.create();

		return "@Created@ " + rfqResponses.size()
				+ ", @IsSendRfQ@=" + producer.getCountPublished();
	}
}
