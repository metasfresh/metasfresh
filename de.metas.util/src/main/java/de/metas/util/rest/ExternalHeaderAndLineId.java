package de.metas.util.rest;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Value;

@Value
public class ExternalHeaderAndLineId {

	private List<ExternalId> externalLineIds;
	private String externalHeaderId;

	@Builder(toBuilder = true)
	private ExternalHeaderAndLineId(List<ExternalId> externalLineIds,String externalHeaderId)
	{
		this.externalLineIds = ImmutableList.copyOf(externalLineIds);
		this.externalHeaderId = externalHeaderId;
	}
}
