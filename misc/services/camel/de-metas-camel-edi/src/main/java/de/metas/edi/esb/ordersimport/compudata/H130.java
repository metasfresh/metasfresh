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

public class H130 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6616466510023162915L;

	private String record;
	private String partner;
	private String messageNo;
	private String skonto;
	private String payTimeSkonto;
	private String payTimeNetto;
	private String transportMode;
	private String deliveryTermCode;
	private String deliveryTermText;
	private String packCode;
	private String iataCode;
	private String iataText;

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

	public final String getSkonto()
	{
		return skonto;
	}

	public final void setSkonto(final String skonto)
	{
		this.skonto = skonto;
	}

	public final String getPayTimeSkonto()
	{
		return payTimeSkonto;
	}

	public final void setPayTimeSkonto(final String payTimeSkonto)
	{
		this.payTimeSkonto = payTimeSkonto;
	}

	public final String getPayTimeNetto()
	{
		return payTimeNetto;
	}

	public final void setPayTimeNetto(final String payTimeNetto)
	{
		this.payTimeNetto = payTimeNetto;
	}

	public final String getTransportMode()
	{
		return transportMode;
	}

	public final void setTransportMode(final String transportMode)
	{
		this.transportMode = transportMode;
	}

	public final String getDeliveryTermCode()
	{
		return deliveryTermCode;
	}

	public final void setDeliveryTermCode(final String deliveryTermCode)
	{
		this.deliveryTermCode = deliveryTermCode;
	}

	public final String getDeliveryTermText()
	{
		return deliveryTermText;
	}

	public final void setDeliveryTermText(final String deliveryTermText)
	{
		this.deliveryTermText = deliveryTermText;
	}

	public final String getPackCode()
	{
		return packCode;
	}

	public final void setPackCode(final String packCode)
	{
		this.packCode = packCode;
	}

	public final String getIataCode()
	{
		return iataCode;
	}

	public final void setIataCode(final String iataCode)
	{
		this.iataCode = iataCode;
	}

	public final String getIataText()
	{
		return iataText;
	}

	public final void setIataText(final String iataText)
	{
		this.iataText = iataText;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (deliveryTermCode == null ? 0 : deliveryTermCode.hashCode());
		result = prime * result + (deliveryTermText == null ? 0 : deliveryTermText.hashCode());
		result = prime * result + (iataCode == null ? 0 : iataCode.hashCode());
		result = prime * result + (iataText == null ? 0 : iataText.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (packCode == null ? 0 : packCode.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (payTimeNetto == null ? 0 : payTimeNetto.hashCode());
		result = prime * result + (payTimeSkonto == null ? 0 : payTimeSkonto.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (skonto == null ? 0 : skonto.hashCode());
		result = prime * result + (transportMode == null ? 0 : transportMode.hashCode());
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
		final H130 other = (H130)obj;
		if (deliveryTermCode == null)
		{
			if (other.deliveryTermCode != null)
			{
				return false;
			}
		}
		else if (!deliveryTermCode.equals(other.deliveryTermCode))
		{
			return false;
		}
		if (deliveryTermText == null)
		{
			if (other.deliveryTermText != null)
			{
				return false;
			}
		}
		else if (!deliveryTermText.equals(other.deliveryTermText))
		{
			return false;
		}
		if (iataCode == null)
		{
			if (other.iataCode != null)
			{
				return false;
			}
		}
		else if (!iataCode.equals(other.iataCode))
		{
			return false;
		}
		if (iataText == null)
		{
			if (other.iataText != null)
			{
				return false;
			}
		}
		else if (!iataText.equals(other.iataText))
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
		if (packCode == null)
		{
			if (other.packCode != null)
			{
				return false;
			}
		}
		else if (!packCode.equals(other.packCode))
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
		if (payTimeNetto == null)
		{
			if (other.payTimeNetto != null)
			{
				return false;
			}
		}
		else if (!payTimeNetto.equals(other.payTimeNetto))
		{
			return false;
		}
		if (payTimeSkonto == null)
		{
			if (other.payTimeSkonto != null)
			{
				return false;
			}
		}
		else if (!payTimeSkonto.equals(other.payTimeSkonto))
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
		if (skonto == null)
		{
			if (other.skonto != null)
			{
				return false;
			}
		}
		else if (!skonto.equals(other.skonto))
		{
			return false;
		}
		if (transportMode == null)
		{
			if (other.transportMode != null)
			{
				return false;
			}
		}
		else if (!transportMode.equals(other.transportMode))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "H130 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", skonto=" + skonto + ", payTimeSkonto=" + payTimeSkonto + ", payTimeNetto="
				+ payTimeNetto
				+ ", transportMode=" + transportMode + ", deliveryTermCode=" + deliveryTermCode + ", deliveryTermText=" + deliveryTermText + ", packCode=" + packCode
				+ ", iataCode=" + iataCode
				+ ", iataText=" + iataText + "]";
	}
}
