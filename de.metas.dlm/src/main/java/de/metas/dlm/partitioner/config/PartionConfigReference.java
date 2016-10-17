package de.metas.dlm.partitioner.config;

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
 * <li>{@link #getReferencedTableName()} <code>=C_Order</code></li>
 * Meaning that this instance, together with its parents tells the engine that <code>C_Invoice</code> records can reference <code>C_Order</code> records via the foreign key column <code>C_Invoice.C_Order_ID</code>.
 *
 * Further, if {@link #getReferencedConfigLine()} is not <code>null</code>, it means that <code>C_Order</code> is also a DLM'ed table and might have its own list of config references.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartionConfigReference
{

	public String getReferencedTableName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getReferencingColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PartitionerConfigLine getReferencedConfigLine()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PartitionerConfigLine getParent()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
