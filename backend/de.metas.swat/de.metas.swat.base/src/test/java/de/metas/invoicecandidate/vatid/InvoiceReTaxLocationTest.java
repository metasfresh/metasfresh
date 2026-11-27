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

package de.metas.invoicecandidate.vatid;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.InvoiceLineReTaxInput;
import de.metas.invoice.service.InvoiceLineTaxLocationProviders;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Tests {@link IInvoiceBL#getReTaxInput} for sales invoice lines without shipment line: the inputs the e2e test cannot see
 * (ship-from country and warehouse passed to the tax query, the candidate chosen for an aggregated line).
 * The location itself is pinned by {@code invoice/vatIdByDelivery/reTaxOnInvoiceDateChange.feature}.
 */
class InvoiceReTaxLocationTest
{
	private static final int ORG_ID = 10;
	private static final Timestamp DATE = TimeUtil.parseTimestamp("2026-11-24");

	private IInvoiceBL invoiceBL;

	private I_C_BP_Group group;
	private int deId;
	private int chId;
	private I_C_BPartner_Location billToDE;
	private I_C_BPartner_Location deliveryNL;
	private I_C_BPartner_Location dropShipNL;
	private WarehouseId warehouseDE;
	private WarehouseId warehouseCH;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		invoiceBL = Services.get(IInvoiceBL.class);

		// the invoice BL looks up the org's country, even if the invoice's warehouse decides the ship-from country
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(ORG_ID);
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		save(orgInfo);

		deId = createCountry("DE");
		chId = createCountry("CH");
		final int nlId = createCountry("NL");

		group = newInstance(I_C_BP_Group.class);
		save(group);

		final I_C_BPartner customer = createBPartner();
		billToDE = createLocation(customer, deId);
		deliveryNL = createLocation(customer, nlId);
		dropShipNL = createLocation(createBPartner(), nlId);

		warehouseDE = createWarehouse(deId);
		warehouseCH = createWarehouse(chId);
	}

	/**
	 * An order line from a Swiss warehouse was taxed with CH as ship-from country; the re-tax keeps it (not the org's or the invoice warehouse's country).
	 */
	@Test
	void orderLine_usesOrderLineWarehouseCountry()
	{
		final I_C_Invoice invoice = createSalesInvoice(warehouseDE);
		final I_C_InvoiceLine line = createLine(invoice);
		line.setC_OrderLine_ID(createOrderLine(deliveryNL, warehouseCH).getC_OrderLine_ID());
		save(line);

		final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, null);

		assertThat(reTaxInput.getCountryFromId()).isEqualTo(CountryId.ofRepoId(chId));
		assertThat(reTaxInput.getTaxLocation().getBpartnerLocationId()).isEqualTo(toBPartnerLocationId(deliveryNL));
	}

	/**
	 * The order line's warehouse goes into the tax query, so that warehouse-dependent rules (warehouse drop-ship) give the same tax as on the order line.
	 */
	@Test
	void orderLine_passesOrderLineWarehouse()
	{
		final I_C_Invoice invoice = createSalesInvoice(warehouseDE);
		final I_C_InvoiceLine line = createLine(invoice);
		line.setC_OrderLine_ID(createOrderLine(deliveryNL, warehouseCH).getC_OrderLine_ID());
		save(line);

		final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, null);

		assertThat(reTaxInput.getWarehouseId()).isEqualTo(warehouseCH);
	}

	/**
	 * A line created from invoice candidates without order line (e.g. order candidates) uses the tax location of its candidate with the lowest ID,
	 * as the candidate's handler reports it (here mocked like an order candidate: drop-ship location with its captured address, org country, no warehouse).
	 */
	@Test
	void olCandCandidate_usesLowestCandidateTaxLocation()
	{
		final I_C_Invoice_Candidate firstCandidate = createCandidate();
		final I_C_Invoice_Candidate secondCandidate = createCandidate();
		final IInvoiceCandidateHandlerBL handlerBL = Mockito.mock(IInvoiceCandidateHandlerBL.class);
		Mockito.when(handlerBL.getTaxLocation(argThat(ic -> ic != null && ic.getC_Invoice_Candidate_ID() == firstCandidate.getC_Invoice_Candidate_ID()), isNull()))
				.thenReturn(new TaxLocation(toBPartnerLocationId(dropShipNL), OrgId.ofRepoId(ORG_ID), DATE, CountryId.ofRepoId(deId))
						.withLocationCaptureId(LocationId.ofRepoId(dropShipNL.getC_Location_ID())));
		Mockito.when(handlerBL.getTaxLocation(argThat(ic -> ic != null && ic.getC_Invoice_Candidate_ID() == secondCandidate.getC_Invoice_Candidate_ID()), isNull()))
				.thenReturn(new TaxLocation(toBPartnerLocationId(deliveryNL), OrgId.ofRepoId(ORG_ID), DATE, CountryId.ofRepoId(chId), warehouseCH));
		Services.registerService(IInvoiceCandidateHandlerBL.class, handlerBL);
		registerProvider();

		final I_C_Invoice invoice = createSalesInvoice(warehouseCH);
		final I_C_InvoiceLine line = createLine(invoice);
		allocate(line, secondCandidate);
		allocate(line, firstCandidate);

		final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, null);

		assertThat(reTaxInput.getTaxLocation().getBpartnerLocationId()).isEqualTo(toBPartnerLocationId(dropShipNL));
		assertThat(reTaxInput.getTaxLocation().getLocationCaptureId()).isEqualTo(LocationId.ofRepoId(dropShipNL.getC_Location_ID()));
		assertThat(reTaxInput.getCountryFromId()).isEqualTo(CountryId.ofRepoId(deId));
		assertThat(reTaxInput.getWarehouseId()).isNull();
	}

	/**
	 * A line without order line and without candidates (added by hand) is re-taxed as before: bill-to, and ship-from country and warehouse of the invoice.
	 */
	@Test
	void noAllocation_noOrderLine_staysOnBillTo_andTodaysFromCountry()
	{
		registerProvider();
		final I_C_Invoice invoice = createSalesInvoice(warehouseCH);
		final I_C_InvoiceLine line = createLine(invoice);

		final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, null);

		assertThat(reTaxInput.getTaxLocation().getBpartnerLocationId()).isEqualTo(toBPartnerLocationId(billToDE));
		assertThat(reTaxInput.getCountryFromId()).isEqualTo(CountryId.ofRepoId(chId));
		assertThat(reTaxInput.getWarehouseId()).isEqualTo(warehouseCH);
	}

	private static void registerProvider()
	{
		SpringContextHolder.registerJUnitBean(InvoiceLineTaxLocationProviders.of(ImmutableList.of(new InvoiceCandidateInvoiceLineTaxLocationProvider())));
	}

	private I_C_Invoice createSalesInvoice(final WarehouseId warehouseId)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(ORG_ID);
		invoice.setIsSOTrx(true);
		invoice.setDateInvoiced(DATE);
		invoice.setM_Warehouse_ID(warehouseId.getRepoId());
		invoice.setC_BPartner_ID(billToDE.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(billToDE.getC_BPartner_Location_ID());
		invoice.setC_BPartner_Location_Value_ID(billToDE.getC_Location_ID());
		save(invoice);
		return invoice;
	}

	private static I_C_InvoiceLine createLine(final I_C_Invoice invoice)
	{
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setAD_Org_ID(ORG_ID);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		save(line);
		return line;
	}

	private static I_C_OrderLine createOrderLine(final I_C_BPartner_Location delivery, final WarehouseId warehouseId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setAD_Org_ID(ORG_ID);
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setAD_Org_ID(ORG_ID);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_BPartner_ID(delivery.getC_BPartner_ID());
		orderLine.setC_BPartner_Location_ID(delivery.getC_BPartner_Location_ID());
		orderLine.setC_BPartner_Location_Value_ID(delivery.getC_Location_ID());
		orderLine.setM_Warehouse_ID(warehouseId.getRepoId());
		save(orderLine);
		return orderLine;
	}

	private static I_C_Invoice_Candidate createCandidate()
	{
		final I_C_Invoice_Candidate candidate = newInstance(I_C_Invoice_Candidate.class);
		candidate.setAD_Org_ID(ORG_ID);
		candidate.setIsSOTrx(true);
		save(candidate);
		return candidate;
	}

	private static void allocate(final I_C_InvoiceLine line, final I_C_Invoice_Candidate candidate)
	{
		final I_C_Invoice_Line_Alloc alloc = newInstance(I_C_Invoice_Line_Alloc.class);
		alloc.setAD_Org_ID(ORG_ID);
		alloc.setC_InvoiceLine_ID(line.getC_InvoiceLine_ID());
		alloc.setC_Invoice_Candidate_ID(candidate.getC_Invoice_Candidate_ID());
		save(alloc);
	}

	private WarehouseId createWarehouse(final int countryId)
	{
		final I_C_BPartner_Location warehouseLocation = createLocation(createBPartner(), countryId);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(ORG_ID);
		warehouse.setC_BPartner_ID(warehouseLocation.getC_BPartner_ID());
		warehouse.setC_BPartner_Location_ID(warehouseLocation.getC_BPartner_Location_ID());
		save(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private I_C_BPartner createBPartner()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BP_Group_ID(group.getC_BP_Group_ID());
		save(bpartner);
		return bpartner;
	}

	private static BPartnerLocationId toBPartnerLocationId(final I_C_BPartner_Location location)
	{
		return BPartnerLocationId.ofRepoId(location.getC_BPartner_ID(), location.getC_BPartner_Location_ID());
	}

	private static int createCountry(final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		save(country);
		return country.getC_Country_ID();
	}

	private static I_C_BPartner_Location createLocation(final I_C_BPartner bpartner, final int countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID);
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setAD_Org_ID(ORG_ID);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		save(bpartnerLocation);
		return bpartnerLocation;
	}
}
