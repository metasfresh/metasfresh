package de.metas.handlingunits.quarantine;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.LotNumberQuarantine;
import de.metas.product.LotNumberQuarantineRepository;
import de.metas.product.ProductId;
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

@Service
public class HULotNumberQuarantineService
{
	private final transient LotNumberQuarantineRepository lotNumberQuarantineRepository;

	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final transient IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	public HULotNumberQuarantineService(@NonNull final LotNumberQuarantineRepository lotNumberQuarantineRepository)
	{
		this.lotNumberQuarantineRepository = lotNumberQuarantineRepository;
	}

	public boolean isQuarantineLotNumber(@NonNull final AbstractHUAttributeStorage huAttributeStorage)
	{
		final String lotNumber = huAttributeStorage.getValueAsString(AttributeConstants.ATTR_LotNumber);

		final List<IHUProductStorage> productStorages = handlingUnitsBL
				.getStorageFactory()
				.getStorage(huAttributeStorage.getM_HU())
				.getProductStorages();

		for (final IHUProductStorage productStorage : productStorages)
		{
			final ProductId productId = productStorage.getProductId();
			final LotNumberQuarantine lotNumberQuarantine = lotNumberQuarantineRepository.getByProductIdAndLot(productId, lotNumber);
			if (lotNumberQuarantine != null)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isQuarantineHU(final I_M_HU huRecord)
	{
		// retrieve the attribute
		final AttributeId quarantineAttributeId = attributeDAO.getAttributeIdByCode(HUAttributeConstants.ATTR_Quarantine);

		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(huRecord, quarantineAttributeId);

		if (huAttribute == null)
		{
			return false;
		}

		return HUAttributeConstants.ATTR_Quarantine_Value_Quarantine.equals(huAttribute.getValue());
	}

	public void markHUAsQuarantine(final I_M_HU huRecord)
	{
		// retrieve the attribute
		final AttributeId quarantineAttributeId = attributeDAO.getAttributeIdByCode(HUAttributeConstants.ATTR_Quarantine);

		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(huRecord, quarantineAttributeId);

		if (huAttribute == null)
		{
			// nothing to do. The HU doesn't have the attribute
			return;
		}

		huAttribute.setValue(HUAttributeConstants.ATTR_Quarantine_Value_Quarantine);

		save(huAttribute);
	}
}
