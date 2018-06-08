package de.metas.handlingunits.receiptschedule.impl;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.receiptschedule.IHUToReceiveValidator;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
final class CompositeHUToReceiveValidator implements IHUToReceiveValidator
{
	public static IHUToReceiveValidator of(final Collection<IHUToReceiveValidator> validators)
	{
		return new CompositeHUToReceiveValidator(validators);
	}

	private final ImmutableList<IHUToReceiveValidator> validators;

	private CompositeHUToReceiveValidator(final Collection<IHUToReceiveValidator> validators)
	{
		this.validators = ImmutableList.copyOf(validators);
	}

	@Override
	public void assertValidForReceiving(@NonNull final I_M_HU hu)
	{
		validators.forEach(validator -> validator.assertValidForReceiving(hu));
	}
}
