package de.metas.inventory.mobileui.job;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.inventory.InventoryId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_Inventory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static de.metas.inventory.mobileui.InventoryMobileApplication.APPLICATION_ID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class InventoryJobId implements RepoIdAware
{
	int repoId;

	private InventoryJobId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_Inventory.COLUMNNAME_M_Inventory_ID);
	}

	public static InventoryJobId ofRepoId(final int repoId) {return new InventoryJobId(repoId);}

	@Nullable
	public static InventoryJobId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new InventoryJobId(repoId) : null;}

	public static Optional<InventoryJobId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	@JsonCreator
	public static InventoryJobId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, InventoryJobId.class, InventoryJobId::ofRepoId);}

	public static int toRepoId(@Nullable final InventoryJobId id) {return id != null ? id.getRepoId() : -1;}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final InventoryJobId o1, @Nullable final InventoryJobId o2) {return Objects.equals(o1, o2);}

	public WFProcessId toWFProcessId() {return WFProcessId.ofIdPart(APPLICATION_ID, this);}

	public static InventoryJobId ofWFProcessId(@NonNull final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoIdAssumingApplicationId(APPLICATION_ID, InventoryJobId::ofRepoId);
	}

	public InventoryId toInventoryId() {return InventoryId.ofRepoId(this.repoId);}

	public static InventoryJobId ofInventoryId(final InventoryId inventoryId) {return ofRepoId(inventoryId.getRepoId());}
}
