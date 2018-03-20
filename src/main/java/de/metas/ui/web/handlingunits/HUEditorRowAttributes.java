package de.metas.ui.web.handlingunits;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValueContext;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.ui.web.view.IViewRowAttributes;
import de.metas.ui.web.view.descriptor.ViewRowAttributesLayout;
import de.metas.ui.web.view.json.JSONViewRowAttributes;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.MutableDocumentFieldChangedEvent;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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

/* package */ class HUEditorRowAttributes implements IViewRowAttributes
{
	public static final HUEditorRowAttributes cast(final IViewRowAttributes attributes)
	{
		return (HUEditorRowAttributes)attributes;
	}

	private final DocumentPath documentPath;
	private final IAttributeStorage attributesStorage;

	private final Supplier<ViewRowAttributesLayout> layoutSupplier;

	private final ImmutableSet<String> readonlyAttributeNames;
	private final ImmutableSet<String> hiddenAttributeNames;

	/* package */ HUEditorRowAttributes(@NonNull final DocumentPath documentPath, @NonNull final IAttributeStorage attributesStorage, final boolean readonly)
	{
		this.documentPath = documentPath;
		this.attributesStorage = attributesStorage;

		this.layoutSupplier = ExtendedMemorizingSupplier.of(() -> HUEditorRowAttributesHelper.createLayout(attributesStorage));

		//
		// Extract readonly attribute names
		final IAttributeValueContext calloutCtx = new DefaultAttributeValueContext();
		final boolean readonlyEffective = readonly || extractIsReadonly(attributesStorage);
		readonlyAttributeNames = attributesStorage.getAttributes()
				.stream()
				.filter(attribute -> readonlyEffective || attributesStorage.isReadonlyUI(calloutCtx, attribute))
				.map(HUEditorRowAttributesHelper::extractAttributeName)
				.collect(GuavaCollectors.toImmutableSet());

		hiddenAttributeNames = attributesStorage.getAttributes()
				.stream()
				.filter(attribute -> !attributesStorage.isDisplayedUI(attribute))
				.map(HUEditorRowAttributesHelper::extractAttributeName)
				.collect(GuavaCollectors.toImmutableSet());

		//
		// Bind attribute storage:
		// each change on attribute storage shall be forwarded to current execution
		AttributeStorage2ExecutionEventsForwarder.bind(attributesStorage, documentPath);
	}

	private static final boolean extractIsReadonly(final IAttributeStorage attributesStorage)
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
		if (!X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			return true;
		}

		return false; // not readonly
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
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public JSONViewRowAttributes toJson(final JSONOptions jsonOpts_NOTUSED)
	{
		final JSONViewRowAttributes jsonDocument = new JSONViewRowAttributes(documentPath);

		final List<JSONDocumentField> jsonFields = attributesStorage.getAttributeValues()
				.stream()
				.map(this::toJSONDocumentField)
				.collect(Collectors.toList());

		jsonDocument.setFields(jsonFields);

		return jsonDocument;
	}

	private final JSONDocumentField toJSONDocumentField(final IAttributeValue attributeValue)
	{
		final String fieldName = HUEditorRowAttributesHelper.extractAttributeName(attributeValue);
		final Object jsonValue = HUEditorRowAttributesHelper.extractJSONValue(attributesStorage, attributeValue);
		final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);
		return JSONDocumentField.ofNameAndValue(fieldName, jsonValue)
				.setDisplayed(isDisplayed(fieldName))
				.setMandatory(false)
				.setReadonly(isReadonly(fieldName))
				.setWidgetType(JSONLayoutWidgetType.fromNullable(widgetType));
	}

	private boolean isReadonly(final String attributeName)
	{
		return readonlyAttributeNames.contains(attributeName);
	}

	private boolean isDisplayed(final String attributeName)
	{
		return !hiddenAttributeNames.contains(attributeName);
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
			final String attributeName = event.getPath();
			if (isReadonly(attributeName))
			{
				throw new DocumentFieldReadonlyException(attributeName, event.getValue());
			}

			final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);

			final Object value = convertFromJson(attribute, event.getValue());
			attributesStorage.setValue(attribute, value);
		}
		else
		{
			throw new IllegalArgumentException("Unknown operation: " + event);
		}
	}

	private final Object convertFromJson(final I_M_Attribute attribute, final Object jsonValue)
	{
		if (jsonValue == null)
		{
			return null;
		}

		final String attributeValueType = attributesStorage.getAttributeValueType(attribute);
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			return JSONDate.fromJson(jsonValue.toString(), DocumentFieldWidgetType.Date);
		}

		return jsonValue;
	}

	@Override
	public LookupValuesList getAttributeTypeahead(final String attributeName, final String query)
	{
		final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);

		return attributesStorage
				.getAttributeValue(attribute)
				.getAvailableValues()
				.stream()
				.map(itemNP -> LookupValue.fromNamePair(itemNP))
				.collect(LookupValuesList.collect())
				.filter(LookupValueFilterPredicates.of(query), 0, 10);
	}

	@Override
	public LookupValuesList getAttributeDropdown(final String attributeName)
	{
		final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);

		return attributesStorage
				.getAttributeValue(attribute)
				.getAvailableValues()
				.stream()
				.map(itemNP -> LookupValue.fromNamePair(itemNP))
				.collect(LookupValuesList.collect());
	}

	public Optional<String> getSSCC18()
	{
		final I_M_Attribute sscc18Attribute = Services.get(ISSCC18CodeDAO.class).retrieveSSCC18Attribute(Env.getCtx());
		if (!attributesStorage.hasAttribute(sscc18Attribute))
		{
			return Optional.empty();
		}
		final String sscc18 = attributesStorage.getValueAsString(sscc18Attribute);
		if (Check.isEmpty(sscc18, true))
		{
			return Optional.empty();
		}

		return Optional.of(sscc18.trim());
	}

	public Optional<Date> getBestBeforeDate()
	{
		final I_M_Attribute bestBeforeDateAttribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(Constants.ATTR_BestBeforeDate);
		if (!attributesStorage.hasAttribute(bestBeforeDateAttribute))
		{
			return Optional.empty();
		}

		Date bestBeforeDate = attributesStorage.getValueAsDate(bestBeforeDateAttribute);
		return Optional.ofNullable(bestBeforeDate);
	}

	/**
	 * Intercepts {@link IAttributeStorage} events and forwards them to {@link Execution#getCurrentDocumentChangesCollector()}.
	 */
	@EqualsAndHashCode
	private static final class AttributeStorage2ExecutionEventsForwarder implements IAttributeStorageListener
	{
		public static final void bind(final IAttributeStorage storage, final DocumentPath documentPath)
		{
			final AttributeStorage2ExecutionEventsForwarder forwarder = new AttributeStorage2ExecutionEventsForwarder(documentPath);
			storage.addListener(forwarder);
		}

		private final DocumentPath documentPath;

		private AttributeStorage2ExecutionEventsForwarder(@NonNull final DocumentPath documentPath)
		{
			this.documentPath = documentPath;
		}

		private final void forwardEvent(final IAttributeStorage storage, final IAttributeValue attributeValue)
		{
			final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollector();

			final String attributeName = HUEditorRowAttributesHelper.extractAttributeName(attributeValue);
			final Object jsonValue = HUEditorRowAttributesHelper.extractJSONValue(storage, attributeValue);
			final DocumentFieldWidgetType widgetType = HUEditorRowAttributesHelper.extractWidgetType(attributeValue);

			changesCollector.collectEvent(MutableDocumentFieldChangedEvent.of(documentPath, attributeName, widgetType)
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

		@Override
		public void onAttributeStorageDisposed(final IAttributeStorage storage)
		{
			// nothing
		}
	}
}
