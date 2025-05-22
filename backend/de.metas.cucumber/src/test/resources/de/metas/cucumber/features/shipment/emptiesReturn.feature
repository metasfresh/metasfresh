@from:cucumber
@ghActions:run_on_executor7
Feature: Empties returns

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-11T13:30:13+01:00[Europe/Berlin]

    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | A             |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | TU_Product | standard_category     |
      | LU_Product | standard_category     |
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
      | plv_PO                 | TU_Product   | 2.0      | PCE      |
      | plv_PO                 | LU_Product   | 5.0      | PCE      |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | TU_Product   | 1                |
      | LU_Product   | 1                |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | InvoiceRule |
      | bpartner   | N          | Y        | ps_1               | D           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | location   | bpartner      | true            | true     |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Locator:
      | M_Locator_ID | M_Warehouse_ID | Value        |
      | locator      | warehouseStd   | LocatorValue |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPackingLU_returns   |
      | huPackingTU_returns   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID       | M_HU_PI_ID          | HU_UnitType | IsCurrent |
      | packingVersionLU_returns | huPackingLU_returns | LU          | Y         |
      | packingVersionTU_returns | huPackingTU_returns | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID     | M_Product_ID |
      | huPackingMaterialTU_returns | TU_Product   |
      | huPackingMaterialLU_returns | LU_Product   |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID    | M_HU_PI_Version_ID       | Qty | ItemType | Included_HU_PI_ID   |
      | huPiItemLU_returns | packingVersionLU_returns | 10  | HU       | huPackingTU_returns |
      | huPiItemTU_returns | packingVersionTU_returns | 0   | PM       |                     |


  @from:cucumber
  @Id:S0160.4_220
  Scenario: Create and complete empties return InOut: TU
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    #
    # Empties Return (Shipment)
    #
    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |
    Then validate the created shipments
      | M_InOut_ID | C_BPartner_ID | C_BPartner_Location_ID | dateordered | poreference | processed | docStatus | C_DocType.DocBaseType | C_DocType.Name |
      | inOut      | bpartner      | location               | 2022-08-11  | po_ref_mock | false     | DR        | MMS                   | Leergutausgabe |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine
    And the return inOut identified by inOut is completed
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |

    #
    # Vendor Credit Memo
    #
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | QtyInvoiced |
      | invoiceCand_1          | -10         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID     |
      | invoiceCand_1          | vendorCreditMemo |
    And validate created invoices
      | C_Invoice_ID     | C_BPartner_ID | C_BPartner_Location_ID | processed | docStatus | DocBaseType |
      | vendorCreditMemo | bpartner      | location               | true      | CO        | APC         |
    And validate created invoice lines
      | C_InvoiceLine_ID     | C_Invoice_ID     | M_Product_ID | QtyInvoiced | Processed | PriceEntered | PriceActual | LineNetAmt | QtyMatched |
      | vendorCreditMemoLine | vendorCreditMemo | TU_Product   | 10          | true      | 2 EUR        | 2 EUR       | 20 EUR     | 10 PCE     |
    And validate the created shipment lines by id
      | Identifier | QtyEntered | QtyMatched |
      | inOutLine  | 10         | 10 PCE     |

    And M_MatchInv are found
      | M_MatchInv_ID | C_InvoiceLine_ID     | M_InOutLine_ID | IsSOTrx | M_Product_ID | QtyInUOM |
      | matchInv1     | vendorCreditMemoLine | inOutLine      | false   | TU_Product   | -10 PCE  | 

    #
    # Check accounting
    #
    And Fact_Acct records are matching
      | AccountConceptualName       | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty     | M_Product_ID | Record_ID        | Testing Comments                                    |
      | V_Liability_Acct            | 26.89     |           | 23.8 EUR    |             |         | -            | vendorCreditMemo |                                                     |
      | T_Credit_Acct               |           | 4.29      |             | +3.80 EUR   |         | -            | vendorCreditMemo |                                                     |
      | P_InventoryClearing_Acct    |           | 22.60     |             | +20.00 EUR  | -10 PCE | TU_Product   | vendorCreditMemo |                                                     |
      #----------------------------------------------------------------------------------------------------------------------------
      | P_InventoryClearing_Acct    |           | -22.60    |             | -20.00 EUR  | +10 PCE | TU_Product   | matchInv1        | from Invoice                                        |
      | P_InvoicePriceVariance_Acct |           | +12.60    |             | +12.60 CHF  |         | TU_Product   | matchInv1        |                                                     |
      | NotInvoicedReceipts_Acct    | -10       |           | -10 CHF     |             | -10 PCE | TU_Product   | matchInv1        | from inOut                                          |
      #----------------------------------------------------------------------------------------------------------------------------
      | NotInvoicedReceipts_Acct    | 10        |           | +10 CHF     |             | +10 PCE | TU_Product   | inOut            |                                                     |
      | P_Asset_Acct                |           | 10        |             | +10 CHF     | -10 PCE | TU_Product   | inOut            | we don't have an order so we use current cost price |
    And Fact_Acct records balances for documents vendorCreditMemo,matchInv1,inOut are matching
      | AccountConceptualName    | M_Product_ID | AcctBalance | SourceBalance | Qty     |
      | P_InventoryClearing_Acct | TU_Product   | 0           | 0 EUR         | 0 PCE   |
      | NotInvoicedReceipts_Acct | TU_Product   | 0           | 0 CHF         | 0 PCE   |
      | P_Asset_Acct             | TU_Product   | -10         | -10 CHF       | -10 PCE |

  @from:cucumber
  @Id:S0160.4_230
  Scenario: Create and complete empties return InOut: LU
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine LU packing  material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_240
  Scenario: Create and complete empties return InOut: TU - then reactivate it
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_250
  Scenario: Create and complete empties return InOut: LU - then reactivate it
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_260
  Scenario: Create and complete empties return InOut: TU - reactivate and complete it again
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is completed again
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10 and C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_261
  Scenario: Create and complete empties return InOut: TU - reactivate, increase qty and complete it again
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 20
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -20 and C_InvoiceCandidate_InOutLine.QtyDelivered = 20;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | inOutLine                 | 20         | 20          |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -20              | -20          | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 20               |


  @from:cucumber
  @Id:S0160.4_262
  Scenario: Create and complete empties return InOut: TU - reactivate, decrease qty and complete it again
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 5
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -5; C_InvoiceCandidate_InOutLine.QtyDelivered = 5;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | inOutLine                 | 5          | 5           |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -5               | -5           | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 5                |

  @from:cucumber
  @Id:S0160.4_270
  Scenario: Create and complete empties return InOut: LU - reactivate and complete it again
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is completed again
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_271
  Scenario: Create and complete empties return InOut: LU - reactivate, increase qty and complete it again
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 20
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -20; C_InvoiceCandidate_InOutLine.QtyDelivered = 20;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | inOutLine                 | 20         | 20          |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -20              | -20          | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 20               |


  @from:cucumber
  @Id:S0160.4_272
  Scenario: Create and complete empties return InOut: LU - reactivate, decrease qty and complete it again
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 5
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -5; C_InvoiceCandidate_InOutLine.QtyDelivered = 5;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | inOutLine                 | 5          | 5           |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -5               | -5           | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 5                |


  @from:cucumber
  @Id:S0160.4_280
  Scenario: Create and complete empties return InOut: TU - then close it
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is closed
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CL        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_290
  Scenario: Create and complete empties return InOut: LU - then close it
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is closed
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CL        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_300
  Scenario: Create and complete empties return InOut: TU - then revert it
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reverted
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | RE        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_310
  Scenario: Create and complete empties return InOut: LU - then revert it
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reverted
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | RE        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_320
  Scenario: Create and complete empties return InOut: TU - then reactivate and void it
  _Given TU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is voided
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | TU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | TU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_330
  Scenario: Create and complete empties return InOut: LU - then reactivate and void it
  _Given LU packing material
  _When EmptiesReturn process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutausgabe automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When the inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is voided
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | LU_Product                  | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | null                      | -10              | -10          | inOutLine                     |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |

    When the shipment identified by inOut is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | LU_Product                  |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |
