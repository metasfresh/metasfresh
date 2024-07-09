package de.metas.ad_reference;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AdRefListRepositoryMocked implements AdRefListRepository
{
	private boolean autoCreateOnDemand = false;
	private final HashMap<ReferenceId, ADRefList> map = new HashMap<>();

	private final AtomicInteger nextAD_Ref_List_ID = new AtomicInteger(601);

	public void setAutoCreateOnDemand(final boolean autoCreateOnDemand)
	{
		this.autoCreateOnDemand = autoCreateOnDemand;
	}

	public void put(@NonNull final ADRefList adRefList)
	{
		map.put(adRefList.getReferenceId(), adRefList);
	}

	@Override
	public void createRefListItem(@NonNull final ADRefListItemCreateRequest request)
	{
		final ADRefList refList = getById(request.getReferenceId());

		final ADRefList refListChanged = refList.toBuilder()
				.items(ImmutableList.<ADRefListItem>builder()
						.addAll(refList.getItems())
						.add(ADRefListItem.builder()
								.referenceId(request.getReferenceId())
								.refListId(ADRefListId.ofRepoId(nextAD_Ref_List_ID.getAndIncrement()))
								.value(request.getValue())
								.valueName(request.getValue())
								.name(request.getName())
								.build())
						.build())
				.build();

		map.put(refListChanged.getReferenceId(), refListChanged);
	}

	@Override
	public ADRefList getById(@NonNull final ReferenceId adReferenceId)
	{
		final ADRefList adRefList = autoCreateOnDemand
				? map.computeIfAbsent(adReferenceId, AdRefListRepositoryMocked::newDummyADRefList)
				: map.get(adReferenceId);
		if (adRefList == null)
		{
			throw new AdempiereException("No List Reference found for " + adReferenceId);
		}
		return adRefList;
	}

	private static ADRefList newDummyADRefList(@NonNull final ReferenceId referenceId)
	{
		return ADRefList.builder()
				.referenceId(referenceId)
				.name("Auto generated for " + referenceId)
				.items(ImmutableList.of())
				.build();
	}

}
