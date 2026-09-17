package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractComparableAssert;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class BPartnerDAOTest
{
	private BPartnerDAO bpartnerDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAO = new BPartnerDAO();
	}

	@Test
	public void retrieveAllDiscountSchemaIdsIndexedByBPartnerId()
	{
		final I_C_BPartner bPartnerRecord1 = newInstance(I_C_BPartner.class);
		bPartnerRecord1.setPO_DiscountSchema_ID(23);
		save(bPartnerRecord1);

		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setPO_DiscountSchema_ID(24);
		save(group);

		final I_C_BPartner bPartnerRecord2 = newInstance(I_C_BPartner.class);
		bPartnerRecord2.setC_BP_Group(group);
		save(bPartnerRecord2);

		// invoke the method under test
		final Map<BPartnerId, Integer> result = bpartnerDAO
				.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType.VENDOR);

		assertThat(result)
				.hasSize(2)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord1.getC_BPartner_ID()), 23)
				.containsEntry(BPartnerId.ofRepoId(bPartnerRecord2.getC_BPartner_ID()), 24);
	}

	@Test
	public void retrieveBPartnerIdByName()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");
		final BPartnerId bpartnerId2 = createBPartnerWithName("BPartner 2");

		assertRetrieveBPartnerIdByName("BPartner 1").isEqualTo(bpartnerId1);
		assertRetrieveBPartnerIdByName("BPartner 2").isEqualTo(bpartnerId2);
		assertRetrieveBPartnerIdByName("BPartner").isNull();
	}

	private AbstractComparableAssert<?, BPartnerId> assertRetrieveBPartnerIdByName(final String queryBPName)
	{
		final BPartnerId bpartnerId = bpartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
				.bpartnerName(queryBPName)
				.onlyOrgId(OrgId.ANY)
				.failIfNotExists(false)
				.build())
				.orElse(null);
		return assertThat(bpartnerId);
	}

	@Test
	public void retrieveBPartnerIdBy_notFound()
	{
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("noSuchPartner")
				.onlyOrgId(OrgId.ofRepoId(20))
				.onlyOrgId(OrgId.ANY)
				.failIfNotExists(true)
				.build();

		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerIdBy(query))
				.isInstanceOf(BPartnerIdNotFoundException.class)
				.hasMessage("Found no existing BPartner;"
						+ " Searched via the following properties one-after-one (list may be empty): Value/Code=noSuchPartner;"
						+ " The search was restricted to the following orgIds (empty means no restriction): [20, 0]");
	}

	@Test
	public void getBPartnerIdByValue_disambiguateByCustomerFlag()
	{
		// Create two BPartners with same Value: one customer, one vendor
		final BPartnerId customerId = createBPartnerWithValueAndFlags("SHARED_VAL", true, false);
		final BPartnerId vendorId = createBPartnerWithValueAndFlags("SHARED_VAL", false, true);

		// With isCustomerFilter=true, should find the customer
		final BPartnerQuery customerQuery = BPartnerQuery.builder()
				.bpartnerValue("SHARED_VAL")
				.onlyOrgId(OrgId.ANY)
				.isCustomerFilter(true)
				.build();
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(customerQuery).orElse(null))
				.isEqualTo(customerId);

		// With isVendorFilter=true, should find the vendor
		final BPartnerQuery vendorQuery = BPartnerQuery.builder()
				.bpartnerValue("SHARED_VAL")
				.onlyOrgId(OrgId.ANY)
				.isVendorFilter(true)
				.build();
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(vendorQuery).orElse(null))
				.isEqualTo(vendorId);
	}

	@Test
	public void getBPartnerIdByValue_singleResult_noDisambiguationNeeded()
	{
		final BPartnerId bpartnerId = createBPartnerWithValueAndFlags("UNIQUE_VAL", true, false);

		// Even without filter, single result should be returned
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("UNIQUE_VAL")
				.onlyOrgId(OrgId.ANY)
				.build();
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(query).orElse(null))
				.isEqualTo(bpartnerId);
	}

	@Test
	public void getBPartnerIdByValue_duplicateWithNoFilter_throwsException()
	{
		// Two BPartners with same Value, no filter — should throw because disambiguation is not possible
		createBPartnerWithValueAndFlags("DUP_VAL", true, false);
		createBPartnerWithValueAndFlags("DUP_VAL", false, true);

		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("DUP_VAL")
				.onlyOrgId(OrgId.ANY)
				.build();

		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerIdBy(query))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("DUP_VAL");
	}

	@Test
	public void getBPartnerIdByName_disambiguateByVendorFlag()
	{
		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setName("Shared Name");
		customer.setIsCustomer(true);
		customer.setIsVendor(false);
		saveRecord(customer);

		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setName("Shared Name");
		vendor.setIsCustomer(false);
		vendor.setIsVendor(true);
		saveRecord(vendor);

		final BPartnerQuery vendorQuery = BPartnerQuery.builder()
				.bpartnerName("Shared Name")
				.onlyOrgId(OrgId.ANY)
				.isVendorFilter(true)
				.build();
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(vendorQuery).orElse(null))
				.isEqualTo(BPartnerId.ofRepoId(vendor.getC_BPartner_ID()));
	}

	// =====================================================================================
	// Tests for #1, #2, #3 — retrieveBPartnerByValue throws on duplicates
	// (ESR import, PO-from-SO, and pricing schema import all catch this exception)
	// =====================================================================================

	@Test
	public void retrieveBPartnerByValue_throwsOnDuplicates()
	{
		// Given: two BPartners with same Value (customer + vendor)
		createBPartnerWithValueAndFlags("SHARED", true, false);
		createBPartnerWithValueAndFlags("SHARED", false, true);

		// When/Then: retrieveBPartnerByValue uses firstOnly() which throws
		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), "SHARED"))
				.isInstanceOf(DBMoreThanOneRecordsFoundException.class);
	}

	@Test
	public void retrieveBPartnerByValue_worksForUniqueValue()
	{
		// Given: a single BPartner with a unique Value
		final BPartnerId bpartnerId = createBPartnerWithValueAndFlags("UNIQUE", true, false);

		// When
		final I_C_BPartner result = bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), "UNIQUE");

		// Then
		assertThat(result).isNotNull();
		assertThat(BPartnerId.ofRepoId(result.getC_BPartner_ID())).isEqualTo(bpartnerId);
	}

	@Test
	public void retrieveBPartnerByValue_returnsNullForMissing()
	{
		// When
		final I_C_BPartner result = bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), "NONEXISTENT");

		// Then
		assertThat(result).isNull();
	}

	// =====================================================================================
	// Tests for disambiguation edge cases
	// =====================================================================================

	@Test
	public void getBPartnerIdByValue_bothCustomerAndVendor_filterForCustomer()
	{
		// Given: one BPartner that is BOTH customer and vendor, plus a vendor-only
		final BPartnerId bothId = createBPartnerWithValueAndFlags("MULTI", true, true);
		final BPartnerId vendorOnlyId = createBPartnerWithValueAndFlags("MULTI", false, true);

		// When: filter for customer
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("MULTI")
				.onlyOrgId(OrgId.ANY)
				.isCustomerFilter(true)
				.build();

		// Then: should find the one that IS a customer (bothId)
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(query).orElse(null))
				.isEqualTo(bothId);
	}

	@Test
	public void getBPartnerIdByValue_disambiguationFailsGracefully_throwsException()
	{
		// Given: two BPartners both marked as customer
		createBPartnerWithValueAndFlags("AMBIG", true, false);
		createBPartnerWithValueAndFlags("AMBIG", true, false);

		// When: filter for customer — still ambiguous (both match)
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("AMBIG")
				.onlyOrgId(OrgId.ANY)
				.isCustomerFilter(true)
				.build();

		// Then: throws because disambiguation still yields >1 result
		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerIdBy(query))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("AMBIG");
	}

	@Test
	public void getBPartnerIdByValue_filterForVendor_noMatchThrowsException()
	{
		// Given: two BPartners, both customers (no vendor)
		createBPartnerWithValueAndFlags("CUST_ONLY", true, false);
		createBPartnerWithValueAndFlags("CUST_ONLY", true, false);

		// When: filter for vendor — no match among candidates
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("CUST_ONLY")
				.onlyOrgId(OrgId.ANY)
				.isVendorFilter(true)
				.build();

		// Then: disambiguation returns empty, but still ambiguous — throws
		assertThatThrownBy(() -> bpartnerDAO.retrieveBPartnerIdBy(query))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("CUST_ONLY");
	}

	@Test
	public void getBPartnerIdByName_disambiguateByCustomerFlag()
	{
		// Given
		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setName("Shared Name 2");
		customer.setIsCustomer(true);
		customer.setIsVendor(false);
		saveRecord(customer);

		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setName("Shared Name 2");
		vendor.setIsCustomer(false);
		vendor.setIsVendor(true);
		saveRecord(vendor);

		// When: filter for customer by name
		final BPartnerQuery customerQuery = BPartnerQuery.builder()
				.bpartnerName("Shared Name 2")
				.onlyOrgId(OrgId.ANY)
				.isCustomerFilter(true)
				.build();

		// Then
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(customerQuery).orElse(null))
				.isEqualTo(BPartnerId.ofRepoId(customer.getC_BPartner_ID()));
	}

	@Test
	public void getBPartnerIdByValue_combinedFilters()
	{
		// Given: vendor-only, customer-only, and both
		createBPartnerWithValueAndFlags("COMBO", false, true);   // vendor
		createBPartnerWithValueAndFlags("COMBO", true, false);   // customer
		final BPartnerId bothId = createBPartnerWithValueAndFlags("COMBO", true, true); // both

		// When: filter for both customer AND vendor
		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue("COMBO")
				.onlyOrgId(OrgId.ANY)
				.isCustomerFilter(true)
				.isVendorFilter(true)
				.build();

		// Then: only the "both" partner matches both filters
		assertThat(bpartnerDAO.retrieveBPartnerIdBy(query).orElse(null))
				.isEqualTo(bothId);
	}

	private BPartnerId createBPartnerWithName(final String name)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setName(name);
		saveRecord(record);

		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	private BPartnerId createBPartnerWithValueAndFlags(final String value, final boolean isCustomer, final boolean isVendor)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setValue(value);
		record.setName(value);
		record.setIsCustomer(isCustomer);
		record.setIsVendor(isVendor);
		saveRecord(record);

		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}
}
