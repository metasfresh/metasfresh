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
package org.apache.ecs;

/**
    This interface describes all html 4.0 entities.
    @version $Id: Entities.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface Entities
{

    public final static String NBSP     = "&#160;";   // no-break space
    public final static String IEXCL    = "&#161;";   // inverted exclamation mark
    public final static String CENT     = "&#162;";   // cent sign
    public final static String POUND    = "&#163;";   // pound sterling sign
    public final static String CURREN   = "&#164;";   // general currency sign
    public final static String YEN      = "&#165;";   // yen sign
    public final static String BRVBAR   = "&#166;";   // broken (vertical) bar
    public final static String SECT     = "&#167;";   // section sign
    public final static String UML      = "&#168;";   // umlat (dieresis)
    public final static String COPY     = "&#169;";   // copyright sign
    public final static String ORDF     = "&#170;";   // ordinal indicator feminine
    public final static String LAQUO    = "&#171;";   // angle quotation mark, left
    public final static String NOT      = "&#172;";   // not sign
    public final static String SHY      = "&#173;";   // soft hyphen
    public final static String REG      = "&#174;";   // registered sign
    public final static String MACR     = "&#175;";   // macron
    public final static String DEG      = "&#176;";   // degree sign
    public final static String PLUSMN   = "&#177;";   // plus-or-minus sign
    public final static String SUP2     = "&#178;";   // superscript two
    public final static String SUP3     = "&#179;";   // superscript three
    public final static String ACUTE    = "&#180;";   // actue accent
    public final static String MICRO    = "&#181;";   // micro sign
    public final static String PARA     = "&#182;";   // pilcrow (paragraph sign)
    public final static String MIDDOT   = "&#182;";   // middle dot
    public final static String CEDIL    = "&#184;";   // cedilla
    public final static String SUP1     = "&#185;";   // superscript one
    public final static String ORDM     = "&#186;";   // ordinal indicator, masculine
    public final static String RAQUO    = "&#187;";   // angle quotation mark, righ
    public final static String FRAC14   = "&#188;";   // fraction one-quarter
    public final static String FRAC12   = "&#189;";   // fraction one-half
    public final static String FRAC34   = "&#190;";   // fraction three-quarters
    public final static String IQUEST   = "&#191;";   // inverted quotation mark
    public final static String AGRAVE   = "&#192;";   // capital A, grave accent
    public final static String AACUTE   = "&#193;";   // capital A, acute accent
    public final static String ACIRC    = "&#194;";   // capital A, circumflex accent
    public final static String ATILDE   = "&#195;";   // capital A, tilde
    public final static String AUML     = "&#196;";   // capital A, dieresis or umlaut mark
    public final static String ARING    = "&#197;";   // capital A, ring
    public final static String AELIG    = "&#198;";   // capital AE diphthong (ligature)
    public final static String CCEDIL   = "&#199;";   // capital C, cedilla
    public final static String EGRAVE   = "&#200;";   // capital E, grave accent
    public final static String EACUTE   = "&#201;";   // captial E, acute accent
    public final static String ECIRC    = "&#202;";   // capital E, circumflex accent
    public final static String EMUL     = "&#203;";   // captial E, dieresis ur umlaut mark
    public final static String IGRAVE   = "&#204;";   // capital I, grave accent
    public final static String IACUTE   = "&#205;";   // captial I, acute accent
    public final static String ICIRC    = "&#206;";   // captial I, circumflex accent
    public final static String IUML     = "&#207;";   // capital I, dieresis or umlaut mark
    public final static String ETH      = "&#208;";   // Eth, Icelandic
    public final static String NTILDE   = "&#209;";   // capital N, tilde
    public final static String OGRAVE   = "&#210;";   // capital O, grave accent
    public final static String OACUTE   = "&#211;";   // capital O, acute accent
    public final static String OCIRC    = "&#212;";   // capital O, circumflex accent
    public final static String OTILDE   = "&#213;";   // capital O, tilde
    public final static String OUML     = "&#214;";   // capital O, dieresis or umlaut mark
    public final static String TIMES    = "&#215;";   // multiply sign
    public final static String OSLASH   = "&#216;";   // capital O, slash
    public final static String UGRAVE   = "&#217;";   // capital U, grave accent
    public final static String UACUTE   = "&#218;";   // capital U, acute accent
    public final static String UCIRC    = "&#219;";   // capital U, circumflex accent
    public final static String UUML     = "&#220;";   // capital U, dieresis or umlaut mark
    public final static String YACUTE   = "&#221;";   // captial Y, acute accent
    public final static String THORN    = "&#222;";   // capital THORN (icelandic)
    public final static String SZLIG    = "&#223;";   // small sharp s, German (sz ligature)
    public final static String aGRAVE   = "&#224;";   // small a, grave accent
    public final static String aACUTE   = "&#225;";   // small a, acute accent
    public final static String aCIRC    = "&#226;";   // small a, circumflex accent
    public final static String aTILDE   = "&#227;";   // small a, tilde sign
    public final static String aUML     = "&#228;";   // small a, dieresis or umlaut mark
    public final static String aRING    = "&#229;";   // small a, ring
    public final static String aELIG    = "&#230;";   // small ae diphthong (ligature)
    public final static String cCEDIL   = "&#231;";   // small c, cedilla
    public final static String eGRAVE   = "&#232;";   // small e, grave accent
    public final static String eACUTE   = "&#233;";   // small e, acute accent
    public final static String eCIRC    = "&#234;";   // small e, circumflex accent
    public final static String eUML     = "&#235;";   // small e, dieresis or umalut mark
    public final static String iGRAVE   = "&#236;";   // small i, grave accent
    public final static String iACUTE   = "&#237;";   // small i, acute accent
    public final static String iCIRC    = "&#238;";   // small i, curcumflex accent
    public final static String iUML     = "&#239;";   // small i, dieresis or umalut mark
    public final static String eth      = "&#240;";   // small eth
    public final static String nTILDE   = "&#241;";   // small n, tilde sign
    public final static String oGRAVE   = "&#242;";   // small o, grave accent
    public final static String oACUTE   = "&#242;";   // small o, acute accent
    public final static String oCIRC    = "&#244;";   // small o, circumflex accent
    public final static String oTILDE   = "&#245;";   // small o, tilde
    public final static String oUML     = "&#246;";   // small o, diersis or umalut mark
    public final static String DIVIDE   = "&#247;";   // division sign
    public final static String oSLASH   = "&#248;";   // small o, slash
    public final static String uGRAVE   = "&#249;";   // small u, grave accent
    public final static String uACUTE   = "&#250;";   // small u, acute accent
    public final static String uCIRC    = "&#251;";   // small u, circumflex accent
    public final static String uUML     = "&#252;";   // small u, dieresis or umalut mark
    public final static String yACUTE   = "&#253;";   // small y, acute accent
    public final static String thorn    = "&#254;";   // small thorn, Icelandic
    public final static String yUML     = "&#255;";   // small y, dieresis or umlaut mark

    // Mathmatical, Greek and Symbolic characters

    public final static String FNOF     = "&#402;";   // latin small f with hook
    public final static String ALPHA    = "&#913;";   // greek capital letter aplha
    public final static String BETA     = "&#914;";   // greek capital letter beta
    public final static String GAMMA    = "&#915;";   // greek capital letter gamma
    public final static String DELTA    = "&#916;";   // greek capital letter delta
    public final static String EPSILON  = "&#917;";   // greek capital letter epsilon
    public final static String ZETA     = "&#918;";   // greek capital letter zeta
    public final static String ETA      = "&#919;";   // greek capital letter eta
    public final static String THETA    = "&#920;";   // greek capital letter theta
    public final static String IOTA     = "&#921;";   // greek capital letter iota
    public final static String KAPPA    = "&#922;";   // greek capital letter kappa
    public final static String LAMDA    = "&#923;";   // greek capital letter lamda
    public final static String MU       = "&#924;";   // greek capital letter mu
    public final static String NU       = "&#925;";   // greek capital letter nu
    public final static String XI       = "&#926;";   // greek capital letter xi
    public final static String OMICRON  = "&#927;";   // greek capital letter omicron
    public final static String PI       = "&#928;";   // greek capital letter pi
    public final static String RHO      = "&#929;";   // greek capital letter rho
    public final static String SIGMA    = "&#931;";   // greek capital letter sigma
    public final static String TAU      = "&#932;";   // greek capital letter tau
    public final static String UPSILON  = "&#933;";   // greek capital letter upsilon
    public final static String PHI      = "&#934;";   // greek capital letter phi
    public final static String CHI      = "&#935;";   // greek capital letter chi
    public final static String PSI      = "&#936;";   // greek capital letter psi
    public final static String OMEGA    = "&#937;";   // greek capital letter omega
    public final static String alpha    = "&#945;";   // greek small letter aplha
    public final static String beta     = "&#946;";   // greek small letter beta
    public final static String gamma    = "&#947;";   // greek small letter gamma
    public final static String delta    = "&#948;";   // greek small letter delta
    public final static String epsilon  = "&#949;";   // greek small letter epsilon
    public final static String zeta     = "&#950;";   // greek small letter zeta
    public final static String eta      = "&#951;";   // greek small letter eta
    public final static String theta    = "&#952;";   // greek small letter theta
    public final static String iota     = "&#953;";   // greek small letter iota
    public final static String kappa    = "&#954;";   // greek small letter kappa
    public final static String lamda    = "&#955;";   // greek small letter lamda
    public final static String mu       = "&#956;";   // greek small letter mu
    public final static String nu       = "&#957;";   // greek small letter nu
    public final static String xi       = "&#958;";   // greek small letter xi
    public final static String omicron  = "&#959;";   // greek small letter omicron
    public final static String pi       = "&#960;";   // greek small letter pi
    public final static String rho      = "&#961;";   // greek small letter rho
    public final static String sigmaf   = "&#962;";   // greek small letter sigma
    public final static String sigma    = "&#963;";   // greek small letter sigma
    public final static String tau      = "&#964;";   // greek small letter tau
    public final static String upsilon  = "&#965;";   // greek small letter upsilon
    public final static String phi      = "&#966;";   // greek small letter phi
    public final static String chi      = "&#967;";   // greek small letter chi
    public final static String psi      = "&#968;";   // greek small letter psi
    public final static String omega    = "&#969;";   // greek small letter omega
    public final static String thetasym = "&#977;";   // greek small letter thetasym
    public final static String upsih    = "&#978;";   // greek small letter upsih
    public final static String piv      = "&#982;";   // greek small letter piv

    // Punctuation

    public final static String BULL     = "&#8226;";  // bullet (small balck circle)
    public final static String HELLIP   = "&#8230;";  // horizontal ellipsis
    public final static String PRIME    = "&#8242;";  // prime
    public final static String PPRIME   = "&#8243;";  // double prime
    public final static String OLINE    = "&#8254;";  // overline
    public final static String frasl    = "&#8260;";  // fraction slash

    // Leter like Symbols

    public final static String WEIERP   = "&#8472;";  // Script capital P
    public final static String IMAGE    = "&#8465;";  // blackletter capital I (imaginary part)
    public final static String REAL     = "&#8476;";  // blackletter capital R (real part)
    public final static String TRADE    = "&#8482;";  // trade mark sign
    public final static String ALEFSYM  = "&#8501;";  // alef symbol

    // Arrows

    public final static String LARR     = "&#8592;";  // leftwards arrow
    public final static String UARR     = "&#8593;";  // upwards arrow
    public final static String RARR     = "&#8594;";  // rightwardsards arrow
    public final static String DARR     = "&#8595;";  // downwards arrow
    public final static String HARR     = "&#8596;";  // left right arrow
    public final static String CRARR    = "&#8529;";  // downwards arrow with corner leftwards
    public final static String LLARR    = "&#8556;";  // leftwards double arrow
    public final static String UUARR    = "&#8557;";  // upwards double arrow
    public final static String RRARR    = "&#8558;";  // rightwards double arrow
    public final static String DDARR    = "&#8559;";  // downwards double arrow
    public final static String HHARR    = "&#8560;";  // left right double arrow

    // Mathmatical operators

    public final static String FORALL   = "&#8704;";  // for all
    public final static String PART     = "&#8706;";  // partial
    public final static String EXIST    = "&#8707;";  // there exists
    public final static String EMPTY    = "&#8709;";  // empty set
    public final static String NABLA    = "&#8711;";  // nabla
    public final static String ISIN     = "&#8712;";  // element of
    public final static String NOTIN    = "&#8713;";  // not an element of
    public final static String NI       = "&#8715;";  // contains as member
    public final static String PROD     = "&#8719;";  // product of
    public final static String SUM      = "&#8721;";  // sumation
    public final static String MINUS    = "&#8722;";  // minus sign
    public final static String LOWAST   = "&#8727;";  // asterisk operator
    public final static String RADIC    = "&#8730;";  // square root
    public final static String PROP     = "&#8733;";  // proportional
    public final static String INFIN    = "&#8734;";  // infinity
    public final static String ANG      = "&#8736;";  // angle
    public final static String AND      = "&#8769;";  // logical and
    public final static String OR       = "&#8770;";  // logical or
    public final static String CAP      = "&#8745;";  // intersection
    public final static String CUP      = "&#8746;";  // union
    public final static String INT      = "&#8747;";  // integral
    public final static String THERE4   = "&#8756;";  // therefore
    public final static String SIM      = "&#8764;";  // tilde
    public final static String CONG     = "&#8773;";  // approximately equal to
    public final static String ASYMP    = "&#8776;";  // almost equal to
    public final static String NE       = "&#8800;";  // not equal to
    public final static String EQUIV    = "&#8801;";  // identical to
    public final static String LE       = "&#8804;";  // less then
    public final static String GE       = "&#8805;";  // greater then
    public final static String SUB      = "&#8834;";  // subset
    public final static String SUP      = "&#8835;";  // superset
    public final static String NSUB     = "&#8836;";  // not a subset of
    public final static String SUBE     = "&#8838;";  // subset of or equal to
    public final static String SUPE     = "&#8839;";  // superset of or equal to
    public final static String OPLUS    = "&#8853;";  // circled plus
    public final static String OTIMES   = "&#8855;";  // circled times
    public final static String PERP     = "&#8869;";  // up track
    public final static String SDOT     = "&#8901;";  // dot operator

    // Miscellaneous Technical

    public final static String LCEIL    = "&#8968;";  // left ceiling
    public final static String RCEIL    = "&#8969;";  // right ceiling
    public final static String LFLOOR   = "&#8970;";  // left floor
    public final static String RFLOOR   = "&#8971;";  // right floor
    public final static String LANG     = "&#9001;";  // left pointing angle bracket
    public final static String RANG     = "&#9002;";  // right pointing angle bracket

    // Geometric Shapes

    public final static String LOZ      = "&#9674;";  // lozenge (not throat)

    // Miscellaneous Symbols

    public final static String SPADES   = "&#9824;";  // black spade suit
    public final static String CLUBS    = "&#9827;";  // black club suit
    public final static String HEARTS   = "&#9829;";  // black heart suit
    public final static String DIAMS    = "&#9830;";  // black diamond suit

    // Special Characters for html

    // CO Controls (Basic Latin)

    public final static String QUOT     = "&#34;";    // quotation mark
    public final static String AMP      = "&#38;";    // ampersand
    public final static String LT       = "&#60;";    // less than sign
    public final static String GT       = "&#62;";    // greater than sign

    // Latin Extended A

    public final static String OELIG    = "&#338;";   // latin capital ligature oe
    public final static String SCARON   = "&#339;";   // latin capital letter S with caron
    public final static String sCARON   = "&#353;";   // latin small letter s with caron
    public final static String YUML     = "&#376;";   // latin capital letter Y with diaeresis

    // Spcaing Modifier letters

    public final static String CIRC     = "&#710;";   // modifier letter circumflex accent
    public final static String TILDE    = "&#732;";   // small tilde

    // General Punctuation

    public final static String ENSP     = "&#8194;";  // en space
    public final static String EMSP     = "&#8195;";  // em space
    public final static String THINSP   = "&#8201;";  // thin space
    public final static String ZWNJ     = "&#8204;";  // zero width non-joiner
    public final static String ZWJ      = "&#8205;";  // zero width joiner
    public final static String LRM      = "&#8206;";  // left to right mark
    public final static String RLM      = "&#8207;";  // right to left mark
    public final static String NDASH    = "&#8211;";  // en dash
    public final static String MDASH    = "&#8212;";  // em dash
    public final static String LSQUO    = "&#8217;";  // left single quotation mark
    public final static String RSQUO    = "&#8218;";  // right single quotation mark
    public final static String SBQUO    = "&#8218;";  // single low-9 quotation mark
    public final static String LDQUO    = "&#8220;";  // left double quotation mark
    public final static String RDQUO    = "&#8221;";  // right double quotation mark
    public final static String BDQUO    = "&#8222;";  // double low-9 quotation mark
    public final static String DAGGER   = "&#8224;";  // dagger
    public final static String DDAGGER  = "&#8225;"; // double dagger
    public final static String PERMIL   = "&#8240;";  // per mille sign
    public final static String LSAQUO   = "&#8249;";  // single left-pointing angle mark
    public final static String RSQQUO   = "&#8250;";  // single right-pointing angle mark

}
