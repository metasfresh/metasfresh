package de.metas.handlingunits.hutransaction;

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


import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.mm.attributes.AttributeId;

import de.metas.handlingunits.HuPackingInstructionsAttributeId;
import de.metas.handlingunits.model.I_M_HU_Attribute;

/**
 * HU Transaction Attribute Change Candidate (immutable)
 *
 * @author tsa
 *
 */
public interface IHUTransactionAttribute
{
	AttributeId getAttributeId();

	HUTransactionAttributeOperation getOperation();

	BigDecimal getValueNumber();
	BigDecimal getValueNumberInitial();

	String getValueString();
	String getValueStringInitial();

	Date getValueDate();
	Date getValueDateInitial();

	/**
	 * Referenced object (i.e. on which object this attribute applies. e.g. M_HU, M_AttributeSetInstance etc)
	 *
	 * @return
	 */
	Object getReferencedObject();

	HuPackingInstructionsAttributeId getPiAttributeId();

	/**
	 *
	 * @return existing {@link I_M_HU_Attribute} (which also could be new and not saved yet) or <code>null</code> in case this is not an HU related transaction (e.g. we deal with ASIs)
	 */
	I_M_HU_Attribute getHuAttribute();
}
