/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.requisition;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.springframework.stereotype.Repository;

import de.metas.util.Services;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Repository
public class RequisitionRepository
{
	public List<I_M_RequisitionLine> getLinesByRequisitionId(final RequisitionId requisitionId)
	{
		return getLinesByRequisitionId(requisitionId.getRepoId());
	}

	public List<I_M_RequisitionLine> getLinesByRequisitionId(final int requisitionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_RequisitionLine.class)
				.addEqualsFilter(I_M_RequisitionLine.COLUMNNAME_M_Requisition_ID, requisitionId)
				.orderBy(I_M_RequisitionLine.COLUMNNAME_Line)
				.create()
				.list();
	}

	public void deleteLinesByRequisitionId(final int requisitionId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_RequisitionLine.class)
				.addEqualsFilter(I_M_RequisitionLine.COLUMNNAME_M_Requisition_ID, requisitionId)
				.orderBy(I_M_RequisitionLine.COLUMNNAME_Line)
				.create()
				.delete();
	}

	public I_M_Requisition getById(final RequisitionId requisitionId){
		return load(requisitionId, I_M_Requisition.class);
	}

}
