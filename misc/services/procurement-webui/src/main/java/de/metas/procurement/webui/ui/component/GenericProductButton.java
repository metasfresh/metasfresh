package de.metas.procurement.webui.ui.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.service.ISendService;
import de.metas.procurement.webui.ui.model.ProductQtyReportRepository;
import de.metas.procurement.webui.util.ProductNameCaptionBuilder;
import de.metas.procurement.webui.util.QuantityUtils;
import de.metas.procurement.webui.util.SwipeHelper;
import de.metas.procurement.webui.util.SwipeHelper.ComponentSwipe;
import de.metas.procurement.webui.util.SwipeHelper.SwipeHandler;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public abstract class GenericProductButton<BT> extends BeanItemNavigationButton<BT>
{
	public static final String STYLE = "product-button";
	protected static final String STYLE_Sent = "sent";

	protected static final String ACTION_Remove = "ProductButton.action.remove";
	protected static final String ACTION_Remove_DefaultCaption = "Remove";

	private static final AtomicLong nextId = new AtomicLong(0);

	@Autowired
	protected I18N i18n;

	//
	// Context menu actions
	private Set<Action> actions = new LinkedHashSet<>();

	//
	// Swipe support
	private boolean swipeEnabled = false;
	private Action swipeAction;
	private ComponentSwipe _currentSwipeComponent;

	public GenericProductButton()
	{
		super();
		Application.autowire(this);

		addStyleName(STYLE);

		addContextClickListener(new ContextClickListener()
		{
			@Override
			public void contextClick(final ContextClickEvent event)
			{
				showContextMenu();
			}
		});
	}

	protected final ProductQtyReportRepository getProductQtyReportRepository()
	{
		return MFProcurementUI.getCurrentMFSession().getProductQtyReportRepository();
	}

	private void enableSwipe()
	{
		if (swipeEnabled)
		{
			return;
		}
		swipeEnabled = true;

		final GenericProductButton<BT> button = this;

		if (getId() == null)
		{
			setId("ProductButton_" + nextId.incrementAndGet());
		}

		SwipeHelper.getCurrent().enable(this, new SwipeHandler()
		{

			@Override
			public void onSwipe(final ComponentSwipe swipe)
			{
				button.onSwipe(swipe);
			}
		});
	}

	@Override
	protected final void updateUI(final BT bean)
	{
		final String caption = extractCaption(bean);
		setCaption(caption);

		final String description = extractDescription(bean);
		setDescription(description);

		//
		// Update sent style
		final boolean sent = bean != null
				&& (bean instanceof ISendService.ISendAwareBean)
				&& ((ISendService.ISendAwareBean)bean).isSent();
		if (sent)
		{
			addStyleName(STYLE_Sent);
		}
		else
		{
			removeStyleName(STYLE_Sent);
		}

		afterUpdateUI(bean);
	}

	protected abstract String extractCaption(final BT bean);

	protected abstract String extractDescription(final BT bean);

	protected void afterUpdateUI(final BT bean)
	{
	}

	protected final String quantityToString(final BigDecimal qty)
	{
		return QuantityUtils.toString(qty);
	}

	protected final String buildCaptionFromProductName(final String productName, final String packingInfo)
	{
		return ProductNameCaptionBuilder.newBuilder()
				.setProductName(productName)
				.setProductPackingInfo(packingInfo)
				.build();
	}

	protected final void onSwipe(final ComponentSwipe swipe)
	{
		final Action swipeAction = getSwipeAction();
		if (swipeAction == null)
		{
			if (swipe != null)
			{
				swipe.close();
			}
			return;
		}

		final ComponentSwipe swipeOld = _currentSwipeComponent; // usually it shall be null
		_currentSwipeComponent = swipe;
		try
		{
			swipeAction.execute(this);
		}
		finally
		{
			closeCurrentSwipeComponent();
			_currentSwipeComponent = swipeOld;
		}
	}

	protected final ComponentSwipe getCurrentSwipeComponent()
	{
		return _currentSwipeComponent;
	}

	protected final void closeCurrentSwipeComponent()
	{
		final ComponentSwipe currentSwipeComponent = _currentSwipeComponent;
		if (currentSwipeComponent != null)
		{
			currentSwipeComponent.close();
		}
	}

	protected void addAction(final Action action)
	{
		Preconditions.checkNotNull(action, "action is null");
		actions.add(action);
	}

	protected List<Action> getActions()
	{
		return new ArrayList<>(actions);
	}

	protected void setSwipeAction(final Action action)
	{
		this.swipeAction = action;
		if (action != null)
		{
			enableSwipe();
			addAction(action);
		}
	}

	protected final Action getSwipeAction()
	{
		return swipeAction;
	}

	private void showContextMenu()
	{
		if (actions.isEmpty())
		{
			return;
		}

		final ProductButtonContextActionsView contextActionsView = new ProductButtonContextActionsView(this);
		getNavigationManager().navigateTo(contextActionsView);
	}

	public static interface Action
	{
		public String getCaption();

		public String getDescription();

		public void execute(GenericProductButton<?> button);
	}

	public static abstract class ActionAdapter implements Action
	{
		private String caption;

		public ActionAdapter()
		{
			super();
		}

		public ActionAdapter(final String caption)
		{
			super();
			this.caption = caption;
		}

		@Override
		public String getCaption()
		{
			return caption;
		}

		@Override
		public String getDescription()
		{
			return null;
		}
	}
}
