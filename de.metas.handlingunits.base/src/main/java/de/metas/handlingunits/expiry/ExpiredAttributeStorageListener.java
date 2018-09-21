package de.metas.handlingunits.expiry;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Component
public class ExpiredAttributeStorageListener implements IAttributeStorageListener
{
	public ExpiredAttributeStorageListener()
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

		final boolean relevantAttributesArePresent = storage.hasAttribute(Constants.ATTR_Expired)
				&& storage.hasAttribute(Constants.ATTR_BestBeforeDate);
		if (!relevantAttributesArePresent)
		{
			return;
		}

		final String attributeIdentifier = attributeValue.getM_Attribute().getValue();
		final boolean relevantAttributeHasChanged = Constants.ATTR_BestBeforeDate.equals(attributeIdentifier);
		if (!relevantAttributeHasChanged)
		{
			return;
		}

		final Date bestBefore = storage.getValueAsDate(Constants.ATTR_BestBeforeDate);
		final int warnInterval = getMinimalWarnIntervalInDays(huAttributeStorage);

		final LocalDateTime warnDate = TimeUtil
				.asLocalDate(bestBefore)
				.minusDays(warnInterval)
				.atStartOfDay();
		if (warnDate.isBefore(LocalDateTime.now()))
		{
			storage.setValue(Constants.ATTR_Expired, Constants.ATTR_Expired_Value_Expired);
		}
		else
		{
			storage.setValue(Constants.ATTR_Expired, null);
		}
	}

	/** get the minimal value from the product(s) included in the storage's HU. */
	private int getMinimalWarnIntervalInDays(@NonNull final AbstractHUAttributeStorage huAttributeStorage)
	{
		int expiryWarningLeadTimeDays = Integer.MAX_VALUE;

		final List<IHUProductStorage> productStorages = Services
				.get(IHandlingUnitsBL.class)
				.getStorageFactory()
				.getStorage(huAttributeStorage.getM_HU())
				.getProductStorages();
		for (final IHUProductStorage productStorage : productStorages)
		{
			final I_M_Product productRecord = productStorage.getM_Product();
			final int currentDays;
			if (productRecord.getGuaranteeDaysMin() > 0)
			{
				currentDays = productRecord.getGuaranteeDaysMin();
			}
			else
			{
				currentDays = productRecord.getM_Product_Category().getGuaranteeDaysMin();
			}
			expiryWarningLeadTimeDays = Integer.min(currentDays, expiryWarningLeadTimeDays);
		}
		if(expiryWarningLeadTimeDays == Integer.MAX_VALUE)
		{
			expiryWarningLeadTimeDays = 0;
		}
		return expiryWarningLeadTimeDays;
	}
}
