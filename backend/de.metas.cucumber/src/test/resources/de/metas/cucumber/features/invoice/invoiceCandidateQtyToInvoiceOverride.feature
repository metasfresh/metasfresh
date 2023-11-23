@from:cucumber
@ghActions:run_on_executor2
Feature: Extend invoice-candidate test-coverage to IC QtyToInvoiceOverride

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0156.2_100
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 100 PCE / 25 kg
  C_Invoice_Candidate.QtyDelivered = 100 PCE / 26.3547 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyToInvoice = 100 PCE / 26.3547 kg

    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_1 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1                     | PCE                    | KGM                  | 0.25         | Y                        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_1 | pricing_system_value_20072022_1 | pricing_system_description_20072022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.InvoicableQtyBasedOn |
      | pp_1       | plv_1                             | p_1                     | 2.0      | KGM               | Normal                        | CatchWeight              |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override |
      | s_s_1                            | 26.3547                        |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | o_1                       | ol_1                          | 100          | 26.3547                    | 100            | 25.000         | 100                            | 100           | 26.3547            | 100              | 26.3547               | 0               | 0                    | 52.71               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.0000                     | 100            | 25.000         | 0                              | 100           | 26.3547            | 100              | 26.3547               | 100             | 26.3547              | 0                   | true          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 100         | true      | 2                | 2               | 52.71          | 0            |

  @from:cucumber
  @Id:S0156.2_110
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 150 PCE / 37.5 kg
  C_Invoice_Candidate.QtyDelivered = 150 PCE / 36.985 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyToInvoice = 150 PCE / 36.985 kg

    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_2 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1                     | PCE                    | KGM                  | 0.25         | Y                        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_2 | pricing_system_value_20072022_2 | pricing_system_description_20072022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.InvoicableQtyBasedOn |
      | pp_1       | plv_1                             | p_1                     | 2.0      | KGM               | Normal                        | CatchWeight              |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_2 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override |
      | s_s_1                            | 36.985                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 150         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | o_1                       | ol_1                          | 150          | 36.985                     | 150            | 37.500         | 150                            | 150           | 36.985             | 150              | 36.985                | 0               | 0                    | 73.97               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 150            | 37.500         | 0                              | 150           | 36.985             | 150              | 36.985                | 150             | 36.985               | 0                   | true          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 150         | true      | 2                | 2               | 73.97          | 0            |

  @from:cucumber
  @Id:S0156.2_120
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 100 PCE / 25 kg
  C_Invoice_Candidate.QtyDelivered = 102 PCE / 27.0156 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyToInvoice = 102 PCE / 27.0156 kg

    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_3 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1                     | PCE                    | KGM                  | 0.25         | Y                        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_3 | pricing_system_value_20072022_3 | pricing_system_description_20072022_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.InvoicableQtyBasedOn |
      | pp_1       | plv_1                             | p_1                     | 2.0      | KGM               | Normal                        | CatchWeight              |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_3 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override | OPT.QtyToDeliver_Override |
      | s_s_1                            | 27.0156                        | 102                       |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 102         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | o_1                       | ol_1                          | 102          | 27.0156                    | 100            | 25.000         | 102                            | 102           | 27.0156            | 102              | 27.0156               | 0               | 0                    | 54.03               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.0000                     | 100            | 25.000         | 0                              | 102           | 27.0156            | 102              | 27.0156               | 102             | 27.0156              | 0                   | true          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 102         | true      | 2                | 2               | 54.03          | 0            |

  @from:cucumber
  @Id:S0156.2_130
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 150 PCE / 37.5 kg
  C_Invoice_Candidate.QtyDelivered = 148 PCE / 35.684 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyToInvoice = 148 PCE / 35.684 kg

    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_4 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | p_1                     | PCE                    | KGM                  | 0.25         | Y                        |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_4 | pricing_system_value_20072022_4 | pricing_system_description_20072022_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.InvoicableQtyBasedOn |
      | pp_1       | plv_1                             | p_1                     | 2.0      | KGM               | Normal                        | CatchWeight              |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_4 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliverCatch_Override | OPT.QtyToDeliver_Override |
      | s_s_1                            | 35.684                         | 148                       |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 148         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | o_1                       | ol_1                          | 148          | 35.684                     | 150            | 37.500         | 148                            | 148           | 35.684             | 148              | 35.684                | 0               | 0                    | 71.37               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 150            | 37.500         | 0                              | 148           | 35.684             | 148              | 35.684                | 148             | 35.684               | 0                   | true          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 148         | true      | 2                | 2               | 71.37          | 0            |

  @from:cucumber
  @Id:S0156.2_140
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyDelivered = 150 PCE
  CatchWeightPrice = N
  C_Invoice_Candidate.QtyToInvoiceOverride = 153 PCE

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyToInvoice = 153 PCE
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_5 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_5 | pricing_system_value_20072022_5 | pricing_system_description_20072022_5 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_5 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_5 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 2.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_5 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 150         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoice_candidate_1               | 153                       |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 153          | 150                        | 150            | 150            | 153                            | 150           | 150                | 150              | 150                   | 0               | 0                    | 306                 | false         |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0                          | 150            | 150            | 0                              | 150           | 150                | 150              | 150                   | 153             | 153                  | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 153         | true      | 2                | 2               | 306            | 0            |

  @from:cucumber
  @Id:S0156.2_150
  Scenario:
  In:
  C_Order.IsSoTrx = Y
  C_Order.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyDelivered = 100 PCE
  CatchWeightPrice = N
  C_Invoice_Candidate.QtyToInvoiceOverride = 98 PCE

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyToInvoice = 98 PCE
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_20072022_6 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_20072022_6 | pricing_system_value_20072022_6 | pricing_system_description_20072022_6 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_20072022_6 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20072022_6 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 2.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_20072022_6 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | s_1                   | s_s_1                            | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | po_ref_mock | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | sl1_1                     | s_1                   | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoice_candidate_1               | 98                        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 98           | 100                        | 100            | 100            | 98                             | 100           | 100                | 100              | 100                   | 0               | 0                    | 196                 | false         |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyPicked | OPT.QtyPickedInUOM | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 2                          | 100            | 100            | 0                              | 100           | 100                | 100              | 100                   | 98              | 98                   | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus |
      | invoice_1               | endcustomer_1            | l_1                               | po_ref_mock     | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 98          | true      | 2                | 2               | 196            | 0            |

  @from:cucumber
  @Id:S0156.2_160
  Scenario:
  In:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 100 PCE / 25 kg
  C_Invoice_Candidate.QtyDelivered = 100 PCE / 26 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyToInvoice = 100 PCE / 26 kg
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endvendor_1              | 2156423           |
    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | Y                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | CatchWeight              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_PaymentTerm_ID |
      | o_1        | N       | endvendor_1              | 2021-04-16  | po_ref_mock     | POO             | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21072022_3      | o_1                   | ol_1                      | endvendor_1              | 2205173                           | 2005577                 | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21072022_3      | N               | 1     | N               | 1     | N               | 100         | 101                                | 1000006                      |

    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.ValueNumber |
      | processedTopHU     | WeightGross          | 51              |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueNumber |
      | processedTopHU     | WeightGross          | 51.000      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21072022_3      | inOut_21072022_3      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 100          | 26.000                     | 100            | 25.000         | 100                            | 100              | 26.000                | 0               | 0                    | 26                  | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 100            | 25.000         | 0                              | 100              | 26.000                | 100             | 26.000               | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus |
      | invoice_1               | endvendor_1              | 2205173                           | po_ref_mock     | 30 Tage netto | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | 2005577                 | 100         | true      | 1                | 1               | 26.00          | 0            |

    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | N                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | Nominal                  |

  @from:cucumber
  @Id:S0156.2_170
  Scenario:
  In:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 150 PCE / 37.5 kg
  C_Invoice_Candidate.QtyDelivered = 150 PCE / 35 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyToInvoice = 150 PCE / 35 kg
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endvendor_1              | 2156423           |
    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | Y                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | CatchWeight              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_PaymentTerm_ID |
      | o_1        | N       | endvendor_1              | 2021-04-16  | po_ref_mock     | POO             | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21072022_5      | o_1                   | ol_1                      | endvendor_1              | 2205173                           | 2005577                 | 150        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21072022_5      | N               | 1     | N               | 1     | N               | 150         | 101                                | 1000006                      |

    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.ValueNumber |
      | processedTopHU     | WeightGross          | 60              |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueNumber |
      | processedTopHU     | WeightGross          | 60.000      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21072022_5      | inOut_21072022_5      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 150          | 35.000                     | 150            | 37.500         | 150                            | 150              | 35.000                | 0               | 0                    | 35                  | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 150            | 37.500         | 0                              | 150              | 35.000                | 150             | 35.000               | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus |
      | invoice_1               | endvendor_1              | 2205173                           | po_ref_mock     | 30 Tage netto | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | 2005577                 | 150         | true      | 1                | 1               | 35.00          | 0            |
    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | N                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | Nominal                  |

  @from:cucumber
  @Id:S0156.2_200
  Scenario:
  In:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 100 PCE / 25 kg
  C_Invoice_Candidate.QtyDelivered = 102 PCE / 26 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 	100 PCE
  C_Invoice_Candidate.QtyToInvoice = 102 PCE / 26 kg
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endvendor_1              | 2156423           |
    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | Y                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | CatchWeight              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_PaymentTerm_ID |
      | o_1        | N       | endvendor_1              | 2021-04-16  | po_ref_mock     | POO             | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21072022_8      | o_1                   | ol_1                      | endvendor_1              | 2205173                           | 2005577                 | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21072022_8      | N               | 1     | N               | 1     | N               | 102         | 101                                | 1000006                      |

    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.ValueNumber |
      | processedTopHU     | WeightGross          | 51              |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueNumber |
      | processedTopHU     | WeightGross          | 51.000      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21072022_8      | inOut_21072022_8      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 102          | 26.000                     | 100            | 25.000         | 102                            | 102              | 26.000                | 0               | 0                    | 26                  | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 100            | 25.000         | 0                              | 102              | 26.000                | 102             | 26.000               | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus |
      | invoice_1               | endvendor_1              | 2205173                           | po_ref_mock     | 30 Tage netto | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | 2005577                 | 102         | true      | 1                | 1               | 26.00          | 0            |

    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | N                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | Nominal                  |

  @from:cucumber
  @Id:S0156.2_210
  Scenario:
  In:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 150 PCE / 37.5 kg
  C_Invoice_Candidate.QtyDelivered = 147 PCE / 35 kg
  CatchWeightPrice = Y
  C_Invoice_Candidate.QtyToInvoiceOverride = /

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 	150 PCE
  C_Invoice_Candidate.QtyToInvoice = 147 PCE / 35 kg
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endvendor_1              | 2156423           |
    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | Y                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | CatchWeight              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.C_PaymentTerm_ID |
      | o_1        | N       | endvendor_1              | 2021-04-16  | po_ref_mock     | POO             | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21072022_9      | o_1                   | ol_1                      | endvendor_1              | 2205173                           | 2005577                 | 150        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21072022_9      | N               | 1     | N               | 1     | N               | 147         | 101                                | 1000006                      |

    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.ValueNumber |
      | processedTopHU     | WeightGross          | 60              |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueNumber |
      | processedTopHU     | WeightGross          | 60.000      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21072022_9      | inOut_21072022_9      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 147          | 35.000                     | 150            | 37.500         | 147                            | 147              | 35.000                | 0               | 0                    | 35                  | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0.000                      | 150            | 37.500         | 0                              | 147              | 35.000                | 147             | 35.000               | 0                   | true          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus |
      | invoice_1               | endvendor_1              | 2205173                           | po_ref_mock     | 30 Tage netto | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | 2005577                 | 147         | true      | 1                | 1               | 35.00          | 0            |

    And update C_UOM_Conversion:
      | C_UOM_Conversion_ID.Identifier | OPT.IsCatchUOMForProduct |
      | 2002195                        | N                        |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 | OPT.InvoicableQtyBasedOn |
      | 540024                       | 1.00         | KGM                   | Nominal                  |

  @from:cucumber
  @Id:S0156.2_180
  Scenario:
  IN:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 100 PCE
  C_Invoice_Candidate.QtyDelivered = 100 PCE
  CatchWeightPrice = N
  C_Invoice_Candidate.QtyToInvoiceOverride = 97

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 	100 PCE
  C_Invoice_Candidate.QtyToInvoice = 97 PCE
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22032022_1 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 100 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 100 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 100 | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22032022_1 | pricing_system_value_22032022_1 | pricing_system_description_22032022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22032022_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22032022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 1.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_22032022_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21032022_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21032022_1      | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21032022_1      | inOut_210320222_1     |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoice_candidate_1               | 97                        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 97           | 100                        | 100            | 100            | 97                             | 100              | 100                   | 0               | 0                    | 97                  | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 3                          | 100            | 100            | 0                              | 100              | 100                   | 97              | 97                   | 0                   | true          |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 97          | true      | 1                | 1               | 97             | 0            |

  @from:cucumber
  @Id:S0156.2_190
  Scenario:
  In:
  C_Order.IsSoTrx = N
  C_Order.QtyOrdered = 150 PCE
  C_Invoice_Candidate.QtyDelivered = 150 PCE
  CatchWeightPrice = N
  C_Invoice_Candidate.QtyToInvoiceOverride = 152

  Expectation:
  C_Invoice_Candidate.QtyOrdered = 	150 PCE
  C_Invoice_Candidate.QtyToInvoice = 152 PCE
  C_Invoice_Candidate.QtyToInvoiceBeforeDiscount = 0
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22032022_2 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 152 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 152 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 152 | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22032022_2 | pricing_system_value_22032022_2 | pricing_system_description_22032022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22032022_2 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22032022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 1.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_22032022_2 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 150        |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_21032022_1      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 150        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_21032022_1      | N               | 1     | N               | 1     | N               | 150         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_21032022_1      | inOut_210320222_1     |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoice_candidate_1               | 152                       |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 152          | 150                        | 150            | 150            | 152                            | 150              | 150                   | 0               | 0                    | 152                 | false         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyToInvoiceInUOM_Calc | OPT.QtyOrdered | OPT.QtyEntered | OPT.QtyToInvoiceBeforeDiscount | OPT.QtyDelivered | OPT.QtyDeliveredInUOM | OPT.QtyInvoiced | OPT.QtyInvoicedInUOM | OPT.NetAmtToInvoice | OPT.Processed |
      | invoice_candidate_1               | o_1                       | ol_1                          | 0            | 0                          | 150            | 150            | 0                              | 150              | 150                   | 152             | 152                  | 0                   | true          |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.C_DocType_ID.Name |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock     | 1000002     | true      | CO        | Eingangsrechnung      |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 152         | true      | 1                | 1               | 152            | 0            |