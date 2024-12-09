package de.metas.costing;

<<<<<<< HEAD
import java.util.Objects;
import java.util.function.IntFunction;

import javax.annotation.Nullable;

import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.util.Check;
=======
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.order.MatchPOId;
import de.metas.project.ProjectIssueId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
<<<<<<< HEAD
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
	public static final String TABLE_NAME_M_InOutLine = I_M_InOutLine.Table_Name;
	public static final String TABLE_NAME_M_InventoryLine = I_M_InventoryLine.Table_Name;
	public static final String TABLE_NAME_M_MovementLine = I_M_MovementLine.Table_Name;
	public static final String TABLE_NAME_C_ProjectIssue = I_C_ProjectIssue.Table_Name;
	public static final String TABLE_NAME_PP_Cost_Collector = I_PP_Cost_Collector.Table_Name;
<<<<<<< HEAD

	public static CostingDocumentRef ofMatchPOId(final int matchPOId)
	{
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MatchPO, matchPOId, I_M_CostDetail.COLUMNNAME_M_MatchPO_ID, outboundTrx);
=======
	public static final String TABLE_NAME_M_CostRevaluationLine = I_M_CostRevaluationLine.Table_Name;

	public static CostingDocumentRef ofMatchPOId(final int matchPOId)
	{
		return ofMatchPOId(MatchPOId.ofRepoId(matchPOId));
	}

	public static CostingDocumentRef ofMatchPOId(final MatchPOId matchPOId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MatchPO, matchPOId, I_M_CostDetail.COLUMNNAME_M_MatchPO_ID, false);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofMatchInvoiceId(final int matchInvId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MatchInv, matchInvId, I_M_CostDetail.COLUMNNAME_M_MatchInv_ID, outboundTrx);
=======
		return ofMatchInvoiceId(MatchInvId.ofRepoId(matchInvId));
	}

	public static CostingDocumentRef ofMatchInvoiceId(@NonNull final MatchInvId matchInvId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MatchInv, matchInvId, I_M_CostDetail.COLUMNNAME_M_MatchInv_ID, false);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofReceiptLineId(final int inOutLineId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, inOutLineId, I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, outboundTrx);
=======
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, InOutLineId.ofRepoId(inOutLineId), I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, false);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofShipmentLineId(final int inOutLineId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = Boolean.TRUE;
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, inOutLineId, I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, outboundTrx);
=======
		return new CostingDocumentRef(TABLE_NAME_M_InOutLine, InOutLineId.ofRepoId(inOutLineId), I_M_CostDetail.COLUMNNAME_M_InOutLine_ID, true);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofInventoryLineId(final int inventoryLineId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = null;
		return new CostingDocumentRef(TABLE_NAME_M_InventoryLine, inventoryLineId, I_M_CostDetail.COLUMNNAME_M_InventoryLine_ID, outboundTrx);
=======
		return ofInventoryLineId(InventoryLineId.ofRepoId(inventoryLineId));
	}

	public static CostingDocumentRef ofInventoryLineId(@NonNull final InventoryLineId inventoryLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_InventoryLine, inventoryLineId, I_M_CostDetail.COLUMNNAME_M_InventoryLine_ID, null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofOutboundMovementLineId(final int movementLineId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = Boolean.TRUE;
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, outboundTrx);
=======
		return ofOutboundMovementLineId(MovementLineId.ofRepoId(movementLineId));
	}

	public static CostingDocumentRef ofOutboundMovementLineId(final MovementLineId movementLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, true);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofInboundMovementLineId(final int movementLineId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = Boolean.FALSE;
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, outboundTrx);
=======
		return ofInboundMovementLineId(MovementLineId.ofRepoId(movementLineId));
	}

	public static CostingDocumentRef ofInboundMovementLineId(final MovementLineId movementLineId)
	{
		return new CostingDocumentRef(TABLE_NAME_M_MovementLine, movementLineId, I_M_CostDetail.COLUMNNAME_M_MovementLine_ID, false);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofProjectIssueId(final int projectIssueId)
	{
<<<<<<< HEAD
		final Boolean outboundTrx = null;
		return new CostingDocumentRef(TABLE_NAME_C_ProjectIssue, projectIssueId, I_M_CostDetail.COLUMNNAME_C_ProjectIssue_ID, outboundTrx);
=======
		return new CostingDocumentRef(TABLE_NAME_C_ProjectIssue, ProjectIssueId.ofRepoId(projectIssueId), I_M_CostDetail.COLUMNNAME_C_ProjectIssue_ID, null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostingDocumentRef ofCostCollectorId(final int ppCostCollectorId)
	{
<<<<<<< HEAD
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
		Check.assumeGreaterThanZero(recordId, "recordId");

		this.tableName = tableName;
		this.recordId = recordId;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.costDetailColumnName = costDetailColumnName;
		this.outboundTrx = outboundTrx;
	}

<<<<<<< HEAD
	public boolean isTableName(final String expectedTableName)
	{
		return Objects.equals(tableName, expectedTableName);
	}
	
	public boolean isInventoryLine()
	{
		return isTableName(TABLE_NAME_M_InventoryLine);
	}

	public void assertTableName(final String expectedTableName)
	{
		Check.assume(isTableName(expectedTableName), "TableName expected to be {} for {}", expectedTableName, this);
	}

	public <T extends RepoIdAware> T getCostCollectorId(@NonNull final IntFunction<T> mapper)
	{
		return assertTableNameAndGetId(TABLE_NAME_PP_Cost_Collector, mapper);
	}

	public <T extends RepoIdAware> T assertTableNameAndGetId(@NonNull final String expectedTableName, @NonNull final IntFunction<T> mapper)
	{
		assertTableName(expectedTableName);
		return mapper.apply(getRecordId());
	}

	public boolean isInboundTrx()
	{
		return outboundTrx != null && !outboundTrx;
	}

	public boolean isOutboundTrx()
	{
		return outboundTrx != null && outboundTrx;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
