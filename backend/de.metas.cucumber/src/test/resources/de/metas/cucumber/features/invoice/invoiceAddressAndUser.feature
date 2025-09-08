@from:cucumber
@ghActions:run_on_executor5
Feature: Validate default address and contact is considered on invoice based on invoicing params

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier      | Name                           |
      | purchaseProduct | purchaseProduct_S0170_16082022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value          |
      | ps_1       | pricing_system_name | S0170_16082022 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_1                          | DE                        | EUR                 | price_list_name_PO | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_PO     | pl_PO                     | purchaseOrder-PLV | 2022-07-02 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_PO                            | purchaseProduct         | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                        | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer |
      | bpartner_1 | BPartnerName_S0170_Purchase | ps_1                          | Y            | N              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.Name          |
      | bpLocation        | 0123456789011 | bpartner_1               | N                   | bpLocation        |
      | bpLocationDefault | 0123456789012 | bpartner_1               | Y                   | bpLocationDefault |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name             | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default |
      | bpContact             | bpContact        | bpartner_1                   | bpLocation                            | N                           |
      | bpContactDefault      | bpContactDefault | bpartner_1                   | bpLocationDefault                     | Y                           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | o_1        | false   | bpartner_1               | bpLocation                            | 2022-08-16  | po_ref_mock     | POO             | bpLocation                      | bpContact                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | purchaseProduct         | 10         |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | bpLocation                        | purchaseProduct         | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | purchaseProduct         | 10          | true      | 10             |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 10               | 10           | receiptLine_1                 |

  @Id:S0170_100
  Scenario: Generate invoice with UpdateLocationAndContactForInvoice param set to true
  _Given x1 M_Product
  _And x1 C_BPartner
  _And x2 C_BPartner_Location associated to the C_BPartner ('Location1' has C_BPartner_Location.IsBillToDefault flag set on true and 'Location2' set on false)
  _And x2 AD_User associated to the C_BPartner ('Contact1' has AD_User.IsBillToContact_Default flag set on true and the 'Contact2' set on false)
  _And one C_Order created with C_Order.Bill_Location_ID = 'Location2' and C_Order.Bill_User_ID 'Contact2'
  _When generate invoice with "UpdateLocationAndContactForInvoice" parameter set on true
  _Then C_Invoice was generated with C_BPartner_Location_ID = 'Location1' and AD_User_ID = 'Contact1', i.e. the default values

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsUpdateLocationAndContactForInvoice |
      | invoiceCand_1                     | Y                                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.AD_User_ID.Identifier |
      | invoice_1               | bpartner_1               | bpLocationDefault                 | po_ref_mock     | 30 Tage netto | true      | CO        | bpContactDefault          |

  @Id:S0170_110
  Scenario: Generate invoice with UpdateLocationAndContactForInvoice param set to false
  _Given x1 M_Product
  _And x1 C_BPartner
  _And x2 C_BPartner_Location associated to the C_BPartner ('Location1' has C_BPartner_Location.IsBillToDefault flag set on true and 'Location2' set on false)
  _And x2 AD_User associated to the C_BPartner ('Contact1' has AD_User.IsBillToContact_Default flag set on true and the 'Contact2' set on false)
  _And one C_Order created with C_Order.Bill_Location_ID = 'Location2' and C_Order.Bill_User_ID 'Contact2'
  _When generate invoice with "UpdateLocationAndContactForInvoice" parameter set on false
  _Then C_Invoice was generated with C_BPartner_Location_ID = 'Location2' and AD_User_ID = 'Contact2', i.e. the values coming from C_Order

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsUpdateLocationAndContactForInvoice |
      | invoiceCand_1                     | N                                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.AD_User_ID.Identifier |
      | invoice_1               | bpartner_1               | bpLocation                        | po_ref_mock     | 30 Tage netto | true      | CO        | bpContact                 |