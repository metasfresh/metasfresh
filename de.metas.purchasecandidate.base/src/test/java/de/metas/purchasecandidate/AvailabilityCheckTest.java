package de.metas.purchasecandidate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult.Type;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AvailabilityCheckTest
{

	@Test
	public void  Type_ofAvailabilityResponseItemType()
	{
		final Type resultAvailable = Type.ofAvailabilityResponseItemType(de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type.AVAILABLE);
		assertThat(resultAvailable).isSameAs(Type.AVAILABLE);

		final Type resultNotAvailable = Type.ofAvailabilityResponseItemType(de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type.NOT_AVAILABLE);
		assertThat(resultNotAvailable).isSameAs(Type.NOT_AVAILABLE);
	}
}
