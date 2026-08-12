package de.metas.purchasecandidate.interceptor;

import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateSource;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class C_PurchaseCandidateTest
{
	private C_PurchaseCandidate interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_PurchaseCandidate(Mockito.mock(PurchaseCandidateRepository.class));
	}

	@Test
	public void rejectsUnknownSourceOnNew()
	{
		final I_C_PurchaseCandidate record = newInstance(I_C_PurchaseCandidate.class);
		record.setSource(PurchaseCandidateSource.Unknown.getCode());

		assertThatThrownBy(() -> interceptor.rejectLegacyUnknownSourceOnNew(record))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void allowsRealSourceOnNew()
	{
		final I_C_PurchaseCandidate record = newInstance(I_C_PurchaseCandidate.class);
		record.setSource(PurchaseCandidateSource.SalesOrder.getCode());

		assertThatCode(() -> interceptor.rejectLegacyUnknownSourceOnNew(record))
				.doesNotThrowAnyException();
	}
}
