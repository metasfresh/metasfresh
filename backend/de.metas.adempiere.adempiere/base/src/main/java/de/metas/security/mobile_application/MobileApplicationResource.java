package de.metas.security.mobile_application;

import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.permissions.Resource;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class MobileApplicationResource implements Resource
{
	@NonNull MobileApplicationRepoId mobileApplicationId;

	public static MobileApplicationResource cast(@NonNull final Resource resource)
	{
		return (MobileApplicationResource)resource;
	}
}
