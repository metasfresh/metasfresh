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


import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * Builds {@link I_C_Async_Batch}.
 * 
 * @author tsa
 *
 */
public interface IAsyncBatchBuilder
{
	/**
	 * Builds the {@link I_C_Async_Batch} and it also enqueue a workpackage to watch for it.
	 * 
	 * @return built {@link I_C_Async_Batch}
	 */
	I_C_Async_Batch build();

	IAsyncBatchBuilder setContext(Properties ctx);

	IAsyncBatchBuilder setC_Async_Batch_Type(final String internalName);

	IAsyncBatchBuilder setName(final String name);
	
	IAsyncBatchBuilder setCountExpected(final int expected);

	IAsyncBatchBuilder setDescription(final String description);

	IAsyncBatchBuilder setAD_PInstance_Creator_ID(PInstanceId adPInstanceId);
	
	IAsyncBatchBuilder setParentAsyncBatchId(@Nullable AsyncBatchId parentAsyncBatchId);

	 IAsyncBatchBuilder setOrgId(@Nullable final OrgId orgId);
}
