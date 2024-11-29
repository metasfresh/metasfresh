package de.metas.allocation.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@RepoIdAwares.SkipTest
public class PaymentAllocationLineId implements RepoIdAware
{
	@Getter private final PaymentAllocationId headerId;
	private final int lineRecordId;

	public PaymentAllocationLineId(@NonNull final PaymentAllocationId headerId, final int C_AllocationLine_ID)
	{
		this.headerId = headerId;
		this.lineRecordId = Check.assumeGreaterThanZero(C_AllocationLine_ID, "C_AllocationLine_ID > 0");
	}

	@JsonCreator
	public static PaymentAllocationLineId ofRepoId(final int C_AllocationHdr_ID, final int C_AllocationLine_ID)
	{
		return new PaymentAllocationLineId(PaymentAllocationId.ofRepoId(C_AllocationHdr_ID), C_AllocationLine_ID);
	}

	public static PaymentAllocationLineId ofRepoIdOrNull(final int C_AllocationHdr_ID, final int C_AllocationLine_ID)
	{
		if (C_AllocationLine_ID <= 0)
		{
			return null;
		}
		return new PaymentAllocationLineId(PaymentAllocationId.ofRepoId(C_AllocationHdr_ID), C_AllocationLine_ID);
	}

	@JsonValue
	@Override
	public int getRepoId() {return lineRecordId;}

	public static int toRepoId(final PaymentAllocationLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

}
