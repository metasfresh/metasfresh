package de.metas.picking.api;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.picking.model.I_M_PickingSlot;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

public interface IPickingSlotDAO extends ISingletonService
{
	/**
	 * Retrieve all picking slots for current tenant/client.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return list of picking slots
	 */
	List<I_M_PickingSlot> retrievePickingSlots(Properties ctx, String trxName);

	/**
	 * Retrieve all {@link I_M_PickingSlot}s and filter them according to the given {@code query}.<br>
	 * The query's bPartner and location properties are forwarded to {@link IPickingSlotBL#isAvailableForBPartnerAndLocation(I_M_PickingSlot, int, int)}.
	 * 
	 * @param query
	 * @return
	 */
	List<I_M_PickingSlot> retrivePickingSlots(PickingSlotQuery query);

	@Builder
	@Value
	public static class PickingSlotQuery
	{
		public static final PickingSlotQuery ALL = builder().build();
		
		/**
		 * {@code -1} means "no restriction".
		 */
		@Default
		int bpartnerId = -1;

		/**
		 * {@code -1} means "no restriction".
		 */
		@Default
		int bpartnerLocationId = -1;

		/**
		 * {@code -1} means "no restriction".
		 */
		@Default
		int warehouseId = -1;
	}

}
