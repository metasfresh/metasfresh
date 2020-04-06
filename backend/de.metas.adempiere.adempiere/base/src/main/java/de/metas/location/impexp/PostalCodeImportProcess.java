package de.metas.location.impexp;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_Postal;
import org.compiere.model.I_I_Postal;
import org.compiere.model.X_I_Postal;

import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.impexp.processing.SimpleImportProcessTemplate.ImportRecordResult;
import de.metas.location.ICountryDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Import {@link I_I_Postal} to {@link I_C_Postal}.
 */
public class PostalCodeImportProcess extends SimpleImportProcessTemplate<I_I_Postal>
{
	@Override
	public Class<I_I_Postal> getImportModelClass()
	{
		return I_I_Postal.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Postal.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_Postal.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Postal.COLUMNNAME_Postal;
	}

	@Override
	protected I_I_Postal retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_Postal(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			@NonNull final IMutable<Object> state_NOTUSED, 
			@NonNull final I_I_Postal importRecord,
			final boolean isInsertOnly_NOTUSED)
	{
		return importPostalCode(importRecord);
	}

	private ImportRecordResult importPostalCode(@NonNull final I_I_Postal importRecord)
	{
		// the current handling for duplicates (postal code + country) is nonexistent.
		// we blindly try to insert in db, and if there are unique constraints failing the records will automatically be marked as failed.

		//noinspection UnusedAssignment
		ImportRecordResult importResult = ImportRecordResult.Nothing;

		final I_C_Postal cPostal = createNewCPostalCode(importRecord);
		importResult = ImportRecordResult.Inserted;

		importRecord.setC_Postal_ID(cPostal.getC_Postal_ID());
		InterfaceWrapperHelper.save(importRecord);

		return importResult;
	}

	private I_C_Postal createNewCPostalCode(final I_I_Postal importRecord)
	{
		final I_C_Postal cPostal = InterfaceWrapperHelper.create(getCtx(), I_C_Postal.class, ITrx.TRXNAME_ThreadInherited);

		cPostal.setC_Country(Services.get(ICountryDAO.class).retrieveCountryByCountryCode(importRecord.getCountryCode()));
		cPostal.setCity(importRecord.getCity());
		cPostal.setPostal(importRecord.getPostal());
		cPostal.setRegionName(importRecord.getRegionName());

		// these 2 are not yet used
		//		importRecord.getValidFrom()
		//		importRecord.getValidTo()

		InterfaceWrapperHelper.save(cPostal);
		return cPostal;
	}
}
