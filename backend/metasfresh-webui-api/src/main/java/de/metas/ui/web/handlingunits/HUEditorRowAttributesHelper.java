package de.metas.ui.web.handlingunits;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Evaluatee;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.impl.TooltipType;
import de.metas.device.adempiere.AttributeDeviceAccessor;
import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.devices.DeviceDescriptor;
import de.metas.ui.web.devices.DeviceDescriptorsList;
import de.metas.ui.web.devices.DeviceWebSocketProducerFactory;
import de.metas.ui.web.view.descriptor.ViewRowAttributesLayout;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
 */
@UtilityClass
public final class HUEditorRowAttributesHelper
{
	public static ViewRowAttributesLayout createLayout(final IAttributeStorage attributeStorage)
	{
		final WarehouseId warehouseId = attributeStorage.getWarehouseId().orElse(null);
		final List<DocumentLayoutElementDescriptor> elements = attributeStorage.getAttributeValues()
				.stream()
				.map(av -> createLayoutElement(av, warehouseId))
				.collect(GuavaCollectors.toImmutableList());

		return ViewRowAttributesLayout.of(elements);
	}

	private static DocumentLayoutElementDescriptor createLayoutElement(
			@NonNull final IAttributeValue attributeValue,
			@Nullable final WarehouseId warehouseId)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);

		final IModelTranslationMap attributeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(attribute);
		final ITranslatableString caption = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, attribute.getName());
		final ITranslatableString description = attributeTrlMap.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, attribute.getDescription());

		final AttributeCode attributeCode = HUEditorRowAttributesHelper.extractAttributeCode(attribute);

		return DocumentLayoutElementDescriptor.builder()
				.setCaption(caption)
				.setDescription(description)
				.setWidgetType(widgetType)
				.addField(DocumentLayoutElementFieldDescriptor.builder(attributeCode.getCode())
						.setPublicField(true)
						.setDevices(getDeviceDescriptors(attributeCode, warehouseId)))
				.build();
	}

	public static DeviceDescriptorsList getDeviceDescriptors(
			@NonNull final AttributeCode attributeCode,
			@Nullable final WarehouseId warehouseId)
	{
		final ImmutableList<DeviceDescriptor> deviceDescriptors = Services.get(IDevicesHubFactory.class)
				.getDefaultAttributesDevicesHub()
				.getAttributeDeviceAccessors(attributeCode)
				.stream(warehouseId)
				.map(attributeDeviceAccessor -> toDeviceDescriptor(attributeDeviceAccessor))
				.collect(GuavaCollectors.toImmutableList());

		return DeviceDescriptorsList.ofList(deviceDescriptors);
	}

	private static DeviceDescriptor toDeviceDescriptor(final AttributeDeviceAccessor attributeDeviceAccessor)
	{
		final String deviceId = attributeDeviceAccessor.getPublicId();

		return DeviceDescriptor.builder()
				.deviceId(deviceId)
				.caption(attributeDeviceAccessor.getDisplayName())
				.websocketEndpoint(DeviceWebSocketProducerFactory.buildDeviceTopicName(deviceId))
				.build();
	}

	public static AttributeCode extractAttributeCode(final org.compiere.model.I_M_Attribute attribute)
	{
		return AttributeCode.ofString(attribute.getValue());
	}

	public static Object extractJSONValue(
			@NonNull final IAttributeStorage attributesStorage,
			@NonNull final IAttributeValue attributeValue,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object value = extractValueAndResolve(attributesStorage, attributeValue);

		final Object jsonValue = Values.valueToJsonObject(value, jsonOpts);
		return jsonValue;
	}

	@Nullable
	private static Object extractValueAndResolve(
			@NonNull final IAttributeStorage attributesStorage,
			@NonNull final IAttributeValue attributeValue)
	{
		final Object value = attributeValue.getValue();
		if (!attributeValue.isList())
		{
			return value;
		}

		final IAttributeValuesProvider valuesProvider = attributeValue.getAttributeValuesProvider();
		final Evaluatee evalCtx = valuesProvider.prepareContext(attributesStorage);
		final NamePair valueNP = valuesProvider.getAttributeValueOrNull(evalCtx, value);
		final TooltipType tooltipType = Services.get(IADTableDAO.class).getTooltipTypeByTableName(I_M_Attribute.Table_Name);

		return LookupValue.fromNamePair(valueNP, null, tooltipType);
	}

	public static DocumentFieldWidgetType extractWidgetType(final IAttributeValue attributeValue)
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
			return DocumentFieldWidgetType.LocalDate;
		}
		else
		{
			throw new IllegalArgumentException("Cannot extract widgetType from " + attributeValue);
		}
	}

}
