package de.metas.ui.web.view.descriptor;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.compiere.model.I_M_Attribute;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.handlingunits.HUDocumentViewAttributesHelper;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;

/*
 * #%L
 * metasfresh-webui-api
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

public final class DocumentViewAttributesLayout
{
	public static final DocumentViewAttributesLayout of(final IAttributeStorage attributeStorage)
	{
		return new DocumentViewAttributesLayout(attributeStorage);
	}

	private final List<DocumentLayoutElementDescriptor> elements;

	private DocumentViewAttributesLayout(final IAttributeStorage attributeStorage)
	{
		super();

		elements = attributeStorage.getAttributeValues()
				.stream()
				.map(av -> createElement(av))
				.collect(GuavaCollectors.toImmutableList());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("elements", elements)
				.toString();
	}

	private static final DocumentLayoutElementDescriptor createElement(final IAttributeValue attributeValue)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final IModelTranslationMap attributeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(attribute);
		final ITranslatableString caption = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, attribute.getName());
		final ITranslatableString description = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, attribute.getDescription());

		final String attributeName = HUDocumentViewAttributesHelper.extractAttributeName(attributeValue);
		final DocumentFieldWidgetType widgetType = HUDocumentViewAttributesHelper.extractWidgetType(attributeValue);
		
		return DocumentLayoutElementDescriptor.builder()
				.setCaption(caption)
				.setDescription(description)
				.setWidgetType(widgetType)
				.addField(DocumentLayoutElementFieldDescriptor.builder(attributeName)
						.setPublicField(true))
				.build();
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

}
