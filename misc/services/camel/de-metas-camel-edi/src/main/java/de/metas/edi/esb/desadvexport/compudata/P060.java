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

package de.metas.edi.esb.desadvexport.compudata;

import java.io.Serializable;

public class P060 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 372101200931588674L;

	private String record;
	private String partner;
	private String messageNo;
	private String cPScounter;
	private String levelID;
	private String palettQTY;
	private String innerOuterCode;
	private String palettTyp;
	private String hoehe;
	private String laenge;
	private String breite;
	private String volumen;
	private String bruttogewicht;
	private String hierachSSCC;
	private String normalSSCC;

	public String getRecord()
	{
		return record;
	}

	public void setRecord(final String record)
	{
		this.record = record;
	}

	public String getPartner()
	{
		return partner;
	}

	public void setPartner(final String partner)
	{
		this.partner = partner;
	}

	public String getMessageNo()
	{
		return messageNo;
	}

	public void setMessageNo(final String messageNo)
	{
		this.messageNo = messageNo;
	}

	public String getcPScounter()
	{
		return cPScounter;
	}

	public void setcPScounter(final String cPScounter)
	{
		this.cPScounter = cPScounter;
	}

	public String getLevelID()
	{
		return levelID;
	}

	public void setLevelID(final String levelID)
	{
		this.levelID = levelID;
	}

	public String getPalettQTY()
	{
		return palettQTY;
	}

	public void setPalettQTY(final String palettQTY)
	{
		this.palettQTY = palettQTY;
	}

	public String getInnerOuterCode()
	{
		return innerOuterCode;
	}

	public void setInnerOuterCode(final String innerOuterCode)
	{
		this.innerOuterCode = innerOuterCode;
	}

	public String getPalettTyp()
	{
		return palettTyp;
	}

	public void setPalettTyp(final String palettTyp)
	{
		this.palettTyp = palettTyp;
	}

	public String getHoehe()
	{
		return hoehe;
	}

	public void setHoehe(final String hoehe)
	{
		this.hoehe = hoehe;
	}

	public String getLaenge()
	{
		return laenge;
	}

	public void setLaenge(final String laenge)
	{
		this.laenge = laenge;
	}

	public String getBreite()
	{
		return breite;
	}

	public void setBreite(final String breite)
	{
		this.breite = breite;
	}

	public String getVolumen()
	{
		return volumen;
	}

	public void setVolumen(final String volumen)
	{
		this.volumen = volumen;
	}

	public String getBruttogewicht()
	{
		return bruttogewicht;
	}

	public void setBruttogewicht(final String bruttogewicht)
	{
		this.bruttogewicht = bruttogewicht;
	}

	public String getHierachSSCC()
	{
		return hierachSSCC;
	}

	public void setHierachSSCC(final String hierachSSCC)
	{
		this.hierachSSCC = hierachSSCC;
	}

	public String getNormalSSCC()
	{
		return normalSSCC;
	}

	public void setNormalSSCC(final String sSCC)
	{
		this.normalSSCC = sSCC;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (hierachSSCC == null ? 0 : hierachSSCC.hashCode());
		result = prime * result + (normalSSCC == null ? 0 : normalSSCC.hashCode());
		result = prime * result + (breite == null ? 0 : breite.hashCode());
		result = prime * result + (bruttogewicht == null ? 0 : bruttogewicht.hashCode());
		result = prime * result + (cPScounter == null ? 0 : cPScounter.hashCode());
		result = prime * result + (hoehe == null ? 0 : hoehe.hashCode());
		result = prime * result + (innerOuterCode == null ? 0 : innerOuterCode.hashCode());
		result = prime * result + (laenge == null ? 0 : laenge.hashCode());
		result = prime * result + (levelID == null ? 0 : levelID.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (palettQTY == null ? 0 : palettQTY.hashCode());
		result = prime * result + (palettTyp == null ? 0 : palettTyp.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (volumen == null ? 0 : volumen.hashCode());
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
		final P060 other = (P060)obj;
		if (hierachSSCC == null)
		{
			if (other.hierachSSCC != null)
			{
				return false;
			}
		}
		else if (!hierachSSCC.equals(other.hierachSSCC))
		{
			return false;
		}
		if (normalSSCC == null)
		{
			if (other.normalSSCC != null)
			{
				return false;
			}
		}
		else if (!normalSSCC.equals(other.normalSSCC))
		{
			return false;
		}
		if (breite == null)
		{
			if (other.breite != null)
			{
				return false;
			}
		}
		else if (!breite.equals(other.breite))
		{
			return false;
		}
		if (bruttogewicht == null)
		{
			if (other.bruttogewicht != null)
			{
				return false;
			}
		}
		else if (!bruttogewicht.equals(other.bruttogewicht))
		{
			return false;
		}
		if (cPScounter == null)
		{
			if (other.cPScounter != null)
			{
				return false;
			}
		}
		else if (!cPScounter.equals(other.cPScounter))
		{
			return false;
		}
		if (hoehe == null)
		{
			if (other.hoehe != null)
			{
				return false;
			}
		}
		else if (!hoehe.equals(other.hoehe))
		{
			return false;
		}
		if (innerOuterCode == null)
		{
			if (other.innerOuterCode != null)
			{
				return false;
			}
		}
		else if (!innerOuterCode.equals(other.innerOuterCode))
		{
			return false;
		}
		if (laenge == null)
		{
			if (other.laenge != null)
			{
				return false;
			}
		}
		else if (!laenge.equals(other.laenge))
		{
			return false;
		}
		if (levelID == null)
		{
			if (other.levelID != null)
			{
				return false;
			}
		}
		else if (!levelID.equals(other.levelID))
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
		if (palettQTY == null)
		{
			if (other.palettQTY != null)
			{
				return false;
			}
		}
		else if (!palettQTY.equals(other.palettQTY))
		{
			return false;
		}
		if (palettTyp == null)
		{
			if (other.palettTyp != null)
			{
				return false;
			}
		}
		else if (!palettTyp.equals(other.palettTyp))
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
		if (volumen == null)
		{
			if (other.volumen != null)
			{
				return false;
			}
		}
		else if (!volumen.equals(other.volumen))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "P060 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", cPScounter=" + cPScounter + ", levelID=" + levelID + ", palettQTY=" + palettQTY
				+ ", innerOuterCode=" + innerOuterCode + ", palettTyp=" + palettTyp + ", hoehe=" + hoehe + ", laenge=" + laenge + ", breite=" + breite + ", volumen=" + volumen + ", bruttogewicht="
				+ bruttogewicht + ", hierachSSCC=" + hierachSSCC + ", SSCC=" + normalSSCC + "]";
	}
}
