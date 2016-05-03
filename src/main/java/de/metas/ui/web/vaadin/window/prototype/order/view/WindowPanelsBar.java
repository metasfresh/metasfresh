package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.IdentityHashMap;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import de.metas.ui.web.vaadin.window.prototype.order.editor.Editor;
import de.metas.ui.web.vaadin.window.prototype.order.editor.EditorsContainer;

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
public class WindowPanelsBar extends CustomComponent
{
	private final HorizontalLayout content;
	public WindowPanelsBar()
	{
		super();
		
		content = new HorizontalLayout();
		setCompositionRoot(content);
	}
	
	public void setNavigationShortcutsFromEditors(final Iterable<Editor> editors)
	{
		content.removeAllComponents();
		
		final IdentityHashMap<Editor, Boolean> alreadyAddedEditors = new IdentityHashMap<>();
		for (final Editor editor : editors)
		{
			if (alreadyAddedEditors.containsKey(editor))
			{
				continue;
			}
			alreadyAddedEditors.put(editor, Boolean.TRUE);

			if (EditorsContainer.isDocumentFragment(editor))
			{
				addNavigationShortcut(editor);
			}
		}
	}

	public void addNavigationShortcut(final Editor editor)
	{
		final Button button = new Button();
		button.setPrimaryStyleName("mf-tabbar-button");
		button.setCaption(editor.getCaption());
		content.addComponent(button);
		
		button.addClickListener(new Button.ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				UI.getCurrent().scrollIntoView(editor);
			}
		});
	}

}
