package de.metas.handlingunits.impl;

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

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

public class MutableHUTransactionAttribute implements IMutableHUTransactionAttribute
{
	private String operation;
	private I_M_HU_PI_Attribute piAttribute;
	private I_M_Attribute attribute;
	private String valueString;
	private BigDecimal valueNumber;
	private Date valueDate;
	private String valueStringInitial;
	private BigDecimal valueNumberInitial;
	private Date valueDateInitial;
	private Object referencedObject = null;
	private I_M_HU_Attribute huAttribute = null;

	public MutableHUTransactionAttribute()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("attribute", attribute == null ? "?" : attribute.getName())
				.add("valueString", valueString)
				.add("valueNumber", valueNumber)
				.add("valueDate", valueDate)
				.add("valueStringInitial", valueStringInitial)
				.add("valueNumberInitial", valueNumberInitial)
				.add("valueDateInitial", valueDateInitial)
				.add("operation", operation)
				.add("piAttribute", piAttribute)
				.add("huAttribute", huAttribute)
				.add("referencedObject", referencedObject)
				.toString();
	}

	@Override
	public String getOperation()
	{
		return operation;
	}

	@Override
	public void setOperation(final String operation)
	{
		this.operation = operation;
	}

	@Override
	public I_M_Attribute getM_Attribute()
	{
		return attribute;
	}

	@Override
	public void setM_Attribute(final I_M_Attribute attribute)
	{
		this.attribute = attribute;
	}

	@Override
	public String getValueString()
	{
		return valueString;
	}

	@Override
	public void setValueString(final String valueString)
	{
		this.valueString = valueString;
	}

	@Override
	public BigDecimal getValueNumber()
	{
		return valueNumber;
	}

	@Override
	public void setValueNumber(final BigDecimal valueNumber)
	{
		this.valueNumber = valueNumber;
	}

	@Override
	public I_M_HU_PI_Attribute getM_HU_PI_Attribute()
	{
		return piAttribute;
	}

	@Override
	public void setM_HU_PI_Attribute(final I_M_HU_PI_Attribute m_HU_PI_Attribute)
	{
		piAttribute = m_HU_PI_Attribute;
	}

	@Override
	public Object getReferencedObject()
	{
		return referencedObject;
	}

	@Override
	public void setReferencedObject(final Object referencedObject)
	{
		this.referencedObject = referencedObject;
	}

	@Override
	public BigDecimal getValueNumberInitial()
	{
		return valueNumberInitial;
	}

	@Override
	public void setValueNumberInitial(final BigDecimal valueNumberInitial)
	{
		this.valueNumberInitial = valueNumberInitial;
	}

	@Override
	public String getValueStringInitial()
	{
		return valueStringInitial;
	}

	@Override
	public void setValueStringInitial(final String valueStringInitial)
	{
		this.valueStringInitial = valueStringInitial;
	}

	@Override
	public I_M_HU_Attribute getM_HU_Attribute()
	{
		return huAttribute;
	}

	@Override
	public void setM_HU_Attribute(final I_M_HU_Attribute huAttribute)
	{
		this.huAttribute = huAttribute;
	}

	@Override
	public Date getValueDate()
	{
		return valueDate;
	}

	@Override
	public void setValueDate(Date valueDate)
	{
		this.valueDate = valueDate;
	}

	@Override
	public Date getValueDateInitial()
	{
		return valueDateInitial;
	}

	@Override
	public void setValueDateInitial(Date valueDateInitial)
	{
		this.valueDateInitial = valueDateInitial;
	}
}
