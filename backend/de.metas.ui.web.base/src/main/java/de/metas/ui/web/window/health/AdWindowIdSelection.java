package de.metas.ui.web.window.health;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedHashSet;

class AdWindowIdSelection implements Iterable<AdWindowId>
{
	private static final ImmutableSet<AdWindowId> SKIP_WINDOW_IDS = ImmutableSet.of(
			AdWindowId.ofRepoId(540371), // Picking Tray Clearing - placeholder window
			AdWindowId.ofRepoId(540674), // Shipment Schedule Editor - placeholder window
			AdWindowId.ofRepoId(540759), // Payment Allocation - placeholder window
			AdWindowId.ofRepoId(540485) // Picking Terminal (v2) - placeholder window
	);

	@NonNull private final ImmutableSet<AdWindowId> allAdWidowIds;
	//@NonNull private final ImmutableSet<AdWindowId> onlyAdWindowIds;
	@NonNull private final ImmutableSet<AdWindowId> selectedWindowIds;

	@Builder
	private AdWindowIdSelection(
			@NonNull final ImmutableSet<AdWindowId> allAdWidowIds,
			@NonNull final ImmutableSet<AdWindowId> onlyAdWindowIds)
	{

		this.allAdWidowIds = allAdWidowIds;
		//this.onlyAdWindowIds = onlyAdWindowIds;

		final LinkedHashSet<AdWindowId> selectedWindowIds = new LinkedHashSet<>(onlyAdWindowIds.isEmpty() ? allAdWidowIds : onlyAdWindowIds);
		selectedWindowIds.removeAll(SKIP_WINDOW_IDS);

		this.selectedWindowIds = ImmutableSet.copyOf(selectedWindowIds);
	}

	public int size()
	{
		return selectedWindowIds.size();
	}

	@Override
	public @NotNull Iterator<AdWindowId> iterator()
	{
		return selectedWindowIds.iterator();
	}

	public boolean contains(@NonNull final AdWindowId adWindowId)
	{
		return selectedWindowIds.contains(adWindowId);
	}

	public boolean contains(final ContextPath path)
	{
		return contains(path.getAdWindowId());
	}

	public boolean isExistingActiveWindow(@NonNull final AdWindowId adWindowId)
	{
		return allAdWidowIds.contains(adWindowId);
	}

}
