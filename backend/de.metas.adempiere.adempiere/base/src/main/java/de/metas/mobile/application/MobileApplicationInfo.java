package de.metas.mobile.application;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class MobileApplicationInfo
{
	@NonNull MobileApplicationRepoId repoId;
	@NonNull MobileApplicationId id;
	@NonNull ITranslatableString caption;
	@NonNull ImmutableList<MobileApplicationAction> actions;
	boolean requiresWorkstation;
	boolean requiresWorkplace;
	boolean showFilterByQRCode;
	boolean showFilters;
	boolean showFilterByDocumentNo;
	boolean showInMainMenu;
	int sortNo;
	@NonNull @Singular ImmutableMap<String, Object> applicationParameters;

	@NonNull ImmutableMap<MobileApplicationActionId, MobileApplicationAction> actionsById;

	@Builder(toBuilder = true)
	private MobileApplicationInfo(
			@NonNull final MobileApplicationRepoId repoId,
			@NonNull final MobileApplicationId id,
			@NonNull final ITranslatableString caption,
			@Nullable final List<MobileApplicationAction> actions,
			final boolean requiresWorkstation,
			final boolean requiresWorkplace,
			final boolean showFilterByQRCode,
			final boolean showFilters,
			final boolean showFilterByDocumentNo,
			@Nullable final Boolean showInMainMenu,
			@Nullable final Integer sortNo,
			@NonNull @Singular final ImmutableMap<String, Object> applicationParameters)
	{
		this.repoId = repoId;
		this.id = id;
		this.caption = caption;
		this.actions = actions != null && !actions.isEmpty() ? ImmutableList.copyOf(actions) : ImmutableList.of();
		this.requiresWorkstation = requiresWorkstation;
		this.requiresWorkplace = requiresWorkplace;
		this.showFilterByQRCode = showFilterByQRCode;
		this.showFilters = showFilters;
		this.showFilterByDocumentNo = showFilterByDocumentNo;
		this.showInMainMenu = showInMainMenu != null ? showInMainMenu : true;
		this.sortNo = sortNo != null ? sortNo : 100;
		this.applicationParameters = applicationParameters;

		this.actionsById = Maps.uniqueIndex(this.actions, MobileApplicationAction::getId);
	}
}
