/**
 * 
 */
package de.metas.acct.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_ElementValue;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AccountImportProcess extends AbstractImportProcess<I_I_ElementValue>
{

	@Override
	public Class<I_I_ElementValue> getImportModelClass()
	{
		return I_I_ElementValue.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_ElementValue.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_ElementValue.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String whereClause = getWhereClause();
		AccountImportTableSqlUpdater.updateAccountImportTable(whereClause);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_ElementValue.COLUMNNAME_Value;
	}

	@Override
	protected I_I_ElementValue retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_ElementValue(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(IMutable<Object> state, I_I_ElementValue importRecord) throws Exception
	{
		//
		// Get previous values
		AccountImportContext context = (AccountImportContext)state.getValue();
		if (context == null)
		{
			context = new AccountImportContext();
			state.setValue(context);
		}
		final I_I_ElementValue previousImportRecord = context.getPreviousImportRecord();
		final int previousElementId = context.getPreviousC_Element_ID();
		context.setPreviousImportRecord(importRecord);

		final ImportRecordResult accountImportResult;

		final boolean firstImportRecordOrNewAccount = previousImportRecord == null
				|| !Objects.equals(importRecord.getC_Element_ID(), previousElementId);

		if (firstImportRecordOrNewAccount)
		{
			// create a new list because we are passing to a new discount
			context.clearPreviousRecordsForSameElement();
			accountImportResult = importElement(importRecord);
		}
		else
		{
			if (previousElementId <= 0)
			{
				accountImportResult = importElement(importRecord);
			}
			else if (importRecord.getC_Element_ID() <= 0 || importRecord.getC_Element_ID() == previousElementId)
			{
				accountImportResult = doNothingAndUsePreviousElement(importRecord, previousImportRecord);
			}
			else
			{
				throw new AdempiereException("Same value or movement date as previous line but not same Inventory linked");
			}
		}

		importElementValue(importRecord);
		context.collectImportRecordForSameElement(importRecord);

		return accountImportResult;
	}

	private ImportRecordResult importElement(I_I_ElementValue importRecord)
	{
		final ImportRecordResult schemaImportResult;

		final I_C_Element element;
		if (importRecord.getC_Element_ID() <= 0)
		{
			element = createNewElement(importRecord);
			schemaImportResult = ImportRecordResult.Inserted;
		}
		else
		{
			element = importRecord.getC_Element();
			schemaImportResult = ImportRecordResult.Updated;
		}

		ModelValidationEngine.get().fireImportValidate(this, importRecord, element, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(element);

		importRecord.setC_Element_ID(element.getC_Element_ID());
		InterfaceWrapperHelper.save(importRecord);

		return schemaImportResult;

	}

	private I_C_Element createNewElement(I_I_ElementValue importRecord)
	{
		final I_C_Element element;
		element = InterfaceWrapperHelper.create(getCtx(), I_C_Element.class, ITrx.TRXNAME_ThreadInherited);
		element.setAD_Org_ID(importRecord.getAD_Org_ID());
		element.setName(importRecord.getElementName());
		InterfaceWrapperHelper.save(element);
		return element;
	}

	private ImportRecordResult doNothingAndUsePreviousElement(I_I_ElementValue importRecord, I_I_ElementValue previousImportRecord)
	{
		importRecord.setC_Element_ID(previousImportRecord.getC_Element_ID());
		InterfaceWrapperHelper.save(importRecord);
		return ImportRecordResult.Nothing;
	}

	private I_C_ElementValue importElementValue(I_I_ElementValue importRecord)
	{
		I_C_ElementValue elementvalue = importRecord.getC_ElementValue();
		if (elementvalue == null)
		{
			elementvalue = InterfaceWrapperHelper.create(getCtx(), I_C_ElementValue.class, ITrx.TRXNAME_ThreadInherited);
			elementvalue.setC_Element_ID(importRecord.getC_Element_ID());
		}

		setElementValueFields(importRecord, elementvalue);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, elementvalue, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(elementvalue);

		importRecord.setC_ElementValue_ID(elementvalue.getC_ElementValue_ID());
		return elementvalue;
	}

	private void setElementValueFields(I_I_ElementValue importRecord, I_C_ElementValue elementvalue)
	{
		elementvalue.setValue(importRecord.getValue());
		elementvalue.setName(importRecord.getName());
		elementvalue.setAccountSign(importRecord.getAccountSign());
		elementvalue.setAccountType(importRecord.getAccountType());
		elementvalue.setIsSummary(importRecord.isSummary());
		elementvalue.setIsDocControlled(importRecord.isDocControlled());
		elementvalue.setPostActual(importRecord.isPostActual());
		elementvalue.setPostBudget(importRecord.isPostBudget());
		elementvalue.setPostStatistical(importRecord.isPostStatistical());
	}

	@Override
	protected void afterImport()
	{
		AccountImportTableSqlUpdater.dbUpdateParentElementValue(getWhereClause());
	}
}
