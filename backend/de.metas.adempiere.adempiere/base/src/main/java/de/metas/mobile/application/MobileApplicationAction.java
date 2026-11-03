package de.metas.mobile.application;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MobileApplicationAction
{
	@NonNull MobileApplicationActionId id;
	@NonNull String internalName;

	/**
	 * {@code Mobile_Application_Action.IsIncludedInAllowAllActions}: whether a role that is allowed all actions of the application gets this action.
	 * If false, only roles with an explicit action grant get it.
	 */
	boolean includedInAllowAllActions;
}
