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

public class H120 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3115547711048907017L;

	private String record;
	private String partner;
	private String messageNo;
	private String qualifier;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	private String text5;

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

	public final String getQualifier()
	{
		return qualifier;
	}

	public final void setQualifier(final String qualifier)
	{
		this.qualifier = qualifier;
	}

	public final String getText1()
	{
		return text1;
	}

	public final void setText1(final String text1)
	{
		this.text1 = text1;
	}

	public final String getText2()
	{
		return text2;
	}

	public final void setText2(final String text2)
	{
		this.text2 = text2;
	}

	public final String getText3()
	{
		return text3;
	}

	public final void setText3(final String text3)
	{
		this.text3 = text3;
	}

	public final String getText4()
	{
		return text4;
	}

	public final void setText4(final String text4)
	{
		this.text4 = text4;
	}

	public final String getText5()
	{
		return text5;
	}

	public final void setText5(final String text5)
	{
		this.text5 = text5;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (qualifier == null ? 0 : qualifier.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (text1 == null ? 0 : text1.hashCode());
		result = prime * result + (text2 == null ? 0 : text2.hashCode());
		result = prime * result + (text3 == null ? 0 : text3.hashCode());
		result = prime * result + (text4 == null ? 0 : text4.hashCode());
		result = prime * result + (text5 == null ? 0 : text5.hashCode());
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
		final H120 other = (H120)obj;
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
		if (qualifier == null)
		{
			if (other.qualifier != null)
			{
				return false;
			}
		}
		else if (!qualifier.equals(other.qualifier))
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
		if (text1 == null)
		{
			if (other.text1 != null)
			{
				return false;
			}
		}
		else if (!text1.equals(other.text1))
		{
			return false;
		}
		if (text2 == null)
		{
			if (other.text2 != null)
			{
				return false;
			}
		}
		else if (!text2.equals(other.text2))
		{
			return false;
		}
		if (text3 == null)
		{
			if (other.text3 != null)
			{
				return false;
			}
		}
		else if (!text3.equals(other.text3))
		{
			return false;
		}
		if (text4 == null)
		{
			if (other.text4 != null)
			{
				return false;
			}
		}
		else if (!text4.equals(other.text4))
		{
			return false;
		}
		if (text5 == null)
		{
			if (other.text5 != null)
			{
				return false;
			}
		}
		else if (!text5.equals(other.text5))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "H120 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", qualifier=" + qualifier + ", text1=" + text1 + ", text2=" + text2
				+ ", text3=" + text3 + ", text4="
				+ text4 + ", text5=" + text5 + "]";
	}
}
