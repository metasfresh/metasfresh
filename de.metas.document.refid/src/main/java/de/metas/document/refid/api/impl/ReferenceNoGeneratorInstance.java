package de.metas.document.refid.api.impl;

import lombok.NonNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.util.Check;

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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignedTableIds == null) ? 0 : assignedTableIds.hashCode());
		result = prime * result + ((generator == null) ? 0 : generator.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		ReferenceNoGeneratorInstance other = (ReferenceNoGeneratorInstance)obj;
		if (assignedTableIds == null)
		{
			if (other.assignedTableIds != null)
			{
				return false;
			}
		}
		else if (!assignedTableIds.equals(other.assignedTableIds))
		{
			return false;
		}
		if (generator == null)
		{
			if (other.generator != null)
			{
				return false;
			}
		}
		else if (!generator.equals(other.generator))
		{
			return false;
		}
		if (type == null)
		{
			if (other.type != null)
			{
				return false;
			}
		}
		else if (!type.equals(other.type))
		{
			return false;
		}
		return true;
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
