/**
 * 
 */
package org.compiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.util.ProcessUtil;
import org.compiere.model.MClient;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * @author cg
 *
 */
public class TestProcess extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		System.out.println("Process 1");
		ProcessInfo pi = new ProcessInfo ("Test Process", 540065);
		pi.setClassName("de.metas.adempiere.process.ExecuteUpdateSQL");
		Trx trx = Trx.get("Trxname", true);
		ProcessUtil.startJavaProcess(Env.getCtx(), pi, trx);
		
		//
		
		String message = "message";
		//
		String to = "cristinamghita@gmail.com";
		MClient client = MClient.get(getCtx(), getAD_Client_ID());
		EMail email = client.createEMail(to,
				"test",
				message,
				true);
		String status = "Check Setup";
		status = email.send();
		System.out.println(status);
		//
		return "test";
	}

	@Override
	protected void prepare()
	{
		// TODO Auto-generated method stub

	}

}
