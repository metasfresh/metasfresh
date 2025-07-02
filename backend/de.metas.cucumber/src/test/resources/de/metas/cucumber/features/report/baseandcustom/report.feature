@report
@from:cucumber
Feature: Jasper Report Tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    And all periods are open
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | true                     |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps_1               | CH           | CHF           | false |
      | pl_sales    | ps_1               | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
      | plv_sales    | pl_sales       |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_1       | plv_purchase           | product      | 8.0      | PCE      |
      | pp_2       | plv_sales              | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps_1               |
      | customer   | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation   | vendor        | CH           | Y               | Y               |
      | customerLocation | customer      | CH           | Y               | Y               |
    And metasfresh contains C_Tax
      | Identifier        | C_TaxCategory_ID.InternalName | Name      | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | de_ch_tax         | Normal                        | de_ch_tax | 2021-04-02 | 2.5  | DE                       | CH                        |
      | ch_ch_tax         | Normal                        | ch_ch_tax | 2021-04-02 | 2.5  | CH                       | CH                        |

  @S0471_100
  @from:cucumber
  Scenario: Purchase Report Test
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_PricingSystem_ID | M_Warehouse_ID |
      | po1        | N       | vendor        | 2025-04-01  | POO         | ps_1               | wh             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po1_l1     | po1        | product      | 10         |
    And the order identified by po1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs1                  | po1        | po1_l1         | vendor        | vendorLocation         | product      | 10         | wh             |
    And The jasper process is run
      | Value               | Record_ID  |
      | Bestellung (Jasper) | po1        |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | huPiItemLU      | LU_Version         | 10  | HU       | TU                    |
      | huPiItemTU      | TU_Version         |     | MI       |                       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | product      | 10  | 2021-01-01 |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | hu1     | rs1                  | N               | 1     | N               | 1     | N               | 10          | product_TU_10CU         | LU            |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | hu1     | rs1                  | receipt1   |
    And validate the created material receipt lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | C_OrderLine_ID |
      | receipt1_l1    | receipt1   | product      | po1_l1         |
    And The jasper process is run
      | Value                 | Record_ID  |
      | Wareneingang (Jasper) | receipt1   |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | po_ic_1                | po1_l1         |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | po_ic_1                           |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | po_ic_1                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID      | C_Invoice_Candidate_ID |
      | purchaseInvoice_1 | po_ic_1                |
    And The jasper process is run
      | Value                     | Record_ID         |
      | Eingangsrechnung (Jasper) | purchaseInvoice_1 |



  @S0471_200
  @from:cucumber
  Scenario: Sales Report Test
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so1        | true    | customer      | 2025-04-01  | wh             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so1_l1     | so1        | product      | 10         |
    When the order identified by so1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | M_Warehouse_ID |
      | ss1        | so1_l1         | N             | wh             |
    And The jasper process is run
      | Value            | Record_ID  |
      | Auftrag (Jasper) | so1        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss1                   | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss1                   | shipment1  |
    And The jasper process is run
      | Value                 | Record_ID  |
      | Lieferschein (Jasper) | shipment1  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | so_ic1                            | so1_l1                    | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | so_ic1                 |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID  |
      | so_ic1                 | salesInvoice1 |
    And validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | C_BPartner_Location_ID | processed | docStatus |
      | salesInvoice1 | customer      | customerLocation       | true      | CO        |
    And The jasper process is run
      | Value             | Record_ID     |
      | Rechnung (Jasper) | salesInvoice1 |




  @from:cucumber
  Scenario: Deactivate StoreArchiveOnFileSystem
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
