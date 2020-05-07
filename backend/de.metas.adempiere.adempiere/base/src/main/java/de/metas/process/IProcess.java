package de.metas.process;

import org.adempiere.ad.trx.api.ITrx;

/**
 * Root interface for process implementations.
 * 
 * Consider extending {@link JavaProcess} instead of this interface.
 * 
 * @author authors of earlier versions of this class are: Jorg Janke
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IProcess
{
	/**
	 * Start the process
	 *
	 * @param pi Process Info
	 * @param trx actual transaction or {@link ITrx#TRX_None}
	 * @throws Exception in case of any failure
	 */
	public void startProcess(ProcessInfo pi, ITrx trx) throws Exception;
}
