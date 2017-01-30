package de.metas.ui.web.handlingunits;

import org.adempiere.util.Services;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

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

public final class HUDocumentViewAttributesHelper
{
	private HUDocumentViewAttributesHelper()
	{
		super();
	}

	public static DocumentPath extractDocumentPath(final IAttributeStorage attributesStorage)
	{
		final int huId = Services.get(IHUAttributesBL.class).getM_HU(attributesStorage).getM_HU_ID();
		return DocumentPath.rootDocumentPath(DocumentType.HUAttributes, huId, huId);
	}

	public static String extractAttributeName(final IAttributeValue attributeValue)
	{
		return attributeValue.getM_Attribute().getValue();
	}

	public static Object extractJSONValue(final IAttributeValue attributeValue)
	{
		final Object value = attributeValue.getValue();
		final Object jsonValue = Values.valueToJsonObject(value);
		return jsonValue;
	}

	public static final DocumentFieldWidgetType extractWidgetType(final IAttributeValue attributeValue)
	{
		if (attributeValue.isList())
		{
			return DocumentFieldWidgetType.List;
		}
		else if (attributeValue.isStringValue())
		{
			return DocumentFieldWidgetType.Text;
		}
		else if (attributeValue.isNumericValue())
		{
			return DocumentFieldWidgetType.Number;
		}
		else if (attributeValue.isDateValue())
		{
			return DocumentFieldWidgetType.Date;
		}
		else
		{
			throw new IllegalArgumentException("Cannot extract widgetType from " + attributeValue);
		}
	}

}
