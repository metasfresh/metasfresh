package de.metas.department;

import de.metas.sectionCode.SectionCodeId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class DepartmentService
{
	private final DepartmentRepository departmentRepository;
	private final DepartmentSectionCodeAssignmentRepository departmentSectionCodeAssignmentRepository;

	public DepartmentService(
			@NonNull final DepartmentRepository departmentRepository,
			@NonNull final DepartmentSectionCodeAssignmentRepository departmentSectionCodeAssignmentRepository)
	{
		this.departmentRepository = departmentRepository;
		this.departmentSectionCodeAssignmentRepository = departmentSectionCodeAssignmentRepository;
	}

	public Optional<Department> getDepartmentBySectionCodeId(
			@NonNull SectionCodeId sectionCodeId,
			@NonNull Instant date)
	{
		return departmentSectionCodeAssignmentRepository.getDepartmentIdBySectionCodeId(sectionCodeId, date)
				.map(departmentRepository::getById);
	}
}
