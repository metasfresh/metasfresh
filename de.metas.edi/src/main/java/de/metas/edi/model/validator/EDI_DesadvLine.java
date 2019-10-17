package de.metas.edi.model.validator;



/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.util.Check;
import de.metas.util.Services;

@Interceptor(I_EDI_DesadvLine.class)
@Component
public class EDI_DesadvLine
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDesadvLineDelete(final I_EDI_DesadvLine desadvLine)
	{
		final List<I_M_InOutLine> allInOutLines = Services.get(IDesadvDAO.class).retrieveAllInOutLines(desadvLine);
		for (final I_M_InOutLine inOutLine : allInOutLines)
		{
			inOutLine.setEDI_DesadvLine_ID(0);
			InterfaceWrapperHelper.save(inOutLine);
		}

		final List<I_C_OrderLine> allOrderLines = Services.get(IDesadvDAO.class).retrieveAllOrderLines(desadvLine);
		for (final I_C_OrderLine orderLine : allOrderLines)
		{
			orderLine.setEDI_DesadvLine_ID(0);
			InterfaceWrapperHelper.save(orderLine);
		}
	}

	/**
	 * Generate SSCC18 code only for manual and if no other code was generated.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = {
					I_EDI_DesadvLine.COLUMNNAME_IsManual_IPA_SSCC18,
					I_EDI_DesadvLine.COLUMNNAME_IPA_SSCC18,
					I_EDI_DesadvLine.COLUMNNAME_M_HU_ID,
					I_EDI_DesadvLine.COLUMNNAME_EDI_DesadvLine_ID })
	public void generateForIsManualIPASSCC18Changed(final I_EDI_DesadvLine desadvLine)
	{
		if (!desadvLine.isManual_IPA_SSCC18())
		{
			return; // only for manual
		}

		if (!Check.isEmpty(desadvLine.getIPA_SSCC18(), true))
		{
			return; // only for not-generated
		}

		//
		// Services
		final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(desadvLine);
		final int serialNumber = desadvLine.getM_HU_ID() > 0 ? desadvLine.getM_HU_ID() : desadvLine.getEDI_DesadvLine_ID();

		final SSCC18 sscc18 = sscc18CodeBL.generate(ctx, serialNumber);
		final String ipaSSCC18 = sscc18CodeBL.toString(sscc18, false); // humanReadable=false
		desadvLine.setIPA_SSCC18(ipaSSCC18);
	}
}
