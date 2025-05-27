@from:cucumber
@ghActions:run_on_executor4
Feature: Match Invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-07-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_PO      | ps_1               | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_PO     | pl_PO          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_PO                 | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | M_PricingSystem_ID | IsVendor | IsCustomer |
      | bpartner_1 | ps_1               | Y        | N          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | bpartner_1               | Y                   | Y                   |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | LU                    |
      | TU                    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | LU_Version                    | 10  | HU       | TU                               |
      | huPiItemTU                 | TU_Version                    |     | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | product_TU_10CU                    | huPiItemTU                 | product                 | 10  | 2021-01-01 |
    #
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | M             |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    # ##################################################################################################################################################
    # ##################################################################################################################################################
    # ##################################################################################################################################################
    # ##################################################################################################################################################
    # ##################################################################################################################################################
    # ##################################################################################################################################################
    # ##################################################################################################################################################
  @Id:S0160_100
  @from:cucumber
  Scenario: Receipt, Invoice => MatchInv
    #
    # Create the sales order
    #
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | po1        | false   | bpartner_1    | POO         | 2022-07-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po1_l1     | po1        | product      | 100        | 10           | product_TU_10CU         |
    When the order identified by po1 is completed

    #
    # Create the material receipt
    #
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule1                | po1                   | po1_l1                    | bpartner_1               | l_1                               | product                 | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu1                | receiptSchedule1                | N               | 1     | N               | 10    | N               | 10          | product_TU_10CU                    | LU                           |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu1                | receiptSchedule1                | materialReceipt       |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | materialReceipt       | CO        |
    And validate the created material receipt lines
      | M_InOutLine_ID      | M_InOut_ID      | M_Product_ID | movementqty | processed |
      | materialReceiptLine | materialReceipt | product      | 100         | true      |

    #
    # Create the vendor invoice
    #
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | po1                       | po1_l1                    | 100              | 100          | materialReceiptLine           |
    And validate created C_InvoiceCandidate_InOutLine
      | C_Invoice_Candidate_ID | M_InOutLine_ID      | QtyDelivered |
      | invoiceCand_1          | materialReceiptLine | 100          |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | invoiceCand_1          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice           | invoiceCand_1                     |
    And validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | C_BPartner_Location_ID | docStatus |
      | vendorInvoice | bpartner_1    | l_1                    | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID  | C_Invoice_ID  | M_Product_ID | QtyInvoiced |
      | vendorInvoiceLine | vendorInvoice | product      | 100         |
    And M_MatchInv are found
      | M_MatchInv_ID | C_InvoiceLine_ID  | M_InOutLine_ID      | M_Product_ID | QytInUOM | IsSOTrx |
      | matchInv1     | vendorInvoiceLine | materialReceiptLine | product      | 100      | N       |

    #
    # Check the accounting (for Moving Average Invoice)
    #
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty      | M_Product_ID | Record_ID       | C_BPartner_ID |
      | V_Liability_Acct         |           | 1344.70   |             | 1190 EUR    |          | -            | vendorInvoice   | bpartner_1    |
      | T_Credit_Acct            | 214.70    |           | 190 EUR     |             |          | -            | vendorInvoice   | bpartner_1    |
      | P_InventoryClearing_Acct | 1130      |           | 1000 EUR    |             | +100 PCE | product      | vendorInvoice   | bpartner_1    |
      #-----------------------------------------------------------------------------------------------------------------------------
      | P_InventoryClearing_Acct |           | 1130      |             | 1000 EUR    | -100 PCE | product      | matchInv1       | bpartner_1    |
      | NotInvoicedReceipts_Acct | 1130      |           | 1130 CHF    |             | +100 PCE | product      | matchInv1       | bpartner_1    |
      #-----------------------------------------------------------------------------------------------------------------------------
      | NotInvoicedReceipts_Acct |           | 1130      |             | 1000 EUR    | -100 PCE | product      | materialReceipt | bpartner_1    |
      | P_Asset_Acct             | 1130      |           | 1000 EUR    |             | +100 PCE | product      | materialReceipt | bpartner_1    |
    And Fact_Acct records balances for documents vendorInvoice,matchInv1,materialReceipt are matching
      | AccountConceptualName    | M_Product_ID | SourceBalance | AcctBalance | Qty      |
      | P_InventoryClearing_Acct | product      | 0 EUR         | 0           | 0 PCE    |
      | NotInvoicedReceipts_Acct | product      |               | 0           | 0 PCE    |
      | P_Asset_Acct             | product      | 1000 EUR      | 1130        | +100 PCE |

    #
    # Check the accounting (for AveragePO)
    #
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | A             |
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty      | M_Product_ID | Record_ID       | C_BPartner_ID |
      | V_Liability_Acct         |           | 1344.70   |             | 1190 EUR    |          | -            | vendorInvoice   | bpartner_1    |
      | T_Credit_Acct            | 214.70    |           | 190 EUR     |             |          | -            | vendorInvoice   | bpartner_1    |
      | P_InventoryClearing_Acct | 1130      |           | 1000 EUR    |             | +100 PCE | product      | vendorInvoice   | bpartner_1    |
      #-----------------------------------------------------------------------------------------------------------------------------
      | P_InventoryClearing_Acct |           | 1130      |             | 1000 EUR    | -100 PCE | product      | matchInv1       | bpartner_1    |
      | NotInvoicedReceipts_Acct | 1130      |           | 1130 CHF    |             | +100 PCE | product      | matchInv1       | bpartner_1    |
      #-----------------------------------------------------------------------------------------------------------------------------
      | NotInvoicedReceipts_Acct |           | 1130      |             | 1000 EUR    | -100 PCE | product      | materialReceipt | bpartner_1    |
      | P_Asset_Acct             | 1130      |           | 1000 EUR    |             | +100 PCE | product      | materialReceipt | bpartner_1    |
    And Fact_Acct records balances for documents vendorInvoice,matchInv1,materialReceipt are matching
      | AccountConceptualName    | M_Product_ID | SourceBalance | AcctBalance | Qty      |
      | P_InventoryClearing_Acct | product      | 0 EUR         | 0           | 0 PCE    |
      | NotInvoicedReceipts_Acct | product      |               | 0           | 0 PCE    |
      | P_Asset_Acct             | product      | 1000 EUR      | 1130        | +100 PCE |
