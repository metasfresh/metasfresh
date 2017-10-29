package de.metas.material.dispo.service.event;

import javax.annotation.Nullable;

import de.metas.material.dispo.candidate.CandidateStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventUtil
{
	public CandidateStatus getCandidateStatus(@Nullable final String docStatus)
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
}
