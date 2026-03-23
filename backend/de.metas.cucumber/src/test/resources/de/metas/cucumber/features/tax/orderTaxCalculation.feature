@from:cucumber
@ghActions:run_on_executor7
Feature: Validate tax calculation for orders taking into account dropship location

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name                     | Value           |
      | switzerland_org      | Switzerland Organization | switzerland_org |
    And metasfresh contains C_Tax
      | Identifier        | C_TaxCategory_ID.InternalName | Name                  | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | OPT.AD_Org_ID.Identifier |
      | switzerland_tax   | Normal                        | switzerland_tax_S0151 | 2021-04-02 | 2.5  | CH                       | CH                        | switzerland_org          |
      | swiss-to-neth_tax | Normal                        | swiss-to-neth_tax     | 2021-04-02 | 2.5  | CH                       | NL                        | switzerland_org          |
    And metasfresh contains M_Products:
      | Identifier    | Value         | Name          | OPT.AD_Org_ID.Identifier |
      | product_S0151 | product_S0151 | product_S0151 | switzerland_org          |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value               | OPT.AD_Org_ID.Identifier |
      | ps_1       | pricing_system_name | pricing_system_name | switzerland_org          |
    And metasfresh contains M_PriceLists
      | Identifier              | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                            | SOTrx | IsTaxIncluded | PricePrecision | OPT.AD_Org_ID.Identifier |
      | pl_Switzerland_Sales    | ps_1                          | CH                        | EUR                 | price_list_Switzerland_Sales    | true  | false         | 2              | switzerland_org          |
      | pl_Netherlands_Sales    | ps_1                          | NL                        | EUR                 | price_list_Netherlands_Sales    | true  | false         | 2              | switzerland_org          |
      | pl_Switzerland_Purchase | ps_1                          | CH                        | EUR                 | price_list_Switzerland_Purchase | false | false         | 2              | switzerland_org          |
      | pl_Netherlands_Purchase | ps_1                          | NL                        | EUR                 | price_list_Netherlands_Purchase | false | false         | 2              | switzerland_org          |
    And metasfresh contains M_PriceList_Versions
      | Identifier               | M_PriceList_ID.Identifier | Name                     | ValidFrom  | OPT.AD_Org_ID.Identifier |
      | plv_Switzerland_Sales    | pl_Switzerland_Sales      | Switzerland-PLV-Sales    | 2022-07-01 | switzerland_org          |
      | plv_Netherlands_Sales    | pl_Netherlands_Sales      | Netherlands-PLV-Sales    | 2022-07-01 | switzerland_org          |
      | plv_Switzerland_Purchase | pl_Switzerland_Purchase   | Switzerland-PLV-Purchase | 2022-07-01 | switzerland_org          |
      | plv_Netherlands_Purchase | pl_Netherlands_Purchase   | Netherlands-PLV-Purchase | 2022-07-01 | switzerland_org          |
    And metasfresh contains M_ProductPrices
      | Identifier              | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_Switzerland_Sales    | plv_Switzerland_Sales             | product_S0151           | 10.0     | PCE               | Normal                        |
      | pp_Netherlands_Sales    | plv_Netherlands_Sales             | product_S0151           | 10.0     | PCE               | Normal                        |
      | pp_Switzerland_Purchase | plv_Switzerland_Purchase          | product_S0151           | 10.0     | PCE               | Normal                        |
      | pp_Netherlands_Purchase | plv_Netherlands_Purchase          | product_S0151           | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name                 | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.AD_Org_ID.Identifier | OPT.AD_OrgBP_ID.Identifier |
      | bpartner_1   | BPartnerNameS0151    | ps_1                          | Y            | Y              | switzerland_org          |                            |
      | org_bpartner | OrgBPartnerNameS0151 | ps_1                          |              |                | switzerland_org          | switzerland_org            |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.AD_Org_ID.Identifier |
      | location_Switzerland     | CH          | switzerland_org          |
      | location_Netherlands     | NL          | switzerland_org          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                  | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault | OPT.Name                          | OPT.C_Location_ID.Identifier |
      | bp_location_Switzerland     | 0123456789011 | bpartner_1               | Y                   | Y                   | Bpartner_Switzerland_Location     | location_Switzerland         |
      | bp_location_Netherlands     | 0123456789012 | bpartner_1               | Y                   | Y                   | Bpartner_Netherlands_Location     | location_Netherlands         |
      | org_Bp_location_Switzerland | 0123456789013 | org_bpartner             | Y                   | Y                   | Org_Bpartner_Switzerland_Location | location_Switzerland         |
    And metasfresh contains AD_OrgInfo:
      | AD_OrgInfo_ID.Identifier | AD_Org_ID.Identifier | Org_BPartner_ID.Identifier | OrgBP_Location_ID.Identifier |
      | switzerland_org_info     | switzerland_org      | org_bpartner               | org_Bp_location_Switzerland  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                 | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | switzerland_warehouse     | switzerland_warehouse | switzerlandWarehouse | org_bpartner                 | org_Bp_location_Switzerland           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | switzerland_locator     | switzerland_locator | switzerland_warehouse     |

  @Id:S0151_100
  Scenario: Calculate tax for purchase order that has IsDropShip flag set to false and order's BPartnerLocation.Country != org.BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one purchase C_Order with C_BPartnerLocation in Netherlands and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for international transaction
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for international transaction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | o_1        | false   | bpartner_1               | 2022-08-18  | po_ref_mock     | POO             | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | swiss-to-neth_tax       |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | swiss-to-neth_tax                 |

  @Id:S0151_110
  Scenario: Calculate tax for purchase order that has IsDropShip flag set to false and order's C_BPartnerLocation.Country == org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one purchase C_Order with C_BPartnerLocation in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for Switzerland
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for Switzerland

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | o_1        | false   | bpartner_1               | 2022-08-18  | po_ref_mock     | POO             | bp_location_Switzerland               | switzerland_org          | switzerland_warehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | switzerland_tax         |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | switzerland_tax                   |

  @Id:S0151_120
  Scenario: Calculate tax for purchase order that has IsDropShip flag set to true and order's Dropship_Location.Country != org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one purchase C_Order with C_BPartnerLocation in Netherlands, DropShip_Location in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for international transaction
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for international transaction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DropShip_Location_ID.Identifier |
      | o_1        | false   | bpartner_1               | 2022-08-18  | po_ref_mock     | POO             | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         | bp_location_Switzerland             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | swiss-to-neth_tax       |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | swiss-to-neth_tax                 |

  @Id:S0151_130
  Scenario: Calculate tax for purchase order that has IsDropShip flag set to true and order's Dropship_Location.Country == org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one purchase C_Order with C_BPartnerLocation in Switzerland, DropShip_Location in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for Switzerland
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured Switzerland

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DropShip_Location_ID.Identifier |
      | o_1        | false   | bpartner_1               | 2022-08-18  | po_ref_mock     | POO             | bp_location_Switzerland               | switzerland_org          | switzerland_warehouse         | bp_location_Switzerland             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | switzerland_tax         |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | switzerland_tax                   |

  @Id:S0151_140
  Scenario: Calculate tax for sales order that has IsDropShip flag set to false and order's C_BPartnerLocation.Country != organization's C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one sales C_Order with C_BPartnerLocation in Netherlands and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for international transaction
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for international transaction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-08-18  | po_ref_mock     | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | swiss-to-neth_tax       |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | swiss-to-neth_tax                 |

  @Id:S0151_150
  Scenario: Calculate tax for sales order that has IsDropShip flag set to false and order's C_BPartnerLocation.Country == org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one sales C_Order with C_BPartnerLocation in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for Switzerland
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for Switzerland

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-08-18  | po_ref_mock     | bp_location_Switzerland               | switzerland_org          | switzerland_warehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | switzerland_tax         |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | switzerland_tax                   |

  @Id:S0151_160
  Scenario: Calculate tax for sales order that has IsDropShip flag set to true and order's Dropship_Location.Country != org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one sales C_Order with C_BPartnerLocation in Netherlands, DropShip_Location in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for Switzerland
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured for Switzerland

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.IsDropShip | OPT.DropShip_Location_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-08-18  | po_ref_mock     | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         | Y              | bp_location_Switzerland             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | switzerland_tax         |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | switzerland_tax                   |

  @Id:S0151_170
  Scenario: Calculate tax for sales order that has IsDropShip flag set to true and order's Dropship_Location.Country == org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one sales C_Order with C_BPartnerLocation in Switzerland, DropShip_Location in Switzerland and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for Switzerland
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured Switzerland

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.IsDropShip | OPT.DropShip_Location_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-08-18  | po_ref_mock     | bp_location_Switzerland               | switzerland_org          | switzerland_warehouse         | Y              | bp_location_Switzerland             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | switzerland_tax         |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | switzerland_tax                   |

  @Id:S0151_180
  Scenario: Calculate tax for sales order that has IsDropShip flag set to true and order's C_BPartnerLocation.Country == Dropship_Location.Country != org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one sales C_Order with C_BPartnerLocation in Netherlands, DropShip_Location in Netherlands and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for international transaction
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured international transaction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.IsDropShip | OPT.DropShip_Location_ID.Identifier |
      | o_1        | true    | bpartner_1               | 2022-08-18  | po_ref_mock     | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         | Y              | bp_location_Netherlands             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | swiss-to-neth_tax       |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | swiss-to-neth_tax                 |

  @Id:S0151_190
  Scenario: Calculate tax for purchase order that has IsDropShip flag set to true and order's C_BPartnerLocation.Country == Dropship_Location.Country != org.C_BPartnerLocation.Country
  _Given 2x C_Tax records -> one configured for Switzerland and one for international transaction
  _When completing one purchase C_Order with C_BPartnerLocation in Netherlands, DropShip_Location in Netherlands and the AD_Org located in Switzerland
  _Then the C_Tax_ID on C_OrderLine should be the one configured for international transaction
  _And the C_Tax_Effective_ID on C_InvoiceCandidate should be the one configured international transaction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_Org_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DropShip_Location_ID.Identifier |
      | o_1        | false   | bpartner_1               | 2022-08-18  | po_ref_mock     | POO             | bp_location_Netherlands               | switzerland_org          | switzerland_warehouse         | bp_location_Netherlands             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0151           | 1          |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_Tax_ID.Identifier |
      | ol_1                      | o_1                   | 2022-08-18  | product_S0151           | 0            | 1          | 0           | 10    | 0        | EUR          | true      | swiss-to-neth_tax       |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Effective_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0            | swiss-to-neth_tax                 |