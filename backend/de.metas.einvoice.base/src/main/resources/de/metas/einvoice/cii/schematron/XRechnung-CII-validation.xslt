<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2"
               xmlns:error="https://doi.org/10.5281/zenodo.1495494#error"
               xmlns:qdt="urn:un:unece:uncefact:data:standard:QualifiedDataType:100"
               xmlns:ram="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100"
               xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
               xmlns:rsm="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"
               xmlns:sch="http://purl.oclc.org/dsdl/schematron"
               xmlns:schxslt="https://doi.org/10.5281/zenodo.1495494"
               xmlns:schxslt-api="https://doi.org/10.5281/zenodo.1495494#api"
               xmlns:u="utils"
               xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100"
               xmlns:xs="http://www.w3.org/2001/XMLSchema"
               xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
               version="2.0">
   <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/"
                    xmlns:dct="http://purl.org/dc/terms/"
                    xmlns:skos="http://www.w3.org/2004/02/skos/core#">
      <dct:creator>
         <dct:Agent>
            <skos:prefLabel>SchXslt/1.10.1 SAXON/HE 12.8</skos:prefLabel>
            <schxslt.compile.typed-variables xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
         </dct:Agent>
      </dct:creator>
      <dct:created>2026-02-04T10:57:48.804707566Z</dct:created>
   </rdf:Description>
   <xsl:output indent="yes"/>
   <xsl:function xmlns="http://purl.oclc.org/dsdl/schematron"
                 as="xs:decimal"
                 name="u:decimalOrZero">
      <xsl:param name="element"/>
      <xsl:sequence select="if (boolean($element)) then xs:decimal($element) else 0"/>
   </xsl:function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:gln"
             as="xs:boolean">
      <param name="val"/>
      <variable name="length" select="string-length($val) - 1"/>
      <variable name="digits"
                select="reverse(for $i in string-to-codepoints(substring($val, 0, $length + 1)) return $i - 48)"/>
      <variable name="weightedSum"
                select="sum(for $i in (0 to $length - 1) return $digits[$i + 1] * (1 + ((($i + 1) mod 2) * 2)))"/>
      <sequence select="(10 - ($weightedSum mod 10)) mod 10 = number(substring($val, $length + 1, 1))"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:slack"
             as="xs:boolean">
      <param name="exp" as="xs:decimal"/>
      <param name="val" as="xs:decimal"/>
      <param name="slack" as="xs:decimal"/>
      <sequence select="xs:decimal($exp + $slack) &gt;= $val and xs:decimal($exp - $slack) &lt;= $val"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:mod11"
             as="xs:boolean">
      <param name="val"/>
      <variable name="length" select="string-length($val) - 1"/>
      <variable name="digits"
                select="reverse(for $i in string-to-codepoints(substring($val, 0, $length + 1)) return $i - 48)"/>
      <variable name="weightedSum"
                select="sum(for $i in (0 to $length - 1) return $digits[$i + 1] * (($i mod 6) + 2))"/>
      <sequence select="number($val) &gt; 0 and (11 - ($weightedSum mod 11)) mod 11 = number(substring($val, $length + 1, 1))"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:mod97-0208"
             as="xs:boolean">
      <param name="val"/>
      <variable name="checkdigits" select="substring($val,9,2)"/>
      <variable name="calculated_digits"
                select="xs:string(97 - (xs:integer(substring($val,1,8)) mod 97))"/>
      <sequence select="number($checkdigits) = number($calculated_digits)"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkCodiceIPA"
             as="xs:boolean">
      <param name="arg" as="xs:string?"/>
      <variable name="allowed-characters">ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789</variable>
      <sequence select="if ( (string-length(translate($arg, $allowed-characters, '')) = 0) and (string-length($arg) = 6) ) then true() else false()"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkCF"
             as="xs:boolean">
      <param name="arg" as="xs:string?"/>
      <sequence select="   if ( (string-length($arg) = 16) or (string-length($arg) = 11) )   then   (    if ((string-length($arg) = 16))    then    (     if (u:checkCF16($arg))     then     (      true()     )     else     (      false()     )    )    else    (     if(($arg castable as xs:integer)) then true() else false()     )   )   else   (    false()   )   "/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkCF16"
             as="xs:boolean">
      <param name="arg" as="xs:string?"/>
      <variable name="allowed-characters">ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz</variable>
      <sequence select="     if (  (string-length(translate(substring($arg,1,6), $allowed-characters, '')) = 0) and       (substring($arg,7,2) castable as xs:integer) and       (string-length(translate(substring($arg,9,1), $allowed-characters, '')) = 0) and       (substring($arg,10,2) castable as xs:integer) and       (substring($arg,12,3) castable as xs:string) and       (substring($arg,15,1) castable as xs:integer) and       (string-length(translate(substring($arg,16,1), $allowed-characters, '')) = 0)      )     then true()     else false()     "/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkPIVAseIT"
             as="xs:boolean">
      <param name="arg" as="xs:string"/>
      <variable name="paese" select="substring($arg,1,2)"/>
      <variable name="codice" select="substring($arg,3)"/>
      <sequence select="     if ( $paese = 'IT' or $paese = 'it' )    then    (     if ( ( string-length($codice) = 11 ) and ( if (u:checkPIVA($codice)!=0) then false() else true() ))     then     (      true()     )     else     (      false()     )    )    else    (     true()    )    "/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkPIVA"
             as="xs:integer">
      <param name="arg" as="xs:string?"/>
      <sequence select="     if (not($arg castable as xs:integer))      then 1      else ( u:addPIVA($arg,xs:integer(0)) mod 10 )"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:addPIVA"
             as="xs:integer">
      <param name="arg" as="xs:string"/>
      <param name="pari" as="xs:integer"/>
      <variable name="tappo"
                select="if (not($arg castable as xs:integer)) then 0 else 1"/>
      <variable name="mapper"
                select="if ($tappo = 0) then 0 else                   ( if ($pari = 1)                    then ( xs:integer(substring('0246813579', ( xs:integer(substring($arg,1,1)) +1 ) ,1)) )                    else ( xs:integer(substring($arg,1,1) ) )                   )"/>
      <sequence select="if ($tappo = 0) then $mapper else ( xs:integer($mapper) + u:addPIVA(substring(xs:string($arg),2), (if($pari=0) then 1 else 0) ) )"/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:abn"
             as="xs:boolean">
      <param name="val"/>
      <sequence select="( ((string-to-codepoints(substring($val,1,1)) - 49) * 10) + ((string-to-codepoints(substring($val,2,1)) - 48) * 1) + ((string-to-codepoints(substring($val,3,1)) - 48) * 3) + ((string-to-codepoints(substring($val,4,1)) - 48) * 5) + ((string-to-codepoints(substring($val,5,1)) - 48) * 7) + ((string-to-codepoints(substring($val,6,1)) - 48) * 9) + ((string-to-codepoints(substring($val,7,1)) - 48) * 11) + ((string-to-codepoints(substring($val,8,1)) - 48) * 13) + ((string-to-codepoints(substring($val,9,1)) - 48) * 15) + ((string-to-codepoints(substring($val,10,1)) - 48) * 17) + ((string-to-codepoints(substring($val,11,1)) - 48) * 19)) mod 89 = 0 "/>
   </function>
   <function xmlns="http://www.w3.org/1999/XSL/Transform"
             name="u:checkSEOrgnr"
             as="xs:boolean">
      <param name="number" as="xs:string"/>
      <choose>
         <when test="not(matches($number, '^\d+$'))">
            <sequence select="false()"/>
         </when>
         <otherwise>
            <variable name="mainPart" select="substring($number, 1, 9)"/>
            <variable name="checkDigit" select="substring($number, 10, 1)"/>
            <variable name="sum" as="xs:integer">
               <sequence select="xs:integer(sum(       for $pos in 1 to string-length($mainPart) return         if ($pos mod 2 = 1)         then (number(substring($mainPart, string-length($mainPart) - $pos + 1, 1)) * 2) mod 10 +           (number(substring($mainPart, string-length($mainPart) - $pos + 1, 1)) * 2) idiv 10         else number(substring($mainPart, string-length($mainPart) - $pos + 1, 1))      ))"/>
            </variable>
            <variable name="calculatedCheckDigit" select="(10 - $sum mod 10) mod 10"/>
            <sequence select="$calculatedCheckDigit = number($checkDigit)"/>
         </otherwise>
      </choose>
   </function>
   <xsl:param name="profile"
              select="             if (/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext/ram:BusinessProcessSpecifiedDocumentContextParameter and matches(normalize-space(/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext/ram:BusinessProcessSpecifiedDocumentContextParameter/ram:ID), 'urn:fdc:peppol.eu:2017:poacc:billing:([0-9]{2}):1.0')) then                 tokenize(normalize-space(/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext/ram:BusinessProcessSpecifiedDocumentContextParameter/ram:ID), ':')[7]             else                 'Unknown'"/>
   <xsl:param name="supplierCountry"
              select="             if (/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction[1]/ram:ApplicableHeaderTradeAgreement[1]/ram:SellerTradeParty[1]/ram:SpecifiedTaxRegistration[ram:ID/@schemeID = 'VAT']/substring(ram:ID, 1, 2)) then                 upper-case(normalize-space(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction[1]/ram:ApplicableHeaderTradeAgreement[1]/ram:SellerTradeParty[1]/ram:SpecifiedTaxRegistration[ram:ID/@schemeID = 'VAT']/substring(ram:ID, 1, 2)))             else                 if (/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTaxRepresentativeTradeParty/ram:SpecifiedTaxRegistration[ram:ID/@schemeID = 'VAT']/substring(ram:ID, 1, 2)) then                     upper-case(normalize-space(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTaxRepresentativeTradeParty/ram:SpecifiedTaxRegistration[ram:ID/@schemeID = 'VAT']/substring(ram:ID, 1, 2)))                 else                     if (/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:CountryID) then                         upper-case(normalize-space(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:CountryID))                     else                         'XX'"/>
   <xsl:param name="documentCurrencyCode"
              select="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:InvoiceCurrencyCode"/>
   <xsl:param name="slackValue"
              select="if($documentCurrencyCode = 'HUF') then 0.5 else 0.02"/>
   <xsl:param name="taxCurrencyCode"
              select="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:TaxCurrencyCode"/>
   <xsl:variable name="XR-MAJOR-MINOR-VERSION" select="'3.0'"/>
   <xsl:variable name="CVD-MAJOR-MINOR-VERSION" select="'0.9'"/>
   <xsl:variable name="XR-CIUS-ID"
                 select="concat('urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_', $XR-MAJOR-MINOR-VERSION )"/>
   <xsl:variable name="XR-EXTENSION-ID"
                 select="concat($XR-CIUS-ID, '#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_' ,$XR-MAJOR-MINOR-VERSION )"/>
   <xsl:variable name="XR-CVD-ID"
                 select="concat($XR-CIUS-ID, '#compliant#urn:xeinkauf.de:kosit:xrechnung:cvd_' , $CVD-MAJOR-MINOR-VERSION )"/>
   <xsl:variable name="XR-SKONTO-REGEX"
                 select="'(^|\r?\n)#(SKONTO)#TAGE=([0-9]+#PROZENT=[0-9]+\.[0-9]{2})(#BASISBETRAG=-?[0-9]+\.[0-9]{2})?#$'"/>
   <xsl:variable name="XR-EMAIL-REGEX" select="'^[^@\s]+@([^@.\s]+\.)+[^@.\s]+$'"/>
   <xsl:variable name="XR-TELEPHONE-REGEX" select="'.*([0-9].*){3,}.*'"/>
   <xsl:variable name="XR-URL-REGEX" select="'^([a-zA-Z])([a-zA-Z0-9+.-])+:.*'"/>
   <xsl:variable name="DIGA-CODES" select="' XR01 XR02 XR03 '"/>
   <xsl:variable name="ISO-6523-ICD-CODES"
                 select="' 0002 0003 0004 0005 0006 0007 0008 0009 0010 0011 0012 0013 0014 0015 0016 0017 0018 0019 0020 0021 0022 0023 0024 0025 0026 0027 0028 0029 0030 0031 0032 0033 0034 0035 0036 0037 0038 0039 0040 0041 0042 0043 0044 0045 0046 0047 0048 0049 0050 0051 0052 0053 0054 0055 0056 0057 0058 0059 0060 0061 0062 0063 0064 0065 0066 0067 0068 0069 0070 0071 0072 0073 0074 0075 0076 0077 0078 0079 0080 0081 0082 0083 0084 0085 0086 0087 0088 0089 0090 0091 0093 0094 0095 0096 0097 0098 0099 0100 0101 0102 0104 0105 0106 0107 0108 0109 0110 0111 0112 0113 0114 0115 0116 0117 0118 0119 0120 0121 0122 0123 0124 0125 0126 0127 0128 0129 0130 0131 0132 0133 0134 0135 0136 0137 0138 0139 0140 0141 0142 0143 0144 0145 0146 0147 0148 0149 0150 0151 0152 0153 0154 0155 0156 0157 0158 0159 0160 0161 0162 0163 0164 0165 0166 0167 0168 0169 0170 0171 0172 0173 0174 0175 0176 0177 0178 0179 0180 0183 0184 0185 0186 0187 0188 0189 0190 0191 0192 0193 0194 0195 0196 0197 0198 0199 0200 0201 0202 0203 0204 0205 0206 0207 0208 0209 0210 0211 0212 0213 0214 0215 0216 0217 0218 0219 0220 0221 0222 0223 0224 0225 0226 0227 0228 0229 0230 0231 0232 0233 0234 0235 0236 0237 0238 0239 0240 0241 0242 0243 0244'"/>
   <xsl:variable name="ISO-6523-ICD-EXT-CODES"
                 select="concat($DIGA-CODES, $ISO-6523-ICD-CODES)"/>
   <xsl:variable name="CEF-EAS-CODES"
                 select="' 0002 0007 0009 0037 0060 0088 0096 0097 0106 0130 0135 0142 0147 0151 0154 0158 0170 0177 0183 0184 0188 0190 0191 0192 0193 0194 0195 0196 0198 0199 0200 0201 0202 0203 0204 0205 0208 0209 0210 0211 0212 0213 0215 0216 0217 0218 0219 0220 0221 0225 0230 0235 0240 0244 9910 9913 9914 9915 9918 9919 9920 9922 9923 9924 9925 9926 9927 9928 9929 9930 9931 9932 9933 9934 9935 9936 9937 9938 9939 9940 9941 9942 9943 9944 9945 9946 9947 9948 9949 9950 9951 9952 9953 9957 9959 AN AQ AS AU EM '"/>
   <xsl:variable name="CEF-EAS-EXT-CODES" select="concat($DIGA-CODES, $CEF-EAS-CODES)"/>
   <xsl:variable name="CVD-CODE" select="' CVD '"/>
   <xsl:variable name="UNTDID-7143-CODES"
                 select="' AA AB AC AD AE AF AG AH AI AJ AK AL AM AN AO AP AQ AR AS AT AU AV AW AX AY AZ BA BB BC BD BE BF BG BH BI BJ BK BL BM BN BO BP BQ BR BS BT BU BV BW BX BY BZ CC CG CL CR CV DR DW EC EF EMD EN FS GB GN GMN GS HS IB IN IS IT IZ MA MF MN MP NB ON PD PL PO PPI PV QS RC RN RU RY SA SG SK SN SRS SRT SRU SRV SRW SRX SRY SRZ SS SSA SSB SSC SSD SSE SSF SSG SSH SSI SSJ SSK SSL SSM SSN SSO SSP SSQ SSR SSS SST SSU SSV SSW SSX SSY SSZ ST STA STB STC STD STE STF STG STH STI STJ STK STL STM STN STO STP STQ STR STS STT STU STV STW STX STY STZ SUA SUB SUC SUD SUE SUF SUG SUH SUI SUJ SUK SUL SUM TG TSN TSO TSP TSQ TSR TSS TST TSU UA UP VN VP VS VX ZZZ '"/>
   <xsl:variable name="UNTDID-7143-CVD-CODES"
                 select="concat($CVD-CODE, $UNTDID-7143-CODES)"/>
   <xsl:variable name="CVD-VEHICLE-CATEGORY"
                 select="('M1', 'M2', 'M3', 'N1', 'N2', 'N3')"/>
   <xsl:variable name="CVA-CODES" select="('clean', 'zero-emission', 'other')"/>
   <xsl:variable name="isExtension"
                 select="exists(/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext/ram:GuidelineSpecifiedDocumentContextParameter/ram:ID[text() = concat( 'urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_', $XR-MAJOR-MINOR-VERSION ,'#conformant#urn:xeinkauf.de:kosit:extension:xrechnung_', $XR-MAJOR-MINOR-VERSION) ] )"/>
   <xsl:variable name="isCVD"
                 select="rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext/ram:GuidelineSpecifiedDocumentContextParameter/ram:ID/text() = $XR-CVD-ID"/>
   <xsl:param name="schxslt.validate.initial-document-uri" as="xs:string?"/>
   <xsl:template name="schxslt.validate">
      <xsl:apply-templates select="document($schxslt.validate.initial-document-uri)"/>
   </xsl:template>
   <xsl:template match="root()">
      <xsl:param name="schxslt.validate.recursive-call"
                 as="xs:boolean"
                 select="false()"/>
      <xsl:choose>
         <xsl:when test="not($schxslt.validate.recursive-call) and (normalize-space($schxslt.validate.initial-document-uri) != '')">
            <xsl:apply-templates select="document($schxslt.validate.initial-document-uri)">
               <xsl:with-param name="schxslt.validate.recursive-call"
                               as="xs:boolean"
                               select="true()"/>
            </xsl:apply-templates>
         </xsl:when>
         <xsl:otherwise>
            <xsl:variable name="metadata" as="element()?">
               <svrl:metadata xmlns:dct="http://purl.org/dc/terms/"
                              xmlns:skos="http://www.w3.org/2004/02/skos/core#"
                              xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <dct:creator>
                     <dct:Agent>
                        <skos:prefLabel>
                           <xsl:value-of separator="/"
                                         select="(system-property('xsl:product-name'), system-property('xsl:product-version'))"/>
                        </skos:prefLabel>
                     </dct:Agent>
                  </dct:creator>
                  <dct:created>
                     <xsl:value-of select="current-dateTime()"/>
                  </dct:created>
                  <dct:source>
                     <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/">
                        <dct:creator>
                           <dct:Agent>
                              <skos:prefLabel>SchXslt/1.10.1 SAXON/HE 12.8</skos:prefLabel>
                              <schxslt.compile.typed-variables xmlns="https://doi.org/10.5281/zenodo.1495494#">true</schxslt.compile.typed-variables>
                           </dct:Agent>
                        </dct:creator>
                        <dct:created>2026-02-04T10:57:48.804707566Z</dct:created>
                     </rdf:Description>
                  </dct:source>
               </svrl:metadata>
            </xsl:variable>
            <xsl:variable name="report" as="element(schxslt:report)">
               <schxslt:report>
                  <xsl:call-template name="d13e206"/>
               </schxslt:report>
            </xsl:variable>
            <xsl:variable name="schxslt:report" as="node()*">
               <xsl:sequence select="$metadata"/>
               <xsl:for-each select="$report/schxslt:document">
                  <xsl:for-each select="schxslt:pattern">
                     <xsl:sequence select="node()"/>
                     <xsl:sequence select="../schxslt:rule[@pattern = current()/@id]/node()"/>
                  </xsl:for-each>
               </xsl:for-each>
            </xsl:variable>
            <svrl:schematron-output xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    title="Schematron Version 2.5.0 - XRechnung 3.0.2 compatible - CII">
               <svrl:ns-prefix-in-attribute-values prefix="rsm"
                                                   uri="urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100"/>
               <svrl:ns-prefix-in-attribute-values prefix="ccts"
                                                   uri="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2"/>
               <svrl:ns-prefix-in-attribute-values prefix="udt"
                                                   uri="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100"/>
               <svrl:ns-prefix-in-attribute-values prefix="qdt"
                                                   uri="urn:un:unece:uncefact:data:standard:QualifiedDataType:100"/>
               <svrl:ns-prefix-in-attribute-values prefix="ram"
                                                   uri="urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100"/>
               <svrl:ns-prefix-in-attribute-values prefix="u" uri="utils"/>
               <xsl:sequence select="$schxslt:report"/>
            </svrl:schematron-output>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="text() | @*" mode="#all" priority="-10"/>
   <xsl:template match="/" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template match="*" mode="#all" priority="-10">
      <xsl:apply-templates mode="#current" select="@*"/>
      <xsl:apply-templates mode="#current" select="node()"/>
   </xsl:template>
   <xsl:template name="d13e206">
      <schxslt:document>
         <schxslt:pattern id="d13e206">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="variable-pattern"
                                    id="variable-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e260">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="peppol-cii-pattern-1"
                                    id="peppol-cii-pattern-1">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e377">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="peppol-cii-pattern-0-a"
                                    id="peppol-cii-pattern-0-a">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e386">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="peppol-cii-pattern-0-b"
                                    id="peppol-cii-pattern-0-b">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e403">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="cii-pattern"
                                    id="cii-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e568">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="cii-extension-pattern"
                                    id="cii-extension-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <schxslt:pattern id="d13e630">
            <xsl:if test="exists(base-uri(root()))">
               <xsl:attribute name="documents" select="base-uri(root())"/>
            </xsl:if>
            <xsl:for-each select="root()">
               <svrl:active-pattern xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                    name="cii-cvd-pattern"
                                    id="cii-cvd-pattern">
                  <xsl:attribute name="documents" select="base-uri(.)"/>
               </svrl:active-pattern>
            </xsl:for-each>
         </schxslt:pattern>
         <xsl:apply-templates mode="d13e206" select="root()"/>
      </schxslt:document>
   </xsl:template>
   <xsl:template match="rsm:ExchangedDocumentContext" priority="41" mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "rsm:ExchangedDocumentContext" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:ExchangedDocumentContext</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:ExchangedDocumentContext</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:BusinessProcessSpecifiedDocumentContextParameter/ram:ID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R001">
                     <xsl:attribute name="test">ram:BusinessProcessSpecifiedDocumentContextParameter/ram:ID</xsl:attribute>
                     <svrl:text>Business process MUST be provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:ApplicableHeaderTradeSettlement"
                 priority="40"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:ApplicableHeaderTradeSettlement" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ApplicableHeaderTradeSettlement</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ApplicableHeaderTradeSettlement</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:TaxCurrencyCode) or normalize-space(ram:TaxCurrencyCode/text()) != normalize-space(ram:InvoiceCurrencyCode/text()))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R005">
                     <xsl:attribute name="test">not(ram:TaxCurrencyCode) or normalize-space(ram:TaxCurrencyCode/text()) != normalize-space(ram:InvoiceCurrencyCode/text())</xsl:attribute>
                     <svrl:text>VAT accounting currency code MUST be different from invoice currency code when provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode]) &lt;=1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R053">
                     <xsl:attribute name="test">count(ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode]) &lt;=1</xsl:attribute>
                     <svrl:text>No more than one tax total amount must be provided where currency id equals document currency code.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(                     count(ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID != $documentCurrencyCode]) = (if (ram:TaxCurrencyCode) then                         1                     else                         0))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R054">
                     <xsl:attribute name="test">                     count(ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID != $documentCurrencyCode]) = (if (ram:TaxCurrencyCode) then                         1                     else                         0)</xsl:attribute>
                     <svrl:text>Only one tax total amount must be provided where currency id equals tax currency code, if tax currency code (BT-6) is provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:TaxCurrencyCode and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode]) or (ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $taxCurrencyCode] &lt; 0 and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode] &lt; 0) or (ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $taxCurrencyCode] &gt;= 0 and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode] &gt;= 0))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R055">
                     <xsl:attribute name="test">not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:TaxCurrencyCode and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode]) or (ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $taxCurrencyCode] &lt; 0 and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode] &lt; 0) or (ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $taxCurrencyCode] &gt;= 0 and ram:SpecifiedTradeSettlementHeaderMonetarySummation/ram:TaxTotalAmount[@currencyID = $documentCurrencyCode] &gt;= 0)</xsl:attribute>
                     <svrl:text>Invoice total VAT amount and Invoice total VAT amount in accounting currency MUST have the same operational sign</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:BuyerTradeParty" priority="39" mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:BuyerTradeParty" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:BuyerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:BuyerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:URIUniversalCommunication/ram:URIID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R010">
                     <xsl:attribute name="test">ram:URIUniversalCommunication/ram:URIID</xsl:attribute>
                     <svrl:text>Buyer electronic address MUST be provided</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:SellerTradeParty" priority="38" mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:SellerTradeParty" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SellerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SellerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:URIUniversalCommunication/ram:URIID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R020">
                     <xsl:attribute name="test">ram:URIUniversalCommunication/ram:URIID</xsl:attribute>
                     <svrl:text>Seller electronic address MUST be provided</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:SpecifiedTradeAllowanceCharge[ram:CalculationPercent and not(ram:BasisAmount)]"
                 priority="37"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:SpecifiedTradeAllowanceCharge[ram:CalculationPercent and not(ram:BasisAmount)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge[ram:CalculationPercent and not(ram:BasisAmount)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge[ram:CalculationPercent and not(ram:BasisAmount)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R041">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>Allowance/charge base
                amount MUST be provided when allowance/charge percentage is provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:SpecifiedTradeAllowanceCharge[not(ram:CalculationPercent) and ram:BasisAmount]"
                 priority="36"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:SpecifiedTradeAllowanceCharge[not(ram:CalculationPercent) and ram:BasisAmount]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge[not(ram:CalculationPercent) and ram:BasisAmount]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge[not(ram:CalculationPercent) and ram:BasisAmount]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R042">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>Allowance/charge percentage
                MUST be provided when allowance/charge base amount is provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:SpecifiedTradeAllowanceCharge"
                 priority="35"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:SpecifiedTradeAllowanceCharge" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeAllowanceCharge</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:CalculationPercent and ram:BasisAmount) or u:slack(if (ram:ActualAmount) then ram:ActualAmount else 0, (xs:decimal(ram:BasisAmount) * xs:decimal(ram:CalculationPercent)) div 100, $slackValue))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R040">
                     <xsl:attribute name="test">not(ram:CalculationPercent and ram:BasisAmount) or u:slack(if (ram:ActualAmount) then ram:ActualAmount else 0, (xs:decimal(ram:BasisAmount) * xs:decimal(ram:CalculationPercent)) div 100, $slackValue)</xsl:attribute>
                     <svrl:text>Allowance/charge amount must equal base amount * percentage/100 if base amount and percentage exists</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'true' or normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'false')">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R043-1">
                     <xsl:attribute name="test">normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'true' or normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'false'</xsl:attribute>
                     <svrl:text>Allowance/charge ChargeIndicator value MUST equal 'true' or 'false'</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:AppliedTradeAllowanceCharge"
                 priority="34"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:AppliedTradeAllowanceCharge" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:AppliedTradeAllowanceCharge</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:AppliedTradeAllowanceCharge</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'true' or normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'false')">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R043-2">
                     <xsl:attribute name="test">normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'true' or normalize-space(ram:ChargeIndicator/udt:Indicator/text()) = 'false'</xsl:attribute>
                     <svrl:text>Allowance/charge ChargeIndicator value MUST equal 'true' or 'false'</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="                 ram:SpecifiedTradeSettlementPaymentMeans[some $code in tokenize('49 59', '\s')                     satisfies normalize-space(ram:TypeCode) = $code]"
                 priority="33"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context " ram:SpecifiedTradeSettlementPaymentMeans[some $code in tokenize('49 59', '\s') satisfies normalize-space(ram:TypeCode) = $code]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">                 ram:SpecifiedTradeSettlementPaymentMeans[some $code in tokenize('49 59', '\s')                     satisfies normalize-space(ram:TypeCode) = $code]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">                 ram:SpecifiedTradeSettlementPaymentMeans[some $code in tokenize('49 59', '\s')                     satisfies normalize-space(ram:TypeCode) = $code]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(../ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R061">
                     <xsl:attribute name="test">../ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID</xsl:attribute>
                     <svrl:text>Mandate reference MUST be provided for direct debit.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime"
                 priority="32"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(udt:DateTimeString &gt;= ../../../../ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime/udt:DateTimeString)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R110">
                     <xsl:attribute name="test">udt:DateTimeString &gt;= ../../../../ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:StartDateTime/udt:DateTimeString</xsl:attribute>
                     <svrl:text>Start date of line period MUST be within invoice period.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime"
                 priority="31"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction[ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime]/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(udt:DateTimeString &lt;= ../../../../ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime/udt:DateTimeString)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R111">
                     <xsl:attribute name="test">udt:DateTimeString &lt;= ../../../../ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod/ram:EndDateTime/udt:DateTimeString</xsl:attribute>
                     <svrl:text>End date of line period MUST be within invoice period.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:IncludedSupplyChainTradeLineItem"
                 priority="30"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="lineExtensionAmount"
                    select="                     if (ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount) then                         xs:decimal(ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount)                     else                         0"/>
      <xsl:variable name="quantity"
                    select="                     if (ram:SpecifiedLineTradeDelivery/ram:BilledQuantity) then                         xs:decimal(ram:SpecifiedLineTradeDelivery/ram:BilledQuantity)                     else                         1"/>
      <xsl:variable name="priceAmount"
                    select="                     if (ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount) then                         xs:decimal(ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount)                     else                         0"/>
      <xsl:variable name="baseQuantity"
                    select="if (ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity and xs:decimal(ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity) != 0) then xs:decimal(ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity) else if (ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity and xs:decimal(ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity) != 0) then xs:decimal(ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity) else 1"/>
      <xsl:variable name="allowancesTotal"
                    select="                     if (ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeAllowanceCharge[normalize-space(ram:ChargeIndicator/udt:Indicator) = 'false']) then                         round(sum(ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeAllowanceCharge[normalize-space(ram:ChargeIndicator/udt:Indicator) = 'false']/ram:ActualAmount/xs:decimal(.)) * 10 * 10) div 100                     else                         0"/>
      <xsl:variable name="chargesTotal"
                    select="                     if (ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeAllowanceCharge[normalize-space(ram:ChargeIndicator/udt:Indicator) = 'true']) then                         round(sum(ram:SpecifiedLineTradeSettlement/ram:SpecifiedTradeAllowanceCharge[normalize-space(ram:ChargeIndicator/udt:Indicator) = 'true']/ram:ActualAmount/xs:decimal(.)) * 10 * 10) div 100                     else                         0"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:IncludedSupplyChainTradeLineItem" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:IncludedSupplyChainTradeLineItem</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:IncludedSupplyChainTradeLineItem</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(u:slack($lineExtensionAmount, ($quantity * ($priceAmount div $baseQuantity)) + $chargesTotal - $allowancesTotal, $slackValue))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="PEPPOL-EN16931-R120">
                     <xsl:attribute name="test">u:slack($lineExtensionAmount, ($quantity * ($priceAmount div $baseQuantity)) + $chargesTotal - $allowancesTotal, $slackValue)</xsl:attribute>
                     <svrl:text>Invoice line net amount MUST equal (Invoiced quantity * (Item net price/item price base quantity) + Sum of invoice line charge amount - sum of invoice line allowance amount</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not((not(ram:SpecifiedLineTradeSettlement/ram:AdditionalReferencedDocument) or (ram:SpecifiedLineTradeSettlement/ram:AdditionalReferencedDocument/ram:TypeCode='130')))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R101">
                     <xsl:attribute name="test">(not(ram:SpecifiedLineTradeSettlement/ram:AdditionalReferencedDocument) or (ram:SpecifiedLineTradeSettlement/ram:AdditionalReferencedDocument/ram:TypeCode='130'))</xsl:attribute>
                     <svrl:text>Element Additional referenced document can only be used for Invoice line object.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:NetPriceProductTradePrice | ram:GrossPriceProductTradePrice"
                 priority="29"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:NetPriceProductTradePrice | ram:GrossPriceProductTradePrice" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:NetPriceProductTradePrice | ram:GrossPriceProductTradePrice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:NetPriceProductTradePrice | ram:GrossPriceProductTradePrice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:BasisQuantity) or xs:decimal(ram:BasisQuantity) &gt; 0)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R121">
                     <xsl:attribute name="test">not(ram:BasisQuantity) or xs:decimal(ram:BasisQuantity) &gt; 0</xsl:attribute>
                     <svrl:text>Base quantity MUST be a positive number above zero.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:NetPriceProductTradePrice/ram:BasisQuantity[@unitCode] | ram:GrossPriceProductTradePrice/ram:BasisQuantity[@unitCode]"
                 priority="28"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e260']">
            <schxslt:rule pattern="d13e260">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:NetPriceProductTradePrice/ram:BasisQuantity[@unitCode] | ram:GrossPriceProductTradePrice/ram:BasisQuantity[@unitCode]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:NetPriceProductTradePrice/ram:BasisQuantity[@unitCode] | ram:GrossPriceProductTradePrice/ram:BasisQuantity[@unitCode]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e260">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:NetPriceProductTradePrice/ram:BasisQuantity[@unitCode] | ram:GrossPriceProductTradePrice/ram:BasisQuantity[@unitCode]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(@unitCode = ../../../ram:SpecifiedLineTradeDelivery/ram:BilledQuantity/@unitCode)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R130">
                     <xsl:attribute name="test">@unitCode = ../../../ram:SpecifiedLineTradeDelivery/ram:BilledQuantity/@unitCode</xsl:attribute>
                     <svrl:text>Unit code of price base quantity MUST be same as invoiced quantity.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e260')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//*[not(name() = 'ram:ApplicableHeaderTradeDelivery') and not(*) and not(normalize-space())]"
                 priority="27"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e377']">
            <schxslt:rule pattern="d13e377">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "//*[not(name() = 'ram:ApplicableHeaderTradeDelivery') and not(*) and not(normalize-space())]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">//*[not(name() = 'ram:ApplicableHeaderTradeDelivery') and not(*) and not(normalize-space())]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e377">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">//*[not(name() = 'ram:ApplicableHeaderTradeDelivery') and not(*) and not(normalize-space())]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(false())">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R008">
                     <xsl:attribute name="test">false()</xsl:attribute>
                     <svrl:text>Document MUST not contain empty elements.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e377')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice"
                 priority="26"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e386']">
            <schxslt:rule pattern="d13e386">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e386">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:AppliedTradeAllowanceCharge/ram:ActualAmount) or ram:AppliedTradeAllowanceCharge/ram:ChargeIndicator/udt:Indicator = 'false')">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R044">
                     <xsl:attribute name="test">not(ram:AppliedTradeAllowanceCharge/ram:ActualAmount) or ram:AppliedTradeAllowanceCharge/ram:ChargeIndicator/udt:Indicator = 'false'</xsl:attribute>
                     <svrl:text>Charge on price level is NOT allowed. Only value 'false' allowed.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(ram:ChargeAmount) or xs:decimal(../ram:NetPriceProductTradePrice/ram:ChargeAmount) = xs:decimal(ram:ChargeAmount) - u:decimalOrZero(ram:AppliedTradeAllowanceCharge/ram:ActualAmount[1]))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="PEPPOL-EN16931-R046">
                     <xsl:attribute name="test">not(ram:ChargeAmount) or xs:decimal(../ram:NetPriceProductTradePrice/ram:ChargeAmount) = xs:decimal(ram:ChargeAmount) - u:decimalOrZero(ram:AppliedTradeAllowanceCharge/ram:ActualAmount[1])</xsl:attribute>
                     <svrl:text>Item net price MUST equal (Gross price - Allowance amount) when gross price is provided.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e386')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice" priority="25" mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:variable name="BT-89-path"
                    select="rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID"/>
      <xsl:variable name="BT-90-path"
                    select="rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID"/>
      <xsl:variable name="BT-91-path"
                    select="rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans/ram:PayerPartyDebtorFinancialAccount/ram:IBANID"/>
      <xsl:variable name="BG-19-not-existing"
                    select="not(exists(($BT-89-path, $BT-90-path, $BT-91-path)))"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not((($BT-89-path or $BT-91-path) and $BT-90-path) or $BG-19-not-existing)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-30">
                     <xsl:attribute name="test">(($BT-89-path or $BT-91-path) and $BT-90-path) or $BG-19-not-existing</xsl:attribute>
                     <svrl:text>[BR-DE-30] Wenn "DIRECT DEBIT" BG-19 vorhanden ist, dann muss "Bank assigned creditor identifier" BT-90 übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not((($BT-89-path or $BT-90-path) and $BT-91-path) or $BG-19-not-existing)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-31">
                     <xsl:attribute name="test">(($BT-89-path or $BT-90-path) and $BT-91-path) or $BG-19-not-existing</xsl:attribute>
                     <svrl:text>[BR-DE-31] Wenn "DIRECT DEBIT" BG-19 vorhanden ist, dann muss "Debited account identifier" BT-91 übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-1">
                     <xsl:attribute name="test">rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans</xsl:attribute>
                     <svrl:text>[BR-DE-1] Eine Rechnung (INVOICE) muss Angaben zu "PAYMENT INSTRUCTIONS" (BG-16) enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerReference[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-15">
                     <xsl:attribute name="test">rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerReference[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-15] Das Element "Buyer reference" (BT-10) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not((rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:TypeCode = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M')) or                         (rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeAllowanceCharge/ram:CategoryTradeTax = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeAllowanceCharge/ram:CategoryTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M')) or                         (rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:TypeCode = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M'))) or                     ((rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration/ram:ID[normalize-space(@schemeID)='VA' or                                                                                                                                                   normalize-space(@schemeID)='FC'][boolean(normalize-space(.))], rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTaxRepresentativeTradeParty)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-16">
                     <xsl:attribute name="test">not((rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:TypeCode = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M')) or                         (rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeAllowanceCharge/ram:CategoryTradeTax = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeAllowanceCharge/ram:CategoryTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M')) or                         (rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:TypeCode = 'VAT' and                          rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:CategoryCode = ('S', 'Z', 'E', 'AE', 'K', 'G', 'L', 'M'))) or                     ((rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration/ram:ID[normalize-space(@schemeID)='VA' or                                                                                                                                                   normalize-space(@schemeID)='FC'][boolean(normalize-space(.))], rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTaxRepresentativeTradeParty))</xsl:attribute>
                     <svrl:text>[BR-DE-16] Wenn in einer Rechnung die Steuercodes S, Z, E, AE, K, G, L oder M verwendet werden, muss mindestens eines der Elemente "Seller VAT identifier" (BT-31), "Seller tax registration identifier" (BT-32)
          oder "SELLER TAX REPRESENTATIVE PARTY" (BG-11) übermittelt werden.
      </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(normalize-space(rsm:ExchangedDocument/ram:TypeCode) = ('326', '380', '384', '389', '381', '875', '876', '877'))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-17">
                     <xsl:attribute name="test">normalize-space(rsm:ExchangedDocument/ram:TypeCode) = ('326', '380', '384', '389', '381', '875', '876', '877')</xsl:attribute>
                     <svrl:text>[BR-DE-17] Mit dem Element "Invoice type code" (BT-3) sollen ausschließlich folgende Codes aus der Codeliste UNTDID 1001 übermittelt werden: 326 (Partial invoice), 380 (Commercial invoice), 384 (Corrected invoice), 389 (Self-billed invoice) und 381 (Credit note),875 (Partial construction invoice), 876 (Partial final construction invoice), 877 (Final construction invoice).</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(every $line                        in rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:Description[1]/tokenize(. , '(\r?\n)')[starts-with( normalize-space(.) , '#')]                        satisfies matches ( normalize-space ($line), $XR-SKONTO-REGEX ) and                     matches( rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:Description[1]/tokenize(. ,  '#.+#')[last()], '^\s*\n' ))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-18">
                     <xsl:attribute name="test">every $line                        in rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:Description[1]/tokenize(. , '(\r?\n)')[starts-with( normalize-space(.) , '#')]                        satisfies matches ( normalize-space ($line), $XR-SKONTO-REGEX ) and                     matches( rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:Description[1]/tokenize(. ,  '#.+#')[last()], '^\s*\n' )</xsl:attribute>
                     <svrl:text>[BR-DE-18] Skonto Zeilen in <xsl:value-of select="name()"/> muessen diesem regulärem Ausdruck entsprechen: <xsl:value-of select="$XR-SKONTO-REGEX"/>. Die Informationen zur Gewährung von Skonto müssen wie folgt im Element "Payment terms" (BT-20) übermittelt werden: Anzugeben ist im ersten Segment "SKONTO", im zweiten "TAGE=n", im dritten "PROZENT=n". Prozentzahlen sind ohne Vorzeichen sowie mit Punkt getrennt von zwei Nachkommastellen anzugeben. Liegt dem zu berechnenden Betrag nicht BT-115, "fälliger Betrag" zugrunde, sondern nur ein Teil des fälligen Betrags der Rechnung, ist der Grundwert zur Berechnung von Skonto als viertes Segment "BASISBETRAG=n" gemäß dem semantischen Datentypen Amount anzugeben. Jeder Eintrag beginnt mit einer #, die Segmente sind mit einer # getrennt und eine Zeile schließt mit einer # ab. Am Ende einer vollständigen Skontoangabe muss ein XML-konformer Zeilenumbruch folgen. Alle Angaben zur Gewährung von Skonto müssen in Großbuchstaben gemacht werden. Zusätzliches Whitespace (Leerzeichen, Tabulatoren oder Zeilenumbrüche) ist nicht zulässig. Andere Zeichen oder Texte als in den oberen Vorgaben genannt sind nicht zulässig.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(count(//ram:AdditionalReferencedDocument) = count(//ram:AdditionalReferencedDocument[not(./ram:AttachmentBinaryObject/@filename = preceding-sibling::ram:AdditionalReferencedDocument/ram:AttachmentBinaryObject/@filename)]))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-22">
                     <xsl:attribute name="test">count(//ram:AdditionalReferencedDocument) = count(//ram:AdditionalReferencedDocument[not(./ram:AttachmentBinaryObject/@filename = preceding-sibling::ram:AdditionalReferencedDocument/ram:AttachmentBinaryObject/@filename)])</xsl:attribute>
                     <svrl:text>[BR-DE-22] Not all filename attributes of the embeddedDocumentBinaryObject elements are unique</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(normalize-space(rsm:ExchangedDocument/ram:TypeCode) = '384') or                     (rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:InvoiceReferencedDocument))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-26">
                     <xsl:attribute name="test">not(normalize-space(rsm:ExchangedDocument/ram:TypeCode) = '384') or                     (rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:InvoiceReferencedDocument)</xsl:attribute>
                     <svrl:text>[BR-DE-26] Wenn im Element Invoice type code (BT-3) der Code 384 (Corrected invoice) übergeben wird, soll PRECEDING INVOICE REFERENCE BG-3 mind. einmal vorhanden sein.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext"
                 priority="24"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-CIUS-ID or                     ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-EXTENSION-ID or                     ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-CVD-ID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-21">
                     <xsl:attribute name="test">ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-CIUS-ID or                     ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-EXTENSION-ID or                     ram:GuidelineSpecifiedDocumentContextParameter/ram:ID = $XR-CVD-ID</xsl:attribute>
                     <svrl:text>[BR-DE-21] Das Element "Specification identifier" (BT-24) soll syntaktisch der Kennung des Standards XRechnung entsprechen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty"
                 priority="23"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:DefinedTradeContact)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-2">
                     <xsl:attribute name="test">ram:DefinedTradeContact</xsl:attribute>
                     <svrl:text>[BR-DE-2] Die Gruppe "SELLER CONTACT" (BG-6) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress"
                 priority="22"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:CityName[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-3">
                     <xsl:attribute name="test">ram:CityName[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-3] Das Element "Seller city" (BT-37) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:PostcodeCode[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-4">
                     <xsl:attribute name="test">ram:PostcodeCode[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-4] Das Element "Seller post code" (BT-38) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact"
                 priority="21"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not((ram:PersonName,ram:DepartmentName)[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-5">
                     <xsl:attribute name="test">(ram:PersonName,ram:DepartmentName)[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-5] Das Element "Seller contact point" (BT-41) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:TelephoneUniversalCommunication/ram:CompleteNumber[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-6">
                     <xsl:attribute name="test">ram:TelephoneUniversalCommunication/ram:CompleteNumber[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-6] Das Element "Seller contact telephone number" (BT-42) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:EmailURIUniversalCommunication/ram:URIID[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-7">
                     <xsl:attribute name="test">ram:EmailURIUniversalCommunication/ram:URIID[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-7] Das Element "Seller contact email address" (BT-43) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(matches(normalize-space(ram:TelephoneUniversalCommunication/ram:CompleteNumber), $XR-TELEPHONE-REGEX))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-27">
                     <xsl:attribute name="test">matches(normalize-space(ram:TelephoneUniversalCommunication/ram:CompleteNumber), $XR-TELEPHONE-REGEX)</xsl:attribute>
                     <svrl:text>[BR-DE-27] In BT-42 sollen mindestens drei Ziffern enthalten sein.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(matches(normalize-space(ram:EmailURIUniversalCommunication/ram:URIID), $XR-EMAIL-REGEX))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-28">
                     <xsl:attribute name="test">matches(normalize-space(ram:EmailURIUniversalCommunication/ram:URIID), $XR-EMAIL-REGEX)</xsl:attribute>
                     <svrl:text>[BR-DE-28] In BT-43 soll genau ein @-Zeichen enthalten sein, welches nicht von einem Leerzeichen, einem Punkt, aber mindestens zwei Zeichen auf beiden Seiten flankiert werden soll. Ein Punkt sollte nicht am Anfang oder am Ende stehen.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress"
                 priority="20"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:CityName[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-8">
                     <xsl:attribute name="test">ram:CityName[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-8] Das Element "Buyer city" (BT-52) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:PostcodeCode[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-9">
                     <xsl:attribute name="test">ram:PostcodeCode[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-9] Das Element "Buyer post code" (BT-53) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument[ram:TypeCode = '916']"
                 priority="19"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument[ram:TypeCode = '916']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument[ram:TypeCode = '916']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:AdditionalReferencedDocument[ram:TypeCode = '916']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(exists(ram:URIID)) or (matches(ram:URIID, $XR-URL-REGEX)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-TMP-2">
                     <xsl:attribute name="test">not(exists(ram:URIID)) or (matches(ram:URIID, $XR-URL-REGEX))</xsl:attribute>
                     <svrl:text>[BR-TMP-2] BT-124 "External document location" muss eine absolute URL mit gültigem Schema enthalten.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:PostalTradeAddress"
                 priority="18"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:PostalTradeAddress" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:PostalTradeAddress</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:CityName[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-10">
                     <xsl:attribute name="test">ram:CityName[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-10] Das Element "Deliver to city" (BT-77) muss übermittelt werden, wenn die Gruppe "DELIVER TO ADDRESS" (BG-15) übermittelt wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:PostcodeCode[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-11">
                     <xsl:attribute name="test">ram:PostcodeCode[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-11] Das Element "Deliver to post code" (BT-78) muss übermittelt werden, wenn die Gruppe "DELIVER TO ADDRESS" (BG-15) übermittelt wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('30','58')]"
                 priority="17"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('30','58')]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('30','58')]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('30','58')]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(normalize-space(ram:TypeCode) = '58') or                     matches(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')), '^[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{0,30}$') and                     xs:integer(string-join(for $cp in string-to-codepoints(concat(substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),5),upper-case(substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),1,2)),substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),3,2))) return  (if($cp &gt; 64) then string($cp - 55) else  string($cp - 48)),'')) mod 97 = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-19">
                     <xsl:attribute name="test">not(normalize-space(ram:TypeCode) = '58') or                     matches(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')), '^[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{0,30}$') and                     xs:integer(string-join(for $cp in string-to-codepoints(concat(substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),5),upper-case(substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),1,2)),substring(normalize-space(replace(ram:PayeePartyCreditorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),3,2))) return  (if($cp &gt; 64) then string($cp - 55) else  string($cp - 48)),'')) mod 97 = 1</xsl:attribute>
                     <svrl:text>[BR-DE-19] "Payment account identifier" (BT-84) soll eine korrekte IBAN enthalten, wenn in "Payment means type code" (BT-81) mit dem Code 58 SEPA als Zahlungsmittel gefordert wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:PayeePartyCreditorFinancialAccount)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-23-a">
                     <xsl:attribute name="test">ram:PayeePartyCreditorFinancialAccount</xsl:attribute>
                     <svrl:text>[BR-DE-23-a] Wenn BT-81 "Payment means type code" einen Schlüssel für Überweisungen enthält (30, 58), muss BG-17 "CREDIT TRANSFER" übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(ram:ApplicableTradeSettlementFinancialCard) and                     not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                         /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                         ram:PayerPartyDebtorFinancialAccount/ram:IBANID))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-23-b">
                     <xsl:attribute name="test">not(ram:ApplicableTradeSettlementFinancialCard) and                     not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                         /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                         ram:PayerPartyDebtorFinancialAccount/ram:IBANID)</xsl:attribute>
                     <svrl:text>[BR-DE-23-b] Wenn BT-81 "Payment means type code" einen Schlüssel für Überweisungen enthält (30, 58), dürfen BG-18 und BG-19 nicht übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('48','54','55')]"
                 priority="16"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('48','54','55')]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('48','54','55')]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = ('48','54','55')]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:ApplicableTradeSettlementFinancialCard)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-24-a">
                     <xsl:attribute name="test">ram:ApplicableTradeSettlementFinancialCard</xsl:attribute>
                     <svrl:text>[BR-DE-24-a] Wenn BT-81 "Payment means type code" einen Schlüssel für Kartenzahlungen enthält (48, 54, 55), muss genau BG-18 "PAYMENT CARD INFORMATION" übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(ram:PayeePartyCreditorFinancialAccount) and                     not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                         /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                         ram:PayerPartyDebtorFinancialAccount/ram:IBANID))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-24-b">
                     <xsl:attribute name="test">not(ram:PayeePartyCreditorFinancialAccount) and                     not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                         /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                         ram:PayerPartyDebtorFinancialAccount/ram:IBANID)</xsl:attribute>
                     <svrl:text>[BR-DE-24-b] Wenn BT-81 "Payment means type code" einen Schlüssel für Kartenzahlungen enthält (48, 54, 55), dürfen BG-17 und BG-19 nicht übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = '59']"
                 priority="15"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = '59']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = '59']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans[normalize-space(ram:TypeCode) = '59']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(normalize-space(ram:TypeCode) = '59') or                     matches(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')), '^[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{0,30}$') and                     xs:decimal(string-join(for $cp in string-to-codepoints(concat(substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),5),upper-case(substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),1,2)),substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),3,2))) return  (if($cp &gt; 64) then string($cp - 55) else  string($cp - 48)),'')) mod 97 = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DE-20">
                     <xsl:attribute name="test">not(normalize-space(ram:TypeCode) = '59') or                     matches(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')), '^[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{0,30}$') and                     xs:decimal(string-join(for $cp in string-to-codepoints(concat(substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),5),upper-case(substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),1,2)),substring(normalize-space(replace(ram:PayerPartyDebtorFinancialAccount/ram:IBANID, '([ \n\r\t\s])', '')),3,2))) return  (if($cp &gt; 64) then string($cp - 55) else  string($cp - 48)),'')) mod 97 = 1</xsl:attribute>
                     <svrl:text>[BR-DE-20] "Debited account identifier" (BT-91) soll eine korrekte IBAN enthalten, wenn in "Payment means type code" (BT-81) mit dem Code 59 SEPA als Zahlungsmittel gefordert wird.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                     /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                     ram:PayerPartyDebtorFinancialAccount/ram:IBANID)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-25-a">
                     <xsl:attribute name="test">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradePaymentTerms/ram:DirectDebitMandateID or                     /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:CreditorReferenceID or                     ram:PayerPartyDebtorFinancialAccount/ram:IBANID</xsl:attribute>
                     <svrl:text>[BR-DE-25-a] Wenn BT-81 "Payment means type code" einen Schlüssel für Lastschriften enthält (59), muss genau BG-19 "DIRECT DEBIT" übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(ram:PayeePartyCreditorFinancialAccount) and                     not(ram:PayeeSpecifiedCreditorFinancialInstitution) and                     not(ram:PayerSpecifiedDebtorFinancialInstitution) and                     not(ram:ApplicableTradeSettlementFinancialCard))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-25-b">
                     <xsl:attribute name="test">not(ram:PayeePartyCreditorFinancialAccount) and                     not(ram:PayeeSpecifiedCreditorFinancialInstitution) and                     not(ram:PayerSpecifiedDebtorFinancialInstitution) and                     not(ram:ApplicableTradeSettlementFinancialCard)</xsl:attribute>
                     <svrl:text>[BR-DE-25-b] Wenn BT-81 "Payment means type code" einen Schlüssel für Lastschriften enthält (59), dürfen BG-17 und BG-18 nicht übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:ApplicableTradeTax"
                 priority="14"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:ApplicableTradeTax" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:ApplicableTradeTax</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:ApplicableTradeTax</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:RateApplicablePercent[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-14">
                     <xsl:attribute name="test">ram:RateApplicablePercent[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>[BR-DE-14] Das Element "VAT category rate" (BT-119) muss übermittelt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
                 priority="13"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(               ram:ApplicableHeaderTradeDelivery/ram:ActualDeliverySupplyChainEvent/ram:OccurrenceDateTime               or ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod               or (every $line in ram:IncludedSupplyChainTradeLineItem               satisfies $line/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="information"
                                      id="BR-DE-TMP-32">
                     <xsl:attribute name="test">               ram:ApplicableHeaderTradeDelivery/ram:ActualDeliverySupplyChainEvent/ram:OccurrenceDateTime               or ram:ApplicableHeaderTradeSettlement/ram:BillingSpecifiedPeriod               or (every $line in ram:IncludedSupplyChainTradeLineItem               satisfies $line/ram:SpecifiedLineTradeSettlement/ram:BillingSpecifiedPeriod)</xsl:attribute>
                     <svrl:text>
              [BR-DE-TMP-32] Eine Rechnung sollte zur Angabe des Liefer-/Leistungsdatums entweder BT-72 "Actual delivery date", BG-14 "Invoicing period" oder in jeder Rechnungsposition BG-26 "Invoice line period" enthalten.
          </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem"
                 priority="12"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e403']">
            <schxslt:rule pattern="d13e403">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e403">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity                         and                         ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity)                     or                     (ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity =                      ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity                      and                      (not(ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity/@unitCode                      and                      ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity/@unitCode)                       or                       ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity/@unitCode =                       ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity/@unitCode)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-TMP-3">
                     <xsl:attribute name="test">not(ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity                         and                         ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity)                     or                     (ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity =                      ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity                      and                      (not(ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity/@unitCode                      and                      ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity/@unitCode)                       or                       ram:SpecifiedLineTradeAgreement/ram:GrossPriceProductTradePrice/ram:BasisQuantity/@unitCode =                       ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:BasisQuantity/@unitCode))</xsl:attribute>
                     <svrl:text>[BR-TMP-3] Wenn BT-149 (Item price base quantity) sowohl in GrossPriceProductTradePrice als auch in NetPriceProductTradePrice vorhanden ist, müssen die Werte identisch sein. Wenn BT-150 (unit of measure code) auf dem NetPrice-Pfad vorhanden ist, muss es auch auf dem GrossPrice-Pfad vorhanden und identisch sein.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e403')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:AssociatedDocumentLineDocument[$isExtension]"
                 priority="11"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:AssociatedDocumentLineDocument[$isExtension]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:AssociatedDocumentLineDocument[$isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:AssociatedDocumentLineDocument[$isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(exists(//ram:ParentLineID)))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="warning"
                                      id="BR-DEX-15">
                     <xsl:attribute name="test">not(exists(//ram:ParentLineID))</xsl:attribute>
                     <svrl:text>
              [BR-DEX-15] This CII file might use the concept of Sub Invoice Lines. However XRechnung does not support this.
          </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="//ram:GlobalID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTradeProduct) and not(ancestor::ram:ShipToTradeParty)]"
                 priority="10"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "//ram:GlobalID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTradeProduct) and not(ancestor::ram:ShipToTradeParty)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">//ram:GlobalID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTradeProduct) and not(ancestor::ram:ShipToTradeParty)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">//ram:GlobalID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTradeProduct) and not(ancestor::ram:ShipToTradeParty)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-04">
                     <xsl:attribute name="test">((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' '))))</xsl:attribute>
                     <svrl:text>[BR-DEX-04] Any scheme identifier in <xsl:value-of select="name()"/> MUST be coded using one of the ISO 6523 ICD list. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:ID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTaxRegistration)]"
                 priority="9"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:ID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTaxRegistration)]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTaxRegistration)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ID[@schemeID and $isExtension][not(ancestor::ram:SpecifiedTaxRegistration)]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-05">
                     <xsl:attribute name="test">((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' '))))</xsl:attribute>
                     <svrl:text>[BR-DEX-05] Any scheme identifier in <xsl:value-of select="name()"/> MUST be coded using one of the ISO 6523 ICD list. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:SpecifiedTradeProduct/ram:GlobalID[@schemeID and $isExtension]"
                 priority="8"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:SpecifiedTradeProduct/ram:GlobalID[@schemeID and $isExtension]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeProduct/ram:GlobalID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:SpecifiedTradeProduct/ram:GlobalID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-06">
                     <xsl:attribute name="test">((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' '))))</xsl:attribute>
                     <svrl:text>[BR-DEX-06] Any scheme identifier in <xsl:value-of select="name()"/> MUST be coded using one of the ISO 6523 ICD list. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:URIUniversalCommunication/ram:URIID[@schemeID and $isExtension]"
                 priority="7"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:URIUniversalCommunication/ram:URIID[@schemeID and $isExtension]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:URIUniversalCommunication/ram:URIID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:URIUniversalCommunication/ram:URIID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@schemeID), ' ')) and contains($CEF-EAS-EXT-CODES, concat(' ', normalize-space(@schemeID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-07">
                     <xsl:attribute name="test">((not(contains(normalize-space(@schemeID), ' ')) and contains($CEF-EAS-EXT-CODES, concat(' ', normalize-space(@schemeID), ' '))))</xsl:attribute>
                     <svrl:text>[BR-DEX-07] Any scheme identifier for an Endpoint Identifier in <xsl:value-of select="name()"/> MUST belong to the CEF EAS code list. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:GlobalID[@schemeID and $isExtension]"
                 priority="6"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:GlobalID[@schemeID and $isExtension]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:GlobalID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:ApplicableHeaderTradeDelivery/ram:ShipToTradeParty/ram:GlobalID[@schemeID and $isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-08">
                     <xsl:attribute name="test">((not(contains(normalize-space(@schemeID), ' ')) and contains($ISO-6523-ICD-EXT-CODES, concat(' ', normalize-space(@schemeID), ' '))))</xsl:attribute>
                     <svrl:text>[BR-DEX-08] Any scheme identifier for a Delivery location identifier in <xsl:value-of select="name()"/> MUST be coded using one of the ISO 6523 ICD list. </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="ram:AttachmentBinaryObject[$isExtension]"
                 priority="5"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e568']">
            <schxslt:rule pattern="d13e568">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "ram:AttachmentBinaryObject[$isExtension]" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:AttachmentBinaryObject[$isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e568">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">ram:AttachmentBinaryObject[$isExtension]</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(.[@mimeCode = 'application/pdf' or               @mimeCode = 'image/png' or               @mimeCode = 'image/jpeg' or               @mimeCode = 'text/csv' or               @mimeCode = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' or               @mimeCode = 'application/vnd.oasis.opendocument.spreadsheet' or               @mimeCode = 'application/xml'])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DEX-01">
                     <xsl:attribute name="test">.[@mimeCode = 'application/pdf' or               @mimeCode = 'image/png' or               @mimeCode = 'image/jpeg' or               @mimeCode = 'text/csv' or               @mimeCode = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' or               @mimeCode = 'application/vnd.oasis.opendocument.spreadsheet' or               @mimeCode = 'application/xml']</xsl:attribute>
                     <svrl:text>[BR-DEX-01] Das Element <xsl:value-of select="name()"/> "Attached Document" (BT-125) benutzt einen nicht zulässigen MIME-Code: <xsl:value-of select="@mimeCode"/>. Im Falle einer Extension darf zusätzlich zu der Liste der mime codes (definiert in Abschnitt 8.2, "Binary Object") der MIME-Code application/xml genutzt werden.</svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e568')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction"
                 priority="4"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e630']">
            <schxslt:rule pattern="d13e630">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e630">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct[ram:DesignatedProductClassification/ram:ClassCode/@listID = 'CVD' and ram:ApplicableProductCharacteristic/ram:Description = 'cva'])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-03">
                     <xsl:attribute name="test">ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct[ram:DesignatedProductClassification/ram:ClassCode/@listID = 'CVD' and ram:ApplicableProductCharacteristic/ram:Description = 'cva']</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-03] In einer Rechnung muss mindestens eine <xsl:value-of select="name()"/> INVOICE LINE (BG-25) enthalten sein, in der der Scheme identifier von <xsl:value-of select="name()"/> "Item classification identifier" (BT-158) den Wert 'CVD' und der <xsl:value-of select="name()"/> "Item attribute name" (BT-160) den Wert 'cva' enthält.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e630')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct"
                 priority="3"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e630']">
            <schxslt:rule pattern="d13e630">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e630">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(not(ram:ApplicableProductCharacteristic/ram:Description = 'cva') or count(ram:DesignatedProductClassification/ram:ClassCode[@listID = 'CVD']) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-06-b">
                     <xsl:attribute name="test">not(ram:ApplicableProductCharacteristic/ram:Description = 'cva') or count(ram:DesignatedProductClassification/ram:ClassCode[@listID = 'CVD']) = 1</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-06-b] Wenn <xsl:value-of select="name()"/> "Item attribute name" (BT-160) mit dem Wert 'cva' angegeben ist, muss in derselben Rechnungszeile genau ein <xsl:value-of select="name()"/> "Item classification identifier" (BT-158) mit dem Scheme identifier 'CVD' vorhanden sein.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(ram:DesignatedProductClassification/ram:ClassCode/@listID = 'CVD') or count(ram:ApplicableProductCharacteristic[ram:Description = 'cva']) = 1)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-06-a">
                     <xsl:attribute name="test">not(ram:DesignatedProductClassification/ram:ClassCode/@listID = 'CVD') or count(ram:ApplicableProductCharacteristic[ram:Description = 'cva']) = 1</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-06-a] Wenn der Scheme identifier von <xsl:value-of select="name()"/> "Item classification identifier" (BT-158) mit dem Wert 'CVD' angegeben ist, muss in derselben Rechnungszeile genau ein <xsl:value-of select="name()"/> "Item attribute name" (BT-160) mit dem Wert 'cva' vorhanden sein.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e630')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:DesignatedProductClassification/ram:ClassCode"
                 priority="2"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e630']">
            <schxslt:rule pattern="d13e630">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:DesignatedProductClassification/ram:ClassCode" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:DesignatedProductClassification/ram:ClassCode</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e630">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:DesignatedProductClassification/ram:ClassCode</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(((not(contains(normalize-space(@listID), ' ')) and contains($UNTDID-7143-CVD-CODES, concat(' ', normalize-space(@listID), ' ')))))">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-TMP-CVD-01">
                     <xsl:attribute name="test">((not(contains(normalize-space(@listID), ' ')) and contains($UNTDID-7143-CVD-CODES, concat(' ', normalize-space(@listID), ' '))))</xsl:attribute>
                     <svrl:text>
                [BR-TMP-CVD-01] Das Bildungsschema für <xsl:value-of select="name()"/> "Item classification identifier" (BT-158) ist aus der Codeliste UNTDID 7143 zu wählen.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(not(normalize-space(@listID) = 'CVD') or normalize-space(.) = $CVD-VEHICLE-CATEGORY)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-04">
                     <xsl:attribute name="test">not(normalize-space(@listID) = 'CVD') or normalize-space(.) = $CVD-VEHICLE-CATEGORY</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-04] Ein <xsl:value-of select="name()"/> "Item classification identifier" (BT-158) mit dem Scheme identifier 'CVD' muss einen Wert aus der Liste der zulässigen Fahrzeugkategorien enthalten.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e630')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:ApplicableProductCharacteristic[ram:Description = 'cva']"
                 priority="1"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e630']">
            <schxslt:rule pattern="d13e630">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:ApplicableProductCharacteristic[ram:Description = 'cva']" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:ApplicableProductCharacteristic[ram:Description = 'cva']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e630">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/ram:SpecifiedTradeProduct/ram:ApplicableProductCharacteristic[ram:Description = 'cva']</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(normalize-space(ram:Value) = $CVA-CODES)">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-05">
                     <xsl:attribute name="test">normalize-space(ram:Value) = $CVA-CODES</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-05] Wenn innerhalb von <xsl:value-of select="name()"/> ITEM ATTRIBUTES (BG-32) der <xsl:value-of select="name()"/> "Item attribute name" (BT-160) den Wert 'cva' hat, muss der <xsl:value-of select="name()"/> "Item attribute value" (BT-161) einen der zulässigen Werte enthalten.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e630')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:template match="/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement"
                 priority="0"
                 mode="d13e206">
      <xsl:param name="schxslt:patterns-matched" as="xs:string*"/>
      <xsl:choose>
         <xsl:when test="$schxslt:patterns-matched[. = 'd13e630']">
            <schxslt:rule pattern="d13e630">
               <xsl:comment xmlns:svrl="http://purl.oclc.org/dsdl/svrl">WARNING: Rule for context "/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement" shadowed by preceding rule</xsl:comment>
               <svrl:suppressed-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:suppressed-rule>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="$schxslt:patterns-matched"/>
            </xsl:next-match>
         </xsl:when>
         <xsl:otherwise>
            <schxslt:rule pattern="d13e630">
               <svrl:fired-rule xmlns:svrl="http://purl.oclc.org/dsdl/svrl">
                  <xsl:attribute name="context">/rsm:CrossIndustryInvoice[$isCVD]/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement</xsl:attribute>
                  <xsl:variable name="documentUri" as="xs:anyURI?" select="document-uri()"/>
                  <xsl:if test="exists($documentUri)">
                     <xsl:attribute name="document" select="$documentUri"/>
                  </xsl:if>
               </svrl:fired-rule>
               <xsl:if test="not(ram:ContractReferencedDocument/ram:IssuerAssignedID[boolean(normalize-space(.))])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-01">
                     <xsl:attribute name="test">ram:ContractReferencedDocument/ram:IssuerAssignedID[boolean(normalize-space(.))]</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-01] Das Element <xsl:value-of select="name()"/> "Contract reference" (BT-12) muss übermittelt werden.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
               <xsl:if test="not(ram:AdditionalReferencedDocument[normalize-space(ram:TypeCode) = '50' and normalize-space(ram:IssuerAssignedID)])">
                  <svrl:failed-assert xmlns:svrl="http://purl.oclc.org/dsdl/svrl"
                                      location="{schxslt:location(.)}"
                                      flag="fatal"
                                      id="BR-DE-CVD-02">
                     <xsl:attribute name="test">ram:AdditionalReferencedDocument[normalize-space(ram:TypeCode) = '50' and normalize-space(ram:IssuerAssignedID)]</xsl:attribute>
                     <svrl:text>
                [BR-DE-CVD-02] Das Element <xsl:value-of select="name()"/> "Tender or lot reference" (BT-17) muss übermittelt werden.
            </svrl:text>
                  </svrl:failed-assert>
               </xsl:if>
            </schxslt:rule>
            <xsl:next-match>
               <xsl:with-param name="schxslt:patterns-matched"
                               as="xs:string*"
                               select="($schxslt:patterns-matched, 'd13e630')"/>
            </xsl:next-match>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>
   <xsl:function name="schxslt:location" as="xs:string">
      <xsl:param name="node" as="node()"/>
      <xsl:variable name="segments" as="xs:string*">
         <xsl:for-each select="($node/ancestor-or-self::node())">
            <xsl:variable name="position">
               <xsl:number level="single"/>
            </xsl:variable>
            <xsl:choose>
               <xsl:when test=". instance of element()">
                  <xsl:value-of select="concat('Q{', namespace-uri(.), '}', local-name(.), '[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of attribute()">
                  <xsl:value-of select="concat('@Q{', namespace-uri(.), '}', local-name(.))"/>
               </xsl:when>
               <xsl:when test=". instance of processing-instruction()">
                  <xsl:value-of select="concat('processing-instruction(&#34;', name(.), '&#34;)[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of comment()">
                  <xsl:value-of select="concat('comment()[', $position, ']')"/>
               </xsl:when>
               <xsl:when test=". instance of text()">
                  <xsl:value-of select="concat('text()[', $position, ']')"/>
               </xsl:when>
               <xsl:otherwise/>
            </xsl:choose>
         </xsl:for-each>
      </xsl:variable>
      <xsl:value-of select="concat('/', string-join($segments, '/'))"/>
   </xsl:function>
</xsl:transform>
