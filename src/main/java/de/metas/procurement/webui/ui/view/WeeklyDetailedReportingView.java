package de.metas.procurement.webui.ui.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.ui.component.BeansVerticalComponentGroup;
import de.metas.procurement.webui.ui.model.ProductQtyReport;
import de.metas.procurement.webui.ui.model.ProductQtyReportContainer;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/*
 * #%L
 * de.metas.procurement.webui
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

@SuppressWarnings("serial")
public class WeeklyDetailedReportingView extends MFProcurementNavigationView
{
	private static final String STYLE = "WeeklyDetailedReportingView";
	private static final String STYLE_CurrentTrend = "current-trend";

	@Autowired
    private I18N i18n;

	private BeanItem<WeekProductQtyReport> weekQtyReportItem;

	// UI
	private final BeansVerticalComponentGroup<ProductQtyReport> productButtons;
	private final Map<Trend, Button> trend2button = new HashMap<>();
	private final Toolbar toolbar;

	public WeeklyDetailedReportingView()
	{
		super();
		addStyleName(STYLE);

		//
		// Content
		{
			productButtons = new BeansVerticalComponentGroup<ProductQtyReport>()
			{

				@Override
				protected Component createItemComponent(final BeanItem<ProductQtyReport> item)
				{
					final ProductItemButton itemComp = new ProductItemButton();
					itemComp.setItem(item);
					return itemComp;
				}
			};

			final VerticalLayout content = new VerticalLayout();
			content.addComponents(productButtons);

			setContent(content);
		}

		//
		// Toolbar
		{
			this.toolbar = new Toolbar();
			toolbar.addComponents(
					createTrendButton(Trend.UP)
					, createTrendButton(Trend.DOWN)
					, createTrendButton(Trend.EVEN)
					, createTrendButton(Trend.ZERO)
					);
			setToolbar(toolbar);
		}
	}

	private final Button createTrendButton(final Trend trend)
	{
		final Button button = new Button();
		button.setStyleName("no-decoration");
		Trend.applyStyleName(button, trend);
		button.setIcon(trend.getIcon());
		button.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(final ClickEvent event)
			{
				onNextWeekTrendButtonPressed(trend);
			}
		});

		trend2button.put(trend, button);
		return button;
	}

	public void setWeekQtyReport(final BeanItem<WeekProductQtyReport> weekQtyReportItem)
	{
		if (Objects.equal(this.weekQtyReportItem, weekQtyReportItem))
		{
			return;
		}

		this.weekQtyReportItem = weekQtyReportItem;
		final WeekProductQtyReport weekQtyReport = weekQtyReportItem == null ? null : weekQtyReportItem.getBean();

		//
		// Header caption
		{
			final String caption = weekQtyReport == null ? null : weekQtyReport.getCaption();
			setCaption(caption);
		}

		//
		// Product buttons
		final ProductQtyReportContainer dailyQtyReportContainer = weekQtyReport == null ? null : weekQtyReport.getDailyQtyReportContainer();
		productButtons.setContainerDataSource(dailyQtyReportContainer);

		//
		// Toolbar caption and trend buttons
		{
			final String nextWeek = weekQtyReport == null ? "-" : weekQtyReport.getNextWeekString();
			toolbar.setCaption(i18n.get("WeeklyDetailedReportingView.toolbar.caption", nextWeek));

			updateUI_NextWeekTrand();
		}
	}

	private void onNextWeekTrendButtonPressed(final Trend nextWeekTrend)
	{
		@SuppressWarnings("unchecked")
		final Property<Trend> nextWeekTrendProperty = weekQtyReportItem.getItemProperty(WeekProductQtyReport.PROPERTY_NextWeekTrend);
		nextWeekTrendProperty.setValue(nextWeekTrend);

		updateUI_NextWeekTrand();
	}

	private final void updateUI_NextWeekTrand()
	{
		final WeekProductQtyReport weekQtyReport = weekQtyReportItem == null ? null : weekQtyReportItem.getBean();
		final Trend nextWeekTrend = weekQtyReport == null ? null : weekQtyReport.getNextWeekTrend();

		for (Map.Entry<Trend, Button> e : trend2button.entrySet())
		{
			final Trend trend = e.getKey();
			final Button button = e.getValue();

			if (Objects.equal(nextWeekTrend, trend))
			{
				button.addStyleName(STYLE_CurrentTrend);
			}
			else
			{
				button.removeStyleName(STYLE_CurrentTrend);
			}
		}
	}

	private class ProductItemButton extends DailyReportingView.ProductItemButton
	{
		@Override
		protected String extractCaption(final ProductQtyReport bean)
		{
			if (bean == null)
			{
				return null;
			}
			final DateFormat dateFormat = new SimpleDateFormat("EEEE\ndd.MM.yyyy", i18n.getLocale());
			dateFormat.setLenient(false);

			return dateFormat.format(bean.getDay());
		}
	}
}
