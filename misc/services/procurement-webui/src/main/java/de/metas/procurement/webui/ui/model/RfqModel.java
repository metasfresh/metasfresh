package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.BeanItem;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.service.ISendService;

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

/**
 * RfQ editing model.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class RfqModel
{
	private final BeanItem<RfqHeader> rfqHeaderItem;
	private RfqQuantityReportContainer _rfqQuantitiesContainer; // lazy

	@SuppressWarnings("serial")
	private final ValueChangeListener priceChangeListener = new ValueChangeListener()
	{

		@Override
		public void valueChange(final ValueChangeEvent event)
		{
			updateSentStatus();
		}
	};

	@SuppressWarnings("serial")
	private final ValueChangeListener qtyPromisedChangeListener = new ValueChangeListener()
	{
		@Override
		public void valueChange(final ValueChangeEvent event)
		{
			updateQtyPromised();
			updateSentStatus();
		}
	};
	private final Property<BigDecimal> qtyPromisedSumProperty;

	@SuppressWarnings("unchecked")
	public RfqModel(final BeanItem<RfqHeader> rfqHeaderItem)
	{
		super();
		Application.autowire(this);

		//
		// RfQ Header
		this.rfqHeaderItem = rfqHeaderItem;

		final Property<?> priceProperty = rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_Price);
		((ValueChangeNotifier)priceProperty).addValueChangeListener(priceChangeListener);

		qtyPromisedSumProperty = rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_QtyPromised);
	}

	private void updateQtyPromised()
	{
		final RfqHeader rfqHeader = rfqHeaderItem.getBean();
		final BigDecimal qtyPromisedSum = rfqHeader.calculateQtyPromisedSum();
		qtyPromisedSumProperty.setValue(qtyPromisedSum);
	}

	public BeanItem<RfqHeader> getRfqHeaderItem()
	{
		return rfqHeaderItem;
	}

	public RfqQuantityReportContainer getRfqQuantitiesContainer()
	{
		if (_rfqQuantitiesContainer == null)
		{
			_rfqQuantitiesContainer = createRfqQuantitiesContainer();
			updateQtyPromised();
		}
		return _rfqQuantitiesContainer;
	}

	private RfqQuantityReportContainer createRfqQuantitiesContainer()
	{
		final RfqQuantityReportContainer rfqQuantitiesContainer = new RfqQuantityReportContainer();
		final RfqHeader rfqHeader = rfqHeaderItem.getBean();
		for (final RfqQuantityReport rfqQuantityReport : rfqHeader.getQuantities())
		{
			final BeanItem<RfqQuantityReport> rfqQuantityReportItem = rfqQuantitiesContainer.addBean(rfqQuantityReport);
			final Property<?> qtyProperty = rfqQuantityReportItem.getItemProperty(RfqQuantityReport.PROPERTY_Qty);
			((ValueChangeNotifier)qtyProperty).addValueChangeListener(qtyPromisedChangeListener);
		}

		return rfqQuantitiesContainer;
	}

	private void updateSentStatus()
	{
		final ISendService sendService = MFProcurementUI.getCurrentMFSession().getSendService();
		sendService.updateSentStatus(rfqHeaderItem);
	}
}
