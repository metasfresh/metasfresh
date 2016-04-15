package de.metas.commission.custom.type;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;

import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComDoc;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_AdvComDoc;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.IFieldAccessBL;

/**
 * Type processes C_AdvComDoc records.
 * 
 * @author ts
 * 
 */
public class CommissionDoc implements ICommissionType
{
	private I_C_AdvComSystem_Type comSystemType;

	@Override
	public void evaluateCandidate(
			final MCAdvCommissionFactCand candidate,
			final String status,
			final int adPinstanceId)
	{
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(candidate);
		Check.assume(InterfaceWrapperHelper.isInstanceOf(po, I_C_AdvComDoc.class), "Expecting po to be instanceof MCAdvComDoc; po={}", po);

		final MCAdvComDoc comDoc = (MCAdvComDoc)po;
		if (!X_C_AdvComDoc.DOCSTATUS_Fertiggestellt.equals(comDoc.getDocStatus()))
		{
			return;
		}
		final PO referencedPO = comDoc.retrievePO();

		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);
		final ICommissionFactBL comFactBL = Services.get(ICommissionFactBL.class);

		for (final Object poLine : faBL.retrieveLines(referencedPO, true))
		{
			final List<IAdvComInstance> instancesForPoLine = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(poLine, null);
			for (final IAdvComInstance inst : instancesForPoLine)
			{
				if (inst.getC_AdvComSystem_Type().getC_AdvComSystem_ID() != getComSystemType().getC_AdvComSystem_ID())
				{
					continue;
				}
				comFactBL.recordComDoc(candidate, comDoc, poLine, inst, adPinstanceId);
			}
		}
		comDoc.setIsProcessedByComSystem(true);
		comDoc.saveEx();
	}

	@Override
	public I_C_AdvComSystem_Type getComSystemType()
	{
		return comSystemType;
	}

	@Override
	public BigDecimal getCommissionPointsSum(final IAdvComInstance inst, final String status, final Timestamp date, final Object po)
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getFactor()
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	@Override
	public IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		// not applicable
		return BigDecimal.ZERO;
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return false;
	}

	@Override
	public void setComSystemType(final I_C_AdvComSystem_Type comSystemType)
	{
		this.comSystemType = comSystemType;
	}

}
