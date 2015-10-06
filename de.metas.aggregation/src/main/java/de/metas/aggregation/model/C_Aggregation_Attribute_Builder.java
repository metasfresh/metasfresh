package de.metas.aggregation.model;

/*
 * #%L
 * de.metas.aggregation
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


import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.Env;

/**
 * Builder class for {@link I_C_Aggregation_Attribute}.
 * 
 * @author tsa
 *
 */
public class C_Aggregation_Attribute_Builder
{
	private IContextAware context = new PlainContextAware(Env.getCtx());
	private String type;
	private String code;

	public I_C_Aggregation_Attribute build()
	{
		final I_C_Aggregation_Attribute attr = InterfaceWrapperHelper.newInstance(I_C_Aggregation_Attribute.class, context);
		attr.setType(type);
		attr.setCode(code);
		InterfaceWrapperHelper.save(attr);

		return attr;
	}

	public C_Aggregation_Attribute_Builder setType(final String type)
	{
		this.type = type;
		return this;
	}

	public C_Aggregation_Attribute_Builder setCode(final String code)
	{
		this.code = code;
		return this;
	}
}
