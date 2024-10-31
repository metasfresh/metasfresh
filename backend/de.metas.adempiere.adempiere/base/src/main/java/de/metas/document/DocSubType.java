package de.metas.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum DocSubType implements ReferenceListAwareEnum
{
	ANY("ANY"),
	NONE("NONE"),
	OnCreditOrder(X_C_DocType.DOCSUBTYPE_OnCreditOrder),
	POSOrder(X_C_DocType.DOCSUBTYPE_POSOrder),
	WarehouseOrder(X_C_DocType.DOCSUBTYPE_WarehouseOrder),
	StandardOrder(X_C_DocType.DOCSUBTYPE_StandardOrder),
	Proposal(X_C_DocType.DOCSUBTYPE_Proposal),
	Quotation(X_C_DocType.DOCSUBTYPE_Quotation),
	ReturnMaterial(X_C_DocType.DOCSUBTYPE_ReturnMaterial),
	PrepayOrder(X_C_DocType.DOCSUBTYPE_PrepayOrder),
	Provisionskorrektur(X_C_DocType.DOCSUBTYPE_Provisionskorrektur),
	CommissionSettlement(X_C_DocType.DOCSUBTYPE_CommissionSettlement),
	FlatFee(X_C_DocType.DOCSUBTYPE_FlatFee),
	HoldingFee(X_C_DocType.DOCSUBTYPE_HoldingFee),
	Subscription(X_C_DocType.DOCSUBTYPE_Subscription),
	AQ(X_C_DocType.DOCSUBTYPE_AQ),
	AP(X_C_DocType.DOCSUBTYPE_AP),
	DeliveryDiff(X_C_DocType.DOCSUBTYPE_GS_Lieferdifferenz),
	PriceDiff(X_C_DocType.DOCSUBTYPE_GS_Preisdifferenz),
	QualityInspection(X_C_DocType.DOCSUBTYPE_QualityInspection),
	EmptiesReceipt(X_C_DocType.DOCSUBTYPE_Leergutanlieferung),
	ProductReceipt(X_C_DocType.DOCSUBTYPE_Produktanlieferung),
	ProductShipment(X_C_DocType.DOCSUBTYPE_Produktauslieferung),
	EmptyShipment(X_C_DocType.DOCSUBTYPE_Leergutausgabe),
	GSRetoure(X_C_DocType.DOCSUBTYPE_GS_Retoure),
	VendorInvoice(X_C_DocType.DOCSUBTYPE_VendorInvoice),
	DownPayment(X_C_DocType.DOCSUBTYPE_DownPayment),
	SaldoCorrection(X_C_DocType.DOCSUBTYPE_Saldokorektur),
	InternalUseInventory(X_C_DocType.DOCSUBTYPE_InternalUseInventory),
	RefundInvoice(X_C_DocType.DOCSUBTYPE_Rueckverguetungsrechnung),
	RefundCreditMemo(X_C_DocType.DOCSUBTYPE_Rueckverguetungsgutschrift),
	Healthcare_CH_GM(X_C_DocType.DOCSUBTYPE_Healthcare_CH_GM),
	Healthcare_CH_EA(X_C_DocType.DOCSUBTYPE_Healthcare_CH_EA),
	Healthcare_CH_KV(X_C_DocType.DOCSUBTYPE_Healthcare_CH_KV),
	Healthcare_CH_KT(X_C_DocType.DOCSUBTYPE_Healthcare_CH_KT),
	AggregatedHUInventory(X_C_DocType.DOCSUBTYPE_AggregatedHUInventory),
	SingleHUInventory(X_C_DocType.DOCSUBTYPE_SingleHUInventory),
	NAR(X_C_DocType.DOCSUBTYPE_NAR),
	Cashbook(X_C_DocType.DOCSUBTYPE_Cashbook),
	Bankstatement(X_C_DocType.DOCSUBTYPE_Bankstatement),
	VirtualInventory(X_C_DocType.DOCSUBTYPE_VirtualInventory),
	SR(X_C_DocType.DOCSUBTYPE_SR),
	Requisition(X_C_DocType.DOCSUBTYPE_Requisition),
	FrameAgrement(X_C_DocType.DOCSUBTYPE_FrameAgrement),
	OrderCall(X_C_DocType.DOCSUBTYPE_OrderCall),
	Mediated(X_C_DocType.DOCSUBTYPE_Mediated),
	MEDIATED_COMMISSION(X_C_DocType.DOCSUBTYPE_RD),
	CostEstimate(X_C_DocType.DOCSUBTYPE_CostEstimate),
	CreditorPostCharge(X_C_DocType.DOCSUBTYPE_KreditorenNachbelastung),
	LICENSE_COMMISSION (X_C_DocType.DOCSUBTYPE_LS),
	PaymentServiceProviderInvoice(X_C_DocType.DOCSUBTYPE_PaymentServiceProviderInvoice),
	CallOrder(X_C_DocType.DOCSUBTYPE_CallOrder),
	InterimInvoice(X_C_DocType.DOCSUBTYPE_InterimInvoice),
	Withholding(X_C_DocType.DOCSUBTYPE_Withholding),
	InternalVendorInvoice(X_C_DocType.DOCSUBTYPE_InternalVendorInvoice),
	DeliveryInstruction(X_C_DocType.DOCSUBTYPE_DeliveryInstruction),
	InventoryShortageDocument(X_C_DocType.DOCSUBTYPE_InventoryShortageDocument),
	InventoryOverageDocument(X_C_DocType.DOCSUBTYPE_InventoryOverageDocument),
	CorrectionInvoice(X_C_DocType.DOCSUBTYPE_CorrectionInvoice),
	Provision(X_C_DocType.DOCSUBTYPE_Provision),
	Proforma(X_C_DocType.DOCSUBTYPE_ProForma),
	FinalInvoice(X_C_DocType.DOCSUBTYPE_FinalInvoice),
	FinalCreditMemo(X_C_DocType.DOCSUBTYPE_FinalCreditMemo),
	DefinitiveInvoice(X_C_DocType.DOCSUBTYPE_DefinitiveInvoice),
	DefinitiveCreditMemo(X_C_DocType.DOCSUBTYPE_DefinitiveCreditMemo)
	;

	public static final ReferenceId AD_REFERENCE_ID = ReferenceId.ofRepoId(X_C_DocType.DOCSUBTYPE_AD_Reference_ID);

	private static final ReferenceListAwareEnums.ValuesIndex<DocSubType> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@JsonCreator
	public static DocSubType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static DocSubType ofNullableCode(@Nullable final String code)
	{
		final DocSubType docSubType = index.ofNullableCode(code);
		return docSubType != null ? docSubType : DocSubType.NONE;
	}

	public static boolean equals(@NonNull final DocSubType o1, @NonNull final DocSubType o2) { return Objects.equals(o1, o2); }

	@JsonValue
	public String getCode()
	{
		return code;
	}

	@Nullable
	public String getNullableCode()
	{
		return isAnyOrNone() ? null : code;
	}

	public boolean isAny() { return ANY.equals(this); }
	public boolean isNone() { return NONE.equals(this); }
	public boolean isAnyOrNone() { return isAny() || isNone(); }
	public boolean isProforma() { return Proforma.equals(this); }
	public boolean isFinalInvoice() { return FinalInvoice.equals(this); }
	public boolean isFinalCreditMemo() { return FinalCreditMemo.equals(this); }
	public boolean IsInterimInvoice() { return DownPayment.equals(this); }
	public boolean isDefinitiveInvoice() { return DefinitiveInvoice.equals(this); }
	public boolean isDefinitiveCreditMemo() { return DefinitiveCreditMemo.equals(this); }
	public boolean isPrepay() { return PrepayOrder.equals(this); }
	public boolean isInternalVendorInvoice() { return InternalVendorInvoice.equals(this); }
	public boolean isDeliveryInstruction() { return DeliveryInstruction.equals(this); }
	public boolean isCallOrder() { return CallOrder.equals(this); }
	public boolean isMediated() { return Mediated.equals(this); }
	public boolean isRequisition() { return Requisition.equals(this); }
}
