package de.metas.resource;

import de.metas.cache.CCache;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_PP_Workstation_UserAssign;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserWorkstationAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserId, Optional<UserWorkstationAssignment>> cacheByUserId = CCache.<UserId, Optional<UserWorkstationAssignment>>builder()
			.tableName(I_PP_Workstation_UserAssign.Table_Name)
			.build();

	public Optional<UserWorkstationAssignment> getByUserId(@NonNull final UserId userId)
	{
		return cacheByUserId.getOrLoad(userId, this::retrieveByUserId);
	}

	private Optional<UserWorkstationAssignment> retrieveByUserId(@NonNull final UserId userId)
	{
		return retrieveRecordByUserId(userId).map(UserWorkstationAssignmentRepository::fromRecord);
	}

	private Optional<I_PP_Workstation_UserAssign> retrieveRecordByUserId(@NonNull final UserId userId)
	{
		return queryBL.createQueryBuilder(I_PP_Workstation_UserAssign.class)
				.addEqualsFilter(I_PP_Workstation_UserAssign.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_PP_Workstation_UserAssign.class);
	}

	private static UserWorkstationAssignment fromRecord(final I_PP_Workstation_UserAssign record)
	{
		return UserWorkstationAssignment.builder()
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.workstationId(ResourceId.ofRepoId(record.getWorkStation_ID()))
				.active(record.isActive())
				.build();
	}

	public void assign(@NonNull final UserId userId, @NonNull final ResourceId workstationId)
	{
		final I_PP_Workstation_UserAssign record = retrieveRecordByUserId(userId)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_PP_Workstation_UserAssign.class));

		record.setAD_User_ID(userId.getRepoId());
		record.setWorkStation_ID(workstationId.getRepoId());
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);
	}
}
