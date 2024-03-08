package de.metas.resource;

import de.metas.product.ResourceId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWorkstationService
{
	@NonNull private final UserWorkstationAssignmentRepository userWorkstationAssignmentRepository;

	public Optional<ResourceId> getUserWorkstationId(@NonNull final UserId userId)
	{
		return userWorkstationAssignmentRepository.getByUserId(userId)
				.filter(UserWorkstationAssignment::isActive)
				.map(UserWorkstationAssignment::getWorkstationId);
	}

	public void assign(@NonNull final UserId userId, @NonNull final ResourceId workstationId)
	{
		userWorkstationAssignmentRepository.assign(userId, workstationId);
	}

	public boolean isUserAssigned(@NonNull final UserId userId, @NonNull final ResourceId workstationId)
	{
		final ResourceId assignedWorkstationId = getUserWorkstationId(userId).orElse(null);
		return ResourceId.equals(assignedWorkstationId, workstationId);
	}
}
