package de.metas.dlm.partitioner.config;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.dlm.model.I_DLM_Partition_Config_Reference;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionerConfigLine.LineBuilder;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Each instance of this class belongs to one {@link PartitionerConfigLine} (parent) and specifies one column of its parent's {@link PartitionerConfigLine#getTableName()}.
 * <p>
 * E.g. if the parent's table name is <code>C_Invoice</code>, then this instance might be about
 * <li>{@link #getReferencingColumnName()} <code>=_C_Order_ID</code></li>
 * <li>{@link #getReferencedTableName()} <code>= C_Order</code></li>
 * Meaning that this instance, together with its parents tells the engine that <code>C_Invoice</code> records can reference <code>C_Order</code> records via the foreign key column <code>C_Invoice.C_Order_ID</code>.
 *
 * Further, if {@link #getReferencedConfigLine()} is not <code>null</code>, it means that <code>C_Order</code> is also a DLM'ed table and might have its own list of config references.
 * <p>
 * Almost immutable; only the {@link #getDLM_Partition_Config_Reference_ID()} property is mutable.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerConfigReference
{
	private final String referencedTableName;
	private final String referencingColumnName;
	private final PartitionerConfigLine parent;
	private final boolean partitionBoundary;

	private int DLM_Partition_Config_Reference_ID;

	private PartitionerConfigReference(final PartitionerConfigLine parent,
			final String referencingColumnName,
			final String referencedTableName,
			final boolean partitionBoundary)
	{
		this.parent = parent;
		this.referencingColumnName = referencingColumnName;
		this.referencedTableName = referencedTableName;
		this.partitionBoundary = partitionBoundary;
	}

	public String getReferencedTableName()
	{
		return referencedTableName;
	}

	public String getReferencingColumnName()
	{
		return referencingColumnName;
	}

	public boolean isPartitionBoundary()
	{
		return partitionBoundary;
	}

	public PartitionerConfigLine getParent()
	{
		return parent;
	}

	@Override
	public String toString()
	{
		return "PartitionConfigReference [referencingColumnName=" + referencingColumnName + ", referencedTableName=" + referencedTableName + ", partitionBoundary=" + partitionBoundary + "]";
	}

	/**
	 * See {@link PartitionerConfigReference.RefBuilder#setDLM_Partition_Config_Reference_ID(int)} and {@link #getDLM_Partition_Config_Reference_ID()}.
	 *
	 * @return
	 */
	public int getDLM_Partition_Config_Reference_ID()
	{
		return DLM_Partition_Config_Reference_ID;
	}

	/**
	 * To be invoked by {@link IPartitionerService#storePartitionConfig(PartitionerConfig)} after a {@link I_DLM_Partition_Config_Reference}
	 * record was created or updated for this instance.
	 *
	 * @param DLM_Partition_Config_Reference_ID
	 */
	public void setDLM_Partition_Config_Reference_ID(final int DLM_Partition_Config_Reference_ID)
	{
		this.DLM_Partition_Config_Reference_ID = DLM_Partition_Config_Reference_ID;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (!(other instanceof PartitionerConfigReference))
		{
			return false;
		}

		final PartitionerConfigReference otherRef = (PartitionerConfigReference)other;

		return new EqualsBuilder()
				.append(parent.getTableName(), otherRef.parent.getTableName())
				.append(referencedTableName, otherRef.referencedTableName)
				.append(referencingColumnName, otherRef.referencingColumnName)
				.isEqual();
	}

	public static class RefBuilder
	{
		private String referencedTableName = "";
		private String referencingColumnName = "";

		private final PartitionerConfigLine.LineBuilder parentbuilder;

		private int DLM_Partition_Config_Reference_ID;
		private boolean partitionBoundary;

		RefBuilder(final PartitionerConfigLine.LineBuilder parentBuilder)
		{
			parentbuilder = parentBuilder;
		}

		public RefBuilder setReferencedTableName(final String referencedTableName)
		{
			this.referencedTableName = referencedTableName;
			return this;
		}

		public RefBuilder setReferencingColumnName(final String referencingColumnName)
		{
			this.referencingColumnName = referencingColumnName;
			return this;
		}

		/**
		 * Sets the primary key of the {@link I_DLM_Partition_Config_Reference} records that already exists for the {@link PartitionerConfigReference} instances that we want to build.
		 * This information is important when a config is persisted because it determines if a {@link I_DLM_Partition_Config_Reference} record shall be loaded and updated rather than inserted.
		 *
		 * @param dlm_Partition_Config_Reference_ID
		 * @return
		 */
		public RefBuilder setDLM_Partition_Config_Reference_ID(final int dlm_Partition_Config_Reference_ID)
		{
			DLM_Partition_Config_Reference_ID = dlm_Partition_Config_Reference_ID;
			return this;
		}

		/**
		 * We need this getter because in {@link PartitionerConfigLine.LineBuilder#endRef()},
		 * we need to discard duplicated {@link RefBuilder}s.
		 * But at the same time, if one of the duplicates already has a <code>DLM_Partition_Config_Reference_ID</code>, then we need to pick that one.
		 *
		 * @return
		 */
		public int getDLM_Partition_Config_Reference_ID()
		{
			return DLM_Partition_Config_Reference_ID;
		}

		public RefBuilder setIsPartitionBoundary(boolean partitionBoundary)
		{
			this.partitionBoundary = partitionBoundary;
			return this;
		}

		public LineBuilder endRef()
		{
			return parentbuilder.endRef();
		}

		/**
		 * Convenience method to end the current ref builder and start a new one.
		 *
		 * @return the new reference builder, <b>not</b> this instance.
		 */
		public RefBuilder newRef()
		{
			return endRef().ref();
		}

		/**
		 * Supposed to be called only by the parent builder.
		 *
		 * @param parent
		 * @return
		 */
		/* package */ PartitionerConfigReference build(final PartitionerConfigLine parent)
		{
			final PartitionerConfigReference partitionerConfigReference = new PartitionerConfigReference(parent, referencingColumnName, referencedTableName, partitionBoundary);
			partitionerConfigReference.setDLM_Partition_Config_Reference_ID(DLM_Partition_Config_Reference_ID);
			return partitionerConfigReference;
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(referencedTableName)
					.append(referencingColumnName)
					.append(parentbuilder)
					.toHashcode();

		};

		/**
		 * Note: it's important not to include {@link #getDLM_Partition_Config_Reference_ID()} in the result.
		 * Check the implementation of {@link PartitionerConfigLine.LineBuilder#endRef()} for details.
		 */
		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			final RefBuilder other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}
			return new EqualsBuilder()
					.append(referencedTableName, other.referencedTableName)
					.append(referencingColumnName, other.referencingColumnName)
					.append(parentbuilder, other.parentbuilder)
					.isEqual();
		}

		@Override
		public String toString()
		{
			return "RefBuilder [referencingColumnName=" + referencingColumnName + ", referencedTableName=" + referencedTableName + ", partitionBoundary=" + partitionBoundary + ", DLM_Partition_Config_Reference_ID=" + DLM_Partition_Config_Reference_ID + "]";
		}
	}
}
