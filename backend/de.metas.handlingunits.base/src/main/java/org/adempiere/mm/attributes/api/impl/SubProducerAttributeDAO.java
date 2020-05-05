package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ISubProducerAttributeDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;

import de.metas.interfaces.I_C_BP_Relation;
import de.metas.util.Services;

public class SubProducerAttributeDAO implements ISubProducerAttributeDAO
{
	@Override
	public List<I_C_BPartner> retrieveSubProducerBPartners(final Properties ctx, final int bpartnerId)
	{
		final IQuery<I_C_BP_Relation> bpRelationQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(org.compiere.model.I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, bpartnerId)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsMainProducer, true)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create();

		final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner.class, ctx, ITrx.TRXNAME_None)
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID,
						org.compiere.model.I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bpRelationQuery)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx);

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner.COLUMNNAME_Name);

		final List<I_C_BPartner> subProducerBPartners = queryBuilder.create()
				.list();
		return subProducerBPartners;
	}

}
