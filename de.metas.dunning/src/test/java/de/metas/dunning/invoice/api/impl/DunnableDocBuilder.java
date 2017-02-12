package de.metas.dunning.invoice.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.impl.DunnableDoc;

public class DunnableDocBuilder
{
	private String tableName;
	private int record_id = -1;
	private String documentNo;
	private int AD_Client_ID = -1;
	private int AD_Org_ID = -1;
	private int C_BPartner_ID = -1;
	private int C_BPartner_Location_ID = -1;
	private int Contact_ID = -1;
	private int C_Currency_ID = -1;
	private BigDecimal totalAmt;
	private BigDecimal openAmt;
	private Date dueDate;
	private Date graceDate;
	private int daysDue;
	private boolean isInDispute;

	public DunnableDocBuilder()
	{
		super();

		// Defaults:
		final Properties ctx = Env.getCtx();
		setAD_Client_ID(Env.getAD_Client_ID(ctx));
		setAD_Org_ID(Env.getAD_Org_ID(ctx));
	}

	public IDunnableDoc create()
	{
		final IDunnableDoc dunnableDoc = new DunnableDoc(tableName, record_id,
				documentNo, // FRESH-504: DocumentNo
				AD_Client_ID, AD_Org_ID,
				C_BPartner_ID, C_BPartner_Location_ID, Contact_ID,
				C_Currency_ID, totalAmt, openAmt,
				dueDate,
				graceDate,
				daysDue,
				isInDispute);

		return dunnableDoc;
	}

	public IDunnableDoc createAndAppend(List<IDunnableDoc> list)
	{
		final IDunnableDoc dunnableDoc = create();
		list.add(dunnableDoc);
		return dunnableDoc;
	}

	public DunnableDocBuilder setTableName(String tableName)
	{
		this.tableName = tableName;
		return this;
	}

	public DunnableDocBuilder setRecord_ID(int record_id)
	{
		this.record_id = record_id;
		return this;
	}

	public DunnableDocBuilder setRecord(Object model)
	{
		this.tableName = InterfaceWrapperHelper.getModelTableName(model);
		this.record_id = InterfaceWrapperHelper.getId(model);
		return this;
	}
	
	/**
	 * FRESH-504: DocuemntNo is also needed
	 * @param documentNo
	 * @return
	 */
	public DunnableDocBuilder setDocumentNo (String documentNo)
	{
		this.documentNo = documentNo;
		return this;
	}
	

	public DunnableDocBuilder setAD_Client_ID(int adClientId)
	{
		this.AD_Client_ID = adClientId;
		return this;
	}

	public DunnableDocBuilder setAD_Org_ID(int adOrgId)
	{
		this.AD_Org_ID = adOrgId;
		return this;
	}

	public DunnableDocBuilder setC_BPartner_ID(int c_BPartner_ID)
	{
		C_BPartner_ID = c_BPartner_ID;
		return this;
	}

	public DunnableDocBuilder setC_BPartner_Location_ID(int c_BPartner_Location_ID)
	{
		C_BPartner_Location_ID = c_BPartner_Location_ID;
		return this;
	}

	public DunnableDocBuilder setContact_ID(int contact_ID)
	{
		Contact_ID = contact_ID;
		return this;
	}

	public DunnableDocBuilder setC_Currency_ID(int c_Currency_ID)
	{
		C_Currency_ID = c_Currency_ID;
		return this;
	}

	public DunnableDocBuilder setC_Currency(I_C_Currency currency)
	{
		C_Currency_ID = currency.getC_Currency_ID();
		return this;
	}

	public DunnableDocBuilder setTotalAmt(BigDecimal totalAmt)
	{
		this.totalAmt = totalAmt;
		return this;
	}

	public DunnableDocBuilder setOpenAmt(BigDecimal openAmt)
	{
		this.openAmt = openAmt;
		return this;
	}

	public DunnableDocBuilder setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
		return this;
	}

	public DunnableDocBuilder setGraceDate(Date graceDate)
	{
		this.graceDate = graceDate;
		return this;
	}

	public DunnableDocBuilder setDaysDue(int daysDue)
	{
		this.daysDue = daysDue;
		return this;
	}

	public DunnableDocBuilder setInDispute(boolean isInDispute)
	{
		this.isInDispute = isInDispute;
		return this;
	}
}
