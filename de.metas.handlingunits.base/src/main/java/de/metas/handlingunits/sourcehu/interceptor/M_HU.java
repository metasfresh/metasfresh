package de.metas.handlingunits.sourcehu.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.sourcehu.ISourceHuDAO;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_M_HU.class)
public class M_HU
{
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

	public static final class SourceHuMayNotBeRemovedException extends AdempiereException
	{
		private static final long serialVersionUID = -7002396154928421269L;
		private static final String MSG_CANNOT_MOVE_SOURCE_HU_1P = "CANNOT_MOVE_SOURCE_HU";

		private SourceHuMayNotBeRemovedException(final I_M_HU hu)
		{
			super(MSG_CANNOT_MOVE_SOURCE_HU_1P, new Object[] { hu.getValue() });
		}
	}

}
