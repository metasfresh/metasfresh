/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.remittanceadvice.document;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.remittanceadvice.RemittanceAdviceService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.X_C_RemittanceAdvice;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

public class C_RemittanceAdvice_DocHandler  implements DocumentHandler
{
	final RemittanceAdviceService remittanceAdviceService = SpringContextHolder.instance.getBean(RemittanceAdviceService.class);
	final RemittanceAdviceRepository remittanceAdviceRepository = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extractRemittanceAdvice(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRemittanceAdvice(docFields).getCreatedBy();
	}

	@Override
	public LocalDate getDocumentDate(final DocumentTableFields docFields)
	{
		final I_C_RemittanceAdvice remittanceAdvice = extractRemittanceAdvice(docFields);

		return TimeUtil.asLocalDate(remittanceAdvice.getDateDoc());
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		final I_C_RemittanceAdvice remittanceAdviceRecord = extractRemittanceAdvice(docFields);
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(remittanceAdviceRecord.getC_RemittanceAdvice_ID());

		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.getRemittanceAdvice(remittanceAdviceId);

		remittanceAdviceService.resolveRemittanceAdviceLines(remittanceAdvice);
		remittanceAdviceRepository.updateRemittanceAdvice(remittanceAdvice);

		remittanceAdvice.validateCompleteAction();

		remittanceAdviceRecord.setDocAction(X_C_RemittanceAdvice.DOCACTION_Re_Activate);
		remittanceAdviceRecord.setProcessed(true);
		return X_C_RemittanceAdvice.DOCSTATUS_Completed;
	}

	@Override
	public void reactivateIt(final DocumentTableFields docFields)
	{
		final I_C_RemittanceAdvice remittanceAdvice = extractRemittanceAdvice(docFields);

		if (remittanceAdvice.getC_Payment_ID() > 0)
		{
			throw new AdempiereException("A payment was already created!");
		}

		remittanceAdvice.setProcessed(false);
		remittanceAdvice.setDocAction(X_C_RemittanceAdvice.DOCACTION_Complete);
	}

	private static I_C_RemittanceAdvice extractRemittanceAdvice(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_RemittanceAdvice.class);
	}

}
