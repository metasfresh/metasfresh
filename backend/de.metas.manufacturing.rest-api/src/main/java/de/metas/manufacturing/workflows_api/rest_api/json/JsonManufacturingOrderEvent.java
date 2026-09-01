package de.metas.manufacturing.workflows_api.rest_api.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import de.metas.common.util.CoalesceUtil;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonLUReceivingTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonTUReceivingTarget;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class JsonManufacturingOrderEvent
{
	//
	// Activity Identifier
	@NonNull String wfProcessId;
	@NonNull String wfActivityId;

	@Value
	@Builder
	@Jacksonized
	public static class IssueTo
	{
		@NonNull String issueStepId;
		@NonNull String huQRCode;
		@Nullable BigDecimal huWeightGrossBeforeIssue;
		@NonNull BigDecimal qtyIssued;
		@Nullable BigDecimal qtyRejected;
		@Nullable String qtyRejectedReasonCode;
	}

	@Nullable IssueTo issueTo;

	@Value
	@Builder
	@Jacksonized
	public static class ReceiveFrom
	{
		@NonNull String lineId;
		@NonNull BigDecimal qtyReceived;
		@Nullable String bestBeforeDate;
		@Nullable String productionDate;
		@Nullable String lotNo;
		@Nullable BigDecimal catchWeight;
		@Nullable String catchWeightUomSymbol;
		@Nullable ScannedCode barcode;
		@Nullable JsonLUReceivingTarget aggregateToLU;
		@Nullable JsonTUReceivingTarget aggregateToTU;

		/**
		 * Generic, per-attribute-code editable-attribute values entered by the operator at receipt (the
		 * config's {@code editableAttributes} list, see {@code MaterialReceiptActivityHandler}), EXCLUDING
		 * Lot/Best-before/Production date which keep using their own dedicated fields above so the
		 * auto-lot (F8041) gate stays untouched. Applied by {@code ReceiveGoodsCommand} onto the produced
		 * HU(s) via the HU attribute storage, next to the catch-weight apply.
		 */
		@Nullable List<Attribute> attributes;

		@JsonIgnore
		public FinishedGoodsReceiveLineId getFinishedGoodsReceiveLineId() {return FinishedGoodsReceiveLineId.ofString(lineId);}

		@JsonIgnore
		@NonNull
		public Map<AttributeCode, String> getAttributesAsMap()
		{
			if (attributes == null || attributes.isEmpty())
			{
				return ImmutableMap.of();
			}

			final HashMap<AttributeCode, String> result = new HashMap<>();
			for (final Attribute attribute : attributes)
			{
				result.put(attribute.getCode(), attribute.getValue());
			}
			return result;
		}
	}

	@Value
	@Builder
	@Jacksonized
	public static class Attribute
	{
		@NonNull AttributeCode code;
		@Nullable String value;
	}

	@Nullable ReceiveFrom receiveFrom;

	@Value
	@Builder
	@Jacksonized
	public static class PickTo
	{
		@NonNull String wfProcessId;
		@NonNull String activityId;
		@NonNull String lineId;
	}

	@Nullable PickTo pickTo;

	@Builder
	@Jacksonized
	private JsonManufacturingOrderEvent(
			@NonNull final String wfProcessId,
			@NonNull final String wfActivityId,
			//
			@Nullable final IssueTo issueTo,
			@Nullable final ReceiveFrom receiveFrom,
			@Nullable final PickTo pickTo)
	{
		if (CoalesceUtil.countNotNulls(issueTo, receiveFrom) != 1)
		{
			throw new AdempiereException("One and only one action like issueTo, receiveFrom etc shall be specified in an event.");
		}

		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;

		this.issueTo = issueTo;
		this.receiveFrom = receiveFrom;
		this.pickTo = pickTo;
	}
}
