package de.metas.contracts.commission.commissioninstance.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.contracts
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

class BeneficiaryTest
{
	@Test
	void serialize_deserialize()
	{
		final JSONObjectMapper<Beneficiary> objectMapper = JSONObjectMapper.forClass(Beneficiary.class);

		final Beneficiary original = Beneficiary.of(BPartnerId.ofRepoId(20));
		final String json = objectMapper.writeValueAsString(original);

		final Beneficiary result = objectMapper.readValue(json);

		assertThat(result).isEqualTo(original);
	}
}