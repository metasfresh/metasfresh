package de.metas.handlingunits.rest_api.grai;

import de.metas.common.handlingunits.JsonGRAICodesResponse;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class GetHUGraiCommand
{
	@NonNull private final HuId huId;

	@NonNull
	public JsonGRAICodesResponse execute()
	{
		final HUGraiSnapshot snapshot = HUGraiSnapshotLoader.builder().huId(huId).build().load();

		return JsonGRAICodesResponse.builder()
				.huId(huId.getRepoId())
				.graiCodes(snapshot.getAllGrais().toStringList())
				.tuCount(snapshot.getTUCount().toInt())
				.build();
	}
}
