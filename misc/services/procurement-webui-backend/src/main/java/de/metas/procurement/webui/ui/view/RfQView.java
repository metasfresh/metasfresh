package de.metas.procurement.webui.ui.view;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.ui.component.BeanItemNavigationButton;
import de.metas.procurement.webui.ui.component.BeansVerticalComponentGroup;
import de.metas.procurement.webui.ui.model.RfqHeader;
import de.metas.procurement.webui.ui.model.RfqModel;
import de.metas.procurement.webui.ui.model.RfqQuantityReport;
import de.metas.procurement.webui.util.StringToDateConverter;
import de.metas.procurement.webui.util.StringToPriceConverter;
import de.metas.procurement.webui.util.StringToQuantityConverter;

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
 * Displays one RfQ
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public class RfQView extends MFProcurementNavigationView
{
	private static final String STYLE = "rfq-view";

	@Autowired
	private I18N i18n;

	private BeanItem<RfqHeader> _rfqHeaderItem;
	private RfqModel _model; // lazy

	private RfqHeaderPanel rfqHeaderPanel;
	private RfqPriceButton priceButton;
	private BeansVerticalComponentGroup<RfqQuantityReport> rfqQuantityButtons;

	public RfQView()
	{
		super();

		createUI();
	}

	private void createUI()
	{
		addStyleName(STYLE);

		setCaption(i18n.get("RfQView.caption"));

		final VerticalLayout content = new VerticalLayout();

		//
		// RfQ header
		{
			rfqHeaderPanel = new RfqHeaderPanel();
			content.addComponent(rfqHeaderPanel);
		}

		//
		// Price
		{
			final VerticalComponentGroup panel = new VerticalComponentGroup();
			panel.setCaption(i18n.getWithDefault("RfQView.Price", "Price"));
			content.addComponent(panel);

			priceButton = new RfqPriceButton();
			priceButton.setCaption(i18n.getWithDefault("RfQView.Price", "Price"));
			panel.addComponent(priceButton);
		}

		//
		// Daily quantities
		{
			rfqQuantityButtons = new BeansVerticalComponentGroup<RfqQuantityReport>()
			{
				@Override
				protected Component createItemComponent(final BeanItem<RfqQuantityReport> item)
				{
					final RfqQuantityButton button = new RfqQuantityButton();
					button.setItem(item);
					return button;
				}

			};
			rfqQuantityButtons.setCaption(i18n.getWithDefault("RfQView.DailyQuantities", "Daily quantities"));

			content.addComponent(rfqQuantityButtons);

		}

		setContent(content);
	}

	@Override
	protected void onBecomingVisible()
	{
		super.onBecomingVisible();

		final RfqModel model = getModel();
		rfqHeaderPanel.setItem(model.getRfqHeaderItem());
		priceButton.setItem(model.getRfqHeaderItem());
		rfqQuantityButtons.setContainerDataSource(model.getRfqQuantitiesContainer());
	}

	private RfqModel getModel()
	{
		if (_model == null)
		{
			final BeanItem<RfqHeader> rfqHeaderItem = getRfqHeaderItem();
			_model = new RfqModel(rfqHeaderItem);
		}
		return _model;
	}

	public void setRfqHeaderItem(final BeanItem<RfqHeader> rfqHeaderItem)
	{
		if (_rfqHeaderItem == rfqHeaderItem)
		{
			return;
		}

		_rfqHeaderItem = rfqHeaderItem;
		_model = null;
	}

	private BeanItem<RfqHeader> getRfqHeaderItem()
	{
		Preconditions.checkNotNull(_rfqHeaderItem);
		return _rfqHeaderItem;
	}

	private class RfqHeaderPanel extends VerticalComponentGroup
	{
		private final Label productNameField;
		private final Label productPackingInfoField;
		private final Label dateStartField;
		private final Label dateEndField;
		private final Label dateCloseField;
		private final Label qtyRequestedField;
		private final Label qtyPromisedField;

		private final StringToQuantityConverter qtyConverter = new StringToQuantityConverter();

		public RfqHeaderPanel()
		{
			final StringToDateConverter dateConverter = StringToDateConverter.instance;

			productNameField = new Label();
			productNameField.setCaption(i18n.getWithDefault("RfQView.ProductName", "Product"));
			addComponent(productNameField);

			productPackingInfoField = new Label();
			productPackingInfoField.setCaption(i18n.getWithDefault("RfQView.ProductPackingInfo", "Packing"));
			addComponent(productPackingInfoField);

			dateStartField = new Label();
			dateStartField.setCaption(i18n.getWithDefault("RfQView.DateStart", "From"));
			dateStartField.setConverter(dateConverter);
			addComponent(dateStartField);

			dateEndField = new Label();
			dateEndField.setCaption(i18n.getWithDefault("RfQView.DateEnd", "To"));
			dateEndField.setConverter(dateConverter);
			addComponent(dateEndField);

			dateCloseField = new Label();
			dateCloseField.setCaption(i18n.getWithDefault("RfQView.DateClose", "Close"));
			dateCloseField.setConverter(dateConverter);
			addComponent(dateCloseField);

			qtyRequestedField = new Label();
			qtyRequestedField.setCaption(i18n.getWithDefault("RfQView.QtyRequested", "Qty requested"));
			qtyRequestedField.setConverter(qtyConverter);
			addComponent(qtyRequestedField);

			qtyPromisedField = new Label();
			qtyPromisedField.setCaption(i18n.getWithDefault("RfQView.QtyPromised", "Qty promised"));
			qtyPromisedField.setConverter(qtyConverter);
			addComponent(qtyPromisedField);
		}

		public void setItem(final BeanItem<RfqHeader> rfqHeaderItem)
		{
			qtyConverter.setUom(rfqHeaderItem.getBean().getQtyCUInfo());

			productNameField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_ProductName));
			productPackingInfoField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_ProductPackingInfo));
			dateStartField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_DateStart));
			dateEndField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_DateEnd));
			dateCloseField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_DateClose));
			qtyRequestedField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_QtyRequested));
			qtyPromisedField.setPropertyDataSource(rfqHeaderItem.getItemProperty(RfqHeader.PROPERTY_QtyPromised));
		}
	}

	private static class RfqPriceButton extends BeanItemNavigationButton<RfqHeader>
	{
		private static final String MSG_EditorCaption = "RfQView.PriceEditor.caption";
		
		private final NumberEditorView<RfqHeader> editorView = new NumberEditorView<RfqHeader>(RfqHeader.PROPERTY_Price)
				.setNumberConverter(new StringToPriceConverter())
				.setCaptionAndTranslate(MSG_EditorCaption);

		private final StringToPriceConverter priceConverter = new StringToPriceConverter();

		public RfqPriceButton()
		{
			super();
			setTargetView(editorView);
		}

		@Override
		protected void onItemChanged(final BeanItem<RfqHeader> item)
		{
			final RfqHeader rfqHeader = item == null ? null : item.getBean();
			priceConverter.setCurrencyCode(rfqHeader == null ? null : rfqHeader.getCurrencyCode());
			editorView.setItem(item);
		}

		@Override
		protected void updateUI(final RfqHeader bean)
		{
			final BigDecimal price = bean.getPrice();
			final String priceStr = priceConverter.convertToPresentation(price);
			setDescription(priceStr);
		}
	}

	private static class RfqQuantityButton extends BeanItemNavigationButton<RfqQuantityReport>
	{
		private static final String MSG_EditorCaption = "RfQView.QtyEditor.caption";

		private final NumberEditorView<RfqQuantityReport> editorView = new NumberEditorView<RfqQuantityReport>(RfqQuantityReport.PROPERTY_Qty)
				.setCaptionAndTranslate(MSG_EditorCaption, "?");
		
		private final StringToQuantityConverter qtyConverter;

		public RfqQuantityButton()
		{
			super();
			setTargetView(editorView);
			qtyConverter = new StringToQuantityConverter();
		}

		@Override
		protected void onItemChanged(final BeanItem<RfqQuantityReport> item)
		{
			editorView.setItem(item);
		}

		@Override
		protected void updateUI(final RfqQuantityReport rfqQuantityReport)
		{
			final Date day = rfqQuantityReport.getDay();
			final String dayStr = StringToDateConverter.instance.convertToPresentation(day, String.class, getLocale());
			setCaption(dayStr);

			qtyConverter.setUom(rfqQuantityReport.getQtyCUInfo());
			final BigDecimal qty = rfqQuantityReport.getQty();
			final String qtyStr = qtyConverter.convertToPresentation(qty, String.class, getLocale());
			setDescription(qtyStr);
			
			editorView.setCaptionAndTranslate(MSG_EditorCaption, dayStr);
		}
	}
}
