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


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Bankstatement {

	private BigInteger ktoNr = null;
	private BigInteger blz = null;
	private String auftragsreferenzNr = null;
	private String bezugsrefernznr = null;
	private BigInteger auszugsNr = null;
	private Saldo anfangsSaldo = null;
	private Saldo schlussSaldo = null;
	private Saldo aktuellValutenSaldo = null;
	private Saldo zukunftValutenSaldo = null;
	
	private List<BankstatementLine> lines = null;
	
	public Bankstatement() {
		lines = new ArrayList<BankstatementLine>();
	}

	public BigInteger getKtoNr() {
		return ktoNr;
	}
	public void setKtoNr(BigInteger ktoNr) {
		this.ktoNr = ktoNr;
	}
	public BigInteger getBlz() {
		return blz;
	}
	public void setBlz(BigInteger blz) {
		this.blz = blz;
	}
	public String getAuftragsreferenzNr() {
		return auftragsreferenzNr;
	}
	public void setAuftragsreferenzNr(String auftragsreferenzNr) {
		this.auftragsreferenzNr = auftragsreferenzNr;
	}
	public String getBezugsrefernznr() {
		return bezugsrefernznr;
	}
	public void setBezugsrefernznr(String bezugsrefernznr) {
		this.bezugsrefernznr = bezugsrefernznr;
	}
	public BigInteger getAuszugsNr() {
		return auszugsNr;
	}
	public void setAuszugsNr(BigInteger auszugsNr) {
		this.auszugsNr = auszugsNr;
	}
	public Saldo getAnfangsSaldo() {
		return anfangsSaldo;
	}
	public void setAnfangsSaldo(Saldo anfangsSaldo) {
		this.anfangsSaldo = anfangsSaldo;
	}
	public Saldo getSchlussSaldo() {
		return schlussSaldo;
	}
	public void setSchlussSaldo(Saldo schlussSaldo) {
		this.schlussSaldo = schlussSaldo;
	}
	public Saldo getAktuellValutenSaldo() {
		return aktuellValutenSaldo;
	}
	public void setAktuellValutenSaldo(Saldo aktuellValutenSaldo) {
		this.aktuellValutenSaldo = aktuellValutenSaldo;
	}
	public Saldo getZukunftValutenSaldo() {
		return zukunftValutenSaldo;
	}
	public void setZukunftValutenSaldo(Saldo zukunftValutenSaldo) {
		this.zukunftValutenSaldo = zukunftValutenSaldo;
	}
	public List<BankstatementLine> getLines() {
		return lines;
	}
	public void setLines(List<BankstatementLine> lines) {
		this.lines = lines;
	}
}
