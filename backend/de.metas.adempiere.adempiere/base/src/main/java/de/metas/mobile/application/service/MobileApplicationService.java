package de.metas.mobile.application.service;

import de.metas.logging.LogManager;
import de.metas.mobile.application.MobileApplication;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.mobile.application.MobileApplicationsMap;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import de.metas.security.IUserRolePermissions;
import de.metas.security.mobile_application.MobileApplicationPermissions;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MobileApplicationService
{
	@NonNull private static final Logger logger = LogManager.getLogger(MobileApplicationService.class);
	@NonNull private final MobileApplicationInfoRepository applicationInfoRepository;
	@NonNull private final MobileApplicationsMap applications;

	public MobileApplicationService(
			@NonNull final MobileApplicationInfoRepository applicationInfoRepository,
			@NonNull final Optional<List<MobileApplication>> applications
	)
	{
		this.applicationInfoRepository = applicationInfoRepository;
		this.applications = MobileApplicationsMap.of(applications);
		logger.info("applications: {}", this.applications);
	}

	public void assertAccess(@NonNull final MobileApplicationId applicationId, @NonNull final MobileApplicationPermissions permissions)
	{
		if (!hasAccess(applicationId, permissions))
		{
			throw new AdempiereException("Access denied");
		}
	}

	public void assertActionAccess(@NonNull final MobileApplicationId applicationId, @NonNull String actionInternalName, @NonNull final MobileApplicationPermissions permissions)
	{
		if (!hasAccess(applicationId, permissions))
		{
			throw new AdempiereException("Access denied");
		}
	}

	public Stream<MobileApplicationInfo> streamMobileApplicationInfos(final IUserRolePermissions permissions)
	{
		return applicationInfoRepository.getAllActive()
				.stream()
				.filter(applicationInfo -> hasAccess(applicationInfo.getRepoId(), permissions.getMobileApplicationPermissions()))
				.map(applicationInfo -> {
					try
					{
						final MobileApplication application = applications.getById(applicationInfo.getId());
						return application.customizeApplicationInfo(applicationInfo, permissions.getUserId());
					}
					catch (Exception ex)
					{
						logger.warn("Failed getting application from {}. Skipped", applicationInfo, ex);
						return null;
					}
				})
				.filter(Objects::nonNull);
	}

	public <T extends MobileApplication> Stream<T> streamMobileApplicationsOfType(final Class<T> type, final IUserRolePermissions permissions)
	{
		return streamMobileApplicationInfos(permissions)
				.map(applicationInfo -> {
					final MobileApplication application = applications.getById(applicationInfo.getId());
					return type.isInstance(application) ? type.cast(application) : null;
				})
				.filter(Objects::nonNull);
	}

	public MobileApplication getById(@NonNull final MobileApplicationId id)
	{
		return applications.getById(id);
	}

	public void logout(@NonNull final IUserRolePermissions permissions)
	{
		final UserId userId = permissions.getUserId();
		final MobileApplicationPermissions mobileApplicationPermissions = permissions.getMobileApplicationPermissions();

		applications.stream()
				.filter(application -> hasAccess(application.getApplicationId(), mobileApplicationPermissions))
				.forEach(application -> {
					try
					{
						application.logout(userId);
					}
					catch (Exception ex)
					{
						logger.warn("Application {} failed to logout. Skipped", application, ex);
					}
				});
	}

	private boolean hasAccess(@NonNull final MobileApplicationId applicationId, @NonNull final MobileApplicationPermissions permissions)
	{
		final MobileApplicationRepoId repoId = applicationInfoRepository.getById(applicationId).getRepoId();
		return hasAccess(repoId, permissions);
	}

	private static boolean hasAccess(final MobileApplicationRepoId repoId, final MobileApplicationPermissions permissions)
	{
		return permissions.isAllowAccess(repoId);
	}

	private boolean hasAccess(@NonNull final MobileApplicationId applicationId, @NonNull String actionInternalName, final MobileApplicationPermissions permissions)
	{
		final MobileApplicationInfo applicationInfo = applicationInfoRepository.getById(applicationId);
		final MobileApplicationActionId actionId = applicationInfo.getActionIdByInternalName(actionInternalName).orElse(null);
		if (actionId == null)
		{
			return false;
		}

		return permissions.isAllowAction(applicationInfo.getRepoId(), actionId);
	}

}