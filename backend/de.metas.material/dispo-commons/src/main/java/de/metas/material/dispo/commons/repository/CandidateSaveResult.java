package de.metas.material.dispo.commons.repository;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class CandidateSaveResult
{
	@NonNull Candidate candidate;
	@Nullable DateAndSeqNo previousTime;
	@Nullable BigDecimal previousQty;

	public CandidateId getId() {return candidate.getId();}

	public Optional<MaterialDispoGroupId> getEffectiveGroupId() {return Optional.ofNullable(candidate.getEffectiveGroupId());}

	public Optional<MaterialDispoGroupId> getGroupId() {return Optional.ofNullable(candidate.getGroupId());}

	public BigDecimal getQtyDelta()
	{
		BigDecimal qtyDelta = candidate.getQuantity();
		if (previousQty != null)
		{
			qtyDelta = qtyDelta.subtract(previousQty);
		}
		return qtyDelta;
	}

	/**
	 * @return {@code true} if before the save, there already was a record with a different date.
	 */
	public boolean isDateMoved()
	{
		if (previousTime == null)
		{
			return false;
		}
		return !DateAndSeqNo.equals(previousTime, DateAndSeqNo.ofCandidate(candidate));
	}

	public boolean isDateMovedBackwards()
	{
		if (previousTime == null)
		{
			return false;
		}
		return previousTime.isAfter(DateAndSeqNo.ofCandidate(candidate));
	}

	public boolean isDateMovedForwards()
	{
		if (previousTime == null)
		{
			return false;
		}
		return previousTime.isBefore(DateAndSeqNo.ofCandidate(candidate));
	}

	/**
	 * @return {@code true} there was no record before the save, or the record's date was changed.
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDateChanged()
	{
		if (previousTime == null)
		{
			return true;
		}
		return !DateAndSeqNo.equals(previousTime, DateAndSeqNo.ofCandidate(candidate));
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isQtyChanged()
	{
		return getQtyDelta().signum() != 0;
	}

	// TODO figure out if we really need this
	public Candidate toCandidateWithQtyDelta()
	{
		return candidate.withQuantity(getQtyDelta());
	}

	/**
	 * Convenience method that returns a new instance whose included {@link Candidate} has the given id.
	 */
	public CandidateSaveResult withCandidateId(@Nullable final CandidateId candidateId)
	{
		return toBuilder()
				.candidate(candidate.withId(candidateId))
				.build();
	}

	/**
	 * Convenience method that returns a new instance with negated candidate quantity and previousQty
	 */
	public CandidateSaveResult withNegatedQuantity()
	{
		return toBuilder()
				.candidate(candidate.withNegatedQuantity())
				.previousQty(previousQty == null ? null : previousQty.negate())
				.build();
	}

	public CandidateSaveResult withParentId(@Nullable final CandidateId parentId)
	{
		return toBuilder()
				.candidate(candidate.withParentId(parentId))
				.build();
	}
}
