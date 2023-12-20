@from:cucumber
@ghActions:run_on_executor7
Feature: Empties returns

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-11T13:30:13+01:00[Europe/Berlin]

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains M_Products:
      | Identifier               | Name                                    | OPT.M_Product_Category_ID.Identifier |
      | packingProductTU_returns | packingProductTU_returns_S0160_11082022 | standard_category                    |
      | packingProductLU_returns | packingProductLU_returns_S0160_11082022 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value          |
      | ps_1       | pricing_system_name | S0160_11082022 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_1                          | DE                        | EUR                 | price_list_name_PO | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_PO     | pl_PO                     | purchase-PLV | 2022-08-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_PO                            | packingProductTU_returns | 2.0      | PCE               | Normal                        |
      | pp_2       | plv_PO                            | packingProductLU_returns | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner   | BPartnerName | N              | Y            | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location   | 1234567890036 | bpartner                 | true                | true         |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value        |
      | locator                 | warehouseStd              | LocatorValue |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                      |
      | huPackingLU_returns   | huPackingLU_returns_S0160 |
      | huPackingTU_returns   | huPackingTU_returns_S0160 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                           | HU_UnitType | IsCurrent |
      | packingVersionLU_returns      | huPackingLU_returns   | packingVersionLU_returns_S0160 | LU          | Y         |
      | packingVersionTU_returns      | huPackingTU_returns   | packingVersionTU_returns_S0160 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                              | OPT.M_Product_ID.Identifier |
      | huPackingMaterialTU_returns        | huPackingMaterialTU_returns_S0160 | packingProductTU_returns    |
      | huPackingMaterialLU_returns        | huPackingMaterialLU_returns_S0160 | packingProductLU_returns    |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_returns         | packingVersionLU_returns      | 10  | HU       | huPackingTU_returns              |
      | huPiItemTU_returns         | packingVersionTU_returns      | 0   | PM       |                                  |


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

    When trigger EMPTIES RETURN process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-11  | po_ref_mock | false     | DR        | MMS                       | Leergutausgabe     |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
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
      | invoiceCand_1                     | null                          | -20              | -20          | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
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
      | invoiceCand_1                     | null                          | -5               | -5           | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
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
      | invoiceCand_1                     | null                          | -20              | -20          | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
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
      | invoiceCand_1                     | null                          | -5               | -5           | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
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
      | inOutLine                 | inOut                 | packingProductTU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_returns    |
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
      | inOutLine                 | inOut                 | packingProductLU_returns    | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_returns    |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |