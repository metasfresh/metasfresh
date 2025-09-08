package de.metas.invoicecandidate.api;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.PInstanceId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class InvoiceCandidateIdsSelection
{
	private static final InvoiceCandidateIdsSelection EMPTY = new InvoiceCandidateIdsSelection(null, ImmutableSet.of());

	public static InvoiceCandidateIdsSelection ofSelectionId(@NonNull final PInstanceId selectionId)
	{
		return new InvoiceCandidateIdsSelection(selectionId, null);
	}

	public static InvoiceCandidateIdsSelection ofIdsSet(@NonNull final Set<InvoiceCandidateId> ids)
	{
		return !ids.isEmpty()
				? new InvoiceCandidateIdsSelection(null, ImmutableSet.copyOf(ids))
				: EMPTY;
	}

	public static InvoiceCandidateIdsSelection extractFixedIdsSet(@NonNull final Iterable<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		final ImmutableSet.Builder<InvoiceCandidateId> ids = ImmutableSet.builder();
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			final InvoiceCandidateId id = InvoiceCandidateId.ofRepoIdOrNull(ic.getC_Invoice_Candidate_ID());
			if (id != null)
			{
				ids.add(id);
			}
		}

		return ofIdsSet(ids.build());
	}

	@Nullable private final PInstanceId selectionId;
	@Nullable private final ImmutableSet<InvoiceCandidateId> ids;

	private InvoiceCandidateIdsSelection(
			@Nullable final PInstanceId selectionId,
			@Nullable final ImmutableSet<InvoiceCandidateId> ids)
	{
		if (CoalesceUtil.countNotNulls(selectionId, ids) != 1)
		{
			throw new AdempiereException("Only `selectionId` or only `ids` can be set, but not both")
					.appendParametersToMessage()
					.setParameter("selectionId", selectionId)
					.setParameter("ids", ids);
		}

		this.selectionId = selectionId;
		this.ids = ids;
	}

	public boolean isEmpty() {return selectionId == null && (ids == null || ids.isEmpty());}

	public boolean isDatabaseSelection()
	{
		return selectionId != null;
	}

	public interface CaseMapper
	{
		void empty();

		void fixedSet(@NonNull ImmutableSet<InvoiceCandidateId> ids);

		void selectionId(@NonNull PInstanceId selectionId);
	}

	public void apply(@NonNull final CaseMapper mapper)
	{
		if (selectionId != null)
		{
			mapper.selectionId(selectionId);
		}
		else if (ids != null && !ids.isEmpty())
		{
			mapper.fixedSet(ids);
		}
		else
		{
			mapper.empty();
		}
	}
}
