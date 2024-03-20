package de.metas.document.refid.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;

@EqualsAndHashCode
class ReferenceNoGeneratorInstance implements IReferenceNoGeneratorInstance
{
	private final I_C_ReferenceNo_Type type;
	private final List<Integer> assignedTableIds;
	private final IReferenceNoGenerator generator;

	public ReferenceNoGeneratorInstance(
			@NonNull final I_C_ReferenceNo_Type type,
			@NonNull final List<Integer> assignedTableIds,
			@NonNull final IReferenceNoGenerator generator)
	{
		Check.assume(!assignedTableIds.isEmpty(), "assignedTableIds not null and not empty");

		this.type = type;
		this.assignedTableIds = ImmutableList.copyOf(assignedTableIds);
		this.generator = generator;
	}

	@Override
	public String generateReferenceNo(@NonNull final Object sourceModel)
	{
		return generator.generateReferenceNo(sourceModel);
	}

	@Override
	public I_C_ReferenceNo_Type getType()
	{
		return type;
	}

	@Override
	public IReferenceNoGenerator getGenerator()
	{
		return generator;
	}

	@Override
	public List<Integer> getAssignedTableIds()
	{
		return assignedTableIds;
	}
	
	@Override
	public String toString()
	{
		return "ReferenceNoGeneratorInstance ["
				+ "type=" + (type == null ? null : type.getName())
				+ ", assignedTableIds=" + assignedTableIds
				+ ", generator=" + generator
				+ "]";
	}
}
