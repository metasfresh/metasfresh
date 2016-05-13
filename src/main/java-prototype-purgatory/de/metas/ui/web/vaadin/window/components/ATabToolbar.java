package de.metas.ui.web.vaadin.window.components;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;

import de.metas.ui.web.vaadin.window.model.TabToolbarModel;
import de.metas.ui.web.vaadin.window.model.TabToolbarModel.TabToolbarListener;

/*
 * #%L
 * de.metas.ui.web.vaadin
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
public class ATabToolbar extends HorizontalLayout
{
	private final TabToolbarModel model;

	private final Map<String, Button> action2button = new HashMap<>();

	private final TabToolbarListener tabToolbarListener = new TabToolbarListener()
	{
		@Override
		public void onActionEnabledChanged(final String action, final boolean enabled)
		{
			ATabToolbar.this.onActionEnabledChanged(action, enabled);
		}
	};

	private final Button.ClickListener actionButtonListener = new Button.ClickListener()
	{

		@Override
		public void buttonClick(final ClickEvent event)
		{
			final Button button = event.getButton();
			final ActionInfo actionInfo = (ActionInfo)button.getData();
			onActionButtonClicked(button, actionInfo);
		}
	};

	public ATabToolbar(final TabToolbarModel model)
	{
		super();

		this.model = model;
		this.model.addTabToolbarListener(tabToolbarListener);

		addToolbarButton(ActionInfo.of(TabToolbarModel.ACTION_PreviousRecord));
		addToolbarButton(ActionInfo.of(TabToolbarModel.ACTION_NextRecord));
	}

	private void addToolbarButton(final ActionInfo actionInfo)
	{
		final String action = actionInfo.getAction();

		final Button button = new Button();
		button.setCaption(action);
		button.setData(actionInfo);
		button.setEnabled(model.isActionEnabled(action));
		button.addClickListener(actionButtonListener);

		action2button.put(action, button);

		addComponent(button);
	}

	private void onActionButtonClicked(final Button button, final ActionInfo actionInfo)
	{
		model.executeAction(actionInfo.getAction());
	}

	private void onActionEnabledChanged(final String action, final boolean enabled)
	{
		final Button button = action2button.get(action);
		if (button == null)
		{
			return;
		}

		button.setEnabled(enabled);
	}

	private static final class ActionInfo
	{
		public static final ActionInfo of(final String action)
		{
			return new ActionInfo(action);
		}

		private final String action;

		private ActionInfo(final String action)
		{
			super();
			this.action = action;
		}

		public String getAction()
		{
			return action;
		}
	}
}
