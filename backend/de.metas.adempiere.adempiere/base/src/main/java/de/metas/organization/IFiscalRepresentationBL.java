/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.organization;

import de.metas.location.CountryId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_Fiscal_Representation;

import java.sql.Timestamp;
import java.util.Optional;

public interface IFiscalRepresentationBL extends ISingletonService
{
    /**
     * @return true if the given org has a fiscal representation in the given country at the given time
     */
	boolean hasFiscalRepresentation(@NonNull CountryId countryId, @NonNull OrgId orgId, @NonNull Timestamp date);

	/**
	 * @return the active registration of the given org in the given destination country at the given time;
	 * if several overlap, the one with the latest {@code ValidFrom}, then the highest ID.
	 */
	Optional<I_C_Fiscal_Representation> getFiscalRepresentation(@NonNull CountryId countryId, @NonNull OrgId orgId, @NonNull Timestamp date);
}
