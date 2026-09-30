@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00144_Free_Text_Above_Order_Lines
@ghActions:run_on_executor2
Feature: Free text lines print at their position on a sales order confirmation
  A user can put a free-text line between the article lines of a sales order -- a packing instruction, a
  blank spacer, a heading introducing the articles beneath it. On the order confirmation, each text line
  prints at the position it was placed, carrying only its own text: no article number, name, quantity, unit
  or price. Its stored scope (whether it belongs with the whole document or with the article run that
  follows it) is derived from where it was inserted, never set directly.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # render the real jasper report; the mock report service would archive a placeholder PDF instead
    And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    # the Bestellkontrolle (Barcode) template embeds a live barcode image, fetched by JasperReports at render
    # time over real HTTP; the scrambled test DB's own sysconfig value is a stale port from wherever that
    # dump's data originated, so it is repointed here at this cucumber JVM's own (ephemeral) embedded server
    And set sys config 'de.metas.adempiere.report.barcode.BarcodeServlet' to this instance's own URL at '/adempiereJasper/BarcodeServlet'
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    # another feature in this suite flips this flag; pin it so this scenario cannot depend on suite order.
    # It must stay 'false': the steps read the archive back out of the database.
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    # feeds the order-checkup ("Bestellkontrolle") Plant-level report TC8/TC12 print below: the production
    # OrderCheckupBL builds that report's "Plant" row for every non-packaging-material order line once the
    # order's warehouse has a PP_Plant_ID -- no PP_Product_Planning fixture needed for that row. Created
    # before the warehouse itself: PP_Plant_ID must resolve an already-registered S_Resource identifier.
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID |
      | wh             | plant       |
    # Value differs from Name on purpose: the article row shows the Name, the product-number row below it
    # shows the Value, which gives each article two distinct text anchors. Keep the Names short - a
    # wrapping Name would change the line counts the assertions below rely on.
    And metasfresh contains M_Products:
      | Identifier | Value    | Name      |
      | productA   | ALPHA-NR | AlphaItem |
      | productB   | BETA-NR  | BetaItem  |
      | productC   | GAMMA-NR | GammaItem |
      | productD   | DELTA-NR | DeltaItem |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | ppA        | plv                    | productA     | 10.0     | PCE      |
      | ppB        | plv                    | productB     | 10.0     | PCE      |
      | ppC        | plv                    | productC     | 10.0     | PCE      |
      | ppD        | plv                    | productD     | 10.0     | PCE      |
    # dev-note: pin the payment term, so the order does not depend on the branch's default
    And metasfresh contains C_PaymentTerm
      | Identifier          |
      | customerPaymentTerm |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | C_PaymentTerm_ID    |
      | customer   | N        | Y          | ps                 | customerPaymentTerm |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name      | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | de_ch_tax  | Normal                        | de_ch_tax | 2021-04-02 | 2.5  | DE                       | CH                        |
      | ch_ch_tax  | Normal                        | ch_ch_tax | 2021-04-02 | 2.5  | CH                       | CH                        |

  @Id:S27486_TC1
  Scenario: A packing instruction and a group heading print at their positions, with their blank lines intact
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # line40 carries its own Description -- an article line printing alongside text lines is a realistic
    # order, and it is exactly the interaction the guard-removal sweep below needs: the unguarded text band
    # would print this same value a second time, which only exists to be caught if some article line has one
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Description             |
      | line10     | order      | productA     | 1          |                         |
      | line20     | order      | productB     | 1          |                         |
      | line30     | order      | productC     | 1          |                         |
      | line40     | order      | productD     | 1          | Bruchsichere Verpackung |

    # the packing instruction: entered above the very first article line, so nothing precedes it at all
    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    # the group heading: entered above the second group's first article, with an article line standing
    # above it -- its own value begins with a blank line, reproducing the separator row above the heading
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """

    # the scope is derived from the insert position, never chosen by hand
    Then the text line identified by "topBlock" has TextLineScope "Document"
    And the text line identified by "groupHeading" has TextLineScope "Following"

    # the persisted value is read back, blank lines and all -- not assumed to have round-tripped
    And the text line identified by "topBlock" has text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And the text line identified by "groupHeading" has text:
      """

      Sortimentsware
      """

    # inserting text lines never renumbered the article lines around them
    And the order line identified by "line10" still has Line 10
    And the order line identified by "line20" still has Line 20
    And the order line identified by "line30" still has Line 30
    And the order line identified by "line40" still has Line 40

    # independent of the doc string that wrote it: a Gherkin-layer collapse of the embedded blank line would
    # still round-trip identically through "has text:" above, since the same string supplies both sides. A
    # literal line count and blank-line position cannot collapse the same way.
    And the text line identified by "topBlock" has 4 lines
    And the text line identified by "topBlock" has a blank line at position 3
    And the text line identified by "groupHeading" has 2 lines
    And the text line identified by "groupHeading" has a blank line at position 1

    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |

    Then an AD_Archive exists for the record identified by "order"

    # the packing instruction's own two non-blank lines sit on consecutive visual lines
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Für die Truhe:" and text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"
    # the block prints directly above the first article -- nothing stands between them
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "AlphaItem"
    # ordinary article-to-article adjacency is unaffected by the text lines elsewhere in the document
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
    # nothing else prints between the second group's preceding article and its own heading -- this is
    # exactly what would break if a text row fell through the article band instead of its own
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "BETA-NR" and text "Sortimentsware"
    # the heading prints directly above the first article of the group it introduces
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "GAMMA-NR" and text "DeltaItem"
    # the last article's own Description prints directly below its product-number row
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "DELTA-NR" and text "Bruchsichere Verpackung"
    # ...and nothing stands between that Description and the item table's own total -- the guard that keeps
    # the text band off an ARTICLE row is what this pins: were it removed, the band would render unconditionally,
    # and since this article line HAS a Description (unlike the others), that value would print a second time
    # right here, between its own Description row and the total, where a line-count assertion can see it
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Bruchsichere Verpackung" and text "Total 40,00"

    # each text line's own row carries its text and nothing else -- no article number, name, quantity, unit
    # or price glued onto the same visual line
    And the visual line containing text "Für die Truhe:" in the PDF archived for the record identified by "order" is exactly "Für die Truhe:"
    And the visual line containing text "Bitte saubere Schalen mit ordentlichem beklebten Deckel" in the PDF archived for the record identified by "order" is exactly "Bitte saubere Schalen mit ordentlichem beklebten Deckel"
    And the visual line containing text "Kartons bitte mit \"Truhe\" beschriften" in the PDF archived for the record identified by "order" is exactly "Kartons bitte mit \"Truhe\" beschriften"
    And the visual line containing text "Sortimentsware" in the PDF archived for the record identified by "order" is exactly "Sortimentsware"

    # the article lines print unchanged: each one's Line number precedes its own Name on the printed row,
    # not merely persisted unchanged in the database (checked above)
    And the PDF archived for the record identified by "order" contains text "10 AlphaItem"
    And the PDF archived for the record identified by "order" contains text "20 BetaItem"
    And the PDF archived for the record identified by "order" contains text "30 GammaItem"
    And the PDF archived for the record identified by "order" contains text "40 DeltaItem"

    # the packing instruction's own embedded blank line occupies exactly one full line: the span across it
    # (from the line before the blank to the line after it) covers exactly twice the height of a single
    # ordinary line within the very same block (from its first line to its second) -- not a collapsed row
    And in the PDF archived for the record identified by "order", the vertical distance from text "Bitte saubere Schalen mit ordentlichem beklebten Deckel" to text "Kartons bitte mit \"Truhe\" beschriften" is 2 times the distance from text "Für die Truhe:" to text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"

    # the heading's own leading blank line likewise consumed real space: the span from the last article
    # before it to its own text is bigger than an ordinary single-line article-to-article gap, whatever the
    # exact figure is (the two spans have different band shapes, so no exact multiple can be pinned here)
    And in the PDF archived for the record identified by "order", the vertical distance from text "BETA-NR" to text "Sortimentsware" is greater than the distance from text "ALPHA-NR" to text "BetaItem"
    # tighter than "greater than": pins the gap to EXACTLY one article pitch plus one text-line height, so it
    # catches an OVERSHOOT too -- an extra band rendering blank-but-present ahead of the heading's own text,
    # which no line-count or content assertion above can see, since a blank band emits no glyphs at all
    And in the PDF archived for the record identified by "order", the vertical distance from text "BETA-NR" to text "Sortimentsware" equals the distance from text "ALPHA-NR" to text "BetaItem" plus the distance from text "Für die Truhe:" to text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"

    # nothing is printed on top of anything else, anywhere in the document
    And the PDF archived for the record identified by "order" has no overlapping text

    # -- the Bestellkontrolle: the fresh-produce order-checkup report, generated from the same order. The
    # order's warehouse carries a PP_Plant_ID (Background), so OrderCheckupBL builds a real "Plant" row
    # carrying all four article lines -- both text lines are carried onto it too: "topBlock" is
    # Document-scoped (rule 5 in the report SQL function: a whole-document-scoped line is always carried),
    # and "groupHeading"'s own run (line30 onward) sits inside this record's own line set (rule 4)
    And the order-checkup reports are generated for the order identified by "order"
    And The jasper process is run
      | Value                                    | Record_ID     |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup |
    Then an AD_Archive exists for the record identified by "order_checkup"

    # each text line prints directly above the article row it precedes here too, same adjacency as on the
    # order confirmation above
    And in the PDF archived for the record identified by "order_checkup", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "AlphaItem"
    And in the PDF archived for the record identified by "order_checkup", exactly 0 lines appear between text "BETA-NR" and text "Sortimentsware"
    And in the PDF archived for the record identified by "order_checkup", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"

    # the text rows carry only their own text, never the barcode meant for an article row: the report SQL
    # function's article-describing columns (barcode included) are NULL on a text row, and Java string
    # concatenation would otherwise turn that into a bogus barcode encoding the literal text "null" -- this
    # count proves the guard keeps the barcode-bearing band OFF both text rows: one image per real article
    # row (line10..line40) plus the document's own logo, none contributed by "topBlock" or "groupHeading".
    # Removing the article-band guard alone does NOT isolate this: it dies on $F{capacity} being NULL (a
    # DecimalFormat.format(null) IllegalArgumentException) before the <image> element is even reached, so
    # that mutation proves the band guard is load-bearing but says nothing about the barcode on its own. The
    # isolating check: article-band guard removed AND the capacity expression null-safed so the render
    # survives -> 7 image XObjects, two of them drawn on the two text rows (six barcodes, one for each of
    # topBlock/line10/line20/groupHeading/line30/line40, plus the logo) -- exactly the failure this count
    # exists to catch. This report field carries just the logo's own page-count caveat: report.jrxml invokes
    # the logo subreport a second time on every page after the first, so a fixture spilling onto page 2 would
    # add a sixth image with no barcode involved -- moot for this one-page fixture, but the reason the count
    # is not "4 articles + 1 logo, always"
    And the PDF archived for the record identified by "order_checkup" contains exactly 5 images
    And the PDF archived for the record identified by "order_checkup" has no overlapping text

    # the article row itself carries only its own content: pins the article band at exactly its own declared
    # height (47pt, report_details.jrxml's detail band) -- a text row's own band sitting alongside it renders
    # blank-but-PRESENT when it is not itself guarded off the article row (isBlankWhenNull suppresses the
    # glyphs, not the band's reserved height), so an inflated row here is exactly what a missing guard on the
    # text band looks like: no wrong text anywhere, just silent, growing whitespace
    And in the PDF archived for the record identified by "order_checkup", the vertical distance from text "AlphaItem" to text "BetaItem" is 47.0 points

  @Id:S27486_TC8
  Scenario: A trailing text line prints at the end of the order confirmation but reaches no derived document
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |

    # the requirements give no insert-below; a trailing line is created by inserting above the LAST article
    # line, then invoking "move down" once. The move-down step does NOT run the WebUI's own quick action
    # (DocTextLinesRows#moveRow, de.metas.ui.web.base, not on this module's classpath) -- it fabricates the
    # position the quick action would produce, via the same shared arithmetic and the same persistence call
    # production uses, so the write is genuine even though the decision that led to it is not. The real move
    # decision is unit-tested in DocTextLinesQuickActionsTest; see the step's own Javadoc for the drift risk
    # this leaves and how the Line-value pin below narrows it.
    When a text line "trailing" is inserted above the order line identified by "line20" with text:
      """
      Vielen Dank für Ihren Einkauf
      """
    And the text line identified by "trailing" is moved down

    # the scope was derived once, at insert time, from the article lines that preceded the row back THEN --
    # moving it past the last article line afterwards does not re-derive it, so it stays "Following" even
    # though the row is now, in print position, past every article line
    Then the text line identified by "trailing" has TextLineScope "Following"
    # pins the fabricated position itself -- see C_Doc_TextLine_StepDef#validateTextLineLine
    And the text line identified by "trailing" has Line "21"

    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    Then an AD_Archive exists for the record identified by "order"

    # it prints directly after the last article line -- the zero-line-gap assertion right below is what
    # proves that, not this one
    And the PDF archived for the record identified by "order" contains text "Vielen Dank für Ihren Einkauf"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "BETA-NR" and text "Vielen Dank für Ihren Einkauf"
    And the PDF archived for the record identified by "order" has no overlapping text

    # -- the delivery note: a separate print pipeline, generated from the same completed order --
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss10                  |
      | ss20                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss10                  | shipment   | CO        |
    And The jasper process is run
      | Value                 | Record_ID |
      | Lieferschein (Jasper) | shipment  |
    Then an AD_Archive exists for the record identified by "shipment"
    # Load-bearing since the delivery note gained text-line support: this trailing line is scoped Following
    # and has NO article line beneath it, so its run is empty. TextLineShipmentCopier skips a Following line
    # whose run is not present on the shipment, so it is not carried -- even though this shipment contains
    # every article line of the order.
    And the PDF archived for the record identified by "shipment" does not contain text "Vielen Dank für Ihren Einkauf"
    And the PDF archived for the record identified by "shipment" has no overlapping text

    # -- the Bestellkontrolle: the fresh-produce order-checkup report, generated from the same order. Value
    # picks the barcode-aware print process (C_Order_MFGWarehouse_Report_With_Barcode -> report_details.jrxml
    # under ordercheckup_with_barcode/), the one whose report SQL function (Docs_Sales_OrderCheckup_Details)
    # this feature actually touches -- the plain "Bestellkontrolle" process queries an unrelated view and
    # never sees a text line under any circumstance, feature or no feature. The order's warehouse carries a
    # PP_Plant_ID (Background), so OrderCheckupBL builds a real "Plant" row from both article lines --
    # Record_ID below targets that row directly (its own C_Order_MFGWarehouse_Report_ID), the same way a real
    # user prints it from that row's own window, not the order's
    And the order-checkup reports are generated for the order identified by "order"
    And The jasper process is run
      | Value                                    | Record_ID     |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup |
    Then an AD_Archive exists for the record identified by "order_checkup"
    # the trailing text line belongs to the order confirmation only -- not because the Bestellkontrolle's own
    # report SQL function ignores C_Doc_TextLine (it does not: it emits text rows, and this template prints
    # them too, see TC1's own Bestellkontrolle assertions above), but because THIS particular line's own run
    # is empty: it sits after every article line, so no article line of its run lies at or beyond its own
    # position, and being scope "Following" (not "Document") it fails the carry rule and reaches no report
    And the PDF archived for the record identified by "order_checkup" does not contain text "Vielen Dank für Ihren Einkauf"
    And the PDF archived for the record identified by "order_checkup" has no overlapping text

  @Id:S27486_TC12
  Scenario: An order with no text lines still prints its Description and DescriptionBottom exactly as before
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # line10 carries its own Description, exactly like TC1's line40 -- the anchor the line-count assertions
    # below need to prove nothing was inserted between an article and its own Description
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Description         |
      | line10     | order      | productA     | 1          | Kühlkette einhalten |
      | line20     | order      | productB     | 1          |                     |
    # DescriptionBottom is set as a separate update, not at creation: setting it together with
    # C_DocTypeTarget_ID/C_BPartner_ID (both set when the order itself is created) has the model
    # interceptor re-derive it from the doc type's own DocumentNote before the row is even inserted
    And update order
      | C_Order_ID | DescriptionBottom             |
      | order      | Vielen Dank für Ihren Einkauf |

    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    Then an AD_Archive exists for the record identified by "order"

    # no band was inserted anywhere the feature could have inserted one -- covering the three actual
    # insertion-point windows the requirement names, each a same-subreport adjacency exactly like TC1's own:
    # above the first article row, between the two article lines (the first article's own Description prints
    # directly below its own product-number row, not the Name row -- same anchor TC1 uses for line40's
    # "Bruchsichere Verpackung"), and after the last article line -- the trailing position TC8's own new
    # scenario shows is reachable.
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Pos. Artikel" and text "10 AlphaItem"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "Kühlkette einhalten"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Kühlkette einhalten" and text "BetaItem"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "BETA-NR" and text "Total 20,00"
    # the other half of the requirement: DescriptionBottom itself prints, unperturbed, in its own footer band.
    # PaymentRule and DescriptionBottom are adjacent fields of the SAME footer subreport
    # (report_details_footer.jrxml, Incoterms sits between them in the JRXML but is empty and
    # printWhen-suppressed here) -- this pair does NOT cover an insertion-point window (no text line could
    # ever print between two footer fields; the last ARTICLE row sits in an entirely different subreport, with
    # the order's totals/payment-terms block always between it and the footer regardless of text lines), it
    # only proves DescriptionBottom itself still prints correctly
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Zahlungsweise Zahlung via Rechnung" and text "Vielen Dank für Ihren Einkauf"
    And the PDF archived for the record identified by "order" has no overlapping text

    # -- the delivery note: its own report SQL function does not reference C_Doc_TextLine at all, and its
    # template was not touched by this feature -- an order with zero text lines gives it nothing to catch
    # either way. Printed anyway, per the frozen test case, to prove the feature does not break its own
    # pipeline for the ordinary, text-line-free order that is still the common case.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss10                  |
      | ss20                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss10                  | shipment   | CO        |
    And The jasper process is run
      | Value                 | Record_ID |
      | Lieferschein (Jasper) | shipment  |
    Then an AD_Archive exists for the record identified by "shipment"
    And the PDF archived for the record identified by "shipment" has no overlapping text

    # -- the Bestellkontrolle: Value picks the barcode-aware print process, same as TC1/TC8 above. Its report
    # SQL function (Docs_Sales_OrderCheckup_Details) DOES reference C_Doc_TextLine (an earlier part of this
    # feature) and this template now prints text rows too (TC1 above) -- but this order has none, so every
    # row here is an ordinary article row: the purest form of the "guard band never fires blank in front of
    # an article row" case, since nothing here could ever make the text band's own guard evaluate true.
    And the order-checkup reports are generated for the order identified by "order"
    And The jasper process is run
      | Value                                    | Record_ID     |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup |
    Then an AD_Archive exists for the record identified by "order_checkup"
    # "has no overlapping text" alone cannot see this: a band that renders blank-but-present shifts rows down
    # without colliding with anything. This pins the article band at exactly its own declared height (47pt) --
    # see the step's own Javadoc for why "equals a literal" is the only shape that survives BOTH legs of a
    # pitch inflating together, which is exactly what an unguarded text band on every row would do here
    And in the PDF archived for the record identified by "order_checkup", the vertical distance from text "AlphaItem" to text "BetaItem" is 47.0 points
    And the PDF archived for the record identified by "order_checkup" has no overlapping text

  @Id:S27486_TC13
  Scenario: A text line's markup prints literally and its special characters render correctly
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |

    # markup, German umlauts, ß, €, and a pair of ASCII double quotes in one text line
    When a text line "richText" is inserted above the order line identified by "line10" with text:
      """
      <b>fett</b> Größe M, äöüÄÖÜß, 5€, "Sonderangebot"
      """
    And the text line identified by "richText" has text:
      """
      <b>fett</b> Größe M, äöüÄÖÜß, 5€, "Sonderangebot"
      """

    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |
    Then an AD_Archive exists for the record identified by "order"

    # the tags print literally: markup="none" on this field suppresses JasperReports' own HTML-tag
    # interpretation, so the angle brackets are asserted directly, not merely the letters "fett" -- that is
    # what would catch a template that started INTERPRETING the markup instead of printing it
    And the PDF archived for the record identified by "order" contains text "<b>fett</b>"
    # German umlauts and ß render rather than falling back to a missing-glyph replacement character. Caveat:
    # this reads the PDF's text stream via the font's own ToUnicode map, not the rendered glyph outlines -- a
    # font that mapped a glyph to the right codepoint but drew it as .notdef (a visible fallback box) would
    # still pass this assertion, so it proves the text stream carries the right characters, not that they were
    # drawn correctly
    And the PDF archived for the record identified by "order" contains text "äöüÄÖÜß"
    # the Euro sign renders
    And the PDF archived for the record identified by "order" contains text "5€"
    # a plain ASCII double quote pair renders (no smart-quote substitution, no dropped character)
    And the PDF archived for the record identified by "order" contains text "\"Sonderangebot\""
    And the PDF archived for the record identified by "order" has no overlapping text

    # -- the Bestellkontrolle: its own text band sets markup="none" too (matching the sales-order templates
    # above), but nothing above pins THIS template's own property -- a regression to markup="html" here would
    # collapse "<b>fett</b>" into a bolded, tag-stripped "fett" and every one of TC1's own checkup assertions
    # (adjacency, image count, band height) would still pass, since none of them look at markup interpretation
    And the order-checkup reports are generated for the order identified by "order"
    And The jasper process is run
      | Value                                    | Record_ID     |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup |
    Then an AD_Archive exists for the record identified by "order_checkup"
    And the PDF archived for the record identified by "order_checkup" contains text "<b>fett</b>"
    And the PDF archived for the record identified by "order_checkup" has no overlapping text

  @Id:S27486_TC5
  Scenario: The order checkup's Warehouse report carries only the routed article, prints its whole-document text line at the head, and drops a following-scoped one whose run never reaches it
    # the routing's own responsible user -- fixture realism for the "Warehouse" grouping key
    # (Util.mkKey(order, "WH", responsibleUserId)), matching the requirements' description of that grouping.
    # Not independently asserted: no step here queries AD_User_Responsible_ID, and discriminating on it would
    # need a second user-in-charge producing a second Warehouse record, which the firstIdOnly() comment on
    # the step def explicitly scopes out of this single-order contrast. Reuses the Background's own seeded
    # login.
    Given load AD_User:
      | Login      | AD_User_ID.Identifier |
      | metasfresh | checkupUser           |
    # the manufacturing routing for productD: an AD_Workflow whose first node carries a resource -- both
    # required for OrderCheckupBL to build a "Warehouse" row at all (silent skip otherwise, see
    # PP_Product_Planning below). Reuses the Background's own "plant" S_Resource -- the same physical plant
    # already serving the order's warehouse, no second one needed.
    And create AD_Workflow:
      | AD_Workflow_ID.Identifier | WorkflowType | OPT.AD_User_InCharge_ID.Identifier |
      | checkupRouting            | M            | checkupUser                        |
    And create AD_WF_Node:
      | AD_WF_Node_ID.Identifier | AD_Workflow_ID.Identifier | OPT.S_Resource_ID.Identifier | Name               | Value              | Duration |
      | checkupRoutingNode       | checkupRouting            | plant                        | checkupRoutingNode | checkupRoutingNode | 1        |
    And update AD_Workflow:
      | AD_Workflow_ID.Identifier | OPT.AD_WF_Node_ID.Identifier |
      | checkupRouting            | checkupRoutingNode           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # only line40/productD carries a manufacturing PP_Product_Planning (below) -- line10/20/30 are silently
    # skipped for the Warehouse report by OrderCheckupBL.generateReportsIfEligible (:120-125), so the
    # Warehouse record's own line set is exactly {line40}
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | S_Resource_ID | AD_Workflow_ID | M_Warehouse_ID |
      | productD     | plant         | checkupRouting | wh             |

    # topBlock is inserted above the very first line (Document scope, nothing precedes it) and then moved
    # past every article -- one "moved down" per article, before any other text line exists so each hop's
    # nearest neighbour is always an article, never a text line (the only shape this step implements; see
    # its own Javadoc). Moving never re-derives scope (also documented there), so it stays Document-scoped
    # while its own persisted Line ends up AFTER line40 -- greater than every article's Line, including the
    # Warehouse record's only one. This is the case the function's head-offset (Docs_Sales_OrderCheckup_Details
    # :199-211, "tl_line - 1000000") exists for: a whole-document line whose OWN ordinary position would sort
    # it to the FOOT of the Warehouse record, which must still print at the head there. Its run (computed from
    # this final position) is bounded below by nothing -- it is now the last text line -- so line40 is not in
    # it either way; the override fires for the same absence-of-run reason as before, but now the offset is
    # what makes the difference between head and foot, not merely between printing and not.
    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Kühlkette
      """
    And the text line identified by "topBlock" is moved down
    And the text line identified by "topBlock" is moved down
    And the text line identified by "topBlock" is moved down
    And the text line identified by "topBlock" is moved down
    # unchanged by the move above: scope is derived once, at insert time, and never re-derived (see TC8's own
    # established coverage of this invariant) -- pinned here because this scenario is the one whose head-offset
    # assertions depend on it still being 'D' after crossing every article
    Then the text line identified by "topBlock" has TextLineScope "Document"
    # pins the position that makes the head-offset load-bearing (see the comment above): topBlock's Line must
    # sit AFTER line40's (40) for the "tl_line - 1000000" branch to be exercised rather than merely present. A
    # later fixture edit (e.g. changing the article count) could silently move this back below every article --
    # every assertion below would still pass, carrying the line by the ordinary path instead. Same shape as
    # TC13's own pin of "trailing"'s Line further up this file.
    And the text line identified by "topBlock" has Line "41"

    # midNote (Following-scoped, above line20) has a run bounded by routingNote -- article lines 20 and 30
    # only. Neither is on the Warehouse record (only line40 is), so its run is genuinely absent there and,
    # being Following- not Document-scoped, nothing forces it through: it must not print on the Warehouse
    # report at all, while it prints normally on the Plant one (whose line set has both 20 and 30). Inserted
    # only now, after topBlock has already moved past this position, so it cannot become one of topBlock's
    # move-down neighbours.
    And a text line "midNote" is inserted above the order line identified by "line20" with text:
      """
      Sonderposten
      """
    # routingNote (Following-scoped, directly above the routed line itself) has a run of article 40 only --
    # exactly the Warehouse record's own line -- so it carries there by the ordinary rule, at its own
    # position immediately above DeltaItem: the ordinary-carry counterpart to topBlock's override. Its own
    # upper run bound is now topBlock's post-move position (the next text line after it), not unbounded, but
    # line40 -- the only line that matters -- is still within it either way
    And a text line "routingNote" is inserted above the order line identified by "line40" with text:
      """
      Expresslieferung
      """

    When the order identified by order is completed
    And the order-checkup reports are generated for the order identified by "order"

    # the report must exist before anything about its content is asserted. The guarantee itself comes from
    # Java: "the order-checkup reports are generated..." only ever registers a Warehouse-row identifier when
    # OrderCheckupBL actually built one, so a fixture that failed to route any line resolves no identifier at
    # all and the very next step -- printing it -- dies in identifier resolution, one step before any content
    # assertion could even run. The "AD_Archive exists" step below still earns its place: it is what would
    # catch the different failure of "a Warehouse row was built and printed, but nothing got archived".
    And The jasper process is run
      | Value                                    | Record_ID     |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup |
    Then an AD_Archive exists for the record identified by "order_checkup"

    And The jasper process is run
      | Value                                    | Record_ID        |
      | C_Order_MFGWarehouse_Report_With_Barcode | order_checkup_WH |
    Then an AD_Archive exists for the record identified by "order_checkup_WH"

    # the Plant report carries every non-packaging-material article line, and every text line, unconditionally
    And the PDF archived for the record identified by "order_checkup" contains text "AlphaItem"
    And the PDF archived for the record identified by "order_checkup" contains text "BetaItem"
    And the PDF archived for the record identified by "order_checkup" contains text "GammaItem"
    And the PDF archived for the record identified by "order_checkup" contains text "DeltaItem"
    And the PDF archived for the record identified by "order_checkup" contains text "Kühlkette"
    And the PDF archived for the record identified by "order_checkup" contains text "Sonderposten"
    And the PDF archived for the record identified by "order_checkup" contains text "Expresslieferung"
    And the PDF archived for the record identified by "order_checkup" has no overlapping text

    # the Warehouse report carries only the routed article (line40/productD) -- the three unrouted products
    # never reach it
    And the PDF archived for the record identified by "order_checkup_WH" contains text "DeltaItem"
    And the PDF archived for the record identified by "order_checkup_WH" does not contain text "AlphaItem"
    # midNote's run never reaches the Warehouse record -- it must not print there at all
    And the PDF archived for the record identified by "order_checkup_WH" does not contain text "Sonderposten"
    # topBlock (head override) and routingNote (ordinary carry) are the Warehouse record's ONLY other two
    # rows besides DeltaItem itself -- "0 lines between" pins their adjacency to each other and to DeltaItem,
    # which together fix the print order as topBlock, then routingNote, then DeltaItem: topBlock first (the
    # head placement the override exists for), with nothing -- in particular not midNote -- between any pair.
    # This pair is now load-bearing on the offset itself, not merely on "prints somewhere": topBlock's own
    # post-move Line sorts AFTER line40, so without the "- 1000000" it would print at the FOOT, after
    # DeltaItem -- these two adjacency checks would then find DeltaItem sitting between topBlock and
    # routingNote and fail, rather than merely holding for a different reason.
    And in the PDF archived for the record identified by "order_checkup_WH", exactly 0 lines appear between text "Kühlkette" and text "Expresslieferung"
    And in the PDF archived for the record identified by "order_checkup_WH", exactly 0 lines appear between text "Expresslieferung" and text "DeltaItem"
    And the PDF archived for the record identified by "order_checkup_WH" has no overlapping text

  @Id:S27486_TC2
  Scenario: A partial delivery carries the whole-document block at the head and only the heading of the group it ships
    # a fifth article, in a third group of its own -- the Background's four products span two groups only.
    # Added scenario-locally rather than to the Background, so every scenario already in this file keeps
    # exactly the fixture it was written against.
    Given metasfresh contains M_Products:
      | Identifier | Value      | Name        |
      | productE   | EPSILON-NR | EpsilonItem |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | ppE        | plv                    | productE     | 10.0     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # three groups: {line10,line20}, {line30,line40}, {line50}
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |
      | line50     | order      | productE     | 1          |

    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """
    And a text line "freshHeading" is inserted above the order line identified by "line50" with text:
      """
      Frischware
      """

    # every scope below is derived from the insert position, never chosen by hand -- and each one is what
    # decides that line's fate on the partial shipment asserted further down
    Then the text line identified by "topBlock" has TextLineScope "Document"
    And the text line identified by "groupHeading" has TextLineScope "Following"
    And the text line identified by "freshHeading" has TextLineScope "Following"

    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
      | ss30       | line30         | N             |
      | ss40       | line40         | N             |
      | ss50       | line50         | N             |
    # the partial delivery: ONLY the second group ships
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss30                  |
      | ss40                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss30                  | shipment   | CO        |
    And The jasper process is run
      | Value                 | Record_ID |
      | Lieferschein (Jasper) | shipment  |
    Then an AD_Archive exists for the record identified by "shipment"

    # the shipment really is partial -- without this, every "does not print" assertion below could be
    # satisfied by a document that simply carries nothing
    And the PDF archived for the record identified by "shipment" contains text "GammaItem"
    And the PDF archived for the record identified by "shipment" contains text "DeltaItem"
    And the PDF archived for the record identified by "shipment" does not contain text "AlphaItem"
    And the PDF archived for the record identified by "shipment" does not contain text "BetaItem"
    And the PDF archived for the record identified by "shipment" does not contain text "EpsilonItem"

    # the whole-document block prints although NONE of its own run (line10, line20) is on this shipment --
    # and it prints at the head: the next text on the page after its last line is the group heading, which
    # in turn sits directly above the first shipped article. Nothing of the block is separated from them.
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "Für die Truhe:" and text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "Sortimentsware"
    # the heading prints immediately above the first shipped article of its own group
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"
    # ...and the group it introduces is intact behind it: article-to-article adjacency is untouched
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "GAMMA-NR" and text "DeltaItem"

    # the third group's heading has no article of its run on this shipment and is not whole-document scoped:
    # it must not print at all. This is the assertion the whole carry rule exists for.
    And the PDF archived for the record identified by "shipment" does not contain text "Frischware"

    # each carried text row carries its own text and nothing else -- no article number, name, quantity, unit
    # or price glued onto the same visual line
    And the visual line containing text "Für die Truhe:" in the PDF archived for the record identified by "shipment" is exactly "Für die Truhe:"
    And the visual line containing text "Kartons bitte mit \"Truhe\" beschriften" in the PDF archived for the record identified by "shipment" is exactly "Kartons bitte mit \"Truhe\" beschriften"
    And the visual line containing text "Sortimentsware" in the PDF archived for the record identified by "shipment" is exactly "Sortimentsware"

    # the block's own embedded blank line occupies exactly one full line here too: the span across it covers
    # exactly twice the height of a single ordinary line within the very same block. A line-count assertion
    # cannot see this -- a blank line emits no glyphs at all.
    And in the PDF archived for the record identified by "shipment", the vertical distance from text "Bitte saubere Schalen mit ordentlichem beklebten Deckel" to text "Kartons bitte mit \"Truhe\" beschriften" is 2 times the distance from text "Für die Truhe:" to text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"

    And the PDF archived for the record identified by "shipment" has no overlapping text

  @Id:S27486_TC3
  Scenario: Overriding the derived scope by hand keeps the block off a delivery note that carries none of its run
    # the TC2 fixture, unchanged: three groups, the same three text lines, the same partial delivery. The ONE
    # difference is the scope field of the top block, set by hand below -- so anything that differs in the
    # printed result is attributable to that field and to nothing else.
    Given metasfresh contains M_Products:
      | Identifier | Value      | Name        |
      | productE   | EPSILON-NR | EpsilonItem |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | ppE        | plv                    | productE     | 10.0     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |
      | line50     | order      | productE     | 1          |

    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """
    And a text line "freshHeading" is inserted above the order line identified by "line50" with text:
      """
      Frischware
      """

    # derived first, exactly as in TC2 -- the override below has to be visible as a CHANGE, not as a value
    # that happened to be there all along
    Then the text line identified by "topBlock" has TextLineScope "Document"
    When the text line identified by "topBlock" has its TextLineScope set to "Following"
    Then the text line identified by "topBlock" has TextLineScope "Following"
    # the edit touched the scope field only: the text is still the one that was typed
    And the text line identified by "topBlock" has 4 lines
    And the text line identified by "topBlock" has a blank line at position 3

    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
      | ss30       | line30         | N             |
      | ss40       | line40         | N             |
      | ss50       | line50         | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss30                  |
      | ss40                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss30                  | shipment   | CO        |
    And The jasper process is run
      | Value                 | Record_ID |
      | Lieferschein (Jasper) | shipment  |
    Then an AD_Archive exists for the record identified by "shipment"

    # the same partial shipment as TC2
    And the PDF archived for the record identified by "shipment" contains text "GammaItem"
    And the PDF archived for the record identified by "shipment" contains text "DeltaItem"

    # the override's whole point: none of the top block's run (line10, line20) is on this shipment, and it is
    # no longer whole-document scoped, so nothing carries it any more. In TC2, on the identical fixture, every
    # one of these four lines printed.
    And the PDF archived for the record identified by "shipment" does not contain text "Für die Truhe:"
    And the PDF archived for the record identified by "shipment" does not contain text "Bitte saubere Schalen mit ordentlichem beklebten Deckel"
    And the PDF archived for the record identified by "shipment" does not contain text "Kartons bitte mit \"Truhe\" beschriften"

    # unchanged by the override, both of them: the heading of the shipped group still prints in position, and
    # the absent group's heading still does not. Without this pair, "does not print" above would also be
    # satisfied by a document that had lost its text lines altogether.
    And the PDF archived for the record identified by "shipment" contains text "Sortimentsware"
    And in the PDF archived for the record identified by "shipment", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"
    And the PDF archived for the record identified by "shipment" does not contain text "Frischware"

    And the PDF archived for the record identified by "shipment" has no overlapping text

  @Id:S27486_TC4
  Scenario: A group split across two deliveries prints its heading on both, above that document's own first line of the group
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |

    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    # the second group's heading: its run is line30 and line40, the two lines the two shipments below split
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """
    Then the text line identified by "groupHeading" has TextLineScope "Following"

    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
      | ss30       | line30         | N             |
      | ss40       | line40         | N             |

    # first delivery: one article of the second group
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss30                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss30                  | shipmentOne | CO        |
    # second delivery: the rest of that same group
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss40                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss40                  | shipmentTwo | CO        |

    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentOne |
    Then an AD_Archive exists for the record identified by "shipmentOne"
    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentTwo |
    Then an AD_Archive exists for the record identified by "shipmentTwo"

    # the split is real: each document carries exactly one of the group's two articles
    And the PDF archived for the record identified by "shipmentOne" contains text "GammaItem"
    And the PDF archived for the record identified by "shipmentOne" does not contain text "DeltaItem"
    And the PDF archived for the record identified by "shipmentTwo" contains text "DeltaItem"
    And the PDF archived for the record identified by "shipmentTwo" does not contain text "GammaItem"

    # the heading prints on BOTH, each time directly above that document's OWN first line of the group --
    # a different article line on each. This is what a copy tied to one fixed order line could not do.
    And in the PDF archived for the record identified by "shipmentOne", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"
    And in the PDF archived for the record identified by "shipmentTwo", exactly 0 lines appear between text "Sortimentsware" and text "DeltaItem"

    # on each document the heading is the row immediately after the whole-document block, whose own run
    # (line10, line20) reaches neither shipment: it prints at the head of both, ahead of the heading
    And in the PDF archived for the record identified by "shipmentOne", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "Sortimentsware"
    And in the PDF archived for the record identified by "shipmentTwo", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "Sortimentsware"

    And the visual line containing text "Sortimentsware" in the PDF archived for the record identified by "shipmentOne" is exactly "Sortimentsware"
    And the visual line containing text "Sortimentsware" in the PDF archived for the record identified by "shipmentTwo" is exactly "Sortimentsware"
    And the PDF archived for the record identified by "shipmentOne" has no overlapping text
    And the PDF archived for the record identified by "shipmentTwo" has no overlapping text

  @Id:S27486_TC7
  Scenario: Deleting an article line re-anchors the text lines above it onto whatever line now follows
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |

    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """

    # the first article of each group is deleted. The first deletion is the requirement's own case; the
    # second is what makes the re-anchoring OBSERVABLE at all. "topBlock" is whole-document scoped, so it
    # would print on a derived document whether it re-anchored or not -- only "groupHeading", which prints
    # solely when an article of its run is present, can fail if the run stayed pinned to the deleted line.
    And delete C_OrderLine identified by line10, but keep its id into identifierIds table
    And delete C_OrderLine identified by line30, but keep its id into identifierIds table

    # deleting an article line leaves the text lines themselves untouched -- text and scope both
    Then the text line identified by "topBlock" has TextLineScope "Document"
    And the text line identified by "topBlock" has 4 lines
    And the text line identified by "topBlock" has a blank line at position 3
    And the text line identified by "groupHeading" has TextLineScope "Following"
    And the text line identified by "groupHeading" has text:
      """

      Sortimentsware
      """
    # the surviving article lines keep their own Line values: deleting never renumbers
    And the order line identified by "line20" still has Line 20
    And the order line identified by "line40" still has Line 40

    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss20       | line20         | N             |
      | ss40       | line40         | N             |

    # the remaining article of the first group
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss20                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss20                  | shipmentOne | CO        |
    # and the remaining article of the second group
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss40                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss40                  | shipmentTwo | CO        |

    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentOne |
    Then an AD_Archive exists for the record identified by "shipmentOne"
    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentTwo |
    Then an AD_Archive exists for the record identified by "shipmentTwo"

    # first delivery note: the top block still prints, above the article line that followed the deleted one
    And the PDF archived for the record identified by "shipmentOne" contains text "BetaItem"
    And in the PDF archived for the record identified by "shipmentOne", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "BetaItem"
    # the second group's heading has no article of its (re-anchored) run here, so it must not print -- the
    # contrast proving the rule is still discriminating on this very document
    And the PDF archived for the record identified by "shipmentOne" does not contain text "Sortimentsware"
    And the PDF archived for the record identified by "shipmentOne" has no overlapping text

    # second delivery note: the heading prints above line40, although the line it was inserted above is gone.
    # Nothing but the re-anchoring can produce this: had its run stayed the deleted line30, the run would be
    # empty here and a Following-scoped line with an empty run reaches no derived document at all (TC8).
    And the PDF archived for the record identified by "shipmentTwo" contains text "DeltaItem"
    And in the PDF archived for the record identified by "shipmentTwo", exactly 0 lines appear between text "Sortimentsware" and text "DeltaItem"
    # and the whole-document block is still ahead of it, its own run (line20) being absent from this one
    And in the PDF archived for the record identified by "shipmentTwo", exactly 0 lines appear between text "Kartons bitte mit \"Truhe\" beschriften" and text "Sortimentsware"
    And the PDF archived for the record identified by "shipmentTwo" has no overlapping text

  @Id:S27486_TC9
  Scenario: A delivery note's text lines are its own copies and do not follow a later edit of the order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
      | line40     | order      | productD     | 1          |

    When a text line "topBlock" is inserted above the order line identified by "line10" with text:
      """
      Für die Truhe:
      Bitte saubere Schalen mit ordentlichem beklebten Deckel

      Kartons bitte mit "Truhe" beschriften
      """
    And a text line "groupHeading" is inserted above the order line identified by "line30" with text:
      """

      Sortimentsware
      """

    When the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss10       | line10         | N             |
      | ss20       | line20         | N             |
      | ss30       | line30         | N             |
      | ss40       | line40         | N             |

    # the first delivery note takes one article out of each group, so the heading's run reaches it
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss10                  |
      | ss30                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss10                  | shipmentOne | CO        |

    # NOW the order's own text line is corrected. The wording is changed outright rather than extended, so
    # that "the old wording is gone" and "the new wording is here" are two independent assertions on each
    # document -- a new value containing the old one as a substring could not tell them apart.
    When the text line identified by "groupHeading" is changed to text:
      """

      Trockensortiment
      """
    Then the text line identified by "groupHeading" has text:
      """

      Trockensortiment
      """
    # the edit changed the text field only
    And the text line identified by "groupHeading" has TextLineScope "Following"

    # the second delivery note is created AFTER the edit, from the same order
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss20                  |
      | ss40                  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  | DocStatus |
      | ss20                  | shipmentTwo | CO        |

    # both are printed only now, after the edit: the first one's print therefore cannot be a stale rendering
    # taken before the change -- it reads the copies that were written when it was created
    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentOne |
    Then an AD_Archive exists for the record identified by "shipmentOne"
    And The jasper process is run
      | Value                 | Record_ID   |
      | Lieferschein (Jasper) | shipmentTwo |
    Then an AD_Archive exists for the record identified by "shipmentTwo"

    # the delivery note that already existed still shows the original wording, in position
    And the PDF archived for the record identified by "shipmentOne" contains text "Sortimentsware"
    And the PDF archived for the record identified by "shipmentOne" does not contain text "Trockensortiment"
    And in the PDF archived for the record identified by "shipmentOne", exactly 0 lines appear between text "Sortimentsware" and text "GammaItem"

    # the one created afterwards shows the new wording, equally in position
    And the PDF archived for the record identified by "shipmentTwo" contains text "Trockensortiment"
    And the PDF archived for the record identified by "shipmentTwo" does not contain text "Sortimentsware"
    And in the PDF archived for the record identified by "shipmentTwo", exactly 0 lines appear between text "Trockensortiment" and text "DeltaItem"

    And the PDF archived for the record identified by "shipmentOne" has no overlapping text
    And the PDF archived for the record identified by "shipmentTwo" has no overlapping text
