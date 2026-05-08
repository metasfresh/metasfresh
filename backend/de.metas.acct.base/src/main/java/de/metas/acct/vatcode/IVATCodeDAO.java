package de.metas.acct.vatcode;

import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.Optional;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@link VATCode} DAO
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IVATCodeDAO extends ISingletonService
{
	/**
	 * Find matching {@link VATCode} for given context, .
	 *
	 * @return vat code or {@link Optional#empty()}; never returns null
	 */
	Optional<VATCode> findVATCode(VATCodeMatchingRequest request);

	@NonNull
	VatCodeId getIdByCodeAndOrgId(@NonNull String code, @NonNull OrgId orgId);

	/**
	 * @return true if there is any active {@link VATCode} record for the given accounting schema and tax.
	 */
	boolean existsForAcctSchemaAndTax(@NonNull AcctSchemaId acctSchemaId, @NonNull TaxId taxId);

	/**
	 * Create a new {@link VATCode} record.
	 */
	@NonNull
	VATCode createVATCode(@NonNull CreateVATCodeRequest request);

	/**
	 * Returns the IsSOTrx flag of the C_VAT_Code record that has the given VATCode string.
	 * Used to derive the correct IsSOTrx for Net VAT code lookup when the tax leg's IsSOTrx
	 * differs from the document's (e.g. reverse-charge T_Due_Acct within a purchase allocation).
	 *
	 * @return Optional.empty() if vatCode is null/empty or no record found
	 */
	Optional<Boolean> findIsSOTrxByCode(@javax.annotation.Nullable String vatCode);
}
