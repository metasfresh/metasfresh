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
import org.compiere.util.Evaluatee;
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
 * Covers {@link ExternalSystem_Endpoint#resetFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)}:
 * after a save, every field the window hides stands at its {@code AD_Column.DefaultValue} -- no value at
 * all for the columns that have none -- and no field the window shows has lost one.
 * <p>
 * The five hideable columns that carry a default are {@code ContentType}, {@code IsFileUpload},
 * {@code SftpPort}, {@code SftpPollingIntervalMs} and {@code Frequency}; that those copies match the
 * live dictionary is {@code externalSystemEndpointDisplayLogic.feature}'s job, not this suite's.
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
		// deliberately NOT application/json, this column's AD_Column.DefaultValue: a fixture equal to the
		// default would make "the window still shows it" and "the window hid it and it was reset"
		// indistinguishable
		endpoint.setContentType("application/xml");
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
	 * The HTTP-only columns, i.e. the ones no other transport shows, each back at its
	 * {@code AD_Column.DefaultValue} -- which for a column that has none is no value at all.
	 * {@code Password} is NOT among them -- SFTP password authentication shows it too, so each test states
	 * its fate itself -- and neither is {@code IsArrayFanOut}, which has no display logic at all (see
	 * {@link #assertArrayFanOutPreserved}).
	 */
	private static void assertHttpOnlyFieldsResetToTheirColumnDefaults(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getHttpEndPoint()).isNull();
		assertThat(endpoint.getOutboundHttpMethod()).isNull();
		// ContentType and IsFileUpload are the two HTTP columns that DO carry an AD_Column.DefaultValue
		assertThat(endpoint.getContentType()).isEqualTo("application/json");
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
	 * authentication type, and the outbound dispatch fans out on it under both HTTP and SFTP. Nothing may
	 * ever clear it.
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
		assertThat(endpoint.getContentType()).isEqualTo("application/xml");
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
		// 2222 and 30_000 are deliberately NOT these columns' AD_Column.DefaultValue (22 / 60000), for the
		// reason given in setAllHttpFields
		endpoint.setSftpPort(2222);
		endpoint.setSftpUsername("sftpuser");
		endpoint.setSftpAuthType("PASSWORD");
		endpoint.setSshPrivateKey("private-key");
		endpoint.setSftpRemotePath("/upload");
		endpoint.setSftpFilenamePattern("order_{date}.edi");
		endpoint.setSftpPollingIntervalMs(30_000);
	}

	/** The stored value: the generated {@code int} getter answers 0 for a stored 0 and for SQL NULL alike. */
	@Nullable
	private static Integer sftpPortOf(final I_ExternalSystem_Endpoint endpoint)
	{
		return InterfaceWrapperHelper.getValueOrNull(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort);
	}

	/**
	 * Every SFTP column back at its {@code AD_Column.DefaultValue} -- which for a column that has none is no
	 * value at all.
	 */
	private static void assertAllSftpFieldsResetToTheirColumnDefaults(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isNull();
		// SftpPort and SftpPollingIntervalMs are the two SFTP columns that DO carry an AD_Column.DefaultValue
		assertThat(sftpPortOf(endpoint)).isEqualTo(22);
		assertThat(endpoint.getSftpUsername()).isNull();
		assertThat(endpoint.getSftpAuthType()).isNull();
		assertThat(endpoint.getSshPrivateKey()).isNull();
		assertThat(endpoint.getSftpRemotePath()).isNull();
		assertThat(endpoint.getSftpFilenamePattern()).isNull();
		assertThat(endpoint.getSftpPollingIntervalMs()).isEqualTo(60_000);
	}

	/** The SFTP columns an {@code SftpAuthType='PASSWORD'} endpoint shows -- every one set by {@link #setAllSftpFields} except the SSH key. */
	private static void assertPasswordAuthSftpFieldsPreserved(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getSftpHost()).isEqualTo("sftp.example.com");
		assertThat(endpoint.getSftpPort()).isEqualTo(2222);
		assertThat(endpoint.getSftpUsername()).isEqualTo("sftpuser");
		assertThat(endpoint.getSftpAuthType()).isEqualTo("PASSWORD");
		assertThat(endpoint.getSftpRemotePath()).isEqualTo("/upload");
		assertThat(endpoint.getSftpFilenamePattern()).isEqualTo("order_{date}.edi");
		assertThat(endpoint.getSftpPollingIntervalMs()).isEqualTo(30_000);
	}

	private static void setAllLocalFileFields(final I_ExternalSystem_Endpoint endpoint)
	{
		endpoint.setLocalRootLocation("/data/in");
		endpoint.setFrequency(5000);
		endpoint.setImportFileNamePattern("{filename}_{timestamp}");
	}

	/** The stored value: the generated {@code int} getter answers 0 for a stored 0 and for SQL NULL alike. */
	@Nullable
	private static Integer frequencyOf(final I_ExternalSystem_Endpoint endpoint)
	{
		return InterfaceWrapperHelper.getValueOrNull(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_Frequency);
	}

	/**
	 * Every LOCAL_FILE column back at its {@code AD_Column.DefaultValue} -- which for a column that has none
	 * is no value at all.
	 */
	private static void assertAllLocalFileFieldsResetToTheirColumnDefaults(final I_ExternalSystem_Endpoint endpoint)
	{
		assertThat(endpoint.getLocalRootLocation()).isNull();
		// Frequency is the one LOCAL_FILE column that DOES carry an AD_Column.DefaultValue
		assertThat(frequencyOf(endpoint)).isEqualTo(60_000);
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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every SFTP-specific and LOCAL_FILE-specific field is cleared ...
			assertAllSftpFieldsResetToTheirColumnDefaults(endpoint);
			assertAllLocalFileFieldsResetToTheirColumnDefaults(endpoint);
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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every HTTP-only field is cleared ...
			assertHttpOnlyFieldsResetToTheirColumnDefaults(endpoint);
			// ... but the password survives: SFTP password authentication shows that very field, so the
			// endpoint stays usable instead of ending up valid-looking and unable to log in ...
			assertThat(endpoint.getPassword()).isEqualTo("secret");
			// ... every LOCAL_FILE-specific field is cleared ...
			assertAllLocalFileFieldsResetToTheirColumnDefaults(endpoint);
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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			// then: every HTTP-specific field is cleared ...
			assertHttpOnlyFieldsResetToTheirColumnDefaults(endpoint);
			// ... the password too: no LOCAL_FILE configuration shows it ...
			assertThat(endpoint.getPassword()).isNull();
			// ... every SFTP-specific field is cleared ...
			assertAllSftpFieldsResetToTheirColumnDefaults(endpoint);
			// ... the just-entered LOCAL_FILE fields survive ...
			assertAllLocalFileFieldsPreserved(endpoint);
			// ... and the always-visible / transport-agnostic fields are untouched
			assertArrayFanOutPreserved(endpoint);
			assertDirectoriesPreserved(endpoint);
		}

		/**
		 * Round trip LOCAL_FILE -&gt; another transport -&gt; LOCAL_FILE. Frequency carries an
		 * {@code AD_Column.DefaultValue} of 60000, so that is what the switch away leaves behind and what the
		 * operator finds on the way back -- the same value a newly created LOCAL_FILE endpoint is pre-filled
		 * with. Never 0: {@code ExternalSystemEndpointRepository} reads a frequency &lt;= 0 as no frequency at
		 * all, while the column's MandatoryLogic accepts it, which would end the round trip on a record the
		 * window calls valid and the dispatch cannot poll.
		 */
		@Test
		void switchBackToLocalFile_afterSwitchingAway_findsTheFrequencyAtItsColumnDefault()
		{
			// given: a working LOCAL_FILE endpoint polling every 5 seconds, not the column's default
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// ... that is switched away to SFTP, which resets its local-file settings
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);
			assertThat(frequencyOf(endpoint)).isEqualTo(60_000);

			// when: switching back to LOCAL_FILE, re-entering only the root location
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			endpoint.setLocalRootLocation("/data/in2");
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the frequency stands at its column default, so the record is complete without the
			// operator retyping a value the window would have pre-filled on a new record
			assertThat(frequencyOf(endpoint)).isEqualTo(60_000);
		}

		/**
		 * The same round trip for SftpPort, which has the identical shape: an {@code AD_Column.DefaultValue}
		 * of 22 and MandatoryLogic {@code @TransportType/X@='SFTP'}. Never 0, for the reason given on
		 * {@link #switchBackToLocalFile_afterSwitchingAway_findsTheFrequencyAtItsColumnDefault()}.
		 */
		@Test
		void switchBackToSftp_afterSwitchingAway_findsThePortAtItsColumnDefault()
		{
			// given: a working SFTP endpoint on port 2222, not the column's default
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.SFTP.getCode());
			setAllSftpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// ... that is switched away to LOCAL_FILE, which resets its SFTP settings
			endpoint.setTransportType(TransportType.LOCAL_FILE.getCode());
			setAllLocalFileFields(endpoint);
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);
			assertThat(sftpPortOf(endpoint)).isEqualTo(22);

			// when: switching back to SFTP, re-entering only the host
			endpoint.setTransportType(TransportType.SFTP.getCode());
			endpoint.setSftpHost("sftp.example.com");
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			// then: the port stands at its column default, the value 2222 the operator had entered gone with
			// the transport that showed it
			assertThat(sftpPortOf(endpoint)).isEqualTo(22);
		}
	}

	/**
	 * A transport code {@link TransportType} has no constant for. Whether a code the enum DOES know is
	 * keyed on by any rule is a separate guarantee -- see
	 * {@link VisibilityRules#everyTransportTypeIsKeyedOnByARule()}.
	 */
	@Nested
	class UnknownTransportType
	{
		@Test
		void aCodeTheEnumHasNoConstantFor_isRejected()
		{
			// given: a fully configured HTTP endpoint
			final I_ExternalSystem_Endpoint endpoint = InterfaceWrapperHelper.newInstance(I_ExternalSystem_Endpoint.class);
			endpoint.setTransportType(TransportType.HTTP.getCode());
			setAllHttpFields(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: the transport is switched to a code the enum has no constant for -- what a fourth
			// transport added to the ref list without a matching Java constant would look like
			endpoint.setTransportType("CARRIER_PIGEON");

			// then: the save is refused ...
			assertThatThrownBy(() -> interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint))
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

			assertThatThrownBy(() -> interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint))
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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getLoginUsername()).isNull();
			assertThat(endpoint.getPassword()).isNull();
			assertThat(endpoint.getAuthToken()).isEqualTo("token");
		}

		@Test
		void basicToSas_clearsEveryOtherCredential_keepsTheSignature()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("SAS");
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);
			InterfaceWrapperHelper.saveRecord(endpoint);

			// when: back to Basic, which does not show the SAS signature
			endpoint.setAuthType("Basic");
			endpoint.setLoginUsername("user");
			endpoint.setPassword("secret");
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getSasSignature()).isNull();
			assertThat(endpoint.getLoginUsername()).isEqualTo("user");
			assertThat(endpoint.getPassword()).isEqualTo("secret");
		}

		/**
		 * OAuth (v1) fetches its token from a login endpoint, and the scripted-adapter route can put client
		 * id, client secret, username AND password into that request -- so each of the four is a value the
		 * operator must be able to enter. The window shows all four under {@code HTTP + OAuth}, so a switch
		 * to OAuth must leave all four alone.
		 */
		@Test
		void basicToOAuth_keepsEveryCredentialTheTokenRequestSends()
		{
			final I_ExternalSystem_Endpoint endpoint = newSavedBasicAuthEndpoint();

			endpoint.setAuthType("OAuth");
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

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
			interceptor.resetFieldsHiddenByTheNewConfiguration(endpoint);

			assertThat(endpoint.getSshPrivateKey()).isNull();
			assertThat(endpoint.getPassword()).isEqualTo("sftp-secret");
		}
	}

	/**
	 * Structural guards on the visibility rules themselves. They do not check WHAT the rules say — only the
	 * behavioural tests above can do that — but they do rule out the ways the rule table can become
	 * unenforceable, or stop agreeing with the window, without anyone noticing.
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
		 * a fourth column would therefore keep its stale verdict when that column changed.
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
					.getMethod("resetFieldsHiddenByTheNewConfiguration", I_ExternalSystem_Endpoint.class);

			assertThat(handler.getAnnotation(ModelChange.class).ifColumnsChanged())
					.containsExactlyInAnyOrderElementsOf(ExternalSystem_Endpoint.VISIBILITY_GOVERNING_COLUMN_NAMES);
		}

		/**
		 * Every variable falls back to a default, so no display logic can end up undecidable at save time --
		 * an undecidable one is answered as NOT shown and the field is CLEARED, see
		 * {@link #anUndecidableDisplayLogicCountsAsHiddenJustAsTheWindowCountsIt()}.
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
		 * variable that has no value AND no default. It answers "not shown", so the field is cleared; the
		 * window answers FALSE too, by a different route. The expression below is synthetic and deliberately
		 * outside the governing columns; {@link #everyDisplayLogicVariableHasADefaultValue()} keeps a real
		 * rule from ever reaching this case.
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
					null,
					endpoint -> {});

			assertThat(column.isVisible(Evaluatees.ofMap(ImmutableMap.of()))).isFalse();
		}

		/**
		 * Every transport the enum knows is keyed on by at least one rule. A transport no rule mentions
		 * satisfies no condition at all, so the FIRST save of such an endpoint clears every hideable column
		 * -- and {@code ExternalSystem_Endpoint#assertTransportTypeIsAKnownCode} lets it through, because
		 * the code is in the enum.
		 */
		@Test
		void everyTransportTypeIsKeyedOnByARule()
		{
			for (final TransportType transportType : TransportType.values())
			{
				final Evaluatee onlyTheTransportSet = Evaluatees.ofMap(ImmutableMap.of(
						I_ExternalSystem_Endpoint.COLUMNNAME_TransportType, transportType.getCode()));

				assertThat(hideableColumns())
						.as("columns still shown under transport %s -- none means every one of them is cleared",
								transportType.getCode())
						.anyMatch(column -> column.isVisible(onlyTheTransportSet));
			}
		}

		/**
		 * No rule may compare against the EMPTY literal. An unset ref-list field resolves to {@code ""} in
		 * the window and to the {@link CtxName} default {@code "X"} here, so a rule of the form
		 * {@code @AuthType/X@=''} would be TRUE for the window and FALSE for this interceptor -- clearing a
		 * field the operator can still see.
		 */
		@Test
		void noDisplayLogicComparesAgainstTheEmptyLiteral()
		{
			for (final HideableColumn column : hideableColumns())
			{
				assertThat(column.getDisplayLogic())
						.as("display logic of %s", column.getColumnName())
						.doesNotContain("''");
			}
		}

		/**
		 * Nor against a variable's own default. The window compares the unset field's {@code ""}, this
		 * interceptor the {@link CtxName} default {@code "X"}, so a rule of the form {@code @AuthType/X@='X'}
		 * would be TRUE here and FALSE for the window -- keeping a field the operator cannot see. This and
		 * {@link #noDisplayLogicComparesAgainstTheEmptyLiteral()} together are what make every literal in the
		 * rule table answered alike by both.
		 */
		@Test
		void noDisplayLogicComparesAgainstItsVariablesOwnDefault()
		{
			for (final HideableColumn column : hideableColumns())
			{
				for (final CtxName parameter : column.getVisibleIf().getParameters())
				{
					assertThat(column.getDisplayLogic())
							.as("display logic of %s, whose variable %s defaults to %s",
									column.getColumnName(), parameter.getName(), parameter.getDefaultValue())
							.doesNotContain("'" + parameter.getDefaultValue() + "'");
				}
			}
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
