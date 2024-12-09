package de.metas.acct.vatcode;

<<<<<<< HEAD
import de.metas.util.ISingletonService;
=======
import de.metas.organization.OrgId;
import de.metas.tax.api.VatCodeId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link VATCode} DAO
<<<<<<< HEAD
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
=======
 *
 * @author metas-dev <dev@metasfresh.com>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public interface IVATCodeDAO extends ISingletonService
{
	/**
<<<<<<< HEAD
	 * Find matching {@link VATCode} for given context.
	 * 
	 * @return vat code or {@link VATCode#NULL}; never returns null
	 */
	VATCode findVATCode(VATCodeMatchingRequest request);

=======
	 * Find matching {@link VATCode} for given context, .
	 *
	 * @return vat code or {@link Optional#empty()}; never returns null
	 */
	Optional<VATCode> findVATCode(VATCodeMatchingRequest request);

	@NonNull
	VatCodeId getIdByCodeAndOrgId(@NonNull String code, @NonNull OrgId orgId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
