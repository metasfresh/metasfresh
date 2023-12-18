package de.metas.ad_reference;

import lombok.NonNull;

interface AdRefListRepository
{
	void createRefListItem(@NonNull ADRefListItemCreateRequest request);

	ADRefList getById(ReferenceId adReferenceId);
}
