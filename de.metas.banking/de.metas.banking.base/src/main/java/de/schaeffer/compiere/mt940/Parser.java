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
import java.util.List;


public class Parser {

	public static Bankstatement parseMT940String(String mt940) {
		
		Bankstatement statement = new Bankstatement();
		
		String[] mt940lines = mt940.replaceAll("\n","").replaceAll(":[0-9][0-9][A-Z]?:","\n$0").split("\n");
		
		for (String line : mt940lines) {
			if (line.startsWith(":20:")) {
				statement.setAuftragsreferenzNr(line.substring(4));
			} else if (line.startsWith(":21:")) {
				statement.setBezugsrefernznr(line.substring(4));
			} else if (line.startsWith(":25:")) {
				statement.setBlz(new BigInteger(line.substring(4).split("/")[0]));
				statement.setKtoNr(new BigInteger(line.substring(4).split("/")[1]));
			} else if (line.startsWith(":28C:")) {
				if (line.split("/").length == 2) {
					statement.setAuszugsNr(new BigInteger(line.split("/")[1]));
				} else {
					statement.setAuszugsNr(new BigInteger(line.substring(5)));
				}
			} else if (line.startsWith(":60F:")) {
				statement.setAnfangsSaldo(new Saldo(
						line.substring(5,6), 
						new Date(Integer.valueOf(line.substring(6,8))+100, Integer.valueOf(line.substring(8,10))-1, Integer.valueOf(line.substring(10,12))),
						line.substring(12,15), 
						new BigDecimal(line.substring(15).replaceAll(",",".").replaceAll("-",""))
				));
			} else if (line.startsWith(":62F:")) {
				statement.setSchlussSaldo(new Saldo(
						line.substring(5,6), 
						new Date(Integer.valueOf(line.substring(6,8))+100, Integer.valueOf(line.substring(8,10))-1, Integer.valueOf(line.substring(10,12))),
						line.substring(12,15), 
						new BigDecimal(line.substring(15).replaceAll(",",".").replaceAll("-",""))
				));
			} else if (line.startsWith(":64:")) {
				statement.setAktuellValutenSaldo(new Saldo(
						line.substring(5,6), 
						new Date(Integer.valueOf(line.substring(6,8))+100, Integer.valueOf(line.substring(8,10))-1, Integer.valueOf(line.substring(10,12))),
						line.substring(12,15), 
						new BigDecimal(line.substring(15).replaceAll(",",".").replaceAll("-",""))
				));
			} else if (line.startsWith(":65:")) {
				statement.setZukunftValutenSaldo(new Saldo(
						line.substring(5,6), 
						new Date(Integer.valueOf(line.substring(6,8))+100, Integer.valueOf(line.substring(8,10))-1, Integer.valueOf(line.substring(10,12))),
						line.substring(12,15), 
						new BigDecimal(line.substring(15).replaceAll(",",".").replaceAll("-",""))
				));
			} else if (line.startsWith(":61:")) {
				int index = 10;
				BankstatementLine sLine = new BankstatementLine();
				sLine.setValuta(new Date(Integer.valueOf(line.substring(4,6))+100, Integer.valueOf(line.substring(6,8))-1, Integer.valueOf(line.substring(8,10))));
				if (line.substring(10,14).replaceAll("\\D","").length() == 4) {
					sLine.setBuchungsdatum(new Date(Integer.valueOf(line.substring(4,6))+100, Integer.valueOf(line.substring(10,12))-1, Integer.valueOf(line.substring(12,14))));
					index = 14;
				}
				if ("C".equals(line.substring(index, index+1)) || "D".equals(line.substring(index, index+1))) {
					sLine.setSollHabenKennung(line.substring(index, index+1));
					index = 15;
				} else {
					sLine.setSollHabenKennung(line.substring(index, index+2));
					index = 16;
				}
				if (line.substring(index, index+1).matches("[A-Z]")) {
					sLine.setWaehrung(line.substring(index, index+1));
					index++;
				}
				for (int i = 1; i < 1001; i++) {
					if (i == 1000) {
//						throw new ParseException;
					} else if (line.substring(index, index+i).endsWith("N")) {
						sLine.setBetrag(new BigDecimal(line.substring(index, index + i - 1).replaceAll(",",".")));
						index = index + i - 1;
						break;
					}
				}
				sLine.setBuchungsschluessel(line.substring(index, index + 4));
				index = index + 4;
				if (line.substring(index).replaceAll("//","").length() == line.substring(index).length()) {
					sLine.setReferenz(line.substring(index));
				} else {
					sLine.setReferenz(line.substring(index).split("//")[0]);
					if (line.substring(index).replaceAll("/OCMT/","").length() == line.substring(index).length() && line.substring(index).replaceAll("/CHGS/","").length() == line.substring(index).length()) {
						sLine.setBankReferenz(line.substring(index));
					} else {
						
						if (line.substring(index).replaceAll("/OCMT/", "").length() != line.substring(index).length()) {
							sLine.setBankReferenz(line.substring(index).split("/OCMT/")[0].split("//")[1]);
							sLine.setUrsprungsbetrag(new BigDecimal(line.substring(index).split("/OCMT/")[1].substring(3).replaceAll(",", ".")));
							sLine.setUrsprungsbetragWaehrung(line.substring(index).split("/OCMT/")[1].substring(0, 3));
						}
						if (line.substring(index).replaceAll("/CHGS/", "").length() != line.substring(index).length()) {
							sLine.setBankReferenz(line.substring(index).split("/CHGS/")[0].split("//")[1]);
							sLine.setUrsprungsbetrag(new BigDecimal(line.substring(index).split("/CHGS/")[1].substring(3).replaceAll(",", ".")));
							sLine.setUrsprungsbetragWaehrung(line.substring(index).split("/CHGS/")[1].substring(0,3));
						}
					}
				}
				statement.getLines().add(sLine);
				
			} else if (line.startsWith(":86:")) {
				List<BankstatementLine> lines = statement.getLines();
				String[] feld = line.substring(4).split("\\?");
				lines.get(lines.size()-1).setGeschaeftsvorfallCode(new BigInteger(feld[0]));
				for (int i = 1; i < feld.length; i++) {
					try {
						if (feld[i].length() >= 2) {
							if (Integer.valueOf(feld[i].substring(0, 2)) == 0) {
								lines.get(lines.size() - 1).setBuchungstext(feld[i].substring(2));
							} else if (Integer.valueOf(feld[i].substring(0, 2)) == 10) {
								lines.get(lines.size() - 1).setPrimanotennummer(feld[i].substring(2));
							} else if ((Integer.valueOf(feld[i].substring(0, 2)) >= 20 && Integer.valueOf(feld[i].substring(0, 2)) < 30) || (Integer.valueOf(feld[i].substring(0, 2)) >= 60 && Integer.valueOf(feld[i].substring(0, 2)) < 64)) {
								if (lines.get(lines.size() - 1).getVerwendungszweck() == null) {
									lines.get(lines.size() - 1).setVerwendungszweck("");
								}
								lines.get(lines.size() - 1).setVerwendungszweck(lines.get(lines.size() - 1).getVerwendungszweck() + feld[i].substring(2) + " ");
							} else if (Integer.valueOf(feld[i].substring(0, 2)) == 30) {
								lines.get(lines.size() - 1).setPartnerBlz(feld[i].substring(2));
							} else if (Integer.valueOf(feld[i].substring(0, 2)) == 31) {
								lines.get(lines.size() - 1).setPartnerKtoNr(feld[i].substring(2));
							} else if (Integer.valueOf(feld[i].substring(0, 2)) == 32 || Integer.valueOf(feld[i].substring(0, 2)) == 33) {
								lines.get(lines.size() - 1).setPartnerName(feld[i].substring(2));
							} else if (Integer.valueOf(feld[i].substring(0, 2)) == 34) {
								lines.get(lines.size() - 1).setTextschluessel(feld[i].substring(2));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
		}
		return statement;
	}
}
