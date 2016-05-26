package de.metas.ui.web.vaadin.window.editor;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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
public class WindowContentRootEditorsContainer extends EditorsContainer
{
	private final VerticalLayout content;

	private final List<Editor> editors = new ArrayList<>();

	public WindowContentRootEditorsContainer(final ViewPropertyDescriptor rootPropertyDescriptor)
	{
		super(rootPropertyDescriptor);

		content = new VerticalLayout();
		content.setSizeFull();
		content.addStyleName("editor-container-content");
		content.addStyleName("window-content-root");
		setCompositionRoot(content);
	}

	@Override
	public void addChildEditor(final Editor editor)
	{
		Preconditions.checkNotNull(editor, "editor");

		editors.add(editor);

		final Component editorComp = editor.getComponent();
		if (editorComp != null)
		{
			content.addComponent(editorComp);
		}
	}

	@Override
	public List<Editor> getChildEditors()
	{
		return ImmutableList.copyOf(editors);
	}
}
