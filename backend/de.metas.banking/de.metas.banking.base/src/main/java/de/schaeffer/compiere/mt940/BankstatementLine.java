package de.schaeffer.compiere.mt940;

/*
 * #%L
 * de.metas.banking.base
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
import java.math.BigInteger;
import java.sql.Date;

public class BankstatementLine {

	private BigDecimal betrag = null;
	private String waehrung = null;
	private String sollHabenKennung = null;
	private Date buchungsdatum = null;
	private Date valuta = null;
	private String buchungsschluessel = null;
	private String referenz = null;
	private String bankReferenz = null;
	private BigDecimal ursprungsbetrag = null;
	private String ursprungsbetragWaehrung = null;
	private BigDecimal gebuehrenbetrag = null;
	private String gebuehrenbetragWaehrung = null;
	private String mehrzweckfeld = null;
	private BigInteger geschaeftsvorfallCode = null;
	private String buchungstext = null;
	private String primanotennummer = null;
	private String verwendungszweck = null;
	private String partnerBlz = null;
	private String partnerKtoNr = null;
	private String partnerName = null;
	private String textschluessel = null;
	
	public BankstatementLine() {
		
	}

	public BigDecimal getBetrag() {
		if (sollHabenKennung.equals("D") || sollHabenKennung.equals("RC")) {
			return betrag.negate();
		} else {
			return betrag;
		}
	}

	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}

	public String getWaehrung() {
		return waehrung;
	}

	public void setWaehrung(String waehrung) {
		this.waehrung = waehrung;
	}

	public String getSollHabenKennung() {
		return sollHabenKennung;
	}

	public void setSollHabenKennung(String sollHabenKennung) {
		this.sollHabenKennung = sollHabenKennung;
	}

	public Date getBuchungsdatum() {
		return buchungsdatum;
	}

	public void setBuchungsdatum(Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}

	public Date getValuta() {
		return valuta;
	}

	public void setValuta(Date valuta) {
		this.valuta = valuta;
	}

	public String getBuchungsschluessel() {
		return buchungsschluessel;
	}

	public void setBuchungsschluessel(String buchungsschluessel) {
		this.buchungsschluessel = buchungsschluessel;
	}

	public String getReferenz() {
		return referenz;
	}

	public void setReferenz(String referenz) {
		this.referenz = referenz;
	}

	public String getBankReferenz() {
		return bankReferenz;
	}

	public void setBankReferenz(String bankReferenz) {
		this.bankReferenz = bankReferenz;
	}

	public BigDecimal getUrsprungsbetrag() {
		return ursprungsbetrag;
	}

	public void setUrsprungsbetrag(BigDecimal ursprungsbetrag) {
		this.ursprungsbetrag = ursprungsbetrag;
	}

	public String getUrsprungsbetragWaehrung() {
		return ursprungsbetragWaehrung;
	}

	public void setUrsprungsbetragWaehrung(String ursprungsbetragWaehrung) {
		this.ursprungsbetragWaehrung = ursprungsbetragWaehrung;
	}

	public BigDecimal getGebuehrenbetrag() {
		return gebuehrenbetrag;
	}

	public void setGebuehrenbetrag(BigDecimal gebuehrenbetrag) {
		this.gebuehrenbetrag = gebuehrenbetrag;
	}

	public String getGebuehrenbetragWaehrung() {
		return gebuehrenbetragWaehrung;
	}

	public void setGebuehrenbetragWaehrung(String gebuehrenbetragWaehrung) {
		this.gebuehrenbetragWaehrung = gebuehrenbetragWaehrung;
	}

	public String getMehrzweckfeld() {
		return mehrzweckfeld;
	}

	public void setMehrzweckfeld(String mehrzweckfeld) {
		this.mehrzweckfeld = mehrzweckfeld;
	}

	public BigInteger getGeschaeftsvorfallCode() {
		return geschaeftsvorfallCode;
	}

	public void setGeschaeftsvorfallCode(BigInteger geschaeftsvorfallCode) {
		this.geschaeftsvorfallCode = geschaeftsvorfallCode;
	}

	public String getBuchungstext() {
		return buchungstext;
	}

	public void setBuchungstext(String buchungstext) {
		this.buchungstext = buchungstext;
	}

	public String getPrimanotennummer() {
		return primanotennummer;
	}

	public void setPrimanotennummer(String primanotennummer) {
		this.primanotennummer = primanotennummer;
	}

	public String getVerwendungszweck() {
		return verwendungszweck;
	}

	public void setVerwendungszweck(String verwendungszweck) {
		this.verwendungszweck = verwendungszweck;
	}

	public String getPartnerBlz() {
		return partnerBlz;
	}

	public void setPartnerBlz(String partnerBlz) {
		this.partnerBlz = partnerBlz;
	}

	public String getPartnerKtoNr() {
		return partnerKtoNr;
	}

	public void setPartnerKtoNr(String partnerKtoNr) {
		this.partnerKtoNr = partnerKtoNr;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getTextschluessel() {
		return textschluessel;
	}

	public void setTextschluessel(String textschluessel) {
		this.textschluessel = textschluessel;
	}
	
}
