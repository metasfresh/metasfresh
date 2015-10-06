package org.adempiere.mm.attributes.api;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Collection;
import java.util.Date;

import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;

/**
 * BL to handle Best-Before date on HUs.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/09363_Best-before_management_%28Mindesthaltbarkeit%29_%28108375354495%29
 */
public interface IHUBestBeforeBL extends ISingletonService
{
	/**
	 * 
	 * @return best-before attribute; never returns null
	 */
	I_M_Attribute getBestBeforeDateAttribute();

	/**
	 * Calculates and set the Best-Before date to given {@link I_M_HU}s.
	 * 
	 * @param huContext
	 * @param vhus VHUs to be updated
	 * @param dateReceipt date when the HUs were received.
	 */
	void setBestBeforeDate(IHUContext huContext, Collection<I_M_HU> vhus, Date dateReceipt);
}
