package de.metas.adempiere.docline.sort.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_C_DocLine_Sort.class)
public class C_DocLine_Sort
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE
			, ifColumnsChanged = {
					I_C_DocLine_Sort.COLUMNNAME_DocBaseType
					, I_C_DocLine_Sort.COLUMNNAME_IsActive
					, I_C_DocLine_Sort.COLUMNNAME_IsDefault
			})
	public void validateNoConflictingBPConfiguration(final I_C_DocLine_Sort sort)
	{
		if (!sort.isActive())
		{
			return; // if it was deactivated
		}

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(sort);
		final String trxName = InterfaceWrapperHelper.getTrxName(sort);

		final IQuery<I_C_DocLine_Sort> activeDocLineSortSubQuery = queryBL.createQueryBuilder(I_C_DocLine_Sort.class, ctx, trxName)
				.setJoinOr()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, sort.getC_DocLine_Sort_ID())
				.create();

		//
		// Header query builder
		final IQueryBuilder<I_C_DocLine_Sort> docLineQueryBuilder = queryBL.createQueryBuilder(I_C_DocLine_Sort.class, ctx, trxName)
				.addInSubQueryFilter(I_C_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, I_C_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, activeDocLineSortSubQuery);

		//
		// IsDefault
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_IsDefault, sort.isDefault());

		//
		// DocBaseType
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_DocBaseType, sort.getDocBaseType());

		final IQuery<I_C_BP_DocLine_Sort> activeBPSubQuery = queryBL.createQueryBuilder(I_C_BP_DocLine_Sort.class, ctx, trxName)
				.setJoinOr()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, sort.getC_DocLine_Sort_ID())
				.create();

		//
		// Collect BPartner links
		final IQueryBuilder<I_C_BP_DocLine_Sort> bpQueryBuilder = docLineQueryBuilder
				.andCollectChildren(I_C_BP_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, I_C_BP_DocLine_Sort.class)
				.addInSubQueryFilter(I_C_BP_DocLine_Sort.COLUMN_C_BP_DocLine_Sort_ID, I_C_BP_DocLine_Sort.COLUMN_C_BP_DocLine_Sort_ID, activeBPSubQuery);

		final List<Integer> bpIdSeen = new ArrayList<>();

		final List<I_C_BP_DocLine_Sort> bpDocLineSortConfigs = bpQueryBuilder
				.create()
				.list();
		for (final I_C_BP_DocLine_Sort bpDocLineSort : bpDocLineSortConfigs)
		{
			final int bpartnerId = bpDocLineSort.getC_BPartner_ID();
			if (bpIdSeen.contains(bpartnerId))
			{
				throw new AdempiereException("@DuplicateBPDocLineSort@");
			}
			bpIdSeen.add(bpartnerId);
		}
	}
}
