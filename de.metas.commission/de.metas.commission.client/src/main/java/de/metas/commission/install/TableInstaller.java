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


import java.util.Properties;

import org.compiere.model.MTable;
import org.compiere.util.DisplayType;

public final class TableInstaller extends Installer {

	private static final int REFERENCE_VALUE_AD_USER = 110;

	private MTable table;

	private TableInstaller(Properties ctx, String trxName) {
		super(ctx, trxName);
	}

	public static TableInstaller mkInst(final Properties ctx,
			final String trxName) {

		return new TableInstaller(ctx, trxName);
	}

	public TableInstaller createOrUpdate(final String entityType,
			final String tableName) {

		MTable table = MTable.get(ctx, tableName);

		if (table == null) {
			table = new MTable(ctx, 0, trxName);
			table.setTableName(tableName);
		}

		table.setEntityType(entityType);
		this.table = table;

		return this;
	}

	public TableInstaller standardColumns() {

		checkSaved();

		final String entityType = table.getEntityType();
		final String tableName = table.getTableName();

		// id column
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, tableName + "_ID").setReference(DisplayType.ID)
				.setMandatory(true).setUpdatable(false).setKey(true).save()
				.syncColumn(table.isView());

		// AD_Client_ID
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "AD_Client_ID").setReference(DisplayType.TableDir)
				.setMandatory(true).setUpdatable(false).setDefaultValue(
						"@#AD_Client_ID@").save().syncColumn(table.isView());

		// AD_Org_ID
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "AD_Org_ID").setReference(DisplayType.TableDir)
				.setMandatory(true).setUpdatable(false).setDefaultValue(
						"@#AD_Org_ID@").save().syncColumn(table.isView());

		// Created
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "Created").setReference(DisplayType.DateTime)
				.setMandatory(true).setUpdatable(false).save().syncColumn(table.isView());

		// CreatedBy
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "CreatedBy").setReference(DisplayType.Table)
				.setReferenceValue(REFERENCE_VALUE_AD_USER).setMandatory(true)
				.setUpdatable(false).save().syncColumn(table.isView());

		// Updated
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "Updated").setReference(DisplayType.DateTime)
				.setMandatory(true).setUpdatable(false).save().syncColumn(table.isView());

		// UpdatedBy
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "UpdatedBy").setReference(DisplayType.Table)
				.setReferenceValue(REFERENCE_VALUE_AD_USER).setMandatory(true)
				.setUpdatable(false).save().syncColumn(table.isView());

		// IsActive
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(entityType,
				tableName, "IsActive").setReference(DisplayType.YesNo)
				.setMandatory(true).setUpdatable(true).setDefaultValue("Y")
				.save().syncColumn(table.isView());

		return this;
	}

	public TableInstaller setName(final String name) {

		table.setName(name);

		setSaved(false);
		return this;
	}

	public TableInstaller setAccessLevel(final String accessLevel) {

		table.setAccessLevel(accessLevel);

		setSaved(false);
		return this;
	}

	public TableInstaller save() {
		save(table);
		return this;
	}

	public MTable getTable() {
		return table;
	}

	public TableInstaller setView(final boolean view) {

		table.setIsView(view);
		setSaved(false);

		return this;
	}

}
