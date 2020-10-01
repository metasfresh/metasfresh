package de.metas.handlingunits.quarantine;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
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
public class QuarantineAttributeStorageListener implements IAttributeStorageListener
{

	private final transient HULotNumberQuarantineService lotNumberQuarantineService;

	public QuarantineAttributeStorageListener(@NonNull final HULotNumberQuarantineService lotNumberQuarantineService)
	{

		this.lotNumberQuarantineService = lotNumberQuarantineService;

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

		if (!storage.hasAttribute(HUAttributeConstants.ATTR_Quarantine))
		{
			return;
		}

		final AttributeCode attributeCode = attributeValue.getAttributeCode();
		final boolean relevantAttributeHasChanged = LotNumberDateAttributeDAO.ATTR_LotNumber.equals(attributeCode);

		if (!relevantAttributeHasChanged)
		{
			return;
		}

		if (lotNumberQuarantineService.isQuarantineLotNumber(huAttributeStorage))
		{
			storage.setValue(HUAttributeConstants.ATTR_Quarantine, HUAttributeConstants.ATTR_Quarantine_Value_Quarantine);
		}
		else
		{
			storage.setValue(HUAttributeConstants.ATTR_Quarantine, null);
		}
	}

}
