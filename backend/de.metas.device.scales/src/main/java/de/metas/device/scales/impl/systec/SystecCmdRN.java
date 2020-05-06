package de.metas.device.scales.impl.systec;

/**
 * Generates the command string <code>RN</code> which requests the "stable" weight from the scale.
 * If the scale is unable to measure a stable weight within a certain timeout, it returns an error.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class SystecCmdRN extends AbstractSystecCmd
{
	private static final SystecCmdRN instance = new SystecCmdRN();

	private SystecCmdRN()
	{
	}

	@Override
	public String getCmd()
	{
		return "<RN>";
	}

	public static SystecCmdRN getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("SystecCmdRN []");
	}
}
