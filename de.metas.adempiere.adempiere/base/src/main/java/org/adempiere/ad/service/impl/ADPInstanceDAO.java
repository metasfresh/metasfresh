package org.adempiere.ad.service.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class ADPInstanceDAO implements IADPInstanceDAO
{
	@Override
	public List<I_AD_PInstance_Para> retrievePInstanceParams(final Properties ctx, final int adPInstanceId)
	{
		if (adPInstanceId <= 0)
		{
			return Collections.emptyList();
		}

		final IQueryBuilder<I_AD_PInstance_Para> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_PInstance_Para.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PInstance_Para.COLUMNNAME_AD_PInstance_ID, adPInstanceId);

		queryBuilder.orderBy()
		.addColumn(I_AD_PInstance_Para.COLUMNNAME_SeqNo);

		return queryBuilder
				.create()
				.list(I_AD_PInstance_Para.class);
	}

	@Override
	public List<ProcessInfoParameter> retrieveProcessInfoParameters(final Properties ctx, final int adPInstanceId)
	{
		return retrievePInstanceParams(ctx, adPInstanceId)
				.stream()
				.map(adPInstancePara -> createProcessInfoParameter(adPInstancePara))
				.collect(GuavaCollectors.toImmutableList());
	}

	@Override
	public void loadFromDB(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "pi not null");

		//
		// Retrieve and create ProcessInfoParameters
		final Properties ctx = pi.getCtx();
		final int adPInstanceId = pi.getAD_PInstance_ID();

		//
		// Set ProcessInfo's AD_Client_ID and AD_User_ID (if not already set)
		final Integer adClientId = pi.getAD_Client_ID();
		final Integer adUserId = pi.getAD_User_ID();
		if (adPInstanceId > 0 && (adClientId == null || adUserId == null))
		{
			final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
			if (adClientId == null)
			{
				pi.setAD_Client_ID(adPInstance.getAD_Client_ID());
			}
			if (adUserId == null)
			{
				pi.setAD_User_ID(adPInstance.getAD_User_ID());
			}
		}

		//
		// Set ProcessInfo's Parameters
		final List<ProcessInfoParameter> processInfoParams = retrieveProcessInfoParameters(ctx, adPInstanceId);
		final ProcessInfoParameter[] paramsArr = processInfoParams.toArray(new ProcessInfoParameter[processInfoParams.size()]);
		pi.setParameter(paramsArr);
	}

	// package level for testing
	/* package */final ProcessInfoParameter createProcessInfoParameter(final I_AD_PInstance_Para adPInstancePara)
	{
		final String ParameterName = adPInstancePara.getParameterName();
		// Info
		final String Info = adPInstancePara.getInfo();
		final String Info_To = adPInstancePara.getInfo_To();

		//
		// String
		Object Parameter = adPInstancePara.getP_String();
		Object Parameter_To = adPInstancePara.getP_String_To();

		//
		// Timestamp
		if (Parameter == null && Parameter_To == null)
		{
			Parameter = adPInstancePara.getP_Date();
			Parameter_To = adPInstancePara.getP_Date_To();
		}

		//
		// Big Decimal
		// NOTE: keep this one last because getP_Number() is always returning not null
		if (Parameter == null && Parameter_To == null
				&& (!InterfaceWrapperHelper.isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number)
						|| !InterfaceWrapperHelper.isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number_To)))
		{
			Parameter = adPInstancePara.getP_Number();
			if (InterfaceWrapperHelper.isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number_To))
			{
				Parameter_To = null;
			}
			else
			{
				Parameter_To = adPInstancePara.getP_Number_To();
			}
		}
		//
		final ProcessInfoParameter param = new ProcessInfoParameter(ParameterName, Parameter, Parameter_To, Info, Info_To);
		return param;
	}

	@Override
	public void saveParameterToDB(final ProcessInfo pi)
	{
		saveParameterToDB(pi.getAD_PInstance_ID(), pi.getParameter());
	}
	
	@Override
	public void saveParameterToDB(final int adPInstanceId, final ProcessInfoParameter[] piParams)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints() // 02625
			.setOnlyAllowedTrxNamePrefixes(true)
			.addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

			saveParameterToDB0(adPInstanceId, piParams);
		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	/**
	 *
	 * Called by {@link #saveParameterToDB(ProcessInfo)} to do the actual work.
	 */
	private static void saveParameterToDB0(final int adPInstanceId, final ProcessInfoParameter[] piParams)
	{
		// exit if this ProcessInfo has no Parameters
		if (piParams == null || piParams.length == 0)
		{
			return;
		}

		// get Parameters from the Database
		final MPInstance adPInstance = new MPInstance(Env.getCtx(), adPInstanceId, ITrx.TRXNAME_None);
		final List<I_AD_PInstance_Para> instParams = adPInstance.getParameters();

		// Put these Parameters in a map, so we can get them by name later.
		// Also get the highest SeqNo for this PInstance.
		final Map<String, I_AD_PInstance_Para> map;
		int lastSeqNo = 0;
		if (instParams != null && !instParams.isEmpty())
		{
			map = new HashMap<>(instParams.size());

			for (final I_AD_PInstance_Para instPara : instParams)
			{
				map.put(instPara.getParameterName(), instPara);
				if (instPara.getSeqNo() > lastSeqNo)
				{
					lastSeqNo = instPara.getSeqNo();
				}
			}
		}
		else
		{
			map = Collections.emptyMap();
			lastSeqNo = 0;
		}

		// Iterate through the ProcessInfo's Parameters and decide if they have to be created or can be overwritten.
		for (final ProcessInfoParameter piParam : piParams)
		{
			I_AD_PInstance_Para instParam = map.get(piParam.getParameterName());

			if (instParam == null) // if this Parameter is not yet existing in the DB
			{
				final int seqNo = lastSeqNo + 10;
				instParam = createProcessParameter(piParam, adPInstance, seqNo);
				lastSeqNo = instParam.getSeqNo();
			}
			else
			{
				overwriteProcessParameter(piParam, instParam);
			}
		}
	} // saveParameterToDB

	private static I_AD_PInstance_Para createProcessParameter(final ProcessInfoParameter piPara, final I_AD_PInstance adPInstance, final int seqNo)
	{
		final Object value = piPara.getParameter();
		final Object valueTo = piPara.getParameter_To();
		// If ValueTo is null, it won't be set in the Database. Otherwise this Parameter would be split
		// and renamed to ParameterName + "1" and ParameterName + "2" since Jasper doesn't support range
		// parameters.

		final I_AD_PInstance_Para instPara = InterfaceWrapperHelper.newInstance(I_AD_PInstance_Para.class, adPInstance);
		instPara.setAD_PInstance_ID(adPInstance.getAD_PInstance_ID());
		instPara.setSeqNo(seqNo);

		instPara.setInfo(piPara.getInfo());
		instPara.setInfo_To(piPara.getInfo_To());
		instPara.setParameterName(piPara.getParameterName());

		if (value instanceof Timestamp)
		{
			instPara.setP_Date((Timestamp)value);
			if (valueTo != null)
			{
				instPara.setP_Date_To((Timestamp)valueTo);
			}
		}
		else if (value instanceof BigDecimal)
		{
			instPara.setP_Number((BigDecimal)value);
			if (valueTo != null)
			{
				instPara.setP_Number_To((BigDecimal)valueTo);
			}
		}
		else if (value instanceof Integer)
		{
			instPara.setP_Number(BigDecimal.valueOf((Integer)value));
			if (valueTo != null)
			{
				instPara.setP_Number_To(BigDecimal.valueOf((Integer)valueTo));
			}
		}
		else if (value instanceof String)
		{
			instPara.setP_String((String)value);
			if (valueTo != null)
			{
				instPara.setP_String_To((String)valueTo);
			}
		}

		InterfaceWrapperHelper.save(instPara);
		return instPara;
	}

	/**
	 * Overwrites an AD_PInstance Parameter (represented by the Object instParam) with a ProcessInfoParameter (represented by the Object piParam). The new values will be saved to the Database only if
	 * there's a difference.
	 *
	 * @param piParam
	 * @param instParam
	 * @task US1007
	 */
	private static void overwriteProcessParameter(final ProcessInfoParameter piParam, final I_AD_PInstance_Para instParam)
	{
		final Object value = piParam.getParameter();
		final Object valueTo = piParam.getParameter_To();

		// Check which type of Parameter we have, and if the instPara value differs from the piPara value
		// apply the piPara Value to the instPara value and save. If not, do nothing.

		if (value instanceof Timestamp &&
				(!value.equals(instParam.getP_Date()) ||
						!valueTo.equals(instParam.getP_Date_To())))
		{
			instParam.setP_Date((Timestamp)value);
			if (valueTo != null)
			{
				instParam.setP_Date_To((Timestamp)valueTo);
			}
			instParam.setInfo(piParam.getInfo());
			instParam.setInfo_To(piParam.getInfo_To());
			InterfaceWrapperHelper.save(instParam);
		}
		else if (value instanceof BigDecimal &&
				(!value.equals(instParam.getP_Number()) ||
						!valueTo.equals(instParam.getP_Number_To())))
		{
			instParam.setP_Number((BigDecimal)value);
			if (valueTo != null)
			{
				instParam.setP_Number_To((BigDecimal)valueTo);
			}
			instParam.setInfo(piParam.getInfo());
			instParam.setInfo_To(piParam.getInfo_To());
			InterfaceWrapperHelper.save(instParam);
		}
		else if (value instanceof Integer &&
				(!value.equals(instParam.getP_Number()) ||
						!valueTo.equals(instParam.getP_Number_To())))
		{
			final BigDecimal valueBD = value == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)value);
			instParam.setP_Number(valueBD);
			if (valueTo != null)
			{
				final BigDecimal valueToBD = valueTo == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)valueTo);
				instParam.setP_Number_To(valueToBD);
			}
			instParam.setInfo(piParam.getInfo());
			instParam.setInfo_To(piParam.getInfo_To());
			InterfaceWrapperHelper.save(instParam);
		}
		else if (value instanceof String &&
				(!value.equals(instParam.getP_String()) ||
						!valueTo.equals(instParam.getP_String_To())))
		{
			instParam.setP_String((String)value);
			if (valueTo != null)
			{
				instParam.setP_String_To((String)valueTo);
			}
			instParam.setInfo(piParam.getInfo());
			instParam.setInfo_To(piParam.getInfo_To());
			InterfaceWrapperHelper.save(instParam);
		}
	}
}
