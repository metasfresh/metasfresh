package de.metas.handlingunits.age;

import java.time.LocalDateTime;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * This one is used to set the age whenever production date is changed.
 */
@Component
public class AgeAttributeStorageListener implements IAttributeStorageListener
{
	private final transient AgeAttributesService ageAttributesService;

	public AgeAttributeStorageListener(final AgeAttributesService ageAttributesService)
	{
		this.ageAttributesService = ageAttributesService;
		Services.get(IAttributeStorageFactoryService.class).addAttributeStorageListener(this);
	}

	@Override
	public void onAttributeValueChanged(
			@NonNull final IAttributeValueContext attributeValueContext,
			@NonNull final IAttributeStorage storage,
			final IAttributeValue attributeValue,
			final Object valueOld)
	{
		// checks and so on
		final AbstractHUAttributeStorage huAttributeStorage = AbstractHUAttributeStorage.castOrNull(storage);

		final boolean storageIsAboutHUs = huAttributeStorage != null;
		if (!storageIsAboutHUs)
		{
			return;
		}

		final boolean relevantAttributesArePresent = storage.hasAttribute(HUAttributeConstants.ATTR_Age)
				&& storage.hasAttribute(HUAttributeConstants.ATTR_ProductionDate);
		if (!relevantAttributesArePresent)
		{
			return;
		}

		final AttributeCode attributeCode = attributeValue.getAttributeCode();
		final boolean relevantAttributeHasChanged = HUAttributeConstants.ATTR_ProductionDate.equals(attributeCode);
		if (!relevantAttributeHasChanged)
		{
			return;
		}

		// actual logic starts here
		final LocalDateTime productionDate = storage.getValueAsLocalDateTime(HUAttributeConstants.ATTR_ProductionDate);
		if (productionDate != null)
		{
			final Age age = ageAttributesService.getAgeValues().computeAgeInMonths(productionDate);
			storage.setValue(HUAttributeConstants.ATTR_Age, age.toStringValue());
		}
		else
		{
			storage.setValue(HUAttributeConstants.ATTR_Age, null);
		}
	}
}
