package de.metas.inoutcandidate.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintService;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Interceptor on C_BPartner for the manual delivery/order stop feature (gh#28631).
 * <p>
 * Thin dispatcher: forwards BPartner change events to {@link ShipmentConstraintService}.
 * When {@code IsDeliveryStop=Y}, the service creates or re-activates the manual
 * {@code M_Shipment_Constraint} row mirroring the BPartner. When set back to N,
 * the service deactivates that row.
 * <p>
 * The mandatory-reason rule lives at the {@code AD_Column.MandatoryLogic} layer
 * ({@code @IsDeliveryStop/N@=Y}), not here.
 */
@Interceptor(I_C_BPartner.class)
@Component
public class C_BPartner_DeliveryStop
{
	private final ShipmentConstraintService shipmentConstraintService;

	public C_BPartner_DeliveryStop(@NonNull final ShipmentConstraintService shipmentConstraintService)
	{
		this.shipmentConstraintService = shipmentConstraintService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsDeliveryStop, I_C_BPartner.COLUMNNAME_DeliveryStopReason })
	public void syncShipmentConstraint(@NonNull final I_C_BPartner bpartner)
	{
		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());

		if (bpartner.isDeliveryStop())
		{
			shipmentConstraintService.createOrUpdateManualDeliveryStop(
					billBPartnerId,
					OrgId.ofRepoId(bpartner.getAD_Org_ID()),
					bpartner.getDeliveryStopReason());
		}
		else
		{
			shipmentConstraintService.deactivateManualDeliveryStop(billBPartnerId);
		}
	}
}
