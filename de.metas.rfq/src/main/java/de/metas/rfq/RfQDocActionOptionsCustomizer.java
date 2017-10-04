package de.metas.rfq;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;
import de.metas.rfq.model.I_C_RfQ;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class RfQDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{

	@Override
	public String getAppliesToTableName()
	{
		return I_C_RfQ.Table_Name;
	}

	@Override
	public void customizeValidActions(final IDocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());

		final String docStatus = optionsCtx.getDocStatus();
		if (IDocument.STATUS_Closed.equals(docStatus))
		{
			// remove the void option when Drafted
			docActions.add(IDocument.ACTION_UnClose);
		}

		optionsCtx.setDocActions(docActions);
	}

}
