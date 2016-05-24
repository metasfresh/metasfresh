package de.metas.ui.web.vaadin.window.editor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.PropertyLayoutInfo;

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
 * Fields group editor container.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class FieldEditorsContainer extends DocumentSectionEditorsContainer
{
	private int contentNextColumn = 0;
	private int contentNextRow = 0;

	private final Map<PropertyName, Editor> childEditors = new LinkedHashMap<>();

	public FieldEditorsContainer(final PropertyDescriptor descriptor)
	{
		super(descriptor);
	}
	
	@Override
	protected Component createPanelContent()
	{
		return new GridLayout();
	}
	
	@Override
	protected GridLayout getContent()
	{
		return (GridLayout)super.getContent();
	}

	@Override
	public void addChildEditor(final Editor editor)
	{
		Preconditions.checkNotNull(editor, "editor not null");

		final PropertyLayoutInfo layoutInfo = editor.getLayoutInfo();
		final Label editorCaption = editor.getLabel();
		final Component editorComp = editor.getComponent();
		addChildEditor(editorCaption, editorComp, layoutInfo);

		childEditors.put(editor.getPropertyName(), editor);
	}

	private void addChildEditor(final Label label, final Component editorComp, final PropertyLayoutInfo layoutInfo)
	{
		final GridLayout content = getContent();
		int labelColumn = contentNextColumn * 2;
		int labelRow = contentNextRow;

		if (layoutInfo.isNextColumn())
		{
			labelColumn = content.getColumns();
			labelRow = 0;
		}

		final int editorRowsSpan = layoutInfo.getRowsSpan();
		final int editorColumnFrom = labelColumn + 1;
		final int editorColumnTo = editorColumnFrom;
		final int editorRowFrom = labelRow;
		final int editorRowTo = editorRowFrom + (editorRowsSpan - 1);
		if (editorColumnTo >= content.getColumns())
		{
			content.setColumns(editorColumnTo + 1);
		}
		if (editorRowTo >= content.getRows())
		{
			content.setRows(editorRowTo + 1);
		}

		//
		//
		if (label != null)
		{
			content.addComponent(label, labelColumn, labelRow);
			content.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);
		}
		content.addComponent(editorComp, editorColumnFrom, editorRowFrom, editorColumnTo, editorRowTo);
		content.setComponentAlignment(editorComp, Alignment.MIDDLE_LEFT);
		editorComp.setSizeFull();

		//
		//
		// contentNextColumn;
		contentNextRow = editorRowTo + 1;
	}
	

	@Override
	public List<Editor> getChildEditors()
	{
		return ImmutableList.copyOf(childEditors.values());
	}

}
