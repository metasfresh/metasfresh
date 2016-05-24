package de.metas.ui.web.vaadin.window.editor;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;

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

/**
 * Base class for all document section containers.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public abstract class DocumentSectionEditorsContainer extends EditorsContainer
{
	private static final String STYLE = "mf-editor-container";
	private static final String STYLE_Panel = STYLE + "-panel";
	private static final String STYLE_PanelContent = STYLE + "-panel-content";

	private final Panel panel;
	private final Component content;

	public DocumentSectionEditorsContainer(final PropertyDescriptor descriptor)
	{
		super(descriptor);
		addStyleName(STYLE);

		content = createPanelContent();
		content.setSizeFull();
		content.setPrimaryStyleName(STYLE_PanelContent);
		panel = new Panel(content);
		panel.setPrimaryStyleName(STYLE_Panel);
		setCompositionRoot(panel);

		panel.setCaption(descriptor.getPropertyName().toString());
	}

	protected abstract Component createPanelContent();

	protected Component getContent()
	{
		return content;
	}
}
