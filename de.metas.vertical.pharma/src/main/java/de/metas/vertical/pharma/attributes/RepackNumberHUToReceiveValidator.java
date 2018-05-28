package de.metas.vertical.pharma.attributes;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.receiptschedule.IHUToReceiveValidator;

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

@Component
public class RepackNumberHUToReceiveValidator implements IHUToReceiveValidator
{
	private static final String MSG_RepackNumberNotSetForHU = "pharma.RepackNumberNotSetForHU";

	@Override
	public void assertValidForReceiving(final I_M_HU hu)
	{
		assertRepackNumberSetIfRequired(hu);
	}

	private void assertRepackNumberSetIfRequired(final I_M_HU hu)
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);

		if (!RepackNumberUtils.isRepackNumberRequired(huAttributes))
		{
			return;
		}

		final String repackNumber = RepackNumberUtils.getRepackNumber(huAttributes);
		if (!Check.isEmpty(repackNumber, true))
		{
			return;
		}

		throw new AdempiereException(MSG_RepackNumberNotSetForHU, new Object[] { hu.getValue() });
	}
}
