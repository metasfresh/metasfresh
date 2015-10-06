000;${(cctopInvoice.cctop000V.senderGln)!""};${(cctopInvoice.cctop000V.gln)!""};${(cctopInvoice.currentDate?string("yyyyMMdd"))!""};${(cctopInvoice.currentDate?string("HHmm"))!""};${(cctopInvoice.cctop000V.interchangeReferenceNo)!""};;;${(cctopInvoice.cctop000V.isTest)!""};4.5;;
100;1;INVOIC;D;96A;HE;;${cctopInvoice.eancomDoctype!""};9;${cctopInvoice.invoiceDocumentno!""};${(cctopInvoice.dateInvoiced?string("yyyyMMdd"))!""};;;;;;
111;;;;;${(cctopInvoice.cctop111V.poReference)!""};${(cctopInvoice.cctop111V.dateOrdered?string("yyyyMMdd"))!""};;;${(cctopInvoice.cctop111V.movementDate?string("yyyyMMdd"))!""};${(cctopInvoice.cctop111V.shipmentDocumentno)!""};${(cctopInvoice.cctop111V.movementDate?string("yyyyMMdd"))!""};1;${(cctopInvoice.currentDate?string("yyyyMMdd"))!""};;;;;;;;;
<#list cctopInvoice.cctop119V! as cctop119V>
119;${cctop119V.eancomLocationtype!""};${cctop119V.gln!""};${cctop119V.name!""};${cctop119V.name2!""};;${cctop119V.address1!""};${cctop119V.address2!""};;${cctop119V.postal!""};${cctop119V.city!""};${cctop119V.countryCode!""};${cctop119V.value!""};${cctop119V.referenceNo!""};${cctop119V.vaTaxID!""};;${cctop119V.taxID!""};;${cctop119V.phone!""};${cctop119V.fax!""};;
</#list>
<#list cctopInvoice.cctop120V! as cctop120V>
120;${cctop120V.isoCode!""};${(cctop120V.singlevat)!""};${(cctop120V.netDays)!""};;${(cctop120V.netdate?string("yyyyMMdd"))!""};;;;;${cctop120V.taxfree!""};;;
</#list>
<#list cctopInvoice.cctop140V! as cctop140V>
140;${(cctop140V.rate)!""};${(cctop140V.discount)!""};;;;;${(cctop140V.discountDays)!""};${(cctop140V.discountDate?string("yyyyMMdd"))!""};;
</#list>
<#list cctopInvoice.cctopInvoice500V! as cctopInvoice500V>
500;${(cctopInvoice500V.line)!""};;${cctopInvoice500V.upc!""};${cctopInvoice500V.value!""};${cctopInvoice500V.vendorProductNo!""};;${cctopInvoice500V.name!""};${cctopInvoice500V.name2!""};;;${(cctopInvoice500V.qtyInvoiced?string("0.000"))!""};;${cctopInvoice500V.isoCode!""};${(cctopInvoice500V.rate?string("0.00"))!""};${(cctopInvoice500V.priceActual?string("0.0000"))!""};;${(cctopInvoice500V.priceActual?string("0.0000"))!""};;${(cctopInvoice500V.priceList?string("0.0000"))!""};;${cctopInvoice500V.eancomUom!""};${(cctopInvoice500V.lineNetAmt?string("0.000"))!""};${(cctopInvoice500V.lineGrossAmt?string("0.000"))!""};;;;${cctopInvoice500V.leergut!""};;;${cctopInvoice500V.taxfree!""};;;;;
</#list>
900;${(cctopInvoice.grandTotal?string("0.00"))!""};${(cctopInvoice.totalvat?string("0.00"))!""};${(cctopInvoice.totalLines?string("0.00"))!""};${(cctopInvoice.totaltaxbaseamt?string("0.00"))!""};${(cctopInvoice.grandTotal?string("0.00"))!""};;;;
<#list cctopInvoice.cctop901991V! as cctop901991V>
901;${(cctop901991V.taxAmtSumTaxBaseAmt?string("0.00"))!""};${cctop901991V.taxAmt?string("0.00")!""};${(cctop901991V.taxBaseAmt?string("0.00"))!""};${(cctop901991V.taxBaseAmt?string("0.00"))!""};${(cctop901991V.taxAmtSumTaxBaseAmt?string("0.00"))!""};;${(cctop901991V.rate?string("0.00"))!""};;
</#list>
990;393;1;${(cctopInvoice.currentDate?string("yyyyMMdd"))!""};;${(cctopInvoice.cctop000V.senderGln)!""};;${(cctopInvoice.cctop000V.gln)!""};${(cctopInvoice.cctop000V.senderGln)!""};${(cctopInvoice.cctop000V.gln)!""};${cctopInvoice.isoCode!""};;;${(cctopInvoice.grandTotal?string("0.00"))!""};${(cctopInvoice.totalvat?string("0.00"))!""};${(cctopInvoice.totalLines?string("0.00"))!""};${(cctopInvoice.totaltaxbaseamt?string("0.00"))!""};;;;;;;
<#list cctopInvoice.cctop901991V! as cctop901991V>
991;${(cctop901991V.taxAmtSumTaxBaseAmt?string("0.00"))!""};${cctop901991V.taxAmt?string("0.00")!""};${(cctop901991V.taxBaseAmt?string("0.00"))!""};${(cctop901991V.taxBaseAmt?string("0.00"))!""};;;${(cctop901991V.rate?string("0.00"))!""};;
</#list>