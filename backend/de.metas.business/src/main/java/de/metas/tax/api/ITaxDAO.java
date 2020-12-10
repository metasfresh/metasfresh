package de.metas.tax.api;

import java.sql.Timestamp;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Optional;
import java.util.Properties;

import de.metas.util.lang.Percent;
import org.adempiere.exceptions.ExemptTaxNotFoundException;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.tax.model.I_C_VAT_SmallBusiness;
import de.metas.util.ISingletonService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

public interface ITaxDAO extends ISingletonService
{
	int C_TAX_ID_NO_TAX_FOUND = 100;

	I_C_Tax getTaxById(int taxRepoId);

	I_C_Tax getTaxById(TaxId taxRepoId);

	I_C_Tax getTaxByIdOrNull(int taxRepoId);

	/**
	 * @return true if the given bpartner currently has a {@link I_C_VAT_SmallBusiness} record.
	 */
	boolean retrieveIsTaxExemptSmallBusiness(BPartnerId bPartnerId, Timestamp date);

	/**
	 * @throws ExemptTaxNotFoundException if no tax exempt found
	 */
	TaxId retrieveExemptTax(OrgId orgId);

	/**
	 * getDefaultTax Get the default tax id associated with this tax category
	 */
	I_C_Tax getDefaultTax(I_C_TaxCategory taxCategory);

	/**
	 * If the taxBL can't find a tax, it shall return this one instead
	 *
	 * @param ctx
	 * @return placeholder tax that is used when no other tax was found
	 */
	I_C_Tax retrieveNoTaxFound(Properties ctx);

	/**
	 * If the taxBL can't find a tax category, it shall return this one instead
	 *
	 * @param ctx
	 * @return placeholder tax category that is used when no other tax was found (note: not used yet; may be helpful in the future)
	 */
	I_C_TaxCategory retrieveNoTaxCategoryFound(Properties ctx);

	int findTaxCategoryId(TaxCategoryQuery query);

	I_C_TaxCategory getTaxCategoryById(TaxCategoryId id);

	ITranslatableString getTaxCategoryNameById(TaxCategoryId id);

	Optional<TaxCategoryId> getTaxCategoryIdByName(@NonNull String name);

	Percent getRateById(@NonNull TaxId taxId);

	@Builder
	@Value
	public static class TaxCategoryQuery
	{
		@AllArgsConstructor
		@Getter
		public enum VATType
		{
			RegularVAT("N"), ReducedVAT("R"), TaxExempt("E");

			private final String value;
		}

		@NonNull
		final VATType type;

		@NonNull
		final CountryId countryId;
	}
}
