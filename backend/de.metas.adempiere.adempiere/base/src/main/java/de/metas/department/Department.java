package de.metas.department;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class Department
{
	@NonNull DepartmentId departmentId;
	@NonNull String code;
	@NonNull String name;
	@Nullable String description;

	@Builder.Default boolean isActive = true;
}
