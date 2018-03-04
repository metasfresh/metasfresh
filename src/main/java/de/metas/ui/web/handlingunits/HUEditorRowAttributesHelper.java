package de.metas.ui.web.handlingunits;

import java.util.List;

import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Evaluatee;
import org.compiere.util.NamePair;

import de.metas.device.adempiere.AttributesDevicesHub.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.devices.DeviceWebSocketProducerFactory;
import de.metas.ui.web.devices.JSONDeviceDescriptor;
import de.metas.ui.web.view.descriptor.ViewRowAttributesLayout;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Values;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Collection of helper methods for building HU attributes layout, extracting widgetType etc.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class HUEditorRowAttributesHelper
{
	private HUEditorRowAttributesHelper()
	{
	}

	public static final ViewRowAttributesLayout createLayout(final IAttributeStorage attributeStorage)
	{
		final int warehouseId = attributeStorage.getM_Warehouse_ID();
		final List<DocumentLayoutElementDescriptor> elements = attributeStorage.getAttributeValues()
				.stream()
				.map(av -> createLayoutElement(av, warehouseId))
				.collect(GuavaCollectors.toImmutableList());

		return ViewRowAttributesLayout.of(elements);
	}

	private static final DocumentLayoutElementDescriptor createLayoutElement(final IAttributeValue attributeValue, final int warehouseId)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final IModelTranslationMap attributeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(attribute);
		final ITranslatableString caption = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, attribute.getName());
		final ITranslatableString description = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, attribute.getDescription());

		final String attributeName = HUEditorRowAttributesHelper.extractAttributeName(attributeValue);
		final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);

		return DocumentLayoutElementDescriptor.builder()
				.setCaption(caption)
				.setDescription(description)
				.setWidgetType(widgetType)
				.addField(DocumentLayoutElementFieldDescriptor.builder(attributeName)
						.setPublicField(true)
						.addDevices(createDevices(attribute.getValue(), warehouseId)))
				.build();
	}

	private static final List<JSONDeviceDescriptor> createDevices(final String attributeCode, final int warehouseId)
	{
		return Services.get(IDevicesHubFactory.class)
				.getDefaultAttributesDevicesHub()
				.getAttributeDeviceAccessors(attributeCode)
				.stream(warehouseId)
				.map(attributeDeviceAccessor -> createDevice(attributeDeviceAccessor))
				.collect(GuavaCollectors.toImmutableList());

	}

	private static JSONDeviceDescriptor createDevice(final AttributeDeviceAccessor attributeDeviceAccessor)
	{
		final String deviceId = attributeDeviceAccessor.getPublicId();
		return JSONDeviceDescriptor.builder()
				.setDeviceId(deviceId)
				.setCaption(attributeDeviceAccessor.getDisplayName())
				.setWebsocketEndpoint(DeviceWebSocketProducerFactory.buildDeviceTopicName(deviceId))
				.build();
	}

	public static String extractAttributeName(final IAttributeValue attributeValue)
	{
		return extractAttributeName(attributeValue.getM_Attribute());
	}

	public static String extractAttributeName(final org.compiere.model.I_M_Attribute attribute)
	{
		return attribute.getValue();
	}

	public static Object extractJSONValue(final IAttributeStorage attributesStorage, final IAttributeValue attributeValue)
	{
		final Object value = extractValueAndResolve(attributesStorage, attributeValue);

		final Object jsonValue = Values.valueToJsonObject(value);
		return jsonValue;
	}

	private static final Object extractValueAndResolve(final IAttributeStorage attributesStorage, final IAttributeValue attributeValue)
	{
		final Object value = attributeValue.getValue();
		if (!attributeValue.isList())
		{
			return value;
		}

		final IAttributeValuesProvider valuesProvider = attributeValue.getAttributeValuesProvider();
		final Evaluatee evalCtx = valuesProvider.prepareContext(attributesStorage);
		final NamePair valueNP = valuesProvider.getAttributeValueOrNull(evalCtx, value);
		return LookupValue.fromNamePair(valueNP);
	}

	public static final DocumentFieldWidgetType extractWidgetType(final IAttributeValue attributeValue)
	{
		if (attributeValue.isList())
		{
			final I_M_Attribute attribute = attributeValue.getM_Attribute();
			if (attribute.isHighVolume())
			{
				return DocumentFieldWidgetType.Lookup;
			}
			else
			{
				return DocumentFieldWidgetType.List;
			}
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
