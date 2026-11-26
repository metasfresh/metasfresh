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

package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.vatid.InvoiceCandidateVATaxIdResolver;
import de.metas.location.ICountryAreaBL;
import de.metas.money.MoneyService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.SOPOType;
import de.metas.tax.api.TypeOfDestCountry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The seller VAT ID that goes onto the invoice header and the seller part of the header aggregation key come from the same resolution.
 */
public class AggregationEngineVATaxIdsTest extends AbstractAggregationEngineTestBase
{
	private static final String REGISTRATION_VAT_ID = "EL123456783";
	private static final Timestamp SHIP_DATE = TimeUtil.parseTimestamp("2026-04-12");

	private int euAreaId;

	@BeforeEach
	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository())); // needed by InvoiceLineBL
	}

	@Test
	public void storedSellerVATaxId_equalsKeySellerPart()
	{
		createEUArea();
		final int grId = createCountry("GR");

		// the candidate's own tax is a plain tax (no fiscal representation) ...
		final I_C_BPartner customer = createBPartner();
		final I_C_BPartner_Location billing = BusinessTestHelper.createBPartnerLocation(customer); // the builder gives it an own address
		final I_C_Invoice_Candidate ic = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(BPartnerLocationId.ofRepoId(customer.getC_BPartner_ID(), billing.getC_BPartner_Location_ID()))
				.setPriceEntered(1)
				.setQtyOrdered(10)
				.setSOTrx(true)
				.build();
		assertThat(tax_Default.getIsFiscalRepresentation()).isNull();

		// ... but the goods leave from a Greek warehouse to the Greek address, where the organisation is registered:
		// the invoice line from the shipment gets the GR->GR tax that is flagged IsFiscalRepresentation
		final I_C_BPartner_Location grDelivery = createLocation(customer, grId);
		final I_C_BPartner orgPartner = createBPartner();
		final I_M_Warehouse grWarehouse = createWarehouse(createLocation(orgPartner, grId));
		createTax(grId, grId);
		createRegistration(grId);

		final I_M_InOut shipment = createInOut(customer.getC_BPartner_ID(), ic.getC_Order_ID(), "1");
		shipment.setAD_Org_ID(orgId.getRepoId());
		shipment.setIsSOTrx(true);
		shipment.setMovementDate(SHIP_DATE);
		shipment.setM_Warehouse_ID(grWarehouse.getM_Warehouse_ID());
		shipment.setC_BPartner_Location_ID(grDelivery.getC_BPartner_Location_ID());
		shipment.setC_BPartner_Location_Value_ID(grDelivery.getC_Location_ID());
		save(shipment);
		createInvoiceCandidateInOutLine(ic, shipment, StockQtyAndUOMQtys.create(BigDecimal.TEN, productId, new BigDecimal("100"), uomId), "1");
		completeInOut(shipment);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic);

		// the real resolver instead of the fixture's "no VAT IDs" one
		final AggregationEngine engine = AggregationEngine.newInstanceForUnitTesting()
				.vatTaxIdResolver(new InvoiceCandidateVATaxIdResolver(InvoiceVATaxIdService.newInstanceForUnitTesting()))
				.build();
		engine.addInvoiceCandidate(ic);
		final List<IInvoiceHeader> invoices = engine.aggregate();

		assertThat(invoices).hasSize(1);
		final InvoiceVATaxIds invoiceVATaxIds = invoices.get(0).getInvoiceVATaxIds();
		assertThat(invoiceVATaxIds).isNotNull();
		assertThat(invoiceVATaxIds.getSellerVATaxId()).isEqualTo(REGISTRATION_VAT_ID);
		final List<String> headerAggregationKeys = engine.getHeaderAggregationKeyStrings();
		assertThat(headerAggregationKeys).hasSize(1);
		assertThat(headerAggregationKeys.get(0)).contains("SellerVAT=" + REGISTRATION_VAT_ID);
	}

	private void createEUArea()
	{
		final I_C_CountryArea countryArea = newInstance(I_C_CountryArea.class);
		countryArea.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
		save(countryArea);
		euAreaId = countryArea.getC_CountryArea_ID();
	}

	private int createCountry(final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		save(country);

		final I_C_CountryArea_Assign assign = newInstance(I_C_CountryArea_Assign.class);
		assign.setC_CountryArea_ID(euAreaId);
		assign.setC_Country_ID(country.getC_Country_ID());
		assign.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		save(assign);
		return country.getC_Country_ID();
	}

	private I_C_BPartner createBPartner()
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		save(group);

		final I_C_BPartner bpartner = BusinessTestHelper.createBPartner("bp");
		bpartner.setC_BP_Group_ID(group.getC_BP_Group_ID());
		save(bpartner);
		return bpartner;
	}

	private I_C_BPartner_Location createLocation(final I_C_BPartner bpartner, final int countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bpartnerLocation.setIsShipTo(true);
		bpartnerLocation.setIsBillTo(true);
		save(bpartnerLocation);
		return bpartnerLocation;
	}

	private I_M_Warehouse createWarehouse(final I_C_BPartner_Location location)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(orgId.getRepoId());
		warehouse.setC_BPartner_ID(location.getC_BPartner_ID());
		warehouse.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		save(warehouse);
		return warehouse;
	}

	private void createTax(final int fromCountryId, final int toCountryId)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("GR-GR");
		tax.setAD_Org_ID(orgId.getRepoId());
		tax.setC_Country_ID(fromCountryId);
		tax.setTo_Country_ID(toCountryId);
		tax.setTypeOfDestCountry(TypeOfDestCountry.DOMESTIC.getCode());
		tax.setRequiresTaxCertificate(null);
		tax.setIsSmallbusiness(null);
		tax.setIsFiscalRepresentation("Y");
		tax.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		tax.setSOPOType(SOPOType.SALES_TAX.getCode());
		tax.setC_TaxCategory_ID(tax_Default.getC_TaxCategory_ID());
		tax.setRate(new BigDecimal("24"));
		save(tax);
	}

	private void createRegistration(final int toCountryId)
	{
		final I_C_Fiscal_Representation registration = newInstance(I_C_Fiscal_Representation.class);
		registration.setAD_Org_ID(orgId.getRepoId());
		registration.setTo_Country_ID(toCountryId);
		registration.setVATaxID(REGISTRATION_VAT_ID);
		registration.setValidFrom(TimeUtil.parseTimestamp("2026-04-11"));
		registration.setValidTo(TimeUtil.parseTimestamp("2026-04-13"));
		save(registration);
	}
}
