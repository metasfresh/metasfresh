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

package de.metas.vertical.healthcare.alberta.service.bpartner.role;

<<<<<<< HEAD
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.bpartner.BPartnerId;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRole;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleRepository;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleType;
import org.adempiere.test.AdempiereTestHelper;
<<<<<<< HEAD
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class AlbertaRoleRepositoryTest
{
	private AlbertaRoleRepository albertaRoleRepository;
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
public class AlbertaRoleRepositoryTest
{
	private AlbertaRoleRepository albertaRoleRepository;
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		albertaRoleRepository =  new AlbertaRoleRepository();
	}

<<<<<<< HEAD
	@BeforeAll
	public static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	public static void afterAll()
	{
		validateSnapshots();
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Test
	public void save()
	{
		//given
		final AlbertaRole role = AlbertaRole.builder()
				.bPartnerId(BPartnerId.ofRepoId(1))
				.role(AlbertaRoleType.HealthInsurance)
				.build();

		//when
		final AlbertaRole result = albertaRoleRepository.save(role);

		//then
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
