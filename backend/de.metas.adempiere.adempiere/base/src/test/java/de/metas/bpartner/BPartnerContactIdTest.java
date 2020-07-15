package de.metas.bpartner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class BPartnerContactIdTest
{
	@Test
	public void test_standardCase()
	{
		final BPartnerContactId contactId = BPartnerContactId.ofRepoId(1, 2);
		assertThat(contactId.getBpartnerId()).isEqualTo(BPartnerId.ofRepoId(1));
		assertThat(contactId.getUserId()).isEqualTo(UserId.ofRepoId(2));
	}

	@Test
	public void systemUserIsNotAValidContact()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(1);

		assertThat(BPartnerContactId.ofRepoIdOrNull(bpartnerId, 123)).isNotNull();
		assertThat(BPartnerContactId.ofRepoIdOrNull(bpartnerId, UserId.SYSTEM.getRepoId())).isNull();
	}

}
