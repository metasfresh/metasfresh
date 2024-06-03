/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ordercandidate.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.interfaces.I_C_BPartner;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.util.Services;
import groovy.lang.Tuple2;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class C_OLCand_SetOverrideValuesTest
{
	private final Tuple2<BPartnerLocationId, GLN> bp1LocGLN_123 = new Tuple2<>(
			BPartnerLocationId.ofRepoId(1, 100),
			GLN.ofString("123"));
	private final Tuple2<BPartnerLocationId, GLN> bp1LocGLN_abc = new Tuple2<>(
			BPartnerLocationId.ofRepoId(1, 101),
			GLN.ofString("abc"));
	private final Tuple2<BPartnerLocationId, GLN> bp2LocGLN_abc = new Tuple2<>(
			BPartnerLocationId.ofRepoId(2, 102),
			GLN.ofString("abc"));
	private final Tuple2<BPartnerLocationId, GLN> bp3LocGLN_abcd = new Tuple2<>(
			BPartnerLocationId.ofRepoId(3, 103),
			GLN.ofString("abcd"));

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		createTestData();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
	}

	@Test
	void testWithNoMatchingGLN()
	{
		final I_C_OLCand olCand = createOLCandData(bp1LocGLN_123);
		final C_OLCand_SetOverrideValues process = getProcess(olCand.getC_OLCand_ID(), bp2LocGLN_abc.getFirst().getBpartnerId());
		assertThrows(AdempiereException.class, process::prepare);
	}

	@Test
	void testbp1WithMatchingGLN()
	{
		final I_C_OLCand olCand = createOLCandData(bp1LocGLN_abc);
		final C_OLCand_SetOverrideValues process = getProcess(olCand.getC_OLCand_ID(), bp2LocGLN_abc.getFirst().getBpartnerId());
		assertDoesNotThrow(process::prepare);
		assertEquals(olCand.getC_BP_Location_Override_ID(), 0);
		assertDoesNotThrow(process::doIt);

		InterfaceWrapperHelper.refresh(olCand);
		assertEquals(olCand.getC_BP_Location_Override_ID(), bp2LocGLN_abc.getFirst().getRepoId());
	}

	private C_OLCand_SetOverrideValues getProcess(final Integer olCandId, final BPartnerId bpartnerOverrideId)
	{
		final C_OLCand_SetOverrideValues overrideValuesProcess = new C_OLCand_SetOverrideValues()
		{
			@Override
			protected IQueryFilter<I_C_OLCand> getQueryFilter()
			{
				return queryBL.createCompositeQueryFilter(I_C_OLCand.class)
						.addEqualsFilter(I_C_OLCand.COLUMNNAME_C_OLCand_ID, olCandId);
			}
		};
		overrideValuesProcess.init(ProcessInfo.builder()
				.addParameter(I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID, bpartnerOverrideId.getRepoId())
				.addParameter(I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID, "")
				.setWhereClause(I_C_OLCand.COLUMNNAME_C_OLCand_ID + "=" + olCandId)
				.setCtx(Env.getCtx())
				.build());

		return overrideValuesProcess;
	}

	private void createTestData()
	{
		createBpartnerLocationData(bp1LocGLN_123);
		createBpartnerLocationData(bp1LocGLN_abc);
		createBpartnerLocationData(bp2LocGLN_abc);
		createBpartnerLocationData(bp3LocGLN_abcd);
	}

	private I_C_OLCand createOLCandData(final Tuple2<BPartnerLocationId, GLN> bpLoc)
	{
		final I_C_OLCand cand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class);
		cand.setC_BPartner_ID(bpLoc.getFirst().getBpartnerId().getRepoId());
		cand.setC_BPartner_Location_ID(bpLoc.getFirst().getRepoId());
		cand.setAD_User_ID(-1);
		InterfaceWrapperHelper.save(cand);
		return cand;

	}

	private void createBpartnerLocationData(final Tuple2<BPartnerLocationId, GLN> bpLoc)
	{
		//bpartner
		final BPartnerId bpartnerId = bpLoc.getFirst().getBpartnerId();
		I_C_BPartner bpartner;
		try
		{
			bpartner = InterfaceWrapperHelper.load(bpartnerId, I_C_BPartner.class);
		}
		catch (final RuntimeException e)
		{
			bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bpartner.setC_BPartner_ID(bpartnerId.getRepoId());
			bpartner.setIsActive(true);
			InterfaceWrapperHelper.save(bpartner);
		}

		//location
		final I_C_BPartner_Location location = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		location.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		location.setC_BPartner_Location_ID(bpLoc.getFirst().getRepoId());
		location.setGLN(bpLoc.getSecond().getCode());
		location.setIsBillTo(true);
		InterfaceWrapperHelper.save(location);
	}
}
