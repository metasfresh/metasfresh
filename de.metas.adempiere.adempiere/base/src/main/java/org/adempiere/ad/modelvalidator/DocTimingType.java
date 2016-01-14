package org.adempiere.ad.modelvalidator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.Check;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;

public enum DocTimingType
{
	/** Called before document is prepared */
	BEFORE_PREPARE(ModelValidator.TIMING_BEFORE_PREPARE),
	/** Called before document is void */
	BEFORE_VOID(ModelValidator.TIMING_BEFORE_VOID),
	/** Called before document is close */
	BEFORE_CLOSE(ModelValidator.TIMING_BEFORE_CLOSE),
	/** Called before document is reactivate */
	BEFORE_REACTIVATE(ModelValidator.TIMING_BEFORE_REACTIVATE),
	/** Called before document is reversecorrect */
	BEFORE_REVERSECORRECT(ModelValidator.TIMING_BEFORE_REVERSECORRECT),
	/** Called before document is reverseaccrual */
	BEFORE_REVERSEACCRUAL(ModelValidator.TIMING_BEFORE_REVERSEACCRUAL),
	/** Called before document is completed */
	BEFORE_COMPLETE(ModelValidator.TIMING_BEFORE_COMPLETE),
	/** Called after document is prepared */
	AFTER_PREPARE(ModelValidator.TIMING_AFTER_PREPARE),
	/** Called after document is completed */
	AFTER_COMPLETE(ModelValidator.TIMING_AFTER_COMPLETE),
	/** Called after document is void */
	AFTER_VOID(ModelValidator.TIMING_AFTER_VOID),
	/** Called after document is closed */
	AFTER_CLOSE(ModelValidator.TIMING_AFTER_CLOSE),
	/** Called after document is reactivated */
	AFTER_REACTIVATE(ModelValidator.TIMING_AFTER_REACTIVATE),
	/** Called after document is reversecorrect */
	AFTER_REVERSECORRECT(ModelValidator.TIMING_AFTER_REVERSECORRECT),
	/** Called after document is reverseaccrual */
	AFTER_REVERSEACCRUAL(ModelValidator.TIMING_AFTER_REVERSEACCRUAL),
	/** Called after document is un-posted */
	AFTER_UNPOST(ModelValidator.TIMING_AFTER_UNPOST),
	/** Called before document is posted */
	BEFORE_POST(ModelValidator.TIMING_BEFORE_POST),
	/** Called after document is posted */
	AFTER_POST(ModelValidator.TIMING_AFTER_POST),
	/** Called before document is un-closed */
	BEFORE_UNCLOSE(ModelValidator.TIMING_BEFORE_UNCLOSE),
	/** Called after document is un-closed */
	AFTER_UNCLOSE(ModelValidator.TIMING_AFTER_UNCLOSE)
	;

	private final int timing;

	//
	// Implementation
	//
	DocTimingType(final int timing)
	{
		this.timing = timing;
	}

	public final int getTiming()
	{
		return timing;
	}

	public static final DocTimingType valueOf(final int timing)
	{
		final DocTimingType[] values = values();
		for (final DocTimingType value : values)
		{
			if (timing == value.getTiming())
			{
				return value;
			}
		}

		throw new IllegalArgumentException("No enum constant found for timing=" + timing + " in " + values);
	}

	public static final DocTimingType forAction(final String docAction, final BeforeAfterType beforeAfterType)
	{
		Check.assumeNotEmpty(docAction, "docAction not null");

		Check.assumeNotNull(beforeAfterType, "beforeAfterType not null");
		final boolean before;
		if (beforeAfterType == BeforeAfterType.Before)
		{
			before = true;
		}
		else if (beforeAfterType == BeforeAfterType.After)
		{
			before = false;
		}
		else
		{
			throw new IllegalArgumentException("Unknown Before/After type: " + beforeAfterType);
		}

		if (DocAction.ACTION_Complete.equals(docAction))
		{
			return before ? BEFORE_COMPLETE : AFTER_COMPLETE;
		}
		else if (DocAction.ACTION_Prepare.equals(docAction))
		{
			return before ? BEFORE_PREPARE : AFTER_PREPARE;
		}
		else if (DocAction.ACTION_Close.equals(docAction))
		{
			return before ? BEFORE_CLOSE : AFTER_CLOSE;
		}
		else if (DocAction.ACTION_ReActivate.equals(docAction))
		{
			return before ? BEFORE_REACTIVATE : AFTER_REACTIVATE;
		}
		else if (DocAction.ACTION_Reverse_Correct.equals(docAction))
		{
			return before ? BEFORE_REVERSECORRECT : AFTER_REVERSECORRECT;
		}
		else if (DocAction.ACTION_Reverse_Accrual.equals(docAction))
		{
			return before ? BEFORE_REVERSEACCRUAL : AFTER_REVERSEACCRUAL;
		}
		else if (DocAction.ACTION_Void.equals(docAction))
		{
			return before ? BEFORE_VOID : AFTER_VOID;
		}
		else if (DocAction.ACTION_UnClose.equals(docAction))
		{
			return before ? BEFORE_UNCLOSE : AFTER_UNCLOSE;
		}
		else if (DocAction.ACTION_Post.equals(docAction))
		{
			return before ? BEFORE_POST : AFTER_POST;
		}
		else
		{
			throw new IllegalArgumentException("Unknown DocAction: " + docAction);
		}
	}

}
