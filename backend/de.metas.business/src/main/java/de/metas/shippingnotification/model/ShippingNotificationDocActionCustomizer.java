package de.metas.shippingnotification.model;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;
import org.springframework.stereotype.Component;

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
public class ShippingNotificationDocActionCustomizer implements IDocActionOptionsCustomizer
{
	@Override
	public String getAppliesToTableName()
	{
		return I_M_Shipping_Notification.Table_Name;
	}

	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		optionsCtx.setDocActions(suggestDocActions(optionsCtx.getDocStatus()));
	}

	private static ImmutableSet<String> suggestDocActions(final String docStatus)
	{
		return switch (docStatus)
		{
			case IDocument.STATUS_Drafted -> ImmutableSet.of(IDocument.ACTION_Complete);
			case IDocument.STATUS_Completed -> ImmutableSet.of(IDocument.ACTION_Reverse_Correct);
			default -> ImmutableSet.of();
		};
	}

}
