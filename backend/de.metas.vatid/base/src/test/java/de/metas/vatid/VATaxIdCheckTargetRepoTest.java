/*
 * #%L
 * metasfresh-vatid-base
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

package de.metas.vatid;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.process.PInstanceId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercises {@link VATaxIdCheckTargetRepo}'s selection-path queries against the in-memory query layer — the
 * real {@code addInSubQueryFilter} + guaranteed iterator, not stubs — because the one property that matters
 * here, that {@link VATaxIdCheckTargetRepo#countSelectedTargets(PInstanceId)} and
 * {@link VATaxIdCheckTargetRepo#iterateSelectedTargets(PInstanceId, java.util.function.Consumer)} agree, lives in
 * the SQL layer and no mock can prove it.
 */
class VATaxIdCheckTargetRepoTest
{
	private IQueryBL queryBL;
	private VATaxIdCheckTargetRepo repo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		repo = new VATaxIdCheckTargetRepo();
	}

	private BPartnerId newBPartner(final String vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setVATaxID(vataxID);
		InterfaceWrapperHelper.saveRecord(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	private void newLocation(final BPartnerId bpartnerId, final String vataxID)
	{
		final I_C_BPartner_Location record = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setVATaxID(vataxID);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * The regression that guards the count/stream drift: {@code countSelectedTargets} must count exactly the
	 * targets {@code iterateSelectedTargets} yields — VAT-ID-bearing partners plus VAT-ID-bearing locations of
	 * any selected partner, and NOTHING for the selected partners and locations that carry no VAT-ID. Before
	 * the fix the partner count was taken from the raw, unfiltered selection, so a broad selection (where most
	 * partners have no VAT-ID) reported a pendingCount inflated by every VAT-ID-less selected partner.
	 */
	@Test
	void countSelectedTargets_equalsWhatIterateSelectedTargetsYields_excludingRecordsWithoutAVATaxID()
	{
		final BPartnerId withVat1 = newBPartner("DE111111111");
		final BPartnerId withVat2 = newBPartner("DE222222222");
		final BPartnerId noVat1 = newBPartner(null);
		final BPartnerId noVat2 = newBPartner(null);

		newLocation(withVat1, "DE333333333"); // location target, parent has a VAT-ID
		newLocation(noVat1, "DE444444444");   // location target, parent has NONE -- still selected, still counts
		newLocation(withVat2, null);           // no VAT-ID -- excluded from both count and stream

		// The whole selection: all four partners, VAT-ID or not (a "select all" run), materialised into a
		// T_Selection exactly as the process does before handing the run its PInstanceId.
		final PInstanceId selectionId = PInstanceId.ofRepoId(1_000_050);
		queryBL.createQueryBuilder(I_C_BPartner.class).create().createSelection(selectionId);

		int streamed = 0;
		final Iterator<VATaxIdCheckTargetRepo.CheckTarget> targets = repo.iterateSelectedTargets(selectionId, logLabel -> {});
		while (targets.hasNext())
		{
			targets.next();
			streamed++;
		}

		// 2 VAT-ID-bearing partners + 2 VAT-ID-bearing locations = 4; the two VAT-ID-less partners and the
		// VAT-ID-less location contribute nothing.
		assertThat(streamed).as("targets iterateSelectedTargets yields").isEqualTo(4);
		assertThat(repo.countSelectedTargets(selectionId))
				.as("countSelectedTargets must equal what the iterator yields (no drift)")
				.isEqualTo(streamed);
	}
}
