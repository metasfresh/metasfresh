/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalreference.interceptor;

import de.metas.cache.CacheMgt;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_ExternalReference.class)
@Component
public class S_ExternalReference
{
	final ITrxManager trxManager = Services.get(ITrxManager.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_S_ExternalReference.COLUMNNAME_IsReadOnlyInMetasfresh)
	public void cacheResetOnReadOnlyChange(@NonNull final I_S_ExternalReference externalReference)
	{
		final TableRecordReference referencedRecord = TableRecordReference.of(externalReference.getReferenced_AD_Table_ID(), externalReference.getRecord_ID());

		trxManager.runAfterCommit(() -> CacheMgt.get().reset(referencedRecord));
	}
}
