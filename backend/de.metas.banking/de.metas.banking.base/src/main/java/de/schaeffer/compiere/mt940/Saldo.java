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
import java.sql.Date;

public class Saldo {
	private String sollHabenKennung;

	private String waehrung;

	private Date buchungsdatum;

	private BigDecimal betrag;

	public Saldo(String sollHabenKennung, Date buchungsdatum, String waehrung, BigDecimal betrag) {
		this.sollHabenKennung = sollHabenKennung;
		this.waehrung = waehrung;
		this.buchungsdatum = buchungsdatum;
		this.betrag = betrag;
	}

	public String getSollHabenKennung() {
		return sollHabenKennung;
	}

	public void setSollHabenKennung(String sollHabenKennung) {
		this.sollHabenKennung = sollHabenKennung;
	}

	public String getWaehrung() {
		return waehrung;
	}

	public void setWaehrung(String waehrung) {
		this.waehrung = waehrung;
	}

	public Date getBuchungsdatum() {
		return buchungsdatum;
	}

	public void setBuchungsdatum(Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}

	public BigDecimal getBetrag() {
		return betrag;
	}

	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}
}
