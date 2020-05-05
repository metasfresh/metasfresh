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


import java.util.Properties;

import de.metas.util.ISingletonService;

public interface ISubProducerAttributeBL extends ISingletonService
{

	/**
	 * Change values of the ADR attribute that is related with the sub-producer (if and when there will be more than 1 implementation, pls move this specific information to other places so that the javadoc is correct while also being informative).
	 * <p>
	 * If the given attribute set has no ADR attribute, then do nothing.
	 * <p>
	 * Retrieve the partner (i.e. the actual sup-producer) from the attribute set's subProducer attribute, falling back to HU's bpartner. If there is none, set the ADR attribute to <code>null</code>
	 * <p>
	 * If called with <code>subProducerInitialized==true</code>, then don't overwrite pre-existing ADR attribute values, but only set them if there aren't values set yet. This behavior is supposed to
	 * prevent us from loosing attribute values that were explicitly set while the sub-producer was not yet set or propagated. See task <a
	 * href="http://dewiki908/mediawiki/index.php/08782_ADR_not_correct_in_MRP_Product_Info%2C_Passende_Best%C3%A4nde">08782 ADR not correct in MRP Product Info, Passende Bestände</a> for a
	 * bug that happens if we <i>always</i> reset/recalculate the attributes. On the other hand, if the subproducer changes, from one not-empty value to another one, then we reset the ADR attribute to
	 * the new partner's value.
	 *
	 * @param ctx
	 * @param attributeSet
	 * @param subProducerJustInitialized <code>true</code> if the subProducer was changed from 0/null to an actual value.
	 */
	void updateAttributesOnSubProducerChanged(Properties ctx, IAttributeSet attributeSet, boolean subProducerJustInitialized);
}
