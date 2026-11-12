@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F36020_Outbound_Documents
@ghActions:run_on_executor1
@topic:docOutboundFollowsDocument
Feature: Outbound document log follows its document through reactivation and re-completion

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-03T09:15:00+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

    And metasfresh contains C_Doc_Outbound_Config:
      | AD_Table.Name | DocBaseType |
      | C_Order       | SOO         |

    And metasfresh contains M_Products:
      | Identifier   | Name         |
      | dofd_product | dofd_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name      | Value     |
      | dofd_ps    | dofd_ps_1 | dofd_ps_1 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | dofd_pl    | dofd_ps                       | DE                        | EUR                 | dofd_pl_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | dofd_plv   | dofd_pl                   | dofd_plv_so | 2022-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | dofd_pp    | dofd_plv                          | dofd_product            | 10.0     | Normal                        | PCE               |
    # customers A and B are in different business partner groups (Standard 1000000 and Kunde 2000228)
    And metasfresh contains C_BPartners:
      | Identifier | Name      | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_bp_a  | dofd_bp_a | 1000000           | dofd_ps                       | Y              |
      | dofd_bp_b  | dofd_bp_b | 2000228           | dofd_ps                       | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_loc_a | 3333323456781 | dofd_bp_a                | true         | true         |
      | dofd_loc_b | 3333323456782 | dofd_bp_b                | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier  | Name        | OPT.EMail         | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | dofd_user_a | dofd_user_a | dofd_a@email.test | dofd_bp_a                    | dofd_loc_a                            |
      | dofd_user_b | dofd_user_b | dofd_b@email.test | dofd_bp_b                    | dofd_loc_b                            |

  Scenario: Reference case replayed - outbound log follows the order through reactivation, customer change and re-completion
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | dofd_order | true    | dofd_bp_a                | 2022-02-02  | dofd_loc_a                            | dofd_user_a               | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_orderLine | dofd_order            | dofd_product            | 1          |

    # step 1: completed order -> one outbound document, customer A, CO, one PDF line
    When the order identified by dofd_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocBaseType | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | SOO         | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |

    # step 2: reactivate, change customer to B, save without completing -> same log, IP, still customer A, not found by a CO selection
    When the order identified by dofd_order is reactivated
    And update order
      | Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier |
      | dofd_order | dofd_bp_b                | dofd_loc_b                        | dofd_user_b           |
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | IP        |
    And no C_Doc_Outbound_Log with DocStatus CO exists for record "dofd_order"

    # step 3: mass print while IP -> customer A and IP unchanged, new history line with IP
    When Massendruck PDF is run for C_Doc_Outbound_Log "dofd_log"
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | IP        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |

    # step 4: complete again -> still one log, customer B, CO, new history line
    # the re-completion happens the next morning, as in the reference case; with the clock still frozen at the first completion,
    # both PDFs would carry the same Created and "the newest PDF" (ordered by Created) would be ambiguous
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    When the order identified by dofd_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_b                | 2000228       | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_3                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |

    # step 5: mass print again -> prints B's new PDF, header stays B / CO; history CO, IP, CO, CO
    When Massendruck PDF is run for C_Doc_Outbound_Log "dofd_log"
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_b                | 2000228       | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_3                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |
      | dofd_line_4                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |

  # TC2a
  Scenario: E-mail recipient and invoice-e-mail flag follow the customer change - equal those of a new outbound document for the new customer
    # B's contact is flagged "invoice as e-mail"; for an order the outbound document's flag is off nevertheless
    Given metasfresh contains C_BPartners:
      | Identifier   | Name         | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_2a_bp_a | dofd_2a_bp_a | 1000000           | dofd_ps                       | Y              |
      | dofd_2a_bp_b | dofd_2a_bp_b | 2000228           | dofd_ps                       | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier    | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_2a_loc_a | 3333323456791 | dofd_2a_bp_a             | true         | true         |
      | dofd_2a_loc_b | 3333323456792 | dofd_2a_bp_b             | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier     | Name           | OPT.EMail            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInvoiceEmailEnabled |
      | dofd_2a_user_a | dofd_2a_user_a | dofd_2a_a@email.test | dofd_2a_bp_a                 | dofd_2a_loc_a                         |                           |
      | dofd_2a_user_b | dofd_2a_user_b | dofd_2a_b@email.test | dofd_2a_bp_b                 | dofd_2a_loc_b                         | Y                         |
    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | dofd_2a_order    | true    | dofd_2a_bp_a             | 2022-02-02  | dofd_2a_loc_a                         | dofd_2a_user_a            | F                | S                   |
      | dofd_2a_order_ct | true    | dofd_2a_bp_b             | 2022-02-02  | dofd_2a_loc_b                         | dofd_2a_user_b            | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_2a_orderLine    | dofd_2a_order         | dofd_product            | 1          |
      | dofd_2a_orderLine_ct | dofd_2a_order_ct      | dofd_product            | 1          |
    # the order's own e-mail, as the contact callout sets it in the UI
    And update order
      | Identifier       | EMail                |
      | dofd_2a_order    | dofd_2a_a@email.test |
      | dofd_2a_order_ct | dofd_2a_b@email.test |

    # control: a newly created outbound document for an order of customer B with the same data
    When the order identified by dofd_2a_order_ct is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled | DocStatus |
      | dofd_2a_log_ct                   | dofd_2a_order_ct     | C_Order       | dofd_2a_bp_b             | dofd_2a_user_b                      | dofd_2a_b@email.test | false                 | CO        |

    When the order identified by dofd_2a_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled | DocStatus |
      | dofd_2a_log                      | dofd_2a_order        | C_Order       | dofd_2a_bp_a             | dofd_2a_user_a                      | dofd_2a_a@email.test | false                 | CO        |

    When the order identified by dofd_2a_order is reactivated
    And update order
      | Identifier    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | EMail                |
      | dofd_2a_order | dofd_2a_bp_b             | dofd_2a_loc_b                     | dofd_2a_user_b        | dofd_2a_b@email.test |
    # the re-completion's PDF needs a later Created than the first PDF (frozen clock)
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    And the order identified by dofd_2a_order is completed

    # same values as the control's newly created outbound document
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled | DocStatus |
      | dofd_2a_log                      | dofd_2a_order        | C_Order       | dofd_2a_bp_b             | dofd_2a_user_b                      | dofd_2a_b@email.test | false                 | CO        |

  # TC2b
  Scenario: New customer without any e-mail - the old customer's e-mail address is cleared
    Given metasfresh contains C_BPartners:
      | Identifier   | Name         | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_2b_bp_a | dofd_2b_bp_a | 1000000           | dofd_ps                       | Y              |
      | dofd_2b_bp_b | dofd_2b_bp_b | 2000228           | dofd_ps                       | Y              |
    # B has no e-mail on its contact, its location or itself
    And metasfresh contains C_BPartner_Locations:
      | Identifier    | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_2b_loc_a | 3333323456793 | dofd_2b_bp_a             | true         | true         |
      | dofd_2b_loc_b | 3333323456794 | dofd_2b_bp_b             | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier     | Name           | OPT.EMail            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | dofd_2b_user_a | dofd_2b_user_a | dofd_2b_a@email.test | dofd_2b_bp_a                 | dofd_2b_loc_a                         |
      | dofd_2b_user_b | dofd_2b_user_b |                      | dofd_2b_bp_b                 | dofd_2b_loc_b                         |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | dofd_2b_order | true    | dofd_2b_bp_a             | 2022-02-02  | dofd_2b_loc_a                         | dofd_2b_user_a            | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_2b_orderLine | dofd_2b_order         | dofd_product            | 1          |
    And update order
      | Identifier    | EMail                |
      | dofd_2b_order | dofd_2b_a@email.test |

    When the order identified by dofd_2b_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | DocStatus |
      | dofd_2b_log                      | dofd_2b_order        | C_Order       | dofd_2b_bp_a             | dofd_2b_user_a                      | dofd_2b_a@email.test | CO        |

    # customer changed to B; the order's e-mail and contact fields are cleared
    When the order identified by dofd_2b_order is reactivated
    And update order
      | Identifier    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | EMail |
      | dofd_2b_order | dofd_2b_bp_b             | dofd_2b_loc_b                     | null                  | null  |
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    And the order identified by dofd_2b_order is completed

    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress | CurrentEMailAddressCC | DocStatus |
      | dofd_2b_log                      | dofd_2b_order        | C_Order       | dofd_2b_bp_b             | null                                | null                | null                  | CO        |

  # TC2c
  Scenario: PO reference and order date changed while reopened - the outbound document shows the new PDF's document fields
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.POReference |
      | dofd_2c_order | true    | dofd_bp_a                | 2022-02-02  | dofd_loc_a                            | dofd_user_a               | F                | S                   | dofd_2c_po_1    |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_2c_orderLine | dofd_2c_order         | dofd_product            | 1          |

    When the order identified by dofd_2c_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | POReference  | DateDoc    | DocBaseType | DocStatus |
      | dofd_2c_log                      | dofd_2c_order        | C_Order       | dofd_2c_po_1 | 2022-02-02 | SOO         | CO        |

    When the order identified by dofd_2c_order is reactivated
    And update order
      | Identifier    | POReference  | DateOrdered |
      | dofd_2c_order | dofd_2c_po_2 | 2022-02-03  |
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    And the order identified by dofd_2c_order is completed

    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | POReference  | DateDoc    | DocBaseType | DocStatus |
      | dofd_2c_log                      | dofd_2c_order        | C_Order       | dofd_2c_po_2 | 2022-02-03 | SOO         | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocStatus | AD_Archive_ID.Identifier |
      | dofd_2c_line_1                        | dofd_2c_log                      | dofd_2c_order        | C_Order       | CO        | dofd_2c_archive_1        |
      | dofd_2c_line_2                        | dofd_2c_log                      | dofd_2c_order        | C_Order       | CO        | dofd_2c_archive_2        |
    # document no. and file name are those of the new PDF
    And after not more than 10s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | AD_Archive_ID.Identifier |
      | dofd_2c_log                      | dofd_2c_order        | C_Order       | dofd_2c_archive_2        |

  # TC2f
  Scenario: Same customer, the order's contact is changed while reopened - the outbound document carries the new contact's e-mail
    Given metasfresh contains C_BPartners:
      | Identifier | Name       | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_2f_bp | dofd_2f_bp | 1000000           | dofd_ps                       | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_2f_loc | 3333323456795 | dofd_2f_bp               | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier     | Name           | OPT.EMail            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | dofd_2f_user_1 | dofd_2f_user_1 | dofd_2f_1@email.test | dofd_2f_bp                   | dofd_2f_loc                           |
      | dofd_2f_user_2 | dofd_2f_user_2 | dofd_2f_2@email.test | dofd_2f_bp                   | dofd_2f_loc                           |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | dofd_2f_order | true    | dofd_2f_bp               | 2022-02-02  | dofd_2f_loc                           | dofd_2f_user_1            | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_2f_orderLine | dofd_2f_order         | dofd_product            | 1          |
    And update order
      | Identifier    | EMail                |
      | dofd_2f_order | dofd_2f_1@email.test |

    When the order identified by dofd_2f_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | DocStatus |
      | dofd_2f_log                      | dofd_2f_order        | C_Order       | dofd_2f_bp               | dofd_2f_user_1                      | dofd_2f_1@email.test | CO        |

    When the order identified by dofd_2f_order is reactivated
    And update order
      | Identifier    | AD_User_ID.Identifier | EMail                |
      | dofd_2f_order | dofd_2f_user_2        | dofd_2f_2@email.test |
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    And the order identified by dofd_2f_order is completed

    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | DocStatus |
      | dofd_2f_log                      | dofd_2f_order        | C_Order       | dofd_2f_bp               | dofd_2f_user_2                      | dofd_2f_2@email.test | CO        |

  # TC2e
  Scenario: Invoice PDF re-created - a recipient picked by hand is replaced by the new outbound document's one, the invoice-e-mail flag is off as on creation
    Given set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh contains C_Doc_Outbound_Config:
      | AD_Table.Name | DocBaseType |
      | C_Invoice     | ARI         |
    # the partner's invoice-e-mail setting is empty; both contacts are flagged "invoice as e-mail"
    And metasfresh contains C_BPartners:
      | Identifier | Name       | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_2e_bp | dofd_2e_bp | 1000000           | dofd_ps                       | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_2e_loc | 3333323456796 | dofd_2e_bp               | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier     | Name           | OPT.EMail            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsBillToContact_Default | OPT.IsInvoiceEmailEnabled |
      | dofd_2e_user_1 | dofd_2e_user_1 | dofd_2e_1@email.test | dofd_2e_bp                   | dofd_2e_loc                           | Y                           | Y                         |
      | dofd_2e_user_2 | dofd_2e_user_2 | dofd_2e_2@email.test | dofd_2e_bp                   | dofd_2e_loc                           | N                           | Y                         |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | dofd_2e_order | true    | dofd_2e_bp               | 2022-02-02  | dofd_2e_loc                           | dofd_2e_user_1            | dofd_2e_user_1              |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_2e_orderLine | dofd_2e_order         | dofd_product            | 1          |
    And the order identified by dofd_2e_order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | dofd_2e_ic                        | dofd_2e_orderLine         | 0            |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | dofd_2e_ic                        | I                        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | dofd_2e_ic                        | dofd_2e_orderLine         | 1            |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | dofd_2e_ic                        | true                   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | dofd_2e_ic                        | dofd_2e_invoice         |

    # as created: the bill contact; the flag follows the partner's (empty) setting
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled | DocStatus |
      | dofd_2e_log                      | dofd_2e_invoice      | C_Invoice     | dofd_2e_bp               | dofd_2e_user_1                      | dofd_2e_1@email.test | false                 | CO        |

    # a user picks the other contact by hand; the flag then comes from that contact
    When the e-mail recipient is picked by hand on C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | CurrentEMailRecipient_ID.Identifier |
      | dofd_2e_log                      | dofd_2e_user_2                      |
    Then after not more than 10s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled |
      | dofd_2e_log                      | dofd_2e_invoice      | C_Invoice     | dofd_2e_user_2                      | dofd_2e_2@email.test | true                  |

    # the PDF is re-created (partner unchanged); it needs a later Created than the first PDF (frozen clock)
    When metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    And the document PDF of "dofd_2e_invoice" is re-created
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | CurrentEMailRecipient_ID.Identifier | CurrentEMailAddress  | IsInvoiceEmailEnabled | DocStatus |
      | dofd_2e_log                      | dofd_2e_invoice      | C_Invoice     | dofd_2e_bp               | dofd_2e_user_1                      | dofd_2e_1@email.test | false                 | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocStatus | AD_Archive_ID.Identifier |
      | dofd_2e_line_1                        | dofd_2e_log                      | dofd_2e_invoice      | C_Invoice     | CO        | dofd_2e_archive_1        |
      | dofd_2e_line_2                        | dofd_2e_log                      | dofd_2e_invoice      | C_Invoice     | CO        | dofd_2e_archive_2        |
