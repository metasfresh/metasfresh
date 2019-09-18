package de.metas.handlingunits.expiry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.annotation.PostConstruct;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class MonthsUntilExpiryAttributeStorageListener implements IAttributeStorageListener
{
	@PostConstruct
	public void postConstruct()
	{
		Services.get(IAttributeStorageFactoryService.class).addAttributeStorageListener(this);
	}

	@Override
	public void onAttributeValueChanged(
			@NonNull final IAttributeValueContext attributeValueContext,
			@NonNull final IAttributeStorage storage,
			IAttributeValue attributeValue,
			Object valueOld)
	{
		final AbstractHUAttributeStorage huAttributeStorage = AbstractHUAttributeStorage.castOrNull(storage);

		final boolean storageIsAboutHUs = huAttributeStorage != null;
		if (!storageIsAboutHUs)
		{
			return;
		}

		final boolean relevantAttributesArePresent = storage.hasAttribute(HUAttributeConstants.ATTR_MonthsUntilExpiry)
				&& storage.hasAttribute(HUAttributeConstants.ATTR_BestBeforeDate);
		if (!relevantAttributesArePresent)
		{
			return;
		}

		final String attributeIdentifier = attributeValue.getM_Attribute().getValue();
		final boolean relevantAttributeHasChanged = HUAttributeConstants.ATTR_BestBeforeDate.equals(attributeIdentifier);
		if (!relevantAttributeHasChanged)
		{
			return;
		}

		final LocalDate bestBeforeDate = storage.getValueAsLocalDate(HUAttributeConstants.ATTR_BestBeforeDate);

		final LocalDate today = SystemTime.asLocalDate();
		final long months = ChronoUnit.MONTHS.between(today, bestBeforeDate);

		storage.setValue(HUAttributeConstants.ATTR_MonthsUntilExpiry, new BigDecimal(months));
	}
}
