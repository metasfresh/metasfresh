package de.metas.ui.web.handlingunits;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_M_Attribute;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.ui.web.view.IDocumentViewAttributes;
import de.metas.ui.web.view.descriptor.DocumentViewAttributesLayout;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/*package*/ class HUDocumentViewAttributes implements IDocumentViewAttributes
{
	private final IAttributeStorage attributesStorage;
	private final DocumentPath documentPath;

	private final Supplier<DocumentViewAttributesLayout> layoutSupplier = ExtendedMemorizingSupplier.of(() -> createLayout());

	/* package */ HUDocumentViewAttributes(final IAttributeStorage attributesStorage)
	{
		super();

		Check.assumeNotNull(attributesStorage, "Parameter attributesStorage is not null");
		this.attributesStorage = attributesStorage;
		documentPath = HUDocumentViewAttributesHelper.extractDocumentPath(attributesStorage);
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
	public Map<String, Object> getData()
	{
		return attributesStorage.getAttributeValues()
				.stream()
				.map(av -> toMapEntryOrNull(av))
				.filter(entry -> entry != null)
				.collect(GuavaCollectors.toImmutableMap());
	}

	private static final Map.Entry<String, Object> toMapEntryOrNull(final IAttributeValue attributeValue)
	{
		final Object jsonValue = HUDocumentViewAttributesHelper.extractJSONValue(attributeValue);
		if (jsonValue == null)
		{
			return null;
		}

		final String attributeName = HUDocumentViewAttributesHelper.extractAttributeName(attributeValue);
		return GuavaCollectors.entry(attributeName, jsonValue);
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
			final I_M_Attribute attribute = attributesStorage.getAttributeByValueKeyOrNull(attributeName);

			final Object value = event.getValue(); // TODO: i think we will need some conversions
			attributesStorage.setValue(attribute, value);
		}
		else
		{
			throw new IllegalArgumentException("Unknown operation: " + event);
		}
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
