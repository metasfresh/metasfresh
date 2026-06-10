/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.externlasystem;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.IExternalSystemInvocationSuccessListener;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.externalsystem.externalservice.ExternalServices;
import de.metas.externalsystem.process.runtimeparameters.RuntimeParametersRepository;
import de.metas.process.PInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.springframework.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the success-listener wiring in {@link ExternalSystemService}.
 * <p>
 * Covers {@link ExternalSystemService#handleExportSuccess(PInstanceId, int)} at the service seam:
 * verifies that each registered success listener is invoked with the correct arguments.
 */
public class ExternalSystemServiceSuccessTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void handleExportSuccess_invokesAllApplicableSuccessListeners()
	{
		final IExternalSystemInvocationSuccessListener listener = mock(IExternalSystemInvocationSuccessListener.class);
		when(listener.applies(Mockito.any())).thenReturn(true);

		final ExternalSystemService service = buildService(Collections.singletonList(listener));

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(3001);
		service.handleExportSuccess(pInstanceId, 200);

		verify(listener).onInvocationSuccess(pInstanceId, ExternalSystemInvocationContext.UNKNOWN, HttpStatus.OK);
	}

	@Test
	void handleExportSuccess_skipsListeners_whenAppliesReturnsFalse()
	{
		final IExternalSystemInvocationSuccessListener listener = mock(IExternalSystemInvocationSuccessListener.class);
		when(listener.applies(Mockito.any())).thenReturn(false);

		final ExternalSystemService service = buildService(Collections.singletonList(listener));

		service.handleExportSuccess(PInstanceId.ofRepoId(3002), 201);

		verify(listener).applies(ExternalSystemInvocationContext.UNKNOWN);
		// onInvocationSuccess must NOT have been called
		verify(listener, Mockito.never()).onInvocationSuccess(
				Mockito.any(), Mockito.any(), Mockito.any(HttpStatus.class));
	}

	@Test
	void handleExportSuccess_doesNotThrow_whenListenerThrows()
	{
		final IExternalSystemInvocationSuccessListener failingListener = mock(IExternalSystemInvocationSuccessListener.class);
		when(failingListener.applies(Mockito.any())).thenReturn(true);
		Mockito.doThrow(new RuntimeException("listener failure"))
				.when(failingListener).onInvocationSuccess(Mockito.any(), Mockito.any(), Mockito.any(HttpStatus.class));

		final ExternalSystemService service = buildService(Collections.singletonList(failingListener));

		// must not propagate listener exception
		Assertions.assertThatCode(
				() -> service.handleExportSuccess(PInstanceId.ofRepoId(3003), 202))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private static ExternalSystemService buildService(
			final List<IExternalSystemInvocationSuccessListener> successListeners)
	{
		return new ExternalSystemService(
				ExternalSystemConfigRepo.newInstanceForUnitTesting(),
				ExternalSystemExportAuditRepo.newInstanceForUnitTesting(),
				new RuntimeParametersRepository(),
				ExternalServices.newInstanceForUnitTesting(),
				new JsonExternalSystemRetriever(),
				new ExternalSystemRepository(),
				Collections.emptyList(),
				successListeners);
	}
}
