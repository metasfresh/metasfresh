package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.IdConstants;
import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.reconcile.SourceDocumentStatus;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Decides whether an {@link de.metas.material.dispo.commons.candidate.CandidateType#DEMAND}/supply
 * candidate still contributes to the projected ATP, by looking at the status of the document the
 * candidate was created from.
 * <p>
 * The candidate's own quantities cannot answer that question: a position that was never moved and a
 * position that will never be moved both read {@code Qty = 20, QtyFulfilled = 0}. Measured on
 * {@code modus_operandi_hotfix} with cucumber scenario {@code @Id:ATPBASE_006}: after a manufacturing
 * order is CLOSED with its components never issued, the {@code DEMAND}/{@code PRODUCTION} candidate
 * still carries {@code Qty = 20} and still decrements ATP. For the same reason the denormalized
 * {@code ProductionDetail#getPpOrderDocStatus()} / {@code DistributionDetail#getDdOrderDocStatus()}
 * snapshots on the candidate are deliberately NOT used here — only the live source document is.
 * <p>
 * The still-open condition per business case:
 * <table>
 * <caption>liveness predicates</caption>
 * <tr><th>business case</th><th>source document</th><th>still open when</th></tr>
 * <tr><td>SHIPMENT</td><td>{@code M_ShipmentSchedule}</td><td>{@code Processed='N' AND IsActive='Y'}</td></tr>
 * <tr><td>PURCHASE</td><td>{@code M_ReceiptSchedule}</td><td>{@code Processed='N' AND IsActive='Y'}</td></tr>
 * <tr><td>PRODUCTION</td><td>{@code PP_Order}</td><td>{@code DocStatus IN ('DR','IP','CO')}</td></tr>
 * <tr><td>DISTRIBUTION</td><td>{@code DD_Order}</td><td>{@code DocStatus IN ('DR','IP','CO')}</td></tr>
 * <tr><td>FORECAST</td><td>{@code M_Forecast} (via {@code M_ForecastLine})</td><td>{@code DocStatus IN ('DR','IP','CO')}</td></tr>
 * </table>
 * <p>
 * {@code STOCK_CHANGE} and every candidate without a business case (i.e. every {@code STOCK}
 * candidate) yield {@link SourceDocumentStatus#NO_SOURCE_DOCUMENT}, and that is deliberate rather than
 * a gap: those candidates describe a change that has already been realized, so its effect is already
 * inside the {@code MD_Stock.QtyOnHand} term of the target expression. Letting them contribute a second
 * time would double-count them against physical stock — the same failure family as the drift this
 * service exists to reconcile.
 */
@Service
public class SourceDocumentLivenessService
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static SourceDocumentLivenessService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				SourceDocumentLivenessService.class,
				SourceDocumentLivenessService::new);
	}

	/**
	 * @return the status of the given candidate's source document
	 */
	public SourceDocumentStatus getStatus(@NonNull final Candidate candidate)
	{
		final CandidateBusinessCase businessCase = candidate.getBusinessCase();
		if (businessCase == null)
		{
			// stock candidates carry no business case, and thus no source document
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		switch (businessCase)
		{
			case SHIPMENT:
				return getShipmentScheduleStatus(candidate);
			case PURCHASE:
				return getReceiptScheduleStatus(candidate);
			case PRODUCTION:
				return getPPOrderStatus(candidate);
			case DISTRIBUTION:
				return getDDOrderStatus(candidate);
			case FORECAST:
				return getForecastStatus(candidate);
			case STOCK_CHANGE:
			default:
				return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}
	}

	/**
	 * Same as {@link #getStatus(Candidate)}, but a candidate dated strictly before the given cutoff is
	 * considered {@link SourceDocumentStatus#CLOSED} no matter what its source document says. That
	 * covers an era whose document statuses are themselves not trustworthy: orders that were long since
	 * fulfilled but never processed still look open, so their candidates must not contribute to ATP.
	 *
	 * @param livenessCutoff may be {@code null}, in which case no candidate is cut off
	 */
	public SourceDocumentStatus getStatus(
			@NonNull final Candidate candidate,
			@Nullable final Instant livenessCutoff)
	{
		if (livenessCutoff != null && candidate.getDate().isBefore(livenessCutoff))
		{
			return SourceDocumentStatus.CLOSED;
		}

		return getStatus(candidate);
	}

	private SourceDocumentStatus getShipmentScheduleStatus(@NonNull final Candidate candidate)
	{
		final DemandDetail demandDetail = DemandDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (demandDetail == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final I_M_ShipmentSchedule shipmentSchedule = getSourceDocumentOrNull(
				I_M_ShipmentSchedule.class,
				I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
				demandDetail.getShipmentScheduleId());
		if (shipmentSchedule == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		return toStatus(!shipmentSchedule.isProcessed() && shipmentSchedule.isActive());
	}

	private SourceDocumentStatus getReceiptScheduleStatus(@NonNull final Candidate candidate)
	{
		final PurchaseDetail purchaseDetail = PurchaseDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (purchaseDetail == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final I_M_ReceiptSchedule receiptSchedule = getSourceDocumentOrNull(
				I_M_ReceiptSchedule.class,
				I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID,
				purchaseDetail.getReceiptScheduleRepoId());
		if (receiptSchedule == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		return toStatus(!receiptSchedule.isProcessed() && receiptSchedule.isActive());
	}

	private SourceDocumentStatus getPPOrderStatus(@NonNull final Candidate candidate)
	{
		final ProductionDetail productionDetail = ProductionDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (productionDetail == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final PPOrderId ppOrderId = productionDetail.getPpOrderId();
		if (ppOrderId == null)
		{
			// the candidate references a PP_Order_Candidate only; there is no manufacturing order yet
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final I_PP_Order ppOrder = getSourceDocumentOrNull(
				I_PP_Order.class,
				I_PP_Order.COLUMNNAME_PP_Order_ID,
				ppOrderId.getRepoId());
		if (ppOrder == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		return toStatus(isStillOpenDocStatus(ppOrder.getDocStatus()));
	}

	private SourceDocumentStatus getDDOrderStatus(@NonNull final Candidate candidate)
	{
		final DistributionDetail distributionDetail = DistributionDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (distributionDetail == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final DDOrderRef ddOrderRef = distributionDetail.getDdOrderRef();
		if (ddOrderRef == null)
		{
			// the candidate references a DD_Order_Candidate only; there is no distribution order yet
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final I_DD_Order ddOrder = getSourceDocumentOrNull(
				I_DD_Order.class,
				I_DD_Order.COLUMNNAME_DD_Order_ID,
				ddOrderRef.getDdOrderId());
		if (ddOrder == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		return toStatus(isStillOpenDocStatus(ddOrder.getDocStatus()));
	}

	private SourceDocumentStatus getForecastStatus(@NonNull final Candidate candidate)
	{
		final DemandDetail demandDetail = DemandDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (demandDetail == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		// the demand detail references the forecast LINE; the DocStatus lives on the forecast header
		final I_M_ForecastLine forecastLine = getSourceDocumentOrNull(
				I_M_ForecastLine.class,
				I_M_ForecastLine.COLUMNNAME_M_ForecastLine_ID,
				demandDetail.getForecastLineId());
		if (forecastLine == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		final I_M_Forecast forecast = getSourceDocumentOrNull(
				I_M_Forecast.class,
				I_M_Forecast.COLUMNNAME_M_Forecast_ID,
				forecastLine.getM_Forecast_ID());
		if (forecast == null)
		{
			return SourceDocumentStatus.NO_SOURCE_DOCUMENT;
		}

		return toStatus(isStillOpenDocStatus(forecast.getDocStatus()));
	}

	@Nullable
	private <T> T getSourceDocumentOrNull(
			@NonNull final Class<T> modelClass,
			@NonNull final String idColumnName,
			final int recordId)
	{
		// note that an unset id is IdConstants.UNSPECIFIED_REPO_ID, which is positive
		final int repoId = IdConstants.toRepoId(recordId);
		if (repoId <= 0)
		{
			return null;
		}

		return queryBL.createQueryBuilder(modelClass)
				.addEqualsFilter(idColumnName, repoId)
				.create()
				.firstOnly(modelClass);
	}

	/**
	 * @return {@code true} if the given code is one of {@code DR}, {@code IP} or {@code CO}
	 */
	private static boolean isStillOpenDocStatus(@Nullable final String docStatusCode)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(docStatusCode);
		return docStatus != null && docStatus.isDraftedInProgressOrCompleted();
	}

	private static SourceDocumentStatus toStatus(final boolean stillOpen)
	{
		return stillOpen
				? SourceDocumentStatus.STILL_OPEN
				: SourceDocumentStatus.CLOSED;
	}
}
