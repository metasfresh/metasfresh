package de.metas.handlingunits;

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

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

/**
 * HU Transaction Attribute Change Candidate (mutable)
 *
 * @author tsa
 *
 */
public interface IMutableHUTransactionAttribute extends IHUTransactionAttribute
{

	void setM_HU_PI_Attribute(I_M_HU_PI_Attribute m_HU_PI_Attribute);

	@Override
	I_M_HU_PI_Attribute getM_HU_PI_Attribute();

	@Override
	Object getReferencedObject();

	void setReferencedObject(final Object referencedObject);

	//@formatter:off
	@Override
	BigDecimal getValueNumber();
	void setValueNumber(BigDecimal valueNumber);
	@Override
	BigDecimal getValueNumberInitial();
	void setValueNumberInitial(BigDecimal valueNumberInitial);
	//@formatter:on

	//@formatter:off
	@Override
	String getValueString();
	void setValueString(String valueString);
	@Override
	String getValueStringInitial();
	void setValueStringInitial(String valueStringInitial);
	//@formatter:on
	
	//@formatter:off
	@Override
	Date getValueDate();
	void setValueDate(Date valueDate);
	@Override
	Date getValueDateInitial();
	void setValueDateInitial(Date valueDateInitial);
	//@formatter:on

	void setM_Attribute(I_M_Attribute m_Attribute);

	@Override
	I_M_Attribute getM_Attribute();

	void setOperation(String operation);

	@Override
	String getOperation();

	@Override
	I_M_HU_Attribute getM_HU_Attribute();

	void setM_HU_Attribute(I_M_HU_Attribute huAttribute);

}
