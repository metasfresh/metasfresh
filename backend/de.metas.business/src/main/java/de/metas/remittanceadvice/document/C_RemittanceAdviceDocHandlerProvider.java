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
import de.metas.document.engine.DocumentHandlerProvider;
import org.compiere.model.I_C_RemittanceAdvice;
import org.springframework.stereotype.Component;

@Component
public class C_RemittanceAdviceDocHandlerProvider implements DocumentHandlerProvider
{
	@Override
	public String getHandledTableName()
	{
		return I_C_RemittanceAdvice.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model_NOTUSED)
	{
		return new C_RemittanceAdvice_DocHandler();
	}
}
