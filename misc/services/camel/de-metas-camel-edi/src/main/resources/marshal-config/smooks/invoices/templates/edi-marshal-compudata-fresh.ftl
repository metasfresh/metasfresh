<#-- Assign system variables -->
<#--setting number_format="0.####"-->
<#-- Assign missing fields -->
<#-- H100 -->
<#assign PaySkonto = "">
<#assign Skonto = 0>
<#assign DiscountTextH100 = "">
<#-- P100 -->
<#assign MessageNoP100 = "">
<#assign ArticleNoSupplier = "">
<#assign CuPerTuQty = "">
<#assign QtyUnit3 = "">
<#assign DiscountTextP100 = "">
<#-- T100 -->
<#assign MessageNoT100 = "">
<#assign TaxRate2 = 0>
<#assign TaxAmount2 = 0>
<#assign TaxableAmount2 = 0>
<#assign TaxRate3 = 0>
<#assign TaxAmount3 = 0>
<#assign TaxableAmount3 = 0>
<#-- End Assign missing fields -->
<#-- Actual EDI file -->
<#-- H000 -->
${"H000"?right_pad(4)?substring(0, 4)}<#lt><#t>
${cctopInvoice.cctop000V.senderGln?right_pad(35)?substring(0, 35)}<#lt><#t>
${"14"?right_pad(4)?substring(0, 4)}<#lt><#t>
${cctopInvoice.cctop000V.gln?right_pad(35)?substring(0, 35)}<#lt><#t>
${"14"?right_pad(4)?substring(0, 4)}<#lt><#t>
${cctopInvoice.cctop000V.interchangeReferenceNo?right_pad(35)?substring(0, 35)}<#lt><#t>
${"INHEDI"?right_pad(6)?substring(0, 6)}<#lt><#t>
${"INVOIC"?right_pad(25)?substring(0, 25)}<#lt><#t>
${"D01B"?right_pad(25)?substring(0, 25)}<#lt><#t>
${"1"?left_pad(4, "0")}<#lt><#t>
${"UNOC"?right_pad(4)?substring(0, 4)}<#lt><#t>
${"4"?right_pad(1)?substring(0, 1)}<#lt><#t>
${cctopInvoice.currentDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
${cctopInvoice.currentDate?string("mmHH")?right_pad(4)?substring(0, 4)}<#lt><#t>
<#if cctopInvoice.eancomDoctype == "380" || cctopInvoice.eancomDoctype == "383" || cctopInvoice.eancomDoctype == "84"><#-- normal INVOIC -->
	${"INVOIC0102"?right_pad(14)?substring(0, 14)}<#lt><#t>
<#elseif cctopInvoice.eancomDoctype == "381" || cctopInvoice.eancomDoctype == "83"><#-- CreditMemo -->
	${"INVOIC0202"?right_pad(14)?substring(0, 14)}<#lt><#t>
<#else><#-- Error, this will throw exception -->
	${Err.EAN.COM.DocType?right_pad(14)?substring(0, 14)}<#lt><#t>
</#if>
${"EANCOM76170270000007640134460009903"?right_pad(35)?substring(0, 35)}<#lt><#t>
${cctopInvoice.cctop000V.isTest?right_pad(1)?substring(0, 1)}<#lt><#t>

<#-- H100 -->
<#-- StartPosition=0001 -->${"H100"?right_pad(4)?substring(0, 4)}<#lt><#t>
<#-- StartPosition=0005 -->${cctopInvoice.cctop000V.gln?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0040 -->${"1"?right_pad(14)?substring(0, 14)}<#lt><#t>
<#-- StartPosition=0054 -->${"INVOIC"?right_pad(6)?substring(0, 6)}<#lt><#t>
<#-- StartPosition=0060 -->${"D"?right_pad(3)?substring(0, 3)}<#lt><#t>
<#-- StartPosition=0063 -->${"01B"?right_pad(3)?substring(0, 3)}<#lt><#t>
<#-- StartPosition=0066 -->${"UN"?right_pad(2)?substring(0, 2)}<#lt><#t>
<#-- StartPosition=0068 -->${"EAN010"?right_pad(6)?substring(0, 6)}<#lt><#t>
<#-- StartPosition=0074 -->${cctopInvoice.eancomDoctype?right_pad(3)?substring(0, 3)}<#lt><#t>
<#-- StartPosition=0077 -->${cctopInvoice.invoiceDocumentno?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0112 -->${cctopInvoice.dateInvoiced?string("yyyyMMddHHmmss")?right_pad(14)?substring(0, 14)}<#lt><#t>
<#if cctopInvoice.movementDate??><#-- NULL handling -->
	<#-- StartPosition=0126 -->${cctopInvoice.movementDate?string("yyyyMMddHHmmss")?right_pad(14)?substring(0, 14)}<#lt><#t>
<#else>
	<#-- StartPosition=0126 -->${cctopInvoice.dateInvoiced?string("yyyyMMddHHmmss")?right_pad(14)?substring(0, 14)}<#lt><#t>
</#if>
<#-- StartPosition=0140 -->${""?right_pad(16)?substring(0, 16)}<#lt><#t>
<#-- StartPosition=0156 -->${cctopInvoice.cctop111V.poReference?right_pad(35)?substring(0, 35)}<#lt><#t>
<#if cctopInvoice.cctop111V.dateOrdered??><#-- NULL handling -->
	<#-- StartPosition=0191 -->${cctopInvoice.cctop111V.dateOrdered?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
<#else>
	<#-- StartPosition=0191 -->${"19700101"?right_pad(8)?substring(0, 8)}<#lt><#t>
</#if>
<#-- StartPosition=0199 -->${(cctopInvoice.shipmentDocumentno!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
<#if cctopInvoice.movementDate??><#-- NULL handling -->
	<#-- StartPosition=0234 -->${cctopInvoice.movementDate?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
<#else>
	<#-- StartPosition=0234 -->${""?right_pad(8)?substring(0, 8)}<#lt><#t>
</#if>
<#-- StartPosition=0242 -->${cctopInvoice.invoiceDocumentno?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=0277 -->${cctopInvoice.dateInvoiced?string("yyyyMMdd")?right_pad(8)?substring(0, 8)}<#lt><#t>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "SU">
		<#assign isEmpty = false>
		<#-- StartPosition=0285 -->${cctop119V.gln?right_pad(14)?substring(0, 14)}<#lt><#t>
		<#-- StartPosition=0299 -->${cctop119V.name?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0334 -->${(cctop119V.name2!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0369 -->${cctop119V.address1?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0404 -->${cctop119V.city?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0439 -->${cctop119V.postal?right_pad(17)?substring(0, 17)}<#lt><#t>
		<#-- StartPosition=0456 -->${cctop119V.countryCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		<#-- StartPosition=0459 -->${""?right_pad(10)?substring(0, 10)}<#lt><#t><#-- cctop119V.vaTaxID will be empty (outdated) -->
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=0285 -->${""?right_pad(14)?substring(0, 14)}<#lt><#t>
	<#-- StartPosition=0299 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0334 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0369 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0404 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0439 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	<#-- StartPosition=0456 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	<#-- StartPosition=0459 -->${""?right_pad(10)?substring(0, 10)}<#lt><#t>
</#if>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "BY">
		<#assign isEmpty = false>
		<#-- StartPosition=0469 -->${cctop119V.gln?right_pad(14)?substring(0, 14)}<#lt><#t>
		<#-- StartPosition=0483 -->${cctop119V.name?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0518 -->${(cctop119V.name2!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0553 -->${cctop119V.address1?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0588 -->${cctop119V.city?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0623 -->${cctop119V.postal?right_pad(17)?substring(0, 17)}<#lt><#t>
		<#-- StartPosition=0640 -->${cctop119V.countryCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		<#-- StartPosition=0643 -->${""?right_pad(10)?substring(0, 10)}<#lt><#t><#-- cctop119V.vaTaxID will be empty (outdated) -->
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=0469 -->${""?right_pad(14)?substring(0, 14)}<#lt><#t>
	<#-- StartPosition=0483 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0518 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0553 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0588 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0623 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	<#-- StartPosition=0640 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	<#-- StartPosition=0643 -->${""?right_pad(10)?substring(0, 10)}<#lt><#t>
</#if>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "IV">
		<#assign isEmpty = false>
		<#-- StartPosition=0653 -->${cctop119V.gln?right_pad(14)?substring(0, 14)}<#lt><#t>
		<#-- StartPosition=0667 -->${cctop119V.name?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0702 -->${(cctop119V.name2!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0737 -->${cctop119V.address1?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0772 -->${cctop119V.city?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0807 -->${cctop119V.postal?right_pad(17)?substring(0, 17)}<#lt><#t>
		<#-- StartPosition=0824 -->${cctop119V.countryCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=0653 -->${""?right_pad(14)?substring(0, 14)}<#lt><#t>
	<#-- StartPosition=0667 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0702 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0737 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0772 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0807 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	<#-- StartPosition=0824 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
</#if>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "DP">
		<#assign isEmpty = false>
		<#-- StartPosition=0827 -->${cctop119V.gln?right_pad(14)?substring(0, 14)}<#lt><#t>
		<#-- StartPosition=0841 -->${cctop119V.name?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0876 -->${(cctop119V.name2!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0911 -->${cctop119V.address1?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0946 -->${cctop119V.city?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=0981 -->${cctop119V.postal?right_pad(17)?substring(0, 17)}<#lt><#t>
		<#-- StartPosition=0998 -->${cctop119V.countryCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=0827 -->${""?right_pad(14)?substring(0, 14)}<#lt><#t>
	<#-- StartPosition=0841 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0876 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0911 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0946 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=0981 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	<#-- StartPosition=0998 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
</#if>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "SN">
		<#assign isEmpty = false>
		<#-- StartPosition=1001 -->${cctop119V.gln?right_pad(14)?substring(0, 14)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=1001 -->${Missing.SN.address.gln?right_pad(14)?substring(0, 14)}<#lt><#t><#-- will throw an exception, which is what we want in this case -->
</#if>
<#-- StartPosition=1015 -->${""?left_pad(5)?substring(0, 5)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1020 -->${"A"?right_pad(1)?substring(0, 1)}<#lt><#t>
<#assign isEmpty = true>
<#list cctopInvoice.cctop120V! as cctop120V>
	<#assign isEmpty = false>
	<#-- StartPosition=1024 -->${cctop120V.isoCode?right_pad(3)?substring(0, 3)}<#lt><#t>
	<#-- StartPosition=1027 -->${cctop120V.netDays?string?left_pad(3)?substring(0, 3)}<#lt><#t>
	<#break><#-- there should only be one -->
</#list>
<#if isEmpty><#-- if there's no cctop120V, make sure we don't get negative offset (might never be the case due to BL, but just to be safe...) -->
	<#-- StartPosition=1024 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	<#-- StartPosition=1027 -->${""?string?left_pad(3)?substring(0, 3)}<#lt><#t>
</#if>
<#-- StartPosition=1027 -->${PaySkonto?left_pad(3)?substring(0, 3)}<#lt><#t>
<#-- StartPosition=1030 -->${Skonto?number?string("0.00")?left_pad(5)?substring(0, 5)}<#lt><#t>
<#-- StartPosition=1035 -->${""?right_pad(8)?substring(0, 8)}<#lt><#t>
<#-- StartPosition=1043 -->${""?right_pad(8)?substring(0, 8)}<#lt><#t>
<#-- StartPosition=1051 -->${""?right_pad(8)?substring(0, 8)}<#lt><#t>
<#-- StartPosition=1059 -->${cctopInvoice.creditMemoReasonText!""?right_pad(70)?substring(0, 70)}<#lt><#t>
<#-- StartPosition=1129 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=1164 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
<#-- StartPosition=1167 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
<#-- StartPosition=1184 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=1219 -->${""?right_pad(25)?substring(0, 25)}<#lt><#t>
<#-- StartPosition=1244 -->${""?right_pad(25)?substring(0, 25)}<#lt><#t>
<#-- StartPosition=1269 -->${""?right_pad(25)?substring(0, 25)}<#lt><#t>
<#-- StartPosition=1294 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1309 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1324 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1339 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1354 -->${""?right_pad(15)?substring(0, 15)}<#lt><#t>
<#-- StartPosition=1369 -->${""?right_pad(1)?substring(0, 1)}<#lt><#t>
<#-- StartPosition=1370 -->${""?right_pad(50)?substring(0, 50)}<#lt><#t>
<#-- StartPosition=1420 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=1455 -->${""?left_pad(5)?substring(0, 5)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=1460 -->${""?right_pad(500)?substring(0, 500)}<#lt><#t>
<#-- StartPosition=1960 -->${DiscountTextH100?right_pad(35)?substring(0, 35)}<#lt><#t>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "SN">
		<#assign isEmpty = false>
		<#-- StartPosition=1995 -->${cctop119V.name?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=2030 -->${(cctop119V.name2!"")?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=2065 -->${cctop119V.address1?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=2100 -->${cctop119V.city?right_pad(35)?substring(0, 35)}<#lt><#t>
		<#-- StartPosition=2135 -->${cctop119V.postal?right_pad(17)?substring(0, 17)}<#lt><#t>
		<#-- StartPosition=2152 -->${cctop119V.countryCode?right_pad(3)?substring(0, 3)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=1995 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=2030 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=2065 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=2100 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	<#-- StartPosition=2135 -->${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	<#-- StartPosition=2152 -->${""?right_pad(3)?substring(0, 3)}<#lt><#t>
</#if>
<#-- StartPosition=2155 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=2190 -->${""?left_pad(5)?substring(0, 5)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=2195 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=2210 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=2225 -->${""?right_pad(35)?substring(0, 35)}<#lt><#t>
<#-- StartPosition=2260 -->${""?left_pad(5)?substring(0, 5)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=2265 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#-- StartPosition=2280 -->${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "BY">
		<#assign isEmpty = false>
		<#-- StartPosition=2295 -->${cctop119V.vaTaxID?right_pad(20)?substring(0, 20)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=2295 -->${""?right_pad(20)?substring(0, 20)}<#lt><#t>
</#if>
<#assign isEmpty = true>
<#list cctopInvoice.cctop119V! as cctop119V>
	<#if cctop119V.eancomLocationtype == "SU">
		<#assign isEmpty = false>
		<#-- StartPosition=2315 -->${cctop119V.vaTaxID?right_pad(20)?substring(0, 20)}<#lt><#t>
		<#break><#-- there should only be one -->
	</#if>
</#list>
<#if isEmpty>
	<#-- StartPosition=2315 -->${""?right_pad(20)?substring(0, 20)}<#lt><#t>
</#if>

<#-- P100 -->
<#list cctopInvoice.cctopInvoice500V! as cctopInvoice500V>
	${"P100"?right_pad(4)?substring(0, 4)}<#lt><#t>
	${cctopInvoice.cctop000V.gln?right_pad(35)?substring(0, 35)}<#lt><#t>
	${MessageNoP100?right_pad(14)?substring(0, 14)}<#lt><#t>
	${cctopInvoice500V.line?right_pad(6)?substring(0, 6)}<#lt><#t>
	${cctopInvoice500V.upc?right_pad(35)?substring(0, 35)}<#lt><#t>
	${(cctopInvoice500V.vendorProductNo!"")?right_pad(35)?substring(0, 35)}<#lt><#t><#-- made it null-able (but not sure) -->
	${ArticleNoSupplier?right_pad(35)?substring(0, 35)}<#lt><#t>
	${(cctopInvoice500V.productDescription!"")?right_pad(70)?substring(0, 70)}<#lt><#t><#-- mandatory field -->
	${cctopInvoice500V.qtyInvoiced?left_pad(15)?substring(0, 15)}<#lt><#t>
	${cctopInvoice500V.eancomUom?string?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?left_pad(15)?substring(0, 15)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${CuPerTuQty?left_pad(15)?substring(0, 15)}<#lt><#t>
	${QtyUnit3?right_pad(3)?substring(0, 3)}<#lt><#t>
	${cctopInvoice500V.lineNetAmt?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${cctopInvoice500V.priceActual?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${(cctopInvoice500V.eancomPriceUom!"")?string?right_pad(3)?substring(0, 3)}<#lt><#t><#-- NetPriceUnit -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${(cctopInvoice500V.rate!""?number?string("0.00"))?left_pad(5)?substring(0, 5)}<#lt><#t><#-- made it null-able (but not sure) -->
	${cctopInvoice500V.taxAmount?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?right_pad(8)?substring(0, 8)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	${cctopInvoice.cctop111V.poReference?right_pad(35)?substring(0, 35)}<#lt><#t>
	${cctopInvoice500V.orderLine?string?left_pad(6, "0")?substring(0, 6)}<#lt><#t>
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?right_pad(50)?substring(0, 50)}<#lt><#t>
	${""?left_pad(5)?substring(0, 5)}<#lt><#t><#-- {0?number?string("0.00") -->
	${"DE"?right_pad(3)?substring(0, 3)}<#lt><#t><#--TODO: get language/sprachcode code (from partner maybe?)--><#-- cctopInvoice.countryCode -->
	${DiscountTextP100?right_pad(35)?substring(0, 35)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?right_pad(14)?substring(0, 14)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>
	${""?right_pad(17)?substring(0, 17)}<#lt><#t>
	${""?right_pad(3)?substring(0, 3)}<#lt><#t>
	${""?right_pad(35)?substring(0, 35)}<#lt><#t>

</#list>
<#-- T100 -->
<#list cctopInvoice.cctop901991V! as cctop901991V>
	${"T100"?right_pad(4)?substring(0, 4)}<#lt><#t>
	${cctopInvoice.cctop000V.gln?right_pad(35)?substring(0, 35)}<#lt><#t>
	${MessageNoT100?right_pad(14)?substring(0, 14)}<#lt><#t>
	${cctop901991V.totalAmt?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${cctop901991V.rate?number?string("0.00")?left_pad(5)?substring(0, 5)}<#lt><#t>
	${cctop901991V.taxAmt?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${cctop901991V.taxBaseAmt?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${TaxRate2?number?string("0.00")?left_pad(5)?substring(0, 5)}<#lt><#t>
	${TaxAmount2?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${TaxableAmount2?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${TaxRate3?number?string("0.00")?left_pad(5)?substring(0, 5)}<#lt><#t>
	${TaxAmount3?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${TaxableAmount3?number?string("0.00")?left_pad(15)?substring(0, 15)}<#lt><#t>
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->
	${""?left_pad(15)?substring(0, 15)}<#lt><#t><#-- {0?number?string("0.00") -->I
	${(cctop901991V.ESRNumber!"")?right_pad(70)?substring(0, 70)}<#lt><#t>

</#list>