@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020
@ghActions:run_on_executor2
Feature: shipment packing material lines split per project
## F68020: C_Project propagation through Purchase ↔ Sales flow

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
    And metasfresh contains C_Projects:
      | Identifier |
      | P1         |
      | P2         |
      | P3         |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: sales order lines for three projects ship in one shipment and invoice with one packing material line per project
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And metasfresh contains M_Products:
      | Identifier |
      | p_goods    |
      | p_pm       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_goods      | 10.0     | PCE      |
      | plv_1                  | p_pm         | 1.0      | PCE      |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_TU    |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID | HU_UnitType |
      | huVersion  | huPI_TU    | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_1                    | p_pm         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem   | huVersion          | 10  | PM       | pm_1                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier    | M_HU_PI_Item_ID | Qty | M_Product_ID | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsInvoiceable | OPT.M_Packing_Material_Product_ID |
      | huItemProduct | huPIItem        | 10  | p_goods      | 2021-01-01 | false                  | true              | p_pm                              |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | so_1       | true    | customer_1    | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID |
      | sol_1      | so_1       | p_goods      | 20         | P1           |
      | sol_2      | so_1       | p_goods      | 30         | P2           |
      | sol_3      | so_1       | p_goods      | 40         | P3           |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | sol_1                     | huItemProduct               |
      | sol_2                     | huItemProduct               |
      | sol_3                     | huItemProduct               |
    And the order identified by so_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
      | ss_2       | sol_2          | N             |
      | ss_3       | sol_3          | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.C_Project_ID.Identifier |
      | ss_1                  | P1                          |
      | ss_2                  | P2                          |
      | ss_3                  | P3                          |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_1                  |
      | ss_2                  |
      | ss_3                  |

    Then the shipment schedules ss_1,ss_2,ss_3 are shipped in exactly one M_InOut identified by shipment_1

    And validate the created shipment lines
      | M_InOut_ID | C_OrderLine_ID | C_Project_ID | movementqty |
      | shipment_1 | sol_1          | P1           | 20          |
      | shipment_1 | sol_2          | P2           | 30          |
      | shipment_1 | sol_3          | P3           | 40          |

    And the packing material lines of shipment shipment_1 are exactly:
      | Identifier | M_Product_ID | C_Project_ID | MovementQty |
      | pm_line_P1 | p_pm         | P1           | 2           |
      | pm_line_P2 | p_pm         | P2           | 3           |
      | pm_line_P3 | p_pm         | P3           | 4           |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.M_InOutLine_ID.Identifier |
      | ic_sol_1                          | sol_1                     | null                          |
      | ic_sol_2                          | sol_2                     | null                          |
      | ic_sol_3                          | sol_3                     | null                          |
      | ic_pm_P1                          | null                      | pm_line_P1                    |
      | ic_pm_P2                          | null                      | pm_line_P2                    |
      | ic_pm_P3                          | null                      | pm_line_P3                    |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | OPT.M_Product_ID | C_Project_ID |
      | ic_sol_1               | p_goods          | P1           |
      | ic_sol_2               | p_goods          | P2           |
      | ic_sol_3               | p_goods          | P3           |
      | ic_pm_P1               | p_pm             | P1           |
      | ic_pm_P2               | p_pm             | P2           |
      | ic_pm_P3               | p_pm             | P3           |

    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_sol_1               |
      | ic_sol_2               |
      | ic_sol_3               |
      | ic_pm_P1               |
      | ic_pm_P2               |
      | ic_pm_P3               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_1    | ic_sol_1               |
      | invoice_1    | ic_sol_2               |
      | invoice_1    | ic_sol_3               |
      | invoice_1    | ic_pm_P1               |
      | invoice_1    | ic_pm_P2               |
      | invoice_1    | ic_pm_P3               |

    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | OPT.C_Project_ID.Identifier |
      | il_pm_P1         | invoice_1    | p_pm         | 2           | pm_line_P1     | P1                          |
      | il_pm_P2         | invoice_1    | p_pm         | 3           | pm_line_P2     | P2                          |
      | il_pm_P3         | invoice_1    | p_pm         | 4           | pm_line_P3     | P3                          |
