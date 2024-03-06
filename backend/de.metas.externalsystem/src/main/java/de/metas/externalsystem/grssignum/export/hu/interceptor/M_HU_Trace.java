/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.grssignum.export.hu.interceptor;

import de.metas.externalsystem.grssignum.ExportHUToGRSService;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_HU_Trace.class)
@Component
public class M_HU_Trace
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExportHUToGRSService exportHUToGRSService;

	public M_HU_Trace(@NonNull final ExportHUToGRSService exportHUToGRSService)
	{
		this.exportHUToGRSService = exportHUToGRSService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void enqueueHUToExport(final I_M_HU_Trace huTrace)
	{
		trxManager.runAfterCommit(() -> exportHUToGRSService.exportHUToGRS(huTrace));
	}
}
