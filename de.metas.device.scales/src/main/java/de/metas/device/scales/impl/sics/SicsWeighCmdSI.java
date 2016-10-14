package de.metas.device.scales.impl.sics;

/**
 * Generates the command string <code>SI\r\n</code> which requests the "instant" weight from the scale.
 * This result might be "stable" or "dynamic".
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class SicsWeighCmdSI extends AbstractSicsWeighCmd
{
	private static final SicsWeighCmdSI instance = new SicsWeighCmdSI();

	/**
	 * @return the string <code>S</code> that shall be send to the scale hardware in order to get the current weight.
	 */
	@Override
	public String getCmd()
	{
		return "SI" + SICS_CMD_TERMINATOR;
	}

	public static SicsWeighCmdSI getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("SicsCmdSI []");
	}
}
