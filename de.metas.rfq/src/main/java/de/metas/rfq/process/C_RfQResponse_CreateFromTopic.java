package de.metas.rfq.process;

import java.util.List;

import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.process.Param;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.IRfqTopicDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQ_Topic;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Create {@link I_C_RfQResponse}s from {@link I_C_RfQ}'s topic.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class C_RfQResponse_CreateFromTopic extends SvrProcess
{
	// services
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqTopicDAO rfqTopicDAO = Services.get(IRfqTopicDAO.class);

	/** Send RfQ */
	@Param(parameterName = "IsSendRfQ")
	private final boolean p_IsSendRfQ = false;

	/**
	 * Perform process.
	 *
	 * @return Message (translated text)
	 * @throws Exception if not successful
	 */
	@Override
	protected String doIt()
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		rfqBL.checkQuoteTotalAmtOnly(rfq);

		int counter = 0;
		int sent = 0;
		int notSent = 0;

		// Get all existing responses
		final List<I_C_RfQResponse> responses = rfqDAO.retrieveResponses(rfq, false, false);

		// Topic
		final I_C_RfQ_Topic topic = rfq.getC_RfQ_Topic();
		for (final I_C_RfQ_TopicSubscriber subscriber : rfqTopicDAO.retrieveSubscribers(topic))
		{

			//
			// Skip existing responses
			boolean skip = false;
			for (final I_C_RfQResponse response : responses)
			{
				if (subscriber.getC_BPartner_ID() == response.getC_BPartner_ID()
						&& subscriber.getC_BPartner_Location_ID() == response.getC_BPartner_Location_ID())
				{
					skip = true;
					break;
				}
			}
			if (skip)
			{
				continue;
			}

			// Create Response
			final I_C_RfQResponse response = rfqBL.createRfqResponse(rfq, subscriber);
			if (response == null)   	// no lines
			{
				continue;
			}

			counter++;
			if (p_IsSendRfQ)
			{
				if (rfqBL.sendRfQ(response))
				{
					sent++;
				}
				else
				{
					notSent++;
				}
			}
		}   	// for all subscribers

		String retValue = "@Created@ " + counter;
		if (p_IsSendRfQ)
		{
			retValue += " - @IsSendRfQ@=" + sent + " - @Error@=" + notSent;
		}
		return retValue;
	}
}
