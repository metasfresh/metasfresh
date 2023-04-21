package de.metas.calendar.plan_optimizer.domain;

import com.google.common.collect.ImmutableList;
import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

@Value
public class Project
{
	@NonNull ProjectId id;
	@NonNull ImmutableList<Step> steps;
}
