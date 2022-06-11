package de.metas.invoicecandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.IfNotFound;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.impex.InputDataSourceId;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.coalesce;

/**
 * Aggregates multiple {@link I_C_Invoice_Candidate} records and returns a result that that is suitable to create invoices.
 *
 * @see IAggregator
 */

public final class AggregationEngine
{

	public static AggregationEngine newInstance()
	{
		return builder().build();
	}

	//
	// services
	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(AggregationEngine.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
	private final transient IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
	private final transient IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository = SpringContextHolder.instance.getBean(
			OrderEmailPropagationSysConfigRepository.class);

	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private static final AdMessageKey ERR_INVOICE_CAND_PRICE_LIST_MISSING_2P = AdMessageKey.of("InvoiceCand_PriceList_Missing");

	//
	// Parameters
	private final IBPartnerBL bpartnerBL;
	private final boolean alwaysUseDefaultHeaderAggregationKeyBuilder;
	private final LocalDate today;
	private final LocalDate dateInvoicedParam;
	private final LocalDate dateAcctParam;
	private final boolean useDefaultBillLocationAndContactIfNotOverride;

	private final AdTableId inoutLineTableId;
	/**
	 * Map: HeaderAggregationKey to {@link InvoiceHeaderAndLineAggregators}
	 */
	private final Map<AggregationKey, InvoiceHeaderAndLineAggregators> key2headerAndAggregators = new LinkedHashMap<>();

	@Builder
	private AggregationEngine(
			final IBPartnerBL bpartnerBL,
			final boolean alwaysUseDefaultHeaderAggregationKeyBuilder,
			@Nullable final LocalDate dateInvoicedParam,
			@Nullable final LocalDate dateAcctParam,
			final boolean useDefaultBillLocationAndContactIfNotOverride)
	{
		this.bpartnerBL = coalesce(bpartnerBL, Services.get(IBPartnerBL.class));

		this.alwaysUseDefaultHeaderAggregationKeyBuilder = alwaysUseDefaultHeaderAggregationKeyBuilder;

		this.today = invoiceCandBL.getToday();

		this.dateInvoicedParam = dateInvoicedParam;
		this.dateAcctParam = dateAcctParam;
		this.useDefaultBillLocationAndContactIfNotOverride = useDefaultBillLocationAndContactIfNotOverride;

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		inoutLineTableId = AdTableId.ofRepoId(adTableDAO.retrieveTableId(I_M_InOutLine.Table_Name));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("key2headerAndAggregators", key2headerAndAggregators)
				.add("alwaysUseDefaultHeaderAggregationKeyBuilder", alwaysUseDefaultHeaderAggregationKeyBuilder)
				.toString();
	}

	public AggregationEngine addInvoiceCandidate(@NonNull final I_C_Invoice_Candidate ic)
	{
		Check.assume(!ic.isToClear(), "{} has IsToClear='N'", ic);
		Check.assume(!ic.isProcessed(), "{} not processed", ic);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID());
		final List<I_C_InvoiceCandidate_InOutLine> iciols = invoiceCandDAO.retrieveICIOLAssociationsExclRE(invoiceCandidateId);

		//
		// Case: No IC-IOL association found;
		// => Proceed with normal invoice aggregation
		if (iciols.isEmpty())
		{
			// Log a possible internal error: no IC-IOL association although invoice candidate was created from inout line.
			if (ic.getAD_Table_ID() == inoutLineTableId.getRepoId())
			{
				final AdempiereException ex = new AdempiereException(
						"No IC-IOL associations were found although invoice candidate points to M_InOutLine."
								+ "\n This case shall not happen because and IC-IOL association should be created by InOut handler."
								+ "\n @C_Invoice_Candidate_ID@: " + ic
								+ "\n @HeaderAggregationKey@: " + getHeaderAggregationKey(ic));
				logger.warn(ex.getLocalizedMessage(), ex);
			}

			final I_C_InvoiceCandidate_InOutLine iciol = null; // no IC-IOL association
			final boolean isLastIcIol = false; // doesn't really matter as there is no icIol at all
			addInvoiceCandidateForInOutLine(ic, iciol, isLastIcIol);
			return this; // stop here
		}

		//
		// Case: we have IC-IOL associations
		// => add them one by one; create a separate aggregation key for each of them, if needed (06630)

		// task 08606: find the last iciol for this candidate which is not in dispute
		// If the QtytoInvoice for 'ic' is greater than the delivered qty for all the iols, then we will assign/allocate the surplus qty to this one.
		// Notes:
		// * we need to assign it to the icIol that is added last, because if the qty is allocated too early, it is not available to other iciol anymore. In other words, we want to allocate the IC's
		// *full* qtyToInvoice, but when doing so, we need to prefer those icIols whose iols actuually do have a Qtydelivered.
		// * we assume that if there is any iciol at all (i.e. !icIols.isEmpty() ), then there is also one that is not in dispute, because each in-dispute-iol comes in a pair with a
		// not-in-dispute-iol.
		I_C_InvoiceCandidate_InOutLine lastNondisputeIcIol = null;
		for (final I_C_InvoiceCandidate_InOutLine iciol : iciols)
		{
			if (InterfaceWrapperHelper.create(iciol.getM_InOutLine(), de.metas.invoicecandidate.model.I_M_InOutLine.class).isInDispute())
			{
				continue;
			}
			lastNondisputeIcIol = iciol;
		}

		// now add them, flagging the last one for special treatment (see comment above)
		for (final I_C_InvoiceCandidate_InOutLine icIol : iciols)
		{
			final boolean isLast = icIol == lastNondisputeIcIol;
			addInvoiceCandidateForInOutLine(ic, icIol, isLast);
		}

		return this;
	}

	private AggregationKey getHeaderAggregationKey(final I_C_Invoice_Candidate ic)
	{
		AggregationKey aggregationKey;
		if (alwaysUseDefaultHeaderAggregationKeyBuilder)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			final IAggregationKeyBuilder<I_C_Invoice_Candidate> defaultAggregationKeyBuilder = aggregationFactory.getDefaultAggregationKeyBuilder(
					ctx,
					I_C_Invoice_Candidate.class,
					ic.isSOTrx(),
					X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header);

			aggregationKey = defaultAggregationKeyBuilder.buildAggregationKey(ic);
		}
		else
		{
			aggregationKey = new AggregationKey(ic.getHeaderAggregationKey(), AggregationId.ofRepoIdOrNull(ic.getHeaderAggregationKeyBuilder_ID()));
		}

		//
		// Append DateInvoiced to our aggregation key
		final LocalDate dateInvoiced = computeDateInvoiced(ic);
		aggregationKey = aggregationKey.append("DateInvoiced=" + dateInvoiced);

		//
		// Append DateAcct to our aggregation key
		final LocalDate dateAcct = computeDateInvoiced(ic);
		aggregationKey = aggregationKey.append("DateAcct=" + dateAcct);

		return aggregationKey;
	}

	/**
	 * Add an Invoice Candidate to be aggregated.
	 * <p>
	 * If the InOutLine association <code>iciol</code> is not <code>null</code>, create an extended aggregation key for it; otherwise create a normal header key.
	 *
	 * @param iciol       IC-IOL association (could be <code>null</code>)
	 * @param isLastIcIol if true, then we need to allocate all the given <code>ic</code>'s remaining qtyToInvoice to the given icIol.
	 */
	private void addInvoiceCandidateForInOutLine(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@Nullable final I_C_InvoiceCandidate_InOutLine iciol,
			final boolean isLastIcIol)
	{
		final I_M_InOutLine icInOutLine = iciol == null ? null : iciol.getM_InOutLine();
		final InOutId inoutId = icInOutLine != null ? InOutId.ofRepoIdOrNull(icInOutLine.getM_InOut_ID()) : null;

		//
		// Get and parse aggregation key
		// => resolve last variables, right before invoicing
		final AggregationKey headerAggregationKey;
		{
			final AggregationKey headerAggregationKeyUnparsed = getHeaderAggregationKey(icRecord);
			final AggregationKeyEvaluationContext evalCtx = AggregationKeyEvaluationContext.builder()
					.invoiceCandidate(icRecord)
					.inoutLine(icInOutLine)
					.build();
			headerAggregationKey = headerAggregationKeyUnparsed.parse(evalCtx);
		}

		//
		// Get/Create InvoiceHeaderAndLineAggregators structure for current header aggregation key
		InvoiceHeaderAndLineAggregators headerAndAggregators = key2headerAndAggregators.get(headerAggregationKey);
		if (headerAndAggregators == null)
		{
			headerAndAggregators = new InvoiceHeaderAndLineAggregators(headerAggregationKey);
			key2headerAndAggregators.put(headerAggregationKey, headerAndAggregators);

			final InvoiceHeaderImplBuilder invoiceHeader = headerAndAggregators.getInvoiceHeader();
			addToInvoiceHeader(invoiceHeader, icRecord, inoutId);

			// task 08451: log why we create a new invoice header
			final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
			loggable.addLog("Created new InvoiceHeaderAndLineAggregators instance. current number: {}\n"
									+ "Params: ['ic'={}, 'headerAggregationKey'={}, 'inutId'={}, 'iciol'={}];\n"
									+ " ic's own headerAggregationKey = {};\n"
									+ " new headerAndAggregators = {}",
							key2headerAndAggregators.size(), icRecord, headerAggregationKey, inoutId, iciol, icRecord.getHeaderAggregationKey(), headerAndAggregators);
		}
		else
		{
			final InvoiceHeaderImplBuilder invoiceHeader = headerAndAggregators.getInvoiceHeader();
			addToInvoiceHeader(invoiceHeader, icRecord, inoutId);
		}

		//
		// Get/Create the Invoice Line aggregator
		final int invoiceCandidateAggId = icRecord.getC_Invoice_Candidate_Agg_ID();
		IAggregator lineAggregator = headerAndAggregators.getLineAggregator(invoiceCandidateAggId);
		if (lineAggregator == null)
		{
			lineAggregator = aggregationBL.createInvoiceLineAggregatorInstance(icRecord);
			headerAndAggregators.setLineAggregator(invoiceCandidateAggId, lineAggregator);
		}

		// note: even if the iol is in dispute, we will call the aggregator. Allthough we don't expect it to be invoiced there are currently different ways of "not invoicing":
		// 1. ignore them alltogether
		// 2. invoice them, but also subtract directly them in another invoice line

		final InvoiceLineAggregationRequest.Builder icAggregationRequestBuilder = InvoiceLineAggregationRequest.builder()
				.setC_Invoice_Candidate(icRecord)
				.setC_InvoiceCandidate_InOutLine(iciol);

		// different orders need to go into different invoice lines
		// and different order lines need to go into different invoice lines
		// but note: if the ic references an InOutLine (Gebinde/Packagingmaterial!), then ICs may be aggregated into one invoice line.
		// This works, because all those IC have the same C_OrderLine_ID (i.e. 0).
		// icAggregationRequest.addLineAggregationKeyElement(ic.getC_Order_ID());
		// icAggregationRequest.addLineAggregationKeyElement(ic.getC_OrderLine_ID());

		// task 08543: *do not* aggregate different ICs into one invoice line. This includes "Gebinde/Packagingmaterial!"
		// background: the user expects the invoice candidates to be transformed into invoice lines one-by-one.
		// NOTE: this shall be configured in line aggregation definition.
		// For legacy key builder we moved to de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD.buildAggregationKey(I_C_Invoice_Candidate)
		// icAggregationRequestBuilder.addLineAggregationKeyElement(ic.getC_Invoice_Candidate_ID());

		if (iciol != null)
		{
			final I_M_InOutLine inOutLine = iciol.getM_InOutLine();

			//
			// Extract relevant product attribute instances from inout line
			// Those shall be present on invoice line.
			// FIXME: this shall not be hardcoded, but handled by C_Aggregation configuration, as an AggregationItem of type Attribute
			final List<IInvoiceLineAttribute> invoiceLineAttributes = aggregationBL.extractInvoiceLineAttributes(inOutLine);
			icAggregationRequestBuilder.addInvoiceLineAttributes(invoiceLineAttributes);

			//
			// Sales iols from different inOuts shall go into different invoice lines
			// NOTE: this shall be configured in line aggregation definition.
			// For legacy key builder we moved to de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD.buildAggregationKey(I_C_Invoice_Candidate)
			// if (ic.isSOTrx())
			// {
			// icAggregationRequestBuilder.addLineAggregationKeyElement(inOutLine.getM_InOut_ID());
			// }

			// this is only relevant if iciol != null. Otherwise we allocate the full invoicable Qty anyways.
			icAggregationRequestBuilder.setAllocateRemainingQty(isLastIcIol);
		}

		//
		// Add invoice candidate to invoice line aggregator
		final IInvoiceLineAggregationRequest icAggregationRequest = icAggregationRequestBuilder.build();
		lineAggregator.addInvoiceCandidate(icAggregationRequest);
	}

	private void addToInvoiceHeader(
			@NonNull final InvoiceHeaderImplBuilder invoiceHeader,
			@NonNull final I_C_Invoice_Candidate icRecord,
			@Nullable final InOutId inoutId)
	{
		try
		{
			final BPartnerLocationAndCaptureId billBPLocationId = getBillLocationId(icRecord);

			invoiceHeader.setC_Async_Batch_ID(icRecord.getC_Async_Batch_ID());
			invoiceHeader.setAD_Org_ID(icRecord.getAD_Org_ID());
			invoiceHeader.setBillTo(getBillTo(icRecord));
			invoiceHeader.setC_BPartner_SalesRep_ID(icRecord.getC_BPartner_SalesRep_ID());
			invoiceHeader.setC_Order_ID(icRecord.getC_Order_ID());
			invoiceHeader.setC_Incoterms_ID(icRecord.getC_Incoterms_ID());
			invoiceHeader.setIncotermLocation(icRecord.getIncotermLocation());
			invoiceHeader.setPOReference(icRecord.getPOReference()); // task 07978

			if(orderEmailPropagationSysConfigRepository.isPropagateToCInvoice(ClientAndOrgId.ofClientAndOrg(icRecord.getAD_Client_ID(), icRecord.getAD_Org_ID())))
			{
				invoiceHeader.setEmail(icRecord.getEMail());
			}

			invoiceHeader.setAD_InputDataSource_ID(InputDataSourceId.ofRepoIdOrNull(icRecord.getAD_InputDataSource_ID()));
			final OrderId orderId = OrderId.ofRepoIdOrNull(icRecord.getC_Order_ID());
			if (orderId != null)
			{
				final I_C_Order order = orderDAO.getById(orderId);
				invoiceHeader.setExternalId(order.getExternalId());

				// task 12953 : prevent SalesRep_ID from being a header aggregation criteria
				// invoiceHeader.setSalesRep_ID(order.getSalesRep_ID());

			}
			invoiceHeader.setPaymentRule(icRecord.getPaymentRule());
			// why not using DateToInvoice[_Override] if available?
			// ts: DateToInvoice[_Override] is "just" the field saying from which date onwards this icRecord may be invoiced
			// tsa: true, but as far as i can see, using the Override is available could be also intuitive for user. More, in some test this logic is also assumed.
			final LocalDate dateInvoiced = computeDateInvoiced(icRecord);
			logger.debug("Setting invoiceHeader's dateInvoiced={}", dateInvoiced);
			invoiceHeader.setDateInvoiced(dateInvoiced);

			final LocalDate dateAcct = computeDateAcct(icRecord);
			logger.debug("Setting invoiceHeader's dateAcct={}", dateAcct);
			invoiceHeader.setDateAcct(dateAcct);

			// #367 Invoice candidates invoicing Pricelist not found
			// https://github.com/metasfresh/metasfresh/issues/367
			// If we know the PLV, then just go with the PLV's M_PriceList_ID (new behavior).
			// Otherwise falls back to looking up the M_PriceList_ID via M_PricingSystem, location and SOTrx (old behavior).
			// The old behavior can fail as described by #367.
			final int M_PriceList_ID;
			if (icRecord.getM_PriceList_Version_ID() > 0)
			{
				M_PriceList_ID = priceListDAO.getPriceListByPriceListVersionId(PriceListVersionId.ofRepoId(icRecord.getM_PriceList_Version_ID())).getM_PriceList_ID();
			}
			else
			{
				final BPartnerLocationAndCaptureId bpLocationId = InvoiceCandidateLocationAdapterFactory.billLocationAdapter(icRecord).getBPartnerLocationAndCaptureId();
				final PriceListId plId = priceListDAO.retrievePriceListIdByPricingSyst(
						PricingSystemId.ofRepoIdOrNull(icRecord.getM_PricingSystem_ID()),
						bpLocationId,
						SOTrx.ofBoolean(icRecord.isSOTrx()));
				if (plId == null)
				{
					final String pricingSystemName = priceListDAO.getPricingSystemName(PricingSystemId.ofRepoIdOrNull(icRecord.getM_PricingSystem_ID()));
					throw new AdempiereException(ERR_INVOICE_CAND_PRICE_LIST_MISSING_2P,
												 pricingSystemName,
												 invoiceHeader.getBillTo())
							.appendParametersToMessage()
							.setParameter("M_PricingSystem_ID", icRecord.getM_PricingSystem_ID())
							.setParameter("C_Invoice_Candidate", icRecord);
				}
				M_PriceList_ID = plId.getRepoId();
			}
			invoiceHeader.setM_PriceList_ID(M_PriceList_ID);
			// #367 end

			// 03805: set also the currency id
			invoiceHeader.setC_Currency_ID(icRecord.getC_Currency_ID());

			// 04258: set header and footer
			invoiceHeader.setDescription(icRecord.getDescriptionHeader());
			invoiceHeader.setDescriptionBottom(icRecord.getDescriptionBottom());

			invoiceHeader.setIsSOTrx(icRecord.isSOTrx());

			invoiceHeader.setTaxIncluded(invoiceCandBL.isTaxIncluded(icRecord)); // task 08541

			if (icRecord.getC_DocTypeInvoice_ID() > 0)
			{
				final I_C_DocType docTypeInvoice = docTypeDAO.getById(icRecord.getC_DocTypeInvoice_ID());
				invoiceHeader.setC_DocTypeInvoice(docTypeInvoice);
			}

			// 06630: set shipment id to header
			invoiceHeader.setM_InOut_ID(InOutId.toRepoId(inoutId));
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte).appendParametersToMessage()
					.setParameter("C_Invoice_Candidate_ID", icRecord.getC_Invoice_Candidate_ID())
					.setParameter("M_InOut_ID", InOutId.toRepoId(inoutId));
		}
	}

	private LocalDate computeDateInvoiced(@NonNull final I_C_Invoice_Candidate ic)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> {
					if (dateInvoicedParam != null)
					{
						logger.debug("computeDateInvoiced - returning aggregator's dateInvoicedParam={} as dateInvoiced", dateInvoicedParam);
					}
					return dateInvoicedParam;
				},
				() -> {
					final LocalDate result = TimeUtil.asLocalDate(ic.getPresetDateInvoiced());
					if (result != null)
					{
						logger.debug("computeDateInvoiced - returning ic's presetDateInvoiced={} as dateInvoiced", result);
					}
					return result;
				},
				() -> {
					final LocalDate result = TimeUtil.asLocalDate(ic.getDateInvoiced());
					if (result != null)
					{
						logger.debug("computeDateInvoiced - returning ic's dateInvoiced={} as dateInvoiced", result);
					}
					return result;
				},
				() -> {
					logger.debug("computeDateInvoiced - returning aggregator's today={} as dateInvoiced", today);
					return today;
				});
	}

	private LocalDate computeDateAcct(@NonNull final I_C_Invoice_Candidate ic)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> {
					if (dateAcctParam != null)
					{
						logger.debug("computeDateAcct - returning aggregator's dateAcctParam={} as dateAcct", dateAcctParam);
					}
					return dateAcctParam;
				},
				() -> {
					final LocalDate result = TimeUtil.asLocalDate(ic.getPresetDateInvoiced());
					if (result != null)
					{
						logger.debug("computeDateAcct - returning ic's presetDateInvoiced={} as dateAcct", result);
					}
					return result;
				},
				() -> {
					final LocalDate result = TimeUtil.asLocalDate(ic.getDateAcct());
					if (result != null)
					{
						logger.debug("computeDateAcct - returning ic's dateAcct={} as dateAcct", result);
					}
					return result;
				},
				() -> {
					logger.debug("computeDateAcct - falling back to aggregator's computeDateInvoiced as dateAcct");
					return computeDateInvoiced(ic);
				});
	}

	private BPartnerInfo getBillTo(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BPartnerLocationAndCaptureId bpLocationId = getBillLocationId(ic);
		final BPartnerContactId bpContactId = getBillContactId(ic, bpLocationId.getBpartnerLocationId());
		return BPartnerInfo.ofLocationAndContact(bpLocationId, bpContactId);
	}

	@NonNull
	private BPartnerLocationAndCaptureId getBillLocationId(@NonNull final I_C_Invoice_Candidate ic)
	{
		return invoiceCandBL.getBillLocationId(ic, useDefaultBillLocationAndContactIfNotOverride);
	}

	private BPartnerContactId getBillContactId(
			@NonNull final I_C_Invoice_Candidate ic,
			final BPartnerLocationId billBPLocationId)
	{
		final BPartnerContactId billContactOverrideId = InvoiceCandidateLocationAdapterFactory
				.billLocationOverrideAdapter(ic)
				.getBPartnerContactId();
		if (billContactOverrideId != null)
		{
			return billContactOverrideId;
		}

		if (useDefaultBillLocationAndContactIfNotOverride)
		{
			final User defaultBillContact = bpartnerBL.retrieveContactOrNull(RetrieveContactRequest.builder()
																					 .onlyActive(true)
																					 .contactType(ContactType.BILL_TO_DEFAULT)
																					 .bpartnerId(billBPLocationId.getBpartnerId())
																					 .bPartnerLocationId(billBPLocationId)
																					 .ifNotFound(IfNotFound.RETURN_NULL)
																					 .build());
			if (defaultBillContact != null)
			{
				return BPartnerContactId.ofRepoId(defaultBillContact.getBpartnerId(), defaultBillContact.getId().getRepoId());
			}
		}

		return InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.getBPartnerContactId();
	}

	public List<IInvoiceHeader> aggregate()
	{
		final List<IInvoiceHeader> invoiceHeaders = new ArrayList<>();

		for (final InvoiceHeaderAndLineAggregators headerAndAggregators : key2headerAndAggregators.values())
		{
			final IInvoiceHeader invoiceHeader = aggregate(headerAndAggregators);

			final ILoggable loggable = Loggables.get();
			if (!Loggables.isNull(loggable))
			{
				loggable.addLog("Aggregated InvoiceHeaderAndLineAggregators=" + headerAndAggregators + "; result IInvoiceHeader=" + invoiceHeader);
			}

			// Skip it if the invoice could not be aggregated for some reason
			if (invoiceHeader == null)
			{
				continue;
			}

			invoiceHeaders.add(invoiceHeader);
		}

		return invoiceHeaders;
	}

	/**
	 * Aggregates all invoice lines and populate {@link IInvoiceHeader}'s lines.
	 *
	 * @return {@link IInvoiceHeader} or <code>null</code>
	 */
	private IInvoiceHeader aggregate(final InvoiceHeaderAndLineAggregators headerAndAggregators)
	{
		final InvoiceHeaderImplBuilder invoiceHeaderBuilder = headerAndAggregators.getInvoiceHeader();
		final InvoiceHeaderImpl invoiceHeader = invoiceHeaderBuilder.build();

		final ArrayList<IAggregator> lineAggregators = new ArrayList<>(headerAndAggregators.getLineAggregators());

		//
		// Initialize invoice header's lines
		final List<IInvoiceCandAggregate> lines = new ArrayList<>();
		invoiceHeader.setLines(lines);

		//
		// Iterate invoice line aggregators and ask them to create the line aggregates
		for (final IAggregator lineAggregator : lineAggregators)
		{
			final List<IInvoiceCandAggregate> invoiceCandAggregates = lineAggregator.aggregate();
			lines.addAll(invoiceCandAggregates);
		}

		// If there are no lines, there is no point to have this invoice header, so discard it
		if (lines.isEmpty())
		{
			return null;
		}

		// Set Invoice's DocBaseType
		setDocBaseType(invoiceHeader);

		return invoiceHeader;
	}

	// NOTE: not static because we are using services
	private/* static */void setDocBaseType(final InvoiceHeaderImpl invoiceHeader)
	{
		final boolean invoiceIsSOTrx = invoiceHeader.isSOTrx();
		final I_C_DocType invoiceDocType = invoiceHeader.getC_DocTypeInvoice();
		final Money totalAmt = invoiceHeader.calculateTotalNetAmtFromLines();

		final InvoiceDocBaseType docBaseType;

		//
		// Case: Invoice DocType was preset
		if (invoiceHeader.getC_DocTypeInvoice() != null)
		{
			Check.assume(invoiceIsSOTrx == invoiceDocType.isSOTrx(), "InvoiceHeader's IsSOTrx={} shall match document type {}", invoiceIsSOTrx, invoiceDocType);

			final InvoiceDocBaseType invoiceDocBaseType = InvoiceDocBaseType.ofCode(invoiceDocType.getDocBaseType());

			// handle negative amounts: switch the base type to credit memo, based on the IsSOTrx
			if (totalAmt.signum() < 0)
			{
				docBaseType = flipDocBaseType(invoiceDocBaseType, invoiceIsSOTrx);
			}
			else
			{
				docBaseType = invoiceDocBaseType;
			}
		}
		//
		// Case: no invoice DocType was set
		// We need to find out the DocBaseType based on Total Amount and IsSOTrx
		else
		{
			if (invoiceIsSOTrx)
			{
				if (totalAmt.signum() < 0)
				{
					// AR Credit Memo Invoice (sales)
					docBaseType = InvoiceDocBaseType.CustomerCreditMemo;
				}
				else
				{
					// Regular AR Invoice (sales)
					docBaseType = InvoiceDocBaseType.CustomerInvoice;
				}
			}
			else
			{
				if (totalAmt.signum() < 0)
				{
					docBaseType = InvoiceDocBaseType.VendorCreditMemo;
				}
				else
				{
					docBaseType = InvoiceDocBaseType.VendorInvoice;
				}
			}
		}

		//
		// NOTE: in credit memos, amount are positive but the invoice effect is reversed
		if (totalAmt.signum() < 0)
		{
			invoiceHeader.negateAllLineAmounts();
		}

		invoiceHeader.setDocBaseType(docBaseType);
		invoiceHeader.setPaymentTermId(getPaymentTermId(invoiceHeader).orElse(null));
	}

	private InvoiceDocBaseType flipDocBaseType(final InvoiceDocBaseType docBaseType, final boolean invoiceIsSOTrx)
	{
		if (docBaseType.isCreditMemo())
		{
			if (invoiceIsSOTrx)
			{
				return InvoiceDocBaseType.CustomerInvoice;
			}
			else
			{
				return InvoiceDocBaseType.VendorInvoice;
			}
		}

		if (invoiceIsSOTrx)
		{
			return InvoiceDocBaseType.CustomerCreditMemo;
		}

		return InvoiceDocBaseType.VendorCreditMemo;
	}

	private Optional<PaymentTermId> getPaymentTermId(final InvoiceHeaderImpl invoiceHeader)
	{
		final Optional<PaymentTermId> paymentTermId = extractPaymentTermIdFromLines(invoiceHeader);
		if (paymentTermId.isPresent())
		{
			return paymentTermId;
		}
		// task 07242: setting the payment term from the given bill partner. Note that C_BP_Group has no payment term columns, so we don't need a BL to fall back to C_BP_Group
		return bpartnerBL.getPaymentTermIdForBPartner(invoiceHeader.getBillTo().getBpartnerId(), SOTrx.ofBoolean(invoiceHeader.isSOTrx()));
	}

	/**
	 * extract C_PaymentTerm_ID from invoice candidate
	 */
	private Optional<PaymentTermId> extractPaymentTermIdFromLines(@NonNull final InvoiceHeaderImpl invoiceHeader)
	{
		final List<IInvoiceCandAggregate> lines = invoiceHeader.getLines();
		if (lines == null || lines.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableMap<Optional<PaymentTermId>, IInvoiceLineRW> uniquePaymentTermLines = mapUniqueIInvoiceLineRWPerPaymentTerm(lines);
		// extract payment term if all lines have same C_PaymentTerm_ID
		if (uniquePaymentTermLines.size() == 1)
		{
			final ImmutableSet<Optional<PaymentTermId>> ids = uniquePaymentTermLines.keySet();
			return ids.iterator().next();
		}

		return Optional.empty();
	}

	private ImmutableMap<Optional<PaymentTermId>, IInvoiceLineRW> mapUniqueIInvoiceLineRWPerPaymentTerm(@NonNull final List<IInvoiceCandAggregate> lines)
	{
		final List<IInvoiceLineRW> invoiceLinesRW = new ArrayList<>();
		lines.forEach(lineAgg -> invoiceLinesRW.addAll(lineAgg.getAllLines()));

		return invoiceLinesRW.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(line -> PaymentTermId.optionalOfRepoId(line.getC_PaymentTerm_ID())));
	}
}
