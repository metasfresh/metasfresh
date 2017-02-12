package de.metas.bpartner.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.interfaces.I_C_BP_Relation;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPRelationDAO implements IBPRelationDAO
{

	@Override
	public I_C_BP_Relation retrieveHandoverBPRelation(final I_C_BPartner partner, final I_C_BPartner relationPartner)
	{
		final IQueryBuilder<I_C_BP_Relation> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class, partner);
		
		// only active
		queryBuilder.addOnlyActiveRecordsFilter();

		// partner condition
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID());

		// relation partner condition
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, relationPartner.getC_BPartner_ID());

		// must ne of type handover
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsHandOverLocation, true);

		// retrieve the last created one in case of several handover relations for the same partner
		queryBuilder.orderBy()
				.addColumn(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID, Direction.Descending, Nulls.Last);

		return queryBuilder.create()
				.first();
	}

}
