package de.metas.costing;

import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.order.MatchPOId;
import de.metas.project.ProjectIssueId;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementLineId;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.model.I_PP_Cost_Collector;

import javax.annotation.Nullable;
import java.util.Objects;

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
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class CostingDocumentRef
{
	public static final String TABLE_NAME_M_MatchPO = I_M_MatchPO.Table_Name;
	public static final String TABLE_NAME_M_MatchInv = I_M_MatchInv.Table_Name;
	public static final String TABLE_NAME_M_Shipping_NotificationLine = I_M_Shipping_NotificationLine.Table_Name;
	public static final String TABLE_NAME_M_InOutLine = I_M_InOutLine.Table_Name;
	public static final String TABLE_NAME_M_InventoryLine = I_M_InventoryLine.Table_Name;
	public static final String TABLE_NAME_M_MovementLine = I_M_MovementLine.Table_Name;
	public static final String TABLE_NAME_C_ProjectIssue = I_C_ProjectIssue.Table_Name;
	public static final String TABLE_NAME_PP_Cost_Collector = I_PP_Cost_Collector.Table_Name;
	public static final String TABLE_NAME_M_CostRevaluationLine = I_M_CostRevaluationLine.Table_Name;

	public static CostingDocumentRef ofMatchPOId(final int matchPOId)
	{
		return ofMatchPOId(MatchPOId.ofRepoId(matchPOId));
	}

	public static CostingDocumentRef ofMatchPOId(final MatchPOId matchPOId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MatchPO, matchPOId, I_M_CostDetail.COLUMNNAME_M_MatchPO_ID, false);
	}

	public static CostingDocumentRef ofMatchInvoiceId(final int matchInvId)
	{
		return ofMatchInvoiceId(MatchInvId.ofRepoId(matchInvId));
	}

	public static CostingDocumentRef ofMatchInvoiceId(@NonNull final MatchInvId matchInvId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MatchInv, matchInvId, I_M_CostDetail.COLUMNNAME_M_MatchInv_ID, false);
	}

	public static CostingDocumentRef ofReceiptLineId(final int inOutLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, InOutLineId.ofRepoId(inOutLineId), I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, false);
	}

	public static CostingDocumentRef ofShipmentLineId(final int inOutLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, InOutLineId.ofRepoId(inOutLineId), I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, true);
	}

	public static CostingDocumentRef ofShippingNotificationLineId(@NonNull final ShippingNotificationLineId shippingNotificationLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_Shipping_NotificationLine, shippingNotificationLineId, I_M_CostDetail.COLUMNNAME_M_Shipping_NotificationLine_ID, true);
	}

	public static CostingDocumentRef ofInventoryLineId(final int inventoryLineId)
	{
		return ofInventoryLineId(InventoryLineId.ofRepoId(inventoryLineId));
	}

	public static CostingDocumentRef ofInventoryLineId(@NonNull final InventoryLineId inventoryLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_InventoryLine, inventoryLineId, I_M_CostDetail.COLUMNNAME_M_InventoryLine_ID, null);
	}

	public static CostingDocumentRef ofOutboundMovementLineId(final int movementLineId)
	{
		return ofOutboundMovementLineId(MovementLineId.ofRepoId(movementLineId));
	}

	public static CostingDocumentRef ofOutboundMovementLineId(final MovementLineId movementLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, true);
	}

	public static CostingDocumentRef ofInboundMovementLineId(final int movementLineId)
	{
		return ofInboundMovementLineId(MovementLineId.ofRepoId(movementLineId));
	}

	public static CostingDocumentRef ofInboundMovementLineId(final MovementLineId movementLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, false);
	}

	public static CostingDocumentRef ofProjectIssueId(final int projectIssueId)
	{
		return new CostingDocumentRef(TABLE_NAME_C_ProjectIssue, ProjectIssueId.ofRepoId(projectIssueId), I_M_CostDetail.COLUMNNAME_C_ProjectIssue_ID, null);
	}

	public static CostingDocumentRef ofCostCollectorId(final int ppCostCollectorId)
	{
		return ofCostCollectorId(PPCostCollectorId.ofRepoId(ppCostCollectorId));
	}

	public static CostingDocumentRef ofCostCollectorId(final PPCostCollectorId ppCostCollectorId)
	{
		return new CostingDocumentRef(TABLE_NAME_PP_Cost_Collector, ppCostCollectorId, I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID, null);
	}

	public static CostingDocumentRef ofCostRevaluationLineId(@NonNull final CostRevaluationLineId costRevaluationLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_CostRevaluationLine, costRevaluationLineId, I_M_CostDetail.COLUMNNAME_M_CostRevaluationLine_ID, null);
	}

	String tableName;
	RepoIdAware id;
	String costDetailColumnName;
	Boolean outboundTrx;

	private CostingDocumentRef(
			@NonNull final String tableName,
			@NonNull final RepoIdAware id,
			@NonNull final String costDetailColumnName,
			@Nullable final Boolean outboundTrx)
	{
		this.tableName = tableName;
		this.id = id;
		this.costDetailColumnName = costDetailColumnName;
		this.outboundTrx = outboundTrx;
	}

	public boolean isTableName(final String expectedTableName) {return Objects.equals(tableName, expectedTableName);}

	public boolean isInventoryLine() {return isTableName(TABLE_NAME_M_InventoryLine);}

	public boolean isCostRevaluationLine() {return isTableName(TABLE_NAME_M_CostRevaluationLine);}

	public boolean isMatchInv()
	{
		return isTableName(TABLE_NAME_M_MatchInv);
	}

	public PPCostCollectorId getCostCollectorId()
	{
		return getId(PPCostCollectorId.class);
	}

	public <T extends RepoIdAware> T getId(final Class<T> idClass)
	{
		if (idClass.isInstance(id))
		{
			return idClass.cast(id);
		}
		else
		{
			throw new AdempiereException("Expected id to be of type " + idClass + " but it was " + id);
		}
	}
}
