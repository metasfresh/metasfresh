/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.api.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.invoicecandidate.M_InOutLine_Handler;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.location.CountryId;
import de.metas.order.invoicecandidate.C_OrderLine_Handler;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Where, by whom and when an invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class InvoiceCandidateTaxLocationTest extends AbstractICTestSupport
{
	private IInvoiceCandidateHandlerBL handlerBL;

	private I_C_BPartner partner;
	private BPartnerLocationId billLocationId;
	private BPartnerLocationId otherLocationId;

	@BeforeEach
	void init()
	{
		handlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		SpringContextHolder.registerJUnitBean(BPartnerEffectiveBL.newInstanceForUnitTesting()); // needed by M_InOutLine_Handler

		partner = BusinessTestHelper.createBPartner("partner");
		billLocationId = createPartnerLocation();
		otherLocationId = createPartnerLocation();
	}

	@Test
	void withShipmentLine_usesShipmentLocationOrgMovementDate()
	{
		final CountryId warehouseCountryId = createCountry();
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setAD_Org_ID(createOrg().getRepoId());
		inOut.setC_BPartner_ID(partner.getC_BPartner_ID());
		inOut.setC_BPartner_Location_ID(otherLocationId.getRepoId());
		inOut.setM_Warehouse_ID(createWarehouse(orgId, warehouseCountryId).getRepoId());
		inOut.setMovementDate(Timestamp.valueOf("2026-03-04 00:00:00"));
		save(inOut);
		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		final I_C_Invoice_Candidate ic = createIC(plainHandler);

		final TaxLocation result = handlerBL.getTaxLocation(ic, inOutLine);

		assertThat(result.getBpartnerLocationId()).isEqualTo(otherLocationId);
		assertThat(result.getOrgId().getRepoId()).isEqualTo(inOut.getAD_Org_ID());
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-04 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(warehouseCountryId);
	}

	@Test
	void orderLine_noShipment_usesOrderLineLocation_notOrderHeader()
	{
		final CountryId warehouseCountryId = createCountry();
		final I_C_OrderLine orderLine = createOrderLine(otherLocationId, billLocationId, warehouseCountryId, Timestamp.valueOf("2026-03-05 00:00:00"));
		final I_C_Invoice_Candidate ic = createIC(handlerRecord(C_OrderLine_Handler.class));
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getBpartnerLocationId()).isEqualTo(otherLocationId); // the order header has billLocationId
		assertThat(result.getOrgId().getRepoId()).isEqualTo(orderLine.getAD_Org_ID());
		assertThat(result.getFromCountryId()).isEqualTo(warehouseCountryId);
	}

	@Test
	void orderLine_datePromised_isTaxDate()
	{
		final I_C_OrderLine orderLine = createOrderLine(otherLocationId, otherLocationId, createCountry(), Timestamp.valueOf("2026-03-05 00:00:00"));
		final I_C_Invoice_Candidate ic = createIC(handlerRecord(C_OrderLine_Handler.class));
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setDateOrdered(Timestamp.valueOf("2026-01-01 00:00:00"));
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-05 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isNotNull();
	}

	@Test
	void inOutLineHandler_usesInOutDeliveryLocation()
	{
		final CountryId warehouseCountryId = createCountry();
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setC_BPartner_ID(partner.getC_BPartner_ID());
		inOut.setC_BPartner_Location_ID(otherLocationId.getRepoId());
		inOut.setM_Warehouse_ID(createWarehouse(orgId, warehouseCountryId).getRepoId());
		inOut.setMovementDate(Timestamp.valueOf("2026-03-06 00:00:00"));
		save(inOut);
		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setAD_Org_ID(orgId.getRepoId());
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		final I_C_Invoice_Candidate ic = createIC(handlerRecord(M_InOutLine_Handler.class));
		ic.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOutLine.class));
		ic.setRecord_ID(inOutLine.getM_InOutLine_ID());
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getBpartnerLocationId()).isEqualTo(otherLocationId); // the IC has billLocationId
		assertThat(result.getOrgId()).isEqualTo(orgId);
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-06 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(warehouseCountryId);
	}

	@Test
	void manualHandler_usesWarehouseOrg()
	{
		// the org's default warehouse belongs to another org: that org is the one the tax lookup uses
		final OrgId warehouseOrgId = createOrg();
		final CountryId warehouseCountryId = createCountry();
		final I_AD_OrgInfo orgInfo = loadOrgInfo();
		orgInfo.setM_Warehouse_ID(createWarehouse(warehouseOrgId, warehouseCountryId).getRepoId());
		save(orgInfo);

		final I_C_Invoice_Candidate ic = createIC(handlerRecord(ManualCandidateHandler.class));
		ic.setIsSOTrx(true);
		ic.setDateOrdered(Timestamp.valueOf("2026-03-07 00:00:00"));
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(result.getOrgId()).isEqualTo(warehouseOrgId);
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-07 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(warehouseCountryId);
	}

	@Test
	void manualHandler_purchase_usesPurchaseWarehouseOrg()
	{
		final OrgId poWarehouseOrgId = createOrg();
		final CountryId poWarehouseCountryId = createCountry();
		final I_AD_OrgInfo orgInfo = loadOrgInfo();
		orgInfo.setM_Warehouse_ID(createWarehouse(createOrg(), createCountry()).getRepoId()); // the sales warehouse must be ignored
		orgInfo.setM_WarehousePO_ID(createWarehouse(poWarehouseOrgId, poWarehouseCountryId).getRepoId());
		save(orgInfo);

		final I_C_Invoice_Candidate ic = createIC(handlerRecord(ManualCandidateHandler.class));
		ic.setIsSOTrx(false);
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getOrgId()).isEqualTo(poWarehouseOrgId);
		assertThat(result.getFromCountryId()).isEqualTo(poWarehouseCountryId);
	}

	@Test
	void manualHandler_noOrgWarehouse_usesCandidateOrgAndNoCountry()
	{
		final I_C_Invoice_Candidate ic = createIC(handlerRecord(ManualCandidateHandler.class));
		ic.setIsSOTrx(true);
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getOrgId()).isEqualTo(orgId);
		assertThat(result.getFromCountryId()).isNull();
	}

	@Test
	void withShipmentLine_noWarehouse_hasNoCountry()
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setAD_Org_ID(orgId.getRepoId());
		inOut.setC_BPartner_ID(partner.getC_BPartner_ID());
		inOut.setC_BPartner_Location_ID(otherLocationId.getRepoId());
		inOut.setMovementDate(Timestamp.valueOf("2026-03-04 00:00:00"));
		save(inOut);
		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		save(inOutLine);

		final TaxLocation result = handlerBL.getTaxLocation(createIC(plainHandler), inOutLine);

		assertThat(result.getBpartnerLocationId()).isEqualTo(otherLocationId);
		assertThat(result.getFromCountryId()).isNull();
	}

	@Test
	void otherHandler_fallsBackToBillLocationAndDateOrdered()
	{
		final I_C_Invoice_Candidate ic = createIC(plainHandler);
		ic.setDateOrdered(Timestamp.valueOf("2026-03-08 00:00:00"));
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(result.getOrgId()).isEqualTo(orgId);
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-08 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isNull(); // not known: a re-tax keeps its default
	}

	@Test
	void otherHandler_noDateOrdered_fallsBackToPresetDateInvoiced()
	{
		final I_C_Invoice_Candidate ic = createIC(plainHandler);
		ic.setDateOrdered(null);
		ic.setPresetDateInvoiced(Timestamp.valueOf("2026-03-09 00:00:00"));
		ic.setDateInvoiced(Timestamp.valueOf("2026-03-10 00:00:00"));
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-09 00:00:00").toInstant());
	}

	@Test
	void otherHandler_noDateOrderedNorPreset_fallsBackToDateInvoiced()
	{
		final I_C_Invoice_Candidate ic = createIC(plainHandler);
		ic.setDateOrdered(null);
		ic.setDateInvoiced(Timestamp.valueOf("2026-03-10 00:00:00"));
		save(ic);

		final TaxLocation result = handlerBL.getTaxLocation(ic, null);

		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-10 00:00:00").toInstant());
	}

	@Test
	void otherHandler_noDateAtAll_failsNamingTheCandidate()
	{
		final I_C_Invoice_Candidate ic = createIC(plainHandler);
		ic.setDateOrdered(null);
		save(ic);

		assertThatThrownBy(() -> handlerBL.getTaxLocation(ic, null))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("DateOrdered")
				.hasMessageContaining(String.valueOf(ic.getC_Invoice_Candidate_ID()));
	}

	private I_AD_OrgInfo loadOrgInfo()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_OrgInfo.class)
				.addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnlyNotNull(I_AD_OrgInfo.class);
	}

	private I_C_Invoice_Candidate createIC(final I_C_ILCandHandler handler)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(orgId.getRepoId());
		ic.setBill_BPartner_ID(partner.getC_BPartner_ID());
		ic.setBill_Location_ID(billLocationId.getRepoId());
		ic.setDateOrdered(Timestamp.valueOf("2026-01-01 00:00:00"));
		ic.setC_ILCandHandler_ID(handler.getC_ILCandHandler_ID());
		save(ic);
		return ic;
	}

	private I_C_ILCandHandler handlerRecord(final Class<?> handlerClass)
	{
		final I_C_ILCandHandler handler = InterfaceWrapperHelper.create(Env.getCtx(), I_C_ILCandHandler.class, org.adempiere.ad.trx.api.ITrx.TRXNAME_None);
		handler.setClassname(handlerClass.getName());
		save(handler);
		return handler;
	}

	private I_C_OrderLine createOrderLine(
			final BPartnerLocationId lineLocationId,
			final BPartnerLocationId headerLocationId,
			final CountryId lineWarehouseCountryId,
			final Timestamp datePromised)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setAD_Org_ID(orgId.getRepoId());
		order.setIsSOTrx(true);
		order.setC_BPartner_ID(partner.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(headerLocationId.getRepoId());
		order.setM_Warehouse_ID(warehouseId.getRepoId());
		order.setDatePromised(Timestamp.valueOf("2026-02-01 00:00:00"));
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setAD_Org_ID(orgId.getRepoId());
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(partner.getC_BPartner_ID());
		orderLine.setC_BPartner_Location_ID(lineLocationId.getRepoId());
		orderLine.setM_Warehouse_ID(createWarehouse(orgId, lineWarehouseCountryId).getRepoId());
		orderLine.setDatePromised(datePromised);
		save(orderLine);
		return orderLine;
	}

	private BPartnerLocationId createPartnerLocation()
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		save(location);
		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpl.setC_Location_ID(location.getC_Location_ID());
		save(bpl);
		return BPartnerLocationId.ofRepoId(partner.getC_BPartner_ID(), bpl.getC_BPartner_Location_ID());
	}

	private CountryId createCountry()
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}

	private OrgId createOrg()
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		save(org);
		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	private WarehouseId createWarehouse(final OrgId warehouseOrgId, final CountryId countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		save(location);
		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpl.setC_Location_ID(location.getC_Location_ID());
		save(bpl);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(warehouseOrgId.getRepoId());
		warehouse.setC_BPartner_ID(partner.getC_BPartner_ID());
		warehouse.setC_BPartner_Location_ID(bpl.getC_BPartner_Location_ID());
		save(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}
}
