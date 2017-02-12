package de.metas.adempiere.service.impl;

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


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.exceptions.PeriodClosedException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_PeriodControl;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPeriodBL;
import de.metas.logging.LogManager;

public class PeriodBL implements IPeriodBL
{

	private static final Logger s_log = LogManager.getLogger(PeriodBL.class);

	@Override
	public boolean isOpen(Properties ctx, Timestamp DateAcct, String DocBaseType, int AD_Org_ID)
	{
		if (DateAcct == null)
		{
			s_log.warn("No DateAcct");
			return false;
		}
		if (DocBaseType == null)
		{
			s_log.warn("No DocBaseType");
			return false;
		}
		MPeriod period = MPeriod.get(ctx, DateAcct, AD_Org_ID);
		if (period == null)
		{
			s_log.warn("No Period for " + DateAcct + " (" + DocBaseType + ")");
			return false;
		}
		boolean open = period.isOpen(DocBaseType, DateAcct, AD_Org_ID);
		if (!open)
		{
			s_log.warn(period.getName() + ": Not open for " + DocBaseType + " (" + DateAcct + ")");
		}
		return open;
	}

	@Override
	public void testPeriodOpen(Properties ctx, Timestamp dateAcct, String docBaseType, int AD_Org_ID) throws PeriodClosedException
	{
		if (!isOpen(ctx, dateAcct, docBaseType, AD_Org_ID))
		{
			throw new PeriodClosedException(dateAcct, docBaseType);
		}
	}

	@Override
	public String getPeriodStatusForAction(final String periodAction)
	{
		if (periodAction == null)
		{
			throw new FillMandatoryException("PeriodAction");
		}
		// Open
		else if (X_C_PeriodControl.PERIODACTION_OpenPeriod.equals(periodAction))
		{
			return X_C_PeriodControl.PERIODSTATUS_Open;
		}
		else if (X_C_PeriodControl.PERIODACTION_ClosePeriod.equals(periodAction))
		{
			return X_C_PeriodControl.PERIODSTATUS_Closed;
		}
		else if (X_C_PeriodControl.PERIODACTION_PermanentlyClosePeriod.equals(periodAction))
		{
			return X_C_PeriodControl.PERIODSTATUS_PermanentlyClosed;
		}
		else if (X_C_PeriodControl.PERIODACTION_NoAction.equals(periodAction))
		{
			throw new AdempiereException("@NoAction@");
		}
		else
		{
			throw new AdempiereException("Unknown PeriodAction: " + periodAction);
		}
	}

	@Override
	public void createPeriodControls(final I_C_Period period)
	{
		Check.assumeNotNull(period, "period not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(period);
		final String trxName = InterfaceWrapperHelper.getTrxName(period);

		final Set<String> docBaseTypesConsidered = new HashSet<>();
		for (MDocType docType : MDocType.getOfClient(ctx))
		{
			final String docBaseType = docType.getDocBaseType();
			if (!docBaseTypesConsidered.add(docBaseType))
			{
				continue; // already considered
			}

			final I_C_PeriodControl pc = InterfaceWrapperHelper.create(ctx, I_C_PeriodControl.class, trxName);
			pc.setAD_Org_ID(0);
			pc.setC_Period(period);
			pc.setDocBaseType(docBaseType);
			pc.setPeriodAction(X_C_PeriodControl.PERIODACTION_NoAction);
			pc.setPeriodStatus(X_C_PeriodControl.PERIODSTATUS_NeverOpened);

			InterfaceWrapperHelper.save(pc);
		}
	}

	@Override
	public boolean isInPeriod(final I_C_Period period, final Date date)
	{
		if (date == null)
		{
			return false;
		}

		final Timestamp dateOnly = TimeUtil.getDay(date);
		final Timestamp from = TimeUtil.getDay(period.getStartDate());
		if (dateOnly.before(from))
		{
			return false;
		}

		final Timestamp to = TimeUtil.getDay(period.getEndDate());
		if (dateOnly.after(to))
		{
			return false;
		}

		return true;
	}

}
