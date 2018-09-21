package de.metas.handlingunits.client.terminal.lutuconfig.model;

import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Check;
import de.metas.util.Services;

public class AbstractLUTUKey extends AbstractLUTUCUKey
{
	private final I_M_HU_PI huPI;
	private final I_M_HU_PI_Item huPIItemChildJoin;
	private String _id;
	private final KeyNamePair value;
	private final boolean isNoPI;
	private final boolean isVirtualPI;

	public AbstractLUTUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI)
	{
		this(terminalContext, huPI, null); // huPIItemChildJoin=null
	}

	public AbstractLUTUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI, final I_M_HU_PI_Item huPIItemChildJoin)
	{
		super(terminalContext);

		Check.assumeNotNull(huPI, "huPI not null");
		this.huPI = huPI;
		this.huPIItemChildJoin = huPIItemChildJoin;

		final int huPIId = huPI.getM_HU_PI_ID();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		isNoPI = huPIId == handlingUnitsDAO.getPackingItemTemplate_HU_PI_ID();
		isVirtualPI = huPIId == handlingUnitsDAO.getVirtual_HU_PI_ID();

		value = new KeyNamePair(huPIId, huPI.getName());
	}

	@Override
	protected String createName()
	{
		final I_M_HU_PI huPI = getM_HU_PI();
		return huPI.getName();
	}

	@Override
	public final String getId()
	{
		if (_id == null)
		{
			_id = buildId();
			Check.assumeNotNull(_id, "_id not null");
		}
		return _id;
	}

	protected String buildId()
	{
		final int huPIId = getM_HU_PI().getM_HU_PI_ID();

		// final int huPIItemChildJoinId = huPIItemChildJoin == null ? -1 : huPIItemChildJoin.getM_HU_PI_Item_ID();
		final String id = getClass().getName() + "#M_HU_PI_ID=" + huPIId;
		// 07377
		// do not include the child id in the LU TU id.
		// + "#M_HU_PI_Item_ID=" + huPIItemChildJoinId
		;

		return id;
	}

	@Override
	public final KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public final I_M_HU_PI getM_HU_PI()
	{
		return huPI;
	}

	public final I_M_HU_PI_Item getM_HU_PI_Item_ForChildJoin()
	{
		return huPIItemChildJoin;
	}

	public final boolean isNoPI()
	{
		return isNoPI;
	}

	public final boolean isVirtualPI()
	{
		return isVirtualPI;
	}
}
