package de.metas.process.impl;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoLog;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.model.I_AD_PInstance_SelectedIncludedRecords;
import de.metas.security.RoleId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

public class ADPInstanceDAO implements IADPInstanceDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADPInstanceDAO.class);

	/** Result OK = 1 */
	public static final int RESULT_OK = 1;
	/** Result FALSE = 0 */
	public static final int RESULT_ERROR = 0;

	private List<I_AD_PInstance_Para> retrieveAD_PInstance_Params(final PInstanceId pinstanceId)
	{
		if (pinstanceId == null)
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_PInstance_Para.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PInstance_Para.COLUMNNAME_AD_PInstance_ID, pinstanceId)
				.orderBy(I_AD_PInstance_Para.COLUMNNAME_SeqNo)
				.create()
				.list(I_AD_PInstance_Para.class);
	}

	@Override
	public List<ProcessInfoParameter> retrieveProcessInfoParameters(final PInstanceId pinstanceId)
	{
		return retrieveAD_PInstance_Params(pinstanceId)
				.stream()
				.map(this::createProcessInfoParameter)
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
				&& (!isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number)
						|| !isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number_To)))
		{
			Parameter = adPInstancePara.getP_Number();
			if (isNull(adPInstancePara, I_AD_PInstance_Para.COLUMNNAME_P_Number_To))
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
	public void saveParameterToDB(@NonNull final PInstanceId pinstanceId, final List<ProcessInfoParameter> piParams)
	{
		DB.saveConstraints();
		try
		{
			DB.getConstraints() // 02625
			.setOnlyAllowedTrxNamePrefixes(true)
			.addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

			saveParametersToDB0(pinstanceId, piParams);
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
	private void saveParametersToDB0(@NonNull final PInstanceId pinstanceId, final List<ProcessInfoParameter> piParams)
	{
		// exit if this ProcessInfo has no Parameters
		if (piParams == null || piParams.isEmpty())
		{
			return;
		}

		//
		// Retrieve parameters from the database, indexed by ParameterName
		final Map<String, I_AD_PInstance_Para> adPInstanceParams = retrieveAD_PInstance_Params(pinstanceId)
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

			if (adPInstanceParam == null) // if this Parameter is not yet existing in the DB
			{
				final int seqNo = lastSeqNo + 10;
				adPInstanceParam = createAD_PInstance_Para(pinstanceId, piParam, seqNo);
				lastSeqNo = adPInstanceParam.getSeqNo();
			}
			else
			{
				updateAD_PInstance_Para(adPInstanceParam, piParam);
			}
		}
	} // saveParameterToDB

	private static I_AD_PInstance_Para createAD_PInstance_Para(@NonNull final PInstanceId pinstanceId, final ProcessInfoParameter from, final int seqNo)
	{
		final I_AD_PInstance_Para record = newInstanceOutOfTrx(I_AD_PInstance_Para.class);
		record.setAD_PInstance_ID(pinstanceId.getRepoId());
		record.setSeqNo(seqNo);
		record.setParameterName(from.getParameterName());

		updateAD_PInstance_Para(record, from);

		return record;
	}

	private static void updateAD_PInstance_Para(final I_AD_PInstance_Para record, final ProcessInfoParameter from)
	{
		final Object value = from.getParameter();
		final Object valueTo = from.getParameter_To();

		Timestamp valueDate = null;
		Timestamp valueDateTo = null;
		String valueString = null;
		String valueStringTo = null;
		BigDecimal valueBigDecimal = null;
		BigDecimal valueBigDecimalTo = null;

		// Check which type of Parameter we have, and if the instPara value differs from the piPara value
		// apply the piPara Value to the instPara value and save. If not, do nothing.

		if (value == null && valueTo == null)
		{
			// all values will be null
		}
		else if (TimeUtil.isDateOrTimeObject(value) || TimeUtil.isDateOrTimeObject(valueTo))
		{
			valueDate = TimeUtil.asTimestamp(value);
			valueDateTo = TimeUtil.asTimestamp(valueTo);
		}
		else if (value instanceof BigDecimal || valueTo instanceof BigDecimal)
		{
			valueBigDecimal = NumberUtils.asBigDecimal(value, BigDecimal.ZERO);
			valueBigDecimalTo = NumberUtils.asBigDecimal(valueTo, BigDecimal.ZERO);
		}
		else if (value instanceof Integer || valueTo instanceof Integer)
		{
			valueBigDecimal = NumberUtils.asBigDecimal(value, BigDecimal.ZERO);
			valueBigDecimalTo = NumberUtils.asBigDecimal(valueTo, BigDecimal.ZERO);
		}
		else if (value instanceof String || valueTo instanceof String)
		{
			valueString = value != null ? value.toString() : null;
			valueStringTo = valueTo != null ? valueTo.toString() : null;
		}
		else if (value instanceof Boolean || valueTo instanceof Boolean)
		{
			valueString = DisplayType.toBooleanString((Boolean)value);
			valueStringTo = DisplayType.toBooleanString((Boolean)valueTo); // assumes valueTo is also boolean
		}
		else
		{
			logger.warn("Don't know how to convert {} and {}", value, valueTo);
		}

		record.setP_Date(valueDate);
		record.setP_Date_To(valueDateTo);
		record.setP_String(valueString);
		record.setP_String_To(valueStringTo);
		record.setP_Number(valueBigDecimal);
		record.setP_Number_To(valueBigDecimalTo);

		record.setInfo(from.getInfo());
		record.setInfo_To(from.getInfo_To());

		saveRecord(record);
	}

	@Override
	public List<ProcessInfoLog> retrieveProcessInfoLogs(final PInstanceId pinstanceId)
	{
		if (pinstanceId == null)
		{
			return ImmutableList.of();
		}

		final String sql = "SELECT Log_ID, P_Date, P_Number, P_Msg "
				+ "FROM AD_PInstance_Log "
				+ "WHERE AD_PInstance_ID=? "
				// Order chronologically
				// note: sometimes Log_ID=0, sometimes P_Date is null so we sort by both to make sure we will have a chronologically order.
				+ "ORDER BY Log_ID, P_Date";
		final Object[] sqlParams = new Object[] { pinstanceId };

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
				final int logId = rs.getInt(1);
				final Timestamp date = rs.getTimestamp(2);
				final BigDecimal number = rs.getBigDecimal(3);
				final String message = rs.getString(4);
				final ProcessInfoLog log = new ProcessInfoLog(logId, date, number, message);
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

	private void saveProcessInfoLogs(final PInstanceId pinstanceId, final List<ProcessInfoLog> logs)
	{
		if (pinstanceId == null)
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
						pinstanceId,
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
		final Object[] sqlParams = new Object[] { result.getPinstanceId() };

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
						logger.warn("Waiting for {} to return a result", result.getPinstanceId());
					}
					else
					{
						logger.debug("Waiting for {} to return a result", result.getPinstanceId());
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
	public void lock(final PInstanceId pinstanceId)
	{
		Services.get(IQueryBL.class)
		.createQueryBuilderOutOfTrx(I_AD_PInstance.class)
		.addEqualsFilter(I_AD_PInstance.COLUMN_AD_PInstance_ID, pinstanceId)
		.create()
		.updateDirectly()
		.addSetColumnValue(I_AD_PInstance.COLUMNNAME_IsProcessing, true)
		.execute();
	}

	@Override
	public void unlockAndSaveResult(final ProcessExecutionResult result)
	{
		final PInstanceId pinstanceId = result.getPinstanceId();
		if (pinstanceId == null)
		{
			throw new AdempiereException("Cannot save process execution result because there is no AD_PInstance_ID: " + result);
		}

		final I_AD_PInstance adPInstance = loadOutOfTrx(pinstanceId, I_AD_PInstance.class);
		Check.assumeNotNull(adPInstance, "adPInstance is not null for {} of {}", pinstanceId, result);

		adPInstance.setIsProcessing(false); // unlock
		adPInstance.setResult(result.isError() ? RESULT_ERROR : RESULT_OK);
		adPInstance.setErrorMsg(result.getSummary());
		saveRecord(adPInstance);

		saveProcessInfoLogs(pinstanceId, result.getCurrentLogs());
	}

	@Override
	public void saveProcessInfo(final ProcessInfo pi)
	{
		saveProcessInfoOnly(pi);

		//
		// Save Parameters to AD_PInstance_Para, if needed
		final List<ProcessInfoParameter> parameters = pi.getParametersNoLoad();
		if (parameters != null && !parameters.isEmpty())
		{
			saveParameterToDB(pi.getPinstanceId(), parameters);
		}

		saveSelectedIncludedRecords(pi.getPinstanceId(), pi.getSelectedIncludedRecords());
	}

	@Override
	public void saveProcessInfoOnly(final ProcessInfo pi)
	{
		//
		// Create/Load the AD_PInstance
		final I_AD_PInstance adPInstance;
		if (pi.getPinstanceId() == null)
		{
			adPInstance = newInstanceOutOfTrx(I_AD_PInstance.class);
			setValue(adPInstance, I_AD_PInstance.COLUMNNAME_AD_Client_ID, pi.getAD_Client_ID());
			adPInstance.setIsProcessing(false);
		}
		else
		{
			adPInstance = loadOutOfTrx(pi.getPinstanceId(), I_AD_PInstance.class);
			Check.assumeNotNull(adPInstance, "Parameter adPInstance is not null for {}", pi);
		}

		//
		// Update the AD_PInstance and save
		adPInstance.setAD_Org_ID(pi.getAD_Org_ID());
		adPInstance.setAD_User_ID(pi.getAD_User_ID());
		adPInstance.setAD_Role_ID(RoleId.toRepoId(pi.getRoleId()));
		adPInstance.setAD_Table_ID(pi.getTable_ID());
		adPInstance.setRecord_ID(CoalesceUtil.firstGreaterThanZero(pi.getRecord_ID(), 0)); // TODO: workaround while Record_ID is mandatory and value <= is interpreted as null
		adPInstance.setWhereClause(pi.getWhereClause());
		adPInstance.setAD_Process_ID(pi.getAdProcessId().getRepoId());
		adPInstance.setAD_Window_ID(pi.getAD_Window_ID());

		final Language reportingLanguage = pi.getReportLanguage();
		final String adLanguage = reportingLanguage == null ? null : reportingLanguage.getAD_Language();
		adPInstance.setAD_Language(adLanguage);

		saveRecord(adPInstance);

		//
		// Update ProcessInfo's AD_PInstance_ID
		pi.setPInstanceId(PInstanceId.ofRepoId(adPInstance.getAD_PInstance_ID()));
	}

	@Override
	public PInstanceId createSelectionId()
	{
		final String trxName = ITrx.TRXNAME_None;
		final int adPInstanceId = DB.getNextID(Env.getCtx(), I_AD_PInstance.Table_Name, trxName);
		return PInstanceId.ofRepoId(adPInstanceId);
	}

	@Override
	public I_AD_PInstance createAD_PInstance(@NonNull final AdProcessId adProcessId)
	{
		final I_AD_PInstance adPInstance = newInstanceOutOfTrx(I_AD_PInstance.class);
		adPInstance.setAD_Process_ID(adProcessId.getRepoId());

		adPInstance.setAD_Table(null);
		adPInstance.setRecord_ID(0); // mandatory

		final Properties ctx = Env.getCtx();
		adPInstance.setAD_User_ID(Env.getAD_User_ID(ctx));
		adPInstance.setAD_Role_ID(Env.getAD_Role_ID(ctx));
		adPInstance.setIsProcessing(false);
		saveRecord(adPInstance);

		return adPInstance;
	}

	@Override
	public PInstanceId createADPinstanceAndADPInstancePara(final @NonNull PInstanceRequest pinstanceRequest)
	{
		final I_AD_PInstance adPInstanceRecord = createAD_PInstance(pinstanceRequest.getProcessId());
		final PInstanceId adPInstance = PInstanceId.ofRepoId(adPInstanceRecord.getAD_PInstance_ID());
		saveParameterToDB(adPInstance, pinstanceRequest.getProcessParams());
		return adPInstance;
	}



	@Override
	public I_AD_PInstance getById(@NonNull final PInstanceId pinstanceId)
	{
		final I_AD_PInstance adPInstance = loadOutOfTrx(pinstanceId, I_AD_PInstance.class);
		if (adPInstance == null || adPInstance.getAD_PInstance_ID() != pinstanceId.getRepoId())
		{
			throw new AdempiereException("@NotFound@ @AD_PInstance_ID@: " + pinstanceId);
		}
		return adPInstance;
	}

	private static final String SQL_SelectAll_AD_PInstance_SelectedIncludedRecords = "SELECT * FROM " + I_AD_PInstance_SelectedIncludedRecords.Table_Name
			+ " WHERE " + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_AD_PInstance_ID + "=?"
			+ " ORDER BY " + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_SeqNo;

	@Override
	public Set<TableRecordReference> retrieveSelectedIncludedRecords(@NonNull final PInstanceId pinstanceId)
	{
		final Object[] sqlParams = new Object[] { pinstanceId };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_SelectAll_AD_PInstance_SelectedIncludedRecords, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<TableRecordReference> recordRefs = ImmutableSet.builder();
			while (rs.next())
			{
				final int adTableId = rs.getInt(I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_AD_Table_ID);
				final int recordId = rs.getInt(I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_Record_ID);
				final TableRecordReference recordRef = TableRecordReference.of(adTableId, recordId);
				recordRefs.add(recordRef);
			}
			return recordRefs.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, SQL_SelectAll_AD_PInstance_SelectedIncludedRecords, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public void saveSelectedIncludedRecords(final PInstanceId pinstanceId, final Set<TableRecordReference> recordRefs)
	{
		deleteSelectedIncludedRecords(pinstanceId);
		insertSelectedIncludedRecords(pinstanceId, recordRefs);
	}

	private static final String SQL_InsertInto_AD_PInstance_SelectedIncludedRecords = "INSERT INTO " + I_AD_PInstance_SelectedIncludedRecords.Table_Name
			+ "(" + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_AD_PInstance_ID
			+ "," + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_AD_Table_ID
			+ "," + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_Record_ID
			+ "," + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_SeqNo
			+ ") VALUES (?, ?, ?, ?)";

	private void insertSelectedIncludedRecords(final PInstanceId pinstanceId, final Set<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return;
		}

		PreparedStatement pstmt = null;
		final ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_InsertInto_AD_PInstance_SelectedIncludedRecords, ITrx.TRXNAME_ThreadInherited);

			int nextSeqNo = 1;
			for (final TableRecordReference recordRef : recordRefs)
			{
				final int seqNo = nextSeqNo;
				nextSeqNo++;

				final Object[] sqlParams = new Object[] { pinstanceId, recordRef.getAD_Table_ID(), recordRef.getRecord_ID(), seqNo };
				DB.setParameters(pstmt, sqlParams);
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, SQL_SelectAll_AD_PInstance_SelectedIncludedRecords);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static final String SQL_DeleteFrom_AD_PInstance_SelectedIncludedRecords = "DELETE FROM " + I_AD_PInstance_SelectedIncludedRecords.Table_Name
			+ " WHERE " + I_AD_PInstance_SelectedIncludedRecords.COLUMNNAME_AD_PInstance_ID + "=?";

	private final void deleteSelectedIncludedRecords(final PInstanceId pinstanceId)
	{
		DB.executeUpdateEx(SQL_DeleteFrom_AD_PInstance_SelectedIncludedRecords, new Object[] { pinstanceId }, ITrx.TRXNAME_ThreadInherited);
	}
}
