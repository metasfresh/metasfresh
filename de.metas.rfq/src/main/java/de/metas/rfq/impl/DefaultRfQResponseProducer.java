package de.metas.rfq.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.rfq.IRfQConfiguration;
import de.metas.rfq.IRfQResponseProducer;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.IRfqTopicBL;
import de.metas.rfq.IRfqTopicDAO;
import de.metas.rfq.RfQResponsePublisherRequest;
import de.metas.rfq.RfQResponsePublisherRequest.PublishingType;
import de.metas.rfq.event.IRfQEventDispacher;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.model.I_C_RfQ_Topic;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;
import de.metas.rfq.model.X_C_RfQResponse;

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
 * Default {@link IRfQResponse} producer.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DefaultRfQResponseProducer implements IRfQResponseProducer
{
	// services
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IRfQConfiguration rfqConfiguration = Services.get(IRfQConfiguration.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqTopicDAO rfqTopicDAO = Services.get(IRfqTopicDAO.class);
	private final transient IRfqTopicBL rfqTopicBL = Services.get(IRfqTopicBL.class);
	private final transient IRfQEventDispacher rfqEventDispacher = Services.get(IRfQEventDispacher.class);

	// Status
	private AtomicBoolean _processed = new AtomicBoolean(false);
	private I_C_RfQ _rfq;
	private boolean _publish;
	private List<I_C_RfQResponse> _generatedResponses = new ArrayList<>();
	private AtomicInteger _countPublished = new AtomicInteger(0);

	@Override
	public List<I_C_RfQResponse> create()
	{
		markAsProcessed();

		final I_C_RfQ rfq = getC_RfQ();
		rfqBL.assertComplete(rfq);

		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				createInTrx();
			}
		});

		return getGeneratedResponses();
	}

	private void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "not already processed");
	}

	private void markAsProcessed()
	{
		_processed.getAndSet(true);
	}

	@Override
	public IRfQResponseProducer setC_RfQ(I_C_RfQ rfq)
	{
		assertNotProcessed();
		this._rfq = rfq;
		return this;
	}

	protected final I_C_RfQ getC_RfQ()
	{
		Check.assumeNotNull(_rfq, "RfQ not null");
		return _rfq;
	}

	@Override
	public IRfQResponseProducer setPublish(boolean publish)
	{
		assertNotProcessed();
		this._publish = publish;
		return this;
	}

	private boolean isPublish()
	{
		return _publish;
	}

	private void addGeneratedResponse(final I_C_RfQResponse rfqResponse)
	{
		Check.assumeNotNull(rfqResponse, "rfqResponse not null");

		publishIfNeeded(rfqResponse);

		_generatedResponses.add(rfqResponse);
	}

	private void publishIfNeeded(final I_C_RfQResponse rfqResponse)
	{
		if (!isPublish())
		{
			return;
		}

		try
		{
			rfqConfiguration
					.getRfQResponsePublisher()
					.publish(RfQResponsePublisherRequest.of(rfqResponse, PublishingType.Invitation));
			
			_countPublished.incrementAndGet();
		}
		catch (Exception e)
		{
			logger.warn("Failed sending {} to vendor", rfqResponse, e);
		}
	}

	private List<I_C_RfQResponse> getGeneratedResponses()
	{
		return ImmutableList.copyOf(_generatedResponses);
	}

	@Override
	public int getCountPublished()
	{
		return _countPublished.get();
	}

	private void createInTrx()
	{
		final I_C_RfQ rfq = getC_RfQ();
		final List<I_C_RfQResponse> rfqResponsesExisting = rfqDAO.retrieveAllResponses(rfq);

		// Topic
		final I_C_RfQ_Topic topic = rfq.getC_RfQ_Topic();
		for (final I_C_RfQ_TopicSubscriber subscriber : rfqTopicDAO.retrieveSubscribers(topic))
		{
			// Skip existing responses
			if (hasResponseForTopicSubscriber(rfqResponsesExisting, subscriber))
			{
				continue;
			}

			// Create Response
			final I_C_RfQResponse rfqResponse = createRfqResponseRecursivelly(rfq, subscriber);
			if (rfqResponse == null)
			{
				continue;
			}

			rfqEventDispacher.fireDraftCreated(rfqResponse);

			addGeneratedResponse(rfqResponse);
		}
	}

	private boolean hasResponseForTopicSubscriber(final List<I_C_RfQResponse> rfqResponses, final I_C_RfQ_TopicSubscriber subscriber)
	{
		if (rfqResponses.isEmpty())
		{
			return false;
		}

		for (final I_C_RfQResponse response : rfqResponses)
		{
			if (isResponseMatchingSubscriber(response, subscriber))
			{
				return true;
			}
		}

		return false;
	}

	private boolean isResponseMatchingSubscriber(final I_C_RfQResponse response, final I_C_RfQ_TopicSubscriber subscriber)
	{
		return subscriber.getC_BPartner_ID() == response.getC_BPartner_ID()
				&& subscriber.getC_BPartner_Location_ID() == response.getC_BPartner_Location_ID();
	}

	protected boolean isGenerateForLine(final I_C_RfQLine rfqLine, final I_C_RfQ_TopicSubscriber subscriber)
	{
		if (!rfqLine.isActive())
		{
			return false;
		}

		//
		// Product on "Only" list
		if (subscriber != null && !rfqTopicBL.isProductIncluded(subscriber, rfqLine.getM_Product_ID()))
		{
			return false;
		}

		return true;
	}

	protected boolean isGenerateForLineQty(final I_C_RfQLineQty lineQty)
	{
		if (!lineQty.isActive())
		{
			return false;
		}

		if (!lineQty.isRfQQty())
		{
			return false;
		}

		return true;
	}

	private I_C_RfQResponse createRfqResponseRecursivelly(final I_C_RfQ rfq, final I_C_RfQ_TopicSubscriber subscriber)
	{
		final ExtendedMemorizingSupplier<I_C_RfQResponse> responseSupplier = createRfqResponseSupplier(rfq, subscriber);

		//
		// Create Lines
		for (final I_C_RfQLine rfqLine : rfqDAO.retrieveLines(rfq))
		{
			if (!isGenerateForLine(rfqLine, subscriber))
			{
				continue;
			}

			final Supplier<I_C_RfQResponseLine> responseLineSupplier = createRfqResponseLineSupplier(responseSupplier, rfqLine);

			//
			// Create line Qty records
			if (rfqLine.isUseLineQty())
			{
				for (final I_C_RfQLineQty lineQty : rfqDAO.retrieveLineQtys(rfqLine))
				{
					if (!isGenerateForLineQty(lineQty))
					{
						continue;
					}

					// Create RfQ Response Line Qty
					createRfQResponseLineQty(responseLineSupplier, lineQty);
				}
			}
			else
			{
				responseLineSupplier.get(); // actually creates the line as it is
			}
		}

		return responseSupplier.peek();
	}

	private ExtendedMemorizingSupplier<I_C_RfQResponse> createRfqResponseSupplier(final I_C_RfQ rfq, final I_C_RfQ_TopicSubscriber subscriber)
	{
		return ExtendedMemorizingSupplier.of(new Supplier<I_C_RfQResponse>()
		{
			@Override
			public I_C_RfQResponse get()
			{
				final I_C_RfQResponse rfqResponse = createRfqResponse(rfq, subscriber);
				if (rfqResponse != null)
				{
					InterfaceWrapperHelper.save(rfqResponse);
				}
				return rfqResponse;
			}
		});
	}

	protected I_C_RfQResponse createRfqResponse(final I_C_RfQ rfq, final I_C_RfQ_TopicSubscriber subscriber)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(rfq);
		final I_C_RfQResponse response = InterfaceWrapperHelper.create(ctx, I_C_RfQResponse.class, ITrx.TRXNAME_ThreadInherited);

		// Defaults
		response.setIsSelectedWinner(false);
		response.setIsSelfService(false);
		response.setPrice(BigDecimal.ZERO);
		response.setDocStatus(X_C_RfQResponse.DOCSTATUS_Drafted);
		response.setProcessed(false);
		response.setProcessing(false);

		// From RfQ
		response.setAD_Org_ID(rfq.getAD_Org_ID());
		response.setC_RfQ(rfq);
		response.setC_Currency_ID(rfq.getC_Currency_ID());
		response.setName(rfq.getName());

		// Subscriber info
		response.setC_BPartner_ID(subscriber.getC_BPartner_ID());
		response.setC_BPartner_Location_ID(subscriber.getC_BPartner_Location_ID());
		response.setAD_User_ID(subscriber.getAD_User_ID());

		// NOTE: don't save it

		return response;
	}

	private ExtendedMemorizingSupplier<I_C_RfQResponseLine> createRfqResponseLineSupplier(final Supplier<I_C_RfQResponse> response, final I_C_RfQLine rfqLine)
	{
		return ExtendedMemorizingSupplier.of(new Supplier<I_C_RfQResponseLine>()
		{
			@Override
			public I_C_RfQResponseLine get()
			{
				final I_C_RfQResponseLine rfqResponseLine = createRfqResponseLine(response, rfqLine);
				if (rfqResponseLine != null)
				{
					InterfaceWrapperHelper.save(rfqResponseLine);
				}
				return rfqResponseLine;
			}
		});
	}

	protected I_C_RfQResponseLine createRfqResponseLine(final Supplier<I_C_RfQResponse> responseSupplier, final I_C_RfQLine rfqLine)
	{
		final I_C_RfQResponse rfqResponse = responseSupplier.get();

		final Properties ctx = InterfaceWrapperHelper.getCtx(rfqResponse);
		final I_C_RfQResponseLine rfqResponseLine = InterfaceWrapperHelper.create(ctx, I_C_RfQResponseLine.class, ITrx.TRXNAME_ThreadInherited);

		//
		// Defaults
		rfqResponseLine.setIsSelectedWinner(false);
		rfqResponseLine.setIsSelfService(false);
		rfqResponseLine.setDocStatus(rfqResponse.getDocStatus());
		rfqResponseLine.setProcessed(rfqResponse.isProcessed());

		//
		// From RfQ Response header
		rfqResponseLine.setC_RfQResponse(rfqResponse);
		rfqResponseLine.setAD_Org_ID(rfqResponse.getAD_Org_ID());
		rfqResponseLine.setC_BPartner_ID(rfqResponse.getC_BPartner_ID());
		rfqResponseLine.setC_Currency_ID(rfqResponse.getC_Currency_ID());

		//
		// From RfQ header
		final I_C_RfQ rfq = rfqLine.getC_RfQ();
		rfqResponseLine.setC_RfQ(rfq);
		rfqResponseLine.setDateResponse(rfq.getDateResponse());
		rfqResponseLine.setDateWorkStart(rfq.getDateWorkStart());
		rfqResponseLine.setDeliveryDays(rfq.getDeliveryDays());

		//
		// From RfQ Line
		rfqResponseLine.setC_RfQLine(rfqLine);
		rfqResponseLine.setLine(rfqLine.getLine());
		rfqResponseLine.setM_Product_ID(rfqLine.getM_Product_ID());
		rfqResponseLine.setC_UOM_ID(rfqLine.getC_UOM_ID());
		rfqResponseLine.setQtyRequiered(rfqLine.getQty());
		rfqResponseLine.setDescription(rfqLine.getDescription());
		rfqResponseLine.setUseLineQty(rfqLine.isUseLineQty());

		// NOTE: don't save it!

		return rfqResponseLine;
	}

	private I_C_RfQResponseLineQty createRfQResponseLineQty(final Supplier<I_C_RfQResponseLine> responseLineSupplier, final I_C_RfQLineQty lineQty)
	{
		final I_C_RfQResponseLine responseLine = responseLineSupplier.get();

		final Properties ctx = InterfaceWrapperHelper.getCtx(responseLine);
		final I_C_RfQResponseLineQty responseQty = InterfaceWrapperHelper.create(ctx, I_C_RfQResponseLineQty.class, ITrx.TRXNAME_ThreadInherited);
		responseQty.setAD_Org_ID(responseLine.getAD_Org_ID());
		responseQty.setC_RfQResponseLine(responseLine);
		responseQty.setC_RfQLineQty(lineQty);

		InterfaceWrapperHelper.save(responseQty);
		return responseQty;
	}
}
