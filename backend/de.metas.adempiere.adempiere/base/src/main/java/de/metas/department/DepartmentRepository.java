package de.metas.department;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class DepartmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, DepartmentsMap> cache = CCache.<Integer, DepartmentsMap>builder()
			.tableName(I_M_Department.Table_Name)
			.build();

	public Department getById(@NonNull final DepartmentId departmentId)
	{
		return getDepartmentsMap().getById(departmentId);
	}

	private DepartmentsMap getDepartmentsMap()
	{
		return cache.getOrLoad(0, this::retrieveDepartmentsMap);
	}

	private DepartmentsMap retrieveDepartmentsMap()
	{
		final ImmutableList<Department> departments = queryBL.createQueryBuilder(I_M_Department.class)
				.stream()
				.map(DepartmentRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new DepartmentsMap(departments);
	}

	private static Department fromRecord(final I_M_Department record)
	{
		return Department.builder()
				.departmentId(DepartmentId.ofRepoId(record.getM_Department_ID()))
				.code(record.getValue())
				.name(record.getName())
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.isActive(record.isActive())
				.build();
	}

	private static final class DepartmentsMap
	{
		private final ImmutableMap<DepartmentId, Department> byId;

		private DepartmentsMap(final List<Department> list)
		{
			this.byId = Maps.uniqueIndex(list, Department::getDepartmentId);
		}

		public Department getById(@NonNull final DepartmentId departmentId)
		{
			final Department department = byId.get(departmentId);
			if (department == null)
			{
				throw new AdempiereException("No department found for " + departmentId);
			}
			return department;
		}
	}
}
