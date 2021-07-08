package org.adempiere.ad.modelvalidator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.document.engine.IDocument;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import org.compiere.model.ModelValidator;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public enum DocTimingType implements TimingType
{
	BEFORE_PREPARE(ModelValidator.TIMING_BEFORE_PREPARE, IDocument.ACTION_Prepare, IDocument.STATUS_InProgress, BeforeAfterType.Before) //
	, AFTER_PREPARE(ModelValidator.TIMING_AFTER_PREPARE, IDocument.ACTION_Prepare, IDocument.STATUS_InProgress, BeforeAfterType.After) //
	, BEFORE_VOID(ModelValidator.TIMING_BEFORE_VOID, IDocument.ACTION_Void, IDocument.STATUS_Voided, BeforeAfterType.Before) //
	, AFTER_VOID(ModelValidator.TIMING_AFTER_VOID, IDocument.ACTION_Void, IDocument.STATUS_Voided, BeforeAfterType.After) //
	, BEFORE_CLOSE(ModelValidator.TIMING_BEFORE_CLOSE, IDocument.ACTION_Close, IDocument.STATUS_Closed, BeforeAfterType.Before) //
	, AFTER_CLOSE(ModelValidator.TIMING_AFTER_CLOSE, IDocument.ACTION_Close, IDocument.STATUS_Closed, BeforeAfterType.After) //
	, BEFORE_REACTIVATE(ModelValidator.TIMING_BEFORE_REACTIVATE, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress, BeforeAfterType.Before) //
	, AFTER_REACTIVATE(ModelValidator.TIMING_AFTER_REACTIVATE, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress, BeforeAfterType.After) //
	, BEFORE_REVERSECORRECT(ModelValidator.TIMING_BEFORE_REVERSECORRECT, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed, BeforeAfterType.Before) //
	, AFTER_REVERSECORRECT(ModelValidator.TIMING_AFTER_REVERSECORRECT, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed, BeforeAfterType.After) //
	, BEFORE_REVERSEACCRUAL(ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, IDocument.ACTION_Reverse_Accrual, IDocument.STATUS_Reversed, BeforeAfterType.Before) //
	, AFTER_REVERSEACCRUAL(ModelValidator.TIMING_AFTER_REVERSEACCRUAL, IDocument.ACTION_Reverse_Accrual, IDocument.STATUS_Reversed, BeforeAfterType.After) //
	, BEFORE_COMPLETE(ModelValidator.TIMING_BEFORE_COMPLETE, IDocument.ACTION_Complete, IDocument.STATUS_Completed, BeforeAfterType.Before) //
	, AFTER_COMPLETE(ModelValidator.TIMING_AFTER_COMPLETE, IDocument.ACTION_Complete, IDocument.STATUS_Completed, BeforeAfterType.After) //
	, AFTER_UNPOST(ModelValidator.TIMING_AFTER_UNPOST, IDocument.ACTION_UnPost, IDocument.STATUS_Unknown, BeforeAfterType.After) //
	, BEFORE_POST(ModelValidator.TIMING_BEFORE_POST, IDocument.ACTION_Post, IDocument.STATUS_Unknown, BeforeAfterType.Before) //
	, AFTER_POST(ModelValidator.TIMING_AFTER_POST, IDocument.ACTION_Post, IDocument.STATUS_Unknown, BeforeAfterType.After) //
	, BEFORE_UNCLOSE(ModelValidator.TIMING_BEFORE_UNCLOSE, IDocument.ACTION_UnClose, IDocument.STATUS_Completed, BeforeAfterType.Before) //
	, AFTER_UNCLOSE(ModelValidator.TIMING_AFTER_UNCLOSE, IDocument.ACTION_UnClose, IDocument.STATUS_Completed, BeforeAfterType.After) //
	;

	//
	// Implementation
	//

	private final int timing;
	private final String docAction;
	private final String docStatus;
	private final BeforeAfterType beforeAfter;

	DocTimingType(final int timing, final String docAction, final String docStatus, final BeforeAfterType beforeAfter)
	{
		this.timing = timing;
		this.docAction = docAction;
		this.docStatus = docStatus;
		this.beforeAfter = beforeAfter;
	}

	@Override
	public int toInt()
	{
		return timing;
	}

	public String getDocAction()
	{
		return docAction;
	}

	public String getDocStatus()
	{
		return docStatus;
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

	public static DocTimingType valueOf(final int timing)
	{
		final DocTimingType value = timingInt2type.get(timing);
		if (value == null)
		{
			throw new IllegalArgumentException("No enum constant found for timing=" + timing + " in " + timingInt2type);
		}
		return value;
	}

	private static final Map<Integer, DocTimingType> timingInt2type = Stream.of(values()).collect(GuavaCollectors.toImmutableMapByKey(DocTimingType::toInt));

	public static DocTimingType forAction(final String docAction, final BeforeAfterType beforeAfterType)
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

	public boolean isVoid()
	{
		return this == BEFORE_VOID || this == AFTER_VOID;
	}

	public boolean isReverse()
	{
		return this == BEFORE_REVERSECORRECT || this == AFTER_REVERSECORRECT
				|| this == BEFORE_REVERSEACCRUAL || this == AFTER_REVERSEACCRUAL;
	}

}
