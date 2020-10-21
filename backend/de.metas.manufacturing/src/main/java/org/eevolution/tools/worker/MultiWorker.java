/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.tools.worker;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
* @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
* @version 1.0, October 14th 2005
*/
public abstract class MultiWorker {

	protected abstract class WorkerThread extends Thread {

		abstract public Object doWork();
		
		@Override
		public void run() {
			
			isWorking = true;
			value = doWork();
			isWorking = false;
		}

		@Override
		public void interrupt() {
			
			super.interrupt();
			isWorking = false;
		}
	}

	protected boolean isWorking;
	protected WorkerThread workerThread;
	protected int timeout;
	protected Object value;

	public MultiWorker() {
	
		setTimeout(-1);
	}
	
	public abstract void start();


	public int getTimeout() {

		return timeout;
	}

	public void setTimeout(int timeout) {
		
		this.timeout = timeout;
	}
	
	public boolean isWorking() {
		
		return isWorking;
	}
	
	public void waitForComplete(int timeout) {
		
		setTimeout(timeout);
		waitForComplete();
	}
	
	public void stop() {
		
		workerThread.interrupt();
	}
	
	public void waitForComplete() {
		
		boolean to = getTimeout() > -1;
		
		int c = 0;
		int i = 1000;
		while(isWorking()) {
			
			try {
				
				Thread.sleep(i);
				c+= to ? c+=i : -1;
			}			
			catch(Exception e) {}
		
			if(to && c >= getTimeout()) {
				
				workerThread.interrupt();
				workerThread = null;
				break;
			}
		}
	}
}
