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

import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.endpoint.interceptor.ExternalSystem_Endpoint.HideableColumn;
import de.metas.externalsystem.endpoint.TransportType;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.util.Services;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers {@link ExternalSystem_Endpoint#clearFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)}:
 * after a save, no field the window hides may still carry a value, and no field the window shows may have
 * lost one.
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

	/**
	 * Every HTTP-ish column filled in, with {@code AuthType='Basic'}. Deliberately a kitchen sink: the
	 * columns that {@code Basic} does not show are exactly the stale values the interceptor has to take away.
	 */
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

	/**
	 * The HTTP-only columns, i.e. the ones no other transport shows. {@code Password} is NOT among them --
	 * SFTP password authentication shows it too, so each test states its fate itself -- and neither is
	 * {@code IsArrayFanOut}, which has no display logic at all (see {@link #assertArrayFanOutPreserved}).
	 */
	private static void assertHttpOnlyFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
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
	}

	/**
	 * {@code IsArrayFanOut} carries no display logic: the window shows it under every transport and every
	 * authentication type, and both the HTTP and the SFTP outbound dispatch read it. Nothing may ever clear
	 * it.
	 */
	private static void assertArrayFanOutPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.isArrayFanOut()).isTrue();
	}

	/**
	 * The HTTP columns an {@code AuthType='Basic'} endpoint shows, as set by {@link #setAllHttpFields}.
	 * The other HTTP columns are asserted cleared by {@link #assertBasicAuthHiddenFieldsCleared}.
	 */
	private static void assertBasicAuthHttpFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getHttpEndPoint()).isEqualTo("https://example.com/api");
		assertThat(endpoint.getOutboundHttpMethod()).isEqualTo("POST");
		assertThat(endpoint.getContentType()).isEqualTo("application/json");
		assertThat(endpoint.getAuthType()).isEqualTo("Basic");
		assertThat(endpoint.getLoginUsername()).isEqualTo("user");
		assertThat(endpoint.getPassword()).isEqualTo("secret");
		assertThat(endpoint.isFileUpload()).isTrue();
	}

	/** The HTTP columns {@code AuthType='Basic'} does NOT show -- a token, OAuth and SAS credentials. */
	private static void assertBasicAuthHiddenFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getAuthToken()).isNull();
		assertThat(endpoint.getClientId()).isNull();
		assertThat(endpoint.getClientSecret()).isNull();
		assertThat(endpoint.getSasSignature()).isNull();
		assertThat(endpoint.getOAuthTokenUrl()).isNull();
		assertThat(endpoint.getOAuthScope()).isNull();
	}

	/** Every SFTP column filled in, with {@code SftpAuthType='PASSWORD'} -- including the SSH key that combination hides. */
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

	/**
	 * The stored value, not the generated {@code int} getter: that getter answers 0 both for a stored 0 and
	 * for SQL NULL, and only NULL trips the column's MandatoryLogic.
	 */
	@Nullable
	private static Integer sftpPortOf(final I_ExternalSystem_Endpoint endpoint)
	{
		return InterfaceWrapperHelper.getValueOrNull(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort);
	}

	private static void assertAllSftpFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isNull();
		assertThat(sftpPortOf(endpoint)).isNull();
		assertThat(endpoint.getSftpUsername()).isNull();
		assertThat(endpoint.getSftpAuthType()).isNull();
		assertThat(endpoint.getSshPrivateKey()).isNull();
		assertThat(endpoint.getSftpRemotePath()).isNull();
		assertThat(endpoint.getSftpFilenamePattern()).isNull();
		assertThat(endpoint.getSftpPollingIntervalMs()).isZero();
	}

	/** The SFTP columns an {@code SftpAuthType='PASSWORD'} endpoint shows -- every one set by {@link #setAllSftpFields} except the SSH key. */
	private static void assertPasswordAuthSftpFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isEqualTo("sftp.example.com");
		assertThat(endpoint.getSftpPort()).isEqualTo(22);
		assertThat(endpoint.getSftpUsername()).isEqualTo("sftpuser");
		assertThat(endpoint.getSftpAuthType()).isEqualTo("PASSWORD");
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

	/**
	 * The stored value, not the generated {@code int} getter: that getter answers 0 both for a stored 0 and
	 * for SQL NULL, and only NULL trips the column's MandatoryLogic.
	 */
	@Nullable
	private static Integer frequencyOf(final I_ExternalSystem_Endpoint endpoint)
	{
		return InterfaceWrapperHelper.getValueOrNull(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_Frequency);
	}

	private static void assertAllLocalFileFieldsCleared(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getLocalRootLocation()).isNull();
		assertThat(frequencyOf(endpoint)).isNull();
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
		// regression-guard: the transport-agnostic directory fields carry no display logic, so they are
		// NEVER cleared, regardless of transport
		assertThat(endpoint.getProcessedDirectory()).isEqualTo("/processed");
		assertThat(endpoint.getErrorDirectory()).isEqualTo("/error");
	}

	/** The full HTTP / SFTP / LOCAL_FILE transport matrix. */
	@Nested
	class TransportTypeChange
	{
		@Test
		void switchToHttp_clearsSftpAndLocalFileFields_keepsTheFieldsBasicAuthShows()
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
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every SFTP-specific and LOCAL_FILE-specific field is cleared ...
			assertAllSftpFieldsCleared(endpoint);
			assertAllLocalFileFieldsCleared(endpoint);
			// ... the HTTP fields Basic authentication shows survive ...
			assertBasicAuthHttpFieldsPreserved(endpoint);
			// ... the HTTP fields it hides are taken away, although HTTP is the new transport ...
			assertBasicAuthHiddenFieldsCleared(endpoint);
			// ... and the always-visible / transport-agnostic fields are untouched
			assertArrayFanOutPreserved(endpoint);
			assertDirectoriesPreserved(endpoint);
		}

		/**
		 * {@code Type} is a retired column: its {@code AD_Field} and {@code AD_UI_Element} are both
		 * {@code IsActive='N'}, so no configuration renders it and nothing reads it. A handler that cleared
		 * it would produce a value the operator can neither see nor restore, and -- because the column's
		 * MandatoryLogic is {@code @TransportType/X@='HTTP'} -- an HTTP endpoint that cannot be saved at
		 * all. The handler therefore leaves it alone under every transport.
		 */
		@Test
		void switchToSftp_leavesTheRetiredTypeColumnAlone()
		{
			// given: an HTTP endpoint that still carries a value in the retired Type column
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			endpoint.setType("HTTP");
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: switching to a transport whose configuration would not show an HTTP-only field
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the retired column keeps its value
			assertThat(endpoint.getType()).isEqualTo("HTTP");
		}

		@Test
		void switchToSftp_clearsHttpOnlyAndLocalFileFields_keepsTheFieldsPasswordAuthShows()
		{
			// given: an HTTP endpoint carrying its own HTTP settings plus stale local-file settings
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
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every HTTP-only field is cleared ...
			assertHttpOnlyFieldsCleared(endpoint);
			// ... but the password survives: SFTP password authentication shows that very field, so the
			// endpoint stays usable instead of ending up valid-looking and unable to log in ...
			assertThat(endpoint.getPassword()).isEqualTo("secret");
			// ... every LOCAL_FILE-specific field is cleared ...
			assertAllLocalFileFieldsCleared(endpoint);
			// ... the SFTP fields password authentication shows survive ...
			assertPasswordAuthSftpFieldsPreserved(endpoint);
			// ... the SSH key it hides is taken away ...
			assertThat(endpoint.getSshPrivateKey()).isNull();
			// ... and the always-visible / transport-agnostic fields are untouched
			assertArrayFanOutPreserved(endpoint);
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
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every HTTP-specific field is cleared -- including the four HTTP/OAuth columns
			// (OAuthTokenUrl, OAuthScope, IsFileUpload, IsArrayFanOut) that a prior version of this
			// interceptor left set on every transport switch ...
			assertHttpOnlyFieldsCleared(endpoint);
			// ... the password too: no LOCAL_FILE configuration shows it ...
			assertThat(endpoint.getPassword()).isNull();
			// ... every SFTP-specific field is cleared ...
			assertAllSftpFieldsCleared(endpoint);
			// ... the just-entered LOCAL_FILE fields survive ...
			assertAllLocalFileFieldsPreserved(endpoint);
			// ... and the always-visible / transport-agnostic fields are untouched
			assertArrayFanOutPreserved(endpoint);
			assertDirectoriesPreserved(endpoint);
		}

		/**
		 * Round trip LOCAL_FILE -&gt; another transport -&gt; LOCAL_FILE. The switch away must leave Frequency
		 * unset rather than 0: the column's MandatoryLogic rejects only an unset value, so a 0 would end the
		 * round trip on a record the window calls valid while
		 * {@code ExternalSystemEndpointRepository} reads the frequency back as absent — an endpoint that
		 * silently never polls. Unset, the operator is prompted for it, exactly as for LocalRootLocation.
		 */
		@Test
		void switchBackToLocalFile_afterFrequencyWasClearedBySwitchingAway_leavesFrequencyUnset()
		{
			// given: a working LOCAL_FILE endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// ... that is switched away to SFTP, which clears its local-file settings
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);
			assertThat(frequencyOf(endpoint)).isNull();

			// when: switching back to LOCAL_FILE, re-entering only the root location
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			endpoint.setLocalRootLocation("/data/in2");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the frequency is still unset, so the window keeps asking the operator for it
			assertThat(frequencyOf(endpoint)).isNull();
		}

		/**
		 * The same round trip for SftpPort, which has the identical shape: MandatoryLogic
		 * {@code @TransportType/X@='SFTP'}, and a stored 0 passes it while
		 * {@code ExternalSystemEndpointRepository} hands the 0 straight on as the port to connect to -- an
		 * endpoint the window calls valid that dials {@code sftp://host:0}. Unset, the operator is prompted
		 * for it, exactly as for SftpHost.
		 */
		@Test
		void switchBackToSftp_afterSftpPortWasClearedBySwitchingAway_leavesSftpPortUnset()
		{
			// given: a working SFTP endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// ... that is switched away to LOCAL_FILE, which clears its SFTP settings
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);
			assertThat(sftpPortOf(endpoint)).isNull();

			// when: switching back to SFTP, re-entering only the host
			endpoint.setTransportType(TransportType.SFTP.getCode());
			endpoint.setSftpHost("sftp.example.com");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the port is still unset, so the window keeps asking the operator for it
			assertThat(sftpPortOf(endpoint)).isNull();
		}
	}

	/**
	 * A transport code this class carries no rules for. Every rule is keyed on a transport, so an
	 * unrecognised one matches none of them.
	 */
	@Nested
	class UnknownTransportType
	{
		@Test
		void isRejected_ratherThanSilentlyClearingEveryColumn()
		{
			// given: a fully configured HTTP endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: the transport is switched to a code this class has no rules for -- what a fourth
			// transport added to the ref list without a matching Java entry would look like
			endpoint.setTransportType("CARRIER_PIGEON");

			// then: the save is refused ...
			assertThatThrownBy(() -> interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint))
					.hasMessageContaining("CARRIER_PIGEON");

			// ... and nothing was taken away on the way out
			assertThat(endpoint.getHttpEndPoint()).isEqualTo("https://example.com/api");
			assertThat(endpoint.getAuthType()).isEqualTo("Basic");
			assertThat(endpoint.getPassword()).isEqualTo("secret");
		}

		@Test
		void anUnsetTransportType_isRejectedToo()
		{
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			endpoint.setTransportType(null);

			assertThatThrownBy(() -> interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint))
					.hasMessageContaining("TransportType");
			assertThat(endpoint.getHttpEndPoint()).isEqualTo("https://example.com/api");
		}
	}

	/**
	 * One save that changes MORE THAN ONE of the columns the clearing logic keys on. The point is that each
	 * field's fate is decided from the state the record ends up in, not from whichever single column
	 * happened to fire a handler.
	 */
	@Nested
	class CombinedChange
	{
		@Test
		void switchToSftpWithPasswordAuth_inOneSave_keepsThePassword()
		{
			// given: a saved HTTP endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: ONE save turns it into an SFTP endpoint authenticating by password
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			endpoint.setPassword("sftp-secret");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the record ends up SFTP + PASSWORD, a state in which the window SHOWS the password
			// field, so the value the operator just entered must still be there
			assertThat(endpoint.getPassword()).isEqualTo("sftp-secret");
		}

		@Test
		void switchToSftpWithSshKeyAuth_inOneSave_clearsThePasswordAndKeepsTheKey()
		{
			// given: a saved HTTP endpoint with a password
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: ONE save turns it into an SFTP endpoint authenticating by SSH key
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			endpoint.setSftpAuthType("SSH_KEY");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			// then: SFTP + SSH_KEY hides the password and shows the key
			assertThat(endpoint.getPassword()).isNull();
			assertThat(endpoint.getSshPrivateKey()).isEqualTo("private-key");
		}
	}

	/** Changing only the HTTP authentication type, the transport staying HTTP. */
	@Nested
	class AuthTypeChange
	{
		private I_ExternalSystem_Endpoint newSavedBasicAuthEndpoint()
		{
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);
			return endpoint;
		}

		@Test
		void basicToToken_clearsTheUsernameAndPassword_keepsTheToken()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("Token");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getLoginUsername()).isNull();
			assertThat(endpoint.getPassword()).isNull();
			assertThat(endpoint.getAuthToken()).isEqualTo("token");
		}

		@Test
		void basicToSas_clearsEveryOtherCredential_keepsTheSignature()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("SAS");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getLoginUsername()).isNull();
			assertThat(endpoint.getPassword()).isNull();
			assertThat(endpoint.getAuthToken()).isNull();
			assertThat(endpoint.getClientId()).isNull();
			assertThat(endpoint.getClientSecret()).isNull();
			assertThat(endpoint.getSasSignature()).isEqualTo("sasSignature");
		}

		@Test
		void sasToBasic_clearsTheSignature()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();
			endpoint.setAuthType("SAS");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: back to Basic, which does not show the SAS signature
			endpoint.setAuthType("Basic");
			endpoint.setLoginUsername("user");
			endpoint.setPassword("secret");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getSasSignature()).isNull();
			assertThat(endpoint.getLoginUsername()).isEqualTo("user");
			assertThat(endpoint.getPassword()).isEqualTo("secret");
		}

		/**
		 * OAuth (v1) fetches its token from a login endpoint, and the scripted-adapter route builds that
		 * request out of client id, client secret, username AND password -- every one of them a value the
		 * operator has to be able to enter. The window shows all four under {@code HTTP + OAuth}, so a
		 * switch to OAuth must leave all four alone.
		 */
		@Test
		void basicToOAuth_keepsEveryCredentialTheTokenRequestSends()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("OAuth");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getPassword()).isEqualTo("secret");
			assertThat(endpoint.getLoginUsername()).isEqualTo("user");
			assertThat(endpoint.getClientId()).isEqualTo("clientId");
			assertThat(endpoint.getClientSecret()).isEqualTo("clientSecret");
			// hidden under OAuth: the token URL and scope belong to OAuth2, and a bearer token or SAS
			// signature is a different authentication type altogether
			assertThat(endpoint.getAuthToken()).isNull();
			assertThat(endpoint.getSasSignature()).isNull();
			assertThat(endpoint.getOAuthTokenUrl()).isNull();
			assertThat(endpoint.getOAuthScope()).isNull();
		}

		/**
		 * OAuth2 shows the widest credential set of all authentication types: username + password (the
		 * resource-owner grant) as well as client id + secret, token URL and scope.
		 */
		@Test
		void basicToOAuth2_keepsEveryCredentialOAuth2Shows_clearsTheRest()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("OAuth2");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getLoginUsername()).isEqualTo("user");
			assertThat(endpoint.getPassword()).isEqualTo("secret");
			assertThat(endpoint.getClientId()).isEqualTo("clientId");
			assertThat(endpoint.getClientSecret()).isEqualTo("clientSecret");
			assertThat(endpoint.getOAuthTokenUrl()).isEqualTo("https://example.com/oauth/token");
			assertThat(endpoint.getOAuthScope()).isEqualTo("docuware.platform");
			// hidden under OAuth2
			assertThat(endpoint.getAuthToken()).isNull();
			assertThat(endpoint.getSasSignature()).isNull();
		}
	}

	/** Changing only the SFTP authentication type, the transport staying SFTP. */
	@Nested
	class SftpAuthTypeChange
	{
		@Test
		void passwordToSshKey_clearsThePassword_keepsTheKey()
		{
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			endpoint.setPassword("sftp-secret");
			InterfaceWrapperHelper.saveRecord(endpoint);

			endpoint.setSftpAuthType("SSH_KEY");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getPassword()).isNull();
			assertThat(endpoint.getSshPrivateKey()).isEqualTo("private-key");
		}

		@Test
		void sshKeyToPassword_clearsTheKey_keepsThePassword()
		{
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			endpoint.setSftpAuthType("SSH_KEY");
			InterfaceWrapperHelper.saveRecord(endpoint);

			endpoint.setSftpAuthType("PASSWORD");
			endpoint.setPassword("sftp-secret");
			interceptor.clearFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getSshPrivateKey()).isNull();
			assertThat(endpoint.getPassword()).isEqualTo("sftp-secret");
		}
	}

	/**
	 * Structural guards on the visibility rules themselves. They do not check WHAT the rules say — only the
	 * behavioural tests above can do that — but they do rule out the two ways the rule table can become
	 * unenforceable without anyone noticing.
	 */
	@Nested
	class VisibilityRules
	{
		private List<HideableColumn> hideableColumns()
		{
			return interceptor.hideableColumns.get();
		}

		/**
		 * The interceptor only runs when one of the columns it declares fires it. A display logic that named
		 * a fourth column would therefore keep its stale verdict when that column changed — which is the
		 * defect the three separate handlers this class replaced had, one column at a time.
		 */
		@Test
		void everyDisplayLogicUsesOnlyTheColumnsTheInterceptorTriggersOn()
		{
			for (final HideableColumn column : hideableColumns())
			{
				final List<String> parameterNames = column.getVisibleIf().getParameters().stream()
						.map(CtxName::getName)
						.collect(Collectors.toList());

				assertThat(parameterNames)
						.as("display logic of %s: %s", column.getColumnName(), column.getDisplayLogic())
						.isSubsetOf(ExternalSystem_Endpoint.VISIBILITY_GOVERNING_COLUMN_NAMES);
			}
		}

		/**
		 * ... and the two places that spell those columns out stay equal. They cannot be reduced to one:
		 * {@code VISIBILITY_GOVERNING_COLUMN_NAMES} is an {@code ImmutableSet} built at runtime, while
		 * {@code @ModelChange(ifColumnsChanged = ...)} is an annotation and takes compile-time constants
		 * only, so it has to list the same names again. This test does not remove that duplication -- it
		 * pins the two copies to each other, so a column added to one and forgotten in the other fails
		 * here instead of silently leaving a rule un-triggered.
		 */
		@Test
		void theInterceptorTriggersOnExactlyTheVisibilityGoverningColumns() throws NoSuchMethodException
		{
			final Method handler = ExternalSystem_Endpoint.class
					.getMethod("clearFieldsHiddenByTheNewConfiguration", I_ExternalSystem_Endpoint.class);

			assertThat(handler.getAnnotation(ModelChange.class).ifColumnsChanged())
					.containsExactlyInAnyOrderElementsOf(ExternalSystem_Endpoint.VISIBILITY_GOVERNING_COLUMN_NAMES);
		}

		/**
		 * Every variable falls back to a default, so no display logic can end up undecidable at save time.
		 * That matters because an undecidable one is answered as NOT shown and the field is CLEARED -- see
		 * {@link #anUndecidableDisplayLogicCountsAsHiddenJustAsTheWindowCountsIt()}. This test is what keeps
		 * that case out of the table.
		 */
		@Test
		void everyDisplayLogicVariableHasADefaultValue()
		{
			for (final HideableColumn column : hideableColumns())
			{
				for (final CtxName parameter : column.getVisibleIf().getParameters())
				{
					assertThat(parameter.getDefaultValue())
							.as("variable %s of the display logic of %s", parameter.getName(), column.getColumnName())
							.isNotNull();
				}
			}
		}

		/**
		 * What {@code HideableColumn#isVisible} does with an expression it cannot decide -- one naming a
		 * variable that has no value AND no default. It answers "not shown", so the field is cleared.
		 * <p>
		 * That is not a safety valve, it is the window's own answer: {@code LogicExpressionEvaluator#evaluate}
		 * collapses the undecided result to {@code false}, and the window's {@code Document#updateFieldDisplayed}
		 * falls back to {@code LogicExpressionResult.FALSE} as well. A field the interceptor clears here is
		 * therefore a field the operator genuinely cannot see. The sibling test
		 * {@link #everyDisplayLogicVariableHasADefaultValue()} keeps the case from arising at all.
		 */
		@Test
		void anUndecidableDisplayLogicCountsAsHiddenJustAsTheWindowCountsIt()
		{
			final String displayLogicNamingAVariableWithoutADefault = "@NoSuchColumn@='X'";
			final ILogicExpression undecidable = Services.get(IExpressionFactory.class)
					.compile(displayLogicNamingAVariableWithoutADefault, ILogicExpression.class);

			final HideableColumn column = new HideableColumn(
					"SomeColumn",
					displayLogicNamingAVariableWithoutADefault,
					undecidable,
					endpoint -> {});

			assertThat(column.isVisible(Evaluatees.ofMap(ImmutableMap.of()))).isFalse();
		}

		@Test
		void everyColumnAppearsAtMostOnce()
		{
			final List<String> columnNames = hideableColumns().stream()
					.map(HideableColumn::getColumnName)
					.collect(Collectors.toList());

			assertThat(columnNames).doesNotHaveDuplicates();
		}
	}
}
