/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.PO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AD_Workflow_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss");

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		return I_AD_Workflow.COLUMNNAME_Name.equals(columnName)
				? String.valueOf(from.get_Value(columnName)).concat("_").concat(DATE_FORMATTER.format(SystemTime.asLocalDateTime()))
				: super.getValueToCopy(to, from, columnName);
	}

	@Override
	public Object getValueToCopy(final GridField gridField)
	{
		return I_AD_Workflow.COLUMNNAME_Name.equals(gridField.getColumnName())
				? String.valueOf(gridField.getValue()).concat("_").concat(DATE_FORMATTER.format(SystemTime.asLocalDateTime()))
				: super.getValueToCopy(gridField);
	}

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(childTableInfo -> I_AD_WF_Node.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}
}