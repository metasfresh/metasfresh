package de.metas.frontend_testing.masterdata.vatid;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.StringUtils;
import de.metas.vatid.VATaxIDCheckLogId;
import de.metas.vatid.VATaxIDCheckRepository;
import de.metas.vatid.VATaxIDCheckRequest;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Appends one {@code VATaxID_CheckLog} row for an already-created business partner, through the real
 * production writer {@link VATaxIDCheckRepository#writeRequestSent(VATaxIDCheckRequest)} — never through
 * raw SQL or {@code InterfaceWrapperHelper} directly. No VIES call is involved: the row is written at
 * {@code VATaxIDStatus.RequestSent}, exactly the state a real check-log row is in the instant a request
 * is sent, which is all this module's tests need to exercise the real count-gated zoom mechanism.
 */
@Builder
public class VATaxIDCheckLogCreateCommand
{
	@NonNull private final VATaxIDCheckRepository vataxIDCheckRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonVATaxIDCheckLogRequest request;
	@NonNull private final Identifier identifier;

	private static final String DEFAULT_VATAXID = "DE123456788";

	public JsonVATaxIDCheckLogResponse execute()
	{
		final BPartnerId bpartnerId = context.getId(request.getBpartner(), BPartnerId.class);

		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocation() != null
				? context.getBPartnerLocationId(request.getBpartnerLocation())
				: null;

		final String vataxIDValue = StringUtils.trimBlankToOptional(request.getVataxID()).orElse(DEFAULT_VATAXID);

		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder()
						.bpartnerId(bpartnerId)
						.bpartnerLocationId(bpartnerLocationId)
						.vataxID(VATIdentifier.of(vataxIDValue))
						.build());

		context.putIdentifier(identifier, checkLogId);

		return JsonVATaxIDCheckLogResponse.builder()
				.checkLogId(checkLogId.getRepoId())
				.build();
	}
}
