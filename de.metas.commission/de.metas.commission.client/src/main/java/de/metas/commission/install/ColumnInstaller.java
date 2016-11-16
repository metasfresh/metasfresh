package de.metas.commission.install;

/*
 * #%L
 * de.metas.commission.client
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


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;

public final class ColumnInstaller extends Installer {

	private static final Logger logger = LogManager.getLogger(ColumnInstaller.class);

	private MColumn column;

	private ColumnInstaller(Properties ctx, String trxName) {
		super(ctx, trxName);
	}

	public static ColumnInstaller mkInst(final Properties ctx,
			final String trxName) {

		return new ColumnInstaller(ctx, trxName);
	}

	public ColumnInstaller createOrUpdate(final String entityType,
			final String tableName, final String columnName) {

		M_Element element = M_Element.get(ctx, columnName, trxName);
		if (element == null) {

			logger.debug("Creating new AD_Element for column");
			element = new M_Element(ctx, 0, trxName);
			element.setEntityType(entityType);
			element.setColumnName(columnName);
			element.setName(columnName);
			element.setPrintName(columnName);
			element.saveEx();
		}

		final MTable table = MTable.get(ctx, tableName);
		MColumn col = table.getColumn(columnName);

		if (col == null) {

			col = new MColumn(ctx, 0, trxName);
			col.setColumnName(columnName);
			col.setAD_Table_ID(table.get_ID());

		} else {
			col.set_TrxName(trxName);
		}

		if (col.getAD_Element_ID() <= 0) {
			col.setAD_Element_ID(element.get_ID());
		}

		col.setName(columnName);
		col.setEntityType(entityType);
		column = col;
		return this;
	}

	public ColumnInstaller setMandatory(final boolean mandatory) {

		column.setIsMandatory(mandatory);
		setSaved(false);
		return this;
	}

	public ColumnInstaller setUpdatable(final boolean updatable) {

		column.setIsUpdateable(updatable);
		setSaved(false);
		return this;
	}

	public ColumnInstaller setReference(final int referenceId) {

		column.setAD_Reference_ID(referenceId);

		if (referenceId == DisplayType.YesNo) {
			column.setFieldLength(1);
		}

		setSaved(false);
		return this;
	}

	public ColumnInstaller setKey(final boolean key) {

		column.setIsKey(key);

		setSaved(false);
		return this;
	}

	public ColumnInstaller setDefaultValue(final String defaultVal) {

		column.setDefaultValue(defaultVal);
		setSaved(false);

		return this;
	}

	public ColumnInstaller setReferenceValue(final int referenceValueId) {

		column.setAD_Reference_Value_ID(referenceValueId);

		setSaved(false);

		return this;
	}

	public ColumnInstaller save() {

		save(column);

		return this;
	}

	ColumnInstaller syncColumn(final boolean ignoreCall) {

		if (ignoreCall) {
			return this;
		}
		return syncColumn();
	}

	public ColumnInstaller syncColumn() {

		checkSaved();

		final String processValue = "AD_Column Sync";
		final ProcessInfo pi = ProcessInfo.builder()
				.setAD_ProcessByValue(processValue)
				.setRecord(I_AD_Column.Table_Name, column.getAD_Column_ID())
				.build();

		// final Trx trx = Trx.get(trxName, false);
		ProcessExecutor.builder()
				.setProcessInfo(pi)
				.execute();

		return this;
	}

	/**
	 * Removes the given old column from AD and DB. If the old column is still
	 * referred to by fields, those fields' AD_Column_ID is updated to this
	 * installer's column. If the old column still exists in the DB, this
	 * installer's column is updated with the old column's values before the old
	 * column is dropped.
	 * 
	 * @param oldColName
	 */
	public ColumnInstaller replacesOldCol(final String oldColName) {

		checkSaved();

		final MTable table = (MTable) column.getAD_Table();
		final MColumn oldCol = table.getColumn(oldColName);

		if (oldCol != null) {
			oldCol.set_TrxName(trxName);

			logger.debug(table.get_TableName() + "." + oldCol
					+ " still existing in AD");

			final List<MField> fields = new Query(ctx, I_AD_Field.Table_Name,
					I_AD_Field.COLUMNNAME_AD_Column_ID + "=?", trxName)
					.setParameters(oldCol.get_ID()).list();

			for (final MField field : fields) {
				logger.debug(field + " still uses the old column. Updating it");
				field.setAD_Column_ID(column.get_ID());
				field.save();
			}

			oldCol.deleteEx(false);
		}

		final Connection conn = DB.getConnectionRO();
		final String catalog = DB.getDatabase().getCatalog();
		final String schema = DB.getDatabase().getSchema();

		try {
			final DatabaseMetaData md = conn.getMetaData();

			final String tableNamePattern;
			final String colNamePattern;

			if (md.storesUpperCaseIdentifiers()) {
				tableNamePattern = table.getTableName().toUpperCase();
				colNamePattern = oldColName.toUpperCase();
			} else if (md.storesLowerCaseIdentifiers()) {
				tableNamePattern = table.getTableName().toLowerCase();
				colNamePattern = oldColName.toLowerCase();
			} else {
				tableNamePattern = table.getTableName();
				colNamePattern = oldColName;
			}

			final ResultSet rs = md.getColumns(catalog, schema,
					tableNamePattern, colNamePattern);
			if (rs.next()) {
				logger
						.debug(tableNamePattern
								+ "."
								+ colNamePattern
								+ " still existing in DB. Copying data to new column and dropping it");

				final int no = DB.executeUpdateEx("UPDATE "
						+ table.getTableName() + " SET "
						+ column.getColumnName() + "=" + oldColName, trxName);
				logger.debug("updated " + no + " rows");

				DB.executeUpdateEx("ALTER TABLE " + table.getTableName()
						+ " DROP COLUMN " + colNamePattern, trxName);
			}

		} catch (SQLException e) {
			throw new DBException(e);
		}

		return this;
	}

	public MColumn getColumn() {
		return column;
	}


	public ColumnInstaller setParent(final boolean parentLink) {

		column.setIsParent(parentLink);

		setSaved(false);
		return this;
	}

	public ColumnInstaller setFieldLength(final int fieldLength) {

		column.setFieldLength(fieldLength);

		setSaved(false);
		return this;
	}

	public ColumnInstaller setColumnSQL(String columnSQL) {

		column.setColumnSQL(columnSQL);

		setSaved(false);
		return this;
	}

}
