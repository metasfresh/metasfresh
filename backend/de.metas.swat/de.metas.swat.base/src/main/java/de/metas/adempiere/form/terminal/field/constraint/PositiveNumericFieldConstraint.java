package de.metas.adempiere.form.terminal.field.constraint;

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


import java.math.BigDecimal;

import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.WrongValueException;

/**
 * Positive or ZERO constraint.
 * 
 * @author tsa
 *
 */
public class PositiveNumericFieldConstraint implements ITerminalFieldConstraint<BigDecimal>
{
	public static final String ERR_QTY_NOT_NULL = "QtyNotNull";
	public static final String ERR_NEGATIVE_QTY_NOT_ALLOWED = "NegativeQtyNotAllowed";

	/**
	 * Default instance
	 */
	public static final PositiveNumericFieldConstraint instance = new PositiveNumericFieldConstraint(ERR_QTY_NOT_NULL, ERR_NEGATIVE_QTY_NOT_ALLOWED);

	private final String msgValueNotNull;
	private final String msgValueNotAllowed;

	public PositiveNumericFieldConstraint(final String msgValueNotNull, final String msgValueNotAllowed)
	{
		super();

		this.msgValueNotNull = msgValueNotNull;
		this.msgValueNotAllowed = msgValueNotAllowed;
	}

	@Override
	public void evaluate(final ITerminalField<BigDecimal> field, final BigDecimal value) throws WrongValueException
	{
		if (value == null)
		{
			throw new WrongValueException(field, "@" + msgValueNotNull + "@");
		}

		final BigDecimal valueBD = value;
		if (valueBD.signum() < 0)
		{
			throw new WrongValueException(field, "@" + msgValueNotAllowed + "@");
		}
	}
}
