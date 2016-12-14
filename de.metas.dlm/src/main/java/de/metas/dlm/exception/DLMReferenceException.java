package de.metas.dlm.exception;

import org.adempiere.exceptions.DBException;

import com.google.common.annotations.VisibleForTesting;

import de.metas.dlm.partitioner.config.TableReferenceDescriptor;

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
 * Instances of this exceptions should be created by {@link DBException#wrapIfNeeded(Throwable)} via {@link DLMReferenceExceptionWrapper}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLMReferenceException extends DBException
{
	private static final long serialVersionUID = -4557251479983766242L;

	private final boolean referencingTableHasDLMLevel;
	private final TableReferenceDescriptor tableReferenceDescriptor;

	@VisibleForTesting
	public DLMReferenceException(final Throwable cause,
			final TableReferenceDescriptor tableReferenceDescriptor,
			final boolean referencingTableHasDLMLevel)
	{
		super("Another record references the given record", cause);

		this.tableReferenceDescriptor = tableReferenceDescriptor;
		this.referencingTableHasDLMLevel = referencingTableHasDLMLevel;
	}

	public boolean isReferencingTableHasDLMLevel()
	{
		return referencingTableHasDLMLevel;
	}

	public TableReferenceDescriptor getTableReferenceDescriptor()
	{
		return tableReferenceDescriptor;
	}

	@Override
	public String toString()
	{
		return "DLMException [tableReferenceDescriptor=" + tableReferenceDescriptor + ", referencingTableHasDLMLevel=" + referencingTableHasDLMLevel + "]";
	}
}
