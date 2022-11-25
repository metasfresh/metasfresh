package de.metas.bpartner.composite;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

class BPartnerCompositeTest
{

	@Test
	void extractContact()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(10);
		final BPartnerContactId bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, 10);

		final BPartnerContact contact = BPartnerContact.builder()
				.id(bpartnerContactId)
				.build();

		final BPartnerLocation location = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.build();

		final BPartnerBankAccount bankAccount = BPartnerBankAccount.builder()
				.id(BPartnerBankAccountId.ofRepoId(bpartnerId, 10))
				.iban("IBAN")
				.currencyId(CurrencyId.ofRepoId(123))
				.build();

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.bpartner(BPartner.builder().id(bpartnerId).build())
				.contact(contact)
				.location(location)
				.bankAccount(bankAccount)
				.build();

		// invoke the method under test
		final Optional<BPartnerContact> result = bpartnerComposite.extractContact(bpartnerContactId);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(contact);
	}

	@Test
	void extractBillLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(10);

		final BPartnerLocation location = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.locationType(BPartnerLocationType.builder().billTo(true).billToDefault(false).build())
				.build();
		final BPartnerLocation location2 = BPartnerLocation.builder()
				.id(BPartnerLocationId.ofRepoId(bpartnerId, 10))
				.locationType(BPartnerLocationType.builder().billTo(true).billToDefault(true).build())
				.build();

		final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
				.location(location)
				.location(location2)
				.build();

		// invoke the method under test
		final Optional<BPartnerLocation> result = bpartnerComposite.extractBillToLocation();

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(location2);
	}

}
