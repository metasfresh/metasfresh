package de.metas.handlingunits.picking;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

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
}
