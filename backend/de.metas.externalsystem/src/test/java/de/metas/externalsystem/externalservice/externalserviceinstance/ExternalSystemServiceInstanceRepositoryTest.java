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

package de.metas.externalsystem.externalservice.externalserviceinstance;

<<<<<<< HEAD
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.externalservice.ExternalSystemServiceTestHelper;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceId;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceRepository;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.externalsystem.model.X_ExternalSystem_Status;
import org.adempiere.test.AdempiereTestHelper;
<<<<<<< HEAD
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

<<<<<<< HEAD
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

public class ExternalSystemServiceInstanceRepositoryTest
{
	private ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepo;
=======
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class ExternalSystemServiceInstanceRepositoryTest
{
	private ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepo;
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemServiceInstanceRepo = new ExternalSystemServiceInstanceRepository(new ExternalSystemServiceRepository());
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
	void givenValidIds_whenGetByConfigAndServiceId_thenReturnInstance()
	{
		//given
		final ExternalSystemParentConfigId configId = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemServiceId serviceId = ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.value("grs_service_value")
				.build();

		ExternalSystemServiceTestHelper.createExternalServiceInstanceBuilder()
				.configId(configId.getRepoId())
				.serviceId(serviceId.getRepoId())
				.expectedStatus(X_ExternalSystem_Status.EXTERNALSYSTEMSTATUS_Inactive)
				.build();

		//when
		final Optional<ExternalSystemServiceInstance> result = externalSystemServiceInstanceRepo.getByConfigAndServiceId(configId, serviceId);

		//then
		assertThat(result).isNotEmpty();
<<<<<<< HEAD
		expect(result.get()).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	void givenUnknownIds_whenGetByConfigAndServiceId_thenReturnEmpty()
	{
		//given
		final ExternalSystemParentConfigId configId = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemServiceId serviceId = ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.value("grs_service_value")
				.build();

		ExternalSystemServiceTestHelper.createExternalServiceInstanceBuilder()
				.configId(configId.getRepoId())
				.serviceId(serviceId.getRepoId())
				.expectedStatus(X_ExternalSystem_Status.EXTERNALSYSTEMSTATUS_Inactive)
				.build();

		//when
		final Optional<ExternalSystemServiceInstance> result = externalSystemServiceInstanceRepo.getByConfigAndServiceId(ExternalSystemParentConfigId.ofRepoId(1), ExternalSystemServiceId.ofRepoId(1));

		//then
		assertThat(result).isEmpty();
	}

	@Test
	void givenInstanceRequest_whenCreate_thenCreateAndReturnInstance()
	{

		//given
		final ExternalSystemParentConfigId configId = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemServiceId serviceId = ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.value("grs_service_value")
				.build();

		final CreateServiceInstanceRequest request = CreateServiceInstanceRequest.builder()
				.configId(configId)
				.serviceId(serviceId)
				.expectedStatus(ExternalStatus.INACTIVE)
				.build();

		//when
		final ExternalSystemServiceInstance result = externalSystemServiceInstanceRepo.create(request);

		//then
		assertThat(result).isNotNull();
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	void givenInstance_whenUpdate_thenUpdateAndReturnInstance()
	{

		//given
		final ExternalSystemParentConfigId configId = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemServiceId serviceId = ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.value("grs_service_value")
				.build();

		final CreateServiceInstanceRequest request = CreateServiceInstanceRequest.builder()
				.configId(configId)
				.serviceId(serviceId)
				.expectedStatus(ExternalStatus.INACTIVE)
				.build();

		final ExternalSystemServiceInstance created = externalSystemServiceInstanceRepo.create(request);

		//when
		final ExternalSystemServiceInstance updated = externalSystemServiceInstanceRepo.update(created.toBuilder().expectedStatus(ExternalStatus.ACTIVE).build());

		//then
		assertThat(updated).isNotNull();
<<<<<<< HEAD
		expect(updated).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(updated);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Test
	void givenConfigIds_whenStreamByConfigIds_thenReturnMatchingInstances()
	{

		//given
		final ExternalSystemParentConfigId configId_1 = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemParentConfigId configId_2 = ExternalSystemServiceTestHelper.createExternalConfigBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.build();

		final ExternalSystemServiceId serviceId = ExternalSystemServiceTestHelper.createExternalSystemServiceBuilder()
				.type(X_ExternalSystem_Config.TYPE_GRSSignum)
				.name("Name")
				.value("grs_service_value")
				.build();

		ExternalSystemServiceTestHelper.createExternalServiceInstanceBuilder()
				.configId(configId_1.getRepoId())
				.serviceId(serviceId.getRepoId())
				.expectedStatus(X_ExternalSystem_Status.EXTERNALSYSTEMSTATUS_Inactive)
				.build();

		ExternalSystemServiceTestHelper.createExternalServiceInstanceBuilder()
				.configId(configId_2.getRepoId())
				.serviceId(serviceId.getRepoId())
				.expectedStatus(X_ExternalSystem_Status.EXTERNALSYSTEMSTATUS_Inactive)
				.build();

		//when
		final List<ExternalSystemServiceInstance> result = externalSystemServiceInstanceRepo.streamByConfigIds(ImmutableSet.of(configId_1))
				.collect(Collectors.toList());

		//then
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
