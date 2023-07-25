/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final AdMessageKey MSG_ERR_DELETION_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.interceptor.C_OrderLine.DeletionNotAllowed");

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ModularContractLogDAO contractLogDAO;

	public C_OrderLine(@NonNull final ModularContractLogDAO contractLogDAO)
	{
		this.contractLogDAO = contractLogDAO;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_C_OrderLine orderLine)
	{
		final TableRecordReference recordReference = TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID());

		if (contractLogDAO.hasAnyModularLogs(recordReference))
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_ERR_DELETION_NOT_ALLOWED))
					.markAsUserValidationError();
		}
	}
}
