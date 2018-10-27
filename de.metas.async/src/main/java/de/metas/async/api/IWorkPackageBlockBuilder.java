package de.metas.async.api;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import de.metas.async.model.I_C_Queue_Block;
import de.metas.process.PInstanceId;

/**
 * Build and enqueue a new queue block ({@link I_C_Queue_Block}).
 * 
 * @author tsa
 *
 */
public interface IWorkPackageBlockBuilder
{
	/**
	 * Builds and enqueue the {@link I_C_Queue_Block}.
	 * 
	 * @return created {@link I_C_Queue_Block}; never return <code>null</code>
	 */
	I_C_Queue_Block build();

	IWorkPackageBlockBuilder setContext(final Properties ctx);

	IWorkPackageBlockBuilder setAD_PInstance_Creator_ID(final PInstanceId adPInstanceId);

	/**
	 * Start creating a new workpackage.
	 * 
	 * NOTE: the {@link IWorkPackageBuilder} will trigger the creation of {@link I_C_Queue_Block}.
	 */
	IWorkPackageBuilder newWorkpackage();

}
