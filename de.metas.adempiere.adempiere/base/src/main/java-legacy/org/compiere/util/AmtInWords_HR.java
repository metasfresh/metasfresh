package org.compiere.util;


import java.math.BigDecimal;
import static java.math.BigDecimal.valueOf;
import java.util.HashMap;
import java.util.Map;
/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/



/**
 *	Amount in Words for Croatian
 *
 *  @author Mislav Kašner, algoritam: Domagoj Klepac
 *  @version $Id: AmtInWords_HR.java,v 1.0 2009/03/28
 */
public class AmtInWords_HR implements AmtInWords
{
	/**
	 * 	AmtInWords_HR
	 */
	public AmtInWords_HR ()
	{
		super ();
	}	//	AmtInWords_HR

	 /**
   * Konfiguracijski parametri.
   */

  // odabrati prvu varijantu za veznik "i" prije jedinica - gramatika
  // dozvoljava obje varijante
  // primjer: "sto dvadeset i tri kune", "sto dvadeset tri kune"

  // private final static String I = " i ";
  private final static String I = "";

  // ne preporučujem jer nije gramatički ispravno, ali razmak se
//ukinuti za
//  // nepismenu verziju koju obično viđamo na raznim POS-ovima; ali u
//tom
//  // slučaju I iznad treba također biti "".
  // primjer: "sto dvadeset tri kune i nula lipa",
  //          "stodvadesettrikuneinulalipa"

  private final static String RAZMAK = " ";
  // private final static String RAZMAK = "";

  /**
   * Vraća dani broj napisan slovima, u valuti (kune). Broj mora biti
između
   * -10^100 i 10^100. Bit će automatski zaokružen na dvije decimale.
   *
   * @param broj
   * @return broj napisan slovima
   */
  public static String slovimaUValuti(BigDecimal broj) {
    StringBuilder rezultat = new StringBuilder();

    // zaokruži na dvije decimale
    broj = broj.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    // kune (dio bez decimala)
    BigDecimal kune = broj.setScale(0, BigDecimal.ROUND_DOWN);
    rezultat.append(slovima(kune, "kuna", false, false));

    rezultat.append(RAZMAK + "i" + RAZMAK);

    // lipe (samo decimale)
    BigDecimal lipe = broj.movePointRight(2).remainder(valueOf(100));
    rezultat.append(slovima(lipe, "lipa", false, false));

    return rezultat.toString();
  }

  /**
   * Vraća dani broj napisan slovima. Broj mora biti između -10^100 i
10^100.
   * Decimale se ignoriraju (dakle režu, a ne zaokružuju).
   *
   * @param broj
   * @return broj napisan slovima
   */
  public static StringBuilder slovima(BigDecimal broj) {
    return slovima(broj, null);
  }

  /**
   * Vraća dani broj napisan slovima. Broj mora biti između -10^100 i
10^100.
   * Decimale se ignoriraju (dakle režu, a ne zaokružuju).
   *
   * @param broj
   * @param jedinica u nominativu ("kuna", "lipa", "tisuća",
"milijun"); može
   *        biti null
   * @return broj napisan slovima
   */
  public static StringBuilder slovima(BigDecimal broj, String
jedinica) {
    return slovima(broj, jedinica, false, false);
  }

  /**
   * Privatne i cacheirane varijable - pune se samo kod prvog
instanciranja
   * klase.
   */

  private final static BigDecimal googol = valueOf(10).pow(100);

  private final static BigDecimal dvadeset = valueOf(20);

  private final static Map<Integer, String> brojevi = new
HashMap<Integer, String>(39);

  private final static Map<Integer, String> potencije = new
HashMap<Integer, String>(27);

  private final static String[][] nominativi = {
    // redom:
    // sufiks nominativa (-a za "tisuća")
    // sufiks kad je jedan ali bez broja ispred (-u za "tisuću")
    // sufiks kad ide nakon broja jedan (-a za "jedna tisuća")
    // sufiks kad ide nakon brojeva dva do pet (-e za "dvije tisuće")
    // sufiks koji ide nakon svih ostalih brojeva (-a za "nula
//tisuća",
    // "pet tisuća")
    { "da", "a", "a", "e",  "i"  },
    { "a",  "u", "a", "e",  "a"  },
    { "n",  "n", "n", "na", "na" },
  };

  // statički blok za napuniti hashmapove kod učitavanja klase
  static {
    // izuzeci za slučaj kad je imenica iza broja ženskog roda, stavit
//ćemo
    // kao negativne da iskoristimo istu mapu; svi ostali brojevi su
//za
    // ženski rod isti kao i za muški
    brojevi.put(-1, "jedna");
    brojevi.put(-2, "dvije");

    // jedinice
    brojevi.put(0, "nula");
    brojevi.put(1, "jedan");
    brojevi.put(2, "dva");
    brojevi.put(3, "tri");
    brojevi.put(4, "četiri");
    brojevi.put(5, "pet");
    brojevi.put(6, "šest");
    brojevi.put(7, "sedam");
    brojevi.put(8, "osam");
    brojevi.put(9, "devet");

    // desetice
    brojevi.put(10, "deset");
    brojevi.put(11, "jedanaest");
    brojevi.put(12, "dvanaest");
    brojevi.put(13, "trinaest");
    brojevi.put(14, "četrnaest");
    brojevi.put(15, "petnaest");
    brojevi.put(16, "šesnaest");
    brojevi.put(17, "sedamnaest");
    brojevi.put(18, "osamnaest");
    brojevi.put(19, "devetnaest");

    // desetke
    brojevi.put(20, "dvadeset");
    brojevi.put(30, "trideset");
    brojevi.put(40, "četrdeset");
    brojevi.put(50, "pedeset");
    brojevi.put(60, "šezdeset");
    brojevi.put(70, "sedamdeset");
    brojevi.put(80, "osamdeset");
    brojevi.put(90, "devedeset");

    // stotke
    brojevi.put(100, "sto");
    brojevi.put(200, "dvjesto");
    brojevi.put(300, "tristo");
    brojevi.put(400, "četristo");
    brojevi.put(500, "petsto");
    brojevi.put(600, "šesto");
    brojevi.put(700, "sedamsto");
    brojevi.put(800, "osamsto");
    brojevi.put(900, "devetsto");

    // potencije; 10^3 je tisuća, 10^6 milijun, itd.
    potencije.put(3, "tisuća");
    potencije.put(6, "milijun");
    potencije.put(9, "milijarda");
    potencije.put(12, "bilijun");
    potencije.put(15, "bilijarda");
    potencije.put(18, "trilijun");
    potencije.put(21, "trilijarda");
    potencije.put(24, "kvatrilijun");
    potencije.put(27, "kvatrilijarda");
    potencije.put(30, "kvintilijun");
    potencije.put(33, "kvintilijarda");
    potencije.put(36, "sekstilijun");
    potencije.put(39, "sekstilijarda");
    potencije.put(42, "septilijun");
    potencije.put(45, "septilijarda");
    potencije.put(48, "oktilijun");
    potencije.put(51, "oktilijarda");
    potencije.put(54, "nonilijun");
    potencije.put(57, "nonilijarda");
    potencije.put(60, "decilijun");
    potencije.put(63, "decilijarda");
    potencije.put(66, "undecilijun");
    potencije.put(69, "undecilijarda");
    potencije.put(72, "duodecilijun");
    potencije.put(75, "duodecilijarda");
    potencije.put(78, "tridecilijun");
    potencije.put(81, "tridecilijarda");
    potencije.put(84, "kvatridecilijun");
    potencije.put(87, "kvatridecilijarda");
    potencije.put(90, "kvindecilijun");
    potencije.put(93, "kvindecilijarda");
    potencije.put(96, "seksdecilijun");
    potencije.put(99, "seksdecilijarda");
    potencije.put(100, "googol");
  }

  /**
   * Vraća dani broj napisan slovima. Broj mora biti između -10^66 i
10^66.
   * Decimale se ignoriraju (dakle režu, a ne zaokružuju).
   *
   * @param broj
   * @param jedinica u nominativu ("kuna", "lipa", "tisuća",
"milijun"); može
   *        biti null
   * @param jediniceBezBroja false kada je jedinica valuta, a ne
brojčana
   *        jedinica
   * @param rekurzija true kad ova metoda poziva samu sebe
   * @return broj napisan slovima
   */
  private static StringBuilder slovima(BigDecimal broj, String
jedinica, boolean jediniceBezBroja, boolean rekurzija) {
    StringBuilder rezultat = new StringBuilder();

    // za negativne iznose - stavi minus i radi dalje s pozitivnim
//brojem
    if (broj.compareTo(valueOf(0)) == -1) {
      rezultat.append("minus" + RAZMAK);
      broj = broj.multiply(valueOf(-1));
    }

    // provjera ulaznog parametra
    if (googol.compareTo(broj) < 1) {
      // googol je izuzetak od pravila jer potencija nije djeljiva sa
//tri
      // pa ga i vrati kao izuzetak i nemoj dozvoliti veće brojeve
      if (googol.compareTo(broj) == 0) {
        rezultat.append(potencije.get(100));
        return rezultat;
      }
      throw new IllegalArgumentException("nepoznat broj - nije između-googol i googol (-10^100 i 10^100)");
    }

    int potencija = potencija(broj);

    // glavna petlja za logiku pisanja brojeva - poziva samu sebe i
//sve
    // svodi na 1, <20, <100, <1000, <x^n gdje je n djeljiv sa 3
    if (broj.compareTo(valueOf(1)) == 0) {
      // jedan - ovdje se određuje hoće li se napisati "milijarda",
      // "tisuću", ili "i jedna kuna"

      // treba li dodati veznik "i"; ukratko, NE TREBA dodati ako:
      // - ova jedinica treba biti bez broja ("sto milijuna i jedna
      //   tisuća", ali ne kod "sto milijuna i tisuću"
      // - ova jedinica je prva kod ispisa broja (ne: "i jedan milijun
//i
      //   jedan", da: "jedan milijun i jedan") tj. metoda se poziva
      //   rekurzivno
      // - konfigurirano je da se ne koristi I
      // (substring je da se izbjegnu dva razmaka u ovom specifičnom
      // slučaju)
      if (!jediniceBezBroja && rekurzija)
        rezultat.append(I.length() > 1 ? I.substring(1) : I);

      // jediniceSuValuta i ova logika služi tome da se "jedna kuna i
      // jedna lipa" ne napišu kao "kunu i lipu"; ali da bude npr.
      // "tisuću" a ne "jedna tisuća"
      rezultat.append(dekliniraj(1, jedinica, jediniceBezBroja));
    } else if (broj.compareTo(dvadeset) < 1) {
      // brojevi manji od 20 su izuzeci i sve čupamo direktno iz mape

      // "i" ispred jedinica - ista logika kao i gore, s tim da je
      // još dodana provjera koja osigurava da se "i" dodaje samo za
      // jedinice a ne i veće brojeve
      if (!jediniceBezBroja && rekurzija &&
broj.compareTo(valueOf(10)) == -1)
        rezultat.append(I.length() > 1 ? I.substring(1) : I);

      rezultat.append(dekliniraj(broj.intValue(), jedinica, false));
    } else if (potencija < 2) {
      // brojevi od 20 do 99

      // .movePointRight je brži način potenciranja ako se radi
      // potenciranje broja 10; ostatak je u ovom slučaju zadnja
      // znamenka (npr. 4 za 34)
      int ostatak =
broj.remainder(valueOf(1).movePointRight(potencija)).intValue();

      // "dvadeset", "trideset"...

rezultat.append(brojevi.get(broj.subtract(valueOf(ostatak)).intValue()));
      if (ostatak > 0) {
        // "i" ispred jedinica - "dvadeset i jedan" ili "dvadeset
//jedan"
        rezultat.append(I.length() > 0 ? I : RAZMAK);

        // deklinirani ostatak i jedinica (kuna, lipa, tisuća...)
        rezultat.append(dekliniraj(ostatak, jedinica, false));
      } else if (jedinica != null) {
        // ako nema ostatka, onda samo stavi "kuna", "lipa",
//"tisuća"...
        rezultat.append(RAZMAK);
        rezultat.append(jedinica);
      }
    } else if (potencija < 3) {
      // brojevi od 100 do 999
      BigDecimal ostatak =
broj.remainder(valueOf(1).movePointRight(potencija));
      if (ostatak.compareTo(valueOf(0)) == 1) {
        // "sto", "dvjesto"...

rezultat.append(brojevi.get(broj.subtract(ostatak).intValue()));
        rezultat.append(RAZMAK);
        rezultat.append(slovima(ostatak, jedinica, jediniceBezBroja,
true));
      } else {
        rezultat.append(dekliniraj(broj.subtract(ostatak).intValue(),
jedinica, false));
      }
    } else {
      // svi ostali brojevi

      // prva niža potencija djeljiva sa tri (dakle ona za koju
//postoji
      // ekvivalent u mapi s potencijama)
      int punaPotencija = potencija - potencija % 3;
      BigDecimal ostatak =
broj.remainder(valueOf(1).movePointRight(punaPotencija));

rezultat.append(slovima(broj.subtract(ostatak).movePointLeft(punaPotencija),
potencije.get(punaPotencija),
          true, true));
      if (ostatak.compareTo(valueOf(0)) == 1) {
        rezultat.append(RAZMAK);
        rezultat.append(slovima(ostatak, jedinica, jediniceBezBroja,
true));
      } else if (jedinica != null) {
        rezultat.append(RAZMAK);
        rezultat.append(jedinica);
      }
    }

    return rezultat;
  }

  /**
   * Vraća broj za jedan manji od broja znamenki zadanog broja.
   *
   * @param broj
   * @return
   */
  private static int potencija(BigDecimal broj) {
    int potencija = 0;
    BigDecimal deset = valueOf(10);

    // varijanta na: je li broj veći ili jednak deset - ako je,
//povećaj
    // potenciju; je li broj veći ili jednak od sto...
    for (BigDecimal i = deset; i.compareTo(broj) < 1; i =
i.multiply(deset))
      potencija++;

    return potencija;
  }

  /**
   * Deklinira imenice koje završavaju na -a ili -n u nominativu. Ako
je
   * nominativ null, onda vraća samo broj u muškom rodu.
   *
   * @param znamenka 0-20
   * @param nominativ ("kuna", "lipa", "tisuća", "milijun"); može biti
null
   * @param jedinceBezBroja ako je ovaj flag dignut, onda se preskače
broj i
   *        vraća se samo jedinica, npr. "milijarda" umjesto "jedna
milijarda"
   * @return broj i imenicu dekliniranu s obzirom na broj ispred nje
   * @throws IllegalArgumentException ako nominativ ne završava na -n
ili -a
   */
  private static String dekliniraj(int znamenka, String nominativ,
boolean jedinceBezBroja) {
    if (nominativ == null)
      // nema nominativa - vrati samo broj u muškom rodu
      return brojevi.get(znamenka);
    else {
      // ovisno o završetku nominativa pronađi podatke u arrayu
//nominativi
      for (int i = 0; i < nominativi.length; i++) {
        if (nominativ.endsWith(nominativi[i][0])) {
          String korijen = nominativ.substring(0, nominativ.length() -
1);
          String broj = null;

          // je li nominativ u ženskom rodu?
          if (nominativ.endsWith("a"))
            broj = brojevi.get(znamenka * -1);

          // ako nema ženske varijante broja, onda je broj isti kao i
          // kod muškog roda
          if (broj == null)
            broj = brojevi.get(znamenka);

          // i sada ide deklinacija
          if (znamenka == 1) {
            if (jedinceBezBroja) {
              return korijen + nominativi[i][1];
            } else {
              return broj + RAZMAK + korijen + nominativi[i][2];
            }
          } else if (znamenka > 1 && znamenka < 5) {
            return broj + RAZMAK + korijen + nominativi[i][3];
          } else {
            return broj + RAZMAK + korijen + nominativi[i][4];
          }
        }
      }
    }

    throw new IllegalArgumentException("nominativ '" + nominativ + "'ne znam deklinirati!");
  }


	/**************************************************************************
	 * 	Get Amount in Words
	 * 	@param amount numeric amount (352.80)
	 * 	@return amount in words (three*five*two 80/100)
	 * 	@throws Exception
	 */
	public String getAmtInWords (String amount) throws Exception
	{
		if (amount == null)
			return amount;
		//
		StringBuffer sb = new StringBuffer ();

		amount = amount.replaceAll (",", "");

        Double iznos = Double.parseDouble(amount);
		sb.append (slovimaUValuti (new BigDecimal(Double.valueOf(iznos))));

		return sb.toString ();
	}	//	getAmtInWords

	/**
	 * 	Test Print
	 *	@param amt amount
	 */
	private void print (String amt)
	{
		try
		{
			System.out.println(amt + " = " + getAmtInWords(amt));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	//	print

	/**
	 * 	Test
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
		AmtInWords_HR aiw = new AmtInWords_HR();
	//	aiw.print (".23");	Error
		aiw.print("263.52");
        aiw.print ("0.23");
		aiw.print ("1.23");
		aiw.print ("12.345");
		aiw.print ("123.45");
		aiw.print ("1234.56");
		aiw.print ("12345.78");
		aiw.print ("123457.89");
		aiw.print ("1,234,578.90");
	}	//	main

}	//	AmtInWords_HR
