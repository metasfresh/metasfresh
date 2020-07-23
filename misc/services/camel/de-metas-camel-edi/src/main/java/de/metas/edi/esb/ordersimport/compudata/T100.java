/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.esb.ordersimport.compudata;

import java.io.Serializable;

public class T100 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6000839351758419437L;

	private String record;
	private String partner;
	private String messageNo;
	private String contractValue;

	public final String getRecord()
	{
		return record;
	}

	public final void setRecord(final String record)
	{
		this.record = record;
	}

	public final String getPartner()
	{
		return partner;
	}

	public final void setPartner(final String partner)
	{
		this.partner = partner;
	}

	public final String getMessageNo()
	{
		return messageNo;
	}

	public final void setMessageNo(final String messageNo)
	{
		this.messageNo = messageNo;
	}

	public final String getContractValue()
	{
		return contractValue;
	}

	public final void setContractValue(final String contractValue)
	{
		this.contractValue = contractValue;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (contractValue == null ? 0 : contractValue.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final T100 other = (T100)obj;
		if (contractValue == null)
		{
			if (other.contractValue != null)
			{
				return false;
			}
		}
		else if (!contractValue.equals(other.contractValue))
		{
			return false;
		}
		if (messageNo == null)
		{
			if (other.messageNo != null)
			{
				return false;
			}
		}
		else if (!messageNo.equals(other.messageNo))
		{
			return false;
		}
		if (partner == null)
		{
			if (other.partner != null)
			{
				return false;
			}
		}
		else if (!partner.equals(other.partner))
		{
			return false;
		}
		if (record == null)
		{
			if (other.record != null)
			{
				return false;
			}
		}
		else if (!record.equals(other.record))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "T100 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", contractValue=" + contractValue + "]";
	}
}
