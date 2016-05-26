package de.metas.ui.web.vaadin.window.editor;

import java.util.Map;
import java.util.Set;

import org.compiere.util.DisplayType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.shared.datatype.ComposedValue;
import de.metas.ui.web.window.shared.datatype.LookupValue;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorType;
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

public class EditorFactory
{
	public Editor createEditor(final ViewPropertyDescriptor descriptor)
	{
		if (descriptor.getType() == PropertyDescriptorType.Tabular)
		{
			return new GridEditor(descriptor);
		}
		else if (descriptor.isValueProperty())
		{
			return createValueEditor(descriptor);
		}
		else if (WindowConstants.PROPERTYNAME_WindowRoot.equals(descriptor.getPropertyName()))
		{
			return new WindowContentRootEditorsContainer(descriptor);
		}
		else
		{
			return createEditorContainer(descriptor);
		}
	}

	private EditorsContainer createEditorContainer(final ViewPropertyDescriptor descriptor)
	{
		return new FieldEditorsContainer(descriptor);
	}

	private Editor createValueEditor(final ViewPropertyDescriptor descriptor)
	{
		final Class<?> valueType = descriptor.getValueType();
		final int displayType = descriptor.getDisplayType();

		if (String.class.equals(valueType))
		{
			return new TextEditor(descriptor);
		}
		else if (java.util.Date.class.equals(valueType))
		{
			return new DateEditor(descriptor);
		}
		else if (java.math.BigDecimal.class.equals(valueType))
		{
			return new BigDecimalEditor(descriptor);
		}
		else if (Integer.class.equals(valueType))
		{
			return new IntegerEditor(descriptor);
		}
		else if (LookupValue.class.isAssignableFrom(valueType))
		{
			if (displayType == DisplayType.Location)
			{
				return new LocationEditor(descriptor);
			}
			
//			return new ComboLookupValueEditor(descriptor);
			return new SearchLookupValueEditor(descriptor);
		}
		else if (ComposedValue.class.isAssignableFrom(valueType))
		{
			return new ComposedValueEditor(descriptor);
		}
		else if (Boolean.class.isAssignableFrom(valueType))
		{
			return new CheckboxEditor(descriptor);
		}
		else
		{
			throw new IllegalArgumentException("Unsupported property for " + valueType + " (descriptor: " + descriptor);
		}
	}
	
	public Editor createEditorsRecursivelly(final ViewPropertyDescriptor descriptor)
	{
		Preconditions.checkNotNull(descriptor, "descriptor");
		final Editor editor = createEditor(descriptor);

		if (editor.isAddingChildEditorsAllowed())
		{
			for (final ViewPropertyDescriptor childDescriptor : descriptor.getChildDescriptorsList())
			{
				final Editor childEditor = createEditorsRecursivelly(childDescriptor);
				editor.addChildEditor(childEditor);
			}
		}

		return editor;
	}
	
	public Map<PropertyName, Editor> getAllWatchedPropertyNamesAndEditors(final Editor rootEditor)
	{
		Preconditions.checkNotNull(rootEditor, "rootEditor");
		final ImmutableMap.Builder<PropertyName, Editor> editorsCollector = ImmutableMap.builder();
		collectAllWatchedPropertyNamesAndEditors(editorsCollector, rootEditor);
		return editorsCollector.build();
	}

	private void collectAllWatchedPropertyNamesAndEditors(final ImmutableMap.Builder<PropertyName, Editor> editorsCollector, final Editor editor)
	{
		editorsCollector.put(editor.getPropertyName(), editor);

		final Set<PropertyName> editorWatchedPropertyNames = editor.getWatchedPropertyNames();
		if (editorWatchedPropertyNames != null && !editorWatchedPropertyNames.isEmpty())
		{
			for (final PropertyName editorWatchedPropertyName : editorWatchedPropertyNames)
			{
				editorsCollector.put(editorWatchedPropertyName, editor);
			}
		}
		
		for (final Editor childEditor : editor.getChildEditors())
		{
			collectAllWatchedPropertyNamesAndEditors(editorsCollector, childEditor);
		}
	}

}
