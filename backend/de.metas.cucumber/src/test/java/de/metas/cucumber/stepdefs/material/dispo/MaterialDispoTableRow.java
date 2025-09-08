package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class MaterialDispoTableRow
{
	@NonNull StepDefDataIdentifier identifier;
	@NonNull CandidateType type;
	@Nullable CandidateBusinessCase businessCase;
	@NonNull ProductId productId;
	@NonNull BigDecimal qty;
	@NonNull BigDecimal atp;
	@NonNull Instant time;
	@Nullable StepDefDataIdentifier attributeSetInstanceId;
	boolean simulated;
	@Nullable WarehouseId warehouseId;

	@Nullable Distribution distribution;
	@Nullable Production production;

	//
	@NonNull DataTableRow rawValues;

	public CandidatesQuery toCandidatesQuery()
	{
		return CandidatesQuery.builder()
				.type(type)
				.businessCase(businessCase)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.productId(productId.getRepoId())
						.storageAttributesKey(AttributesKey.ALL) // don't restrict on ASI for now; we might use the row's attributeSetInstanceId in this query at a later time
						.timeRangeEnd(DateAndSeqNo.builder()
								.date(time)
								.operator(DateAndSeqNo.Operator.INCLUSIVE)
								.build())
						.build())
				.simulatedQueryQualifier(this.simulated ? SimulatedQueryQualifier.ONLY_SIMULATED : SimulatedQueryQualifier.EXCLUDE_SIMULATED)
				// NOTE: don't add fields like warehouseId to query. Here we need to set only the barely minimum in order to find that record in database
				.build();
	}

	//
	//
	// ------------------------------------------------------------------------
	//
	//

	@Value
	public static class Distribution
	{
		@Nullable DDOrderRefIdentifiers ddOrderRef;
		@Nullable PPOrderRefIdentifiers forwardPPOrderRef;

		@Builder
		private Distribution(
				@Nullable final DDOrderRefIdentifiers ddOrderRef,
				@Nullable final PPOrderRefIdentifiers forwardPPOrderRef)
		{
			this.ddOrderRef = ddOrderRef != null ? ddOrderRef.toNullIfEmpty() : null;
			this.forwardPPOrderRef = forwardPPOrderRef != null ? forwardPPOrderRef.toNullIfEmpty() : null;
		}

		@Nullable
		public Distribution toNullIfEmpty() {return isEmpty() ? null : this;}

		public boolean isEmpty()
		{
			return ddOrderRef == null && forwardPPOrderRef == null;
		}
	}

	@Value
	@Builder
	public static class DDOrderRefIdentifiers
	{
		@Nullable StepDefDataIdentifier ddOrderCandidateId;
		@Nullable StepDefDataIdentifier ddOrderId;
		@Nullable StepDefDataIdentifier ddOrderLineId;

		@Nullable
		public DDOrderRefIdentifiers toNullIfEmpty() {return isEmpty() ? null : this;}

		public boolean isEmpty()
		{
			return ddOrderCandidateId == null
					&& ddOrderId == null
					&& ddOrderLineId == null;
		}
	}

	@Value
	@Builder
	public static class PPOrderRefIdentifiers
	{
		@Nullable StepDefDataIdentifier ppOrderCandidateId;
		@Nullable StepDefDataIdentifier ppOrderLineCandidateId;
		@Nullable StepDefDataIdentifier ppOrderId;
		@Nullable StepDefDataIdentifier ppOrderBOMLineId;

		@Nullable
		public PPOrderRefIdentifiers toNullIfEmpty() {return isEmpty() ? null : this;}

		public boolean isEmpty()
		{
			return ppOrderCandidateId == null
					&& ppOrderLineCandidateId == null
					&& ppOrderId == null
					&& ppOrderBOMLineId == null;
		}
	}

	@Value
	public static class Production
	{
		@Nullable PPOrderRefIdentifiers ppOrderRef;

		@Builder
		private Production(@Nullable final PPOrderRefIdentifiers ppOrderRef)
		{
			this.ppOrderRef = ppOrderRef != null ? ppOrderRef.toNullIfEmpty() : null;
		}

		@Nullable
		public Production toNullIfEmpty() {return isEmpty() ? null : this;}

		public boolean isEmpty()
		{
			return ppOrderRef == null;
		}
	}

}
