package de.metas.product.async.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;
import org.compiere.model.MStorage;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.product.IStorageBL;
import de.metas.util.Services;

/**
 * This workpackage processor invokes {@link MStorage#add(java.util.Properties, int, int, int, int, int, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, String)}.
 *
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/08999_Lieferdisposition_a.frieden_%28104263801724%29
 */
public class M_Storage_Add extends WorkpackageProcessorAdapter
{
	public static final String WP_PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	public static final String WP_PARAM_M_Locator_ID = "M_Locator_ID";
	public static final String WP_PARAM_M_Product_ID = "M_Product_ID";
	public static final String WP_PARAM_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	public static final String WP_PARAM_reservationAttributeSetInstance_ID = "reservationAttributeSetInstance_ID";
	public static final String WP_PARAM_diffQtyOnHand = "diffQtyOnHand";
	public static final String WP_PARAM_diffQtyReserved = "diffQtyReserved";
	public static final String WP_PARAM_diffQtyOrdered = "diffQtyOrdered";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);

		final IParams params = workpackageParamDAO.retrieveWorkpackageParams(workpackage);

		Services.get(IStorageBL.class).add(
				InterfaceWrapperHelper.getCtx(workpackage),
				params.getParameterAsInt(WP_PARAM_M_Warehouse_ID, -1),
				params.getParameterAsInt(WP_PARAM_M_Locator_ID, -1),
				params.getParameterAsInt(WP_PARAM_M_Product_ID, -1),
				params.getParameterAsInt(WP_PARAM_M_AttributeSetInstance_ID, -1),
				params.getParameterAsInt(WP_PARAM_reservationAttributeSetInstance_ID, -1),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyOnHand),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyReserved),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyOrdered),
				localTrxName);

		return Result.SUCCESS;
	}

}
