package de.metas.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum DocBaseType implements ReferenceListAwareEnum
{
	GLJournal(X_C_DocType.DOCBASETYPE_GLJournal),
	GLDocument(X_C_DocType.DOCBASETYPE_GLDocument),
	APInvoice(X_C_DocType.DOCBASETYPE_APInvoice),
	APPayment(X_C_DocType.DOCBASETYPE_APPayment),
	ARInvoice(X_C_DocType.DOCBASETYPE_ARInvoice),
	ARReceipt(X_C_DocType.DOCBASETYPE_ARReceipt),
	SalesOrder(X_C_DocType.DOCBASETYPE_SalesOrder),
	ARProFormaInvoice(X_C_DocType.DOCBASETYPE_ARProFormaInvoice),
	MaterialDelivery(X_C_DocType.DOCBASETYPE_MaterialDelivery),
	MaterialReceipt(X_C_DocType.DOCBASETYPE_MaterialReceipt),
	MaterialMovement(X_C_DocType.DOCBASETYPE_MaterialMovement),
	PurchaseOrder(X_C_DocType.DOCBASETYPE_PurchaseOrder),
	PurchaseRequisition(X_C_DocType.DOCBASETYPE_PurchaseRequisition),
	MaterialPhysicalInventory(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory),
	APCreditMemo(X_C_DocType.DOCBASETYPE_APCreditMemo),
	ARCreditMemo(X_C_DocType.DOCBASETYPE_ARCreditMemo),
	BankStatement(X_C_DocType.DOCBASETYPE_BankStatement),
	CashJournal(X_C_DocType.DOCBASETYPE_CashJournal),
	PaymentAllocation(X_C_DocType.DOCBASETYPE_PaymentAllocation),
	MatchInvoice(X_C_DocType.DOCBASETYPE_MatchInvoice),
	MatchPO(X_C_DocType.DOCBASETYPE_MatchPO),
	ProjectIssue(X_C_DocType.DOCBASETYPE_ProjectIssue),
	MaintenanceOrder(X_C_DocType.DOCBASETYPE_MaintenanceOrder),
	ManufacturingOrder(X_C_DocType.DOCBASETYPE_ManufacturingOrder),
	QualityOrder(X_C_DocType.DOCBASETYPE_QualityOrder),
	Payroll(X_C_DocType.DOCBASETYPE_Payroll),
	DistributionOrder(X_C_DocType.DOCBASETYPE_DistributionOrder),
	ManufacturingCostCollector(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector),
	GehaltsrechnungAngestellter(X_C_DocType.DOCBASETYPE_GehaltsrechnungAngestellter),
	InterneRechnungLieferant(X_C_DocType.DOCBASETYPE_InterneRechnungLieferant),
	ShipperTransportation(X_C_DocType.DOCBASETYPE_ShipperTransportation),
	ShippingNotification(X_C_DocType.DOCBASETYPE_ShippingNotification),
	CustomerContract(X_C_DocType.DOCBASETYPE_CustomerContract),
	DunningDoc(X_C_DocType.DOCBASETYPE_DunningDoc),
	ShipmentDeclaration(X_C_DocType.DOCBASETYPE_ShipmentDeclaration),
	ShipmentDeclarationCorrection(X_C_DocType.DOCBASETYPE_ShipmentDeclarationCorrection),
	CustomsInvoice(X_C_DocType.DOCBASETYPE_CustomsInvoice),
	ServiceRepairOrder(X_C_DocType.DOCBASETYPE_ServiceRepairOrder),
	RemittanceAdvice(X_C_DocType.DOCBASETYPE_RemittanceAdvice),
	BillOfMaterialVersion(X_C_DocType.DOCBASETYPE_BOMFormula),
	CostRevaluation(X_C_DocType.DOCBASETYPE_CostRevaluation),
	ModularOrder(X_C_DocType.DOCBASETYPE_ModularOrder),
	;

	public static final int AD_REFERENCE_ID = X_C_DocType.DOCBASETYPE_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<DocBaseType> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@JsonCreator
	public static DocBaseType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static DocBaseType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static boolean equals(@Nullable final DocBaseType o1, @Nullable final DocBaseType o2) {return Objects.equals(o1, o2);}

	@JsonValue
	public String getCode()
	{
		return code;
	}

	public boolean isSOTrx()
	{
		return SalesOrder.equals(this)
				|| MaterialDelivery.equals(this)
				|| ARInvoice.equals(this)
				|| ARCreditMemo.equals(this)
				|| ARReceipt.equals(this);
	}

	public boolean isSalesOrder() {return SalesOrder.equals(this);}

	public boolean isPurchaseOrder() {return PurchaseOrder.equals(this);}

	public boolean isSalesInvoice() {return ARInvoice.equals(this);}

	public boolean isARCreditMemo() {return ARCreditMemo.equals(this);}

	public boolean isDunningDoc(){return DunningDoc.equals((this));}
}
