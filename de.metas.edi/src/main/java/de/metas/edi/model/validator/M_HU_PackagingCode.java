package de.metas.edi.model.validator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.model.I_M_HU_PackagingCode;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_HU_PackagingCode.class)
@Component
public class M_HU_PackagingCode
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_M_HU_PackagingCode.COLUMNNAME_PackagingCode)
	public void updateDesadvLines(@NonNull final I_M_HU_PackagingCode packagingCodeRecord)
	{
		final int packagingCodeId = packagingCodeRecord.getM_HU_PackagingCode_ID();
		final String packagingCodeString = packagingCodeRecord.getPackagingCode();

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> updateDesadvLinesNow(packagingCodeId, packagingCodeString));
	}

	private void updateDesadvLinesNow(final int packagingCodeId, final String packagingCodeString)
	{
		queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_M_HU_PackagingCode_LU_ID, packagingCodeId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_EDI_DesadvLine.COLUMNNAME_M_HU_PackagingCode_LU_Text, packagingCodeString)
				.execute();

		queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_M_HU_PackagingCode_TU_ID, packagingCodeId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_EDI_DesadvLine.COLUMNNAME_M_HU_PackagingCode_TU_Text, packagingCodeString)
				.execute();
	}
}
