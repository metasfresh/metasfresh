/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.attribute;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_M_AttributeSetInstance;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class M_AttributeSetInstance_StepDefData extends StepDefData<I_M_AttributeSetInstance>
		implements StepDefDataGetIdAware<AttributeSetInstanceId, I_M_AttributeSetInstance>
{
	public M_AttributeSetInstance_StepDefData()
	{
		super(I_M_AttributeSetInstance.class);
	}

	@Override
	public AttributeSetInstanceId extractIdFromRecord(final I_M_AttributeSetInstance record)
	{
		return AttributeSetInstanceId.ofRepoId(record.getM_AttributeSetInstance_ID());
	}

	@Override
	public boolean isAllowDuplicateRecordsForSameIdentifier(final AttributeSetInstanceId attributeSetInstanceId)
	{
		return attributeSetInstanceId.isNone();
	}
}
