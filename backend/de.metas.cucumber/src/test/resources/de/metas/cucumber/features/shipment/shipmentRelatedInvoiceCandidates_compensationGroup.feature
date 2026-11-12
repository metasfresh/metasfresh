@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor8
Feature: Related documents of a shipment list the invoice candidates the invoicing needs (compensation group siblings)
## F2070: Compensation Group Contract
##
## From a sales shipment, "Related Documents" -> "Rechnungsdisposition" must list the directly related invoice
## candidates plus the siblings of their compensation group (the invoicing demands the whole group, see
## C_Invoice_Candidate_SelectionIncompleteGroups). The steps list the candidates as the WebUI shows them.
## The contract fixture mirrors the customer: 3 % bonus, service-type bonus product (never shipped), goods 1000 / 500 / 200,
## invoice rule "after delivery".

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    # the bonus product is a service, so its tax category must not be restricted to items
    And taxCategory 'Normal' is updated to work with all productTypes

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
    And metasfresh contains M_Products:
      | Identifier   | M_Product_Category_ID | ProductType | IsStocked |
      | goods1       | goodsCategory         | I           | false     |
      | goods2       | goodsCategory         | I           | false     |
      | goods3       | goodsCategory         | I           | false     |
      | bonusProduct |                       | S           | false     |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS         | DE                    | EUR                 | contractPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID | Name        | ValidFrom  |
      | contractPLV | contractPL     | contractPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods1  | contractPLV            | goods1       | 1000     | PCE               | Normal                        |
      | pp_goods2  | contractPLV            | goods2       | 500      | PCE               | Normal                        |
      | pp_goods3  | contractPLV            | goods3       | 200      | PCE               | Normal                        |
      | pp_bonus   | contractPLV            | bonusProduct | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | TermOfNotice | TermOfNoticeUnit | ExtensionType | EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0            | day              | EO            | 2026,2027             |

    # invoice rule "after delivery": only what was shipped becomes invoiceable
    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | headOfficeBP | Y          | contractPS         | D           |
      | storeBP      | Y          | contractPS         | D           |
      | plainBP      | Y          | contractPS         | D           |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          | IsAdditive |
      | mainSchema | Bonus Ware 3% | true       |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID | M_Product_ID | CompleteOrderDiscount | M_Product_Category_ID |
      | mainSchemaLine | mainSchema                    | bonusProduct | 3                     | goodsCategory         |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name          | C_CompensationGroup_Schema_ID |
      | mainSettings | Main settings | mainSchema                    |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID | C_DocType_ID      |
      | mainSettings                            | docTypeSalesOrder |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name            | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | mainConditions | Main conditions | CompensationGroup | zeroDurTrans                            | mainSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | mainTerm   | mainConditions                      | headOfficeBP                | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by mainTerm is completed

  @from:cucumber
  @Id:S33240_TC15a
  Scenario: Contract group, shipment of goods1: the related invoice candidates are the whole group, in one entry
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment1" are:
      | C_Invoice_Candidate_ID                    |
      | ic_goods1, ic_goods2, ic_goods3, ic_bonus |
    And shipment "shipment1" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC15b
  Scenario: After the first invoice, the second shipment lists the whole group again, incl. the invoiced goods1
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    # ticking the whole list of shipment 1 and "Create invoices"
    When run the invoicing process for the invoice candidates opened from the shipment "shipment1"
    Then the invoicing run succeeded
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice1     | ic_goods1              |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
      | ss_goods3             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods2             | shipment2  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment2" are:
      | C_Invoice_Candidate_ID                    |
      | ic_goods1, ic_goods2, ic_goods3, ic_bonus |
    And shipment "shipment2" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC15c
  Scenario: Two shipments of the same order, none invoiced: both list the same whole group
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods2             | shipment2  |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods3             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods3             | shipment3  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment2" are:
      | C_Invoice_Candidate_ID                    |
      | ic_goods1, ic_goods2, ic_goods3, ic_bonus |
    Then the related invoice candidates of shipment "shipment3" are:
      | C_Invoice_Candidate_ID                    |
      | ic_goods1, ic_goods2, ic_goods3, ic_bonus |

  @from:cucumber
  @Id:S33240_TC15d
  Scenario: Order without a compensation group: the related invoice candidates are only those of the shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | plainOrder | true    | plainBP       | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_plain1  | plainOrder | goods1       | 1          |
      | ol_plain2  | plainOrder | goods2       | 1          |
    And the order identified by plainOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_plain1  | ol_plain1      | N             |
      | ss_plain2  | ol_plain2      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_plain1      | ic_plain1              |
      | ol_plain2      | ic_plain2              |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_plain1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_plain1             | plainShipment |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_plain1              |
      | ic_plain2              |
    Then the related invoice candidates of shipment "plainShipment" are:
      | C_Invoice_Candidate_ID |
      | ic_plain1              |
    And shipment "plainShipment" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC15e
  Scenario: A direct candidate that may be invoiced separately gets no siblings
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    # set before the order is completed, as the flag is copied once to the invoice candidate when it is created
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | IsAllowSeparateInvoicing |
      | ol_goods1                 | Y                        |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment1" are:
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
    And shipment "shipment1" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC15f
  Scenario: Customer return keeps its invoice-candidate entry with the candidates of the return
    Given metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsQualityReturnWarehouse |
      | wh             | Y                        |
    And metasfresh contains M_Products:
      | Identifier |
      | product_CR |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_CR      | contractPLV            | product_CR   | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | DeliveryRule | OPT.DeliveryViaRule |
      | order_CR   | Y       | plainBP       | 2026-07-01  | contractPS         | plainBP                | F            | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_CR | order_CR   | product_CR   | 10         |
    And the order identified by order_CR is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | schedule_CR | orderLine_CR   | N             | 10           |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_CR           | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | schedule_CR           | shipment_CR |
    And generate customer return from shipment
      | M_InOut_ID  | CustomerReturn_ID |
      | shipment_CR | customerReturn_CR |
    And the return inOut identified by customerReturn_CR is completed
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID        | C_Invoice_Candidate_ID | M_Product_ID |
      | customerReturn_CR | ic_CR                  | product_CR   |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_CR                  |
    Then the related invoice candidates of shipment "customerReturn_CR" are:
      | C_Invoice_Candidate_ID |
      | ic_CR                  |
    And shipment "customerReturn_CR" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC15g
  Scenario: Vendor return keeps its invoice-candidate entry with the candidates of the return
    Given load M_Warehouse:
      | Identifier   | Value        |
      | warehouseStd | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product_VR |
    And load M_Product:
      | Identifier      | M_Product_ID |
      | packingMaterial | 2001343      |
    And load M_Product:
      | Identifier    | M_Product_ID |
      | palletProduct | 2000616      |
    And load M_HU_PI:
      | M_HU_PI_ID.Identifier  | M_HU_PI_ID |
      | huPackingTauschpalette | 1000006    |
    And load M_HU_PI_Version:
      | Identifier          | M_HU_PI_Version_ID |
      | packingItem_IFCO_VR | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | Identifier       | M_HU_PI_Version_ID  | Qty | ItemType |
      | huPiItem_IFCO_VR | packingItem_IFCO_VR | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier         | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | huPiItemProduct_VR | huPiItem_IFCO_VR | product_VR   | 10  | 2026-06-01 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_VR      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_VR      | ps_VR              | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_VR     | pl_VR          | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_VR      | plv_VR                 | product_VR      | 10.0     | PCE               | Normal                        |
      | pm_VR      | plv_VR                 | packingMaterial | 0.2      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | supplier_VR | Y        | N          | ps_VR              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | DocBaseType | M_PricingSystem_ID | C_BPartner_Location_ID | DeliveryRule | OPT.DeliveryViaRule |
      | order_VR   | N       | supplier_VR   | 2026-07-01  | po_ref_VR   | POO         | ps_VR              | supplier_VR            | F            | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_VR | order_VR   | product_VR   | 10         |
    And the order identified by order_VR is completed
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_VR   | order_VR   | orderLine_VR   | supplier_VR   | supplier_VR            | product_VR   | 10         | warehouseStd   |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | Identifier      | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID          |
      | huLuTuConfig_VR | hu_VR   | receiptSchedule_VR   | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_VR      | huPackingTauschpalette |
    When create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | hu_VR   | receiptSchedule_VR   | receipt_VR |
    And generate vendor return from receipt
      | M_InOut_ID | VendorReturn_ID |
      | receipt_VR | vendorReturn_VR |
    And the return inOut identified by vendorReturn_VR is completed
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID      | C_Invoice_Candidate_ID | M_Product_ID    |
      | vendorReturn_VR | ic_VR                  | product_VR      |
      | vendorReturn_VR | ic_VR_ifco             | packingMaterial |
      | vendorReturn_VR | ic_VR_pallet           | palletProduct   |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_VR                  |
      | ic_VR_ifco             |
      | ic_VR_pallet           |
    Then the related invoice candidates of shipment "vendorReturn_VR" are:
      | C_Invoice_Candidate_ID          |
      | ic_VR, ic_VR_ifco, ic_VR_pallet |
    And shipment "vendorReturn_VR" has 1 related documents entry to the invoice candidates

  @from:cucumber
  @Id:S33240_TC16
  Scenario: Goods1 delivered in two picked parts, first part invoiced: the list of the second shipment (the invoice candidate stays linked to the first shipment)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 10         |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    # first part: 5 picked and shipped
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_1               | 5            | ss_goods1                        | goods1                  |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | ss_goods1                        | goods1                  | 5         | hu_1               |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    When run the invoicing process for the invoice candidates opened from the shipment "shipment1"
    Then the invoicing run succeeded
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice1     | ic_goods1              |
    # second part: the other 5; the first shipment closed the schedule, the user reopens it ("Open processed")
    And the M_ShipmentSchedule identified by ss_goods1 is reactivated
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_2               | 5            | ss_goods1                        | goods1                  |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | ss_goods1                        | goods1                  | 5         | hu_2               |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | ss_goods1             | shipment2  | shipment1                               |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    # goods1's invoice candidate keeps M_InOut_ID of shipment1, so shipment2 has no direct candidate and nothing is added
    Then shipment "shipment2" has 0 related documents entries to the invoice candidates

  @from:cucumber
  @Id:S33240_TC17
  Scenario: Product-schema group (no contract): list = the whole group; partial tick refused, tick all invoiced
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier    | Name       |
      | productSchema | Bundle 3 % |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_goods1  | productSchema                 | goods1       | 1   | PCE      | 10    |
      | tl_goods2  | productSchema                 | goods2       | 1   | PCE      | 20    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier        | C_CompensationGroup_Schema_ID | M_Product_ID | CompleteOrderDiscount |
      | productSchemaLine | productSchema                 | bonusProduct | 3                     |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered |
      | orderProduct | true    | plainBP       | 2026-07-01  |
    When create compensation group from schema template:
      | C_Order_ID   | C_CompensationGroup_Schema_ID | Qty |
      | orderProduct | productSchema                 | 1   |
    And the order identified by orderProduct is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID   | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | orderProduct | bonusProduct | 1          | true                    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | schema_ol_1    | N             |
      | ss_goods2  | schema_ol_2    | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | schema_ol_1    | ic_goods1              |
      | schema_ol_2    | ic_goods2              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment1" are:
      | C_Invoice_Candidate_ID         |
      | ic_goods1, ic_goods2, ic_bonus |
    When run the invoicing process for the invoice candidates "ic_goods1"
    Then the invoicing run failed
    And the invoicing run summary contains:
      | Cannot invoice incomplete compensation groups |
    When run the invoicing process for the invoice candidates opened from the shipment "shipment1"
    Then the invoicing run succeeded
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice1     | ic_goods1              |
