package de.metas.ui.web.handlingunits;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValueContext;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.ui.web.view.IDocumentViewAttributes;
import de.metas.ui.web.view.descriptor.DocumentViewAttributesLayout;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.exceptions.DocumentFieldReadonlyException;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates;

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

/* package */ class HUDocumentViewAttributes implements IDocumentViewAttributes
{
	private final IAttributeStorage attributesStorage;
	private final DocumentPath documentPath;

	private final Supplier<DocumentViewAttributesLayout> layoutSupplier = ExtendedMemorizingSupplier.of(() -> createLayout());

	private final Set<String> readonlyAttributeNames;

	/* package */ HUDocumentViewAttributes(final IAttributeStorage attributesStorage)
	{
		super();

		Check.assumeNotNull(attributesStorage, "Parameter attributesStorage is not null");
		this.attributesStorage = attributesStorage;
		documentPath = HUDocumentViewAttributesHelper.extractDocumentPath(attributesStorage);

		final boolean readonly = extractIsReadonly(attributesStorage);

		final IAttributeValueContext calloutCtx = new DefaultAttributeValueContext();
		readonlyAttributeNames = attributesStorage.getAttributes()
				.stream()
				.filter(attribute -> readonly || attributesStorage.isReadonlyUI(calloutCtx, attribute))
				.map(HUDocumentViewAttributesHelper::extractAttributeName)
				.collect(GuavaCollectors.toImmutableSet());
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
	public DocumentViewAttributesLayout getLayout()
	{
		return layoutSupplier.get();
	}

	private DocumentViewAttributesLayout createLayout()
	{
		return DocumentViewAttributesLayout.of(attributesStorage);
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public JSONDocument toJSONDocument()
	{
		final JSONDocument jsonDocument = new JSONDocument(documentPath);

		final List<JSONDocumentField> jsonFields = attributesStorage.getAttributeValues()
				.stream()
				.map(this::toJSONDocumentField)
				.collect(Collectors.toList());

		jsonDocument.setFields(jsonFields);

		return jsonDocument;
	}

	private final JSONDocumentField toJSONDocumentField(final IAttributeValue attributeValue)
	{
		final String fieldName = HUDocumentViewAttributesHelper.extractAttributeName(attributeValue);
		final Object jsonValue = HUDocumentViewAttributesHelper.extractJSONValue(attributesStorage, attributeValue);
		return JSONDocumentField.ofNameAndValue(fieldName, jsonValue)
				.setDisplayed(true)
				.setMandatory(false)
				.setReadonly(isReadonly(fieldName));
	}
	
	private boolean isReadonly(final String attributeName)
	{
		return readonlyAttributeNames.contains(attributeName);
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
			if(isReadonly(attributeName))
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
		if(jsonValue == null)
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
}
