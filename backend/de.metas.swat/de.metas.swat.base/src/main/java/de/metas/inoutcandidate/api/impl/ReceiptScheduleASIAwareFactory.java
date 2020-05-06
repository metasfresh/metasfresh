package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class ReceiptScheduleASIAwareFactory implements IAttributeSetInstanceAwareFactory
{

	@Override
	public IAttributeSetInstanceAware createOrNull(Object referencedObj)
	{
		if (referencedObj == null)
		{
			return null;
		}

		if (!InterfaceWrapperHelper.isInstanceOf(referencedObj, I_M_ReceiptSchedule.class))
		{
			return null;
		}

		final I_M_ReceiptSchedule rs = InterfaceWrapperHelper.create(referencedObj, I_M_ReceiptSchedule.class);
		return new ReceiptScheduleASIAware(rs);
	}

}
