package de.metas.procurement.webui.ui.view;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.ui.component.BeansVerticalComponentGroup;
import de.metas.procurement.webui.ui.component.GenericProductButton;
import de.metas.procurement.webui.ui.model.RfqHeader;
import de.metas.procurement.webui.util.QuantityUtils;
import de.metas.procurement.webui.util.StringToDateConverter;

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

@SuppressWarnings("serial")
public class RfQsListView extends MFProcurementNavigationView
{
	@Autowired
	private I18N i18n;
	private BeansVerticalComponentGroup<RfqHeader> rfqsPanel;

	private boolean _rfqHeadersLoaded = false;

	public RfQsListView()
	{
		super();
		setCaption(i18n.get("RfQsListView.caption"));
		createUI();
	}

	private void createUI()
	{
		final VerticalLayout content = new VerticalLayout();
		setContent(content);

		rfqsPanel = new BeansVerticalComponentGroup<RfqHeader>()
		{
			@Override
			protected Component createItemComponent(final BeanItem<RfqHeader> rfqHeaderItem)
			{
				final RfqButton button = new RfqButton();
				button.setItem(rfqHeaderItem);
				return button;
			}
		};
		content.addComponent(rfqsPanel);
	}

	@Override
	protected void onBecomingVisible()
	{
		super.onBecomingVisible();

		//
		// Load RfQ items
		if (!_rfqHeadersLoaded)
		{
			final BeanItemContainer<RfqHeader> rfqHeaderContainer = MFProcurementUI.getCurrentMFSession().getActiveRfqs();
			rfqsPanel.setContainerDataSource(rfqHeaderContainer);
			_rfqHeadersLoaded = true;
		}
	}

	private static class RfqButton extends GenericProductButton<RfqHeader>
	{
		private final RfQView rfqView = new RfQView();

		public RfqButton()
		{
			super();
			setTargetView(rfqView);
		}

		@Override
		public RfqHeader getBean()
		{
			return super.getBean();
		}

		@Override
		protected void onItemChanged(final BeanItem<RfqHeader> item)
		{
			rfqView.setRfqHeaderItem(item);
		}

		@Override
		protected String extractCaption(final RfqHeader bean)
		{
			final Locale locale = getLocale();

			final StringBuilder caption = new StringBuilder();
			caption.append(bean.getProductName());
			caption.append("\n");
			caption.append(StringToDateConverter.instance.convertToPresentation(bean.getDateStart(), String.class, locale));
			caption.append(" - ");
			caption.append(StringToDateConverter.instance.convertToPresentation(bean.getDateEnd(), String.class, locale));
			return caption.toString();
		}

		@Override
		protected String extractDescription(final RfqHeader bean)
		{
			final BigDecimal qty = bean.getQtyPromised();
			final String qtyStr = QuantityUtils.toString(qty);
			return qtyStr;
		}
	}

}
