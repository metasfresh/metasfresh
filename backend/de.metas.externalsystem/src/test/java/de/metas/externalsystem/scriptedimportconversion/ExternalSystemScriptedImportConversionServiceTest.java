/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedimportconversion;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedImportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.externalsystem.model.X_ExternalSystem_Endpoint;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PROCESSED_DIR;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_DIR;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExternalSystemScriptedImportConversionServiceTest
{
	private ExternalSystemScriptedImportConversionService service;
	private UserAuthTokenRepository userAuthTokenRepository;
	private ExternalSystemConfigRepo externalSystemConfigRepo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		userAuthTokenRepository = new UserAuthTokenRepository();
		externalSystemConfigRepo = ExternalSystemConfigRepo.newInstanceForUnitTesting();
		service = new ExternalSystemScriptedImportConversionService(userAuthTokenRepository, new ExternalSystemEndpointRepository(), externalSystemConfigRepo);
	}

	@Test
	void resolveCommands_parentWithSftpAndHttpChildren_derivesCommandPerEndpoint()
	{
		// given a parent with two active children: one bound to an SFTP endpoint, one to an HTTP endpoint
		final ExternalSystemParentConfigId parentId = ExternalSystemParentConfigId.ofRepoId(1);
		final UserId userImportId = createUserId();

		final ExternalSystemEndpointId sftpEndpointId = createEndpoint("eddyson-sftp", X_ExternalSystem_Endpoint.TRANSPORTTYPE_SFTP);
		final ExternalSystemEndpointId httpEndpointId = createEndpoint("eddyson-rest", X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);

		final ExternalSystemScriptedImportConversionConfigId sftpChildId = createChild(parentId, "ORDERS-SFTP", sftpEndpointId, userImportId);
		final ExternalSystemScriptedImportConversionConfigId httpChildId = createChild(parentId, "ORDERS-REST", httpEndpointId, userImportId);

		// when: a single Start run on the parent
		final Map<Integer, ScriptedImportConversionCommand> started = service.resolveCommands(parentId, null, ScriptedImportConversionIntent.Start)
				.stream()
				.collect(ImmutableMap.toImmutableMap(rc -> rc.getConfig().getId().getRepoId(), ExternalSystemScriptedImportConversionService.ResolvedChildCommand::getCommand));

		// then: transport is derived per child from its own endpoint
		assertThat(started).hasSize(2);
		assertThat(started.get(sftpChildId.getRepoId())).isEqualTo(ScriptedImportConversionCommand.EnableSftpPolling);
		assertThat(started.get(httpChildId.getRepoId())).isEqualTo(ScriptedImportConversionCommand.EnableRestAPI);

		// and Stop yields the disable variants
		final Map<Integer, ScriptedImportConversionCommand> stopped = service.resolveCommands(parentId, null, ScriptedImportConversionIntent.Stop)
				.stream()
				.collect(ImmutableMap.toImmutableMap(rc -> rc.getConfig().getId().getRepoId(), ExternalSystemScriptedImportConversionService.ResolvedChildCommand::getCommand));
		assertThat(stopped.get(sftpChildId.getRepoId())).isEqualTo(ScriptedImportConversionCommand.DisableSftpPolling);
		assertThat(stopped.get(httpChildId.getRepoId())).isEqualTo(ScriptedImportConversionCommand.DisableRestAPI);
	}

	@Test
	void resolveCommands_singleChildById_derivesFromThatChildEndpoint()
	{
		final ExternalSystemParentConfigId parentId = ExternalSystemParentConfigId.ofRepoId(1);
		final UserId userImportId = createUserId();
		final ExternalSystemEndpointId sftpEndpointId = createEndpoint("eddyson-sftp", X_ExternalSystem_Endpoint.TRANSPORTTYPE_SFTP);
		final ExternalSystemScriptedImportConversionConfigId sftpChildId = createChild(parentId, "ORDERS-SFTP", sftpEndpointId, userImportId);

		final ImmutableList<ExternalSystemScriptedImportConversionService.ResolvedChildCommand> resolved =
				service.resolveCommands(null, sftpChildId, ScriptedImportConversionIntent.Start);

		assertThat(resolved).hasSize(1);
		assertThat(resolved.get(0).getCommand()).isEqualTo(ScriptedImportConversionCommand.EnableSftpPolling);
	}

	@Test
	void getParameters_endpointNameParam_comesFromLinkedEndpointValue()
	{
		// given
		final UserId userImportId = createUserId();
		userAuthTokenRepository.createNew(CreateUserAuthTokenRequest.builder()
				.userId(userImportId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.roleId(RoleId.WEBUI)
				.build());

		final I_ExternalSystem_Endpoint endpointRecord = newInstance(I_ExternalSystem_Endpoint.class);
		endpointRecord.setValue("eddyson-orders");
		endpointRecord.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);
		endpointRecord.setAuthType(X_ExternalSystem_Endpoint.AUTHTYPE_Token);
		endpointRecord.setIsArrayFanOut(false);
		saveRecord(endpointRecord);

		final ExternalSystemScriptedImportConversionConfig config = ExternalSystemScriptedImportConversionConfig.builder()
				.id(ExternalSystemScriptedImportConversionConfigId.ofRepoId(1))
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.value("scriptedImportValue")
				.scriptIdentifier("scriptId")
				.userImportId(userImportId)
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID()))
				.build();

		// when
		final Map<String, String> parameters = service.getParameters(config);

		// then
		assertThat(parameters.get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME)).isEqualTo("eddyson-orders");
		// and the stable per-child route key is derived from the child config id (NOT the endpoint value),
		// so a later endpoint change cannot orphan the previously-started camel poll route.
		assertThat(parameters.get(PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY)).isEqualTo("ScriptedImportConversion-1");
	}

	@Test
	void getParameters_sftpPollingSettings_comeFromLinkedEndpoint()
	{
		// given: the SFTP polling settings live on the ENDPOINT (moved off the config)
		final UserId userImportId = createUserId();
		userAuthTokenRepository.createNew(CreateUserAuthTokenRequest.builder()
				.userId(userImportId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.roleId(RoleId.WEBUI)
				.build());

		final I_ExternalSystem_Endpoint endpointRecord = newInstance(I_ExternalSystem_Endpoint.class);
		endpointRecord.setValue("eddyson-sftp");
		endpointRecord.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_SFTP);
		endpointRecord.setSftpPollingIntervalMs(30000);
		endpointRecord.setProcessedDirectory("/inbound/processed");
		endpointRecord.setErrorDirectory("/inbound/error");
		endpointRecord.setIsArrayFanOut(false);
		saveRecord(endpointRecord);

		final ExternalSystemScriptedImportConversionConfig config = ExternalSystemScriptedImportConversionConfig.builder()
				.id(ExternalSystemScriptedImportConversionConfigId.ofRepoId(1))
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.value("scriptedImportValue")
				.scriptIdentifier("scriptId")
				.userImportId(userImportId)
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID()))
				.build();

		// when
		final Map<String, String> parameters = service.getParameters(config);

		// then: the poll interval + processed/error directories are sourced from the endpoint
		assertThat(parameters.get(PARAM_SFTP_POLLING_INTERVAL_MS)).isEqualTo("30000");
		assertThat(parameters.get(PARAM_PROCESSED_DIR)).isEqualTo("/inbound/processed");
		assertThat(parameters.get(PARAM_ERROR_DIR)).isEqualTo("/inbound/error");
	}

	@Test
	void getParameters_processedErrorDirs_includedRegardlessOfTransport()
	{
		// given: an HTTP (REST) endpoint with the LOCAL processed/error dirs set — these are
		// transport-agnostic (used by both SFTP and REST local archiving), not SFTP-only
		final UserId userImportId = createUserId();
		userAuthTokenRepository.createNew(CreateUserAuthTokenRequest.builder()
				.userId(userImportId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.roleId(RoleId.WEBUI)
				.build());

		final I_ExternalSystem_Endpoint endpointRecord = newInstance(I_ExternalSystem_Endpoint.class);
		endpointRecord.setValue("eddyson-rest");
		endpointRecord.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);
		endpointRecord.setAuthType(X_ExternalSystem_Endpoint.AUTHTYPE_Token);
		endpointRecord.setProcessedDirectory("/local/rest/processed");
		endpointRecord.setErrorDirectory("/local/rest/error");
		endpointRecord.setIsArrayFanOut(false);
		saveRecord(endpointRecord);

		final ExternalSystemScriptedImportConversionConfig config = ExternalSystemScriptedImportConversionConfig.builder()
				.id(ExternalSystemScriptedImportConversionConfigId.ofRepoId(1))
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.value("scriptedImportValue")
				.scriptIdentifier("scriptId")
				.userImportId(userImportId)
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID()))
				.build();

		// when
		final Map<String, String> parameters = service.getParameters(config);

		// then: the local archive dirs are present even though the endpoint is HTTP, not SFTP
		assertThat(parameters.get(PARAM_PROCESSED_DIR)).isEqualTo("/local/rest/processed");
		assertThat(parameters.get(PARAM_ERROR_DIR)).isEqualTo("/local/rest/error");
	}

	@Test
	void getParameters_importeurWithoutWebuiToken_throwsClearError()
	{
		// given: an Importeur that has NO WEBUI auth token
		final UserId userImportId = createUserId();

		final I_ExternalSystem_Endpoint endpointRecord = newInstance(I_ExternalSystem_Endpoint.class);
		endpointRecord.setValue("eddyson-orders");
		endpointRecord.setTransportType(X_ExternalSystem_Endpoint.TRANSPORTTYPE_HTTP);
		endpointRecord.setAuthType(X_ExternalSystem_Endpoint.AUTHTYPE_Token);
		endpointRecord.setIsArrayFanOut(false);
		saveRecord(endpointRecord);

		final ExternalSystemScriptedImportConversionConfig config = ExternalSystemScriptedImportConversionConfig.builder()
				.id(ExternalSystemScriptedImportConversionConfigId.ofRepoId(1))
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.value("scriptedImportValue")
				.scriptIdentifier("scriptId")
				.userImportId(userImportId)
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID()))
				.build();

		// when / then: a clear, actionable error naming the Importeur + the missing WEBUI token
		// (not the obscure "Invalid token (1)" the token repo would otherwise raise)
		assertThatThrownBy(() -> service.getParameters(config))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("WEBUI")
				.hasMessageContaining(String.valueOf(userImportId.getRepoId()));
	}

	private static UserId createUserId()
	{
		final I_AD_User record = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		record.setAD_Language("de_DE");
		InterfaceWrapperHelper.save(record);
		return UserId.ofRepoId(record.getAD_User_ID());
	}

	private static ExternalSystemEndpointId createEndpoint(final String value, final String transportType)
	{
		final I_ExternalSystem_Endpoint endpointRecord = newInstance(I_ExternalSystem_Endpoint.class);
		endpointRecord.setValue(value);
		endpointRecord.setTransportType(transportType);
		endpointRecord.setIsArrayFanOut(false);
		saveRecord(endpointRecord);
		return ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID());
	}

	private static ExternalSystemScriptedImportConversionConfigId createChild(
			final ExternalSystemParentConfigId parentId,
			final String value,
			final ExternalSystemEndpointId endpointId,
			final UserId userImportId)
	{
		final I_ExternalSystem_Config_ScriptedImportConversion childRecord = newInstance(I_ExternalSystem_Config_ScriptedImportConversion.class);
		childRecord.setExternalSystem_Config_ID(parentId.getRepoId());
		childRecord.setExternalSystemValue(value);
		childRecord.setScriptIdentifier("echo");
		childRecord.setAD_User_Import_ID(userImportId.getRepoId());
		childRecord.setExternalSystem_Endpoint_ID(endpointId.getRepoId());
		childRecord.setIsActive(true);
		saveRecord(childRecord);
		return ExternalSystemScriptedImportConversionConfigId.ofRepoId(childRecord.getExternalSystem_Config_ScriptedImportConversion_ID());
	}
}
