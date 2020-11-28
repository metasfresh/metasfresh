package de.metas.handlingunits.client.terminal.lutuconfig.model;

import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Check;
import lombok.NonNull;

public class AbstractLUTUKey extends AbstractLUTUCUKey
{
	private final I_M_HU_PI huPI;
	private final I_M_HU_PI_Item huPIItemChildJoin;
	private String _id;
	private final KeyNamePair value;
	private final boolean isTemplatePI;
	private final boolean isVirtualPI;

	public AbstractLUTUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI)
	{
		this(terminalContext, huPI, null); // huPIItemChildJoin=null
	}

	public AbstractLUTUKey(final ITerminalContext terminalContext, @NonNull final I_M_HU_PI huPI, final I_M_HU_PI_Item huPIItemChildJoin)
	{
		super(terminalContext);

		this.huPI = huPI;
		this.huPIItemChildJoin = huPIItemChildJoin;

		final HuPackingInstructionsId huPIId = HuPackingInstructionsId.ofRepoId(huPI.getM_HU_PI_ID());

		isTemplatePI = huPIId.isTemplate();
		isVirtualPI = huPIId.isVirtual();

		value = KeyNamePair.of(huPIId, huPI.getName());
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
		return isTemplatePI;
	}

	public final boolean isVirtualPI()
	{
		return isVirtualPI;
	}
}
