package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.shipmentschedule.segments.ShipmentScheduleSegmentFromHUAttribute;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_HU_Attribute.class)
@Component
public class M_HU_Attribute
{
	private final HUUniqueAttributesService huUniqueAttributesService;
	final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);

	public M_HU_Attribute(@NonNull HUUniqueAttributesService huUniqueAttributesService)
	{
		this.huUniqueAttributesService = huUniqueAttributesService;
	}

	final IHUPIAttributesDAO huPIAttributeDAO = Services.get(IHUPIAttributesDAO.class);

	final
	/**
	 * Fire {@link IShipmentScheduleSegment} changed when an {@link I_M_HU_Attribute} is changed.
	 *
	 * NOTE: there is no point to trigger this on AFTER_NEW because then an segment change will be fired on {@link I_M_HU} level (remember: we are creating attributes once, when the HU is created).
	 *
	 * @param huAttribute
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_M_HU_Attribute.COLUMNNAME_IsActive,
					I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID,
					I_M_HU_Attribute.COLUMNNAME_Value,
					I_M_HU_Attribute.COLUMNNAME_ValueNumber
			})
	public void fireAttributeSegmentChanged(final I_M_HU_Attribute huAttribute)
	{
		// NOTE: Fire just notifying about the segment change and later on it will be determined if this attribute change really affects the storage.
		final IShipmentScheduleSegment storageSegment = new ShipmentScheduleSegmentFromHUAttribute(huAttribute);
		Services.get(IShipmentScheduleInvalidateBL.class).notifySegmentChanged(storageSegment);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = {
					I_M_HU_Attribute.COLUMNNAME_Value
			})
	public void handleHUUniqueAttributes(final I_M_HU_Attribute huAttribute)
	{
		if (!huAttribute.isUnique())
		{
			// nothing to do
			return;
		}

		final String attributeValue = huAttribute.getValue();
		if (Check.isBlank(attributeValue))
		{
			huUniqueAttributesService.deleteHUUniqueAttributesForHUAttribute(huAttribute);
		}
		else if(huUniqueAttributesService.belongsToQualityWarehouse(huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()))))
		{
			huUniqueAttributesService.deleteHUUniqueAttributesForHUAttribute(huAttribute);
		}

		else
		{
			huUniqueAttributesService.validateHUUniqueAttribute(huAttribute);

			final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));
			if (huStatusBL.isQtyOnHand(huRecord.getHUStatus()))
			{
				huUniqueAttributesService.createOrUpdateHUUniqueAttribute(huAttribute);
			}
		}

	}
}
