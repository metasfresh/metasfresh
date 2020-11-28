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

public class H110 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6146526185784022345L;

	private String record;
	private String partner;
	private String messageNo;
	private String delName1;
	private String delName2;
	private String delName3;
	private String delStrasse1;
	private String delStrasse2;
	private String delOrt;
	private String delPLZ;
	private String delLand;
	private String homeDelName;
	private String homeDelStrasse;
	private String homeDelOrt;
	private String custKommission1;
	private String custKommission2;

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

	public final String getDelName1()
	{
		return delName1;
	}

	public final void setDelName1(final String delName1)
	{
		this.delName1 = delName1;
	}

	public final String getDelName2()
	{
		return delName2;
	}

	public final void setDelName2(final String delName2)
	{
		this.delName2 = delName2;
	}

	public final String getDelName3()
	{
		return delName3;
	}

	public final void setDelName3(final String delName3)
	{
		this.delName3 = delName3;
	}

	public final String getDelStrasse1()
	{
		return delStrasse1;
	}

	public final void setDelStrasse1(final String delStrasse1)
	{
		this.delStrasse1 = delStrasse1;
	}

	public final String getDelStrasse2()
	{
		return delStrasse2;
	}

	public final void setDelStrasse2(final String delStrasse2)
	{
		this.delStrasse2 = delStrasse2;
	}

	public final String getDelOrt()
	{
		return delOrt;
	}

	public final void setDelOrt(final String delOrt)
	{
		this.delOrt = delOrt;
	}

	public final String getDelPLZ()
	{
		return delPLZ;
	}

	public final void setDelPLZ(final String delPLZ)
	{
		this.delPLZ = delPLZ;
	}

	public final String getDelLand()
	{
		return delLand;
	}

	public final void setDelLand(final String delLand)
	{
		this.delLand = delLand;
	}

	public final String getHomeDelName()
	{
		return homeDelName;
	}

	public final void setHomeDelName(final String homeDelName)
	{
		this.homeDelName = homeDelName;
	}

	public final String getHomeDelStrasse()
	{
		return homeDelStrasse;
	}

	public final void setHomeDelStrasse(final String homeDelStrasse)
	{
		this.homeDelStrasse = homeDelStrasse;
	}

	public final String getHomeDelOrt()
	{
		return homeDelOrt;
	}

	public final void setHomeDelOrt(final String homeDelOrt)
	{
		this.homeDelOrt = homeDelOrt;
	}

	public final String getCustKommission1()
	{
		return custKommission1;
	}

	public final void setCustKommission1(final String custKommission1)
	{
		this.custKommission1 = custKommission1;
	}

	public final String getCustKommission2()
	{
		return custKommission2;
	}

	public final void setCustKommission2(final String custKommission2)
	{
		this.custKommission2 = custKommission2;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (custKommission1 == null ? 0 : custKommission1.hashCode());
		result = prime * result + (custKommission2 == null ? 0 : custKommission2.hashCode());
		result = prime * result + (delLand == null ? 0 : delLand.hashCode());
		result = prime * result + (delName1 == null ? 0 : delName1.hashCode());
		result = prime * result + (delName2 == null ? 0 : delName2.hashCode());
		result = prime * result + (delName3 == null ? 0 : delName3.hashCode());
		result = prime * result + (delOrt == null ? 0 : delOrt.hashCode());
		result = prime * result + (delPLZ == null ? 0 : delPLZ.hashCode());
		result = prime * result + (delStrasse1 == null ? 0 : delStrasse1.hashCode());
		result = prime * result + (delStrasse2 == null ? 0 : delStrasse2.hashCode());
		result = prime * result + (homeDelName == null ? 0 : homeDelName.hashCode());
		result = prime * result + (homeDelOrt == null ? 0 : homeDelOrt.hashCode());
		result = prime * result + (homeDelStrasse == null ? 0 : homeDelStrasse.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) // NOPMD by al
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
		final H110 other = (H110)obj;
		if (custKommission1 == null)
		{
			if (other.custKommission1 != null)
			{
				return false;
			}
		}
		else if (!custKommission1.equals(other.custKommission1))
		{
			return false;
		}
		if (custKommission2 == null)
		{
			if (other.custKommission2 != null)
			{
				return false;
			}
		}
		else if (!custKommission2.equals(other.custKommission2))
		{
			return false;
		}
		if (delLand == null)
		{
			if (other.delLand != null)
			{
				return false;
			}
		}
		else if (!delLand.equals(other.delLand))
		{
			return false;
		}
		if (delName1 == null)
		{
			if (other.delName1 != null)
			{
				return false;
			}
		}
		else if (!delName1.equals(other.delName1))
		{
			return false;
		}
		if (delName2 == null)
		{
			if (other.delName2 != null)
			{
				return false;
			}
		}
		else if (!delName2.equals(other.delName2))
		{
			return false;
		}
		if (delName3 == null)
		{
			if (other.delName3 != null)
			{
				return false;
			}
		}
		else if (!delName3.equals(other.delName3))
		{
			return false;
		}
		if (delOrt == null)
		{
			if (other.delOrt != null)
			{
				return false;
			}
		}
		else if (!delOrt.equals(other.delOrt))
		{
			return false;
		}
		if (delPLZ == null)
		{
			if (other.delPLZ != null)
			{
				return false;
			}
		}
		else if (!delPLZ.equals(other.delPLZ))
		{
			return false;
		}
		if (delStrasse1 == null)
		{
			if (other.delStrasse1 != null)
			{
				return false;
			}
		}
		else if (!delStrasse1.equals(other.delStrasse1))
		{
			return false;
		}
		if (delStrasse2 == null)
		{
			if (other.delStrasse2 != null)
			{
				return false;
			}
		}
		else if (!delStrasse2.equals(other.delStrasse2))
		{
			return false;
		}
		if (homeDelName == null)
		{
			if (other.homeDelName != null)
			{
				return false;
			}
		}
		else if (!homeDelName.equals(other.homeDelName))
		{
			return false;
		}
		if (homeDelOrt == null)
		{
			if (other.homeDelOrt != null)
			{
				return false;
			}
		}
		else if (!homeDelOrt.equals(other.homeDelOrt))
		{
			return false;
		}
		if (homeDelStrasse == null)
		{
			if (other.homeDelStrasse != null)
			{
				return false;
			}
		}
		else if (!homeDelStrasse.equals(other.homeDelStrasse))
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
		return "H110 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", delName1=" + delName1 + ", delName2=" + delName2 + ", delName3="
				+ delName3 + ", delStrasse1="
				+ delStrasse1 + ", delStrasse2=" + delStrasse2 + ", delOrt=" + delOrt + ", delPLZ=" + delPLZ + ", delLand=" + delLand + ", homeDelName=" + homeDelName
				+ ", homeDelStrasse="
				+ homeDelStrasse + ", homeDelOrt=" + homeDelOrt + ", custKommission1=" + custKommission1 + ", custKommission2=" + custKommission2 + "]";
	}
}
