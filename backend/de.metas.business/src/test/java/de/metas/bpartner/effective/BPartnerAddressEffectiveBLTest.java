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

package de.metas.bpartner.effective;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.bpartner.effective.BPartnerEffectiveBLTest.assertBillTo;
import static de.metas.bpartner.effective.BPartnerEffectiveBLTest.createBPartner;
import static de.metas.bpartner.effective.BPartnerEffectiveBLTest.createBillToRelation;
import static de.metas.bpartner.effective.BPartnerEffectiveBLTest.createLocation;
import static de.metas.bpartner.effective.BPartnerEffectiveBLTest.createPlainBPGroup;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bill-to resolution for a partner location: the location's bill-to relation, else the partner-level resolution
 * ({@link BPartnerEffectiveBL#getEffectiveBillBPartner(BPartnerId)}).
 */
public class BPartnerAddressEffectiveBLTest
{
	private BPartnerAddressEffectiveBL bpartnerAddressEffectiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		bpartnerAddressEffectiveBL = BPartnerAddressEffectiveBL.newInstanceForUnitTesting();
	}

	/**
	 * One active bill-to relation is allowed per partner location (unique index C_BP_Relation_UC_IsBillTo),
	 * so the relation is resolved for the given partner location.
	 */
	@Test
	public void getEffectiveBillBPartner_perLocationRelations_resolvedByGivenLocation()
	{
		final I_C_BPartner memberBP = createBPartner(createPlainBPGroup());
		final BPartnerLocationId memberLocA = createLocation(memberBP);
		final BPartnerLocationId memberLocB = createLocation(memberBP);

		final BPartnerLocationId billLocX1 = createLocation(createBPartner(null));
		final BPartnerLocationId billLocY1 = createLocation(createBPartner(null));

		createBillToRelation(memberBP, memberLocA, billLocX1);
		createBillToRelation(memberBP, memberLocB, billLocY1);

		assertBillTo(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(memberLocA), billLocX1);
		assertBillTo(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(memberLocB), billLocY1);
	}

	@Test
	public void getEffectiveBillBPartner_locationWithoutRelation_usesPartnerWideRelation()
	{
		final I_C_BPartner memberBP = createBPartner(createPlainBPGroup());
		final BPartnerLocationId memberLocA = createLocation(memberBP);
		final BPartnerLocationId memberLocC = createLocation(memberBP);

		final BPartnerLocationId billLocX1 = createLocation(createBPartner(null));
		final BPartnerLocationId billLocZ1 = createLocation(createBPartner(null));

		createBillToRelation(memberBP, memberLocA, billLocX1);
		createBillToRelation(memberBP, null, billLocZ1);

		assertBillTo(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(memberLocC), billLocZ1);
	}

	@Test
	public void getEffectiveBillBPartner_locationRelationWinsOverAssociationGroup()
	{
		final I_C_BPartner centralBillingBP = createBPartner(null);
		final I_C_BP_Group assocGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		assocGroup.setIsDeviatingBillBPartner(true);
		assocGroup.setBill_BPartner_ID(centralBillingBP.getC_BPartner_ID());
		saveRecord(assocGroup);

		final I_C_BPartner memberBP = createBPartner(assocGroup);
		final BPartnerLocationId memberLocA = createLocation(memberBP);
		final BPartnerLocationId billLocX1 = createLocation(createBPartner(null));
		createBillToRelation(memberBP, memberLocA, billLocX1);

		assertBillTo(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(memberLocA), billLocX1);
	}

	@Test
	public void getEffectiveBillBPartner_locationWithoutAnyRelation_usesAssociationGroup()
	{
		final I_C_BPartner centralBillingBP = createBPartner(null);
		final BPartnerLocationId centralLoc = createLocation(centralBillingBP);
		final I_C_BP_Group assocGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		assocGroup.setIsDeviatingBillBPartner(true);
		assocGroup.setBill_BPartner_ID(centralBillingBP.getC_BPartner_ID());
		assocGroup.setBill_Location_ID(centralLoc.getRepoId());
		saveRecord(assocGroup);

		final I_C_BPartner memberBP = createBPartner(assocGroup);
		final BPartnerLocationId memberLocA = createLocation(memberBP);

		assertBillTo(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(memberLocA), centralLoc);
	}

	@Test
	public void getEffectiveBillBPartner_neitherRelationNorAssociationGroup_returnsNull()
	{
		final I_C_BPartner memberBP = createBPartner(createPlainBPGroup());

		assertThat(bpartnerAddressEffectiveBL.getEffectiveBillBPartner(createLocation(memberBP))).isNull();
	}
}
