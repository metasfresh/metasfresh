/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.process;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

public class WEBUI_CloneLine extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return getPlan(context)
				.mapIfAbsent(ProcessPreconditionsResolution::rejectWithInternalReason)
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	@Nullable
	@Override
	protected String doIt()
	{
		final ClonePlan plan = getPlan(null).get();
		final PO rowPO = plan.getRowPO();
		final String rowTableName = rowPO.get_TableName();

		CopyRecordFactory.getCopyRecordSupport(rowTableName)
				.setAdWindowId(plan.getAdWindowId())
				.setParentLink(plan.getHeaderPO(), plan.getRowPO_LinkColumnName())
				.copyToNew(rowPO);

		return MSG_OK;
	}

	private ExplainedOptional<ClonePlan> getPlan(final @Nullable IProcessPreconditionsContext preconditionsContext)
	{
		final Set<TableRecordReference> selectedRowRefs;
		if (preconditionsContext != null)
		{
			if (preconditionsContext.getAdTabId() == null)
			{
				return ExplainedOptional.emptyBecause("No row(s) from a tab selected.");
			}

			selectedRowRefs = preconditionsContext.getSelectedIncludedRecords();
		}
		else
		{
			selectedRowRefs = getProcessInfo().getSelectedIncludedRecords();
		}

		if (selectedRowRefs.size() != 1)
		{
			return ExplainedOptional.emptyBecause("More or less than one row selected.");
		}

		final TableRecordReference rowRef = CollectionUtils.singleElement(selectedRowRefs);
		if (!CopyRecordFactory.isEnabledForTableName(rowRef.getTableName()))
		{
			return ExplainedOptional.emptyBecause("CopyRecordFactory not enabled for the table the row belongs to.");
		}

		// we have e.g. in the C_BPartner-window two subtabs ("Customer" and "Vendor") that show a different view on the same C_BPartner record.
		// There, cloning the subtab-line makes no sense
		if (Objects.equals(getProcessInfo().getTableNameOrNull(), rowRef.getTableName()))
		{
			return ExplainedOptional.emptyBecause("The main-window has the same Record as the sub-tab, there can only be one line.");
		}

		final TableRecordReference headerRef = TableRecordReference.of(getTable_ID(), getRecord_ID());
		final PO headerPO = headerRef.getModel(PlainContextAware.newWithThreadInheritedTrx(), PO.class);
		final String headerPO_KeyColumName = headerPO.getPOInfo().getKeyColumnName();
		if (headerPO_KeyColumName == null)
		{
			throw new AdempiereException("A document header which does not have a single primary key is not supported");
		}

		final PO rowPO = rowRef.getModel(PlainContextAware.newWithThreadInheritedTrx(), PO.class);
		if (!rowPO.getPOInfo().hasColumnName(headerPO_KeyColumName))
		{
			throw new AdempiereException("Row which does not link to it's header via header's primary key is not supported");
		}

		return ExplainedOptional.of(
				ClonePlan.builder()
						.rowPO(rowPO)
						.rowPO_LinkColumnName(headerPO_KeyColumName)
						.headerPO(headerPO)
						.adWindowId(getProcessInfo().getAdWindowId())
						.build());
	}

	@Value
	@Builder
	private static class ClonePlan
	{
		@NonNull PO rowPO;
		@NonNull String rowPO_LinkColumnName;

		@NonNull PO headerPO;

		@Nullable AdWindowId adWindowId;
	}
}
