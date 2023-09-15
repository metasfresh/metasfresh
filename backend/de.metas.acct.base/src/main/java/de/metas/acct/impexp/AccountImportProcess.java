/**
 *
 */
package de.metas.acct.impexp;

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueCreateOrUpdateRequest;
import de.metas.elementvalue.ElementValueService;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.X_I_ElementValue;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Properties;

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
	private final ElementValueService elementValueService;
	private final ChartOfAccountsImportHelper chartOfAccountsImportHelper;

	private final HashSet<ChartOfAccountsId> affectedChartOfAccountsIds = new HashSet<>();

	public AccountImportProcess()
	{
		this(
				SpringContextHolder.instance.getBean(ChartOfAccountsService.class),
				SpringContextHolder.instance.getBean(ElementValueService.class)
		);
	}

	@VisibleForTesting
	public AccountImportProcess(
			@NonNull final ChartOfAccountsService chartOfAccountsService,
			@NonNull final ElementValueService elementValueService)
	{
		this.elementValueService = elementValueService;
		this.chartOfAccountsImportHelper = new ChartOfAccountsImportHelper(chartOfAccountsService);
	}

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
		// nothing
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_ElementValue.COLUMNNAME_I_ElementValue_ID;
	}

	@Override
	protected I_I_ElementValue retrieveImportRecord(Properties ctx, ResultSet rs)
	{
		return new X_I_ElementValue(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,
											  @NonNull final I_I_ElementValue importRecord,
											  final boolean isInsertOnly)
	{
		final ChartOfAccountsId chartOfAccountsId = chartOfAccountsImportHelper.importChartOfAccounts(importRecord);
		chartOfAccountsImportHelper.setChartOfAccountsToDefaultSchemaElement(chartOfAccountsId);

		final ElementValueId existingElementValueId = ElementValueId.ofRepoIdOrNull(importRecord.getC_ElementValue_ID());
		if (existingElementValueId != null && isInsertOnly)
		{
			return ImportRecordResult.Nothing;
		}

		final ElementValue elementValue = elementValueService.createOrUpdate(
				ElementValueCreateOrUpdateRequest.builder()
						.existingElementValueId(existingElementValueId)
						.orgId(OrgId.ofRepoId(importRecord.getAD_Org_ID()))
						.chartOfAccountsId(chartOfAccountsId)
						.value(Check.assumeNotNull(importRecord.getValue(), "Value shall be set"))
						.name(Check.assumeNotNull(importRecord.getName(), "Name shall be set"))
						.accountSign(Check.assumeNotNull(importRecord.getAccountSign(), "AccountSign shall be set"))
						.accountType(Check.assumeNotNull(importRecord.getAccountType(), "AccountType shall be set"))
						.isSummary(importRecord.isSummary())
						.isDocControlled(importRecord.isDocControlled())
						.isPostActual(importRecord.isPostActual())
						.isPostBudget(importRecord.isPostBudget())
						.isPostStatistical(importRecord.isPostStatistical())
						.parentId(getParentElementValueId(importRecord))
						.defaultAccountName(importRecord.getDefault_Account())
						.build()
		);

		importRecord.setC_ElementValue_ID(elementValue.getId().getRepoId());
		importRecord.setParentElementValue_ID(ElementValueId.toRepoId(elementValue.getParentId()));
		InterfaceWrapperHelper.save(importRecord);

		affectedChartOfAccountsIds.add(chartOfAccountsId);

		return existingElementValueId == null ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	@Nullable
	private ElementValueId getParentElementValueId(final @NonNull I_I_ElementValue importRecord)
	{
		final ElementValueId parentId = ElementValueId.ofRepoIdOrNull(importRecord.getParentElementValue_ID());
		if (parentId != null)
		{
			return parentId;
		}

		final ChartOfAccountsId chartOfAccountsId = ChartOfAccountsId.ofRepoId(importRecord.getC_Element_ID());
		final String parentAccountNo = StringUtils.trimBlankToNull(importRecord.getParentValue());
		if (parentAccountNo != null)
		{
			return elementValueService.getByAccountNo(parentAccountNo, chartOfAccountsId)
					.map(ElementValue::getId)
					.orElseThrow(() -> new AdempiereException("No parent account found for `" + parentAccountNo + "` and " + chartOfAccountsId));
		}
		else
		{
			return null;
		}
	}

	@Override
	protected void afterImport()
	{
		affectedChartOfAccountsIds.forEach(elementValueService::reorderByAccountNo);
	}
}
