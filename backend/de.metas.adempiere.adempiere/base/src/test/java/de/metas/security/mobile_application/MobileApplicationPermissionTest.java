package de.metas.security.mobile_application;

import com.google.common.collect.ImmutableSet;
import de.metas.mobile.application.MobileApplicationAction;
import de.metas.mobile.application.MobileApplicationActionId;
import de.metas.mobile.application.MobileApplicationRepoId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Who gets an action: allow-all roles only get the actions that are included in "allow all actions"
 * ({@code Mobile_Application_Action.IsIncludedInAllowAllActions}); an explicit grant always works; no access means no action.
 */
class MobileApplicationPermissionTest
{
	private static final MobileApplicationRepoId APPLICATION_ID = MobileApplicationRepoId.ofRepoId(540005);
	private static final MobileApplicationActionId ACTION_ID = MobileApplicationActionId.ofRepoId(540008);

	private static MobileApplicationAction action(final boolean includedInAllowAllActions)
	{
		return MobileApplicationAction.builder()
				.id(ACTION_ID)
				.internalName("someAction")
				.includedInAllowAllActions(includedInAllowAllActions)
				.build();
	}

	private static MobileApplicationPermission permission(final boolean allowAccess, final boolean allowAllActions, final boolean granted)
	{
		return MobileApplicationPermission.builder()
				.mobileApplicationId(APPLICATION_ID)
				.allowAccess(allowAccess)
				.allowAllActions(allowAllActions)
				.allowedActionIds(granted ? ImmutableSet.of(ACTION_ID) : ImmutableSet.of())
				.build();
	}

	@Test
	void allowAll_actionIncludedInAllowAll_noGrant_isAllowed()
	{
		assertThat(permission(true, true, false).isAllowAction(action(true))).isTrue();
	}

	@Test
	void allowAll_actionNotIncludedInAllowAll_noGrant_isRefused()
	{
		assertThat(permission(true, true, false).isAllowAction(action(false))).isFalse();
	}

	@Test
	void allowAll_actionNotIncludedInAllowAll_withGrant_isAllowed()
	{
		assertThat(permission(true, true, true).isAllowAction(action(false))).isTrue();
	}

	@Test
	void noAllowAll_withGrant_isAllowed()
	{
		assertThat(permission(true, false, true).isAllowAction(action(false))).isTrue();
	}

	@Test
	void noAllowAll_noGrant_isRefused()
	{
		assertThat(permission(true, false, false).isAllowAction(action(true))).isFalse();
	}

	@Test
	void noAccess_isRefused_evenWithAllowAllAndGrant()
	{
		assertThat(permission(false, true, true).isAllowAction(action(true))).isFalse();
	}
}
