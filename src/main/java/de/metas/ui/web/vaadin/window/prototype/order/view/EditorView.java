package de.metas.ui.web.vaadin.window.prototype.order.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.window.prototype.order.editor.Editor;

/*
 * #%L
 * metasfresh-webui
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

/**
 * Wraps an root {@link Editor} and behaves like an {@link WindowView}.
 * Useful for implementing custom windows or popups.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EditorView extends AbstractView
{
	private VerticalLayout content;

	@Override
	protected Component createUI()
	{
		content = new VerticalLayout();
		return content;
	}

	@Override
	protected void updateUI_OnRootPropertyEditorChanged(Editor rootEditor)
	{
		content.removeAllComponents();
		if (rootEditor != null)
		{
			final Component rootEditorComp = rootEditor.getComponent();
			if(rootEditorComp != null)
			{
				content.addComponent(rootEditorComp);
			}
		}
	}

	@Override
	public void setNextRecordEnabled(boolean enabled)
	{
		// nothing
	}

	@Override
	public void setPreviousRecordEnabled(boolean enabled)
	{
		// nothing
	}
}
