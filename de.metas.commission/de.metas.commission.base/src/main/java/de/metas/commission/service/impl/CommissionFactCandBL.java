package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.PO;

import com.google.common.base.Optional;

import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionRelevantPO;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionFactCandDAO;

public class CommissionFactCandBL implements ICommissionFactCandBL
{
	private static final String DYN_ATTR_PO_TO_PROCESS = I_C_AdvCommissionFactCand.class.getName() + ".poToProcess";

	private static final String DYN_ATTR_CAUSE = I_C_AdvCommissionFactCand.class.getName() + ".cause";

	public PO getPoToProcess(final I_C_AdvCommissionFactCand cand)
	{
		return InterfaceWrapperHelper.getDynAttribute(cand, CommissionFactCandBL.DYN_ATTR_PO_TO_PROCESS);
	}

	public void setPoToProcess(final I_C_AdvCommissionFactCand cand, final PO poToProcess)
	{
		InterfaceWrapperHelper.setDynAttribute(cand, CommissionFactCandBL.DYN_ATTR_PO_TO_PROCESS, poToProcess);
	}

	/**
	 * May contain the original candidate.
	 */
	@Override
	public I_C_AdvCommissionFactCand getCause(final I_C_AdvCommissionFactCand cand)
	{
		final I_C_AdvCommissionFactCand cause = InterfaceWrapperHelper.getDynAttribute(cand, CommissionFactCandBL.DYN_ATTR_CAUSE);

		if (cause == null)
		{
			return cand;
		}
		return cause;
	}

	@Override
	public void setCause(final I_C_AdvCommissionFactCand cand, final MCAdvCommissionFactCand cause)
	{
		final int causeId = getCause(cause).getC_AdvCommissionFactCand_ID();
		if (causeId != 0)
		{
			cand.setC_AdvComFactCand_Cause_ID(causeId);
		}
	}

	@Override
	@Cached
	public Timestamp retrieveDateDocOfReferencedPO(final I_C_AdvCommissionFactCand cand)
	{
		return retrieveDateDocOfPO(cand.getC_AdvCommissionRelevantPO(), retrievePO(cand));
	}

	private final Timestamp retrieveDateDocOfPO(final I_C_AdvCommissionRelevantPO comRelevantPO, final Object po)
	{
		final I_AD_Column dateDocCol = comRelevantPO.getDateDocColumn();
		final Optional<Timestamp> dateDoc = InterfaceWrapperHelper.getValue(po, dateDocCol.getColumnName());
		return dateDoc.orNull();
	}

	@Override
	public Timestamp retrieveDateDocOfPO(final Object po)
	{
		final MCAdvCommissionRelevantPO comRelevantPO = MCAdvCommissionRelevantPO.retrieveIfRelevant(po);
		Check.assume(comRelevantPO != null, po + " has a C_AdvCommissionRelevantPO record");

		return retrieveDateDocOfPO(comRelevantPO, po);
	}

	@Override
	public PO retrievePO(final I_C_AdvCommissionFactCand cand)
	{
		if (getPoToProcess(cand) != null)
		{
			return getPoToProcess(cand);
		}
		return Services.get(ICommissionFactCandDAO.class).retrievePOFromDB(cand);
	}

}
