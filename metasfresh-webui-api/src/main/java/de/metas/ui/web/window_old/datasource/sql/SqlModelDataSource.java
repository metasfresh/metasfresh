package de.metas.ui.web.window_old.datasource.sql;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ZoomInfoFactory;
import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.SecureEngine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.service.ILocationService;
import de.metas.ui.web.window_old.PropertyName;
import de.metas.ui.web.window_old.datasource.ModelDataSource;
import de.metas.ui.web.window_old.datasource.ModelDataSourceQuery;
import de.metas.ui.web.window_old.datasource.SaveResult;
import de.metas.ui.web.window_old.descriptor.PropertyDescriptor;
import de.metas.ui.web.window_old.model.ModelPropertyDescriptorValueTypeHelper;
import de.metas.ui.web.window_old.shared.datatype.LazyPropertyValuesListDTO;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;
import de.metas.ui.web.window_old.shared.datatype.NullValue;
import de.metas.ui.web.window_old.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window_old.shared.datatype.PropertyValuesListDTO;
import de.metas.ui.web.window_old.shared.descriptor.PropertyDescriptorValueType;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Deprecated
public class SqlModelDataSource implements ModelDataSource
{
	// services
	private static final Logger logger = LogManager.getLogger(SqlModelDataSource.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ILocationService locationService = Services.get(ILocationService.class);

	//
	// SQL definitions
	private final String sqlTableName;
	private final Map<PropertyName, SqlField> sqlFields;
	private final SqlField sqlField_KeyColumn;
	private final SqlField sqlField_ParentLinkColumn;
	/** SQL: SELECT ... FROM ... */
	private final String sqlSelect;
	private final Map<PropertyName, ModelDataSource> includedDataSources;

	private final Object recordsSync = new Object();
	private List<PropertyValuesDTO> _records;

	public SqlModelDataSource(final PropertyDescriptor rootPropertyDescriptor)
	{
		super();

		//
		// Build SQLs
		final SqlsBuilder builder = SqlsBuilder.newBuilder()
				.addRootProperty(rootPropertyDescriptor);
		sqlTableName = builder.getMainTableName();
		sqlFields = builder.buildSqlFieldsIndexedByPropertyName();
		sqlField_KeyColumn = builder.getKeyColumn();
		sqlField_ParentLinkColumn = builder.getParentLinkColumn();
		sqlSelect = builder.buildSqlSelect();
		includedDataSources = ImmutableMap.copyOf(builder.getIncludedDataSources());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("keyColumn", sqlField_KeyColumn)
				.add("parentLinkColumn", sqlField_ParentLinkColumn)
				.toString();
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public int getRecordsCount()
	{
		return getRecords().size();
	}

	@Override
	public PropertyValuesDTO getRecord(final int index)
	{
		return getRecords().get(index);
	}

	private List<PropertyValuesDTO> getRecords()
	{
		if (_records == null)
		{
			synchronized (recordsSync)
			{
				if (_records == null)
				{
					final ModelDataSourceQuery query = ModelDataSourceQuery.builder()
							.build();
					final List<PropertyValuesDTO> records = retriveRecords(query).getList();
					_records = new ArrayList<>(records); // NOTE: make sure it's mutable because we want to change it on save, delete etc
				}
			}
		}
		return _records;
	}

	@Override
	public ITableRecordReference getTableRecordReference(final int recordIndex)
	{
		final PropertyValuesDTO record = getRecord(recordIndex);
		final int recordId = getRecordId(record);

		return new TableRecordReference(sqlTableName, recordId);
	}

	private final Integer getRecordId(final PropertyValuesDTO record)
	{
		final PropertyName keyProperyName = sqlField_KeyColumn.getPropertyName();

		Object keyValue = record.get(keyProperyName);
		if (NullValue.isNull(keyValue))
		{
			keyValue = null;
		}

		return (Integer)keyValue;
	}

	@Override
	public LazyPropertyValuesListDTO retrieveRecordsSupplier(final ModelDataSourceQuery query)
	{
		return LazyPropertyValuesListDTO.memoize(() -> retriveRecords(query));
	}

	@Override
	public PropertyValuesDTO retrieveRecordById(final Object recordId)
	{
		return retriveRecord(ModelDataSourceQuery.builder()
				.setRecordId(recordId)
				.build());
	}

	private final PropertyValuesDTO retriveRecord(final ModelDataSourceQuery query)
	{
		// TODO: optimize

		final PropertyValuesListDTO records = retriveRecords(query);
		final List<PropertyValuesDTO> recordsList = records.getList();
		if (recordsList.isEmpty())
		{
			return null;
		}
		else if (recordsList.size() > 1)
		{
			throw new DBMoreThenOneRecordsFoundException("More than one record found for " + query + " on " + this
					+ "\nRecords: " + Joiner.on("\n").join(recordsList));
		}
		else
		{
			return recordsList.get(0);
		}
	}

	private final PropertyValuesListDTO retriveRecords(final ModelDataSourceQuery query)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(sqlParams, query);
		logger.debug("Retrieving records: SQL={} -- {}", sql, sqlParams);

		final PropertyValuesListDTO.Builder recordsCollector = PropertyValuesListDTO.builder();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final PropertyValuesDTO record = retriveRecord(rs);
				recordsCollector.add(record);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		final PropertyValuesListDTO records = recordsCollector.build();
		if (logger.isTraceEnabled())
			logger.trace("Retrieved {} records.", records.getList().size());
		return records;
	}

	private PropertyValuesDTO retriveRecord(final ResultSet rs) throws SQLException
	{
		final PropertyValuesDTO.Builder data = PropertyValuesDTO.builder();

		//
		// Retrieve main record values
		Object keyColumn_Value = null;
		for (final SqlField sqlField : sqlFields.values())
		{
			final PropertyName propertyName = sqlField.getPropertyName();
			final Object value = retrieveField(rs, sqlField);
			data.put(propertyName, NullValue.makeNotNull(value));

			if (sqlField.isKeyColumn())
			{
				keyColumn_Value = value;
			}
		}

		//
		// Retrieved values from included data sources
		final ModelDataSourceQuery query = ModelDataSourceQuery.builder()
				.setParentLinkId(keyColumn_Value)
				.build();
		for (final Map.Entry<PropertyName, ModelDataSource> e : includedDataSources.entrySet())
		{
			final PropertyName propertyName = e.getKey();
			final ModelDataSource dataSource = e.getValue();

			final LazyPropertyValuesListDTO dataSourceValue = dataSource.retrieveRecordsSupplier(query);
			data.put(propertyName, dataSourceValue);
		}

		return data.build();
	}

	private final String buildSql(final List<Object> sqlParams, final ModelDataSourceQuery query)
	{
		final StringBuilder sql = new StringBuilder();

		sql.append(sqlSelect);

		final String whereClause = buildSqlWhereClause(sqlParams, query);
		if (!Strings.isNullOrEmpty(whereClause))
		{
			sql.append("\n WHERE ").append(whereClause);
		}

		//
		// final String orderBy = buildSqlOrderBy(query);
		// if (!Strings.isNullOrEmpty(orderBy))
		// {
		// sql.append("\n ORDER BY ").append(orderBy);
		// }
		//
		// final String limit = buildSqlLimit(query);
		// if (!Strings.isNullOrEmpty(limit))
		// {
		// sql.append("\n LIMIT ").append(limit);
		// }

		return sql.toString();
	}

	private String buildSqlWhereClause(final List<Object> sqlParams, final ModelDataSourceQuery query)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();

		//
		// Key column
		if (query.getRecordId() != null)
		{
			if (sqlField_KeyColumn == null)
			{
				throw new RuntimeException("Failed building where clause for " + query + " because there is no KeyColumn");
			}

			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(sqlField_KeyColumn.getColumnName()).append("=?");
			sqlParams.add(query.getRecordId());
		}

		//
		// Parent link where clause (if any)
		if (sqlField_ParentLinkColumn != null)
		{
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(sqlField_ParentLinkColumn.getColumnName()).append("=?");
			sqlParams.add(query.getParentLinkId());
		}

		return sqlWhereClause.toString();
	}

	/*
	 * Based on org.compiere.model.GridTable.readData(ResultSet)
	 */
	private Object retrieveField(final ResultSet rs, final SqlField sqlField) throws SQLException
	{
		final String columnName = sqlField.getColumnName();
		final PropertyDescriptorValueType valueType = sqlField.getValueType();
		final Class<?> valueClass = ModelPropertyDescriptorValueTypeHelper.getValueClass(valueType);
		boolean encrypted = sqlField.isEncrypted();

		final Object value;

		if (sqlField.isUsingDisplayColumn())
		{
			final String displayName = rs.getString(sqlField.getDisplayColumnName());
			if (sqlField.isNumericKey())
			{
				final int id = rs.getInt(columnName);
				value = rs.wasNull() ? null : LookupValue.of(id, displayName);
			}
			else
			{
				final String key = rs.getString(columnName);
				value = rs.wasNull() ? null : LookupValue.of(key, displayName);
			}
			encrypted = false;
		}
		else if (valueType == PropertyDescriptorValueType.Location)
		{
			// FIXME: implement it efficiently - see https://metasfresh.atlassian.net/browse/FRESH-287 - Precalculate C_Location display address
			final int locationId = rs.getInt(columnName);
			value = locationService.findLookupValueById(locationId);
			encrypted = false;
		}
		else if(java.lang.String.class == valueClass)
		{
			value = rs.getString(columnName);
		}
		else if (java.lang.Integer.class == valueClass)
		{
			final int valueInt = rs.getInt(columnName);
			value = rs.wasNull() ? null : valueInt;
		}
		else if (java.math.BigDecimal.class == valueClass)
		{
			value = rs.getBigDecimal(columnName);
		}
		else if (java.util.Date.class.isAssignableFrom(valueClass))
		{
			final Timestamp valueTS = rs.getTimestamp(columnName);
			value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
		}
		// YesNo
		else if (Boolean.class == valueClass)
		{
			String valueStr = rs.getString(columnName);
			if (encrypted)
			{
				valueStr = decrypt(valueStr).toString();
				encrypted = false;
			}

			value = ModelPropertyDescriptorValueTypeHelper.convertToBoolean(valueStr);
		}
		// LOB
		else if (byte[].class == valueClass)
		{
			final Object valueObj = rs.getObject(columnName);
			final byte[] valueBytes;
			if (rs.wasNull())
			{
				valueBytes = null;
			}
			else if (valueObj instanceof Clob)
			{
				final Clob lob = (Clob)valueObj;
				final long length = lob.length();
				valueBytes = lob.getSubString(1, (int)length).getBytes();
			}
			else if (valueObj instanceof Blob)
			{
				final Blob lob = (Blob)valueObj;
				final long length = lob.length();
				valueBytes = lob.getBytes(1, (int)length);
			}
			else if (valueObj instanceof String)
			{
				valueBytes = ((String)valueObj).getBytes();
			}
			else if (valueObj instanceof byte[])
			{
				valueBytes = (byte[])valueObj;
			}
			else
			{
				logger.warn("Unknown LOB value '{}' for {}. Considering it null.", valueObj, sqlField);
				valueBytes = null;
			}
			//
			value = valueBytes;
		}
		else
		{
			value = rs.getString(columnName);
		}

		// Decrypt if needed
		final Object valueDecrypted = encrypted ? decrypt(value) : value;

		logger.trace("Retrived value for {}: {} ({})", sqlField, valueDecrypted, valueDecrypted == null ? "null" : valueDecrypted.getClass());

		return valueDecrypted;
	}

	private static final Object decrypt(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return SecureEngine.decrypt(value);
	}	// decrypt

	@Override
	public SaveResult saveRecord(final int recordIndex, final PropertyValuesDTO values)
	{
		final Mutable<SaveResult> resultRef = new Mutable<>();
		trxManager.run(ITrx.TRXNAME_ThreadInherited, (trxName) -> {
			final SaveResult result = saveRecord0(recordIndex, values);
			resultRef.setValue(result);
		});

		return resultRef.getValue();
	}

	private SaveResult saveRecord0(final int recordIndex, final PropertyValuesDTO values)
	{
		final Integer recordId = getRecordId(values);
		if (recordId != null && recordIndex < 0)
		{
			throw new IllegalArgumentException("Cannot save values as a new record because the values contains an existing ID: " + recordId);
		}

		//
		// Load the PO / Create new PO instance
		final PO po;
		final PropertyValuesDTO valuesOld;
		if (recordId == null)
		{
			// new
			po = TableModelLoader.instance.newPO(getCtx(), sqlTableName, ITrx.TRXNAME_ThreadInherited);
			valuesOld = PropertyValuesDTO.of();
		}
		else
		{
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(getCtx(), sqlTableName, recordId, checkCache, ITrx.TRXNAME_ThreadInherited);
			valuesOld = getRecord(recordIndex);
		}
		Check.assumeNotNull(po, "po is not null");
		// TODO po.set_ManualUserAction(m_WindowNo); // metas: tsa: 02380: mark it as manual user action

		//
		// Set values to PO
		for (final Map.Entry<PropertyName, Object> valueEntry : values.entrySet())
		{
			final PropertyName propertyName = valueEntry.getKey();
			final Object value = valueEntry.getValue();

			final ModelDataSource includedDataSource = includedDataSources.get(propertyName);
			if (includedDataSource != null)
			{
				// TODO: implement
				logger.warn("Saving {} to {} NOT IMPLEMENTED!!!", propertyName, includedDataSource);
			}
			else
			{
				final Object valueOld = valuesOld.get(propertyName);
				setPOValue(po, propertyName, value, valueOld);
			}
		}

		//
		// Actual save
		InterfaceWrapperHelper.save(po);
		final Object keyValueNew = InterfaceWrapperHelper.getId(po);

		//
		// Update the buffer
		final PropertyValuesDTO valuesNew = retrieveRecordById(keyValueNew);
		final List<PropertyValuesDTO> records = getRecords();
		final int recordIndexNew;
		if (recordIndex >= 0)
		{
			records.set(recordIndex, PropertyValuesDTO.copyOf(valuesNew));
			recordIndexNew = recordIndex;
		}
		else
		{
			records.add(PropertyValuesDTO.copyOf(valuesNew));
			recordIndexNew = records.size() - 1;
		}

		return SaveResult.of(recordIndexNew, keyValueNew);
	}

	private void setPOValue(final PO po, final PropertyName propertyName, Object value, Object valueOld)
	{
		final SqlField sqlField = sqlFields.get(propertyName);
		if (sqlField == null)
		{
			logger.trace("Skip setting value {}={} because there is no SQL field defined", propertyName, value);
			return;
		}

		final String columnName = sqlField.getColumnName();

		if (NullValue.isNull(value))
		{
			value = null;
		}
		if (NullValue.isNull(valueOld))
		{
			valueOld = null;
		}

		if (Objects.equal(valueOld, value))
		{
			// logger.trace("Skip setting {}={} because value is not changed", propertyName, value);
			return;
		}
		logger.trace("Setting PO value: {}={} (old={}) -- PO={}", columnName, value, valueOld, po);

		if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			value = lookupValue.getId();
		}

		if (value instanceof java.util.Date)
		{
			value = TimeUtil.asTimestamp((java.util.Date)value);
		}

		po.set_ValueOfColumnReturningBoolean(columnName, value);
	}

	@Override
	public List<ZoomInfo> retrieveZoomAccrossInfos(final int recordIndex)
	{
		final PropertyName keyProperyName = sqlField_KeyColumn.getPropertyName();
		final PropertyValuesDTO record = getRecord(recordIndex);
		if (record == null)
		{
			return ImmutableList.of();
		}

		Object keyValue = record.get(keyProperyName);
		if (NullValue.isNull(keyValue))
		{
			keyValue = null;
		}
		if (keyValue == null)
		{
			return ImmutableList.of();
		}

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(sqlTableName);
		final Integer recordId = (Integer)keyValue;

		final String keyColumnName = sqlField_KeyColumn.getColumnName();
		final List<String> keyColumnNames = ImmutableList.of(keyColumnName);

		final IZoomSource zoomSource = new IZoomSource()
		{

			@Override
			public String getTrxName()
			{
				return ITrx.TRXNAME_None;
			}

			@Override
			public String getTableName()
			{
				return sqlTableName;
			}

			@Override
			public int getRecord_ID()
			{
				return recordId;
			}

			@Override
			public List<String> getKeyColumnNames()
			{
				return keyColumnNames;
			}

			@Override
			public String getKeyColumnName()
			{
				return keyColumnName;
			}

			@Override
			public Properties getCtx()
			{
				return SqlModelDataSource.this.getCtx();
			}

			@Override
			public int getAD_Window_ID()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getAD_Table_ID()
			{
				return adTableId;
			}
			
			@Override
			public Evaluatee createEvaluationContext()
			{
				// TODO Auto-generated method stub
				return null;
			}
		};
		return ZoomInfoFactory.get().retrieveZoomInfos(zoomSource);
	}
}
