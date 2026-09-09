/*
 * #%L
 * de.metas.business
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

package org.adempiere.mm.attributes.api.impl;

import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Evaluatee;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LotNumberBLTest
{
	private IDocumentNoBuilderFactory documentNoBuilderFactory;
	private IDocumentNoBuilder documentNoBuilder;
	private LotNumberBL lotNumberBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		documentNoBuilderFactory = mock(IDocumentNoBuilderFactory.class);
		documentNoBuilder = mock(IDocumentNoBuilder.class);

		// Chain the builder methods so each returns itself
		when(documentNoBuilderFactory.forSequenceId(any())).thenReturn(documentNoBuilder);
		when(documentNoBuilder.setFailOnError(true)).thenReturn(documentNoBuilder);
		when(documentNoBuilder.setClientId(any())).thenReturn(documentNoBuilder);
		when(documentNoBuilder.setEvaluationContext(any())).thenReturn(documentNoBuilder);
		when(documentNoBuilder.build()).thenReturn("LOT-001");

		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);

		lotNumberBL = new LotNumberBL();
	}

	@Test
	void evalContextCarriesPPOrderId()
	{
		// arrange
		final int ppOrderRepoId = 12345;
		final LotNoContext context = LotNoContext.builder()
				.sequenceId(DocSequenceId.ofRepoId(1))
				.clientId(ClientId.ofRepoId(1))
				.ppOrderId(PPOrderId.ofRepoId(ppOrderRepoId))
				.build();

		// capture the Evaluatee passed to setEvaluationContext
		final ArgumentCaptor<Evaluatee> evaluateeCaptor = ArgumentCaptor.forClass(Evaluatee.class);
		when(documentNoBuilder.setEvaluationContext(evaluateeCaptor.capture())).thenReturn(documentNoBuilder);

		// act
		lotNumberBL.getAndIncrementLotNo(context);

		// assert — the captured evaluatee must expose Record_ID = ppOrderRepoId
		final Evaluatee capturedEvaluatee = evaluateeCaptor.getValue();
		assertThat(capturedEvaluatee).isNotNull();
		assertThat(capturedEvaluatee.get_ValueAsInt(IPOReferenceAware.COLUMNNAME_Record_ID, -1))
				.as("Evaluatee must expose PP_Order repo-id under key Record_ID")
				.isEqualTo(ppOrderRepoId);
	}
}
