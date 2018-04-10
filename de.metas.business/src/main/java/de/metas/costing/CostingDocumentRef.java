package de.metas.costing;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_PP_Cost_Collector;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class CostingDocumentRef
{
	public static final String TABLE_NAME_M_MatchPO = I_M_MatchPO.Table_Name;
	public static final String TABLE_NAME_M_MatchInv = I_M_MatchInv.Table_Name;
	public static final String TABLE_NAME_M_InOutLine = I_M_InOutLine.Table_Name;
	public static final String TABLE_NAME_M_InventoryLine = I_M_InventoryLine.Table_Name;
	public static final String TABLE_NAME_M_MovementLine = I_M_MovementLine.Table_Name;
	public static final String TABLE_NAME_C_ProjectIssue = I_C_ProjectIssue.Table_Name;
	public static final String TABLE_NAME_PP_Cost_Collector = I_PP_Cost_Collector.Table_Name;

	public static CostingDocumentRef ofMatchPOId(final int matchPOId)
	{
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MatchPO, matchPOId, I_M_CostDetail.COLUMNNAME_M_MatchPO_ID, outboundTrx);
	}

	public static CostingDocumentRef ofMatchInvoiceId(final int matchInvId)
	{
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MatchInv, matchInvId, I_M_CostDetail.COLUMNNAME_M_MatchInv_ID, outboundTrx);
	}

	public static CostingDocumentRef ofReceiptLineId(final int inOutLineId)
	{
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, inOutLineId, I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, outboundTrx);
	}

	public static CostingDocumentRef ofShipmentLineId(final int inOutLineId)
	{
		final Boolean outboundTrx = Boolean.TRUE;
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, inOutLineId, I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, outboundTrx);
	}

	public static CostingDocumentRef ofInventoryLineId(final int inventoryLineId)
	{
		final Boolean outboundTrx = null;
		return new CostingDocumentRef(TABLE_NAME_M_InventoryLine, inventoryLineId, I_M_CostDetail.COLUMNNAME_M_InventoryLine_ID, outboundTrx);
	}

	public static CostingDocumentRef ofOutboundMovementLineId(final int movementLineId)
	{
		final Boolean outboundTrx = Boolean.TRUE;
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, outboundTrx);
	}

	public static CostingDocumentRef ofInboundMovementLineId(final int movementLineId)
	{
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, outboundTrx);
	}

	public static CostingDocumentRef ofProjectIssueId(final int projectIssueId)
	{
		final Boolean outboundTrx = null;
		return new CostingDocumentRef(TABLE_NAME_C_ProjectIssue, projectIssueId, I_M_CostDetail.COLUMNNAME_C_ProjectIssue_ID, outboundTrx);
	}

	public static CostingDocumentRef ofCostCollectorId(final int ppCostCollectorId)
	{
		final Boolean outboundTrx = null;
		return new CostingDocumentRef(TABLE_NAME_PP_Cost_Collector, ppCostCollectorId, I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID, outboundTrx);
	}

	private final String tableName;
	private final int recordId;
	private final String costDetailColumnName;
	private final Boolean outboundTrx;

	private CostingDocumentRef(
			@NonNull final String tableName,
			final int recordId,
			@NonNull final String costDetailColumnName,
			@Nullable final Boolean outboundTrx)
	{
		Check.assume(recordId > 0, "recordId > 0");

		this.tableName = tableName;
		this.recordId = recordId;
		this.costDetailColumnName = costDetailColumnName;
		this.outboundTrx = outboundTrx;
	}

	public boolean isTableName(final String expectedTableName)
	{
		return Objects.equals(tableName, expectedTableName);
	}

	public boolean isInboundTrx()
	{
		return outboundTrx != null && !outboundTrx;
	}

	public boolean isOutboundTrx()
	{
		return outboundTrx != null && outboundTrx;
	}

}
