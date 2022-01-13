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

package de.metas.externalsystem.process.runtimeparameters;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_RuntimeParameter;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class RuntimeParametersRepositoryTest
{
	private RuntimeParametersRepository runtimeParametersRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		runtimeParametersRepository = new RuntimeParametersRepository();
	}

	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void upsertRuntimeParameter_new()
	{
		final RuntimeParameterUpsertRequest runtimeParameterUpsertRequest = RuntimeParameterUpsertRequest.builder()
				.value("value")
				.runtimeParamUniqueKey(RuntimeParamUniqueKey.builder()
											   .externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoId(1))
											   .name("name")
											   .request("request")
											   .build()
				)
				.build();

		final RuntimeParameter result = runtimeParametersRepository.upsertRuntimeParameter(runtimeParameterUpsertRequest);

		assertThat(result).isNotNull();
		expect(result).toMatchSnapshot();
	}

	@Test
	public void upsertRuntimeParameter_update()
	{
		//first run
		final RuntimeParamUniqueKey runtimeParamUniqueKey = RuntimeParamUniqueKey.builder()
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoId(1))
				.name("name")
				.request("request")
				.build();

		final RuntimeParameterUpsertRequest runtimeParameterUpsertRequest = RuntimeParameterUpsertRequest.builder()
				.value("value")
				.runtimeParamUniqueKey(runtimeParamUniqueKey)
				.build();

		runtimeParametersRepository.upsertRuntimeParameter(runtimeParameterUpsertRequest);

		//second run
		final RuntimeParameterUpsertRequest runtimeParameterUpsertRequest_update = RuntimeParameterUpsertRequest.builder()
				.value("value_updated")
				.runtimeParamUniqueKey(runtimeParamUniqueKey)
				.build();

		final RuntimeParameter runtimeParameter = runtimeParametersRepository.upsertRuntimeParameter(runtimeParameterUpsertRequest_update);

		final List<I_ExternalSystem_RuntimeParameter> allParams = Services.get(IQueryBL.class).createQueryBuilder(I_ExternalSystem_RuntimeParameter.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		assertThat(allParams).isNotNull();
		assertThat(allParams.size()).isEqualTo(1);
		assertThat(allParams.get(0).getExternalSystem_RuntimeParameter_ID()).isEqualTo(runtimeParameter.getRuntimeParameterId().getRepoId());
		expect(runtimeParameter).toMatchSnapshot();
	}

	@Test
	public void getByConfigIdAndRequest()
	{
		//given
		final ExternalSystemParentConfigId targetConfigId = ExternalSystemParentConfigId.ofRepoId(1);
		final String targetRequest = "request";

		createRuntimeParamRecord(targetConfigId, targetRequest, "name1", "value1");
		createRuntimeParamRecord(targetConfigId, targetRequest, "name2", "value2");
		createRuntimeParamRecord(ExternalSystemParentConfigId.ofRepoId(2), "notOurTargetRequest", "name3", "value3");

		//when
		final ImmutableList<RuntimeParameter> runtimeParameters = runtimeParametersRepository.getByConfigIdAndRequest(targetConfigId, targetRequest);

		//then
		assertThat(runtimeParameters).isNotNull();
		assertThat(runtimeParameters.size()).isEqualTo(2);
		expect(runtimeParameters).toMatchSnapshot();
	}

	private void createRuntimeParamRecord(final ExternalSystemParentConfigId configId,
			final String request,
			final String name,
			final String value)
	{
		final I_ExternalSystem_RuntimeParameter record = InterfaceWrapperHelper.newInstance(I_ExternalSystem_RuntimeParameter.class);

		record.setExternalSystem_Config_ID(configId.getRepoId());
		record.setExternal_Request(request);
		record.setName(name);
		record.setValue(value);

		saveRecord(record);
	}
}
