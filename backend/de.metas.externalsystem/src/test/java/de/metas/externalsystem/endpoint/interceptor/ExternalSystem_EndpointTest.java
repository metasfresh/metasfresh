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
		// given: an SFTP endpoint with a polling interval configured
		final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
		endpoint.setTransportType(TransportType.SFTP.getCode());
		endpoint.setSftpHost("sftp.example.com");
		endpoint.setSftpPort(22);
		endpoint.setSftpUsername("sftpuser");
		endpoint.setSftpPollingIntervalMs(60_000);
		endpoint.setProcessedDirectory("/processed");
		endpoint.setErrorDirectory("/error");
		InterfaceWrapperHelper.saveRecord(endpoint);

		// when: switching the transport type to HTTP
		endpoint.setTransportType(TransportType.HTTP.getCode());
		interceptor.resetTransportSpecificFields(endpoint);

		// then: the SFTP-only polling interval is reset to the unset sentinel (0)
		assertThat(endpoint.getSftpPollingIntervalMs()).isZero();

		// and (regression-guard): the transport-agnostic directory fields are NOT cleared
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}
}
