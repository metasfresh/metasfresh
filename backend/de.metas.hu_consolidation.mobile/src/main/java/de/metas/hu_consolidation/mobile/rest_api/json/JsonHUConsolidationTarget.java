package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.grai.HUGraiSnapshot;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUConsolidationTarget
{
	@Nullable String id;
	@Nullable String caption;
	@Nullable HuPackingInstructionsId luPIId;
	@Nullable HuId luId;
	@Nullable String luQRCode;
	boolean printable;

	/** Number of TU slots on this LU that need a GRAI (0 when not yet materialised as an existing LU). */
	int graiExpectedCount;
	/** Number of GRAIs already assigned to TU slots on this LU. */
	int graiAssignedCount;

	@Nullable
	@Contract("!null -> !null")
	public static JsonHUConsolidationTarget ofNullable(@Nullable final HUConsolidationTarget target)
	{
		return target != null ? of(target, null) : null;
	}

	@NonNull
	public static JsonHUConsolidationTarget of(@NonNull final HUConsolidationTarget target)
	{
		return of(target, null);
	}

	/**
	 * @param graiSnapshot snapshot of the target LU's GRAI state; may be {@code null} when
	 *                     the target is a new (not-yet-materialised) LU or GRAI is not enabled.
	 */
	@NonNull
	public static JsonHUConsolidationTarget of(
			@NonNull final HUConsolidationTarget target,
			@Nullable final HUGraiSnapshot graiSnapshot)
	{
		return builder()
				.id(target.getId())
				.caption(target.getCaption())
				.luPIId(target.getLuPIId())
				.luId(target.getLuId())
				.luQRCode(target.getLuQRCode() != null ? target.getLuQRCode().toGlobalQRCodeString() : null)
				.printable(target.isPrintable())
				.graiExpectedCount(graiSnapshot != null ? graiSnapshot.getTUCount().toInt() : 0)
				.graiAssignedCount(graiSnapshot != null ? graiSnapshot.getAllGrais().size() : 0)
				.build();
	}
}
