package de.metas.purchasecandidate.availability;

import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
		final Type resultAvailable = Type.ofAvailabilityResponseItemType(AvailabilityResponseItem.Type.AVAILABLE);
		assertThat(resultAvailable).isSameAs(Type.AVAILABLE);

		final Type resultNotAvailable = Type.ofAvailabilityResponseItemType(AvailabilityResponseItem.Type.NOT_AVAILABLE);
		assertThat(resultNotAvailable).isSameAs(Type.NOT_AVAILABLE);
	}
}
