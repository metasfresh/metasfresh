package de.metas.department;

import de.metas.cache.CCache;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Department_SectionCode;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
class DepartmentSectionCodeAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, DepartmentSectionCodeAssignmentsMap> cache = CCache.<Integer, DepartmentSectionCodeAssignmentsMap>builder()
			.tableName(I_M_Department_SectionCode.Table_Name)
			.build();

	public Optional<DepartmentId> getDepartmentIdBySectionCodeId(@NonNull SectionCodeId sectionCodeId, @NonNull Instant date)
	{
		return getMap().getDepartmentIdBySectionCodeId(sectionCodeId, date);
	}

	private DepartmentSectionCodeAssignmentsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private DepartmentSectionCodeAssignmentsMap retrieveMap()
	{
		final List<DepartmentSectionCodeAssignment> list = queryBL.createQueryBuilder(I_M_Department_SectionCode.class)
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(DepartmentSectionCodeAssignmentRepository::fromRecord)
				.collect(Collectors.toList());

		return new DepartmentSectionCodeAssignmentsMap(list);
	}

	private static DepartmentSectionCodeAssignment fromRecord(final I_M_Department_SectionCode record)
	{
		return DepartmentSectionCodeAssignment.builder()
				.departmentId(DepartmentId.ofRepoId(record.getM_Department_ID()))
				.sectionCodeId(SectionCodeId.ofRepoId(record.getM_SectionCode_ID()))
				.validFrom(record.getValidFrom().toInstant())
				.validTo(TimeUtil.asInstant(record.getValidTo()))
				.build();
	}
}
