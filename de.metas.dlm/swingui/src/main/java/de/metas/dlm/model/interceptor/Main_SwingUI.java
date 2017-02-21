package de.metas.dlm.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;

import de.metas.dlm.swingui.PreferenceCustomizer;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * DLM SwingUI module activator.
 * 
 * This class will be loaded only if running with {@link org.compiere.Adempiere.RunMode#SWING_CLIENT} run mode.
 * 
 * NOTE: for this it's important to keep the name (incl. package name!) in sync with {@link de.metas.dlm.model.interceptor.Main} and also to keep the suffix {@code _SwingUI}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public class Main_SwingUI extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		PreferenceCustomizer.customizePrefernces(); // gh #975
	}
}
