package de.metas.material.dispo.service.event;

import java.math.BigDecimal;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import lombok.NonNull;

public class EventUtil
{
	public static CandidateStatus getCandidateStatus(@Nullable final String docStatus)
	{
		final CandidateStatus candidateStatus;
		if ("DR".equals(docStatus) || "IP".equals(docStatus))
		{
			candidateStatus = CandidateStatus.doc_created;
		}
		else if ("CO".equals(docStatus))
		{
			candidateStatus = CandidateStatus.doc_completed;
		}
		else if ("CL".equals(docStatus))
		{
			candidateStatus = CandidateStatus.doc_closed;
		}
		else
		{
			candidateStatus = CandidateStatus.unexpected;
		}
		return candidateStatus;
	}

	/** take the max(actual, planned) */
	private static final Function<Candidate, BigDecimal> MATERIAL_QTY_FUNCTION_DEFAULT = candidate -> {

		final BigDecimal plannedQty = candidate.getBusinessCaseDetail().getPlannedQty();
		return candidate.computeActualQty().max(plannedQty);
	};

	/** Take the "actualQty" instead of max(actual, planned) */
	private static final Function<Candidate, BigDecimal> MATERIAL_QTY_FUNCTION_FOR_CLOSED_DD_ORDER = candidate -> candidate.computeActualQty();

	public static Function<Candidate, BigDecimal> deriveDistributionDetail2QtyProvider(
			@NonNull final CandidateStatus candidateStatus)
	{
		if (CandidateStatus.doc_closed.equals(candidateStatus))
		{
			return MATERIAL_QTY_FUNCTION_FOR_CLOSED_DD_ORDER;
		}
		return MATERIAL_QTY_FUNCTION_DEFAULT;

	}
}
