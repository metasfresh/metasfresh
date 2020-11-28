package de.metas.procurement.webui.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.ui.component.BeansVerticalComponentGroup;
import de.metas.procurement.webui.ui.component.DateNavigation;
import de.metas.procurement.webui.ui.component.GenericProductButton;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;
import de.metas.procurement.webui.ui.model.WeekProductQtyReportContainer;

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
public class WeeklyReportingView extends MFProcurementNavigationView
{
	private static final String STYLE = "weekly-report-view";

	@Autowired
	private I18N i18n;

	private final DateNavigation datePanel;

	private final BeansVerticalComponentGroup<WeekProductQtyReport> productButtons;
	private final ProductQtyReportRepository productQtyReportRepository = MFProcurementUI.getCurrentMFSession().getProductQtyReportRepository();

	public WeeklyReportingView(final Date date)
	{
		super();

		addStyleName(STYLE);

		final NavigationBar navigationBar = getNavigationBar();
		navigationBar.setCaption(i18n.get("WeeklyReportingView.caption"));

		//
		// Content
		{
			final VerticalLayout content = new VerticalLayout();

			// Date
			{
				datePanel = new DateNavigation();
				datePanel.setDateHandler(DateNavigation.DATEHANDLER_Week);
				datePanel.addDateChangedListener(new PropertyChangeListener()
				{

					@Override
					public void propertyChange(final PropertyChangeEvent evt)
					{
						onDayChanged(datePanel.getDate());
					}
				});

				final VerticalComponentGroup datePanelGroup = new VerticalComponentGroup();
				datePanelGroup.addComponent(datePanel);
				content.addComponent(datePanelGroup);
			}

			// Product buttons
			productButtons = new BeansVerticalComponentGroup<WeekProductQtyReport>()
			{
				@Override
				protected Component createItemComponent(final BeanItem<WeekProductQtyReport> item)
				{
					final ProductItemButton itemComp = new ProductItemButton();
					itemComp.setItem(item);
					return itemComp;
				};
			};
			content.addComponent(productButtons);

			setContent(content);
		}

		//
		// Toolbar (bottom)
		{

		}

		//
		// Load
		datePanel.setDate(date);
	}

	@Override
	protected void onBecomingVisible()
	{
		super.onBecomingVisible();

		//
		// Make sure qtys are up2date
		final BeanItemContainer<WeekProductQtyReport> container = productButtons.getContainerDataSource();
		if (container instanceof WeekProductQtyReportContainer)
		{
			final WeekProductQtyReportContainer weekReportContainer = (WeekProductQtyReportContainer)container;
			weekReportContainer.updateQtys();
		}
	}

	private void onDayChanged(final Date date)
	{
		final WeekProductQtyReportContainer weekReportContainer = productQtyReportRepository.getWeek(date);
		productButtons.setContainerDataSource(weekReportContainer);
	}

	private static final class ProductItemButton extends GenericProductButton<WeekProductQtyReport>
	{
		private WeeklyDetailedReportingView weekDetailedReportingView = new WeeklyDetailedReportingView();

		public ProductItemButton()
		{
			super();
			setTargetView(weekDetailedReportingView);

			setSwipeAction(new ActionAdapter(i18n.getWithDefault(ACTION_Remove, ACTION_Remove_DefaultCaption))
			{
				@Override
				public void execute(final GenericProductButton<?> button)
				{
					actionRemove();
				}
			});
		}

		@Override
		protected void onItemChanged(final BeanItem<WeekProductQtyReport> item)
		{
			weekDetailedReportingView.setWeekQtyReport(item);
		}

		@Override
		protected String extractCaption(final WeekProductQtyReport bean)
		{
			if (bean == null)
			{
				return null;
			}

			return buildCaptionFromProductName(bean.getProductName(), bean.getProductPackingInfo());
		}

		@Override
		protected String extractDescription(WeekProductQtyReport bean)
		{
			return quantityToString(bean == null ? null : bean.getQty());
		}

		@Override
		protected void afterUpdateUI(WeekProductQtyReport bean)
		{
			final Trend nextWeekTrend = bean == null ? null : bean.getNextWeekTrend();
			Trend.applyStyleName(this, nextWeekTrend);
		}

		private void actionRemove()
		{
			final WeekProductQtyReport bean = getBean();
			if (bean == null)
			{
				// closeCurrentSwipeComponent(); // not needed; it's done by the caller
				return;
			}

			final Product product = bean.getProduct();

			final ProductQtyReportRepository productQtyReportRepository = getProductQtyReportRepository();
			productQtyReportRepository.removeFavoriteProduct(product);

			// closeCurrentSwipeComponent(); // not needed; it's done by the caller
		}
	}
}
