/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.printing.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BP_PrintFormat.class)
@Component
public class C_BP_PrintFormat
{
	private final BPartnerPrintFormatRepository bPartnerPrintFormatRepository;

	public C_BP_PrintFormat(final BPartnerPrintFormatRepository bPartnerPrintFormatRepository)
	{
		this.bPartnerPrintFormatRepository = bPartnerPrintFormatRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setNextSeqNoIfNeeded(@NonNull final I_C_BP_PrintFormat bpPrintFormat)
	{
		if(bpPrintFormat.getSeqNo() != 0)
		{
			return;
		}

		final SeqNo seqNo = bPartnerPrintFormatRepository.getNextSeqNo(BPartnerId.ofRepoId(bpPrintFormat.getC_BPartner_ID()));

		bpPrintFormat.setSeqNo(seqNo.toInt());
	}
}
