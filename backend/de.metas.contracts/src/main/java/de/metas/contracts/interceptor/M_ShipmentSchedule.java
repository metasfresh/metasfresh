/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.interceptor;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import java.math.BigDecimal;

/**
 * Updates the {@link I_C_SubscriptionProgress} record's status on shipment schedule changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09869_Enable_subscription_contracts_%28104949638829%29
 */
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	public static M_ShipmentSchedule INSTANCE = new M_ShipmentSchedule();

	private M_ShipmentSchedule()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
					I_M_ShipmentSchedule.COLUMNNAME_QtyDelivered,
					I_M_ShipmentSchedule.COLUMNNAME_QtyPickList })
	public void updateSubScriptionProgress(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_SubscriptionProgress subscriptionProgress = getSubscriptionRecordOrNull(shipmentSchedule);
		if (subscriptionProgress == null)
		{
			return;
		}

		final BigDecimal qtyDelivered = shipmentSchedule.getQtyDelivered();
		if (qtyDelivered.compareTo(subscriptionProgress.getQty()) >= 0)
		{
			subscriptionProgress.setStatus(X_C_SubscriptionProgress.STATUS_Done);
			InterfaceWrapperHelper.save(subscriptionProgress);
			return;
		}

		final BigDecimal qtyPickList = shipmentSchedule.getQtyPickList();
		if (qtyPickList.signum() > 0)
		{
			subscriptionProgress.setStatus(X_C_SubscriptionProgress.STATUS_InPicking);
			InterfaceWrapperHelper.save(subscriptionProgress);
			return;
		}

		subscriptionProgress.setStatus(X_C_SubscriptionProgress.STATUS_Open);
		InterfaceWrapperHelper.save(subscriptionProgress);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void updateSubScriptionProgressAfterDelete(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_SubscriptionProgress subscriptionProgress = getSubscriptionRecordOrNull(shipmentSchedule);
		if (subscriptionProgress == null)
		{
			return;
		}

		subscriptionProgress.setProcessed(false);
		subscriptionProgress.setM_ShipmentSchedule_ID(0);
		subscriptionProgress.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		InterfaceWrapperHelper.save(subscriptionProgress);
	}

	private I_C_SubscriptionProgress getSubscriptionRecordOrNull(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final TableRecordReference ref = TableRecordReference.of(shipmentSchedule.getAD_Table_ID(), shipmentSchedule.getRecord_ID());
		if (!I_C_SubscriptionProgress.Table_Name.equals(ref.getTableName()))
		{
			return null;
		}

		final I_C_SubscriptionProgress subscriptionProgress = ref.getModel(
				InterfaceWrapperHelper.getContextAware(shipmentSchedule),
				I_C_SubscriptionProgress.class);
		return subscriptionProgress;
	}
}
