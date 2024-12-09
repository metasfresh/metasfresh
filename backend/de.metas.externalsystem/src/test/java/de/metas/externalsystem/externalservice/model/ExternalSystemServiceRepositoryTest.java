/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.externalservice.model;

<<<<<<< HEAD
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.ExternalSystemServiceTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.ExternalSystemServiceTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

public class ExternalSystemServiceRepositoryTest
{
	private ExternalSystemServiceRepository externalSystemServiceRepo;
=======
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class ExternalSystemServiceRepositoryTest
{
	private ExternalSystemServiceRepository externalSystemServiceRepo;
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemServiceRepo = new ExternalSystemServiceRepository();
	}

<<<<<<< HEAD
	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Test
	void givenType_whenGetAllByType_thenReturnMatchingServices()
	{
		//given
		final ExternalSystemType externalSystemType = ExternalSystemType.GRSSignum;

		ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(externalSystemType.getCode())
				.name("grs_name")
				.value("grs_service_value")
				.build();

		ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(ExternalSystemType.WOO.getCode())
				.name("woo_name")
				.value("woo_service_value")
				.build();

		//when
		final List<ExternalSystemServiceModel> result = externalSystemServiceRepo.getAllByType(externalSystemType);

		//then
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	void givenKnownValue_whenGetByValue_thenReturnMatchingService()
	{
		//given
		ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(ExternalSystemType.GRSSignum.getCode())
				.name("grs_name")
				.value("grs_service_value")
				.build();

		ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(ExternalSystemType.WOO.getCode())
				.name("woo_name")
				.value("woo_service_value")
				.build();

		//when
		final Optional<ExternalSystemServiceModel> result = externalSystemServiceRepo.getByValue("grs_service_value");

		//then
		assertThat(result).isNotEmpty();
<<<<<<< HEAD
		expect(result.get()).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	void givenUnknownValue_whenGetByValue_thenReturnEmpty()
	{
		//given
		ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(ExternalSystemType.GRSSignum.getCode())
				.name("grs_name")
				.value("grs_service_value")
				.build();

		//when
		final Optional<ExternalSystemServiceModel> result = externalSystemServiceRepo.getByValue("randomValue");

		//then
		assertThat(result).isEmpty();
	}
}
