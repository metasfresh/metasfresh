package de.metas.tax.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

public interface ITaxDAO extends ISingletonService
{
	boolean retrieveIsTaxExempt(Properties ctx, int bPartnerId, Timestamp date, String trxName);

	boolean retrieveIsTaxExempt(I_C_BPartner bPartner, Timestamp date);

	/**
	 * getDefaultTax Get the default tax id associated with this tax category
	 *
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

	int findTaxCategoryId(@NonNull final TaxCategoryQuery query);

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
	}
}
