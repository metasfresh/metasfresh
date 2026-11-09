package de.metas.pos.invoice_settlement.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class POSInvoiceCardSettlementId implements RepoIdAware
{
	int repoId;

	private POSInvoiceCardSettlementId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_POS_InvoiceCardSettlement_ID");
	}

	public static POSInvoiceCardSettlementId ofRepoId(final int repoId)
	{
		return new POSInvoiceCardSettlementId(repoId);
	}

	@JsonCreator
	public static POSInvoiceCardSettlementId ofString(@NonNull final String str)
	{
		return RepoIdAwares.ofObject(str, POSInvoiceCardSettlementId.class, POSInvoiceCardSettlementId::ofRepoId);
	}

	@Nullable
	public static POSInvoiceCardSettlementId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new POSInvoiceCardSettlementId(repoId) : null;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
