package de.metas.device.scales.impl.systec;

/**
 * Generates the command string <code>RM</code> which requests the "instant"/"dynamic" weight from the scale.
 * This weight might also be a "stable" weight, but that's not guaranteed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class SystecCmdRM extends AbstractSystecCmd
{
	private static final SystecCmdRM instance = new SystecCmdRM();

	private SystecCmdRM()
	{
	}

	@Override
	public String getCmd()
	{
		return "<RM>";
	}

	public static SystecCmdRM getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("SystecCmdRM []");
	}
}
