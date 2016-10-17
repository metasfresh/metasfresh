package de.metas.dlm;

import java.util.Collection;

import de.metas.dlm.model.IDLMAware;

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
 * A partition is a set of {@link IDLMAware} records that can be DLM'ed. Each an {@link IDLMAware} is only part of one partition.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Partition
{
	public Collection<IDLMAware> getRecords()
	{
		// TODO
		return null;
	}
}
