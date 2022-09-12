package de.metas.ad_reference;

import de.metas.i18n.ExplainedOptional;

import javax.annotation.Nullable;

public interface AdRefTableRepository
{
	ExplainedOptional<ADRefTable> getById(ReferenceId referenceId);

	boolean hasTableReference(@Nullable ReferenceId referenceId);
}
