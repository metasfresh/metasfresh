package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
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

		final boolean relevantAttributesArePresent = storage.hasAttribute(HUAttributeConstants.ATTR_Expired)
				&& storage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate);
		if (!relevantAttributesArePresent)
		{
			return;
		}

		final AttributeCode attributeCode = attributeValue.getAttributeCode();
		final boolean relevantAttributeHasChanged = AttributeConstants.ATTR_BestBeforeDate.equals(attributeCode);
		if (!relevantAttributeHasChanged)
		{
			return;
		}

		final LocalDate bestBefore = storage.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate);
		final int warnInterval = getMinimalWarnIntervalInDays(huAttributeStorage);

		final LocalDateTime warnDate = bestBefore != null
				? bestBefore.minusDays(warnInterval).atStartOfDay()
				: null;
		if (warnDate != null && warnDate.isBefore(LocalDateTime.now()))
		{
			storage.setValue(HUAttributeConstants.ATTR_Expired, HUAttributeConstants.ATTR_Expired_Value_Expired);
		}
		else
		{
			storage.setValue(HUAttributeConstants.ATTR_Expired, null);
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
			final IProductDAO productDAO = Services.get(IProductDAO.class);
			final I_M_Product productRecord = productDAO.getById(productStorage.getProductId());
			final int currentDays;
			if (productRecord.getGuaranteeDaysMin() > 0)
			{
				currentDays = productRecord.getGuaranteeDaysMin();
			}
			else
			{
				final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID());
				final I_M_Product_Category productCategoryRecord = productDAO.getProductCategoryById(productCategoryId);
				currentDays = productCategoryRecord.getGuaranteeDaysMin();
			}
			expiryWarningLeadTimeDays = Integer.min(currentDays, expiryWarningLeadTimeDays);
		}
		if (expiryWarningLeadTimeDays == Integer.MAX_VALUE)
		{
			expiryWarningLeadTimeDays = 0;
		}
		return expiryWarningLeadTimeDays;
	}
}
