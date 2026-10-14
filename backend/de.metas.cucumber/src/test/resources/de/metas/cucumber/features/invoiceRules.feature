@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00703_Invoice_Rule
@ghActions:run_on_executor5
Feature: invoice rules
## F00703: Invoice Rule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  Scenario: we can invoice a sales order with invoice rule after delivery
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name           |
      | p_1        | d_salesProduct |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_1       | d_pricing_system_name | d_pricing_system_value | d_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_1      | pl_1                      | d_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer2 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | processed | DocStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      |

  Scenario: we can invoice a sales order with invoice rule after pick
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name           |
      | p_2        | p_salesProduct |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_2                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_2       | p_pricing_system_name | p_pricing_system_value | p_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_2       | ps_2                          | DE                        | EUR                 | p_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_2      | pl_2                      | p_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                             | p_2                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_2 | p_Endcustomer | N            | Y              | ps_2                          | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_2        | 0123456789012 | endcustomer_2            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_2        | true    | endcustomer_2            | 2021-04-15  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_2                     | 12         |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_1               | 12           | s_s_2                            | p_2                     |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_2                            | p_2                     | 6         | hu_1               |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_2                              | ol_2                      | 6            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_2                              | 6               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_2                              | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | processed | DocStatus |
      | invoice_2               | endcustomer_2            | l_2                               | po_ref_mock     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_2               | invoice_2               | p_2                     | 6           | true      |

  Scenario: we can invoice a sales order with invoice rule after pick and over-picked quantity
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name             |
      | p_3        | p_salesProduct_2 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_3                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                    | Value                    | OPT.Description              | OPT.IsActive |
      | ps_3       | p_pricing_system_name_2 | p_pricing_system_value_2 | p_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_3       | ps_3                          | DE                        | EUR                 | p_price_list_name_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_3      | pl_3                      | p_salesOrder-PLV_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_3       | plv_3                             | p_3                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_3 | p_Endcustomer2 | N            | Y              | ps_3                          | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_3        | 0123456789012 | endcustomer_3            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_3        | true    | endcustomer_3            | 2021-04-15  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_3                     | 10         |
    And the order identified by o_3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_3      | ol_3                      | N             |
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_2               | 12           | s_s_3                            | p_3                     |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_3                            | p_3                     | 12        | hu_2               |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_3                              | ol_3                      | 12           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_3                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_3                              | invoice_3               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | processed | DocStatus |
      | invoice_3               | endcustomer_3            | l_3                               | po_ref_mock     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_3_1             | invoice_3               | p_3                     | 12          | true      |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | s_s_3                            | P                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | poreference | processed | DocStatus |
      | shipment              | endcustomer_3            | l_3                               | 2021-04-15  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1_1           | shipment              | p_3                     | 12          | true      |


  Scenario: we can double-invoice a sales order with invoice rule after pick, if we pick some quantity and then override the qty to deliver to be equal to qty ordered
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name             |
      | p_3        | p_salesProduct_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                    | Value                    |
      | ps_3       | p_pricing_system_name_3 | p_pricing_system_value_3 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_3       | ps_3                          | DE                        | EUR                 | p_price_list_name_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_3      | pl_3                      | p_salesOrder-PLV_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_3       | plv_3                             | p_3                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_3 | p_Endcustomer_3 | N            | Y              | ps_3                          | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_3        | 0123456789012 | endcustomer_3            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_3        | true    | endcustomer_3            | 2021-04-15  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_3                     | 10         |
    And the order identified by o_3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_3      | ol_3                      | N             |
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_2               | 5            | s_s_3                            | p_3                     |
    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | QtyPicked | M_HU_ID.Identifier |
      | s_s_3                            | p_3                     | 5         | hu_2               |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_3                      |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | endcustomer_3               | p_3                     | 50                  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_3               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | processed | DocStatus |
      | invoice_3               | endcustomer_3            | l_3                               | po_ref_mock     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceline_3_1             | invoice_3               | p_3                     | 5           | true      |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_3                            | 10                        |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_3                            |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | s_s_3                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | poreference | processed | DocStatus |
      | shipment_1            | endcustomer_3            | l_3                               | 2021-04-15  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1_1           | shipment_1            | p_3                     | 10          | true      |
    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_3                            |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_3                            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyPickList | OPT.Processed |
      | s_s_3                            |                           | 0                | 10               | 10             | 5               | false         |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier  | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1, shipment_2 | s_s_3                            | P                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | poreference | processed | DocStatus |
      | shipment_2            | endcustomer_3            | l_3                               | 2021-04-15  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1_1           | shipment_2            | p_3                     | 5           | true      |
    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_3                            |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_3                            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyPickList | OPT.Processed |
      | s_s_3                            |                           | 0                | 15               | 10             | 0               | true          |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | endcustomer_3               | p_3                     | 100                 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_3, invoice_4    | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | processed | DocStatus |
      | invoice_4               | endcustomer_3            | l_3                               | po_ref_mock     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_4_1             | invoice_4               | p_3                     | 10          | true      |

  @Id:S30448_TC1
  Scenario: MOrder.setBPartner inherits InvoiceRule and IsAutoInvoice from BP group when BP has no direct value
    # Verifies that MOrder.setBPartner (called from beforeSave when no location is set) resolves
    # InvoiceRule and IsAutoInvoice via BPartnerEffectiveBL (group chain), not raw bp.getInvoiceRule().
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                           |
      | product    | d_invRuleGroupInheritance_prod |
    And metasfresh contains M_PricingSystems
      | Identifier     | Name                           | Value                           | OPT.IsActive |
      | pricingSystem  | d_invRuleGroupInheritance_ps   | d_invRuleGroupInheritance_psv   | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList  | pricingSystem                 | DE                        | EUR                 | d_invRuleGroupInheritance_pl    | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID.Identifier | Name                             | ValidFrom  |
      | priceListVersion | priceList                 | d_invRuleGroupInheritance_plv    | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice | priceListVersion                  | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BP_Groups:
      | Identifier | OPT.InvoiceRule | OPT.IsAutoInvoice |
      | bpGroup    | D               | Y                 |
    And metasfresh contains C_BPartners:
      | Identifier | Name                             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | C_BP_Group_ID.Identifier |
      | customer   | d_invRuleGroupInheritance_cust   | N            | Y              | pricingSystem                 | bpGroup                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation | 0123456789013         | customer                 | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order      | true    | customer                 | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    When the order identified by order is completed
    Then validate the created orders
      | C_Order_ID.Identifier | InvoiceRule | IsAutoInvoice |
      | order                 | D           | true          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule  | orderLine                 | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule                  | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule                  | shipment              |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | InvoiceRule | IsAutoInvoice |
      | invoiceCandidate                  | D           | true          |

  @Id:S30448_TC2
  Scenario: OLCand explicit invoiceRule and isAutoInvoice win over BP-group defaults
    # Guard scenario: when an OLCand request explicitly sets invoiceRule=I (Immediate) and isAutoInvoice=false,
    # those values must be preserved on the resulting order even though the BP's group has InvoiceRule=D/IsAutoInvoice=Y.
    # The OLCand path sets the location before save, so MOrder.beforeSave skips setBPartner → no group override.
    Given metasfresh contains M_PricingSystems
      | Identifier        | Name                              | Value                             | OPT.IsActive |
      | psOLCandGuard     | d_olcandGuard_pricingSystem       | d_olcandGuard_pricingSystemValue  | true         |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | plOLCandGuard   | psOLCandGuard                 | DE                        | EUR                 | d_olcandGuard_priceList  | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID.Identifier | Name                           | ValidFrom  |
      | plvOLCandGuard   | plOLCandGuard             | d_olcandGuard_priceListVersion | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier       | Name                    |
      | productOLCand    | d_olcandGuard_product   |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | ppOLCandGuard    | plvOLCandGuard                    | productOLCand           | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BP_Groups:
      | Identifier       | OPT.InvoiceRule | OPT.IsAutoInvoice |
      | bpGroupOLCand    | D               | Y                 |
    And metasfresh contains C_BPartners:
      | Identifier       | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | C_BP_Group_ID.Identifier |
      | custOLCandGuard  | d_olcandGuard_customer   | N            | Y              | psOLCandGuard                 | bpGroupOLCand            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN              | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | locOLCandGuard        | 0123456789014    | custOLCandGuard          | Y                   | Y                   |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "olcandGuard30448",
            "externalLineId": "olcandGuard30448_0",
            "externalSystemCode": "Other",
            "bpartner": {
                "bpartnerIdentifier": "val-d_olcandGuard_customer",
                "bpartnerLocationIdentifier": "gln-0123456789014"
            },
            "dateRequired": "2021-04-16",
            "dateOrdered": "2021-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-d_olcandGuard_product",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "olcandGuard30448",
            "deliveryViaRule": "S",
            "isAutoInvoice": false,
            "invoiceRule": "I"
        }
    ]
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "olcandGuard30448",
    "externalSystemCode": "Other",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | orderOLCandGuard      |
    And validate the created orders
      | C_Order_ID.Identifier | InvoiceRule | IsAutoInvoice |
      | orderOLCandGuard      | I           | false         |
