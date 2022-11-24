package de.metas.async.spi;

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

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageSkipRequest;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Implementation responsible for processing given workPackage.
 * 
 * NOTE: don't extend this interface directly, but use {@link WorkpackageProcessorAdapter}.
 */
public interface IWorkpackageProcessor
{
	String PARAMETERNAME_ElementsLockOwner = "ElementsLockOwner";

	/**
	 * The possible results of an {@link IWorkpackageProcessor#processWorkPackage(I_C_Queue_WorkPackage, String)}  invocation.
	 * 
	 * 
	 */
	enum Result
	{
		/**
		 * The processor should return this to indicate that the package was successfully processed and can therefore be marked as <code>Processed</code> in the DB.
		 */
		SUCCESS,
	}

	/**
	 * Actual WorkPackage processing. Hint: Most implementors will probably start by calling {@link IQueueDAO#retrieveItems(I_C_Queue_WorkPackage, Class, String)}.
	 * 
	 * NOTE: never call this method directly, it will be called by API.
	 * 
	 * @param workpackage the package to be processed. Note that this package's context contains the <code>AD_Client_ID</code>, <code>AD_Org_ID</code> and <code>AD_User_ID</code> from the context in
	 *            which the package's <code>C_Queue_Block</code> record was created.
	 * @param localTrxName transaction to be used while processing
	 * @return the processing's outcome
	 * @see Result
	 * 
	 * @throws IWorkpackageSkipRequest if we want to skip this workpackage. The request will contain more infos.
	 */
	Result processWorkPackage(@NonNull I_C_Queue_WorkPackage workpackage, @Nullable String localTrxName);
}
