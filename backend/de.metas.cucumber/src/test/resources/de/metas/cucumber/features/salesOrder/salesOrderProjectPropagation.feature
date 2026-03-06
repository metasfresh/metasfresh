@from:cucumber
@ghActions:run_on_executor4
Feature: C_Project_ID propagates through sales order fulfillment workflow

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
      | project_1  |

  @from:cucumber
  Scenario: C_Project_ID propagates from sales order to shipment schedule to shipment to invoice candidate
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_Project_ID |
      | so_1       | true    | customer_1    | 2021-04-17  | project_1    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID |
      | sol_1      | so_1       | p_1          | 10         | project_1    |
    And the order identified by so_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | C_Project_ID |
      | ss_1                  | project_1    |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                  | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |

    Then validate the created shipments
      | M_InOut_ID | C_Project_ID |
      | shipment_1 | project_1    |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | C_Project_ID |
      | shipment_1 | p_1          | project_1    |

    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | sol_1          |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_Project_ID |
      | ic_1                   | project_1    |

  @from:cucumber
  Scenario: C_Project_ID propagates to packing material invoice candidates
    Given metasfresh contains M_Products:
      | Identifier   |
      | p_product    |
      | p_packingMat |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_product    | 10.0     | PCE      |
      | plv_1                  | p_packingMat | 1.0      | PCE      |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID | HU_UnitType |
      | huVersion  | huPI       | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_1                    | p_packingMat |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem   | huVersion          | 10  | PM       | pm_1                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier    | M_HU_PI_Item_ID | Qty | M_Product_ID | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsInvoiceable | OPT.M_Packing_Material_Product_ID |
      | huItemProduct | huPIItem        | 10  | p_product    | 2021-01-01 | false                  | true              | p_packingMat                      |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_Project_ID |
      | so_1       | true    | customer_1    | 2021-04-17  | project_1    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID |
      | sol_1      | so_1       | p_product    | 10         | project_1    |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | sol_1                     | huItemProduct               |
    And the order identified by so_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | sol_1          | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | C_Project_ID |
      | ss_1                  | project_1    |

      # Validate packing material order line was also created
    Then validate the created order lines
      | Identifier | C_Order_ID | M_Product_ID | QtyOrdered | C_Project_ID |
      | sol_2      | so_1       | p_packingMat | 1          | project_1    |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                  | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_1                  | shipment_1 |

    Then validate the created shipment lines
      | Identifier | M_InOut_ID | M_Product_ID | C_Project_ID |
      | sline_1    | shipment_1 | p_product    | project_1    |
      | sline_2    | shipment_1 | p_packingMat | project_1    |

    # Validate two invoice candidates: one for product, one for packing material
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID    | C_Order_ID |
      | ic_product, ic_packingMat | so_1       |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | OPT.M_Product_ID | C_Project_ID |
      | ic_product             | p_product        | project_1    |
      | ic_packingMat          | p_packingMat     | project_1    |

    # Invoice the candidates and verify single invoice with 2 lines, both with project
    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_product             |
      | ic_packingMat          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_1    | ic_product             |
      | invoice_1    | ic_packingMat          |

    And validate created invoices
      | C_Invoice_ID | C_BPartner_ID | C_Project_ID |
      | invoice_1    | customer_1    | project_1    |

    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced | OPT.C_Project_ID.Identifier |
      | il_product       | invoice_1    | p_product    | 10          | project_1                   |
      | il_packingMat    | invoice_1    | p_packingMat | 1           | project_1                   |

