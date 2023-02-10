package de.metas.material;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_InOut;

@AllArgsConstructor
public enum MovementType implements ReferenceListAwareEnum
{
	CustomerShipment(X_M_InOut.MOVEMENTTYPE_CustomerShipment),
	CustomerReturns(X_M_InOut.MOVEMENTTYPE_CustomerReturns),
	VendorReceipts(X_M_InOut.MOVEMENTTYPE_VendorReceipts),
	VendorReturns(X_M_InOut.MOVEMENTTYPE_VendorReturns),
	InventoryOut(X_M_InOut.MOVEMENTTYPE_InventoryOut),
	InventoryIn(X_M_InOut.MOVEMENTTYPE_InventoryIn),
	MovementFrom(X_M_InOut.MOVEMENTTYPE_MovementFrom),
	MovementTo(X_M_InOut.MOVEMENTTYPE_MovementTo),
	ProductionPlus(X_M_InOut.MOVEMENTTYPE_ProductionPlus),
	ProductionMinus(X_M_InOut.MOVEMENTTYPE_ProductionMinus),
	WorkOrderPlus(X_M_InOut.MOVEMENTTYPE_WorkOrderPlus),
	WorkOrderMinus(X_M_InOut.MOVEMENTTYPE_WorkOrderMinus),
	;

	@Getter @NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<MovementType> index = ReferenceListAwareEnums.index(values());

	public static MovementType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isMaterialReturn() {return CustomerReturns.equals(this) || VendorReturns.equals(this);}

	public static boolean isMaterialReturn(@NonNull final String movementType) {return ofCode(movementType).isMaterialReturn();}
}
