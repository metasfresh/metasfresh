/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.marketing.base.interceptor;

import de.metas.marketing.base.model.I_MKTG_Channel;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;

@Callout(I_MKTG_Channel.class)
@Interceptor(I_MKTG_Channel.class)
public class MKTG_Channel
{
	public static final MKTG_Channel INSTANCE = new MKTG_Channel();

	private MKTG_Channel()
	{
	}
}
