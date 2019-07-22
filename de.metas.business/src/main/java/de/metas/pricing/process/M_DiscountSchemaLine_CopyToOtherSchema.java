package de.metas.pricing.process;

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.Adempiere;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaLine;

import com.google.common.collect.ImmutableList;

import de.metas.pricing.DiscountSchemaId;
import de.metas.pricing.DiscountSchemaLineId;
import de.metas.pricing.DiscountSchemaRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

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

public class M_DiscountSchemaLine_CopyToOtherSchema extends JavaProcess implements IProcessPrecondition
{
	private final DiscountSchemaRepository discountSchemaRepo = Adempiere.getBean(DiscountSchemaRepository.class);

	@Param(parameterName = I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID)
	private DiscountSchemaId p_DiscountSchemaId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize() <= 0)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<DiscountSchemaLineId> linesToCopy = retrieveLinesToCopy();

		linesToCopy.stream()
		.forEach(lineToCopy -> discountSchemaRepo.createLineCopy(lineToCopy, p_DiscountSchemaId));

		return MSG_OK;
	}

	private List<DiscountSchemaLineId> retrieveLinesToCopy()
	{
		final IQueryFilter<I_M_DiscountSchemaLine> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ImmutableList<DiscountSchemaLineId> selectedDiscountSchemaLineIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaLine.class)
				.filter(queryFilter)
				.create()
				.listIds(DiscountSchemaLineId::ofRepoId)
				.stream()
				.collect(ImmutableList.toImmutableList());

		return selectedDiscountSchemaLineIds;

	}

}
