package de.metas.shipping.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class ShipperTransportationDocActionCustomizer implements IDocActionOptionsCustomizer
{
	@Override
	public String getAppliesToTableName()
	{
		return I_M_ShipperTransportation.Table_Name;
	}

	@Override
	public void customizeValidActions(final IDocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());

		final String docStatus = optionsCtx.getDocStatus();
		if (IDocument.STATUS_Drafted.equals(docStatus))
		{
			// remove the void option when Drafted
			docActions.remove(IDocument.ACTION_Void);
		}
		// Complete .. CO
		else if (IDocument.STATUS_Completed.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_ReActivate);
		}

		//
		// Correct options
		optionsCtx.setDocActions(docActions);
	}

}
