package de.metas.procurement.base.impl;

import java.util.Date;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;

import de.metas.procurement.base.IPMMWeekDAO;
import de.metas.procurement.base.balance.PMMBalanceSegment;
import de.metas.procurement.base.model.I_PMM_Week;

/*
 * #%L
 * de.metas.procurement.base
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

public class PMMWeekDAO implements IPMMWeekDAO
{
	@Override
	public I_PMM_Week retrieveFor(final PMMBalanceSegment segment, final Date weekDate)
	{
		final PlainContextAware context = PlainContextAware.newWithThreadInheritedTrx();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PMM_Week.class, context)
				.addOnlyContextClient()
				.addEqualsFilter(I_PMM_Week.COLUMN_C_BPartner_ID, segment.getC_BPartner_ID())
				.addEqualsFilter(I_PMM_Week.COLUMN_M_Product_ID, segment.getM_Product_ID())
				.addEqualsFilter(I_PMM_Week.COLUMN_M_AttributeSetInstance_ID, segment.getM_AttributeSetInstance_ID() > 0 ? segment.getM_AttributeSetInstance_ID() : null)
				//.addEqualsFilter(I_PMM_Week.COLUMN_M_HU_PI_Item_Product_ID, segment.getM_HU_PI_Item_Product_ID() > 0 ? segment.getM_HU_PI_Item_Product_ID() : null)
				.addEqualsFilter(I_PMM_Week.COLUMN_WeekDate, weekDate)
				.create()
				.firstOnly(I_PMM_Week.class);
	}

}
