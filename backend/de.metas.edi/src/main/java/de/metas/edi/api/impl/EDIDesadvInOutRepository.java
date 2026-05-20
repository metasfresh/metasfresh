/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.IEDIDesadvInOutRepository;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

@Repository
public class EDIDesadvInOutRepository implements IEDIDesadvInOutRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void assignDesadvToInOut(@NonNull final EDIDesadvId desadvId, @NonNull final InOutId inOutId)
	{
		final int existingId = queryBL.createQueryBuilder(I_EDI_Desadv_M_InOut.class)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_EDI_Desadv_ID, desadvId)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
		if (existingId > 0)
		{
			return;
		}

		final I_EDI_Desadv_M_InOut row = InterfaceWrapperHelper.newInstance(I_EDI_Desadv_M_InOut.class);
		row.setEDI_Desadv_ID(desadvId.getRepoId());
		row.setM_InOut_ID(inOutId.getRepoId());
		InterfaceWrapperHelper.saveRecord(row);
	}
}
