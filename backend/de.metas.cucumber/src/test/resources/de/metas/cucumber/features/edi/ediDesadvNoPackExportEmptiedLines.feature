@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI
Feature: EDI DESADV no-pack export must include emptied DESADV lines
# The single-mode no-pack EXP_Format (Name: EDI_Exp_DesadvLineWithNoPack) reads the physical
# EDI_DesadvLine table. A line that had a delivery but is then "emptied" drops out of the pack
# export and must still surface in the no-pack section, otherwise it disappears from the DESADV:
#   - scn 31: delivered qty set to 0        -> line must be selected by the no-pack format
#   - scn 32: line set inactive             -> line must be selected by the no-pack format
#   - scn 33: the line's pack set inactive  -> line no longer covered by an active pack, must be selected

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2024-06-10T13:30:13+02:00[Europe/Berlin]
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl         | ps                 | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp         | plv                    | product      | 10.0     | PCE      | Normal           |
    # new_dawn_uat keys the EDI recipient config on C_BPartner_EDI_Setting; an EDI_Setting with no
    # C_BPartner_Location_ID applies to all of the partner's locations.
    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID | GLN          |
      | endcustomer | Y          | ps                 | location_gln |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN      | Identifier |
      | endcustomer   | true                 | bPartnerDesadvRecipientGLN | ediSetting |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endcustomer              | product                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference   |
      | order      | true    | endcustomer   | 2024-06-10  | po_ref_@Date@ |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 10         |
    When the order identified by order is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched  | orderLine                 | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched                        | D            | true                | false       |
    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched                        | shipment              |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine              | shipment              | product                 | 10          | true      | orderLine                     |
    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier | OPT.M_HU_PackagingCode_ID.Identifier | OPT.GTIN_PackingMaterial | OPT.SeqNo |
      | packMain                      | true                | null                   | null                                 | null                     | 1         |
    Then EDI_Desadv is found:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_Desadv_ID.Identifier |
      | endcustomer              | order                 | desadv                   |
    # This DESADV has exactly one line (single order line); capture it for the emptying mutations below.
    Then EDI_DesadvLine records are found:
      | EDI_DesadvLine_ID | EDI_Desadv_ID |
      | desadvLine        | desadv        |

  @from:cucumber
  @Id:S0353_031
  Scenario: A DESADV line whose delivered qty is set to 0 is exported in the no-pack section
    # A user zeroes the delivered qty of the line in the WebUI (window 540256).
    Then EDI_DesadvLine records are updated:
      | EDI_DesadvLine_ID | QtyDeliveredInUOM |
      | desadvLine        | 0                 |
    # The emptied line no longer belongs in the pack export, so the no-pack format must select it.
    Then the DESADV no-pack-line export-format selects only:
      | EDI_Desadv_ID | EDI_DesadvLine_ID |
      | desadv        | desadvLine        |

  @from:cucumber
  @Id:S0353_032
  Scenario: A DESADV line that is set inactive is exported in the no-pack section
    # A user deactivates the line in the WebUI (window 540256).
    Then EDI_DesadvLine records are updated:
      | EDI_DesadvLine_ID | IsActive |
      | desadvLine        | N        |
    # The inactive line must be selected by the no-pack format (its WhereClause matches IsActive='N').
    Then the DESADV no-pack-line export-format selects only:
      | EDI_Desadv_ID | EDI_DesadvLine_ID |
      | desadv        | desadvLine        |

  @from:cucumber
  @Id:S0353_033
  Scenario: When the line's DESADV-Pack is set inactive the line is exported in the no-pack section
    # A user deactivates the line's DESADV-Pack in the WebUI; the line then has no active pack.
    Then EDI_Desadv_Pack records are updated
      | EDI_Desadv_Pack_ID.Identifier | OPT.IsActive |
      | packMain                      | N            |
    Then the DESADV no-pack-line export-format selects only:
      | EDI_Desadv_ID | EDI_DesadvLine_ID |
      | desadv        | desadvLine        |
