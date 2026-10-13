package de.metas.handlingunits.picking;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.ad_reference.ADRefList;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
@SuppressWarnings("UnstableApiUsage")
public final class QtyRejectedReasonCode
{
	public static final ReferenceId REFERENCE_ID = ReferenceId.ofRepoId(X_M_Picking_Candidate.REJECTREASON_AD_Reference_ID);

	public static QtyRejectedReasonCode ofCode(@NonNull final String code)
	{
		return interner.intern(new QtyRejectedReasonCode(code));
	}

	public static Optional<QtyRejectedReasonCode> ofNullableCode(@Nullable final String code)
	{
		return StringUtils.trimBlankToOptional(code).map(QtyRejectedReasonCode::ofCode);
	}

	private static final Interner<QtyRejectedReasonCode> interner = Interners.newStrongInterner();

	/** Reason for a handling unit emptied during a raw-materials issue. Declared after {@code interner}, which {@link #ofCode(String)} dereferences. */
	public static final QtyRejectedReasonCode EMPTIED = ofCode("E");

	@Getter
	private final String code;

	private QtyRejectedReasonCode(@NonNull final String code)
	{
		Check.assumeNotEmpty(code, "code not empty");
		this.code = code;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@JsonValue
	public String toJson()
	{
		return getCode();
	}

	@Nullable
	public static String toCode(@Nullable QtyRejectedReasonCode reasonCode) {return reasonCode != null ? reasonCode.getCode() : null;}

	/** The only place in the codebase that knows which reason codes are context-specific. */
	public static ADRefList reasonsFor(
			@NonNull final ADRefList all,
			@NonNull final QtyRejectedReasonContext context)
	{
		switch (context)
		{
			case ManufacturingIssue:
				return all;                                   // EMPTIED is valid only here
			case Picking:
			case Distribution:
			case InventoryDisposal:
				return all.excluding(ImmutableSet.of(EMPTIED.getCode()));
			default:
				throw new AdempiereException("Unknown QtyRejectedReasonContext: " + context);
		}
	}
}
