/**
 * 
 */
package de.metas.printing.spi.impl;

import java.util.HashMap;
import java.util.Map;

import de.metas.printing.spi.IPrintJobBatchMonitor;
import de.metas.printing.spi.IPrintJobMonitor;


/**
 * @author cg
 *
 */
public abstract class AbstractPrintJobMonitor implements IPrintJobMonitor
{

	abstract public IPrintJobBatchMonitor createBatchMonitor();

	
	private Map<String, Object> m_dynAttrs = null;
	

	public Object setDynAttribute(String name, Object value)
	{
		if (m_dynAttrs == null)
			m_dynAttrs = new HashMap<String, Object>();
		return m_dynAttrs.put(name, value);
	}

	public Object getDynAttribute(String name)
	{
		if (m_dynAttrs == null)
			return null;
		return m_dynAttrs.get(name);
	}
}
