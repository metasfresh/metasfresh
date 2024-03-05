package de.metas.handlingunits.attribute.strategy.impl;

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

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import org.compiere.model.I_M_Attribute;

public final class CopyHUAttributeTransferStrategy implements IHUAttributeTransferStrategy
{
	public static final CopyHUAttributeTransferStrategy instance = new CopyHUAttributeTransferStrategy();

	private CopyHUAttributeTransferStrategy()
	{
	}

	@Override
	public void transferAttribute(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		final IAttributeStorage attributesFrom = request.getAttributesFrom();
		final Object value = attributesFrom.getValue(attribute);

		final IAttributeStorage attributesTo = request.getAttributesTo();
		attributesTo.setValue(attribute, value);
	}

	@Override
	public boolean isTransferable(final IHUAttributeTransferRequest request, final I_M_Attribute attribute)
	{
		return true;
	}
}
