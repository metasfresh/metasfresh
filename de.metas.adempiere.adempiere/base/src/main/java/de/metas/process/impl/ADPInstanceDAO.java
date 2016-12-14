package de.metas.process.impl;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoLog;
import de.metas.process.ProcessInfoParameter;

public class ADPInstanceDAO implements IADPInstanceDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADPInstanceDAO.class);

	/** Result OK = 1 */
	public static final int RESULT_OK = 1;
	/** Result FALSE = 0 */
	public static final int RESULT_ERROR = 0;

	private List<I_AD_PInstance_Para> retrieveAD_PInstance_Params(final Properties ctx, final int adPInstanceId)
	{
		if (adPInstanceId <= 0)
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_PInstance_Para.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PInstance_Para.COLUMNNAME_AD_PInstance_ID, adPInstanceId)
				//
				.orderBy()
				.addColumn(I_AD_PInstance_Para.COLUMNNAME_SeqNo)
				.endOrderBy()
				//
				.create()
				.list(I_AD_PInstance_Para.class);
	}

	@Override
	public List<ProcessInfoParameter> retrieveProcessInfoParameters(final Properties ctx, final int adPInstanceId)
	{
		return retrieveAD_PInstance_Params(ctx, adPInstanceId)
				.stream()
				.map(adPInstancePara -> createProcessInfoParameter(adPInstancePara))
				.collect(GuavaCollectors.toImmutableList());
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
	public void saveParameterToDB(final int adPInstanceId, final List<ProcessInfoParameter> piParams)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints() // 02625
					.setOnlyAllowedTrxNamePrefixes(true)
					.addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

			saveParametersToDB0(adPInstanceId, piParams);
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
	private void saveParametersToDB0(final int adPInstanceId, final List<ProcessInfoParameter> piParams)
	{
		// exit if this ProcessInfo has no Parameters
		if (piParams == null || piParams.isEmpty())
		{
			return;
		}

		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");

		//
		// Retrieve parameters from the database, indexed by ParameterName
		final Properties ctx = Env.getCtx();
		final Map<String, I_AD_PInstance_Para> adPInstanceParams = retrieveAD_PInstance_Params(ctx, adPInstanceId)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(I_AD_PInstance_Para::getParameterName));

		//
		// Find out which is the last SeqNo
		int lastSeqNo = adPInstanceParams.values()
				.stream()
				.mapToInt(I_AD_PInstance_Para::getSeqNo)
				.max()
				.orElse(0);

		// Iterate through the ProcessInfo's Parameters and decide if they have to be created or can be overwritten.
		for (final ProcessInfoParameter piParam : piParams)
		{
			I_AD_PInstance_Para adPInstanceParam = adPInstanceParams.get(piParam.getParameterName());

			if (adPInstanceParam == null)        // if this Parameter is not yet existing in the DB
			{
				final int seqNo = lastSeqNo + 10;
				adPInstanceParam = createAD_PInstance_Para(ctx, adPInstanceId, piParam, seqNo);
				lastSeqNo = adPInstanceParam.getSeqNo();
			}
			else
			{
				updateAD_PInstance_Para(adPInstanceParam, piParam);
			}
		}
	} // saveParameterToDB

	private static I_AD_PInstance_Para createAD_PInstance_Para(final Properties ctx, final int adPInstanceId, final ProcessInfoParameter piPara, final int seqNo)
	{
		final Object value = piPara.getParameter();
		final Object valueTo = piPara.getParameter_To();
		// If ValueTo is null, it won't be set in the Database. Otherwise this Parameter would be split
		// and renamed to ParameterName + "1" and ParameterName + "2" since Jasper doesn't support range
		// parameters.

		final I_AD_PInstance_Para instPara = InterfaceWrapperHelper.create(ctx, I_AD_PInstance_Para.class, ITrx.TRXNAME_None);
		instPara.setAD_PInstance_ID(adPInstanceId);
		instPara.setSeqNo(seqNo);

		instPara.setInfo(piPara.getInfo());
		instPara.setInfo_To(piPara.getInfo_To());
		instPara.setParameterName(piPara.getParameterName());

		if (value instanceof java.util.Date || valueTo instanceof java.util.Date)
		{
			final Timestamp valueTS = TimeUtil.asTimestamp((java.util.Date)value);
			instPara.setP_Date(valueTS);
			if (valueTo != null)
			{
				final Timestamp valueToTS = TimeUtil.asTimestamp((java.util.Date)valueTo);
				instPara.setP_Date_To(valueToTS);
			}
		}
		else if (value instanceof BigDecimal || valueTo instanceof BigDecimal)
		{
			instPara.setP_Number((BigDecimal)value);
			if (valueTo != null)
			{
				instPara.setP_Number_To((BigDecimal)valueTo);
			}
		}
		else if (value instanceof Integer || valueTo instanceof Integer)
		{
			instPara.setP_Number(BigDecimal.valueOf((Integer)value));
			if (valueTo != null)
			{
				instPara.setP_Number_To(BigDecimal.valueOf((Integer)valueTo));
			}
		}
		else if (value instanceof String || valueTo instanceof String)
		{
			instPara.setP_String((String)value);
			if (valueTo != null)
			{
				instPara.setP_String_To((String)valueTo);
			}
		}
		else if (value instanceof Boolean || valueTo instanceof Boolean)
		{
			final String valueStr = DisplayType.toBooleanString((Boolean)value);
			instPara.setP_String(valueStr);
			if (valueTo != null)
			{
				final String valueStrTo = DisplayType.toBooleanString((Boolean)valueTo);
				instPara.setP_String_To(valueStrTo);
			}
		}
		else
		{
			logger.warn("Skip setting parameter value for {} because value type is unknown: {}", instPara, piPara);
		}

		InterfaceWrapperHelper.save(instPara);
		return instPara;
	}

	/**
	 * Overwrites an AD_PInstance Parameter (represented by the Object instParam) with a ProcessInfoParameter (represented by the Object piParam). The new values will be saved to the Database only if
	 * there's a difference.
	 *
	 * @param piParam
	 * @param adPInstanceParam
	 * @task US1007
	 */
	private static void updateAD_PInstance_Para(final I_AD_PInstance_Para adPInstanceParam, final ProcessInfoParameter piParam)
	{
		final Object value = piParam.getParameter();
		final Object valueTo = piParam.getParameter_To();

		boolean hasChanges = false;
		Timestamp valueDate = null;
		Timestamp valueDateTo = null;
		String valueString = null;
		String valueStringTo = null;
		BigDecimal valueBigDecimal = null;
		BigDecimal valueBigDecimalTo = null;

		// Check which type of Parameter we have, and if the instPara value differs from the piPara value
		// apply the piPara Value to the instPara value and save. If not, do nothing.

		if (value instanceof java.util.Date
				&& (!value.equals(adPInstanceParam.getP_Date()) || !Objects.equals(valueTo, adPInstanceParam.getP_Date_To())) // value changed
		)
		{
			hasChanges = true;
			valueDate = TimeUtil.asTimestamp((java.util.Date)value);
			valueDateTo = TimeUtil.asTimestamp((java.util.Date)valueTo);
		}
		else if (value instanceof BigDecimal
				&& (!value.equals(adPInstanceParam.getP_Number()) || !Objects.equals(valueTo, adPInstanceParam.getP_Number_To())) // value changed
		)
		{
			hasChanges = true;
			valueBigDecimal = (BigDecimal)value;
			valueBigDecimalTo = (BigDecimal)valueTo;
		}
		else if (value instanceof Integer
				&& (!value.equals(adPInstanceParam.getP_Number()) || !Objects.equals(valueTo, adPInstanceParam.getP_Number_To())) // value changed
		)
		{
			hasChanges = true;
			valueBigDecimal = value == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)value);
			valueBigDecimalTo = valueTo == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)valueTo);
		}
		else if (value instanceof String
				&& (!value.equals(adPInstanceParam.getP_String()) || !Objects.equals(valueTo, adPInstanceParam.getP_String_To())) // value changed
		)
		{
			hasChanges = true;
			valueString = value == null ? null : value.toString();
			valueStringTo = valueTo == null ? null : valueTo.toString();
		}
		else if (value instanceof Boolean)
		{
			hasChanges = true;
			valueString = DisplayType.toBooleanString((Boolean)value);
			valueStringTo = DisplayType.toBooleanString((Boolean)valueTo); // assumes valueTo is also boolean
		}

		if (hasChanges)
		{
			adPInstanceParam.setP_Date(valueDate);
			adPInstanceParam.setP_Date_To(valueDateTo);
			adPInstanceParam.setP_String(valueString);
			adPInstanceParam.setP_String_To(valueStringTo);
			adPInstanceParam.setP_Number(valueBigDecimal);
			adPInstanceParam.setP_Number_To(valueBigDecimalTo);

			adPInstanceParam.setInfo(piParam.getInfo());
			adPInstanceParam.setInfo_To(piParam.getInfo_To());

			InterfaceWrapperHelper.save(adPInstanceParam);
		}
	}

	@Override
	public List<ProcessInfoLog> retrieveProcessInfoLogs(final int adPInstanceId)
	{
		if (adPInstanceId <= 0)
		{
			return ImmutableList.of();
		}

		final String sql = "SELECT Log_ID, P_Date, P_Number, P_Msg "
				+ "FROM AD_PInstance_Log "
				+ "WHERE AD_PInstance_ID=? "
				+ "ORDER BY Log_ID";
		final Object[] sqlParams = new Object[] { adPInstanceId };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<ProcessInfoLog> logs = new ArrayList<>();
			while (rs.next())
			{
				// int Log_ID, int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg
				final ProcessInfoLog log = new ProcessInfoLog(rs.getInt(1), rs.getTimestamp(3), rs.getBigDecimal(4), rs.getString(5));
				log.markAsSavedInDB();
				logs.add(log);
			}

			return logs;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private void saveProcessInfoLogs(final int AD_PInstance_ID, final List<ProcessInfoLog> logs)
	{
		if (AD_PInstance_ID <= 0)
		{
			return;
		}

		final List<ProcessInfoLog> logsToSave = logs
				.stream()
				.filter(log -> !log.isSavedInDB())
				.collect(Collectors.toList());
		if (logsToSave.isEmpty())
		{
			return;
		}

		if (Adempiere.isUnitTestMode())
		{
			// don't try this is we aren't actually connected
			logsToSave.stream().forEach(log -> log.markAsSavedInDB());
			return;
		}

		final String sql = "INSERT INTO " + I_AD_PInstance_Log.Table_Name
				+ " (AD_PInstance_ID, Log_ID, P_Date, P_Number, P_Msg)"
				+ " VALUES (?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			for (final ProcessInfoLog log : logsToSave)
			{
				final Object[] sqlParams = new Object[] {
						AD_PInstance_ID,
						log.getLog_ID(),
						log.getP_Date(),
						log.getP_Number(),
						log.getP_Msg()
				};

				DB.setParameters(pstmt, sqlParams);
				pstmt.addBatch();
			}

			pstmt.executeBatch();

			logsToSave.stream().forEach(log -> log.markAsSavedInDB());
		}
		catch (final SQLException e)
		{
			// log only, don't fail
			logger.error("Error while saving the process log lines", e);
		}
		finally
		{
			DB.close(pstmt);
			pstmt = null;
		}
	}

	@Override
	public void loadResultSummary(final ProcessExecutionResult result)
	{
		//
		final int sleepTime = 1000;	// 1 seconds
		final int noRetry = 5;        // 10 seconds total
		//
		final String sql = "SELECT Result, ErrorMsg FROM AD_PInstance "
				+ "WHERE AD_PInstance_ID=?"
				+ " AND Result IS NOT NULL";
		final Object[] sqlParams = new Object[] { result.getAD_PInstance_ID() };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ITrx.TRXNAME_None);
			for (int noTry = 0; noTry < noRetry; noTry++)
			{
				DB.setParameters(pstmt, sqlParams);

				rs = pstmt.executeQuery();
				if (rs.next())
				{
					// we have a result
					final int resultInt = rs.getInt(1);
					final String message = rs.getString(2);
					if (resultInt == RESULT_OK)
					{
						result.markAsSuccess(message);
					}
					else
					{
						result.markAsError(message);
					}
					return;
				}

				rs.close();

				// sleep
				try
				{
					if (noTry >= 3)
					{
						logger.warn("Waiting for AD_PInstance_ID={} to return a result", result.getAD_PInstance_ID());
					}
					else
					{
						logger.debug("Waiting for AD_PInstance_ID={} to return a result", result.getAD_PInstance_ID());
					}
					Thread.sleep(sleepTime);
				}
				catch (final InterruptedException ie)
				{
					logger.error("Sleep Thread", ie);
				}
			}
		}
		catch (final SQLException e)
		{
			logger.error(sql, e);
			result.markAsError(e);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public void lock(final Properties ctx, final int adPInstanceId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_PInstance.class, ctx, ITrx.TRXNAME_None) // outside trx
				.addEqualsFilter(I_AD_PInstance.COLUMN_AD_PInstance_ID, adPInstanceId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_AD_PInstance.COLUMNNAME_IsProcessing, true)
				.execute();
	}

	@Override
	public void unlockAndSaveResult(final Properties ctx, final ProcessExecutionResult result)
	{
		final int adPInstanceId = result.getAD_PInstance_ID();
		if (adPInstanceId <= 0)
		{
			throw new AdempiereException("Cannot save process execution result because there is no AD_PInstance_ID: " + result);
		}

		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(adPInstance, "adPInstance is not null for AD_PInstance_ID={} of {}", adPInstanceId, result);

		adPInstance.setIsProcessing(false); // unlock
		adPInstance.setResult(result.isError() ? RESULT_ERROR : RESULT_OK);
		adPInstance.setErrorMsg(result.getSummary());
		InterfaceWrapperHelper.save(adPInstance);

		saveProcessInfoLogs(adPInstanceId, result.getCurrentLogs());
	}

	@Override
	public void saveProcessInfoOnly(final ProcessInfo pi)
	{
		//
		// Create/Load the AD_PInstance
		final I_AD_PInstance adPInstance;
		if (pi.getAD_PInstance_ID() <= 0)
		{
			adPInstance = InterfaceWrapperHelper.create(pi.getCtx(), I_AD_PInstance.class, ITrx.TRXNAME_None);
			InterfaceWrapperHelper.setValue(adPInstance, I_AD_PInstance.COLUMNNAME_AD_Client_ID, pi.getAD_Client_ID());
			adPInstance.setIsProcessing(false);
		}
		else
		{
			adPInstance = InterfaceWrapperHelper.create(pi.getCtx(), pi.getAD_PInstance_ID(), I_AD_PInstance.class, ITrx.TRXNAME_None);
			Check.assumeNotNull(adPInstance, "Parameter adPInstance is not null for {}", pi);
		}

		//
		// Update the AD_PInstance and save
		adPInstance.setAD_Org_ID(pi.getAD_Org_ID());
		adPInstance.setAD_User_ID(pi.getAD_User_ID());
		adPInstance.setAD_Role_ID(pi.getAD_Role_ID());
		adPInstance.setAD_Table_ID(pi.getTable_ID());
		adPInstance.setRecord_ID(pi.getRecord_ID());
		adPInstance.setWhereClause(pi.getWhereClause());
		adPInstance.setAD_Process_ID(pi.getAD_Process_ID());

		final Language reportingLanguage = pi.getReportLanguage();
		final String adLanguage = reportingLanguage == null ? null : reportingLanguage.getAD_Language();
		adPInstance.setAD_Language(adLanguage);

		InterfaceWrapperHelper.save(adPInstance);

		//
		// Update ProcessInfo's AD_PInstance_ID
		pi.setAD_PInstance_ID(adPInstance.getAD_PInstance_ID());
	}

	@Override
	public int createAD_PInstance_ID(final Properties ctx)
	{
		final String trxName = ITrx.TRXNAME_None;
		final int adPInstanceId = DB.getNextID(ctx, I_AD_PInstance.Table_Name, trxName);
		Check.assume(adPInstanceId > 0, "Invalid generated AD_PInstance_ID: {}", adPInstanceId);
		return adPInstanceId;
	}

	@Override
	public I_AD_PInstance createAD_PInstance(final Properties ctx, final int AD_Process_ID, final int AD_Table_ID, final int recordId)
	{
		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, I_AD_PInstance.class, ITrx.TRXNAME_None);
		adPInstance.setAD_Process_ID(AD_Process_ID);
		if (AD_Table_ID > 0)
		{
			adPInstance.setAD_Table_ID(AD_Table_ID);
			adPInstance.setRecord_ID(recordId);
		}
		else
		{
			adPInstance.setAD_Table(null);
			adPInstance.setRecord_ID(0); // mandatory
		}
		adPInstance.setAD_User_ID(Env.getAD_User_ID(ctx));
		adPInstance.setAD_Role_ID(Env.getAD_Role_ID(ctx));
		adPInstance.setIsProcessing(false);
		InterfaceWrapperHelper.save(adPInstance);

		return adPInstance;
	}

	@Override
	public I_AD_PInstance retrieveAD_PInstance(final Properties ctx, final int adPInstanceId)
	{
		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");
		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
		if (adPInstance == null || adPInstance.getAD_PInstance_ID() != adPInstanceId)
		{
			throw new AdempiereException("@NotFound@ @AD_PInstance_ID@ (ID=" + adPInstanceId + ")");
		}
		return adPInstance;
	}
}
