package de.metas.manufacturing.job.service.commands.issue_what_was_received;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Value
class SourceHUsCollection
{
	@NonNull ImmutableList<I_M_HU> husThatAreFlaggedAsSource;
	@NonNull ImmutableMap<HuId, I_M_Source_HU> huId2SourceHu;

	@Builder
	private SourceHUsCollection(
			@NonNull final List<I_M_HU> husThatAreFlaggedAsSource,
			@NonNull final Collection<I_M_Source_HU> sourceHUs)
	{
		this.husThatAreFlaggedAsSource = ImmutableList.copyOf(husThatAreFlaggedAsSource);
		this.huId2SourceHu = Maps.uniqueIndex(sourceHUs, sourceHU -> HuId.ofRepoId(sourceHU.getM_HU_ID()));
	}

	public boolean isEmpty() {return husThatAreFlaggedAsSource.isEmpty();}

	@NonNull
	public Optional<I_M_Source_HU> getSourceHU(@NonNull final HuId huId)
	{
		return Optional.ofNullable(huId2SourceHu.get(huId));
	}
}
