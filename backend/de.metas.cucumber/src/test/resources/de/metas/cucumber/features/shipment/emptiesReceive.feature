@from:cucumber
@ghActions:run_on_executor7
Feature: Empties receives

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-10T13:30:13+01:00[Europe/Berlin]

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains M_Products:
      | Identifier                | Name                                     | OPT.M_Product_Category_ID.Identifier |
      | purchaseProduct           | purchaseProduct_10082022                 | standard_category                    |
      | packingProductTU_receives | packingProductTU_receives_S0160_10082022 | standard_category                    |
      | packingProductLU_receives | packingProductLU_receives_S0160_10082022 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value          |
      | ps_1       | pricing_system_name | S0160_10082022 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_1                          | DE                        | EUR                 | price_list_name_PO | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_PO     | pl_PO                     | purchase-PLV | 2022-08-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier   | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_PO                            | purchaseProduct           | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_PO                            | packingProductTU_receives | 2.0      | PCE               | Normal                        |
      | pp_2       | plv_PO                            | packingProductLU_receives | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner   | BPartnerName | N              | Y            | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location   | 1232567890036 | bpartner                 | true                | true         |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value        |
      | locator                 | warehouseStd              | LocatorValue |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                       |
      | huPackingLU_receives  | huPackingLU_receives_S0160 |
      | huPackingTU_receives  | huPackingTU_receives_S0160 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                            | HU_UnitType | IsCurrent |
      | packingVersionLU_receives     | huPackingLU_receives  | packingVersionLU_receives_S0160 | LU          | Y         |
      | packingVersionTU_receives     | huPackingTU_receives  | packingVersionTU_receives_S0160 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                               | OPT.M_Product_ID.Identifier |
      | huPackingMaterialTU_receives       | huPackingMaterialTU_receives_S0160 | packingProductTU_receives   |
      | huPackingMaterialLU_receives       | huPackingMaterialLU_receives_S0160 | packingProductLU_receives   |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_receives        | packingVersionLU_receives     | 10  | HU       | huPackingTU_receives             |
      | huPiItemTU_receives        | packingVersionTU_receives     | 0   | PM       |                                  |


  @from:cucumber
  @Id:S0160.4_100
  Scenario: Create and complete empties receive InOut: TU
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
  @Id:S0160.4_110
  Scenario: Create and complete empties receive InOut: LU
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
  @Id:S0160.4_120
  Scenario: Create and complete empties receive InOut: TU - then reactivate it
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_130
  Scenario: Create and complete empties receive InOut: LU - then reactivate it
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_140
  Scenario: Create and complete empties receive InOut: TU - reactivate and complete it again
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is completed again
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_141
  Scenario: Create and complete empties receive InOut: TU - reactivate, increase qty and complete it again
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 20
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -20; C_InvoiceCandidate_InOutLine.QtyDelivered = 20;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
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
      | invoiceCand_1                     | null                          | -20              | -20          | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 20               |


  @from:cucumber
  @Id:S0160.4_142
  Scenario: Create and complete empties receive InOut: TU - reactivate, decrease qty and complete it again
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 5
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -5; C_InvoiceCandidate_InOutLine.QtyDelivered = 5;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
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
      | invoiceCand_1                     | null                          | -5               | -5           | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 5                |

  @from:cucumber
  @Id:S0160.4_150
  Scenario: Create and complete empties receive InOut: LU - reactivate and complete it again
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is completed again
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_151
  Scenario: Create and complete empties receive InOut: LU - reactivate, = qty and complete it again
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 20
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -20; C_InvoiceCandidate_InOutLine.QtyDelivered = 20;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    And validate M_In_Out status
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
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
      | invoiceCand_1                     | null                          | -20              | -20          | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 20               |


  @from:cucumber
  @Id:S0160.4_152
  Scenario: Create and complete empties receive InOut: LU - reactivate, decrease qty and complete it again
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _And change M_InOutLine.QtyEntered to 5
  _And complete inOut
  _Then validate that C_InvoiceCandidate.QtyDelivered = -5; C_InvoiceCandidate_InOutLine.QtyDelivered = 5;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: inOutLine

    When the return inOut identified by inOut is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | CO        |
    Then after not more than 60s, C_Invoice_Candidate are found:
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
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
      | invoiceCand_1                     | null                          | -5               | -5           | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 5                |


  @from:cucumber
  @Id:S0160.4_160
  Scenario: Create and complete empties receive InOut: TU - then close it
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is closed
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_170
  Scenario: Create and complete empties receive InOut: LU - then close it
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is closed
  _Then validate that C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | -10              | -10          | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 10               |


  @from:cucumber
  @Id:S0160.4_180
  Scenario: Create and complete empties receive InOut: TU - then revert it
  _Given TU packing material;
  _And create and complete purchase order;
  _When EmptiesReceive process is triggered for M_ReceiptSchedule found for the completed order
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reverted
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered | OPT.C_BPartner_Location_ID.Identifier |
      | order      | false   | bpartner                 | po_ref_mock     | POO             | 2022-08-10  | location                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | purchaseProduct         | 10         |

    And the order identified by order is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | bpartner                 | location                          | purchaseProduct         | 10         | warehouseStd              |

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | OPT.M_ReceiptSchedule_ID.Identifier |
      | inOut                 | receiptSchedule                     |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_190
  Scenario: Create and complete empties receive InOut: LU - then revert it
  _Given LU packing material
  _And create and complete purchase order for M_Product = purchaseProduct
  _When EmptiesReceive process is triggered for M_ReceiptSchedule found for the completed order
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reverted
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered | OPT.C_BPartner_Location_ID.Identifier |
      | order      | false   | bpartner                 | po_ref_mock     | POO             | 2022-08-10  | location                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | purchaseProduct         | 10         |

    And the order identified by order is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | bpartner                 | location                          | purchaseProduct         | 10         | warehouseStd              |

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | OPT.M_ReceiptSchedule_ID.Identifier |
      | inOut                 | receiptSchedule                     |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_200
  Scenario: Create and complete empties receive InOut: TU - then reactivate and void it
  _Given TU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for TU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for TU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is voided
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductTU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductTU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |


  @from:cucumber
  @Id:S0160.4_210
  Scenario: Create and complete empties receive InOut: LU - then reactivate and void it
  _Given LU packing material
  _When EmptiesReceive process is triggered for no selected M_ReceiptSchedule
  _Then empties receive InOut is created without any lines having C_DocType.Name = Leergutrücknahme automatically set
  _And add M_InOutLine for LU packing material (M_InOutLine.QtyEntered = 10)
  _And validate no C_InvoiceCandidate_InOutLine found
  _When inOut is completed
  _Then validate created C_InvoiceCandidate for LU packing - C_InvoiceCandidate.QtyDelivered = -10; C_InvoiceCandidate_InOutLine.QtyDelivered = 10;
  _When the inOut is reactivated
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;
  _When the inOut is voided
  _Then validate that C_InvoiceCandidate.QtyDelivered = 0; C_InvoiceCandidate_InOutLine.QtyDelivered = 0;

    When trigger EMPTIES RECEIVE process:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | inOut                 | bpartner                 | location                          | warehouseStd              |

    Then validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus | OPT.C_DocType.DocBaseType | OPT.C_DocType.Name |
      | inOut                 | bpartner                 | location                          | 2022-08-10  | po_ref_mock | false     | DR        | MMR                       | Leergutrücknahme   |
    And validate no M_InOutLine found for M_InOut identified by inOut
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | inOutLine                 | inOut                 | packingProductLU_receives   | 10         | locator                     | PCE     | true                    | 10          |
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
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |

    When the shipment identified by inOut is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | inOut                 | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | null                          | 0                | 0            | packingProductLU_receives   |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | inOutLine                     | 0                |