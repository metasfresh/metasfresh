/*
 * #%L
 * de.metas.business
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

package de.metas.incoterms.impl;

import de.metas.incoterms.IIncotermsDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Incoterms;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class IncotermsDAO implements IIncotermsDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@Nullable
	public I_C_Incoterms getDefaultIncoterms(final @NotNull OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_C_Incoterms.class)
				.addEqualsFilter(I_C_Incoterms.COLUMNNAME_IsDefault, true)
				.addEqualsFilter(I_C_Incoterms.COLUMNNAME_AD_Org_ID, orgId.getRepoId())
				.create()
				.firstOnlyOrNull(I_C_Incoterms.class);
	}

	@Override
	public void save(final @NotNull I_C_Incoterms incoterms)
	{
		saveRecord(incoterms);
	}
}
