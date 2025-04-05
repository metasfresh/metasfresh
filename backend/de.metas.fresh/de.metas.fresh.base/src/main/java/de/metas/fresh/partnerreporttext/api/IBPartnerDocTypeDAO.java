/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.fresh.partnerreporttext.api;

import de.metas.fresh.partnerreporttext.model.I_C_BPartner_DocType;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_Report_Text;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IBPartnerDocTypeDAO extends ISingletonService
{
	void setBPartner(I_C_BPartner_DocType bPartnerDocType, int bpartnerId);

	void save(@NonNull I_C_BPartner_DocType bPartnerDocType);

	boolean isPartnerDocTypeUnique(I_C_BPartner_Report_Text reportTextRecord, int docTypeID);
}
