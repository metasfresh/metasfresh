package de.metas.procurement.webui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.MFSession;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.service.ISendService;
import de.metas.procurement.webui.ui.model.ProductQtyReport;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;
import de.metas.procurement.webui.ui.model.RfqHeader;

/*
 * #%L
 * metasfresh-procurement-webui
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

public class SendService implements ISendService
{
	private final ObjectProperty<Integer> notSentCounterProperty = new ObjectProperty<>(0);

	@Autowired
	@Lazy
	private IRfQService rfqService;

	public SendService()
	{
		super();
		Application.autowire(this);
	}

	@Override
	public void decrementNotSentCounter()
	{
		final int counter = notSentCounterProperty.getValue();
		notSentCounterProperty.setValue(counter > 0 ? counter - 1 : 0); // guard against negative counters (i.e. some bug)
	}

	@Override
	public void incrementNotSentCounter()
	{
		int counter = notSentCounterProperty.getValue();
		notSentCounterProperty.setValue(counter + 1);
	}

	@Override
	public Property<Integer> getNotSentCounterProperty()
	{
		return notSentCounterProperty;
	}

	@Override
	public int getNotSentCounter()
	{
		return notSentCounterProperty.getValue();
	}

	@Override
	public <BT extends ISendAwareBean> void updateSentStatus(final BeanItem<BT> item)
	{
		final BT bean = item.getBean();
		final boolean sent = bean.checkSent();

		@SuppressWarnings("unchecked")
		final Property<Boolean> sentProperty = item.getItemProperty(ProductQtyReport.PROPERTY_Sent);
		if (sentProperty.getValue() == sent)
		{
			return;
		}

		sentProperty.setValue(sent);

		//
		// Adjust the not-sent counter
		if (sent)
		{
			decrementNotSentCounter();
		}
		else
		{
			incrementNotSentCounter();
		}
	}

	@Override
	public void sendAll()
	{
		final MFSession mfSession = MFProcurementUI.getCurrentMFSession();

		//
		// Product Qty Reports
		{
			final ProductQtyReportRepository productQtyReportRepository = mfSession.getProductQtyReportRepository();
			productQtyReportRepository.sendAll();
		}

		//
		// RfQ reports
		{
			final BeanItemContainer<RfqHeader> rfqHeadersContainer = mfSession.getActiveRfqs();
			for (final Object itemId : rfqHeadersContainer.getItemIds())
			{
				final BeanItem<RfqHeader> rfqHeaderItem = rfqHeadersContainer.getItem(itemId);
				final RfqHeader rfqHeader = rfqHeaderItem.getBean();
				rfqService.send(rfqHeader);
				
				rfqHeader.setSentFieldsFromActualFields();
				updateSentStatus(rfqHeaderItem);
			}
		}
	}
}
