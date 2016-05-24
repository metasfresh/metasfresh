package de.metas.ui.web.vaadin.listeners;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

/*
 * #%L
 * 
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

public abstract class OnEnterKeyHandler
{
	final ShortcutListener enterShortCut = new ShortcutListener("EnterOnTextAreaShorcut", ShortcutAction.KeyCode.ENTER, null)
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void handleAction(Object sender, Object target)
		{
			onEnterKeyPressed();
		}
	};

	public void installOn(final TextField component)
	{
		component.addFocusListener(new FieldEvents.FocusListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FieldEvents.FocusEvent event)
			{
				component.addShortcutListener(enterShortCut);
			}

		});

		component.addBlurListener(new FieldEvents.BlurListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void blur(FieldEvents.BlurEvent event)
			{
				component.removeShortcutListener(enterShortCut);
			}

		});
	}

	public abstract void onEnterKeyPressed();

}