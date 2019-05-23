package de.metas.handlingunits.age.process;

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

import de.metas.handlingunits.age.AgeAttributesService;
import de.metas.handlingunits.age.HUWithAgeRepository;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.compiere.Adempiere;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class M_HU_UpdateHUAgeAttributeProcess extends JavaProcess
{
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
	private final HUWithAgeRepository huWithAgeRepository = Adempiere.getBean(HUWithAgeRepository.class);

	private final AgeAttributesService ageAttributesService = Adempiere.getBean(AgeAttributesService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		streamHUs().forEach(this::updateAgeAttribute);

		return MSG_OK;
	}

	private Stream<I_M_HU> streamHUs()
	{
		return huWithAgeRepository.getAllWhereProductionDateIsNotEmptyAndQtyOnHandStatus();
	}

	private void updateAgeAttribute(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributeStorageFactory.getAttributeStorage(hu);
		storage.setSaveOnChange(true);

		final LocalDateTime productionDate = storage.getValueAsLocalDateTime(HUAttributeConstants.ATTR_ProductionDate);
		final int age = ageAttributesService.getAgeValues().computeAgeInMonths(productionDate);
		storage.setValue(HUAttributeConstants.ATTR_Age, String.valueOf(age));
	}

}
