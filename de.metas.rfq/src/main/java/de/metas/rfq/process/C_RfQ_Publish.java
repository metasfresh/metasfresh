package de.metas.rfq.process;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.process.SvrProcess;

import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfQResponsePublisher;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.RfQResponsePublisherRequest;
import de.metas.rfq.RfQResponsePublisherRequest.PublishingType;
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
 * Publish RfQ bidding invitations.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class C_RfQ_Publish extends SvrProcess implements ISvrProcessPrecondition
{
	// services
	private final transient IRfQConfiguration rfqConfiguration = Services.get(IRfQConfiguration.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		final I_C_RfQ rfq = InterfaceWrapperHelper.create(gridTab, I_C_RfQ.class);
		return rfqBL.isCompleted(rfq);
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);
		rfqBL.assertComplete(rfq);

		final IRfQResponsePublisher rfqPublisher = rfqConfiguration.getRfQResponsePublisher();

		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			if (!rfqBL.isDraft(rfqResponse))
			{
				continue;
			}

			rfqPublisher.publish(RfQResponsePublisherRequest.of(rfqResponse, PublishingType.Invitation));
		}

		return MSG_OK;
	}
}
