package de.metas.material.dispo.service.event;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.util.function.Function;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;

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

		final BigDecimal plannedQty = candidate.getBusinessCaseDetail().getQty();
		return candidate.computeActualQty().max(plannedQty);
	};

	/** Take the "actualQty" instead of max(actual, planned) */
	private static final Function<Candidate, BigDecimal> MATERIAL_QTY_FUNCTION_FOR_CLOSED = candidate -> candidate.computeActualQty();

	public static Function<Candidate, BigDecimal> deriveCandiadte2QtyProvider(
			@NonNull final CandidateStatus candidateStatus)
	{
		if (CandidateStatus.doc_closed.equals(candidateStatus))
		{
			return MATERIAL_QTY_FUNCTION_FOR_CLOSED;
		}
		return MATERIAL_QTY_FUNCTION_DEFAULT;

	}
}
