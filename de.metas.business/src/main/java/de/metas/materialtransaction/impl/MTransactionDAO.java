package de.metas.materialtransaction.impl;

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


import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Transaction;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.materialtransaction.IMTransactionDAO;
import lombok.NonNull;

public class MTransactionDAO implements IMTransactionDAO
{
	@Override
	public List<I_M_Transaction> retrieveReferenced(final Object referencedModel)
	{
		final IQueryFilter<I_M_Transaction> referencedModelFilter = createReferencedModelQueryFilter(referencedModel);
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Transaction.class, referencedModel)
				.filter(referencedModelFilter)
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_Transaction.class);
	}

	private IQueryFilter<I_M_Transaction> createReferencedModelQueryFilter(@NonNull final Object referencedModel)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(referencedModel);
		final int id = InterfaceWrapperHelper.getId(referencedModel);
		if (I_M_InOutLine.Table_Name.equals(tableName))
		{
			return new EqualsQueryFilter<>(I_M_Transaction.COLUMNNAME_M_InOutLine_ID, id);
		}
		else if (I_M_InventoryLine.Table_Name.equals(tableName))
		{
			return new EqualsQueryFilter<>(I_M_Transaction.COLUMNNAME_M_InventoryLine_ID, id);
		}
		else if (I_M_MovementLine.Table_Name.equals(tableName))
		{
			return new EqualsQueryFilter<>(I_M_Transaction.COLUMNNAME_M_MovementLine_ID, id);
		}
		else if (I_C_ProjectIssue.Table_Name.equals(tableName))
		{
			return new EqualsQueryFilter<>(I_M_Transaction.COLUMNNAME_C_ProjectIssue_ID, id);
		}
		else if (I_PP_Cost_Collector.Table_Name.equals(tableName))
		{
			return new EqualsQueryFilter<>(I_M_Transaction.COLUMNNAME_PP_Cost_Collector_ID, id);
		}
		else
		{
			throw new IllegalArgumentException("Referenced model not supported: " + referencedModel);
		}
	}

	@Override
	public I_M_Transaction retrieveReversalTransaction(final Object referencedModelReversal, final I_M_Transaction originalTrx)
	{
		Check.assumeNotNull(referencedModelReversal, "referencedModelReversal not null");
		Check.assumeNotNull(originalTrx, "originalTrx not null");

		final IQueryFilter<I_M_Transaction> referencedModelFilter = createReferencedModelQueryFilter(referencedModelReversal);

		final ICompositeQueryFilter<I_M_Transaction> filters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_M_Transaction.class);
		filters.addFilter(referencedModelFilter)
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_Product_ID, originalTrx.getM_Product_ID())
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_M_AttributeSetInstance_ID, originalTrx.getM_AttributeSetInstance_ID())
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_MovementType, originalTrx.getMovementType())
				.addEqualsFilter(I_M_Transaction.COLUMNNAME_MovementQty, originalTrx.getMovementQty().negate())
		//
		;

		final I_M_Transaction reversalTrx = Services.get(IQueryBL.class).createQueryBuilder(I_M_Transaction.class, referencedModelReversal)
				.filter(filters)
				.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_M_Transaction.class);

		if (reversalTrx == null)
		{
			throw new AdempiereException("@NotFound@ @Reversal_ID@ @M_Transaction_ID@ ("
					+ "@ReversalLine_ID@: " + referencedModelFilter
					+ ", @M_Transaction_ID@: " + originalTrx
					+ ")");
		}

		return reversalTrx;
	}

}
