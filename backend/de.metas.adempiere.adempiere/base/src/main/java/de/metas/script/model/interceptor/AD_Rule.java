package de.metas.script.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.ModelValidator;

import de.metas.script.ScriptEngineFactory;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Interceptor(I_AD_Rule.class)
public class AD_Rule
{
	public static final transient AD_Rule instance = new AD_Rule();

	private AD_Rule()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_AD_Rule rule)
	{
		ScriptEngineFactory.get().assertScriptEngineExists(rule);
	}
}
