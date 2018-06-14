package de.metas.purchasecandidate;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import de.metas.money.CurrencyRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PurchaseCandidateRepositoryTest
{
	@Mocked
	ReferenceGenerator referenceGenerator;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

	}

	@Test
	public void save()
	{
		// @formatter:off
		new Expectations()
		{{
			referenceGenerator.getNextDemandReference(); result = "nextDemandReference";
		}}; // @formatter:on

		final PurchaseCandidateRepository purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				new CurrencyRepository(),
				referenceGenerator);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(0, Quantity.of(TEN, uom));
		assertThat(purchaseCandidate.getGroupReference().getDemandReference()).isEqualTo(DemandGroupReference.REFERENCE_NOT_YET_SET); // guard

		// invoke the method under test
		final PurchaseCandidateId id = purchaseCandidateRepository.save(purchaseCandidate);

		final List<I_C_PurchaseCandidate> candidateRecords = POJOLookupMap.get().getRecords(I_C_PurchaseCandidate.class);
		assertThat(candidateRecords).hasSize(1);

		final I_C_PurchaseCandidate candidateRecord = candidateRecords.get(0);
		assertThat(candidateRecord.getC_PurchaseCandidate_ID()).isEqualTo(id.getRepoId());
		assertThat(candidateRecord.getDemandReference()).isEqualTo("nextDemandReference");
	}

}
