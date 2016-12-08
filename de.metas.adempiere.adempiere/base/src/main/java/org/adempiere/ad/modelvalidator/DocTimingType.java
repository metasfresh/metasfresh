package org.adempiere.ad.modelvalidator;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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
import org.adempiere.util.GuavaCollectors;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;

import com.google.common.annotations.VisibleForTesting;

public enum DocTimingType
{
	BEFORE_PREPARE(ModelValidator.TIMING_BEFORE_PREPARE, DocAction.ACTION_Prepare, BeforeAfterType.Before) //
	, AFTER_PREPARE(ModelValidator.TIMING_AFTER_PREPARE, DocAction.ACTION_Prepare, BeforeAfterType.After) //
	, BEFORE_VOID(ModelValidator.TIMING_BEFORE_VOID, DocAction.ACTION_Void, BeforeAfterType.Before) //
	, AFTER_VOID(ModelValidator.TIMING_AFTER_VOID, DocAction.ACTION_Void, BeforeAfterType.After) //
	, BEFORE_CLOSE(ModelValidator.TIMING_BEFORE_CLOSE, DocAction.ACTION_Close, BeforeAfterType.Before) //
	, AFTER_CLOSE(ModelValidator.TIMING_AFTER_CLOSE, DocAction.ACTION_Close, BeforeAfterType.After) //
	, BEFORE_REACTIVATE(ModelValidator.TIMING_BEFORE_REACTIVATE, DocAction.ACTION_ReActivate, BeforeAfterType.Before) //
	, AFTER_REACTIVATE(ModelValidator.TIMING_AFTER_REACTIVATE, DocAction.ACTION_ReActivate, BeforeAfterType.After) //
	, BEFORE_REVERSECORRECT(ModelValidator.TIMING_BEFORE_REVERSECORRECT, DocAction.ACTION_Reverse_Correct, BeforeAfterType.Before) //
	, AFTER_REVERSECORRECT(ModelValidator.TIMING_AFTER_REVERSECORRECT, DocAction.ACTION_Reverse_Correct, BeforeAfterType.After) //
	, BEFORE_REVERSEACCRUAL(ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, DocAction.ACTION_Reverse_Accrual, BeforeAfterType.Before) //
	, AFTER_REVERSEACCRUAL(ModelValidator.TIMING_AFTER_REVERSEACCRUAL, DocAction.ACTION_Reverse_Accrual, BeforeAfterType.After) //
	, BEFORE_COMPLETE(ModelValidator.TIMING_BEFORE_COMPLETE, DocAction.ACTION_Complete, BeforeAfterType.Before) //
	, AFTER_COMPLETE(ModelValidator.TIMING_AFTER_COMPLETE, DocAction.ACTION_Complete, BeforeAfterType.After) //
	, AFTER_UNPOST(ModelValidator.TIMING_AFTER_UNPOST, DocAction.ACTION_UnPost, BeforeAfterType.After) //
	, BEFORE_POST(ModelValidator.TIMING_BEFORE_POST, DocAction.ACTION_Post, BeforeAfterType.Before) //
	, AFTER_POST(ModelValidator.TIMING_AFTER_POST, DocAction.ACTION_Post, BeforeAfterType.After) //
	, BEFORE_UNCLOSE(ModelValidator.TIMING_BEFORE_UNCLOSE, DocAction.ACTION_UnClose, BeforeAfterType.Before) //
	, AFTER_UNCLOSE(ModelValidator.TIMING_AFTER_UNCLOSE, DocAction.ACTION_UnClose, BeforeAfterType.After) //
	;

	//
	// Implementation
	//

	private final int timing;
	private final String docAction;
	private final BeforeAfterType beforeAfter;

	DocTimingType(final int timing, final String docAction, final BeforeAfterType beforeAfter)
	{
		this.timing = timing;
		this.docAction = docAction;
		this.beforeAfter = beforeAfter;
	}

	public final int getTiming()
	{
		return timing;
	}

	public String getDocAction()
	{
		return docAction;
	}

	public boolean isDocAction(final String docAction)
	{
		return Objects.equals(this.docAction, docAction);
	}

	@VisibleForTesting
	BeforeAfterType getBeforeAfter()
	{
		return beforeAfter;
	}

	public boolean isBefore()
	{
		return beforeAfter == BeforeAfterType.Before;
	}

	public boolean isAfter()
	{
		return beforeAfter == BeforeAfterType.After;
	}

	private boolean matches(final String docAction, final BeforeAfterType beforeAfter)
	{
		return isDocAction(docAction) && this.beforeAfter == beforeAfter;
	}

	public static final DocTimingType valueOf(final int timing)
	{
		final DocTimingType value = timingInt2type.get(timing);
		if (value == null)
		{
			throw new IllegalArgumentException("No enum constant found for timing=" + timing + " in " + values());
		}
		return value;
	}

	private static final Map<Integer, DocTimingType> timingInt2type = Stream.of(values()).collect(GuavaCollectors.toImmutableMapByKey(DocTimingType::getTiming));

	public static final DocTimingType forAction(final String docAction, final BeforeAfterType beforeAfterType)
	{
		Check.assumeNotEmpty(docAction, "docAction not null");
		Check.assumeNotNull(beforeAfterType, "beforeAfterType not null");

		// NOTE: no need to use an indexed map because this method is not used very often

		for (final DocTimingType timing : values())
		{
			if (timing.matches(docAction, beforeAfterType))
			{
				return timing;
			}
		}

		throw new IllegalArgumentException("Unknown DocAction: " + docAction + ", " + beforeAfterType);
	}

}
