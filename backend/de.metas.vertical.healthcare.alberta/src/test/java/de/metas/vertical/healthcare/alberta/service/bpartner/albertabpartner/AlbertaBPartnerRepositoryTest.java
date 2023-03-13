/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.service.bpartner.albertabpartner;

import au.com.origin.snapshots.Expect;

import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.bpartner.BPartnerId;
import de.metas.vertical.healthcare.alberta.bpartner.albertabpartner.AlbertaBPartner;
import de.metas.vertical.healthcare.alberta.bpartner.albertabpartner.AlbertaBPartnerRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;


@ExtendWith(SnapshotExtension.class)
public class AlbertaBPartnerRepositoryTest
{
	private AlbertaBPartnerRepository albertaBPartnerRepository;
	private Expect expect;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		albertaBPartnerRepository =  new AlbertaBPartnerRepository();
	}

	@Test
	public void save_allFields()
	{
		//given
		final AlbertaBPartner bPartner = AlbertaBPartner.builder()
				.bPartnerId(BPartnerId.ofRepoId(1))
				.isArchived(true)
				.title("title")
				.titleShort("titleShort")
				.timestamp(Instant.parse("2019-11-22T00:00:00Z"))
				.build();

		//when
		final AlbertaBPartner result = albertaBPartnerRepository.save(bPartner);

		//then
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}


	@Test
	public void save_onlyMandatory()
	{
		//given
		final AlbertaBPartner bPartner = AlbertaBPartner.builder()
				.bPartnerId(BPartnerId.ofRepoId(1))
				.build();

		//when
		final AlbertaBPartner result = albertaBPartnerRepository.save(bPartner);

		//then
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}
}
