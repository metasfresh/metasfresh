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
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
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

	@Test
	public void vatTaxId_locationVAT_wins()
	{
		final I_C_BPartner bpartner = createBPartner(createPlainBPGroup());
		bpartner.setVATaxID("DE111111125");
		saveRecord(bpartner);
		final BPartnerLocationId locationId = createLocation(bpartner);
		setLocationVATaxID(locationId, "NL333333330B01");

		final BPartnerAddressEffective result = bpartnerAddressEffectiveBL.getDeliveryEffective(null, locationId);

		assertThat(result.getVatTaxId()).isEqualTo(VATIdentifier.of("NL333333330B01"));
	}

	@Test
	public void vatTaxId_noLocationVAT_fallsBackToHeader()
	{
		final I_C_BPartner bpartner = createBPartner(createPlainBPGroup());
		bpartner.setVATaxID("DE111111125");
		saveRecord(bpartner);
		final BPartnerLocationId locationId = createLocation(bpartner);

		final BPartnerAddressEffective result = bpartnerAddressEffectiveBL.getDeliveryEffective(null, locationId);

		assertThat(result.getVatTaxId()).isEqualTo(VATIdentifier.of("DE111111125"));
	}

	@Test
	public void vatTaxId_ignorePartnerVATID_noLocationVAT_isNull()
	{
		Services.get(ISysConfigBL.class).setValue(IBPartnerBL.SYS_CONFIG_IgnorePartnerVATID, true, ClientId.SYSTEM, OrgId.ANY);
		final I_C_BPartner bpartner = createBPartner(createPlainBPGroup());
		bpartner.setVATaxID("DE111111125");
		saveRecord(bpartner);
		final BPartnerLocationId locationId = createLocation(bpartner);

		final BPartnerAddressEffective result = bpartnerAddressEffectiveBL.getDeliveryEffective(null, locationId);

		assertThat(result.getVatTaxId()).isNull();
	}

	@Test
	public void vatTaxId_ignorePartnerVATID_locationVAT_stillWins()
	{
		Services.get(ISysConfigBL.class).setValue(IBPartnerBL.SYS_CONFIG_IgnorePartnerVATID, true, ClientId.SYSTEM, OrgId.ANY);
		final I_C_BPartner bpartner = createBPartner(createPlainBPGroup());
		bpartner.setVATaxID("DE111111125");
		saveRecord(bpartner);
		final BPartnerLocationId locationId = createLocation(bpartner);
		setLocationVATaxID(locationId, "NL333333330B01");

		final BPartnerAddressEffective result = bpartnerAddressEffectiveBL.getDeliveryEffective(null, locationId);

		assertThat(result.getVatTaxId()).isEqualTo(VATIdentifier.of("NL333333330B01"));
	}

	@Test
	public void vatTaxId_dropShipLocation_isUsedInsteadOfMainLocation()
	{
		final I_C_BPartner mainBP = createBPartner(createPlainBPGroup());
		mainBP.setVATaxID("DE111111125");
		saveRecord(mainBP);
		final BPartnerLocationId mainLocationId = createLocation(mainBP);
		setLocationVATaxID(mainLocationId, "DE811128135");

		final I_C_BPartner dropShipBP = createBPartner(createPlainBPGroup());
		final BPartnerLocationId dropShipLocationId = createLocation(dropShipBP);
		setLocationVATaxID(dropShipLocationId, "NL333333330B01");

		final BPartnerAddressEffective result = bpartnerAddressEffectiveBL.getDeliveryEffective(dropShipLocationId, mainLocationId);

		assertThat(result.getVatTaxId()).isEqualTo(VATIdentifier.of("NL333333330B01"));
	}

	private static void setLocationVATaxID(final BPartnerLocationId locationId, final String vatTaxId)
	{
		final I_C_BPartner_Location location = InterfaceWrapperHelper.load(locationId.getRepoId(), I_C_BPartner_Location.class);
		location.setVATaxID(vatTaxId);
		saveRecord(location);
	}
}
