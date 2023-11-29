package de.metas.invoicecandidate.spi.impl;

import de.metas.bpartner.service.BPartnerInfo;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.util.Iterator;
import java.util.Properties;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_Invoice_Candidate} from {@link I_C_OLCand}.
 *
 * Please note:
 * <ul>
 * <li>only those {@link I_C_OLCand}s are handled which have {@link InvoiceCandidate_Constants#DATA_DESTINATION_INTERNAL_NAME} as their destination datasource
 * </ul>
 */
public class C_OLCand_Handler extends AbstractInvoiceCandidateHandler
{
	private final C_OLCand_HandlerDAO dao = new C_OLCand_HandlerDAO();
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(@NonNull final Object model)
	{
		final I_C_OLCand olCandRecord = create(model, I_C_OLCand.class);

		return isEligibleForInvoiceCandidateCreate(olCandRecord) 
				? CandidatesAutoCreateMode.CREATE_CANDIDATES 
				: CandidatesAutoCreateMode.DONT;
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OLCand.Table_Name;
	}

	@Override
	public Iterator<I_C_OLCand> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return dao.retrieveMissingCandidatesQuery(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setLimit(limit)
				.create()
				.iterate(I_C_OLCand.class);
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_C_OLCand olCand = request.getModel(I_C_OLCand.class);

		//
		// Make sure the OL_Cand is eligible for creating invoice candidates
		if (!isEligibleForInvoiceCandidateCreate(olCand))
		{
			return InvoiceCandidateGenerateResult.of(this);
		}

		final I_C_Invoice_Candidate ic = createInvoiceCandidateForOLCand(olCand);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	private boolean isEligibleForInvoiceCandidateCreate(final I_C_OLCand olCand)
	{
		final Properties ctx = getCtx(olCand);
		final String trxName = getTrxName(olCand);
		return dao.retrieveMissingCandidatesQuery(ctx, trxName)
				.addEqualsFilter(I_C_OLCand.COLUMN_C_OLCand_ID, olCand.getC_OLCand_ID())
				.create()
				.anyMatch();
	}

	private I_C_Invoice_Candidate createInvoiceCandidateForOLCand(@NonNull final I_C_OLCand olcRecord)
	{
		// services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final Properties ctx = getCtx(olcRecord);
		Check.assume(Env.getAD_Client_ID(ctx) == olcRecord.getAD_Client_ID(), "AD_Client_ID of {} and of its Ctx are the same", olcRecord);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class, olcRecord);

		// org id
		final OrgId orgId = OrgId.ofRepoId(olcRecord.getAD_Org_ID());
		ic.setAD_Org_ID(orgId.getRepoId());

		ic.setC_ILCandHandler(getHandlerRecord());

		ic.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name));
		ic.setRecord_ID(olcRecord.getC_OLCand_ID());

		ic.setPOReference(olcRecord.getPOReference());
		// product
		final ProductId productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olcRecord);
		ic.setM_Product_ID(ProductId.toRepoId(productId));

		// charge
		final int chargeId = olcRecord.getC_Charge_ID();
		ic.setC_Charge_ID(chargeId);

		setOrderedData(ic, olcRecord);

		ic.setQtyToInvoice(ZERO); // to be computed

		ic.setInvoicableQtyBasedOn(olcRecord.getInvoicableQtyBasedOn());
		ic.setM_PricingSystem_ID(olcRecord.getM_PricingSystem_ID());
		ic.setPriceActual(olcRecord.getPriceActual());
		ic.setPrice_UOM_ID(olCandEffectiveValuesBL.getEffectiveUomId(olcRecord).getRepoId()); // 07090 when we set PriceActual, we shall also set PriceUOM.

		ic.setPriceEntered(olcRecord.getPriceEntered()); // cg : task 04917
		ic.setDiscount(olcRecord.getDiscount());
		ic.setC_Currency_ID(olcRecord.getC_Currency_ID());
		// ic.setC_ConversionType_ID(C_ConversionType_ID); // N/A

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(olCandEffectiveValuesBL.getBuyerPartnerInfo(olcRecord));

		ic.setDescription(olcRecord.getDescription());

		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate); // Immediate

		// 04285: set header and footer

		ic.setDescriptionBottom(olcRecord.getDescriptionBottom());
		ic.setDescriptionHeader(olcRecord.getDescriptionHeader());

		// 05265
		ic.setIsSOTrx(true);

		ic.setPresetDateInvoiced(olcRecord.getPresetDateInvoiced());
		if (olcRecord.getC_DocTypeInvoice_ID() > 0)
		{
			ic.setC_DocTypeInvoice_ID(olcRecord.getC_DocTypeInvoice_ID());
		}
		else
		{
			setDefaultInvoiceDocType(ic);
		}
		
		// 07442 activity and tax
		final ActivityId activityId = Services.get(IProductActivityProvider.class).getActivityForAcct(
				ClientId.ofRepoId(olcRecord.getAD_Client_ID()),
				OrgId.ofRepoId(olcRecord.getAD_Org_ID()),
				productId);
		ic.setC_Activity_ID(ActivityId.toRepoId(activityId));

		final BPartnerInfo shipToPartnerInfo = olCandEffectiveValuesBL
				.getDropShipPartnerInfo(olcRecord)
				.orElseGet(() -> olCandEffectiveValuesBL.getBuyerPartnerInfo(olcRecord));
		final VatCodeId vatCodeId = null;

		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final TaxId taxId = taxBL.getTaxNotNull(
				ic, // model
				TaxCategoryId.ofRepoIdOrNull(olcRecord.getC_TaxCategory_ID()),
				ProductId.toRepoId(productId),
				CoalesceUtil.coalesceNotNull(olcRecord.getDatePromised_Override(), olcRecord.getDatePromised(), olcRecord.getPresetDateInvoiced()),
				orgId,
				(WarehouseId)null,
				shipToPartnerInfo.toBPartnerLocationAndCaptureId(),
				SOTrx.SALES,
				vatCodeId);
		ic.setC_Tax_ID(taxId.getRepoId());

		ic.setExternalLineId(olcRecord.getExternalLineId());
		ic.setExternalHeaderId(olcRecord.getExternalHeaderId());

		ic.setAD_InputDataSource_ID(olcRecord.getAD_InputDataSource_ID());

		ic.setM_SectionCode_ID(olcRecord.getM_SectionCode_ID());
		ic.setC_Auction_ID(olcRecord.getC_Auction_ID());

		olcRecord.setProcessed(true);
		saveRecord(olcRecord);

		return ic;
	}

	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * <ul>
	 * <li>QtyEntered := C_OLCand.Qty
	 * <li>C_UOM_ID := C_OLCand's effective UOM
	 * <li>QtyOrdered := C_OLCand.Qty's converted to effective product's UOM
	 * <li>DateOrdered := C_OLCand.DateCandidate
	 * <li>C_Order_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = getOLCand(ic);
		setOrderedData(ic, olc);
	}

	private void setOrderedData(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final I_C_OLCand olc)
	{
		ic.setDateOrdered(olc.getDateCandidate());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final Quantity olCandQuantity = Quantity.of(olc.getQtyEntered(), olCandEffectiveValuesBL.getC_UOM_Effective(olc));
		ic.setQtyEntered(olCandQuantity.toBigDecimal());
		ic.setC_UOM_ID(UomId.toRepoId(olCandQuantity.getUomId()));

		final ProductId productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olc);
		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(olCandQuantity, productId);

		ic.setQtyOrdered(qtyInProductUOM.toBigDecimal());
	}

	private I_C_OLCand getOLCand(@NonNull final I_C_Invoice_Candidate ic)
	{
		return TableRecordCacheLocal.getReferencedValue(ic, I_C_OLCand.class);
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate ic)
	{
		ic.setQtyDelivered(ic.getQtyOrdered()); // when changing this, make sure to threat ProductType.Service specially
		ic.setQtyDeliveredInUOM(ic.getQtyEntered());

		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(ic.getAD_Org_ID()));
		final I_C_OLCand olc = getOLCand(ic);
		final IPricingResult pricingResult = Services.get(IOLCandBL.class).computePriceActual(
				olc,
				null,
				PricingSystemId.NULL,
				TimeUtil.asLocalDate(olc.getDateCandidate(), timeZone));

		return PriceAndTax.builder()
				.priceUOMId(pricingResult.getPriceUomId())
				.priceActual(pricingResult.getPriceStd())
				.taxIncluded(pricingResult.isTaxIncluded())
				.invoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn())
				.build();
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_OLCand olc = getOLCand(ic);

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(olCandEffectiveValuesBL.getBuyerPartnerInfo(olc));
	}
}
