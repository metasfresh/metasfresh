package de.metas.ui.web.vaadin.login;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.listeners.OnEnterKeyHandler;

/*
 * #%L
 * test_vaadin
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
public class SimpleTestView extends VerticalLayout
{
	public SimpleTestView()
	{
		super();
		

		final TextField name = new TextField();
		name.setCaption("Type your name here:");

		final Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				addComponent(new Label("Thanks " + name.getValue() + ", it works! ....xxx"));
			}
		});
		
		OnEnterKeyHandler enterHandler = new OnEnterKeyHandler()
		{
			
			@Override
			public void onEnterKeyPressed()
			{
				button.click();
			}
		};
		enterHandler.installOn(name);
		
		addComponents(name, button);
		setMargin(true);
		setSpacing(true);
	}
}
