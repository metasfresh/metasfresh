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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the full HTTP / SFTP / LOCAL_FILE transport matrix for
 * {@link ExternalSystem_Endpoint#resetTransportSpecificFields(I_ExternalSystem_Endpoint)}: for each of the three
 * transports, switching TO it must clear every column owned by the OTHER two transports (never the
 * transport-agnostic ProcessedDirectory/ErrorDirectory) and must NOT clear the columns it owns itself.
 */
public class ExternalSystem_EndpointTest
{
	private ExternalSystem_Endpoint interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new ExternalSystem_Endpoint();
	}

	private static void setAllHttpFields(final I_ExternalSystem_Endpoint endpoint)
	{
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
		endpoint.setOAuthTokenUrl("https://example.com/oauth/token");
		endpoint.setOAuthScope("docuware.platform");
		endpoint.setIsFileUpload(true);
		endpoint.setIsArrayFanOut(true);
	}

	private static void assertAllHttpFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
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
		assertThat(endpoint.getOAuthTokenUrl()).isNull();
		assertThat(endpoint.getOAuthScope()).isNull();
		assertThat(endpoint.isFileUpload()).isFalse();
		assertThat(endpoint.isArrayFanOut()).isFalse();
	}

	/**
	 * Same as {@link #assertAllHttpFieldsCleared(I_ExternalSystem_Endpoint)}, for the switch-to-SFTP case
	 * specifically, where two of the HTTP-set columns are NOT actually HTTP-only:
	 * <ul>
	 * <li>{@code IsArrayFanOut} is also read by the SFTP outbound dispatch, so SFTP owns it too and it must
	 * survive the switch (must NOT be cleared) — this is the one behavioural difference from
	 * {@link #assertAllHttpFieldsCleared(I_ExternalSystem_Endpoint)}.</li>
	 * <li>{@code Password} is left un-asserted here on purpose: the production code still clears it on this
	 * switch (pre-existing, unchanged by this method), but a fixture with {@code SftpAuthType=PASSWORD}
	 * needs a password for SFTP password auth to work at all, so asserting the clear here would read as an
	 * endorsement of a valid-looking-but-broken SFTP config. Switching a saved endpoint to SFTP therefore
	 * leaves it permanently invalid until a password is re-entered; that is pre-existing behaviour and is
	 * deliberately not changed here.</li>
	 * </ul>
	 */
	private static void assertHttpFieldsClearedForSftpSwitch(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getHttpEndPoint()).isNull();
		assertThat(endpoint.getOutboundHttpMethod()).isNull();
		assertThat(endpoint.getContentType()).isNull();
		assertThat(endpoint.getAuthType()).isNull();
		assertThat(endpoint.getAuthToken()).isNull();
		assertThat(endpoint.getLoginUsername()).isNull();
		assertThat(endpoint.getClientId()).isNull();
		assertThat(endpoint.getClientSecret()).isNull();
		assertThat(endpoint.getSasSignature()).isNull();
		assertThat(endpoint.getOAuthTokenUrl()).isNull();
		assertThat(endpoint.getOAuthScope()).isNull();
		assertThat(endpoint.isFileUpload()).isFalse();
		// SFTP owns IsArrayFanOut too (the outbound dispatch reads it regardless of transport) — must survive
		assertThat(endpoint.isArrayFanOut()).isTrue();
	}

	private static void assertAllHttpFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getHttpEndPoint()).isEqualTo("https://example.com/api");
		assertThat(endpoint.getOutboundHttpMethod()).isEqualTo("POST");
		assertThat(endpoint.getContentType()).isEqualTo("application/json");
		assertThat(endpoint.getAuthType()).isEqualTo("Basic");
		assertThat(endpoint.getAuthToken()).isEqualTo("token");
		assertThat(endpoint.getLoginUsername()).isEqualTo("user");
		assertThat(endpoint.getPassword()).isEqualTo("secret");
		assertThat(endpoint.getClientId()).isEqualTo("clientId");
		assertThat(endpoint.getClientSecret()).isEqualTo("clientSecret");
		assertThat(endpoint.getSasSignature()).isEqualTo("sasSignature");
		assertThat(endpoint.getOAuthTokenUrl()).isEqualTo("https://example.com/oauth/token");
		assertThat(endpoint.getOAuthScope()).isEqualTo("docuware.platform");
		assertThat(endpoint.isFileUpload()).isTrue();
		assertThat(endpoint.isArrayFanOut()).isTrue();
	}

	private static void setAllSftpFields(final I_ExternalSystem_Endpoint endpoint)
	{
		endpoint.setSftpHost("sftp.example.com");
		endpoint.setSftpPort(22);
		endpoint.setSftpUsername("sftpuser");
		endpoint.setSftpAuthType("PASSWORD");
		endpoint.setSshPrivateKey("private-key");
		endpoint.setSftpRemotePath("/upload");
		endpoint.setSftpFilenamePattern("order_{date}.edi");
		endpoint.setSftpPollingIntervalMs(60_000);
	}

	private static void assertAllSftpFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isNull();
		assertThat(endpoint.getSftpPort()).isZero();
		assertThat(endpoint.getSftpUsername()).isNull();
		assertThat(endpoint.getSftpAuthType()).isNull();
		assertThat(endpoint.getSshPrivateKey()).isNull();
		assertThat(endpoint.getSftpRemotePath()).isNull();
		assertThat(endpoint.getSftpFilenamePattern()).isNull();
		assertThat(endpoint.getSftpPollingIntervalMs()).isZero();
	}

	private static void assertAllSftpFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isEqualTo("sftp.example.com");
		assertThat(endpoint.getSftpPort()).isEqualTo(22);
		assertThat(endpoint.getSftpUsername()).isEqualTo("sftpuser");
		assertThat(endpoint.getSftpAuthType()).isEqualTo("PASSWORD");
		assertThat(endpoint.getSshPrivateKey()).isEqualTo("private-key");
		assertThat(endpoint.getSftpRemotePath()).isEqualTo("/upload");
		assertThat(endpoint.getSftpFilenamePattern()).isEqualTo("order_{date}.edi");
		assertThat(endpoint.getSftpPollingIntervalMs()).isEqualTo(60_000);
	}

	private static void setAllLocalFileFields(final I_ExternalSystem_Endpoint endpoint)
	{
		endpoint.setLocalRootLocation("/data/in");
		endpoint.setFrequency(5000);
		endpoint.setImportFileNamePattern("{filename}_{timestamp}");
	}

	private static void assertAllLocalFileFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getLocalRootLocation()).isNull();
		assertThat(endpoint.getFrequency()).isZero();
		assertThat(endpoint.getImportFileNamePattern()).isNull();
	}

	private static void assertAllLocalFileFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getLocalRootLocation()).isEqualTo("/data/in");
		assertThat(endpoint.getFrequency()).isEqualTo(5000);
		assertThat(endpoint.getImportFileNamePattern()).isEqualTo("{filename}_{timestamp}");
	}

	private static void assertDirectoriesPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		// regression-guard: the transport-agnostic directory fields are NEVER cleared, regardless of transport
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}

	/**
	 * The full HTTP / SFTP / LOCAL_FILE transport matrix for
	 * {@link ExternalSystem_Endpoint#resetTransportSpecificFields(I_ExternalSystem_Endpoint)}.
	 */
	@Nested
	class ResetTransportSpecificFields
	{
		@Test
		void switchToHttp_clearsSftpAndLocalFileFields_keepsHttpFields()
		{
			// given: an SFTP endpoint carrying its own SFTP settings plus stale local-file settings
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			setAllLocalFileFields(endpoint);
			endpoint.setProcessedDirectory("/processed");
			endpoint.setErrorDirectory("/error");
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: switching the transport type to HTTP and filling in the new transport's own fields
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			interceptor.resetTransportSpecificFields(endpoint);

			// then: every SFTP-specific and LOCAL_FILE-specific field is cleared ...
			assertAllSftpFieldsCleared(endpoint);
			assertAllLocalFileFieldsCleared(endpoint);
			// ... the just-entered HTTP fields (including OAuth2 + file-upload + array-fan-out) survive ...
			assertAllHttpFieldsPreserved(endpoint);
			// ... and the transport-agnostic directories are untouched
			assertDirectoriesPreserved(endpoint);
		}

		@Test
		void switchToSftp_clearsHttpOnlyAndLocalFileFields_keepsSftpFieldsAndSharedArrayFanOut()
		{
			// given: an HTTP/OAuth endpoint carrying its own HTTP settings plus stale local-file settings
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			setAllLocalFileFields(endpoint);
			endpoint.setProcessedDirectory("/processed");
			endpoint.setErrorDirectory("/error");
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: switching the transport type to SFTP and filling in the new transport's own fields
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			interceptor.resetTransportSpecificFields(endpoint);

			// then: every HTTP-only field is cleared, but IsArrayFanOut survives -- SFTP owns it too (see
			// assertHttpFieldsClearedForSftpSwitch) ...
			assertHttpFieldsClearedForSftpSwitch(endpoint);
			// ... every LOCAL_FILE-specific field is cleared too ...
			assertAllLocalFileFieldsCleared(endpoint);
			// ... the just-entered SFTP fields survive ...
			assertAllSftpFieldsPreserved(endpoint);
			// ... and the transport-agnostic directories are untouched
			assertDirectoriesPreserved(endpoint);
		}

		@Test
		void switchToLocalFile_clearsHttpAndSftpFields_keepsLocalFileFields()
		{
			// given: an endpoint carrying stale HTTP/OAuth and SFTP settings from prior transports
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			setAllSftpFields(endpoint);
			endpoint.setProcessedDirectory("/processed");
			endpoint.setErrorDirectory("/error");
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: switching the transport type to LOCAL_FILE and filling in the new transport's own fields
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			interceptor.resetTransportSpecificFields(endpoint);

			// then: every HTTP-specific field is cleared -- including the four HTTP/OAuth columns
			// (OAuthTokenUrl, OAuthScope, IsFileUpload, IsArrayFanOut) that a prior version of this
			// interceptor left set on every transport switch ...
			assertAllHttpFieldsCleared(endpoint);
			// ... every SFTP-specific field is cleared too ...
			assertAllSftpFieldsCleared(endpoint);
			// ... the just-entered LOCAL_FILE fields survive ...
			assertAllLocalFileFieldsPreserved(endpoint);
			// ... and the transport-agnostic directories are untouched
			assertDirectoriesPreserved(endpoint);
		}

		/**
		 * Round trip LOCAL_FILE -&gt; another transport -&gt; LOCAL_FILE. Switching away clears Frequency to 0 —
		 * the only "empty" its {@code int} setter can express — and the column's DefaultValue never re-fires
		 * on an existing row, so without a re-default the endpoint comes back with a frequency the local-file
		 * import route rejects at enable time.
		 */
		@Test
		void switchBackToLocalFile_afterFrequencyWasClearedBySwitchingAway_restoresTheDefaultFrequency()
		{
			// given: a working LOCAL_FILE endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// ... that is switched away to SFTP, which clears its local-file settings
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			interceptor.resetTransportSpecificFields(endpoint);
			assertThat(endpoint.getFrequency()).isZero();

			// when: switching back to LOCAL_FILE, re-entering only the root location
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			endpoint.setLocalRootLocation("/data/in2");
			interceptor.resetTransportSpecificFields(endpoint);

			// then: the endpoint is polling-ready again without the operator having to re-enter a frequency
			assertThat(endpoint.getFrequency()).isEqualTo(60_000);
		}
	}
}
