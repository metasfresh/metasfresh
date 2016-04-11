package de.metas.procurement.webui.ui.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.event.UIEventBus;
import de.metas.procurement.webui.event.UserLogoutRequestEvent;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.ui.component.BeansVerticalComponentGroup;
import de.metas.procurement.webui.ui.component.DateNavigation;
import de.metas.procurement.webui.ui.component.GenericProductButton;
import de.metas.procurement.webui.ui.model.ProductQtyReport;
import de.metas.procurement.webui.ui.model.ProductQtyReportContainer;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.widgetset.TextOverlay;

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
public class DailyReportingView extends MFProcurementNavigationView
{
	private static final String STYLE = "daily-report-view";

	@Autowired
	private I18N i18n;

	private DateNavigation datePanel;
	private BeansVerticalComponentGroup<ProductQtyReport> productButtons;

	private final ProductQtyReportRepository productQtyReportRepository = MFProcurementUI.getCurrentMFSession().getProductQtyReportRepository();

	public DailyReportingView()
	{
		super();

		addStyleName(STYLE);

		//
		// Top
		{
			final NavigationBar navigationBar = getNavigationBar();
			navigationBar.setCaption(i18n.get("DailyReportingView.caption"));

			final NavigationButton logoutButton = new NavigationButton(i18n.get("Logout.caption"));
			logoutButton.setTargetView(this);
			logoutButton.addClickListener(new NavigationButtonClickListener()
			{
				@Override
				public void buttonClick(final NavigationButtonClickEvent event)
				{
					onLogout();
				}
			});
			navigationBar.setRightComponent(logoutButton);
		}

		//
		// Content
		{
			final VerticalLayout content = new VerticalLayout();

			// Date
			{
				datePanel = new DateNavigation();
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
			productButtons = new BeansVerticalComponentGroup<ProductQtyReport>()
			{
				@Override
				protected Component createItemComponent(final BeanItem<ProductQtyReport> item)
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
			final Button weekViewButton = new Button(i18n.get("DailyReportingView.weekViewButton"));
			weekViewButton.setStyleName("no-decoration");
			weekViewButton.setIcon(FontAwesome.CALENDAR);
			weekViewButton.addClickListener(new ClickListener()
			{
				@Override
				public void buttonClick(final ClickEvent event)
				{
					onWeekView();
				}
			});

			final Button addProductButton = new Button(i18n.get("DailyReportingView.addProductButton"));
			addProductButton.setStyleName("no-decoration");
			addProductButton.setIcon(FontAwesome.PLUS);
			addProductButton.addClickListener(new ClickListener()
			{
				@Override
				public void buttonClick(final ClickEvent event)
				{
					onProductAdd();
				}
			});

			final Button sendButton = new Button(i18n.get("DailyReportingView.sendButton"));
			sendButton.setStyleName("no-decoration");
			sendButton.setIcon(FontAwesome.CHECK);
			final TextOverlay sendButtonOverlay = TextOverlay.extend(sendButton);
			sendButtonOverlay.setPropertyDataSource(productQtyReportRepository.getNotSentCounterProperty());
			sendButtonOverlay.setConverter(TextOverlay.CONVERTER_PositiveCounterOrNull);
			sendButton.addClickListener(new ClickListener()
			{
				@Override
				public void buttonClick(final ClickEvent event)
				{
					onSend();
				}
			});

			final Button infoButton = new Button(i18n.getWithDefault("InfoMessageView.caption.short", "Info"));
			infoButton.setStyleName("no-decoration");
			infoButton.setIcon(FontAwesome.INFO);
			infoButton.addClickListener(new ClickListener()
			{
				@Override
				public void buttonClick(final ClickEvent event)
				{
					onInfo();
				}
			});

			final Toolbar toolbar = new Toolbar();
			toolbar.addComponents(weekViewButton, addProductButton, sendButton, infoButton);
			setToolbar(toolbar);
		}

		//
		// Initialize
		datePanel.setDate(DateUtils.getToday());
	}

	private void onLogout()
	{
		UIEventBus.post(UserLogoutRequestEvent.of());
	}

	private void onDayChanged(final Date date)
	{
		final Date day = DateUtils.truncToDay(date);
		final ProductQtyReportContainer productQtyReportContainer = productQtyReportRepository.getDailyProductQtyReportContainer(day);
		productButtons.setContainerDataSource(productQtyReportContainer);
	}

	protected void onWeekView()
	{
		final WeeklyReportingView weeklyView = new WeeklyReportingView(getDay());
		getNavigationManager().navigateTo(weeklyView);
	}

	protected void onProductAdd()
	{
		final SelectProductView selectProductView = new SelectProductView(productQtyReportRepository);
		getNavigationManager().navigateTo(selectProductView);
	}

	protected void onSend()
	{
		productQtyReportRepository.sendAll();
	}

	protected void onInfo()
	{
		final InfoMessageView infoMessageView = new InfoMessageView();
		if (!infoMessageView.isDisplayable())
		{
			return;
		}

		getNavigationManager().navigateTo(infoMessageView);
	}

	public final Date getDay()
	{
		return datePanel.getDate();
	}

	public static class ProductItemButton extends GenericProductButton<ProductQtyReport>
	{
		private static final String STYLE_Sent = "sent";
		private final DailyProductQtyReportView reportQtyView = new DailyProductQtyReportView();

		public ProductItemButton()
		{
			super();
			setTargetView(reportQtyView);

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
		protected void onItemChanged(final BeanItem<ProductQtyReport> item)
		{
			reportQtyView.setItem(item);
		}

		@Override
		protected String extractCaption(final ProductQtyReport bean)
		{
			if (bean == null)
			{
				return null;
			}

			final Locale locale = UI.getCurrent().getLocale();
			return buildCaptionFromProductName(bean.getProductName(locale), bean.getProductPackingInfo(locale));
		}

		@Override
		protected String extractDescription(final ProductQtyReport bean)
		{
			return quantityToString(bean == null ? null : bean.getQty());
		}

		@Override
		protected Product extractProduct(final ProductQtyReport bean)
		{
			return bean.getProduct();
		}

		@Override
		protected void afterUpdateUI(final ProductQtyReport bean)
		{
			final boolean sent = bean != null && bean.isSent();
			if (sent)
			{
				addStyleName(STYLE_Sent);
			}
			else
			{
				removeStyleName(STYLE_Sent);
			}
		}

		private void actionRemove()
		{
			final ProductQtyReport bean = getBean();
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
