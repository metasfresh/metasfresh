package de.metas.department;

import de.metas.sectionCode.SectionCodeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class DepartmentSectionCodeAssignment
{
	@NonNull DepartmentId departmentId;
	@NonNull SectionCodeId sectionCodeId;
	@NonNull Instant validFrom;
	@Nullable Instant validTo;

	public boolean isMatching(@NonNull final Instant date)
	{
		return validFrom.compareTo(date) <= 0
				&& (validTo == null || validTo.compareTo(date) >= 0);
	}
}
