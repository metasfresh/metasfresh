package de.metas.bpartner.composite;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

class BPartnerLocationTest
{

	/**
	 * Verifies that we can use {@link BPartnerLocation#getOriginalOrSelf()} and {@link BPartnerLocation#equals(Object)}
	 * to figure out if a {@link BPartnerLocation} was changed since its isntantiation.
	 */
	@Test
	void getOriginalOrSelf()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(10);

		final BPartnerLocation location = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.address1("address1")
				.build();

		assertThat(location.getOriginalOrSelf()).isEqualTo(location);
		assertThat(location.deepCopy().getOriginalOrSelf()).isEqualTo(location.deepCopy());

		location.setAddress1("address1_mod");
		assertThat(location.getOriginalOrSelf().getAddress1()).isEqualTo("address1");
		assertThat(location.getOriginalOrSelf()).isNotEqualTo(location);
		assertThat(location.deepCopy().getOriginalOrSelf()).isNotEqualTo(location.deepCopy());

		location.setAddress1("address1");
		assertThat(location.getOriginalOrSelf()).isEqualTo(location);
		assertThat(location.deepCopy().getOriginalOrSelf()).isEqualTo(location.deepCopy());
	}
}
