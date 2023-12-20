package de.metas.department;

import com.google.common.collect.ImmutableListMultimap;
import de.metas.sectionCode.SectionCodeId;
import lombok.NonNull;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class DepartmentSectionCodeAssignmentsMap
{
	private final ImmutableListMultimap<SectionCodeId, DepartmentSectionCodeAssignment> bySectionCodeDescending;

	public DepartmentSectionCodeAssignmentsMap(@NonNull final List<DepartmentSectionCodeAssignment> list)
	{
		bySectionCodeDescending = list.stream()
				.sorted(Comparator.comparing(DepartmentSectionCodeAssignment::getValidFrom).reversed())
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						assignment -> assignment.getSectionCodeId(),
						assignment -> assignment));
	}

	public Optional<DepartmentId> getDepartmentIdBySectionCodeId(
			@NonNull final SectionCodeId sectionCodeId,
			@NonNull final Instant date)
	{
		return bySectionCodeDescending.get(sectionCodeId)
				.stream()
				.filter(assignment -> assignment.isMatching(date))
				.map(DepartmentSectionCodeAssignment::getDepartmentId)
				.findFirst();
	}
}
