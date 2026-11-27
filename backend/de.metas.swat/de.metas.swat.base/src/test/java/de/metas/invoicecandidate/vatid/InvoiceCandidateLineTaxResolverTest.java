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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.ICountryAreaBL;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.tax.api.SOPOType;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TypeOfDestCountry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCandidateLineTaxResolverTest
{
	private static final int ORG_ID = 10;
	private static final int DE_ID = 3001;
	private static final int NL_ID = 3002;
	private static final int GR_ID = 3003;
	private static final int TAX_CATEGORY_ID = 44;
	private static final int PRODUCT_ID = 55;
	private static final Timestamp SHIP_DATE = TimeUtil.parseTimestamp("2026-04-12");

	private InvoiceCandidateLineTaxResolver resolver;
	private int euAreaId;

	private I_C_BPartner_Location deDelivery;
	private I_C_BPartner_Location nlDelivery;
	private I_C_BPartner_Location grDelivery;
	private I_M_Warehouse deWarehouse;
	private I_M_Warehouse dropShipWarehouse;

	private TaxId taxDeToDe;
	private TaxId taxDeToNl;
	private TaxId taxDeToGr;
	private TaxId taxGrToGr;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository())); // needed by InvoiceLineBL
		resolver = new InvoiceCandidateLineTaxResolver();

		createEUArea();
		createCountry(DE_ID, "DE");
		createCountry(NL_ID, "NL");
		createCountry(GR_ID, "GR");

		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(ORG_ID);
		org.setIsDropShipTaxFromDestCountry(true);
		save(org);

		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setAD_Org_ID(ORG_ID);
		save(customer);
		deDelivery = createLocation(customer, DE_ID);
		nlDelivery = createLocation(customer, NL_ID);
		grDelivery = createLocation(customer, GR_ID);

		final I_C_BPartner orgPartner = newInstance(I_C_BPartner.class);
		orgPartner.setAD_Org_ID(ORG_ID);
		save(orgPartner);
		deWarehouse = createWarehouse(createLocation(orgPartner, DE_ID), false);
		dropShipWarehouse = createWarehouse(createLocation(orgPartner, DE_ID), true);

		// the organisation's registration in Greece
		final I_C_Fiscal_Representation registration = newInstance(I_C_Fiscal_Representation.class);
		registration.setAD_Org_ID(ORG_ID);
		registration.setTo_Country_ID(GR_ID);
		registration.setValidFrom(TimeUtil.parseTimestamp("2026-04-11"));
		registration.setValidTo(TimeUtil.parseTimestamp("2026-04-13"));
		save(registration);

		taxDeToDe = createTax(DE_ID, DE_ID, DOMESTIC, "19", null);
		taxDeToNl = createTax(DE_ID, NL_ID, WITHIN_COUNTRY_AREA, "0", null);
		taxDeToGr = createTax(DE_ID, GR_ID, WITHIN_COUNTRY_AREA, "0", null);
		taxGrToGr = createTax(GR_ID, GR_ID, DOMESTIC, "24", "Y");
	}

	@Test
	void withoutShipmentLine_usesEffectiveTax()
	{
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);

		assertThat(resolver.getLineTax(ic, null)).isEqualTo(taxDeToDe);
	}

	@Test
	void withoutShipmentLine_taxOverride_usesOverride()
	{
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);
		ic.setC_Tax_Override_ID(taxDeToNl.getRepoId());
		save(ic);

		assertThat(resolver.getLineTax(ic, null)).isEqualTo(taxDeToNl);
	}

	@Test
	void withShipmentLine_predictsShipmentTax()
	{
		// the candidate was taxed for the German address, the goods were shipped to the Dutch one
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);
		final I_M_InOutLine shipmentLine = createShipmentLine(deWarehouse, nlDelivery);

		assertThat(resolver.getLineTax(ic, shipmentLine)).isEqualTo(taxDeToNl);
	}

	@Test
	void withShipmentLine_normalWarehouse_toGreece_predictsDeToGr()
	{
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);
		final I_M_InOutLine shipmentLine = createShipmentLine(deWarehouse, grDelivery);

		assertThat(resolver.getLineTax(ic, shipmentLine)).isEqualTo(taxDeToGr);
	}

	@Test
	void withShipmentLine_warehouseDropShip_predictsGrToGr()
	{
		// the shipment's warehouse is passed to the tax query, so the drop-ship ship-from rule applies
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);
		final I_M_InOutLine shipmentLine = createShipmentLine(dropShipWarehouse, grDelivery);

		assertThat(resolver.getLineTax(ic, shipmentLine)).isEqualTo(taxGrToGr);
	}

	@Test
	void withShipmentLine_candidateWithoutProduct_usesEffectiveTax()
	{
		// a line without product is not re-taxed from the shipment
		final I_C_Invoice_Candidate ic = createCandidate(taxDeToDe);
		ic.setM_Product_ID(-1);
		save(ic);
		final I_M_InOutLine shipmentLine = createShipmentLine(deWarehouse, nlDelivery);

		assertThat(resolver.getLineTax(ic, shipmentLine)).isEqualTo(taxDeToDe);
	}

	private I_C_Invoice_Candidate createCandidate(final TaxId tax)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(ORG_ID);
		ic.setM_Product_ID(PRODUCT_ID);
		ic.setC_Tax_ID(tax.getRepoId());
		ic.setIsSOTrx(true);
		save(ic);
		return ic;
	}

	private I_M_InOutLine createShipmentLine(final I_M_Warehouse warehouse, final I_C_BPartner_Location delivery)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setAD_Org_ID(ORG_ID);
		shipment.setIsSOTrx(true);
		shipment.setMovementDate(SHIP_DATE);
		shipment.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		shipment.setC_BPartner_ID(delivery.getC_BPartner_ID());
		shipment.setC_BPartner_Location_ID(delivery.getC_BPartner_Location_ID());
		shipment.setC_BPartner_Location_Value_ID(delivery.getC_Location_ID());
		save(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setAD_Org_ID(ORG_ID);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		shipmentLine.setM_Product_ID(PRODUCT_ID);
		save(shipmentLine);
		return shipmentLine;
	}

	private I_M_Warehouse createWarehouse(final I_C_BPartner_Location location, final boolean dropShip)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setAD_Org_ID(ORG_ID);
		warehouse.setC_BPartner_ID(location.getC_BPartner_ID());
		warehouse.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		warehouse.setIsDropShipWarehouse(dropShip);
		save(warehouse);
		return warehouse;
	}

	private I_C_BPartner_Location createLocation(final I_C_BPartner bpartner, final int countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAD_Org_ID(ORG_ID);
		location.setC_Country_ID(countryId);
		save(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setAD_Org_ID(ORG_ID);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		bpartnerLocation.setIsShipTo(true);
		save(bpartnerLocation);
		return bpartnerLocation;
	}

	private void createEUArea()
	{
		final I_C_CountryArea countryArea = newInstance(I_C_CountryArea.class);
		countryArea.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
		save(countryArea);
		euAreaId = countryArea.getC_CountryArea_ID();
	}

	private void createCountry(final int countryId, final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setC_Country_ID(countryId);
		country.setCountryCode(countryCode);
		save(country);

		final I_C_CountryArea_Assign assign = newInstance(I_C_CountryArea_Assign.class);
		assign.setC_CountryArea_ID(euAreaId);
		assign.setC_Country_ID(countryId);
		assign.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		save(assign);
	}

	private TaxId createTax(final int fromCountryId, final int toCountryId, final TypeOfDestCountry type, final String rate, final String isFiscalRepresentation)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName(fromCountryId + "-" + toCountryId);
		tax.setAD_Org_ID(ORG_ID);
		tax.setC_Country_ID(fromCountryId);
		tax.setTo_Country_ID(toCountryId);
		tax.setTypeOfDestCountry(type.getCode());
		tax.setRequiresTaxCertificate(null);
		tax.setIsSmallbusiness(null);
		tax.setIsFiscalRepresentation(isFiscalRepresentation);
		tax.setValidFrom(TimeUtil.parseTimestamp("2020-01-01"));
		tax.setSOPOType(SOPOType.SALES_TAX.getCode());
		tax.setC_TaxCategory_ID(TAX_CATEGORY_ID);
		tax.setRate(new BigDecimal(rate));
		save(tax);
		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}
}
