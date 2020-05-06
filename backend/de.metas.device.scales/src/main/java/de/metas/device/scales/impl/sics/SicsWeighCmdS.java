package de.metas.device.scales.impl.sics;

/**
 * Generates the command string <code>S\r\n</code> which requests the "stable" weight from the scale.
 * If the scale is unable to measure a stable weight within a certain timeout, it returns an error.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class SicsWeighCmdS extends AbstractSicsWeighCmd
{

	private static final SicsWeighCmdS instance = new SicsWeighCmdS();

	/**
	 * @return the string <code>S</code> that shall be send to the scale hardware in order to get the current weight.
	 */
	@Override
	public String getCmd()
	{
		return "S" + SICS_CMD_TERMINATOR;
	}

	public static SicsWeighCmdS getInstance()
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("SicsCmdS []");
	}
}
