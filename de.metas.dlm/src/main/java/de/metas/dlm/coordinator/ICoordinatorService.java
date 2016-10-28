package de.metas.dlm.coordinator;

import org.adempiere.util.ISingletonService;

import de.metas.dlm.Partition;

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

public interface ICoordinatorService extends ISingletonService
{
	void registerInspector(IRecordInspector recordInspector);

	/**
	 * Use the registered {@link IRecordInspector}s on the given <code>partition</code> to decide
	 * <li>which DLM level the partition shall be moved to (i.e. the <i>target DLM level</i>) and
	 * <li>when the partition shall be reevaluated.
	 *
	 * The target DLM level is computed as the minimum
	 *
	 * @param partition
	 */
	void validatePartition(Partition partition);
}
