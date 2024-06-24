package de.metas.ui.web.handlingunits;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.descriptor.ViewRowAttributesLayout;
import de.metas.ui.web.view.json.JSONViewRowAttributes;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.MutableDocumentFieldChangedEvent;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValueContext;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.SYS_CONFIG_CLEARANCE;

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

public class HUEditorRowAttributes implements IViewRowAttributes
{
	public static HUEditorRowAttributes cast(final IViewRowAttributes attributes)
	{
		return (HUEditorRowAttributes)attributes;
	}

	private final DocumentPath documentPath;
	private final IAttributeStorage attributesStorage;
	private final String clearanceNote;

	private final Supplier<ViewRowAttributesLayout> layoutSupplier;

	private final ImmutableSet<AttributeCode> readonlyAttributeNames;
	private final ImmutableSet<AttributeCode> hiddenAttributeNames;

	@Getter
	private final ImmutableSet<AttributeCode> mandatoryAttributeNames;

	@Builder
	private HUEditorRowAttributes(
			@NonNull final DocumentPath documentPath,
			@NonNull final IAttributeStorage attributesStorage,
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final I_M_HU hu,
			final boolean readonly,
			final boolean isMaterialReceipt)
	{
		this.documentPath = documentPath;
		this.attributesStorage = attributesStorage;

		this.layoutSupplier = ExtendedMemorizingSupplier.of(() -> HUEditorRowAttributesHelper.createLayout(attributesStorage, hu));

		this.clearanceNote = hu.getClearanceNote();

		// Extract readonly attribute names
		final IAttributeValueContext calloutCtx = new DefaultAttributeValueContext();
		final boolean editableInHU = !readonly && !extractIsReadonly(attributesStorage);

		final ImmutableSet.Builder<AttributeCode> readonlyAttributeNames = ImmutableSet.builder();
		final ImmutableSet.Builder<AttributeCode> hiddenAttributeNames = ImmutableSet.builder();
		final ImmutableSet.Builder<AttributeCode> mandatoryAttributeNames = ImmutableSet.builder();

		for (final I_M_Attribute attribute : attributesStorage.getAttributes())
		{
			final AttributeCode attributeCode = HUEditorRowAttributesHelper.extractAttributeCode(attribute);

			final boolean editableInUI = !attributesStorage.isReadonlyUI(calloutCtx, attribute);

			final boolean editableInHuOverride = attribute.isAlwaysUpdateable()
					&& !isExceptionFromAlwaysUpdatable(attribute);

			final boolean editableEffective = editableInUI &&
					(editableInHuOverride || editableInHU);

			if (!editableEffective)
			{
				readonlyAttributeNames.add(attributeCode);
			}
			if (!attributesStorage.isDisplayedUI(attribute, productIds))
			{
				hiddenAttributeNames.add(attributeCode);
			}
			if (attributesStorage.isMandatory(attribute, productIds, isMaterialReceipt))
			{
				mandatoryAttributeNames.add(attributeCode);
			}
		}

		this.readonlyAttributeNames = readonlyAttributeNames.build();
		this.hiddenAttributeNames = hiddenAttributeNames.build();
		this.mandatoryAttributeNames = mandatoryAttributeNames.build();

		// Bind attribute storage:
		// each change on attribute storage shall be forwarded to current execution
		AttributeStorage2ExecutionEventsForwarder.bind(attributesStorage, documentPath);
	}

	/*
	Introduced in #gh11244
	To be taken out when we have an implementation for updating the stocks and/or receipt lines when we change
	this attribute in an active HU.
	 */
	private boolean isExceptionFromAlwaysUpdatable(@NonNull final I_M_Attribute attribute)
	{
		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());

		if (isWeightAttribute(attributeCode))
		{
			return true;
		}

		return AttributeCode.ofString(HUAttributeConstants.ATTR_QualityDiscountPercent_Value).equals(attributeCode);
	}

	private static boolean isWeightAttribute(@NonNull final AttributeCode attributeCode)
	{
		return Weightables.isWeightableAttribute(attributeCode);

	}

	private static boolean extractIsReadonly(final IAttributeStorage attributesStorage)
	{
		final I_M_HU hu = IHUAware.getM_HUOrNull(attributesStorage);
		if (hu == null)
		{
			return true;
		}
		if (!hu.isActive())
		{
			return true;
		}

		final String huStatus = hu.getHUStatus();
		return !X_M_HU.HUSTATUS_Planning.equals(huStatus);// not readonly
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("documentPath", documentPath)
				.add("attributesStorage", attributesStorage)
				.toString();
	}

	@Override
	public ViewRowAttributesLayout getLayout()
	{
		return layoutSupplier.get();
	}

	@Override
	public JSONViewRowAttributes toJson(final JSONOptions jsonOpts)
	{
		final JSONViewRowAttributes jsonDocument = new JSONViewRowAttributes(documentPath);

		final List<JSONDocumentField> jsonFields = attributesStorage.getAttributeValues()
				.stream()
				.map(attributeValue -> toJSONDocumentField(attributeValue, jsonOpts))
				.collect(Collectors.toList());

		getClearanceNoteField(jsonOpts).ifPresent(jsonFields::add);

		jsonDocument.setFields(jsonFields);

		return jsonDocument;
	}

	private JSONDocumentField toJSONDocumentField(final IAttributeValue attributeValue, final JSONOptions jsonOpts)
	{
		final AttributeCode attributeCode = attributeValue.getAttributeCode();
		final Object jsonValue = HUEditorRowAttributesHelper.extractJSONValue(attributesStorage, attributeValue, jsonOpts);
		final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);
		return JSONDocumentField.ofNameAndValue(attributeCode.getCode(), jsonValue)
				.setDisplayed(isDisplayed(attributeCode))
				.setMandatory(isMandatory(attributeCode))
				.setReadonly(isReadonly(attributeCode))
				.setWidgetType(JSONLayoutWidgetType.fromNullable(widgetType));
	}

	private boolean isMandatory(final AttributeCode attributeCode)
	{
		return mandatoryAttributeNames.contains(attributeCode);
	}

	private boolean isReadonly(final AttributeCode attributeCode)
	{
		return readonlyAttributeNames.contains(attributeCode);
	}

	private boolean isDisplayed(final AttributeCode attributeCode)
	{
		return !hiddenAttributeNames.contains(attributeCode);
	}

	@Override
	public void processChanges(final List<JSONDocumentChangedEvent> events)
	{
		if (events == null || events.isEmpty())
		{
			return;
		}

		events.forEach(this::processChange);
	}

	private void processChange(final JSONDocumentChangedEvent event)
	{
		if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
		{
			final AttributeCode attributeCode = AttributeCode.ofString(event.getPath());
			if (isReadonly(attributeCode))
			{
				throw new DocumentFieldReadonlyException(attributeCode.getCode(), event.getValue());
			}

			final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeCode);

			final Object value = convertFromJson(attribute, event.getValue());
			attributesStorage.setValue(attribute, value);
		}
		else
		{
			throw new IllegalArgumentException("Unknown operation: " + event);
		}
	}

	@Nullable
	private Object convertFromJson(final I_M_Attribute attribute, final Object jsonValue)
	{
		if (jsonValue == null)
		{
			return null;
		}

		final String attributeValueType = attributesStorage.getAttributeValueType(attribute);
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate localDate = DateTimeConverters.fromObjectToLocalDate(jsonValue.toString());
			if (localDate == null)
			{
				return null;
			}

			// convert the LocalDate to ZonedDateTime using session's time zone,
			// because later on the date is converted to Timestamp using system's default time zone.
			// And we want to have a valid date for session's timezone.
			final ZoneId zoneId = UserSession.getTimeZoneOrSystemDefault();
			return localDate.atStartOfDay(zoneId);
		}
		else
		{
			return jsonValue;
		}
	}

	@Override
	public LookupValuesList getAttributeTypeahead(final String attributeName, final String query)
	{
		final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);
		final TooltipType tooltipType = Services.get(IADTableDAO.class).getTooltipTypeByTableName(I_M_Attribute.Table_Name);

		return attributesStorage
				.getAttributeValue(attribute)
				.getAvailableValues()
				.stream()
				.map(itemNP -> LookupValue.fromNamePair(itemNP, null, tooltipType))
				.collect(LookupValuesList.collect())
				.filter(LookupValueFilterPredicates.of(query), 0, 10);
	}

	@Override
	public LookupValuesList getAttributeDropdown(final String attributeName)
	{
		final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);
		final TooltipType tooltipType = Services.get(IADTableDAO.class).getTooltipTypeByTableName(I_M_Attribute.Table_Name);

		return attributesStorage
				.getAttributeValue(attribute)
				.getAvailableValues()
				.stream()
				.map(itemNP -> LookupValue.fromNamePair(itemNP, null, tooltipType))
				.collect(LookupValuesList.collect());
	}

	public Optional<String> getSSCC18()
	{
		if (!attributesStorage.hasAttribute(HUAttributeConstants.ATTR_SSCC18_Value))
		{
			return Optional.empty();
		}

		final String sscc18 = attributesStorage.getValueAsString(HUAttributeConstants.ATTR_SSCC18_Value);
		if (Check.isEmpty(sscc18, true))
		{
			return Optional.empty();
		}

		return Optional.of(sscc18.trim());
	}

	public Optional<LocalDate> getBestBeforeDate()
	{
		if (!attributesStorage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
		{
			return Optional.empty();
		}

		final LocalDate bestBeforeDate = attributesStorage.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate);
		return Optional.ofNullable(bestBeforeDate);
	}

	public Optional<BigDecimal> getWeightGross()
	{
		final IWeightable weightable = toWeightable().orElse(null);
		if (weightable == null)
		{
			return Optional.empty();
		}

		if (!weightable.hasWeightGross())
		{
			return Optional.empty();
		}

		final BigDecimal weightGross = weightable.getWeightGross();
		return Optional.ofNullable(weightGross);
	}

	public Optional<IWeightable> toWeightable()
	{
		final IWeightable weightable = Weightables.wrap(attributesStorage);
		return Optional.ofNullable(weightable);
	}

	public Object getValue(@NonNull final AttributeCode attributeCode)
	{
		return attributesStorage.getValue(attributeCode);
	}

	@Nullable
	public String getValueAsString(@NonNull final AttributeCode attributeCode)
	{
		return attributesStorage.getValueAsString(attributeCode);
	}

	public boolean hasAttribute(@NonNull final AttributeCode attributeCode)
	{
		return attributesStorage.hasAttribute(attributeCode);
	}

	@NonNull
	private Optional<JSONDocumentField> getClearanceNoteField(@NonNull final JSONOptions jsonOptions)
	{
		final boolean isDisplayedClearanceStatus = Services.get(ISysConfigBL.class).getBooleanValue(SYS_CONFIG_CLEARANCE, true);

		if (!isDisplayedClearanceStatus || Check.isBlank(clearanceNote))
		{
			return Optional.empty();
		}

		return Optional.of(toJSONDocumentField(clearanceNote, jsonOptions));
	}


	@NonNull
	private static JSONDocumentField toJSONDocumentField(@NonNull final String clearanceNote,@NonNull final JSONOptions jsonOpts)
	{
		final Object jsonValue = Values.valueToJsonObject(clearanceNote, jsonOpts);

		return JSONDocumentField.ofNameAndValue(I_M_HU.COLUMNNAME_ClearanceNote, jsonValue)
				.setDisplayed(true)
				.setMandatory(false)
				.setReadonly(true)
				.setWidgetType(JSONLayoutWidgetType.Text);
	}

	/**
	 * Intercepts {@link IAttributeStorage} events and forwards them to {@link Execution#getCurrentDocumentChangesCollector()}.
	 */
	@EqualsAndHashCode
	private static final class AttributeStorage2ExecutionEventsForwarder implements IAttributeStorageListener
	{
		public static void bind(final IAttributeStorage storage, final DocumentPath documentPath)
		{
			final AttributeStorage2ExecutionEventsForwarder forwarder = new AttributeStorage2ExecutionEventsForwarder(documentPath);
			storage.addListener(forwarder);
		}

		private final DocumentPath documentPath;

		private AttributeStorage2ExecutionEventsForwarder(@NonNull final DocumentPath documentPath)
		{
			this.documentPath = documentPath;
		}

		private void forwardEvent(final IAttributeStorage storage, final IAttributeValue attributeValue)
		{
			final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollector();

			final AttributeCode attributeCode = attributeValue.getAttributeCode();
			final Object jsonValue = HUEditorRowAttributesHelper.extractJSONValue(storage, attributeValue, JSONOptions.newInstance());
			final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);

			changesCollector.collectEvent(MutableDocumentFieldChangedEvent.of(documentPath, attributeCode.getCode(), widgetType)
												  .setValue(jsonValue));
		}

		@Override
		public void onAttributeValueCreated(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
		{
			forwardEvent(storage, attributeValue);
		}

		@Override
		public void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue, final Object valueOld)
		{
			forwardEvent(storage, attributeValue);
		}

		@Override
		public void onAttributeValueDeleted(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
		{
			throw new UnsupportedOperationException();
		}
	}
}
