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

package de.metas.externalsystem.endpoint.interceptor;

import de.metas.externalsystem.endpoint.TransportType;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalSystem_EndpointTest
{
	private ExternalSystem_Endpoint interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new ExternalSystem_Endpoint();
	}

	@Test
	void resetTransportSpecificFields_switchFromSftpToHttp_resetsSftpPollingIntervalMs()
	{
		// given: an SFTP endpoint with a polling interval configured, plus stale local-file settings
		final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
		endpoint.setTransportType(TransportType.SFTP.getCode());
		endpoint.setSftpHost("sftp.example.com");
		endpoint.setSftpPort(22);
		endpoint.setSftpUsername("sftpuser");
		endpoint.setSftpPollingIntervalMs(60_000);
		endpoint.setLocalRootLocation("/data/in");
		endpoint.setFrequency(5000);
		endpoint.setImportFileNamePattern("{filename}_{timestamp}");
		endpoint.setProcessedDirectory("/processed");
		endpoint.setErrorDirectory("/error");
		InterfaceWrapperHelper.saveRecord(endpoint);

		// when: switching the transport type to HTTP
		endpoint.setTransportType(TransportType.HTTP.getCode());
		interceptor.resetTransportSpecificFields(endpoint);

		// then: the SFTP-only polling interval is reset to the unset sentinel (0)
		assertThat(endpoint.getSftpPollingIntervalMs()).isZero();

		// and: the local-file-only fields are cleared
		assertThat(endpoint.getLocalRootLocation()).isNull();
		assertThat(endpoint.getFrequency()).isZero();
		assertThat(endpoint.getImportFileNamePattern()).isNull();

		// and (regression-guard): the transport-agnostic directory fields are NOT cleared
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}

	@Test
	void resetTransportSpecificFields_switchToSftp_resetsLocalFileFields()
	{
		// given: an HTTP endpoint carrying stale local-file settings from a prior transport
		final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
		endpoint.setTransportType(TransportType.HTTP.getCode());
		endpoint.setHttpEndPoint("https://example.com/api");
		endpoint.setLocalRootLocation("/data/in");
		endpoint.setFrequency(5000);
		endpoint.setImportFileNamePattern("{filename}_{timestamp}");
		endpoint.setProcessedDirectory("/processed");
		endpoint.setErrorDirectory("/error");
		InterfaceWrapperHelper.saveRecord(endpoint);

		// when: switching the transport type to SFTP
		endpoint.setTransportType(TransportType.SFTP.getCode());
		interceptor.resetTransportSpecificFields(endpoint);

		// then: the local-file-only fields are cleared
		assertThat(endpoint.getLocalRootLocation()).isNull();
		assertThat(endpoint.getFrequency()).isZero();
		assertThat(endpoint.getImportFileNamePattern()).isNull();

		// and (regression-guard): the transport-agnostic directory fields are NOT cleared
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}

	@Test
	void resetTransportSpecificFields_switchToLocalFile_resetsHttpAndSftpFields()
	{
		// given: an endpoint carrying stale HTTP and SFTP settings from a prior transport
		final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
		endpoint.setTransportType(TransportType.HTTP.getCode());
		endpoint.setHttpEndPoint("https://example.com/api");
		endpoint.setOutboundHttpMethod("POST");
		endpoint.setContentType("application/json");
		endpoint.setAuthType("Basic");
		endpoint.setAuthToken("token");
		endpoint.setLoginUsername("user");
		endpoint.setPassword("secret");
		endpoint.setClientId("clientId");
		endpoint.setClientSecret("clientSecret");
		endpoint.setSasSignature("sasSignature");
		endpoint.setSftpHost("sftp.example.com");
		endpoint.setSftpPort(22);
		endpoint.setSftpUsername("sftpuser");
		endpoint.setSftpAuthType("PASSWORD");
		endpoint.setSshPrivateKey("private-key");
		endpoint.setSftpRemotePath("/upload");
		endpoint.setSftpFilenamePattern("order_{date}.edi");
		endpoint.setSftpPollingIntervalMs(60_000);
		endpoint.setProcessedDirectory("/processed");
		endpoint.setErrorDirectory("/error");
		InterfaceWrapperHelper.saveRecord(endpoint);

		// when: switching the transport type to LOCAL_FILE
		endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
		interceptor.resetTransportSpecificFields(endpoint);

		// then: every HTTP-specific field is cleared
		assertThat(endpoint.getHttpEndPoint()).isNull();
		assertThat(endpoint.getOutboundHttpMethod()).isNull();
		assertThat(endpoint.getContentType()).isNull();
		assertThat(endpoint.getAuthType()).isNull();
		assertThat(endpoint.getAuthToken()).isNull();
		assertThat(endpoint.getLoginUsername()).isNull();
		assertThat(endpoint.getPassword()).isNull();
		assertThat(endpoint.getClientId()).isNull();
		assertThat(endpoint.getClientSecret()).isNull();
		assertThat(endpoint.getSasSignature()).isNull();

		// and: every SFTP-specific field is cleared
		assertThat(endpoint.getSftpHost()).isNull();
		assertThat(endpoint.getSftpPort()).isZero();
		assertThat(endpoint.getSftpUsername()).isNull();
		assertThat(endpoint.getSftpAuthType()).isNull();
		assertThat(endpoint.getSshPrivateKey()).isNull();
		assertThat(endpoint.getSftpRemotePath()).isNull();
		assertThat(endpoint.getSftpFilenamePattern()).isNull();
		assertThat(endpoint.getSftpPollingIntervalMs()).isZero();

		// and (regression-guard): the transport-agnostic directory fields are NOT cleared
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}
}
