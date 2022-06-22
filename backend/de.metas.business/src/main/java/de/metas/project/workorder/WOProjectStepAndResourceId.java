package de.metas.project.workorder;

import de.metas.project.ProjectId;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@Value(staticConstructor = "of")
public class WOProjectStepAndResourceId
{
	@NonNull ProjectId projectId;
	@NonNull WOProjectStepId stepId;
	@NonNull WOProjectResourceId projectResourceId;
}
