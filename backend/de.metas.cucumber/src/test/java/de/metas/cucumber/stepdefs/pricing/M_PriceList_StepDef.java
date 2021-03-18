/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_PriceList_StepDef
{
	private final OrgId defaultOrgId = StepDefConstants.ORG_ID;
	private final CurrencyRepository currencyRepository;

	public M_PriceList_StepDef(final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	@And("metasfresh contains M_PricingSystem")
	public void add_M_PricingSystem(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableRow = dataTable.asMaps().get(0);
		createM_PricingSystem(dataTableRow);
	}

	@And("metasfresh contains M_PriceList")
	public void add_M_PriceList(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_PriceList(dataTableRow);
		}
	}

	private void createM_PricingSystem(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, "Identifier");
		final String name = DataTableUtil.extractStringForColumnName(row, "Name");
		final String value = DataTableUtil.extractStringForColumnName(row, "Value");
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Description");
		final String isActiveValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.IsActive");

		final boolean isActive = !isActiveValue.equals("false");

		final I_M_PricingSystem m_pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);

		m_pricingSystem.setM_PricingSystem_ID(Integer.parseInt(identifier));
		m_pricingSystem.setAD_Org_ID(defaultOrgId.getRepoId());
		m_pricingSystem.setName(name);
		m_pricingSystem.setValue(value);
		m_pricingSystem.setIsActive(isActive);

		if (!description.equals("null"))
		{
			m_pricingSystem.setDescription(description);
		}

		saveRecord(m_pricingSystem);
	}

	private void createM_PriceList(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, "Identifier");
		final String pricingSystemIdentifier = DataTableUtil.extractStringForColumnName(row, "M_PricingSystem_ID.Identifier");
		final String countryCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.C_Country.CountryCode");
		final String isoCode = DataTableUtil.extractStringForColumnName(row, "C_Currency.ISO_Code");
		final String name = DataTableUtil.extractStringForColumnName(row, "Name");
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.Description");
		final String soTrxValue = DataTableUtil.extractStringForColumnName(row, "SOTrx");
		final String isTaxIncludedValue = DataTableUtil.extractStringForColumnName(row, "IsTaxIncluded");
		final String pricePrecision = DataTableUtil.extractStringForColumnName(row, "PricePrecision");
		final String isActiveValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.IsActive");

		final CurrencyId currencyId = getCurrencyIdByCurrencyISO(isoCode);
		final boolean isTaxIncluded = isTaxIncludedValue.equals("true");
		final boolean isActive = isActiveValue.equals("true");
		final boolean soTrx = soTrxValue.equals("true");

		final I_M_PriceList m_priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);

		m_priceList.setM_PriceList_ID(Integer.parseInt(identifier));
		m_priceList.setAD_Org_ID(defaultOrgId.getRepoId());
		m_priceList.setM_PricingSystem_ID(Integer.parseInt(pricingSystemIdentifier));
		m_priceList.setC_Currency_ID(currencyId.getRepoId());
		m_priceList.setName(name);
		m_priceList.setIsTaxIncluded(isTaxIncluded);
		m_priceList.setPricePrecision(Integer.parseInt(pricePrecision));
		m_priceList.setIsActive(isActive);
		m_priceList.setIsSOPriceList(soTrx);

		if (!countryCode.equals("null"))
		{
			final I_C_Country countryPO = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(countryCode);
			m_priceList.setC_Country_ID(countryPO.getC_Country_ID());
		}

		if (!description.equals("null"))
		{
			m_priceList.setDescription(description);
		}

		saveRecord(m_priceList);
	}

	@NonNull
	private CurrencyId getCurrencyIdByCurrencyISO(@NonNull final String currencyISO)
	{
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(currencyISO);
		return currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCode);
	}
}
