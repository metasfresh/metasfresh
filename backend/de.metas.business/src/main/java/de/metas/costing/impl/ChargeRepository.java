/*
 * #%L
 * de.metas.business
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

package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import de.metas.costing.ChargeId;
import de.metas.costing.ChargeTypeId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Charge;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * Repository Tables: C_Charge
 * Repository Cluster: ChargeRepository
 */
@Repository
public class ChargeRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_Charge getById(@NonNull final ChargeId chargeId)
	{
		final I_C_Charge chargeRecord = loadOutOfTrx(chargeId.getRepoId(), I_C_Charge.class);

		if (chargeRecord == null)
		{
			throw new AdempiereException("@NotFound@ @C_Charge_ID@: " + chargeId);
		}

		return chargeRecord;
	}

	/**
	 * @return the active charges of the given charge type that are visible to the given client and org (i.e. of that org or of org {@code *}), ordered by name
	 */
	@NonNull
	public ImmutableList<I_C_Charge> getActiveByChargeTypeId(@NonNull final ChargeTypeId chargeTypeId, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_Charge.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Charge.COLUMNNAME_C_ChargeType_ID, chargeTypeId)
				.addEqualsFilter(I_C_Charge.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId())
				.addInArrayFilter(I_C_Charge.COLUMNNAME_AD_Org_ID, clientAndOrgId.getOrgId(), OrgId.ANY)
				.orderBy(I_C_Charge.COLUMNNAME_Name)
				.orderBy(I_C_Charge.COLUMNNAME_C_Charge_ID)
				.create()
				.listImmutable(I_C_Charge.class);
	}
}
