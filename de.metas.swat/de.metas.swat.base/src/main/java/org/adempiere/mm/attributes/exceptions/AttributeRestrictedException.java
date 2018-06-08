package org.adempiere.mm.attributes.exceptions;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

public class AttributeRestrictedException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7812550089813860111L;

	public static final String MSG = "de.metas.swat.Attribute.attributeRestricted";

	public static final String MSG_SOTransaction = "de.metas.swat.SOTrx";
	public static final String MSG_POTransaction = "de.metas.swat.POTrx";

	/**
	 * 
	 * @param ctx
	 * @param isSOTrx
	 * @param attributeValue
	 * @param referenceName name of referenced model on which given attribute value is restricted
	 */
	public AttributeRestrictedException(final Properties ctx, final boolean isSOTrx, final I_M_AttributeValue attributeValue, final String referenceName)
	{
		super(buildMsg(ctx, isSOTrx, attributeValue, referenceName));

	}

	private static String buildMsg(Properties ctx, boolean isSOTrx, I_M_AttributeValue attributeValue, final String referenceName)
	{
		final String transactionType = Services.get(IMsgBL.class).getMsg(ctx, (isSOTrx ? MSG_SOTransaction : MSG_POTransaction));

		final String adLanguage = Env.getAD_Language(ctx);
		return Services.get(IMsgBL.class).getMsg(adLanguage,
				MSG,
				new Object[] { attributeValue.getM_Attribute().getName(), referenceName, transactionType });
	}
}
