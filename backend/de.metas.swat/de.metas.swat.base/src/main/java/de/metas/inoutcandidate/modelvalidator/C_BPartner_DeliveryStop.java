package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.interfaces.I_C_BPartner;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.i18n.AdMessageKey;

/**
 * Interceptor on C_BPartner for the manual delivery/order stop feature (gh#28631).
 * <p>
 * When {@code IsDeliveryStop} is set to Y, creates an {@link I_M_Shipment_Constraint}
 * record with {@code IsManual=Y}. When set back to N, deactivates it.
 * Also syncs {@code DeliveryStopReason} and validates it is non-empty when blocked.
 */
@Interceptor(I_C_BPartner.class)
public class C_BPartner_DeliveryStop
{
	private static final AdMessageKey MSG_DeliveryStopReasonRequired = AdMessageKey.of("DeliveryStopReasonRequired");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsDeliveryStop, I_C_BPartner.COLUMNNAME_DeliveryStopReason })
	public void validateDeliveryStopReason(@NonNull final I_C_BPartner bpartner)
	{
		if (bpartner.isDeliveryStop() && Check.isBlank(bpartner.getDeliveryStopReason()))
		{
			throw new AdempiereException(MSG_DeliveryStopReasonRequired)
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsDeliveryStop, I_C_BPartner.COLUMNNAME_DeliveryStopReason })
	public void syncShipmentConstraint(@NonNull final I_C_BPartner bpartner)
	{
		if (bpartner.isDeliveryStop())
		{
			createOrUpdateManualConstraint(bpartner);
		}
		else
		{
			deactivateManualConstraint(bpartner);
		}
	}

	private void createOrUpdateManualConstraint(@NonNull final I_C_BPartner bpartner)
	{
		final int bpartnerId = bpartner.getC_BPartner_ID();
		final I_M_Shipment_Constraint existing = retrieveManualConstraint(bpartnerId);

		if (existing != null)
		{
			existing.setDeliveryStopReason(bpartner.getDeliveryStopReason());
			if (!existing.isActive())
			{
				existing.setIsActive(true);
			}
			InterfaceWrapperHelper.save(existing);
		}
		else
		{
			final I_M_Shipment_Constraint constraint = InterfaceWrapperHelper.newInstance(I_M_Shipment_Constraint.class);
			constraint.setBill_BPartner_ID(bpartnerId);
			constraint.setIsDeliveryStop(true);
			constraint.setIsManual(true);
			constraint.setDeliveryStopReason(bpartner.getDeliveryStopReason());
			constraint.setAD_Org_ID(bpartner.getAD_Org_ID());
			InterfaceWrapperHelper.save(constraint);
		}
	}

	private void deactivateManualConstraint(@NonNull final I_C_BPartner bpartner)
	{
		final int bpartnerId = bpartner.getC_BPartner_ID();
		final I_M_Shipment_Constraint existing = retrieveManualConstraint(bpartnerId);

		if (existing != null && existing.isActive())
		{
			existing.setIsActive(false);
			InterfaceWrapperHelper.save(existing);
		}
	}

	private I_M_Shipment_Constraint retrieveManualConstraint(final int bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, true)
				.create()
				.firstOnly(I_M_Shipment_Constraint.class);
	}
}
