/**
 *
 */
package de.metas.acct.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_AD_Tree;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.MAccount;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_AD_Tree;
import org.compiere.model.X_C_Element;
import org.compiere.model.X_I_ElementValue;

import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

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
public class AccountImportProcess extends SimpleImportProcessTemplate<I_I_ElementValue>
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
		final ImportRecordsSelection importRecordsSelection = getImportRecordsSelection();
		AccountImportTableSqlUpdater.updateAccountImportTable(importRecordsSelection);
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
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,
			@NonNull final I_I_ElementValue importRecord,
			final boolean isInsertOnly)
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
		final String previousElementName = context.getPreviousElementName();
		context.setPreviousImportRecord(importRecord);

		final ImportRecordResult accountImportResult;

		final boolean firstImportRecordOrNewAccount = previousImportRecord == null
				|| !Objects.equals(importRecord.getElementName(), previousElementName);

		if (!firstImportRecordOrNewAccount && isInsertOnly)
		{
			// #4994 do not update
			return ImportRecordResult.Nothing;

		}
		if (firstImportRecordOrNewAccount)
		{
			// create a new list because we are passing to a new discount
			context.clearPreviousRecordsForSameElement();
			accountImportResult = importElement(importRecord);
		}
		else
		{
			if (Check.isEmpty(previousElementName, true))
			{
				accountImportResult = importElement(importRecord);
			}
			else if ((importRecord.getC_Element_ID() <= 0 && importRecord.getElementName().equals(previousElementName))
					|| importRecord.getC_Element_ID() > 0)
			{
				accountImportResult = doNothingAndUsePreviousElement(importRecord, previousImportRecord);
			}
			else
			{
				throw new AdempiereException("Same value or movement date as previous line but not same Element linked");
			}
		}

		importElementValue(importRecord);
		createOrGetValidCombination(importRecord);

		context.collectImportRecordForSameElement(importRecord);

		return accountImportResult;
	}

	private ImportRecordResult importElement(@NonNull final I_I_ElementValue importRecord)
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

	private I_C_Element createNewElement(@NonNull final I_I_ElementValue importRecord)
	{
		final I_AD_Tree tree = createADTree(importRecord);
		return createElement(importRecord, tree);
	}

	private I_AD_Tree createADTree(final I_I_ElementValue importRecord)
	{
		final I_AD_Tree tree = InterfaceWrapperHelper.newInstance(I_AD_Tree.class, importRecord);
		tree.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_ElementValue.Table_Name));
		tree.setName(importRecord.getElementName());
		tree.setTreeType(X_AD_Tree.TREETYPE_ElementValue);
		tree.setIsAllNodes(true);
		InterfaceWrapperHelper.save(tree);
		return tree;
	}

	private I_C_Element createElement(final I_I_ElementValue importRecord, final I_AD_Tree tree)
	{
		final I_C_Element element = InterfaceWrapperHelper.newInstance(I_C_Element.class, importRecord);
		element.setAD_Org_ID(importRecord.getAD_Org_ID());
		element.setName(importRecord.getElementName());
		element.setAD_Tree_ID(tree.getAD_Tree_ID());
		element.setElementType(X_C_Element.ELEMENTTYPE_Account);
		InterfaceWrapperHelper.save(element);
		return element;
	}

	private ImportRecordResult doNothingAndUsePreviousElement(@NonNull final I_I_ElementValue importRecord, @NonNull final I_I_ElementValue previousImportRecord)
	{
		InterfaceWrapperHelper.refresh(previousImportRecord);
		importRecord.setC_Element_ID(previousImportRecord.getC_Element_ID());
		InterfaceWrapperHelper.save(importRecord);
		return ImportRecordResult.Nothing;
	}

	private I_C_ElementValue importElementValue(@NonNull final I_I_ElementValue importRecord)
	{
		final I_C_ElementValue elementvalue;
		if (importRecord.getC_ElementValue_ID() <= 0)
		{
			elementvalue = InterfaceWrapperHelper.newInstance(I_C_ElementValue.class, importRecord);
		}
		else
		{
			elementvalue = importRecord.getC_ElementValue();
		}

		setElementValueFields(importRecord, elementvalue);

		InterfaceWrapperHelper.save(elementvalue);
		importRecord.setC_ElementValue_ID(elementvalue.getC_ElementValue_ID());

		return elementvalue;
	}

	private void setElementValueFields(@NonNull final I_I_ElementValue importRecord, @NonNull final I_C_ElementValue elementvalue)
	{
		elementvalue.setC_Element_ID(importRecord.getC_Element_ID());
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

	private void createOrGetValidCombination(@NonNull final I_I_ElementValue importRecord)
	{
		if (importRecord.isSummary())
		{
			return;
		}
		final AccountDimension acctDim = newAccountDimension(importRecord);
		MAccount.getCreate(acctDim);
	}

	private AccountDimension newAccountDimension(@NonNull final I_I_ElementValue importRecord)
	{
		final AcctSchemaId acctSchemaId = Services.get(IAcctSchemaDAO.class).getAcctSchemaIdByClientAndOrg(ClientId.ofRepoId(importRecord.getAD_Client_ID()), OrgId.ofRepoId(importRecord.getAD_Org_ID()));

		return AccountDimension.builder()
				.setAcctSchemaId(acctSchemaId)
				.setAD_Client_ID(importRecord.getAD_Client_ID())
				.setAD_Org_ID(importRecord.getAD_Org_ID())
				.setC_ElementValue_ID(importRecord.getC_ElementValue_ID())
				.build();
	}

	@Override
	protected void afterImport()
	{
		AccountImportTableSqlUpdater.dbUpdateParentElementValue(getImportRecordsSelection());
		retrieveImportedAdTrees().forEach(AccountImportTableSqlUpdater::dbUpdateParentElementValueId);
		retrieveImportedElements().forEach(AccountImportTableSqlUpdater::dbUpdateElementValueParentAndSeqNo);
	}

	private Set<Integer> retrieveImportedAdTrees()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_I_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_ElementValue.COLUMNNAME_Processed, true)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_I_ElementValue.COLUMN_C_Element_ID)
				.andCollect(I_C_Element.COLUMN_AD_Tree_ID)
				.create()
				.listDistinct(I_AD_Tree.COLUMNNAME_AD_Tree_ID)
				.stream()
				.map(map -> extractTreeIdOrNull(map))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final Integer extractTreeIdOrNull(final Map<String, Object> map)
	{
		final int treeId = NumberUtils.asInt(map.get(I_AD_Tree.COLUMNNAME_AD_Tree_ID), -1);
		if (treeId <= 0)
		{
			return null;
		}

		return treeId;
	}
	
	private Set<Integer> retrieveImportedElements()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_I_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_ElementValue.COLUMNNAME_Processed, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_I_ElementValue.COLUMNNAME_C_Element_ID)
				.stream()
				.map(map -> extractElementIdOrNull(map))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final Integer extractElementIdOrNull(final Map<String, Object> map)
	{
		final int elementId = NumberUtils.asInt(map.get(I_C_Element.COLUMNNAME_C_Element_ID), -1);
		if (elementId <= 0)
		{
			return null;
		}

		return elementId;
	}
}
