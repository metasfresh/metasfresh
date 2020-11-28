/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.contracts.flatrate.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.PO;
import org.compiere.util.DB;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_I_Flatrate_Term;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;

public class FlatrateTermImportProcess extends SimpleImportProcessTemplate<I_I_Flatrate_Term>
{
	private final FlatrateTermImporter flatRateImporter;

	public FlatrateTermImportProcess()
	{
		flatRateImporter = FlatrateTermImporter.newInstance(this);
	}

	@Override
	public Class<I_I_Flatrate_Term> getImportModelClass()
	{
		return I_I_Flatrate_Term.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Flatrate_Term.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_Flatrate_Term.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Flatrate_Term.COLUMNNAME_I_Flatrate_Term_ID;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		final String sqlImportWhereClause = ImportTableDescriptor.COLUMNNAME_I_IsImported + "<>" + DB.TO_BOOLEAN(true)
				+ "\n " + selection.toSqlWhereClause("i");
		FlatrateTermImportTableSqlUpdater.updateFlatrateTermImportTable(sqlImportWhereClause);
	}

	@Override
	protected I_I_Flatrate_Term retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		final PO po = TableModelLoader.instance.getPO(ctx, I_I_Flatrate_Term.Table_Name, rs, ITrx.TRXNAME_ThreadInherited);
		return InterfaceWrapperHelper.create(po, I_I_Flatrate_Term.class);
	}

	@Override
	protected ImportRecordResult importRecord(final @NonNull IMutable<Object> state,
			final @NonNull I_I_Flatrate_Term importRecord,
			final boolean isInsertOnly /* not used. This import always inserts*/)
	{
		flatRateImporter.importRecord(importRecord);
		return ImportRecordResult.Inserted;
	}

	@Override
	protected void markImported(final I_I_Flatrate_Term importRecord)
	{
		// NOTE: overriding the method from abstract class because in case of I_Flatrate_Term,
		// * the I_IsImported is a List (as it should be) and not YesNo
		// * there is no Processing column
		importRecord.setI_IsImported(X_I_Flatrate_Term.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
