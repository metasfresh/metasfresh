package de.metas.costrevaluation.process;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_M_Product;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;

import java.util.Iterator;

/**
 * Process to create M_CostRevaluationLine records for products stocked based on accounting schmea and cost element of M_CostRevaluation
 */
public class M_CostRevaluation_CreateLines_Process extends JavaProcess implements IProcessPrecondition
{
	private final  IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CostRevaluationRepository costRevaluationRepo = SpringContextHolder.instance.getBean(CostRevaluationRepository.class);


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		else if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		else if (!isDraftDocument(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already in progress/finished.");
		}
		return ProcessPreconditionsResolution.accept();
	}

	private boolean isDraftDocument(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(context.getSingleSelectedRecordId());
		return costRevaluationRepo.isDraftedDocument(costRevaluationId);
	}

	@Override
	protected String doIt() throws Exception
	{

		final Iterator<I_M_Product> iterator = createProductIterator();

		if (!iterator.hasNext())
		{
			addLog("@NoSelection@");
		}

		int counterProcessed = 0;

		while (iterator.hasNext())
		{
			final I_C_Invoice invoice = iterator.next();
			final boolean processed = invoiceDiscount(invoice);
			if (processed)
			{
				counterProcessed++;
			}
		}

		return msgBL.getMsg(getCtx(), "msg", new Object[] { counterProcessed });
	}


	private Iterator<I_M_Product> createProductIterator()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = queryBL
				.createQueryBuilder(I_M_Product.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product.COLUMNNAME_IsStocked, true);

		return queryBuilder
				.orderBy().addColumn(I_M_Product.COLUMNNAME_Value)
				.endOrderBy()
				.create()
				.iterate(I_M_Product.class);
	}
}
