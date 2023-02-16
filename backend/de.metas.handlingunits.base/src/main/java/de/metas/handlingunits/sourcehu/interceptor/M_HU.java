package de.metas.handlingunits.sourcehu.interceptor;

import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.sourcehu.ISourceHuDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.compiere.model.ModelValidator;

@Interceptor(I_M_HU.class)
public class M_HU
{
	private static final AdMessageKey MSG_MOVING_NOT_ALLOWED = AdMessageKey.of("MovingNotAllowed");

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	public static final M_HU INSTANCE = new M_HU();

	private M_HU()
	{
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_M_HU.COLUMNNAME_M_Locator_ID //
	)
	public void preventMovingSourceHu(@NonNull final I_M_HU hu)
	{
		final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);
		final boolean sourceHU = sourceHuDAO.isSourceHu(HuId.ofRepoId(hu.getM_HU_ID()));
		if (sourceHU)
		{
			throw new SourceHuMayNotBeRemovedException(hu);
		}
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_M_HU.COLUMNNAME_M_Locator_ID //
	)
	public void preventMovingUnclearedHu(@NonNull final I_M_HU hu)
	{
		final I_M_Locator locatorRecord = warehouseDAO.getLocatorByRepoId(hu.getM_Locator_ID());

		if (locatorRecord.isOnlyClearedHUs() 
				&& !ClearanceStatus.Cleared.getCode().equals(hu.getClearanceStatus()))
		{
			throw new AdempiereException(MSG_MOVING_NOT_ALLOWED)
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("LocatorId", hu.getM_Locator_ID())
					.setParameter("HuId", hu.getM_HU_ID());
		}
	}

	@SuppressWarnings("serial")
	public static final class SourceHuMayNotBeRemovedException extends AdempiereException
	{
		private static final AdMessageKey MSG_CANNOT_MOVE_SOURCE_HU_1P = AdMessageKey.of("CANNOT_MOVE_SOURCE_HU");

		private SourceHuMayNotBeRemovedException(final I_M_HU hu)
		{
			super(MSG_CANNOT_MOVE_SOURCE_HU_1P, new Object[] { hu.getValue() });
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_HU.COLUMNNAME_HUStatus)
	public void validateHUStatus(@NonNull final I_M_HU hu)
	{
		final String huStatus = hu.getHUStatus();

		final boolean isHUConsumed = X_M_HU.HUSTATUS_Picked.equals(huStatus) || X_M_HU.HUSTATUS_Shipped.equals(huStatus) || X_M_HU.HUSTATUS_Issued.equals(huStatus);

		if (!isHUConsumed)
		{
			return;
		}

		if (!handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(hu.getM_HU_ID())))
		{
			throw new AdempiereException("M_HUs that are not cleared can not be picked, shipped or issued!")
					.appendParametersToMessage()
					.setParameter("M_HU_ID", hu.getM_HU_ID());
		}
	}
}
