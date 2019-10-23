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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_C_BP_DocLine_Sort.class)
public class C_BP_DocLine_Sort
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE
			, ifColumnsChanged = {
					I_C_BP_DocLine_Sort.COLUMNNAME_C_BPartner_ID
					, I_C_BP_DocLine_Sort.COLUMNNAME_IsActive
			})
	public void validateNoConflictingBPConfiguration(final I_C_BP_DocLine_Sort bpSort)
	{
		if (!bpSort.isActive())
		{
			return; // if it was deactivated
		}

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(bpSort);
		final String trxName = InterfaceWrapperHelper.getTrxName(bpSort);

		//
		// Header query builder
		final IQueryBuilder<I_C_DocLine_Sort> docLineQueryBuilder = queryBL.createQueryBuilder(I_C_DocLine_Sort.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		final I_C_DocLine_Sort sort = bpSort.getC_DocLine_Sort();

		//
		// DocBaseType
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_DocBaseType, sort.getDocBaseType());

		//
		// Collect BPartner links
		final IQueryBuilder<I_C_BP_DocLine_Sort> bpQueryBuilder = docLineQueryBuilder
				.andCollectChildren(I_C_BP_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, I_C_BP_DocLine_Sort.class)
				.addOnlyActiveRecordsFilter();

		//
		// Not same BP configuration
		bpQueryBuilder.addNotEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_BP_DocLine_Sort_ID, bpSort.getC_BP_DocLine_Sort_ID());

		//
		// Same BP
		bpQueryBuilder.addEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_BPartner_ID, bpSort.getC_BPartner_ID());

		final boolean existsDuplicateBPConfig = bpQueryBuilder
				.create()
				.match();
		if (existsDuplicateBPConfig)
		{
			throw new AdempiereException("@DuplicateBPDocLineSort@");
		}
	}
}
