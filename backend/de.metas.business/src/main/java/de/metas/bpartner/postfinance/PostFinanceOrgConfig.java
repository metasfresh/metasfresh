/*
 * #%L
<<<<<<<< HEAD:backend/de.metas.postfinance/src/main/java/de/metas/postfinance/async/PostFinanceUploadInvoiceWorkpackageProcessor.java
 * de.metas.postfinance
========
 * de.metas.business
>>>>>>>> inner_silence_uat_gh17320:backend/de.metas.business/src/main/java/de/metas/bpartner/postfinance/PostFinanceOrgConfig.java
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public class PostFinanceUploadInvoiceWorkpackageProcessor implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String localTrxName)
	{
		return null;
	}
========
package de.metas.bpartner.postfinance;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PostFinanceOrgConfig
{
	@NonNull PostFinanceOrgConfigId id;

	@NonNull String billerId;
	
	boolean isArchiveData;
>>>>>>>> inner_silence_uat_gh17320:backend/de.metas.business/src/main/java/de/metas/bpartner/postfinance/PostFinanceOrgConfig.java
}
